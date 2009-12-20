package com.nosm.elearning.ariadne.model.game.uri;

import com.nosm.elearning.ariadne.constants.C;

public class OLGameUri extends GameUri {

	private String host = C.GAME_HOST;
	private String path = C.GAME_URL_PATH;
	private String pathSuffix = C.GAME_URL_PATH_SUFFIX;
	private String namePrefix = C.GAME_URL_NAME_PREFIX;
	private String ssidPrefix = C.GAME_URL_LINK_SSID;
	private String idSuffix = C.GAME_URL_ID_SUFFIX;
	private String nodePath = C.GAME_URL_NODE_PATH;
	private String rootPath = C.GAME_URL_ROOT;



	  public OLGameUri(){
			super();
	  }

	  public OLGameUri(String nodeId){
			super(nodeId);
			this.setHost(C.GAME_HOST);
			this.setPath(C.GAME_URL_PATH);
			this.setPathSuffix(C.GAME_URL_PATH_SUFFIX);
			this.setNamePrefix(C.GAME_URL_NAME_PREFIX);
			this.setIdSuffix(C.GAME_URL_ID_SUFFIX);
			this.setNodePath(C.GAME_URL_NODE_PATH);
			this.setSsidPrefix(C.GAME_URL_LINK_SSID);
			this.setRootPath(C.GAME_URL_ROOT);
			this.setLinkStart("");
	  }

		public String getAddress(){
			if (this.getNodeId()== null || this.getNodeId().equals("")){
				return getRootAddress();
			}else{
			    return "http://" +this.getHost() + this.getPath()  + this.getNodeId() + this.getPathSuffix(true);
			}
		}

		public String getRootAddress(){
			return "http://" +this.getHost() + this.getRootPath() ;
		}



}
