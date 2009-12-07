package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.ErrorConstants;
import com.nosm.elearning.ariadne.util.XConstants;

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
	private GameUri uri;

	public GameNode(GameUri gameUrl, Game thisGame, Document gameResults){
		super();
		this.setUri(gameUrl);
		this.setParentGame(thisGame);
		this.setGameXml(gameResults);
		transform(gameResults);
	}
	public GameNode(Game pGame, Document xml) {
		super();
		this.setParentGame(pGame);
		this.setGameXml(xml);
		transform(xml);
	}

	public GameNode(Document xml) {
		super();
		this.setGameXml(xml);
		transform(xml);
	}

	public org.w3c.dom.Document getAriadneXml(boolean isAdmin) {
		if (ariadnexml != null) {
			return ariadnexml;
		} else {
			return getAriadneXml(isAdmin, true);
		}
	}

	public Document getAriadneXml(boolean isAdmin, boolean autoIncludeTextAsset) {
		if (ariadnexml != null) {
			return ariadnexml;
		} else {

			StringBuffer reply = new StringBuffer();
			reply.append(XConstants.XML_HEAD_ARIADNE + Constants.lineSep
					+ "<session id=\"" + this.getParentGame().getSession()
					+ "\" type=\"OL\" ></session>"
					+ Constants.lineSep
					+ "<node id=\"" + Integer.toString(this.getId())
					+ "\" label=\"" + this.getName() + "\" ></node>"
					+ Constants.lineSep);

			reply.append("<"+XConstants.XML_ASSET + "s>" + Constants.lineSep);
			if (autoIncludeTextAsset) {
				// hack to avoid parse error
				//this.setContent(java.net.URLDecoder.decode(this.getContent()));

				// trim paragraph
				if (this.getContent().indexOf(this.getUri().getLinkPrefix()) > -1) {
					this.setContent(this.getContent().substring(this.getContent().indexOf(this.getUri().getLinkPrefix())
							+ this.getUri().getLinkPrefix().length(), this.getContent().indexOf(this.getUri().getLinkSuffix())));
				}

				Asset textAsset = new Asset( XConstants.XML_TEXT_NAME,  XConstants.XML_TEXT_TYPE,
						this.getContent(),  "",  XConstants.XML_TEXT_TARGET, this.getId());
				reply.append(textAsset.getXML(isAdmin));
			}

			Iterator i = this.getAssets().iterator();
			while (i.hasNext()) {
				Asset thisAsset = (Asset) i.next();
				reply.append(thisAsset.getXML(isAdmin));
			}
			reply.append(Constants.lineSep + "</"+XConstants.XML_ASSET+"s>" + Constants.lineSep);

			reply.append(this.getLinksXML());

			reply.append("</ariadne>");
			System.out.println("built this ariadne xml  "+reply.toString());
			XPathReader xR = new XPathReader(reply.toString());
			ariadnexml = xR.getXmlDocument();
			return ariadnexml;
		}

	}

	public void transform() {
		transform(gamexml);
	}

	public void transform(Document gamexml) {

		try {
			XPathReader xR = new XPathReader(gamexml);
			this.setName(java.net.URLDecoder.decode(xR.extractNode(
					XConstants.XPATH_TITLE + XConstants.XPATH_VAL, gamexml)));

			this.setContent(java.net.URLDecoder.decode(xR.extractNode(XConstants.XPATH_CONTENT + XConstants.XPATH_VAL, gamexml)));

			String thisSSID = java.net.URLDecoder.decode(xR.extractNode(XConstants.XPATH_SESSION +
					XConstants.XPATH_VAL, gamexml));

			GameUri url = this.getUri();
			url.setSession(thisSSID);

			Game thisGame = this.getParentGame();

			if(!thisSSID.equals(thisGame.getSession())){
				System.out.println(ErrorConstants.XML_BASIC_PREFIX +
						" SSIDs don't match: "+thisSSID + " : "+
						thisGame.getSession() +ErrorConstants.XML_ET);
			}

			thisGame.setSession(thisSSID);

			this.setParentGame(thisGame);
			this.setUri(url);

			if(this.getParentGame().getVersion() == 2){
				// trim goofy OL title text brackets
				if (this.getName().indexOf(this.getUri().getNamePrefix()) > -1) {
					this.setName(this.getName().substring(this.getName().indexOf(this.getUri().getNamePrefix())
							+ this.getUri().getNamePrefix().length(),this.getName().length()));
				}
			}

			// trim paragraph
			if (this.getContent().indexOf(this.getUri().getLinkPrefix()) > -1) {
				this.setContent(this.getContent().substring(this.getContent().indexOf(this.getUri().getLinkPrefix())
						+ this.getUri().getLinkPrefix().length(), this.getContent().indexOf(this.getUri().getLinkSuffix())));
			}

			this.setId(Integer.parseInt(xR.extractNode(XConstants.XPATH_ID + XConstants.XPATH_VAL,
					gamexml)));
			String linkerXML = xR.extractNode( XConstants.XPATH_LINKS + XConstants.XPATH_VAL, gamexml);
			//linkerXML = java.net.URLEncoder.encode(linkerXML); // if v3, simulate v2
			this.setLinks(linkerXML);
			} catch (UnsupportedEncodingException e) {
				System.out.println(ErrorConstants.XML_BASIC_PREFIX+" transform UnsupportedEncodingException: "+e.getMessage()+ErrorConstants.XML_ET);
				// e.printStackTrace(response.getWriter());
				// response.getWriter().println("<Error>AriadneControllerException:
				// "+e.getMessage()+ e."</Error>");
			} catch (XPathExpressionException ex) {
				System.out.println(ErrorConstants.XML_BASIC_PREFIX+" transform XPathExpressionException: "+ex.getMessage()+ErrorConstants.XML_ET);
			}
	}

	public ArrayList<Asset> getAssets() {
		return assets;
	}

	public void setAssets(ArrayList<Asset> assets) {
		if (this.getAssets() != null){
			if (assets.get(0).getNodeid() != this.getId()){
				System.out.println(ErrorConstants.XML_BASIC_PREFIX+" GameNode asset node ids do not match"+assets.get(0).getNodeid() + ":"+ this.getId());
				if(this.getAssets().get(0).getNodeid()!= assets.get(0).getNodeid()){
					System.out.println(ErrorConstants.XML_BASIC_PREFIX+" ArrayList asset node ids do not match"+this.assets.get(0).getNodeid()  + ":"+ assets.get(0).getNodeid());
				}
			}
		}
	 this.assets = assets;
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

	public String getLinksXML() {
		StringBuffer reply = new StringBuffer();
		reply.append("<"+XConstants.XML_LINK+"s>");
		Iterator j = this.getLinks().iterator();
		while (j.hasNext()) {
			StringTokenizer lT = new StringTokenizer((String) j.next(), ":");
			//while(lT.hasMoreTokens()){
			String thisID = lT.nextToken();
			String thisLabel = lT.nextToken();
			//}
			reply.append("<"+XConstants.XML_LINK+" "+XConstants.XML_LINK_LABEL+"=\"" + thisLabel + "\" "+
					XConstants.XML_LINK_REF+"=\""
					+ thisID
					+ "\" ></"+XConstants.XML_LINK+">" + Constants.lineSep);
		}
		reply.append("</"+XConstants.XML_LINK+"s>" + Constants.lineSep);

		return reply.toString();
	}

	public void setLinks(ArrayList<String> links) {
		this.links = (ArrayList<String>)links.clone();
	}

	public void setLinks(String pLnk) {
		pLnk = java.net.URLDecoder.decode(pLnk);
		ArrayList<String> theseLnks = new ArrayList<String>();

		//theseLnks.ensureCapacity(64);
		String strLinker = pLnk;

		while (strLinker.indexOf(this.getUri().getIdPrefix()) != -1) {
			theseLnks.add(Integer.parseInt(strLinker.substring(strLinker.indexOf(this.getUri().getIdPrefix()) +
					this.getUri().getIdPrefix().length(),strLinker.indexOf(this.getUri().getIdSuffix())))
				,
				strLinker.substring(strLinker.indexOf(this.getUri().getNamePrefix()) +
						this.getUri().getNamePrefix().length(), strLinker.indexOf(this.getUri().getNameSuffix()))
				);
			strLinker = strLinker.substring(strLinker.indexOf(this.getUri().getLinkSuffix()) +
					this.getUri().getLinkSuffix().length(), strLinker.length());
		}

		if(theseLnks == null || theseLnks.isEmpty()){
			StringTokenizer lT = new StringTokenizer(pLnk, ";");
				while(lT.hasMoreTokens()){
					StringTokenizer lT2 = new StringTokenizer((String)lT.nextToken(), ",");
					String thisID = lT2.nextToken();
					String thisLabel = lT2.nextToken();
					try{
						theseLnks.add(thisID+":"+thisLabel);
					}catch(IndexOutOfBoundsException noArryE){
						System.out.println(ErrorConstants.XML_BASIC_PREFIX+
								" setLinks IndexOutOfBoundsException: "+
								noArryE.getMessage()+ErrorConstants.XML_ET);
					}
				}
		}else{
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+
					" alert: overwriting a set of links with these new links: "+
					pLnk + ErrorConstants.XML_ET);
		}

		this.setLinks(theseLnks) ;
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

	public void setGameXml(Document xml) {
		this.gamexml = xml;
	}

	public Game getParentGame() {
		return parentGame;
	}

	public void setParentGame(Game parentGame) {
		this.parentGame = parentGame;
	}

	public GameUri getUri() {
		return uri;
	}

	public void setUri(GameUri uri) {
		this.uri = uri;
	}

}
