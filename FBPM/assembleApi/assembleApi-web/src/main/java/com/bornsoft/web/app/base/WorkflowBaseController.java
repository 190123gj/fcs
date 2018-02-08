package com.bornsoft.web.app.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.BpmButtonInfo;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.info.WebNodeInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.api.service.app.ModuleEnum;
import com.bornsoft.api.service.app.entity.AuditBtnEnum;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.web.app.main.UserMainPageController.ViewType;
import com.bornsoft.web.app.main.UserMainPageController.ViewType.APPViewEnum;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

public class WorkflowBaseController extends BaseController {
	
	@Autowired
	protected WorkflowEngineWebClient workflowEngineWebClient;
	
	protected WebNodeInfo initWorkflow(final JSONObject data, long formId, String taskId) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		} else {
			return initWorkflow(data, form, taskId);
		}
	}
	
	protected WebNodeInfo initWorkflow(Map<String, Object> data, FormInfo formInfo, String taskId) {
		
		WebNodeInfo nodeInfo = workflowEngineWebClient.getTaskNode(taskId);
		JSONArray btnArr = new JSONArray();
		ViewType view = ViewType.getViewByCode(String.valueOf(data.get("viewCode")));
		//button初始化
		for (BpmButtonInfo btnInfo : nodeInfo.getBpmButtonInfos()) {
			AuditBtnEnum btnEnum = AuditBtnEnum.getByCode(btnInfo.getOperatortype() + "");
			
			//交办类型,更新界面类型【暂时把交办合并到指派人，因为目前只有普通的审核才有交办】
			if (btnEnum == AuditBtnEnum.fn_AssignBtn) {
				data.put("view", ViewType.ASSIGN.getType().getCode());
				data.put("viewCode", ViewType.ASSIGN.getCode());
			}
			if (btnEnum != null) {
				if (view == ViewType.POINT_ROUTE_3 && btnEnum == AuditBtnEnum.fn_AuditBtnPass) {
					JSONObject btn = new JSONObject();
					btn.put("name", btnInfo.getButtonName());
					
					btn.put("url", "/projectMg/form/meetingMg/summary/submit.json");
					btnArr.add(btn);
				} else {
					JSONObject btn = new JSONObject();
					btn.put("name", btnInfo.getButtonName());
					btn.put("url", btnEnum.getUrl());
					btnArr.add(btn);
				}
				
			}
		}
		data.put("btnList", btnArr);
		//没有按钮的、发起人提交步骤的转到PC去
		if (btnArr.size() == 0 || StringUtil.contains(nodeInfo.getNodeName(), "发起人提交")) {
			data.put("view", ViewType.PC.getType().getCode());
			data.put("viewCode", ViewType.PC.getCode());
			return nodeInfo;
		}
		
		boolean isCanWorkflowFinished = false;
		if (ListUtil.isNotEmpty(nodeInfo.getBpmButtonInfos())) {
			for (BpmButtonInfo buttonInfo : nodeInfo.getBpmButtonInfos()) {
				if (BpmButtonInfo.NODE_BUTTON_TYPE_SAVEFORM == buttonInfo.getOperatortype()) {
					data.put("isSaveForm", true);
				}
				if (BpmButtonInfo.NODE_BUTTON_TYPE_COMPLETE_END == buttonInfo.getOperatortype()) {
					isCanWorkflowFinished = true;
					data.put("isCanWorkflowFinished", isCanWorkflowFinished);
				}
			}
			
			//判断是否当前执行人，当前表单还可操作
			List<FormExecuteInfo> exeInfos = formInfo.getFormExecuteInfo();
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			boolean isCurrentExecutor = false;
			if (ListUtil.isNotEmpty(exeInfos)) {
				for (FormExecuteInfo info : exeInfos) {
					if (info.isExecute() || info.isSetAgent())
						continue;
					if (taskId.equals(info.getTaskId())) {//当前任务
						if (info.getUserId() > 0 && info.getUserId() == sessionLocal.getUserId()) {
							isCurrentExecutor = true;
							break;
						} else if (info.getUserId() == 0
									&& ListUtil.isNotEmpty(info.getCandidateUserList())) {
							for (SimpleUserInfo user : info.getCandidateUserList()) {
								if (user.getUserId() == sessionLocal.getUserId()) {
									isCurrentExecutor = true;
									break;
								}
							}
						}
					}
				}
			}
			if (!isCurrentExecutor) {
				//不是当前执行人再去bpm查询实时的执行人
				isCurrentExecutor = false;
				List<Long> nodeExcutor = getNodeExcutor(formInfo, nodeInfo.getNodeId());
				for (Long userId : nodeExcutor) {
					if (userId == sessionLocal.getUserId()) {
						isCurrentExecutor = true;
						break;
					}
				}
				//不是当前执行人清除按钮
				if (!isCurrentExecutor) {
					nodeInfo.getBpmButtonInfos().clear(); //清空按钮就行了
					data.remove("btnList");
				}
			}
		}
		//TODO 
		boolean hasNexNode = false;
		List<TaskNodeInfo> nodeInfos = Lists.newArrayList();
		if (isCanWorkflowFinished && ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
			&& nodeInfo.getNextFlowNode().size() >= 1) {
			hasNexNode = true;
			data.put("selectNodeCount", nodeInfo.getNextFlowNode().size() + 1);
			
		} else if (ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
					&& nodeInfo.getNextFlowNode().size() >= 2) {
			hasNexNode = true;
			data.put("selectNodeCount", nodeInfo.getNextFlowNode().size());
			
		}
		data.put("hasSelectNode", hasNexNode);
		if (hasNexNode) {
			TaskNodeInfo firstNode = nodeInfo.getNextFlowNode().get(0);
			data.put("firstNode", firstNode);
			nodeInfos.addAll(nodeInfo.getNextFlowNode());
			nodeInfos.remove(0);
			if (isCanWorkflowFinished) {
				TaskNodeInfo node = new TaskNodeInfo();
				node.setNodeId("workflowFinished");
				node.setNodeName("流程结束审核通过");
				nodeInfos.add(node);
			}
			nodeInfos.add(firstNode);
			data.put("secondNodes", nodeInfos);
		}
		
		//只要不是这个最特殊的一种
		
		if (nodeInfos.size() == 0
			&& (view == ViewType.POINT_ROUTE_1 || view == ViewType.POINT_ROUTE_2)) {
			data.put("view", ViewType.COMMON.getType().getCode());
			data.put("viewCode", ViewType.COMMON.getCode());
		} else if (view == ViewType.ASSIGN) {
			data.put("personName", "接收人");
		} else if (view.getType() == APPViewEnum.POINT) {
			String nodeName = nodeInfo.getNodeName();
			int index = nodeName.indexOf("指定");
			if (index == -1) {
				index = nodeName.indexOf("指派");
			}
			if (index != -1) {
				data.put("personName", nodeName.substring(index + 2));
			} else {
				data.put("personName", "业务人员");
			}
		}
		data.put("taskId", taskId);
		return nodeInfo;
	}
	
	/**
	 * 根据任务ID验证当前登陆是是否该任务执行人
	 * @param form
	 * @param taskId
	 * @return
	 */
	private List<Long> getNodeExcutor(FormInfo formInfo, String nodeId) {
		List<Long> nodeExcutor = Lists.newArrayList();
		try {
			//查询执行人
			List<TaskOpinion> taskOpinions = workflowEngineWebClient.getTaskUsers(
				String.valueOf(formInfo.getActInstId()), nodeId);
			if (ListUtil.isNotEmpty(taskOpinions)) {
				for (TaskOpinion taskOpinion : taskOpinions) {
					//正在审批
					if (taskOpinion.getCheckStatus() == (long) TaskOpinion.STATUS_CHECKING) {
						long userId = taskOpinion.getExeUserId();
						if (userId > 0) {
							nodeExcutor.add(userId);
						} else if (ListUtil.isNotEmpty(taskOpinion.getCandidateUserList())) { //查询候选人列表
							for (Long uid : taskOpinion.getCandidateUserList()) {
								nodeExcutor.add(uid);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("实时获取任务执行人出错 {}", e);
		}
		return nodeExcutor;
	}
	
	protected JSONObject doNext(HttpServletRequest request, HttpServletResponse response,
								Short intTaskOpinion) {
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId, taskId);
		auditOrder.setVoteAgree(String.valueOf(intTaskOpinion));
		if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TOSTART) {
			auditOrder.setIsBack("2");
		} else if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE) {
			auditOrder.setIsBack("3");
		} else if (intTaskOpinion == TaskOpinion.STATUS_ENDPROCESS) {
			auditOrder.setNextNodeId("workflowFinished");
		}
		JSONObject jsonObject = new JSONObject();
		//module参数校验
		if (!validateModule(request)) {
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "module参数非法");
			return jsonObject;
		}
		if ("workflowFinished".equals(auditOrder.getNextNodeId())) {
			FcsBaseResult baseResult = jckFormService.getSystemMatchedFormService(request)
				.endWorkflow(auditOrder);
			if (baseResult.isSuccess()) {
				jsonObject.put("code", "1");
				jsonObject.put("success", true);
				jsonObject.put("nextAuditor", baseResult.getUrl());
				jsonObject.put("message", "流程处理完成");
				
				//上传附件
				if ("YES".equals(request.getParameter("uploadAttach"))) {
					addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
						request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				if (StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "处理失败");
				}
			}
			
		} else {
			FcsBaseResult baseResult = jckFormService.getSystemMatchedFormService(request)
				.auditProcess(auditOrder);
			if (baseResult.isSuccess()) {
				jsonObject.put("code", "1");
				jsonObject.put("success", true);
				
				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = baseResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "处理成功 ";
				if ("FLOW_FINISH".equals(nextNode)) {
					message = "流程处理完成";
				} else {
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "[ 待执行人：" + nextAuditor + " ]";
					}
				}
				
				jsonObject.put("message", message);
				jsonObject.put("nextAuditor", nextAuditor);
				jsonObject.put("nextNode", nextNode);
				//上传附件
				if ("YES".equals(request.getParameter("uploadAttach"))) {
					addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
						request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				if (StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "流程处理失败");
				}
			}
		}
		logger.info("doNext,出参={}", jsonObject);
		return jsonObject;
	}
	
	protected JSONObject doTaskAssign(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		//module参数校验
		if (!validateModule(request)) {
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "module参数非法");
			return jsonObject;
		}
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		TaskAssignFormOrder auditOrder = new TaskAssignFormOrder();
		auditOrder.setFormId(formId);
		auditOrder.setAssigneeId(request.getParameter("busiManagerbId"));
		auditOrder.setAssigneeName(request.getParameter("busiManagerbName"));
		auditOrder.setMemo(request.getParameter("workflowVoteContent"));
		auditOrder.setTaskId(String.valueOf(taskId));
		auditOrder.setUserId(sessionLocal.getUserId());
		auditOrder.setUserAccount(sessionLocal.getUserName());
		auditOrder.setUserName(sessionLocal.getRealName());
		FcsBaseResult baseResult = jckFormService.getSystemMatchedFormService(request).taskAssign(
			auditOrder);
		if (baseResult.isSuccess()) {
			jsonObject.put("code", "1");
			jsonObject.put("success", true);
			
			String nextAuditor = "";
			String nextNode = "";
			String nextInfo = baseResult.getUrl();
			if (StringUtil.isNotBlank(nextInfo)) {
				String[] next = nextInfo.split(";");
				if (next.length > 0)
					nextNode = next[0];
				if (next.length > 1)
					nextAuditor = next[1];
			}
			
			String message = "处理成功 ";
			if ("FLOW_FINISH".equals(nextNode)) {
				message = "流程处理完成";
			} else {
				if (StringUtil.isNotBlank(nextNode)) {
					message += "[ " + nextNode + " ]";
				}
				if (StringUtil.isNotBlank(nextAuditor)) {
					message += "[ 待执行人：" + nextAuditor + " ]";
				}
			}
			
			jsonObject.put("message", message);
			jsonObject.put("nextAuditor", nextAuditor);
			jsonObject.put("nextNode", nextNode);
			
			//上传附件
			if ("YES".equals(request.getParameter("uploadAttach"))) {
				addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
					request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
			}
		} else {
			jsonObject.put("code", "0");
			jsonObject.put("success", false);
			jsonObject.put("message", "流程处理失败");
		}
		logger.info("doTaskAssign,出参={}", jsonObject);
		return jsonObject;
	}
	
	/**
	 * module参数有效
	 * @param request
	 * @return
	 */
	protected boolean validateModule(HttpServletRequest request) {
		String module = request.getParameter(JckFormService.MODULE);
		return ModuleEnum.getByCode(module) != null;
	}
	
	/**
	 * 构造审核order
	 * @param request
	 * @param formId
	 * @param taskId
	 * @return
	 */
	protected SimpleFormAuditOrder makeAuditOrder(HttpServletRequest request, long formId,
													long taskId) {
		SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
		
		setSessionLocalInfo2Order(auditOrder);
		
		auditOrder.setFormId(formId);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		auditOrder.setUserId(sessionLocal.getUserId());
		auditOrder.setUserAccount(sessionLocal.getUserName());
		auditOrder.setUserName(sessionLocal.getRealName());
		auditOrder.setNextNodeId(request.getParameter("selectNodeId"));
		auditOrder.setNextUser(request.getParameter("selectNodeNextUser"));
		auditOrder.setTaskId(taskId);
		auditOrder.setVoteContent(request.getParameter("workflowVoteContent"));
		auditOrder.setUserIp(sessionLocal.getRemoteAddr());
		
		UserInfo userInfo = sessionLocal.getUserInfo();
		if (userInfo != null) {
			auditOrder.setUserEmail(userInfo.getEmail());
			auditOrder.setUserMobile(userInfo.getMoblie());
		}
		
		//serviceName 直接写死在 FormCodeEnum中
		//auditOrder.setServiceName(request.getParameter("workflowServiceName"));
		auditOrder.setNextNodeId(request.getParameter("radNextNodeId"));
		Map<String, String> reqMap = WebUtil.getRequestMap(request, "selectNodeId",
			"selectNodeNextUser", "workflowVoteContent", "radNextNodeId", "assignMemo",
			"fnAssignManName", "fnAssignManId", "taskId", "hddFormId", "hddFormActDefId", "_time",
			"hddFormActInstId", "pathName_FORM_ATTACH");
		
		Map<String, Object> customizeMap = auditOrder.getCustomizeMap();
		if (customizeMap == null)
			customizeMap = Maps.newHashMap();
		ViewType view = ViewType.getViewByCode(reqMap.get("viewCode"));
		if (view == null) {
			throw new BornApiException("未找到界面类型");
		}
		for (String key : reqMap.keySet()) {
			customizeMap.put(key, reqMap.get(key));
		}
		//app字段综合转换
		if (view == ViewType.POINT_LEGAL_CONTRACT) {
			customizeMap.put("needLegalManager", "IS");
			transLegal(customizeMap);
		} else if (ViewType.POINT_LEGAL_SETUP_SS == view) {
			customizeMap.put("chooseLegalManager", "YES");
			transLegal(customizeMap);
		} else if (ViewType.POINT_LEGAL_SETUP_BROKER_BUSINESS == view) {
			customizeMap.put("chooseLegalManager", "YES");
			transLegal(customizeMap);
		} else if (ViewType.POINT_RISK_MANAGE == view) {
			customizeMap.put("chooseRiskManager", BooleanEnum.YES.code());
			
			customizeMap.put("riskManagerName", customizeMap.remove("busiManagerbName"));
			customizeMap.put("riskManagerId", customizeMap.remove("busiManagerbId"));
			customizeMap.put("riskManagerAccount", customizeMap.remove("busiManagerbAccount"));
		} else if (ViewType.POINT_B == view) {
			customizeMap.put("chooseBusiManagerb", BooleanEnum.YES.code());
			customizeMap.put("busiManagerbName", customizeMap.remove("busiManagerbName"));
			customizeMap.put("busiManagerbId", customizeMap.remove("busiManagerbId"));
			customizeMap.put("busiManagerbAccount", customizeMap.remove("busiManagerbAccount"));
		} else if (view == ViewType.POINT_LEGAL_JINGBAN) {
			//指派经办人好像有问题
		} else if (view == ViewType.ASSIGN) {
			//此处无需处理
		}
		customizeMap.remove("view");
		auditOrder.setCustomizeMap(customizeMap);
		
		return auditOrder;
	}
	
	private void transLegal(Map<String, Object> customizeMap) {
		customizeMap.put("legalManagerName", customizeMap.remove("busiManagerbName"));
		customizeMap.put("legalManagerId", customizeMap.remove("busiManagerbId"));
		customizeMap.put("legalManagerAccount", customizeMap.remove("busiManagerbAccount"));
	}
	
}
