package com.born.fcs.face.web.controller.project.fund;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationInfo;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("projectMg/refundApplication")
public class RefundApplicationController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/cashMg/returnPremium/";
	
	/**
	 * 退费申请单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String refundApplicationList(FRefundApplicationQueryOrder queryOrder, Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new FRefundApplicationQueryOrder();
			
			setSessionLocalInfo2Order(queryOrder);
			model.addAttribute("isBusiManager", isBusiManager());
			if (StringUtil.isEmpty(queryOrder.getSortCol())) {
				queryOrder.setSortCol("form_add_time");
				queryOrder.setSortOrder("DESC");
			}
			QueryBaseBatchResult<FRefundApplicationInfo> batchResult = refundApplicationServiceClient
				.query(queryOrder);
			model.addAttribute("conditions", queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		} catch (Exception e) {
			logger.error("查询退费申请单列表出错");
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看退费详情
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FRefundApplicationInfo info = refundApplicationServiceClient
			.findRefundApplicationByFormId(formId);
		
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		
		List<FRefundApplicationFeeInfo> feeInfoList = refundApplicationServiceClient
			.findByRefundId(info.getRefundId());
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("info", info);
		model.addAttribute("form", form);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("feeInfoList", feeInfoList);
		
		return vm_path + "viewAdd.vm";
	}
	
	/**
	 * 编辑退费申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FRefundApplicationInfo info = refundApplicationServiceClient
			.findRefundApplicationByFormId(formId);
		
		RefundApplicationResult refundResult = refundApplicationServiceClient
			.findAmountByChargeNotification(info.getProjectCode(), info.getRefundId(), true);
		model.addAttribute("refundResult", toJson(refundResult));
		List<FRefundApplicationFeeInfo> feeInfoList = refundApplicationServiceClient
			.findByRefundId(info.getRefundId());
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("feeType", getFeeTypeEnum(info.getProjectCode()));
		model.addAttribute("isEdit", "true");//是否编辑
		model.addAttribute("feeInfoList", feeInfoList);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		return vm_path + "add.vm";
	}
	
	/**
	 * 新增退费申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	@ResponseBody
	public JSONObject add(String projectCode, Model model) {
		JSONObject jsonObject = new JSONObject();
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		RefundApplicationResult refundResult = refundApplicationServiceClient
			.findAmountByChargeNotification(projectCode, 0, true);
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
		jsonObject.put("feeType", dataList);
		jsonObject.put("refundResult", refundResult);
		//jsonObject.put("feeType",getFeeTypeEnum(projectCode));
		jsonObject.put("projectInfo", projectInfo);
		//		model.addAttribute("refundResult", refundResult);
		//		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		//		
		//		model.addAttribute("projectInfo", projectInfo);
		jsonObject.put("ishaveApproval", isHaveApproval(projectInfo));
		return jsonObject;
	}
	
	/**
	 * 去新增资金划付
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(Model model) {
		FRefundApplicationInfo info = new FRefundApplicationInfo();
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("info", info);
		return vm_path + "add.vm";
	}
	
	/**
	 * 保存退费申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, HttpServletResponse response,
							FRefundApplicationOrder order, String remark1, Model model) {
		String tipPrefix = "保存退费申请单";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// 初始化Form验证信息
			FormBaseResult result = null;
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setFormId(order.getFormId());
			order.setFormCode(FormCodeEnum.REFUND_APPLICATION);
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			setSessionLocalInfo2Order(order);
			order.setRemark(remark1);
			result = refundApplicationServiceClient.saveRefundApplication(order);
			jsonObject = toJSONResult(jsonObject, result, "提交退费申请单成功", null);
			jsonObject.put("formId", result.getFormInfo().getFormId());
			
			if (result.isSuccess() && order.getCheckStatus() == 1) {
				jsonObject.put("success", true);
				jsonObject.put("status", "SUBMIT");
				jsonObject.put("message", "提交成功");
			} else if (result.isSuccess() && order.getCheckStatus() == 0) {
				jsonObject.put("success", true);
				jsonObject.put("status", "hold");
				jsonObject.put("message", "暂存成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 退费申请单审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String toPage, HttpServletRequest request, Integer toIndex,
						Model model, HttpSession session) {
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		// SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		FRefundApplicationInfo info = refundApplicationServiceClient
			.findRefundApplicationByFormId(formId);
		
		List<FRefundApplicationFeeInfo> feeInfoList = refundApplicationServiceClient
			.findByRefundId(info.getRefundId());
		
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		
		model.addAttribute("feeInfoList", feeInfoList);
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("info", info);
		model.addAttribute("currPage", toPage);
		
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		if (toIndex == null)
			toIndex = 0;
		model.addAttribute("toIndex", toIndex);
		//WebNodeInfo nodeInfo = initWorkflow(model, form, request.getParameter("taskId"));
		//if(nodeInfo.getFormUrl())
		initWorkflow(model, form, request.getParameter("taskId"));
		
		return vm_path + "viewAdd.vm";
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
		List<FeeTypeEnum> feeTypeEnum = Lists.newArrayList();
		if (isGuarantee || isBond) {
			//担保费、项目评审费、顾问费、其他
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
			feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
			feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		if (isEntrusted) {
			//委贷利息、项目评审费、顾问费、其他
			feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
			feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		if (isLitigation) {
			//担保费、其他
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		if (isUnderwriting) {
			//承销费、代收费用、其他
			feeTypeEnum.add(FeeTypeEnum.CONSIGNMENT_INWARD_INCOME);
			feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		return feeTypeEnum;
	}
	
	/**
	 * 打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FRefundApplicationInfo info = refundApplicationServiceClient
			.findRefundApplicationByFormId(formId);
		
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		
		List<FRefundApplicationFeeInfo> feeInfoList = refundApplicationServiceClient
			.findByRefundId(info.getRefundId());
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("info", info);
		model.addAttribute("form", form);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("feeInfoList", feeInfoList);
		
		return vm_path + "viewAdd.vm";
	}
	
	//承销项目判断是否有批复
	private String isHaveApproval(ProjectSimpleDetailInfo info) {
		if (info == null) {
			return null;
		}
		if (ProjectUtil.isUnderwriting(info.getBusiType()) && StringUtil.isEmpty(info.getSpCode())) {
			return "NO";
		}
		if (StringUtil.isEmpty(info.getSpCode())) {
			return "NO";
		}
		return "IS";
	}
	
	public JSONObject toJson(RefundApplicationResult refundResult) {
		JSONObject json = new JSONObject();
		json.put("compensatoryInterestWithdrawal", refundResult.getCompensatoryInterestWithdrawal());
		json.put("compensatoryPrincipalWithdrawal",
			refundResult.getCompensatoryPrincipalWithdrawal());
		json.put("consignmentInwardIncome", refundResult.getConsignmentInwardIncome());
		json.put("consultFee", refundResult.getConsultFee());
		json.put("entrustedLoanInterestWithdrawal",
			refundResult.getEntrustedLoanInterestWithdrawal());
		json.put("entrustedLoanPrincipalWithdrawal",
			refundResult.getEntrustedLoanPrincipalWithdrawal());
		
		json.put("guaranteeDeposit", refundResult.getGuaranteeDeposit());
		json.put("guaranteeFee", refundResult.getGuaranteeFee());
		json.put("projectReviewFee", refundResult.getProjectReviewFee());
		json.put("proxyCharging", refundResult.getProxyCharging());
		json.put("recoveryIncome", refundResult.getRecoveryIncome());
		json.put("refundableDepositsDrawBack", refundResult.getRefundableDepositsDrawBack());
		json.put("other", refundResult.getOther());
		json.put("assetsTransfer", refundResult.getAssetsTransfer());
		return json;
	}
}
