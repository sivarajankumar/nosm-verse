import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.nosm.elearning.ariadne.AriadneData;
//import com.nosm.elearning.ariadne.AriadneJdbc;
import com.nosm.elearning.ariadne.XPathReader;

import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.AssetType;
import com.nosm.elearning.ariadne.model.User;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

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
				// is empty for second life requests?
				// (String) request.getParameter("USER_AGENT");
				// if stats: assetusage by type, by sl_type
				if (mnodeid == null || mnodeid == ""
					&& (aMode.toLowerCase() == "slplay" || aMode.toLowerCase() == "admin")) {
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
								+mnodeid + "&mode=admin\"></body></html>");
					}else{
						session = request.getSession();
						String OLAddress = "http://142.51.75.111/mnode.asp?mnode="+mnodeid+"&mode=remote";
						if (sessid == "" || sessid == null) {
							OLAddress += "mnode.asp?mode=remote&id=" + mnodeid;
						} else {
							OLAddress += "mnode.asp?mode=remote&id=" + mnodeid + "&sessid="+ sessid;
						}

						org.w3c.dom.Document OLResults = null;

						try{
							// GET OPENLABYRINTH XML
							if (false){// testing switch
								OLResults = loadXMLFrom(new URL(OLAddress).openStream());
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

						olXMLSess = extractNode("/labyrinth/mysession/text()",
								OLResults);

						if (sessid == null || sessid == "") {
							session.setAttribute("ol-sessid", olXMLSess);
						} else {
							session.setAttribute("ol-sessid", sessid);
						}

						strMNodeTitle = java.net.URLDecoder.decode(extractNode("/labyrinth/mnodetitle/text()", OLResults));

						// trim goofy OL title text brackets
						if (strMNodeTitle.indexOf("]]]]") > -1){
							strMNodeTitle = strMNodeTitle.substring(strMNodeTitle.indexOf("]]]] - ")
									+ "]]]] - ".length(), strMNodeTitle.length());
						}

						strMNodeContent = java.net.URLDecoder.decode(extractNode("/labyrinth/message/text()", OLResults));

						//trim paragraph
						if (strMNodeContent.indexOf("<p>") > -1){
							strMNodeContent = strMNodeContent.substring(strMNodeContent.indexOf("<p>")
									+ "<p>".length(), strMNodeContent.indexOf("</p>"));
						}

						String thisNodeID = "";

						try {
							thisNodeID = extractNode("/labyrinth/mnodeid/text()",OLResults);
						} catch (Exception e) {
							reply.append(e.getMessage() + ", OLResults:" + OLResults);
						}

					/*	if (!thisNodeID.equals(mnodeid))
							throw new RuntimeException("<error>Critical Error: extracted returned node id: "
									+thisNodeID+" from OL does not match ID requested: "
									+ mnodeid+ " xml document: "+ xmlToString( OLResults)+"</error>"); // or is this normal? ie. how counters work?
					 */

						int intMNodeID = Integer.parseInt(thisNodeID);

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
						reply.append(getLinkSetXML(java.net.URLDecoder.decode(extractNode(
								"/labyrinth/linker/text()", OLResults))));
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
				(String) request.getParameter("assetNameOUT"),
				(String) request.getParameter("assetType"),
				(String) request.getParameter("assetValueIN"),
				(String) request.getParameter("savedUIPairs"),
				(String) request.getParameter("assetTargetIN"),
				Integer.parseInt((String) request.getParameter("assetMapNodeid"))
		);

		try {
			String mappedid = request.getParameter("assetid");
			if (mappedid != null){
				if (mappedid.equals("")) mappedid = "0"; //hack to avoid nulls
				if(AriadneData.doesAssetExist(Integer.parseInt(mappedid))) {
					asset.setId(Integer.parseInt(mappedid));
					AriadneData.updateAsset(asset);
					AriadneData.resetSeqForNode(asset, (String) request.getParameter("assetNameOUT"));
				} else {
					AriadneData.insertAsset(asset);
					AriadneData.addNode2Seq(asset);
				}
			}
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.println("<html><head><title></title></head><body onload='window.location=\"/ariadne4j/index2.html?mnodeid="+asset.getNodeid() + "&mode=admin\"></body></html>");

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
		XPathReader reader = new XPathReader(doc);
		return reader.read(nodePath, XPathConstants.STRING).toString();
	}

	private String buildXML(String sessionID, String nodeid, String title, StringBuffer xml){
		String xmlout = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+lineSep+"<ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+lineSep+"<session id=\"" + sessionID + "\" type=\"OL\" />"
			+ lineSep+"<node id=\""+ nodeid +"\" label=\"" + title + "\" />"+lineSep+ xml.toString()+ "</ariadne>";
		return xmlout;
	}

	private String getLinkSetXML(String strLinker) {
		String xmlout = "";
		strLinker = strLinker.toLowerCase();

		while (strLinker.indexOf("asp?id=") != -1){
			xmlout = xmlout + "<link label=\"" + strLinker.substring(strLinker.indexOf("]]]] - ")
					+ "]]]] - ".length(), strLinker.indexOf("</a>")) + "\" ref=\"" + strLinker.substring(strLinker.indexOf("asp?id=")
							+ "asp?id=".length(), strLinker.indexOf("mode=remote")-1) + "\" ></link>"+ lineSep;
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
			retXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
			"<labyrinth>"+
			"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+start+node:+sphere,+cube</mnodetitle>"+
			"<mapname>Test+OL%2DPivote</mapname>"+
			"<mapid>233</mapid>"+
			"<mnodeid>8336</mnodeid>"+
			"<message>%3Cp%3Emessage:+there+is+an+open+space%3C%2Fp%3E</message>"+
			"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8337%26mode%3Dremote%26sessid"+
			"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules+"+
			"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8338%26mode%3Dremote%26sessid%3DE27A2972"+
			"%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+a+dark+room+with+2+computer+screens%3C%2Fa%3E%3C%2Fp%3E</linker>"+
			"<rootnode>8336</rootnode>"+
			"<mysession>E27A2972-DE2B-4995-8EE7-786115628B9E</mysession>"+
			"<maptype>maze</maptype>"+
		"</labyrinth>";
		}else{
			if (mnodeId.equals("8337")){
				retXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<labyrinth>"+
				"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules</mnodetitle>"+
				"<mapname>Test+OL%2DPivote</mapname>"+
				"<mapid>233</mapid>"+
				"<mnodeid>8337</mnodeid>"+
				"<message>%3Cp%3Emessage:+there+is+an+open+space%3C%2Fp%3E</message>"+
				"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8336%26mode%3Dremote%26sessid"+
				"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+back+to+start+node+"+
				"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8338%26mode%3Dremote%26sessid%3DE27A2972"+
				"%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+a+dark+room+with+2+computer+screens%3C%2Fa%3E%3C%2Fp%3E</linker>"+
				"<rootnode>8336</rootnode>"+
				"<mysession>E27A2972-DE2B-4995-8EE7-786115628B9E</mysession>"+
				"<maptype>maze</maptype>"+
			"</labyrinth>";
			}else{
				retXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<labyrinth>"+
				"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+a+dark+room+with+2+computers</mnodetitle>"+
				"<mapname>Test+OL%2DPivote</mapname>"+
				"<mapid>233</mapid>"+
				"<mnodeid>8338</mnodeid>"+
				"<message>%3Cp%3Emessage:+a+dark+room+with+2+computers%3C%2Fp%3E</message>"+
				"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8336%26mode%3Dremote%26sessid"+
				"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+back+to+start+node+"+
				"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8337%26mode%3Dremote%26sessid%3DE27A2972"+
				"%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules%3C%2Fa%3E%3C%2Fp%3E</linker>"+
				"<rootnode>8336</rootnode>"+
				"<mysession>E27A2972-DE2B-4995-8EE7-786115628B9E</mysession>"+
				"<maptype>maze</maptype>"+
			"</labyrinth>";
			}
		}
		return retXML;
	}


}