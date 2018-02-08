package com.born.fcs.rm.ws.order.submission;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;

import java.util.List;

/**
 * 数据记录查询order
 */
public class SubmissionQueryOrder extends QueryPageBase {

    private static final long serialVersionUID = 5098510561504633399L;

    private String reportName;
    
    private String reportCode;

    private String deptCode;

    private String deptName;

    private String reporterName;

    private int reportYear;

    private int reportMonth;

    private ReportStatusEnum status;

    private String year;

    private String month;

    private List statusList;

    private String startDate;

    private String endDate;

    private long reporterId;

    //用户所属部门列表
    private List<Long> deptIdList;

    private long draftUserId;

    public long getReporterId() {
        return reporterId;
    }

    public void setReporterId(long reporterId) {
        this.reporterId = reporterId;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public long getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(long draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List getStatusList() {
        return statusList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public int getReportYear() {
        return reportYear;
    }

    public void setReportYear(int reportYear) {
        this.reportYear = reportYear;
    }

    public int getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(int reportMonth) {
        this.reportMonth = reportMonth;
    }

    public ReportStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReportStatusEnum status) {
        this.status = status;
    }
}
