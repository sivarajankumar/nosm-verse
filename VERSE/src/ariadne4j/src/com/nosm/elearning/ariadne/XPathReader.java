package com.nosm.elearning.ariadne;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;


public class XPathReader {

	private String xmlFile;
	private Document xmlDocument;
	private XPath xPath;


	public XPathReader(java.io.InputStream is){
		super();
		try{
			this.xmlDocument = XPathReader.loadXMLFrom(is, true);
			xPath =  XPathFactory.newInstance().newXPath();
		}catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
	}



	public XPathReader(String xml) {
		super();
		try{
			this.xmlDocument = 	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new InputSource(new StringReader(xml)));
			xPath =  XPathFactory.newInstance().newXPath();
		}catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
	}



	public XPathReader(URL url) throws IOException, ParserConfigurationException, SAXException{
		super();
		try{
			InputStream stream = url.openStream();
			this.xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
			xPath =  XPathFactory.newInstance().newXPath();
		}catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
	}



	public XPathReader(Document doc) {// throws Exception{
		try{
			if (doc != null && xmlToString(doc).length() > 50){
				this.xmlDocument = doc;
				xPath =  XPathFactory.newInstance().newXPath();
			}else{
				throw new Exception("XPathReader: attempted to create XML doc with empty OL request:"+xmlToString(doc));
			}
		}catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
	}

	public String extractNode(String nodePath, org.w3c.dom.Document doc)
	throws XPathExpressionException, UnsupportedEncodingException {
		//xml = new String(xml.getBytes(),"utf-8"); // is this correct?

		XPathReader reader = new XPathReader(doc);
		String outS = reader.read(nodePath, XPathConstants.STRING).toString();
		System.out.println("XPathReader: this xPath"+nodePath+" call retrieved this value: "+ outS);
		return outS;
	}

	public NodeList extractNodeSet(String nodePath, org.w3c.dom.Document doc)
	throws XPathExpressionException, UnsupportedEncodingException {
		//xml = new String(xml.getBytes(),"utf-8"); // is this correct?
		XPathReader reader = new XPathReader(doc);
		NodeList outS = (NodeList)reader.read(nodePath, XPathConstants.NODESET);
		System.out.println("XPathReader: this xPath"+nodePath+" call retrieved this value: "+ outS.toString());
		return outS;
	}




	public String xmlToString() {
		return xmlToString(xmlDocument) ;
	}

	public static String xmlToString(Document doc) {
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

	public Object read(String expression,
			QName returnType)throws XPathExpressionException{//throws Exception{
		//try {
		XPathExpression xPathExpression =
			xPath.compile(expression);
		return xPathExpression.evaluate
		(xmlDocument, returnType);
		// } catch (XPathExpressionException ex) {
		//    ex.printStackTrace();
		//    return null;
		//}
	}


	public static org.w3c.dom.Document loadXMLFrom(String xml, boolean loose)
	throws org.xml.sax.SAXException, java.io.IOException {
		return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()),loose);
	}

	public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is, boolean loose)
	throws org.xml.sax.SAXException, java.io.IOException {
		javax.xml.parsers.DocumentBuilderFactory factory =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);

		if(loose){
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			//factory.setAttribute(name, value)
			//factory.setSchema(schema)
		}
		javax.xml.parsers.DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			//System.out.println("xml builder is validating? "+builder.isValidating());
		}
		catch (javax.xml.parsers.ParserConfigurationException ex) {
			System.out.println("ariadne xml builder ParserConfigurationException: "+ex.getMessage());
		}
		org.w3c.dom.Document doc = builder.parse(is);
		//System.out.println("THE WHOLE DOC XML "+ doc.getTextContent());
		is.close();
		return doc;
	}



	public Document getXmlDocument() {
		return xmlDocument;
	}



	public void setXmlDocument(Document xmlDocument) {
		this.xmlDocument = xmlDocument;
	}




}