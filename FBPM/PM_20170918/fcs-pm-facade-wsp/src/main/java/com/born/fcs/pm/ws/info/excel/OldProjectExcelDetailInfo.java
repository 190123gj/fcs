/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:28:24 创建
 */
package com.born.fcs.pm.ws.info.excel;

import java.io.Serializable;

/**
 * 
 * 直接录入的老项目Info合集子集-项目变动情况
 * @author hjiajie
 * 
 */
public class OldProjectExcelDetailInfo implements Serializable {
	
	private static final long serialVersionUID = 4645372001915106704L;
	
	/*** 时间1（年\月\日）） 2014-1-15 */
	private String time;
	
	/** 放款金额1（万元，两位小数） 40000000 */
	private String amount;
	
	public String getTime() {
		return this.time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getAmount() {
		return this.amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OldProjectExcelInfoDetailInfo [time=");
		builder.append(time);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
}
