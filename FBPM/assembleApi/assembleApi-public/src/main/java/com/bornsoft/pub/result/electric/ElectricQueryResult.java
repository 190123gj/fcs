package com.bornsoft.pub.result.electric;

import com.bornsoft.utils.base.BornSynResultBase;

/**
 * @Description:电量信息查询
 * @author taibai@yiji.com
 * @date 2016-8-15 下午5:23:52
 * @version V1.0
 */
public class ElectricQueryResult extends BornSynResultBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
