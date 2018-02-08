package com.born.fcs.fm.biz.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.ProjectRelatedUserDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.BpmUserQueryService;
import com.born.fcs.fm.integration.bpm.service.client.user.SysOrg;
import com.born.fcs.fm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.fm.integration.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.fm.integration.common.PropertyConfigService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.RelatedUserResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
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
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserServicePmClient;
	
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
				
				if (order.getUserType() == ProjectRelatedUserTypeEnum.MANUAL_GRANT) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.FUNCTION_NOT_OPEN,
						"当前接口不支持");
				}
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
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
					|| userType == ProjectRelatedUserTypeEnum.FLOW_RELATED)
					queryDO.setUserId(relatedUser.getUserId());
				queryDO.setIsCurrent(BooleanEnum.IS.code());
				queryDO.setIsDel(BooleanEnum.NO.code());
				
				//查询已经存在的人员
				List<ProjectRelatedUserDO> exists = projectRelatedUserDAO.findByCondition(queryDO,
					null, null, null, 0, 50);
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
					if (StringUtil.equals(relatedUser.getDeptCode(), sysParameterServiceClient
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
	public FcsBaseResult setCouncilJudges(String projectCode, List<Long> judgeIds, long councilId) {
		return projectRelatedUserServicePmClient.setCouncilJudges(projectCode, judgeIds, councilId);
	}
	
	@Override
	public FcsBaseResult setRiskTeamMember(String projectCode, List<Long> memberIds) {
		return projectRelatedUserServicePmClient.setRiskTeamMember(projectCode, memberIds);
	}
	
	@Override
	public FcsBaseResult manualGrant(final String projectCode, final String remark,
										final SimpleUserInfo... users) {
		return projectRelatedUserServicePmClient.manualGrant(projectCode, remark, users);
	}
	
	@Override
	public FcsBaseResult manualRevoke(final String projectCode, final long... userIds) {
		return projectRelatedUserServicePmClient.manualRevoke(projectCode, userIds);
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
		return projectRelatedUserServicePmClient.getBusiManager(projectCode);
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerb(String projectCode) {
		return projectRelatedUserServicePmClient.getBusiManagerb(projectCode);
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerbByPhase(String projectCode,
															ChangeManagerbPhaseEnum phase) {
		return projectRelatedUserServicePmClient.getBusiManagerbByPhase(projectCode, phase);
	}
	
	@Override
	public ProjectRelatedUserInfo getRiskManager(String projectCode) {
		return projectRelatedUserServicePmClient.getRiskManager(projectCode);
	}
	
	@Override
	public ProjectRelatedUserInfo getLegalManager(String projectCode) {
		return projectRelatedUserServicePmClient.getLegalManager(projectCode);
	}
	
	@Override
	public ProjectRelatedUserInfo getFinancialManager(String projectCode) {
		return projectRelatedUserServicePmClient.getFinancialManager(projectCode);
	}
	
	@Override
	public ProjectRelatedUserInfo getOrignalRiskManager(String projectCode) {
		return projectRelatedUserServicePmClient.getOrignalRiskManager(projectCode);
	}
	
	@Override
	public List<SimpleUserInfo> getBusiDirector(String projectCode) {
		return projectRelatedUserServicePmClient.getBusiDirector(projectCode);
	}
	
	@Override
	public List<SimpleUserInfo> getDirector(String deptCode) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			//总监岗位 = 部门编码_总监职位编码
			String jobCode = deptCode.toLowerCase()
								+ "_"
								+ sysParameterServiceClient
									.getSysParameterValue(SysParamEnum.SYS_PARAM_DIRECTOR_POSITION
										.code());
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
		} catch (Exception e) {
			logger.error("查询部门总监出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getFgfz(String deptCode) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			String fgfzPosName = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_POSITION.code());
			Org dept = bpmUserQueryService.findDeptByOrgCode(deptCode);
			return getDeptPosUser(dept.getCode(), fgfzPosName);
		} catch (Exception e) {
			logger.error("查询部门分管副总出错：{}", e);
		}
		return list;
	}
	
	@Override
	public List<SimpleUserInfo> getDeptRoleUser(String deptCode, String roleName) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			logger.info("开始查询指定部门中指定角色人员 deptCode {} , roleName {}", deptCode, roleName);
			Org dept = bpmUserQueryService.findDeptByOrgCode(deptCode);
			List<SysUser> deptUsers = bpmUserQueryService.findUserByDeptCode(dept.getCode());
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
	public List<SimpleUserInfo> getPosDeptUser(long deptId, String posName) {
		return projectRelatedUserServicePmClient.getPosDeptUser(deptId, posName);
	}
	
	@Override
	public List<SimpleUserInfo> getDeptPosUser(String deptCode, String posName) {
		return projectRelatedUserServicePmClient.getDeptPosUser(deptCode, posName);
	}
	
	@Override
	protected FcsBaseResult createResult() {
		return new RelatedUserResult();
	}
}
