package com.born.fcs.face.integration.pm.service.risklevel;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.CollectionInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelScoreTemplateInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.FRiskLevelOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.risklevel.RiskLevelService;

/**
 * 
 * 风险等级评级
 * 
 * 
 * @author lirz
 * 
 * 2016-5-19 下午3:26:09
 */
@Service("riskLevelServiceClient")
public class RiskLevelServiceClient extends ClientAutowiredBaseService implements RiskLevelService {
	
	@Override
	public FormBaseResult save(final FRiskLevelOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return riskLevelWebService.save(order);
			}
		});
	}
	
	@Override
	public FRiskLevelInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FRiskLevelInfo>() {
			
			@Override
			public FRiskLevelInfo call() throws SocketTimeoutException {
				return riskLevelWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<RiskLevelInfo> queryList(final RiskLevelQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskLevelInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RiskLevelInfo> call() throws SocketTimeoutException {
				return riskLevelWebService.queryList(queryOrder);
			}
		});
	}

	@Override
	public List<FRiskLevelScoreTemplateInfo> queryTemplates() {
		return callInterface(new CallExternalInterface<List<FRiskLevelScoreTemplateInfo>>() {
			
			@Override
			public List<FRiskLevelScoreTemplateInfo> call() throws SocketTimeoutException {
				return riskLevelWebService.queryTemplates();
			}
		});
	}

	@Override
	public CollectionInfo queryMultiple(final String projectCode) {
		return callInterface(new CallExternalInterface<CollectionInfo>() {
		
		@Override
		public CollectionInfo call() throws SocketTimeoutException {
			return riskLevelWebService.queryMultiple(projectCode);
		}
	});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(final ProjectQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return riskLevelWebService.queryProjects(queryOrder);
			}
		});
	}

}
