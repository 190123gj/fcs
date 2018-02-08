/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.domain.exception;

import com.born.fcs.rm.domain.enums.FcsPmDomainResultEnum;
import com.yjf.common.lang.exception.ApplicationNestException;

/**
 * 
 * @Filename EstateDomainException.java
 * 
 * @Description 领域模型异常
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
public class FcsPmDomainException extends ApplicationNestException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -2527668261623906864L;
	
	private FcsPmDomainResultEnum domainResult;
	
	private String errorMsg;
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 */
	public FcsPmDomainException() {
		super();
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param domainResult
	 * @param errorMsg
	 */
	public FcsPmDomainException(FcsPmDomainResultEnum domainResult, String errorMsg) {
		super(errorMsg);
		this.domainResult = domainResult;
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param message
	 */
	public FcsPmDomainException(String message) {
		super(message);
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param cause
	 */
	public FcsPmDomainException(Throwable cause) {
		super(cause);
	}
	
	public FcsPmDomainResultEnum getDomainResult() {
		return domainResult;
	}
	
	public void setDomainResult(FcsPmDomainResultEnum domainResult) {
		this.domainResult = domainResult;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateDomainException [domainResult=");
		builder.append(domainResult);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}
	
}
