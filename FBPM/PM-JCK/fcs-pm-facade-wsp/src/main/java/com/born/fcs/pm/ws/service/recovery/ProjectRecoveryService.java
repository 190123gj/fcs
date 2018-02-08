/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:33:59 创建
 */
package com.born.fcs.pm.ws.service.recovery;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforePreservationPrecautionOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforeTrailOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;

/**
 * 
 * 追偿服务
 * @author hjiajie
 * 
 */
@WebService
public interface ProjectRecoveryService {
	
	/** 保存 */
	FcsBaseResult save(ProjectRecoveryOrder order);
	
	/** 单个详情查询 */
	ProjectRecoveryResult findById(Long recoveryId);
	
	/** 单个详情查询 */
	ProjectRecoveryResult findByProjectCode(String projectCode);
	
	/** 列表查询 */
	QueryBaseBatchResult<ProjectRecoveryListInfo> queryRecovery(ProjectRecoveryQueryOrder projectRecoveryQueryOrder);
	
	/** 追偿关闭 **/
	FcsBaseResult closeProjectRecovery(ProjectRecoveryOrder order);
	
	/** 确认追偿关闭 **/
	FcsBaseResult closeProjectRecoverySure(ProjectRecoveryOrder order);
	
	/** 抓取函件 */
	ProjectRecoveryResult queryNoticeLetter(Long recoveryId);
	
	/** 保存函件 */
	ProjectRecoveryResult saveNoticeLetter(ProjectRecoveryOrder order);
	
	/** 关闭开庭通知 */
	FcsBaseResult changeBeforeTrailNotice(ProjectRecoveryLitigationBeforeTrailOrder order);
	
	/** 关闭保全期限通知 */
	FcsBaseResult changePreservationPrecautionNotice(	ProjectRecoveryLitigationBeforePreservationPrecautionOrder order);
	
}
