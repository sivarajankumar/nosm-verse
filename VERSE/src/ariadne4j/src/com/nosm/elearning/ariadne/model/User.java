package com.nosm.elearning.ariadne.model;


public class User {

	  private int id;
	  private String avatarFirst;
	  private String avatarLast;
	  private String first;
	  private String last;
	  private String avatarKey;


	public User() {
		super();
	}


	public String getAvatarFirst() {
		return avatarFirst;
	}


	public void setAvatarFirst(String avatarFirst) {
		this.avatarFirst = avatarFirst;
	}


	public String getAvatarKey() {
		return avatarKey;
	}


	public void setAvatarKey(String avatarKey) {
		this.avatarKey = avatarKey;
	}


	public String getAvatarLast() {
		return avatarLast;
	}


	public void setAvatarLast(String avatarLast) {
		this.avatarLast = avatarLast;
	}


	public String getFirst() {
		return first;
	}


	public void setFirst(String first) {
		this.first = first;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLast() {
		return last;
	}


	public void setLast(String last) {
		this.last = last;
	}


	public User(String avatarFirst, String avatarLast, String avatarKey) {
		super();
		this.avatarFirst = avatarFirst;
		this.avatarLast = avatarLast;
		this.avatarKey = avatarKey;
	}


	public User(String avatarFirst, String avatarLast) {
		super();
		this.avatarFirst = avatarFirst;
		this.avatarLast = avatarLast;
	}



}
