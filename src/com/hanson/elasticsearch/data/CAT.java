package com.hanson.elasticsearch.data;

public class CAT {
	private int catId;
	private String catName;
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public CAT(int catId, String catName) {
		this.catId = catId;
		this.catName = catName;
	}
	
	
}
