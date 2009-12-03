

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
import com.nosm.elearning.ariadne.model.Game;
import com.nosm.elearning.ariadne.model.GameNode;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.HttpFetch;
import com.nosm.elearning.ariadne.util.TestConstants;
import com.nosm.elearning.ariadne.util.XPathReader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Ariadne extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	private static final long serialVersionUID = 7526471155622776147L;
	HttpSession session;
	String lineSep = Constants.lineSep;
	public Ariadne() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		StringBuffer reply = new StringBuffer();
		String sessid = "";
		String mnodeid = "";
		String linkid = "";
		String aMode = ""; // admin gets you uipairs and id, slplay gets you short sl xml
		GameNode thisNode = null;
		try {
			sessid = (String) request.getParameter("sessid");
			mnodeid = (String) request.getParameter("mnodeid");
			linkid = (String) request.getParameter("linkid");
			aMode = (String) request.getParameter("mode");

			 if (sessid != null){
				 if (!sessid.equals(session.getAttribute("ol-sessid"))){  // does it match the current session?
			      throw new RuntimeException("<error>Fatal Error: session id "+sessid+" returned does not match the current session id: "
				       + session.getAttribute("ol-sessid")+"</error>");
			   }
			 }
			if (aMode == null || aMode == "") {
				reply.append("<error>Critical error: No mode or node has been defined in the request</error>");
			} else {
				// TODO: if aMode=stats: asset usage by type, by sl_type
				if ((aMode.toLowerCase() == "admin") && (mnodeid == null || mnodeid == "")) {
					reply.append("Missing required MNodeID in the request");
				} else {
					String action = (String)request.getParameter("action");
					if (action != null && action.equals("del")){
						String a2rem = (String)request.getParameter("asset");
						if (a2rem.equals(""))throw new Exception("<error>no asset id defined, cannot remove</error>");
						AriadneData.removeAssetFromSeq(Integer.parseInt(a2rem));
						AriadneData.deleteAsset(Integer.parseInt(a2rem)) ;
						reply.append("<html><head><title></title></head><body onload='window.location=\"/ariadne4j/index2.html?mnodeid="
								+mnodeid + "&mode=admin\"></body></html>"); // use response.sendRedirect()?
					}else{
						session = request.getSession();
						String gameAddress = Constants.GAME_URL_BASE_PATH3+"/link/"+ linkid + Constants.GAME_URL_PATH_SUFFIX3;
						if (linkid.equals("start") ) {
							sessid = HttpFetch.getString4Url("/django/session/create/", new ArrayList(), false);
							session.setAttribute("ol-sessid", sessid);
							// (re)start at root node:
							gameAddress = Constants.GAME_URL_BASE_PATH3+"/root/"+Constants.GAME_URL_PATH_SUFFIX3 ;
						}
						//System.out.println("calling OL3 with url "+gameAddress);
						org.w3c.dom.Document gameResults = null;
						try{
							if (true){// testing switch
								ArrayList qparams = new ArrayList();
								qparams.add(new BasicNameValuePair("sessionid", sessid));
								gameResults = XPathReader.loadXMLFrom(HttpFetch.getString4Url(gameAddress, qparams, true)); // use Post
							}else{
								gameResults = XPathReader.loadXMLFrom(getTestXML(mnodeid));
							}
							Game thisGame = new Game(gameResults);
							thisNode = new GameNode(gameResults);

							if (!sessid.equals(thisGame.getSession())){  // does it match the current session?
							      throw new RuntimeException("<error>Fatal Error: session id "+thisGame.getSession()+" returned does not match the current session id: "
								       + sessid +"</error>");
							   }
							thisGame.addNode(thisNode);
							thisNode.setParentGame(thisGame);
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
			//response.getWriter().println("<Error>AriadneControllerException: "+e.getMessage()+ e."</Error>");
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
			out.println("<html><head><title></title></head><body "+
					"onload='window.location=\"/ariadne4j/index2.html?mnodeid="
					+asset.getNodeid() + "&mode=admin\"></body></html>");

		} catch (SQLException sqe) {
			sqe.printStackTrace(response.getWriter());
			//response.getWriter().println("<Error>AriadneDataException: "+sqe.getMessage()+"</Error>");
		}catch (Exception e) {
			//response.getWriter().println("<Error>AriadneControllerException: "+e.getMessage()+"</Error>");
			e.printStackTrace(response.getWriter());
		}
	}

	private static String getTestXML(String mnodeId){
		String retXML = "";
		if (mnodeId.equals("8336")){
			retXML = TestConstants.NODE1;
		}else{
			if (mnodeId.equals("8337")){
				retXML = TestConstants.NODE2;
			}else{
				if (mnodeId.equals("8338")){
					retXML = TestConstants.NODE3;
				}
			}
		}
		if (mnodeId.equals("6")){
			retXML = TestConstants.NODE4;
		}
		if (mnodeId.equals("19")){
			retXML = TestConstants.NODE5;
		}
		return retXML;
	}
}