package com.born.fcs.rm.ws.info.test;


import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

public class TestInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 985601198626039190L;

    private long testId;

    private String testKey;

    private String testValue;

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTestKey() {
        return testKey;
    }

    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}
