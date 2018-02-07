package com.born.fcs.face.integration.pm.service.common;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.SysDbBackupConfigInfo;
import com.born.fcs.pm.ws.info.common.SysDbBackupLogInfo;
import com.born.fcs.pm.ws.order.common.SysDbBackupConfigOrder;
import com.born.fcs.pm.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.fcs.pm.ws.order.common.SysDbBackupLogQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.SysDbBackupService;

@Service("sysDbBackupServiceClient")
public class SysDbBackupServiceClient extends ClientAutowiredBaseService implements
																		SysDbBackupService {
	
	@Override
	public FcsBaseResult saveConfig(final SysDbBackupConfigOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.saveConfig(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delConfig(final long configId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.delConfig(configId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<SysDbBackupConfigInfo> queryConfig(	final SysDbBackupConfigQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SysDbBackupConfigInfo>>() {
			
			@Override
			public QueryBaseBatchResult<SysDbBackupConfigInfo> call() {
				return sysDbBackupWebService.queryConfig(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delBackupFile(final long logId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.delBackupFile(logId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<SysDbBackupLogInfo> queryLog(final SysDbBackupLogQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SysDbBackupLogInfo>>() {
			
			@Override
			public QueryBaseBatchResult<SysDbBackupLogInfo> call() {
				return sysDbBackupWebService.queryLog(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult runBackupByConfig(final SysDbBackupConfigInfo config,
											final boolean isManual) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.runBackupByConfig(config, isManual);
			}
		});
	}
	
	@Override
	public FcsBaseResult runBackupByConfigId(final long configId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.runBackupByConfigId(configId);
			}
		});
	}
	
	@Override
	public FcsBaseResult runBackup() {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.runBackup();
			}
		});
	}
	
	@Override
	public FcsBaseResult changeInUse(final long configId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDbBackupWebService.changeInUse(configId);
			}
		});
	}
}
