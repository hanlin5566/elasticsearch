package com.hanson.elasticsearch;

import com.hanson.elasticsearch.client.ESClient;
import com.hanson.elasticsearch.data.Translator;

public class BootStart {
	public static void main(String[] args) {
		try {
			String indicesName = "hanson";
			String typeName = "translatorInfo";
//			ImportData im = new ImportData();
//			Map<Integer, Translator> translatorMap = im.importData();
			ESClient client = new ESClient("localhost", 9300);
			client.connect();
			client.create(indicesName, typeName);
			client.createMapping(indicesName, typeName, Translator.properties);
//			for (Integer translatorId : translatorMap.keySet()) {
//				Translator translator = translatorMap.get(translatorId);
//				JSONObject json = new JSONObject(translator);
//				json.put("id", translatorId);
//				client.put(indicesName, typeName, json);
//				System.out.println("translatorId:[" + translatorId + "] json:" + json);
//			}
			client.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
