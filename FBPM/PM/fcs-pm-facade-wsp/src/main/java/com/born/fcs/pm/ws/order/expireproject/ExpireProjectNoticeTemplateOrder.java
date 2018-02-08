package com.born.fcs.pm.ws.order.expireproject;

import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.Date;


public class ExpireProjectNoticeTemplateOrder extends ProcessOrder {
    private static final long serialVersionUID = -2117782445369989078L;

    private Long id;

    private long sequence;

    private String projectCode;

    private String projectName;

    private String exhibitionPeriodProtocolNo;

    private long borrowMoney;

    private long operatorId;

    private String operator;

    private Date issueDate;

    private Date expirationDate;

    private long interest;

    private long fullInNumbers;

    private long returnNumbers;

    private String remark;

    private Date rawAddTime;

    private String year;

    private String html;

    private long expireId;

    private ExpireProjectStatusEnum status;

    private String isUnderwriting;//是否承销项目

    private String receipt;

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getIsUnderwriting() {
        return isUnderwriting;
    }

    public void setIsUnderwriting(String isUnderwriting) {
        this.isUnderwriting = isUnderwriting;
    }

    public ExpireProjectStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExpireProjectStatusEnum status) {
        this.status = status;
    }

    public long getExpireId() {
        return expireId;
    }

    public void setExpireId(long expireId) {
        this.expireId = expireId;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
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

    public String getExhibitionPeriodProtocolNo() {
        return exhibitionPeriodProtocolNo;
    }

    public void setExhibitionPeriodProtocolNo(String exhibitionPeriodProtocolNo) {
        this.exhibitionPeriodProtocolNo = exhibitionPeriodProtocolNo;
    }

    public long getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(long borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getInterest() {
        return interest;
    }

    public void setInterest(long interest) {
        this.interest = interest;
    }

    public long getFullInNumbers() {
        return fullInNumbers;
    }

    public void setFullInNumbers(long fullInNumbers) {
        this.fullInNumbers = fullInNumbers;
    }

    public long getReturnNumbers() {
        return returnNumbers;
    }

    public void setReturnNumbers(long returnNumbers) {
        this.returnNumbers = returnNumbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
