package com.born.fcs.face.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.shortmessage.ShortMessageInfo;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageOrder;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 短信发送controller
 * @author ji
 *
 */
@Controller
@RequestMapping("systemMg/shortMessage")
public class ShortmessageController extends BaseController {
	
	private final String vm_path = "/systemMg/shortMessage/";
	
	@RequestMapping("list.htm")
	public String list(ShortMessageQueryOrder order, HttpServletRequest request, Model model) {
		
		model.addAttribute("queryOrder", order);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("id");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<ShortMessageInfo> pList = shortMessageServiceClient.query(order);
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 短信详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long id, Model model) {
		ShortMessageInfo shortMessageInfo = shortMessageServiceClient.findById(id);
		
		model.addAttribute("shortMessageInfo", shortMessageInfo);
		return vm_path + "view.vm";
	}
	
	/**
	 * 去新增短信
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(Model model) {
		return vm_path + "add.vm";
	}
	
	/*
	 * 发送短信
	 */
	@ResponseBody
	@RequestMapping("shortMessageSend.json")
	public JSONObject shortMessageSend(ShortMessageOrder order, Model model, HttpSession session,
										HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtil.hasBlank(order.getMessageContent())
			|| StringUtil.hasBlank(order.getMessageReceiver())) {
			jsonObject.put("success", false);
			jsonObject.put("message", "信息录入不全");
			return jsonObject;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setMessageSenderId(sessionLocal.getUserId());
		order.setMessageSenderName(sessionLocal.getUserName());
		order.setMessageSenderAccount(sessionLocal.getUserDetailInfo().getAccount());
		
		FcsBaseResult result = shortMessageServiceClient.save(order);
		if (result.isSuccess()) {
			jsonObject.put("success", true);
			jsonObject.put("message", "短信发送成功");
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "短信发送失败");
		}
		return jsonObject;
	}
}
