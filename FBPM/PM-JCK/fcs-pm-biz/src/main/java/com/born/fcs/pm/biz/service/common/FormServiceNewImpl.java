package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dataobject.BackTaskDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.TaskEntity;
import com.born.fcs.pm.integration.bpm.WorkflowEngineClient;
import com.born.fcs.pm.integration.bpm.result.StartFlowResult;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.FormUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormMessageBaseOnEnum;
import com.born.fcs.pm.ws.enums.FormMessageTypeEnum;
import com.born.fcs.pm.ws.enums.FormMessageVarEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormMessageTempleteInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.WorkflowAssignOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowDoNextOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowRecoverOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.common.CancelFormOrder;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormNotifyAssginService;
import com.born.fcs.pm.ws.service.common.FormNotifyNextService;
import com.born.fcs.pm.ws.service.common.FormNotifyResultService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("formService")
public class FormServiceNewImpl extends BaseAutowiredDomainService implements FormService,
																	ApplicationContextAware {
	
	private static ApplicationContext _applicationContext;
	
	@Autowired
	WorkflowEngineClient workflowEngineClient;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	FormMessageTempleteService formMessageTempleteService;
	
	@Autowired
	ProjectService projectService;
	
	public Map<String, String> makeDefaultMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		vars.put(FormMessageVarEnum.单据号.code(), String.valueOf(formInfo.getFormId()));
		vars.put(FormMessageVarEnum.单据名称.code(), formInfo.getFormName());
		vars.put(FormMessageVarEnum.发起人.code(), formInfo.getUserName());
		vars.put(FormMessageVarEnum.发起部门.code(), formInfo.getDeptName());
		vars.put(FormMessageVarEnum.发起时间.code(), DateUtil.simpleDate(formInfo.getSubmitTime()));
		vars.put(FormMessageVarEnum.操作时间.code(), DateUtil.simpleDate(getSysdate()));
		String result = formInfo.getStatus().getMessage();
		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			result = "已通过";
		} else if (formInfo.getStatus() == FormStatusEnum.BACK) {
			result = "已驳回";
		} else if (formInfo.getStatus() == FormStatusEnum.DENY) {
			result = "未通过";
		}
		vars.put(FormMessageVarEnum.审核结果.code(), result);
		vars.put(FormMessageVarEnum.系统地址.code(), getFaceWebUrl());
		
		if (StringUtil.isNotEmpty(formInfo.getRelatedProjectCode())) {
			String[] projectCodes = formInfo.getRelatedProjectCode().split(",");
			String projectName = "";
			String timeLimit = "";
			String busiType = "";
			String customerName = "";
			String projectCode = "";
			for (String pCode : projectCodes) {
				//签报 理财 经纪业务不参与
				if (pCode.startsWith("QB") || pCode.startsWith("F") || pCode.startsWith("JJ"))
					continue;
				ProjectInfo project = projectService.queryByCode(pCode, false);
				if (project != null) {
					projectCode += project.getProjectCode() + "，";
					projectName += project.getProjectName() + "，";
					if (!ProjectUtil.isLitigation(project.getBusiType()))
						timeLimit += (project.getTimeLimit() + project.getTimeUnit().viewName())
										+ "，";
					busiType += project.getBusiTypeName() + "，";
					customerName += project.getCustomerName() + "，";
				}
			}
			if (StringUtil.isNotBlank(projectCode))
				projectCode = projectCode.substring(0, projectCode.length() - 1);
			if (StringUtil.isNotBlank(projectName))
				projectName = projectName.substring(0, projectName.length() - 1);
			if (StringUtil.isNotBlank(timeLimit))
				timeLimit = timeLimit.substring(0, timeLimit.length() - 1);
			if (StringUtil.isNotBlank(busiType))
				busiType = busiType.substring(0, busiType.length() - 1);
			if (StringUtil.isNotBlank(customerName))
				customerName = customerName.substring(0, customerName.length() - 1);
			vars.put(FormMessageVarEnum.项目编号.code(), projectCode);
			vars.put(FormMessageVarEnum.项目名称.code(), projectName);
			vars.put(FormMessageVarEnum.项目期限.code(), timeLimit);
			vars.put(FormMessageVarEnum.业务类型.code(), busiType);
			vars.put(FormMessageVarEnum.客户名称.code(), customerName);
		}
		
		//当前操作人
		SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
			"currentUser");
		if (currentUser != null) {
			vars.put(FormMessageVarEnum.操作人.code(), currentUser.getUserName());
			vars.put(FormMessageVarEnum.操作部门.code(), currentUser.getDeptName());
		}
		
		return vars;
	}
	
	/**
	 * 通知下步执行人
	 * @param formInfo
	 */
	private void notifyNext(FormInfo formInfo, WorkflowExtProcessService processService) {
		
		try {
			
			//查询消息模板
			FormMessageTempleteInfo templete = formMessageTempleteService.findByFormCodeAndType(
				formInfo.getFormCode(), FormMessageTypeEnum.STEP);
			if (templete == null) {
				templete = formMessageTempleteService.findByFormCodeAndType(FormCodeEnum.DEFAULT,
					FormMessageTypeEnum.STEP);
			}
			
			if (templete == null) {
				logger.info("{} {} 模板不存在", formInfo.getFormCode(),
					FormMessageTypeEnum.STEP.message());
				return;
			}
			
			if (processService == null) {
				processService = new BaseProcessService();
			}
			
			//获取通知实现
			FormNotifyNextService notifyNextService = processService.getNotifyNextService();
			
			//基于BPM配置
			if (templete.getBaseOn() == FormMessageBaseOnEnum.BPM) {
				
				templete.setIsSendMail(BooleanEnum.NO);
				templete.setIsSendSiteMessage(BooleanEnum.NO);
				templete.setIsSendSms(BooleanEnum.NO);
				
				//下个节点
				TaskNodeInfo nodeInfo = (TaskNodeInfo) FcsPmDomainHolder.get().getAttribute(
					"nodeInfo");
				
				if (nodeInfo != null) {
					String informType = nodeInfo.getInformType();
					if (informType != null) {
						if (informType.contains("1")) {
							templete.setIsSendMail(BooleanEnum.IS);
						}
						if (informType.contains("2")) {
							templete.setIsSendSms(BooleanEnum.IS);
						}
						if (informType.contains("3")) {
							templete.setIsSendSiteMessage(BooleanEnum.IS);
						}
					}
				}
			}
			
			if (templete.getIsSendMail() == BooleanEnum.IS
				|| templete.getIsSendSiteMessage() == BooleanEnum.IS
				|| templete.getIsSendSms() == BooleanEnum.IS) {
				Map<String, String> vars = makeDefaultMessageVar(formInfo);
				Map<String, String> customVars = null;
				try {
					customVars = processService.makeMessageVar(formInfo);
				} catch (Exception e) {
					logger.error("获取自定义消息参数出错：{}, {}", processService, e);
				}
				if (customVars != null)
					vars.putAll(customVars);
				notifyNextService.notifyNext(formInfo, templete, vars);
			}
		} catch (Exception e) {
			logger.error("通知下步执行人出错: {}", e);
		}
	}
	
	/**
	 * 通知交办人(沿用通知下步执行人消息模板)
	 * @param formInfo
	 */
	private void notifyAssign(FormInfo formInfo, FormRelatedUserInfo assignMan,
								WorkflowExtProcessService processService) {
		
		try {
			
			//查询消息模板
			FormMessageTempleteInfo templete = formMessageTempleteService.findByFormCodeAndType(
				formInfo.getFormCode(), FormMessageTypeEnum.STEP);
			if (templete == null) {
				templete = formMessageTempleteService.findByFormCodeAndType(FormCodeEnum.DEFAULT,
					FormMessageTypeEnum.STEP);
			}
			
			if (templete == null) {
				logger.info("{} {} 模板不存在", formInfo.getFormCode(),
					FormMessageTypeEnum.STEP.message());
				return;
			}
			
			if (processService == null) {
				processService = new BaseProcessService();
			}
			
			//获取通知实现
			FormNotifyAssginService notifyAssignService = processService.getNotifyAssignService();
			
			//基于BPM配置
			if (templete.getBaseOn() == FormMessageBaseOnEnum.BPM) {
				
				templete.setIsSendMail(BooleanEnum.NO);
				templete.setIsSendSiteMessage(BooleanEnum.NO);
				templete.setIsSendSms(BooleanEnum.NO);
				
				//下个节点
				TaskNodeInfo nodeInfo = (TaskNodeInfo) FcsPmDomainHolder.get().getAttribute(
					"nodeInfo");
				
				if (nodeInfo != null) {
					String informType = nodeInfo.getInformType();
					if (informType != null) {
						if (informType.contains("1")) {
							templete.setIsSendMail(BooleanEnum.IS);
						}
						if (informType.contains("2")) {
							templete.setIsSendSms(BooleanEnum.IS);
						}
						if (informType.contains("3")) {
							templete.setIsSendSiteMessage(BooleanEnum.IS);
						}
					}
				}
			}
			
			if (templete.getIsSendMail() == BooleanEnum.IS
				|| templete.getIsSendSiteMessage() == BooleanEnum.IS
				|| templete.getIsSendSms() == BooleanEnum.IS) {
				Map<String, String> vars = makeDefaultMessageVar(formInfo);
				Map<String, String> customVars = null;
				try {
					customVars = processService.makeMessageVar(formInfo);
				} catch (Exception e) {
					logger.error("获取自定义消息参数出错：{}, {}", processService, e);
				}
				if (customVars != null)
					vars.putAll(customVars);
				notifyAssignService.notifyAssign(formInfo, assignMan, templete, vars);
			}
		} catch (Exception e) {
			logger.error("通知交办人出错: {}", e);
		}
	}
	
	/**
	 * 通知结果
	 * @param formInfo
	 * @param processService
	 */
	private void notifyResult(FormInfo formInfo, WorkflowExtProcessService processService) {
		try {
			
			if (formInfo.getStatus() == FormStatusEnum.APPROVAL
				|| formInfo.getStatus() == FormStatusEnum.BACK
				|| formInfo.getStatus() == FormStatusEnum.DENY) { //结果才通知
			
				//查询消息模板
				FormMessageTempleteInfo templete = formMessageTempleteService
					.findByFormCodeAndType(formInfo.getFormCode(), FormMessageTypeEnum.RESULT);
				if (templete == null) {
					templete = formMessageTempleteService.findByFormCodeAndType(
						FormCodeEnum.DEFAULT, FormMessageTypeEnum.RESULT);
				}
				
				if (templete == null) {
					logger.info("{} {} 模板不存在", formInfo.getFormCode(),
						FormMessageTypeEnum.RESULT.message());
					return;
				}
				
				if (processService == null) {
					processService = new BaseProcessService();
				}
				
				//获取通知实现
				FormNotifyResultService notifyResultService = processService
					.getNotifyResultService();
				
				if (templete.getIsSendMail() == BooleanEnum.IS
					|| templete.getIsSendSiteMessage() == BooleanEnum.IS
					|| templete.getIsSendSms() == BooleanEnum.IS) {
					Map<String, String> vars = makeDefaultMessageVar(formInfo);
					Map<String, String> customVars = null;
					try {
						customVars = processService.makeMessageVar(formInfo);
					} catch (Exception e) {
						logger.error("获取自定义消息参数出错：{}, {}", processService, e);
					}
					if (customVars != null)
						vars.putAll(customVars);
					
					//通知人 
					List<SimpleUserInfo> notifyUserList = processService
						.resultNotifyUserList(formInfo);
					//去重
					Map<Long, SimpleUserInfo> notifyUserMap = Maps.newHashMap();
					if (ListUtil.isNotEmpty(notifyUserList)) {
						for (SimpleUserInfo userInfo : notifyUserList) {
							notifyUserMap.put(userInfo.getUserId(), userInfo);
						}
						notifyUserList.clear();
						for (long key : notifyUserMap.keySet()) {
							notifyUserList.add(notifyUserMap.get(key));
						}
					}
					notifyResultService.notifyResult(formInfo, templete, vars, notifyUserList);
				}
				
			}
		} catch (Exception e) {
			logger.error("通知审核结果出错: {}", e);
		}
	}
	
	/**
	 * 设置流程处理人员
	 * @param formInfo
	 * @param processMan
	 * @param processTaskId
	 */
	private void setRelatedUser(FormInfo formInfo, String taskName, long processManId,
								long processTaskId) {
		try {
			
			//记录下步流程处理人员
			List<FormExecuteInfo> executeList = formInfo.getFormExecuteInfo();
			if (ListUtil.isNotEmpty(executeList)) {
				for (FormExecuteInfo exeInfo : executeList) {
					if (exeInfo.isExecute() && !exeInfo.isSetAgent()) //执行过后就不重复记录了
						continue;
					
					FormRelatedUserOrder relatedUser = new FormRelatedUserOrder();
					relatedUser.setFormId(formInfo.getFormId());
					relatedUser.setFormCode(formInfo.getFormCode());
					relatedUser.setTaskId(NumberUtil.parseLong(exeInfo.getTaskId()));
					if (exeInfo.isExecute())
						relatedUser.setExeStatus(ExeStatusEnum.EXCUTED);
					
					if (exeInfo.isSetAgent())
						relatedUser.setExeStatus(ExeStatusEnum.AGENT_SET);
					
					if (exeInfo.getUserId() > 0) {
						
						//记录流程处理人员
						relatedUser.setUserType(FormRelatedUserTypeEnum.FLOW_PROCESS_MAN);
						if (exeInfo.isSetAgent()) {
							relatedUser.setRemark(exeInfo.getOpinion());
						} else {
							relatedUser.setRemark("【" + formInfo.getFormName() + "】" + "流程处理人");
						}
						
						relatedUser.setUserId(exeInfo.getUserId());
						relatedUser.setUserAccount(exeInfo.getUserAccount());
						relatedUser.setUserName(exeInfo.getUserName());
						relatedUser.setUserMobile(exeInfo.getMobile());
						relatedUser.setUserEmail(exeInfo.getEmail());
						relatedUser.setTaskName(taskName);
						
						//如果相关项目编号存在则设置项目编号
						String relatedProjectCode = formInfo.getRelatedProjectCode();
						if (StringUtil.isNotBlank(relatedProjectCode)) {
							String[] pcodes = relatedProjectCode.split(",");
							for (String pcode : pcodes) {
								if (StringUtil.isNotBlank(pcode)) {
									relatedUser.setProjectCode(pcode);
									formRelatedUserService.setRelatedUser(relatedUser);
								}
							}
						} else {
							formRelatedUserService.setRelatedUser(relatedUser);
						}
					} else if (ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {
						if (exeInfo.getCandidateUserList().size() == 1) {//只有一个候选人那就是处理人了
							relatedUser.setUserType(FormRelatedUserTypeEnum.FLOW_PROCESS_MAN);
							if (exeInfo.isSetAgent()) {
								relatedUser.setRemark(exeInfo.getOpinion());
							} else {
								relatedUser.setRemark("【" + formInfo.getFormName() + "】" + "流程处理人");
							}
						} else {
							//记录流程候选人员
							relatedUser.setUserType(FormRelatedUserTypeEnum.FLOW_CANDIDATE_MAN);
							if (exeInfo.isSetAgent()) {
								relatedUser.setRemark(exeInfo.getOpinion());
							} else {
								relatedUser.setRemark("【" + formInfo.getFormName() + "】" + "流程候选人");
							}
						}
						for (SimpleUserInfo user : exeInfo.getCandidateUserList()) {
							relatedUser.setUserId(user.getUserId());
							relatedUser.setUserAccount(user.getUserAccount());
							relatedUser.setUserName(user.getUserName());
							relatedUser.setUserMobile(user.getMobile());
							relatedUser.setUserEmail(user.getEmail());
							//如果相关项目编号存在则设置项目编号
							String relatedProjectCode = formInfo.getRelatedProjectCode();
							if (StringUtil.isNotBlank(relatedProjectCode)) {
								String[] pcodes = relatedProjectCode.split(",");
								for (String pcode : pcodes) {
									if (StringUtil.isNotBlank(pcode)) {
										relatedUser.setProjectCode(pcode);
										formRelatedUserService.setRelatedUser(relatedUser);
									}
								}
							} else {
								formRelatedUserService.setRelatedUser(relatedUser);
							}
						}
					}
				}
			}
			
			//删掉不是处理人的候选人
			if (processTaskId > 0 && processManId > 0) {
				formRelatedUserService.deleteNotExecutor(processTaskId, processManId);
			}
			
			//如果是提交表单还要记录表单提交人员
			if (formInfo.getStatus() == FormStatusEnum.SUBMIT) {
				//记录流程处理人员
				FormRelatedUserOrder relatedUser = new FormRelatedUserOrder();
				relatedUser.setFormId(formInfo.getFormId());
				relatedUser.setFormCode(formInfo.getFormCode());
				relatedUser.setTaskId(0);
				relatedUser.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
				relatedUser.setRemark("【" + formInfo.getFormName() + "】" + "表单提交人");
				relatedUser.setUserId(formInfo.getUserId());
				relatedUser.setUserAccount(formInfo.getUserAccount());
				relatedUser.setUserName(formInfo.getUserName());
				relatedUser.setUserMobile(formInfo.getUserMobile());
				relatedUser.setUserEmail(formInfo.getUserEmail());
				relatedUser.setExeStatus(ExeStatusEnum.IN_PROGRESS);
				//如果相关项目编号存在则设置项目编号
				String relatedProjectCode = formInfo.getRelatedProjectCode();
				if (StringUtil.isNotBlank(relatedProjectCode)) {
					String[] pcodes = relatedProjectCode.split(",");
					for (String pcode : pcodes) {
						if (StringUtil.isNotBlank(pcode)) {
							relatedUser.setProjectCode(pcode);
							formRelatedUserService.setRelatedUser(relatedUser);
						}
					}
				} else {
					formRelatedUserService.setRelatedUser(relatedUser);
				}
			}
			
		} catch (Exception e) {
			logger.error("设置流程相关人员出错 {}", e);
		}
	}
	
	/**
	 * 获取表单下步执行信息
	 * @param taskList
	 * @return
	 */
	private List<FormExecuteInfo> getFormExecuteInfo(FormDO form, List<TaskEntity> taskList) {
		List<FormExecuteInfo> exeInfo = Lists.newArrayList();
		try {
			if (ListUtil.isNotEmpty(taskList)) {
				TaskNodeInfo nodeInfo = null;
				String taskUrl = null;
				for (TaskEntity taskInfo : taskList) {
					FormExecuteInfo exe = new FormExecuteInfo();
					exe.setTaskId(taskInfo.getId());
					exe.setCandidateUserList(new ArrayList<SimpleUserInfo>()); //候选人
					if (nodeInfo == null) { //获取当前节点
						nodeInfo = workflowEngineClient.getTaskNode(taskInfo.getId());
						taskUrl = nodeInfo.getFormUrl();
						
						//后面有用
						FcsPmDomainHolder.get().addAttribute("nodeInfo", nodeInfo);
					}
					if (StringUtil.isNotBlank(taskUrl)) { //设置代办任务url
						exe.setTaskUrl(taskUrl + "?formId=" + form.getFormId() + "&taskId="
										+ taskInfo.getId());
						String url = CommonUtil.getRedirectUrl(taskUrl);
						url += "&formId=" + form.getFormId() + "&taskId=" + taskInfo.getId();
						exe.setRedirectTaskUrl(url);
					}
					
					exeInfo.add(exe);
				}
				
				//查询执行人
				List<TaskOpinion> taskOpinions = workflowEngineClient.getTaskUsers(
					String.valueOf(form.getActInstId()), nodeInfo.getNodeId());
				
				if (ListUtil.isNotEmpty(taskOpinions)) {
					for (TaskOpinion taskOpinion : taskOpinions) {
						
						long userId = taskOpinion.getExeUserId();
						
						//查询用户信息
						for (FormExecuteInfo exe : exeInfo) {
							if (taskOpinion.getCheckStatus() != (long) TaskOpinion.STATUS_AGENT
								&& exe.getTaskId().equals(String.valueOf(taskOpinion.getTaskId()))) {//获取当前执行人信息
								exe.setExecute(taskOpinion.getCheckStatus() != (long) TaskOpinion.STATUS_CHECKING);
								if (userId > 0) {
									SysUser user = bpmUserQueryService.findUserByUserId(userId);
									if (user == null)
										continue;
									exe.setUserId(user.getUserId());
									exe.setUserName(user.getFullname());
									exe.setUserAccount(user.getAccount());
									exe.setEmail(user.getEmail());
									exe.setMobile(user.getMobile());
								} else if (ListUtil.isNotEmpty(taskOpinion.getCandidateUserList())) { //查询候选人列表
									for (Long uid : taskOpinion.getCandidateUserList()) {
										SysUser user = bpmUserQueryService.findUserByUserId(uid);
										if (user == null)
											continue;
										SimpleUserInfo userInfo = new SimpleUserInfo();
										userInfo.setUserId(user.getUserId());
										userInfo.setUserAccount(user.getAccount());
										userInfo.setUserName(user.getFullname());
										userInfo.setEmail(user.getEmail());
										userInfo.setMobile(user.getMobile());
										exe.getCandidateUserList().add(userInfo);
									}
									
								}
							}
						}
						
						//代理
						if ((taskOpinion.getCheckStatus() == (long) TaskOpinion.STATUS_AGENT)) {
							List<FormExecuteInfo> agentExeInfo = Lists.newArrayList();
							//查询用户信息
							for (FormExecuteInfo exe : exeInfo) {
								FormExecuteInfo agentExe = new FormExecuteInfo();
								BeanCopier.staticCopy(exe, agentExe);
								agentExeInfo.add(agentExe);
							}
							
							//查询用户信息
							for (FormExecuteInfo exe : agentExeInfo) {
								exe.setSetAgent(true);
								exe.setExecute(true);
								exe.setOpinion(taskOpinion.getOpinion());
								if (exe.getTaskId().equals(String.valueOf(taskOpinion.getTaskId()))) {//获取当前执行人信息
									if (userId > 0) {
										SysUser user = bpmUserQueryService.findUserByUserId(userId);
										if (user == null)
											continue;
										exe.setUserId(user.getUserId());
										exe.setUserName(user.getFullname());
										exe.setUserAccount(user.getAccount());
										exe.setEmail(user.getEmail());
										exe.setMobile(user.getMobile());
									} else if (ListUtil.isNotEmpty(taskOpinion
										.getCandidateUserList())) { //查询候选人列表
										for (Long uid : taskOpinion.getCandidateUserList()) {
											SysUser user = bpmUserQueryService
												.findUserByUserId(uid);
											if (user == null)
												continue;
											SimpleUserInfo userInfo = new SimpleUserInfo();
											userInfo.setUserId(user.getUserId());
											userInfo.setUserAccount(user.getAccount());
											userInfo.setUserName(user.getFullname());
											userInfo.setEmail(user.getEmail());
											userInfo.setMobile(user.getMobile());
											exe.getCandidateUserList().add(userInfo);
										}
									}
								}
							}
							exeInfo.addAll(agentExeInfo);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取表单下步执行信息出错{}", e);
		}
		return exeInfo;
	}
	
	@Override
	public FormBaseResult submit(final SimpleFormOrder order) {
		
		return (FormBaseResult) commonProcess(order, "提交表单", new CheckBeforeProcessService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void check() {
				
				//放到当前线程中
				FcsPmDomainHolder.get().addAttribute("submitFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				
				FormDO form = formDAO.findByFormId(order.getFormId());
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单信息不存在");
				}
				
				FormInfo formInfo = convertFormInfo(form);
				FcsPmDomainHolder.get().addAttribute("formDO", form);
				FcsPmDomainHolder.get().addAttribute("formInfo", formInfo);
				
				//项目是否暂缓
				if (isRelatedProjectPaused(formInfo.getRelatedProjectCode())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"项目已暂缓");
				}
				
				//结果
				FormBaseResult formResult = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				//已通过和审核中得表单不允许提交
				if (FormStatusEnum.SUBMIT == formInfo.getStatus()
					|| FormStatusEnum.APPROVAL == formInfo.getStatus()) {
					if (formResult != null)
						formResult.setFormInfo(formInfo);
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许提交");
				}
				
				if (!formInfo.isValid()) {
					if (formResult != null)
						formResult.setFormInfo(formInfo);
					throw ExceptionFactory.newFcsException(FcsResultEnum.FORM_CHECK_ERROR,
						"数据验证不通过");
				}
				
				if (formInfo.getStatus() == FormStatusEnum.CANCEL
					|| formInfo.getStatus() == FormStatusEnum.BACK) {//从撤销状态或者驳回状态提交
					SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
					auditOrder.setTaskId(formInfo.getTaskId());
					auditOrder.setUserId(order.getUserId());
					auditOrder.setUserAccount(order.getUserAccount());
					auditOrder.setUserName(order.getUserName());
					auditOrder.setVoteContent("重新提交");
					auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_AGREE));
					auditOrder.setFormId(formInfo.getFormId());
					auditOrder.setReSubmit(true);
					FcsBaseResult result = auditProcess(auditOrder);
					if (!result.isSuccess()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
							StringUtil.defaultIfBlank(result.getMessage(), "流程处理异常"));
					} else {
						FormBaseResult returnResult = (FormBaseResult) FcsPmDomainHolder.get()
							.getAttribute("result");
						returnResult.setSuccess(true);
						returnResult.setMessage("提交成功");
						returnResult.setFormInfo(formInfo);
						//用url 作为下步执行人信息
						returnResult.setUrl(result.getUrl());
						return;
					}
				}
				
				if (StringUtil.isBlank(formInfo.getFormCode().getFlowCode())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "尚未配置流程");
				}
				
				WorkflowStartOrder startOrder = new WorkflowStartOrder();
				FcsPmDomainHolder.get().addAttribute("startOrder", startOrder);
				WorkflowExtProcessService processService = null;
				String serviceName = formInfo.getFormCode().processServiceName();
				if (StringUtil.isNotBlank(serviceName)) {
					processService = (WorkflowExtProcessService) getBean(serviceName);
				}
				
				if (processService != null) {
					//启动流程前处理
					WorkFlowBeforeProcessOrder beforeOrder = new WorkFlowBeforeProcessOrder();
					beforeOrder.setFormInfo(formInfo);
					BeanCopier.staticCopy(order, beforeOrder);
					FcsBaseResult result = processService.startBeforeProcess(beforeOrder);
					if (!result.isSuccess()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
							"流程启动前置处理出错[" + result.getMessage() + "]");
					}
					startOrder.setFields(processService.makeWorkFlowVar(formInfo));
					FcsPmDomainHolder.get().addAttribute("processService", processService);
				}
			}
		}, new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormDO form = (FormDO) FcsPmDomainHolder.get().getAttribute("formDO");
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				
				WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
				
				if (startOrder != null) { //如果撤销状态重新提交的表单 这个Order为空 不需要继续往下走了
				
					startOrder.setFormInfo(formInfo);
					startOrder.setFormCodeEnum(formInfo.getFormCode());
					startOrder.setProcessId(order.getUserId());
					startOrder.setProcessUserName(order.getUserAccount());
					startOrder.setProcessRealName(order.getUserName());
					
					//启动流程 
					StartFlowResult workflowResult = workflowEngineClient.startFlow(startOrder);
					
					if (workflowResult.isSuccess()) {
						
						FcsPmDomainHolder.get().addAttribute("workflowResult", workflowResult);
						
						form.setRunId(workflowResult.getRunId());
						form.setActInstId(workflowResult.getActInstId());
						form.setActDefId(workflowResult.getActDefId());
						form.setDefId(workflowResult.getDefId());
						form.setFormName(workflowResult.getSubject());
						//更新表单状态
						form.setStatus(FormStatusEnum.SUBMIT.code());
						form.setSubmitTime(now);
						formInfo.setSubmitTime(now);
						formInfo.setStatus(FormStatusEnum.SUBMIT);
						formInfo.setFormName(workflowResult.getSubject());
						if (ListUtil.isNotEmpty(workflowResult.getNextTaskList())) {
							
							TaskEntity taskEntity = workflowResult.getNextTaskList().get(0);
							FcsPmDomainHolder.get().addAttribute("taskName", taskEntity.getName());
							
							form.setTaskId(NumberUtil.parseLong(taskEntity.getId()));
							form.setDetailStatus(taskEntity.getName() + "处理中");
							
							//获取下步执行信息
							List<FormExecuteInfo> formExeInfo = getFormExecuteInfo(form,
								workflowResult.getNextTaskList());
							
							if (ListUtil.isNotEmpty(formExeInfo))
								form.setTaskUserData(JSONArray.toJSONString(formExeInfo));
							formInfo.setFormExecuteInfo(formExeInfo);
						}
						
						//还未设置相关项目编号的情况下，看看提交的时候传过来的项目编号
						if (StringUtil.isBlank(form.getRelatedProjectCode())
							&& StringUtil.isNotBlank(order.getRelatedProjectCode())) {
							form.setRelatedProjectCode(order.getRelatedProjectCode());
							formInfo.setRelatedProjectCode(order.getRelatedProjectCode());
						}
						
						//更新表单的提交人员
						form.setUserId(order.getUserId());
						form.setUserAccount(order.getUserAccount());
						form.setUserName(order.getUserName());
						form.setUserEmail(order.getUserEmail());
						form.setUserMobile(order.getUserMobile());
						form.setDeptId(order.getDeptId() == null ? 0 : order.getDeptId());
						form.setDeptCode(order.getDeptCode());
						form.setDeptName(order.getDeptName());
						form.setDeptPath(order.getDeptPath());
						form.setDeptPathName(order.getDeptPathName());
						//更新表单的提交人员
						formInfo.setUserId(order.getUserId());
						formInfo.setUserAccount(order.getUserAccount());
						formInfo.setUserName(order.getUserName());
						formInfo.setUserEmail(order.getUserEmail());
						formInfo.setUserMobile(order.getUserMobile());
						formInfo.setDeptId(order.getDeptId() == null ? 0 : order.getDeptId());
						formInfo.setDeptCode(order.getDeptCode());
						formInfo.setDeptName(order.getDeptName());
						formInfo.setDeptPath(order.getDeptPath());
						formInfo.setDeptPathName(order.getDeptPathName());
						
						formDAO.update(form);
					} else {
						throw ExceptionFactory.newFcsException(workflowResult.getFcsResultEnum(),
							"流程启动异常");
					}
					
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					
					//将表单信息返回
					result.setFormInfo(formInfo);
					//下步执行人放到url 
					result.setUrl(formInfo.getNextAuditor());
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				WorkflowResult workflowResult = (WorkflowResult) FcsPmDomainHolder.get()
					.getAttribute("workflowResult");
				WorkflowExtProcessService processService = (WorkflowExtProcessService) FcsPmDomainHolder
					.get().getAttribute("processService");
				
				if (processService != null) {
					//启动流程后处理
					try {
						processService.startAfterProcess(formInfo, workflowResult);
					} catch (Exception e) {
						logger.error("启动流程后置处理出错 formInfo: {} {}", formInfo, e);
					}
					
				}
				
				//设置流程处理人员
				String taskName = (String) FcsPmDomainHolder.get().getAttribute("taskName");
				setRelatedUser(formInfo, taskName, 0, 0);
				
				//通知下步执行人
				try {
					notifyNext(formInfo, processService);
				} catch (Exception e) {
					logger.error("通知下步执行人出错 formInfo: {} {}", formInfo, e);
				}
				
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final SimpleFormOrder order) {
		return commonProcess(order, "刪除表单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				//加入到线程池
				FcsPmDomainHolder.get().addAttribute("deleteFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				
				FormDO form = formDAO.findByFormId(order.getFormId());
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FcsPmDomainHolder.get().addAttribute("formDO", form);
				FcsPmDomainHolder.get().addAttribute("formInfo", convertFormInfo(form));
				
				FormStatusEnum status = FormStatusEnum.getByCode(form.getStatus());
				
				//草稿状态才能删除
				if (status != FormStatusEnum.DRAFT) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许删除");
				}
				
				form.setDetailStatus(null);
				form.setTaskUserData(null);
				form.setStatus(FormStatusEnum.DELETED.code());
				formDAO.update(form);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				WorkflowExtProcessService processService = null;
				String serviceName = formInfo.getFormCode().processServiceName();
				if (StringUtil.isNotBlank(serviceName)) {
					processService = (WorkflowExtProcessService) getBean(serviceName);
				}
				//删除表单后处理
				if (processService != null) {
					try {
						processService.deleteAfterProcess(formInfo);
					} catch (Exception e) {
						logger.error("删除表单后置处理出错 formInfo: {} {}", formInfo, e);
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult auditProcess(final SimpleFormAuditOrder order) {
		return commonProcess(order, "流程审核", new CheckBeforeProcessService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void check() {
				
				//加入到线程中
				FcsPmDomainHolder.get().addAttribute("auditFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				FcsPmDomainHolder.get().addAttribute("customizeMap", order.getCustomizeMap());
				
				FormDO form = (FormDO) FcsPmDomainHolder.get().getAttribute("formDO");
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				
				if (form == null)
					form = formDAO.findByFormId(order.getFormId());
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FcsPmDomainHolder.get().addAttribute("formDO", form);
				
				if (formInfo == null)
					formInfo = convertFormInfo(form);
				FcsPmDomainHolder.get().addAttribute("formInfo", formInfo);
				
				WorkflowDoNextOrder doNextOrder = new WorkflowDoNextOrder();
				WorkflowExtProcessService processService = null;
				String serviceName = formInfo.getFormCode().processServiceName();
				
				if (StringUtil.isNotBlank(serviceName)) {
					processService = (WorkflowExtProcessService) getBean(serviceName);
					FcsPmDomainHolder.get().addAttribute("processService", processService);
				}
				
				if (processService != null) {
					if (!order.isReSubmit()
						&& ("1".equals(order.getVoteAgree()) || "2".equals(order.getVoteAgree()))) { //重新提交 && 同意/反对才处理
						//流程下一步前处理(单独事务)
						WorkFlowBeforeProcessOrder beforOrder = new WorkFlowBeforeProcessOrder();
						beforOrder.setFormInfo(formInfo);
						beforOrder.setCustomizeMap(order.getCustomizeMap());
						FcsBaseResult result = processService.doNextBeforeProcess(beforOrder);
						if (!result.isSuccess()) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
								"审核前置处理出错[" + result.getMessage() + "]");
						}
					}
					doNextOrder.setFields(processService.makeWorkFlowVar(formInfo));
				}
				
				FcsPmDomainHolder.get().addAttribute("doNextOrder", doNextOrder);
				
			}
		}, new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormDO form = (FormDO) FcsPmDomainHolder.get().getAttribute("formDO");
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				
				WorkflowDoNextOrder doNextOrder = (WorkflowDoNextOrder) FcsPmDomainHolder.get()
					.getAttribute("doNextOrder");
				
				doNextOrder.setIsBack(order.getIsBack());
				doNextOrder.setNextNodeId(order.getNextNodeId());
				doNextOrder.setNextUser(order.getNextUser());
				doNextOrder.setTaskId(String.valueOf(order.getTaskId()));
				doNextOrder.setVoteAgree(order.getVoteAgree());
				doNextOrder.setVoteContent(order.getVoteContent());
				doNextOrder.setProcessId(order.getUserId());
				doNextOrder.setUserAccount(order.getUserAccount());
				doNextOrder.setProcessRealName(order.getUserName());
				
				WorkflowResult workflowResult = workflowEngineClient.doNext(doNextOrder);
				
				if (workflowResult.isSuccess()) {
					
					FcsPmDomainHolder.get().addAttribute("workflowResult", workflowResult);
					
					if (order.isReSubmit()) {
						form.setStatus(FormStatusEnum.SUBMIT.code());
						formInfo.setStatus(FormStatusEnum.SUBMIT);
					} else if ("2".equals(order.getIsBack())) {//驳回到发起人
						form.setStatus(FormStatusEnum.BACK.code());
						form.setDetailStatus(null);
						formInfo.setStatus(FormStatusEnum.BACK);
					} else {
						form.setStatus(FormStatusEnum.AUDITING.code());
						formInfo.setStatus(FormStatusEnum.AUDITING);
					}
					
					if (ListUtil.isNotEmpty(workflowResult.getNextTaskList())) {
						TaskEntity taskEntity = workflowResult.getNextTaskList().get(0);
						FcsPmDomainHolder.get().addAttribute("taskName", taskEntity.getName());
						form.setTaskId(NumberUtil.parseLong(taskEntity.getId()));
						if (!"2".equals(order.getIsBack())) {
							form.setDetailStatus(taskEntity.getName() + "处理中");
							
							//获取下步执行信息
							List<FormExecuteInfo> formExeInfo = getFormExecuteInfo(form,
								workflowResult.getNextTaskList());
							
							if (ListUtil.isNotEmpty(formExeInfo))
								form.setTaskUserData(JSONArray.toJSONString(formExeInfo));
							formInfo.setFormExecuteInfo(formExeInfo);
							
							//设置执行轨迹
							setTrace(form, order.getUserId(), order.getUserAccount(),
								order.getUserName());
							formInfo.setTrace(form.getTrace());
						} else { //驳回
							form.setTaskUserData(null);
							form.setTrace(null);
							formInfo.setFormExecuteInfo(null);
							formInfo.setTrace(null);
						}
						
					} else if (workflowResult.isFinished()) {
						form.setStatus(FormStatusEnum.APPROVAL.code());
						form.setDetailStatus(null);
						form.setFinishTime(now);
						form.setTaskUserData(null);
						BeanCopier.staticCopy(form, formInfo);
						formInfo.setStatus(FormStatusEnum.APPROVAL);
						formInfo.setFormExecuteInfo(null);
						
						//设置执行轨迹
						setTrace(form, order.getUserId(), order.getUserAccount(),
							order.getUserName());
						formInfo.setTrace(form.getTrace());
						
					} else if (StringUtil.isNotEmpty(form.getTaskUserData())) { //更新当前执行状态
						try {
							List<FormExecuteInfo> formExeInfo = FormUtil.parseTaskUserData(form
								.getTaskUserData());
							for (FormExecuteInfo exeInfo : formExeInfo) {
								if (exeInfo.getTaskId().equals(String.valueOf(order.getTaskId()))) {
									//更新执行情况
									exeInfo.setExecute(true);
								}
							}
							form.setTaskUserData(JSONArray.toJSONString(formExeInfo));
							formInfo.setFormExecuteInfo(formExeInfo);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
						
						//设置执行轨迹
						setTrace(form, order.getUserId(), order.getUserAccount(),
							order.getUserName());
						formInfo.setTrace(form.getTrace());
					}
					
					//还未设置相关项目编号的情况下，看看提交的时候传过来的项目编号
					if (StringUtil.isBlank(form.getRelatedProjectCode())
						&& StringUtil.isNotBlank(order.getRelatedProjectCode())) {
						form.setRelatedProjectCode(order.getRelatedProjectCode());
					}
					
					formDAO.update(form);
					
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					//下步执行人放到url 
					result.setUrl(formInfo.getNextAuditor());
				} else {
					throw ExceptionFactory.newFcsException(workflowResult.getFcsResultEnum(),
						StringUtil.defaultIfBlank(workflowResult.getMessage(), "流程处理异常"));
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				WorkflowResult workflowResult = (WorkflowResult) FcsPmDomainHolder.get()
					.getAttribute("workflowResult");
				
				WorkflowExtProcessService processService = (WorkflowExtProcessService) FcsPmDomainHolder
					.get().getAttribute("processService");
				
				//设置自定义参数
				workflowResult.setCustomizeMap(order.getCustomizeMap());
				
				//流程审核后处理
				if (processService != null) {
					//流程下步执行
					try {
						processService.doNextAfterProcess(formInfo, workflowResult);
					} catch (Exception e) {
						logger.error("审核后置处理出错 formInfo: {} {}", formInfo, e);
					}
				}
				
				//流程完成处理
				if (workflowResult.isFinished()) {
					
					try {
						if (processService != null)
							processService.endFlowProcess(formInfo, workflowResult);
						
					} catch (Exception e) {
						logger.error("审核完成处理出错 formInfo: {} {}", formInfo, e);
					}
					
					//修改提交人的状态 - 结束同意
					formRelatedUserService.updateSubmitExeStatus(ExeStatusEnum.END_AGREE,
						order.getVoteAgree(), formInfo.getFormId());
				}
				
				//设置流程处理人员
				String taskName = (String) FcsPmDomainHolder.get().getAttribute("taskName");
				setRelatedUser(formInfo, taskName, order.getUserId(), order.getTaskId());
				
				//驳回不通知下步执行人,重新提交上面已经发过一次
				if (!"2".equals(order.getIsBack()) && !order.isReSubmit()) {
					notifyNext(formInfo, processService);
				}
				
				//通知结果
				notifyResult(formInfo, processService);
				
				//更新任务执行状态
				formRelatedUserService.updateExeStatus(ExeStatusEnum.EXCUTED, order.getVoteAgree(),
					order.getVoteContent(), order.getTaskId(), order.getUserId());
				
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult cancel(final CancelFormOrder order) {
		return commonProcess(order, "撤销表单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				FcsPmDomainHolder.get().addAttribute("cancelFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				
				FormDO form = formDAO.findByFormId(order.getFormId());
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				FormInfo formInfo = convertFormInfo(form);
				FcsPmDomainHolder.get().addAttribute("formDO", form);
				FcsPmDomainHolder.get().addAttribute("formInfo", formInfo);
				
				FormStatusEnum status = FormStatusEnum.getByCode(form.getStatus());
				
				//提交状态才能撤销
				if (status != FormStatusEnum.SUBMIT) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许撤销");
				}
				WorkflowRecoverOrder recoverOrder = new WorkflowRecoverOrder();
				recoverOrder.setBackToStart("0");
				recoverOrder.setOpinion(StringUtil.isBlank(order.getVoteContent()) ? "撤销表单" : order
					.getVoteContent());
				recoverOrder.setRunId(form.getRunId());
				recoverOrder.setUserId(order.getUserId());
				WorkflowResult result = workflowEngineClient.defRecover(recoverOrder);
				if (result.isSuccess()) {
					form.setStatus(FormStatusEnum.CANCEL.code());
					form.setDetailStatus(null);
					form.setTaskUserData(null);
					
					if (ListUtil.isNotEmpty(result.getNextTaskList())) {
						form.setTaskId(NumberUtil
							.parseLong(result.getNextTaskList().get(0).getId()));
					}
					form.setTrace(null);
					formInfo.setTrace(null);
					formInfo.setTaskUserData(null);
					formInfo.setFormExecuteInfo(null);
					formInfo.setStatus(FormStatusEnum.CANCEL);
					
					formDAO.update(form);
					
					FcsPmDomainHolder.get().addAttribute("workflowResult", result);
				} else {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(), "流程处理异常");
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				WorkflowResult workflowResult = (WorkflowResult) FcsPmDomainHolder.get()
					.getAttribute("workflowResult");
				
				WorkflowExtProcessService processService = null;
				String serviceName = formInfo.getFormCode().processServiceName();
				if (StringUtil.isNotBlank(serviceName)) {
					processService = (WorkflowExtProcessService) getBean(serviceName);
				}
				
				//撤销表单后处理
				if (processService != null) {
					try {
						processService.cancelAfterProcess(formInfo, workflowResult);
					} catch (Exception e) {
						logger.error("撤销表单后置处理出错 formInfo: {} {}", formInfo, e);
					}
				}
				
				//删掉未执行的任务
				formRelatedUserService.deleteWaiting(formInfo.getFormId());
				
				return null;
			}
		});
	}
	
	/**
	 * 任务交办
	 * @param order
	 * @return
	 */
	@Override
	public FcsBaseResult taskAssign(final TaskAssignFormOrder order) {
		
		return commonProcess(order, "任务交办", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				//加入到线程
				FcsPmDomainHolder.get().addAttribute("assignFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				
				FormDO form = formDAO.findByFormId(order.getFormId());
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FormInfo formInfo = convertFormInfo(form);
				FcsPmDomainHolder.get().addAttribute("formInfo", formInfo);
				
				WorkflowAssignOrder assignOrder = new WorkflowAssignOrder();
				BeanCopier.staticCopy(order, assignOrder);
				assignOrder.setUserId(order.getUserId());
				WorkflowResult result = workflowEngineClient.taskAssign(assignOrder);
				
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(), "流程处理异常");
				} else {
					
					//交办设置交办人
					FcsBaseResult assignResult = formRelatedUserService.taskAssign(order);
					
					if (assignResult.isSuccess()) {
						// 流程处理service
						WorkflowExtProcessService processService = null;
						String serviceName = formInfo.getFormCode().processServiceName();
						if (StringUtil.isNotBlank(serviceName)) {
							processService = (WorkflowExtProcessService) getBean(serviceName);
						}
						
						//交办人
						FormRelatedUserInfo assignMan = (FormRelatedUserInfo) assignResult
							.getReturnObject();
						
						List<FormExecuteInfo> exeInfo = formInfo.getFormExecuteInfo();
						List<FormExecuteInfo> newExeInfo = Lists.newArrayList();
						if (ListUtil.isNotEmpty(exeInfo)) {
							//改变表单执行人情况
							for (FormExecuteInfo eInfo : exeInfo) {
								if (StringUtil.equals(eInfo.getTaskId(),
									String.valueOf(assignOrder.getTaskId()))) {
									FormExecuteInfo newEInfo = new FormExecuteInfo();
									BeanCopier.staticCopy(eInfo, newEInfo);
									if (eInfo.getCandidateUserList() != null)
										eInfo.getCandidateUserList().clear();
									eInfo.setUserId(assignMan.getUserId());
									eInfo.setUserName(assignMan.getUserName());
									eInfo.setUserAccount(assignMan.getUserAccount());
									eInfo.setEmail(assignMan.getUserEmail());
									eInfo.setMobile(assignMan.getUserMobile());
									newExeInfo.add(eInfo);
								} else {
									newExeInfo.add(eInfo);
								}
							}
							formInfo.setFormExecuteInfo(newExeInfo);
							form.setTaskUserData(JSONArray.toJSON(formInfo.getFormExecuteInfo())
								.toString());
							
							//通知交办人
							notifyAssign(formInfo, assignMan, processService);
							
							//设置执行轨迹
							setTrace(form, order.getUserId(), order.getUserAccount(),
								order.getUserName());
							formInfo.setTrace(form.getTrace());
							
							FcsBaseResult returnResult = (FcsBaseResult) FcsPmDomainHolder.get()
								.getAttribute("result");
							
							//下步执行人放到url 
							returnResult.setUrl(formInfo.getNextAuditor());
							
							//更新表单
							formDAO.update(form);
						}
					}
				}
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 终止流程
	 * @param order
	 * @return
	 */
	@Override
	public FcsBaseResult endWorkflow(final SimpleFormAuditOrder order) {
		return commonProcess(order, "手动结束流程", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				//加入到线程
				FcsPmDomainHolder.get().addAttribute("endFormOrder", order);
				
				//把当前操作人放到线程中
				SimpleUserInfo currentUser = new SimpleUserInfo();
				BeanCopier.staticCopy(order, currentUser, UnBoxingConverter.getInstance());
				currentUser.setEmail(order.getUserEmail());
				currentUser.setMobile(order.getUserMobile());
				FcsPmDomainHolder.get().addAttribute("currentUser", currentUser);
				FcsPmDomainHolder.get().addAttribute("customizeMap", order.getCustomizeMap());
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormDO form = formDAO.findByFormId(order.getFormId());
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				FormInfo formInfo = convertFormInfo(form);
				FcsPmDomainHolder.get().addAttribute("formInfo", formInfo);
				
				//FormStatusEnum status = FormStatusEnum.getByCode(form.getStatus());
				WorkflowDoNextOrder doNextOrder = new WorkflowDoNextOrder();
				doNextOrder.setIsBack(order.getIsBack());
				doNextOrder.setNextNodeId(order.getNextNodeId());
				doNextOrder.setNextUser(order.getNextUser());
				doNextOrder.setTaskId(String.valueOf(order.getTaskId()));
				doNextOrder.setVoteAgree(order.getVoteAgree());
				doNextOrder.setVoteContent(StringUtil.isBlank(order.getVoteContent()) ? "作废"
					: order.getVoteContent());
				doNextOrder.setProcessId(order.getUserId());
				doNextOrder.setUserId(order.getUserId());
				doNextOrder.setUserName(order.getUserName());
				doNextOrder.setUserAccount(order.getUserAccount());
				doNextOrder.setProcessRealName(order.getUserName());
				
				WorkflowResult workflowResult = workflowEngineClient.endingWorkflow(doNextOrder);
				if (workflowResult.isSuccess()) {
					
					FcsPmDomainHolder.get().addAttribute("workflowResult", workflowResult);
					
					form.setFinishTime(now);
					if (form.getUserId() == order.getUserId()) {//自己提交的流程结束
						form.setStatus(FormStatusEnum.END.code());
					} else {
						form.setStatus(FormStatusEnum.DENY.code());
					}
					form.setDetailStatus(null);
					form.setTaskUserData(null);
					
					BeanCopier.staticCopy(form, formInfo);
					formInfo.setStatus(FormStatusEnum.getByCode(form.getStatus()));
					
					//设置执行轨迹
					setTrace(form, order.getUserId(), order.getUserAccount(), order.getUserName());
					formInfo.setTrace(form.getTrace());
					
					formDAO.update(form);
				} else {
					throw ExceptionFactory.newFcsException(workflowResult.getFcsResultEnum(),
						"流程处理异常");
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("formInfo");
				WorkflowResult workflowResult = (WorkflowResult) FcsPmDomainHolder.get()
					.getAttribute("workflowResult");
				
				WorkflowExtProcessService processService = null;
				String serviceName = formInfo.getFormCode().processServiceName();
				if (StringUtil.isNotBlank(serviceName)) {
					processService = (WorkflowExtProcessService) getBean(serviceName);
				}
				if (formInfo.getStatus() == FormStatusEnum.DENY) {
					if (processService != null) {
						try {
							processService.manualEndFlowProcess(formInfo, workflowResult);
						} catch (Exception e) {
							logger.error("终止后置处理出错：{}", e);
						}
					}
					//通知结果
					notifyResult(formInfo, processService);
				} else if (formInfo.getStatus() == FormStatusEnum.END) {
					if (processService != null) {
						try {
							processService.selfEndProcess(formInfo, workflowResult);
						} catch (Exception e) {
							logger.error("提交人终止后置处理出错：{}", e);
						}
					}
				}
				
				//更新任务执行状态
				formRelatedUserService.updateExeStatus(ExeStatusEnum.EXCUTED, order.getVoteAgree(),
					order.getVoteContent(), order.getTaskId(), order.getUserId());
				//修改提交人的状态-结束
				formRelatedUserService.updateSubmitExeStatus(
					formInfo.getStatus() == FormStatusEnum.DENY ? ExeStatusEnum.END_DISAGREE
						: ExeStatusEnum.END, order.getVoteAgree(), formInfo.getFormId());
				
				return null;
			}
		});
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		_applicationContext = applicationContext;
	}
	
	private FormInfo convertFormInfo(FormDO form) {
		FormInfo formInfo = new FormInfo();
		BeanCopier.staticCopy(form, formInfo);
		formInfo.setFormCode(FormCodeEnum.getByCode(form.getFormCode()));
		formInfo.setStatus(FormStatusEnum.getByCode(form.getStatus()));
		formInfo.setFormExecuteInfo(FormUtil.parseTaskUserData(form.getTaskUserData()));
		return formInfo;
	}
	
	public static ApplicationContext getApplicationContext() {
		return _applicationContext;
	}
	
	public static Object getBean(String beanName) throws BeansException {
		return _applicationContext.getBean(beanName);
	}
	
	@Override
	public FormInfo findByFormId(long formId) {
		if (formId <= 0) {
			return null;
		}
		FormDO form = formDAO.findByFormId(formId);
		if (form == null) {
			return null;
		} else {
			FormInfo info = convertFormInfo(form);
			return info;
		}
	}
	
	@Override
	public FcsBaseResult updateForm(FormInfo formInfo) {
		FcsBaseResult result = createResult();
		try {
			
			FormDO formDO = new FormDO();
			BeanCopier.staticCopy(formInfo, formDO);
			formDO.setFormCode(formInfo.getFormCode().code());
			formDO.setStatus(formInfo.getStatus().code());
			formDO.setTaskUserData(formInfo.getFormExecuteInfo() == null ? null : JSONArray.toJSON(
				formInfo.getFormExecuteInfo()).toString());
			formDAO.update(formDO);
			result.setSuccess(true);
			result.setMessage("更新表单成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新表单出错");
			logger.error("更新表单出错{}", e);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public QueryBaseBatchResult<FormInfo> queryPage(FormQueryOrder order) {
		QueryBaseBatchResult<FormInfo> batchResult = new QueryBaseBatchResult<FormInfo>();
		
		try {
			FormDO quryForm = new FormDO();
			BeanCopier.staticCopy(order, quryForm, UnBoxingConverter.getInstance());
			
			List<String> statusList = Lists.newArrayList();
			
			if (ListUtil.isNotEmpty(order.getStatusList())) {
				for (FormStatusEnum s : order.getStatusList()) {
					statusList.add(s.getCode());
				}
			}
			
			if (order.getStatus() != null) {
				quryForm.setStatus(order.getStatus().code());
			}
			
			if (order.getFormCode() != null) {
				quryForm.setFormCode(order.getFormCode().code());
			}
			
			long totalCount = formDAO.findByConditionCount(quryForm, statusList);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormDO> list = formDAO.findByCondition(quryForm, statusList, order.getSortCol(),
				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<FormInfo> pageList = new ArrayList<FormInfo>(list.size());
			for (FormDO item : list) {
				pageList.add(convertFormInfo(item));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询表单信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public List<WorkflowTaskInfo> backTaskList(long userId) {
		List<BackTaskDO> backList = busiDAO.backTaskList(userId);
		List<WorkflowTaskInfo> taskList = Lists.newArrayList();
		if (ListUtil.isNotEmpty(backList)) {
			for (BackTaskDO task : backList) {
				WorkflowTaskInfo info = new WorkflowTaskInfo();
				info.setSubject(task.getSubject());
				info.setName(task.getTaskName());
				info.setCreateDate(task.getCreateTime());
				info.setCreateTime(task.getTaskStartTime());
				info.setCreateDateStr(DateUtil.getFormat("yyyy-MM-dd HH:mm").format(
					task.getTaskStartTime()));
				info.setCreator(task.getCreator());
				FormCodeEnum formCode = FormCodeEnum.getByCode(task.getFormCode());
				if (formCode != null)
					info.setTaskUrl(CommonUtil.getRedirectUrl(formCode.getEditUrl()) + "&formId="
									+ task.getFormId());
				taskList.add(info);
			}
		}
		return taskList;
	}
	
	/**
	 * 设置执行轨迹
	 * @param form
	 * @param userId
	 * @param userAccount
	 * @param userName
	 */
	private void setTrace(FormDO form, long userId, String userAccount, String userName) {
		if (StringUtil.isEmpty(form.getTrace())) {
			form.setTrace(userId + "," + userAccount + "," + userName);
		} else {
			form.setTrace(form.getTrace() + "->" + userId + "," + userAccount + "," + userName);
		}
	}
	
	/**
	 * 项目是否暂缓
	 * @param projectCode
	 * @return
	 */
	private boolean isRelatedProjectPaused(String projectCode) {
		
		if (StringUtil.isBlank(projectCode) || projectCode.contains(",")) {
			return false;
		} else if (projectCode.startsWith("QB") || projectCode.startsWith("F")
					|| projectCode.startsWith("JJ")) { //签报 理财 经纪业务
			return false;
		} else {
			ProjectInfo project = projectService.queryByCode(projectCode, false);
			if (project != null && project.getStatus() == ProjectStatusEnum.PAUSE) {
				return true;
			} else {
				return false;
			}
		}
	}
}
