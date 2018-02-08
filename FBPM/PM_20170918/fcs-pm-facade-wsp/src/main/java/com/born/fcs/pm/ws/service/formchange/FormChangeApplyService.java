package com.born.fcs.pm.ws.service.formchange;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordDetailInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 签报申请
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FormChangeApplyService {
	
	/**
	 * 查询申请单
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormChangeApplySearchInfo> searchForm(FormChangeApplyQueryOrder order);
	
	/**
	 * 查询申请单
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormChangeApplyInfo> searchApply(FormChangeApplyQueryOrder order);
	
	/**
	 * 查询签报申请单
	 * @param formId
	 * @return
	 */
	FormChangeApplyInfo queryByFormId(long formId);
	
	/**
	 * 查询签报修改记录
	 * @param applyFormId
	 * @param status
	 * @param queryDetail
	 * @return
	 */
	QueryBaseBatchResult<FormChangeRecordInfo> searchRecord(FormChangeRecordQueryOrder order);
	
	/**
	 * 查询修改记录
	 * @param recordId
	 * @return
	 */
	FormChangeRecordInfo queryRecord(long recordId, boolean queryDetail);
	
	/**
	 * 根据记录ID查询修改明细
	 * @param recordId
	 * @return
	 */
	List<FormChangeRecordDetailInfo> queryRecordDetail(long recordId);
	
	/**
	 * 保存签报申请单
	 * @param order
	 * @return
	 */
	FormBaseResult saveApply(FormChangeApplyOrder order);
	
	/**
	 * 保存签报修改记录
	 * @param order
	 * @return
	 */
	FormBaseResult saveRecord(FormChangeRecordOrder order);
	
	/**
	 * 执行变更
	 * @param formId
	 * @return
	 */
	FcsBaseResult executeChange(long formId);
	
	/**
	 * 重新执行
	 * @param recordId
	 * @return
	 */
	FcsBaseResult reExeChange(long recordId);
	
	/**
	 * 检查是否可签报
	 * @param projectCode
	 * @param changeForm
	 * @return
	 */
	FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder);
}
