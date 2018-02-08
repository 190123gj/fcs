package com.born.fcs.pm.biz.service.assist;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FInternalOpinionExchangeDO;
import com.born.fcs.pm.dal.dataobject.FInternalOpinionExchangeUserDO;
import com.born.fcs.pm.dataobject.InternalOpinionExchangeFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeInfo;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeUserInfo;
import com.born.fcs.pm.ws.info.assist.InternalOpinionExchangeFormInfo;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.assist.FInternalOpinionExchangeOrder;
import com.born.fcs.pm.ws.order.assist.InternalOpinionExchangeQueryOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 内审意见交换
 * @author wuzj
 */
@Service("internalOpinionExchangeService")
public class InternalOpinionExchangeServiceImpl extends BaseFormAutowiredDomainService implements
																						InternalOpinionExchangeService {
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Override
	public FcsBaseResult checkDeptUser(FInternalOpinionExchangeOrder order) {
		
		FcsBaseResult result = createResult();
		try {
			
			String userIds = order.getUserIds();
			String deptIds = order.getDeptIds();
			String deptNames = order.getDeptNames();
			
			logger.info("开始检查部门人员，deptIds ：{} userIds ：{}", deptIds, userIds);
			
			if (StringUtil.isEmpty(userIds) || StringUtil.isEmpty(deptIds)
				|| StringUtil.isEmpty(deptNames)) {
				result.setSuccess(false);
				result.setMessage("人员或部门为空");
			} else {
				String[] userIdArr = userIds.split(",");
				String[] deptIdArr = deptIds.split(",");
				String[] deptNameArr = deptNames.split(",");
				
				//部门的MAP
				Map<String, String> deptMap = Maps.newHashMap();
				//部门人员MAP
				Map<String, Integer> deptUserNumMap = Maps.newHashMap();
				for (int i = 0; i < deptIdArr.length; i++) {
					deptMap.put(deptIdArr[i], deptNameArr[i]);
					deptUserNumMap.put(deptIdArr[i], 0); //开始没有人
				}
				
				String excludeUsers = "";
				for (String userId : userIdArr) {
					if (StringUtil.isNotEmpty(userId)) {
						//查询用户详细信息
						UserDetailInfo userDetail = bpmUserQueryService
							.findUserDetailByUserId(NumberUtil.parseLong(userId));
						int belongDeptNum = 0;
						for (String deptId : deptIdArr) {
							if (userDetail.isBelong2Dept(NumberUtil.parseLong(deptId))) {
								belongDeptNum++;
								deptUserNumMap.put(deptId, deptUserNumMap.get(deptId) + 1);
							}
						}
						if (belongDeptNum == 0) { //所选人员不属于所选部门
							excludeUsers += userDetail.getName() + ",";
						}
					}
				}
				
				//判断部门是否选择了人员
				String noUserDept = "";
				for (String deptId : deptUserNumMap.keySet()) {
					if (deptUserNumMap.get(deptId) == 0) {
						noUserDept += deptMap.get(deptId) + ",";
					}
				}
				
				if (StringUtil.isNotBlank(noUserDept)) {
					noUserDept = noUserDept.substring(0, noUserDept.length() - 1);
					result.setSuccess(false);
					result.setMessage("以下部门尚未选择人员：" + noUserDept);
				} else
				//有不属于所选部门的人员
				if (StringUtil.isNotBlank(excludeUsers)) {
					excludeUsers = excludeUsers.substring(0, excludeUsers.length() - 1);
					result.setSuccess(false);
					result.setMessage("以下人员不属于所选部门：" + excludeUsers);
				} else {
					result.setSuccess(true);
					result.setMessage("部门人员检查通过");
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("检查部门人员出错");
			logger.info("检查部门人员出错:{}", e);
		}
		return result;
	}
	
	@Override
	public FormBaseResult save(final FInternalOpinionExchangeOrder order) {
		
		return commonFormSaveProcess(order, "保存内审意见交换", new CheckBeforeProcessService() {
			
			@Override
			public void check() {
				
				String[] deptIdArr = order.getDeptIds().split(",");
				String[] deptNameArr = order.getDeptNames().split(",");
				
				String notExitsFzrDept = ""; //不存在负责人的部门
				List<SysUser> deptPrincipals = Lists.newArrayList();
				for (int i = 0; i < deptIdArr.length; i++) {
					
					long deptId = NumberUtil.parseLong(deptIdArr[i]);
					Org org = bpmUserQueryService.findDeptByOrgId(deptId);
					if (org == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到部门：" + deptNameArr[i]);
					}
					
					//					//负责人角色编码
					//					String fzrRole = sysParameterService
					//						.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
					//					
					//					//查询部门负责人
					//					List<SimpleUserInfo> deptPrincipal = projectRelatedUserService.getRoleDeptUser(
					//						org.getId(), fzrRole);
					List<SysUser> deptPrincipal = bpmUserQueryService.findChargeByOrgId(org.getId());
					
					if (ListUtil.isEmpty(deptPrincipal)) { //负责人不存在
						//查询当前是否部门
						notExitsFzrDept += deptNameArr[i] + ",";
					}
					deptPrincipals.addAll(deptPrincipal);
				}
				if (StringUtil.isNotBlank(notExitsFzrDept)) {
					notExitsFzrDept = notExitsFzrDept.substring(0, notExitsFzrDept.length() - 1);
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到以下部门负责人：" + notExitsFzrDept);
				}
				
				FcsPmDomainHolder.get().addAttribute("deptPrincipals", deptPrincipals);
				
				//根据表单类型匹配流程
				if (order.getExType() == InternalOpinionExTypeEnum.REPORT_DELI) {
					order.setFormCode(FormCodeEnum.INTERNAL_OPINION_EXCHANGE_REPORT_DELI);
				} else if (order.getExType() == InternalOpinionExTypeEnum.REPORT_SEEK_OP) {
					order.setFormCode(FormCodeEnum.INTERNAL_OPINION_EXCHANGE_REPORT_SEEK_OP);
				} else if (order.getExType() == InternalOpinionExTypeEnum.WP_CONFIRM) {
					order.setFormCode(FormCodeEnum.INTERNAL_OPINION_EXCHANGE_WP_CONFIRM);
				}
				
				String[] userIdArr = order.getUserIds().split(",");
				List<SimpleUserInfo> userList = Lists.newArrayList();
				String users = "";
				for (String userId : userIdArr) {
					SysUser user = bpmUserQueryService.findUserByUserId(NumberUtil
						.parseLong(userId));
					if (user != null) {
						SimpleUserInfo sUser = new SimpleUserInfo();
						sUser.setUserId(user.getUserId());
						sUser.setUserAccount(user.getAccount());
						sUser.setUserName(user.getFullname());
						sUser.setEmail(user.getEmail());
						sUser.setMobile(user.getMobile());
						userList.add(sUser);
						users += user.getFullname() + ",";
					}
				}
				FcsPmDomainHolder.get().addAttribute("userList", userList);
				FcsPmDomainHolder.get().addAttribute("users",
					StringUtil.isNotBlank(users) ? users.substring(0, users.length() - 1) : "");
			}
		}, new BeforeProcessInvokeService() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FInternalOpinionExchangeDO exDO = FInternalOpinionExchangeDAO.findByFormId(form
					.getFormId());
				
				boolean isUpdate = false;
				if (exDO == null) {
					exDO = new FInternalOpinionExchangeDO();
					exDO.setExCode(genCode(order.getExType()));
					exDO.setRawAddTime(now);
				} else {
					isUpdate = true;
				}
				exDO.setExType(order.getExType().code());
				exDO.setFormId(form.getFormId());
				exDO.setDeptIds(order.getDeptIds());
				exDO.setDeptNames(order.getDeptNames());
				exDO.setRemark(order.getRemark());
				exDO.setNeedFeedback(order.getNeedFeedback() == BooleanEnum.YES ? BooleanEnum.YES
					.code() : BooleanEnum.NO.code());
				exDO.setFeedbackTime(order.getFeedbackTime());
				exDO.setUsers((String) FcsPmDomainHolder.get().getAttribute("users"));
				
				if (isUpdate) {
					FInternalOpinionExchangeDAO.updateByFormId(exDO);
				} else {
					FInternalOpinionExchangeDAO.insert(exDO);
				}
				
				if (isUpdate) {
					//删掉原来的审计人员
					FInternalOpinionExchangeUserDAO.deleteByFormId(form.getFormId());
				}
				
				//保存新的审计人员
				List<SimpleUserInfo> userList = (List<SimpleUserInfo>) FcsPmDomainHolder.get()
					.getAttribute("userList");
				if (ListUtil.isNotEmpty(userList)) {
					for (SimpleUserInfo user : userList) {
						FInternalOpinionExchangeUserDO userDO = new FInternalOpinionExchangeUserDO();
						userDO.setFormId(form.getFormId());
						userDO.setUserId(user.getUserId());
						userDO.setUserName(user.getUserName());
						userDO.setUserAccount(user.getUserAccount());
						userDO.setUserMobile(user.getMobile());
						userDO.setUserEmail(user.getEmail());
						userDO.setFeedbackTime(order.getFeedbackTime());
						userDO.setIsNotified(BooleanEnum.NO.code());
						userDO.setIsPrincipal(BooleanEnum.NO.code());
						userDO.setExType(order.getExType().code());
						userDO.setRawAddTime(now);
						FInternalOpinionExchangeUserDAO.insert(userDO);
					}
				}
				
				List<SysUser> deptPrincipals = (List<SysUser>) FcsPmDomainHolder.get()
					.getAttribute("deptPrincipals");
				
				//去重
				HashMap<Long, SysUser> deptPrincipalSetMap = new HashMap<Long, SysUser>();
				for (SysUser user : deptPrincipals) {
					deptPrincipalSetMap.put(user.getUserId(), user);
				}
				//保存负责人
				for (Long usreId : deptPrincipalSetMap.keySet()) {
					SysUser user = deptPrincipalSetMap.get(usreId);
					FInternalOpinionExchangeUserDO userDO = new FInternalOpinionExchangeUserDO();
					userDO.setFormId(form.getFormId());
					userDO.setUserId(user.getUserId());
					userDO.setUserName(user.getFullname());
					userDO.setUserAccount(user.getAccount());
					userDO.setUserMobile(user.getMobile());
					userDO.setUserEmail(user.getEmail());
					userDO.setFeedbackTime(order.getFeedbackTime());
					userDO.setIsNotified(BooleanEnum.NO.code());
					userDO.setIsPrincipal(BooleanEnum.IS.code());
					userDO.setExType(order.getExType().code());
					userDO.setRawAddTime(now);
					FInternalOpinionExchangeUserDAO.insert(userDO);
				}
				
				//新增流程人员 防止未提交到流程时看不到数据
				FormRelatedUserQueryOrder queryOrder = new FormRelatedUserQueryOrder();
				queryOrder.setFormId(form.getFormId());
				queryOrder.setUserId(form.getUserId());
				queryOrder.setUserType(FormRelatedUserTypeEnum.OTHER);
				long otherNum = formRelatedUserService.queryCount(queryOrder);
				if (otherNum == 0) {
					FormRelatedUserOrder userOrder = new FormRelatedUserOrder();
					userOrder.setFormId(form.getFormId());
					userOrder.setFormCode(form.getFormCode());
					userOrder.setUserType(FormRelatedUserTypeEnum.OTHER);
					BeanCopier.staticCopy(form, userOrder);
					formRelatedUserService.setRelatedUser(userOrder);
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 生成编号
	 * @param exType
	 * @return
	 */
	private String genCode(InternalOpinionExTypeEnum exType) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String seqName = SysDateSeqNameEnum.INTERNAL_OPINION_EXCHANGE_CODE.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		return "内控合规【" + year + "】" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
	}
	
	@Override
	public QueryBaseBatchResult<InternalOpinionExchangeFormInfo> searchForm(InternalOpinionExchangeQueryOrder order) {
		QueryBaseBatchResult<InternalOpinionExchangeFormInfo> batchResult = new QueryBaseBatchResult<InternalOpinionExchangeFormInfo>();
		
		try {
			
			InternalOpinionExchangeFormDO exForm = new InternalOpinionExchangeFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			
			BeanCopier.staticCopy(order, exForm);
			
			long totalCount = busiDAO.searchInternalOpinionFormCount(exForm);
			PageComponent component = new PageComponent(order, totalCount);
			List<InternalOpinionExchangeFormDO> dataList = busiDAO
				.searchInternalOpinionForm(exForm);
			
			List<InternalOpinionExchangeFormInfo> list = Lists.newArrayList();
			for (InternalOpinionExchangeFormDO DO : dataList) {
				InternalOpinionExchangeFormInfo info = new InternalOpinionExchangeFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setExType(InternalOpinionExTypeEnum.getByCode(DO.getExType()));
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询内审意见交换表单失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FInternalOpinionExchangeInfo findByFormId(long formId) {
		if (formId <= 0)
			return null;
		FInternalOpinionExchangeDO DO = FInternalOpinionExchangeDAO.findByFormId(formId);
		if (DO != null) {
			FInternalOpinionExchangeInfo info = new FInternalOpinionExchangeInfo();
			BeanCopier.staticCopy(DO, info);
			info.setExType(InternalOpinionExTypeEnum.getByCode(DO.getExType()));
			info.setNeedFeedback(BooleanEnum.getByCode(DO.getNeedFeedback()));
			info.setUserList(findUserByFormId(formId));
			if (ListUtil.isNotEmpty(info.getUserList())) {
				String userIds = "";
				for (FInternalOpinionExchangeUserInfo userInfo : info.getUserList()) {
					if (userInfo.getIsPrincipal() != BooleanEnum.IS) {
						userIds += userInfo.getUserId() + ",";
					}
				}
				if (StringUtil.isNotBlank(userIds)) {
					info.setUserIds(userIds);
				}
			}
			return info;
		}
		return null;
	}
	
	@Override
	public List<FInternalOpinionExchangeUserInfo> findUserByFormId(long formId) {
		if (formId <= 0)
			return null;
		List<FInternalOpinionExchangeUserDO> userList = FInternalOpinionExchangeUserDAO
			.findByFormId(formId);
		if (ListUtil.isNotEmpty(userList)) {
			List<FInternalOpinionExchangeUserInfo> list = Lists.newArrayList();
			for (FInternalOpinionExchangeUserDO DO : userList) {
				FInternalOpinionExchangeUserInfo info = new FInternalOpinionExchangeUserInfo();
				BeanCopier.staticCopy(DO, info);
				info.setIsNotified(BooleanEnum.getByCode(DO.getIsNotified()));
				info.setIsPrincipal(BooleanEnum.getByCode(DO.getIsPrincipal()));
				info.setExType(InternalOpinionExTypeEnum.getByCode(DO.getExType()));
				list.add(info);
			}
			return list;
		}
		return null;
	}
}
