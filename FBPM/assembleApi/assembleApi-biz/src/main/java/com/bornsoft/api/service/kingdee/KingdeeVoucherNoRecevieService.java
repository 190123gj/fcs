package com.bornsoft.api.service.kingdee;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.base.AbsSyncBornApiService;
import com.bornsoft.integration.jck.JckDistanceClient;
import com.bornsoft.pub.order.kingdee.KingdeeVoucherNoRecevieOrder;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.tool.BornApiRequestUtils;

/**
  * @Description: 权利凭证号接收
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午5:57:27
  * @version V1.0
 */
public class KingdeeVoucherNoRecevieService extends AbsSyncBornApiService {

	@Autowired
	public JckDistanceClient jckDistanceClient;
	@Override
	protected Map<String, String> doProcess(String orderNo,
			HttpServletRequest request, MerchantInfo merchantInfo) {
		//构建请求order
		KingdeeVoucherNoRecevieOrder reqOrder = createReqOrder(request, merchantInfo);
		//执行调用
		BornSynResultBase result = this.jckDistanceClient.recieveVoucherNo(reqOrder);
		//构建返回结果
		Map<String, String> resultMap = super.buildResultMap(orderNo, getServiceCode(), result);
		return resultMap;
	}

	private KingdeeVoucherNoRecevieOrder createReqOrder(HttpServletRequest request,
			MerchantInfo merchantInfo) {
		KingdeeVoucherNoRecevieOrder reqOrder = super.createReqOrder(request, KingdeeVoucherNoRecevieOrder.class, merchantInfo);
		reqOrder.setBizNo(BornApiRequestUtils.getParameter(request, "bizNo"));
		if(BornApiRequestUtils.getParameter(request, "isDelete")!=null){
			reqOrder.setDelete(Boolean.valueOf(BornApiRequestUtils.getParameter(request, "isDelete")));
		}
		reqOrder.setVoucherNo(BornApiRequestUtils.getParameter(request, "voucherNo"));
		
		reqOrder.setVoucherSyncFinishTime(BornApiRequestUtils.getParameter(request, "date"));
		return reqOrder;
	}

	@Override
	public BornApiServiceEnum getServiceEnum() {
		return BornApiServiceEnum.KINGDEE_VOUCHERNO_RECEVIE_SERVICE;
	}

}
