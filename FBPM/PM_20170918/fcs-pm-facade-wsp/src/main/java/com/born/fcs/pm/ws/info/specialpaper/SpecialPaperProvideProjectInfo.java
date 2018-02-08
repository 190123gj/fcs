package com.born.fcs.pm.ws.info.specialpaper;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class SpecialPaperProvideProjectInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 643923707798223886L;

    private long id;

    private long receiveManId;

    private String receiveManName;

    private long provideManId;

    private String provideManName;

    private String projectCode;

    private String projectName;

    private String receiptPlace;

    private String receiptMan;

    private long pieces;

    private String profiles;

    private Date rawAddTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiveManId() {
        return receiveManId;
    }

    public void setReceiveManId(long receiveManId) {
        this.receiveManId = receiveManId;
    }

    public String getReceiveManName() {
        return receiveManName;
    }

    public void setReceiveManName(String receiveManName) {
        this.receiveManName = receiveManName;
    }

    public long getProvideManId() {
        return provideManId;
    }

    public void setProvideManId(long provideManId) {
        this.provideManId = provideManId;
    }

    public String getProvideManName() {
        return provideManName;
    }

    public void setProvideManName(String provideManName) {
        this.provideManName = provideManName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public long getPieces() {
        return pieces;
    }

    public void setPieces(long pieces) {
        this.pieces = pieces;
    }

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
