package com.born.fcs.face.integration.rm.service.report;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.rm.ws.info.report.project.Project;
import com.born.fcs.rm.ws.info.report.project.ProjectCustomerInfo;
import com.born.fcs.rm.ws.order.report.project.BatchSaveOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectCustomerQueryOrder;
import com.born.fcs.rm.ws.service.report.inner.ProjectInfoService;

/**
 * 
 * 基层定期报表(客户信息与项目信息)
 *
 * @author lirz
 *
 * 2016-8-12 下午4:17:43
 */
@Service("projectInfoServiceClient")
public class ProjectInfoServiceClient extends ClientAutowiredBaseService implements ProjectInfoService {

	@Override
	public ProjectCustomerInfo queryCustomerInfo(final ProjectCustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<ProjectCustomerInfo>() {

            @Override
            public ProjectCustomerInfo call() {
                return projectInfoWebService.queryCustomerInfo(queryOrder);
            }
        });
	}

	@Override
	public Project queryProjectInfo(final ProjectBaseQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<Project>() {

            @Override
            public Project call() {
                return projectInfoWebService.queryProjectInfo(queryOrder);
            }
        });
	}

	@Override
	public FcsBaseResult saveBatch(final BatchSaveOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return projectInfoWebService.saveBatch(order);
            }
        });
	}
	
}
