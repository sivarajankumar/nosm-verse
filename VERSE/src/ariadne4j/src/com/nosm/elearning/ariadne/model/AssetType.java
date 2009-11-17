package com.nosm.elearning.ariadne.model;

public class AssetType {
	private int id; //type id
	private String name;
	private String value;
	private String type;
	private String ui_cat;
	private int sl_inv_type;
	private String desc;

	private boolean parcel_media;
	private boolean loop;
	private boolean duration;
	private boolean prim;
	private boolean object;
	private boolean media;
	private boolean action;
	private boolean text;
	private boolean mut_excl;
	private boolean persist;
	private boolean chat;
	private boolean clickable;
	private boolean controllable;
	private boolean restrainable;
	private boolean url;
	private boolean mime;
	private boolean system;
	private boolean hidden;
	private boolean admin;


	public AssetType() {
		super();
	}
	public String getValue() {
		if (value == null){
			return new String();
		}
		return value;
	}
	public void setValue(String attribs) {
		this.value = attribs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AssetType(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public AssetType(String name, String value, int sltype) {
		super();
		this.name = name;
		this.value = value;
		this.sl_inv_type = sltype;
	}


	public AssetType(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name; // if name is null, should be the select field name for this asset type
		this.value = value;
	}

	public AssetType(String id, String name, String value) {
		this(Integer.parseInt(id), name, value);
	}

	public boolean isAction() {
		return action;
	}
	public boolean isAdmin() {
		return admin;
	}
	public boolean isChat() {
		return chat;
	}
	public boolean isClickable() {
		return clickable;
	}
	public boolean isControllable() {
		return controllable;
	}
	public boolean isDuration() {
		return duration;
	}
	public boolean isHidden() {
		return hidden;
	}
	public boolean isLoop() {
		return loop;
	}
	public boolean isMedia() {
		return media;
	}
	public boolean isMime() {
		return mime;
	}
	public boolean isMut_excl() {
		return mut_excl;
	}
	public boolean isObject() {
		return object;
	}
	public boolean isParcel_media() {
		return parcel_media;
	}
	public boolean isPersist() {
		return persist;
	}
	public boolean isPrim() {
		return prim;
	}
	public boolean isRestrainable() {
		return restrainable;
	}
	public int getSl_inv_type() {
		return sl_inv_type;
	}
	public boolean isSystem() {
		return system;
	}
	public boolean isText() {
		return text;
	}
	public String getUi_cat() {
		return ui_cat;
	}
	public boolean isUrl() {
		return url;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public void setChat(boolean chat) {
		this.chat = chat;
	}
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}
	public void setDuration(boolean duration) {
		this.duration = duration;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	public void setMedia(boolean media) {
		this.media = media;
	}
	public void setMime(boolean mime) {
		this.mime = mime;
	}
	public void setMut_excl(boolean mut_excl) {
		this.mut_excl = mut_excl;
	}
	public void setObject(boolean object) {
		this.object = object;
	}
	public void setParcel_media(boolean parcel_media) {
		this.parcel_media = parcel_media;
	}
	public void setPersist(boolean persist) {
		this.persist = persist;
	}
	public void setPrim(boolean prim) {
		this.prim = prim;
	}
	public void setRestrainable(boolean restrainable) {
		this.restrainable = restrainable;
	}
	public void setSl_inv_type(int sl_inv_type) {
		this.sl_inv_type = sl_inv_type;
	}
	public void setSystem(boolean system) {
		this.system = system;
	}
	public void setText(boolean text) {
		this.text = text;
	}
	public void setUi_cat(String ui_cat) {
		this.ui_cat = ui_cat;
	}
	public void setUrl(boolean url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}


}
