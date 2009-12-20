package com.nosm.elearning.ariadne.model.game.uri;

import com.nosm.elearning.ariadne.constants.C;

public abstract class GameUri {
	private  String host = C.GAME_HOST3;

	private  int gameId = C.GAME_GAME_ID3; // hard-coded until picking Lab from list is supported
	private String nodeId;

	private  String session;

	private  String user = C.GAME_USER;
	private  String password = C.GAME_PASSWORD;

	private  String path = C.GAME_URL_PATH3;
	private  String pathSuffix = C.GAME_URL_PATH_SUFFIX3;

	private String nameSuffix = C.GAME_URL_NAME_SUFFIX;
	private String namePrefix = C.GAME_URL_NAME_PREFIX3;

	private String ssidPrefix = C.GAME_URL_LINK_SSID3;
	private String linkStart = C.GAME_URL_LINK_START;

	private String linkPrefix = C.GAME_URL_LINKER_PREFIX;
	private String linkSuffix = C.GAME_URL_LINKER_SUFFIX;


	private String linkSsid = C.GAME_URL_LINK_SSID3;

	private String idSuffix = C.GAME_URL_ID_SUFFIX3;
	private String nodePath = C.GAME_URL_NODE_PATH3;
	private String rootPath = C.GAME_URL_ROOT3;
	private String idPrefix = this.getGameId() + C.GAME_URL_ID_PREFIX3 ;

	public GameUri(){
		super();
	}

	public String getAddress(){
		if (this.getNodeId()== null || this.getNodeId().equals("")|| this.getNodeId().equals(this.getLinkStart())){
			return getRootAddress();
		}else{
			return this.getPath() + this.getGameId() +"/"+
			this.getNodePath()+"/"+ this.getNodeId()  + this.getPathSuffix(true);
		}
	}

	public String getRootAddress(){
		return this.getPath() + this.getGameId() +this.getRootPath() + this.getPathSuffix(false) ;
	}


	public String getAddress(String ssid){
		if (ssid != null){
				this.setSession(ssid);
		}
		return getAddress();
	}

	public GameUri(String nodeId){
		this();
		if (nodeId != null ){
			if (nodeId != ""  ){
				this.setNodeId(nodeId);
			}
		}
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
	//public String getLinkStart() {
	//	return linkStart;
	//}
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


	public String getPathSuffix(boolean appendSSID) {
		if (appendSSID && this.getSession() != null ){
			return pathSuffix +"&" +this.getSsidPrefix()+"="+this.getSession();
		}else{
			return pathSuffix;
		}
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
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
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

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getSsidPrefix() {
		return ssidPrefix;
	}

	public void setSsidPrefix(String ssidPrefix) {
		this.ssidPrefix = ssidPrefix;
	}

	public String getLinkStart() {
		return linkStart;
	}




}
