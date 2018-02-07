package com.born.fcs.face.integration.risk.service;

import com.born.fcs.face.integration.risk.service.impl.QueryRiskOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 风险页面跳转接口
 * */
public interface RiskPageService {
	/**
	 * 信任登陆:业务管理系统中有权限的操作员点击风险监控系统入口时
	 * @param operator 登陆人账号
	 * */
	FcsBaseResult loginRiskSystem(String operator, String toUrl);
	
	/**
	 * 企业风险信息综合查询接口
	 * @param licenseNo 证件号码
	 * @param customName 企业名称或个人姓名
	 * */
	FcsBaseResult queryRiskInfo(QueryRiskOrder order);
}
