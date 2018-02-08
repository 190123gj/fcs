/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.bornsoft.jck.dal.common;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.yjf.common.lang.util.money.Money;

import java.sql.SQLException;

/**
 *                       
 * @Filename MoneyTypeHandlerCallback.java
 *
 * @Description Money类型特殊处理
 *
 * @Version 1.0
 *
 * @Author hasulee
 *
 * @Email ligen@yiji.com
 *       
 * @History
 *<li>Author: hasulee</li>
 *<li>Date: 2012-7-10 下午10:52:38</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class MoneyTypeHandlerCallback implements TypeHandlerCallback {

	@Override
	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		setter.setLong(((Money)parameter).getCent());
	}

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		Money money = new Money();
		money.setCent(getter.getLong());
		return money;
	}

	@Override
	public Object valueOf(String s) {
		return new Money(s);
	}
}
