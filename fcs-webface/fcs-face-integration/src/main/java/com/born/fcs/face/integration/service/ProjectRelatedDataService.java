package com.born.fcs.face.integration.service;

import com.born.fcs.face.integration.result.ProjectCompResult;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

/**
 * 项目相关数据汇总
 *
 * @author wuzj
 */
public interface ProjectRelatedDataService {
	
	/**
	 * 项目代偿数据
	 * @param projectCode
	 * @return
	 */
	ProjectCompResult projectCompData(String projectCode);
	
	/**
	 * 项目列表导出数据
	 * @param order
	 * @return
	 */
	ReportDataResult projectListExportData(ProjectQueryOrder order);
}
