package com.born.fcs.am.biz.service.pledgetypeattribute;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.convert.UnBoxingConverter;
import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.PledgeTypeAttributeDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeImageDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeNetworkDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeTextDO;
import com.born.fcs.am.ws.enums.FieldTypeEnum;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.enums.TimeRangeEnum;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeImageInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeNetworkInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeImageQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeNetworkQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.am.ws.service.pledgetypeattribute.PledgeTypeAttributeService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("pledgeTypeAttributeService")
public class PledgeTypeAttributeServiceImpl extends BaseFormAutowiredDomainService implements
																					PledgeTypeAttributeService {
	private PledgeTypeAttributeInfo convertDO2Info(PledgeTypeAttributeDO DO) {
		if (DO == null)
			return null;
		PledgeTypeAttributeInfo info = new PledgeTypeAttributeInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private PledgeTypeAttributeTextInfo convertDO2TextInfo(PledgeTypeAttributeTextDO DO) {
		if (DO == null)
			return null;
		PledgeTypeAttributeTextInfo info = new PledgeTypeAttributeTextInfo();
		BeanCopier.staticCopy(DO, info);
		info.setFieldType(FieldTypeEnum.getByCode(DO.getFieldType()));
		info.setTimeSelectionRange(TimeRangeEnum.getByCode(DO.getTimeSelectionRange()));
		info.setIsRequired(BooleanEnum.getByCode(DO.getIsRequired()));
		info.setIsByRelation(BooleanEnum.getByCode(DO.getIsByRelation()));
		info.setLatestEntryForm(LatestEntryFormEnum.getByCode(DO.getLatestEntryForm()));
		return info;
	}
	
	private PledgeTypeAttributeImageInfo convertDO2ImageInfo(PledgeTypeAttributeImageDO DO) {
		if (DO == null)
			return null;
		PledgeTypeAttributeImageInfo info = new PledgeTypeAttributeImageInfo();
		BeanCopier.staticCopy(DO, info);
		info.setLatestEntryForm(LatestEntryFormEnum.getByCode(DO.getLatestEntryForm()));
		info.setIsRequired(BooleanEnum.getByCode(DO.getIsRequired()));
		return info;
	}
	
	private PledgeTypeAttributeNetworkInfo convertDO2NetworkInfo(PledgeTypeAttributeNetworkDO DO) {
		if (DO == null)
			return null;
		PledgeTypeAttributeNetworkInfo info = new PledgeTypeAttributeNetworkInfo();
		BeanCopier.staticCopy(DO, info);
		
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeTextInfo> queryAttributeText(PledgeTypeAttributeTextQueryOrder order) {
		QueryBaseBatchResult<PledgeTypeAttributeTextInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTypeAttributeTextInfo>();
		try {
			PledgeTypeAttributeTextDO queryCondition = new PledgeTypeAttributeTextDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, queryCondition);
			
			long totalCount = extraDAO.searchPledgeTypeAttributeTextDOCount(queryCondition);
			
			List<PledgeTypeAttributeTextDO> pageList = extraDAO
				.searchPledgeTypeAttributeTextList(queryCondition);
			
			List<PledgeTypeAttributeTextInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (PledgeTypeAttributeTextDO sf : pageList) {
					PledgeTypeAttributeTextInfo text = convertDO2TextInfo(sf);
					list.add(text);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
		} catch (Exception e) {
			logger.error("查询资产文字信息属性失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
		
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeImageInfo> queryAttributeImage(	PledgeTypeAttributeImageQueryOrder order) {
		QueryBaseBatchResult<PledgeTypeAttributeImageInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTypeAttributeImageInfo>();
		try {
			PledgeTypeAttributeImageDO queryCondition = new PledgeTypeAttributeImageDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, queryCondition);
			
			long totalCount = extraDAO.searchPledgeTypeAttributeImageCount(queryCondition);
			
			List<PledgeTypeAttributeImageDO> pageList = extraDAO
				.searchPledgeTypeAttributeImageList(queryCondition);
			
			List<PledgeTypeAttributeImageInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (PledgeTypeAttributeImageDO sf : pageList) {
					PledgeTypeAttributeImageInfo text = convertDO2ImageInfo(sf);
					String[] str = text.getAttributeValue().split("&&");
					if (str.length == 1) {
						text.setAttributeImageText(str[0]);
						text.setAttributeValue(null);
					} else if (str.length == 2) {
						text.setAttributeImageText(str[0]);
						text.setAttributeValue(str[1]);
					}
					if ("&&".equals(text.getAttributeValue())) {
						text.setAttributeValue(null);
					}
					list.add(text);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
		} catch (Exception e) {
			logger.error("查询资产图像信息属性失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> queryAttributeNetwork(	PledgeTypeAttributeNetworkQueryOrder order) {
		QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo>();
		try {
			PledgeTypeAttributeNetworkDO queryCondition = new PledgeTypeAttributeNetworkDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, queryCondition);
			
			long totalCount = extraDAO.searchPledgeTypeAttributeNetworkCount(queryCondition);
			
			List<PledgeTypeAttributeNetworkDO> pageList = extraDAO
				.searchPledgeTypeAttributeNetworkList(queryCondition);
			
			List<PledgeTypeAttributeNetworkInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (PledgeTypeAttributeNetworkDO sf : pageList) {
					PledgeTypeAttributeNetworkInfo text = convertDO2NetworkInfo(sf);
					list.add(text);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
		} catch (Exception e) {
			logger.error("查询资产网络信息属性失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeInfo> query(PledgeTypeAttributeQueryOrder order) {
		QueryBaseBatchResult<PledgeTypeAttributeInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTypeAttributeInfo>();
		
		PledgeTypeAttributeDO queryCondition = new PledgeTypeAttributeDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getAttributeId() != null)
			queryCondition.setAttributeId(order.getAttributeId());
		
		long totalSize = pledgeTypeAttributeDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<PledgeTypeAttributeDO> pageList = pledgeTypeAttributeDAO.findByCondition(
			queryCondition, component.getFirstRecord(), component.getPageSize());
		
		List<PledgeTypeAttributeInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (PledgeTypeAttributeDO pledgeType : pageList) {
				list.add(convertDO2Info(pledgeType));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult save(final PledgeTypeAttributeOrder order) {
		return commonProcess(order, "保存资产属性", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				PledgeTypeAttributeDO atttibuteDO = null;
				if (order.getAttributeId() != null && order.getAttributeId() > 0) {
					atttibuteDO = pledgeTypeAttributeDAO.findById(order.getAttributeId());
					if (atttibuteDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"资产属性不存在");
					}
				}
				
				if (atttibuteDO == null) { // 新增
					atttibuteDO = new PledgeTypeAttributeDO();
					BeanCopier.staticCopy(order, atttibuteDO, UnBoxingConverter.getInstance());
					atttibuteDO.setRawAddTime(now);
					
					long atttibuteId = pledgeTypeAttributeDAO.insert(atttibuteDO);// 主键
					
				} else { // 修改
					PledgeTypeAttributeDO atttibuteDO1 = pledgeTypeAttributeDAO.findById(order
						.getAttributeId());
					if (order.getAttributeId() > 0) {
						atttibuteDO1.setAttributeId(order.getAttributeId());
					}
					if (order.getAssetsId() > 0) {
						atttibuteDO1.setAssetsId(order.getAssetsId());
					}
					if (order.getAttributeKey() != null) {
						atttibuteDO1.setAttributeKey(order.getAttributeKey());
					}
					if (order.getAttributeValue() != null) {
						atttibuteDO1.setAttributeValue(order.getAttributeValue());
					}
					if (order.getTypeId() > 0) {
						atttibuteDO1.setTypeId(order.getTypeId());
					}
					if (order.getCustomType() != null) {
						atttibuteDO1.setCustomType(order.getCustomType());
					}
					pledgeTypeAttributeDAO.update(atttibuteDO1);
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PledgeTypeAttributeInfo findByassetsIdAndAttributeKeyAndCustomType(	long assetsId,
																				String attributeKey,
																				String customType) {
		PledgeTypeAttributeDO DO = pledgeTypeAttributeDAO
			.findByassetsIdAndAttributeKeyAndCustomType(assetsId, attributeKey, customType);
		return convertDO2Info(DO);
	}
	
}
