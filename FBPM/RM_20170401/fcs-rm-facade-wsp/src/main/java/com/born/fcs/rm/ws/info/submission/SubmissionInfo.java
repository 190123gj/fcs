package com.born.fcs.rm.ws.info.submission;

import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

import java.util.Date;
import java.util.List;

/**
 * 数据报送Info
 */
public class SubmissionInfo extends BaseToStringInfo{

    private static final long serialVersionUID = 8586406960303922189L;
    private long submissionId;

    private String reportName;

    private SubmissionCodeEnum reportCode;

    private int reportYear;

    private int reportMonth;

    private long reporterId;

    private String reporterAccount;

    private String reporterName;

    private long deptId;

    private String deptCode;

    private String deptName;

    private String deptPath;

    private String deptPathName;

    private ReportStatusEnum reporterStatus;

    private String reportTime;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private List<SubmissionDataInfo> data;

    //========== getters and setters ==========

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public SubmissionCodeEnum getReportCode() {
        return reportCode;
    }

    public void setReportCode(SubmissionCodeEnum reportCode) {
        this.reportCode = reportCode;
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

    public long getReporterId() {
        return reporterId;
    }

    public void setReporterId(long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterAccount() {
        return reporterAccount;
    }

    public void setReporterAccount(String reporterAccount) {
        this.reporterAccount = reporterAccount;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
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

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getDeptPathName() {
        return deptPathName;
    }

    public void setDeptPathName(String deptPathName) {
        this.deptPathName = deptPathName;
    }

    public ReportStatusEnum getReporterStatus() {
        return reporterStatus;
    }

    public void setReporterStatus(ReportStatusEnum reporterStatus) {
        this.reporterStatus = reporterStatus;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public List<SubmissionDataInfo> getData() {
        return data;
    }

    public void setData(List<SubmissionDataInfo> data) {
        this.data = data;
    }
}
