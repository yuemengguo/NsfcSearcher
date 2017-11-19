package com.gym.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.gym.commom.Base;
import com.gym.dataService.dataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;


public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = -8677115563622800674L;

	private String email;
	private String password;
	private String nickName;
	private PrintWriter out;	
	private String id;
	private JSONObject errorMessage;
	private long endTime;
	private String redirect = "";
	private String info;
	private int confirm;
	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
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
					
				}else {
					//发送邮箱验证
					
					JSONObject userInfo = dataService.toJSONObject("select id from nsfc_user where email = ?",email);  
					JSONObject sendStatus = sendCheckEmal(email,userInfo.getString("id"));
					String err = sendStatus.optString("err",null);
					if(err == null) {
						json.put("code", 200);
						json.put("email", email);
					}else {
						json.put("code", 500);
						json.put("message", err);
					    out.println(json);  
					    out.flush();  
					    out.close();  
					    return;
					}

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
				int checkCode = (int)(Math.random()*8999) + 1000;
				//存储验证码：
				ActionContext.getContext().getSession().put("checkCode", checkCode);
				JSONObject sendStatus = sendEmailCode(checkCode);
				String err = sendStatus.optString("err",null);
				if(err == null) {
					json.put("code", 200);
				}else {
					json.put("code", 500);
					json.put("message", err);
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
			//code:209验证码错误
			int checkCode = (int) ActionContext.getContext().getSession().get("checkCode");
			if(checkCode != confirm) {
				json.put("code", 209);
				json.put("message", "验证码错误");
			    out.println(json);  
			    out.flush();  
			    out.close();  
			    return;
			}
			//清空验证码
			ActionContext.getContext().getSession().remove("checkCode");
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
			}else {
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 500);
			json.put("message", e.getMessage()== null ? "服务器错误":e.getMessage());
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
	 * 发送注册邮箱验证
	 * @return 
	 */
	public JSONObject  sendCheckEmal(String toUser, String id) {
		JSONObject json = new JSONObject();
		endTime = System.currentTimeMillis() + 1800000;
		String strContent = email+"|"+endTime;
		try {
			String info = Base.encryptBASE64(strContent.getBytes());
			String url = "http://localhost:8080/NsfcSearcher/checkEmail.action?id="+id+"&info="+info;
			String content = "请点击链接，进行验证<br/>"+url;
			json = sendSimpleMail(toUser,content,"邮箱验证");
		} catch (Exception e) {
			json.put("message", e.getMessage());
			json.put("code", "500");
			return json;
		}
		return json;
	}
	
	/*
	 * 验证注册邮箱
	 */
	public String checkEmail() { 
		try {
			//目前存在bug
			
//			String endTimeStr = Base.decryptBASE64(info).toString().split("|")[1];
//			long endTime = Long.parseLong(endTimeStr);
//			if(endTime < System.currentTimeMillis()) {
//				errorMessage.put("errorMessage", "链接失效");
//				errorMessage.put("code", "500");
//				return ERROR;
//			}
			//目前存在bug
			
			int count = dataService.getTemplate().update("update nsfc_user set status =1 where id = ?",id);
			if(count > 0) {
				return SUCCESS;
			}else {
				errorMessage.put("errorMessage", "验证失败");
				errorMessage.put("code", "500");
				return ERROR;
			}
		} catch (Exception e) {
			errorMessage.put("errorMessage", e.getMessage());
			errorMessage.put("code", "500");
			return ERROR;
		}

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
	private JSONObject sendEmailCode(int checkCode) {
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateTime = sdf.format(new Date());
		try {
	        String contentText = "尊敬的用户:<br/>    您好！<br/>    您于{1}在NSFC系统成功提交了邮箱发送验证码的请求，验证码为：{2}为了保证您的账号安全，该验证码有效期为30分钟。若不是您本人操作，请忽略此邮件或拨打咨询热线：17621096799。感谢您使用！本邮件由系统自动发出，请勿回复。<br/>【NSFC开发团队】"; 
	        //邮件的文本内容
	        contentText = contentText.replace("{1}",dateTime);
	        contentText = contentText.replace("{2}", String.valueOf(checkCode));
			json = sendSimpleMail(email,contentText,"邮箱验证码");
		} catch (Exception e) {
			json.put("err", e.getMessage());
			return json;
		}
		return json;
	}
	
	private JSONObject sendSimpleMail(String user,String content,String subject) {
		JSONObject json = new JSONObject();
		Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts;
		try {
			ts = session.getTransport();

        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
	        ts.connect("smtp.163.com", "guoyuemeng1022", "guo941102");
	        //4、创建邮件
	        Message message = createSimpleMail(session,user,content,subject);
	        //5、发送邮件
	        ts.sendMessage(message, message.getAllRecipients());
	        ts.close();
		} catch (Exception e) {
			json.put("err", e.getMessage());
			return json;
		}
		return json;
	}
	
	 private MimeMessage createSimpleMail(Session session,String user,String content,String subject)
	            throws Exception {
	        //创建邮件对象
	        MimeMessage message = new MimeMessage(session);
	        //指明邮件的发件人
	        message.setFrom(new InternetAddress("guoyuemeng1022@163.com"));
	        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user));
	        //邮件的标题
	        message.setSubject(subject);

	        message.setContent(content, "text/html;charset=UTF-8");
	        //返回创建好的邮件对象
	        return message;
	    }
}
