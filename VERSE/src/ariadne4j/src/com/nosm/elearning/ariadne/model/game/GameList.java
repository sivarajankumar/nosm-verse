package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.*;

import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.constants.*;
import com.nosm.elearning.ariadne.model.Asset;

public class GameList {
	private String name = "labyrinthservice";
	private int version = 2;
	private ArrayList<Game> games;
	private String ip;
	public Game currentGame;

	public GameList(org.w3c.dom.Document xml) {
		super();
		transform(xml);
	}

	public void transform(org.w3c.dom.Document xml) {

		try {
			XPathReader xR = new XPathReader(xml);
			System.out.println("about to try extraction:");

			this.setIp(xR.extractNode(XC.XPATH_REMOTEIP + XC.XPATH_VAL, xml));
			// String enc_gName = java.net.URLEncoder.encode(C.GAME_NAME,
			// "ISO-8859-1");

			// String gName = C.GAME_NAME;
			// String mapSearchPrefix =
			// "/labyrinthservice/labyrinthmap[contains(./labyrinthmapname"+
			// XC.XPATH_VAL+",\'" + gName +"\')][last()]/labyrinthmap";
			String mapSearchPrefix = "/labyrinthservice/labyrinthmap";

			NodeList mapList = xR.extractNodeSet(mapSearchPrefix, xml);
			ArrayList<Game> theseGames = new ArrayList<Game>();

			for (int index = 0; index < mapList.getLength(); index++) {
				Node aNode = mapList.item(index);
				if (aNode.getNodeName() == "labyrinthmap") { // aNode.getNodeType() == Node.ELEMENT_NODE &&
					NodeList childNodes = aNode.getChildNodes();
					if (childNodes.getLength() > 0) {
						String gameId = "";
						String gameRoot = "";
						String gameName ="";


						for (int j = 0; j < childNodes.getLength(); j++) {

							Node mapNode = childNodes.item(j);
							System.out.println("Node Name-->" + mapNode.getNodeName()
									+ " , Node Value-->" + mapNode.getTextContent());
							if (mapNode.getNodeName() == "labyrinthmapid"){
								gameId = mapNode.getTextContent();
							}
							if (mapNode.getNodeName() == "labyrinthmaproot"){
								gameRoot = mapNode.getTextContent();
							}
							if (mapNode.getNodeName() == "labyrinthmapname"){
								gameName = mapNode.getTextContent();
							}

						}

						if (gameId != null || gameRoot != null) {
							if (gameId.trim() != "" || gameRoot.trim() != "") {
								System.out.println("from list: gameId" + gameId + " : gameRoot" + gameRoot);
								Game thisGame = new Game(gameName, Integer
										.parseInt(gameId), Integer
										.parseInt(gameRoot));
								theseGames.add(thisGame);
							} else {
								throw new RuntimeException(
										"Cannot retrieve games from labyrinth map service: "
										+ aNode.getTextContent());
							}
						}
					}
				}

				//if (index > 4) index = mapList.getLength(); // limit to 6 (pivote controller limit)
			}

			// gameId = xR.extractNode(mapSearchPrefix+"id"+ XC.XPATH_VAL, xml);
			// gameRoot = xR.extractNode(mapSearchPrefix+"root"+ XC.XPATH_VAL, xml);
			// gameName = xR.extractNode(mapSearchPrefix+"name"+ XC.XPATH_VAL, xml);
			this.setGames(theseGames);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (XPathExpressionException ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}

	public Document getAriadneXml(boolean isAdmin, boolean autoIncludeTextAsset) {
		StringBuffer reply = new StringBuffer();
		reply.append(XC.XML_HEAD_ARIADNE + C.br
				+ "<session id=\"\" type=\"OL\" ></session>" + C.br
				+ "<node id=\"99999\" label=\"" + this.getName()
				+ "\" ></node>" + C.br);
		reply.append("<" + XC.XML_ASSET + "s>" + C.br);
		reply.append(
				new Asset(
						XC.XML_TEXT_NAME,
						XC.XML_TEXT_TYPE,
						java.net.URLEncoder.encode("Available Games:"),
						"",
						XC.XML_TEXT_TARGET,
						99999)
				.getXML(isAdmin)
				);
		reply.append(C.br + "</" + XC.XML_ASSET + "s>" + C.br);

		reply.append(this.getLinksXML());
		reply.append("</ariadne>");
		System.out.println("built this ariadne xml  " + reply.toString());
		XPathReader xR = new XPathReader(reply.toString());
		return xR.getXmlDocument();
	}

	public String getLinksXML() {
		StringBuffer reply = new StringBuffer();
		reply.append("<" + XC.XML_LINK + "s>");
		Iterator j = this.getGames().iterator();
		while (j.hasNext()) {
			Game thisGame = (Game) j.next();
			String thisID = Integer.toString(thisGame.getRootNode()); // starts game at root node upon next call
			String thisLabel = thisGame.getName();
			reply.append("<" + XC.XML_LINK + " " + XC.XML_LINK_LABEL + "=\""
					+ thisLabel + "\" " + XC.XML_LINK_REF + "=\"" + thisID
					+ "\" ></" + XC.XML_LINK + ">" + C.br);
		}
		reply.append("</" + XC.XML_LINK + "s>" + C.br);

		return reply.toString();
	}

	public String getName() {
		return name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
