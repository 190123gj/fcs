package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.SysDbBackupConfigInfo;
import com.born.fcs.pm.ws.info.common.SysDbBackupLogInfo;
import com.born.fcs.pm.ws.order.common.SysDbBackupConfigOrder;
import com.born.fcs.pm.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.fcs.pm.ws.order.common.SysDbBackupLogQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@WebService
public interface SysDbBackupService {
	
	/**
	 * 保存备份配置信息
	 * @param order
	 * @return
	 */
	FcsBaseResult saveConfig(SysDbBackupConfigOrder order);
	
	/**
	 * 删除配置
	 * @param configId
	 * @return
	 */
	FcsBaseResult delConfig(long configId);
	
	/**
	 * 更新状态
	 * @param configId
	 * @param inUse
	 * @return
	 */
	FcsBaseResult changeInUse(long configId);
	
	/**
	 * 查询备份日志
	 * @return
	 */
	QueryBaseBatchResult<SysDbBackupConfigInfo> queryConfig(SysDbBackupConfigQueryOrder order);
	
	/**
	 * 根据备份日志删除备份文件
	 * @param logId
	 * @return
	 */
	FcsBaseResult delBackupFile(long logId);
	
	/**
	 * 查询备份日志
	 * @return
	 */
	QueryBaseBatchResult<SysDbBackupLogInfo> queryLog(SysDbBackupLogQueryOrder order);
	
	/**
	 * 执行备份
	 * @return
	 */
	FcsBaseResult runBackupByConfig(SysDbBackupConfigInfo config);
	
	/**
	 * 执行备份
	 * @return
	 */
	FcsBaseResult runBackup();
}
