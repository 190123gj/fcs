package com.born.fcs.face.integration.am.service.pledgetype;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeSimpleInfo;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeOrder;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeQueryOrder;
import com.born.fcs.am.ws.service.pledgetype.PledgeTypeService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeTypeServiceClient")
public class PledgeTypeServiceClient extends ClientAutowiredBaseService implements
																		PledgeTypeService {
	
	@Override
	public PledgeTypeInfo findById(final long typeId) {
		return callInterface(new CallExternalInterface<PledgeTypeInfo>() {
			
			@Override
			public PledgeTypeInfo call() {
				return pledgeTypeWebService.findById(typeId);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final PledgeTypeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pledgeTypeWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeInfo> query(final PledgeTypeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeInfo> call() {
				return pledgeTypeWebService.query(order);
			}
		});
	}
	
	@Override
	public Boolean deleteCheck(final long typeId, final String level) {
		return callInterface(new CallExternalInterface<Boolean>() {
			
			@Override
			public Boolean call() {
				return pledgeTypeWebService.deleteCheck(typeId, level);
			}
		});
	}
	
	@Override
	public Boolean isSameNameCheck(final String levelOne, final boolean isAddlevelOne,
									final String levelTwo, final boolean isAddlevelTwo,
									final String levelThree, final boolean isAddlevelThree) {
		return callInterface(new CallExternalInterface<Boolean>() {
			
			@Override
			public Boolean call() {
				return pledgeTypeWebService.isSameNameCheck(levelOne, isAddlevelOne, levelTwo,
					isAddlevelTwo, levelThree, isAddlevelThree);
			}
		});
	}
	
	@Override
	public int deleteById(final long typeId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return pledgeTypeWebService.deleteById(typeId);
			}
		});
	}
	
	@Override
	public List<PledgeTypeInfo> findByLevelOneAndLevelTwo(final String levelOne,
															final String levelTwo) {
		return callInterface(new CallExternalInterface<List<PledgeTypeInfo>>() {
			
			@Override
			public List<PledgeTypeInfo> call() {
				return pledgeTypeWebService.findByLevelOneAndLevelTwo(levelOne, levelTwo);
			}
		});
	}
	
	@Override
	public PledgeTypeInfo findByLevelOneTwoThree(final String levelOne, final String levelTwo,
													final String levelThree) {
		return callInterface(new CallExternalInterface<PledgeTypeInfo>() {
			
			@Override
			public PledgeTypeInfo call() {
				return pledgeTypeWebService.findByLevelOneTwoThree(levelOne, levelTwo, levelThree);
			}
		});
	}
	
	@Override
	public List<PledgeTypeInfo> findByLevelOneAndLevelTwoForAssets(final String levelOne,
																	final String levelTwo) {
		return callInterface(new CallExternalInterface<List<PledgeTypeInfo>>() {
			
			@Override
			public List<PledgeTypeInfo> call() {
				return pledgeTypeWebService.findByLevelOneAndLevelTwoForAssets(levelOne, levelTwo);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<PledgeTypeSimpleInfo> queryAll() {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeSimpleInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeSimpleInfo> call() {
				return pledgeTypeWebService.queryAll();
			}
		});
	}
}
