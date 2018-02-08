package com.born.fcs.rm.ws.info.submission;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * 数据报送ListInfo
 */
public class SubmissionDataInfo extends BaseToStringInfo {

    private static final long serialVersionUID = 225666524654945441L;
    private long submissionDataId;

    private long submissionId;

    private String data1;

    private String data2;

    private String data3;

    private String data4;

    private String data5;

    private String data6;

    private int sortOrder;

    private Date rawAddTime;

    private Date rawUpdateTime;

    //========== getters and setters ==========


    public String getData6() {
        return data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public long getSubmissionDataId() {
        return submissionDataId;
    }

    public void setSubmissionDataId(long submissionDataId) {
        this.submissionDataId = submissionDataId;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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
