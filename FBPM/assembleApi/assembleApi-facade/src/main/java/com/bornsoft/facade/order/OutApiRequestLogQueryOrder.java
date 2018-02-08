package com.bornsoft.facade.order;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;

public class OutApiRequestLogQueryOrder extends BornOrderBase{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private int pageNum;
	private int pageSize;
	private String serviceName;
	private String startTime;
	private String endTime;
	
	public OutApiRequestLogQueryOrder() {
		this.pageNum=1;
		this.pageSize=10;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public void validateOrder() throws BornApiException {
		if(pageNum <= 0){
			pageNum = 1;
		}
		if(pageSize <= 0){
			pageSize =10;
		}
	}
}
