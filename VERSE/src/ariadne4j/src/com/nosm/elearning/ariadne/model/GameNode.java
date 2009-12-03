package com.nosm.elearning.ariadne.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathExpressionException;

import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.XPathReader;

public class GameNode {
	private int id;
	private String name;
	private String type;
	private String content;
	private ArrayList<String> links;
	private ArrayList<Asset> assets;
	private org.w3c.dom.Document gamexml;
	private org.w3c.dom.Document ariadnexml;
	private Game parentGame;

	public GameNode(org.w3c.dom.Document xml) {
		super();
		this.setGameXml(xml);
		transform(xml);
	}

	public org.w3c.dom.Document getAriadneXml(){
		if (ariadnexml != null ) {
			return ariadnexml;
		}else{
			return getAriadneXml(true);
		}
	}

	public org.w3c.dom.Document getAriadneXml(boolean autoIncludeTextAsset){
		if (ariadnexml != null ) {
			return ariadnexml;
		}else{

			StringBuffer reply = new StringBuffer();
			reply.append(Constants.XML_HEAD_ARIADNE +Constants.lineSep+
					"<session id=\"" + parentGame.getSession() + "\" type=\"OL\" />"
			+ Constants.lineSep+"<node id=\""+ Integer.toString(this.getId())+
			"\" label=\"" + this.getName() + "\" />"+Constants.lineSep);

				reply.append("<assets>"+ Constants.lineSep);
				if (autoIncludeTextAsset ){
					reply.append(Constants.lineSep+"<asset type=\"VPDText\" "+
							"name=\"OL\" targettype=\"PIVOTE\" value=\""
							+ this.getContent() + "\" ></asset>"+ Constants.lineSep);
				}
				Iterator i = this.getAssets().iterator();
				while (i.hasNext()) {
					Asset thisAsset = (Asset)i.next();
					reply.append(thisAsset.getXML());
				}
				reply.append(Constants.lineSep +"</assets>"+ Constants.lineSep );

				reply.append("<links>");
				Iterator j = this.getAssets().iterator();
				while (j.hasNext()) {
					String lnk = (String)j.next();
					reply.append("<link label=\"" + lnk + "\" ref=\"" + Integer.toString(this.getAssets().indexOf(lnk))  + "\" ></link>" + Constants.lineSep);
				}
				reply.append("</links>" + Constants.lineSep);
			reply.append("</ariadne>");

			XPathReader xR = new XPathReader(reply.toString());
			ariadnexml = xR.getXmlDocument();
			return ariadnexml;
		}

	}

	public void transform ()
	{
		transform (gamexml);
	}


	public void transform (org.w3c.dom.Document gamexml) {

		try{
			XPathReader xR = new XPathReader(gamexml);
			this.setName(java.net.URLDecoder.decode(
					xR.extractNode(Constants.XPATH_TITLE+ Constants.XPATH_VAL, gamexml)));
			// trim goofy OL title text brackets
			if (this.getName().indexOf("]]]]") > -1){
				this.setName(this.getName().substring(this.getName().indexOf("]]]] - ")
						+ "]]]] - ".length(), this.getName().length()));
			}
			this.setContent(java.net.URLDecoder.decode(
					xR.extractNode(Constants.XPATH_CONTENT+ Constants.XPATH_VAL, gamexml)));
			//trim paragraph
			if (this.getContent().indexOf("<p>") > -1){
				this.setContent(this.getContent().substring(this.getContent().indexOf("<p>")
						+ "<p>".length(), this.getContent().indexOf("</p>")));
			}
			this.setId(xR.extractNode(Constants.XPATH_ID+ Constants.XPATH_VAL,gamexml));
			this.setLinks(xR.extractNode(Constants.XPATH_ID+ Constants.XPATH_LINKS, gamexml)); // setting with str param will parse it

		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace(response.getWriter());
			//response.getWriter().println("<Error>AriadneControllerException: "+e.getMessage()+ e."</Error>");
		} catch( XPathExpressionException ex){
		}
	}

public ArrayList<Asset> getAssets() {
	return assets;
}
public void setAssets(ArrayList<Asset> assets) {
	this.assets = (ArrayList)assets.clone();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public void setId(String id) {
	this.id = Integer.parseInt(id);
}

public ArrayList<String> getLinks() {
	return links;
}
public void setLinks(ArrayList<String> links) {
	this.links = (ArrayList)links.clone();
}

public void setLinks(String strLinker) {
	strLinker =java.net.URLDecoder.decode(strLinker);
	ArrayList<String> theseLnks = new ArrayList<String>();
	if (parentGame.getVersion()==3){
		while (strLinker.indexOf(Constants.GAME_URL_ID_PREFIX3) != -1){ // is v3, classic mode
			theseLnks.add(
					Integer.parseInt(
							strLinker.substring(
									strLinker.indexOf(Constants.GAME_URL_ID_PREFIX3) + Constants.GAME_URL_ID_PREFIX3.length(),
									strLinker.indexOf(Constants.GAME_URL_ID_SUFFIX3)
							)
					),
					strLinker.substring(
							strLinker.indexOf(Constants.GAME_URL_NAME_PREFIX3 )
							+ Constants.GAME_URL_NAME_PREFIX3.length(), strLinker.indexOf(Constants.GAME_URL_NAME_SUFFIX )));
		}
	}else{
		while (strLinker.indexOf(Constants.GAME_URL_ID_PREFIX) != -1){ // is v2
			theseLnks.add(
					Integer.parseInt(
							strLinker.substring(
									strLinker.indexOf(Constants.GAME_URL_ID_PREFIX)+ Constants.GAME_URL_ID_PREFIX.length(),
									strLinker.indexOf(Constants.GAME_URL_ID_SUFFIX)
							)
					),
					strLinker.substring(
							strLinker.indexOf(Constants.GAME_URL_NAME_PREFIX )
							+ Constants.GAME_URL_NAME_PREFIX.length(), strLinker.indexOf(Constants.GAME_URL_NAME_SUFFIX )));
		}
	}

	this.links = theseLnks;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getContent() {
	return content;
}
public void setContent(String value) {
	this.content = value;
}

public void setGameXml(org.w3c.dom.Document xml) {
	this.gamexml = xml;
}


public Game getParentGame() {
	return parentGame;
}


public void setParentGame(Game parentGame) {
	this.parentGame = parentGame;
}

}
