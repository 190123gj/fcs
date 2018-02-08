package com.bornsoft.utils.base;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.enums.CommonResultEnum;

/**
 * @Description: 基础结果【大部分接口结果都可以直接用此类】
 * @author taibai@yiji.com
 * @date 2016-8-6 下午4:24:19
 * @version V1.0
 */
public class BornSynResultBase extends BornResultBase {

	private static final long serialVersionUID = 6240239882943130668L;

	/** 公共结果 **/
	protected String data;

	/** ==================================构造方法 开始============================= **/
	public BornSynResultBase() {

	}

	public BornSynResultBase(CommonResultEnum resultCode, String resultMessage) {
		this.setResultCode(resultCode);
		this.setResultMessage(resultMessage);
	}

	public BornSynResultBase(CommonResultEnum resultCode, String resultMessage,
			BornOrderBase order) {
		this(resultCode, resultMessage);
		this.setOrderNo(order.getOrderNo());
	}

	/** ==================================构造方法 结束============================= **/

	protected void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
