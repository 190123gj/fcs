//package com.born.fcs.pm.biz.service.common;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallback;
//
//import rop.thirdparty.com.google.common.collect.Lists;
//
//import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
//import com.born.fcs.pm.dal.dataobject.RelatedUserDO;
//import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
//import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
//import com.born.fcs.pm.integration.bpm.service.client.user.SysOrg;
//import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
//import com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceProxy;
//import com.born.fcs.pm.integration.common.PropertyConfigService;
//import com.born.fcs.pm.ws.base.PageComponent;
//import com.born.fcs.pm.ws.enums.BooleanEnum;
//import com.born.fcs.pm.ws.enums.ExeStatusEnum;
//import com.born.fcs.pm.ws.enums.FormCodeEnum;
//import com.born.fcs.pm.ws.enums.RelatedUserTypeEnum;
//import com.born.fcs.pm.ws.enums.SysParamEnum;
//import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
//import com.born.fcs.pm.ws.info.common.ProjectInfo;
//import com.born.fcs.pm.ws.info.common.RelatedUserInfo;
//import com.born.fcs.pm.ws.order.common.RelatedUserOrder;
//import com.born.fcs.pm.ws.order.common.RelatedUserQueryOrder;
//import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
//import com.born.fcs.pm.ws.result.base.FcsBaseResult;
//import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
//import com.born.fcs.pm.ws.result.common.RelatedUserResult;
//import com.born.fcs.pm.ws.service.common.ProjectService;
//import com.born.fcs.pm.ws.service.common.RelatedUserService;
//import com.yjf.common.domain.api.Domain;
//import com.yjf.common.lang.beans.cglib.BeanCopier;
//import com.yjf.common.lang.util.ListUtil;
//import com.yjf.common.lang.util.StringUtil;
//import com.yjf.common.service.base.BeforeProcessInvokeService;
//
//@Service("relatedUserService")
//public class RelatedUserServiceImpl extends BaseAutowiredDomainService implements
//																		RelatedUserService {
//	@Autowired
//	PropertyConfigService propertyConfigService;
//	
//	@Autowired
//	ProjectService projectService;
//	
//	@Autowired
//	BpmUserQueryService bpmUserQueryService;
//	
//	@Autowired
//	SysParameterService sysParameterService;
//	
//	@Override
//	public List<RelatedUserInfo> query(RelatedUserQueryOrder order) {
//		
//		RelatedUserDO relatedUser = new RelatedUserDO();
//		
//		BeanCopier.staticCopy(order, relatedUser);
//		if (order.getIsCurrent() != null) {
//			relatedUser.setIsCurrent(order.getIsCurrent().code());
//		}
//		
//		if (order.getUserType() != null) {
//			relatedUser.setUserType(order.getUserType().code());
//		}
//		if (order.getExeStatus() != null) {
//			relatedUser.setExeStatus(order.getExeStatus().code());
//		}
//		
//		if (order.getFormCode() != null) {
//			relatedUser.setFormCode(order.getFormCode().code());
//		}
//		
//		if (order.getIsDel() != null) {
//			relatedUser.setIsDel(order.getIsDel().code());
//		}
//		
//		List<RelatedUserDO> dataList = relatedUserDAO.findByCondition(relatedUser,
//			order.getDeptIdList(), order.getExeStatusList(), null, null, 0, 999);
//		List<RelatedUserInfo> list = Lists.newArrayList();
//		for (RelatedUserDO DO : dataList) {
//			list.add(convertDO2Info(DO));
//		}
//		
//		return list;
//	}
//	
//	@Override
//	public QueryBaseBatchResult<RelatedUserInfo> queryPage(RelatedUserQueryOrder order) {
//		QueryBaseBatchResult<RelatedUserInfo> batchResult = new QueryBaseBatchResult<RelatedUserInfo>();
//		
//		try {
//			
//			RelatedUserDO relatedUser = new RelatedUserDO();
//			
//			BeanCopier.staticCopy(order, relatedUser);
//			
//			if (order.getIsCurrent() != null) {
//				relatedUser.setIsCurrent(order.getIsCurrent().code());
//			}
//			
//			if (order.getIsDel() != null) {
//				relatedUser.setIsDel(order.getIsDel().code());
//			}
//			
//			if (order.getUserType() != null) {
//				relatedUser.setUserType(order.getUserType().code());
//			}
//			
//			if (order.getExeStatus() != null) {
//				relatedUser.setExeStatus(order.getExeStatus().code());
//			}
//			
//			if (order.getFormCode() != null) {
//				relatedUser.setFormCode(order.getFormCode().code());
//			}
//			
//			long totalCount = relatedUserDAO.findByConditionCount(relatedUser,
//				order.getDeptIdList(), order.getExeStatusList());
//			PageComponent component = new PageComponent(order, totalCount);
//			
//			List<RelatedUserDO> dataList = relatedUserDAO.findByCondition(relatedUser,
//				order.getDeptIdList(), order.getExeStatusList(), order.getSortCol(),
//				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
//			
//			List<RelatedUserInfo> list = Lists.newArrayList();
//			for (RelatedUserDO DO : dataList) {
//				list.add(convertDO2Info(DO));
//			}
//			
//			batchResult.setSuccess(true);
//			batchResult.setPageList(list);
//			batchResult.initPageParam(component);
//		} catch (Exception e) {
//			logger.error("查询相关人员失败" + e.getMessage(), e);
//			batchResult.setSuccess(false);
//			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
//		}
//		return batchResult;
//	}
//	
//	@Override
//	public FcsBaseResult setRelatedUser(final RelatedUserOrder order) {
//		
//		return commonProcess(order, "设置项目/表单相关人员", new BeforeProcessInvokeService() {
//			
//			@Override
//			public Domain before() {
//				
//				Date now = FcsPmDomainHolder.get().getSysDate();
//				
//				//查询当前人员配置
//				RelatedUserDO relatedUser = new RelatedUserDO();
//				BeanCopier.staticCopy(order, relatedUser);
//				if (order.getUserType() == null) {
//					relatedUser.setUserType(RelatedUserTypeEnum.OTHER.code());
//				} else {
//					relatedUser.setUserType(order.getUserType().code());
//				}
//				
//				if (order.getFormCode() != null) {
//					relatedUser.setFormCode(order.getFormCode().code());
//				}
//				
//				if (order.getTaskId() > 0) {
//					if (order.getExeStatus() == null) {
//						relatedUser.setExeStatus(ExeStatusEnum.WAITING.code());
//					} else {
//						relatedUser.setExeStatus(order.getExeStatus().code());
//					}
//				} else if (order.getExeStatus() == null) {
//					relatedUser.setExeStatus(ExeStatusEnum.NOT_TASK.code());
//				} else {
//					relatedUser.setExeStatus(order.getExeStatus().code());
//				}
//				
//				relatedUser.setIsCurrent(BooleanEnum.IS.code());
//				relatedUser.setIsDel(BooleanEnum.NO.code());
//				relatedUser.setRawAddTime(now);
//				
//				//从bmp查询用户获取mobile和email
//				setUserMobileAndEmail(relatedUser);
//				
//				//从bpm查询并设置部门信息
//				setUserDept(relatedUser);
//				
//				//查询项目/表单 相同类型的人员
//				RelatedUserDO queryDO = new RelatedUserDO();
//				queryDO.setFormId(order.getFormId());
//				queryDO.setUserType(relatedUser.getUserType());
//				queryDO.setProjectCode(order.getProjectCode());
//				queryDO.setIsCurrent(BooleanEnum.IS.code());
//				queryDO.setIsDel(BooleanEnum.NO.code());
//				
//				//流程的不同人员每次都新增
//				if (order.getUserType() == RelatedUserTypeEnum.FLOW_PROCESS_MAN
//					|| order.getUserType() == RelatedUserTypeEnum.FLOW_CANDIDATE_MAN
//					|| order.getUserType() == RelatedUserTypeEnum.COUNCIL_JUDGE
//					|| order.getUserType() == RelatedUserTypeEnum.COUNCIL_PARTICIPANT
//					|| order.getUserType() == RelatedUserTypeEnum.OTHER) {
//					queryDO.setUserId(relatedUser.getUserId());
//					if (order.getTaskId() > 0) {
//						queryDO.setTaskId(order.getTaskId());
//					}
//				}
//				
//				//查询已经存在的人员
//				List<RelatedUserDO> exists = relatedUserDAO.findByCondition(queryDO, null, null,
//					null, null, 0, 999);
//				
//				if (ListUtil.isNotEmpty(exists)) {
//					for (RelatedUserDO exist : exists) { //已经存在的
//						if (exist.getUserId() != relatedUser.getUserId()
//							|| exist.getDeptId() != relatedUser.getDeptId()) { //人员变动或者部门变动,记录成历史
//							exist.setIsCurrent(BooleanEnum.NO.code());
//							exist.setTransferTime(now);
//							if (order.isDelOrinigal()) {
//								exist.setIsDel(BooleanEnum.IS.code());
//							}
//							relatedUserDAO.update(exist);
//							relatedUserDAO.insert(relatedUser);
//						} else if (order.getExeStatus() != ExeStatusEnum.AGENT_SET
//									&& ExeStatusEnum.AGENT_SET.code().equals(exist.getExeStatus())) { //如果存在一条代理的记录并且现在又有同一个人来处理
//							relatedUserDAO.insert(relatedUser);
//						}
//					}
//				} else { //直接新增
//					relatedUserDAO.insert(relatedUser);
//				}
//				
//				FcsPmDomainHolder.get().addAttribute("relatedUser", relatedUser);
//				
//				return null;
//			}
//		}, null, null);
//		
//	}
//	
//	@Override
//	public FcsBaseResult setRelatedUserByType(final String projectCode,
//												final RelatedUserTypeEnum userType,
//												final List<SimpleUserInfo> users) {
//		logger.info("设置项目相关人员");
//		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
//			@Override
//			public FcsBaseResult doInTransaction(TransactionStatus status) {
//				FcsBaseResult result = createResult();
//				try {
//					
//					relatedUserDAO.deleteByProjectCodeAndUserType(projectCode, userType.code());
//					
//					if (users != null) {
//						for (SimpleUserInfo user : users) {
//							RelatedUserDO relatedUser = new RelatedUserDO();
//							BeanCopier.staticCopy(user, relatedUser);
//							relatedUser.setUserEmail(user.getEmail());
//							relatedUser.setUserMobile(user.getMobile());
//							relatedUser.setExeStatus(ExeStatusEnum.NOT_TASK.code());
//							relatedUser.setUserType(userType.code());
//							relatedUser.setIsCurrent(BooleanEnum.IS.code());
//							relatedUser.setIsDel(BooleanEnum.NO.code());
//							
//							setUserDept(relatedUser);
//							setUserMobileAndEmail(relatedUser);
//							relatedUserDAO.insert(relatedUser);
//						}
//					}
//					result.setSuccess(true);
//					result.setMessage("设置成功");
//				} catch (Exception e) {
//					result.setSuccess(false);
//					result.setMessage("根据类型设置相关人员出错");
//					logger.error("根据类型设置相关人员出错：{}", e);
//					if (status != null)
//						status.setRollbackOnly();
//				}
//				return result;
//			}
//		});
//		
//	}
//	
//	@Override
//	public FcsBaseResult updateExeStatus(ExeStatusEnum exeStatus, String remark, long taskId,
//											long userId) {
//		FcsBaseResult result = new FcsBaseResult();
//		try {
//			if (taskId > 0)
//				relatedUserDAO.updateExeStatus(exeStatus.code(), remark, taskId, userId);
//			result.setSuccess(true);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			logger.error("更新任务执行状态出错 {}", e);
//		}
//		return result;
//	}
//	
//	@Override
//	public FcsBaseResult updateSubmitExeStatus(ExeStatusEnum exeStatus, long formId) {
//		FcsBaseResult result = new FcsBaseResult();
//		try {
//			if (formId > 0)
//				relatedUserDAO.updateSubmitExeStatus(exeStatus.code(), formId);
//			result.setSuccess(true);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			logger.error("更新提交人状态出错 {}", e);
//		}
//		return result;
//	}
//	
//	@Override
//	public FcsBaseResult deleteWhenCancel(long formId) {
//		FcsBaseResult result = new FcsBaseResult();
//		try {
//			if (formId > 0)
//				relatedUserDAO.deleteWhenCancel(formId);
//			result.setSuccess(true);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			logger.error("更新撤销状态出错 {}", e);
//		}
//		return result;
//	}
//	
//	/**
//	 * 从bmp查询用户部门信息
//	 * @param userAccount
//	 */
//	private void setUserDept(RelatedUserDO relatedUser) {
//		try {
//			if (relatedUser.getDeptId() == 0 && StringUtil.isBlank(relatedUser.getDeptName())) {
//				logger.info("查询用户部门信息：{}", relatedUser.getUserAccount());
//				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
//					propertyConfigService.getBmpServiceUserDetailsService());
//				SysOrg org = serviceProxy.loadPrimaryOrgByUsername(relatedUser.getUserAccount());
//				if (org != null) {
//					relatedUser.setDeptId(org.getOrgId());
//					relatedUser.setDeptCode(org.getCode());
//					relatedUser.setDeptName(org.getOrgName());
//				}
//				logger.info("查询用户部门信息完成  {} ： {}", relatedUser.getUserAccount(), org);
//			}
//		} catch (Exception e) {
//			logger.error("查询用户部门信息出错 : {}", e);
//		}
//	}
//	
//	/**
//	 * 根据用户ID查询用户信息
//	 * @param userId
//	 * @return
//	 */
//	private void setUserMobileAndEmail(RelatedUserDO relatedUser) {
//		try {
//			//邮箱和手机都为空的时候再查下
//			if (StringUtil.isBlank(relatedUser.getUserEmail())
//				&& StringUtil.isBlank(relatedUser.getUserMobile())) {
//				SysUser sysUser = bpmUserQueryService.findUserByUserId(relatedUser.getUserId());
//				if (sysUser != null) {
//					relatedUser.setUserId(sysUser.getUserId());
//					relatedUser.setUserAccount(sysUser.getAccount());
//					relatedUser.setUserName(sysUser.getFullname());
//					relatedUser.setUserMobile(sysUser.getMobile());
//					relatedUser.setUserEmail(sysUser.getEmail());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查询用户手机和email出错{}", e);
//		}
//	}
//	
//	private RelatedUserInfo convertDO2Info(RelatedUserDO DO) {
//		if (DO == null)
//			return null;
//		RelatedUserInfo info = new RelatedUserInfo();
//		BeanCopier.staticCopy(DO, info);
//		info.setUserType(RelatedUserTypeEnum.getByCode(DO.getUserType()));
//		if (info.getUserType() == null) {
//			info.setUserType(RelatedUserTypeEnum.OTHER);
//		}
//		info.setIsCurrent(BooleanEnum.getByCode(DO.getIsCurrent()));
//		info.setIsDel(BooleanEnum.getByCode(DO.getIsDel()));
//		info.setExeStatus(ExeStatusEnum.getByCode(DO.getExeStatus()));
//		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
//		return info;
//	}
//	
//	@Override
//	public RelatedUserInfo getBusiManager(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.BUSI_MANAGER);
//		order.setIsCurrent(BooleanEnum.IS);
//		order.setIsDel(BooleanEnum.NO);
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public RelatedUserInfo getBusiManagerb(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.BUSI_MANAGERB);
//		order.setIsCurrent(BooleanEnum.IS);
//		order.setIsDel(BooleanEnum.NO);
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public RelatedUserInfo getRiskManager(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.RISK_MANAGER);
//		order.setIsCurrent(BooleanEnum.IS);
//		order.setIsDel(BooleanEnum.NO);
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public RelatedUserInfo getLegalManager(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.LEGAL_MANAGER);
//		order.setIsCurrent(BooleanEnum.IS);
//		order.setIsDel(BooleanEnum.NO);
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public RelatedUserInfo getFinancialManager(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.FINANCIAL_MANAGER);
//		order.setIsCurrent(BooleanEnum.IS);
//		order.setIsDel(BooleanEnum.NO);
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public RelatedUserInfo getOrignalRiskManager(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		RelatedUserQueryOrder order = new RelatedUserQueryOrder();
//		order.setProjectCode(projectCode);
//		order.setUserType(RelatedUserTypeEnum.RISK_MANAGER);
//		order.setSortCol("related_id");
//		order.setSortOrder("ASC");
//		List<RelatedUserInfo> users = query(order);
//		if (ListUtil.isNotEmpty(users)) {
//			return users.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getBusiDirector(String projectCode) {
//		if (StringUtil.isBlank(projectCode))
//			return null;
//		List<SimpleUserInfo> list = Lists.newArrayList();
//		try {
//			ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
//			if (projectInfo != null) {
//				//业务部总监岗位 = 业务部门编码_总监职位编码
//				String jobCode = projectInfo.getDeptCode().toLowerCase()
//									+ "_"
//									+ sysParameterService
//										.getSysParameterValue(SysParamEnum.SYS_PARAM_DIRECTOR_POSITION
//											.code());
//				List<SysUser> users = bpmUserQueryService.findUserByJobCode(jobCode);
//				
//				if (ListUtil.isNotEmpty(users)) {
//					for (SysUser user : users) {
//						SimpleUserInfo simpleUser = new SimpleUserInfo();
//						simpleUser.setUserId(user.getUserId());
//						simpleUser.setUserName(user.getFullname());
//						simpleUser.setUserAccount(user.getAccount());
//						simpleUser.setEmail(user.getEmail());
//						simpleUser.setMobile(user.getMobile());
//						list.add(simpleUser);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查询项目业务部总监出错：{}", e);
//		}
//		return list;
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getDirector(String deptCode) {
//		List<SimpleUserInfo> list = Lists.newArrayList();
//		try {
//			//总监岗位 = 部门编码_总监职位编码
//			String jobCode = deptCode.toLowerCase()
//								+ "_"
//								+ sysParameterService
//									.getSysParameterValue(SysParamEnum.SYS_PARAM_DIRECTOR_POSITION
//										.code());
//			List<SysUser> users = bpmUserQueryService.findUserByJobCode(jobCode);
//			if (ListUtil.isNotEmpty(users)) {
//				for (SysUser user : users) {
//					SimpleUserInfo simpleUser = new SimpleUserInfo();
//					simpleUser.setUserId(user.getUserId());
//					simpleUser.setUserName(user.getFullname());
//					simpleUser.setUserAccount(user.getAccount());
//					simpleUser.setEmail(user.getEmail());
//					simpleUser.setMobile(user.getMobile());
//					list.add(simpleUser);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查询部门总监出错：{}", e);
//		}
//		return list;
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getFgfz(String deptCode) {
//		List<SimpleUserInfo> list = Lists.newArrayList();
//		try {
//			//分管副总岗位 = 部门编码_分管副总职位编码
//			String jobCode = deptCode.toLowerCase()
//								+ "_"
//								+ sysParameterService
//									.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_POSITION
//										.code());
//			List<SysUser> users = bpmUserQueryService.findUserByJobCode(jobCode);
//			if (ListUtil.isNotEmpty(users)) {
//				for (SysUser user : users) {
//					SimpleUserInfo simpleUser = new SimpleUserInfo();
//					simpleUser.setUserId(user.getUserId());
//					simpleUser.setUserName(user.getFullname());
//					simpleUser.setUserAccount(user.getAccount());
//					simpleUser.setEmail(user.getEmail());
//					simpleUser.setMobile(user.getMobile());
//					list.add(simpleUser);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查询部门分管副总出错：{}", e);
//		}
//		return list;
//	}
//	
//	@Override
//	protected FcsBaseResult createResult() {
//		return new RelatedUserResult();
//	}
//}
