package com.born.fcs.pm.ws.info.file;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * 档案一览表Info
 *
 *
 * @author hehao
 *
 */
public class FileViewInfo extends BaseToStringInfo {
    private static final long serialVersionUID = -1580835286943655629L;

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
