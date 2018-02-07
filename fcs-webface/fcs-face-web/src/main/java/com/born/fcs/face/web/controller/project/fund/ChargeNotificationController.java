package com.born.fcs.face.web.controller.project.fund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeNotificationExportInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.base.FormStatusEnum;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.brokerbusiness.BrokerBusinessFormInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.order.brokerbusiness.BrokerBusinessQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款通知
 * @author heh
 *
 */
@Controller
@RequestMapping("projectMg/chargeNotification")
public class ChargeNotificationController extends WorkflowBaseController {
	final static String vm_path = "/projectMg/cashMg/chargeNotice/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "payTime", "startTime", "endTime", "timeBegin", "timeEnd","payTime","anotherPayTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "payAmount", "chargeBase", "chargeAmount", "anotherPayAmount" };
	}
	
	/**
	 * 新增收款通知
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("addChargeNotification.htm")
	public String addChargeNotification(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		FChargeNotificationInfo info = new FChargeNotificationInfo();
		ProjectSimpleDetailInfo projectInfo = new ProjectSimpleDetailInfo();
		if (null != projectCode) {
			projectInfo = projectServiceClient.querySimpleDetailInfo(projectCode);
			//showFenbaoInfo(projectCode, model);
		}
		model.addAttribute("feeType", projectCode == null ? null : getFeeTypeEnum(projectCode));
		model.addAttribute("signFeeType", getSignFeeTypeEnum());
		model.addAttribute("booleanEnum", BooleanEnum.getAllEnum());
		model.addAttribute("chargeBasis", ChargeBasisEnum.getAllEnum());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", 0);
		return vm_path + "add_notice.vm";
	}
	
	/**
	 * 加载费用种类
	 *
	 * @param projectCode
	 * @return
	 */
	@RequestMapping("loadFeeTypeEnum.htm")
	@ResponseBody
	public JSONObject loadFeeTypeEnum(String projectCode) {
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			List<FeeTypeEnum> list = getFeeTypeEnum(projectCode);
			if (list != null && list.size() > 0) {
				for (FeeTypeEnum typeEnum : list) {
					JSONObject json = new JSONObject();
					json.put("code", typeEnum.code());
					json.put("message", typeEnum.message());
					dataList.add(json);
				}
			}
			result.put("feeTypeEnum", dataList);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e);
			logger.error("加载收款种类出错", e);
		}
		return result;
	}
	
	/**
	 * 得到费用类型枚举
	 *
	 * @param projectCode
	 * @return
	 */
	public List<FeeTypeEnum> getFeeTypeEnum(String projectCode) {
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		boolean isGuarantee = ProjectUtil.isGuarantee(projectInfo.getBusiType());//是否担保业务
		boolean isLitigation = ProjectUtil.isLitigation(projectInfo.getBusiType());//是否诉讼保函业务
		boolean isUnderwriting = ProjectUtil.isUnderwriting(projectInfo.getBusiType());//是否是承销业务
		boolean isEntrusted = ProjectUtil.isEntrusted(projectInfo.getBusiType());//是否是委贷业务
		boolean isBond = ProjectUtil.isBond(projectInfo.getBusiType());//发债是担保的一种
		boolean ishaveASSETS_TRANSFER = false;
		List<FeeTypeEnum> feeTypeEnum = Lists.newArrayList();
		if (projectInfo.getStatus() == ProjectStatusEnum.FINISH) {
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
		} else {
			
			if (isGuarantee || isBond) {
				//担保费、项目评审费、追偿收入、代偿本金收回、代偿利息收回、顾问费、存出保证金划回、存入保证金、代收款用、其他
				feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
				feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
				feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
				feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
				feeTypeEnum.add(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
				feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
				feeTypeEnum.add(FeeTypeEnum.OTHER);
			}
			if (isEntrusted) {
				//项目评审费、追偿收入、代偿本金收回、代偿利息收回、委贷本金收回、委贷利息收回、顾问费、存出保证金划回、存入保证金、其他
				feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
				feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_POUNDAGE);
				feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_IQUIDATED_DAMAGES);
				feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
				feeTypeEnum.add(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
				feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
				feeTypeEnum.add(FeeTypeEnum.OTHER);
			}
			if (isLitigation) {
				//担保费、顾问费、代收款、追偿收入、代偿本金收回、代偿利息收回、资产转让
				feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
				feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
				feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
				feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
				feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
				feeTypeEnum.add(FeeTypeEnum.ASSETS_TRANSFER);
				ishaveASSETS_TRANSFER = true;
			}
			if (isUnderwriting) {
				//承销费、代收款用、其他
				feeTypeEnum.add(FeeTypeEnum.CONSIGNMENT_INWARD_INCOME);
				feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
				feeTypeEnum.add(FeeTypeEnum.OTHER);
			}
			//当选择不需要上会，则该申请单审核通过，可收款；如果需要上会，则该项目上会的会议纪要内容为通过，则可收款，收款种类为“”资产转让“
			FAssetsTransferApplicationQueryOrder queryOrder = new FAssetsTransferApplicationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationServiceClient
				.query(queryOrder);
			if (batchResult != null && batchResult.getPageList().size() > 0) {
				FAssetsTransferApplicationInfo info = batchResult.getPageList().get(0);
				if (info.getIsToMeet() != null && info.getIsToMeet()==BooleanEnum.IS
					&& info.getCouncilStatus() == ProjectCouncilStatusEnum.COUNCIL_APPROVAL
					&& !ishaveASSETS_TRANSFER) {
					feeTypeEnum.add(FeeTypeEnum.ASSETS_TRANSFER);
				} else if (info.getIsToMeet()!=BooleanEnum.IS&&info.getFormStatus().code().equals(FormStatusEnum.APPROVAL.code())
							&& !ishaveASSETS_TRANSFER) {//审核通过
					feeTypeEnum.add(FeeTypeEnum.ASSETS_TRANSFER);
				}
			}
		}
		return feeTypeEnum;
	}
	
	/**
	 * 得到经纪业务收款种类
	 *
	 * @param
	 * @return
	 */
	public List<FeeTypeEnum> getSignFeeTypeEnum() {
		List<FeeTypeEnum> feeTypeEnum = Lists.newArrayList();
		//其他、经纪收入
		feeTypeEnum.add(FeeTypeEnum.SOLD_INCOME);
		feeTypeEnum.add(FeeTypeEnum.OTHER);
		return feeTypeEnum;
	}
	
	/**
	 * 保存收款通知
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveChargeNotification.htm")
	@ResponseBody
	public JSONObject saveChargeNotification(FChargeNotificationOrder order,HttpServletRequest request, Model model) {
		String tipPrefix = " 保存收款通知";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			if (order.getChargeBasis() == ChargeBasisEnum.PROJECT) {
				ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
						.getProjectCode());
				if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
					jsonObject.put("success", false);
					jsonObject.put("message", "该项目已被暂缓，不允许保存！");
					logger.error("保存收款通知出错", "该项目已被暂缓，不允许保存！");
					return jsonObject;
				}
				order.setInstitutionId(projectInfo.getInstitutionIds());
				order.setInstitutionName(projectInfo.getInstitutionNames());
				order.setChargeName(projectInfo.getChargeName());
				order.setChargeFee(projectInfo.getChargeFee());
				order.setChargeType(projectInfo.getChargeType()==null?null:projectInfo.getChargeType().code());
			} else {
				order.setProjectCode(order.getApplyCode());
				order.setCustomerName(order.getApplyTitle());
			}
			// 初始化Form验证信息
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setFormId(order.getFormId());
			order.setFormCode(FormCodeEnum.CHARGE_NOTIFICATION);
			order.setSubmitManId(sessionLocal.getUserId());
			order.setSubmitManName(sessionLocal.getRealName());
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			//校验feeType是存出保证划回 时不能大于project表self_deposit_amount
			//			boolean flag = true;
			//			if (order.getProjectCode() != null&&order.getChargeBasis()==ChargeBasisEnum.PROJECT) {
			//				flag = checkBackAmount(order);
			//			}
			//			if (!flag) {
			//				jsonObject.put("success", false);
			//				jsonObject.put("message", "存出保证金划回金额不能超过该项目已划付的存出保证金金额");
			//				logger.error("保存失败,存出保证金划回金额不能超过该项目已划付的存出保证金金额", "");
			//				return jsonObject;
			//			}
			
			FormBaseResult saveResult = chargeNotificationServiceClient
				.saveChargeNotification(order);
			addAttachfile(saveResult.getFormInfo().getFormId() + "", request,
					order.getProjectCode(), "收费通知单-基本信息附件", CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存收款通知成功", null);
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
			jsonObject.put("submitStatus", order.getSubmitStatus());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存收款通知出错", e);
		}
		
		return jsonObject;
	}
	
	public boolean checkBackAmount(FChargeNotificationOrder order) {
		List<FChargeNotificationInfo> noticeInfos = chargeNotificationServiceClient
			.queryAuditingByProjectCode(order.getProjectCode());
		Money totalBackMoney = new Money(0, 0);
		if (noticeInfos != null) {
			for (FChargeNotificationInfo noticeInfo : noticeInfos) {//审核中的单子 存出保证金总计
				List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
				if (feeInfos != null) {
					for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
						if (feeInfo.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
							totalBackMoney = totalBackMoney.add(feeInfo.getChargeAmount());
						}
					}
				}
			}
		}
		List<FChargeNotificationFeeOrder> feeOrders = order.getFeeList();
		if (feeOrders != null) {
			for (FChargeNotificationFeeOrder feeOrder : feeOrders) {//加上本张单子的金额
				if (feeOrder.getFeeType() != null) {
					if (feeOrder.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
						totalBackMoney = totalBackMoney.add(feeOrder.getChargeAmount());
					}
				}
			}
		}
		ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(), false);
		//校验
		if (projectInfo.getSelfDepositAmount().compareTo(totalBackMoney) == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 收款通知列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String StampApplyList(String rePage, FChargeNotificationQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FChargeNotificationResultInfo> batchResult = chargeNotificationServiceClient
			.queryChargeNotificationList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("chargeBasis", ChargeBasisEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 收款通知编辑
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("editChargeNotification.htm")
	public String editChargeNotification(Long formId, Model model) {
		String tipPrefix = "收款通知编辑";
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			FChargeNotificationInfo info = chargeNotificationServiceClient
				.queryChargeNotificationByFormId(formId);
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
				.getProjectCode());
			if (info.getChargeBasis() == ChargeBasisEnum.PROJECT) {
				model.addAttribute("feeType", getFeeTypeEnum(projectInfo.getProjectCode()));
			} else {
				model.addAttribute("signFeeType", getSignFeeTypeEnum());
			}
			queryCommonAttachmentData(model, String.valueOf(info.getFormId()),
					CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE);
			//showFenbaoInfo(info.getProjectCode(), model);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("info", info);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);
			model.addAttribute("showFormChangeApply", showFormChangeApply(info.getProjectCode()));
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "add_notice.vm";
	}
	
	/**
	 * 收款通知审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model, HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FChargeNotificationInfo info = chargeNotificationServiceClient
			.queryChargeNotificationByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		showFenbaoInfo(info.getProjectCode(), model);
		queryCommonAttachmentData(model, String.valueOf(info.getFormId()),
				CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE);
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", form.getCheckStatus());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("showFormChangeApply", showFormChangeApply(info.getProjectCode()));
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "investigateNotice.vm";
	}
	
	/**
	 * 收款通知审核--财务应付岗
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("affirmAudit.htm")
	public String affirmAudit(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FChargeNotificationInfo info = chargeNotificationServiceClient
			.queryChargeNotificationByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<FChargeNotificationFeeInfo> feeInfos = info.getFeeList();
		Money totalMoney = Money.zero();
		for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
			totalMoney = totalMoney.add(feeInfo.getChargeAmount());
		}
		queryCommonAttachmentData(model, String.valueOf(info.getFormId()),
				CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE);
		showFenbaoInfo(info.getProjectCode(), model);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("isFinanceAffirm", "IS");
		model.addAttribute("from", FinanceAffirmTypeEnum.CHARGE_NOTIFICATION);
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", form.getCheckStatus());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("showFormChangeApply", showFormChangeApply(info.getProjectCode()));
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "investigateNotice.vm";
	}
	
	@RequestMapping("viewAudit.htm")
	public String viewAudit(long formId, HttpServletRequest request, Model model,
							HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FChargeNotificationInfo info = chargeNotificationServiceClient
			.queryChargeNotificationByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		Money totalMoney = Money.zero();
		List<FChargeNotificationFeeInfo> feeInfos = info.getFeeList();
		if(info.getFirmInfo()!=null&&ListUtil.isNotEmpty(info.getFirmInfo().getFeeList())) {
			List<FFinanceAffirmDetailInfo> detailInfos = info.getFirmInfo().getFeeList();
			for (FFinanceAffirmDetailInfo detailInfo : detailInfos) {
				totalMoney = totalMoney.add(detailInfo.getPayAmount());
			}
		}
		queryCommonAttachmentData(model, String.valueOf(info.getFormId()),
				CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE);
		showFenbaoInfo(info.getProjectCode(), model);
		setAuditHistory2Page(form, model);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("isView", true);
		showProjectApproval(info.getProjectCode(), model);
		model.addAttribute("showFormChangeApply", showFormChangeApply(info.getProjectCode()));
		return vm_path + "investigateNotice.vm";
	}
	
	/**
	 * 查看历史收款情况
	 *
	 * @param projectCode
	 * @param model
	 * @return
	 */
	
	@RequestMapping("showChargeHistory.htm")
	@ResponseBody
	public JSONObject showChargeHistory(String projectCode, HttpServletRequest request,
										Model model, HttpSession session) {
		String tipPrefix = "查看历史收款情况";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			JSONArray dataList = new JSONArray();
			List<FChargeNotificationInfo> history = chargeNotificationServiceClient
				.queryChargeNotificationByProjectCode(projectCode);
			Money totalMoney = new Money(0, 0);
			Money payTotalMoney = new Money(0, 0);
			if (history != null && history.size() > 0) {
				for (FChargeNotificationInfo noticeInfo : history) {
					List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
					//财务确认信息
					List<FFinanceAffirmDetailInfo> detailInfos = null;
					Map<Long,Money> map=new HashMap<Long,Money>();
					if(noticeInfo.getFirmInfo()!=null&&ListUtil.isNotEmpty(noticeInfo.getFirmInfo().getFeeList())){
						detailInfos = noticeInfo.getFirmInfo().getFeeList();
						for(FFinanceAffirmDetailInfo detailInfo:detailInfos){
							map.put(detailInfo.getDetailId(),detailInfo.getPayAmount());
						}
					}

					if (feeInfos != null) {
						for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
							totalMoney = totalMoney.add(feeInfo.getChargeAmount());
							JSONObject json = new JSONObject();
							json.put("chargeNo", noticeInfo.getChargeNo());
							json.put("chargeTime",
								DateUtil.dtSimpleFormat(noticeInfo.getChargeTime()));
							json.put("feeType", feeInfo.getFeeType().message());
							json.put("chargeBase", feeInfo.getChargeBase());
							json.put("chargeRate", feeInfo.getChargeRate());
							json.put(
								"chargePeriod",
								DateUtil.dtSimpleFormat(feeInfo.getStartTime()) + " "
										+ DateUtil.dtSimpleFormat(feeInfo.getEndTime()));
							json.put("chargeAmount", feeInfo.getChargeAmount());
							json.put("payAmount", map.get(feeInfo.getId())==null?Money.zero():map.get(feeInfo.getId()));
							payTotalMoney=payTotalMoney.add(map.get(feeInfo.getId())==null?Money.zero():map.get(feeInfo.getId()));
							dataList.add(json);
						}
					}
				}
			}
			data.put("dataList", dataList);
			data.put("totalMoney", totalMoney);
			data.put("payTotalMoney", payTotalMoney);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 加载签报事项
	 *
	 * @param
	 * @param model
	 * @return
	 */
	
	@RequestMapping("loadSign.json")
	@ResponseBody
	public JSONObject contractChoose(BrokerBusinessQueryOrder order, Model model) {
		String tipPrefix = "加载经纪业务";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			setSessionLocalInfo2Order(order);
			order.setIsSelForCharge(BooleanEnum.IS.code());
			QueryBaseBatchResult<BrokerBusinessFormInfo> batchResult = brokerBusinessServiceClient
				.queryPage(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (BrokerBusinessFormInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("customerName", info.getCustomerName());
					json.put("author", info.getFormUserName());
					json.put("isCouncil", info.getIsNeedCouncil().message());
					dataList.add(json);
				}
			}
			model.addAttribute("conditions", order);
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}

	/***
	 * 导出
	 */
	@RequestMapping("chargeNotificationExport.htm")
	public String chargeNotificationExport(FChargeNotificationQueryOrder order, HttpServletRequest request,
											HttpServletResponse response, Model model) {
		setSessionLocalInfo2Order(order);
		ReportDataResult result = chargeNotificationServiceClient
				.chargeNorificationExport(order);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("收款通知单");
		TableBuilder builder = new TableBuilder(result, reportTemplate,
				TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		return null;
	}

	@RequestMapping("recoveryEenteustedLoanPrincipalWithdrawal.json")
	@ResponseBody
	public JSONObject recoveryEenteustedLoanPrincipalWithdrawal(String projectCode,String amount) {
		JSONObject json = new JSONObject();
		FcsBaseResult result = chargeNotificationServiceClient.recoveryEenteustedLoanPrincipalWithdrawal(projectCode,amount);
		if (result != null && result.isSuccess()) {
			json.put("success", true);
			json.put("message", "调用成功");
		} else {
			json.put("success", false);
			json.put("message", result == null ? "调用失败" : result.getMessage());
		}
		return json;
	}
}
