package com.nosm.elearning.ariadne.util;

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
    	try{
	    	this.xmlDocument = XPathReader.loadXMLFrom(is);
	    	xPath =  XPathFactory.newInstance().newXPath();
		}catch (Exception e) {
			//ex.printStackTrace();
	        //return null;
		}
	}



    public XPathReader(String xml) {
    	try{
        	this.xmlDocument = 	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        	xPath =  XPathFactory.newInstance().newXPath();
    	}catch (Exception e) {
			//ex.printStackTrace();
            //return null;
		}
    }



    public XPathReader(URL url) throws IOException, ParserConfigurationException, SAXException{
    	//try{
    	    InputStream stream = url.openStream();
    	    this.xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
    	    xPath =  XPathFactory.newInstance().newXPath();
    //	}catch (Exception e) {
			//ex.printStackTrace();
            //return null;
		//}
    }



    public XPathReader(Document doc) {// throws Exception{
    	//try{
        	this.xmlDocument = doc;
        	xPath =  XPathFactory.newInstance().newXPath();
    	//}catch (Exception e) {
			//ex.printStackTrace();
            //return null;
		//}
    }

	public String extractNode(String nodePath, org.w3c.dom.Document doc)  throws XPathExpressionException, UnsupportedEncodingException {
		//xml = new String(xml.getBytes(),"utf-8"); // is this correct?
		System.out.println("` document retrieved from OL3: "+ xmlToString(doc));
		XPathReader reader = new XPathReader(doc);
		return reader.read(nodePath, XPathConstants.STRING).toString();
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


	public static org.w3c.dom.Document loadXMLFrom(String xml)
	throws org.xml.sax.SAXException, java.io.IOException {
		return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
	}

	public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is)
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



	public Document getXmlDocument() {
		return xmlDocument;
	}



	public void setXmlDocument(Document xmlDocument) {
		this.xmlDocument = xmlDocument;
	}




}