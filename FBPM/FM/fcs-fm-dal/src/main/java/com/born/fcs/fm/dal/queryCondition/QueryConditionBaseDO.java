package com.born.fcs.fm.dal.queryCondition;

import java.io.Serializable;

/**
 * 通用查询条件
 * @author wuzj
 */
public class QueryConditionBaseDO implements Serializable {
	
	private static final long serialVersionUID = 8449426737336274167L;
	long pageSize = 10;
	
	long pageNumber = 1;
	
	long limitStart = 0;
	
	String sortCol;
	
	String sortOrder;
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public long getLimitStart() {
		return limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
