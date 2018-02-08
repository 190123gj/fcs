package com.born.fcs.fm.ws.service.incomeconfirm;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailListInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmInfo;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeBatchConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailListQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmQueryOrder;
import com.born.fcs.fm.ws.result.incomeconfirm.IncomeDetailBatchResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;

/**
 * 收入确认
 * @author jil
 */
@WebService
public interface IncomeConfirmService {
	
	/**
	 * 保存收入确认
	 * @param order
	 * @return
	 */
	FcsBaseResult save(IncomeConfirmOrder order);
	
	/***
	 * 查询列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<IncomeConfirmInfo> query(IncomeConfirmQueryOrder order);
	
	/***
	 * 查询明细列表
	 * @param order
	 * @return
	 */
	IncomeDetailBatchResult<IncomeConfirmDetailListInfo> queryBatchConfirmList(	IncomeConfirmDetailListQueryOrder order);
	
	/**
	 * 根据ID收入确认信息
	 * @param formId
	 * @return
	 */
	IncomeConfirmInfo findById(long incomeId);
	
	/**
	 * 根据项目编号查询收入确认信息
	 * @param projectCode
	 * @return
	 */
	IncomeConfirmInfo findByProjectCode(String projectCode);
	
	/**
	 * 根据收入确认ID查询收入确认详细信息
	 * @param formId
	 * @return
	 */
	List<IncomeConfirmDetailInfo> findIncomeConfirmDetailById(long incomeId);
	
	/**
	 * 根据项目编号查询费用收取明细
	 * @param formId
	 * @return
	 */
	List<ProjectChargeDetailInfo> queryFeeList(String projectCode);
	
	/***
	 * 查询明细列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<IncomeConfirmDetailInfo> queryDetail(IncomeConfirmDetailQueryOrder order);
	
	//	/**
	//	 * 根据项目编号更新收入确认信息
	//	 * @param projectCode
	//	 * @return
	//	 */
	//	FcsBaseResult updateIncomeConfirm(String projectCode);
	
	/**
	 * 更新收入确认明细】
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult updateIncomeConfirmDetail(String projectCode, long incomeId, String incomePeriod);
	
	/**
	 * 批量确认
	 * @param order
	 * @return
	 */
	FcsBaseResult batchConfirm(IncomeBatchConfirmOrder order);
}
