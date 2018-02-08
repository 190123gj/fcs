package com.born.fcs.rm.ws.order.report;

/**
 * 
 *某个报表查询时，录入的询值
 * @author feihong
 *
 */
public class ReportQueryParam {
	
	private long reportId;

	private String reportValue;

	private String filter1Value;

	private String filter2Value;

	private String filter3Value;

	private String filter4Value;

	private String filter5Value;

	private String filter6Value;

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getReportValue() {
		return this.reportValue;
	}

	public void setReportValue(String reportValue) {
		this.reportValue = reportValue;
	}

	public String getFilter1Value() {
		return this.filter1Value;
	}

	public void setFilter1Value(String filter1Value) {
		this.filter1Value = filter1Value;
	}

	public String getFilter2Value() {
		return this.filter2Value;
	}

	public void setFilter2Value(String filter2Value) {
		this.filter2Value = filter2Value;
	}

	public String getFilter3Value() {
		return this.filter3Value;
	}

	public void setFilter3Value(String filter3Value) {
		this.filter3Value = filter3Value;
	}

	public String getFilter4Value() {
		return this.filter4Value;
	}

	public void setFilter4Value(String filter4Value) {
		this.filter4Value = filter4Value;
	}

	public String getFilter5Value() {
		return this.filter5Value;
	}

	public void setFilter5Value(String filter5Value) {
		this.filter5Value = filter5Value;
	}

	public String getFilter6Value() {
		return this.filter6Value;
	}

	public void setFilter6Value(String filter6Value) {
		this.filter6Value = filter6Value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportQueryOrder [reportId=");
		builder.append(reportId);
		builder.append(", reportValue=");
		builder.append(reportValue);
		builder.append(", filter1Value=");
		builder.append(filter1Value);
		builder.append(", filter2Value=");
		builder.append(filter2Value);
		builder.append(", filter3Value=");
		builder.append(filter3Value);
		builder.append(", filter4Value=");
		builder.append(filter4Value);
		builder.append(", filter5Value=");
		builder.append(filter5Value);
		builder.append(", filter6Value=");
		builder.append(filter6Value);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
