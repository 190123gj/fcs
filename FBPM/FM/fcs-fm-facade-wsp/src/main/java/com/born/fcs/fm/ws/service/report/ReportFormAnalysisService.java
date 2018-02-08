/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:05:56 创建
 */
package com.born.fcs.fm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.OfficialCardInfo;
import com.born.fcs.fm.ws.order.payment.AccountPayQueryOrder;
import com.born.fcs.fm.ws.order.payment.OfficialCardQueryOrder;
import com.born.fcs.fm.ws.order.payment.ReportAccountDetailOrder;
import com.born.fcs.fm.ws.order.report.ProjectDepositQueryOrder;
import com.born.fcs.fm.ws.result.report.AccountDetailResult;
import com.born.fcs.fm.ws.result.report.DeptAccountTypeBankMessageResult;
import com.born.fcs.fm.ws.result.report.ProjectFinancialDetailResult;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@WebService
public interface ReportFormAnalysisService {
	
	/** 可用资金明细表 */
	DeptAccountTypeBankMessageResult usedAccountDetail();
	
	/** 公务卡报销明细表 */
	public QueryBaseBatchResult<OfficialCardInfo> queryOfficialCards(OfficialCardQueryOrder order);
	
	/*** 资金状况表 */
	AccountDetailResult accountDetail(ReportAccountDetailOrder order);
	
	/** 理财项目明细表 */
	ProjectFinancialDetailResult projectFinancialDetail(FinancialProjectQueryOrder order);
	
	/** 委贷明细表 */
	ReportDataResult getEntrustedLoanDetail(ProjectOrder order);
	
	/**
	 * 项目保证金明细
	 * @param order
	 * @return
	 */
	ReportDataResult getProjectDepositDetail(ProjectDepositQueryOrder order);

	/**
	 * 项目保证金利息计提
	 * @param order
	 * @return
	 */
	ReportDataResult getProjectDepositInterest(ProjectDepositQueryOrder order);
	
	/** 财务支付打款明细表 */
	ReportDataResult getAccountPay(AccountPayQueryOrder order);
}
