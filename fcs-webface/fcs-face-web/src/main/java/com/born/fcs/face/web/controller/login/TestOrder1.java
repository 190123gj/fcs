package com.born.fcs.face.web.controller.login;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class TestOrder1 extends ValidateOrderBase {
	
	private static final long serialVersionUID = 891230417816730582L;
	
	private String code1;
	
	private String name1;
	
	List<TestOrder2> testOrder2;
	
	public String getCode1() {
		return code1;
	}
	
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	
	public String getName1() {
		return name1;
	}
	
	public void setName1(String name1) {
		this.name1 = name1;
	}
	
	public List<TestOrder2> getTestOrder2() {
		return testOrder2;
	}
	
	public void setTestOrder2(List<TestOrder2> testOrder2) {
		this.testOrder2 = testOrder2;
	}
	
}
