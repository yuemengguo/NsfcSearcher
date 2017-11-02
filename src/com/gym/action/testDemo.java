package com.gym.action;

import com.opensymphony.xwork2.ActionSupport;

public class testDemo extends ActionSupport{
	
	private static final long serialVersionUID = -1897666459714278752L;

	public String execute() throws Exception{
		System.out.println("ok");
		return SUCCESS;
	}
}	
