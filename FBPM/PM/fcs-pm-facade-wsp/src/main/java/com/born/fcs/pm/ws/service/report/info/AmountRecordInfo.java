package com.born.fcs.pm.ws.service.report.info;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 记录表
 * 
 * @author lirz
 * 
 * 2017-4-20 上午11:51:01
 * 
 */
public class AmountRecordInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5643048411803530374L;
	
	private String dept;
	
	private String busiType;
	
	private String fixed[];
	
	private String datas[];
	
	private int max;
	
	public int getDatasLenth() {
		return null != datas ? datas.length : 0;
	}
	
	public int getEmptyColumn(int max) {
		if (null == datas) {
			return max;
		} else {
			return max - datas.length;
		}
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String[] getDatas() {
		return datas;
	}
	
	public void setDatas(String[] datas) {
		this.datas = datas;
	}
	
	public String getDept() {
		return dept;
	}
	
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public String[] getFixed() {
		return this.fixed;
	}
	
	public void setFixed(String[] fixed) {
		this.fixed = fixed;
	}

	public int getMax() {
		return this.max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
}
