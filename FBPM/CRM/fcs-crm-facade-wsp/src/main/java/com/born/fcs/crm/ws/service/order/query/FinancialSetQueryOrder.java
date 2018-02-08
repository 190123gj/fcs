package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 一般企业财务指标配置查询
 * */
public class FinancialSetQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 8611666304541848347L;
	
	/** 指标类型下小分类 */
	private String typeChild;
	/** 指标类型 */
	private String type;
	/** 指标年限 如 ：2007 */
	private String year;
	
	public String getTypeChild() {
		return this.typeChild;
	}
	
	public void setTypeChild(String typeChild) {
		this.typeChild = typeChild;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinancialSetQueryOrder [typeChild=");
		builder.append(typeChild);
		builder.append(", type=");
		builder.append(type);
		builder.append(", year=");
		builder.append(year);
		builder.append("]");
		return builder.toString();
	}
	
}
