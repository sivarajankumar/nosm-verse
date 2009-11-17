import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.nosm.elearning.ariadne.AriadneData;

import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.AssetType;
import com.nosm.elearning.ariadne.model.User;

public class Inventory extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	HttpSession session;
	public Inventory() {
		super();
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		StringBuffer reply = new StringBuffer();
		// if mode=stats:
		// invcount by type, by sl type
		//shell vs. scene percentage
		try{

			//build the slholodeck type getHolodeckScenes, getHolodeckShells
			if (((String)request.getParameter("action")).equals("getall")){
				ArrayList mylist = (ArrayList)AriadneData.getAllAssetTypes();
				Iterator iterator = mylist.iterator();
				while (iterator.hasNext()) {
					AssetType thisAttrib = (AssetType) iterator.next();
					reply.append("<atype name=\""+ thisAttrib.getName()
							+ "\" type=\""+ thisAttrib.getType()
							+ "\" sltype=\""+ thisAttrib.getSl_inv_type()+ "\"></atype>");
				}
			}

			String xmlout = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+ reply.toString()+ "</ariadne>";
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.println(xmlout);


		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// add / remove holodeck scenes and shells
		// inventory count by type and by sl_type
		// shell-scene %

		try{
			StringTokenizer splitter = new StringTokenizer(
					java.net.URLDecoder.decode((String) request.getParameter("av")), " "); // need to decode?
			//while (splitter.hasMoreTokens()) {
			String firstn = splitter.nextToken().trim();
			String lastn = splitter.nextToken().trim();
			//}

			if (AriadneData.isAdmin(firstn, lastn)){
				AssetType thisAT = new AssetType((String) request.getParameter("inv_name"),
						(String) request.getParameter("inv_val"),
						Integer.parseInt(request.getParameter("inv_type")));
				if (((String)request.getParameter("action")).equals("insert")){
					AriadneData.addAssetType(thisAT);
					// getAssetTypeBySLInvType
				}//else{
					//if((String)request.getParameter("action") == "update"){
						//AriadneData.
					//}else{ // delete
						//if((String)request.getParameter("action") == "delete"){
						//}
					//}
				//}
			}else{

			}

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String xmlout = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
				+"<error>Ariadne has run the " + (String)request.getParameter("action") + "operation with the "
				+ (String) request.getParameter("inv_name")+ " object.</error></ariadne>";

			out.println(xmlout);
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}
}
