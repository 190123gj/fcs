package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

import java.util.Date;


public class StampConfigureListOrder extends ValidateOrderBase {
    private static final long serialVersionUID = 768862644309516L;

    private Long id;

    private String orgId;

    private String orgCode;

    private String orgName;

    private String gzRoleCode;

    private String frzRoleCode;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGzRoleCode() {
        return gzRoleCode;
    }

    public void setGzRoleCode(String gzRoleCode) {
        this.gzRoleCode = gzRoleCode;
    }

    public String getFrzRoleCode() {
        return frzRoleCode;
    }

    public void setFrzRoleCode(String frzRoleCode) {
        this.frzRoleCode = frzRoleCode;
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
