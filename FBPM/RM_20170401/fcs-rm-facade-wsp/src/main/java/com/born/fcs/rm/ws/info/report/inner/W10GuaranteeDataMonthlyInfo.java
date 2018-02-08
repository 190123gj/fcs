package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

/**
 * W10-（市金融办）担保公司主要数据月报表
 * @author heh
 */
public class W10GuaranteeDataMonthlyInfo extends BaseToStringInfo {
    private static final long serialVersionUID = -8723307083150024673L;

    private long index=0;

    private String project1;

    private String project2;

    private String currMonth;

    private String yearInit;

    private String samePeriod;

    public W10GuaranteeDataMonthlyInfo() {

    }

    public W10GuaranteeDataMonthlyInfo(long index, String project1, String project2, String currMonth, String yearInit, String samePeriod) {
        this.index = index;
        this.project1 = project1;
        this.project2 = project2;
        this.currMonth = currMonth;
        this.yearInit = yearInit;
        this.samePeriod = samePeriod;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getProject1() {
        return project1;
    }

    public void setProject1(String project1) {
        this.project1 = project1;
    }

    public String getProject2() {
        return project2;
    }

    public void setProject2(String project2) {
        this.project2 = project2;
    }

    public String getCurrMonth() {
        return currMonth;
    }

    public void setCurrMonth(String currMonth) {
        this.currMonth = currMonth;
    }

    public String getYearInit() {
        return yearInit;
    }

    public void setYearInit(String yearInit) {
        this.yearInit = yearInit;
    }

    public String getSamePeriod() {
        return samePeriod;
    }

    public void setSamePeriod(String samePeriod) {
        this.samePeriod = samePeriod;
    }
}
