

import java.io.*;
//import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
//import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.nosm.elearning.ariadne.AriadneData;
//import com.nosm.elearning.ariadne.AriadneJdbc;
import com.nosm.elearning.ariadne.XPathReader;

import com.nosm.elearning.ariadne.model.Asset;
//import com.nosm.elearning.ariadne.model.AssetType;
//import com.nosm.elearning.ariadne.model.User;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.HttpFetch;
import com.nosm.elearning.ariadne.util.TestConstants;
//import com.nosm.elearning.ariadne.util.Constants;

//import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.HashSet;
import java.util.Iterator;
//import java.util.StringTokenizer;

public class Ariadne extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	private static final long serialVersionUID = 7526471155622776147L;
	HttpSession session;
	static final String lineSep = System.getProperty ( "line.separator" );

	public Ariadne() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		StringBuffer reply = new StringBuffer();
		String sessid = "";
		String mnodeid = "";
		String aMode = ""; // admin gets you uipairs and id, slplay gets you short sl xml
		String olXMLSess = "";
		String strMNodeTitle ="";
		String strMNodeContent = "";

		try {

			sessid = (String) request.getParameter("sessid");
			mnodeid = (String) request.getParameter("mnodeid");
			aMode = (String) request.getParameter("mode");

			if (aMode == null || aMode == "") {
				reply.append("<error>Critical error: No mode or node has been defined in the request</error>");
			} else {
				// TODO: if aMode=stats: asset usage by type, by sl_type
				if (mnodeid == null || mnodeid == ""
					&& (aMode.toLowerCase() == "admin")) {
					reply.append("Missing required MNodeID in the request");
				} else {
					String action = (String)request.getParameter("action");
					if (action != null && action.equals("del")){
						String a2rem = (String)request.getParameter("asset");
						if (a2rem.equals(""))throw new Exception("<error>no asset id defined, cannot remove</error>");
						AriadneData.removeAssetFromSeq(Integer.parseInt(a2rem));
						AriadneData.deleteAsset(Integer.parseInt(a2rem)) ;
						response.setContentType("text/html");

						PrintWriter out = response.getWriter();
						out.println("<html><head><title></title></head><body onload='window.location=\"/ariadne4j/index2.html?mnodeid="
								+mnodeid + "&mode=admin\"></body></html>"); // use response.sendRedirect()?
					}else{
						session = request.getSession();
						String OLAddress = "";

						if (sessid != null) {
							// does it match the current session?
							//if (!sessid.equals(session.getAttribute("ol-sessid"))){
								//throw new RuntimeException("<error>Fatal Error: session id "+sessid+" returned does not match the current session id: "
									//	+ session.getAttribute("ol-sessid")+"</error>");
							//}
							OLAddress = "/django/labyrinth/"+Constants.OL_GAME_ID+"/link/"+ mnodeid +"/data/classic/";
						} else {
							// get a new one:
							sessid = HttpFetch.getString4Url("/django/session/create/", new ArrayList(), false);
							// (re)start at root node:
							OLAddress = "/django/labyrinth/"+Constants.OL_GAME_ID+"/root/data/classic/";

						}
						System.out.println("calling OL3 with url "+OLAddress);
						org.w3c.dom.Document OLResults = null;
						try{
							// GET OPENLABYRINTH XML
							if (true){// testing switch
								ArrayList qparams = new ArrayList();
								qparams.add(new BasicNameValuePair("sessionid", sessid));
								OLResults = loadXMLFrom(HttpFetch.getString4Url(OLAddress, qparams, true)); // use Post
							}else{
								OLResults = loadXMLFrom(getTestXML(mnodeid));
							}
							//}catch (ParserConfigurationException  e) {
							//reply.append("<Error>"+e.getMessage() + "</Error>");
						}catch (SAXException se) {
							reply.append("<error>"+se.getMessage() + "</error>");
						}catch (IOException ioe) {
							reply.append("<error>"+ioe.getMessage() + "</error>");
						}

						olXMLSess = extractNode(Constants.XPATH_SESSION+ Constants.XPATH_VAL,
								OLResults);

						if ((sessid == null || sessid == "") ) {
							if (olXMLSess != null || olXMLSess != ""){
								session.setAttribute("ol-sessid", olXMLSess);
							}
						} else {
							session.setAttribute("ol-sessid", sessid);
						}

						strMNodeTitle = java.net.URLDecoder.decode(
								extractNode(Constants.XPATH_TITLE+ Constants.XPATH_VAL, OLResults));

						// trim goofy OL title text brackets
						if (strMNodeTitle.indexOf("]]]]") > -1){
							strMNodeTitle = strMNodeTitle.substring(strMNodeTitle.indexOf("]]]] - ")
									+ "]]]] - ".length(), strMNodeTitle.length());
						}

						strMNodeContent = java.net.URLDecoder.decode(
								extractNode(Constants.XPATH_CONTENT+ Constants.XPATH_VAL, OLResults));

						//trim paragraph
						if (strMNodeContent.indexOf("<p>") > -1){
							strMNodeContent = strMNodeContent.substring(strMNodeContent.indexOf("<p>")
									+ "<p>".length(), strMNodeContent.indexOf("</p>"));
						}

						String thisNodeID = "";

						try {
							thisNodeID = extractNode(Constants.XPATH_ID+ Constants.XPATH_VAL,OLResults);
						} catch (Exception e) {
							reply.append(e.getMessage() + ", OLResults:" + OLResults);
						}

					/*
					 	if (!thisNodeID.equals(mnodeid))
							throw new RuntimeException("<error>Critical Error: extracted returned node id: "
									+thisNodeID+" from OL does not match ID requested: "
									+ mnodeid+ " xml document: "+ xmlToString( OLResults)+"</error>"); // or is this normal? ie. how counters work?
					 */
						int intMNodeID = -1;
						try{
							 intMNodeID = Integer.parseInt(thisNodeID);
						}catch(NumberFormatException e){
							reply.append(e.getMessage() + ", OLResults:" + OLResults);
						}

						// always add vpdtext
						reply.append("<assets>"+lineSep+"<asset type=\"VPDText\" name=\"OL\" targettype=\"PIVOTE\" value=\""
								+ strMNodeContent + "\" ></asset>"+ lineSep);

						try {
							// get avatar name
							//StringTokenizer splitter = new StringTokenizer(java.net.URLDecoder.decode((String) request.getParameter("av")), " "); // need to decode?
							//while (splitter.hasMoreTokens()) {
							//String firstn = splitter.nextToken().trim();
							//String lastn = splitter.nextToken().trim();
							//}
						//AriadneData.isUser(firstn, lastn) &&
							if ( aMode.toLowerCase().equals("slplay")){ // if in SL mode, give short XML
						 // if (request.getRemoteHost().toLowerCase().indexOf(".agni.") > 0){ // is from SL grid
								ArrayList curAssets = (ArrayList) AriadneData.selectAssetsByMNodeId(intMNodeID, false);
								Iterator iterator = curAssets.iterator();
								// reply.append("<assets>");
								while (iterator.hasNext()) {
									Asset thisAsset = (Asset) iterator.next();
									reply.append("<asset type=\"" + thisAsset.getType()
											+ "\" targettype=\"" + thisAsset.getTarget()
											+ "\" name=\"" + thisAsset.getName()
											+ "\" value=\"" + thisAsset.getValue()
											+ "\" ></asset>"+lineSep);
								}
							}else{
								if (aMode.toLowerCase().equals("admin")){ // if in admin mode, also give ids, ui param name/value pairs
									ArrayList curAssets = (ArrayList) AriadneData.selectAssetsByMNodeId(intMNodeID, true);
									Iterator iterator = curAssets.iterator();
									while (iterator.hasNext()) {
										Asset thisAsset = (Asset) iterator.next();
										reply.append("<asset type=\"" + thisAsset.getType()
												+ "\" iid=\"" + thisAsset.getId()
												+ "\" name=\"" + thisAsset.getName()
												+ "\" targettype=\"" + thisAsset.getTarget()
												+ "\" value=\"" + thisAsset.getValue()
												+ "\" uipairs=\"" + thisAsset.getEncodedUIPairs()
												+ "\" ></asset>"+lineSep);
									}
								}
							}
						} catch (SQLException sqe) {
							reply.append(sqe.getMessage());
						}
						reply.append("</assets>");

						// links do we need conversion form utf-8?
						reply.append(getLinkSetXML(java.net.URLDecoder.decode(extractNode(Constants.XPATH_LINKS + " /text()", OLResults))));
					}
				}
			}
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.println(buildXML(olXMLSess, mnodeid, strMNodeTitle, reply));

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

	private String extractNode(String nodePath, org.w3c.dom.Document doc)  throws XPathExpressionException, UnsupportedEncodingException {
		//xml = new String(xml.getBytes(),"utf-8"); // is this correct?
		 System.out.println("` document retrieved from OL3: "+ xmlToString(doc));
		XPathReader reader = new XPathReader(doc);
		return reader.read(nodePath, XPathConstants.STRING).toString();
	}

	private String buildXML(String sessionID, String nodeid, String title, StringBuffer xml){
		String xmlout = Constants.XML_HEAD_ARIADNE +lineSep+"<session id=\"" + sessionID + "\" type=\"OL\" />"
			+ lineSep+"<node id=\""+ nodeid +"\" label=\"" + title + "\" />"+lineSep+ xml.toString()+ "</ariadne>";
		return xmlout;
	}

	private String getLinkSetXML(String strLinker) {
		String xmlout = "";
		strLinker = strLinker.toLowerCase();

		while (strLinker.indexOf("asp?id=") != -1){ // is v2
			xmlout = xmlout + "<link label=\"" + strLinker.substring(strLinker.indexOf("]]]] - ")
					+ "]]]] - ".length(), strLinker.indexOf("</a>")) + "\" ref=\"" + strLinker.substring(strLinker.indexOf("asp?id=")
							+ "asp?id=".length(), strLinker.indexOf("mode=remote")-1) + "\" ></link>"+ lineSep;
			strLinker = strLinker.substring(strLinker.indexOf("</p>") + "</p>".length(), strLinker.length() );
		}

		while (strLinker.indexOf("django") != -1){ // is v3, classic mode
			xmlout = xmlout + "<link label=\"" + strLinker.substring(strLinker.indexOf("/data/'>")
					+ "/data/'>".length(), strLinker.indexOf("</a>")) + "\" ref=\"" + strLinker.substring(strLinker.indexOf("/link/")
							+ "/link/".length(), strLinker.indexOf("/data/'>")) + "\" ></link>"+ lineSep;
			strLinker = strLinker.substring(strLinker.indexOf("</p>") + "</p>".length(), strLinker.length() );
		}

		return lineSep + "<links>" + lineSep + xmlout + "</links>" + lineSep;
	}


	private static String xmlToString(Document doc) {
		try {

			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);

			return stringWriter.getBuffer().toString();

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static org.w3c.dom.Document loadXMLFrom(String xml)
		throws org.xml.sax.SAXException, java.io.IOException {
		return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
	}

	private static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is)
		throws org.xml.sax.SAXException, java.io.IOException {
		javax.xml.parsers.DocumentBuilderFactory factory =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		javax.xml.parsers.DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException ex) {

		}
		org.w3c.dom.Document doc = builder.parse(is);
		is.close();
		return doc;
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