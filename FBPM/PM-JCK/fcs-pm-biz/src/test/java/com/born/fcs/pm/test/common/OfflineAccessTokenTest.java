package com.born.fcs.pm.test.common;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;

public class OfflineAccessTokenTest extends SeviceTestBase {
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Test
	public void testQuery() {
		ProjectRelatedUserQueryOrder order = new ProjectRelatedUserQueryOrder();
		QueryBaseBatchResult<ProjectRelatedUserInfo> result = projectRelatedUserService
			.queryPage(order);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void test() {
		
		long text = System.currentTimeMillis();
		System.out.println("====================" + text);
		ProjectRelatedUserOrder order = new ProjectRelatedUserOrder();
		//		order.setUserId(10000000520042l);
		//		order.setUserAccount("cq-05");
		//		order.setUserName("业经1");
		order.setUserId(10000000780003l);
		order.setUserAccount("cq-12");
		order.setUserName("叶经二");
		order.setUserType(ProjectRelatedUserTypeEnum.MANUAL_GRANT);
		order.setProjectCode("test1");
		order.setRemark("test");
		
		projectRelatedUserService.setRelatedUser(order);
		
		System.out.println("====================" + (text - System.currentTimeMillis()));
	}
}
