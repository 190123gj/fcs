package com.bornsoft.utils.tool;

import java.util.Date;

/**
  * @Description: 订单号util
  * @author xiaohui@yiji.com 
  * @date 2015-5-6 下午5:27:25 
  * @version V1.0
 */
public class SingleOrderUtil {

	private static SingleOrderUtil orderUtil = new SingleOrderUtil();
		
	private SingleOrderUtil(){
		
	}
	
	/**
	 * 获取对象实例
	 * @return
	 */
	public static SingleOrderUtil getInstance() {
		return orderUtil;
	}
	
	/**
	 * 获取唯一订单号
	 * @return
	 */
	public synchronized String createOrderNo() {
		String orderNo = DateUtils.toString(new Date(), DateUtils.PATTERN6);
		try {
			Thread.sleep(1);
		} catch (InterruptedException ex) {}
		return "B" + orderNo;
	}
	
	/**
	 * 获取唯一订单号
	 * @param perfix
	 * @return
	 */
	public synchronized String createOrderNo(String perfix) {
		String orderNo = DateUtils.toString(new Date(), DateUtils.PATTERN6);
		try {
			Thread.sleep(1);
		} catch (InterruptedException ex) {}
		return perfix + orderNo;
	}
	
}
