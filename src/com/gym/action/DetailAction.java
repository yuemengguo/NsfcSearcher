package com.gym.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.dao.DataAccessException;

import com.gym.commom.Base;
import com.gym.dataService.dataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class DetailAction extends ActionSupport{

	private static final long serialVersionUID = -2384153070344532630L;
	public static ActionContext actionContext = ActionContext.getContext();
	private String key = "";
	private JSONObject detailInfo = new JSONObject();
	private JSONObject errorMessage = new JSONObject();
	private JSONObject userInfo = new JSONObject();
	private String id = "";
	public String getKey() {
		return key;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKey(String key) throws UnsupportedEncodingException {
		byte[] b = key.getBytes("ISO-8859-1");//用tomcat的格式（iso-8859-1）方式去读。
		this.key = new String(b,"utf-8");//采用utf-8去接string
		System.out.println(this.key);
	}
	public JSONObject getDetailInfo() {
		return detailInfo;
	}
	public JSONObject getErrorMessage() {
		return errorMessage;
	}
	public JSONObject getUserInfo() {
		return userInfo;
	}
	public String execute() {
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
		//加载项目详细信息
		try {
			detailInfo = dataService.toJSONObject("select * from nsfc_organization_all, nsfc_project_all,nsfc_leader_all " + 
					"where pro_approvalNumber = ? and pro_organizationCode = org_code and pro_leaderCode = leader_code GROUP BY pro_approvalNumber"
					,id);
		} catch (DataAccessException | IOException e) {
			errorMessage.put("code", 500);
			errorMessage.put("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
			return ERROR; 
		}
		if(detailInfo.isEmpty()) {
			errorMessage.put("code", 500);
			errorMessage.put("errorMessage", "数据库数据不存在");
			return ERROR;
		}
		return SUCCESS;
	}
}
