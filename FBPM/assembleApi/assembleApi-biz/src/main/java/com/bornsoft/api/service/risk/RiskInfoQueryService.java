package com.bornsoft.api.service.risk;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsRedirectBornApiService;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.LoginRiskSystemOrder;
import com.bornsoft.pub.order.risk.RiskInfoQueryOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.QueryRiskInfoResult;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 跳转查询风险信息 
 * @author taibai@yiji.com
 * @date 2016-8-23 下午3:43:17
 * @version V1.0
 */
public class RiskInfoQueryService extends AbsRedirectBornApiService {

	@Autowired
	private RiskSystemFacade riskSystemFacadeApi;
	
	@Override
	protected BornRedirectResult doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		RiskInfoQueryOrder order = createReqOrder(request, merchantInfo);
		//执行调用
		BornRedirectResult result = queryRiskInfoOrder(order);
		return result;
	}
	
	private RiskInfoQueryOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		RiskInfoQueryOrder riskOrder = super.createReqOrder(request, RiskInfoQueryOrder.class, merchantInfo);
		riskOrder.setCustomName(BornApiRequestUtils.getParameter(request, "customName"));
		riskOrder.setLicenseNo(BornApiRequestUtils.getParameter(request, "licenseNo"));
		riskOrder.setOperator(BornApiRequestUtils.getParameter(request, "operator"));;
		return riskOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.QUERY_RISK_INFO;
	}
	
	/**
	 * 执行查询
	 * @param order
	 * @return
	 */
	private BornRedirectResult queryRiskInfoOrder(RiskInfoQueryOrder order) {
		BornRedirectResult result;
		try {
			//校验证件号和名称是否匹配
			VerifyOrganizationOrder vryOrder = new VerifyOrganizationOrder();
			vryOrder.setCustomName(order.getCustomName());
			vryOrder.setLicenseNo(order.getLicenseNo());
			vryOrder.setOrderNo(order.getOrderNo()+"V");
			vryOrder.setUserType(UserTypeEnum.BUSINESS);
			//校验号码|名称匹配性
			VerifyOrganizationResult vryResult = riskSystemFacadeApi.verifyOrganizationInfo(vryOrder);
			if(vryResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS && vryResult.isVerySuccess()){
				QueryRiskInfoResult riskResult =  riskSystemClient.execute(order, QueryRiskInfoResult.class);
				if(riskResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS){
					//构造登录order
					LoginRiskSystemOrder loginOrder = createLoginOrder(order);
					result = riskSystemClient.execute(loginOrder, BornRedirectResult.class);
					if(result.getResultCode()  == CommonResultEnum.EXECUTE_SUCCESS){
						result.setToUrl(systemParamCacheHolder.getRiskSystemUrl() + riskResult.getDetailUrl());
						result.setLogOutUrl(systemParamCacheHolder.getRiskSystemLogOutUrl());
					}else{
						throw new BornApiException("构建登录请求失败:" + result.getResultMessage());
					}
				}else{
					throw new BornApiException("未找到风险信息:" + riskResult.getResultMessage());
				}
			}else{
				result = InstallCommonResultUtil.installRedirectFailureResult(order.getOrderNo(), StringUtil.defaultIfBlank(vryResult.getResultMessage(), "校验证件号、名称一致性失败"));
			}

		} catch (Exception e) {
			logger.error("查询企业风险信息失败",e);
			result = InstallCommonResultUtil.installRedirectFailureResult(order.getOrderNo(), e.getMessage());
		}
		return result;
	}
	
	/**
	 * 构造登录order
	 * @param order
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private LoginRiskSystemOrder createLoginOrder(RiskInfoQueryOrder order) throws IllegalAccessException, InvocationTargetException {
		LoginRiskSystemOrder login = new LoginRiskSystemOrder();
		BeanUtils.copyProperties(login, order);
		return login;
	}

}
