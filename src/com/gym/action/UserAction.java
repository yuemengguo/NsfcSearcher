package com.gym.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.gym.commom.Base;
import com.gym.dataService.dataService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserAction extends ActionSupport{
	
	private static final long serialVersionUID = -3292423769521497355L;
	
	private int pageNum = 1;
	private int pageSize = 10;
	private String sortName = "";
	private boolean sortType = true;//默认降序排序
	private int totals = 0;
	private int totalPage = 0;
	
	private JSONObject errorMessage = new JSONObject();
	private JSONObject listInfo = new JSONObject();
	private PrintWriter out;

	private JSONObject userInfo;
	
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

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public boolean isSortType() {
		return sortType;
	}

	public void setSortType(boolean sortType) {
		this.sortType = sortType;
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

	public void setListInfo(JSONObject listInfo) {
		this.listInfo = listInfo;
	}

	public JSONObject getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(JSONObject userInfo) {
		this.userInfo = userInfo;
	}
	public JSONObject getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(JSONObject errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void userList() {
		HttpServletResponse response=ServletActionContext.getResponse();  
	    /* 
	     * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码), 
	     * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会 
	     * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。 
	     * */  
		response.setContentType("text/html;charset=utf-8");
		// 指定允许其他域名访问
	    response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应头设置  
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//	    response.setCharacterEncoding("UTF-8");  
		JSONObject json = new JSONObject();
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			json.put("code", 500);
			json.put("message", e1.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close(); 
		    return;
		}
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			String userId = userCheck.getString("userId");
			int offset = (pageNum -1) * pageSize;
			try {
				JSONArray jsonArray = dataService.toJSONArray("SELECT " + 
						"pro_approvalNumber,pro_name,pro_year,pro_type,pro_ChineseAbstract,leader_name,org_ChineseName " + 
						"FROM nsfc_project_all, nsfc_leader_all ,nsfc_organization_all ,nsfc_heart " + 
						"WHERE user_id = ? and pro_id = pro_approvalNumber and " + 
						"pro_leaderCode = leader_code and pro_organizationCode = org_code GROUP BY pro_approvalNumber " + 
						"ORDER BY pro_year desc limit ?,? ", userId,offset,pageSize);
				json.put("items", jsonArray);
			} catch (Exception e) {
				json.put("code", 500);
				json.put("message", e.getMessage());
			    out.println(json);  
			    out.flush();  
			    out.close();  
			    return;
			} 
		}
	    out.println(json);  
	    out.flush();  
	    out.close(); 
	}
	public String execute() {

		//检查用户登陆
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			String userId = userCheck.getString("userId");
			try {
				userInfo = Base.getUserInfo(userCheck.getString("userId"));
			} catch (DataAccessException | IOException e) {
				errorMessage.put("code", 500);
				errorMessage.put("errorMessage", e.getMessage());
				System.out.println(e.getMessage());
			}
			
			int offset = (pageNum -1) * pageSize;
			try {
				JSONObject pageInfo = dataService.toJSONObject("SELECT count(*) as totals " +  
						"FROM nsfc_project_all, nsfc_leader_all ,nsfc_organization_all ,nsfc_heart " + 
						"WHERE user_id = ? and pro_id = pro_approvalNumber and " + 
						"pro_leaderCode = leader_code and pro_organizationCode = org_code GROUP BY pro_approvalNumber " 
						,userId);
				totals = pageInfo.getInt("totals");
				totalPage = totals/pageSize +1;
				
				JSONArray jsonArray = dataService.toJSONArray("SELECT " + 
						"pro_approvalNumber,pro_name,pro_year,pro_type,pro_ChineseAbstract,leader_name,org_ChineseName,leader_organization,leader_jobTitle  " + 
						"FROM nsfc_project_all, nsfc_leader_all ,nsfc_organization_all ,nsfc_heart " + 
						"WHERE user_id = ? and pro_id = pro_approvalNumber and " + 
						"pro_leaderCode = leader_code and pro_organizationCode = org_code GROUP BY pro_approvalNumber " + 
						"ORDER BY pro_year desc limit ?,? ", userId,offset,pageSize);
				listInfo.put("items", jsonArray);
				System.out.println(jsonArray);
			} catch (DataAccessException | IOException e) {
				errorMessage.put("code", 500);
				errorMessage.put("errorMessage", e.getMessage());
				return ERROR;	
			}
			return SUCCESS;
		}else {
			errorMessage.put("code", 211);
			errorMessage.put("errorMessage", "您未登陆...");
			return "redirectLogin";	
		}
	}



}
