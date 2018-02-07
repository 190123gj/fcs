package com.born.fcs.face.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormMessageTypeEnum;
import com.born.fcs.pm.ws.enums.FormMessageVarEnum;
import com.born.fcs.pm.ws.info.common.FormMessageTempleteInfo;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteOrder;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 *
 * @Description 站内消息管理
 *
 */
@Controller
@RequestMapping("/systemMg/messageTemplete")
public class FormMessageTempleteController extends BaseController {
	
	private final String vm_path = "/systemMg/messageTemplete/";
	
	@RequestMapping("form.htm")
	public String form(HttpServletRequest request, Model model, FormCodeEnum formCode,
						FormMessageTypeEnum type, Long templeteId) {
		if (templeteId != null && templeteId > 0) {
			FormMessageTempleteInfo templete = formMessageTempleteServiceClient
				.findByTempleteId(templeteId);
			model.addAttribute("templete", templete);
		} else if (formCode != null && type != null) {
			FormMessageTempleteInfo templete = formMessageTempleteServiceClient
				.findByFormCodeAndType(formCode, type);
			model.addAttribute("templete", templete);
		}
		model.addAttribute("formList", FormCodeEnum.getAllEnum());
		model.addAttribute("typeList", FormMessageTypeEnum.getAllEnum());
		model.addAttribute("varList", FormMessageVarEnum.getAllEnum());
		return vm_path + "form.vm";
	}
	
	/*
	 * 模板列表
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, FormMessageTempleteQueryOrder order,
						Model model, String status) {
		model.addAttribute("queryOrder", order);
		model.addAttribute("page",
			PageUtil.getCovertPage(formMessageTempleteServiceClient.queryPage(order)));
		List<FormCodeEnum> formList = FormCodeEnum.getAllEnum();
		model.addAttribute("formList", formList);
		return vm_path + "list.vm";
	}
	
	/*
	* 保存模板
	*/
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject saveTemplete(HttpServletRequest request, FormMessageTempleteOrder order,
									Model model, String status) {
		
		try {
			FcsBaseResult result = formMessageTempleteServiceClient.saveTemplete(order);
			return toJSONResult(result, "保存消息模板");
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "保存消息模板出错");
			logger.error("保存消息模板出错 {}", e);
			return json;
		}
	}
	
	/*
	 * 删除收到的消息
	 */
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject deleteReceivedMessageInfo(long templeteId, Model model, String status) {
		
		try {
			FcsBaseResult result = formMessageTempleteServiceClient.deleteByTempleteId(templeteId);
			return toJSONResult(result, "删除消息模板");
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "删除消息模板出错");
			logger.error("删除消息模板出错 {}", e);
			return json;
		}
	}
	
	/*
	 * 删除收到的消息
	 */
	@ResponseBody
	@RequestMapping("checkExist.json")
	public JSONObject checkExist(FormCodeEnum formCode, FormMessageTypeEnum type) {
		JSONObject json = new JSONObject();
		try {
			
			FormMessageTempleteInfo templete = formMessageTempleteServiceClient
				.findByFormCodeAndType(formCode, type);
			if (templete != null) {
				json.put("isExist", true);
			} else {
				json.put("isExist", false);
			}
			
			json.put("success", true);
			json.put("message", "成功");
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "检查消息模板出错");
			logger.error("检查消息模板出错 {}", e);
		}
		return json;
	}
	
}
