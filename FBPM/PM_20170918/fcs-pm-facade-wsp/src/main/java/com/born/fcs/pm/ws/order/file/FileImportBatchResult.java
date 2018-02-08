package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

import java.util.List;

/**
 *
 * 已解保项目导入结果
 *
 * @author heh
 *
 */
public class FileImportBatchResult extends FormBaseResult {
    private static final long serialVersionUID = -2481469523129138699L;

    List<FileImportResult> batchResult;

    public List<FileImportResult> getBatchResult() {
        return batchResult;
    }

    public void setBatchResult(List<FileImportResult> batchResult) {
        this.batchResult = batchResult;
    }
}
