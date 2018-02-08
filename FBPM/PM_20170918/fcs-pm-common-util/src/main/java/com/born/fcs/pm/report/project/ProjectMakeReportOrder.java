package com.born.fcs.pm.report.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.google.common.collect.Lists;

public class ProjectMakeReportOrder implements Serializable {
	private static final long serialVersionUID = -8292375897484276798L;
	List<FcsField> fcsFields = Lists.newArrayList();
	String whereSql = "";
	String having = "";
	String orderBy = "";
	/**
	 * 是否搜索历史数据
	 */
	boolean isSearchHisData;
	/**
	 * 历史数据截至日期
	 */
	Date hisDataDeadline;
	
	/**
	 * 多个历史截至日期对比
	 */
	Date[] hisDataDeadlineArray;
	
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
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
