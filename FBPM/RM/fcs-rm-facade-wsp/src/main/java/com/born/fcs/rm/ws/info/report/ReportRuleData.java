package com.born.fcs.rm.ws.info.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportRuleData implements Serializable {
	
	private static final long serialVersionUID = -3637239298151584114L;
	
	private long reportId;
	
	private String reportName;
	
	private String sqlStr;
	
	private long[] roleIds;
	
	private String filter1Name;
	
	private String filter1Type;
	
	private String filter1Sql;
	
	private String filter2Name;
	
	private String filter2Type;
	
	private String filter2Sql;
	
	private String filter3Name;
	
	private String filter3Type;
	
	private String filter3Sql;
	
	private String filter4Name;
	
	private String filter4Type;
	
	private String filter4Sql;
	
	private String filter5Name;
	
	private String filter5Type;
	
	private String filter5Sql;
	
	private String filter6Name;
	
	private String filter6Type;
	
	private String filter6Sql;
	
	private String filter1Options;
	
	private String filter2Options;
	
	private String filter3Options;
	
	private String filter4Options;
	
	private String filter5Options;
	
	private String filter6Options;
	
	/** 列名 */
	private String note;
	
	private String sortOrder;
	
	/** 页面显示列名 */
	public List<String> getTitiles() {
		List<String> list = new ArrayList<>();
		if (note != null && note != "") {
			String[] arr = this.note.replaceAll("，", ",").split(",");
			for (String s : arr) {
				list.add(s);
			}
		}
		return list;
		
	}
	
	public long getReportId() {
		return this.reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public String getReportName() {
		return this.reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String getSqlStr() {
		return this.sqlStr;
	}
	
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	
	public long[] getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	public String getFilter1Name() {
		return this.filter1Name;
	}
	
	public void setFilter1Name(String filter1Name) {
		this.filter1Name = filter1Name;
	}
	
	public String getFilter1Type() {
		return this.filter1Type;
	}
	
	public void setFilter1Type(String filter1Type) {
		this.filter1Type = filter1Type;
	}
	
	public String getFilter1Sql() {
		return this.filter1Sql;
	}
	
	public void setFilter1Sql(String filter1Sql) {
		this.filter1Sql = filter1Sql;
	}
	
	public String getFilter2Name() {
		return this.filter2Name;
	}
	
	public void setFilter2Name(String filter2Name) {
		this.filter2Name = filter2Name;
	}
	
	public String getFilter2Type() {
		return this.filter2Type;
	}
	
	public void setFilter2Type(String filter2Type) {
		this.filter2Type = filter2Type;
	}
	
	public String getFilter2Sql() {
		return this.filter2Sql;
	}
	
	public void setFilter2Sql(String filter2Sql) {
		this.filter2Sql = filter2Sql;
	}
	
	public String getFilter3Name() {
		return this.filter3Name;
	}
	
	public void setFilter3Name(String filter3Name) {
		this.filter3Name = filter3Name;
	}
	
	public String getFilter3Type() {
		return this.filter3Type;
	}
	
	public void setFilter3Type(String filter3Type) {
		this.filter3Type = filter3Type;
	}
	
	public String getFilter3Sql() {
		return this.filter3Sql;
	}
	
	public void setFilter3Sql(String filter3Sql) {
		this.filter3Sql = filter3Sql;
	}
	
	public String getFilter4Name() {
		return this.filter4Name;
	}
	
	public void setFilter4Name(String filter4Name) {
		this.filter4Name = filter4Name;
	}
	
	public String getFilter4Type() {
		return this.filter4Type;
	}
	
	public void setFilter4Type(String filter4Type) {
		this.filter4Type = filter4Type;
	}
	
	public String getFilter4Sql() {
		return this.filter4Sql;
	}
	
	public void setFilter4Sql(String filter4Sql) {
		this.filter4Sql = filter4Sql;
	}
	
	public String getFilter5Name() {
		return this.filter5Name;
	}
	
	public void setFilter5Name(String filter5Name) {
		this.filter5Name = filter5Name;
	}
	
	public String getFilter5Type() {
		return this.filter5Type;
	}
	
	public void setFilter5Type(String filter5Type) {
		this.filter5Type = filter5Type;
	}
	
	public String getFilter5Sql() {
		return this.filter5Sql;
	}
	
	public void setFilter5Sql(String filter5Sql) {
		this.filter5Sql = filter5Sql;
	}
	
	public String getFilter6Name() {
		return this.filter6Name;
	}
	
	public void setFilter6Name(String filter6Name) {
		this.filter6Name = filter6Name;
	}
	
	public String getFilter6Type() {
		return this.filter6Type;
	}
	
	public void setFilter6Type(String filter6Type) {
		this.filter6Type = filter6Type;
	}
	
	public String getFilter6Sql() {
		return this.filter6Sql;
	}
	
	public void setFilter6Sql(String filter6Sql) {
		this.filter6Sql = filter6Sql;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getFilter1Options() {
		return filter1Options;
	}
	
	public void setFilter1Options(String filter1Options) {
		this.filter1Options = filter1Options;
	}
	
	public String getFilter2Options() {
		return filter2Options;
	}
	
	public void setFilter2Options(String filter2Options) {
		this.filter2Options = filter2Options;
	}
	
	public String getFilter3Options() {
		return filter3Options;
	}
	
	public void setFilter3Options(String filter3Options) {
		this.filter3Options = filter3Options;
	}
	
	public String getFilter4Options() {
		return filter4Options;
	}
	
	public void setFilter4Options(String filter4Options) {
		this.filter4Options = filter4Options;
	}
	
	public String getFilter5Options() {
		return filter5Options;
	}
	
	public void setFilter5Options(String filter5Options) {
		this.filter5Options = filter5Options;
	}
	
	public String getFilter6Options() {
		return filter6Options;
	}
	
	public void setFilter6Options(String filter6Options) {
		this.filter6Options = filter6Options;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportRuleData [reportId=");
		builder.append(reportId);
		builder.append(", reportName=");
		builder.append(reportName);
		builder.append(", sqlStr=");
		builder.append(sqlStr);
		builder.append(", roleIds=");
		builder.append(Arrays.toString(roleIds));
		builder.append(", filter1Name=");
		builder.append(filter1Name);
		builder.append(", filter1Type=");
		builder.append(filter1Type);
		builder.append(", filter1Sql=");
		builder.append(filter1Sql);
		builder.append(", filter2Name=");
		builder.append(filter2Name);
		builder.append(", filter2Type=");
		builder.append(filter2Type);
		builder.append(", filter2Sql=");
		builder.append(filter2Sql);
		builder.append(", filter3Name=");
		builder.append(filter3Name);
		builder.append(", filter3Type=");
		builder.append(filter3Type);
		builder.append(", filter3Sql=");
		builder.append(filter3Sql);
		builder.append(", filter4Name=");
		builder.append(filter4Name);
		builder.append(", filter4Type=");
		builder.append(filter4Type);
		builder.append(", filter4Sql=");
		builder.append(filter4Sql);
		builder.append(", filter5Name=");
		builder.append(filter5Name);
		builder.append(", filter5Type=");
		builder.append(filter5Type);
		builder.append(", filter5Sql=");
		builder.append(filter5Sql);
		builder.append(", filter6Name=");
		builder.append(filter6Name);
		builder.append(", filter6Type=");
		builder.append(filter6Type);
		builder.append(", filter6Sql=");
		builder.append(filter6Sql);
		builder.append(", filter1Options=");
		builder.append(filter1Options);
		builder.append(", filter2Options=");
		builder.append(filter2Options);
		builder.append(", filter3Options=");
		builder.append(filter3Options);
		builder.append(", filter4Options=");
		builder.append(filter4Options);
		builder.append(", filter5Options=");
		builder.append(filter5Options);
		builder.append(", filter6Options=");
		builder.append(filter6Options);
		builder.append(", note=");
		builder.append(note);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append("]");
		return builder.toString();
	}
	
	public void check() throws Exception {
		
		String sqlStr2 = this.toString();
		sqlStr2 = sqlStr2.toUpperCase();
		if (sqlStr2.indexOf("DELETE ") >= 0 || sqlStr2.indexOf("UPDATE ") >= 0
			|| sqlStr2.indexOf("INSERT ") >= 0 || sqlStr.indexOf("TRUNCATE ") >= 0
			|| sqlStr2.indexOf(";") >= 0) {
			this.sqlStr = "";
			throw new Exception("危险的SQL");
		}
	}
	
}
