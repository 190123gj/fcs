/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:44:18 创建
 */
package com.born.fcs.fm.ws.result.innerLoan;

import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FormInnerLoanResult extends FormBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private FormInnerLoanInfo formInnerLoanInfo;
	
	public FormInnerLoanInfo getFormInnerLoanInfo() {
		return this.formInnerLoanInfo;
	}
	
	public void setFormInnerLoanInfo(FormInnerLoanInfo formInnerLoanInfo) {
		this.formInnerLoanInfo = formInnerLoanInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormInnerLoanResult [formInnerLoanInfo=");
		builder.append(formInnerLoanInfo);
		builder.append(", getFormInfo()=");
		builder.append(getFormInfo());
		builder.append(", isExecuted()=");
		builder.append(isExecuted());
		builder.append(", isSuccess()=");
		builder.append(isSuccess());
		builder.append("]");
		return builder.toString();
	}
	
}
