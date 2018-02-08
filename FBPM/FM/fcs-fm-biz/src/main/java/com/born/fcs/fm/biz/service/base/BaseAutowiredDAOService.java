/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.biz.service.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.fm.dal.daointerface.AccountAmountDetailDAO;
import com.born.fcs.fm.dal.daointerface.BankCategoryDAO;
import com.born.fcs.fm.dal.daointerface.BankMessageDAO;
import com.born.fcs.fm.dal.daointerface.BankTradeDAO;
import com.born.fcs.fm.dal.daointerface.BudgetDAO;
import com.born.fcs.fm.dal.daointerface.BudgetDetailDAO;
import com.born.fcs.fm.dal.daointerface.CostCategoryDAO;
import com.born.fcs.fm.dal.daointerface.ExpenseCxDetailDAO;
import com.born.fcs.fm.dal.daointerface.ForecastAccountChangeDetailDAO;
import com.born.fcs.fm.dal.daointerface.ForecastAccountDAO;
import com.born.fcs.fm.dal.daointerface.FormDAO;
import com.born.fcs.fm.dal.daointerface.FormExpenseApplicationDAO;
import com.born.fcs.fm.dal.daointerface.FormExpenseApplicationDetailDAO;
import com.born.fcs.fm.dal.daointerface.FormInnerLoanDAO;
import com.born.fcs.fm.dal.daointerface.FormLabourCapitalDAO;
import com.born.fcs.fm.dal.daointerface.FormLabourCapitalDetailDAO;
import com.born.fcs.fm.dal.daointerface.FormPayChangeDetailDAO;
import com.born.fcs.fm.dal.daointerface.FormPaymentDAO;
import com.born.fcs.fm.dal.daointerface.FormPaymentFeeDAO;
import com.born.fcs.fm.dal.daointerface.FormReceiptDAO;
import com.born.fcs.fm.dal.daointerface.FormReceiptFeeDAO;
import com.born.fcs.fm.dal.daointerface.FormRelatedUserDAO;
import com.born.fcs.fm.dal.daointerface.FormTransferDAO;
import com.born.fcs.fm.dal.daointerface.FormTransferDetailDAO;
import com.born.fcs.fm.dal.daointerface.FormTravelExpenseDAO;
import com.born.fcs.fm.dal.daointerface.FormTravelExpenseDetailDAO;
import com.born.fcs.fm.dal.daointerface.IncomeConfirmDAO;
import com.born.fcs.fm.dal.daointerface.IncomeConfirmDetailDAO;
import com.born.fcs.fm.dal.daointerface.ProjectRelatedUserDAO;
import com.born.fcs.fm.dal.daointerface.ReceiptPaymentFormDAO;
import com.born.fcs.fm.dal.daointerface.ReceiptPaymentFormFeeDAO;
import com.born.fcs.fm.dal.daointerface.SysForecastParamDAO;
import com.born.fcs.fm.dal.daointerface.SysSubjectMessageDAO;
import com.born.fcs.fm.daointerface.BusiDAO;
import com.born.fcs.fm.daointerface.ExtraDAO;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename BaseAutowiredDAOService
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class BaseAutowiredDAOService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected BusiDAO busiDAO;
	
	@Autowired
	protected FormDAO formDAO;
	
	@Autowired
	protected ProjectRelatedUserDAO projectRelatedUserDAO;
	
	@Autowired
	protected FormRelatedUserDAO formRelatedUserDAO;
	
	@Autowired
	protected SysSubjectMessageDAO sysSubjectMessageDAO;
	
	@Autowired
	protected BankMessageDAO bankMessageDAO;
	
	@Autowired
	protected BankTradeDAO bankTradeDAO;
	
	@Autowired
	protected FormTravelExpenseDAO formTravelExpenseDAO;
	
	@Autowired
	protected FormTravelExpenseDetailDAO formTravelExpenseDetailDAO;
	
	@Autowired
	protected FormTransferDAO formTransferDAO;
	
	@Autowired
	protected FormTransferDetailDAO formTransferDetailDAO;
	
	@Autowired
	protected FormExpenseApplicationDAO formExpenseApplicationDAO;
	
	@Autowired
	protected FormExpenseApplicationDetailDAO formExpenseApplicationDetailDAO;
	
	@Autowired
	protected FormLabourCapitalDAO formLabourCapitalDAO;
	
	@Autowired
	protected FormLabourCapitalDetailDAO formLabourCapitalDetailDAO;
	
	@Autowired
	protected ExpenseCxDetailDAO expenseCxDetailDAO;
	
	@Autowired
	protected FormInnerLoanDAO formInnerLoanDAO;
	
	@Autowired
	protected FormPaymentDAO formPaymentDAO;
	
	@Autowired
	protected FormPaymentFeeDAO formPaymentFeeDAO;
	
	@Autowired
	protected ReceiptPaymentFormDAO receiptPaymentFormDAO;
	
	@Autowired
	protected ReceiptPaymentFormFeeDAO receiptPaymentFormFeeDAO;
	
	@Autowired
	protected FormReceiptDAO formReceiptDAO;
	
	@Autowired
	protected FormReceiptFeeDAO formReceiptFeeDAO;
	
	@Autowired
	protected CostCategoryDAO costCategoryDAO;
	
	@Autowired
	protected BankCategoryDAO bankCategoryDAO;
	
	@Autowired
	protected BudgetDAO budgetDAO;
	
	@Autowired
	protected BudgetDetailDAO budgetDetailDAO;
	
	@Autowired
	protected IncomeConfirmDAO incomeConfirmDAO;
	@Autowired
	protected IncomeConfirmDetailDAO incomeConfirmDetailDAO;
	@Autowired
	protected SysForecastParamDAO sysForecastParamDAO;
	@Autowired
	protected ForecastAccountDAO forecastAccountDAO;
	@Autowired
	protected ForecastAccountChangeDetailDAO forecastAccountChangeDetailDAO;
	
	@Autowired
	protected AccountAmountDetailDAO accountAmountDetailDAO;
	
	@Autowired
	protected FormPayChangeDetailDAO formPayChangeDetailDAO;
	
	/**
	 * @return Date
	 */
	protected Date getSysdate() {
		try {
			Date sysDate = extraDAO.getSysdate();
			logger.info("系统时间：sysDate=" + sysDate);
			return sysDate;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return new Date();
	}
	
}
