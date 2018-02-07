package com.born.fcs.face.web.util;

import java.util.List;

import com.born.fcs.face.integration.pm.service.contract.ProjectContractServiceClient;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import rop.thirdparty.com.google.common.collect.Lists;

import com.born.bpm.service.client.user.SysOrg;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.face.integration.bpm.service.BpmUserQueryService;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Job;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.Role;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.stampapply.StampAFileInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 数据权限工具
 * @author wuzj 2016年5月3日
 */
public class DataPermissionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DataPermissionUtil.class);
	
	private static ProjectRelatedUserService projectRelatedUserServiceClient;

	private static ProjectContractService projectContractServiceClient;
	
	private static FormRelatedUserService formRelatedUserServiceClient;
	
	private static SysParameterService sysParameterServiceClient;
	
	private static BpmUserQueryService bpmUserQueryService;
	
	private static FormService formServiceClient;
	
	private static StampApplyService stampApplyServiceClient;
	
	//private static ProjectService projectServiceClient;
	
	static {
		formRelatedUserServiceClient = SpringUtil.getBean("formRelatedUserServiceClient");
		projectRelatedUserServiceClient = SpringUtil.getBean("projectRelatedUserServiceClient");
		projectContractServiceClient = SpringUtil.getBean("projectContractServiceClient");
		sysParameterServiceClient = SpringUtil.getBean("sysParameterServiceClient");
		bpmUserQueryService = SpringUtil.getBean("bpmUserQueryService");
		formServiceClient = SpringUtil.getBean("formServiceClient");
		stampApplyServiceClient = SpringUtil.getBean("stampApplyServiceClient");
		//projectServiceClient = SpringUtil.getBean("projectServiceClient");
	}
	
	/**
	 * 是否有项目查看权限
	 * @param projectCode
	 * @return
	 */
	public static boolean hasViewPermission(String projectCode) {
		
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserQueryOrder order = new ProjectRelatedUserQueryOrder();
				order.setProjectCode(projectCode);
				order.setIsDel(BooleanEnum.NO);
				if (hasAllDataPermission()) { //拥有所有权限
					return true;
				} else if (hasPrincipalDataPermission()) { //拥有部门级别的权限
					UserInfo userInfo = sessionLocal.getUserInfo();
					if (userInfo == null) {
						return false;
					} else {
						order.setUserId(0);
						order.setDeptIdList(userInfo.getDeptIdList());
					}
				} else { //个人权限
					order.setUserId(sessionLocal.getUserId());
					order.setDeptIdList(null);
				}
				QueryBaseBatchResult<ProjectRelatedUserInfo> rData = projectRelatedUserServiceClient
					.queryPage(order);
				if (rData != null && rData.getTotalCount() > 0) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否有表单查询权限
	 * @param formId
	 * @return
	 */
	public static boolean hasViewPermission(long formId) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				FormRelatedUserQueryOrder order = new FormRelatedUserQueryOrder();
				order.setFormId(formId);
				if (hasAllDataPermission()) { //拥有所有权限
					return true;
				} else if (hasPrincipalDataPermission()) { //拥有部门级别的权限
					UserInfo userInfo = sessionLocal.getUserInfo();
					if (userInfo == null) {
						return false;
					} else {
						order.setUserId(0);
						order.setDeptIdList(userInfo.getDeptIdList());
					}
				} else { //个人权限
					order.setUserId(sessionLocal.getUserId());
					order.setDeptIdList(null);
				}
				long count = formRelatedUserServiceClient.queryCount(order);
				if (count > 0) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否项目业务经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isBusiManager(String projectCode) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getBusiManager(projectCode);
				if (rData != null && rData.getUserId() == sessionLocal.getUserId()) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否分支机构支付岗
	 * @return
	 */
	public static boolean isBranchPayPos() {
		String role = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJGZFQRG_ROLE_NAME.code());
		return hasRole(role);
	}
	
	/**
	 * 是否市场营销岗人员
	 * @return
	 */
	public static boolean isScyx() {
		String scyxRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_SCYXG_ROLE_NAME.code());
		return hasRole(scyxRole);
	}
	
	/**
	 * 是否渠道维护人员
	 * @return
	 */
	public static boolean isQdwh() {
		String scyxRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_QDWH_ROLE_NAME.code());
		return hasRole(scyxRole);
	}
	
	/**
	 * 是否业务经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isBusiManager(long userId) {
		String busiManagerRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSI_MANAGER_ROLE_NAME.code());
		return hasRole(userId, busiManagerRole) || isCreditorRightsTransferRole();
	}
	
	/**
	 * 是否业务经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isBusiManager(UserDetailInfo userDetail) {
		String busiManagerRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSI_MANAGER_ROLE_NAME.code());
		return hasRole(userDetail, busiManagerRole);
	}
	
	/**
	 * 是否项目业务经理B
	 * @param projectCode
	 * @return
	 */
	public static boolean isBusiManagerb(String projectCode) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getBusiManagerb(projectCode);
				if (rData != null && rData.getUserId() == sessionLocal.getUserId()) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否项目风险经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isRiskManager(String projectCode) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getRiskManager(projectCode);
				if (rData != null && rData.getUserId() == sessionLocal.getUserId()) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否项目法务经理经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isLegalManager(String projectCode) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getLegalManager(projectCode);
				if (rData != null && rData.getUserId() == sessionLocal.getUserId()) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}

	/**
	 * 得到项目面签岗
	 * @param projectCode
	 * @return
	 */
	public static ProjectRelatedUserInfo  isSignPos(String projectCode) {
		 return getRoleUserInfo(projectCode,ProjectRelatedUserTypeEnum.POS_VISA_INTERVIEW);
	}

	public static boolean  isSignPos() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserQueryOrder order=new ProjectRelatedUserQueryOrder();
				order.setUserId(sessionLocal.getUserId());
				order.setIsCurrent(BooleanEnum.IS);
				order.setUserType(ProjectRelatedUserTypeEnum.POS_VISA_INTERVIEW);
				QueryBaseBatchResult<ProjectRelatedUserInfo> result = projectRelatedUserServiceClient
						.queryPage(order);
				if (result != null && result.getPageList().size()>0) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		return false;
	}
	/**
	 * 得到项目抵质押岗
	 * @param projectCode
	 * @return
	 */
	public static ProjectRelatedUserInfo  isPosPledge(String projectCode) {
		return getRoleUserInfo(projectCode,ProjectRelatedUserTypeEnum.POS_PLEDGE);
	}

	public static ProjectRelatedUserInfo getRoleUserInfo(String projectCode,ProjectRelatedUserTypeEnum userTypeEnum){
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserQueryOrder order=new ProjectRelatedUserQueryOrder();
				order.setProjectCode(projectCode);
				order.setIsCurrent(BooleanEnum.IS);
				order.setUserType(userTypeEnum);
				QueryBaseBatchResult<ProjectRelatedUserInfo> result = projectRelatedUserServiceClient
						.queryPage(order);
				if (result != null && result.getPageList().size()>0) {
					return result.getPageList().get(0);
				}

			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		ProjectRelatedUserInfo relatedUserInfo=new ProjectRelatedUserInfo();
		relatedUserInfo.setUserAccount("");
		relatedUserInfo.setUserName("");
		return relatedUserInfo;
	}


	public static boolean  isHasContractReturn(String projectCode) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				ProjectContractQueryOrder order=new ProjectContractQueryOrder();
				order.setProjectCode(projectCode);
				order.setContractStatus(ContractStatusEnum.RETURN.code());
				QueryBaseBatchResult<ProjectContractResultInfo> result = projectContractServiceClient.query(order);
				if (result != null && result.getPageList().size()>0) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		return false;
	}

	/**
	 * 是否拥有所有数据权限
	 * @return
	 */
	public static boolean hasAllDataPermission() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		UserInfo userInfo = sessionLocal.getUserInfo();
		boolean is = false;
		if (userInfo != null) {
			List<String> roles = userInfo.getRoleAliasList();
			if (ListUtil.isNotEmpty(roles)) {
				String roleName = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_ALL_DATA_PERMISSION_ROLE_NAME
						.code());
				if (StringUtil.isNotBlank(roleName)) {
					roleName = roleName.replaceAll("\r", "").replaceAll("\n", "")
						.replaceAll("，", ",").trim();
					String[] dpRoles = roleName.split(",");
					for (String role : roles) {
						
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						
						if (is)
							break;
						
						role = role.replaceAll("BusinessSys_", "");
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						if (is)
							break;
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是拥有所负责数据权限
	 * @return
	 */
	public static boolean hasPrincipalDataPermission() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		UserInfo userInfo = sessionLocal.getUserInfo();
		boolean is = false;
		if (userInfo != null) {
			List<String> roles = userInfo.getRoleAliasList();
			if (ListUtil.isNotEmpty(roles)) {
				String roleName = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_PRINCIPAL_DATA_PERMISSION_ROLE_NAME
						.code());
				if (StringUtil.isNotBlank(roleName)) {
					roleName = roleName.replaceAll("\r", "").replaceAll("\n", "")
						.replaceAll("，", ",").trim();
					String[] dpRoles = roleName.split(",");
					for (String role : roles) {
						
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						
						if (is)
							break;
						
						role = role.replaceAll("BusinessSys_", "");
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						if (is)
							break;
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是否业务经理
	 * @return
	 */
	public static boolean isBusiManager() {
		String busiManagerRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSI_MANAGER_ROLE_NAME.code());
		return hasRole(busiManagerRole) || isCreditorRightsTransferRole();
	}
	
	/**
	 * 是否业债权转让角色
	 * @return
	 */
	public static boolean isCreditorRightsTransferRole() {
		String role = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CREDITOR_RIGHTS_TRANSFER_ROLE.code());
		return hasRole(role);
	}
	
	/**
	 * 是否业债权转让角色
	 * @return
	 */
	public static boolean isCreditorRightsTransferRole(long userId) {
		String role = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CREDITOR_RIGHTS_TRANSFER_ROLE.code());
		return hasRole(userId, role);
	}
	
	/**
	 * 是否分管副总
	 * @return
	 */
	public static boolean isFgfz() {
		String busiManagerRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME.code());
		return hasRole(busiManagerRole);
	}
	
	/**
	 * 是否总监
	 * @return
	 */
	public static boolean isDirector() {
		String zjPos = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_DIRECTOR_POSITION.code());
		return hasJob(zjPos);
	}
	
	/**
	 * 是否业务部综合岗
	 * @return
	 */
	public static boolean isBusinessZHG() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_ZHG_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否风险经理
	 * @return
	 */
	public static boolean isRiskManager() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_MANAGER_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否总经理秘书
	 * @return
	 */
	public static boolean isManagerSecretary() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否总经理秘书
	 * @return
	 */
	public static boolean isManagerSecretary(UserDetailInfo userDetail) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME.code());
		return hasRole(userDetail, roleName);
	}
	
	/**
	 * 是否总经理秘书
	 * @return
	 */
	public static boolean isManagerSecretaryXH() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_XH_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否总经理秘书
	 * @return
	 */
	public static boolean isManagerSecretaryXH(UserDetailInfo userDetail) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_XH_ROLE_NAME.code());
		return hasRole(userDetail, roleName);
	}
	
	/**
	 * 是否风控秘书
	 * @return
	 */
	public static boolean isRiskSecretary() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否风控秘书
	 * @return
	 */
	public static boolean isRiskSecretary(long userId) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME.code());
		return hasRole(userId, roleName);
	}
	
	/**
	 * 是否风控秘书
	 * @return
	 */
	public static boolean isRiskSecretary(UserDetailInfo userDetail) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME.code());
		return hasRole(userDetail, roleName);
	}
	
	/**
	 * 是否法务经理
	 * @return
	 */
	public static boolean isLegalManager() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_LEGAL_MANAGER_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否法务经理
	 * @return
	 */
	public static boolean isLegalManagerLD() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_LEGAL_MANAGER_LD_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否财务人员
	 * @return
	 */
	public static boolean isFinancialPersonnel() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FINANCIAL_PERSONNEL_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否财务应付岗
	 * @return
	 */
	public static boolean isFinancialYFG() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否信汇财务专员
	 * @return
	 */
	public static boolean isXHCWZY() {
		// BusinessSys_xhcwfzr
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XHCWZY_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否财务应收岗
	 * @return
	 */
	public static boolean isFinancialYSG() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYSG_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否财务资金理财人员
	 * @return
	 */
	public static boolean isFinancialZjlc() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FINANCIAL_ZJLC_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否风险管理部职员（风险经理）
	 * @return
	 */
	public static boolean isRiskZY() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_ZY_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否风险管理部领导
	 * @return
	 */
	public static boolean isRiskLD() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_LD_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否信惠业务人员
	 * @return
	 */
	public static boolean isXinHuiBusiManager(Long userId) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_BUSIMANAGER_ROLE_NAME.code());
		return hasRole(userId, roleName);
		
	}
	
	/**
	 * 是否信惠业务人员
	 * @return
	 */
	public static boolean isXinHuiBusiManager() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_BUSIMANAGER_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否信惠人员
	 * @return
	 */
	public static boolean isXinHuiPersonnel() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_PERSONNEL_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否信惠负责人
	 * @return
	 */
	public static boolean isXinHuiFzr() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//业务部所有部门
				String xinHuiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()); //信惠部所有部门 参数
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				
				//用户详细信息
				UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
				List<Org> orgList = userDetail.getOrgList();
				if (xinHuiDeptCodes != null && orgList != null) {
					String[] depts = xinHuiDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (dept.equals(org.getCode())) {
								//								List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
								//									.getDeptRoleUser(dept, bmfzrCode);
								List<SysUser> userInfos = bpmUserQueryService
									.findChargeByOrgCode(xinHuiDeptCodes);
								if (ListUtil.isNotEmpty(userInfos)) {
									for (SysUser userInfo : userInfos) {
										if (userInfo.getUserId() > 0
											&& userInfo.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("信惠部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 是否信惠负责人
	 * @return
	 */
	public static boolean isXinHuiFzr(UserDetailInfo userDetail) {
		boolean is = false;
		try {
			if (userDetail != null) {
				//信惠部所有部门
				String xinHuiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()); //信惠部所有部门 参数
				if (StringUtil.isNotBlank(xinHuiDeptCodes)) {
					//					String bmfzrCode = sysParameterServiceClient
					//						.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
					List<Org> orgList = userDetail.getOrgList();
					if (orgList != null) {
						String[] busiDeptArr = xinHuiDeptCodes.split(",");
						for (String deptCode : busiDeptArr) {
							if (StringUtil.isNotBlank(deptCode)) {
								for (Org org : orgList) {
									if (StringUtil.equals(deptCode, org.getCode())) {
										//										List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										//											.getDeptRoleUser(deptCode, bmfzrCode);
										List<SysUser> userInfos = bpmUserQueryService
											.findChargeByOrgCode(xinHuiDeptCodes);
										if (ListUtil.isNotEmpty(userInfos)) {
											for (SysUser userInfo : userInfos) {
												if (userInfo.getUserId() > 0
													&& userInfo.getUserId() == userDetail.getId()) {
													is = true;
													break;
												}
											}
										}
									}
									if (is)
										break;
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否信惠部负责人出错{}", e);
		}
		return is;
	}
	
	/**
	 * 是否信惠分管副总
	 * @return
	 */
	public static boolean isXinHuiFGFZ(UserDetailInfo userDetail) {
		boolean is = false;
		try {
			if (userDetail != null) {
				String xinHuiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()); //信惠部所有部门 参数
				if (StringUtil.isNotBlank(xinHuiDeptCodes)) {
					
					List<Org> orgList = userDetail.getOrgList();
					String[] busiDeptArr = xinHuiDeptCodes.split(",");
					for (String deptCode : busiDeptArr) {
						if (StringUtil.isNotBlank(deptCode)) {
							for (Org org : orgList) {
								if (StringUtil.equals(deptCode, org.getCode())) {
									List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										.getFgfz(deptCode);
									if (ListUtil.isNotEmpty(userInfos)) {
										for (SimpleUserInfo userInfo : userInfos) {
											if (userInfo.getUserId() > 0
												&& userInfo.getUserId() == userDetail.getId()) {
												is = true;
												break;
											}
										}
									}
								}
								if (is)
									break;
							}
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否信惠分管副总出错{}", e);
		}
		return is;
	}
	
	/**
	 * 是否风控部门人员
	 * @return
	 */
	public static boolean isRiskdept() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				List<SysOrg> sysOrgs = userInfo.getDeptList();
				if (ListUtil.isNotEmpty(sysOrgs)) {
					String deptCode = sysParameterServiceClient
						.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code());
					for (SysOrg sysOrg : sysOrgs) {
						if (sysOrg.getCode().equalsIgnoreCase(deptCode)) {
							is = true;
							break;
						}
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是否内控部门人员
	 * @return
	 */
	public static boolean isBelongNK() {
		boolean isNK = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			//是否属于内控人员
			isNK = projectRelatedUserServiceClient.isBelongToDept(sessionLocal.getUserDetailInfo(),
				sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_NKHGB_DEPT_CODE.code()));
		}
		return isNK;
	}
	
	/**
	 * 是否综合营运部人员
	 * @return
	 */
	public static boolean isZHYYdept() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				List<SysOrg> sysOrgs = userInfo.getDeptList();
				if (ListUtil.isNotEmpty(sysOrgs)) {
					String deptCode = sysParameterServiceClient
						.getSysParameterValue(SysParamEnum.SYS_PARAM_GHTZB_DEPT_CODE.code());
					for (SysOrg sysOrg : sysOrgs) {
						if (sysOrg.getCode().equalsIgnoreCase(deptCode)) {
							is = true;
							break;
						}
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是否内勤人员
	 * @return
	 */
	public static boolean isNQRole() {
		String roleCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_NQ_ROLE_NAME.code());
		return hasRole(roleCode);
	}
	
	/**
	 * 是否业务总监
	 * @deprecated 不建议使用，建议通过角色判断
	 * @see isBusiFZR(String projectCode)
	 * @return
	 */
	@Deprecated
	public static boolean isBusinessDirector(String projectCode) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				List<SimpleUserInfo> rData = projectRelatedUserServiceClient
					.getBusiDirector(projectCode);
				if (ListUtil.isNotEmpty(rData)) {
					for (SimpleUserInfo user : rData) {
						if (sessionLocal.getUserId() == user.getUserId()) {
							hasPermission = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否系统管理员
	 * @return
	 */
	public static boolean isSystemAdministrator() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否系统管理员
	 * @return
	 */
	public static boolean isSystemAdministrator(long userId) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME.code());
		return hasRole(userId, roleName);
	}
	
	/**
	 * 是否系统管理员
	 * @return
	 */
	public static boolean isSystemAdministrator(UserDetailInfo userDetail) {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME.code());
		return hasRole(userDetail, roleName);
	}
	
	/**
	 * 是否公司领导
	 * @param userId
	 * @return
	 */
	public static boolean isCompanyLeader() {
		return isCompanyLeader(ShiroSessionUtils.getSessionLocal().getUserId());
	}
	
	/**
	 * 是否公司领导
	 * @param userId
	 * @return
	 */
	public static boolean isCompanyLeader(long userId) {
		boolean isLeader = false;
		String deptCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code());
		if (StringUtil.isNotEmpty(deptCode)) {
			List<SysUser> leaders = bpmUserQueryService.findUserByDeptCode(deptCode);
			for (SysUser leader : leaders) {
				if (leader.getUserId() == userId) {
					isLeader = true;
					break;
				}
			}
		}
		return isLeader;
	}
	
	/**
	 * 是否档案管理员
	 * @return
	 */
	public static boolean isFileAdministrator() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FILE_ADMINISTRATOR_ROLE_NAME.code());
		return hasRole(roleName);
	}

	/**
	 * 放款中心负责人
	 * @return
	 */
	public static boolean isLoanLeader() {
		String roleName = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_LOAN_LEADER_ROLE_NAME.code());
		return hasRole(roleName);
	}

	/**
	 * 放款档案岗
	 * @return
	 */
	public static boolean isLoanFile() {
		String roleName = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_LOAN_FILE_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否公司特种纸管理员
	 * @return
	 */
	public static boolean isCompanyPaperAdministrator() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_COMPANY_SPECIALPAPER_ADMINI_ROLE_NAME
				.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否部门特种纸管理员
	 * @return
	 */
	public static boolean isDeptPaperAdministrator() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_DEPT_SPECIALPAPER_ADMIN_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否征信专员
	 * @return
	 */
	public static boolean isCreditCommissioner() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CREDIT_COMMISSIONER_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否公章管理员
	 * @return
	 */
	public static boolean isGZManager() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_GZ_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否法人章管理员
	 * @return
	 */
	public static boolean isFRZManager() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FRZ_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 是否内审专员
	 * @return
	 */
	public static boolean isInternalAuditor() {
		String roleName = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_INTERNAL_AUDITOR_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 业务部门分管副总
	 * @return
	 */
	public static boolean isBusinessFgfz() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
				List<Org> orgList = userDetail.getOrgList();
				//业务部所有部门
				String busiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
				if (busiDeptCodes != null && orgList != null) {
					String[] depts = busiDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (org.getCode().equals(dept)) {
								//查询业务部分管副总
								List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
									.getFgfz(dept);
								if (ListUtil.isNotEmpty(busiFgfzs)) {
									for (SimpleUserInfo fgfz : busiFgfzs) {
										if (fgfz.getUserId() > 0
											&& fgfz.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("业务部门分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 风险部分管副总
	 * @return
	 */
	public static boolean isRiskFgfz() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				List<Org> orgList = sessionLocal.getUserDetailInfo().getOrgList();
				//风险部所有部门
				String riskDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code()); //业务部所有部门 参数
				if (riskDeptCodes != null && orgList != null) {
					String[] depts = riskDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (org.getCode().equals(dept)) {
								//查询风险部分管副总
								List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
									.getFgfz(dept);
								if (ListUtil.isNotEmpty(busiFgfzs)) {
									for (SimpleUserInfo fgfz : busiFgfzs) {
										if (fgfz.getUserId() > 0
											&& fgfz.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("风险部门分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 项目发起人的业务部门分管副总
	 * @return
	 */
	public static boolean isBusinessFgfz(String projectCode) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getBusiManager(projectCode);
				String currDeptCode = rData.getDeptCode();
				if (StringUtil.isNotEmpty(currDeptCode)) {
					//查询业务部分管副总
					List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
						.getFgfz(currDeptCode);
					if (ListUtil.isNotEmpty(busiFgfzs)) {
						for (SimpleUserInfo fgfz : busiFgfzs) {
							if (fgfz.getUserId() > 0
								&& fgfz.getUserId() == sessionLocal.getUserId()) {
								is = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("业务部门分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 是否业务部分管副总
	 * @param userId
	 * @return
	 */
	public static boolean isBusinessFgfz(long userId) {
		boolean is = false;
		try {
			//业务部所有部门
			String busiDeptCodes = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
			if (StringUtil.isNotBlank(busiDeptCodes)) {
				UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
				if (userDetail != null) {
					List<Org> orgList = userDetail.getOrgList();
					String[] busiDeptArr = busiDeptCodes.split(",");
					for (String deptCode : busiDeptArr) {
						if (StringUtil.isNotBlank(deptCode)) {
							for (Org org : orgList) {
								if (StringUtil.equals(deptCode, org.getCode())) {
									List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										.getFgfz(deptCode);
									if (ListUtil.isNotEmpty(userInfos)) {
										for (SimpleUserInfo userInfo : userInfos) {
											if (userInfo.getUserId() > 0
												&& userInfo.getUserId() == userId) {
												is = true;
												break;
											}
										}
									}
								}
								if (is)
									break;
							}
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否业务部负责人出错{}", e);
		}
		return is;
	}
	
	/**
	 * 是否业务部分管副总
	 * @param userId
	 * @return
	 */
	public static boolean isBusinessFgfz(UserDetailInfo userDetail) {
		boolean is = false;
		try {
			if (userDetail != null) {
				//业务部所有部门
				String busiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
				if (StringUtil.isNotBlank(busiDeptCodes)) {
					
					List<Org> orgList = userDetail.getOrgList();
					String[] busiDeptArr = busiDeptCodes.split(",");
					for (String deptCode : busiDeptArr) {
						if (StringUtil.isNotBlank(deptCode)) {
							for (Org org : orgList) {
								if (StringUtil.equals(deptCode, org.getCode())) {
									List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										.getFgfz(deptCode);
									if (ListUtil.isNotEmpty(userInfos)) {
										for (SimpleUserInfo userInfo : userInfos) {
											if (userInfo.getUserId() > 0
												&& userInfo.getUserId() == userDetail.getId()) {
												is = true;
												break;
											}
										}
									}
								}
								if (is)
									break;
							}
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否业务部负责人出错{}", e);
		}
		return is;
	}
	
	/**
	 * 项目风险经理所在部门的分管副总
	 * @return
	 */
	public static boolean isRiskFgfz(String projectCode) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				ProjectRelatedUserInfo rData = projectRelatedUserServiceClient
					.getBusiManager(projectCode);
				String currDeptCode = rData.getDeptCode();
				if (StringUtil.isNotEmpty(currDeptCode)) {
					//查询业务部分管副总
					List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
						.getFgfz(currDeptCode);
					if (ListUtil.isNotEmpty(busiFgfzs)) {
						for (SimpleUserInfo fgfz : busiFgfzs) {
							if (fgfz.getUserId() > 0
								&& fgfz.getUserId() == sessionLocal.getUserId()) {
								is = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("风险部门分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 内控部分管副总
	 * @return
	 */
	public static boolean isNkFgfz() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				String nKDeptCode = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_NKHGB_DEPT_CODE.code());//内控部
				if (StringUtil.isNotEmpty(nKDeptCode)) {
					//查询内控部分管副总
					List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
						.getFgfz(nKDeptCode);
					if (ListUtil.isNotEmpty(busiFgfzs)) {
						for (SimpleUserInfo fgfz : busiFgfzs) {
							if (fgfz.getUserId() > 0
								&& fgfz.getUserId() == sessionLocal.getUserId()) {
								is = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("内控部分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 法务部分管副总
	 * @return
	 */
	public static boolean isLegalFgfz() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				String rislDeptCode = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());//法务部
				if (StringUtil.isNotEmpty(rislDeptCode)) {
					//查询法务部部分管副总
					List<SimpleUserInfo> busiFgfzs = projectRelatedUserServiceClient
						.getFgfz(rislDeptCode);
					if (ListUtil.isNotEmpty(busiFgfzs)) {
						for (SimpleUserInfo fgfz : busiFgfzs) {
							if (fgfz.getUserId() > 0
								&& fgfz.getUserId() == sessionLocal.getUserId()) {
								is = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("法务部分管副总{}", e);
		}
		return is;
	}
	
	/**
	 * 是否业务部负责人
	 * @return
	 */
	public static boolean isBusiFZR() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//业务部所有部门
				String busiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				
				//用户详细信息
				UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
				List<Org> orgList = userDetail.getOrgList();
				if (busiDeptCodes != null && orgList != null) {
					String[] depts = busiDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (dept.equals(org.getCode())) {
								//								List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
								//									.getDeptRoleUser(dept, bmfzrCode);
								List<SysUser> userInfos = bpmUserQueryService
									.findChargeByOrgCode(dept);
								if (ListUtil.isNotEmpty(userInfos)) {
									for (SysUser userInfo : userInfos) {
										if (userInfo.getUserId() > 0
											&& userInfo.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("业务部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 是否该项目业务部负责人
	 * @return
	 */
	public static boolean isBusiFZR(String projectCode) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//业务部所有部门
				ProjectRelatedUserInfo busiManage = projectRelatedUserServiceClient
					.getBusiManager(projectCode);
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				if (busiManage.getDeptCode() != null) {
					//用户详细信息
					UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
					List<Org> orgList = userDetail.getOrgList();
					for (Org org : orgList) {
						if (busiManage.getDeptCode().equals(org.getCode())) {
							//							List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
							//								.getDeptRoleUser(busiManage.getDeptCode(), bmfzrCode);
							List<SysUser> userInfos = bpmUserQueryService
								.findChargeByOrgCode(busiManage.getDeptCode());
							if (ListUtil.isNotEmpty(userInfos)) {
								for (SysUser userInfo : userInfos) {
									if (userInfo.getUserId() > 0
										&& userInfo.getUserId() == sessionLocal.getUserId()) {
										is = true;
										break;
									}
								}
							}
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("业务部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 是否业务部负责人
	 * @param userId
	 * @return
	 */
	public static boolean isBusiFZR(long userId) {
		boolean is = false;
		try {
			//业务部所有部门
			String busiDeptCodes = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
			if (StringUtil.isNotBlank(busiDeptCodes)) {
				UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
				if (userDetail != null) {
					//					String bmfzrCode = sysParameterServiceClient
					//						.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
					List<Org> orgList = userDetail.getOrgList();
					if (orgList != null) {
						String[] busiDeptArr = busiDeptCodes.split(",");
						for (String deptCode : busiDeptArr) {
							if (StringUtil.isNotBlank(deptCode)) {
								for (Org org : orgList) {
									if (StringUtil.equals(deptCode, org.getCode())) {
										//										List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										//											.getDeptRoleUser(deptCode, bmfzrCode);
										List<SysUser> userInfos = bpmUserQueryService
											.findChargeByOrgCode(deptCode);
										if (ListUtil.isNotEmpty(userInfos)) {
											for (SysUser userInfo : userInfos) {
												if (userInfo.getUserId() > 0
													&& userInfo.getUserId() == userId) {
													is = true;
													break;
												}
											}
										}
									}
									if (is)
										break;
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否业务部负责人出错{}", e);
		}
		return is;
	}
	
	/**
	 * 是否业务部负责人
	 * @param userId
	 * @return
	 */
	public static boolean isBusiFZR(UserDetailInfo userDetail) {
		boolean is = false;
		try {
			if (userDetail != null) {
				//业务部所有部门
				String busiDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
				if (StringUtil.isNotBlank(busiDeptCodes)) {
					//					String bmfzrCode = sysParameterServiceClient
					//						.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
					List<Org> orgList = userDetail.getOrgList();
					if (orgList != null) {
						String[] busiDeptArr = busiDeptCodes.split(",");
						for (String deptCode : busiDeptArr) {
							if (StringUtil.isNotBlank(deptCode)) {
								for (Org org : orgList) {
									if (StringUtil.equals(deptCode, org.getCode())) {
										//										List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
										//											.getDeptRoleUser(deptCode, bmfzrCode);
										List<SysUser> userInfos = bpmUserQueryService
											.findChargeByOrgCode(deptCode);
										if (ListUtil.isNotEmpty(userInfos)) {
											for (SysUser userInfo : userInfos) {
												if (userInfo.getUserId() > 0
													&& userInfo.getUserId() == userDetail.getId()) {
													is = true;
													break;
												}
											}
										}
									}
									if (is)
										break;
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否业务部负责人出错{}", e);
		}
		return is;
	}
	
	//当前登陆人是否属于业务部人员
	public static boolean isBusiMenber() {
		boolean is = false;
		try {
			//业务部所有部门
			String busiDeptCodes = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
			if (StringUtil.isNotBlank(busiDeptCodes)) {
				UserDetailInfo userDetail = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
				if (userDetail != null) {
					List<Org> orgList = userDetail.getOrgList();
					if (orgList != null) {
						String[] busiDeptArr = busiDeptCodes.split(",");
						for (String deptCode : busiDeptArr) {
							if (StringUtil.isNotBlank(deptCode)) {
								for (Org org : orgList) {
									if (StringUtil.equals(deptCode, org.getCode())
										|| StringUtil.isNotEmpty(org.getCode())
										&& org.getCode().indexOf(deptCode) > -1) {
										is = true;
										break;
									}
									if (is)
										break;
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否属于业务部出错{}", e);
		}
		return is;
	}
	
	//当前登陆人是否属于业务部人员
	public static boolean isBusiMenber(long userId) {
		boolean is = false;
		try {
			//业务部所有部门
			String busiDeptCodes = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
			if (StringUtil.isNotBlank(busiDeptCodes)) {
				UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
				if (userDetail != null) {
					List<Org> orgList = userDetail.getOrgList();
					if (orgList != null) {
						String[] busiDeptArr = busiDeptCodes.split(",");
						for (String deptCode : busiDeptArr) {
							if (StringUtil.isNotBlank(deptCode)) {
								for (Org org : orgList) {
									if (StringUtil.equals(deptCode, org.getCode())
										|| StringUtil.isNotEmpty(org.getCode())
										&& org.getCode().indexOf(deptCode) > -1) {
										is = true;
										break;
									}
									if (is)
										break;
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查人员是否属于业务部出错{}", e);
		}
		return is;
	}
	
	/**
	 * 是否风险部负责人
	 * @return
	 */
	public static boolean isRiskFZR() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//风险部所有部门
				String riskDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code()); //风险部
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
				List<Org> orgList = userDetail.getOrgList();
				if (riskDeptCodes != null && orgList != null) {
					String[] depts = riskDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (dept.equals(org.getCode())) {
								//								List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
								//									.getDeptRoleUser(dept, bmfzrCode);
								List<SysUser> userInfos = bpmUserQueryService
									.findChargeByOrgCode(dept);
								if (ListUtil.isNotEmpty(userInfos)) {
									for (SysUser userInfo : userInfos) {
										if (userInfo.getUserId() > 0
											&& userInfo.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("是否风险部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 是否法律事务部门负责人
	 * @return
	 */
	public static boolean isFWBFZR() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//法律事务部所有部门
				String riskDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code()); //法律事务部门 参数
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				List<Org> orgList = sessionLocal.getUserDetailInfo().getOrgList();
				if (riskDeptCodes != null && orgList != null) {
					String[] depts = riskDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (dept.equals(org.getCode())) {
								//								List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
								//									.getDeptRoleUser(dept, bmfzrCode);
								List<SysUser> userInfos = bpmUserQueryService
									.findChargeByOrgCode(dept);
								if (ListUtil.isNotEmpty(userInfos)) {
									for (SysUser userInfo : userInfos) {
										if (userInfo.getUserId() > 0
											&& userInfo.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("是否法律事务部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 是否内控合规部负责人
	 * @return
	 */
	public static boolean isNkFZR() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean is = false;
		try {
			if (sessionLocal != null) {
				//内控合规部所有部门
				String riskDeptCodes = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_NKHGB_DEPT_CODE.code()); //内控合规部门 参数
				//				String bmfzrCode = sysParameterServiceClient
				//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
				List<Org> orgList = sessionLocal.getUserDetailInfo().getOrgList();
				if (riskDeptCodes != null && orgList != null) {
					String[] depts = riskDeptCodes.split(",");
					for (String dept : depts) {
						if (StringUtil.isBlank(dept))
							continue;
						for (Org org : orgList) {
							if (dept.equals(org.getCode())) {
								//								List<SimpleUserInfo> userInfos = projectRelatedUserServiceClient
								//									.getDeptRoleUser(dept, bmfzrCode);
								List<SysUser> userInfos = bpmUserQueryService
									.findChargeByOrgCode(dept);
								if (ListUtil.isNotEmpty(userInfos)) {
									for (SysUser userInfo : userInfos) {
										if (userInfo.getUserId() > 0
											&& userInfo.getUserId() == sessionLocal.getUserId()) {
											is = true;
											break;
										}
									}
								}
							}
							if (is)
								break;
						}
						if (is)
							break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("内控合规部负责人{}", e);
		}
		return is;
	}
	
	/**
	 * 当前登陆人员是否属于信惠人员
	 * @return
	 */
	public static boolean isBelong2Xinhui() {
		boolean isXinHui = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			//是否属于信惠人员
			isXinHui = projectRelatedUserServiceClient.isBelongToDept(sessionLocal
				.getUserDetailInfo(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()));
		}
		return isXinHui;
	}
	
	/**
	 * 当前登陆人员是否属于信惠人员
	 * @return
	 */
	public static boolean isBelong2Xinhui(long userId) {
		boolean isXinHui = false;
		UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
		Org org = bpmUserQueryService.findDeptByCode(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()));
		if (userDetail != null && org != null && userDetail.isBelong2Dept(org.getId())) {
			isXinHui = true;
		}
		return isXinHui;
	}
	
	/**
	 * 当前登陆人员是否属于法务人员
	 * @return
	 */
	public static boolean isBelongLegal() {
		boolean isLegal = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			//是否法务部人员
			isLegal = projectRelatedUserServiceClient.isBelongToDept(sessionLocal
				.getUserDetailInfo(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code()));
		}
		return isLegal;
	}
	
	/**
	 * 是否财务应收岗
	 * @return
	 */
	public static boolean isCWYSG() {
		return hasRole(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYSG_ROLE_NAME.code()));
	}
	
	/**
	 * 是否财务应付岗
	 * @return
	 */
	public static boolean isCWYFG() {
		return hasRole(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code()));
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(String... roleAlias) {
		
		boolean is = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null && roleAlias != null) {
			
			List<String> roleAliasList = Lists.newArrayList();
			//兼容传的角色为多个,分开的情况
			for (String r : roleAlias) {
				if (StringUtil.isBlank(r))
					continue;
				String[] rArr = r.split(",");
				for (String ra : rArr) {
					if (StringUtil.isNotBlank(ra))
						roleAliasList.add(ra);
				}
			}
			
			UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
			if (userDetail != null) {
				List<Role> roleList = userDetail.getRoleList();
				for (Role role : roleList) {
					for (String ra : roleAliasList) {
						String roleCode = role.getCode();
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
						//替换掉系统前缀
						roleCode = roleCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
					}
					if (is)
						break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param userId
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(long userId, String... roleAlias) {
		return hasRole(bpmUserQueryService.findUserDetailByUserId(userId), roleAlias);
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param userId
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasJob(long userId, String... jobAlias) {
		return hasRole(bpmUserQueryService.findUserDetailByUserId(userId), jobAlias);
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param userId
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(UserDetailInfo userDetail, String... roleAlias) {
		
		boolean is = false;
		if (userDetail != null && roleAlias != null) {
			
			if (userDetail != null) {
				
				List<String> roleAliasList = Lists.newArrayList();
				//兼容传的角色为多个,分开的情况
				for (String r : roleAlias) {
					String[] rArr = r.split(",");
					for (String ra : rArr) {
						if (StringUtil.isNotBlank(ra))
							roleAliasList.add(ra);
					}
				}
				
				List<Role> roleList = userDetail.getRoleList();
				for (Role role : roleList) {
					for (String ra : roleAliasList) {
						String roleCode = role.getCode();
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
						//替换掉系统前缀
						roleCode = roleCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
					}
					if (is)
						break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断人员是否有相关职位
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasJob(String... jobAlias) {
		
		boolean is = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null && jobAlias != null) {
			
			List<String> jobAliasList = Lists.newArrayList();
			//兼容传的角色为多个,分开的情况
			for (String r : jobAlias) {
				String[] rArr = r.split(",");
				for (String ra : rArr) {
					if (StringUtil.isNotBlank(ra))
						jobAliasList.add(ra);
				}
			}
			
			UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
			if (userDetail != null) {
				List<Job> jobList = userDetail.getJobList();
				for (Job job : jobList) {
					for (String ra : jobAliasList) {
						String roleCode = job.getCode();
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
						//替换掉系统前缀
						roleCode = roleCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
					}
					if (is)
						break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断人员是否有相关职位
	 * @param userId
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasJob(UserDetailInfo userDetail, String... jobAlias) {
		
		boolean is = false;
		if (userDetail != null && jobAlias != null) {
			List<String> jobAliasList = Lists.newArrayList();
			//兼容传的角色为多个,分开的情况
			for (String r : jobAlias) {
				String[] rArr = r.split(",");
				for (String ra : rArr) {
					if (StringUtil.isNotBlank(ra))
						jobAliasList.add(ra);
				}
			}
			List<Job> jobList = userDetail.getJobList();
			for (Job job : jobList) {
				for (String ra : jobAliasList) {
					String jobCode = job.getCode();
					if (StringUtil.equals(jobCode, ra)) {
						is = true;
					}
					if (is)
						break;
					//替换掉系统前缀
					jobCode = jobCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
					if (StringUtil.equals(jobCode, ra)) {
						is = true;
					}
					if (is)
						break;
				}
				if (is)
					break;
			}
		}
		return is;
	}
	
	/**
	 * 判断部门Id和当前登陆用户是否同一部门
	 * 
	 * */
	public static Boolean isOneDep(Long depId) {
		if (depId == null || depId == 0)
			return false;
		UserDetailInfo userDetail = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
		Org org = bpmUserQueryService.findDeptByOrgId(depId);
		if (userDetail.isBelong2Dept(org.getId()))
			return true;
		return false;
		
	}
	
	/**
	 * 是否信惠部门
	 * @return
	 */
	public static boolean isXinHuiDept(String deptCode) {
		if (StringUtil.isBlank(deptCode))
			return false;
		String xinHuiDeptCodes = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code());
		String deptCodes[] = xinHuiDeptCodes.split(",");
		for (String code : deptCodes) {
			if (StringUtil.isNotBlank(code)) {
				if (code.equals(deptCode)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 用印打印权限判断
	 * 
	 * */
	public static Boolean isCanPrint(Long formId) {
		if (formId == null || formId == 0)
			return false;
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		StampApplyInfo applyInfo;
		if ("REPLACE_STAMP_APPLY".equals(form.getFormCode().code())) {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "IS");
		} else {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "NO");
			//List<StampAFileInfo> infos = applyInfo.getStampAFiles();
		}
		boolean hasG = false;
		boolean hasF = false;
		for (StampAFileInfo fileInfo : applyInfo.getStampAFiles()) {
			if (fileInfo.getLegalChapterNum() != 0 && fileInfo.getCachetNum() != 0) {
				hasF = true;
				hasG = true;
				break;
			}
			if (fileInfo.getCachetNum() != 0) {
				hasG = true;
			}
			if (fileInfo.getLegalChapterNum() != 0) {
				hasF = true;
			}
		}
		if (hasG && DataPermissionUtil.isGZManager()) {
			return true;
		}
		if (hasF && DataPermissionUtil.isFRZManager()) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * 是否部门负责人
	 * @return
	 */
	public static boolean isBmfzr() {
		String bmfzrCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
		return hasRole(bmfzrCode);
	}
	
	/**
	 * 是否部门负责人
	 * @return
	 */
	public static boolean isBmfzr(long userId) {
		String bmfzrCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());//部门负责人 参数
		return hasRole(userId, bmfzrCode);
	}
	
	/**
	 * 登陆人是否拥有该部门分配权限
	 * @param depPath 部门code全称
	 * */
	public static boolean canAllocation(String depPath) {
		try {
			if (StringUtil.isBlank(depPath)) {
				return false;
			}
			UserDetailInfo userDetail = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
			String path = userDetail.getPrimaryOrg().getPath();
			return (isBusiFZR() || isXinHuiFzr()) && depPath.indexOf(path) > -1;
		} catch (Exception e) {
			
		}
		return false;
		
	}
	
	/** 是否董事长 */
	public static boolean isChairMan() {
		return hasRole(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CHAIRMAN_ROLE_NAME.code()));
	}
	
	/** 是否董事长 */
	public static boolean isChairMan(long userId) {
		return hasRole(userId,
			sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CHAIRMAN_ROLE_NAME.code()));
	}
	
	/** 是否总经理 */
	public static boolean isGeneralManager() {
		return hasRole(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ZJL_ROLE_NAME.code()));
	}
	
	/** 是否总经理 */
	public static boolean isGeneralManager(long userId) {
		return hasRole(userId,
			sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_ZJL_ROLE_NAME
				.code()));
	}
	
	/**
	 * 是否放款准备岗
	 * @return
	 */
	public static boolean isLoanPrepareRole() {
		return hasRole(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.LOAN_PREPARE_ROLE_NAME.code()));
	}
}
