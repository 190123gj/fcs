package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;


public class StampConfigureOrder extends ProcessOrder {
    private static final long serialVersionUID = -5883244625617176043L;
    private List<StampConfigureListOrder> listOrder;

    public List<StampConfigureListOrder> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<StampConfigureListOrder> listOrder) {
        this.listOrder = listOrder;
    }
}
