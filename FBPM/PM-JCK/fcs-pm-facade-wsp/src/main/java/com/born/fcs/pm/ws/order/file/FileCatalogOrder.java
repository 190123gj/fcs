package com.born.fcs.pm.ws.order.file;



import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.List;

public class FileCatalogOrder extends ProcessOrder{
    private FileTypeEnum type;
    private List<FileListOrder> fileListOrder;

    private String checkStatus;

    private FileTypeEnum currType;

    private String submitStatus;

    private String doing;

    public String getDoing() {
        return doing;
    }

    public void setDoing(String doing) {
        this.doing = doing;
    }

    public FileTypeEnum getCurrType() {
        return currType;
    }

    public void setCurrType(FileTypeEnum currType) {
        this.currType = currType;
    }

    public FileTypeEnum getType() {
        return type;
    }

    public void setType(FileTypeEnum type) {
        this.type = type;
    }

    public List<FileListOrder> getFileListOrder() {
        return fileListOrder;
    }

    public void setFileListOrder(List<FileListOrder> fileListOrder) {
        this.fileListOrder = fileListOrder;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }
}
