package com.born.fcs.rm.ws.order.test;


import com.born.fcs.pm.ws.base.QueryPageBase;

public class TestQueryOrder extends QueryPageBase {
    private static final long serialVersionUID = 1771020334524314702L;

    private String testKey;

    private String testValue;

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
