package com.gym.action;

import com.gym.commom.Base;
import com.opensymphony.xwork2.ActionSupport;

public class testDemo extends ActionSupport{
	
	public static void main(String[] args) throws Exception {
		String str = "sdfsdfsdfsdf";
		String ii = Base.encryptBASE64("str".getBytes());
		System.out.println(ii);
//
	}
}	
