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

public class AdminAction extends ActionSupport{

	private static final long serialVersionUID = -4847173831521511903L;
	private JSONArray userList;
	private PrintWriter out;
	public void list() {
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
		try {
			JSONArray jsonArray = dataService.toJSONArray("select * from nsfc_user");
			JSONArray items = new JSONArray();
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				JSONObject createDate = item.getJSONObject("createDate");
				String date = Base.dateFormat(createDate.getLong("time"));
				item.remove("createDate");
				item.put("createDate", date);
				items.add(item);
			}
			json.put("items", items);
		} catch (DataAccessException | IOException e) {
			json.put("code", 500);
			json.put("error", e.getMessage());
		}
		out.println(json);
	}
	
	public String execute() {
		
		return SUCCESS;
	}

}
