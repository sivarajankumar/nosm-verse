package com.nosm.elearning.ariadne.util;

public final class Constants {
	  private Constants() {}  // can't construct

	  public static final String lineSep = System.getProperty ( "line.separator" );


	  // XPATH expressions to retrieve OL content
	  public static final String XPATH_SESSION = "/labyrinth/mysession";
	  public static final String XPATH_TITLE = "/labyrinth/mnodetitle";
	  public static final String XPATH_CONTENT = "/labyrinth/message";
	  public static final String XPATH_ROOTNODE = "/labyrinth/rootnode";
	  public static final String XPATH_ID = "/labyrinth/mnodeid";
	  public static final String XPATH_LINKS = "/labyrinth/linker";
	  public static final String XPATH_GAME_NAME= "/labyrinth/mapname";
	  public static final String XPATH_GAME_TYPE= "/labyrinth/maptype";
	  public static final String XPATH_GAME_ID= "/labyrinth/mapid";
	  public static final String XPATH_VAL = "/text()";


	  // headers for service responses

	  public static final String XML_HEAD_ARIADNE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

	  public static final String XML_HEAD_USER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<users xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";


	  //public static final String XML_HEAD_ARIADNE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +"<ariadne>";

	  //public static final String XML_HEAD_USER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +"<users>";


	  // misc
	  public static final String GAME_HOST3 = "142.51.75.11";



	  public static final String GAME_GAME_ID = "2"; // hard-coded until picking Lab from list is supported

	  public static final String GAME_USER = "olremote";
	  public static final String GAME_PASSWORD = "s3cr3t";


	  public static final String GAME_URL_PATH3 = "/django/labyrinth/";
	  public static final String GAME_URL_PATH_SUFFIX3 = "/data/classic/";
	  public static final String GAME_URL_ID_PREFIX = "asp?id=";
	  public static final String GAME_URL_ID_SUFFIX = "&mode=remote";

	  //public static final String GAME_URL_BASE_PATH3 = GAME_URL_PATH3 + GAME_GAME_ID;


	  public static final String GAME_URL_NAME_SUFFIX = "</a>";
	  public static final String GAME_URL_NAME_PREFIX = "]]]] - ";

	  public static final String GAME_URL_NAME_PREFIX3 = "/data/'>";

	  public static final String GAME_URL_ID_SUFFIX3 = "/data/";
	  public static final String GAME_URL_LINK_PREFIX3 = "/node/";
	  //public static final String GAME_URL_LINK_PREFIX3 = "/link/";

	  public static final String GAME_URL_LINK_START = "start";



	  public static final String GAME_URL_ID_PREFIX3 = GAME_GAME_ID+ GAME_URL_LINK_PREFIX3 ;

	  //UI
	  public static final String UI_NAME = "assetNameOUT";
	  public static final String UI_TYPE = "assetType";
	  public static final String UI_VAL = "assetValueIN";
	  public static final String UI_PAIRS = "savedUIPairs";
	  public static final String UI_TARGET = "assetTargetIN";
	  public static final String UI_ID = "assetMapNodeid";

	}
