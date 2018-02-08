package com.born.fcs.pm.dataobject;


import com.born.fcs.pm.dal.dataobject.ProjectDO;

import java.util.Date;

/**
 * 需要提醒归档的项目
 *
 * @author heh
 */
public class FileNeedSendMessageProjectDO extends ProjectDO{
    private static final long serialVersionUID = -8548264291416499026L;

    private long applyId;

    private Date actualLoanTime;

    private Date submitTime;

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public Date getActualLoanTime() {
        return actualLoanTime;
    }

    public void setActualLoanTime(Date actualLoanTime) {
        this.actualLoanTime = actualLoanTime;
    }
}
