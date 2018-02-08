package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 *
 * 已解保项目导入结果
 *
 * @author heh
 *
 */
public class FileImportResult extends FormBaseResult {
    private static final long serialVersionUID = -4574732741851632946L;

    private String projectCode;

    private String fileCode;

    private String no;


    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
