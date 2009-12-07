package com.nosm.elearning.ariadne.model;

import java.util.Iterator;

import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.XConstants;

public class Asset {

	private int id;
	private String name = "";
	private String type= "";
	private String value= "";
	private String UIPairs= "";
	private String target= "";
	private int nodeid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String val) {
		this.value = val;
	}

	public String getUIPairs() {
		return UIPairs;
	}

	public String getEncodedUIPairs() {
		return java.net.URLEncoder.encode(getUIPairs());
	}

	public void setUIPairs(String pairs) {
		this.UIPairs = pairs;
	}


	public String getTarget() {
		return target;
	}

	public void setTarget(String tar) {
		this.target = tar;
	}


	public Asset() {
		super();

	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public Asset(String name, String type, String value, String pairs, String target, int nodeid) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
		this.UIPairs = pairs;
		this.target = target;
		this.nodeid = nodeid;


	}

	public Asset(int id, String name, String type, String value, String pairs, String target, int nodeid) {
		this(name, type, value,  pairs,  target,  nodeid);
		this.id = id;

	}

	public Asset(Asset asset) {
		super();
		reset( asset);
	}


	public void reset(Asset asset){
		this.setName( asset.getName());
		this.setType(asset.getType());
		this.setTarget(asset.getTarget());
		this.setValue(asset.getValue());
		this.setId(asset.getId());
		this.setNodeid(asset.getNodeid());
		this.setUIPairs(asset.getUIPairs());
	}


	public String getXML(Asset asset, boolean isAdmin){
		reset(asset);
		return getXML(isAdmin);
	}

	public String getXML(Asset asset){
		reset(asset);
		return getXML(true);
	}

	public String getXML( boolean isAdmin){
		String oXML = "<"+XConstants.XML_ASSET+" "+XConstants.XML_ASSET_TYPE+"=\"" + this.getType();

		if(isAdmin)oXML = oXML + "\" "+
			XConstants.XML_ASSET_ID+"=\"" + this.getId();

		oXML = oXML + "\" "+XConstants.XML_ASSET_NAME+"=\"" + this.getName()+
			"\" "+XConstants.XML_ASSET_TARGET+"=\"" + this.getTarget()+ "\" "+
			XConstants.XML_ASSET_VALUE+"=\"" + this.getValue();

		if(isAdmin)oXML = oXML + "\" "+XConstants.XML_ASSET_PAIRS+"=\"" + this.getEncodedUIPairs();

		oXML = oXML + "\" ></"+XConstants.XML_ASSET+">" + Constants.lineSep;
		return oXML;
	}

}
