package com.bornsoft.jck.dal.common;

/**
 * @Description: 分页基础类
 * @author:      xiaohui@yiji.com
 * @date         2016-3-19 下午4:45:28
 * @version:     v1.0
 */
public class CoreQueryPageDOBase {

	/** 页大小 **/
	private int pageSize = 10;
	/** 当前页 **/
	private int pageNo = 1;
	
	/**
	 * MySQL 获取开始索引
	 * @return
	 */
	public int getStartIndex() {
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
