package com.nosm.elearning.ariadne;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;


public class XPathReader {

    private String xmlFile;
    private Document xmlDocument;
    private XPath xPath;


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
}