package com.born.fcs.face.integration.pm.service.council;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.order.council.CouncilTypeOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilTypeService;

/**
 * 
 * @author jil
 *
 */
@Service("councilTypeServiceClient")
public class CouncilTypeServiceClient extends ClientAutowiredBaseService implements
																		CouncilTypeService {
	
	@Override
	public CouncilTypeInfo findById(final long typeId) {
		return callInterface(new CallExternalInterface<CouncilTypeInfo>() {
			
			@Override
			public CouncilTypeInfo call() {
				return councilTypeService.findById(typeId);
			}
		});
	}
	
	@Override
	public CouncilTypeInfo findCouncilTypeByTypeName(final String typeName,final String typeCode) {
		return callInterface(new CallExternalInterface<CouncilTypeInfo>() {
			
			@Override
			public CouncilTypeInfo call() {
				return councilTypeService.findCouncilTypeByTypeName(typeName,typeCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final CouncilTypeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilTypeService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilTypeInfo> query(final CouncilTypeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilTypeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilTypeInfo> call() {
				return councilTypeService.query(order);
			}
		});
	}
	
	@Override
	public int deleteById(final long typeId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return councilTypeService.deleteById(typeId);
			}
		});
	}
	
	@Override
	public DecisionInstitutionInfo findDecisionInstitutionByCouncilType(final CouncilTypeOrder order) {
		return callInterface(new CallExternalInterface<DecisionInstitutionInfo>() {
			
			@Override
			public DecisionInstitutionInfo call() {
				return councilTypeService.findDecisionInstitutionByCouncilType(order);
			}
		});
	}
	
	@Override
	public List<CouncilJudgeInfo> findCouncilJudgesByCouncilType(final Long councilType) {
		return callInterface(new CallExternalInterface<List<CouncilJudgeInfo>>() {
			
			@Override
			public List<CouncilJudgeInfo> call() {
				return councilTypeService.findCouncilJudgesByCouncilType(councilType);
			}
		});
	}
	
}
