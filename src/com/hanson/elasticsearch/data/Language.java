package com.hanson.elasticsearch.data;

public class Language {
	private int lang_id;
	private String lang_name;
	public int getLang_id() {
		return lang_id;
	}
	public void setLang_id(int lang_id) {
		this.lang_id = lang_id;
	}
	public String getLang_name() {
		return lang_name;
	}
	public void setLang_name(String lang_name) {
		this.lang_name = lang_name;
	}
	public Language(int lang_id, String lang_name) {
		this.lang_id = lang_id;
		this.lang_name = lang_name;
	}
	
	
}
