package com.nosm.elearning.ariadne.util;

public final class ErrorConstants {
	  private ErrorConstants() {}  // can't construct

	  public static final String XML_SSID_PREFIX = "<error>Fatal Error: session id ";
	  public static final String XML_SSID_SUFFIX = " returned does not match the current session id: ";

	  public static final String XML_NO_MODE = "<error>Critical error: No mode has been defined in the request</error>";
	  public static final String XML_NO_NODE = "<error>Missing required MNodeID in the request</error>";
	  public static final String XML_NO_ASSET = "<error>no asset id defined, cannot remove</error>";

	  public static final String XML_REDIR_PREFIX = "<html><head><title></title></head><body onload='window.location=\"/ariadne4j/index2.html?mnodeid=";
	  public static final String XML_REDIR_SUFFIX = "&mode=admin\"></body></html>";

	  public static final String XML_BASIC_PREFIX = "<Error>AriadneException: ";

	  public static final String XML_ET = "</error>";
}
