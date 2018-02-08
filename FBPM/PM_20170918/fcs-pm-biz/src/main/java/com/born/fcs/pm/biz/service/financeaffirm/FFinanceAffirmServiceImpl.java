package com.born.fcs.pm.biz.service.financeaffirm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ChargeNoticeCapitalApproproationDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import com.born.fcs.pm.dataobject.ProjectChargePayDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeNoticeCapitalApproproationOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmDetailOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("fFinanceAffirmService")
public class FFinanceAffirmServiceImpl extends BaseFormAutowiredDomainService implements
																				FinanceAffirmService {
	
	@Override
	public FcsBaseResult save(final FFinanceAffirmOrder order) {
		return commonProcess(order, "财务确认信息-收费或资金划付", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				
				final Date now = FcsPmDomainHolder.get().getSysDate();
				FFinanceAffirmDO affirmDO = null;
				if (order.getFormId() != null && order.getFormId() > 0) {
					affirmDO = fFinanceAffirmDAO.findByFormId(order.getFormId());
				}
				if (affirmDO == null) {
					//保存
					affirmDO = new FFinanceAffirmDO();
					BeanCopier.staticCopy(order, affirmDO);
					affirmDO.setFormId(order.getFormId());
					affirmDO.setRawAddTime(now);
					long id = fFinanceAffirmDAO.insert(affirmDO);//主表id
					List<FFinanceAffirmDetailOrder> listDetailOrder = order.getDetailOrders();
					if (listDetailOrder != null && listDetailOrder.size() > 0) {
						for (FFinanceAffirmDetailOrder detailOrder : listDetailOrder) {
							FFinanceAffirmDetailDO detailDO = new FFinanceAffirmDetailDO();
							BeanCopier.staticCopy(detailOrder, detailDO);
							detailDO.setRawAddTime(now);
							detailDO.setAffirmId(id);
							fFinanceAffirmDetailDAO.insert(detailDO);
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					//更新
					BeanCopier.staticCopy(order, affirmDO);
					
					fFinanceAffirmDAO.update(affirmDO);//更新主表信息
					
					fFinanceAffirmDetailDAO.deleteByAffirmId(affirmDO.getAffirmId());//删除明细，再新增
					
					List<FFinanceAffirmDetailOrder> listDetailOrder = order.getDetailOrders();
					if (listDetailOrder != null && listDetailOrder.size() > 0) {
						for (FFinanceAffirmDetailOrder detailOrder : listDetailOrder) {
							FFinanceAffirmDetailDO detailDO = new FFinanceAffirmDetailDO();
							BeanCopier.staticCopy(detailOrder, detailDO);
							detailDO.setRawAddTime(now);
							fFinanceAffirmDetailDAO.insert(detailDO);
						}
					}
					
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetail(	ProjectChargePayQueryOrder order) {
		
		QueryBaseBatchResult<ProjectChargePayInfo> baseBatchResult = new QueryBaseBatchResult<ProjectChargePayInfo>();
		try {
			
			Map<String, Object> paramMap = MiscUtil.covertPoToMap(order);
			long totalSize = busiDAO.queryProjectChargePayDetailCount(paramMap);
			
			PageComponent component = new PageComponent(order, totalSize);
			List<ProjectChargePayDO> pageList = busiDAO.queryProjectChargePayDetail(paramMap);
			
			List<ProjectChargePayInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectChargePayDO detail : pageList) {
					ProjectChargePayInfo info = new ProjectChargePayInfo();
					BeanCopier.staticCopy(detail, info);
					info.setCustomerType(CustomerTypeEnum.getByCode(detail.getCustomerType()));
					info.setPeriodUnit(TimeUnitEnum.getByCode(detail.getPeriodUnit()));
					if ("CHARGE_NOTIFICATION".equals(detail.getAffirmFormType())) {
						info.setChargeType(FeeTypeEnum.getByCode(detail.getFeeType()));
					} else {
						info.setPayType(PaymentMenthodEnum.getByCode(detail.getFeeType()));
					}
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			logger.error("查询项目收费/划付明细出错：{}", e);
		}
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetailChoose(ProjectChargePayQueryOrder order) {
		
		QueryBaseBatchResult<ProjectChargePayInfo> baseBatchResult = new QueryBaseBatchResult<ProjectChargePayInfo>();
		try {
			
			Map<String, Object> paramMap = MiscUtil.covertPoToMap(order);
			long totalSize = busiDAO.queryProjectChargePayDetailChooseCount(paramMap);
			
			PageComponent component = new PageComponent(order, totalSize);
			List<ProjectChargePayDO> pageList = busiDAO.queryProjectChargePayDetailChoose(paramMap);
			
			List<ProjectChargePayInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectChargePayDO detail : pageList) {
					ProjectChargePayInfo info = new ProjectChargePayInfo();
					BeanCopier.staticCopy(detail, info);
					info.setCustomerType(CustomerTypeEnum.getByCode(detail.getCustomerType()));
					info.setPeriodUnit(TimeUnitEnum.getByCode(detail.getPeriodUnit()));
					if ("CHARGE_NOTIFICATION".equals(detail.getAffirmFormType())) {
						info.setChargeType(FeeTypeEnum.getByCode(detail.getFeeType()));
					} else {
						info.setPayType(PaymentMenthodEnum.getByCode(detail.getFeeType()));
					}
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			logger.error("查询项目收费/划付明细出错：{}", e);
		}
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult saveChargeCapital(final ChargeCapitalOrder order) {
		return commonProcess(order, "收费资金划付相互关联", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//先删除在更新
				//更新
				Long detailId = order.getDetailId();
				String type = order.getType().code();
				chargeNoticeCapitalApproproationDAO.deleteByTypeAndDetailId(type, detailId);
				if (ListUtil.isNotEmpty(order.getItemOrder())) {
					for (ChargeNoticeCapitalApproproationOrder itemOrder : order.getItemOrder()) {
						ChargeNoticeCapitalApproproationDO DO = new ChargeNoticeCapitalApproproationDO();
						BeanCopier.staticCopy(order, DO);
						DO.setPayId(itemOrder.getPayId());
						DO.setUseAmount(itemOrder.getUseAmount());
						DO.setDetailId(detailId);
						DO.setType(type);
						DO.setRawAddTime(now);
						chargeNoticeCapitalApproproationDAO.insert(DO);
					}
				}
				return null;
			}
		}, null, null);
	}
}
