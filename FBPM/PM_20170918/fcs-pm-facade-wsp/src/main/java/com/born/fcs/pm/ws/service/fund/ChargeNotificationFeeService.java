package com.born.fcs.pm.ws.service.fund;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 收费通知 - 收费明细
 *
 *
 * @author Fei
 *
 */
@WebService
public interface ChargeNotificationFeeService {
	
	/**
	 * 保存收费明细
	 * @param order
	 * @return
	 */
	FcsBaseResult saveChargeNotificationFee(FChargeNotificationFeeOrder order);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	FcsBaseResult destroyById(FChargeNotificationFeeOrder order);
	
	/**
	 * 根据ID查询收费明细
	 * @param formId 对应的表单ID
	 * @return 收费明细
	 */
	FChargeNotificationFeeInfo queryById(long id);
	
	/**
	 * 根据ID查询收费明细
	 * @param formId 对应的表单ID
	 * @return 收费明细
	 */
	List<FChargeNotificationFeeInfo> queryByNotificationId(long notificationId);
	
	/**
	 * 分页查询收费明细
	 * @param ChargeNotificationQueryOrder
	 * @return 收费明细List
	 */
	QueryBaseBatchResult<FChargeNotificationFeeInfo> queryChargeNotification(	FChargeNotificationFeeQueryOrder ChargeNotificationFeeQueryOrder);
	
}
