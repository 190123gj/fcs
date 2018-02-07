package com.born.fcs.face.web.controller.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.BpmButtonInfo;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.info.WebNodeInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.bean.ProjectBean;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

public class WorkflowBaseController extends BaseController {
	
	@Autowired
	protected WorkflowEngineWebClient workflowEngineWebClient;
	
	protected WebNodeInfo initWorkflow(Model model, FormInfo formInfo, String taskId, String sysName) {
		model.addAttribute("_SYSNAME", sysName);
		return initWorkflow(model, formInfo, taskId);
	}
	
	protected WebNodeInfo initWorkflow(Model model, FormInfo formInfo, String taskId) {
		
		WebNodeInfo nodeInfo = workflowEngineWebClient.getTaskNode(taskId);
		boolean isCanWorkflowFinished = false;
		if (ListUtil.isNotEmpty(nodeInfo.getBpmButtonInfos())) {
			for (BpmButtonInfo buttonInfo : nodeInfo.getBpmButtonInfos()) {
				if (BpmButtonInfo.NODE_BUTTON_TYPE_SAVEFORM == buttonInfo.getOperatortype()) {
					model.addAttribute("isSaveForm", true);
				}
				if (BpmButtonInfo.NODE_BUTTON_TYPE_COMPLETE_END == buttonInfo.getOperatortype()) {
					isCanWorkflowFinished = true;
					model.addAttribute("isCanWorkflowFinished", isCanWorkflowFinished);
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
					model.addAttribute("notExecutor", true); //不是当前审核人放到model供后面使用
				}
			}
		} else {
			model.addAttribute("notExecutor", true); //不是当前审核人放到model供后面使用
		}
		
		boolean hasNexNode = false;
		List<TaskNodeInfo> nodeInfos = Lists.newArrayList();
		if (isCanWorkflowFinished && ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
			&& nodeInfo.getNextFlowNode().size() >= 1) {
			hasNexNode = true;
			model.addAttribute("selectNodeCount", nodeInfo.getNextFlowNode().size() + 1);
			
		} else if (ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
					&& nodeInfo.getNextFlowNode().size() >= 2) {
			hasNexNode = true;
			model.addAttribute("selectNodeCount", nodeInfo.getNextFlowNode().size());
			
		}
		model.addAttribute("hasSelectNode", hasNexNode);
		if (hasNexNode) {
			model.addAttribute("firstNode", nodeInfo.getNextFlowNode().get(0));
			nodeInfos.addAll(nodeInfo.getNextFlowNode());
			nodeInfos.remove(0);
			model.addAttribute("secondNodes", nodeInfos);
		}
		
		model.addAttribute("webNodeInfo", nodeInfo);
		model.addAttribute("taskId", taskId);
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
			logger.info("实时获取任务执行人, actInstId : {} , nodeId : {}",
				String.valueOf(formInfo.getActInstId()), nodeId);
			//查询执行人
			List<TaskOpinion> taskOpinions = workflowEngineWebClient.getTaskUsers(
				String.valueOf(formInfo.getActInstId()), nodeId);
			logger.info("实时获取任务执行人结果, taskOpinions {}", taskOpinions);
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
	
	protected void doNext(HttpServletRequest request, HttpServletResponse response,
							Short intTaskOpinion) {
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId, taskId);
		auditOrder.setVoteAgree(String.valueOf(intTaskOpinion));
		if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TOSTART) {
			auditOrder.setIsBack("2");
		} else if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE
					|| intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE_1
					|| intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE_2
					|| intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE_3) {
			if (TaskOpinion.STATUS_REJECT_TO_TASK_NODE == intTaskOpinion)
				auditOrder.setIsBack("3");
			if (TaskOpinion.STATUS_REJECT_TO_TASK_NODE_1 == intTaskOpinion)
				auditOrder.setIsBack("4");
			if (TaskOpinion.STATUS_REJECT_TO_TASK_NODE_2 == intTaskOpinion)
				auditOrder.setIsBack("5");
			if (TaskOpinion.STATUS_REJECT_TO_TASK_NODE_3 == intTaskOpinion)
				auditOrder.setIsBack("6");
		} else if (intTaskOpinion == TaskOpinion.STATUS_ENDPROCESS) {
			auditOrder.setNextNodeId("workflowFinished");
		}
		JSONObject jsonObject = new JSONObject();
		if ("workflowFinished".equals(auditOrder.getNextNodeId())) {
			
			FcsBaseResult baseResult = getSystemMatchedFormService(request).endWorkflow(auditOrder);
			if (baseResult != null && baseResult.isSuccess()) {
				
				jsonObject.put("code", "1");
				jsonObject.put("success", true);
				
				jsonObject.put("message", "流程处理完成");
				jsonObject.put("nextAuditor", "");
				jsonObject.put("nextNode", "");
				
				//上传附件
				if ("YES".equals(request.getParameter("uploadAttach"))) {
					addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
						request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				if (baseResult != null && StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "处理失败");
				}
			}
			
		} else {
			FcsBaseResult baseResult = getSystemMatchedFormService(request)
				.auditProcess(auditOrder);
			if (baseResult != null && baseResult.isSuccess()) {
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
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "<br> 待处理人：" + nextAuditor + " ";
					}
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
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
				if (baseResult != null && StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "流程处理失败");
				}
			}
		}
		if (sessionLocal != null)
			jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
		printHttpResponse(response, jsonObject);
	}
	
	protected void doTaskAssign(HttpServletRequest request, HttpServletResponse response) {
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		TaskAssignFormOrder auditOrder = new TaskAssignFormOrder();
		auditOrder.setFormId(formId);
		auditOrder.setAssigneeId(request.getParameter("fnAssignManId"));
		auditOrder.setAssigneeName(request.getParameter("fnAssignManName"));
		auditOrder.setMemo(request.getParameter("assignMemo"));
		auditOrder.setTaskId(String.valueOf(taskId));
		auditOrder.setUserId(sessionLocal.getUserId());
		auditOrder.setUserAccount(sessionLocal.getUserName());
		auditOrder.setUserName(sessionLocal.getRealName());
		FcsBaseResult baseResult = getSystemMatchedFormService(request).taskAssign(auditOrder);
		JSONObject jsonObject = new JSONObject();
		if (baseResult != null && baseResult.isSuccess()) {
			
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
				if (StringUtil.isNotBlank(nextAuditor)) {
					message += "<br> 待处理人：" + nextAuditor + " ";
				}
				if (StringUtil.isNotBlank(nextNode)) {
					message += "[ " + nextNode + " ]";
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
			if (baseResult != null && StringUtil.isNotBlank(baseResult.getMessage())) {
				jsonObject.put("message", baseResult.getMessage());
			} else {
				jsonObject.put("message", "流程处理失败");
			}
		}
		if (sessionLocal != null)
			jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
		printHttpResponse(response, jsonObject);
	}
	
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
		
		for (String key : reqMap.keySet()) {
			customizeMap.put(key, reqMap.get(key));
		}
		
		auditOrder.setCustomizeMap(customizeMap);
		
		return auditOrder;
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	protected FormService getSystemMatchedFormService(HttpServletRequest request) {
		return getSystemMatchedFormService(request.getParameter("_SYSNAME"));
	}
	
	protected ProjectBean getProjectBean(String projectCode) {
		ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
		return getProjectBean(project);
	}
	
	protected ProjectBean getProjectBean(ProjectInfo projectTemp) {
		ProjectBean projectBean = new ProjectBean();
		String capitalSubChannelName = null;
		double guaranteeRate = 0.0;
		if (ProjectUtil.isBond(projectTemp.getBusiType())) {
			FCouncilSummaryProjectBondInfo councilSummaryProjectBondInfo = councilSummaryServiceClient
				.queryBondProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (councilSummaryProjectBondInfo != null) {
				capitalSubChannelName = councilSummaryProjectBondInfo.getCapitalSubChannelName();
				if (councilSummaryProjectBondInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = councilSummaryProjectBondInfo.getGuaranteeFee();
				}
				
			}
		} else if (ProjectUtil.isGuarantee(projectTemp.getBusiType())) {
			FCouncilSummaryProjectGuaranteeInfo guaranteeInfo = councilSummaryServiceClient
				.queryGuaranteeProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (guaranteeInfo != null) {
				capitalSubChannelName = guaranteeInfo.getCapitalSubChannelName();
				if (guaranteeInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = guaranteeInfo.getGuaranteeFee();
				}
			}
			
		} else if (ProjectUtil.isEntrusted(projectTemp.getBusiType())) {
			FCouncilSummaryProjectEntrustedInfo entrustedInfo = councilSummaryServiceClient
				.queryEntrustedProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (entrustedInfo != null) {
				capitalSubChannelName = entrustedInfo.getCapitalSubChannelName();
			}
			
		} else if (ProjectUtil.isLitigation(projectTemp.getBusiType())) {
			FCouncilSummaryProjectLgLitigationInfo lgLitigationInfo = councilSummaryServiceClient
				.queryLgLitigationProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (lgLitigationInfo != null) {
				if (lgLitigationInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = lgLitigationInfo.getGuaranteeFee();
				}
			}
			
		}
		//所有资金渠道
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
			.queryCapitalChannel(projectTemp.getProjectCode());
		if (ListUtil.isNotEmpty(capitalChannels)) {
			String capitalSubChannelNameMulti = "";
			for (ProjectChannelRelationInfo capitalChannel : capitalChannels) {
				if (StringUtil.isNotEmpty(capitalChannel.getSubChannelName())) {
					capitalSubChannelNameMulti += capitalChannel.getSubChannelName() + ",";
				}
			}
			if (StringUtil.isNotEmpty(capitalSubChannelNameMulti)) {
				capitalSubChannelNameMulti = capitalSubChannelNameMulti.substring(0,
					capitalSubChannelNameMulti.length() - 1);
				capitalSubChannelName = capitalSubChannelNameMulti;
			}
		}
		projectBean.setCapitalSubChannelName(capitalSubChannelName);
		projectBean.setDblGuaranteeRate(guaranteeRate);
		if (guaranteeRate > 0.0) {
			projectBean.setGuaranteeRate(NumberUtil.format(guaranteeRate, "0.###") + "%");
		} else {
			projectBean.setGuaranteeRate("");
		}
		return projectBean;
	}
}
