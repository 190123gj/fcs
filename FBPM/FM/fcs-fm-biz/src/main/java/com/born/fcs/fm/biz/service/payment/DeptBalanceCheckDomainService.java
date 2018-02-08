package com.born.fcs.fm.biz.service.payment;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import com.yjf.common.service.base.ProcessInvokeService;

public class DeptBalanceCheckDomainService extends BaseFormAutowiredDomainService {
	
	private final static Semaphore semaphore= new Semaphore(1);
	
	/**
	 * 表单数据存储的通用处理
	 * 涉及到部门预算check的事务处理
	 * @param order
	 * @param processBizName
	 * @param beforeProcessInvokeService
	 * @param processInvokeService
	 * @param successProcessInvokeService
	 * @return
	 */
	protected FormBaseResult commonFormSaveProcess(	final FormOrderBase order,
													final String processBizName,
													final BeforeProcessInvokeService beforeProcessInvokeService,
													final ProcessInvokeService processInvokeService,
													final AfterProcessInvokeService successProcessInvokeService) {
		FormBaseResult result = new FormBaseResult();
		try {
			boolean isAcquire = semaphore.tryAcquire(10L, TimeUnit.SECONDS);
			if(isAcquire){
				result = super.commonFormSaveProcess(order, processBizName, beforeProcessInvokeService, 
					processInvokeService, successProcessInvokeService);
			}else{
				result.setFcsResultEnum(FcsResultEnum.CALL_REMOTE_SERVICE_TIME_OUT);
				result.setMessage("处理失败");
			}
		} catch (InterruptedException e) {
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAILURE);
			result.setMessage("处理失败");
		} finally{
			semaphore.release();
		}
		
		return result;
	}
}
