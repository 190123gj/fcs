/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:44:04 创建
 */
package com.born.fcs.face.web.controller.project.recovery;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.PledgeAssetManagementModeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLetterTypeEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryNoticeLetterInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforePreservationPrecautionOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforeTrailOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 追偿
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("projectMg/recovery")
public class ProjectRecoveryContrlller extends BaseController {
	
	final static String vm_path = "/projectMg/afterLoanMg/recoveryProject/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "startTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "recoveryAmount", "recoveryPrincipalAmount",
								"recoveryInterestAmount", "recoveryInterestPenaltyAmount",
								"recoveryCompensationAmount", "recoveryOtherAmount",
								"estimateLoseAmount", "apportionLoseAmount", "loseCognizanceAmount" };
	}
	
	/**
	 * 追偿可用project列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("recoveryProject.json")
	@ResponseBody
	public JSONObject recoveryProject(ProjectQueryOrder order, Model model) {
		String tipPrefix = "查询追偿用项目列表";
		//当项目风险处置会决议为代偿的项目，资金划付申请单中划付事由为代偿本金、代偿利息，并且审核通过的
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			JSONArray jarray = new JSONArray();
			// 项目中的代偿金额是审核通过之后回写的，所以有金额必定代表审核通过
			//order.setHasCompensatoryAmount(BooleanEnum.IS);
			// 剔除项目关闭状态   20160823:剔除追偿中状态
			//List<ProjectPhasesEnum> phasesList = new ArrayList<ProjectPhasesEnum>();
			//phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
			//			for (ProjectPhasesEnum _enum : ProjectPhasesEnum.values()) {
			//				if (ProjectPhasesEnum.FINISH_PHASES != _enum
			//					&& ProjectPhasesEnum.RECOVERY_PHASES != _enum) {
			//					phasesList.add(_enum);
			//				}
			//			}
			//List<ProjectStatusEnum> statusList = new ArrayList<ProjectStatusEnum>();
			//statusList.add(ProjectStatusEnum.RECOVERY);
			//			for (ProjectStatusEnum _enum : ProjectStatusEnum.values()) {
			//				if (ProjectStatusEnum.FINISH != _enum && ProjectStatusEnum.RECOVERY != _enum) {
			//					statusList.add(_enum);
			//				}
			//			}
			//order.setPhasesList(phasesList);
			//order.setStatusList(statusList);
			// 设置一页10只 
			//order.setPageSize(10L);
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.raw_add_time");
				order.setSortOrder("DESC");
			}
			// 只能查询本风险经理所处的风险处置小组集合能处理的项目
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			Long userId = sessionLocal.getUserId();
			order.setRecoverySelecterId(String.valueOf(userId));
			
			//			QueryBaseBatchResult<ProjectInfo> projectInfos = projectServiceClient
			//				.queryProject(order);
			QueryBaseBatchResult<ProjectInfo> projectInfos = projectRecoveryServiceClient
				.recoveryProject(order);
			
			if (projectInfos != null && ListUtil.isNotEmpty(projectInfos.getPageList())) {
				for (ProjectInfo info : projectInfos.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmountTenThousandString());
					json.put("amount", info.getAmountStandardString());
					json.put("timeLimit", info.getTimeUnit() == null ? "-" : info.getTimeLimit()
																				+ info
																					.getTimeUnit()
																					.viewName());
					//					ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(),
					//						true);
					//					// 项目编号
					//					json.put("projectCode", project.getProjectCode());
					//					// 项目名称
					//					json.put("projectName", project.getProjectName());
					//					// 客户名称 
					//					json.put("customerName", project.getCustomerName());
					//					// 授信金额
					//					json.put("amount", project.getAmountStandardString());
					//					// 在保余额 
					//					json.put("guaranteeingAmount",
					//						project.getLoanedAmount().subtract(project.getReleasedAmount()));
					//					// 已解保金额 
					//					json.put("releasedAmount", project.getReleasedAmount());
					//					// 代偿金额 
					//					Money compAmount = project.getCompPrincipalAmount().addTo(
					//						project.getCompInterestAmount());
					//					json.put("compAmount", compAmount);
					//					
					//					// 收回金额  
					//					RefundApplicationResult principalResult = chargeNotificationServiceClient
					//						.queryChargeTotalAmount(info.getProjectCode(),
					//							FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
					//					RefundApplicationResult interestResult = chargeNotificationServiceClient
					//						.queryChargeTotalAmount(info.getProjectCode(),
					//							FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
					//					Money backAmount = principalResult.getOther().addTo(interestResult.getOther());
					//					json.put("backAmount", backAmount);
					//					// 余额 代偿金额—收回金额
					//					json.put("lastAmount", compAmount.subtractFrom(backAmount));
					jarray.add(json);
				}
			}
			data.put("pageCount", projectInfos.getPageCount());
			data.put("pageNumber", projectInfos.getPageNumber());
			data.put("pageSize", projectInfos.getPageSize());
			data.put("totalCount", projectInfos.getTotalCount());
			data.put("pageList", jarray);
			
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 追偿列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoveryList.htm")
	public String projectRecoveryList(ProjectRecoveryQueryOrder order, Model model) {
		
		//董事长 总经理 看全部
		//        
		//        法务经理 风险处置会成员 看自己的
		//SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//Long userId = sessionLocal.getUserId();
		boolean allFind = false;
		//		// 董事长
		//		List<SysUser> userDs = bpmUserQueryService.findUserByRoleAlias(sysParameterServiceClient
		//			.getSysParameterValue(SysParamEnum.SYS_PARAM_CHAIRMAN_ROLE_NAME.code()));
		//		for (SysUser user : userDs) {
		//			if (user.getUserId() == userId) {
		//				allFind = true;
		//				break;
		//			}
		//		}
		//		if (!allFind) {
		//			// 总经理
		//			List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(sysParameterServiceClient
		//				.getSysParameterValue(SysParamEnum.SYS_PARAM_ZJL_ROLE_NAME.code()));
		//			for (SysUser user : users) {
		//				if (user.getUserId() == userId) {
		//					allFind = true;
		//					break;
		//				}
		//			}
		//		}
		// 添加任务 财务岗看全部
		setSessionLocalInfo2Order(order);
		if (!allFind) {
			if (DataPermissionUtil.isCWYSG()) {
				allFind = true;
			}
		}
		
		if (allFind) {
			order.setIsAllFind(BooleanEnum.YES);
		} else {
			order.setIsAllFind(BooleanEnum.NO);
			//order.setUserId(userId);
		}
		QueryBaseBatchResult<ProjectRecoveryListInfo> batchResult = projectRecoveryServiceClient
			.queryRecovery(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 执行追偿保存
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoverySave.json")
	@ResponseBody
	public JSONObject projectRecoveryAdd(ProjectRecoveryOrder order, Model model) {
		String tipPrefix = " 保存追偿";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			// 添加申请信息
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setApplyUserId(sessionLocal.getUserId());
				order.setApplyUserAccount(sessionLocal.getUserName());
				order.setApplyUserName(sessionLocal.getRealName());
				
				UserInfo userInfo = sessionLocal.getUserInfo();
				if (userInfo != null) {
					SysOrg dept = sessionLocal.getUserInfo().getDept();
					if (dept != null) {
						order.setApplyDeptId(dept.getOrgId());
						order.setApplyDeptCode(dept.getCode());
						order.setApplyDeptName(dept.getOrgName());
					}
				}
			}
			FcsBaseResult saveResult = projectRecoveryServiceClient.save(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error("保存追偿出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 进入追偿新增 或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toSaveProjectRecovery.htm")
	public String toSaveprojectRecovery(Long recoveryId, String projectCode, Model model) {
		
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient.findById(recoveryId, true);
			if (result != null && result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				projectCode = result.getProjectRecoveryInfo().getProjectCode();
				
			}
		} else if (StringUtil.isNotBlank(projectCode)) {
			//			ProjectRecoveryResult result = projectRecoveryServiceClient
			//				.findByProjectCode(projectCode);
			//			if (result.isExecuted() && result.isSuccess()) {
			//				model.addAttribute("recovery", result.getProjectRecoveryInfo());
			//			}
		}
		if (StringUtil.isNotBlank(projectCode)) {
			// 获取项目基本信息
			getRecoveryProjectMessage(model, projectCode);
		}
		model
			.addAttribute("pledgeAssetManagementModes", PledgeAssetManagementModeEnum.getAllEnum());
		return vm_path + "addRecovery.vm";
	}
	
	private void getCapitalAmount(String projectCode, Model model) {
		FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = fCapitalAppropriationApplyServiceClient
			.query(queryOrder);
		List<FCapitalAppropriationApplyInfo> applyInfoList = batchResult.getPageList();
		//代偿本金
		Money recoveryPrincipalAmount = Money.zero();
		//代偿利息
		Money recoveryInterestAmount = Money.zero();
		//罚费
		Money recoveryInterestPenaltyAmount = Money.zero();
		//违约金
		Money recoveryCompensationAmount = Money.zero();
		//其他
		Money recoveryOtherAmount = Money.zero();
		// 代偿总额
		Money compAmount = Money.zero();
		for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList) {
			List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = fCapitalAppropriationApplyServiceClient
				.findByApplyId(fCapitalAppropriationApplyInfo.getApplyId());
			
			for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
				
				//代偿本金
				if (PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code().equals(
					fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
					recoveryPrincipalAmount = recoveryPrincipalAmount
						.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
				}
				//代偿利息
				if (PaymentMenthodEnum.COMPENSATORY_INTEREST.code().equals(
					fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
					recoveryInterestAmount = recoveryInterestAmount
						.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
				}
				//罚费
				if (PaymentMenthodEnum.COMPENSATORY_PENALTY.code().equals(
					fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
					recoveryInterestPenaltyAmount = recoveryInterestPenaltyAmount
						.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
				}
				//违约金
				if (PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES.code().equals(
					fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
					recoveryCompensationAmount = recoveryCompensationAmount
						.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
				}
				//其他
				if (PaymentMenthodEnum.COMPENSATORY_OTHER.code().equals(
					fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
					recoveryOtherAmount = recoveryOtherAmount.add(fCapitalAppropriationApplyFeeInfo
						.getAppropriateAmount());
				}
			}
		}
		model.addAttribute("recoveryPrincipalAmount", recoveryPrincipalAmount);
		model.addAttribute("recoveryInterestAmount", recoveryInterestAmount);
		model.addAttribute("recoveryInterestPenaltyAmount", recoveryInterestPenaltyAmount);
		model.addAttribute("recoveryCompensationAmount", recoveryCompensationAmount);
		model.addAttribute("recoveryOtherAmount", recoveryOtherAmount);
		compAmount = recoveryPrincipalAmount.add(recoveryInterestAmount)
			.add(recoveryInterestPenaltyAmount).add(recoveryCompensationAmount)
			.add(recoveryOtherAmount);
		model.addAttribute("compAmount", compAmount);
		// 收回金额
		RefundApplicationResult principalResult = chargeNotificationServiceClient
			.queryChargeTotalAmount(projectCode, FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
		RefundApplicationResult interestResult = chargeNotificationServiceClient
			.queryChargeTotalAmount(projectCode, FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
		RefundApplicationResult compensatoryDedit = chargeNotificationServiceClient
			.queryChargeTotalAmount(projectCode, FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
		RefundApplicationResult compensatoryPenalty = chargeNotificationServiceClient
			.queryChargeTotalAmount(projectCode,
				FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
		RefundApplicationResult compensatoryOther = chargeNotificationServiceClient
			.queryChargeTotalAmount(projectCode, FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
		Money backAmount = principalResult.getOther().add(interestResult.getOther())
			.add(compensatoryDedit.getOther()).add(compensatoryPenalty.getOther())
			.add(compensatoryOther.getOther());
		model.addAttribute("backAmount", backAmount);
		// 余额 代偿金额—收回金额
		model.addAttribute("lastAmount", compAmount.subtract(backAmount));
	}
	
	/**
	 * 追偿详情
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoveryMessage.htm")
	public String projectRecoveryMessage(Long recoveryId, Model model) {
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient.findById(recoveryId, true);
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				String projectCode = result.getProjectRecoveryInfo().getProjectCode();
				// 获取项目基本信息
				getRecoveryProjectMessage(model, projectCode);
				
			}
		}
		model
			.addAttribute("pledgeAssetManagementModes", PledgeAssetManagementModeEnum.getAllEnum());
		model.addAttribute("change", "change");
		return vm_path + "addRecovery.vm";
	}
	
	/**
	 * 进入项目关闭
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toProjectRecoveryClose.htm")
	public String toProjectRecoveryClose(Long recoveryId, Model model) {
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient.findById(recoveryId, true);
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				String projectCode = result.getProjectRecoveryInfo().getProjectCode();
				// 获取项目基本信息
				getRecoveryProjectMessage(model, projectCode);
				
			}
		} else {
			
			//logger.error("入参id不能为空！");
			
			//return "/error.vm";
		}
		return vm_path + "closeProject.vm";
	}
	
	private void getRecoveryProjectMessage(Model model, String projectCode) {
		ProjectInfo project = projectServiceClient.queryByCode(projectCode, true);
		model.addAttribute("project", project);
		// 客户
		//		FProjectCustomerBaseInfo projectCustomerBaseInfo = projectSetupServiceClient
		//			.queryCustomerBaseInfoByProjectCode(projectCode);
		//		model.addAttribute("projectCustomerBaseInfo", projectCustomerBaseInfo);
		
		// 在保余额 
		model.addAttribute("guaranteeingAmount",
			project.getLoanedAmount().subtract(project.getReleasedAmount()));
		// 已解保金额 
		model.addAttribute("releasedAmount", project.getReleasedAmount());
		
		// 代偿金额
		getCapitalAmount(projectCode, model);
		
		// 抓取法务经理
		// 抓取法务经理
		ProjectRelatedUserInfo rData = projectRelatedUserServiceClient.getLegalManager(projectCode);
		model.addAttribute("rDataUser", rData);
	}
	
	/**
	 * 项目关闭
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoveryCloseFirst.json")
	@ResponseBody
	public JSONObject projectRecoveryCloseFirst(ProjectRecoveryOrder order, Model model) {
		
		String tipPrefix = " 项目关闭";
		if (BooleanEnum.YES == order.getReClose()) {
			tipPrefix = " 撤销关闭";
		}
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = projectRecoveryServiceClient.closeProjectRecovery(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 进入项目关闭确认
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toProjectRecoveryCloseSure.htm")
	public String toProjectRecoveryCloseSure(Long recoveryId, Model model) {
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient.findById(recoveryId, true);
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				String projectCode = result.getProjectRecoveryInfo().getProjectCode();
				// 获取项目基本信息
				getRecoveryProjectMessage(model, projectCode);
			}
		} else {
			logger.error("入参id不能为空！");
			
			return "/error.vm";
		}
		return vm_path + "confirmCloseProject.vm";
	}
	
	/**
	 * 项目关闭确认
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoveryCloseSure.json")
	@ResponseBody
	public JSONObject projectRecoveryCloseSure(ProjectRecoveryOrder order, Model model) {
		
		String tipPrefix = " 项目关闭确认";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = projectRecoveryServiceClient.closeProjectRecoverySure(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 结束追偿
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRecoveryEnd.json")
	@ResponseBody
	public JSONObject projectRecoveryEnd(ProjectRecoveryOrder order, Model model) {
		
		String tipPrefix = " 结束追偿";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = projectRecoveryServiceClient.endProjectRecovery(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 进入通知函填写
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toNoticeLetterSave.htm")
	public String toNoticeLetterSave(Long recoveryId, Model model) {
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient
				.queryNoticeLetter(recoveryId);
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				if (ListUtil.isNotEmpty(result.getProjectRecoveryInfo().getNoticeLetters())) {
					// 循环抓去letters
					for (ProjectRecoveryNoticeLetterInfo info : result.getProjectRecoveryInfo()
						.getNoticeLetters()) {
						if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_SURE == info
							.getLetterType()) {
							model.addAttribute("guaranteeObligationSure", info);
						} else if (ProjectRecoveryLetterTypeEnum.DEBT_OBLIGATION_CHANGE_NOTICE == info
							.getLetterType()) {
							model.addAttribute("debtObligationChangeNotice", info);
						} else if (ProjectRecoveryLetterTypeEnum.RECOVERY_NOTICE == info
							.getLetterType()) {
							model.addAttribute("recoveryNotice", info);
						} else if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_NOTICE == info
							.getLetterType()) {
							model.addAttribute("guaranteeObligationNotice", info);
						}
					}
				}
			}
		} else {
			// 代表新增
			logger.error("入参id不能为空！");
			
			return "/error.vm";
		}
		return vm_path + "addNotice.vm";
	}
	
	/**
	 * 通知函详情
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("noticeLetterMessage.htm")
	public String noticeLetterMessage(Long recoveryId, Long nLetterId, Long stampFormId, Model model) {
		if (recoveryId != null && recoveryId > 0) {
			ProjectRecoveryResult result = projectRecoveryServiceClient
				.queryNoticeLetter(recoveryId);
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("recovery", result.getProjectRecoveryInfo());
				if (ListUtil.isNotEmpty(result.getProjectRecoveryInfo().getNoticeLetters())) {
					// 循环抓去letters
					for (ProjectRecoveryNoticeLetterInfo info : result.getProjectRecoveryInfo()
						.getNoticeLetters()) {
						if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_SURE == info
							.getLetterType()) {
							model.addAttribute("guaranteeObligationSure", info);
						} else if (ProjectRecoveryLetterTypeEnum.DEBT_OBLIGATION_CHANGE_NOTICE == info
							.getLetterType()) {
							model.addAttribute("debtObligationChangeNotice", info);
						} else if (ProjectRecoveryLetterTypeEnum.RECOVERY_NOTICE == info
							.getLetterType()) {
							model.addAttribute("recoveryNotice", info);
						} else if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_NOTICE == info
							.getLetterType()) {
							model.addAttribute("guaranteeObligationNotice", info);
						}
					}
				}
			}
			model.addAttribute("noticeLetterId", nLetterId);
			model.addAttribute("isCanPrint", DataPermissionUtil.isCanPrint(stampFormId));
		} else {
			// 代表新增
			logger.error("入参id不能为空！");
			
			return "/error.vm";
		}
		return vm_path + "viewNotice.vm";
	}
	
	/**
	 * 保存通知函
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "noticeLetterSave.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject noticeLetterSave(ProjectRecoveryOrder order, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		
		String tipPrefix = " 保存通知函";
		//  函件id
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			
			for (ProjectRecoveryNoticeLetterInfo letter : order.getNoticeLetters()) {
				String content = letter.getContent();
				if (StringUtil.isNotBlank(content)) {
					//					javaScript中有三个可以对字符串编码的函数，分别是：
					//					escape,encodeURI,encodeURIComponent，
					//					相应3个解码函数：unescape,decodeURI,decodeURIComponent 。
					//					String s = new String(content.getBytes("ISO8859-1"), "UTF-8");
					//					String s2 = java.net.URLDecoder.decode(content, "UTF-8");
					//					String STR = StringEscapeUtils.unescapeHtml(EscapeUtil.unescape(content));
					String STR = StringEscapeUtils.unescapeHtml(java.net.URLDecoder.decode(content,
						"UTF-8"));
					letter.setContent(STR);
				}
				// 模版内容转码
				String contentMessage = letter.getContentMessage();
				if (StringUtil.isNotBlank(contentMessage)) {
					//					String STR2 = EscapeUtil.unescape(contentMessage);
					String STR2 = java.net.URLDecoder.decode(contentMessage, "UTF-8");
					letter.setContentMessage(STR2);
				}
			}
			
			ProjectRecoveryResult saveResult = projectRecoveryServiceClient.saveNoticeLetter(order);
			
			if (saveResult.getProjectRecoveryInfo() != null
				&& ListUtil.isNotEmpty(saveResult.getProjectRecoveryInfo().getNoticeLetters())) {
				JSONArray data = new JSONArray();
				JSONObject json = new JSONObject();
				Long guaranteeObligationSureId = 0L;
				Long debtObligationChangeNoticeId = 0L;
				Long recoveryNoticeId = 0L;
				Long guaranteeObligationNoticeId = 0L;
				// 循环抓去letters
				for (ProjectRecoveryNoticeLetterInfo info : saveResult.getProjectRecoveryInfo()
					.getNoticeLetters()) {
					if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_SURE == info
						.getLetterType()) {
						guaranteeObligationSureId = info.getId();
					} else if (ProjectRecoveryLetterTypeEnum.DEBT_OBLIGATION_CHANGE_NOTICE == info
						.getLetterType()) {
						debtObligationChangeNoticeId = info.getId();
					} else if (ProjectRecoveryLetterTypeEnum.RECOVERY_NOTICE == info
						.getLetterType()) {
						recoveryNoticeId = info.getId();
					} else if (ProjectRecoveryLetterTypeEnum.GUARANTEE_OBLIGATION_NOTICE == info
						.getLetterType()) {
						guaranteeObligationNoticeId = info.getId();
					}
				}
				List<Long> ids = new ArrayList<Long>();
				ids.add(guaranteeObligationSureId);
				ids.add(debtObligationChangeNoticeId);
				ids.add(recoveryNoticeId);
				ids.add(guaranteeObligationNoticeId);
				data.addAll(ids);
				json.put("success", true);
				json.put("message", tipPrefix + "成功");
				json.put("formIds", data);
				json.put("id", order.getId());
				return json;
			}
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 加载授信已落实的资产信息
	 * @param projectCode
	 * @param model
	 */
	@ResponseBody
	@RequestMapping("initPledge.json")
	public JSONObject initPledge(ProjectCreditConditionQueryOrder order, Model model) {
		
		String tipPrefix = "加载资产信息";
		JSONObject result = new JSONObject();
		try {
			AssetQueryOrder queryOrder = new AssetQueryOrder();
			// 设置分页属性
			MiscUtil.copyPoObject(queryOrder, order);
			JSONObject data = new JSONObject();
			// 查询所有已落实的
			order.setPageSize(10000L);
			order.setStatus(CheckStatusEnum.CHECK_PASS.code());
			QueryBaseBatchResult<ProjectCreditConditionInfo> queryResult = projectCreditConditionServiceClient
				.queryCreditCondition(order);
			//		资产id  assetId
			//		AssetStatusEnum
			//		是否取消 isCancelRelation false
			//  用list id查询list的资产信息
			//PledgeAssetService接口下的queryAssetSimple
			List<ProjectCreditConditionInfo> infos = queryResult.getPageList();
			QueryBaseBatchResult<AssetSimpleInfo> asserResult = new QueryBaseBatchResult<AssetSimpleInfo>();
			JSONArray dataList = new JSONArray();
			
			if (ListUtil.isNotEmpty(infos)) {
				List<Long> assetIds = new ArrayList<Long>();
				for (ProjectCreditConditionInfo info : infos) {
					long assetId = info.getAssetId();
					if (assetId > 0) {
						assetIds.add(assetId);
					}
				}
				if (ListUtil.isNotEmpty(assetIds)) {
					queryOrder.setAssetsIdList(assetIds);
					asserResult = pledgeAssetServiceClient.queryAssetSimple(queryOrder);
					List<AssetSimpleInfo> assetInfos = asserResult.getPageList();
					if (ListUtil.isNotEmpty(assetInfos)) {
						for (AssetSimpleInfo assetInfo : assetInfos) {
							JSONObject json = new JSONObject();
							json.put("assetId", assetInfo.getAssetsId());
							json.put("assetType", assetInfo.getAssetType());
							json.put("ownershipName", assetInfo.getOwnershipName());
							json.put("warrantNo", assetInfo.getWarrantNo());
							json.put("evaluationPrice", assetInfo.getEvaluationPrice());
							json.put("mortgagePrice", assetInfo.getMortgagePrice());
							// 将资产的信息回填到“名称“一列，
							//							回填文字显示，资产名称加权证号，
							//							如果没有权证号的，则为资产名称+抵押金额，
							//							如果“抵押金额为XXX元的应收账款”，
							StringBuilder sb = new StringBuilder();
							if (StringUtil.isNotEmpty(assetInfo.getWarrantNo())) {
								sb.append("权证号为 ");
								sb.append(assetInfo.getWarrantNo());
								sb.append(" 的");
								sb.append(assetInfo.getAssetType());
							} else {
								sb.append("抵押金额为 ");
								sb.append(assetInfo.getMortgagePrice());
								sb.append(" 元的");
								sb.append(assetInfo.getAssetType());
							}
							json.put("pledgeName", sb.toString());
							dataList.add(json);
						}
					}
					
				}
			}
			data.put("pageCount", asserResult.getPageCount());
			data.put("pageNumber", asserResult.getPageNumber());
			data.put("pageSize", asserResult.getPageSize());
			data.put("totalCount", asserResult.getTotalCount());
			data.put("pageList", dataList);
			
			result = toStandardResult(data, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error(tipPrefix + "失败，原因：" + e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 关闭开庭通知
	 * @param id
	 * @return
	 */
	@RequestMapping("endBeforeTrailNotice.htm")
	@ResponseBody
	public JSON endBeforeTrailNotice(ProjectRecoveryLitigationBeforeTrailOrder order, ModelMap model) {
		order.setEndNotice(BooleanEnum.YES);
		FcsBaseResult result = projectRecoveryServiceClient.changeBeforeTrailNotice(order);
		
		return toJSONResult(result, "关闭开庭通知");
	}
	
	/**
	 * 关闭保全措施通知
	 * @param id
	 * @return
	 */
	@RequestMapping("endPrecautionNotice.htm")
	@ResponseBody
	public JSONObject endPrecautionNotice(	ProjectRecoveryLitigationBeforePreservationPrecautionOrder order,
											ModelMap model) {
		order.setEndNotice(BooleanEnum.YES);
		FcsBaseResult result = projectRecoveryServiceClient
			.changePreservationPrecautionNotice(order);
		return toJSONResult(result, "关闭保全措施通知");
	}
}
