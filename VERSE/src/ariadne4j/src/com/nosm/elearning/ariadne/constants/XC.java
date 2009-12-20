package com.nosm.elearning.ariadne.constants;

public final class XC {
	  private XC() {}  // can't construct


	  // XPATH expressions to retrieve OL content
	  public static final String XPATH_SESSION = "/labyrinth/mysession";
	  public static final String XPATH_TITLE = "/labyrinth/mnodetitle";
	  public static final String XPATH_CONTENT = "/labyrinth/message";
	  public static final String XPATH_ROOTNODE = "/labyrinth/rootnode";
	  public static final String XPATH_ID = "/labyrinth/mnodeid";
	  public static final String XPATH_LINKS = "/labyrinth/links";
	  public static final String XPATH_GAME_NAME= "/labyrinth/mapname";
	  public static final String XPATH_GAME_TYPE= "/labyrinth/maptype";
	  public static final String XPATH_GAME_ID= "/labyrinth/mapid";



	  public static final String XPATH_REMOTEIP = "/labyrinthservice/remoteIP";
	  public static final String XPATH_MAPID = "/labyrinthservice/labyrinthmap/labyrinthmapid";
	  //public static final String XPATH_MAPNAME = "/labyrinthservice/labyrinthmap/labyrinthmapname";
	  public static final String XPATH_MAPROOT = "/labyrinthservice/labyrinthmap/labyrinthmaproot";

	  public static final String XPATH_VAL = "/text()";
	  public static final String XPATH_VAL_ALL = "/node()/child::text()";

	  public static final String XML_HEAD_ARIADNE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<ariadne xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

	  public static final String XML_HEAD_USER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<users xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

	  public static final String XML_TEXT_TYPE = "VPDText";
	  public static final String XML_TEXT_TARGET = "PIVOTE";
	  public static final String XML_TEXT_NAME = "OL";

	  public static final String XML_ASSET = "asset";
	  public static final String XML_ASSET_TYPE = "type";
	  public static final String XML_ASSET_NAME = "name";
	  public static final String XML_ASSET_TARGET = "targettype";
	  public static final String XML_ASSET_VALUE = "value";
	  public static final String XML_ASSET_ID = "iid";
	  public static final String XML_ASSET_PAIRS = "uipairs";

	  public static final String XML_LINK = "link";
	  public static final String XML_LINK_LABEL = "label";
	  public static final String XML_LINK_REF = "ref";

	  public static final String POST_HEADER_USER_AGENT = "Mozilla/5.0 (X11; U; Linux " +
		"i686; en-US; rv:1.8.1.6) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)";
	  public static final String POST_HEADER_ACCEPT ="text/html,application/xml," +
		"application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	  public static final String POST_HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";


	}
