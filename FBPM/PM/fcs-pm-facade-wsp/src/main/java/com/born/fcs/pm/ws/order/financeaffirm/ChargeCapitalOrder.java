package com.born.fcs.pm.ws.order.financeaffirm;

import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;

/**
 * 资金划付和收费通知关联Order
 *
 *
 * @author heh
 *
 */
public class ChargeCapitalOrder  extends ProcessOrder {


    private static final long serialVersionUID = 5018613727615053151L;

    private long detailId;

    List<ChargeNoticeCapitalApproproationOrder> itemOrder;

    private FinanceAffirmTypeEnum type;

    public FinanceAffirmTypeEnum getType() {
        return type;
    }

    public void setType(FinanceAffirmTypeEnum type) {
        this.type = type;
    }

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public List<ChargeNoticeCapitalApproproationOrder> getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(List<ChargeNoticeCapitalApproproationOrder> itemOrder) {
        this.itemOrder = itemOrder;
    }
}
