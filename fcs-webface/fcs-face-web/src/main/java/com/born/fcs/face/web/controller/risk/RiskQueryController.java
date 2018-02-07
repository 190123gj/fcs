package com.born.fcs.face.web.controller.risk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.face.integration.risk.service.impl.QueryRiskOrder;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.utils.base.BornSynResultBase;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 跳转风险系统页面
 * */
@Controller
@RequestMapping("riskMg/page")
public class RiskQueryController extends BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//风险页面跳转接口
	@Autowired
	private RiskSystemFacadeClient riskSystemFacadeClient;
	
	/**
	 * 信任登陆
	 * */
	@RequestMapping("loginRiskSystem.htm")
	public void loginRiskSystem(HttpServletRequest request, HttpServletResponse response)
																							throws IOException {
		synUser();
		String operator = ShiroSessionUtils.getSessionLocal().getUserName();
		FcsBaseResult result = riskPageService.loginRiskSystem(operator,
			request.getParameter("toUrl"));
		if (result.isSuccess()) {
			response.sendRedirect(result.getUrl());
		}
	}
	
	/**
	 * 相似企业查询
	 * */
	@RequestMapping("queryRiskInfo.htm")
	public void queryRiskInfo(QueryRiskOrder order, HttpServletResponse response)
																					throws IOException {
		
		synUser();
		String operator = ShiroSessionUtils.getSessionLocal().getUserName();
		order.setOperator(operator);
		FcsBaseResult result = riskPageService.queryRiskInfo(order);
		if (result.isSuccess()) {
			response.sendRedirect(result.getUrl());
		}
		
	}
	
	/** 同步当前用户到风险系统 */
	protected void synUser() {
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
