package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationFeeDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeNotificationFeeService")
public class ChargeNotificationFeeServiceImpl extends BaseAutowiredDomainService implements
																				ChargeNotificationFeeService {
	
	@Override
	public FcsBaseResult saveChargeNotificationFee(final FChargeNotificationFeeOrder order) {
		
		return commonProcess(order, "保存收费明细", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					//保存
					FChargeNotificationFeeDO fChargeNotificationFeeDO = new FChargeNotificationFeeDO();
					BeanCopier.staticCopy(order, fChargeNotificationFeeDO);
					long notificationId = fChargeNotificationFeeDAO
						.insert(fChargeNotificationFeeDO);
					//收费基数 万元
					//					Money chargeBase = Money.amout(String.valueOf(NumberUtil.parseDouble(order
					//						.getChargeBase()) / 10000));
					//					fChargeNotificationFeeDO.setChargeBase(chargeBase);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(notificationId);
				} else {
					//更新
					FChargeNotificationFeeDO fChargeNotificationFeeDO = fChargeNotificationFeeDAO
						.findById(order.getId());
					if (null == fChargeNotificationFeeDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到收费明细");
					}
					
					BeanCopier.staticCopy(order, fChargeNotificationFeeDO);
					//收费基数 万元
					//					Money chargeBase = Money.amout(String.valueOf(NumberUtil.parseDouble(order
					//						.getChargeBase()) / 10000));
					//					fChargeNotificationFeeDO.setChargeBase(chargeBase);
					fChargeNotificationFeeDAO.update(fChargeNotificationFeeDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult destroyById(final FChargeNotificationFeeOrder order) {
		return commonProcess(order, "删除收费明细", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到收费明细");
				} else {
					fChargeNotificationFeeDAO.deleteById(order.getId());
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FChargeNotificationFeeInfo queryById(long id) {
		FChargeNotificationFeeDO fChargeNotificationFeeDO = fChargeNotificationFeeDAO.findById(id);
		if (fChargeNotificationFeeDO != null) {
			return toInfo(fChargeNotificationFeeDO);
		} else {
			return null;
		}
	}
	
	@Override
	public List<FChargeNotificationFeeInfo> queryByNotificationId(long notificationId) {
		List<FChargeNotificationFeeInfo> result = Lists.newArrayList();
		List<FChargeNotificationFeeDO> list = fChargeNotificationFeeDAO
			.findByNotificationId(notificationId);
		
		for (FChargeNotificationFeeDO feeDO : list) {
			result.add(toInfo(feeDO));
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<FChargeNotificationFeeInfo> queryChargeNotification(FChargeNotificationFeeQueryOrder ChargeNotificationFeeQueryOrder) {
		QueryBaseBatchResult<FChargeNotificationFeeInfo> batchResult = new QueryBaseBatchResult<FChargeNotificationFeeInfo>();
		try {
			FChargeNotificationFeeDO fChargeNotificationFeeDO = new FChargeNotificationFeeDO();
			BeanCopier.staticCopy(ChargeNotificationFeeQueryOrder, fChargeNotificationFeeDO);
			
			long totalCount = fChargeNotificationFeeDAO
				.findByConditionCount(fChargeNotificationFeeDO);
			PageComponent component = new PageComponent(ChargeNotificationFeeQueryOrder, totalCount);
			
			List<FChargeNotificationFeeDO> list = fChargeNotificationFeeDAO.findByCondition(
				fChargeNotificationFeeDO, component.getFirstRecord(), component.getPageSize());
			
			List<FChargeNotificationFeeInfo> pageList = new ArrayList<FChargeNotificationFeeInfo>(
				list.size());
			for (FChargeNotificationFeeDO item : list) {
				
				pageList.add(toInfo(item));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费明细失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	private FChargeNotificationFeeInfo toInfo(FChargeNotificationFeeDO fChargeNotificationFeeDO) {
		if (fChargeNotificationFeeDO != null) {
			FChargeNotificationFeeInfo fChargeNotificationFeeInfo = new FChargeNotificationFeeInfo();
			BeanCopier.staticCopy(fChargeNotificationFeeDO, fChargeNotificationFeeInfo);
			fChargeNotificationFeeInfo.setFeeType(FeeTypeEnum.getByCode(fChargeNotificationFeeDO
				.getFeeType()));
			return fChargeNotificationFeeInfo;
		} else {
			return null;
		}
		
	}
	
}
