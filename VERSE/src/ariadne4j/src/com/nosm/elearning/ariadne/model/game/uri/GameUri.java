package com.nosm.elearning.ariadne.model.game.uri;

import com.nosm.elearning.ariadne.util.Constants;

public abstract class GameUri {
	private  String host = Constants.GAME_HOST3;

	private  int gameId = 2; // hard-coded until picking Lab from list is supported
	private int nodeId;

	private  String session;

	private  String user = Constants.GAME_USER;
	private  String password = Constants.GAME_PASSWORD;

	private  String path = Constants.GAME_URL_PATH3;
	private  String pathSuffix = Constants.GAME_URL_PATH_SUFFIX3;

	private String nameSuffix = Constants.GAME_URL_NAME_SUFFIX;
	private String namePrefix = Constants.GAME_URL_NAME_PREFIX3;

	private String linkPrefix = Constants.GAME_URL_LINKER_PREFIX;
	private String linkSuffix = Constants.GAME_URL_LINKER_SUFFIX;

	private String linkStart = Constants.GAME_URL_LINK_START;
	private String linkSsid = Constants.GAME_URL_LINK_SSID;

	private String idSuffix = Constants.GAME_URL_ID_SUFFIX3;
	private String nodePath = Constants.GAME_URL_NODE_PATH3;
	private String idPrefix = this.getGameId() + Constants.GAME_URL_ID_PREFIX3 ;

	public GameUri(){
		super();
	}

	public String getAddress(){
		return this.getPath() + this.getGameId() +"/"+
		this.getNodePath()+"/"+ Integer.toString(this.getNodeId())  + this.getPathSuffix();
	}

	public String getAddress(boolean uselinks){
		if (uselinks)
		{
			return this.getPath() +  this.getIdPrefix()+ Integer.toString(this.getNodeId())  + this.getPathSuffix();
		}else{
			return getAddress(); // use node syntax
		}
	}

	public GameUri(int nodeId){
		this();
		this.setNodeId(nodeId);
	}

	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIdPrefix() {
		return idPrefix;
	}
	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}
	public String getIdSuffix() {
		return idSuffix;
	}
	public void setIdSuffix(String idSuffix) {
		this.idSuffix = idSuffix;
	}
	public String getLinkPrefix() {
		return linkPrefix;
	}
	public void setLinkPrefix(String linkPrefix) {
		this.linkPrefix = linkPrefix;
	}

	public String getLinkSsid() {
		return linkSsid;
	}
	public void setLinkSsid(String linkSsid) {
		this.linkSsid = linkSsid;
	}
	public String getLinkStart() {
		return linkStart;
	}
	public void setLinkStart(String linkStart) {
		this.linkStart = linkStart;
	}
	public String getLinkSuffix() {
		return linkSuffix;
	}
	public void setLinkSuffix(String linkSuffix) {
		this.linkSuffix = linkSuffix;
	}
	public String getNamePrefix() {
		return namePrefix;
	}
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	public String getNameSuffix() {
		return nameSuffix;
	}
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPathSuffix() {
		return pathSuffix;
	}
	public void setPathSuffix(String pathSuffix) {
		this.pathSuffix = pathSuffix;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}




}
