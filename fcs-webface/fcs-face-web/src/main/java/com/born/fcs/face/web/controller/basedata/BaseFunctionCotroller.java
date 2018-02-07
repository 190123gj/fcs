package com.born.fcs.face.web.controller.basedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.risk.service.impl.QueryRiskOrder;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.env.Env;

@Controller
@RequestMapping("baseDataLoad")
public class BaseFunctionCotroller extends BaseController {
	
	/** 校验企业信息 **/
	@ResponseBody
	@RequestMapping("verifyOrganizationInfo.json")
	public JSONObject verifyOrganizationInfo(VerifyOrganizationOrder order) {
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(order.getCustomName()) || StringUtil.isBlank(order.getLicenseNo())) {
			json.put("success", false);
			json.put("message", "客户名与证件号不能为空");
			return json;
		}
		
		if (Env.isNet() || Env.isPre()) {
			json.put("success", false);
			json.put("message", "当前环境不调用风险监控");
			return json;
		}
		
		order.setUserType(UserTypeEnum.BUSINESS);
		order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
		VerifyOrganizationResult result = riskSystemFacadeClient.verifyOrganizationInfo(order);
		logger.info("校验企业信息查询结果：reslut={}", result);
		if (result != null && result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
			if (result.isVerySuccess()) {
				synUsers();
				QueryRiskOrder riskOrder = new QueryRiskOrder();
				String operator = ShiroSessionUtils.getSessionLocal().getUserName();
				riskOrder.setOperator(operator);
				riskOrder.setCustomName(order.getCustomName());
				riskOrder.setLicenseNo(order.getLicenseNo());
				FcsBaseResult urlResult = riskPageService.queryRiskInfo(riskOrder);
				if (urlResult.isSuccess()) {
					json.put("success", true);
					json.put("message", "校验通过");
					json.put("url", urlResult.getUrl());
				} else {
					json.put("success", false);
					json.put("message", "生成跳转链接失败");
				}
				
			} else {
				json.put("success", false);
				json.put("message", result.getResultMessage());
			}
		} else {
			json.put("success", false);
			json.put("message", result != null ? result.getResultMessage() : "查询异常");
		}
		return json;
	}
	
	/** 同步当前用户到风险系统 */
	
	protected void synUsers() {
		UserDetailInfo userInfo = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
		String operator = ShiroSessionUtils.getSessionLocal().getUserName();
		List<com.bornsoft.pub.order.risk.SynOperatorInfoOrder.OperatorInfo> list = new ArrayList<>();
		com.bornsoft.pub.order.risk.SynOperatorInfoOrder.OperatorInfo oper = new com.bornsoft.pub.order.risk.SynOperatorInfoOrder.OperatorInfo();
		oper.setOperator(operator);
		oper.setEmail(StringUtil.defaultIfBlank(userInfo.getEmail(), "xxx@xx.com"));
		oper.setMobile(StringUtil.defaultIfBlank(userInfo.getMobile(), "13944444444"));
		list.add(oper);
		SynOperatorInfoOrder arg0 = new SynOperatorInfoOrder();
		//TODO 配置系统参数
		arg0.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
		arg0.setList(list);
		BornSynResultBase result = riskSystemFacadeClient.synOperatorInfo(arg0);
		logger.info("同步操作员到风险系统结果：result={}", result);
	}
}
