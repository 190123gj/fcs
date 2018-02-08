package com.born.fcs.pm.biz.service.common;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.OaTaskRelationDAO;
import com.born.fcs.pm.dal.daointerface.UserExtraMessageDAO;
import com.born.fcs.pm.dal.dataobject.OaTaskRelationDO;
import com.born.fcs.pm.dal.dataobject.UserExtraMessageDO;
import com.born.fcs.pm.integration.oa.client.WorkflowServicePortTypeProxy;
import com.born.fcs.pm.integration.oa.client.info.WorkflowBaseInfo;
import com.born.fcs.pm.integration.oa.client.info.WorkflowDetailTableInfo;
import com.born.fcs.pm.integration.oa.client.info.WorkflowMainTableInfo;
import com.born.fcs.pm.integration.oa.client.info.WorkflowRequestInfo;
import com.born.fcs.pm.integration.oa.client.info.WorkflowRequestLog;
import com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableField;
import com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableRecord;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.FormMessageVarEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.NotifyOaService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("notifyOaService")
public class NotifyOaServiceImpl extends BaseAutowiredDomainService implements NotifyOaService {
	
	@Autowired
	ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	UserExtraMessageDAO userExtraMessageDAO;
	
	@Autowired
	OaTaskRelationDAO oaTaskRelationDAO;
	
	@Autowired
	SysWebAccessTokenService sysWebAccessTokenService;
	
	@Value("${fcs.oa.webservice.address}")
	String oaWebServiceEndpoint;
	
	@Value("${fcs.oa.workflow.id}")
	String workFlowId;
	
	@Value("${fcs.oa.workflow.name}")
	String workFlowName;
	
	@Value("${fcs.oa.workflow.typeId}")
	String workFlowTypeId;
	
	@Value("${fcs.oa.workflow.tableDbName}")
	String workFlowTableDbName;
	
	WorkflowBaseInfo workflowBaseInfo = null;//工作流信息
	
	static String url = "http://192.168.70.71:8099/services/WorkflowService";
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	private String getAccessToken(UserExtraMessageDO user) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(user, tokenOrder);
		tokenOrder.setRemark("OA访问链接");
		tokenOrder.setId(0);
		FcsBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	private WorkflowBaseInfo initWorkFlowBaseInfo() {
		if (workflowBaseInfo == null)
			workflowBaseInfo = new WorkflowBaseInfo();
		workflowBaseInfo.setWorkflowId(workFlowId);//流程ID
		workflowBaseInfo.setWorkflowName(workFlowName);//流程名称
		workflowBaseInfo.setWorkflowTypeId(workFlowTypeId);//流程类型
		return workflowBaseInfo;
	}
	
	private FcsBaseResult createOaWorkflow(FormInfo formInfo, String url, int oaUserId,
											String message) {
		
		logger.info("创建OA流程开始 oaUserId:{}", oaUserId);
		
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			if (0 > oaUserId) {
				
				result.setSuccess(false);
				result.setMessage("用户OA系统ID尚未配置");
				
			} else {
				
				WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy(
					oaWebServiceEndpoint);
				
				WorkflowRequestInfo requestInfo = new WorkflowRequestInfo();
				//			requestInfo.setRequestId(String.valueOf(1918557));//流程请求ID-创建流程时自动产生 不需要传此项
				requestInfo.setCanView(true);//显示
				requestInfo.setCanEdit(true);//可编辑
				requestInfo.setRequestName(message);//请求标题
				requestInfo.setRequestLevel("0");//请求重要级别 0：正常 1：重要 2：紧急
				requestInfo.setCreatorId(oaUserId + "");//创建者ID 创建流程时为必输项
				
				requestInfo.setWorkflowBaseInfo(initWorkFlowBaseInfo());//工作流信息
				
				WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();
				//workflowMainTableInfo.setTableDBName(workFlowTableDbName);
				
				WorkflowRequestTableRecord[] tableRecord = new WorkflowRequestTableRecord[1];
				WorkflowRequestTableField[] workflowRequestTableFields = new WorkflowRequestTableField[5];
				
				//创建人
				WorkflowRequestTableField reater = new WorkflowRequestTableField();
				reater.setFieldName("creater");
				reater.setFieldValue("" + oaUserId);
				reater.setView(true);
				reater.setEdit(true);
				workflowRequestTableFields[0] = reater;
				
				//创建时间
				WorkflowRequestTableField ateInfo = new WorkflowRequestTableField();
				ateInfo.setFieldName("dateInfo");
				ateInfo.setFieldValue(DateUtil.dtSimpleFormat(formInfo.getSubmitTime()));
				ateInfo.setView(true);
				ateInfo.setEdit(true);
				workflowRequestTableFields[1] = ateInfo;
				//代办事宜名称
				WorkflowRequestTableField rkflowname = new WorkflowRequestTableField();
				rkflowname.setFieldName("rkflowname");
				
				if (formInfo.getStatus() == FormStatusEnum.BACK) {
					rkflowname.setFieldValue(formInfo.getFormName() + "-被驳回");
				} else {
					rkflowname.setFieldValue(formInfo.getFormName()
												+ "-"
												+ formInfo.getFormExecuteInfo().get(0)
													.getTaskName());
				}
				rkflowname.setView(true);
				rkflowname.setEdit(true);
				workflowRequestTableFields[2] = rkflowname;
				//待办人
				WorkflowRequestTableField resourceid = new WorkflowRequestTableField();
				
				resourceid.setFieldName("resourceid");
				resourceid.setFieldValue(oaUserId + "");
				resourceid.setView(true);
				resourceid.setEdit(true);
				workflowRequestTableFields[3] = resourceid;
				
				//处理地址
				WorkflowRequestTableField furl = new WorkflowRequestTableField();
				furl.setFieldName("url");
				//				furl.setFieldValue("<a href=\"javascript:window.open('" + url
				//									+ "','name')\"><span>处理</span></a>");
				//				
				//				furl.setFieldValue("<a href=\"" + url + "\"><span>处理</span></a>");
				furl.setFieldValue("<a href=\"javascript:void(0);\" onclick=\"javascript:top.location.href='"
									+ url + "';\">查看</a>");
				//				
				//				furl.setFieldValue("<a href=\"javascript:window.open('" + url
				//														+ "','name')\"><span>处理</span></a>");
				
				logger.info(furl.getFieldValue());
				furl.setView(true);
				furl.setEdit(true);
				workflowRequestTableFields[4] = furl;
				
				tableRecord[0] = new WorkflowRequestTableRecord();
				tableRecord[0].setWorkflowRequestTableFields(workflowRequestTableFields);
				
				workflowMainTableInfo.setRequestRecords(tableRecord);
				
				requestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
				
				//			requestid：流程请求id
				//			报错类别：
				//			-1：创建流程失败
				//			-2：没有创建权限
				//			-3：创建流程失败
				//			-4：字段或表名不正确
				//			-5：更新流程级别失败
				//			-6：无法创建流程待办任务
				//			-7：流程下一节点出错，请检查流程的配置，在OA中发起流程进行测试
				//			-8：流程节点自动赋值操作错误
				logger.info("发起OA流程开始：{}", requestInfo);
				String response = proxy.doCreateWorkflowRequest(requestInfo, oaUserId);
				logger.info("创建OA流程结果：{}", response);
				
				if (StringUtil.isNotBlank(response) && !response.contains("-")) {
					String requestId = response.trim();
					result.setSuccess(true);
					result.setMessage("创建OA流程成功:" + response);
					result.setKeyId(NumberUtil.parseLong(requestId));
				} else {
					result.setSuccess(false);
					result.setMessage("创建OA流程失败:" + response);
				}
			}
		} catch (Exception e) {
			logger.error("创建OA流程出错{}", e);
			
			result.setSuccess(false);
			result.setMessage("创建OA流程出错");
		}
		return result;
	}
	
	public static void main(String[] args) {
		//doaction();
		
		//		WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy(
		//			"http://192.168.70.71:8099/services/WorkflowService");
		//		WorkflowRequestInfo requestInfo = getRequestInfo(requestid, userid);
		//		requestInfo.setRequestId(String.valueOf(458));//流程请求ID-创建流程时自动产生 不需要传此项
		//		requestInfo.setCanView(true);//显示
		//		requestInfo.setCanEdit(true);//可编辑
		//		requestInfo.setRequestLevel("0");//请求重要级别 0：正常 1：重要 2：紧急
		//		//requestInfo.setCreatorId(42 + "");//创建者ID 创建流程时为必输项
		//		
		//		WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
		//		workflowBaseInfo.setWorkflowId("130");//流程ID
		//		workflowBaseInfo.setWorkflowName("业务系统待办提醒");//流程名称
		//		
		//		workflowBaseInfo.setWorkflowTypeId("1");//流程类型
		//		
		//		requestInfo.setWorkflowBaseInfo(workflowBaseInfo);
		//		//提交结果：error 请求出错 ，failed 失败，success 成功
		//		String response;
		//		try {
		//			response = proxy.submitWorkflowRequest(requestInfo, 458, 0, "submit", "提交流程");
		//			System.out.println("提交OA流程结果：{}" + response);
		//		} catch (RemoteException e) {
		//			e.printStackTrace();
		//		}
		
		//return;
		
		NotifyOaServiceImpl notifyservice = new NotifyOaServiceImpl();
		
		try {
			
			notifyservice.createRequest();
			return;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int oaUserId = 42;
		System.out.println("创建OA流程开始 oaUserId:{}" + 42);
		
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			if (0 > oaUserId) {
				
				result.setSuccess(false);
				result.setMessage("用户OA系统ID尚未配置");
				
			} else {
				
				WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy(
					"http://192.168.70.71:8099/services/WorkflowService");
				
				WorkflowRequestInfo requestInfo = new WorkflowRequestInfo();
				//			requestInfo.setRequestId(String.valueOf(1918557));//流程请求ID-创建流程时自动产生 不需要传此项
				requestInfo.setCanView(true);//显示
				requestInfo.setCanEdit(true);//可编辑
				requestInfo
					.setRequestName("金融科技部马骏飞于2018-02-02 14:07提交了单据号：CLF000009-差旅费报销单流程-马骏飞|2018-02-02 14:07，请及时登录http://www.cguarantee.com:8205处理。");//请求标题
				requestInfo.setRequestLevel("0");//请求重要级别 0：正常 1：重要 2：紧急
				requestInfo.setCreatorId("" + 42);//创建者ID 创建流程时为必输项
				
				WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
				workflowBaseInfo.setWorkflowId("130");//流程ID
				workflowBaseInfo.setWorkflowName("业务系统待办提醒");//流程名称
				
				workflowBaseInfo.setWorkflowTypeId("1");//流程类型
				
				requestInfo.setWorkflowBaseInfo(workflowBaseInfo);//工作流信息formtable_main_87
				
				WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();
				//				workflowMainTableInfo.setTableDBName("formtable_main_87");
				//				
				WorkflowRequestTableRecord[] tableRecord = new WorkflowRequestTableRecord[1];
				WorkflowRequestTableField[] workflowRequestTableFields = new WorkflowRequestTableField[6];
				
				//创建人
				WorkflowRequestTableField reater = new WorkflowRequestTableField();
				reater.setFieldName("creater");
				reater.setFieldValue("95");
				reater.setView(true);
				reater.setEdit(true);
				workflowRequestTableFields[0] = reater;
				//				
				//				//创建时间
				WorkflowRequestTableField ateInfo = new WorkflowRequestTableField();
				ateInfo.setFieldName("dateInfo");
				ateInfo.setFieldValue(DateUtil.dtSimpleFormat(new Date()));
				ateInfo.setView(true);
				ateInfo.setEdit(true);
				workflowRequestTableFields[1] = ateInfo;
				//				//代办事宜名称
				WorkflowRequestTableField rkflowname = new WorkflowRequestTableField();
				rkflowname.setFieldName("rkflowname");
				rkflowname.setFieldValue("单据号：CLF000009-差旅费报销单流程-马骏飞|2018-02-02 14:07-部门经理审批");
				rkflowname.setView(true);
				rkflowname.setEdit(true);
				workflowRequestTableFields[2] = rkflowname;
				//				//待办人
				WorkflowRequestTableField resourceid = new WorkflowRequestTableField();
				
				resourceid.setFieldName("resourceid");
				resourceid.setFieldValue(42 + "");
				resourceid.setView(true);
				resourceid.setEdit(true);
				workflowRequestTableFields[3] = resourceid;
				//				
				//				//处理地址
				WorkflowRequestTableField furl = new WorkflowRequestTableField();
				furl.setFieldName("url");
				furl.setFieldValue("http://www.cguarantee.com:8205/fundMg/index.htm?systemNameDefautUrl=/fundMg/travelExpense/audit.htm&formId=11&taskId=20000015671296&accessToken=8c24fb17-616c-42d4-b126-c3f3cc76bcb7");
				furl.setView(true);
				furl.setEdit(true);
				workflowRequestTableFields[4] = furl;
				
				tableRecord[0] = new WorkflowRequestTableRecord();
				tableRecord[0].setWorkflowRequestTableFields(workflowRequestTableFields);
				//				
				workflowMainTableInfo.setRequestRecords(tableRecord);
				//				
				requestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
				
				//			requestid：流程请求id
				//			报错类别：
				//			-1：创建流程失败
				//			-2：没有创建权限
				//			-3：创建流程失败
				//			-4：字段或表名不正确
				//			-5：更新流程级别失败
				//			-6：无法创建流程待办任务
				//			-7：流程下一节点出错，请检查流程的配置，在OA中发起流程进行测试
				//			-8：流程节点自动赋值操作错误
				System.out.println("requestInfo=" + requestInfo);
				String response = proxy.doCreateWorkflowRequest(requestInfo, 42);
				System.out.println(response);
				if (StringUtil.isNotBlank(response) && response.contains("requestid")) {
					String requestId = response.replaceAll("requestid：", "");
					result.setSuccess(true);
					result.setMessage("创建OA流程成功:" + response);
					result.setKeyId(NumberUtil.parseLong(requestId));
				} else {
					result.setSuccess(false);
					result.setMessage("创建OA流程失败:" + response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void doaction() {
		int requestid = 469;
		int userid = 42;
		WorkflowRequestInfo WorkflowRequestInfo = getRequestInfo(requestid, userid);
		WorkflowRequestInfo.setCanView(true);
		WorkflowRequestInfo.setCanEdit(true);
		WorkflowRequestInfo.setRequestId(String.valueOf(requestid));
		
		//没有表单数据修改 注释掉一下 代码  
		//-------开始------
		//		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[1]; //要修改字段的信息        
		//		wrti[0] = new WorkflowRequestTableField();
		//		wrti[0].setFieldName("spld");//审批领导        
		//		wrti[0].setFieldValue("4,5");//修改为4、5       
		//		wrti[0].setView(true);
		//		wrti[0].setEdit(true);
		//		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];//主字段只有一行数据        
		//		wrtri[0] = new WorkflowRequestTableRecord();
		//		wrtri[0].setWorkflowRequestTableFields(wrti);
		//		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		//		wmi.setRequestRecords(wrtri);
		//		WorkflowRequestInfo.setWorkflowMainTableInfo(wmi);//添加主字段数据        
		//----结束-----
		
		WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(
			url);
		String str;
		try {
			str = WorkflowServicePortTypeProxy.submitWorkflowRequest(WorkflowRequestInfo,
				requestid, userid, "submit", "第三方系统提交流程" + new java.util.Date());
			System.out.println(str);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static WorkflowRequestInfo getRequestInfo(int requestid, int userid) {
		WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(
			url);
		WorkflowRequestInfo WorkflowRequestInfo = null;
		try {
			WorkflowRequestInfo = WorkflowServicePortTypeProxy.getWorkflowRequest(requestid,
				userid, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}//调用接口获取对应requestid的数据        
			//流程基本信息        System.out.println("===============================流程基本(workflow_requestbase)信息================================");       
		System.out.println("getCreatorId:           " + WorkflowRequestInfo.getCreatorId());
		System.out.println("getCreatorName:         " + WorkflowRequestInfo.getCreatorName());
		System.out.println("getRequestName:         " + WorkflowRequestInfo.getRequestName());
		System.out.println("getCreateTime:          " + WorkflowRequestInfo.getCreateTime());
		System.out.println("getCurrentNodeId:       " + WorkflowRequestInfo.getCurrentNodeId());
		System.out.println("getCurrentNodeName:     " + WorkflowRequestInfo.getCurrentNodeName());
		System.out.println("getStatus:          " + WorkflowRequestInfo.getStatus());
		System.out
			.println("===============================流程workflow_base信息=============================================");
		WorkflowBaseInfo WorkflowBaseInfo = WorkflowRequestInfo.getWorkflowBaseInfo();
		System.out.println("getWorkflowId:          " + WorkflowBaseInfo.getWorkflowId());
		System.out.println("getWorkflowName:        " + WorkflowBaseInfo.getWorkflowName());
		System.out
			.println("===============================流程主字段信息====================================================");
		WorkflowMainTableInfo WorkflowMainTableInfo = WorkflowRequestInfo
			.getWorkflowMainTableInfo();
		WorkflowRequestTableRecord WorkflowRequestTableRecord[] = WorkflowMainTableInfo
			.getRequestRecords();
		if (WorkflowRequestTableRecord != null) {
			WorkflowRequestTableField WorkflowRequestTableFields[] = WorkflowRequestTableRecord[0]
				.getWorkflowRequestTableFields();
			System.out.println("字段个数：" + WorkflowRequestTableFields.length);
			for (int i = 0; i < WorkflowRequestTableFields.length; i++) {
				WorkflowRequestTableField WorkflowRequestTableField = WorkflowRequestTableFields[i];
				System.out.println("字段：" + (i + 1));
				System.out.println("getFieldName：" + WorkflowRequestTableField.getFieldName());
				System.out.println("getFieldValue：" + WorkflowRequestTableField.getFieldValue());//原始值                
				System.out.println("getFieldShowName："
									+ WorkflowRequestTableField.getFieldShowName());
			}
			System.out
				.println("===============================流程明细信息======================================================");
			WorkflowDetailTableInfo WorkflowDetailTableInfo[] = WorkflowRequestInfo
				.getWorkflowDetailTableInfos();
			if (WorkflowDetailTableInfo != null) {
				for (int i = 0; i < WorkflowDetailTableInfo.length; i++) {
					WorkflowRequestTableRecord = WorkflowDetailTableInfo[i]
						.getWorkflowRequestTableRecords();
					System.out.println("明细表" + (i + 1));
					System.out.println("明细表行数" + WorkflowRequestTableRecord.length);
					for (int j = 0; j < WorkflowRequestTableRecord.length; j++) {
						System.out.println("***********************************第" + (j + 1)
											+ "行***************************************");
						WorkflowRequestTableField workflowRequestTableFields[] = WorkflowRequestTableRecord[j]
							.getWorkflowRequestTableFields();
						for (int k = 0; k < workflowRequestTableFields.length; k++) {
							WorkflowRequestTableField WorkflowRequestTableField = workflowRequestTableFields[k];
							System.out.println("字段：" + (k + 1));
							System.out.println("getFieldName："
												+ WorkflowRequestTableField.getFieldName());
							System.out.println("getFieldValue："
												+ WorkflowRequestTableField.getFieldValue());//原始值           
							System.out.println("getFieldShowName："
												+ WorkflowRequestTableField.getFieldShowName());
						}
					}
				}
			}
			System.out
				.println("===============================签字意见======================================================");
			WorkflowRequestLog WorkflowRequestLogs[] = WorkflowRequestInfo.getWorkflowRequestLogs();
			if (WorkflowRequestLogs != null) {
				System.out.println("签字意见条数：" + WorkflowRequestLogs.length);
				for (int i = 0; i < WorkflowRequestLogs.length; i++) {
					WorkflowRequestLog WorkflowRequestLog = WorkflowRequestLogs[i];
					System.out.println("***********************************第" + (i + 1)
										+ "行***************************************");
					System.out.println("getNodeId:" + WorkflowRequestLog.getNodeId());
					System.out.println("getNodeName:" + WorkflowRequestLog.getNodeName());
					System.out.println("getOperateDate:" + WorkflowRequestLog.getOperateDate());
					System.out.println("getOperateTime:" + WorkflowRequestLog.getOperateTime());
					System.out.println("getOperateType:" + WorkflowRequestLog.getOperateType());
					System.out.println("getId:" + WorkflowRequestLog.getId());
					System.out.println("getOperatorDept:" + WorkflowRequestLog.getOperatorDept());//操作人部门名称     
					System.out.println("getOperatorName:" + WorkflowRequestLog.getOperatorName());//操作人名称  
					System.out.println("getOperatorId:" + WorkflowRequestLog.getOperatorId());//操作人id            
					System.out.println("getReceivedPersons:"
										+ WorkflowRequestLog.getReceivedPersons());
					System.out.println("getRemark:" + WorkflowRequestLog.getRemark());
				}
			}
		}
		return WorkflowRequestInfo;
	}
	
	private WorkflowRequestInfo getRequestInfoData(int requestid, int userid) {
		WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(
			oaWebServiceEndpoint);
		WorkflowRequestInfo WorkflowRequestInfo = null;
		try {
			WorkflowRequestInfo = WorkflowServicePortTypeProxy.getWorkflowRequest(requestid,
				userid, 0);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}//调用接口获取对应requestid的数据        
			//流程基本信息        System.out.println("===============================流程基本(workflow_requestbase)信息================================");       
		logger.info("getCreatorId:           " + WorkflowRequestInfo.getCreatorId());
		logger.info("getCreatorName:         " + WorkflowRequestInfo.getCreatorName());
		logger.info("getRequestName:         " + WorkflowRequestInfo.getRequestName());
		logger.info("getCreateTime:          " + WorkflowRequestInfo.getCreateTime());
		logger.info("getCurrentNodeId:       " + WorkflowRequestInfo.getCurrentNodeId());
		logger.info("getCurrentNodeName:     " + WorkflowRequestInfo.getCurrentNodeName());
		logger.info("getStatus:          " + WorkflowRequestInfo.getStatus());
		logger
			.info("===============================流程workflow_base信息=============================================");
		WorkflowBaseInfo WorkflowBaseInfo = WorkflowRequestInfo.getWorkflowBaseInfo();
		logger.info("getWorkflowId:          " + WorkflowBaseInfo.getWorkflowId());
		logger.info("getWorkflowName:        " + WorkflowBaseInfo.getWorkflowName());
		System.out
			.println("===============================流程主字段信息====================================================");
		WorkflowMainTableInfo WorkflowMainTableInfo = WorkflowRequestInfo
			.getWorkflowMainTableInfo();
		WorkflowRequestTableRecord WorkflowRequestTableRecord[] = WorkflowMainTableInfo
			.getRequestRecords();
		if (WorkflowRequestTableRecord != null) {
			WorkflowRequestTableField WorkflowRequestTableFields[] = WorkflowRequestTableRecord[0]
				.getWorkflowRequestTableFields();
			logger.info("字段个数：" + WorkflowRequestTableFields.length);
			for (int i = 0; i < WorkflowRequestTableFields.length; i++) {
				WorkflowRequestTableField WorkflowRequestTableField = WorkflowRequestTableFields[i];
				logger.info("字段：" + (i + 1));
				logger.info("getFieldName：" + WorkflowRequestTableField.getFieldName());
				logger.info("getFieldValue：" + WorkflowRequestTableField.getFieldValue());//原始值                
				logger.info("getFieldShowName：" + WorkflowRequestTableField.getFieldShowName());
			}
			logger
				.info("===============================流程明细信息======================================================");
			WorkflowDetailTableInfo WorkflowDetailTableInfo[] = WorkflowRequestInfo
				.getWorkflowDetailTableInfos();
			if (WorkflowDetailTableInfo != null) {
				for (int i = 0; i < WorkflowDetailTableInfo.length; i++) {
					WorkflowRequestTableRecord = WorkflowDetailTableInfo[i]
						.getWorkflowRequestTableRecords();
					logger.info("明细表" + (i + 1));
					logger.info("明细表行数" + WorkflowRequestTableRecord.length);
					for (int j = 0; j < WorkflowRequestTableRecord.length; j++) {
						logger.info("***********************************第" + (j + 1)
									+ "行***************************************");
						WorkflowRequestTableField workflowRequestTableFields[] = WorkflowRequestTableRecord[j]
							.getWorkflowRequestTableFields();
						for (int k = 0; k < workflowRequestTableFields.length; k++) {
							WorkflowRequestTableField WorkflowRequestTableField = workflowRequestTableFields[k];
							logger.info("字段：" + (k + 1));
							logger.info("getFieldName：" + WorkflowRequestTableField.getFieldName());
							logger.info("getFieldValue："
										+ WorkflowRequestTableField.getFieldValue());//原始值           
							logger.info("getFieldShowName："
										+ WorkflowRequestTableField.getFieldShowName());
						}
					}
				}
			}
			System.out
				.println("===============================签字意见======================================================");
			WorkflowRequestLog WorkflowRequestLogs[] = WorkflowRequestInfo.getWorkflowRequestLogs();
			if (WorkflowRequestLogs != null) {
				logger.info("签字意见条数：" + WorkflowRequestLogs.length);
				for (int i = 0; i < WorkflowRequestLogs.length; i++) {
					WorkflowRequestLog WorkflowRequestLog = WorkflowRequestLogs[i];
					logger.info("***********************************第" + (i + 1)
								+ "行***************************************");
					logger.info("getNodeId:" + WorkflowRequestLog.getNodeId());
					logger.info("getNodeName:" + WorkflowRequestLog.getNodeName());
					logger.info("getOperateDate:" + WorkflowRequestLog.getOperateDate());
					logger.info("getOperateTime:" + WorkflowRequestLog.getOperateTime());
					logger.info("getOperateType:" + WorkflowRequestLog.getOperateType());
					logger.info("getId:" + WorkflowRequestLog.getId());
					logger.info("getOperatorDept:" + WorkflowRequestLog.getOperatorDept());//操作人部门名称     
					logger.info("getOperatorName:" + WorkflowRequestLog.getOperatorName());//操作人名称  
					logger.info("getOperatorId:" + WorkflowRequestLog.getOperatorId());//操作人id            
					logger.info("getReceivedPersons:" + WorkflowRequestLog.getReceivedPersons());
					logger.info("getRemark:" + WorkflowRequestLog.getRemark());
				}
			}
		}
		return WorkflowRequestInfo;
	}
	
	private FcsBaseResult submitOaWorkflow(int oaUserId, long requestId) {
		
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			if (0 > oaUserId) {
				result.setSuccess(false);
				result.setMessage("用户OA系统ID尚未配置");
				
			} else {
				
				WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy(
					oaWebServiceEndpoint);
				WorkflowRequestInfo WorkflowRequestInfo = getRequestInfoData((int) requestId,
					oaUserId);
				if ("归档".equals(WorkflowRequestInfo.getStatus())) {
					result.setSuccess(true);
					result.setMessage("提交OA流程成功:");
					return result;
				}
				WorkflowRequestInfo.setCanView(true);
				WorkflowRequestInfo.setCanEdit(true);
				WorkflowRequestInfo.setRequestId(String.valueOf(requestId));
				
				//提交结果：error 请求出错 ，failed 失败，success 成功
				String response = proxy.submitWorkflowRequest(WorkflowRequestInfo, (int) requestId,
					oaUserId, "submit", "提交流程");
				
				logger.info("提交OA流程结果：{}", response);
				
				if (StringUtil.isNotBlank(response) && response.contains("success")) {
					result.setSuccess(true);
					result.setMessage("提交OA流程成功:" + response);
				} else {
					result.setSuccess(false);
					result.setMessage("提交OA流程失败:" + response);
				}
			}
		} catch (Exception e) {
			logger.error("提交OA流程出错{}", e);
			
			result.setSuccess(false);
			result.setMessage("提交OA流程出错");
		}
		return result;
	}
	
	@Override
	public void notifyNext(final FormInfo formInfo, final String title, final String message) {
		
		logger.info("OA notifyNext[{}]：executeInfo[{}],message[{}]", oaWebServiceEndpoint,
			formInfo == null ? null : formInfo.getFormExecuteInfo(), message);
		
		if (StringUtil.isBlank(oaWebServiceEndpoint))
			return;
		
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					if (formInfo.getStatus() == FormStatusEnum.BACK) {
						List<UserExtraMessageDO> notifyList = Lists.newArrayList();
						UserExtraMessageDO user = userExtraMessageDAO.findByUserId(formInfo
							.getUserId());
						if (user != null && StringUtil.isNotBlank(user.getOaSystemId())) {
							notifyList.add(user);
						}
						if (ListUtil.isNotEmpty(notifyList)) {
							String faceUrl = getFaceWebUrl();
							for (UserExtraMessageDO userItem : notifyList) {
								
								//替换收件人
								String content = message.replaceAll("\\$\\{"
																	+ FormMessageVarEnum.收件人.code()
																	+ "\\}", userItem.getUserName());
								
								int oaUserId = NumberUtil.parseInt(userItem.getOaSystemId());
								
								String url = faceUrl + formInfo.getFormCode().editUrl()
												+ "?formId=" + formInfo.getFormId()
												+ "&accessToken=" + getAccessToken(userItem);
								//创建流程
								FcsBaseResult createResult = createOaWorkflow(formInfo, url,
									oaUserId, content);
								
								//提交成功缓存requestId
								if (createResult.isSuccess()) {
									OaTaskRelationDO oaTaskRelation = new OaTaskRelationDO();
									oaTaskRelation.setTaskId(formInfo.getTaskId());
									oaTaskRelation.setUserId(userItem.getUserId());
									oaTaskRelation.setOaUserId(oaUserId);
									oaTaskRelation.setStatus(0);
									oaTaskRelation.setRequestId(createResult.getKeyId());
									oaTaskRelation.setRawAddTime(new Date());
									oaTaskRelationDAO.insert(oaTaskRelation);
								}
							}
						}
					} else if (ListUtil.isNotEmpty(formInfo.getFormExecuteInfo())) {
						String faceUrl = getFaceWebUrl();
						for (FormExecuteInfo exeInfo : formInfo.getFormExecuteInfo()) {
							
							//已执行或已设置代理不做重复提醒
							if (exeInfo.isExecute() || exeInfo.isSetAgent())
								continue;
							List<UserExtraMessageDO> notifyList = Lists.newArrayList();
							if (exeInfo.getUserId() > 0) {
								UserExtraMessageDO user = userExtraMessageDAO.findByUserId(exeInfo
									.getUserId());
								if (user != null && StringUtil.isNotBlank(user.getOaSystemId())) {
									notifyList.add(user);
								}
								
							} else if (ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {
								for (SimpleUserInfo candidate : exeInfo.getCandidateUserList()) {
									UserExtraMessageDO user = userExtraMessageDAO
										.findByUserId(candidate.getUserId());
									if (user != null && StringUtil.isNotBlank(user.getOaSystemId())) {
										notifyList.add(user);
									}
								}
							}
							
							if (ListUtil.isNotEmpty(notifyList)) {
								
								for (UserExtraMessageDO user : notifyList) {
									
									//替换收件人
									String content = message.replaceAll(
										"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
										user.getUserName());
									
									int oaUserId = NumberUtil.parseInt(user.getOaSystemId());
									
									String url = faceUrl + exeInfo.getRedirectTaskUrl()
													+ "&accessToken=" + getAccessToken(user);
									//创建流程
									FcsBaseResult createResult = createOaWorkflow(formInfo, url,
										oaUserId, content);
									
									//提交成功缓存requestId
									if (createResult.isSuccess()) {
										OaTaskRelationDO oaTaskRelation = new OaTaskRelationDO();
										oaTaskRelation.setTaskId(NumberUtil.parseLong(exeInfo
											.getTaskId()));
										oaTaskRelation.setUserId(user.getUserId());
										oaTaskRelation.setOaUserId(oaUserId);
										oaTaskRelation.setStatus(0);
										oaTaskRelation.setRequestId(createResult.getKeyId());
										oaTaskRelation.setRawAddTime(new Date());
										oaTaskRelationDAO.insert(oaTaskRelation);
									}
								}
							}
							
						}
					}
				} catch (Exception e) {
					logger.error("通知OA系统出错{}", e);
				}
				
			}
		});
	}
	
	@Override
	public void notifyAfterAssign(final FormInfo formInfo, final long taskId, final long assignId,
									final String title, final String message) {
		
		logger.info("OA notifyAfterAssign[{}]：taskId[{}],assignId[{}] message[{}] ",
			oaWebServiceEndpoint, taskId, assignId, message);
		
		if (StringUtil.isBlank(oaWebServiceEndpoint))
			return;
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info("通知OA任务交办 taskId {} ,assignId {}", taskId, assignId);
					
					List<OaTaskRelationDO> oaRelations = oaTaskRelationDAO.findByTaskId(taskId);
					if (ListUtil.isNotEmpty(oaRelations)) {
						for (OaTaskRelationDO oaRelation : oaRelations) {
							if (oaRelation.getStatus() == 0) { //相同任务全部标记已处理
								FcsBaseResult submitResult = submitOaWorkflow(
									(int) oaRelation.getOaUserId(), oaRelation.getRequestId());
								if (submitResult.isSuccess()) {
									oaRelation.setStatus(1);
									oaTaskRelationDAO.update(oaRelation);
								}
							}
						}
					}
					//产生交办人的流程
					UserExtraMessageDO user = userExtraMessageDAO.findByUserId(assignId);
					
					if (user != null && StringUtil.isNotBlank(user.getOaSystemId())) {
						
						String faceUrl = getFaceWebUrl();
						
						//替换收件人
						String content = message.replaceAll(
							"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}", user.getUserName());
						
						int oaUserId = NumberUtil.parseInt(user.getOaSystemId());
						
						String url = null;
						if (ListUtil.isNotEmpty(formInfo.getFormExecuteInfo())) {
							for (FormExecuteInfo exeInfo : formInfo.getFormExecuteInfo()) {
								if (StringUtil.equals(exeInfo.getTaskId(), taskId + "")) {
									url = faceUrl + exeInfo.getRedirectTaskUrl() + "&accessToken="
											+ getAccessToken(user);
								}
							}
						}
						
						//创建流程
						FcsBaseResult createResult = createOaWorkflow(formInfo, url, oaUserId,
							content);
						
						//提交成功缓存requestId
						if (createResult.isSuccess()) {
							OaTaskRelationDO oaTaskRelation = new OaTaskRelationDO();
							oaTaskRelation.setTaskId(taskId);
							oaTaskRelation.setUserId(user.getUserId());
							oaTaskRelation.setOaUserId(oaUserId);
							oaTaskRelation.setStatus(0);
							oaTaskRelation.setRequestId(createResult.getKeyId());
							oaTaskRelation.setRawAddTime(new Date());
							oaTaskRelationDAO.insert(oaTaskRelation);
							
						}
						
					}
				} catch (Exception e) {
					logger.error("通知OA任务交办出错{}", e);
				}
			}
		});
	}
	
	@Override
	public void notifyAfterAudit(final long taskId, final long userId) {
		
		logger.info("OA notifyAfterAudit[{}]：taskId[{}],userId[{}]", oaWebServiceEndpoint, taskId,
			userId);
		
		if (StringUtil.isBlank(oaWebServiceEndpoint))
			return;
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					logger.info("通知OA处理完成 taskId {} ,userId {}", taskId, userId);
					
					List<OaTaskRelationDO> oaRelations = oaTaskRelationDAO.findByTaskId(taskId);
					if (ListUtil.isNotEmpty(oaRelations)) {
						for (OaTaskRelationDO oaRelation : oaRelations) {
							if (oaRelation.getStatus() == 0) { //相同任务全部标记已处理
								FcsBaseResult submitResult = submitOaWorkflow(
									(int) oaRelation.getOaUserId(), oaRelation.getRequestId());
								if (submitResult.isSuccess()) {
									oaRelation.setStatus(1);
									oaTaskRelationDAO.update(oaRelation);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("通知OA处理完成出错{}", e);
				}
			}
		});
	}
	
	private void createRequest() throws Exception { //主字段        
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[6]; //字段信息       
		//creater dateInfo  rkflowname resourceid  url
		wrti[0] = new WorkflowRequestTableField();
		wrti[0].setFieldName("creater");//       
		wrti[0].setFieldValue("95");//       
		wrti[0].setView(true);//字段是否可见       
		wrti[0].setEdit(true);//字段是否可编辑       
		wrti[1] = new WorkflowRequestTableField();
		wrti[1].setFieldName("dateInfo");//        
		wrti[1].setFieldValue(DateUtil.dtSimpleFormat(new Date()));
		wrti[1].setView(true);
		wrti[1].setEdit(true);
		wrti[2] = new WorkflowRequestTableField();
		wrti[2].setFieldName("rkflowname");//        
		wrti[2].setFieldValue("CCCCCC");
		wrti[2].setView(true);
		wrti[2].setEdit(true);
		wrti[3] = new WorkflowRequestTableField();
		wrti[3].setFieldName("resourceid");//        
		wrti[3].setFieldValue("42");
		wrti[3].setView(true);
		wrti[3].setEdit(true);
		wrti[4] = new WorkflowRequestTableField();
		wrti[4].setFieldName("url");//        
		String url1 = "http://www.cguarantee.com:8205/fundMg/index.htm?systemNameDefautUrl=/fundMg/travelExpense/audit.htm&formId=23&taskId=20000015681345&accessToken=ac706ed5-0069-4b14-9a3d-3715a372b885";
		wrti[4].setFieldValue("<a href=\"" + url1 + "\" target='_blank'>查看</a>");
		//wrti[4].setFieldValue("<a href=\"javascript:window.open('" + url+ "','name')\"><span>处理</span></a>");
		System.out.println(wrti[4].getFieldValue());
		
		wrti[4].setView(true);
		wrti[4].setEdit(true);
		
		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];//主字段只有一行数据       
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);
		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);
		//添加工作流id        
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId("130");//workflowid 流程接口演示流程2016==38        
		WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息           
		wri.setCreatorId("95");//创建人id        
		wri.setRequestLevel("2");//0 正常，1重要，2紧急        
		wri.setRequestName("CCCC");//流程标题        
		wri.setWorkflowMainTableInfo(wmi);//添加主字段数据        
		
		wri.setWorkflowBaseInfo(wbi);
		WorkflowServicePortTypeProxy WorkflowServicePortTypeProxy = new WorkflowServicePortTypeProxy(
			url);
		//ok
		//实际创建流程 ,如果没有执行wri.setCreatorId("95");默认创建者 hrid 14;    
		String requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, 14);
		System.out.println("new function ==> requestid:" + requestid);
	}
}
