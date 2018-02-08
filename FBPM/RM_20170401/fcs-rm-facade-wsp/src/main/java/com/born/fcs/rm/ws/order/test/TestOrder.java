package com.born.fcs.rm.ws.order.test;


import com.born.fcs.rm.ws.order.base.ValidateOrderBase;

public class TestOrder extends ValidateOrderBase {

    private static final long serialVersionUID = 703012990799781732L;

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
