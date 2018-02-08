package com.born.fcs.pm.biz.service.project.asyn;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public interface ProjectDataAsynchronousService {
	/**
	 * 按项目编码 生成异步数据
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult makeProjectData(String projectCode);
	
	/**
	 * 每天晚上批量处理数据
	 * @return
	 */
	FcsBaseResult makeProjectDataByDay();
	
}
