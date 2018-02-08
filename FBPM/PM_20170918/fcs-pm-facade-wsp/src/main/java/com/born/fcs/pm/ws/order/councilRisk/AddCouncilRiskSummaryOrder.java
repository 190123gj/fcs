package com.born.fcs.pm.ws.order.councilRisk;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;

/**
 * Created by wqh on 2016/9/13.
 */
public class AddCouncilRiskSummaryOrder extends ProcessOrder {
    private long councilId;
    private List<CouncilRiskSummaryOrder> summaryOrder;

    public List<CouncilRiskSummaryOrder> getSummaryOrder() {
        return summaryOrder;
    }

    public void setSummaryOrder(List<CouncilRiskSummaryOrder> summaryOrder) {
        this.summaryOrder = summaryOrder;
    }

    public long getCouncilId() {
        return councilId;
    }

    public void setCouncilId(long councilId) {
        this.councilId = councilId;
    }
}
