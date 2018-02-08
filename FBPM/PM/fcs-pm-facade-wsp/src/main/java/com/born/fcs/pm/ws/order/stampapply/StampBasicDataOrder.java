package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;


public class StampBasicDataOrder extends ProcessOrder {
    private static final long serialVersionUID = -3109148336170564578L;

    private String ids;

    private List<StampBasicDataListOrder> listOrder;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<StampBasicDataListOrder> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<StampBasicDataListOrder> listOrder) {
        this.listOrder = listOrder;
    }
}
