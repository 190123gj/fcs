package com.born.fcs.pm.biz.service.common;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 签报完成后相关业务处理
 *
 * @author wuzj
 */
public interface FormChangeProcessService {
	
	/**
	 * 处理修改记录业务
	 * @param record
	 * @return
	 */
	FcsBaseResult processChange(FormChangeApplyInfo applyInfo, FormChangeRecordInfo record);
	
	/**
	 * 检查是否可以签报
	 * 
	 * success ： true 可以签报 false 不可以签报
	 * @param project
	 * @return
	 */
	FcsBaseResult checkCanChange(ProjectInfo project);
}
