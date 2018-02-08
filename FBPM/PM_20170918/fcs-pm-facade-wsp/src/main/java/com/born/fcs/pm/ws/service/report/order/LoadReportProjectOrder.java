package com.born.fcs.pm.ws.service.report.order;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.google.common.collect.Lists;

public class LoadReportProjectOrder extends QueryPageBase implements Serializable {
	private static final long serialVersionUID = -6062011992376211557L;
	boolean needPage;
	//	private long pageNumber;
	//	private long pageSize;
	
	List<FcsField> fcsFields = Lists.newArrayList();
	String whereSql = "";
	String having = "";
	String orderBy = "";
	/**
	 * 是否搜索历史数据
	 */
	boolean isSearchHisData = false;
	/**
	 * 历史数据截至日期
	 */
	Date hisDataDeadline;
	
	/**
	 * 多个历史截至日期对比
	 */
	Date[] hisDataDeadlineArray;
	
	public boolean isNeedPage() {
		return this.needPage;
	}
	
	public void setNeedPage(boolean needPage) {
		this.needPage = needPage;
	}
	
	//	@Override
	//	public void setPageSize(long pageSize) {
	//		this.pageSize = pageSize;
	//	}
	
	public List<FcsField> getFcsFields() {
		return this.fcsFields;
	}
	
	public void setFcsFields(List<FcsField> fcsFields) {
		this.fcsFields = fcsFields;
	}
	
	public String getWhereSql() {
		return this.whereSql;
	}
	
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
	
	public String getHaving() {
		return this.having;
	}
	
	public void setHaving(String having) {
		this.having = having;
	}
	
	public String getOrderBy() {
		return this.orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public boolean isSearchHisData() {
		return this.isSearchHisData;
	}
	
	public void setSearchHisData(boolean isSearchHisData) {
		this.isSearchHisData = isSearchHisData;
	}
	
	public Date getHisDataDeadline() {
		return this.hisDataDeadline;
	}
	
	public void setHisDataDeadline(Date hisDataDeadline) {
		this.hisDataDeadline = hisDataDeadline;
	}
	
	public Date[] getHisDataDeadlineArray() {
		return this.hisDataDeadlineArray;
	}
	
	public void setHisDataDeadlineArray(Date[] hisDataDeadlineArray) {
		this.hisDataDeadlineArray = hisDataDeadlineArray;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoadReportProjectOrder [needPage=");
		builder.append(needPage);
		//		builder.append(", pageNumber=");
		//		builder.append(pageNumber);
		//		builder.append(", pageSize=");
		//		builder.append(pageSize);
		builder.append(", fcsFields=");
		builder.append(fcsFields);
		builder.append(", whereSql=");
		builder.append(whereSql);
		builder.append(", having=");
		builder.append(having);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append(", isSearchHisData=");
		builder.append(isSearchHisData);
		builder.append(", hisDataDeadline=");
		builder.append(hisDataDeadline);
		builder.append(", hisDataDeadlineArray=");
		builder.append(Arrays.toString(hisDataDeadlineArray));
		builder.append("]");
		return builder.toString();
	}
	
}
