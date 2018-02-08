package com.born.fcs.pm.ws.service.fund;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 退费申请service
 *
 * @author Ji
 */
@WebService
public interface RefundApplicationService {
	
	/**
	 * 根据ID查询退费申请信息
	 * @param id
	 * @return
	 */
	FRefundApplicationInfo findById(long id);
	
	/**
	 * 根据表单ID查询退费申请信息
	 * @param id
	 * @return
	 */
	FRefundApplicationInfo findRefundApplicationByFormId(long formId);
	
	/**
	 * 分页查询退费申请信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FRefundApplicationInfo> query(FRefundApplicationQueryOrder order);
	
	/**
	 * 根据项目编号查询所有的退费申请信息
	 * @param projectCode
	 * @return
	 */
	List<FRefundApplicationInfo> findByProjectCode(String projectCode);
	
	/**
	 * 保存退费申请 表单数据
	 * @param order
	 * @return
	 */
	FormBaseResult saveRefundApplication(FRefundApplicationOrder order);
	
	/**
	 * 根据申请单id查询所有的退费信息
	 * @param projectCode
	 * @return
	 */
	List<FRefundApplicationFeeInfo> findByRefundId(long refundId);
	
	/**
	 * 根据收费通知查询退费金额 所需的金额限制
	 * @param projectCode
	 * @param refundId
	 * @param isEdit
	 * @return
	 */
	RefundApplicationResult findAmountByChargeNotification(String projectCode, long refundId,
															Boolean isEdit);
	
	/**
	 * 新增时分页查询 可以 退费的项目(根据收费项目)
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryRefundApplicationByCharge(ProjectQueryOrder order);
	
	/**
	 * 根据项目编号查询所有的退费申请信息(审核通过)
	 * @param projectCode
	 * @return
	 */
	Money findRefundApplicationByProjectCode(String projectCode);
	
	/**
	 * 同步退费预测数据
	 * @param formId
	 * @param afterAudit
	 * @return
	 */
	FcsBaseResult syncForecast(long formId, boolean afterAudit);
}
