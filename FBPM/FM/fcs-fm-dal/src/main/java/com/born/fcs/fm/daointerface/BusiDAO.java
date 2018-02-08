/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.daointerface;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.born.fcs.fm.dal.queryCondition.BusiQueryCondition;
import com.born.fcs.fm.dal.queryCondition.ExpenseApplyQueryCondition;
import com.born.fcs.fm.dal.queryCondition.FormTransferQueryCondition;
import com.born.fcs.fm.dal.queryCondition.LabourCapitalQueryCondition;
import com.born.fcs.fm.dal.queryCondition.TravelExpenseQueryCondition;
import com.born.fcs.fm.dataobject.BackTaskDO;
import com.born.fcs.fm.dataobject.FormExpenseApplicationDetailAllFormDO;
import com.born.fcs.fm.dataobject.FormExpenseApplicationFormDO;
import com.born.fcs.fm.dataobject.FormInnerLoanFormDO;
import com.born.fcs.fm.dataobject.FormLabourCapitalDetailAllFormDO;
import com.born.fcs.fm.dataobject.FormLabourCapitalFormDO;
import com.born.fcs.fm.dataobject.FormPaymentFormDO;
import com.born.fcs.fm.dataobject.FormReceiptFormDO;
import com.born.fcs.fm.dataobject.FormTransferFormDO;
import com.born.fcs.fm.dataobject.FormTravelExpenseFormDO;
import com.born.fcs.fm.dataobject.IncomeConfirmDetailListDO;
import com.born.fcs.fm.dataobject.IncomeConfirmListDO;
import com.born.fcs.fm.dataobject.ViewOfficialCardsDO;

/**
 * @Filename BusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-8-28</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface BusiDAO {
	
	/**
	 * 查询收款单列表
	 * @param receiptForm
	 * @return
	 */
	public List<FormReceiptFormDO> searchReceiptForm(FormReceiptFormDO receiptForm)
																					throws DataAccessException;
	
	/**
	 * 查询收款单列表统计
	 * @param receiptForm
	 * @return
	 */
	public long searchReceiptFormCount(FormReceiptFormDO receiptForm) throws DataAccessException;
	
	/**
	 * 查询付款单列表
	 * @param receiptForm
	 * @return
	 */
	public List<FormPaymentFormDO> searchPaymentForm(FormPaymentFormDO paymentForm)
																					throws DataAccessException;
	
	/**
	 * 查询付款单列表统计
	 * @param receiptForm
	 * @return
	 */
	public long searchPaymentFormCount(FormPaymentFormDO paymentForm) throws DataAccessException;
	
	/**
	 * 查询内部借款单列表
	 * @param receiptForm
	 * @return
	 */
	public List<FormInnerLoanFormDO> searchInnerLoanForm(FormInnerLoanFormDO innerLoanForm)
																							throws DataAccessException;
	
	/**
	 * 查询内部借款单列表统计
	 * @param receiptForm
	 * @return
	 */
	public long searchInnerLoanFormCount(FormInnerLoanFormDO innerLoanForm)
																			throws DataAccessException;
	
	/**
	 * 驳回的任务列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	List<BackTaskDO> backTaskList(long userId) throws DataAccessException;
	
	public List<ViewOfficialCardsDO> searchViewOfficialCards(BusiQueryCondition condition)
																							throws DataAccessException;
	
	public long searchViewOfficialCardsCount(BusiQueryCondition condition)
																			throws DataAccessException;
	
	/**
	 * 查询收入确认列表
	 * @param receiptForm
	 * @return
	 */
	public List<IncomeConfirmListDO> searchIncomeConfirmList(IncomeConfirmListDO incomeConfirmListDO)
																										throws DataAccessException;
	
	/**
	 * 查询收入确认列表统计
	 * @param receiptForm
	 * @return
	 */
	public long searchIncomeConfirmListCount(IncomeConfirmListDO incomeConfirmListDO)
	
	throws DataAccessException;
	
	/**
	 * 查询收入确认明细列表
	 * @param receiptForm
	 * @return
	 */
	public List<IncomeConfirmDetailListDO> searchIncomeConfirmDetailList(	IncomeConfirmDetailListDO incomeConfirmListDO)
																															throws DataAccessException;
	
	/**
	 * 查询收入确认明细列表统计
	 * @param receiptForm <br>
	 * @return HashMap : <br>
	 * countNum 统计数量 <br>
	 * sumConfirmedAmount 已经确认金额合计<br>
	 * sumEstimatedAmount 系统预估分摊金额合计
	 */
	public HashMap searchIncomeConfirmDetailListCount(IncomeConfirmDetailListDO incomeConfirmListDO)
																									throws DataAccessException;
	
	public List<FormTravelExpenseFormDO> findTravelExpenseByCondition(	TravelExpenseQueryCondition condition)
																												throws DataAccessException;
	
	public long findTravelExpenseByConditionCount(TravelExpenseQueryCondition condition)
																						throws DataAccessException;
	
	public List<FormTransferFormDO> findTransferByCondition(FormTransferQueryCondition condition)
																									throws DataAccessException;
	
	public long findTransferByConditionCount(FormTransferQueryCondition condition)
																					throws DataAccessException;
	
	public List<FormExpenseApplicationFormDO> findExpenseByCondition(	ExpenseApplyQueryCondition condition)
																												throws DataAccessException;
	
	public long findExpenseByConditionCount(ExpenseApplyQueryCondition condition)
																					throws DataAccessException;
	
	public List<FormExpenseApplicationDetailAllFormDO> findExpenseByConditionReport(ExpenseApplyQueryCondition condition)
																															throws DataAccessException;
	
	@SuppressWarnings("rawtypes")
	public HashMap findExpenseByConditionCountReport(ExpenseApplyQueryCondition condition)
																							throws DataAccessException;
	
	public List<FormLabourCapitalFormDO> findLabourCapitalByCondition(	LabourCapitalQueryCondition condition)
																												throws DataAccessException;
	
	public long findLabourCapitalByConditionCount(LabourCapitalQueryCondition condition)
																						throws DataAccessException;
	
	public List<FormLabourCapitalDetailAllFormDO> findLabourCapitalByConditionReport(	LabourCapitalQueryCondition condition);
	
	public long findLabourCapitalByConditionCountReport(LabourCapitalQueryCondition condition)
																								throws DataAccessException;
	
}
