package com.born.fcs.rm.ws.order.report.project;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.rm.ws.order.report.ReportOrder;

/**
 * 
 * 基层定期报表批量保存
 * 
 * @author lirz
 * 
 * 2016-8-10 上午10:32:16
 */
public class BatchSaveOrder extends ReportOrder {
	
	private static final long serialVersionUID = -3887709141304114262L;
	/** 报送年 */
//	private int reportYear;
	/** 报送月 */
//	private int reportMonth;
	
	@Override
	public void check() {
		validateGreaterThan(super.getReportYear(), "报送年");
		validateGreaterThan(super.getReportMonth(), "报送月");
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
