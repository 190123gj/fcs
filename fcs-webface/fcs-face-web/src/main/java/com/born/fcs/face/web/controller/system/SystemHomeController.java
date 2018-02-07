package com.born.fcs.face.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.app.AppAboutConfInfo;
import com.born.fcs.pm.ws.app.AppAboutConfOrder;
import com.born.fcs.pm.ws.app.AppAboutConfService;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.order.basicmaintain.SysConfigOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.BasicDataService;

@Controller
@RequestMapping("systemMg")
public class SystemHomeController extends BaseController {
	
	@Autowired
	AppAboutConfService appAboutConfServiceClient;
	
	@Autowired
	BasicDataService basicDataServiceClient;
	
	final static String vm_path = "/systemMg/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	@RequestMapping("clearCache.htm")
	@ResponseBody
	public JSONObject clearCache() {
		JSONObject result = new JSONObject();
		try {
			sysClearCacheServiceClient.clearCache();
			result.put("success", true);
			result.put("message", "清除缓存成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "清除缓存出错");
			logger.error("清除缓存出错:{}", e);
		}
		return result;
	}
	
	@RequestMapping("saveAppConf.json")
	@ResponseBody
	public JSONObject saveAppConf(HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			AppAboutConfOrder order = new AppAboutConfOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = appAboutConfServiceClient.save(order);
			if (saveResult != null && saveResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "配置成功");
			} else {
				result.put("success", false);
				result.put("message", "配置失败");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "配置出错");
			logger.error("保存APP后台配置出错:{}", e);
		}
		return result;
	}
	
	@RequestMapping("toAppConf.htm")
	public String toAppConf(HttpServletRequest request, Model model) {
		AppAboutConfInfo conf = appAboutConfServiceClient.get();
		model.addAttribute("conf", conf);
		return vm_path + "app/conf.vm";
	}
	
	@RequestMapping("instructions.htm")
	public String instructions(HttpServletRequest request, Model model) {
		model.addAttribute("conf", basicDataServiceClient.findSysConf());
		model.addAttribute("imageServerUrl", sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_IMAGE_SERVER_URL.code()));
		return "/mainPage/Instructions.vm";
	}
	
	@RequestMapping("toSysConf.htm")
	public String toSysConf(HttpServletRequest request, Model model) {
		model.addAttribute("conf", basicDataServiceClient.findSysConf());
		return vm_path + "sysConf.vm";
	}
	
	@RequestMapping("saveSysConf.htm")
	@ResponseBody
	public JSONObject saveSysConf(SysConfigOrder order, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult result = basicDataServiceClient.saveSysConf(order);
			toJSONResult(json, result, "保存成功", "保存失败");
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存系统配置出错");
			logger.error("保存系统配置出错：{}", e);
		}
		return json;
	}
}
