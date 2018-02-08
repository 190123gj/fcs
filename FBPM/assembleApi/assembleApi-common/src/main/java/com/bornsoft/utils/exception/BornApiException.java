package com.bornsoft.utils.exception;

import com.bornsoft.utils.enums.BornApiExceptionEnum;

/**
  * @Description: 接口自定义异常 
  * @author taibai@yiji.com 
  * @date  2016-8-5 下午2:12:24
  * @version V1.0
 */
public class BornApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BornApiException(Exception ex){
		super(ex);
	}
	
	public BornApiException(String msg) {
		super(msg);
	}
	
	public BornApiException(String msg,Throwable e) {
		super(msg,e);
	}

	public BornApiException(BornApiExceptionEnum exEnum) {
		super(exEnum.getDesc());
	}
	
	
}
