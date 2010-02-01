
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import com.nosm.elearning.ariadne.*;
import com.nosm.elearning.ariadne.constants.*;
import com.nosm.elearning.ariadne.model.*;
import com.nosm.elearning.ariadne.model.game.*;
import com.nosm.elearning.ariadne.model.game.uri.*;

public class Ariadne extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	private static final long serialVersionUID = 7526471155622776147L;

	public Ariadne() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		String mnodeid = "";
		String aMode = ""; // admin gets you uipairs and id, slplay gets you short sl xml
		GameNode thisNode = null;
		Game thisGame = null;
		boolean isLive = true;

		try {
			// node id
			if (request.getParameter("mnodeid") != null){
				mnodeid = (String) request.getParameter("mnodeid");
			}

			aMode = (String) request.getParameter("mode");
			if (aMode.equals("test")){
				isLive = false;
				aMode = "admin";
			}

			if (aMode == null || aMode == "") {
				response.setContentType("text/html");
				response.getWriter().println(EC.XML_NO_MODE);
			} else {
				String action = (String)request.getParameter("action");
				if (aMode.equals("admin") && (action != null && action.equals("del"))){
					String a2rem = (String)request.getParameter("asset");
					if (!(a2rem != null) || a2rem.equals(""))throw new Exception(EC.XML_NO_ASSET);
					AriadneData.removeAssetFromSeq(Integer.parseInt(a2rem));
					AriadneData.deleteAsset(Integer.parseInt(a2rem)) ;
					response.setContentType("text/html");
					response.sendRedirect("/ariadne4j/index2.html?mnodeid="+mnodeid+ "&mode=admin");
					//response.getWriter().println(EC.XML_REDIR_PREFIX +mnodeid+ EC.XML_REDIR_SUFFIX);
				}else{
					// using web.xml params:
					// gameUrl.setGameId(Integer.parseInt(getInitParameter("Game_ID")));
					// gameUrl.setUser(getInitParameter("GAME_USER"));
					// gameUrl.setPassword(getInitParameter("GAME_PASSWORD"));

					org.w3c.dom.Document gameResults = null;
					try{
						GameUri gameUrl = new OL3GameUri(); // v3 by default;
						boolean isV2 = false;
						if ((String)request.getParameter("v")!=null
								&& ((String)request.getParameter("v")).equals("2")) isV2 = true;

						if (isV2){
							gameUrl = new OLGameUri(mnodeid);
							//gameUrl.setHost(getInitParameter("GAME_HOST"));
						}

						if (mnodeid != null && mnodeid != "")  {
							if(mnodeid != gameUrl.getLinkStart()){
								gameUrl.setNodeId(mnodeid);
							}
							/*else{
								if (isV2){
									gameUrl.setNodeId(null);
								}
							}
							*/
						}else{
							// starting a new session
							if(isV2){
								GameList thisList = new GameList(XPathReader.loadXMLFrom( // retrieve our game from remote.asp
										HttpFetch.getString4Url(gameUrl, false),isLive));

								//thisGame = thisList.getGames().get(0); // == thisList.currentGame;
								//if (thisGame == null ){
							//		thisGame = thisList.currentGame;
								//}
								//System.out.println("OL2: Game.getName: "+thisGame.getName());
								//System.out.println("OL2: Game.getRootNode: "+thisGame.getRootNode());
								//gameUrl.setNodeId( Integer.toString(thisGame.getRootNode()) );


								//if(thisList.getGames().size() > 1){
								response.setContentType("text/xml");
								PrintWriter out = response.getWriter();
								//out.println(XPathReader.xmlToString(thisNode.getAriadneXml(aMode.equals("admin"))));
								out.println(XPathReader.xmlToString(thisList.getAriadneXml(true, false)));
								return;
							//}



							}else{ // is ol3, and no id or start link included:
								throw new RuntimeException(EC.XML_SSID_PREFIX+"problem setting the node id in Ariadne servlet for OL3: "+ gameUrl.getNodeId()+", "+mnodeid+
										EC.XML_SSID_SUFFIX + gameUrl.getSession() +EC.XML_ET );
							}
						}

						if (isLive){
							if (request.getParameter(gameUrl.getSsidPrefix()) != null &&
									request.getParameter(gameUrl.getSsidPrefix()).length() > 30){
								gameUrl.setSession((String) request.getParameter(gameUrl.getSsidPrefix()));
								System.out.println("found a ssid in the query string: " + (String) request.getParameter(gameUrl.getSsidPrefix()));
							}else{
								if(gameUrl.getRootPath().indexOf("root") > -1 ){ //is v3, can also use reflection to do this ie. if (gameUrl instanceof OL3GameUri)
									String aSSID = HttpFetch.getString4Url("/django/session/create/", null, false).trim();
									gameUrl.setSession(aSSID);
								}
							}

							ArrayList <NameValuePair> qparams = null;
							if (gameUrl.getSession() != null){
								qparams = new ArrayList <NameValuePair>();
								qparams.add(new BasicNameValuePair(gameUrl.getSsidPrefix(), gameUrl.getSession()));
								System.out.println("about to HttpFetch(ssid) with:"+ gameUrl.getAddress());
								gameResults = XPathReader.loadXMLFrom(
										HttpFetch.getString4Url(gameUrl.getAddress(), qparams, (!isV2)),// use Get if not v3, otherwise use post with sessionid param
										isLive); // use 'loose' builder factory
							}else{
								System.out.println("about to HttpFetch with:"+ gameUrl.getAddress());
								gameResults = XPathReader.loadXMLFrom(
										HttpFetch.getString4Url(gameUrl, (!isV2)),// use Get if not v3, otherwise use post with sessionid param
										isLive); // use 'loose' builder factory
							}
						}else{
							gameResults = XPathReader.loadXMLFrom(getTestXML(gameUrl.getNodeId()), !isLive);
							gameUrl.setSession(TC.NODE_SSID);
						}

						System.out.println("about to parse returned xml from gameUrl.getAddress()= "+ gameUrl.getAddress(gameUrl.getSession()));
						thisGame = new Game(gameResults);
						//thisNode = new GameNode(gameUrl, thisGame, gameResults, aMode.equals("admin"));
						thisNode = new GameNode(gameUrl, thisGame, gameResults, true);

						if (gameUrl.getSession() == null || thisNode.getParentGame().getSession() == null ||
								!(gameUrl.getSession().equals(thisNode.getParentGame().getSession()))){
							throw new RuntimeException(EC.XML_SSID_PREFIX+"problem finding the ssid in Ariadne servlet"+ thisNode.getParentGame().getSession()+
									EC.XML_SSID_SUFFIX + gameUrl.getSession() +EC.XML_ET );
						}
					}catch (SAXException se) {
						System.out.println(EC.XML_BASIC_PREFIX+" parsing Exception: "+
								se.getMessage()+EC.XML_ET);
						se.printStackTrace(response.getWriter());
						//response.getWriter().println(EC.XML_BASIC_PREFIX  +
						//se.getMessage()+EC.XML_ET );
					}

				}
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				//out.println(XPathReader.xmlToString(thisNode.getAriadneXml(aMode.equals("admin"))));
				out.println(XPathReader.xmlToString(thisNode.getAriadneXml(true)));
			}
		} catch (Exception e) {
			System.out.println(EC.XML_BASIC_PREFIX+" general Exception: "+
					e.getMessage()+ EC.XML_ET);
			//response.getWriter().println(EC.XML_BASIC_PREFIX  +
			//e.getMessage()+EC.XML_ET );
			e.printStackTrace(response.getWriter());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//implicitly admin mode?
		//throw new RuntimeException("<Error>in doPost</Error>");

		Asset asset = new Asset(
				(String) request.getParameter(C.UI_NAME),
				(String) request.getParameter(C.UI_TYPE),
				(String) request.getParameter(C.UI_VAL),
				(String) request.getParameter(C.UI_PAIRS),
				(String) request.getParameter(C.UI_TARGET),
				Integer.parseInt((String) request.getParameter(C.UI_ID))
		);

		try {
			String mappedid = request.getParameter("assetid");
			if (mappedid != null){
				if (mappedid.equals("")) mappedid = "0"; //hack to avoid nulls
				if(AriadneData.doesAssetExist(Integer.parseInt(mappedid))) {
					asset.setId(Integer.parseInt(mappedid));
					AriadneData.updateAsset(asset);
					//AriadneData.resetSeqForNode(thisasset, asset);
				} else {
					AriadneData.insertAsset(asset);
					asset = AriadneData.refreshAsset(asset); // gets the id
					AriadneData.addNode2Seq(asset);
				}
			}
			// session
			String aSSID = "";
			//if (request.getSession().getAttribute("game-sessid") != null
					//&& ((String)request.getSession().getAttribute("game-sessid")).length() == 32
					//){
				//aSSID = "&sessid="+ (String)request.getSession().getAttribute("game-sessid");

			//}
			response.setContentType("text/html");
			//PrintWriter out = response.getWriter();//asset.getNodeid()
			response.sendRedirect("/ariadne4j/index2.html?mnodeid="+asset.getNodeid()+ "&mode=admin");
			//out.println(EC.XML_REDIR_PREFIX+asset.getNodeid()+aSSID+
					//EC.XML_REDIR_SUFFIX);

		} catch (SQLException sqe) {
			System.out.println(EC.XML_BASIC_PREFIX + " Connection Exception: "+
					sqe.getMessage()+EC.XML_ET);
			sqe.printStackTrace(response.getWriter());
			//response.getWriter().println(EC.XML_BASIC_PREFIX  +
			// sqe.getMessage()+EC.XML_ET );
		}catch (Exception e) {
			System.out.println(EC.XML_BASIC_PREFIX+" general Exception: "+
					e.getMessage()+EC.XML_ET);
			response.getWriter().println(EC.XML_BASIC_PREFIX  +
			 e.getMessage()+EC.XML_ET );
			//e.printStackTrace(response.getWriter());
		}
	}

	private static String getTestXML(String mnodeId){
		String retXML = "";
		if (mnodeId== "8336"){
			retXML = TC.NODE1;
		}else{
			if (mnodeId == "8337"){
				retXML = TC.NODE2;
			}else{
				if (mnodeId == "8338"){
					retXML = TC.NODE3;
				}
			}
		}
		if (mnodeId == "8"){
			retXML = TC.NODE4;
		}
		if (mnodeId == "9"){
			retXML = TC.NODE5;
		}

		if (mnodeId == "start"){
			retXML = TC.NODE4;
		}
		return retXML;
	}

}