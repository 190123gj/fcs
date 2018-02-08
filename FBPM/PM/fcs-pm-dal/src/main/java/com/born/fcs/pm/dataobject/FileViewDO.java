package com.born.fcs.pm.dataobject;

import java.io.Serializable;
import java.util.Date;


public class FileViewDO implements Serializable {

    private static final long serialVersionUID = 5021229300113090118L;

    private String customerName;

    private String projectCode;

    private String projectName;

    private String busiManagerName;

    private Date updateTime;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getBusiManagerName() {
        return busiManagerName;
    }

    public void setBusiManagerName(String busiManagerName) {
        this.busiManagerName = busiManagerName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
