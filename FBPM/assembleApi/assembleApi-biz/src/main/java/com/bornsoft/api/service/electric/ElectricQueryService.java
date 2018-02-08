package com.bornsoft.api.service.electric;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsSyncBornApiService;
import com.bornsoft.pub.order.electric.ElectricQueryOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder.QueryModeEnum;
import com.bornsoft.pub.result.electric.ElectricQueryResult;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.tool.BornApiRequestUtils;

/**
  * @Description: 电量查询接口 
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午5:57:27
  * @version V1.0
 */
public class ElectricQueryService extends AbsSyncBornApiService {

	@Override
	protected Map<String, String> doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		ElectricQueryOrder order = createReqOrder(request, merchantInfo);
		//执行调用
		ElectricQueryResult result = this.jckDistanceClient.queryElectric(order);
		//构建返回结果
		Map<String, String> resultMap = super.buildResultMap(orderNo, getServiceCode(), result);
		resultMap.put("data", result.getData());
		return resultMap;
	}

	private ElectricQueryOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		ElectricQueryOrder queryOrder = super.createReqOrder(request, ElectricQueryOrder.class, merchantInfo);
		queryOrder.setConditions(BornApiRequestUtils.getParameter(request, "conditions"));
		queryOrder.setCustomName(BornApiRequestUtils.getParameter(request, "customName"));
		queryOrder.setLicenseNo(BornApiRequestUtils.getParameter(request, "licenseNo"));
		queryOrder.setQueryMode(QueryModeEnum.getByCode(BornApiRequestUtils.getParameter(request, "queryMode")));
		return queryOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.ELECTRIC_QUERY;
	}

}
