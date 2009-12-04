package com.nosm.elearning.ariadne.model.game.uri;

import com.nosm.elearning.ariadne.util.Constants;

public class OLGameUri extends GameUri {
	  public OLGameUri(){
		  super();
			this.setHost(Constants.GAME_HOST);
			this.setGameId("233"); // hard-coded until picking Lab from list is supported
			this.setPath(Constants.GAME_URL_PATH);
			this.setPathSuffix(Constants.GAME_URL_PATH_SUFFIX);
			this.setNamePrefix(Constants.GAME_URL_NAME_PREFIX);
			this.setLinkPrefix(Constants.GAME_URL_LINK_PREFIX);
			this.setIdSuffix(Constants.GAME_URL_ID_SUFFIX);
	  }

}
