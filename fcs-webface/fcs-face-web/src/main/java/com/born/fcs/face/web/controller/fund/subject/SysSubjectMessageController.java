/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:23:13 创建
 */
package com.born.fcs.face.web.controller.fund.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.SubjectTypeEnum;
import com.born.fcs.fm.ws.info.subject.SysSubjectMessageInfo;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/sysSubject/")
public class SysSubjectMessageController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/basisDataManage/";
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(SysSubjectMessageOrder order, Model model) {
		//		if (StringUtil.isEmpty(order.getSortCol())) {
		//			order.setSortCol("raw_add_time");
		//			order.setSortOrder("DESC");
		//		}
		if (order.getSubjectType() == null) {
			order.setSubjectType(SubjectTypeEnum.RECEIPT);
		}
		order.setPageSize(250L);
		QueryBaseBatchResult<SysSubjectMessageInfo> batchResult = sysSubjectMessageServiceClient
			.querySubject(order);
		
		model.addAttribute("conditions", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "defaultSubjectMaintain.vm";
	}
	
	/**
	 * 批量修改
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("modify.htm")
	@ResponseBody
	public JSONObject modify(SysSubjectMessageBatchOrder order, Model model) {
		
		JSONObject result = new JSONObject();
		String tipPrefix = "修改默认科目信息";
		try {
			
			FcsBaseResult saveResult = sysSubjectMessageServiceClient.updateAll(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "修改默认科目信息出错");
			logger.error("修改默认科目信息出错 {}", e);
		}
		return result;
	}
	
}
