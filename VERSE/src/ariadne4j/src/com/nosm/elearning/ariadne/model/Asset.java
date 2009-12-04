package com.nosm.elearning.ariadne.model;

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

	public String getXML(){
		return "<asset type=\"" + this.getType()
		+ "\" iid=\"" + this.getId()
		+ "\" name=\"" + this.getName()
		+ "\" targettype=\"" + this.getTarget()
		+ "\" value=\"" + this.getValue()
		+ "\" uipairs=\"" + this.getEncodedUIPairs()
		+ "\" ></asset>";
	}

	public String getXML(boolean isAdmin){
		String oXML = "<asset type=\"" + this.getType();
		if(isAdmin)oXML = oXML + "\" iid=\"" + this.getId();
		oXML = oXML + "\" name=\"" + this.getName()+ "\" targettype=\"" + this.getTarget()+ "\" value=\"" + this.getValue();
		if(isAdmin)oXML = oXML + "\" uipairs=\"" + this.getEncodedUIPairs();
		oXML = oXML + "\" ></asset>";
		return oXML;
	}
}
