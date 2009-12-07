package com.nosm.elearning.ariadne.util;

public final class TestConstants {
	  private TestConstants() {}  // can't construct
	  public static final String NODE_SSID ="31456703fb877d3db43a4bcef72b3fec";
	  // XPATH expressions to retrieve OL content
	  public static final String NODE1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<labyrinth>"+
		"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+start+node:+sphere,+cube</mnodetitle>"+
		"<mapname>Test+OL%2DPivote</mapname>"+
		"<mapid>233</mapid>"+
		"<mnodeid>8336</mnodeid>"+
		"<message>%3Cp%3Ebriefing:+patient+is+sick%3C%2Fp%3E</message>"+
		"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8337%26mode%3Dremote%26sessid"+
		"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules+"+
		"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8338%26mode%3Dremote%26sessid%3DE27A2972"+
		"%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+also+complains+of+backache%3C%2Fa%3E%3C%2Fp%3E</linker>"+
		"<rootnode>8336</rootnode>"+
		"<mysession>31456703fb877d3db43a4bcef72b3fec</mysession>"+
		"<maptype>maze</maptype>"+
	"</labyrinth>";


	  public static final String NODE2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<labyrinth>"+
		"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules</mnodetitle>"+
		"<mapname>Test+OL%2DPivote</mapname>"+
		"<mapid>233</mapid>"+
		"<mnodeid>8337</mnodeid>"+
		"<message>%3Cp%3Epatient+has+a+backache%3C%2Fp%3E</message>"+
		"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8336%26mode%3Dremote%26sessid"+
		"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+back+to+start+node+"+
		"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8338%26mode%3Dremote%26sessid%3DE27A2972"+
		"%2DDE2B%2D8338%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+a+dark+room+with+2+computer+screens%3C%2Fa%3E%3C%2Fp%3E</linker>"+
		"<rootnode>8336</rootnode>"+
		"<mysession>31456703fb877d3db43a4bcef72b3fec</mysession>"+
		"<maptype>maze</maptype>"+
	"</labyrinth>";

	  public static final String NODE3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<labyrinth>"+
		"<mnodetitle>ID%3D%5B2%5D%5D%5D%5D+%2D+a+dark+room+with+2+computers</mnodetitle>"+
		"<mapname>Test+OL%2DPivote</mapname>"+
		"<mapid>233</mapid>"+
		"<mnodeid>8338</mnodeid>"+
		"<message>%3Cp%3Emessage:+a+dark+room+with+2+computers%3C%2Fp%3E</message>"+
		"<linker>%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8336%26mode%3Dremote%26sessid"+
		"%3DE27A2972%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B3%5D%5D%5D%5D+%2D+back+to+start+node+"+
		"%2D+%3C%2Fa%3E%3C%2Fp%3E%3Cp%3E%3Ca+href%3D%27mnode%5Fclient%2Easp%3Fid%3D8337%26mode%3Dremote%26sessid%3DE27A2972"+
		"%2DDE2B%2D4995%2D8EE7%2D786115628B9E%27%3EID%3D%5B5%5D%5D%5D%5D+%2D+a+white+room+with+3+molecules%3C%2Fa%3E%3C%2Fp%3E</linker>"+
		"<rootnode>8336</rootnode>"+
		"<mysession>31456703fb877d3db43a4bcef72b3fec</mysession>"+
		"<maptype>maze</maptype>"+
	"</labyrinth>";


	  public static final String NODE4 =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
	  "<labyrinth>"+
	      "<mnodetitle>Briefing</mnodetitle>"+
	      "<mapname>ariadne1</mapname>"+
	      "<mapid>2</mapid>"+
	      "<mnodeid>8</mnodeid>"+
	      "<message>%3Cp%3EWelcome%20to%20the%20briefing%20room%3C/p%3E</message>"+
	      "<linker>"+
          "<p><a href='/django/labyrinth/2/link/6/data/'>Exam Room</a></p>"+
          "<p><a href='/django/labyrinth/2/link/12/data/'>Anims Demo</a></p>"+
      "</linker>"+
	      "<rootnode>8</rootnode>"+
	      "<mysession>31456703fb877d3db43a4bcef72b3fec</mysession>"+
	      "<maptype>game</maptype>"+
	  "</labyrinth>";

	  public static final String NODE5 =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
	  "<labyrinth>"+
	      "<mnodetitle>I%20Agree</mnodetitle>"+
	      "<mapname>ariadne1</mapname>"+
	      "<mapid>2</mapid>"+
	      "<mnodeid>9</mnodeid>"+
	      "<message>%3Cp%3Ethe%20%27yes_happy%27%20animation%3C/p%3E</message>"+
	      "<linker>%3Cp%3E%3Ca%20href%3D'/django/labyrinth%2F2%2Flink%2F8%2Fdata%2F'%3EBack%20to%20briefing%3C%2Fa%3E%3C%2Fp%3E" +
	      "</linker>"+
	      "<rootnode>8</rootnode>"+
	      "<mysession>31456703fb877d3db43a4bcef72b3fec</mysession>"+
	      "<maptype>game</maptype>"+
	  "</labyrinth>";

	}
