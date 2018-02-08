package com.bornsoft.facade.result;

import java.util.List;

import com.bornsoft.facade.info.OutApiRequestLogReportInfo;
import com.bornsoft.utils.base.BornResultBase;

public class OutApiRequestLogReportResult extends BornResultBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private List<OutApiRequestLogReportInfo> dataList = null;

	public List<OutApiRequestLogReportInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<OutApiRequestLogReportInfo> dataList) {
		this.dataList = dataList;
	}
	
	
	

}
