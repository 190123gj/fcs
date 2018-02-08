package com.born.fcs.pm.ws.order.specialpaper;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.SpecialPaperSourceEnum;

import java.util.Date;

/**
 * 特殊纸查询Order
 *
 * @author heh
 *
 */
public class SpecialPaperQueryOrder extends QueryPageBase {

    private String startNo;

    private String endNo;

    private String receiveManName;

    private String keepingManName;

    private String projectName;

    private Date receiveDate;

    private String receiptPlace;

    private String receiptMan;

    private SpecialPaperSourceEnum source;

    private String provideManName;

    private String checkNo;

    private String hasLeft;

    private Date startDate;

    private Date endDate;

    private String deptName;

    private Date invalidDate;

    private Date invalidReceiveDate;

    private long parentId;

    private String isSaveInvlid;

    public String getIsSaveInvlid() {
        return isSaveInvlid;
    }

    public void setIsSaveInvlid(String isSaveInvlid) {
        this.isSaveInvlid = isSaveInvlid;
    }

    public Date getInvalidReceiveDate() {
        return invalidReceiveDate;
    }

    public void setInvalidReceiveDate(Date invalidReceiveDate) {
        this.invalidReceiveDate = invalidReceiveDate;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(String hasLeft) {
        this.hasLeft = hasLeft;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getProvideManName() {
        return provideManName;
    }

    public void setProvideManName(String provideManName) {
        this.provideManName = provideManName;
    }

    public String getReceiptPlace() {
        return receiptPlace;
    }

    public void setReceiptPlace(String receiptPlace) {
        this.receiptPlace = receiptPlace;
    }

    public String getReceiptMan() {
        return receiptMan;
    }

    public void setReceiptMan(String receiptMan) {
        this.receiptMan = receiptMan;
    }

    public SpecialPaperSourceEnum getSource() {
        return source;
    }

    public void setSource(SpecialPaperSourceEnum source) {
        this.source = source;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getReceiveManName() {
        return receiveManName;
    }

    public void setReceiveManName(String receiveManName) {
        this.receiveManName = receiveManName;
    }

    public String getKeepingManName() {
        return keepingManName;
    }

    public void setKeepingManName(String keepingManName) {
        this.keepingManName = keepingManName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
