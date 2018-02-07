/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:33:13 创建
 */
package com.born.fcs.face.integration.fm.service.innerLoan;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanFormInfo;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanInfo;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder;
import com.born.fcs.fm.ws.result.innerLoan.FormInnerLoanResult;
import com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("formInnerLoanServiceClient")
public class FormInnerLoanServiceClient extends ClientAutowiredBaseService implements
																			FormInnerLoanService {
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#save(com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder)
	 */
	@Override
	public FormBaseResult save(final FormInnerLoanOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return formInnerLoanWebService.save(order);
			}
		});
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#findById(java.lang.Long)
	 */
	@Override
	public FormInnerLoanResult findById(final Long id) {
		return callInterface(new CallExternalInterface<FormInnerLoanResult>() {
			@Override
			public FormInnerLoanResult call() {
				return formInnerLoanWebService.findById(id);
			}
		});
	}
	
	/**
	 * @param formId
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#findByFormId(java.lang.Long)
	 */
	@Override
	public FormInnerLoanResult findByFormId(final Long formId) {
		return callInterface(new CallExternalInterface<FormInnerLoanResult>() {
			@Override
			public FormInnerLoanResult call() {
				return formInnerLoanWebService.findByFormId(formId);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#queryInnerLoan(com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<FormInnerLoanInfo> queryInnerLoan(	final FormInnerLoanQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormInnerLoanInfo>>() {
			@Override
			public QueryBaseBatchResult<FormInnerLoanInfo> call() {
				return formInnerLoanWebService.queryInnerLoan(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormInnerLoanFormInfo> searchForm(	final FormInnerLoanQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormInnerLoanFormInfo>>() {
			@Override
			public QueryBaseBatchResult<FormInnerLoanFormInfo> call() {
				return formInnerLoanWebService.searchForm(order);
			}
		});
	}
	
}
