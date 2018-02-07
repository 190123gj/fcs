package com.born.fcs.face.integration.pm.service.contract;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.contract.DbFieldInfo;
import com.born.fcs.pm.ws.info.contract.DbFieldListInfo;
import com.born.fcs.pm.ws.order.contract.DbFieldOrder;
import com.born.fcs.pm.ws.order.contract.DbFieldQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.contract.DbFieldService;

/**
 * 
 * @author heh
 * 
 */
@Service("dbFieldServiceClient")
public class DbFieldServiceClient extends ClientAutowiredBaseService implements DbFieldService {
	
	@Override
	public DbFieldInfo findById(final long id) {
		return callInterface(new CallExternalInterface<DbFieldInfo>() {
			
			@Override
			public DbFieldInfo call() {
				return dbFieldWebService.findById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final DbFieldOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return dbFieldWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<DbFieldListInfo> query(final DbFieldQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<DbFieldListInfo>>() {
			
			@Override
			public QueryBaseBatchResult<DbFieldListInfo> call() {
				return dbFieldWebService.query(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteById(final DbFieldOrder order) {
		
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return dbFieldWebService.deleteById(order);
				
			}
		});
	}
	
	@Override
	public long findByFieldName(final long tableId, final String fieldName) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return dbFieldWebService.findByFieldName(tableId, fieldName);
				
			}
		});
	}
	
	@Override
	public long findByFieldShortName(final long tableId, final String shortForName) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return dbFieldWebService.findByFieldShortName(tableId, shortForName);
				
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<DbFieldInfo> queryByTableId(final DbFieldQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<DbFieldInfo>>() {
			@Override
			public QueryBaseBatchResult<DbFieldInfo> call() {
				return dbFieldWebService.queryByTableId(order);
				
			}
		});
	}
	
}
