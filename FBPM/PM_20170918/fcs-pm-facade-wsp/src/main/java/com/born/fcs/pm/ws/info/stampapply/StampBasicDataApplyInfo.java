package com.born.fcs.pm.ws.info.stampapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 基础资料用印
 *
 *
 * @author hehao
 *
 */
public class StampBasicDataApplyInfo extends BaseToStringInfo {

    private static final long serialVersionUID = 136358642487356274L;
    private long applyId;

    private String applyCode;

    private long formId;

    private String receiver;

    private String orgNames;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private List<StampBasicDataApplyFileInfo> files;

    public List<StampBasicDataApplyFileInfo> getFiles() {
        return files;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public void setFiles(List<StampBasicDataApplyFileInfo> files) {
        this.files = files;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
