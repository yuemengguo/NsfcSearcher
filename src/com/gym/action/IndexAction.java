package com.gym.action;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.gym.commom.Base;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class IndexAction extends ActionSupport{

	private static final long serialVersionUID = 8202211308177681205L;
	public final static ActionContext actionContext = ActionContext.getContext();
	private JSONObject userInfo = null;
	private JSONObject errorMessage = null;
	
	public JSONObject getUserInfo() {
		return userInfo;
	}

	public String execute() {
		//检查用户登陆
		int code = 200;
		JSONObject json = new JSONObject();
		//建立登陆账户
		json = Base.checkPassport();
		if(json.getInt("onlineStatus") == 1) {
			try {
				json = Base.getUserInfo(json.getString("userId"));
			} catch (DataAccessException | IOException e) {
				code = 500;
				json.put("code", code);
				json.put("errorMessage", e.getMessage());
				System.out.println(e.getMessage());
				errorMessage = json;
				return ERROR; 
			}
			json.put("onlineStatus", 1);
		}else {
			json.put("onlineStatus", 0);
		}
		this.userInfo = json;
		
		return SUCCESS;
	}

	/**
	 * 处理错误信息
	 * @return
	 */
	public JSONObject getErrorMessage() {
		return errorMessage;
	}	
}
