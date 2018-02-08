package com.born.fcs.pm.ws.order.projectRiskReport;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.Date;
import java.util.List;

/**
 * Created by wqh on 2016/9/20.
 */
public class ProjectRiskReportProcessOrder extends ProcessOrder {
    private long reportId;

    private long customerId;

    private String customerName;

    private String enterpriseType;

    private String projectCode;

    private String projectName;

    private int timeLimit;

    private String timeUnit;

    private String amount;

    private String guaranteeFee;

    private String guaranteeFeeType;

    private String loanBank;

    private long busiManagerId;

    private String busiManagerName;

    private long riskManagerId;

    private String riskManagerName;

    private String reprot1;

    private String reprot2;

    private String reprot3;

    private String reprot4;

    private String reprot5;

    private String reportType;

    private Date reportTime;

    private long reportManId;

    private String reportManName;

    private String reportStatus;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private List<ProjectRiskReportCompDetailProcessOrder> detailProcessOrders;

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getGuaranteeFeeType() {
        return guaranteeFeeType;
    }

    public void setGuaranteeFeeType(String guaranteeFeeType) {
        this.guaranteeFeeType = guaranteeFeeType;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public long getBusiManagerId() {
        return busiManagerId;
    }

    public void setBusiManagerId(long busiManagerId) {
        this.busiManagerId = busiManagerId;
    }

    public String getBusiManagerName() {
        return busiManagerName;
    }

    public void setBusiManagerName(String busiManagerName) {
        this.busiManagerName = busiManagerName;
    }

    public long getRiskManagerId() {
        return riskManagerId;
    }

    public void setRiskManagerId(long riskManagerId) {
        this.riskManagerId = riskManagerId;
    }

    public String getRiskManagerName() {
        return riskManagerName;
    }

    public void setRiskManagerName(String riskManagerName) {
        this.riskManagerName = riskManagerName;
    }

    public String getReprot1() {
        return reprot1;
    }

    public void setReprot1(String reprot1) {
        this.reprot1 = reprot1;
    }

    public String getReprot2() {
        return reprot2;
    }

    public void setReprot2(String reprot2) {
        this.reprot2 = reprot2;
    }

    public String getReprot3() {
        return reprot3;
    }

    public void setReprot3(String reprot3) {
        this.reprot3 = reprot3;
    }

    public String getReprot4() {
        return reprot4;
    }

    public void setReprot4(String reprot4) {
        this.reprot4 = reprot4;
    }

    public String getReprot5() {
        return reprot5;
    }

    public void setReprot5(String reprot5) {
        this.reprot5 = reprot5;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public long getReportManId() {
        return reportManId;
    }

    public void setReportManId(long reportManId) {
        this.reportManId = reportManId;
    }

    public String getReportManName() {
        return reportManName;
    }

    public void setReportManName(String reportManName) {
        this.reportManName = reportManName;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
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

    public List<ProjectRiskReportCompDetailProcessOrder> getDetailProcessOrders() {
        return detailProcessOrders;
    }

    public void setDetailProcessOrders(List<ProjectRiskReportCompDetailProcessOrder> detailProcessOrders) {
        this.detailProcessOrders = detailProcessOrders;
    }
}
