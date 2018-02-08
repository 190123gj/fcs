package com.born.fcs.pm.ws.service.counterguarantee;

import javax.jws.WebService;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeReleaseInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeRepayInfo;
import com.born.fcs.pm.ws.info.counterguarantee.GuaranteeApplyCounterInfo;
import com.born.fcs.pm.ws.info.counterguarantee.ReleaseApplyInfo;
import com.born.fcs.pm.ws.order.counterguarantee.CounterGuaranteeQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.FCounterGuaranteeReleaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 到期解保
 * 
 * @author lirz
 * 
 * 2016-5-12 下午6:33:14
 */
@WebService
public interface CounterGuaranteeService {
	/**
	 * 保存解保申请(新增/修改)
	 * 
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult save(FCounterGuaranteeReleaseOrder order);
	
	/**
	 * 
	 * 验证是否可申请解保
	 * 
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FcsBaseResult canRelease(FCounterGuaranteeReleaseOrder order);
	
	/**
	 * 根据表单id查找解保申请
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FCounterGuaranteeReleaseInfo findByFormId(long formId);
	
	/**
	 * 查询解保申请列表
	 * 
	 * @param queryOrder 查询条件
	 * @return
	 */
	QueryBaseBatchResult<ReleaseApplyInfo> queryList(CounterGuaranteeQueryOrder queryOrder);
	
	/**
	 * 查询项目已解保金额
	 * 
	 * @param projectCode 项目编号 
	 * @return 该项目已经解保的金额
	 */
	Money queryReleasedAmount(String projectCode);
	
	/**
	 * 查询项目解保中的金额
	 * 
	 * @param projectCode 项目编号 
	 * @return 该项目解保中的金额
	 */
	Money queryReleasingAmount(String projectCode);
	
	/**
	 * 查询可申请解保的项目列表
	 * 
	 * @param queryOrder 查询条件
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryProjects(QueryProjectBase queryOrder);
	
	/**
	 * 
	 * 查询审核不通过的反担保措施
	 * 
	 * @param formId
	 * @return
	 */
	QueryBaseBatchResult<GuaranteeApplyCounterInfo> queryCounts(long formId);
	
	/**
	 * 查询解保审核通过的还款列表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<FCounterGuaranteeRepayInfo> queryRepays(QueryProjectBase queryOrder);
}
