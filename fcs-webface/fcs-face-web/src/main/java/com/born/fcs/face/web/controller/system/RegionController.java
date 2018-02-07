package com.born.fcs.face.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.order.common.RegionOrder;
import com.born.fcs.pm.ws.order.common.RegionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("systemMg/region")
public class RegionController extends BaseController {
	
	private static final String vm_path = "/systemMg/region/";
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("region.htm")
	public String configForm(HttpServletRequest request, Model model) {
		return vm_path + "region.vm";
	}
	
	/**
	 * 树形数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tree.json")
	@ResponseBody
	public JSONArray tree(String parentCode, HttpServletRequest request, Model model) {
		JSONArray finalData = new JSONArray();
		try {
			List<RegionInfo> data = basicDataCacheService.queryRegion(parentCode);
			for (RegionInfo info : data) {
				JSONObject json = new JSONObject();
				json.put("id", info.getId());
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("parentCode", info.getParentCode());
				json.put("hasChildren", info.getHasChildren() == null ? "NO" : info
					.getHasChildren().code());
				json.put("isParent", info.getHasChildren() == BooleanEnum.IS);
				json.put("sortOrder", info.getSortOrder());
				finalData.add(json);
			}
		} catch (Exception e) {
			logger.error("加载行政区域树形结构数据出错", e);
		}
		return finalData;
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			RegionOrder order = new RegionOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			FcsBaseResult result = basicDataServiceClient.saveRegion(order);
			if (result != null && result.isSuccess()) {
				basicDataCacheService.clearCache();
			}
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存失败");
			logger.error("保存行政区域出错{}", e);
		}
		return json;
	}
	
	/**
	 * 删除行政区域
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(int id, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult result = basicDataServiceClient.delRegion(id);
			if (result != null && result.isSuccess()) {
				basicDataCacheService.clearCache();
			}
			toJSONResult(json, result, "删除成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "删除出错");
			logger.error("删除行政区域出错 {}", e);
		}
		return json;
	}
	
	/**
	 * 根据编码查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("loadByCode.json")
	@ResponseBody
	public JSONObject loadByCode(String code, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			RegionQueryOrder order = new RegionQueryOrder();
			order.setCode(code);
			QueryBaseBatchResult<RegionInfo> result = basicDataServiceClient.queryRegion(order);
			if (result != null && result.getTotalCount() > 0) {
				RegionInfo region = result.getPageList().get(0);
				json.put("id", region.getId());
				json.put("code", region.getCode());
				json.put("name", region.getName());
				json.put("parentCode", region.getParentCode());
				json.put("hasChildren", region.getHasChildren() == null ? "NO" : region
					.getHasChildren().code());
				json.put("isParent", region.getHasChildren() == BooleanEnum.IS);
				json.put("sortOrder", region.getSortOrder());
			}
		} catch (Exception e) {
			logger.error("根据编码查询行政区域出错 {}", e);
		}
		return json;
	}
}
