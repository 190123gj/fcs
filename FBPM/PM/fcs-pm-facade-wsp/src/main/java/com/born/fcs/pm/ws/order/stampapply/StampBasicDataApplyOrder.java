package com.born.fcs.pm.ws.order.stampapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;


public class StampBasicDataApplyOrder extends FormOrderBase {

    private static final long serialVersionUID = -8538397564889255972L;
    private Long applyId;

    private String applyCode;

    private String receiver;

    private String orgNames;

    private Date rawAddTime;

    private List<StampBasicDataApplyFileOrder> filesOrder;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public List<StampBasicDataApplyFileOrder> getFilesOrder() {
        return filesOrder;
    }

    public void setFilesOrder(List<StampBasicDataApplyFileOrder> filesOrder) {
        this.filesOrder = filesOrder;
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
}
