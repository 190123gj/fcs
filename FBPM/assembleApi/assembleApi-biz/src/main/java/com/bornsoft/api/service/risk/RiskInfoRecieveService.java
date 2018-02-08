package com.bornsoft.api.service.risk;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsSyncBornApiService;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder.RiskInfoEntity;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.google.gson.reflect.TypeToken;

/**
 * @Description:  风险信息接收
 * @author taibai@yiji.com
 * @date 2016-8-23 下午3:39:48
 * @version V1.0
 */
public class RiskInfoRecieveService extends AbsSyncBornApiService {

	@Override
	protected Map<String, String> doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		RiskInfoRecOrder riskOrder = createReqOrder(request, merchantInfo);
		//执行调用
		BornSynResultBase result = this.jckDistanceClient.recieveRiskInfo(riskOrder);
		//构建返回结果
		Map<String, String> resultMap = super.buildResultMap(orderNo, getServiceCode(), result);
		return resultMap;
	}

	private RiskInfoRecOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		RiskInfoRecOrder riskOrder = super.createReqOrder(request, RiskInfoRecOrder.class, merchantInfo);
		String riskTxt = BornApiRequestUtils.getParameter(request, "list");
		List<RiskInfoEntity> list = GsonUtil.create().fromJson(riskTxt, new TypeToken<List<RiskInfoEntity>>(){}.getType());
		riskOrder.setList(list);
		return riskOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.RISK_INFO_NOTIFY;
	}

}
