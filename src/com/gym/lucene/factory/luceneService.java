package com.gym.lucene.factory;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import net.sf.json.JSONObject;

public class luceneService {
	public static LuceneFactory luceneFactory;
	
	public luceneService() {
		System.out.println("初始化LuceneFactory");
	}
	public static JSONObject createIndex(JSONObject data, String sortName,String[][] indexFields) throws IOException {
		JSONObject json = new JSONObject();
		if(luceneFactory == null) {
			luceneFactory = new LuceneFactory(); 
		}
		if(indexFields.length == 0) {
			json.put("code", 500);
			json.put("message", "index fields is required");
			return json;
		}
		json = luceneFactory.createIndex(data, sortName, indexFields);
		return json;
	}
	
	public static JSONObject searcher(JSONObject request,String[][] indexFields,String[] searchFields) throws IOException, ParseException {
		JSONObject json = new JSONObject();
		
		String queryStr = request.optString("queryStr","");
		String sortName = request.optString("sortName","");
		
		//参数检测
		if(queryStr == "") {
			json.put("code", 500);
			json.put("message","key words is required");
			return json;
		}
		if(sortName == "") {
			json.put("code", 500);
			json.put("message","order field id required");
			return json;
		}
		if(luceneFactory == null) {
			luceneFactory = new LuceneFactory(); 
		}
		json = luceneFactory.search(request, indexFields,searchFields);
//		System.out.println("测试过来的;"+luceneFactory.ttt());
		return json;
	}
}
