package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.base.QueryPageBase;


public class StampBasicDataQueryOrder extends QueryPageBase {
    private static final long serialVersionUID = -1147265668597756345L;

    private String fileName;

    private String remark;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
