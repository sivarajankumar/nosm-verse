package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathExpressionException;

import com.nosm.elearning.ariadne.AriadneData;
import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.constants.C;
import com.nosm.elearning.ariadne.constants.XC;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;

public class Game {
	private int id;
	private String name;
	private String type;
	private int rootNode;
	private int version = 3;
	private String rootUrl = C.GAME_HOST3 ;
	private String rootUser = C.GAME_USER;
	private String rootPwd = C.GAME_PASSWORD;

	private ArrayList<GameNode> nodes;
	private String session;

	public Game(org.w3c.dom.Document xml) {
		super();
		transform(xml);
	}

	public Game(GameNode node) {
		super();
		transform (node);
	}


	public Game(String name, int node, int rootNode) {
		super();
		this.setName(name);
		this.setId(node);
		this.setRootNode(rootNode);
	}

	public Game(GameNode node, org.w3c.dom.Document xml) {
		super();
		transform (node);
		transform(xml);
	}


	public void transform (org.w3c.dom.Document xml) {
		System.out.println("transforming Game obj");
		try{
			XPathReader xR = new XPathReader(xml);
			String ssid =xR.extractNode(XC.XPATH_SESSION+ XC.XPATH_VAL, xml);
			if (ssid != null){
				if (this.getSession() != null || this.getSession() != "")
					System.out.println("about to overwrite ssid: "+this.getSession()+" with OL xml val: "+ ssid);
				this.setSession(ssid);

				this.setName(URLDecoder.decode(xR.extractNode(XC.XPATH_GAME_NAME+ XC.XPATH_VAL, xml)));//
				this.setType(URLDecoder.decode(xR.extractNode(XC.XPATH_GAME_TYPE+ XC.XPATH_VAL, xml)));
				this.setId(xR.extractNode(XC.XPATH_GAME_ID + XC.XPATH_VAL,xml));
				this.setRootNode(Integer.parseInt(xR.extractNode(XC.XPATH_ROOTNODE + XC.XPATH_VAL,xml)));
			}else{
				throw new XPathExpressionException("cannot find the " + XC.XPATH_SESSION+" tag in the xml: "+XPathReader.xmlToString(xml));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch( XPathExpressionException ex){
			ex.printStackTrace();
		}
	}

	public void transform (GameNode node) {
		try{
			if(node.getUri().getSession() != null && this.getSession() == null){
				this.setSession(node.getUri().getSession());
			}
			if (node.getUri().getPath().indexOf("django") > -1){
				this.setVersion(3);
			}else{
				this.setVersion(2);
			}

			 if (node.isRootNode()) this.setRootNode(node.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<GameNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<GameNode> nodes) {
		this.nodes = nodes;
	}

	public void addNode(GameNode node) {
		transform(node);
		this.nodes.add(node);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRootNode() {
		return rootNode;
	}

	public void setRootNode(int rootNode) {
		this.rootNode = rootNode;
	}

	public void setRootNode(GameNode node) {
		if (node.isRootNode()) this.setRootNode(node.getId());
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getRootPwd() {
		return rootPwd;
	}

	public void setRootPwd(String rootPwd) {
		this.rootPwd = rootPwd;
	}

	public String getRootUser() {
		return rootUser;
	}

	public void setRootUser(String rootUser) {
		this.rootUser = rootUser;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

}
