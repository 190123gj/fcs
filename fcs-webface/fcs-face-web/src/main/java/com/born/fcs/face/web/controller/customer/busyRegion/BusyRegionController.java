package com.born.fcs.face.web.controller.customer.busyRegion;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.BusyRegionInfo;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder.region;
import com.born.fcs.crm.ws.service.order.query.BusyRegionQueryOrder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 业务区域维护
 * */
@Controller
@RequestMapping("systemMg/busyRegoin")
public class BusyRegionController extends BaseController {
	
	@RequestMapping("list.htm")
	public String list(BusyRegionQueryOrder order, Model model) {
		
		QueryBaseBatchResult<BusyRegionInfo> batchResult = busyRegionClient.list(order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryOrder", order);
		model.addAttribute("status", busyRegionClient.queryStatus(null).isSuccess());
		return "/systemMg/businessArea/list.vm";
		
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, BusyRegionOrder order) {
		JSONObject json = new JSONObject();
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		if (StringUtil.isNotBlank(code) && StringUtil.isNotBlank(name)) {
			String[] codes = code.split(",");
			String[] names = name.split(",");
			List<region> region = new ArrayList<>();
			for (int i = 0; i < codes.length && i < names.length; i++) {
				region r = new region();
				r.setCode(codes[i]);
				r.setName(names[i]);
				region.add(r);
			}
			order.setRegion(region);
		}
		if (StringUtil.isNotBlank(order.getDepPath())) {
			try {
				String path = order.getDepPath();
				long orgId = Long.parseLong(path);
				Org org = bpmUserQueryService.findDeptById(orgId);
				order.setDepPath(org.getPath());
			} catch (Exception e) {
				
			}
		}
		FcsBaseResult result = busyRegionClient.save(order);
		if (result.isSuccess()) {
			json.put("success", true);
			json.put("message", "操作成功!");
		} else {
			json.put("success", false);
			json.put("message", result.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("query.json")
	public JSONObject query(Long depPath) {
		
		Org org = bpmUserQueryService.findDeptById(depPath);
		JSONObject json = new JSONObject();
		
		List<BusyRegionInfo> list = busyRegionClient.queryAllByDepPath(org.getPath());
		if (ListUtil.isNotEmpty(list)) {
			json.put("data", list);
			json.put("success", true);
			json.put("message", "操作成功!");
		} else {
			json.put("success", false);
			json.put("message", "暂无数据");
		}
		return json;
	}
	
	/**
	 * 业务区域启用状态修改
	 * */
	@ResponseBody
	@RequestMapping("changeStatus.json")
	public JSONObject changeStatus(String depPath, Boolean status) {
		JSONObject json = new JSONObject();
		if (status == null) {
			json.put("success", false);
			json.put("message", "启用状态不能为空!");
			return json;
		}
		FcsBaseResult result = busyRegionClient.changeStatus(depPath, (status ? BooleanEnum.IS
			: BooleanEnum.NO));
		if (result.isSuccess()) {
			json.put("success", true);
			json.put("message", "操作成功!");
		} else {
			json.put("success", false);
			json.put("message", result.getMessage());
		}
		return json;
	}
}
