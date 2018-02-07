package com.born.fcs.face.web.controller.login;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class TestOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8558837148177509803L;
	
	private String code;
	
	private String name;
	
	List<TestOrder1> testOrder1;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<TestOrder1> getTestOrder1() {
		return testOrder1;
	}
	
	public void setTestOrder1(List<TestOrder1> testOrder1) {
		this.testOrder1 = testOrder1;
	}
	
}
