package com.born.fcs.pm.ws.service.summary;

import java.util.Date;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.summary.AfterwardsProjectSummaryInfo;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryOrder;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 保后项目汇总
 *
 * @author Ji
 */
@WebService
public interface AfterwardsProjectSummaryService {
	
	/**
	 * 根据ID保后项目汇总信息
	 * @param summaryId
	 * @return
	 */
	AfterwardsProjectSummaryInfo findById(long summaryId);
	
	/**
	 * 保存保后项目汇总信息
	 * @param order
	 * @return
	 */
	FcsBaseResult save(AfterwardsProjectSummaryOrder order);
	
	/**
	 * 查询保后项目汇总列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<AfterwardsProjectSummaryInfo> query(	AfterwardsProjectSummaryQueryOrder order);
	
	/**
	 * 根据id删除保后项目汇总信息
	 * @param summaryId
	 * @return
	 */
	int deleteById(long summaryId);
	
	/**
	 * 查询 保后项目汇总信息 户数
	 * @param busiType
	 * @param finishTime
	 * @param deptCode
	 * @return
	 */
	int findHouseholdsByCondition(String busiType, Date finishTime, String deptCode);
	
	/**
	 * 查询 保后项目汇总信息 在保余额
	 * @param busiType
	 * @param finishTime
	 * @param deptCode
	 * @return
	 */
	Money findReleasingAmountByCondition(String busiType, Date finishTime, String deptCode);
	
	/**
	 * 根据部门编号和所属报告期
	 * @param deptCode
	 * @param reportPeriod
	 * @return
	 */
	AfterwardsProjectSummaryInfo findByDeptCodeAndReportPeriod(String deptCode, String reportPeriod);
}
