package com.bornsoft.api.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.integration.risk.RiskSystemDistanceClient;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.yjf.common.lang.util.StringUtil;

/**
  * @Description: 风险监控系统 
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午5:19:14
  * @version V1.0
 */
@Service("riskSystemFacadeApi")
public class RiskSystemFacadeImpl implements RiskSystemFacade{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RiskSystemDistanceClient  riskSystemClient;
	
	@Override
	public BornSynResultBase synOperatorInfo(SynOperatorInfoOrder order) {
		return riskSystemClient.execute(order, BornSynResultBase.class);
	}

	@Override
	public BornSynResultBase synWatchList(SynWatchListOrder order) {
		return riskSystemClient.execute(order, BornSynResultBase.class);
	}

	@Override
	public BornSynResultBase synCustomLevel(SynCustomLevelOrder order) {
		return riskSystemClient.execute(order, BornSynResultBase.class);
	}

	@Override
	public BornSynResultBase synRiskInfo(SynRiskInfoOrder order) {
		return riskSystemClient.execute(order, BornSynResultBase.class);
	}

	@Override
	public BornSynResultBase synCustomInfo(SynsCustomInfoOrder order) {
		return riskSystemClient.execute(order, BornSynResultBase.class);
	}

	@Override
	public VerifyOrganizationResult verifyOrganizationInfo(VerifyOrganizationOrder order) {
		logger.error("risk接口请求入参={}",order);
		VerifyOrganizationResult result = riskSystemClient.execute(order, VerifyOrganizationResult.class);
		if(result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS && !result.isVerySuccess()){
			result.setResultMessage("证件号码、企业名称不匹配");
		}
		logger.error("risk接口请求出参={}",result);
		return result;
	}

	@Override
	public QuerySimilarEnterpriseResult querySimilarEnterprise(QuerySimilarEnterpriseOrder order) {
		logger.error("risk接口请求入参={}",order);
		QuerySimilarEnterpriseResult result = null; 
		VerifyOrganizationOrder vryOrder = new VerifyOrganizationOrder();
		vryOrder.setCustomName(order.getCustomName());
		vryOrder.setLicenseNo(order.getLicenseNo());
		vryOrder.setOrderNo(order.getOrderNo()+"V");
		vryOrder.setUserType(UserTypeEnum.BUSINESS);
		//校验号码|名称匹配性
		VerifyOrganizationResult vryResult = verifyOrganizationInfo(vryOrder);
		if(vryResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS && vryResult.isVerySuccess()){
			//查询相似
			result = riskSystemClient.execute(order, QuerySimilarEnterpriseResult.class);
		}else{
			result = new QuerySimilarEnterpriseResult();
			if("no_check".equals(order.getNotifyUrl())){//FIXME 不专门加字段了
				InstallCommonResultUtil.installDefaultSuccessResult(vryOrder, result);
			}else{
				InstallCommonResultUtil.installDefaultFailureResult(order, result,null);
			}
			result.setResultMessage(StringUtil.defaultIfBlank(vryResult.getResultMessage(), "证照号码和企业名称不匹配"));
		}
		logger.error("risk接口请求出参={}",result);
		return result;
	}
	
}

