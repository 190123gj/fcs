package com.born.fcs.face.web.controller.fund.receipt;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
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
import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.receipt.FormReceiptOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("fundMg/receipt/apply")
public class ReceiptApplyController extends WorkflowBaseController {
	
	private static final String vm_path = "/fundMg/fundMgModule/gathering/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "receiptDate" };
	}
	
	/**
	 * @param sourceId 单据来源
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(Long sourceId, Model model) {
		
		model.addAttribute("formSource", FormSourceEnum.getAllEnum());
		model.addAttribute("feeTypeList", SubjectCostTypeEnum.getReceiptEnums());
		List<SubjectCostTypeEnum> projectFeeTypeList = Lists.newArrayList();
		List<SubjectCostTypeEnum> otherFeeTypeList = Lists.newArrayList();
		otherFeeTypeList.add(SubjectCostTypeEnum.INTEREST_INCOME);
		otherFeeTypeList.add(SubjectCostTypeEnum.EQUITY_INVESTMENT_INCOME);
		otherFeeTypeList.add(SubjectCostTypeEnum.EQUITY_INVESTMENT_CAPITAL_BACK);
		otherFeeTypeList.add(SubjectCostTypeEnum.OUTSIDE_FINANCING);
		otherFeeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		for (SubjectCostTypeEnum feeType : SubjectCostTypeEnum.getReceiptEnums()) {
			if (feeType != SubjectCostTypeEnum.INTEREST_INCOME
				&& feeType != SubjectCostTypeEnum.EQUITY_INVESTMENT_INCOME
				&& feeType != SubjectCostTypeEnum.EQUITY_INVESTMENT_CAPITAL_BACK
				&& feeType != SubjectCostTypeEnum.OUTSIDE_FINANCING)
				projectFeeTypeList.add(feeType);
		}
		model.addAttribute("projectFeeTypeList", projectFeeTypeList);
		model.addAttribute("otherFeeTypeList", otherFeeTypeList);
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		
		if (sourceId != null && sourceId > 0) {
			
			ReceiptPaymentFormInfo rpfInfo = receiptPaymentFormServiceClient.queryBySourceId(
				sourceId, true);
			if (rpfInfo == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "来源单据不存在");
			}
			
			if (rpfInfo.getFundDirection() != FundDirectionEnum.IN) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "收款单不支持的单据");
			}
			
			if (rpfInfo.getStatus() != ReceiptPaymentFormStatusEnum.NOT_PROCESS) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"单据已处理");
			}
			
			List<SubjectCostTypeEnum> feeTypeList = getMatchFeeType(rpfInfo.getSourceForm(),
				rpfInfo.getProjectCode());
			if (ListUtil.isEmpty(feeTypeList)) {
				feeTypeList = projectFeeTypeList;
			}
			model.addAttribute("projectFeeTypeList", feeTypeList);
			
			//默认新建一个单据方便取数据
			FormReceiptInfo receiptInfo = new FormReceiptInfo();
			BeanCopier.staticCopy(rpfInfo, receiptInfo);
			receiptInfo.setApplyUserId(rpfInfo.getUserId());
			receiptInfo.setApplyUserName(rpfInfo.getUserName());
			receiptInfo.setApplyUserAccount(rpfInfo.getUserAccount());
			receiptInfo.setApplyDeptId(rpfInfo.getDeptId());
			receiptInfo.setApplyDeptCode(rpfInfo.getDeptCode());
			receiptInfo.setApplyDeptName(rpfInfo.getDeptName());
			receiptInfo.setFormSource(FormSourceEnum.FORM);
			
			// 科目集合
			//			SysSubjectMessageOrder sysSubjectMessageOrder = new SysSubjectMessageOrder();
			//			sysSubjectMessageOrder.setSubjectType(SubjectTypeEnum.RECEIPT);
			//			sysSubjectMessageOrder.setPageSize(999);
			//			QueryBaseBatchResult<SysSubjectMessageInfo> syss = sysSubjectMessageServiceClient
			//				.querySubject(sysSubjectMessageOrder);
			//			List<SysSubjectMessageInfo> subjects = null;
			//			if (syss != null && ListUtil.isNotEmpty(syss.getPageList())) {
			//				subjects = syss.getPageList();
			//			}
			
			//费用明细
			List<ReceiptPaymentFormFeeInfo> sourceFeeList = rpfInfo.getFeeList();
			List<FormReceiptFeeInfo> feeList = Lists.newArrayList();
			for (ReceiptPaymentFormFeeInfo fee : sourceFeeList) {
				FormReceiptFeeInfo frf = new FormReceiptFeeInfo();
				BeanCopier.staticCopy(fee, frf);
				frf.setAccount(fee.getAccount());
				frf.setReceiptDate(fee.getOccurTime());
				// 加载默认会计科目
				//				for (SysSubjectMessageInfo subject : subjects) {
				//					if (fee.getFeeType() == subject.getSubjectCostType()) {
				//						frf.setAtCode(subject.getAtCode());
				//						frf.setAtName(subject.getAtName());
				//					}
				//				}
				feeList.add(frf);
			}
			receiptInfo.setFeeList(feeList);
			model.addAttribute("receiptInfo", receiptInfo);
			
			FormInfo form = new FormInfo();
			form.setFormCode(FormCodeEnum.FM_RECEIPT_APPLY);
			model.addAttribute("form", form);
		} else {
			FormReceiptInfo receiptInfo = new FormReceiptInfo();
			receiptInfo.setFormSource(FormSourceEnum.OTHER);
			model.addAttribute("receiptInfo", receiptInfo);
		}
		
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 获取单据匹配的费用类型
	 * @param rpfInfo
	 * @return
	 */
	private List<SubjectCostTypeEnum> getMatchFeeType(SourceFormEnum sourceForm, String projectCode) {
		List<SubjectCostTypeEnum> feeTypeList = Lists.newArrayList();
		if (sourceForm == SourceFormEnum.FINANCIAL_REDEEM
			|| sourceForm == SourceFormEnum.FINANCIAL_REDEEM_EXPIRE) {
			feeTypeList.add(SubjectCostTypeEnum.FINANCING_PRODUCT_REDEMPTION);
			feeTypeList.add(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
			feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		} else if (sourceForm == SourceFormEnum.FINANCIAL_SETTLEMENT) {
			feeTypeList.add(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
			feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		} else if (sourceForm == SourceFormEnum.FINANCIAL_TRANSFER) {
			feeTypeList.add(SubjectCostTypeEnum.FINANCING_PRODUCT_TRANSFER_NOT);
			feeTypeList.add(SubjectCostTypeEnum.FINANCING_PRODUCT_TRANSFER);
			feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		} else if (sourceForm == SourceFormEnum.FINANCIAL_SETTLEMENT) {
			feeTypeList.add(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
			feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		} else if (SourceFormEnum.INNER_LOAN_IN == sourceForm) {
			feeTypeList.add(SubjectCostTypeEnum.INNER_ACCOUNT_LENDING);
			feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		} else {
			if (StringUtil.isNotEmpty(projectCode)) {
				if (projectCode.startsWith("JJ")) {
					feeTypeList.add(SubjectCostTypeEnum.SOLD_INCOME);
					feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
				} else if (!ProjectUtil.isFinancial(projectCode)) {
					
					String busiType = ProjectUtil.getBusiTypeByCode(projectCode);
					boolean isGuarantee = ProjectUtil.isGuarantee(busiType);
					boolean isBond = ProjectUtil.isBond(busiType);
					boolean isEntrusted = ProjectUtil.isEntrusted(busiType);
					boolean isLitigation = ProjectUtil.isLitigation(busiType);
					boolean isUnderwriting = ProjectUtil.isUnderwriting(busiType);
					
					if (isGuarantee || isBond) {
						//担保费、项目评审费、追偿收入、代偿本金收回、代偿利息收回、顾问费、存出保证金划回、存入保证金、代收费用、其他
						feeTypeList.add(SubjectCostTypeEnum.GUARANTEE_FEE);
						feeTypeList.add(SubjectCostTypeEnum.PROJECT_REVIEW_FEE);
						feeTypeList.add(SubjectCostTypeEnum.RECOVERY_INCOME);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
						feeTypeList
							.add(SubjectCostTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.CONSULT_FEE);
						feeTypeList.add(SubjectCostTypeEnum.GUARANTEE_DEPOSIT);
						feeTypeList.add(SubjectCostTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
						feeTypeList.add(SubjectCostTypeEnum.PROXY_CHARGING);
						feeTypeList.add(SubjectCostTypeEnum.ASSETS_TRANSFER);
					}
					if (isEntrusted) {
						//项目评审费、追偿收入、代偿本金收回、代偿利息收回、委贷本金收回、委贷利息收回、顾问费、存出保证金划回、存入保证金、其他
						feeTypeList.add(SubjectCostTypeEnum.PROJECT_REVIEW_FEE);
						feeTypeList.add(SubjectCostTypeEnum.RECOVERY_INCOME);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
						feeTypeList
							.add(SubjectCostTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.ENTRUSTED_LOAN_POUNDAGE);
						feeTypeList.add(SubjectCostTypeEnum.CONSULT_FEE);
						feeTypeList.add(SubjectCostTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
						feeTypeList.add(SubjectCostTypeEnum.GUARANTEE_DEPOSIT);
						feeTypeList.add(SubjectCostTypeEnum.ASSETS_TRANSFER);
					}
					if (isLitigation) {
						//担保费、顾问费、代收费、追偿收入、代偿本金收回、代偿利息收回、资产转让
						feeTypeList.add(SubjectCostTypeEnum.GUARANTEE_FEE);
						feeTypeList.add(SubjectCostTypeEnum.CONSULT_FEE);
						feeTypeList.add(SubjectCostTypeEnum.PROXY_CHARGING);
						feeTypeList.add(SubjectCostTypeEnum.RECOVERY_INCOME);
						feeTypeList.add(SubjectCostTypeEnum.GUARANTEE_DEPOSIT);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
						feeTypeList
							.add(SubjectCostTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
						feeTypeList.add(SubjectCostTypeEnum.ASSETS_TRANSFER);
					}
					if (isUnderwriting) {
						//承销费、代收费用、其他
						feeTypeList.add(SubjectCostTypeEnum.CONSIGNMENT_INWARD_INCOME);
						feeTypeList.add(SubjectCostTypeEnum.PROXY_CHARGING);
					}
					feeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
				}
			}
		}
		return feeTypeList;
	}
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(FormReceiptQueryOrder order, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			//  2017-3-1 产品&王书&张文静 ： 管理员查看所有数据
			if (DataPermissionUtil.isCWYSG() || DataPermissionUtil.isSystemAdministrator()) {//财务应收拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
		}
		
		QueryBaseBatchResult<FormReceiptFormInfo> batchResult = receiptApplyServiceClient
			.searchForm(order);
		model.addAttribute("queryOrder", order);
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
		return vm_path + "applyViewAudit.vm";
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
		FormInfo formInfo = viewApply(formId, model);
		if (formInfo.getStatus() != FormStatusEnum.DRAFT
			&& formInfo.getStatus() != FormStatusEnum.BACK
			&& formInfo.getStatus() != FormStatusEnum.CANCEL) {
			return vm_path + "applyViewAudit.vm";
		}
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
		return vm_path + "applyViewAudit.vm";
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
		return vm_path + "applyViewAudit.vm";
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
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "收款单不存在");
		}
		
		FormReceiptInfo receiptInfo = receiptApplyServiceClient.findReceiptByFormId(formId, true);
		model.addAttribute("receiptInfo", receiptInfo);
		model.addAttribute("form", formInfo);
		
		model.addAttribute("formSource", FormSourceEnum.getAllEnum());
		model.addAttribute("feeTypeList", SubjectCostTypeEnum.getReceiptEnums());
		List<SubjectCostTypeEnum> projectFeeTypeList = Lists.newArrayList();
		List<SubjectCostTypeEnum> otherFeeTypeList = Lists.newArrayList();
		otherFeeTypeList.add(SubjectCostTypeEnum.INTEREST_INCOME);
		otherFeeTypeList.add(SubjectCostTypeEnum.EQUITY_INVESTMENT_INCOME);
		otherFeeTypeList.add(SubjectCostTypeEnum.EQUITY_INVESTMENT_CAPITAL_BACK);
		otherFeeTypeList.add(SubjectCostTypeEnum.OUTSIDE_FINANCING);
		otherFeeTypeList.add(SubjectCostTypeEnum.RECEIPT_OTHER);
		for (SubjectCostTypeEnum feeType : SubjectCostTypeEnum.getReceiptEnums()) {
			if (feeType != SubjectCostTypeEnum.INTEREST_INCOME
				&& feeType != SubjectCostTypeEnum.EQUITY_INVESTMENT_INCOME
				&& feeType != SubjectCostTypeEnum.EQUITY_INVESTMENT_CAPITAL_BACK
				&& feeType != SubjectCostTypeEnum.OUTSIDE_FINANCING)
				projectFeeTypeList.add(feeType);
		}
		model.addAttribute("projectFeeTypeList", projectFeeTypeList);
		
		List<SubjectCostTypeEnum> feeTypeList = getMatchFeeType(receiptInfo.getSourceForm(),
			receiptInfo.getProjectCode());
		if (ListUtil.isEmpty(feeTypeList)) {
			feeTypeList = projectFeeTypeList;
		}
		
		model.addAttribute("projectFeeTypeList", feeTypeList);
		model.addAttribute("otherFeeTypeList", otherFeeTypeList);
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		
		setAuditHistory2Page(formInfo, model);
		
		return formInfo;
	}
	
	/**
	 * 保存付款单
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FormReceiptOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(FormCodeEnum.FM_RECEIPT_APPLY);
			setSessionLocalInfo2Order(order);
			FormBaseResult sResult = receiptApplyServiceClient.saveApply(order);
			toJSONResult(result, sResult, "保存成功", null);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "保存收款单出错");
			logger.error("保存收款单出错 {}", e);
		}
		return result;
	}
	
}
