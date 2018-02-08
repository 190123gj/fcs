package com.born.fcs.pm.test.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.integration.bpm.WorkflowEngineClient;
import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;

public class OfflineProjectlTest extends SeviceTestBase {
	
	@Autowired
	ProjectService projectService;
	@Autowired
	WorkflowEngineClient workflowEngineClient;
	@Autowired
	FormService formService;
	
	@Test
	public void testQueryProject() {
		
		ProjectQueryOrder order = new ProjectQueryOrder();
		List<ProjectStatusEnum> status = Lists.newArrayList();
		//status.add(ProjectStatusEnum.SET_UP);
		order.setStatusList(status);
		QueryBaseBatchResult<ProjectInfo> result = projectService.queryProject(order);
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testAA() {
		
		long text = System.currentTimeMillis();
		System.out.println("====================" + text);
		WorkflowStartOrder startOrder = new WorkflowStartOrder();
		startOrder.setFormCodeEnum(FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED);
		FormInfo formInfo = formService.findByFormId(211);
		
		SimpleFormOrder formOrder = new SimpleFormOrder();
		formOrder.setFormId(formInfo.getFormId());
		formOrder.setFormCode(FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED);
		//		formOrder.setUserAccount("cq-05");
		//		formOrder.setUserId(10000000520042l);
		formOrder.setUserAccount("admin");
		formOrder.setUserId(1l);
		formOrder.setUserName("业经1");
		formService.submit(formOrder);
		
		System.out.println("====================" + (text - System.currentTimeMillis()));
	}
	
	@Test
	public void testBB() {
		
		long text = System.currentTimeMillis();
		System.out.println("====================" + text);
		WorkflowStartOrder startOrder = new WorkflowStartOrder();
		startOrder.setFormCodeEnum(FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED);
		FormInfo formInfo = formService.findByFormId(127);
		
		SimpleFormAuditOrder formOrder = new SimpleFormAuditOrder();
		formOrder.setFormId(formInfo.getFormId());
		formOrder.setFormCode(FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED);
		formOrder.setUserAccount("cq-02");
		formOrder.setUserId(10000000520029l);
		formOrder.setUserName("付总业务");
		formOrder.setTaskId(10000000710111l);
		formOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_AGREE));
		formOrder.setVoteContent("test");
		
		formService.auditProcess(formOrder);
		
		System.out.println("====================" + (text - System.currentTimeMillis()));
	}
}
