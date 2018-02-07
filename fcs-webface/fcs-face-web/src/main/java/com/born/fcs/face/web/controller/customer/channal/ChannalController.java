package com.born.fcs.face.web.controller.customer.channal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 客户管理-渠道
 * */
@Controller
@RequestMapping("/customerMg/channal")
public class ChannalController extends BaseController {
	
	private static String VM_PATH = "/customerMg/channel/";
	
	@RequestMapping("list.htm")
	public String getList(ChannalQueryOrder queryOrder, Model model) {
		QueryBaseBatchResult<ChannalInfo> batchResult = channalClient.list(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryOrder", queryOrder);
		setCustomerEnums(model);
		return VM_PATH + "list.vm";
		
	}
	
	@RequestMapping("add.htm")
	public String addPage(HttpServletRequest request, String channalType, Model model) {
		setCustomerEnums(model);
		String channalCode = channalClient.createChannalCode(StringUtil.defaultIfBlank(channalType,
			ChanalTypeEnum.YH.code()));
		model.addAttribute("channalCode", channalCode);
		return VM_PATH + "addChannel.vm";
		
	}
	
	@RequestMapping("info.htm")
	public String getList(long id, Boolean isUpdate, Model model) {
		List<ChannalInfo> list = channalClient.queryAll(id, "");
		model.addAttribute("list", list);
		model.addAttribute("isUpdate", isUpdate);
		model.addAttribute("nowId", id);
		setCustomerEnums(model);
		if (isUpdate != null && isUpdate) {
			return VM_PATH + "addChannel.vm";
		}
		return VM_PATH + "viewChannel.vm";
		
	}
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request, ListOrder listOrder, Model model) {
		ChannalOrder order = new ChannalOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setInputPerson(ShiroSessionUtils.getSessionLocal().getRealName());
		order.setListData(listOrder.getListData());
		FcsBaseResult result = channalClient.add(order);
		return toJSONResult(result, "渠道添加成功", null);
		
	}
	
	@ResponseBody
	@RequestMapping("update.json")
	public JSONObject update(HttpServletRequest request, ListOrder listOrder, Model model) {
		ChannalOrder order = new ChannalOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setInputPerson(ShiroSessionUtils.getSessionLocal().getRealName());
		order.setListData(listOrder.getListData());
		FcsBaseResult result = channalClient.update(order);
		return toJSONResult(result, "渠道更新成功", null);
		
	}
	
	/**
	 * 更新渠道状态
	 * **/
	@ResponseBody
	@RequestMapping("updateStatus.json")
	public JSONObject updateStatus(long id, String status) {
		FcsBaseResult result = channalClient.updateStatus(id, status);
		return toJSONResult(result, "渠道状态更新成功", null);
		
	}
	
	/** 验证渠道编号是否可用 */
	@ResponseBody
	@RequestMapping("validataChannalCode.json")
	public Boolean validataChannalCode(String channelCode) {
		FcsBaseResult result = new FcsBaseResult();
		ChannalInfo info = channalClient.queryByChannalCode(channelCode);
		if (info != null) {
			result.setSuccess(false);
			result.setMessage("渠道编号已被使用");
		} else {
			result.setSuccess(true);
			result.setMessage("渠道编号可用");
		}
		return result.isSuccess();
		
	}
	
	/** 验证渠道编名是否可用 */
	@ResponseBody
	@RequestMapping("validataChannalName.json")
	public Boolean validataChannalName(String channelName) {
		FcsBaseResult result = new FcsBaseResult();
		ChannalInfo info = channalClient.queryByChannalName(channelName);
		if (info != null) {
			result.setSuccess(false);
			result.setMessage("渠道名已被使用");
		} else {
			ChannalContractQueryOrder queryOrder = new ChannalContractQueryOrder();
			queryOrder.setPageSize(1);
			queryOrder.setChannalName(channelName);
			QueryBaseBatchResult<ChannalContractInfo> batchResult = channalContractClient
				.list(queryOrder);
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				result.setSuccess(false);
				result.setMessage("渠道名已被使用");
			} else {
				result.setSuccess(true);
				result.setMessage("渠道名可用");
			}
			
		}
		return result.isSuccess();
		
	}
	
	/** 删除渠道 */
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		result = channalClient.delete(id);
		return toJSONResult(result, "删除成功", result.getMessage());
		
	}
	
}
