package com.born.fcs.pm.ws.order.file;


import com.born.fcs.pm.ws.order.common.SimpleFormOrder;

import java.io.Serializable;
import java.util.List;


public class ImportOrder extends SimpleFormOrder implements Serializable {
    private static final long serialVersionUID = 3254986999874383689L;
    private List<FileImportOrder> orders;


    public List<FileImportOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<FileImportOrder> orders) {
        this.orders = orders;
    }
}
