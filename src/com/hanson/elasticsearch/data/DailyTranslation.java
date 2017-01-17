package com.hanson.elasticsearch.data;

public class DailyTranslation {
	private int sourceLang_id;
	private String sourceLang;
	private int targetLang_id;
	private String targetLang;
	private int unit_id;
	private int word_count_min;
	private int word_count_max;
	
	
	
	
	public DailyTranslation(int sourceLang_id, String sourceLang, int targetLang_id, String targetLang,
			int word_count_min, int word_count_max) {
		this.sourceLang_id = sourceLang_id;
		this.sourceLang = sourceLang;
		this.targetLang_id = targetLang_id;
		this.targetLang = targetLang;
		this.word_count_min = word_count_min;
		this.word_count_max = word_count_max;
	}
	public int getSourceLang_id() {
		return sourceLang_id;
	}
	public void setSourceLang_id(int sourceLang_id) {
		this.sourceLang_id = sourceLang_id;
	}
	public String getSourceLang() {
		return sourceLang;
	}
	public void setSourceLang(String sourceLang) {
		this.sourceLang = sourceLang;
	}
	public int getTargetLang_id() {
		return targetLang_id;
	}
	public void setTargetLang_id(int targetLang_id) {
		this.targetLang_id = targetLang_id;
	}
	public String getTargetLang() {
		return targetLang;
	}
	public void setTargetLang(String targetLang) {
		this.targetLang = targetLang;
	}
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}
	public int getWord_count_min() {
		return word_count_min;
	}
	public void setWord_count_min(int word_count_min) {
		this.word_count_min = word_count_min;
	}
	public int getWord_count_max() {
		return word_count_max;
	}
	public void setWord_count_max(int word_count_max) {
		this.word_count_max = word_count_max;
	}
}
