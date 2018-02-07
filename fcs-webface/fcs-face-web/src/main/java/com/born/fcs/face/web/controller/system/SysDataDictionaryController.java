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
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysDataDictionaryInfo;
import com.born.fcs.pm.ws.order.basicmaintain.SysDataDictionaryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * 数据字典维护
 * @author wuzj
 */
@Controller
@RequestMapping("systemMg/dictionary")
public class SysDataDictionaryController extends BaseController {
	
	private final static String vm_path = "/systemMg/dictionary/";
	
	/**
	 * 加载目录，传parentId表示只加载片段
	 * @param request
	 * @param dataCode
	 * @param parentId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String index(HttpServletRequest request, DataCodeEnum dataCode, Long parentId,
						Model model) {
		
		if (dataCode == null)
			dataCode = DataCodeEnum.CURRENCY;
		model.addAttribute("dataCode", dataCode);
		model.addAttribute("dataCodeList", DataCodeEnum.getAllEnum());
		
		if (parentId != null && parentId > 0) {
			List<SysDataDictionaryInfo> dataList = sysDataDictionaryServiceClient
				.findByParentId(parentId);
			if (ListUtil.isNotEmpty(dataList))
				model.addAttribute("dataCode", dataList.get(0).getDataCode());
			
			model.addAttribute("dataList", dataList);
			return vm_path + "dictionaryItem.vm";
		}
		
		if (dataCode != null) {
			model.addAttribute("dataList", sysDataDictionaryServiceClient.findByDataCode(dataCode));
		}
		return vm_path + "dictionary.vm";
	}
	
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(SysDataDictionaryOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = "保存字典数据";
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult saveResult = sysDataDictionaryServiceClient.save(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	/**
	 * 查询字典数据
	 * @param parentCode
	 * @param model
	 * @return
	 */
	@RequestMapping("data.json")
	@ResponseBody
	public JSONObject industry(DataCodeEnum dataCode, Long parentId, Model model) {
		String tipPrefix = "查询字典数据";
		JSONObject result = new JSONObject();
		List<SysDataDictionaryInfo> data = null;
		try {
			if (parentId != null && parentId > 0) {
				data = sysDataDictionaryServiceClient.findByParentId(parentId);
			} else if (dataCode != null) {
				data = sysDataDictionaryServiceClient.findByDataCode(dataCode);
			}
			JSONArray finalData = new JSONArray();
			if (ListUtil.isNotEmpty(data)) {
				for (SysDataDictionaryInfo info : data) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("parentId", info.getParentId());
					json.put("dataCode", info.getDataCode());
					json.put("dataValue", StringUtil.defaultIfBlank(info.getDataValue()));
					json.put("dataValue1", StringUtil.defaultIfBlank(info.getDataValue1()));
					json.put("dataValue2", StringUtil.defaultIfBlank(info.getDataValue2()));
					json.put("dataValue3", StringUtil.defaultIfBlank(info.getDataValue3()));
					json.put("childrenNum", info.getChildrenNum());
					finalData.add(json);
				}
			}
			result = toStandardResult(finalData, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error(tipPrefix + "出错", e);
		}
		return result;
	}
}
