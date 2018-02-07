/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午11:28:10 创建
 */
package com.born.fcs.face.web.controller.project.council;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.user.UserExtraMessageAddOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("systemMg/userExtra")
public class UserExtraMessageController extends BaseController {
	final static String vm_path = "/projectMg/user/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "startTime" };
	}
	
	/**
	 * 进入查询页面
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("listUser.htm")
	public String listUser(UserExtraMessageQueryOrder order, Model model) {
		QueryBaseBatchResult<UserExtraMessageInfo> queryResult = 
				userExtraMessageServiceClient.queryUserExtraMessage(order);
		
//		UserExtraMessageResult result = userExtraMessageServiceClient.findByUserId(20000009790051L);
//		queryResult.getPageList().add(result.getUserExtraMessageInfo());
		
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("userName", order.getUserName());
		return vm_path + "list.vm";
	}
	
	/**
	 * 进入查询页面
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewUser.htm")
	public String viewUser(HttpServletRequest request, UserExtraMessageOrder order, Model model) {
		String isEdit = request.getParameter("isEdit");
		model.addAttribute("isEdit", isEdit==null?"Y":isEdit);
		if (order == null) {
			// donothing
		} else if (order.getUserId() != null && order.getUserId() > 0) {
			UserExtraMessageResult result = userExtraMessageServiceClient.findByUserId(order
				.getUserId());
			if (result.isSuccess() && result.isExecuted()) {
				UserExtraMessageInfo userExtraInfo = result.getUserExtraMessageInfo();
				model.addAttribute("userExtraInfo", userExtraInfo);
				SysUser sysUser = bpmUserQueryService.findUserByUserId(userExtraInfo.getUserId());
				model.addAttribute("sysUser", sysUser);
			}
		} else if (StringUtil.isNotBlank(order.getUserAccount())) {
			UserExtraMessageResult result = userExtraMessageServiceClient.findByUserAccount(order
				.getUserAccount());
			if (result.isSuccess() && result.isExecuted()) {
				UserExtraMessageInfo userExtraInfo = result.getUserExtraMessageInfo();
				model.addAttribute("userExtraInfo", userExtraInfo);
				SysUser sysUser = bpmUserQueryService.findUserByUserId(userExtraInfo.getUserId());
				model.addAttribute("sysUser", sysUser);
			}
		}
		return vm_path + "viewUserExtraMessage.vm";
	}
	
	/**
	 * 查询用户额外信息是否存在
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserExtraMessage.json")
	@ResponseBody
	public JSONObject getUserExtraMessage(UserExtraMessageOrder order, Model model) {
		JSONObject json = new JSONObject();
		if (order == null || order.getUserId() == null || order.getUserId() <= 0) {
			json.put("success", false);
			json.put("message", "用户id不能为空！");
			return json;
		}
		
		SysUser sysUser = bpmUserQueryService.findUserByUserId(order.getUserId());
		json.put("sysUser", sysUser);
		if (sysUser == null) {
			json.put("success", false);
			json.put("message", "未查询到此用户！");
			//return json;
		}
		UserExtraMessageResult result = userExtraMessageServiceClient.findByUserId(order
			.getUserId());
		if (result.isSuccess() && result.isExecuted()) {
			UserExtraMessageInfo userExtraInfo = result.getUserExtraMessageInfo();
			//userExtraInfo.setUserJudgeKey("123");
			json.put("userExtraInfo", userExtraInfo);
			json.put("success", true);
		} else {
			json.put("success", true);
			json.put("message", "未查询到数据！");
		}
		return json;
	}
	
	/**
	 * 保存用户额外信息
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveUserExtraMessage.json")
	@ResponseBody
	public JSONObject saveUserExtraMessage(UserExtraMessageAddOrder order, Model model) {
		JSONObject json = new JSONObject();
		if (order == null || order.getUserId() == null || order.getUserId() <= 0
			|| StringUtil.isBlank(order.getUserAccount())) {
			json.put("success", false);
			json.put("message", "参数不能为空！");
			return json;
		}
		SysUser sysUser = bpmUserQueryService.findUserByUserId(order.getUserId());
		// 一切以bpm的值为准
		order.setUserName(sysUser.getFullname());
		order.setUserAccount(sysUser.getAccount());
		UserExtraMessageResult result = userExtraMessageServiceClient.insertOrUpdate(order);
		if (result.isExecuted() && result.isSuccess()) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("message", result.getMessage());
		}
		return json;
	}
}
