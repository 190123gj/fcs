package com.born.fcs.pm.ws.order.specialpaper;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;


public class SpecialPaperInvalidOrder extends ProcessOrder {

    private static final long serialVersionUID = 5587110283731565851L;
    private long id;

    private Long receiveManId;

    private String receiveManName;

    private long keepingManId;

    private String keepingManName;

    private long pieces;

    private String remark;

    private Date rawAddTime;

    List<SpecialPaperNoOrder> listOrder;

    private Date receiveDate;

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public List<SpecialPaperNoOrder> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<SpecialPaperNoOrder> listOrder) {
        this.listOrder = listOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getReceiveManId() {

        return receiveManId;
    }

    public void setReceiveManId(Long receiveManId) {
        this.receiveManId = receiveManId;
    }

    public String getReceiveManName() {
        return receiveManName;
    }

    public void setReceiveManName(String receiveManName) {
        this.receiveManName = receiveManName;
    }

    public long getKeepingManId() {
        return keepingManId;
    }

    public void setKeepingManId(long keepingManId) {
        this.keepingManId = keepingManId;
    }

    public String getKeepingManName() {
        return keepingManName;
    }

    public void setKeepingManName(String keepingManName) {
        this.keepingManName = keepingManName;
    }

    public long getPieces() {
        return pieces;
    }

    public void setPieces(long pieces) {
        this.pieces = pieces;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
