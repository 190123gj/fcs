package com.born.fcs.face.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.biz.service.common.info.SysParamInfo;
import com.born.fcs.pm.ws.order.sysParam.SysParamOrder;
import com.born.fcs.pm.ws.order.sysParam.SysParamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 系统参数controller
 * @author wuzj
 * 
 */
@Controller
@RequestMapping("systemMg/sysparam")
public class SysParamController extends BaseController {
	
	private final String vm_path = "/systemMg/sysparam/";
	
	@RequestMapping("list.htm")
	public String list(SysParamQueryOrder order, Model model) {
		model.addAttribute("queryOrder", order);
		QueryBaseBatchResult<SysParamInfo> paramiList = sysParameterServiceClient
			.querySysPram(order);
		if (paramiList != null && paramiList.isSuccess())
			model.addAttribute("page", PageUtil.getCovertPage(paramiList));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("queryList.htm")
	public String queryList(SysParamQueryOrder order, Model model) {
		model.addAttribute("queryOrder", order);
		QueryBaseBatchResult<SysParamInfo> paramiList = sysParameterServiceClient
			.querySysPram(order);
		if (paramiList != null && paramiList.isSuccess())
			model.addAttribute("page", PageUtil.getCovertPage(paramiList));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("add.htm")
	@ResponseBody
	public JSONObject add(SysParamOrder order) {
		SysParamInfo info = sysParameterServiceClient.getSysParameterValueDO(order.getParamName());
		FcsBaseResult result = null;
		if (info != null) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "参数已存在");
			return json;
		} else {
			result = sysParameterServiceClient.insertSysParameterValueDO(order);
		}
		return toJSONResult(result);
	}
	
	@RequestMapping("mod.htm")
	@ResponseBody
	public JSONObject mod(SysParamOrder order) {
		SysParamInfo info = sysParameterServiceClient.getSysParameterValueDO(order.getParamName());
		FcsBaseResult result = null;
		if (info == null) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "参数不存在");
			return json;
		} else {
			result = sysParameterServiceClient.updateSysParameterValueDO(order);
		}
		return toJSONResult(result);
	}
	
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(String paramName) {
		JSONObject result = new JSONObject();
		try {
			sysParameterServiceClient.deleteSysParameterValue(paramName);
			result.put("success", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "删除出错");
			logger.error("删除系统参数 {} 出错:{}", paramName, e);
		}
		return result;
	}
	
}
