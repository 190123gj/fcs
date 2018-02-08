package com.born.fcs.pm.ws.order.councilRisk;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * Created by wqh on 2016/9/8.
 */
public class EndCouncilRiskProcessOrder extends ProcessOrder {
    private long councilId;

    public long getCouncilId() {
        return councilId;
    }

    public void setCouncilId(long councilId) {
        this.councilId = councilId;
    }
}
