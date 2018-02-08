package com.bornsoft.api.service.risk;

import javax.servlet.http.HttpServletRequest;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsRedirectBornApiService;
import com.bornsoft.pub.order.risk.LoginRiskSystemOrder;
import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description:登录 
 * @author taibai@yiji.com
 * @date 2016-8-23 下午3:43:01
 * @version V1.0
 */
public class LoginRiskSystemService extends AbsRedirectBornApiService {

	@Override
	protected BornRedirectResult doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		LoginRiskSystemOrder order = createReqOrder(request, merchantInfo);
		//执行调用
		BornRedirectResult result = loginRiskSystem(order); 
		return result;
	}
	
	private LoginRiskSystemOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		LoginRiskSystemOrder riskOrder = super.createReqOrder(request, LoginRiskSystemOrder.class, merchantInfo);
		riskOrder.setOperator(BornApiRequestUtils.getParameter(request, "operator"));
		riskOrder.setToUrl(BornApiRequestUtils.getParameter(request, "toUrl"));
		return riskOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.LOGIN_RISK_SYSTEM;
	}
	
	/**
	 * 执行登录
	 * @param order
	 * @return
	 */
	private BornRedirectResult loginRiskSystem(LoginRiskSystemOrder order) {
		BornRedirectResult result = riskSystemClient.execute(order, BornRedirectResult.class);
		String toUrl = systemParamCacheHolder.getRiskSystemUrl();
		if(StringUtil.isNotBlank(order.getToUrl())){
			toUrl += order.getToUrl();
		}
		result.setToUrl(toUrl);
		result.setLogOutUrl(systemParamCacheHolder.getRiskSystemLogOutUrl());
		return result;
	}

}
