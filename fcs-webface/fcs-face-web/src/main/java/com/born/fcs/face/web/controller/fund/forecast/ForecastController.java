/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:23:08 创建
 */
package com.born.fcs.face.web.controller.fund.forecast;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.fm.ws.info.forecast.ForecastAccountInfo;
import com.born.fcs.fm.ws.order.forecast.AccountAmountDetailQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountUpdateOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamBatchOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 预测基础配置
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/forecast")
public class ForecastController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/basisDataManage/";
	final static String vm_path2 = "/fundMg/fundMgModule/index/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "forecastTimeStart", "forecastTimeEnd" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amount" };
	}
	
	/***
	 * 查询
	 */
	@RequestMapping("findAll.htm")
	public String findAll(Model model) {
		//		if (StringUtil.isEmpty(order.getSortCol())) {
		//			order.setSortCol("raw_add_time");
		//			order.setSortOrder("DESC");
		//		}
		SysForecastParamResult result = forecastServiceClient.findAll();
		//		model.addAttribute("conditions", order);
		if (result.isExecuted() && result.isSuccess()) {
			model.addAttribute("info", result.getParamAllInfo());
			return vm_path + "fundForecast.vm";
		} else {
			return null;
		}
	}
	
	/***
	 * 修改
	 */
	@RequestMapping("modifyAll.htm")
	@ResponseBody
	public JSONObject modifyAll(SysForecastParamBatchOrder order, Model model) {
		String tipPrefix = "修改预测基础数据";
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult saveResult = forecastServiceClient.modifyAll(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		return json;
	}
	
	/**
	 * 资金流入流出预测表
	 */
	@RequestMapping("list.htm")
	public String list(ForecastAccountQueryOrder order, String selectType, Model model) {
		model.addAttribute("selectType", selectType);
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<ForecastAccountInfo> result = forecastServiceClient
			.queryForecastAccountInfo(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(result));
		model.addAttribute("systemFroms", SystemEnum.getAllEnum());
		model.addAttribute("fundDirections", FundDirectionEnum.getAllEnum());
		return vm_path2 + "fundForecastList.vm";
	}
	
	/**
	 * 资金计划
	 */
	@RequestMapping("listChange.htm")
	public String listChange(ForecastAccountQueryOrder order, String selectType,
								HttpServletRequest request, Model model) {
		
		model.addAttribute("selectType", selectType);
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		//setSessionLocalInfo2Order(order);
		List<ProjectRelatedUserTypeEnum> relatedRoleList = Lists.newArrayList();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		boolean isAdmin = DataPermissionUtil.isSystemAdministrator()
							|| DataPermissionUtil.isCompanyLeader();
		if (DataPermissionUtil.isBusiManager() && !isAdmin) {
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
			order.setRelatedRoleList(relatedRoleList);
			order.setLoginUserId(sessionLocal == null ? 0 : sessionLocal.getUserId());
		} else if (DataPermissionUtil.isLegalManager() && !isAdmin) {
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
			order.setRelatedRoleList(relatedRoleList);
			order.setLoginUserId(sessionLocal == null ? 0 : sessionLocal.getUserId());
		} else if (DataPermissionUtil.isCreditorRightsTransferRole() && !isAdmin) {
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
			order.setRelatedRoleList(relatedRoleList);
			order.setLoginUserId(sessionLocal == null ? 0 : sessionLocal.getUserId());
		} else {
			setSessionLocalInfo2Order(order);
		}
		
		QueryBaseBatchResult<ForecastAccountInfo> result = forecastServiceClient
			.queryForecastAccountInfo(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(result));
		model.addAttribute("systemFroms", SystemEnum.getAllEnum());
		model.addAttribute("fundDirections", FundDirectionEnum.getAllEnum());
		model.addAttribute("timeSpacing", request.getParameter("timeSpacing"));
		return vm_path2 + "fundPlanList.vm";
	}
	
	/**
	 * 资金余额明细表
	 */
	@RequestMapping("accountLast.htm")
	public String accountLast(AccountAmountDetailQueryOrder order, String selectType, Model model) {
		model.addAttribute("selectType", selectType);
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("time");
			order.setSortOrder("ASC");
		}
		QueryBaseBatchResult<AccountAmountDetailInfo> result = forecastServiceClient
			.queryAccountAmountDetail(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(result));
		return vm_path2 + "balanceList.vm";
	}
	
	/**
	 * 调整资金计划
	 */
	@RequestMapping("forecastAccountModify.htm")
	@ResponseBody
	public JSONObject forecastAccountModify(HttpServletRequest request, Model model) {
		String tipPrefix = "资金计划调整";
		JSONObject json = new JSONObject();
		try {
			long forecastId = NumberUtil.parseLong(request.getParameter("forecastId"));
			String forecastAmount = request.getParameter("forecastAmount");
			String forecastTime = request.getParameter("forecastTime");
			if (forecastId <= 0 || StringUtil.isBlank(forecastAmount)
				|| StringUtil.isBlank(forecastTime)) {
				json.put("success", false);
				json.put("message", "参数不完整");
				return json;
			}
			ForecastAccountUpdateOrder order = new ForecastAccountUpdateOrder();
			setSessionLocalInfo2Order(order);
			order.setId(forecastId);
			order.setAmount(Money.amout(forecastAmount));
			order.setForecastStartTime(DateUtil.parse(forecastTime));
			FcsBaseResult saveResult = forecastServiceClient.modifyForecastAccount(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("资金计划调整出错 {}", e);
			json.put("success", false);
			json.put("message", "处理出错");
		}
		
		return json;
	}
}
