package com.hanson.elasticsearch.data;

import java.util.List;

import org.json.JSONObject;

public class Translator {
	public static JSONObject properties = new JSONObject();
	/**
	 * Translator的数据类型
	 */
	static{
		JSONObject fullTextJson = new JSONObject();
		JSONObject fieldJson = new JSONObject();
		//定义需要全文检索的字段
		properties.put("properties", fullTextJson);
		fullTextJson.put("type", "string");
		fullTextJson.put("analyzer", "ik");
		fieldJson.put("university", fullTextJson);
		properties.put("properties", fieldJson);
		fieldJson.put("other_cat", fullTextJson);
		properties.put("properties", fieldJson);
	}
	
	private int user_id;
	private String nickname;
	private String email;
//	private String freeze;//是否冻结
	//个人基础信息
	private String signature;//个性签名
	private String university;//学校
	private String major;//专业 user_detail
	private String degree;//学历 user_detail
	private String full_time;//工作性质 全职兼职
	private String weekend_work;//周末是否工作
	private String first_lang;//第一语言
	private List<Language> second_lang;//第二语言
	private String other_lang;//其他语言
	private String other_cat;//其他cat
	private String industry;
	private List<DailyTranslation> dailyTranslations;
	private List<Project> projects;
	private List<CAT> cats;
	private String resume;
	private String description;//备注信息  user_description
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSignature() {
		return signature;
	}
	
	public String getOther_cat() {
		return other_cat;
	}
	public void setOther_cat(String other_cat) {
		this.other_cat = other_cat;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getFull_time() {
		return full_time;
	}
	public void setFull_time(String full_time) {
		this.full_time = full_time;
	}
	public String getWeekend_work() {
		return weekend_work;
	}
	public void setWeekend_work(String weekend_work) {
		this.weekend_work = weekend_work;
	}
	public String getFirst_lang() {
		return first_lang;
	}
	public void setFirst_lang(String first_lang) {
		this.first_lang = first_lang;
	}
	public List<Language> getSecond_lang() {
		return second_lang;
	}
	public void setSecond_lang(List<Language> second_lang) {
		this.second_lang = second_lang;
	}
	public String getOther_lang() {
		return other_lang;
	}
	public void setOther_lang(String other_lang) {
		this.other_lang = other_lang;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public List<DailyTranslation> getDailyTranslations() {
		return dailyTranslations;
	}
	public void setDailyTranslations(List<DailyTranslation> dailyTranslations) {
		this.dailyTranslations = dailyTranslations;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public List<CAT> getCats() {
		return cats;
	}
	public void setCats(List<CAT> cats) {
		this.cats = cats;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
