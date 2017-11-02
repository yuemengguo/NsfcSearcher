package com.gym.lucene.factory;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.dao.DataAccessException;

import com.gym.dataService.dataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {
	public static void main(String[] args) throws DataAccessException, IOException, ParseException {
		String[][] indexFields = {{"pro_approvalNumber","1"},{"pro_name","0"},{"leader_name","0"},{"pro_type","0"},{"org_ChineseName","0"},{"org_EnglishName","0"},{"pro_ChineseAbstract","2"},{"pro_ChineseSubjectWord","2"},{"pro_EnglishAbstract","2"},{"pro_EnglishSubjectWord","0"},{"pro_year","2"}};
		String[] searchFields = {"pro_approvalNumber","pro_name","leader_name","org_ChineseName","pro_year"};
		JSONObject data = new JSONObject();
		JSONArray json = new JSONArray();
		
//		json = dataService.toJSONArray("select pro_approvalNumber,pro_name,leader_name,pro_type,org_ChineseName,org_EnglishName,pro_ChineseAbstract,pro_ChineseSubjectWord,pro_EnglishAbstract,pro_EnglishSubjectWord,pro_year " + 
//				"from nsfc_organization_all, nsfc_project_all,nsfc_leader_all " + 
//				"where pro_organizationCode = org_code and pro_leaderCode = leader_code limit 30000");
//		data.put("items", json);
//		data = luceneService.createIndex(data, "pro_year", indexFields);
//		System.out.println(data);
		
		
		JSONObject request = new JSONObject();
		request.put("queryStr", "上海大学");
		request.put("pageNum", 1);
		request.put("pageSize", 10);
		request.put("sortName", "pro_year");
		request.put("sortType", true);
		data = luceneService.searcher(request,indexFields,searchFields);
		data = getDataAnddealData(data);
		System.out.println(data);

	}
	
	public static JSONObject getDataAnddealData(JSONObject listInfo) {
		JSONObject  json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		JSONArray jsonArray = listInfo.getJSONArray("items");
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject list = jsonArray.getJSONObject(i);
			if(i== 0) {
				sb.append(" pro_approvalNumber='"+list.optString("pro_approvalNumber","N/A")+ "'");
			}else {
				sb.append(" or pro_approvalNumber='"+list.optString("pro_approvalNumber","N/A")+ "'");
			}
		}
		try {
			jsonArray = dataService.toJSONArray("select pro_approvalNumber,pro_name,pro_type,pro_year,pro_ChineseAbstract,leader_name,leader_organization,leader_jobTitle from nsfc_project_all,nsfc_organization_all,nsfc_leader_all " + 
					"where pro_leaderCode = leader_code and ("+sb.toString()+") GROUP BY pro_approvalNumber");
			json.put("items", jsonArray);
			json.put("code", 200);
			json.put("page", listInfo.getJSONObject("page"));
			
		} catch (DataAccessException | IOException e) {
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("code", 500);
			errorMessage.put("message", e.getMessage());
			return errorMessage;
		}
		return json;
	}
}
