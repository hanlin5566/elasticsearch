package com.hanson.elasticsearch.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ImportData {
	private Logger logger = Logger.getLogger(this.getClass());
	public static HashMap<Integer, String> degreeMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> industryMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> languageMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> catMap = new HashMap<Integer, String>();
	private Map<Integer,Translator> translatorMap = new LinkedHashMap<Integer,Translator>();
	public Map<Integer,Translator> importData(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://192.168.29.205:3306/ecis_wiitrans?rewriteBatchedStatements=true&user=myuser&password=password";
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();
			//查询学历字典
			String query = "SELECT degree_id,degree FROM dict_degree";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int degreeId = rs.getInt("degree_id");
				String degree = rs.getString("degree");
				degreeMap.put(degreeId,degree);
			}
			query = "SELECT industry_id,industry_name FROM dict_industry";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int industry_id = rs.getInt("industry_id");
				String industry_name = rs.getString("industry_name");
				industryMap.put(industry_id,industry_name);
			}
			query = "SELECT language_id,language_name,cname FROM dict_language";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int language_id = rs.getInt("language_id");
				String language_name = rs.getString("cname");
				languageMap.put(language_id,language_name);
			}
			query = "SELECT cat_id,name FROM dict_cat";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int cat_id = rs.getInt("cat_id");
				String name = rs.getString("name");
				catMap.put(cat_id,name);
			}
			//query user basic info and description
			query = "SELECT u.user_id,u.nickname,u.email,"
							+"u.signature,"
							+"u.university,"
							+"det.major,"
							+"det.degree_id,"
							+"det.full_time,"
							+"det.weekend_work,"
							+"det.first_lang_id,"
							+"det.other_lang,"
							+"det.other_cat,"
							+"det.other_industry,"
							+"det.resume_file_id "
						+"FROM"
							+"`user` u "
						+"LEFT JOIN translator_details det ON u.user_id = det.translator_id "
						+"WHERE"
							+" u.role_id = 2";
			
			rs = stmt.executeQuery(query);
			while(rs.next()){
				Translator translator = new Translator();
				translator.setUser_id(rs.getInt("user_id"));
				translator.setNickname(rs.getString("nickname"));
				translator.setEmail(rs.getString("email"));
				translator.setSignature(rs.getString("signature"));
				translator.setUniversity(rs.getString("university"));
				translator.setMajor(rs.getString("major"));
				translator.setDegree(degreeMap.get(rs.getInt("degree_id")));//没映射
				translator.setFull_time(rs.getInt("full_time")==1?"兼职":"全职");
				translator.setWeekend_work(rs.getInt("weekend_work")==1?"周末工作":"周末休息");
				translator.setFirst_lang(languageMap.get(rs.getInt("first_lang_id")));
				translator.setOther_cat(rs.getString("other_cat"));
				translator.setOther_lang(rs.getString("other_lang"));
				translator.setSecond_lang(new ArrayList<Language>());
				translator.setDailyTranslations(new ArrayList<DailyTranslation>());
				translator.setCats(new ArrayList<CAT>());
				translator.setProjects(new ArrayList<Project>());
				//需要联查 trans_industry
				translator.setIndustry(rs.getString("other_industry"));
				translatorMap.put(translator.getUser_id(),translator);
			}
			//查询其他语言
			query = "SELECT  translator_id,lang_id FROM trans_other_lang";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int translator_id = rs.getInt("translator_id");
				int lang_id = rs.getInt("lang_id");
				String lang_name = languageMap.get(lang_id);
				Language language = new Language(lang_id, lang_name);
				if(translatorMap.containsKey(translator_id))
					translatorMap.get(translator_id).getSecond_lang().add(language);
			}
			//查询其他领域
			query = "SELECT translator_id,industry_id FROM trans_industry";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int translator_id = rs.getInt("translator_id");
				if(translatorMap.containsKey(translator_id))
					translatorMap.get(translator_id).setIndustry((translatorMap.get(translator_id).getIndustry()+"，"+industryMap.get(rs.getInt("industry_id"))));
			}
			//查询日输出量
			query = "SELECT translator_id,source_lang,target_lang,word_count_min,word_count_max FROM trans_daily_translation";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int translator_id = rs.getInt("translator_id");
				int sourceLang_id = rs.getInt("source_lang");
				int targetLang_id = rs.getInt("target_lang");
				String sourceLang = languageMap.get(sourceLang_id);
				String targetLang = languageMap.get(targetLang_id);
				int word_count_min = rs.getInt("word_count_min");
				int word_count_max = rs.getInt("word_count_max");
				DailyTranslation dailyTranslation = new DailyTranslation(sourceLang_id, sourceLang, targetLang_id, targetLang, word_count_min, word_count_max);
				if(translatorMap.containsKey(translator_id))
					translatorMap.get(translator_id).getDailyTranslations().add(dailyTranslation);
			}
			//cat工具
			query = "SELECT translator_id,cat_id FROM trans_cat WHERE translator_id";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int translator_id = rs.getInt("translator_id");
				int catId = rs.getInt("cat_id");
				String catName = catMap.get(catId);
				CAT cat = new CAT(catId, catName);
				if(translatorMap.containsKey(translator_id))
					translatorMap.get(translator_id).getCats().add(cat);
			}
			//项目经验
			query = "SELECT translator_id,trans_project_id,project_name,project_content FROM trans_project WHERE translator_id";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				int translator_id = rs.getInt("translator_id");
				int trans_project_id = rs.getInt("trans_project_id");
				String project_name = rs.getString("project_name");
				String project_content = rs.getString("project_content");
				Project project = new Project(trans_project_id, project_name,project_content);
				if(translatorMap.containsKey(translator_id))
					translatorMap.get(translator_id).getProjects().add(project);
			}
			return translatorMap;
		} catch (Exception e) {
			logger.error("import data error",e);
		}
		return null;
	}
}
