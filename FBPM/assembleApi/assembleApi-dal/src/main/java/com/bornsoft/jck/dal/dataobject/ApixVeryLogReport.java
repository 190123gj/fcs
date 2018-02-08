package com.bornsoft.jck.dal.dataobject;

/**
 * 
 * 
 * @Filename Maker.java
 * 
 * @Description 自动生成DO
 * 
 * @Version 1.0
 * 
 * @Author jlcon
 * 
 * @Email jianglu@yiji.com
 * 
 * @History <li>Author: jlcon</li> <li>Date:</li> <li>Version: 1.0</li> <li>
 *          Content: create</li>
 * 
 */
public class ApixVeryLogReport {

	public static final String APIXVERYLOGDO = "APIXVERYLOGDO";
	/** 请求服务名称 */
	private String serviceName;
	/** 调用次数 */
	private int count;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}