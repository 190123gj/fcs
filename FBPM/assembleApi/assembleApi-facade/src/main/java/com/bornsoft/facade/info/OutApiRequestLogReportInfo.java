package com.bornsoft.facade.info;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.base.BornInfoBase;

public class OutApiRequestLogReportInfo extends BornInfoBase {
	
	/***/
	private ApixServiceEnum service;
	private int count;

	public ApixServiceEnum getService() {
		return service;
	}

	public void setService(ApixServiceEnum service) {
		this.service = service;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
