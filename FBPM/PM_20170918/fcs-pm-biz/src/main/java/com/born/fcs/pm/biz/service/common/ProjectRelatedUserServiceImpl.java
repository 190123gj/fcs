package com.born.fcs.pm.biz.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FManagerbChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectRelatedUserDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysOrg;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.pm.integration.common.PropertyConfigService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.RelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectRelatedUserService")
public class ProjectRelatedUserServiceImpl extends BaseAutowiredDomainService implements
																				ProjectRelatedUserService {
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	SysParameterService sysParameterService;
	
	@Override
	public QueryBaseBatchResult<ProjectRelatedUserInfo> queryPage(ProjectRelatedUserQueryOrder order) {
		QueryBaseBatchResult<ProjectRelatedUserInfo> batchResult = new QueryBaseBatchResult<ProjectRelatedUserInfo>();
		try {
			ProjectRelatedUserDO relatedUser = new ProjectRelatedUserDO();
			BeanCopier.staticCopy(order, relatedUser);
			if (order.getIsCurrent() != null) {
				relatedUser.setIsCurrent(order.getIsCurrent().code());
			}
			if (order.getIsDel() != null) {
				relatedUser.setIsDel(order.getIsDel().code());
			}
			if (order.getUserType() != null) {
				relatedUser.setUserType(order.getUserType().code());
			}
			long totalCount = projectRelatedUserDAO.findByConditionCount(relatedUser,
				order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			List<ProjectRelatedUserDO> dataList = projectRelatedUserDAO.findByCondition(
				relatedUser, order.getDeptIdList(), order.getSortCol(), order.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			List<ProjectRelatedUserInfo> list = Lists.newArrayList();
			for (ProjectRelatedUserDO DO : dataList) {
				list.add(convertDO2Info(DO));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目相关人员失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FcsBaseResult setRelatedUser(final ProjectRelatedUserOrder order) {
		
		return commonProcess(order, "设置项目项目相关人员", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				if (order.getUserType() == ProjectRelatedUserTypeEnum.MANUAL_GRANT
					|| order.getUserType() == ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER
					|| order.getUserType() == ProjectRelatedUserTypeEnum.COUNCIL_JUDGE) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.FUNCTION_NOT_OPEN,
						"当前接口不支持");
				}
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//查询当前人员配置
				ProjectRelatedUserDO relatedUser = new ProjectRelatedUserDO();
				BeanCopier.staticCopy(order, relatedUser);
				ProjectRelatedUserTypeEnum userType = order.getUserType();
				if (userType == null) {
					userType = ProjectRelatedUserTypeEnum.OTHER;
				}
				
				relatedUser.setUserType(userType.code());
				relatedUser.setIsCurrent(BooleanEnum.IS.code());
				relatedUser.setIsDel(BooleanEnum.NO.code());
				relatedUser.setRawAddTime(now);
				
				//从bmp查询用户获取mobile和email
				setUserMobileAndEmail(relatedUser);
				
				//从bpm查询并设置部门信息
				setUserDept(relatedUser);
				
				//查询项目  相同类型 同一个人员
				ProjectRelatedUserDO queryDO = new ProjectRelatedUserDO();
				
				queryDO.setUserType(relatedUser.getUserType());
				queryDO.setProjectCode(order.getProjectCode());
				
				//流程、其他人、手动授权的不同人员每次都新增
				if (userType == ProjectRelatedUserTypeEnum.OTHER
					|| userType == ProjectRelatedUserTypeEnum.MANUAL_GRANT
					|| userType == ProjectRelatedUserTypeEnum.FLOW_RELATED
					|| userType == ProjectRelatedUserTypeEnum.FLOW_RELATED_SUB_SYSTEM)
					queryDO.setUserId(relatedUser.getUserId());
				queryDO.setIsCurrent(BooleanEnum.IS.code());
				queryDO.setIsDel(BooleanEnum.NO.code());
				
				//查询已经存在的人员
				List<ProjectRelatedUserDO> exists = projectRelatedUserDAO.findByCondition(queryDO,
					null, null, null, 0, 20);
				if (ListUtil.isNotEmpty(exists)) {
					for (ProjectRelatedUserDO exist : exists) { //已经存在的
						if (exist.getUserId() != relatedUser.getUserId()
							|| exist.getDeptId() != relatedUser.getDeptId()) { //人员变动或者部门变动,记录成历史
							exist.setIsCurrent(BooleanEnum.NO.code());
							exist.setTransferTime(now);
							if (order.isDelOrinigal()) {
								exist.setIsDel(BooleanEnum.IS.code());
							}
							exist.setTransferRelatedId(projectRelatedUserDAO.insert(relatedUser));
							projectRelatedUserDAO.update(exist);
						}
					}
				} else { //直接新增
					if (StringUtil.equals(relatedUser.getDeptCode(), sysParameterService
						.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code()))) {
						//公司领导不需要记录部门ID（为了互相之前看不到处理的数据）
						relatedUser.setDeptId(0);
					}
					projectRelatedUserDAO.insert(relatedUser);
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public FcsBaseResult setCouncilJudges(final String projectCode, final List<Long> judgeIds,
											final long councilId) {
		logger.info("设置项目评委权限,projectCode {} , judgeIds {} , councilId {}", projectCode, judgeIds,
			councilId);
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					//删除原来的
					projectRelatedUserDAO.deleteCoucilJudges(projectCode, String.valueOf(councilId));
					//插入新的
					if (ListUtil.isNotEmpty(judgeIds)) {
						for (Long userId : judgeIds) {
							ProjectRelatedUserDO relatedUser = new ProjectRelatedUserDO();
							relatedUser.setUserId(userId);
							relatedUser.setProjectCode(projectCode);
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.COUNCIL_JUDGE.code());
							relatedUser.setIsCurrent(BooleanEnum.IS.code());
							relatedUser.setIsDel(BooleanEnum.NO.code());
							relatedUser.setRemark(String.valueOf(councilId));
							//补齐联系方式
							setUserMobileAndEmail(relatedUser);
							//补齐部门
							setUserDept(relatedUser);
							
							projectRelatedUserDAO.insert(relatedUser);
						}
						result.setSuccess(true);
						result.setMessage("设置项目评委权限成功");
					} else {
						result.setSuccess(false);
						result.setMessage("评委为空");
					}
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("设置项目评委权限出错");
					logger.error("设置项目评委权限出错：{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult setRiskTeamMember(final String projectCode, final List<Long> memberIds) {
		logger.info("设置项目风险处置小组成员权限,projectCode {} , memberIds {}", projectCode, memberIds);
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					//删除原来的
					projectRelatedUserDAO.deleteByProjectCodeAndUserType(projectCode,
						ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER.code());
					//插入新的
					if (ListUtil.isNotEmpty(memberIds)) {
						for (Long userId : memberIds) {
							ProjectRelatedUserDO relatedUser = new ProjectRelatedUserDO();
							relatedUser.setUserId(userId);
							relatedUser.setProjectCode(projectCode);
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER
								.code());
							relatedUser.setIsCurrent(BooleanEnum.IS.code());
							relatedUser.setIsDel(BooleanEnum.NO.code());
							relatedUser.setRemark("风险处置小组成员");
							//补齐联系方式
							setUserMobileAndEmail(relatedUser);
							//补齐部门
							setUserDept(relatedUser);
							
							projectRelatedUserDAO.insert(relatedUser);
						}
						result.setSuccess(true);
						result.setMessage("设置项目风险小组成员成功");
					} else {
						result.setSuccess(false);
						result.setMessage("小组成员为空");
					}
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("设置项目风险小组成员权限出错");
					logger.error("设置项目风险小组成员权限出错：{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult manualGrant(final String projectCode, final String remark,
										final SimpleUserInfo... users) {
		
		logger.info("授权项目相关人员 projectCode:{}, {}", projectCode, users);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					
					if (users != null) {
						ProjectRelatedUserDO queryDO = new ProjectRelatedUserDO();
						queryDO.setProjectCode(projectCode);
						for (SimpleUserInfo user : users) {
							//查询是否已经授权
							queryDO.setUserId(user.getUserId());
							queryDO.setUserType(ProjectRelatedUserTypeEnum.MANUAL_GRANT.code());
							if (projectRelatedUserDAO.findByConditionCount(queryDO, null) <= 0) {
								ProjectRelatedUserDO relatedUser = new ProjectRelatedUserDO();
								BeanCopier.staticCopy(user, relatedUser);
								relatedUser.setUserEmail(user.getEmail());
								relatedUser.setProjectCode(projectCode);
								relatedUser.setUserMobile(user.getMobile());
								relatedUser.setUserType(ProjectRelatedUserTypeEnum.MANUAL_GRANT
									.code());
								relatedUser.setIsCurrent(BooleanEnum.IS.code());
								relatedUser.setIsDel(BooleanEnum.NO.code());
								
								//补齐用户mobile和email
								setUserMobileAndEmail(relatedUser);
								//补齐部门信息
								setUserDept(relatedUser);
								projectRelatedUserDAO.insert(relatedUser);
							}
						}
						
						result.setSuccess(true);
						result.setMessage("授权成功");
					} else {
						result.setSuccess(false);
						result.setMessage("授权人员为空");
					}
					
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("授权相关人员出错");
					logger.error("授权相关人员出错：{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult manualRevoke(final String projectCode, final long... userIds) {
		
		logger.info("取消授权项目相关人员 projectCode:{}, {}", projectCode, userIds);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					if (userIds != null) {
						ProjectRelatedUserDO queryDO = new ProjectRelatedUserDO();
						queryDO.setProjectCode(projectCode);
						for (long userId : userIds) {
							queryDO.setUserId(userId);
							queryDO.setUserType(ProjectRelatedUserTypeEnum.MANUAL_GRANT.code());
							List<ProjectRelatedUserDO> users = projectRelatedUserDAO
								.findByCondition(queryDO, null, null, null, 0, 50);
							if (users != null) {
								for (ProjectRelatedUserDO user : users) {
									projectRelatedUserDAO.deleteById(user.getRelatedId());
								}
							}
						}
					}
					result.setSuccess(true);
					result.setMessage("取消授权成功");
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("取消授权相关人员出错");
					logger.error("取消授权相关人员出错：{}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult revokeFlowRelated(String projectCode, long userId) {
		logger.info("取消项目流程处理人员授权  projectCode:{}, {}", projectCode, userId);
		FcsBaseResult result = createResult();
		try {
			projectRelatedUserDAO.revokeFlowRelated(projectCode, userId);
			result.setSuccess(true);
			result.setMessage("取消成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("取消项目流程处理人员授权出错");
			logger.error("取消项目流程处理人员授权出错 {}", e);
		}
		return result;
	}
	
	/**
	 * 从bmp查询用户部门信息
	 * @param userAccount
	 */
	private void setUserDept(ProjectRelatedUserDO relatedUser) {
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
				logger.info("查询用户主部门信息完成  {} ： {}", relatedUser.getUserAccount(), org);
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
	private void setUserMobileAndEmail(ProjectRelatedUserDO relatedUser) {
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
	
	private ProjectRelatedUserInfo convertDO2Info(ProjectRelatedUserDO DO) {
		if (DO == null)
			return null;
		ProjectRelatedUserInfo info = new ProjectRelatedUserInfo();
		BeanCopier.staticCopy(DO, info);
		info.setUserType(ProjectRelatedUserTypeEnum.getByCode(DO.getUserType()));
		if (info.getUserType() == null) {
			info.setUserType(ProjectRelatedUserTypeEnum.OTHER);
		}
		info.setIsCurrent(BooleanEnum.getByCode(DO.getIsCurrent()));
		info.setIsDel(BooleanEnum.getByCode(DO.getIsDel()));
		return info;
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManager(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO
			.findCurrentByProjectCodeAndUserType(projectCode,
				RelatedUserTypeEnum.BUSI_MANAGER.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerb(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO
			.findCurrentByProjectCodeAndUserType(projectCode,
				RelatedUserTypeEnum.BUSI_MANAGERB.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerbByPhase(String projectCode,
															ChangeManagerbPhaseEnum phase) {
		
		if (StringUtil.isBlank(projectCode))
			return null;
		
		if (phase == null)
			return getBusiManagerb(projectCode);
		
		//查询最新阶段性变更业务经理B的申请
		FManagerbChangeApplyDO applyDO = FManagerbChangeApplyDAO
			.findLatestPhasesChangeApply(projectCode);
		
		//没有就直接取当前业务经理B
		if (applyDO == null || StringUtil.isBlank(applyDO.getChangePhases()))
			return getBusiManagerb(projectCode);
		
		boolean hasMatch = false;
		String phases[] = applyDO.getChangePhases().split(",");
		if (phases != null && phases.length > 0) {
			for (String pha : phases) {
				ChangeManagerbPhaseEnum p = ChangeManagerbPhaseEnum.getByCode(pha);
				if (p == phase) {
					hasMatch = true;
					break;
				}
			}
		}
		
		if (!hasMatch)
			return getBusiManagerb(projectCode);
		
		//有匹配的申请，就取申请变更后的人员
		ProjectRelatedUserDO userDO = new ProjectRelatedUserDO();
		userDO.setProjectCode(projectCode);
		userDO.setUserId(applyDO.getNewBid());
		userDO.setUserName(applyDO.getNewBname());
		userDO.setUserAccount(applyDO.getNewBaccount());
		//补全信息
		setUserDept(userDO);
		setUserMobileAndEmail(userDO);
		ProjectRelatedUserInfo info = convertDO2Info(userDO);
		info.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
		info.setIsCurrent(BooleanEnum.IS);
		info.setIsDel(BooleanEnum.NO);
		info.setRelatedId(0);
		info.setRawAddTime(applyDO.getChangeStartTime());
		info.setRemark(phase.getMessage() + ProjectRelatedUserTypeEnum.BUSI_MANAGERB.message());
		return info;
	}
	
	@Override
	public ProjectRelatedUserInfo getRiskManager(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO
			.findCurrentByProjectCodeAndUserType(projectCode,
				RelatedUserTypeEnum.RISK_MANAGER.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public ProjectRelatedUserInfo getLegalManager(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO
			.findCurrentByProjectCodeAndUserType(projectCode,
				RelatedUserTypeEnum.LEGAL_MANAGER.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public ProjectRelatedUserInfo getFinancialManager(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO
			.findCurrentByProjectCodeAndUserType(projectCode,
				RelatedUserTypeEnum.FINANCIAL_MANAGER.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public ProjectRelatedUserInfo getOrignalRiskManager(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<ProjectRelatedUserDO> users = projectRelatedUserDAO.findByProjectCodeAndUserType(
			projectCode, RelatedUserTypeEnum.RISK_MANAGER.code());
		if (ListUtil.isNotEmpty(users)) {
			return convertDO2Info(users.get(0));
		} else {
			return null;
		}
	}
	
	@Override
	public List<SimpleUserInfo> getBusiDirector(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			//			String fzrRole = sysParameterService
			//				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
			ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
			if (projectInfo != null) {
				List<SysUser> fzrList = bpmUserQueryService.findChargeByOrgId(projectInfo
					.getDeptId());
				if (ListUtil.isNotEmpty(fzrList)) {
					for (SysUser fzr : fzrList) {
						SimpleUserInfo user = new SimpleUserInfo();
						user.setUserId(fzr.getUserId());
						user.setUserAccount(fzr.getAccount());
						user.setUserName(fzr.getFullname());
						user.setMobile(fzr.getMobile());
						user.setEmail(fzr.getEmail());
						list.add(user);
					}
				}
				//list = getDeptRoleUser(projectInfo.getDeptCode(), fzrRole);
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
			}
		} catch (Exception e) {
			logger.error("查询项目业务部总监出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getDirector(String deptCode) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			//			String fzrRole = sysParameterService
			//				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
			//			list = getDeptRoleUser(deptCode, fzrRole);
			List<SysUser> fzrList = bpmUserQueryService.findChargeByOrgCode(deptCode);
			if (ListUtil.isNotEmpty(fzrList)) {
				for (SysUser fzr : fzrList) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserId(fzr.getUserId());
					user.setUserAccount(fzr.getAccount());
					user.setUserName(fzr.getFullname());
					user.setMobile(fzr.getMobile());
					user.setEmail(fzr.getEmail());
					list.add(user);
				}
			}
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
		} catch (Exception e) {
			logger.error("查询部门总监出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getFgfz(String deptCode) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			String fgfzPosName = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_POSITION.code());
			Org dept = bpmUserQueryService.findDeptByOrgCode(deptCode);
			return getDeptPosUser(dept.getCode(), fgfzPosName);
		} catch (Exception e) {
			logger.error("查询部门分管副总出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getPosDeptUser(long deptId, String posName) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			Org org = bpmUserQueryService.findDeptById(deptId);
			if (org != null) {
				logger.info("开始查询指定部门中指定职位（岗位）人员 deptId {} , posName {}", deptId, posName);
				//岗位 = 部门编码_职位编码
				String jobCode = org.getCode().toLowerCase() + "_" + posName;
				List<SysUser> users = bpmUserQueryService.findUserByJobCode(jobCode);
				if (ListUtil.isNotEmpty(users)) {
					for (SysUser user : users) {
						SimpleUserInfo simpleUser = new SimpleUserInfo();
						simpleUser.setUserId(user.getUserId());
						simpleUser.setUserName(user.getFullname());
						simpleUser.setUserAccount(user.getAccount());
						simpleUser.setEmail(user.getEmail());
						simpleUser.setMobile(user.getMobile());
						list.add(simpleUser);
					}
				}
			}
			logger.info("开始查询指定部门中指定职位（岗位）人员 完成：users {}", list);
		} catch (Exception e) {
			logger.error("查询指定部门中指定职位（岗位）人员出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getDeptPosUser(String deptCode, String posName) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			logger.info("开始查询指定部门中指定职位（岗位）人员 deptCode {} , posName {}", deptCode, posName);
			//岗位 = 部门编码_职位编码
			String jobCode = deptCode.toLowerCase() + "_" + posName;
			List<SysUser> users = bpmUserQueryService.findUserByJobCode(jobCode);
			if (ListUtil.isNotEmpty(users)) {
				for (SysUser user : users) {
					SimpleUserInfo simpleUser = new SimpleUserInfo();
					simpleUser.setUserId(user.getUserId());
					simpleUser.setUserName(user.getFullname());
					simpleUser.setUserAccount(user.getAccount());
					simpleUser.setEmail(user.getEmail());
					simpleUser.setMobile(user.getMobile());
					list.add(simpleUser);
				}
			}
			logger.info("开始查询指定部门中指定职位（岗位）人员 完成：users {}", list);
		} catch (Exception e) {
			logger.error("查询指定部门中指定职位（岗位）人员出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getDeptRoleUser(String deptCode, String roleName) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			logger.info("开始查询指定部门中指定角色人员 deptCode {} , roleName {}", deptCode, roleName);
			List<SysUser> deptUsers = bpmUserQueryService.findUserByDeptCode(deptCode);
			List<SysUser> roleUsers = bpmUserQueryService.findUserByRoleAlias(roleName);
			if (ListUtil.isNotEmpty(deptUsers) && ListUtil.isNotEmpty(roleUsers)) {
				//拥有指定角色的人员
				Map<Long, SysUser> userIds = new HashMap<Long, SysUser>();
				for (SysUser user : roleUsers) {
					userIds.put(user.getUserId(), user);
				}
				//筛选部门中拥有指定角色的人员
				for (SysUser user : deptUsers) {
					if (userIds.containsKey(user.getUserId())) {
						SimpleUserInfo simpleUser = new SimpleUserInfo();
						simpleUser.setUserId(user.getUserId());
						simpleUser.setUserName(user.getFullname());
						simpleUser.setUserAccount(user.getAccount());
						simpleUser.setEmail(user.getEmail());
						simpleUser.setMobile(user.getMobile());
						list.add(simpleUser);
					}
				}
			}
			logger.info("开始查询指定部门中指定角色人员完成：users {}", list);
		} catch (Exception e) {
			logger.error("查询指定部门指定角色人员出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getRoleDeptUser(long deptId, String roleName) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			logger.info("开始查询指定部门中指定角色人员 deptId {} , roleName {}", deptId, roleName);
			Org dept = bpmUserQueryService.findDeptByOrgId(deptId);
			List<SysUser> deptUsers = bpmUserQueryService.findUserByDeptId(String.valueOf(dept
				.getId()));
			List<SysUser> roleUsers = bpmUserQueryService.findUserByRoleAlias(roleName);
			if (ListUtil.isNotEmpty(deptUsers) && ListUtil.isNotEmpty(roleUsers)) {
				//拥有指定角色的人员
				Map<Long, SysUser> userIds = new HashMap<Long, SysUser>();
				for (SysUser user : roleUsers) {
					userIds.put(user.getUserId(), user);
				}
				//筛选部门中拥有指定角色的人员
				for (SysUser user : deptUsers) {
					if (userIds.containsKey(user.getUserId())) {
						SimpleUserInfo simpleUser = new SimpleUserInfo();
						simpleUser.setUserId(user.getUserId());
						simpleUser.setUserName(user.getFullname());
						simpleUser.setUserAccount(user.getAccount());
						simpleUser.setEmail(user.getEmail());
						simpleUser.setMobile(user.getMobile());
						list.add(simpleUser);
					}
				}
			}
			logger.info("开始查询指定部门中指定角色人员完成：users {}", list);
		} catch (Exception e) {
			logger.error("查询指定部门指定角色人员出错：{}", e);
		}
		return list;
	}
	
	@Override
	public boolean isBelongToDept(UserDetailInfo userDetail, String deptCode) {
		boolean isBelong = false;
		try {
			logger.info("查询指定人是否属于某部门 ,userDetail {} deptCode {}", userDetail, deptCode);
			if (userDetail != null && StringUtil.isNotBlank(deptCode)) {
				Org org = bpmUserQueryService.findDeptByCode(deptCode);
				if (org != null && userDetail.isBelong2Dept(org.getId())) {
					isBelong = true;
				}
			}
		} catch (Exception e) {
			logger.info("查询指定人是否属于某部门出错 ,userDetail {} deptCode {}", userDetail, deptCode, e);
			
		}
		return isBelong;
	}
	
	@Override
	public boolean isBelong2Dept(long userId, String deptCode) {
		boolean isBelong = false;
		try {
			logger.info("查询指定人是否属于某部门 ,userId {} deptCode {}", userId, deptCode);
			if (userId > 0 && StringUtil.isNotBlank(deptCode)) {
				Org org = bpmUserQueryService.findDeptByCode(deptCode);
				if (org != null) {
					UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
					if (userDetail != null && userDetail.isBelong2Dept(org.getId())) {
						isBelong = true;
					}
				}
			}
		} catch (Exception e) {
			logger.info("查询指定人是否属于某部门出错 ,userId {} deptCode {}", userId, deptCode, e);
			
		}
		return isBelong;
	}
	
	@Override
	protected FcsBaseResult createResult() {
		return new FormBaseResult();
	}
}
