/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.fm.dal.queryCondition.BusiQueryCondition;
import com.born.fcs.fm.dal.queryCondition.ExpenseApplyQueryCondition;
import com.born.fcs.fm.dal.queryCondition.FormTransferQueryCondition;
import com.born.fcs.fm.dal.queryCondition.LabourCapitalQueryCondition;
import com.born.fcs.fm.dal.queryCondition.TravelExpenseQueryCondition;
import com.born.fcs.fm.daointerface.BusiDAO;
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
 * @Filename IbatisBusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-7-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@SuppressWarnings("deprecation")
public class IbatisBusiDAO extends SqlMapClientDaoSupport implements BusiDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormReceiptFormDO> searchReceiptForm(FormReceiptFormDO receiptForm) {
		if (receiptForm == null) {
			throw new IllegalArgumentException("FormReceiptFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", receiptForm.getLimitStart());
		paramMap.put("pageSize", receiptForm.getPageSize());
		paramMap.put("sortOrder", receiptForm.getSortOrder());
		paramMap.put("sortCol", receiptForm.getSortCol());
		
		paramMap.put("formId", receiptForm.getFormId());
		paramMap.put("receiptId", receiptForm.getReceiptId());
		paramMap.put("applyUserId", receiptForm.getApplyUserId());
		paramMap.put("applyDeptId", receiptForm.getApplyDeptId());
		paramMap.put("formStatus", receiptForm.getFormStatus());
		paramMap.put("formSource", receiptForm.getFormSource());
		paramMap.put("sourceForm", receiptForm.getSourceForm());
		paramMap.put("projectCode", receiptForm.getProjectCode());
		paramMap.put("voucherNo", receiptForm.getVoucherNo());
		paramMap.put("billNo", receiptForm.getBillNo());
		paramMap.put("voucherStatus", receiptForm.getVoucherStatus());
		paramMap.put("projectName", receiptForm.getProjectName());
		paramMap.put("applyDeptName", receiptForm.getApplyDeptName());
		paramMap.put("applyUserName", receiptForm.getApplyUserName());
		
		paramMap.put("loginUserId", receiptForm.getLoginUserId());
		paramMap.put("deptIdList", receiptForm.getDeptIdList());
		
		List<FormReceiptFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-FORM-RECEIPT-FORM", paramMap);
		
		return list;
	}
	
	@Override
	public long searchReceiptFormCount(FormReceiptFormDO receiptForm) {
		if (receiptForm == null) {
			throw new IllegalArgumentException("FormReceiptFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formId", receiptForm.getFormId());
		paramMap.put("receiptId", receiptForm.getReceiptId());
		paramMap.put("applyUserId", receiptForm.getApplyUserId());
		paramMap.put("applyDeptId", receiptForm.getApplyDeptId());
		paramMap.put("formStatus", receiptForm.getFormStatus());
		paramMap.put("formSource", receiptForm.getFormSource());
		paramMap.put("sourceForm", receiptForm.getSourceForm());
		paramMap.put("projectCode", receiptForm.getProjectCode());
		paramMap.put("voucherNo", receiptForm.getVoucherNo());
		paramMap.put("billNo", receiptForm.getBillNo());
		paramMap.put("voucherStatus", receiptForm.getVoucherStatus());
		paramMap.put("projectName", receiptForm.getProjectName());
		paramMap.put("applyDeptName", receiptForm.getApplyDeptName());
		paramMap.put("applyUserName", receiptForm.getApplyUserName());
		
		paramMap.put("loginUserId", receiptForm.getLoginUserId());
		paramMap.put("deptIdList", receiptForm.getDeptIdList());
		
		return (Long) getSqlMapClientTemplate().queryForObject("MS-BUSI-FORM-RECEIPT-FORM-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormPaymentFormDO> searchPaymentForm(FormPaymentFormDO paymentForm) {
		if (paymentForm == null) {
			throw new IllegalArgumentException("FormPaymentFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", paymentForm.getLimitStart());
		paramMap.put("pageSize", paymentForm.getPageSize());
		paramMap.put("sortOrder", paymentForm.getSortOrder());
		paramMap.put("sortCol", paymentForm.getSortCol());
		
		paramMap.put("formId", paymentForm.getFormId());
		paramMap.put("receiptId", paymentForm.getPaymentId());
		paramMap.put("applyUserId", paymentForm.getApplyUserId());
		paramMap.put("applyDeptId", paymentForm.getApplyDeptId());
		paramMap.put("formStatus", paymentForm.getFormStatus());
		paramMap.put("formSource", paymentForm.getFormSource());
		paramMap.put("sourceForm", paymentForm.getSourceForm());
		paramMap.put("projectCode", paymentForm.getProjectCode());
		paramMap.put("voucherNo", paymentForm.getVoucherNo());
		paramMap.put("billNo", paymentForm.getBillNo());
		paramMap.put("isSimple", paymentForm.getIsSimple());
		paramMap.put("voucherStatus", paymentForm.getVoucherStatus());
		paramMap.put("projectName", paymentForm.getProjectName());
		paramMap.put("applyDeptName", paymentForm.getApplyDeptName());
		paramMap.put("applyUserName", paymentForm.getApplyUserName());
		
		paramMap.put("loginUserId", paymentForm.getLoginUserId());
		paramMap.put("deptIdList", paymentForm.getDeptIdList());
		
		List<FormPaymentFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-FORM-PAYMENT-FORM", paramMap);
		
		return list;
	}
	
	@Override
	public long searchPaymentFormCount(FormPaymentFormDO paymentForm) {
		if (paymentForm == null) {
			throw new IllegalArgumentException("FormPaymentFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formId", paymentForm.getFormId());
		paramMap.put("receiptId", paymentForm.getPaymentId());
		paramMap.put("applyUserId", paymentForm.getApplyUserId());
		paramMap.put("applyDeptId", paymentForm.getApplyDeptId());
		paramMap.put("formStatus", paymentForm.getFormStatus());
		paramMap.put("formSource", paymentForm.getFormSource());
		paramMap.put("sourceForm", paymentForm.getSourceForm());
		paramMap.put("projectCode", paymentForm.getProjectCode());
		paramMap.put("voucherNo", paymentForm.getVoucherNo());
		paramMap.put("billNo", paymentForm.getBillNo());
		paramMap.put("isSimple", paymentForm.getIsSimple());
		paramMap.put("voucherStatus", paymentForm.getVoucherStatus());
		paramMap.put("projectName", paymentForm.getProjectName());
		paramMap.put("applyDeptName", paymentForm.getApplyDeptName());
		paramMap.put("applyUserName", paymentForm.getApplyUserName());
		
		paramMap.put("loginUserId", paymentForm.getLoginUserId());
		paramMap.put("deptIdList", paymentForm.getDeptIdList());
		
		return (Long) getSqlMapClientTemplate().queryForObject("MS-BUSI-FORM-PAYMENT-FORM-COUNT",
			paramMap);
	}
	
	public List<FormTravelExpenseFormDO> findTravelExpenseByCondition(	TravelExpenseQueryCondition condition)
																												throws DataAccessException {
		
		return getSqlMapClientTemplate().queryForList(
			"MS-FORM-TRAVEL-EXPENSE-FIND-BY-CONDITION-FORM", condition);
		
	}
	
	public long findTravelExpenseByConditionCount(TravelExpenseQueryCondition condition)
																						throws DataAccessException {
		
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-TRAVEL-EXPENSE-FIND-BY-CONDITION-FORM-COUNT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
		
	}
	
	public List<FormTransferFormDO> findTransferByCondition(FormTransferQueryCondition condition)
																									throws DataAccessException {
		
		return getSqlMapClientTemplate().queryForList("MS-FORM-TRANSFER-FIND-BY-CONDITION-FORM",
			condition);
		
	}
	
	public long findTransferByConditionCount(FormTransferQueryCondition condition)
																					throws DataAccessException {
		
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-TRANSFER-FIND-BY-CONDITION-FORM-COUNT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
		
	}
	
	public List<FormExpenseApplicationFormDO> findExpenseByCondition(	ExpenseApplyQueryCondition condition)
																												throws DataAccessException {
		
		return getSqlMapClientTemplate().queryForList(
			"MS-FORM-EXPENSE-APPLICATION-FIND-BY-CONDITION-FORM", condition);
		
	}
	
	public long findExpenseByConditionCount(ExpenseApplyQueryCondition condition)
																					throws DataAccessException {
		
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-EXPENSE-APPLICATION-FIND-BY-CONDITION-FORM-COUNT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormInnerLoanFormDO> searchInnerLoanForm(FormInnerLoanFormDO innerLoanForm) {
		if (innerLoanForm == null) {
			throw new IllegalArgumentException("FormInnerLoanFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", innerLoanForm.getLimitStart());
		paramMap.put("pageSize", innerLoanForm.getPageSize());
		paramMap.put("sortOrder", innerLoanForm.getSortOrder());
		paramMap.put("sortCol", innerLoanForm.getSortCol());
		
		paramMap.put("formId", innerLoanForm.getFormId());
		paramMap.put("id", innerLoanForm.getId());
		paramMap.put("loanAmount", innerLoanForm.getLoanAmount());
		paramMap.put("useTime", innerLoanForm.getUseTime());
		paramMap.put("backTime", innerLoanForm.getBackTime());
		paramMap.put("backTimeEnd", innerLoanForm.getBackTimeEnd());
		paramMap.put("interestTime", innerLoanForm.getInterestTime());
		paramMap.put("formInnerLoanInterestType", innerLoanForm.getFormInnerLoanInterestType());
		paramMap.put("interestRate", innerLoanForm.getInterestRate());
		paramMap.put("protocolCode", innerLoanForm.getProtocolCode());
		paramMap.put("creditorId", innerLoanForm.getCreditorId());
		paramMap.put("creditorName", innerLoanForm.getCreditorName());
		paramMap.put("loanReason", innerLoanForm.getLoanReason());
		paramMap.put("applyUserId", innerLoanForm.getApplyUserId());
		paramMap.put("applyUserAccount", innerLoanForm.getApplyUserAccount());
		paramMap.put("applyUserName", innerLoanForm.getApplyUserName());
		paramMap.put("applyDeptId", innerLoanForm.getApplyDeptId());
		paramMap.put("applyDeptCode", innerLoanForm.getApplyDeptCode());
		paramMap.put("applyDeptName", innerLoanForm.getApplyDeptName());
		paramMap.put("rawAddTime", innerLoanForm.getRawAddTime());
		paramMap.put("rawUpdateTime", innerLoanForm.getRawUpdateTime());
		paramMap.put("loginUserId", innerLoanForm.getLoginUserId());
		paramMap.put("deptIdList", innerLoanForm.getDeptIdList());
		
		paramMap.put("innerLoanType", innerLoanForm.getInnerLoanType());
		paramMap.put("formStatus", innerLoanForm.getFormStatus());
		
		List<FormInnerLoanFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-FORM-INNER-LOAN-FORM", paramMap);
		
		return list;
	}
	
	@Override
	public long searchInnerLoanFormCount(FormInnerLoanFormDO innerLoanForm) {
		if (innerLoanForm == null) {
			throw new IllegalArgumentException("FormInnerLoanFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formId", innerLoanForm.getFormId());
		paramMap.put("id", innerLoanForm.getId());
		paramMap.put("loanAmount", innerLoanForm.getLoanAmount());
		paramMap.put("useTime", innerLoanForm.getUseTime());
		paramMap.put("backTime", innerLoanForm.getBackTime());
		paramMap.put("backTimeEnd", innerLoanForm.getBackTimeEnd());
		paramMap.put("interestTime", innerLoanForm.getInterestTime());
		paramMap.put("formInnerLoanInterestType", innerLoanForm.getFormInnerLoanInterestType());
		paramMap.put("interestRate", innerLoanForm.getInterestRate());
		paramMap.put("protocolCode", innerLoanForm.getProtocolCode());
		paramMap.put("creditorId", innerLoanForm.getCreditorId());
		paramMap.put("creditorName", innerLoanForm.getCreditorName());
		paramMap.put("loanReason", innerLoanForm.getLoanReason());
		paramMap.put("applyUserId", innerLoanForm.getApplyUserId());
		paramMap.put("applyUserAccount", innerLoanForm.getApplyUserAccount());
		paramMap.put("applyUserName", innerLoanForm.getApplyUserName());
		paramMap.put("applyDeptId", innerLoanForm.getApplyDeptId());
		paramMap.put("applyDeptCode", innerLoanForm.getApplyDeptCode());
		paramMap.put("applyDeptName", innerLoanForm.getApplyDeptName());
		paramMap.put("rawAddTime", innerLoanForm.getRawAddTime());
		paramMap.put("rawUpdateTime", innerLoanForm.getRawUpdateTime());
		paramMap.put("loginUserId", innerLoanForm.getLoginUserId());
		paramMap.put("deptIdList", innerLoanForm.getDeptIdList());
		
		paramMap.put("innerLoanType", innerLoanForm.getInnerLoanType());
		paramMap.put("formStatus", innerLoanForm.getFormStatus());
		//SqlMapClientTemplate tm = getSqlMapClientTemplate();
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-FORM-INNER-LOAN-FORM-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BackTaskDO> backTaskList(long userId) throws DataAccessException {
		List<BackTaskDO> list = getSqlMapClientTemplate().queryForList("MS-BUSI-BACK-TASK", userId);
		return list;
	}
	
	@Override
	public List<ViewOfficialCardsDO> searchViewOfficialCards(BusiQueryCondition condition)
																							throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-VIEW-OFFICIAL-CARDS", condition);
	}
	
	@Override
	public long searchViewOfficialCardsCount(BusiQueryCondition condition)
																			throws DataAccessException {
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-VIEW-OFFICIAL-CARDS-COUNT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
	}
	
	@Override
	public List<IncomeConfirmListDO> searchIncomeConfirmList(IncomeConfirmListDO incomeConfirmListDO)
																										throws DataAccessException {
		if (incomeConfirmListDO == null) {
			throw new IllegalArgumentException("FormReceiptFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", incomeConfirmListDO.getLimitStart());
		paramMap.put("pageSize", incomeConfirmListDO.getPageSize());
		paramMap.put("sortOrder", incomeConfirmListDO.getSortOrder());
		paramMap.put("sortCol", incomeConfirmListDO.getSortCol());
		
		paramMap.put("incomeId", incomeConfirmListDO.getIncomeId());
		paramMap.put("projectCode", incomeConfirmListDO.getProjectCode());
		paramMap.put("projectName", incomeConfirmListDO.getProjectName());
		paramMap.put("customerName", incomeConfirmListDO.getCustomerName());
		paramMap.put("busiType", incomeConfirmListDO.getBusiType());
		paramMap.put("busiTypeName", incomeConfirmListDO.getBusiTypeName());
		paramMap.put("customerId", incomeConfirmListDO.getCustomerId());
		paramMap.put("incomePeriod", incomeConfirmListDO.getIncomePeriod());
		paramMap.put("incomeConfirmStatus", incomeConfirmListDO.getIncomeConfirmStatus());
		
		paramMap.put("loginUserId", incomeConfirmListDO.getLoginUserId());
		paramMap.put("deptIdList", incomeConfirmListDO.getDeptIdList());
		
		List<IncomeConfirmListDO> list = getSqlMapClientTemplate().queryForList(
			"MS-INCOME-CONFIRM-LIST", paramMap);
		
		return list;
	}
	
	@Override
	public long searchIncomeConfirmListCount(IncomeConfirmListDO incomeConfirmListDO)
																						throws DataAccessException {
		if (incomeConfirmListDO == null) {
			throw new IllegalArgumentException("FormReceiptFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", incomeConfirmListDO.getLimitStart());
		paramMap.put("pageSize", incomeConfirmListDO.getPageSize());
		paramMap.put("sortOrder", incomeConfirmListDO.getSortOrder());
		paramMap.put("sortCol", incomeConfirmListDO.getSortCol());
		
		paramMap.put("incomeId", incomeConfirmListDO.getIncomeId());
		paramMap.put("projectCode", incomeConfirmListDO.getProjectCode());
		paramMap.put("projectName", incomeConfirmListDO.getProjectName());
		paramMap.put("customerName", incomeConfirmListDO.getCustomerName());
		paramMap.put("busiType", incomeConfirmListDO.getBusiType());
		paramMap.put("busiTypeName", incomeConfirmListDO.getBusiTypeName());
		paramMap.put("customerId", incomeConfirmListDO.getCustomerId());
		paramMap.put("incomePeriod", incomeConfirmListDO.getIncomePeriod());
		paramMap.put("incomeConfirmStatus", incomeConfirmListDO.getIncomeConfirmStatus());
		
		paramMap.put("loginUserId", incomeConfirmListDO.getLoginUserId());
		paramMap.put("deptIdList", incomeConfirmListDO.getDeptIdList());
		
		return (Long) getSqlMapClientTemplate().queryForObject("MS-INCOME-CONFIRM-LIST-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IncomeConfirmDetailListDO> searchIncomeConfirmDetailList(	IncomeConfirmDetailListDO incomeConfirmDetailListDO)
																																throws DataAccessException {
		
		if (incomeConfirmDetailListDO == null) {
			throw new IllegalArgumentException("incomeConfirmDetailListDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", incomeConfirmDetailListDO.getLimitStart());
		paramMap.put("pageSize", incomeConfirmDetailListDO.getPageSize());
		paramMap.put("sortOrder", incomeConfirmDetailListDO.getSortOrder());
		paramMap.put("sortCol", incomeConfirmDetailListDO.getSortCol());
		
		paramMap.put("incomeId", incomeConfirmDetailListDO.getIncomeId());
		paramMap.put("detailId", incomeConfirmDetailListDO.getDetailId());
		paramMap.put("projectCode", incomeConfirmDetailListDO.getProjectCode());
		paramMap.put("projectName", incomeConfirmDetailListDO.getProjectName());
		paramMap.put("customerId", incomeConfirmDetailListDO.getCustomerId());
		paramMap.put("customerName", incomeConfirmDetailListDO.getCustomerName());
		paramMap.put("busiType", incomeConfirmDetailListDO.getBusiType());
		paramMap.put("customerId", incomeConfirmDetailListDO.getCustomerId());
		paramMap.put("incomePeriod", incomeConfirmDetailListDO.getIncomePeriod());
		paramMap.put("incomeConfirmStatus", incomeConfirmDetailListDO.getIncomeConfirmStatus());
		paramMap.put("confirmStatus", incomeConfirmDetailListDO.getConfirmStatus());
		paramMap.put("isConfirmed", incomeConfirmDetailListDO.getIsConfirmed());
		paramMap.put("loginUserId", incomeConfirmDetailListDO.getLoginUserId());
		paramMap.put("deptIdList", incomeConfirmDetailListDO.getDeptIdList());
		
		List<IncomeConfirmDetailListDO> list = getSqlMapClientTemplate().queryForList(
			"MS-INCOME-CONFIRM-DETAIL-LIST", paramMap);
		
		return list;
	}
	
	@Override
	public HashMap searchIncomeConfirmDetailListCount(	IncomeConfirmDetailListDO incomeConfirmDetailListDO)
																										throws DataAccessException {
		if (incomeConfirmDetailListDO == null) {
			throw new IllegalArgumentException("incomeConfirmDetailListDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		paramMap.put("incomeId", incomeConfirmDetailListDO.getIncomeId());
		paramMap.put("detailId", incomeConfirmDetailListDO.getDetailId());
		paramMap.put("projectCode", incomeConfirmDetailListDO.getProjectCode());
		paramMap.put("projectName", incomeConfirmDetailListDO.getProjectName());
		paramMap.put("customerId", incomeConfirmDetailListDO.getCustomerId());
		paramMap.put("customerName", incomeConfirmDetailListDO.getCustomerName());
		paramMap.put("busiType", incomeConfirmDetailListDO.getBusiType());
		paramMap.put("customerId", incomeConfirmDetailListDO.getCustomerId());
		paramMap.put("incomePeriod", incomeConfirmDetailListDO.getIncomePeriod());
		paramMap.put("incomeConfirmStatus", incomeConfirmDetailListDO.getIncomeConfirmStatus());
		paramMap.put("confirmStatus", incomeConfirmDetailListDO.getConfirmStatus());
		paramMap.put("isConfirmed", incomeConfirmDetailListDO.getIsConfirmed());
		paramMap.put("loginUserId", incomeConfirmDetailListDO.getLoginUserId());
		paramMap.put("deptIdList", incomeConfirmDetailListDO.getDeptIdList());
		
		return (HashMap) getSqlMapClientTemplate().queryForObject(
			"MS-INCOME-CONFIRM-DETAIL-LIST-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormExpenseApplicationDetailAllFormDO> findExpenseByConditionReport(ExpenseApplyQueryCondition condition)
																															throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"MS-FORM-EXPENSE-APPLICATION-FIND-BY-CONDITION-FORM-REPORT", condition);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap findExpenseByConditionCountReport(ExpenseApplyQueryCondition condition)
																							throws DataAccessException {
		HashMap retObj = (HashMap) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-EXPENSE-APPLICATION-FIND-BY-CONDITION-FORM-COUNT-REPORT", condition);
		
		if (retObj == null) {
			return new HashMap();
		} else {
			return retObj;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormLabourCapitalFormDO> findLabourCapitalByCondition(	LabourCapitalQueryCondition condition)
																												throws DataAccessException {
		
		return getSqlMapClientTemplate().queryForList(
			"MS-FORM-LABOUR-CAPITAL-FIND-BY-CONDITION-FORM", condition);
		
	}
	
	@Override
	public long findLabourCapitalByConditionCount(LabourCapitalQueryCondition condition)
																						throws DataAccessException {
		
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-LABOUR-CAPITAL-FIND-BY-CONDITION-FORM-COUNT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
		
	}
	
	@Override
	public List<FormLabourCapitalDetailAllFormDO> findLabourCapitalByConditionReport(	LabourCapitalQueryCondition condition)
																																throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"MS-FORM-LABOUR-CAPITAL-FIND-BY-CONDITION-FORM-REPORT", condition);
	}
	
	@Override
	public long findLabourCapitalByConditionCountReport(LabourCapitalQueryCondition condition)
																								throws DataAccessException {
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-FORM-LABOUR-CAPITAL-FIND-BY-CONDITION-FORM-COUNT-REPORT", condition);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
	}
	
}
