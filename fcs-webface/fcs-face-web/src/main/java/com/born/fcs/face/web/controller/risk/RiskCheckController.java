//package com.born.fcs.face.web.controller.risk;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.born.fcs.face.web.controller.base.BaseController;
//import com.born.fcs.face.web.util.WebUtil;
//import com.born.fcs.pm.util.BusinessNumberUtil;
//import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
//import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder.ApixUserTypeEnum;
//import com.bornsoft.pub.order.apix.ApixOrderBase;
//import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
//import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
//import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
//import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
//import com.bornsoft.pub.result.apix.ApixValidateMobileResult;
//import com.yjf.common.lang.util.StringUtil;
//
///**
// * 风险监控校验
// * 
// * EXECUTE_FAILURE
// * */
//@Controller
//@RequestMapping("riskMg/check")
//public class RiskCheckController extends BaseController {
//	/**
//	 * 失信查询
//	 * @param type; 客户类型
//	 * @param certNo 身份证号
//	 * @param name 姓名
//	 * 
//	 * */
//	@ResponseBody
//	@RequestMapping("dishonesty.json")
//	public JSONObject dishonesty(HttpServletRequest request, String type) {
//		ApixDishonestQueryOrder queryOrder = new ApixDishonestQueryOrder();
//		WebUtil.setPoPropertyByRequest(queryOrder, request);
//		if (StringUtil.isNotBlank(type)) {
//			queryOrder.setType(ApixUserTypeEnum.getByCode(type));
//		}
//		JSONObject json = new JSONObject();
//		setOrderNum(queryOrder);
//		ApixDishonestQueryResult result = apixCreditInvestigationFacadeClient
//			.dishonestQuery(queryOrder);
//		toJsonResult(json, result);
//		return json;
//	}
//	
//	/**
//	 * 实名校验
//	 * @param certNo 身份证号
//	 * @param name 姓名
//	 * 
//	 * */
//	@ResponseBody
//	@RequestMapping("validateIdCard.json")
//	public JSONObject validateIdCard(HttpServletRequest request) {
//		ApixValidateIdCardOrder queryOrder = new ApixValidateIdCardOrder();
//		WebUtil.setPoPropertyByRequest(queryOrder, request);
//		
//		JSONObject json = new JSONObject();
//		setOrderNum(queryOrder);
//		ApixValidateIdCardResult result = apixCreditInvestigationFacadeClient
//			.validateIdCard(queryOrder);
//		toJsonResult(json, result);
//		json.put("result", result);
//		if (result != null) {
//			json.put("info", result.getPersonInfo());
//		}
//		
//		return json;
//	}
//	
//	/**
//	 * 手机号校验
//	 * @param mobile;
//	 * @param name; 客户名
//	 * @param certNo; 证件号
//	 * */
//	@ResponseBody
//	@RequestMapping("validateMobile.json")
//	public JSONObject validateMobile(HttpServletRequest request) {
//		ApixValidateMobileOrder queryOrder = new ApixValidateMobileOrder();
//		WebUtil.setPoPropertyByRequest(queryOrder, request);
//		JSONObject json = new JSONObject();
//		setOrderNum(queryOrder);
//		ApixValidateMobileResult result = apixCreditInvestigationFacadeClient
//			.validateMobile(queryOrder);
//		toJsonResult(json, result);
//		json.put("result", result);
//		if (result != null) {
//			json.put("info", result.getData());
//		}
//		return json;
//	}
//	
//	/** 设置查询orderNo */
//	private void setOrderNum(ApixOrderBase order) {
//		order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
//	}
//	
//}
