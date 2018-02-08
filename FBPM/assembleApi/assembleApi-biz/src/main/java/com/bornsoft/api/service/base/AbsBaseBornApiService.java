package com.bornsoft.api.service.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.IBornApiService;
import com.bornsoft.integration.ServiceClientFactory;
import com.bornsoft.integration.jck.JckDistanceClient;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.integration.risk.RiskSystemDistanceClient;
import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.constants.BornApiRequestConstants;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.yjf.common.lang.util.money.Money;

/**
 * @Description:BornAPI的基础服务类
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:13:51 
 * @version V1.0
 */
public abstract class AbsBaseBornApiService implements IBornApiService, IAutoInjectBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ServiceClientFactory serviceClientFactory;
	@Autowired
	public JckDistanceClient jckDistanceClient;
	@Autowired
	protected RiskSystemDistanceClient  riskSystemClient;
	@Autowired
	protected SystemParamCacheHolder 	systemParamCacheHolder;


	private String serviceNameSpace = null;

	public final String getServiceNameSpace() {
		return serviceNameSpace;
	}

	public final void setServiceNameSpace(String serviceNameSpace) {
		this.serviceNameSpace = serviceNameSpace;
	}

	/**
	 * 服务名称常量
	 */
	public abstract BornApiServiceEnum getServiceEnum();

	/**
	 * 服务名称
	 */
	@Override
	public final String getServiceCode() {
		return getServiceEnum().getCode();
	}

	/**
	 * 得到服务名称
	 */
	@Override
	public final String getServiceName() {
		return getServiceEnum().getDesc();
	}



	/**
	 * 填充order
	 * @param request
	 * @param order
	 * @param merchantInfo
	 * @return
	 */
	protected  <T extends BornOutOrderBase> T createReqOrder(HttpServletRequest request, Class<T> orderClass, MerchantInfo merchantInfo){
		T order = BeanUtils.instantiate(orderClass);
		order.setPartnerId(BornApiRequestUtils.getParameter(request, BornApiRequestConstants.PARTNER_ID));
		String orderNo = (String) request.getAttribute(BornApiRequestConstants.ORDER_NO);
		if(StringUtils.isBlank(orderNo)){
			orderNo = BornApiRequestUtils.getParameter(request, BornApiRequestConstants.ORDER_NO);
		}
		order.setOrderNo(orderNo);
		order.setReturnUrl(BornApiRequestUtils.getParameter(request, BornApiRequestConstants.RETURN_URL));
		order.setNotifyUrl(BornApiRequestUtils.getParameter(request, BornApiRequestConstants.NOTIFY_URL));
		
		return order;
	}

	/**
	 * 转化为Money
	 * @param amount
	 * @return
	 */
	protected Money toMoney(String amount) {
		try {
			return new Money(amount);
		} catch (Exception ex) {
			return Money.zero();
		}
	}
}
