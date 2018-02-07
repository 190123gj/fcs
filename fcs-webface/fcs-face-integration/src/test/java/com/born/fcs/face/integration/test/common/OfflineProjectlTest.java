package com.born.fcs.face.integration.test.common;

import java.rmi.RemoteException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.test.SeviceTestBase;
import com.yjf.common.lang.util.DateUtil;

public class OfflineProjectlTest extends SeviceTestBase {
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	@Test
	public void testQueryProject() {
		
		//workflowEngineWebClient.getProcessOpinionByActInstId("20000000340412");
		TaskSearchOrder taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName("qiyue");
		System.out.println(workflowEngineWebClient.getTaskTypeView(taskSearchOrder).getPageList());
	}
	
	@Test
	public void testQueryProject1() {
		
		//workflowEngineWebClient.getProcessOpinionByActInstId("20000000340412");
		TaskSearchOrder taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName("qiyue");
		taskSearchOrder.setProcessName("客户评级审批流程");//业务部负责人;;//合同审批流程//weixuanyi
		taskSearchOrder.setTaskNodeName("客户经理提交");
		System.out
			.println(workflowEngineWebClient.getTasksByAccount(taskSearchOrder).getPageList());
	}
	
	private final String tableId = "";
	
	private void aa(JSONObject jsonObject, String tableId, boolean needPage, int pageSize) {
		//		this.tableId = tableId;
		//		
		//		//this.paramEncoder = new ParamEncoder(tableId);
		//		String tableIdCode = "d-636069-";
		//		try {
		//			// 排序字段
		//			String orderField = jsonObject.get(tableIdCode + "s") != null ? jsonObject
		//				.getString(tableIdCode + "s") : "";
		//			String orderSeqNum = jsonObject.get(tableIdCode + "o") != null ? jsonObject
		//				.getString(tableIdCode + "o") : "";
		//			String orderSeq = "desc";
		//			
		//			if (needPage) {
		//				int page = jsonObject.get(tableIdCode + "p") != null ? jsonObject
		//					.getIntValue(tableIdCode + "p") : 1;
		//				if (page <= 0)
		//					page = 1;
		//				int size = jsonObject.get(tableIdCode + "z") != null ? jsonObject
		//					.getIntValue(tableIdCode + "z") : 0;
		//				if (size > 0)
		//					pageSize = size;
		//				int oldPageSize = jsonObject.get(tableIdCode + "oz") != null ? jsonObject
		//					.getIntValue(tableIdCode + "oz") : 11;
		//				//				if (pageSize != oldPageSize) {
		//				//					int first = PageUtils.getFirstNumber(page, oldPageSize);
		//				//					page = first / pageSize + 1;
		//				//				}
		//				//				this.pageBean = new PageBean(page, pageSize);
		//			}
		//			
		//		} catch (Exception ex) {
		//			ex.printStackTrace();
		//			logger.error(ex.getMessage());
		//		}
	}
	
	public static void main(String[] args) {
		//		JSONObject queryJson = new JSONObject();
		//		queryJson.put("d-636069-p", 2);
		//		queryJson.put("d-636069-z", 5);
		//		queryJson.put("d-636069-oz", 5);
		//		OfflineProjectlTest test = new OfflineProjectlTest();
		//		//test.aa(queryJson, "processRunItem", true, 11);
		//		
		//		ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
		//			"http://127.0.0.1:29440/bornbpm/service/ProcessService");
		//		
		//		java.lang.String ss = null;
		//		try {
		//			ss = processServiceProxy.getAlreadyMattersList("1", queryJson.toJSONString());
		//		} catch (RemoteException e) {
		//			e.printStackTrace();
		//		}
		//		System.out.println(ss);
		
		//		com.born.bpm.service.client.user.UserDetailsServiceProxy detailsServiceProxy = new com.born.bpm.service.client.user.UserDetailsServiceProxy(
		//			"http://127.0.0.1:29440/bornbpm/service/UserDetailsService");
		//		try {
		//			String str = detailsServiceProxy.loadOrgByName("%");
		//			System.out.println(str);
		//		} catch (RemoteException e) {
		//			
		//		}
		
		//		com.born.bpm.service.client.flow.ProcessServiceProxy proxy = new ProcessServiceProxy(
		//			"http://127.0.0.1:29440/bornbpm/service/ProcessService");
		//		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\"" + "qiyue"
		//						+ "\" taskNodeName=\"" + "" + "\" subject=\"" + "" + "\" processName=\"\""
		//						+ "></req>";
		//		System.out.println("获取代办任务发送xml:" + xml);
		//		try {
		//			String str = proxy.getTasksByAccountGroupByProcessName(xml);
		//			System.out.println(str);
		//		} catch (RemoteException e) {
		//			
		//		}
		UserDetailsServiceProxy detailsServiceProxy = new UserDetailsServiceProxy(
			"http://192.168.1.103:29440/bornbpm/service/UserDetailsService");
		String str = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("loginName", "wanxing");
			jsonObject.put("pageNumber", "1");
			jsonObject.put("pageSize", "15");
			jsonObject.put("startLoginDate", DateUtil.simpleFormat(new Date(1, 1, 1)));
			jsonObject.put("endLoginDate", DateUtil.simpleFormat(DateUtil.getMaxDate()));
			str = detailsServiceProxy.searchLoginLog(jsonObject.toJSONString());
			JSONObject result = (JSONObject) new JSONParser().parse(str);
			System.out.println(result.toJSONString());
		} catch (RemoteException e) {
			System.out.println(e);
		} catch (ParseException e) {
			System.out.println(e);
		}
		System.out.println(str);
	}
}
