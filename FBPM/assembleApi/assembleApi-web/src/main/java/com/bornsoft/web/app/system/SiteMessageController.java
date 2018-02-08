package com.bornsoft.web.app.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.MessageReceivedStatusEnum;
import com.born.fcs.pm.ws.info.common.MessageInfo;
import com.born.fcs.pm.ws.info.common.MessageReceivedInfo;
import com.born.fcs.pm.ws.order.common.MyMessageOrder;
import com.born.fcs.pm.ws.order.common.QueryMessageOrder;
import com.born.fcs.pm.ws.order.common.QueryReceviedMessageOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.web.app.base.BaseController;
import com.bornsoft.web.app.util.PageUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 *
 * @Description 站内消息管理
 *
 */
@Controller
@RequestMapping("/systemMg/message")
public class SiteMessageController extends BaseController {
	
	private final String vm_path = "/systemMg/message/";
	
	private static Logger logger = LoggerFactory.getLogger(SiteMessageController.class);
	
	/*
	 * 管理员列表
	 */
	@RequestMapping("admin/list.htm")
	public String messageList(QueryMessageOrder order, HttpServletRequest request, Model model) {
		if (order == null)
			order = new QueryMessageOrder();
		QueryBaseBatchResult<MessageInfo> messageInfoList = siteMessageServiceClient
			.findMessage(order);
		model.addAttribute("page", PageUtil.getCovertPage(messageInfoList));
		model.addAttribute("queryMessageOrder", order);
		return vm_path + "managerList.vm";
	}
	

	
	/*
	 * 查詢消息数量
	 */
	@ResponseBody
	@RequestMapping("user/unRead.json")
	public JSONObject ajaxLoadUnReadData() {
		JSONObject jsonObject = new JSONObject();
		if (ShiroSessionUtils.getSessionLocal() != null) {
			long userId = ShiroSessionUtils.getSessionLocal().getUserId();
			try {
				long count = siteMessageServiceClient.loadUnReadMyMessageCount(userId);
				jsonObject.put("count", count);
				jsonObject.put(TOTAL, count);
				toJSONResult(jsonObject,AppResultCodeEnum.SUCCESS, "查询成功");
			} catch (Exception e) {
				toJSONResult(jsonObject,AppResultCodeEnum.FAILED, "查询未读消息出错");
			}
		} else {
			toJSONResult(jsonObject,AppResultCodeEnum.FAILED, "您未登陆或已掉线");
		}
		return jsonObject;
	}
	
	/*
	 * 用户消息列表
	 */
	@ResponseBody
	@RequestMapping("user/list.json")
	public JSONObject messageInfoList(HttpServletRequest request,
									QueryReceviedMessageOrder queryMessageOrder,
									String status) {
		JSONObject jsonObject = new JSONObject();
		
		long userId = ShiroSessionUtils.getSessionLocal().getUserId();
		if (queryMessageOrder == null)
			queryMessageOrder = new QueryReceviedMessageOrder();
		queryMessageOrder.setMessageReceivedId(userId);
		List<MessageReceivedStatusEnum> statusList = new ArrayList<>();
		if (StringUtil.isEmpty(status)) {
			statusList.add(MessageReceivedStatusEnum.UNREAD);
			statusList.add(MessageReceivedStatusEnum.READ);
			statusList.add(MessageReceivedStatusEnum.COLLECT);
		} else {
			statusList.add(MessageReceivedStatusEnum.getByCode(status));
		}
		queryMessageOrder.setStatusList(statusList);
		QueryBaseBatchResult<MessageReceivedInfo> messageInfoList = siteMessageServiceClient
			.findReceviedMessage(queryMessageOrder);
		JSONObject page = makePage(messageInfoList);
		JSONArray dataList = new JSONArray();
		page.put("result", dataList);
		jsonObject.put("page", page);
		for(MessageReceivedInfo info : messageInfoList.getPageList()){
			JSONObject e = new JSONObject();
			e.put("receivedId", info.getReceivedId());
			e.put("messageTitle", info.getMessageTitle());
			String messageContent =  info.getMessageContent();
			e.put("messageContent", getTextMsg(messageContent));
			e.put("messageSenderName", info.getMessageSenderName());
			e.put("rawAddTime", DateUtils.toString(info.getRawAddTime(), DateUtils.PATTERN2));
			dataList.add(e);
		}
		toJSONResult(jsonObject,AppResultCodeEnum.SUCCESS, "获取成功");
		return jsonObject;
	}

	/*
	* 修改消息
	*/
	@ResponseBody
	@RequestMapping("user/update.json")
	public JSONObject ajaxUpdateMessageInfo(HttpSession session, HttpServletRequest request,
											HttpServletResponse response, Model model, String status) {
		JSONObject json = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null) {
			toJSONResult(json, AppResultCodeEnum.FAILED, "登陆失效");
			return json;
		}
		
		try {
			FcsBaseResult result = null;
			if ("YES".equals(request.getParameter("readAll"))) {
				result = siteMessageServiceClient.updateReceivedMessageStatus(
					sessionLocal.getUserId(), MessageReceivedStatusEnum.READ);
			} else {
				MyMessageOrder myMessageOrder = new MyMessageOrder();
				String ids = request.getParameter("receivedId");
				String[] receivedIds = ids == null ? null : ids.split(",");
				String type = request.getParameter("type");
				if (type.equals(MessageReceivedStatusEnum.COLLECT.code())) {
					myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.COLLECT
						.code());
				} else if (type.equals(MessageReceivedStatusEnum.READ.code())) {
					myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.READ.code());
				}else if(type.equals(MessageReceivedStatusEnum.UNREAD.code())){
					myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.UNREAD.code());
				}
				myMessageOrder.setUserId(sessionLocal.getUserId());
				for (String receivedId : receivedIds) {
					myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
					result = siteMessageServiceClient.readMessageInfo(myMessageOrder);
				}
			}
			
			if (result != null && result.isSuccess()) {
				toJSONResult(json, AppResultCodeEnum.SUCCESS, "");
			} else {
				toJSONResult(json, AppResultCodeEnum.FAILED, "操作失败");
			}
			
		} catch (Exception e) {
			logger.error("更新消息出错 {}", e);
			toJSONResult(json, AppResultCodeEnum.FAILED, "更新消息出错");
		}
		return json;
	}
	
	/*
	 * 删除收到的消息
	 */
	@RequestMapping("user/delete.json")
	@ResponseBody
	public JSONObject ajaxDelete(HttpSession session, HttpServletRequest request,
									HttpServletResponse response, Model model, String status) {
		JSONObject result = new JSONObject();
		try {
			MyMessageOrder myMessageOrder = new MyMessageOrder();
			String ids = request.getParameter("receivedId");
			String[] receivedIds = ids == null ? null : ids.split(",");
			myMessageOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
			for (String receivedId : receivedIds) {
				myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
				siteMessageServiceClient.deleteReceivedMessageInfo(myMessageOrder);
			}
			toJSONResult(result,AppResultCodeEnum.SUCCESS, "删除成功");
		} catch (Exception e) {
			toJSONResult(result,AppResultCodeEnum.FAILED, "删除消息出错");
			logger.error("删除消息出错 {}", e);
		}
		return result;
	}

	
}
