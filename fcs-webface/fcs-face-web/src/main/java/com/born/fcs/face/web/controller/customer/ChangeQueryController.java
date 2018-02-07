package com.born.fcs.face.web.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.ChangeListInfo;
import com.born.fcs.crm.ws.service.order.query.ChangeListQueryOrder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * 客户修改记录查询
 * */
@Controller
@RequestMapping("customerMg/customer/change")
public class ChangeQueryController extends BaseController {
	
	private static String VM_PATH = "/customerMg/customer/change/";
	
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject list(ChangeListQueryOrder queryOrder) {
		String tipPrefix = "查询客户修改记录";
		JSONObject result = new JSONObject();
		try {
			QueryBaseBatchResult<ChangeListInfo> batchResult = changeSaveClient.list(queryOrder);
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ChangeListInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					
					json.put("changeId", info.getChangeId());
					json.put("customerUserId", info.getCustomerUserId());
					json.put("operName", info.getOperName());
					json.put("operId", info.getOperId());
					json.put("changeType", info.getChangeType());
					json.put("rawUpdateTime", info.getRawUpdateTime());
					dataList.add(json);
				}
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	@RequestMapping("info.htm")
	public String info(long changeId, Model model) {
		ChangeListInfo info = changeSaveClient.queryChange(changeId);
		model.addAttribute("info", info);
		setCustomerEnums(model);
		return VM_PATH + "record.vm";
		
	}
}
