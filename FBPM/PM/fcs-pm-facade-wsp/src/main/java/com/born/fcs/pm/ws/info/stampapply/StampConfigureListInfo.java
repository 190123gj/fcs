package com.born.fcs.pm.ws.info.stampapply;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * 印章配置
 */
public class StampConfigureListInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 3222651846868588129L;

    private long id;

    private String orgId;

    private String orgCode;

    private String orgName;

    private String gzRoleCode;

    private String frzRoleCode;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
