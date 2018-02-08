package com.born.fcs.pm.test.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.bpm.BpmCallBackOpTypeEnum;
import com.born.fcs.pm.ws.order.common.BpmCallBackOrder;
import com.born.fcs.pm.ws.service.common.FormService;

public class OfflineBpmCallbackTest extends SeviceTestBase {
	
	@Autowired
	FormService formService;
	
	@Test
	public void test() {
		
		long text = System.currentTimeMillis();
		System.out.println("====================" + text);
		
		BpmCallBackOrder order = new BpmCallBackOrder();
		order.setFormId(9612);
		order.setOpType(BpmCallBackOpTypeEnum.OTHER);
		order.setNodeId("UserTask13");
		formService.bpmCallBackProcess(order);
		
		System.out.println("====================" + (text - System.currentTimeMillis()));
	}
}
