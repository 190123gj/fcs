package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.PermissionUtil;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormFeeInfo;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.order.payment.FormPaymentOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("fundMg/payment/apply")
public class PaymentApplyController extends WorkflowBaseController {
	
	private static final String vm_path = "/fundMg/fundMgModule/payment/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "paymentDate" };
	}
	
	/**
	 * 被扣划单
	 * @param sourceId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("deductForm.htm")
	public String deductForm(Long sourceId, HttpServletRequest request, Model model) {
		return form(sourceId, request, model);
	}
	
	/**
	 * @param sourceId 单据来源
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(Long sourceId, HttpServletRequest request, Model model) {
		
		ReceiptPaymentFormInfo rpfInfo = new ReceiptPaymentFormInfo();
		rpfInfo.setIsSimple(request.getParameter("isSimple"));
		if (sourceId != null && sourceId > 0) {
			
			rpfInfo = receiptPaymentFormServiceClient.queryBySourceId(sourceId, true);
			if (rpfInfo == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "来源单据不存在");
			}
			
			if (rpfInfo.getFundDirection() != FundDirectionEnum.OUT) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "付款单不支持的单据");
			}
			
			if (rpfInfo.getStatus() != ReceiptPaymentFormStatusEnum.NOT_PROCESS) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"单据已处理");
			}
			
			//默认新建一个单据方便取数据
			FormPaymentInfo paymentInfo = new FormPaymentInfo();
			BeanCopier.staticCopy(rpfInfo, paymentInfo);
			paymentInfo.setApplyUserId(rpfInfo.getUserId());
			paymentInfo.setApplyUserName(rpfInfo.getUserName());
			paymentInfo.setApplyUserAccount(rpfInfo.getUserAccount());
			paymentInfo.setApplyDeptId(rpfInfo.getDeptId());
			paymentInfo.setApplyDeptCode(rpfInfo.getDeptCode());
			paymentInfo.setApplyDeptName(rpfInfo.getDeptName());
			paymentInfo.setFormSource(FormSourceEnum.FORM);
			
			// 科目集合
			//			SysSubjectMessageOrder sysSubjectMessageOrder = new SysSubjectMessageOrder();
			//			sysSubjectMessageOrder.setSubjectType(SubjectTypeEnum.PAYMENT);
			//			sysSubjectMessageOrder.setPageSize(999);
			//			QueryBaseBatchResult<SysSubjectMessageInfo> syss = sysSubjectMessageServiceClient
			//				.querySubject(sysSubjectMessageOrder);
			//			List<SysSubjectMessageInfo> subjects = null;
			//			if (syss != null && ListUtil.isNotEmpty(syss.getPageList())) {
			//				subjects = syss.getPageList();
			//			}
			//费用明细
			List<ReceiptPaymentFormFeeInfo> sourceFeeList = rpfInfo.getFeeList();
			List<FormPaymentFeeInfo> feeList = Lists.newArrayList();
			
			for (ReceiptPaymentFormFeeInfo fee : sourceFeeList) {
				FormPaymentFeeInfo frf = new FormPaymentFeeInfo();
				BeanCopier.staticCopy(fee, frf);
				frf.setPaymentAccount(fee.getAccount());
				BankMessageResult bankResult = bankMessageServiceClient.findByAccount(fee
					.getAccount());
				if (bankResult != null && bankResult.isSuccess()
					&& bankResult.getBankMessageInfo() != null) {
					frf.setCanUseAmount(bankResult.getBankMessageInfo().getAmount());
				}
				frf.setPaymentDate(fee.getOccurTime());
				// 加载默认会计科目
				//				for (SysSubjectMessageInfo subject : subjects) {
				//					if (fee.getFeeType() == subject.getSubjectCostType()) {
				//						frf.setAtCode(subject.getAtCode());
				//						frf.setAtName(subject.getAtName());
				//					}
				//				}
				feeList.add(frf);
			}
			paymentInfo.setFeeList(feeList);
			model.addAttribute("paymentInfo", paymentInfo);
			
			FormInfo form = new FormInfo();
			form.setFormCode(FormCodeEnum.FM_PAYMENT_APPLY);
			model.addAttribute("form", form);
			
		} else {
			FormPaymentInfo paymentInfo = new FormPaymentInfo();
			paymentInfo.setFormSource(FormSourceEnum.OTHER);
			model.addAttribute("paymentInfo", paymentInfo);
		}
		model.addAttribute("isSimple", rpfInfo.getIsSimple());
		getModelEnums(model, rpfInfo.getProjectCode(), rpfInfo.getSourceForm());
		return vm_path + "applyAdd.vm";
	}
	
	private void getModelEnums(Model model, String projectCode, SourceFormEnum source) {
		List<SubjectCostTypeEnum> feeTypes = new ArrayList<SubjectCostTypeEnum>();
		if (StringUtil.isBlank(projectCode)) {
			// 需要判断是否是内部借款单据，然后给与 内部借款单据的信息
			if (SourceFormEnum.INNER_LOAN == source) {
				feeTypes.add(SubjectCostTypeEnum.INTERNAL_FUND_LENDING);
				feeTypes.add(SubjectCostTypeEnum.PAYMENT_OTHER);
				model.addAttribute("feeTypes", feeTypes);
			} else {
				model.addAttribute("feeTypes", SubjectCostTypeEnum.getPaymentEnums());
			}
		} else {
			// 判断是否是理财产品
			if (ProjectUtil.isFinancial(projectCode)) {
				feeTypes.add(SubjectCostTypeEnum.FINANCIAL_PRODUCT);
				
				feeTypes.add(SubjectCostTypeEnum.FINANCIAL_PRODUCT_BUY_BACK);
			} else {
				if (source == SourceFormEnum.CAPITAL_APPROPRIATION_COMP) {
					feeTypes.add(SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL);
					feeTypes.add(SubjectCostTypeEnum.COMPENSATORY_INTEREST);
					feeTypes.add(SubjectCostTypeEnum.COMPENSATORY_PENALTY);
					feeTypes.add(SubjectCostTypeEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
					feeTypes.add(SubjectCostTypeEnum.COMPENSATORY_OTHER);
				} else {
					List<PaymentMenthodEnum> enums = fCapitalAppropriationApplyServiceClient
						.getPaymentMenthodEnum(projectCode);
					for (PaymentMenthodEnum e : enums) {
						if (PaymentMenthodEnum.OTHER == e) {
							feeTypes.add(SubjectCostTypeEnum.PAYMENT_OTHER);
							continue;
						}
						SubjectCostTypeEnum sub = SubjectCostTypeEnum.getByCode(e.getCode());
						if (sub != null) {
							feeTypes.add(sub);
						}
					}
				}
			}
			model.addAttribute("feeTypes", feeTypes);
		}
		model.addAttribute("otherFeeTypeList", SubjectCostTypeEnum.getPaymentOtherEnums());
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		
		// 弹出层查询数据
		
		List<SourceFormEnum> sources = new ArrayList<SourceFormEnum>();
		sources.add(SourceFormEnum.CAPITAL_APPROPRIATION);
		//		sources.add(SourceFormEnum.LOAN_USE);
		//		sources.add(SourceFormEnum.REFUND);
		sources.add(SourceFormEnum.INNER_LOAN);
		model.addAttribute("sources", sources);
		
	}
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(FormPaymentQueryOrder order, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			//  2017-3-1 产品&王书&张文静 ： 管理员查看所有数据
			if (DataPermissionUtil.isCWYFG() || DataPermissionUtil.isSystemAdministrator()
				|| PermissionUtil.checkPermission("/fundMg/payment/apply/deductForm.htm")) {//财务应收拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
		//		setSessionLocalInfo2Order(order);
		if (!StringUtil.equals(order.getIsSimple(), "IS")) {
			order.setIsSimple("NO");
		}
		QueryBaseBatchResult<FormPaymentFormInfo> batchResult = paymentApplyServiceClient
			.searchForm(order);
		model.addAttribute("conditions", order);
		model.addAttribute("isSimple", order.getIsSimple());
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, model);
		
		return vm_path + "viewApplyAdd.vm";
	}
	
	/**
	 * 编辑
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, model);
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		initWorkflow(model, viewApply(formId, model), request.getParameter("taskId"));
		return vm_path + "viewApplyAdd.vm";
	}
	
	/**
	 * 财务应付岗审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canModify.htm")
	public String auditCanModify(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("onlyChangeDetailList", BooleanEnum.YES.code());
		initWorkflow(model, viewApply(formId, model), request.getParameter("taskId"));
		return vm_path + "viewApplyAdd.vm";
	}
	
	/**
	 * 打印
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, model);
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 查询单据信息
	 * @param formId
	 * @param model
	 * @return
	 */
	private FormInfo viewApply(long formId, Model model) {
		
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "付款单不存在");
		}
		model.addAttribute("form", formInfo);
		FormPaymentInfo paymentInfo = paymentApplyServiceClient.findPaymentByFormId(formId, true);
		model.addAttribute("paymentInfo", paymentInfo);
		getModelEnums(model, paymentInfo.getProjectCode(), null);
		setAuditHistory2Page(formInfo, model);
		
		if (paymentInfo != null && paymentInfo.getSourceForm() != null
			&& paymentInfo.getSourceFormId() != null) {
			ReceiptPaymentFormInfo rpInfo = receiptPaymentFormServiceClient.queryBySourceFormId(
				paymentInfo.getSourceForm(), paymentInfo.getSourceFormId(), false);
			if (rpInfo != null) {
				model.addAttribute("isSimple", rpInfo.getIsSimple());
			}
		}
		
		return formInfo;
	}
	
	/**
	 * 保存付款单
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FormPaymentOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "保存付款申请单";
		try {
			order.setFormCode(FormCodeEnum.FM_PAYMENT_APPLY);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			
			FormBaseResult saveResult = paymentApplyServiceClient.saveApply(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "保存付款单出错");
			logger.error("保存付款单出错 {}", e);
		}
		return result;
	}
}
