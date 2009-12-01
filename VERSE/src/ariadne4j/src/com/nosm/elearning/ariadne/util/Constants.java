package com.nosm.elearning.ariadne.util;

public final class Constants {
	  private Constants() {}  // can't construct

	  // XPATH expressions to retrieve OL content
	  public static final String XPATH_SESSION = "/labyrinth/mysession";
	  public static final String XPATH_TITLE = "/labyrinth/mnodetitle";
	  public static final String XPATH_CONTENT = "/labyrinth/message";
	  public static final String XPATH_ID = "/labyrinth/mnodeid";
	  public static final String XPATH_LINKS = "/labyrinth/linker";
	  public static final String XPATH_VAL = "/text()";

	  // headers for service responses
	  public static final String XML_HEAD_ARIADNE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

	  public static final String XML_HEAD_USER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<users xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

	  // misc
	  public static final String OL_HOST = "142.51.75.11";
	  public static final String OL_GAME_ID = "2"; // hard-coded until picking Lab from list is supported

	  //UI
	  public static final String UI_NAME = "assetNameOUT";
	  public static final String UI_TYPE = "assetType";
	  public static final String UI_VAL = "assetValueIN";
	  public static final String UI_PAIRS = "savedUIPairs";
	  public static final String UI_TARGET = "assetTargetIN";
	  public static final String UI_ID = "assetMapNodeid";

	}
