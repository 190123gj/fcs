package com.bornsoft.web.app.customer.channal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.web.app.base.BaseController;
import com.bornsoft.web.app.util.PageUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 客户管理-渠道
 * */
@Controller
@RequestMapping("/customerMg/channal")
public class ChannalController extends BaseController {
	
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject getList(ChannalQueryOrder queryOrder, HttpServletRequest request) {
		logger.info("查询渠道列表，入参={}", ReflectUtils.toString(queryOrder));
		JSONObject result = null;
		try {
			String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
			if(StringUtils.isNotBlank(likeCodeOrName)){
				queryOrder.setLikeCodeOrName(likeCodeOrName);
			}
			QueryBaseBatchResult<ChannalInfo> batchResult = channalClient.list(queryOrder);
			result = toJSONResult(batchResult);
			result.put("page", JSONObject.parseObject(GsonUtil.createNewBuilder(myExclusionStrategy).create().toJson(PageUtil.getCovertPage(batchResult))));
		} catch (Exception e) {
			logger.error("查询渠道列表失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		logger.info("查询渠道列表，出参={}", result);
		return result;
		
	}
	
	ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes fa) {
			return fa.getName().equals("listData");
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}

	};
	
}
