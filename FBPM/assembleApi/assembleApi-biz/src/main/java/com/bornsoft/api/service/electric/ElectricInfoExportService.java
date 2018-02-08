package com.bornsoft.api.service.electric;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsSyncBornApiService;
import com.bornsoft.facade.api.common.CoreExportDataFacade;
import com.bornsoft.facade.api.electric.ElectricQueryFacade;
import com.bornsoft.pub.order.electric.ElectricInfoExportOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder.QueryModeEnum;
import com.bornsoft.pub.result.common.CoreExportDataResult;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.tool.BornApiRequestUtils;

/**
  * @Description: 电量报表 
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午5:57:27
  * @version V1.0
 */
public class ElectricInfoExportService extends AbsSyncBornApiService {
	
	@SuppressWarnings("unused")
	@Autowired
	private CoreExportDataFacade coreExportDataFacade;
	@Autowired
	private ElectricQueryFacade  electricQueryFacade;
	
	@Override
	protected Map<String, String> doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		ElectricInfoExportOrder order = createReqOrder(request, merchantInfo);
		//执行调用
		CoreExportDataResult result = this.electricQueryFacade.exportElectricInfo(order);
		//构建返回结果
		Map<String, String> resultMap = super.buildResultMap(orderNo, getServiceCode(), result);
		resultMap.put("downloadUrl", result.getDownloadUrl());
		return resultMap;
	}

	private ElectricInfoExportOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		ElectricInfoExportOrder queryOrder = super.createReqOrder(request, ElectricInfoExportOrder.class, merchantInfo);
		queryOrder.setConditions(BornApiRequestUtils.getParameter(request, "conditions"));
		queryOrder.setCustomName(BornApiRequestUtils.getParameter(request, "customName"));
		queryOrder.setLicenseNo(BornApiRequestUtils.getParameter(request, "licenseNo"));
		queryOrder.setQueryMode(QueryModeEnum.getByCode(BornApiRequestUtils.getParameter(request, "queryMode")));
		return queryOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.ELECTRIC_EXPORT_INFO;
	}

}
