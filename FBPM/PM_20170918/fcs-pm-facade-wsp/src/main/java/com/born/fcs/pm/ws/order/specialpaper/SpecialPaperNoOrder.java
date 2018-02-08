package com.born.fcs.pm.ws.order.specialpaper;

import java.util.Date;

import com.born.fcs.pm.ws.enums.SpecialPaperSourceEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;


public class SpecialPaperNoOrder extends ValidateOrderBase {

    private static final long serialVersionUID = -2525678824171832942L;
    private Long  id;

    private long startNo;

    private long endNo;

    private long sourceId;

    private SpecialPaperSourceEnum source;

    private Date rawAddTime;

    private String keepingManName;

    private long keepingManId;

    private long leftPaper;

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

    public long getLeftPaper() {
        return leftPaper;
    }

    public void setLeftPaper(long leftPaper) {
        this.leftPaper = leftPaper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStartNo() {
        return startNo;
    }

    public void setStartNo(long startNo) {
        this.startNo = startNo;
    }

    public long getEndNo() {
        return endNo;
    }

    public void setEndNo(long endNo) {
        this.endNo = endNo;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public SpecialPaperSourceEnum getSource() {
        return source;
    }

    public void setSource(SpecialPaperSourceEnum source) {
        this.source = source;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
