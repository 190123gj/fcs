package com.bornsoft.pub.result.apix;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.base.BornResultBase;

/**
 * @Description: apix 基础结果 
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:31:22
 * @version V1.0
 */
public class ApixResultBase extends BornResultBase {
	
	/**
	 */
	private static final long serialVersionUID = -7775225395966062188L;

	private String code;
	private String msg;
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
