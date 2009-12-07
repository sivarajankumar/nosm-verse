

import java.io.*;
//import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import com.nosm.elearning.ariadne.AriadneData;
import com.nosm.elearning.ariadne.HttpFetch;
import com.nosm.elearning.ariadne.XPathReader;

import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.game.Game;
import com.nosm.elearning.ariadne.model.game.GameNode;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;
import com.nosm.elearning.ariadne.model.game.uri.OL3GameUri;
import com.nosm.elearning.ariadne.model.game.uri.OLGameUri;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.ErrorConstants;
import com.nosm.elearning.ariadne.util.TestConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			// node id
			if ((String) request.getParameter("mnodeid") != null){
				mnodeid = (String) request.getParameter("mnodeid");
			}
			// ssid
			String storedSSID = null;
			if ((String)request.getParameter("sessid") != null){
				sessid = (String) request.getParameter("sessid");
				storedSSID = (String)request.getSession().getAttribute("game-sessid");
				System.out.println("savedSSID id from session cxt: "+storedSSID +", session id from uri: "+sessid);
				if (storedSSID != null && !storedSSID.equals("")){
					if (!sessid.equals(storedSSID)){ // does it match the current session?
						throw new RuntimeException(ErrorConstants.XML_SSID_PREFIX+sessid +ErrorConstants.XML_SSID_SUFFIX  +
								request.getSession().getAttribute("game-sessid")+ErrorConstants.XML_ET );
					}
				}
			}
			// mode
			aMode = (String) request.getParameter("mode");
			if (aMode.equals("test")){
				isLive = false;
				aMode = "admin";
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
					if (aMode.equals("admin") && (action != null && action.equals("del"))){
						String a2rem = (String)request.getParameter("asset");
						if (!(a2rem != null) || a2rem.equals(""))throw new Exception(ErrorConstants.XML_NO_ASSET);
						AriadneData.removeAssetFromSeq(Integer.parseInt(a2rem));
						AriadneData.deleteAsset(Integer.parseInt(a2rem)) ;
						reply.append(ErrorConstants.XML_REDIR_PREFIX +mnodeid+
								ErrorConstants.XML_REDIR_SUFFIX); // use response.sendRedirect()?
					}else{
						// MAIN
						GameUri gameUrl = new OL3GameUri();
						if (!mnodeid.equals(gameUrl.getLinkStart()))  {
							gameUrl.setNodeId(Integer.parseInt(mnodeid));
						}

						String versionStr = (String)request.getParameter("v");
						if (versionStr!=null && (versionStr).equals("2")){
							gameUrl = new OLGameUri(Integer.parseInt(mnodeid));
						}

						if (storedSSID != null){
							if(sessid.equals(storedSSID)){
								gameUrl.setSession(storedSSID);
							}else{
								System.out.println(ErrorConstants.XML_BASIC_PREFIX +
										" Servlet Stored session id: "+storedSSID+" does not match URI: " +
										sessid+ErrorConstants.XML_ET);
							}
						}
						org.w3c.dom.Document gameResults = null;
						try{
							if (isLive){// testing switch
								String gameAddress = gameUrl.getAddress(true); //use links *** SWITCH IF USING NODE SYNTAX WHEN CALLING OL3 NODE/LINK
								//System.out.println("calling OL3 with url "+gameAddress);
								if (mnodeid.equals(gameUrl.getLinkStart()))  {
									sessid = HttpFetch.getString4Url("/django/session/create/", null, false).trim();
									//System.out.println("sessid post, django fetch: "+sessid);
									gameAddress = gameUrl.getPath() + gameUrl.getGameId() +"/root"
									+ gameUrl.getPathSuffix() ; // (re)start at root node
								}
								if (sessid != null){
									if((!sessid.equals(""))){
										gameUrl.setSession(sessid);
										//System.out.println("sessid, about to call loadXMLFrom: "+sessid+"with address:" +
												//gameAddress+ErrorConstants.XML_ET);
										ArrayList <NameValuePair> qparams = new ArrayList <NameValuePair>();
										qparams.add(new BasicNameValuePair(gameUrl.getLinkSsid(), sessid));
										gameResults = XPathReader.loadXMLFrom(
												HttpFetch.getString4Url(gameAddress, qparams, true),// use Post
												isLive); // use 'loose' builder factory
									}
								}
							}else{
								gameResults = XPathReader.loadXMLFrom(getTestXML(gameUrl.getNodeId()), !isLive);
								sessid = TestConstants.NODE_SSID;
							}
							thisNode = new GameNode(gameUrl, new Game(gameResults), gameResults);
							thisNode.setAssets((ArrayList)AriadneData.selectAssetsByMNodeId(thisNode.getId(), aMode.equals("admin")));
							//thisGame.addNode(thisNode);
							if (sessid != null && sessid.equals(thisNode.getParentGame().getSession())){
								System.out.println(ErrorConstants.XML_BASIC_PREFIX+" about to save this session to servlet: "+
										sessid+ErrorConstants.XML_ET);
								request.getSession().setAttribute("game-sessid", sessid);
							}else{
								throw new RuntimeException(ErrorConstants.XML_SSID_PREFIX+thisNode.getParentGame().getSession()+
										ErrorConstants.XML_SSID_SUFFIX + sessid +ErrorConstants.XML_ET );
							}
						}catch (SAXException se) {
							System.out.println(ErrorConstants.XML_BASIC_PREFIX+" parsing Exception: "+
									se.getMessage()+ErrorConstants.XML_ET);
							//se.printStackTrace(response.getWriter());
							response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +
									se.getMessage()+ErrorConstants.XML_ET );
						}
					}
				}
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			if (reply.toString().length() >0){
				out.println(reply.toString());
			}else{
				out.println(XPathReader.xmlToString(thisNode.getAriadneXml(aMode.equals("admin"))));
			}
		} catch (Exception e) {
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+" general Exception: "+
					e.getMessage()+ ErrorConstants.XML_ET);
			response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +
			e.getMessage()+ErrorConstants.XML_ET );
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
			out.println(ErrorConstants.XML_REDIR_PREFIX+asset.getNodeid()+
					ErrorConstants.XML_REDIR_SUFFIX);

		} catch (SQLException sqe) {
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+" Connection Exception: "+
					sqe.getMessage()+ErrorConstants.XML_ET);
			sqe.printStackTrace(response.getWriter());
			//response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +
			// sqe.getMessage()+ErrorConstants.XML_ET );
		}catch (Exception e) {
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+" general Exception: "+
					e.getMessage()+ErrorConstants.XML_ET);
			//response.getWriter().println(ErrorConstants.XML_BASIC_PREFIX  +
			// e.getMessage()+ErrorConstants.XML_ET );
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

}