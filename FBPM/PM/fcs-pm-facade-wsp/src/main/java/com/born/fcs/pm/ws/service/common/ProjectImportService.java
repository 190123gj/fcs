package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.excel.OldProjectExcelInfo;
import com.born.fcs.pm.ws.result.common.ProjectImportBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportResult;

/**
 * 已解保项目导入
 * 
 * @author wuzj
 */
@WebService
public interface ProjectImportService {
	
	/**
	 * 批量导入
	 * @param oldInfos
	 * @return
	 */
	ProjectImportBatchResult importBatch(List<OldProjectExcelInfo> oldInfos);
	
	/***
	 * 单个导入
	 * @param oldInfos
	 * @return
	 */
	ProjectImportResult importOne(OldProjectExcelInfo oldInfos);
	
	/**
	 * 批量重新导入
	 * @param oldInfos
	 * @return
	 */
	ProjectImportBatchResult reImportBatch(List<OldProjectExcelInfo> oldInfos);
	
	/**
	 * 单个重新导入/主要更改项目日期
	 * @param oldInfo
	 * @return
	 */
	ProjectImportResult reImportOne(OldProjectExcelInfo oldInfo);
}
