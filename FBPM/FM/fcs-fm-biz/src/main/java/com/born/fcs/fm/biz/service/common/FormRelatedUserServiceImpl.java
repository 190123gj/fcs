package com.born.fcs.fm.biz.service.common;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.exception.FcsFmBizException;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.FormRelatedUserDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.BpmUserQueryService;
import com.born.fcs.fm.integration.bpm.WorkflowEngineClient;
import com.born.fcs.fm.integration.bpm.service.client.user.SysOrg;
import com.born.fcs.fm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.fm.integration.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.fm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.ConfigParamProperty;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.RelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.RelatedUserResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("formRelatedUserService")
public class FormRelatedUserServiceImpl extends BaseAutowiredDomainService implements
																			FormRelatedUserService {
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserServicePmClient;
	
	@Autowired
	WorkflowEngineClient workflowEngineClient;
	
	@Override
	public QueryBaseBatchResult<FormRelatedUserInfo> queryPage(FormRelatedUserQueryOrder order) {
		QueryBaseBatchResult<FormRelatedUserInfo> batchResult = new QueryBaseBatchResult<FormRelatedUserInfo>();
		
		try {
			
			FormRelatedUserDO relatedUser = new FormRelatedUserDO();
			
			BeanCopier.staticCopy(order, relatedUser);
			
			if (order.getUserType() != null) {
				relatedUser.setUserType(order.getUserType().code());
			}
			
			if (order.getExeStatus() != null) {
				relatedUser.setExeStatus(order.getExeStatus().code());
			}
			
			if (order.getFormCode() != null) {
				relatedUser.setFormCode(order.getFormCode().code());
			}
			
			long totalCount = formRelatedUserDAO.findByConditionCount(relatedUser,
				order.getDeptIdList(), order.getExeStatusList(), order.getExcludeList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormRelatedUserDO> dataList = formRelatedUserDAO.findByCondition(relatedUser,
				order.getDeptIdList(), order.getExeStatusList(), order.getExcludeList(),
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			
			List<FormRelatedUserInfo> list = Lists.newArrayList();
			for (FormRelatedUserDO DO : dataList) {
				list.add(convertDO2Info(DO));
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询相关人员失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public long queryCount(FormRelatedUserQueryOrder order) {
		long totalCount = 0;
		try {
			FormRelatedUserDO relatedUser = new FormRelatedUserDO();
			BeanCopier.staticCopy(order, relatedUser);
			if (order.getUserType() != null) {
				relatedUser.setUserType(order.getUserType().code());
			}
			if (order.getExeStatus() != null) {
				relatedUser.setExeStatus(order.getExeStatus().code());
			}
			if (order.getFormCode() != null) {
				relatedUser.setFormCode(order.getFormCode().code());
			}
			totalCount = formRelatedUserDAO.findByConditionCount(relatedUser,
				order.getDeptIdList(), order.getExeStatusList(), order.getExcludeList());
			
			return totalCount;
		} catch (Exception e) {
			logger.error("查询相关人员失败" + e.getMessage(), e);
		}
		return totalCount;
	}
	
	@Override
	public FcsBaseResult setRelatedUser(final FormRelatedUserOrder order) {
		
		return commonProcess(order, "表单相关人员", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				//查询当前人员配置
				FormRelatedUserDO relatedUser = new FormRelatedUserDO();
				BeanCopier.staticCopy(order, relatedUser);
				if (order.getUserType() == null) {
					relatedUser.setUserType(RelatedUserTypeEnum.OTHER.code());
				} else {
					relatedUser.setUserType(order.getUserType().code());
				}
				
				if (order.getFormCode() != null) {
					relatedUser.setFormCode(order.getFormCode().code());
				}
				
				if (order.getUserType() == FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN) {
					relatedUser.setTaskStartTime(now); //任务开始时间
					relatedUser.setTaskName("发起人提交");
					if (StringUtil.isEmpty(order.getTaskOpinion()))
						relatedUser.setTaskOpinion(String.valueOf(TaskOpinion.STATUS_AGREE));
				}
				
				if (order.getTaskId() > 0) {
					if (order.getExeStatus() == null) {
						relatedUser.setExeStatus(ExeStatusEnum.WAITING.code());
					} else {
						relatedUser.setExeStatus(order.getExeStatus().code());
					}
					relatedUser.setTaskStartTime(now); //任务开始时间
					if (order.getExeStatus() == ExeStatusEnum.AGENT_SET) {
						relatedUser.setTaskEndTime(now); //被代理任务结束
						relatedUser.setTaskOpinion(String.valueOf(TaskOpinion.STATUS_AGENT));
					}
				} else if (order.getExeStatus() == null) {
					relatedUser.setExeStatus(ExeStatusEnum.NOT_TASK.code());
				} else {
					relatedUser.setExeStatus(order.getExeStatus().code());
				}
				
				relatedUser.setRawAddTime(now);
				
				//从bmp查询用户获取mobile和email
				setUserMobileAndEmail(relatedUser);
				
				//从bpm查询并设置部门信息
				setUserDept(relatedUser);
				
				//查询表单 相同类型的人员
				FormRelatedUserDO queryDO = new FormRelatedUserDO();
				queryDO.setFormId(order.getFormId());
				queryDO.setUserType(relatedUser.getUserType());
				queryDO.setProjectCode(order.getProjectCode());
				//流程的不同人员每次都新增
				queryDO.setUserId(relatedUser.getUserId());
				if (order.getTaskId() > 0) {
					queryDO.setTaskId(order.getTaskId());
				}
				
				//查询已经存在的人员
				List<FormRelatedUserDO> exists = formRelatedUserDAO.findByCondition(queryDO, null,
					null, null, null, null, 0, 50);
				if (ListUtil.isNotEmpty(exists)) {
					for (FormRelatedUserDO exist : exists) { //已经存在的
						if (exist.getUserId() != relatedUser.getUserId()
							|| exist.getDeptId() != relatedUser.getDeptId()) { //人员变动或者部门变动,删掉原来的 
							formRelatedUserDAO.deleteById(exist.getRelatedId());
							formRelatedUserDAO.insert(relatedUser);
							//如果存在一条代理的记录并且现在又有同一个人来处理
						} else if (order.getExeStatus() != ExeStatusEnum.AGENT_SET
									&& ExeStatusEnum.AGENT_SET.code().equals(exist.getExeStatus())) {
							formRelatedUserDAO.insert(relatedUser);
						}
					}
				} else { //直接新增
					if (StringUtil.equals(relatedUser.getDeptCode(), sysParameterServiceClient
						.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code()))) {
						//公司领导不需要记录部门ID（为了互相之前看不到处理的数据）
						relatedUser.setDeptId(0);
					}
					formRelatedUserDAO.insert(relatedUser);
				}
				
				FcsFmDomainHolder.get().addAttribute("relatedUser", relatedUser);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				FormRelatedUserDO relatedUser = (FormRelatedUserDO) FcsFmDomainHolder.get()
					.getAttribute("relatedUser");
				//设置到项目相关人员中
				if (relatedUser != null && StringUtil.isNotBlank(relatedUser.getProjectCode())) {
					ProjectRelatedUserOrder pOrder = new ProjectRelatedUserOrder();
					BeanCopier.staticCopy(relatedUser, pOrder);
					relatedUser.setRelatedId(0);
					pOrder.setUserType(ProjectRelatedUserTypeEnum.FLOW_RELATED);
					projectRelatedUserService.setRelatedUser(pOrder);
				}
				
				return null;
			}
		});
		
	}
	
	@Override
	public FcsBaseResult updateExeStatus(ExeStatusEnum exeStatus, String taskOpinion,
											String remark, long taskId, long userId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (taskId > 0)
				formRelatedUserDAO.updateExeStatus(taskOpinion, exeStatus.code(), remark,
					getSysdate(), taskId, userId);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("更新任务执行状态出错 {}", e);
		}
		
		if (ConfigParamProperty.syncProjectRelatedUser2Pm()) {
			try {
				//执行后把权限同步给PM
				logger.info("开始同步项目权限到项目管理系统 : taskId {} ,userId {}", taskId, userId);
				FormRelatedUserQueryOrder queryOrder = new FormRelatedUserQueryOrder();
				queryOrder.setTaskId(taskId);
				queryOrder.setUserId(userId);
				QueryBaseBatchResult<FormRelatedUserInfo> thisTask = queryPage(queryOrder);
				if (thisTask != null && thisTask.getTotalCount() > 0) {
					FormRelatedUserInfo userInfo = thisTask.getPageList().get(0);
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					BeanCopier.staticCopy(userInfo, relatedUser);
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.FLOW_RELATED_SUB_SYSTEM);
					relatedUser.setRemark(ConfigParamProperty.systemName());
					projectRelatedUserServicePmClient.setRelatedUser(relatedUser);
					logger.info("同步项目权限到项目管理系统 完成：{}", userInfo);
				}
			} catch (Exception e) {
				logger.error("权限同步到项目管理系统出错 : taskId {} ,userId {}", taskId, userId);
			}
		}
		return result;
	}
	
	@Override
	public FcsBaseResult updateSubmitExeStatus(ExeStatusEnum exeStatus, String taskOpinion,
												long formId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (formId > 0)
				formRelatedUserDAO.updateSubmitExeStatus(taskOpinion, exeStatus.code(),
					getSysdate(), formId);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("更新提交人状态出错 {}", e);
		}
		
		if (ConfigParamProperty.syncProjectRelatedUser2Pm()) {
			try {
				
				//执行后把权限同步给PM
				logger.info("开始同步项目权限到项目管理系统 : formId {} ,exeStatus {}", formId, exeStatus);
				FormRelatedUserQueryOrder queryOrder = new FormRelatedUserQueryOrder();
				queryOrder.setFormId(formId);
				queryOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
				QueryBaseBatchResult<FormRelatedUserInfo> thisTask = queryPage(queryOrder);
				if (thisTask != null && thisTask.getTotalCount() > 0) {
					FormRelatedUserInfo userInfo = thisTask.getPageList().get(0);
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					BeanCopier.staticCopy(userInfo, relatedUser);
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.FLOW_RELATED_SUB_SYSTEM);
					relatedUser.setRemark(ConfigParamProperty.systemName());
					projectRelatedUserServicePmClient.setRelatedUser(relatedUser);
					logger.info("同步项目权限到项目管理系统 完成：{}", userInfo);
				}
				
			} catch (Exception e) {
				logger.error("权限同步到项目管理系统出错 : formId {} ,exeStatus {}", formId, exeStatus);
			}
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult deleteWaiting(final long formId) {
		
		logger.info("删除未执行任务  formId：{}", formId);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = new FcsBaseResult();
				try {
					if (formId > 0) {
						
						//查询未执行的任务
						FormRelatedUserDO queryDO = new FormRelatedUserDO();
						queryDO.setFormId(formId);
						queryDO.setExeStatus(ExeStatusEnum.WAITING.code());
						queryDO.setProjectCode("NOTNULL");//查询项目相关的
						List<FormRelatedUserDO> waitingTask = formRelatedUserDAO.findByCondition(
							queryDO, null, null, null, null, null, 0, 999);
						if (ListUtil.isNotEmpty(waitingTask)) {
							Set<String> pCodeAndUserSet = new HashSet<String>();
							List<Long> excludeList = Lists.newArrayList();
							for (FormRelatedUserDO user : waitingTask) {
								excludeList.add(user.getRelatedId());
								pCodeAndUserSet.add(user.getProjectCode() + "|" + user.getUserId());
							}
							
							queryDO.setFormId(0);
							queryDO.setExeStatus(null);
							for (String pCodeAndUser : pCodeAndUserSet) {
								if (StringUtil.isNotBlank(pCodeAndUser)) {
									String[] pCodeAndUserArr = pCodeAndUser.split("\\|");
									queryDO.setProjectCode(pCodeAndUserArr[0]);
									queryDO.setUserId(NumberUtil.parseLong(pCodeAndUserArr[1]));
									long relatedNum = formRelatedUserDAO.findByConditionCount(
										queryDO, null, null, excludeList);
									if (relatedNum == 0) { //除开当前数据如果不存在其他关联数据，取消授权该人员权限
										projectRelatedUserService.revokeFlowRelated(
											queryDO.getProjectCode(), queryDO.getUserId());
									}
								}
							}
						}
						//删掉未执行的任务
						formRelatedUserDAO.deleteWaiting(formId);
					}
					result.setSuccess(true);
					result.setMessage("执行成功");
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("执行出错");
					logger.error("删除未执行任务出错 {}", e);
				}
				
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteNotExecutor(final long processTaskId, final long processManId) {
		
		logger.info("删除非执行人的候选人 processTaskId：{},processManId ：{}", processTaskId, processManId);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					List<FormRelatedUserDO> relatedUsers = formRelatedUserDAO
						.findByTaskId(processTaskId);
					if (ListUtil.isNotEmpty(relatedUsers)) {
						
						Set<String> pCodeAndUserSet = new HashSet<String>();
						List<Long> excludeList = Lists.newArrayList();
						for (FormRelatedUserDO relatedUser : relatedUsers) {
							FormCodeEnum formCode = FormCodeEnum.getByCode(relatedUser
								.getFormCode());
							if (processManId == relatedUser.getUserId()) {
								relatedUser.setUserType(RelatedUserTypeEnum.FLOW_PROCESS_MAN.code());
								if (formCode != null)
									relatedUser.setRemark("【" + formCode.message() + "】" + "流程处理人");
								formRelatedUserDAO.update(relatedUser);
							} else if (RelatedUserTypeEnum.FLOW_CANDIDATE_MAN.code().equals(
								relatedUser.getUserType())) {
								formRelatedUserDAO.deleteById(relatedUser.getRelatedId());
								//没有审核的，并且没有其他流程和项目有管理，取消相关项目权限
								excludeList.add(relatedUser.getRelatedId());
								pCodeAndUserSet.add(relatedUser.getProjectCode() + "|"
													+ relatedUser.getUserId());
							}
						}
						
						//查询除开删掉的数据是否还有其他关联数据
						FormRelatedUserDO queryDO = new FormRelatedUserDO();
						queryDO.setFormId(0);
						queryDO.setExeStatus(null);
						for (String pCodeAndUser : pCodeAndUserSet) {
							if (StringUtil.isNotBlank(pCodeAndUser)) {
								String[] pCodeAndUserArr = pCodeAndUser.split("\\|");
								queryDO.setProjectCode(pCodeAndUserArr[0]);
								queryDO.setUserId(NumberUtil.parseLong(pCodeAndUserArr[1]));
								long relatedNum = formRelatedUserDAO.findByConditionCount(queryDO,
									null, null, excludeList);
								if (relatedNum == 0) { //除开当前数据如果不存在其他关联数据，取消授权该人员权限
									projectRelatedUserService.revokeFlowRelated(
										queryDO.getProjectCode(), queryDO.getUserId());
								}
							}
						}
					}
					result.setSuccess(true);
					result.setMessage("执行成功");
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("执行出错");
					logger.error("删除非执行人的候选人出错:{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult taskAssign(final TaskAssignFormOrder order) {
		logger.info("任务交办后设置相关人员 order {}", order);
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					
					Date now = getSysdate();
					long taskId = NumberUtil.parseLong(order.getTaskId());
					if (taskId == 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
							"任务ID为空");
					}
					List<FormRelatedUserDO> relatedusers = formRelatedUserDAO.findByTaskId(taskId);
					if (ListUtil.isEmpty(relatedusers)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到流程日志记录");
					}
					
					FormRelatedUserDO assign = null;
					for (FormRelatedUserDO relatedUserDO : relatedusers) {
						if (order.getUserId() == relatedUserDO.getUserId()) {//转交后更新状态 
							relatedUserDO.setExeStatus(ExeStatusEnum.TASK_ASSIGN.code());
							relatedUserDO.setTaskOpinion(String
								.valueOf(TaskOpinion.STATUS_DELEGATE));
							relatedUserDO.setRemark(order.getMemo());
							relatedUserDO.setTaskEndTime(now);
							relatedUserDO.setUserType(FormRelatedUserTypeEnum.FLOW_PROCESS_MAN
								.code());
							
							TaskNodeInfo nodeInfo = (TaskNodeInfo) FcsFmDomainHolder.get()
								.getAttribute("nodeInfo");
							if (nodeInfo == null) { //获取当前节点
								nodeInfo = workflowEngineClient.getTaskNode(order.getTaskId());
								FcsFmDomainHolder.get().addAttribute("nodeInfo", nodeInfo);
							}
							if (nodeInfo != null)
								relatedUserDO.setTaskName(nodeInfo.getNodeName());
							formRelatedUserDAO.update(relatedUserDO);
							assign = relatedUserDO;
						} else {//同一个任务只要其中一人执行后删除其他人
							formRelatedUserDAO.deleteById(relatedUserDO.getRelatedId());
						}
					}
					
					if (assign == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到流程日志记录");
					}
					
					//插入新的人员
					FormRelatedUserOrder rOrder = new FormRelatedUserOrder();
					rOrder.setUserId(NumberUtil.parseLong(order.getAssigneeId()));
					rOrder.setUserName(order.getAssigneeName());
					rOrder.setTaskId(taskId);
					rOrder.setTaskName(assign.getTaskName());
					rOrder.setFormId(assign.getFormId());
					rOrder.setUserType(FormRelatedUserTypeEnum.FLOW_PROCESS_MAN);
					rOrder.setProjectCode(assign.getProjectCode());
					rOrder.setFormCode(FormCodeEnum.getByCode(assign.getFormCode()));
					rOrder.setTaskStartTime(now);
					FcsBaseResult setResult = setRelatedUser(rOrder);
					if (!setResult.isSuccess()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"设置交办人失败 " + setResult.getMessage());
					}
					result.setReturnObject(setResult.getReturnObject());
					result.setSuccess(true);
					result.setMessage("执行成功");
				} catch (FcsFmBizException be) {
					result.setSuccess(false);
					result.setMessage(be.getErrorMsg());
					logger.error("任务交办后设置相关人员出错:{}", be);
					if (status != null)
						status.setRollbackOnly();
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("执行出错");
					logger.error("任务交办后设置相关人员出错:{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				
				return result;
			}
		});
	}
	
	/**
	 * 从bmp查询用户部门信息
	 * @param userAccount
	 */
	private void setUserDept(FormRelatedUserDO relatedUser) {
		try {
			if (relatedUser.getDeptId() <= 0 && StringUtil.isBlank(relatedUser.getDeptName())) {
				logger.info("查询用户主部门信息：{}", relatedUser.getUserAccount());
				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
				SysOrg org = serviceProxy.loadPrimaryOrgByUsername(relatedUser.getUserAccount());
				if (org != null) {
					relatedUser.setDeptId(org.getOrgId());
					relatedUser.setDeptCode(org.getCode());
					relatedUser.setDeptName(org.getOrgName());
					relatedUser.setDeptPath(org.getPath());
					relatedUser.setDeptPathName(org.getOrgPathname());
				}
				logger.info("查询用户主部门信息完成  {} ： {}", relatedUser.getUserAccount(), relatedUser);
			}
		} catch (Exception e) {
			logger.error("查询用户主部门信息出错 : {}", e);
		}
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 */
	private void setUserMobileAndEmail(FormRelatedUserDO relatedUser) {
		try {
			//邮箱和手机都为空的时候再查下
			if (StringUtil.isBlank(relatedUser.getUserEmail())
				&& StringUtil.isBlank(relatedUser.getUserMobile())) {
				SysUser sysUser = bpmUserQueryService.findUserByUserId(relatedUser.getUserId());
				if (sysUser != null) {
					relatedUser.setUserId(sysUser.getUserId());
					relatedUser.setUserAccount(sysUser.getAccount());
					relatedUser.setUserName(sysUser.getFullname());
					relatedUser.setUserMobile(sysUser.getMobile());
					relatedUser.setUserEmail(sysUser.getEmail());
				}
			}
		} catch (Exception e) {
			logger.error("查询用户手机和email出错{}", e);
		}
	}
	
	private FormRelatedUserInfo convertDO2Info(FormRelatedUserDO DO) {
		if (DO == null)
			return null;
		FormRelatedUserInfo info = new FormRelatedUserInfo();
		BeanCopier.staticCopy(DO, info);
		info.setUserType(FormRelatedUserTypeEnum.getByCode(DO.getUserType()));
		if (info.getUserType() == null) {
			info.setUserType(FormRelatedUserTypeEnum.OTHER);
		}
		info.setExeStatus(ExeStatusEnum.getByCode(DO.getExeStatus()));
		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
		return info;
	}
	
	@Override
	protected FcsBaseResult createResult() {
		return new RelatedUserResult();
	}
}
