package com.born.fcs.face.integration.pm.service.common;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;

@Service("dateSeqWebServiceClient")
public class DateSeqWebServiceClient extends ClientAutowiredBaseService implements DateSeqService {
	
	@Override
	public String getNextDateSeq(final String seqName, final String prefix, final int length) {
		
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextDateSeq(seqName, prefix, length);
			}
		});
	}
	
	@Override
	public String getNextYearSeq(final String seqName, final String prefix, final int length) {
		
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextYearSeq(seqName, prefix, length);
			}
		});
	}
	
	@Override
	public String getNextCouncilCodeSeq() {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextCouncilCodeSeq();
			}
		});
	}
	
	@Override
	public long getNextSeqNumber(final String seqName) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return dateSeqWebService.getNextSeqNumber(seqName);
			}
		});
	}
	
	@Override
	public long getNextSeqNumberWithCache(final String seqName, final long cacheNumber) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return dateSeqWebService.getNextSeqNumberWithCache(seqName, cacheNumber);
			}
		});
	}
	
	@Override
	public long getNextSeqNumberWithoutCache(final String seqName,
												final boolean transactionRequireNew) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return dateSeqWebService.getNextSeqNumberWithoutCache(seqName,
					transactionRequireNew);
			}
		});
	}
	
	@Override
	public String getAvailableCouncilCodeSeq() {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getAvailableCouncilCodeSeq();
			}
		});
	}
	
	@Override
	public String getNextYearMonthSeq(final String seqName, final String prefix, final int length) {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextYearMonthSeq(seqName, prefix, length);
			}
		});
	}
	
	@Override
	public String getNextAfterwardsCheckSeq(final String projectCode) {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextAfterwardsCheckSeq(projectCode);
			}
		});
	}
	
	@Override
	public String getNextCouncilCodeSeqByParam(final CompanyNameEnum company,
												final CouncilTypeEnum councilType) {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getNextCouncilCodeSeqByParam(company, councilType);
			}
		});
	}
	
	@Override
	public String getAvailableCouncilCodeSeqByParam(final CompanyNameEnum company,
													final CouncilTypeEnum councilType) {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return dateSeqWebService.getAvailableCouncilCodeSeqByParam(company, councilType);
			}
		});
	}
}
