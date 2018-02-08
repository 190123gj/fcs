package com.born.fcs.pm.ws.order.specialpaper;


import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;

public class SpecialPaperNoListOrder extends ProcessOrder {
    private static final long serialVersionUID = -6406258947016845703L;
    List<SpecialPaperNoOrder> listOrder;

    private String keepingManName;

    private long keepingManId;

    public String getKeepingManName() {
        return keepingManName;
    }

    public void setKeepingManName(String keepingManName) {
        this.keepingManName = keepingManName;
    }

    public long getKeepingManId() {
        return keepingManId;
    }

    public void setKeepingManId(long keepingManId) {
        this.keepingManId = keepingManId;
    }

    public List<SpecialPaperNoOrder> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<SpecialPaperNoOrder> listOrder) {
        this.listOrder = listOrder;
    }
}
