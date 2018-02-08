package com.born.fcs.pm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.info.AmountRecordInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectPayDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectRepayDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectPayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectRepayDetailQueryOrder;

/**
 * 项目相关报表
 *
 * @author wuzj
 */
@WebService
public interface ProjectReportService {
	
	/***
	 * 项目还款明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectRepayDetailInfo> projectRepayDetail(ProjectRepayDetailQueryOrder order);
	
	/**
	 * 项目划付明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectPayDetailInfo> projectPayDetail(ProjectPayDetailQueryOrder order);
	
	/***
	 * 项目收费明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectChargeDetailInfo> projectChargeDetail(	ProjectChargeDetailQueryOrder order);
	
	/**
	 * 还款记录表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<AmountRecordInfo> queryRepayRecord(FCreditConditionConfirmQueryOrder queryOrder);
	
	/**
	 * 放款记录表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<AmountRecordInfo> queryLoanRecord(FCreditConditionConfirmQueryOrder queryOrder);
}
