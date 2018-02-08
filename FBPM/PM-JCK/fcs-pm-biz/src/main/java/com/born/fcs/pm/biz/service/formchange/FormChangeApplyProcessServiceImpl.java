package com.born.fcs.pm.biz.service.formchange;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FormChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeRecordStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("formChangeApplyProcessService")
public class FormChangeApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FormChangeApplyService formChangeApplyService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		//departmentLeader 部门负责人
		//companyLeader 公司领导
		//manager 经办人
		
		List<FlowVarField> fields = Lists.newArrayList();
		
		//自定义参数
		Map<String, Object> customizeMap = (Map<String, Object>) FcsPmDomainHolder.get()
			.getAttribute("customizeMap");
		
		if (customizeMap != null) {
			
			String leaderTaskNodeId = sysParameterService
				.getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
			String deptTaskNodeId = sysParameterService
				.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());
			
			String selNodeId = (String) customizeMap.get("selNodeId");
			String selManager = (String) customizeMap.get("selManager");
			
			if (StringUtil.equals(selNodeId, deptTaskNodeId)) { //选择会签部门
				String signDepts = (String) customizeMap.get("signDepts");
				if (StringUtil.isBlank(signDepts)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"请选择会签部门");
				}
				
				String[] deptArr = signDepts.split(",");
				String fzrRole = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
				
				//负责人去重
				Set<String> fzrUserId = new HashSet<String>();
				for (String deptCode : deptArr) {
					List<SimpleUserInfo> fzrList = projectRelatedUserService.getDeptRoleUser(
						deptCode, fzrRole);
					if (ListUtil.isNotEmpty(fzrList)) {
						for (SimpleUserInfo userInfo : fzrList) {
							fzrUserId.add(String.valueOf(userInfo.getUserId()));
						}
					}
				}
				
				//部门负责人流程变量
				String departmentLeader = "";
				if (!fzrUserId.isEmpty()) {
					int index = 0;
					for (String userId : fzrUserId) {
						if (index == 0) {
							departmentLeader = userId;
						} else {
							departmentLeader += "," + userId;
						}
						index++;
					}
					FlowVarField flowVarField = new FlowVarField();
					flowVarField.setVarName("departmentLeader");
					flowVarField.setVarType(FlowVarTypeEnum.STRING);
					flowVarField.setVarVal(departmentLeader);
					fields.add(flowVarField);
				}
			} else if (StringUtil.equals(selNodeId, leaderTaskNodeId)) { //选择公司领导
				//公司领导流程变量
				String leader = (String) customizeMap.get("leader");
				if (StringUtil.isBlank(leader)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"请选择相关领导");
				}
				FlowVarField flowVarField = new FlowVarField();
				flowVarField.setVarName("companyLeader");
				flowVarField.setVarType(FlowVarTypeEnum.STRING);
				flowVarField.setVarVal(leader);
				fields.add(flowVarField);
				
			} else if (StringUtil.equals(selManager, "YES")) { //选择经办人
			
				JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
				JSONArray operator = json.getJSONArray("operator");
				
				//还要包括本次选择的人员
				String managerId = (String) customizeMap.get("managerId");
				long mId = NumberUtil.parseLong(managerId);
				
				String manager = "0";
				if (operator != null && !operator.isEmpty()) {
					//去重
					Set<String> managerUserId = new HashSet<String>();
					for (Object userId : operator) {
						managerUserId.add(userId.toString());
					}
					
					//包含本次选择的 
					if (mId > 0)
						managerUserId.add(String.valueOf(mId));
					
					int index = 0;
					for (String userId : managerUserId) {
						if (index == 0) {
							manager = userId;
						} else {
							manager += "," + userId;
						}
						index++;
					}
				} else { //本次选择的
					manager = String.valueOf(mId);
				}
				FlowVarField flowVarField = new FlowVarField();
				flowVarField.setVarName("manager");
				flowVarField.setVarType(FlowVarTypeEnum.STRING);
				flowVarField.setVarVal(manager);
				fields.add(flowVarField);
			} else {
				JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
				JSONArray principal = json.getJSONArray("principal");
				String departmentLeader1 = "";
				if (principal != null && !principal.isEmpty()) {
					//去重
					Set<String> fzrUserId = new HashSet<String>();
					for (Object userId : principal) {
						fzrUserId.add(userId.toString());
					}
					int index = 0;
					for (String userId : fzrUserId) {
						if (index == 0) {
							departmentLeader1 = userId;
						} else {
							departmentLeader1 += "," + userId;
						}
						index++;
					}
					FlowVarField flowVarField = new FlowVarField();
					flowVarField.setVarName("departmentLeader1");
					flowVarField.setVarType(FlowVarTypeEnum.STRING);
					flowVarField.setVarVal(departmentLeader1);
					fields.add(flowVarField);
				}
			}
		}
		
		return fields;
	}
	
	/***
	 * 获取经办人和负责人 {'principal':[userId,userId],'operator':[userId,userId]}
	 * @param principalAndOperatorJson
	 * @return
	 */
	private JSONObject getPrincipalAndOperator(String principalAndOperatorJson) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isNotBlank(principalAndOperatorJson)) {
				json = (JSONObject) JSONObject.parse(principalAndOperatorJson);
			}
		} catch (Exception e) {
			logger.error("解析负责人经办人出错 {}", e);
		}
		return json;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		
		FcsBaseResult result = createResult();
		try {
			
			//签报申请
			FormChangeApplyDO apply = formChangeApplyDAO.findByFormId(order.getFormInfo()
				.getFormId());
			
			try {
				SimpleFormOrder submitOrder = (SimpleFormOrder) FcsPmDomainHolder.get()
					.getAttribute("submitFormOrder");
				if (submitOrder != null) {
					String relatedProjectCode = apply.getProjectCode();
					submitOrder.setRelatedProjectCode(relatedProjectCode);
				}
			} catch (Exception e) {
				logger.error("获取提交Order出错：{}", e);
			}
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				//和项目相关的签报
				if (StringUtil.isNotEmpty(apply.getProjectCode())) {
					ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
					if (project != null) {
						if ("FORM".equals(apply.getApplyType())) {
							FormChangeApplyEnum changeForm = FormChangeApplyEnum.getByCode(apply
								.getChangeForm());
							startOrder.setCustomTaskName(project.getProjectName() + "-"
															+ changeForm.message() + "签报");
						} else {
							startOrder.setCustomTaskName(project.getProjectName() + "-项目事项签报");
						}
					}
				} else {
					startOrder.setCustomTaskName(apply.getApplyTitle() + "-其他事项签报");
				}
			}
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("自定义任务名称出错");
			logger.error("设置自定义任务名称出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			
			//签报申请
			FormChangeApplyDO apply = formChangeApplyDAO.findByFormId(formInfo.getFormId());
			
			//表单签报 修改记录状态变更
			if (FormChangeApplyTypeEnum.FORM.code().equals(apply.getApplyType())) {
				formChangeRecordDAO.updateStatus(FormChangeRecordStatusEnum.APPLY_AUDITING.code(),
					formInfo.getFormId());
			}
			//审核中
			apply.setStatus(FormChangeApplyStatusEnum.AUDITING.code());
			formChangeApplyDAO.update(apply);
		} catch (Exception e) {
			logger.error("启动签报流程后处理出错：{} ", e);
		}
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
		if (customizeMap != null) {
			
			String leaderTaskNodeId = sysParameterService
				.getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
			String deptTaskNodeId = sysParameterService
				.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());
			
			String selNodeId = (String) customizeMap.get("selNodeId");
			String selManager = (String) customizeMap.get("selManager");
			
			//选择领导或者选择部门清空 选择了经办人的负责人和经办人信息
			if (StringUtil.equals(selNodeId, leaderTaskNodeId)
				|| StringUtil.equals(selNodeId, deptTaskNodeId)) {
				FormDO formDO = formDAO.findByFormId(formInfo.getFormId());
				formDO.setRemark(null);
				formDAO.update(formDO);
			} else if (StringUtil.equals(selManager, "YES")) { //选择经办人
				//当前操作人
				SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
					"currentUser");
				String managerId = (String) customizeMap.get("managerId");
				long mId = NumberUtil.parseLong(managerId);
				if (mId > 0) { //只记录选择的
					JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
					JSONArray principal = json.getJSONArray("principal");
					JSONArray operator = json.getJSONArray("operator");
					
					if (principal == null)
						principal = new JSONArray();
					if (operator == null)
						operator = new JSONArray();
					
					principal.add(currentUser.getUserId());
					operator.add(mId);
					json.put("principal", principal);
					json.put("operator", operator);
					//记录选了经办人的负责人和经办人
					FormDO formDO = formDAO.findByFormId(formInfo.getFormId());
					formDO.setRemark(json.toString());
					formDAO.update(formDO);
				}
			}
		}
	}
	
	@Override
	public void endFlowProcess(final FormInfo formInfo, WorkflowResult workflowResult) {
		
		//签报申请
		final FormChangeApplyDO apply = formChangeApplyDAO.findByFormId(formInfo.getFormId());
		
		//改变状态单独放到一个事务中处理
		FcsBaseResult result = transactionTemplate
			.execute(new TransactionCallback<FcsBaseResult>() {
				@SuppressWarnings("deprecation")
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = createResult();
					try {
						
						//表单签报 修改记录状态变更
						if (FormChangeApplyTypeEnum.FORM.code().equals(apply.getApplyType())) {
							formChangeRecordDAO.updateStatus(
								FormChangeRecordStatusEnum.APPLY_APPROVAL.code(),
								formInfo.getFormId());
							
							//签报流程审核通过后向档案管理员发送信息
							String fileAdminRole = sysParameterService
								.getSysParameterValue(SysParamEnum.SYS_PARAM_FILE_ADMINISTRATOR_ROLE_NAME
									.code());
							if (StringUtil.isNotBlank(fileAdminRole)) {
								List<SysUser> fileAdmins = bpmUserQueryService
									.findUserByRoleAlias(fileAdminRole);
								if (ListUtil.isNotEmpty(fileAdmins)) {
									
									MessageOrder order = MessageOrder.newSystemMessageOrder();
									order.setMessageSubject("项目决策性签报审核通过提醒");
									order.setMessageTitle("项目决策性签报审核通过提醒");
									String messageContent = apply.getProjectName()
															+ " "
															+ apply.getProjectCode()
															+ " 于 "
															+ DateUtil.simpleFormat(formInfo
																.getSubmitTime())
															+ " 发起了决策性签报并已审核通过";
									SimpleUserInfo[] sendUsers = new SimpleUserInfo[fileAdmins
										.size()];
									for (int i = 0; i < sendUsers.length; i++) {
										SysUser fileAdmin = fileAdmins.get(i);
										SimpleUserInfo sendUser = new SimpleUserInfo();
										sendUser.setUserId(fileAdmin.getUserId());
										sendUser.setUserAccount(fileAdmin.getAccount());
										sendUser.setUserName(fileAdmin.getFullname());
									}
									order.setSendUsers(sendUsers);
									order.setMessageContent(messageContent);
									siteMessageService.addMessageInfo(order);
								}
							}
						}
						
						//需要上会的
						if (BooleanEnum.IS.code().equals(apply.getIsNeedCouncil())) {
							//进入待上会列表
							CouncilApplyOrder order = new CouncilApplyOrder();
							order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
							order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
							order.setProjectCode(apply.getApplyCode());
							order.setProjectName(apply.getApplyTitle());
							order.setCustomerId(0);
							order.setCustomerName("-");
							order.setApplyManId(formInfo.getUserId());
							order.setApplyManName(formInfo.getUserName());
							order.setApplyDeptId(formInfo.getDeptId());
							order.setApplyDeptName(formInfo.getDeptName());
							order.setApplyTime(getSysdate());
							order.setStatus(CouncilApplyStatusEnum.WAIT.code());
							councilApplyService.saveCouncilApply(order);
							
							//上会中
							apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_WAITING.code());
						} else {
							//不需要上会的直接通过 
							apply.setStatus(FormChangeApplyStatusEnum.APPROVAL.code());
						}
						
						formChangeApplyDAO.update(apply);
					} catch (Exception e) {
						result.setSuccess(false);
						result.setMessage("签报审核通过处理出错");
						if (status != null)
							status.setRollbackOnly();
					}
					result.setSuccess(true);
					return result;
				}
			});
		
		//处理完成后再去执行变更
		if (result != null && result.isSuccess()) {
			formChangeApplyService.executeChange(formInfo.getFormId());
		}
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			formChangeRecordDAO.updateStatus(FormChangeRecordStatusEnum.APPLY_DENY.code(),
				formInfo.getFormId());
		} catch (Exception e) {
			logger.error("结束签报流程处理出错：{} ", e);
		}
	}
}
