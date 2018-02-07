/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:33:54 创建
 */
package com.born.fcs.face.web.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.enums.TExpQueryProcessEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalQueryOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FundBaseController extends WorkflowBaseController {
	
	/****
	 * 差旅费报销单查询
	 * @param order
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	protected QueryBaseBatchResult<FormTravelExpenseInfo> getTravelExpenseResult(	TravelExpenseQueryOrder order,
																					HttpServletRequest request,
																					HttpServletResponse response,
																					Model model) {
		
		//order.setLoginUserId(0);
		long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
		String process = request.getParameter("process");
		String sef = request.getParameter("sef");
		if (StringUtil.isNotBlank(process)) {//查询流程
			order.setProcess(TExpQueryProcessEnum.getByCode(process));
			model.addAttribute("process", process);
		}
		if (StringUtil.isNotBlank(sef)) {//用户发起
			order.setAgent(String.valueOf(curUserId));
			model.addAttribute("sef", sef);
		}
		
		//		if (!DataPermissionUtil.isSystemAdministrator() && !hasAllDataPermission()) {
		//			order.setCurUserId(curUserId);//发起人仅能看到自己的，审核人能看到自己审核的所有单据
		//		}
		
		String searchUserId = request.getParameter("searchUserId");
		if (StringUtil.isNotBlank(searchUserId)) {
			List searchUserIdList = Lists.newArrayList();
			String[] uid = searchUserId.split(",");
			for (String id : uid) {
				searchUserIdList.add(id);
			}
			order.setSearchUserIdList(searchUserIdList);
		}
		model.addAttribute("searchUserId", searchUserId);
		model.addAttribute("searchUserName", request.getParameter("searchUserName"));
		
		List statusList = Lists.newArrayList();
		statusList.add(FormStatusEnum.DELETED);
		//		if("WAIT".equals(process)){//待办流程
		//			statusList.add(FormStatusEnum.DELETED);
		//		}
		order.setExcFormStatusList(statusList);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<FormTravelExpenseInfo> queryResult = travelExpenseServiceClient
			.queryPage(order);
		return queryResult;
	}
	
	/**
	 * 费用支付申请单入参
	 * @param request
	 * @param model
	 * @return
	 */
	protected ExpenseApplicationQueryOrder getExpenseApplicationQueryOrder(	HttpServletRequest request,
																			Model model) {
		return getExpenseApplicationQueryOrder(request, model, true);
		
	}
	
	/**
	 * 费用支付申请单入参
	 * @param request
	 * @param model
	 * @return
	 */
	protected ExpenseApplicationQueryOrder getExpenseApplicationQueryOrder(	HttpServletRequest request,
																			Model model,
																			boolean setSession) {
		ExpenseApplicationQueryOrder order = new ExpenseApplicationQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		if (setSession) {
			setSessionLocalInfo2Order(order);
			long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
			String sef = request.getParameter("sef");
			if (StringUtil.isNotBlank(sef)) {
				order.setAgent(String.valueOf(curUserId));
				model.addAttribute("sef", sef);
			}
			//			if (!DataPermissionUtil.isSystemAdministrator() && !hasAllDataPermission()) {
			//				order.setCurUserId(curUserId);//发起人仅能看到自己的，审核人能看到自己审核的所有单据
			//			}
			String searchUserId = request.getParameter("searchUserId");
			if (StringUtil.isNotBlank(searchUserId)) {
				List searchUserIdList = Lists.newArrayList();
				String[] uid = searchUserId.split(",");
				for (String id : uid) {
					searchUserIdList.add(id);
				}
				order.setSearchUserIdList(searchUserIdList);
			}
			model.addAttribute("searchUserId", searchUserId);
			model.addAttribute("searchUserName", request.getParameter("searchUserName"));
		}
		//		order.setLoginUserId(0);
		List statusList = Lists.newArrayList();
		statusList.add(FormStatusEnum.DELETED);
		order.setExcFormStatusList(statusList);
		
		// 添加资金类型
		String accountStatus = request.getParameter("accountStatus");
		if (StringUtil.isNotBlank(accountStatus)) {
			order.setAccountStatus(AccountStatusEnum.getByCode(accountStatus));
		}
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		return order;
	}
	
	/**
	 * 劳动支付申请单入参
	 * @param request
	 * @param model
	 * @return
	 */
	protected LabourCapitalQueryOrder getLabourCapitalQueryOrder(HttpServletRequest request,
																	Model model) {
		return getLabourCapitalQueryOrder(request, model, true);
		
	}
	
	/**
	 * 劳动支付申请单入参
	 * @param request
	 * @param model
	 * @return
	 */
	protected LabourCapitalQueryOrder getLabourCapitalQueryOrder(HttpServletRequest request,
																	Model model, boolean setSession) {
		LabourCapitalQueryOrder order = new LabourCapitalQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		if (setSession) {
			setSessionLocalInfo2Order(order);
			long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
			String sef = request.getParameter("sef");
			if (StringUtil.isNotBlank(sef)) {
				order.setAgent(String.valueOf(curUserId));
				model.addAttribute("sef", sef);
			}
			//			if (!DataPermissionUtil.isSystemAdministrator() && !hasAllDataPermission()) {
			//				order.setCurUserId(curUserId);//发起人仅能看到自己的，审核人能看到自己审核的所有单据
			//			}
			String searchUserId = request.getParameter("searchUserId");
			if (StringUtil.isNotBlank(searchUserId)) {
				List searchUserIdList = Lists.newArrayList();
				String[] uid = searchUserId.split(",");
				for (String id : uid) {
					searchUserIdList.add(id);
				}
				order.setSearchUserIdList(searchUserIdList);
			}
			model.addAttribute("searchUserId", searchUserId);
			model.addAttribute("searchUserName", request.getParameter("searchUserName"));
		}
		//		order.setLoginUserId(0);
		List statusList = Lists.newArrayList();
		statusList.add(FormStatusEnum.DELETED);
		order.setExcFormStatusList(statusList);
		
		// 添加资金类型
		String accountStatus = request.getParameter("accountStatus");
		if (StringUtil.isNotBlank(accountStatus)) {
			order.setAccountStatus(AccountStatusEnum.getByCode(accountStatus));
		}
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		return order;
	}
	
	protected BankMessageInfo findDefaultPaymentBankInfo(long deptId) {
		if (deptId <= 0) {
			return null;
		}
		
		BankMessageQueryOrder order = new BankMessageQueryOrder();
		order.setStatus(SubjectStatusEnum.NORMAL);
		order.setDeptId(deptId);
		QueryBaseBatchResult<BankMessageInfo> batchResult = bankMessageServiceClient
			.queryBankMessageInfo(order);
		if (null != batchResult && batchResult.isSuccess()
			&& ListUtil.isNotEmpty(batchResult.getPageList())
			&& batchResult.getPageList().size() == 1) {
			return batchResult.getPageList().get(0);
		}
		return null;
	}
}
