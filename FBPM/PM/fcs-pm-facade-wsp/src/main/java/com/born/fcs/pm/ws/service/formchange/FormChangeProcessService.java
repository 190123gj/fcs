package com.born.fcs.pm.ws.service.formchange;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 签报完相关业务处理
 *
 * @author wuzj
 */
@WebService
public interface FormChangeProcessService {
	
	/**
	 * 处理修改记录业务
	 * @param record
	 * @return
	 */
	FcsBaseResult processChange(FormChangeApplyInfo applyInfo, FormChangeRecordInfo record);
	
	/**
	 * 检查是否可以签报
	 * 
	 * success ： true 可以签报 false 不可以签报
	 * @param project
	 * @return
	 */
	FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder);
}
