package com.nosm.elearning.ariadne.model.game;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathExpressionException;

import com.nosm.elearning.ariadne.AriadneData;
import com.nosm.elearning.ariadne.XPathReader;
import com.nosm.elearning.ariadne.constants.C;
import com.nosm.elearning.ariadne.constants.XC;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;

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

	public void transform (org.w3c.dom.Document xml) {

		try{
			XPathReader xR = new XPathReader(xml);
			System.out.println("about to try extraction:");
			this.setIp(xR.extractNode(XC.XPATH_REMOTEIP+ XC.XPATH_VAL, xml));
			//String enc_gName = java.net.URLEncoder.encode(C.GAME_NAME, "ISO-8859-1");
			String gName = C.GAME_NAME;
			String mapSearchPrefix = "/labyrinthservice/labyrinthmap[contains(./labyrinthmapname"+
			XC.XPATH_VAL+",\'" + gName +"\')][last()]/labyrinthmap";
			String gameId = xR.extractNode(mapSearchPrefix+"id"+ XC.XPATH_VAL, xml);
			String gameRoot = xR.extractNode(mapSearchPrefix+"root"+ XC.XPATH_VAL, xml);
			String gameName = xR.extractNode(mapSearchPrefix+"name"+ XC.XPATH_VAL, xml);

			if (gameId != null || gameRoot!=null){
				if (gameId.trim() != "" || gameRoot.trim() != ""){
					System.out.println("from list: gameId"+gameId +" : gameRoot"+gameRoot);
					currentGame = new Game(gameName, Integer.parseInt(gameId), Integer.parseInt(gameRoot)); // temp
					ArrayList<Game> theseGames = new ArrayList<Game>();
					theseGames.add(currentGame);
					this.setGames(theseGames);
				}else{
					throw new RuntimeException("Cannot retrieve game for :"+gName);
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch( XPathExpressionException ex){
			System.out.println(ex);
			ex.printStackTrace();
		}
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
