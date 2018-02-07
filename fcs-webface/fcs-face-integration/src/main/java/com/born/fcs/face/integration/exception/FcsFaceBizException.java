/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.face.integration.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.yjf.common.lang.exception.ApplicationNestException;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

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
 *          <li>Content: create</li>
 * 
 */
public class FcsFaceBizException extends ApplicationNestException {

	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 554229467642044021L;

	private FcsResultEnum resultCode;

	private String errorMsg;

	private String stackMsg;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 构建一个<code>EstateException.java</code>
	 */
	public FcsFaceBizException() {
		super();
	}

	/**
	 * 构建一个<code>EstateException.java</code>
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public FcsFaceBizException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * 构建一个<code>EstateException.java</code>
	 * 
	 * @param arg0
	 */
	public FcsFaceBizException(String arg0) {
		super(arg0);
	}

	/**
	 * 构建一个<code>EstateException.java</code>
	 * 
	 * @param arg0
	 */
	public FcsFaceBizException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * 构建一个<code>EstateException.java</code>
	 * 
	 * @param resultCode
	 * @param errorMsg
	 */
	public FcsFaceBizException(FcsResultEnum resultCode, String errorMsg) {
		super(errorMsg);
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
		ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
		this.printStackTrace(new java.io.PrintWriter(buf, true));
		this.stackMsg = buf.toString();
		try {
			buf.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
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

	public String getStackMsg() {
		return this.stackMsg;
	}

	public void setStackMsg(String stackMsg) {
		this.stackMsg = stackMsg;
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
