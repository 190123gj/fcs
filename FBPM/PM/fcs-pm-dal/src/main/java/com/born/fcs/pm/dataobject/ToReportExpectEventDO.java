package com.born.fcs.pm.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 预计发生情况汇总表
 *
 * @author heh
 */
public class ToReportExpectEventDO implements Serializable {
    private static final long serialVersionUID = 3995698388521369114L;

    private long amount;

    private long contractAmount;

    private long deptId;

    private String deptCode;

    private String deptName;

    private String type;

    private int year;

    private int month;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String busiType;

    public long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
}
