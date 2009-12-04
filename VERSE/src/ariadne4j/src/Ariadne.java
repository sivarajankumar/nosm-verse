

import java.io.*;
//import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import com.nosm.elearning.ariadne.AriadneData;

import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.game.Game;
import com.nosm.elearning.ariadne.model.game.GameNode;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;
import com.nosm.elearning.ariadne.model.game.uri.OL3GameUri;
import com.nosm.elearning.ariadne.model.game.uri.OLGameUri;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.ErrorConstants;
import com.nosm.elearning.ariadne.util.HttpFetch;
import com.nosm.elearning.ariadne.util.TestConstants;
import com.nosm.elearning.ariadne.util.XPathReader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Ariadne extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	private static final long serialVersionUID = 7526471155622776147L;
	String lineSep = Constants.lineSep;
	public Ariadne() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		StringBuffer reply = new StringBuffer();
		String sessid = "";
		String mnodeid = "";
		//String linkid = "";
		String aMode = ""; // admin gets you uipairs and id, slplay gets you short sl xml
		GameNode thisNode = null;
		boolean isLive = true;

		try {
			if ((String) request.getParameter("mnodeid") != null){
				mnodeid = (String) request.getParameter("mnodeid");
			}
			if ((String) request.getParameter("mnodeid") != null){
				mnodeid = (String) request.getParameter("mnodeid");
			}
			if ((String) request.getParameter("sessid") != null){
				sessid = (String) request.getParameter("sessid");
			}
			aMode = (String) request.getParameter("mode");

			if (aMode.equals("test")){
				isLive = false;
				aMode = "admin";
			}

			if (sessid != null || sessid != ""){
				String savedSSID = (String)request.getSession().getAttribute("game-sessid");
				if (savedSSID != null){  // does it match the current session?
					if (sessid.equals(savedSSID)){
						throw new RuntimeException(cc(sessid, ErrorConstants.XML_SSID_PREFIX, ErrorConstants.XML_SSID_SUFFIX ) +
								request.getSession().getAttribute("game-sessid")+ErrorConstants.XML_ET );
					}
				}
			}
			if (aMode == null || aMode == "") {
				reply.append(ErrorConstants.XML_NO_MODE);
			} else {
				// TODO: if aMode=stats: asset usage by type, by sl_type
				if ( (mnodeid == null || mnodeid == "")) {
					//if (aMode.toLowerCase() == "admin"){
					reply.append(ErrorConstants.XML_NO_NODE);
				} else {
					String action = (String)request.getParameter("action");
					if (action != null && action.equals("del")){
						String a2rem = (String)request.getParameter("asset");
						if (a2rem.equals(""))throw new Exception(ErrorConstants.XML_NO_ASSET);
						AriadneData.removeAssetFromSeq(Integer.parseInt(a2rem));
						AriadneData.deleteAsset(Integer.parseInt(a2rem)) ;
						reply.append(cc(mnodeid, ErrorConstants.XML_REDIR_PREFIX ,
								ErrorConstants.XML_REDIR_SUFFIX)); // use response.sendRedirect()?
					}else{

						GameUri gameUrl = new OL3GameUri(Integer.parseInt(mnodeid));
						String versionStr = (String)request.getParameter("v");
						if (versionStr!=null && (versionStr).equals("2")){
							gameUrl = new OLGameUri(Integer.parseInt(mnodeid));
						}
						String gameAddress = gameUrl.getPath() + gameUrl.getGameId() +
						gameUrl.getLinkPrefix()+ mnodeid + gameUrl.getPathSuffix() ;
						//System.out.println("calling OL3 with url "+gameAddress);
						org.w3c.dom.Document gameResults = null;
						try{
							if (isLive){// testing switch
								if (mnodeid.equals(gameUrl.getLinkStart()))  {
									// if ( sessid == null || sessid.equals("") ){
									sessid = HttpFetch.getString4Url("/django/session/create/", new ArrayList(), false).trim();
									request.getSession().setAttribute("game-sessid", sessid);
									// (re)start at root node:
									gameAddress = gameUrl.getPath() + gameUrl.getGameId() +"/root"+ gameUrl.getPathSuffix() ;
									ArrayList qparams = new ArrayList();
									qparams.add(new BasicNameValuePair(gameUrl.getLinkSsid(), sessid));
									gameResults = XPathReader.loadXMLFrom(HttpFetch.getString4Url(gameAddress, qparams, true));// use Post
								}else{
									gameResults = XPathReader.loadXMLFrom(HttpFetch.getString4Url(gameAddress, null, false));
								}
							}else{
								gameResults = XPathReader.loadXMLFrom(getTestXML(gameUrl.getNodeId()));
							}
							Game thisGame = new Game(gameResults);
							thisNode = new GameNode(thisGame, gameResults);
							thisNode.setAssets((ArrayList)AriadneData.selectAssetsByMNodeId(thisNode.getId(), false));
							//thisGame.addNode(thisNode);
							if (sessid != null ){
								if (!isLive){
									sessid = TestConstants.NODE_SSID;
								}
								if (!sessid.equals(thisGame.getSession())){  // does it match the current session?
								      throw new RuntimeException(cc(thisGame.getSession(), ErrorConstants.XML_SSID_PREFIX , ErrorConstants.XML_SSID_SUFFIX )+
									        sessid +ErrorConstants.XML_ET );
								   }
							}else{
								sessid = thisGame.getSession();
								request.getSession().setAttribute("game-sessid", sessid);
							}
						}catch (SAXException se) {
							se.printStackTrace(response.getWriter());
						}
					}
				}
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			if (reply.toString().length() >0){
				out.println(reply.toString());
			}else{
				out.println(XPathReader.xmlToString(thisNode.getAriadneXml()));
			}
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
			//response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +e.getMessage()+ErrorConstants.XML_ET );
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//implicitly admin mode?
		//throw new RuntimeException("<Error>in doPost</Error>");

		Asset asset = new Asset(
				(String) request.getParameter(Constants.UI_NAME),
				(String) request.getParameter(Constants.UI_TYPE),
				(String) request.getParameter(Constants.UI_VAL),
				(String) request.getParameter(Constants.UI_PAIRS),
				(String) request.getParameter(Constants.UI_TARGET),
				Integer.parseInt((String) request.getParameter(Constants.UI_ID))
		);

		try {
			String mappedid = request.getParameter("assetid");
			if (mappedid != null){
				if (mappedid.equals("")) mappedid = "0"; //hack to avoid nulls
				if(AriadneData.doesAssetExist(Integer.parseInt(mappedid))) {
					asset.setId(Integer.parseInt(mappedid));
					AriadneData.updateAsset(asset);
					AriadneData.resetSeqForNode(asset, asset.getName());
				} else {
					AriadneData.insertAsset(asset);
					AriadneData.addNode2Seq(asset);
				}
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(cc(asset.getNodeid(), ErrorConstants.XML_REDIR_PREFIX,
					ErrorConstants.XML_REDIR_SUFFIX));

		} catch (SQLException sqe) {
			sqe.printStackTrace(response.getWriter());
			//response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +sqe.getMessage()+ErrorConstants.XML_ET );
		}catch (Exception e) {
			//response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +e.getMessage()+ErrorConstants.XML_ET );
			e.printStackTrace(response.getWriter());
		}
	}

	private static String getTestXML(int mnodeId){
		String retXML = "";
		if (mnodeId== 8336){
			retXML = TestConstants.NODE1;
		}else{
			if (mnodeId == 8337){
				retXML = TestConstants.NODE2;
			}else{
				if (mnodeId == 8338){
					retXML = TestConstants.NODE3;
				}
			}
		}
		if (mnodeId == 8){
			retXML = TestConstants.NODE4;
		}
		if (mnodeId == 9){
			retXML = TestConstants.NODE5;
		}
		return retXML;
	}

	private static String cc(int id, String prefix, String suffix){
		return prefix + Integer.toString(id) + suffix;
	}

	private static String cc(String id, String prefix, String suffix){
		return prefix + id + suffix;
	}
}