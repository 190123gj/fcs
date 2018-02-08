/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.biz.exception;

import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.yjf.common.lang.exception.ApplicationNestException;

/**
 * 
 * @Filename EstateException.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2013-2-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class FcsFmBizException extends ApplicationNestException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 554229467642044021L;
	
	private FcsResultEnum resultCode;
	
	private String errorMsg;
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 */
	public FcsFmBizException() {
		super();
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 * @param arg1
	 */
	public FcsFmBizException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public FcsFmBizException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public FcsFmBizException(Throwable arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param resultCode
	 * @param errorMsg
	 */
	public FcsFmBizException(FcsResultEnum resultCode, String errorMsg) {
		super(errorMsg);
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
	}
	
	public FcsResultEnum getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(FcsResultEnum resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateException [resultCode=");
		builder.append(resultCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}
	
}
