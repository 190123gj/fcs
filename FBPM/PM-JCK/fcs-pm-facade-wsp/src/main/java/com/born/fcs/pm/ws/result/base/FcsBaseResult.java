/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.result.base;

import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename YrdBaseResult.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class FcsBaseResult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 5156892170604621621L;
	/** 返回结果码 */
	FcsResultEnum fcsResultEnum = FcsResultEnum.UN_KNOWN_EXCEPTION;
	
	private String url;
	
	/** 新增后返回的主键 */
	private long keyId;
	
	/** 需要返回的对象 */
	private Object returnObject;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getKeyId() {
		return this.keyId;
	}
	
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public boolean isExecuted() {
		
		return FcsResultEnum.EXECUTE_SUCCESS == fcsResultEnum ? true : false;
	}
	
	public FcsResultEnum getFcsResultEnum() {
		return fcsResultEnum;
	}
	
	public void setFcsResultEnum(FcsResultEnum fcsResultEnum) {
		this.fcsResultEnum = fcsResultEnum;
		if (this.fcsResultEnum != null) {
			if (StringUtil.isEmpty(this.getMessage())) {
				this.setMessage(this.fcsResultEnum.getMessage());
			}
			
		}
	}
	
	@Override
	public void setSuccess(boolean success) {
		super.setSuccess(success);
		if (success)
			fcsResultEnum = FcsResultEnum.EXECUTE_SUCCESS;
	}
	
	public Object getReturnObject() {
		return this.returnObject;
	}
	
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FcsBaseResult [fcsResultEnum=");
		builder.append(fcsResultEnum);
		builder.append(", url=");
		builder.append(url);
		builder.append(", keyId=");
		builder.append(keyId);
		builder.append(", returnObject=");
		builder.append(returnObject);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
