package com.gym.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.gym.commom.Base;
import com.gym.dataService.dataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import sun.awt.AppContext;


public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = -8677115563622800674L;

	private String email;
	private String password;
	private String nickName;
	private PrintWriter out;	

	private long endTime;
	private String redirect = "";
	
	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 登陆请求
	 */
	public void doLogin() {
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
			json.put("error", true);
			json.put("message", e1.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close(); 
		    return;
		}
		try {
			JSONObject res = dataService.toJSONObject("select * from nsfc_user where email = ? limit 1", email);
			if(res.has("id")) {
				String pass = res.getString("password");
				MessageDigest md = MessageDigest.getInstance("MD5");
		        // 计算md5函数
		        md.update(password.getBytes());
		        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
		        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		        password = new BigInteger(1, md.digest()).toString(16);
				if(pass.equals(password)) {
					String status = res.getString("status");
					if(status.equals("0")) {
						json.put("code", 203);
						json.put("message", "该账户邮箱未验证");
					}else {
						//session保存在线用户
						ActionContext.getContext().getSession().put("userId", res.getString("id"));
						if(redirect == "") {
							json.put("code", 200);
						}else {
							json.put("code", 301);
							json.put("url", redirect);
						}
					}
				}else {
					json.put("code", 201);
					json.put("message", "密码错误");
				}
			}else {
				json.put("code", 202);
				json.put("message", "用户名不存在");
			}
		} catch (Exception e) {
			json.put("code", 500);
			json.put("message", e.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close();  
		    return;
		} 			
	    out.println(json);  
	    out.flush();  
	    out.close();  
	}
	
	public void doLogout() {
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
			json.put("code", 200);
		} catch (IOException e1) {
			json.put("code", 500);
			json.put("error", true);
			json.put("message", e1.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close(); 
		    return;
		}
		ActionContext.getContext().getSession().clear();
		
	    out.println(json);  
	    out.flush();  
	    out.close();  
	}
	
	
	/**
	 * 注册处理
	 * @return
	 */
	public String register() {
		return SUCCESS;
	}
	
	/**
	 * 用户注册
	 */
	public void doRegister() {
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
		try {
			//先查用户是否存在
			JSONObject count = dataService.toJSONObject("select id from nsfc_user where email = ? ",email);
			if(count.size() > 0) {
				json.put("code", 205);//注册失败
				json.put("message", "您输入的："+email+" 邮箱已经被注册");
				json.put("email", email);
			}else {
				//处理用户注册信息
				nickName = email.split("@")[0] + "_m";//以邮箱名字加下划线+m的形式命名昵称
				
				MessageDigest md = MessageDigest.getInstance("MD5");
		        // 计算md5函数
		        md.update(password.getBytes());
		        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
		        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		        password = new BigInteger(1, md.digest()).toString(16);
				
				int res = dataService.getTemplate().update("insert into nsfc_user set email= ?, nickName= ? ,password=? ",email,nickName,password);
				if(res == 0) {
					json.put("code", 204);//注册失败
					json.put("message", "注册失败");
					json.put("email", email);
					//发送邮箱验证
				}else {
					json.put("code", 200);
					json.put("email", email);
				}
			}
		} catch (Exception e) {
			json.put("code", 500);
			json.put("message", e.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close();  
		    return;
		} 			
	    out.println(json);  
	    out.flush();  
	    out.close(); 
	}
	
	public void doForgetCheck() {
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
		try {
			//先查用户是否存在
			JSONObject count = dataService.toJSONObject("select id from nsfc_user where email = ? ",email);
			if(count.size() == 0) {
				json.put("code", 208);//注册失败
				json.put("message", "您输入的："+email+" 用户不存在");
			}else {
				//处理验证码信息
				@SuppressWarnings("unused")
				int checkCode = (int)(Math.random()*8999) + 1000;
				//发送邮箱验证码 省略。。。。
				
				
				//发送邮箱验证码 省略。。。。
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 500);
			json.put("message", e.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close();  
		    return;
		} 			
	    out.println(json);  
	    out.flush();  
	    out.close(); 
	}
	
	/**
	 * 处理密码重置
	 */
	public void doForget() {
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
		try {
			//检查验证码 省略。。。。
			//code:209验证码错误
			
			//检查验证码 省略。。。。
			MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(password.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        password = new BigInteger(1, md.digest()).toString(16);
			
			int res = dataService.getTemplate().update("update nsfc_user set password=? where email = ? ",password,email);
			if(res == 0) {
				json.put("code", 207);
				json.put("message", "修改失败");
				//发送邮箱验证
			}else {
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 500);
			json.put("message", e.getMessage());
		    out.println(json);  
		    out.flush();  
		    out.close();  
		    return;
		} 			
	    out.println(json);  
	    out.flush();  
	    out.close(); 
	}
	
	public String forget() {
		return SUCCESS;
	}
	public String userInfo() {
		
		return SUCCESS;
	}
	
	/**
	 * 发送邮箱验证
	 */
	public void  sendCheckEmal() {
		
	}
	
	/*
	 * 验证邮箱
	 */
	public void checkEmail() {
		
	
	}
	
	public String execute() {
		//检查用户是否在线，如果不在线，请求登陆，在线，定向主页
		JSONObject userCheck = Base.checkPassport();
		if(userCheck.getInt("onlineStatus") == 1) {
			return "index";
		}else {
			return SUCCESS;	
		}
	}
	
	/**
	 * 发用邮箱验证码
	 * @return
	 */
	@SuppressWarnings("unused")
	private JSONObject sendEmail() {
		JSONObject json = new JSONObject();
		endTime = System.currentTimeMillis() + 1800000;
		String strContent = email+"|"+endTime;
		try {
			String content = Base.encryptBASE64(strContent.getBytes());
			//暂时未实现
			
		} catch (Exception e) {
			json.put("err", e.getMessage());
			return json;
		}
		return json;
	}
}
