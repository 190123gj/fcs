package com.born.fcs.pm.biz.service.investigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FInvestigationAssetReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.AssetReviewStatusEnum;
import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationAssetReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetRemarkOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.investigation.AssetReviewService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 尽调-资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午5:48:46
 * 
 */
@Service("assetReviewService")
public class AssetReviewServiceImpl extends BaseAutowiredDomainService implements
																		AssetReviewService {
	
	@Override
	public FcsBaseResult save(final FInvestigationAssetReviewOrder order) {
		return commonProcess(order, "保存资产复评", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FInvestigationAssetReviewDO doObj = FInvestigationAssetReviewDAO.findByFormId(order
					.getFormId());
				if (null == doObj) {
					doObj = new FInvestigationAssetReviewDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setRawAddTime(getSysdate());
					FInvestigationAssetReviewDAO.insert(doObj);
				} else {
					BeanCopier.staticCopy(order, doObj);
					FInvestigationAssetReviewDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult submit(final UpdateInvestigationAssetReviewOrder order) {
		return commonProcess(order, "提交资产复评", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FInvestigationAssetReviewDO doObj = FInvestigationAssetReviewDAO.findById(order
					.getId());
				if (null == doObj) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "找不到复评信息");
				}
				
				List<FInvestigationCreditSchemePledgeAssetDO> list = FInvestigationCreditSchemePledgeAssetDAO
					.findByFormId(doObj.getFormId());
				if (ListUtil.isNotEmpty(list)) {
					for (FInvestigationCreditSchemePledgeAssetDO asset : list) {
						if (StringUtil.isBlank(asset.getRemark())) {
							FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get()
								.getAttribute("result");
							result.setKeyId(asset.getId());
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "复评意见不完整");
						}
					}
				}
				
				BeanCopier.staticCopy(order, doObj);
				doObj.setStatus(AssetReviewStatusEnum.REVIEWED.code());
				doObj.setReviewTime(getSysdate());
				FInvestigationAssetReviewDAO.update(doObj);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationAssetReviewInfo findById(long id) {
		FInvestigationAssetReviewDO assetReview = FInvestigationAssetReviewDAO.findById(id);
		if (null == assetReview) {
			return null;
		}
		
		FInvestigationAssetReviewInfo info = new FInvestigationAssetReviewInfo();
		BeanCopier.staticCopy(assetReview, info);
		info.setStatus(AssetReviewStatusEnum.getByCode(assetReview.getStatus()));
		
		List<FInvestigationCreditSchemePledgeAssetInfo> pledges = new ArrayList<>();
		List<FInvestigationCreditSchemePledgeAssetInfo> mortgages = new ArrayList<>();
		List<FInvestigationCreditSchemePledgeAssetDO> list = FInvestigationCreditSchemePledgeAssetDAO
			.findByFormId(assetReview.getFormId());
		Money pledgeAssessPrice = new Money(0L); //抵押评估价格
		Money pledgePrice = new Money(0L); //抵押价格
		Money mortgageAssessPrice = new Money(0L); //质押评估价格
		Money mortgagePrice = new Money(0L); //质押价格
		if (ListUtil.isNotEmpty(list)) {
			for (FInvestigationCreditSchemePledgeAssetDO doObj : list) {
				GuaranteeTypeEnum type = GuaranteeTypeEnum.getByCode(doObj.getType());
				FInvestigationCreditSchemePledgeAssetInfo assetInfo = new FInvestigationCreditSchemePledgeAssetInfo();
				BeanCopier.staticCopy(doObj, assetInfo);
				assetInfo.setType(type);
				
				if (type == GuaranteeTypeEnum.PLEDGE) {
					pledges.add(assetInfo);
					pledgeAssessPrice.addTo(assetInfo.getEvaluationPrice());
					pledgePrice.addTo(
						assetInfo.getEvaluationPrice().multiply(assetInfo.getPledgeRate() / 100))
						.subtractFrom(assetInfo.getDebtedAmount());
				} else if (type == GuaranteeTypeEnum.MORTGAGE) {
					mortgages.add(assetInfo);
					mortgageAssessPrice.addTo(assetInfo.getEvaluationPrice());
					mortgagePrice.addTo(
						assetInfo.getEvaluationPrice().multiply(assetInfo.getPledgeRate() / 100))
						.subtractFrom(assetInfo.getDebtedAmount());
				}
			}
		}
		
		info.setPledges(pledges);
		info.setMortgages(mortgages);
		info.setPledgeAssessPrice(pledgeAssessPrice);
		info.setPledgePrice(pledgePrice);
		info.setMortgageAssessPrice(mortgageAssessPrice);
		info.setMortgagePrice(mortgagePrice);
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<FInvestigationAssetReviewInfo> queryList(	FInvestigationAssetReviewQueryOrder queryOrder) {
		QueryBaseBatchResult<FInvestigationAssetReviewInfo> baseBatchResult = new QueryBaseBatchResult<>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("status", queryOrder.getStatus());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
		long totalSize = busiDAO.searchAssetReviewCount(paramMap);
		
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());			
			List<FInvestigationAssetReviewDO> pageList = busiDAO.searchAssetReview(paramMap);
			
			List<FInvestigationAssetReviewInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (FInvestigationAssetReviewDO sf : pageList) {
					FInvestigationAssetReviewInfo info = new FInvestigationAssetReviewInfo();
					BeanCopier.staticCopy(sf, info);
					info.setStatus(AssetReviewStatusEnum.getByCode(sf.getStatus()));
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult saveRemark(final UpdateInvestigationCreditSchemePledgeAssetRemarkOrder order) {
		return commonProcess(order, "保存资产复评意见", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FInvestigationCreditSchemePledgeAssetDO doObj = FInvestigationCreditSchemePledgeAssetDAO
					.findById(order.getId());
				if (null == doObj) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "找不到资产信息");
				}
				
				BeanCopier.staticCopy(order, doObj);
				FInvestigationCreditSchemePledgeAssetDAO.update(doObj);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationCreditSchemePledgeAssetInfo findAssetInfoById(long id) {
		FInvestigationCreditSchemePledgeAssetDO doObj = FInvestigationCreditSchemePledgeAssetDAO
			.findById(id);
		if (null == doObj) {
			return null;
		}
		
		FInvestigationCreditSchemePledgeAssetInfo info = new FInvestigationCreditSchemePledgeAssetInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setType(GuaranteeTypeEnum.getByCode(doObj.getType()));
		return info;
	}
	
}
