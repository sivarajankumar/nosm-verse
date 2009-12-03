import static com.nosm.elearning.ariadne.util.Constants.XML_HEAD_ARIADNE;
import static com.nosm.elearning.ariadne.util.Constants.XML_HEAD_USER;

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

import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.AssetType;
import com.nosm.elearning.ariadne.model.User;
import com.nosm.elearning.ariadne.util.XPathReader;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Users extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {
	HttpSession session;
	public Users() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		StringBuffer reply = new StringBuffer();
		try{
			// if mode stats, get total users
			if (((String)request.getParameter("action")).equals("getall")){

				ArrayList mylist = (ArrayList)AriadneData.getAllUsers();
				Iterator iterator = mylist.iterator();
				while (iterator.hasNext()) {
					User thisUser = (User) iterator.next();
					reply.append("<user name=\""+ thisUser.getAvatarFirst()+ " " +thisUser.getAvatarLast()+ "\"></user>");
				}

				String xmlout = XML_HEAD_USER + reply.toString()+ "</users>";
				response.setContentType("text/html");

				PrintWriter out = response.getWriter();
				out.println(xmlout);
			}
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		StringBuffer reply = new StringBuffer();
		try{

			if (((String)request.getParameter("action")).equals("register")){
				;

				StringTokenizer splitter = new StringTokenizer(java.net.URLDecoder.decode((String) request.getParameter("av")), " "); // need to decode?
				//while (splitter.hasMoreTokens()) {
					String firstn = splitter.nextToken().trim();
					String lastn = splitter.nextToken().trim();
				//}

				AriadneData.registerUser(
						new User(
								firstn,
								lastn,
								(String) request.getParameter("aKey")
								));

				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("ENLISTED:" +(String)request.getParameter("fname") +" "+(String)request.getParameter("lname") + " has been added." );
			}else{
				if (((String)request.getParameter("action")).equals("unregister")){
					AriadneData.removeUser(new User((String) request.getParameter("fname"),
							(String) request.getParameter("lname"), (String) request.getParameter("aKey")));
				}
			}

			PrintWriter out = response.getWriter();
			out.println(XML_HEAD_USER +"<error>" +(String)request.getParameter("fname") +" "+(String)request.getParameter("lname") + " has been removed.</error></users>" );
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}
	}


}
