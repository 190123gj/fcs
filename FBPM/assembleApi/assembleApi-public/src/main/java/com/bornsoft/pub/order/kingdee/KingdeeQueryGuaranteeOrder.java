package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 查询保证金账户信息
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:01:02
 * @version:     v1.0
 */
public class KingdeeQueryGuaranteeOrder extends BornOrderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 查询关键字 **/
	private String keyword;
	/** 分页页数 **/
	private int pageNumber;
	/** 每页大小 **/
	private int pageSize;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(pageNumber>0, "pageNumber");
		ValidateParamUtil.hasTrue(pageSize>0, "pageSize");
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
}
