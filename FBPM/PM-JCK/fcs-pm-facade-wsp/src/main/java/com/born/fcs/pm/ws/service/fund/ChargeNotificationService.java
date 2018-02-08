package com.born.fcs.pm.ws.service.fund;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;

/**
 * 收费通知
 *
 *
 * @author Fei
 *
 */
@WebService
public interface ChargeNotificationService {
	
	/**
	 * 保存收费通知报告
	 * @param order
	 * @return
	 */
	FormBaseResult saveChargeNotification(FChargeNotificationOrder order);
	
	/**
	 * 根据ID查询收费通知报告
	 * @param formId 对应的表单ID
	 * @return 收费通知报告
	 */
	FChargeNotificationInfo queryChargeNotificationById(long notificationId);
	
	/**
	 * 根据ID查询收费通知报告
	 * @param formId 对应的表单ID
	 * @return 收费通知报告
	 */
	FcsBaseResult destroyChargeNotificationById(FChargeNotificationOrder order);
	
	/**
	 * 根据表单ID查询收费通知报告
	 * @param formId 对应的表单ID
	 * @return 收费通知报告
	 */
	FChargeNotificationInfo queryChargeNotificationByFormId(long formId);
	
	/**
	 * 根据项目编号收费通知报告
	 * @param projectCode 对应项目编号
	 * @return 收费通知报告
	 */
	List<FChargeNotificationInfo> queryChargeNotificationByProjectCode(String projectCode);
	
	/**
	 * 分页查询收费通知报告
	 * @param ChargeNotificationQueryOrder
	 * @return 收费通知报告List
	 */
	QueryBaseBatchResult<FChargeNotificationInfo> queryChargeNotification(	FChargeNotificationQueryOrder chargeNotificationQueryOrder);

	/**
	 * 分页查询收费通知报告
	 * @param chargeNotificationQueryOrder
	 * @return 收费通知报告List
	 */
	QueryBaseBatchResult<FChargeNotificationResultInfo> queryChargeNotificationList(FChargeNotificationQueryOrder chargeNotificationQueryOrder);

	/**
	 * 查询审核中的单子
	 * @param projectCode
	 * @return 收费通知报告List
	 */
	public List<FChargeNotificationInfo> queryAuditingByProjectCode(String projectCode);
	
	/**
	 * 
	 * 查询项目某一项收费总额
	 * 
	 * @param projectCode
	 * @param chargeType
	 * @return 收费默认返回在结果集中的other字段中
	 */
	RefundApplicationResult queryChargeTotalAmount(String projectCode, FeeTypeEnum feeType);
}
