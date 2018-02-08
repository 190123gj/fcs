package com.bornsoft.facade.result;

import java.util.List;

import com.bornsoft.facade.info.OutApiRequestLogInfo;
import com.bornsoft.utils.base.BornResultBase;

public class OutApiRequestLogQueryResult extends BornResultBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private int pageNum;
	private int pageSize;
	private int totalCount;
	private int pageCount;

	private List<OutApiRequestLogInfo> dataList = null;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<OutApiRequestLogInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<OutApiRequestLogInfo> dataList) {
		this.dataList = dataList;
	}
	


	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 计算总页数
	 * @param totalPageCount  总纪录数
	 * @param pageSize		     页大小
	 */
	public void calculate(int totalCount, int pageSize) {
		this.totalCount = totalCount;
		if (totalCount % pageSize == 0) {
			pageCount = totalCount / pageSize;
		} else {
			pageCount = totalCount / pageSize + 1;
		}
	}

}
