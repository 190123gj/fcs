package com.born.fcs.pm.ws.service.report.result;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

public class ReportItem implements Serializable {
	private static final long serialVersionUID = 7229756689057489456L;
	String key;
	String value;
	DataTypeEnum dataTypeEnum;
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public DataTypeEnum getDataTypeEnum() {
		return this.dataTypeEnum;
	}
	
	public void setDataTypeEnum(DataTypeEnum dataTypeEnum) {
		this.dataTypeEnum = dataTypeEnum;
	}
	
	public Object getObject() {
		if (this.value == null)
			return null;
		if (dataTypeEnum == DataTypeEnum.MONEY) {
			Money money = Money.zero();
			money.setCent(new BigDecimal(this.value).longValue());
			return money;
		} else if (dataTypeEnum == DataTypeEnum.DATE) {
			try {
				return DateUtil.string2Date(this.value);
			} catch (ParseException e) {
			}
		} else if (dataTypeEnum == DataTypeEnum.BIGDECIMAL) {
			return new BigDecimal(this.value);
		}
		return this.value;
	}
}
