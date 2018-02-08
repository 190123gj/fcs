package com.born.fcs.pm.ws.result.common;

import java.util.List;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 项目批量导入结果
 *
 * @author wuzj
 *
 */
public class ProjectImportBatchResult extends FormBaseResult {
	
	private static final long serialVersionUID = 275258159189110562L;
	
	List<ProjectImportResult> batchResult;
	
	public List<ProjectImportResult> getBatchResult() {
		return this.batchResult;
	}
	
	public void setBatchResult(List<ProjectImportResult> batchResult) {
		this.batchResult = batchResult;
	}
}
