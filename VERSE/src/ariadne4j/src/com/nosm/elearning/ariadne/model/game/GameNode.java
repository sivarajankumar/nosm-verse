package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import com.nosm.elearning.ariadne.AriadneData;
import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.constants.C;
import com.nosm.elearning.ariadne.constants.EC;
import com.nosm.elearning.ariadne.constants.XC;
import com.nosm.elearning.ariadne.model.Asset;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;

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
	private boolean adminMode = false;
	private boolean isRootNode = false;

	public GameNode(GameUri gameUrl, Game thisGame, Document gameResults, boolean isAdminMode){
		super();
		this.setAdminMode(isAdminMode);
		this.setUri(gameUrl);
		this.setParentGame(thisGame);
		this.setGameXml(gameResults);
		consumeContent();
		this.setRootNode(this.getId()== thisGame.getRootNode());
	}

	public GameNode(Document xml) {
		super();
		this.setGameXml(xml);
		consumeContent(xml);
	}

	public org.w3c.dom.Document getAriadneXml(boolean isAdmin) {
			ariadnexml = getAriadneXml(isAdmin, true);
			return ariadnexml;
	}

	public Document getAriadneXml(boolean isAdmin, boolean autoIncludeTextAsset) {
			StringBuffer reply = new StringBuffer();
			reply.append(XC.XML_HEAD_ARIADNE + C.br
					+ "<session id=\"" + this.getParentGame().getSession()
					+ "\" type=\"OL\" ></session>"
					+ C.br + "<node id=\"" + Integer.toString(this.getId())
					+ "\" label=\"" + this.getName() + "\" ></node>" + C.br);
			reply.append("<"+XC.XML_ASSET + "s>" + C.br);
			if (autoIncludeTextAsset) {
				// hack to avoid parse error
				//this.setContent(java.net.URLDecoder.decode(this.getContent()));
				// trim paragraph
				if (this.getContent() != null ){
					if (this.getContent().indexOf(this.getUri().getLinkPrefix()) > -1) {
						this.setContent(this.getContent().substring(this.getContent().indexOf(this.getUri().getLinkPrefix())
								+ this.getUri().getLinkPrefix().length(), this.getContent().indexOf(this.getUri().getLinkSuffix())));
					}

					Asset textAsset = new Asset( XC.XML_TEXT_NAME,  XC.XML_TEXT_TYPE,
							java.net.URLEncoder.encode(this.getContent()),  "",  XC.XML_TEXT_TARGET, this.getId());
					textAsset.setId(99999);
					reply.append(textAsset.getXML(isAdmin));
				}else{
					throw new RuntimeException(EC.XML_BASIC_PREFIX +
							" ***********  No Content in getAriadneXml() for URI: " + this.getUri().getAddress()+EC.XML_ET);
				}
			}
			if (this.getAssets() != null){
				Iterator i = this.getAssets().iterator();
				while (i.hasNext()) {
					Asset thisAsset = (Asset) i.next();
					reply.append(thisAsset.getXML(isAdmin));
				}

			}
			reply.append(C.br + "</"+XC.XML_ASSET+"s>" + C.br);
			reply.append(this.getLinksXML());
			reply.append("</ariadne>");
			System.out.println("built this ariadne xml  "+reply.toString());
			XPathReader xR = new XPathReader(reply.toString());
			ariadnexml = xR.getXmlDocument();
			return ariadnexml;
	}

	public void consumeContent() {
		consumeContent(gamexml);
	}

	public void consumeContent(Document gamexml) {

		try {
			XPathReader xR = new XPathReader(gamexml);
			this.setName(java.net.URLDecoder.decode(xR.extractNode(XC.XPATH_TITLE + XC.XPATH_VAL, gamexml), "ISO-8859-1"));
			this.setContent(java.net.URLDecoder.decode(xR.extractNode(XC.XPATH_CONTENT + XC.XPATH_VAL, gamexml), "ISO-8859-1"));

			String thisSSID = xR.extractNode(XC.XPATH_SESSION +XC.XPATH_VAL, gamexml);

			GameUri url = this.getUri();
			url.setSession(thisSSID);

			Game thisGame = this.getParentGame();
			if(!thisSSID.equals(thisGame.getSession())){
				System.out.println(EC.XML_BASIC_PREFIX + " SSIDs don't match: "+thisSSID + " : "+
				thisGame.getSession() +EC.XML_ET);
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

			this.setId(Integer.parseInt(xR.extractNode(XC.XPATH_ID + XC.XPATH_VAL,
					gamexml)));
			String linkerXML = xR.extractNode( XC.XPATH_LINKS + XC.XPATH_VAL, gamexml);
			//linkerXML = java.net.URLEncoder.encode(linkerXML); // if v3, simulate v2
			this.setLinks(linkerXML);
			this.setAssets((ArrayList)AriadneData.selectAssetsByMNodeId(Integer.parseInt(this.getUri().getNodeId()), this.isAdminMode()));


			} catch (UnsupportedEncodingException e) {
				System.out.println(EC.XML_BASIC_PREFIX+" transform UnsupportedEncodingException: "+e.getMessage()+EC.XML_ET);
				// e.printStackTrace(response.getWriter());
				// response.getWriter().println("<Error>AriadneControllerException:
				// "+e.getMessage()+ e."</Error>");
			} catch (XPathExpressionException ex) {
				System.out.println(EC.XML_BASIC_PREFIX+" transform XPathExpressionException: "+ex.getMessage()+EC.XML_ET);
			} catch (Exception ex) {
				System.out.println(EC.XML_BASIC_PREFIX+" transform general Exception: "+ex.getMessage()+EC.XML_ET);
			}
	}

	public ArrayList<Asset> getAssets() {
		return assets;
	}

	public void setAssets(ArrayList<Asset> assets) {
		if (this.getAssets() != null){
			if (assets.get(0).getNodeid() != this.getId()){
				System.out.println(EC.XML_BASIC_PREFIX+" GameNode asset node ids do not match"+assets.get(0).getNodeid() + ":"+ this.getId());
				if(this.getAssets().get(0).getNodeid()!= assets.get(0).getNodeid()){
					System.out.println(EC.XML_BASIC_PREFIX+" ArrayList asset node ids do not match"+this.assets.get(0).getNodeid()  + ":"+ assets.get(0).getNodeid());
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
		reply.append("<"+XC.XML_LINK+"s>");
		Iterator j = this.getLinks().iterator();
		while (j.hasNext()) {
			StringTokenizer lT = new StringTokenizer((String) j.next(), ":");
			//while(lT.hasMoreTokens()){
			String thisID = lT.nextToken();
			String thisLabel = lT.nextToken();
			//}
			reply.append("<"+XC.XML_LINK+" "+XC.XML_LINK_LABEL+"=\"" + thisLabel + "\" "+
					XC.XML_LINK_REF+"=\""
					+ thisID
					+ "\" ></"+XC.XML_LINK+">" + C.br);
		}
		reply.append("</"+XC.XML_LINK+"s>" + C.br);

		return reply.toString();
	}

	public void setLinks(ArrayList<String> links) {
		this.links = (ArrayList<String>)links.clone();
	}

	public void setLinks(String pLnk) throws UnsupportedEncodingException{
		if (this.getParentGame().getVersion() == 2){
			pLnk = java.net.URLDecoder.decode(pLnk, "ISO-8859-1");
		}else{
			pLnk = java.net.URLDecoder.decode(pLnk); // UTF-8
		}
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
			StringTokenizer links = new StringTokenizer(pLnk, ";");
				while(links.hasMoreTokens()){
					StringTokenizer linkItems = new StringTokenizer((String)links.nextToken(), ",");
					String thisID = linkItems.nextToken();
					String thisLabel = linkItems.nextToken();
					try{
						theseLnks.add(thisID+":"+thisLabel);
					}catch(IndexOutOfBoundsException noArryE){
						System.out.println(EC.XML_BASIC_PREFIX+
								" setLinks IndexOutOfBoundsException: "+
								noArryE.getMessage()+EC.XML_ET);
					}
				}
		}else{
			System.out.println(EC.XML_BASIC_PREFIX+
					" alert: overwriting a set of links with these new links: "+
					pLnk + EC.XML_ET);
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public boolean isRootNode() {
		return isRootNode;
	}

	public void setRootNode(boolean isRootNode) {
		this.isRootNode = isRootNode;
	}

}
