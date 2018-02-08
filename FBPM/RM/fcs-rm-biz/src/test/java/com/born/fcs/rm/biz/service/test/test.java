package com.born.fcs.rm.biz.service.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.fcs.rm.ws.info.test.TestInfo;
import com.born.fcs.rm.ws.order.test.TestOrder;
import com.born.fcs.rm.ws.order.test.TestQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.service.TestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 
 * @author heh
 * 
 */
public class test extends SeviceTestBase {
	
	@Autowired
	TestService testService;
	

	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testSaveTest() {
		FcsBaseResult result = null;
		TestOrder order=new TestOrder();
		order.setTestId(2);
		order.setTestKey("testKey2");
		order.setTestValue("testValue2");
		result=testService.save(order);
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQuery() {
		
		TestQueryOrder order = new TestQueryOrder();
		order.setPageSize(5);
		QueryBaseBatchResult<TestInfo> result = testService
			.query(order);
		Assert.assertEquals(true, result.isSuccess());
	}
	

	
	@Test
	public void testQueryById() {

		TestInfo info = testService.findById(1);
		
		Assert.assertEquals(true, info.getTestId() > 0);
	}

	@Test
	public void testDelete() {
		TestOrder order=new TestOrder();
		order.setTestId(1);
		FcsBaseResult result = testService.deleteById(order);

		Assert.assertEquals(true, result.isSuccess());
	}


	
}
