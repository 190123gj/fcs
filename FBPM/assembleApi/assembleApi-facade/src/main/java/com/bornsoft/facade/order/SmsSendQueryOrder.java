package com.bornsoft.facade.order;

import com.bornsoft.utils.base.BornOrderBase;

/**
 * @Description: 短信发送请求order
 * @author taibai@yiji.com
 * @date 2016-12-1 上午10:41:25
 * @version V1.0
 */
public class SmsSendQueryOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 手机号 **/
	private String mobile;
	private String startTime;
	private String endTime;
	private int pageNum;
	private int pageSize;

	public SmsSendQueryOrder() {
		this.pageNum=1;
		this.pageSize=10;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

}
