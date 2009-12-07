package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathExpressionException;

import com.nosm.elearning.ariadne.AriadneData;
import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;
import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.XConstants;

public class Game {
	private int id = Integer.parseInt(Constants.GAME_GAME_ID); // current nossum island viewer labyrinth
	private String name;
	private String type;
	private String rootNode;
	private int version = 3;
	private String rootUrl = Constants.GAME_HOST3 ;

	private String rootUser = Constants.GAME_USER;
	private String rootPwd = Constants.GAME_PASSWORD;

	private String session;

	public Game(org.w3c.dom.Document xml) {
		super();
		transform(xml);
	}


	public void transform (org.w3c.dom.Document xml) {

		try{

			XPathReader xR = new XPathReader(xml);

			this.setRootNode(java.net.URLDecoder.decode(
					xR.extractNode(XConstants.XPATH_GAME_NAME+ XConstants.XPATH_VAL, xml)));

			this.setSession(java.net.URLDecoder.decode(
					xR.extractNode(XConstants.XPATH_SESSION+ XConstants.XPATH_VAL, xml)));

			this.setName(java.net.URLDecoder.decode(
					xR.extractNode(XConstants.XPATH_GAME_NAME+ XConstants.XPATH_VAL, xml)));

			this.setType(java.net.URLDecoder.decode(
					xR.extractNode(XConstants.XPATH_GAME_TYPE+ XConstants.XPATH_VAL, xml)));

			this.setId(xR.extractNode(XConstants.XPATH_GAME_ID + XConstants.XPATH_VAL,xml));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch( XPathExpressionException ex){
			ex.printStackTrace();
		}
	}


	private ArrayList<GameNode> nodes;

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
		this.nodes.add(node);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootNode() {
		return rootNode;
	}

	public void setRootNode(String rootNode) {
		this.rootNode = rootNode;
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
