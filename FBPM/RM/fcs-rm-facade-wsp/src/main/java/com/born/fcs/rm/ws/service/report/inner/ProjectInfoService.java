package com.born.fcs.rm.ws.service.report.inner;

import javax.jws.WebService;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.rm.ws.info.report.project.Project;
import com.born.fcs.rm.ws.info.report.project.ProjectCustomerInfo;
import com.born.fcs.rm.ws.order.report.project.BatchSaveOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectCustomerQueryOrder;

/**
 * 
 * 基层定期报表(客户信息，项目信息)
 * 
 * @author lirz
 *
 * 2016-8-9 下午5:54:59
 */
@WebService
public interface ProjectInfoService {
	
	/**
	 * 查询客户信息
	 * 
	 * @param queryOrder
	 * @return
	 */
	ProjectCustomerInfo queryCustomerInfo(ProjectCustomerQueryOrder queryOrder);
	
	/**
	 * 查询项目信息
	 * 
	 * @param queryOrder
	 * @return
	 */
	Project queryProjectInfo(ProjectBaseQueryOrder queryOrder);
	
	/**
	 * 批量保存
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult saveBatch(BatchSaveOrder order);
}
