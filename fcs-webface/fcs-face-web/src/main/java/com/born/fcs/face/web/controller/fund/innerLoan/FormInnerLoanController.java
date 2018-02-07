/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:24:35 创建
 */
package com.born.fcs.face.web.controller.fund.innerLoan;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.FormInnerLoanInterestTypeEnum;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanFormInfo;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder;
import com.born.fcs.fm.ws.result.innerLoan.FormInnerLoanResult;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 内部借款单
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/innerLoan")
public class FormInnerLoanController extends WorkflowBaseController {
	final static String vm_path = "/fundMg/fundMgModule/interiorBorrow/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "useTime", "backTime", "interestTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "loanAmount" };
	}
	
	/**
	 * 内部借款单据列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanList.htm")
	public String innerLoanList(FormInnerLoanQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FormInnerLoanFormInfo> batchResult = formInnerLoanServiceClient
			.searchForm(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 进入新增
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanAdd.htm")
	public String innerLoanAdd(Model model) {
		model.addAttribute("interestTypes", FormInnerLoanInterestTypeEnum.getAllEnum());
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 进入修改
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanModify.htm")
	public String innerLoanModify(Long id, Long formId, Model model) {
		
		try {
			FormInnerLoanResult result = new FormInnerLoanResult();
			if (id != null) {
				result = formInnerLoanServiceClient.findById(id);
				
			} else if (formId != null) {
				result = formInnerLoanServiceClient.findByFormId(formId);
			}
			if (result != null && result.isExecuted() && result.isSuccess()) {
				model.addAttribute("formInnerLoanInfo", result.getFormInnerLoanInfo());
				FormInfo form = formServiceFmClient.findByFormId(result.getFormInnerLoanInfo()
					.getFormId());
				model.addAttribute("form", form);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("interestTypes", FormInnerLoanInterestTypeEnum.getAllEnum());
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 查看详情
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanMessage.htm")
	public String innerLoanMessage(Long id, Long formId, Model model) {
		
		try {
			FormInnerLoanResult result = new FormInnerLoanResult();
			if (id != null) {
				result = formInnerLoanServiceClient.findById(id);
				
			} else if (formId != null) {
				result = formInnerLoanServiceClient.findByFormId(formId);
			}
			if (result != null && result.isExecuted() && result.isSuccess()) {
				model.addAttribute("formInnerLoanInfo", result.getFormInnerLoanInfo());
				FormInfo form = formServiceFmClient.findByFormId(result.getFormInnerLoanInfo()
					.getFormId());
				model.addAttribute("form", form);
				setAuditHistory2Page(form, model);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("interestTypes", FormInnerLoanInterestTypeEnum.getAllEnum());
		
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
	public String print(Long formId, HttpServletRequest request, Model model) {
		if (formId != null) {
			FormInnerLoanResult result = formInnerLoanServiceClient.findByFormId(formId);
			if (result != null && result.isExecuted() && result.isSuccess()) {
				model.addAttribute("formInnerLoanInfo", result.getFormInnerLoanInfo());
			}
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
	@RequestMapping("innerLoanAudit.htm")
	public String innerLoanAudit(long formId, HttpServletRequest request, Model model) {
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "收款单不存在");
		}
		model.addAttribute("formInnerLoanInfo", formInnerLoanServiceClient.findByFormId(formId)
			.getFormInnerLoanInfo());
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		model.addAttribute("form", formInfo);
		model.addAttribute("interestTypes", FormInnerLoanInterestTypeEnum.getAllEnum());
		return vm_path + "viewApplyAdd.vm";
	}
	
	/**
	 * 保存
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanSave.json")
	@ResponseBody
	public JSONObject innerLoanSave(FormInnerLoanOrder order, Model model) {
		String tipPrefix = "保存内部借款单";
		JSONObject result = new JSONObject();
		try {
			order.setFormCode(FormCodeEnum.FORM_INNER_LOAN);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			//			// 添加申请信息
			//			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//			if (sessionLocal != null) {
			//				order.setApplyUserId(sessionLocal.getUserId());
			//				order.setApplyUserAccount(sessionLocal.getUserName());
			//				order.setApplyUserName(sessionLocal.getRealName());
			//				
			//				UserInfo userInfo = sessionLocal.getUserInfo();
			//				if (userInfo != null) {
			//					SysOrg dept = sessionLocal.getUserInfo().getDept();
			//					if (dept != null) {
			//						order.setApplyDeptId(dept.getOrgId());
			//						order.setApplyDeptCode(dept.getCode());
			//						order.setApplyDeptName(dept.getOrgName());
			//					}
			//				}
			//			}
			FormBaseResult saveResult = formInnerLoanServiceClient.save(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
}
