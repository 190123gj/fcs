package com.bornsoft.utils.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.enums.CommonResultEnum;

/**
 * @Description: 基础结果【大部分接口结果都可以直接用此类】
 * @author taibai@yiji.com
 * @date 2016-8-6 下午4:24:19
 * @version V1.0
 */
public class BornResultBase implements Serializable {

	private static final long serialVersionUID = 6240239882943130668L;

	/** 订单号 **/
	private String orderNo = "";
	/** 结果码 **/
	private CommonResultEnum resultCode;
	/** 结果详细消息值 **/
	private String resultMessage = "";

	/** ==================================构造方法 开始============================= **/
	public BornResultBase() {

	}

	public BornResultBase(CommonResultEnum resultCode, String resultMessage) {
		this.setResultCode(resultCode);
		this.setResultMessage(resultMessage);
	}

	public BornResultBase(CommonResultEnum resultCode, String resultMessage,
			BornOrderBase order) {
		this(resultCode, resultMessage);
	}

	/** ==================================构造方法 结束============================= **/

	public CommonResultEnum getResultCode() {
		return resultCode;
	}

	public void setResultCode(CommonResultEnum resultCode) {
		this.resultCode = resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = CommonResultEnum.getByCode(resultCode);
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
