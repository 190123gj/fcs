/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:07:31 创建
 */
package com.born.fcs.fm.ws.service.innerLoan;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanFormInfo;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanInfo;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder;
import com.born.fcs.fm.ws.result.innerLoan.FormInnerLoanResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@WebService
public interface FormInnerLoanService {
	
	/*** 保存 */
	FormBaseResult save(FormInnerLoanOrder order);
	
	/*** 通过主键查询 */
	FormInnerLoanResult findById(Long id);
	
	/*** 通过formId查询 */
	FormInnerLoanResult findByFormId(Long formId);
	
	/** 单表列查 */
	QueryBaseBatchResult<FormInnerLoanInfo> queryInnerLoan(FormInnerLoanQueryOrder order);
	
	/***
	 * 查询表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormInnerLoanFormInfo> searchForm(FormInnerLoanQueryOrder order);
	
}
