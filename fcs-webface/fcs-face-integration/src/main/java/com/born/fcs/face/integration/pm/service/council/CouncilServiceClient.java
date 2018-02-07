package com.born.fcs.face.integration.pm.service.council;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.order.council.CouncilDelOrder;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilService;

@Service("councilServiceClient")
public class CouncilServiceClient extends ClientAutowiredBaseService implements CouncilService {
	
	@Override
	public FcsBaseResult saveCouncil(final CouncilOrder councilOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.saveCouncil(councilOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilInfo> queryCouncil(final CouncilQueryOrder councilQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilInfo> call() {
				return councilWebService.queryCouncil(councilQueryOrder);
			}
		});
	}
	
	@Override
	public CouncilInfo queryCouncilById(final long councilId) {
		return callInterface(new CallExternalInterface<CouncilInfo>() {
			
			@Override
			public CouncilInfo call() {
				return councilWebService.queryCouncilById(councilId);
			}
		});
	}
	
	@Override
	public FcsBaseResult endCouncil(final long councilId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.endCouncil(councilId);
			}
		});
	}
	
	@Override
	public FcsBaseResult beginCouncil(final long councilId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.endCouncil(councilId);
			}
		});
	}
	
	public Date getSysDate() {
		return callInterface(new CallExternalInterface<Date>() {
			
			@Override
			public Date call() {
				return councilWebService.getSysDate();
			}
		});
	}
	
	@Override
	public FcsBaseResult delCouncilByCouncilId(final Long councilId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.delCouncilByCouncilId(councilId);
			}
		});
	}
	
	@Override
	public FcsBaseResult delCouncil(final CouncilDelOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.delCouncil(order);
			}
		});
	}
	
	@Override
	public Long queryCouncilCount(final CouncilQueryOrder councilQueryOrder) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return councilWebService.queryCouncilCount(councilQueryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult endOldCouncil(final CouncilOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilWebService.endOldCouncil(order);
			}
		});
	}
}
