package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.biz.service.common.SysClearCacheService;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormMessageTypeEnum;
import com.born.fcs.pm.ws.info.common.FormMessageTempleteInfo;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteOrder;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 表单通知模板
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FormMessageTempleteService extends SysClearCacheService {
	
	/**
	 * 分页查询表单通知模板
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormMessageTempleteInfo> queryPage(FormMessageTempleteQueryOrder order);
	
	/**
	 * 根据表单和类型查询模板
	 * @param formCode
	 * @param type
	 * @return
	 */
	FormMessageTempleteInfo findByFormCodeAndType(FormCodeEnum formCode, FormMessageTypeEnum type);
	
	/**
	 * 根据模板ID查询
	 * @param tempelteId
	 * @return
	 */
	FormMessageTempleteInfo findByTempleteId(long templeteId);
	
	/**
	 * 保存模板
	 * @param order
	 * @return
	 */
	FcsBaseResult saveTemplete(FormMessageTempleteOrder order);
	
	/**
	 * 根据模板ID删除
	 * @param templeteId
	 * @return
	 */
	FcsBaseResult deleteByTempleteId(long templeteId);
	
}
