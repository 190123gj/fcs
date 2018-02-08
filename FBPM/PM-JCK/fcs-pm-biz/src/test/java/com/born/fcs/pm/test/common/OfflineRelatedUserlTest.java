package com.born.fcs.pm.test.common;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.common.RelatedUserService;

public class OfflineRelatedUserlTest extends SeviceTestBase {
	
	@Autowired
	RelatedUserService relatedUserService;
	
	@Test
	public void test() {
		
		long text = System.currentTimeMillis();
		System.out.println("====================" + text);
		
		List<SimpleUserInfo> userList = relatedUserService.getBusiDirector("2016-YWB-111-009");
		for (SimpleUserInfo user : userList) {
			System.out.println(user);
		}
		
		System.out.println("====================" + (text - System.currentTimeMillis()));
	}
}
