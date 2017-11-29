package com.gym.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.gym.commom.Base;
import com.gym.dataService.dataService;
import com.gym.lucene.factory.luceneService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




public class ListAction extends ActionSupport{

	private static final long serialVersionUID = -7103074425036195676L;
	private String key = "";
	private int pageNum = 1;
	private int pageSize = 10;
	private String sortName = "";
	private boolean sortType = true;//默认降序排序
	private int totals = 0;
	private int totalPage = 0;
	
	private String proId = ""; 
	private String userId = "";
	
	private JSONObject listInfo = new JSONObject();
	private JSONObject errorMessage = new JSONObject();
	private JSONObject userInfo = new JSONObject();
	private PrintWriter out;
	public String getKey() {
		return key;
	}

	public void setKey(String key) throws UnsupportedEncodingException {
	    this.key = key;
		System.out.println(this.key);
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortName() {
		return sortName;
	}

	public boolean isSortType() {
		return sortType;
	}

	public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	
	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public JSONObject getListInfo() {
		return listInfo;
	}
	
	public JSONObject getUserInfo() {
		return userInfo;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 该方法用来测试ajax返回请求数据
	 * @throws IOException
	 */
	public void write() throws IOException{  
	    HttpServletResponse response=ServletActionContext.getResponse();  
	    response.setContentType("text/html;charset=utf-8");  
	    PrintWriter out = response.getWriter();  

	    JSONObject json = new JSONObject();
		JSONObject request = formatRequest();
		String[][] indexFields = {{"pro_approvalNumber","1"},{"pro_name","0"},{"leader_name","0"},{"pro_type","0"},{"org_ChineseName","0"},{"org_EnglishName","0"},{"pro_ChineseAbstract","2"},{"pro_ChineseSubjectWord","2"},{"pro_EnglishAbstract","2"},{"pro_EnglishSubjectWord","0"},{"pro_year","2"}};
		String[] searchFields = {"pro_approvalNumber","pro_name","leader_name","org_ChineseName","pro_year"};

		//检查用户登陆
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			try {
				userInfo = Base.getUserInfo(userCheck.getString("userId"));
			} catch (DataAccessException | IOException e) {
				errorMessage.put("code", 500);
				errorMessage.put("errorMessage", e.getMessage());
				System.out.println(e.getMessage());
			}
			json.put("onlineStatus", 1);
			json.put("userInfo", userInfo);
		}else {
			json.put("onlineStatus", 0);
		}
		
		try {
			listInfo = luceneService.searcher(request,indexFields,searchFields);
			json.put("listInfo", listInfo);
		} catch (IOException | ParseException e) {
			errorMessage.put("code", 500);
			errorMessage.put("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
		}
	    
	    out.println(json);  
	    out.flush();  
	    out.close();  
	}
	
	public void saveHeart() {
		HttpServletResponse response=ServletActionContext.getResponse();  
		    /* 
		     * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码), 
		     * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会 
		     * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。 
		     * */  
		response.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject();
		try {
			out = response.getWriter();
		} catch (IOException e) {
			json.put("code", 500);
			json.put("message", e.getMessage());
			out.println(json);
			return;
		} 
		//检查用户登陆
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			userId = userCheck.getString("userId");
			//进行存储
			int count;
			try {
				//先查询是否已经收藏
				JSONObject check = dataService.toJSONObject("select id from nsfc_heart where pro_id =? and user_id = ?", proId,userId);
				if(check.has("id")) {
					json.put("code", 212);
					json.put("message", "已经收藏过了");
				}else {
					count = dataService.getTemplate().update("insert into nsfc_heart set pro_id = ? ,user_id = ? , createDate = now()", proId,userId);
					if(count > 0) {
						json.put("code", 200);
					}else {
						json.put("code", 210);
						json.put("message", "收藏失败");
					}
				}
			} catch (DataAccessException | IOException e) {
				json.put("code", 500);
				json.put("message", e.getMessage());
				out.println(json);
				return;
			}
		}else {
			json.put("code", "211");
			json.put("message", "您未登录...");
		}
		out.println(json);  
	    out.flush();  
	    out.close(); 
	}
	
	public String execute() {

		JSONObject request = formatRequest();
		String[][] indexFields = {{"pro_approvalNumber","1"},{"pro_name","0"},{"leader_name","0"},
				{"pro_type","0"},{"org_ChineseName","0"},{"org_EnglishName","0"},{"pro_ChineseAbstract","2"},
				{"pro_ChineseSubjectWord","2"},{"pro_EnglishAbstract","2"},{"pro_EnglishSubjectWord","0"},{"pro_year","2"}};
		String[] searchFields = {"pro_approvalNumber","pro_name","leader_name","org_ChineseName","pro_year"};

		//检查用户登陆
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			try {
				userInfo = Base.getUserInfo(userCheck.getString("userId"));
			} catch (DataAccessException | IOException e) {
				errorMessage.put("code", 500);
				errorMessage.put("errorMessage", e.getMessage());
				System.out.println(e.getMessage());
				return ERROR; 
			}
			userInfo.put("onlineStatus", 1);	
		}else {
			userInfo.put("onlineStatus", 0);
		}
		
		try {
			listInfo = luceneService.searcher(request,indexFields,searchFields);
			//接下来从数据库获取详细的信息,进行数据的转换
			listInfo = getDataAnddealData(listInfo);
			
			totals = listInfo.getJSONObject("page").optInt("totals",0);
			pageNum = listInfo.getJSONObject("page").optInt("pageNum",1);
			pageSize = listInfo.getJSONObject("page").optInt("pageSize",10);
			totalPage = listInfo.getJSONObject("page").optInt("totalPage",0);
	
		} catch (IOException | ParseException e) {
			errorMessage.put("code", 500);
			errorMessage.put("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
			return ERROR;
		}
		if(listInfo.getInt("code") == 200) {
			System.out.println(listInfo.toString());
			return SUCCESS;
		}else {
			errorMessage.put("code", listInfo.getInt("code"));
			errorMessage.put("errorMessage", listInfo.getString("message"));
			return ERROR;
		}
	}
	
	/**
	 * 处理请求的格式
	 * @return
	 */
	private JSONObject formatRequest() {
		JSONObject request = new JSONObject();
		request.put("queryStr", key);
		request.put("pageSize", pageSize);
		request.put("pageNum", pageNum);
		request.put("sortName", sortName);
		request.put("sortType", sortType);
		request.put("totals", totals);
		request.put("totalPage", totalPage);
		return request;
	}
	
	/**
	 * 以索引从数据库中获取数据
	 * @param listInfo
	 * @return
	 */
	private  JSONObject getDataAnddealData(JSONObject listInfo) {
		JSONObject  json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		JSONArray jsonArray = listInfo.getJSONArray("items");
		//处理零记录
		if(jsonArray.size() > 0) {
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
		}else {
			json = listInfo;
		}
		return json;
	}
}
