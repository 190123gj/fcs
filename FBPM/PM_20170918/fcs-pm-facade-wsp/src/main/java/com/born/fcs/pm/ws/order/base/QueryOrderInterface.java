package com.born.fcs.pm.ws.order.base;

public interface QueryOrderInterface {
	public long getPageSize();
	
	public long getPageNumber();
	
	public String getSortCol();
	
	public String getSortOrder();
	
}
