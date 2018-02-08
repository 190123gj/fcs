package com.born.fcs.pm.ws.service.managerbchange;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.born.fcs.pm.ws.info.managerbchange.FManagerbChangeApplyInfo;
import com.born.fcs.pm.ws.info.managerbchange.ManagerbChangeApplyFormInfo;
import com.born.fcs.pm.ws.order.managerbchange.FManagerbChangeApplyOrder;
import com.born.fcs.pm.ws.order.managerbchange.ManagerbChangeApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/***
 * B角变更Service
 * @author wuzj
 */
@WebService
public interface ManagerbChangeService {
	
	/**
	 * 保存申请
	 * @param order
	 * @return
	 */
	@WebMethod
	public FormBaseResult saveApply(FManagerbChangeApplyOrder order);
	
	/**
	 * 查询申请单
	 * @param order
	 * @return
	 */
	@WebMethod
	QueryBaseBatchResult<ManagerbChangeApplyFormInfo> searchForm(ManagerbChangeApplyQueryOrder order);
	
	/**
	 * 查询申请单
	 * @param formId
	 * @return
	 */
	@WebMethod
	FManagerbChangeApplyInfo queryApplyByFormId(long formId);
	
}
