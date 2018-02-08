package com.born.fcs.pm.ws.service.report.result;

import java.util.List;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.google.common.collect.Lists;

public class ReportDataResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 581378545152763396L;
	List<FcsField> fcsFields = Lists.newArrayList();
	List<DataListItem> dataList = null;
	
	List<DataListItem> dataList2 = null;
	List<DataListItem> dataList3 = null;
	List<DataListItem> dataList4 = null;
	long totalCount;
	long pageSize;
	long pageNumber;
	
	public List<FcsField> getFcsFields() {
		return this.fcsFields;
	}
	
	public void setFcsFields(List<FcsField> fcsFields) {
		this.fcsFields = fcsFields;
	}
	
	public List<DataListItem> getDataList() {
		return this.dataList;
	}
	
	public void setDataList(List<DataListItem> dataList) {
		this.dataList = dataList;
	}
	
	public List<DataListItem> getDataList2() {
		return this.dataList2;
	}
	
	public void setDataList2(List<DataListItem> dataList2) {
		this.dataList2 = dataList2;
	}
	
	public List<DataListItem> getDataList3() {
		return this.dataList3;
	}
	
	public void setDataList3(List<DataListItem> dataList3) {
		this.dataList3 = dataList3;
	}
	
	public List<DataListItem> getDataList4() {
		return this.dataList4;
	}
	
	public void setDataList4(List<DataListItem> dataList4) {
		this.dataList4 = dataList4;
	}
	
	public long getTotalCount() {
		return this.totalCount;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
		return this.pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportDataResult [fcsFields=");
		builder.append(fcsFields);
		builder.append(", dataList=");
		builder.append(dataList);
		builder.append(", dataList2=");
		builder.append(dataList2);
		builder.append(", dataList3=");
		builder.append(dataList3);
		builder.append(", dataList4=");
		builder.append(dataList4);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append("]");
		return builder.toString();
	}
	
}
