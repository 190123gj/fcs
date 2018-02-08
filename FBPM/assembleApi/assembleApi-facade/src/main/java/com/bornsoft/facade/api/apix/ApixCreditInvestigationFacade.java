package com.bornsoft.facade.api.apix;

import javax.jws.WebService;

import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixLocationBasedOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixLocationBasedResult;
import com.bornsoft.pub.result.apix.ApixValidateBankCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult;


/**
 * @Description:  apix 征信数据查询【请通过code来判断征信接口是否调用成功】
 * @author taibai@yiji.com
 * @date 2016-8-29 上午9:34:19
 * @version V1.0
 */
@WebService
public interface ApixCreditInvestigationFacade {
	/**
	 * 失信数据查询【请通过code来判断征信接口校验结果】
	 * @param order
	 * @return
	 */
	ApixDishonestQueryResult dishonestQuery(ApixDishonestQueryOrder order);
	
	
	/**
	 * 基站定位
	 * @param order
	 * @return
	 */
	ApixLocationBasedResult locateByBaseStation(ApixLocationBasedOrder order );
	
	/**
	 * 银行卡实名校验【请通过code来判断征信接口校验结果】
	 * @param order
	 * @return
	 */
	ApixValidateBankCardResult validateBankCard(ApixValidateBankCardOrder order); 
	
	/**
	 * 身份证实名校验【请通过code来判断征信接口校验结果】
	 * @param order
	 * @return
	 */
	ApixValidateIdCardResult validateIdCard(ApixValidateIdCardOrder order);
	
	/**
	 * 手机号实名校验【请通过code来判断征信接口校验结果】
	 * @param order
	 * @return
	 */
	ApixValidateMobileResult validateMobile(ApixValidateMobileOrder order);
}
