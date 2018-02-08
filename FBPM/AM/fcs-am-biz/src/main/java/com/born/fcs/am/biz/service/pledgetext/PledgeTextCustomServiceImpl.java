package com.born.fcs.am.biz.service.pledgetext;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.PledgeTextCustomDO;
import com.born.fcs.am.ws.enums.FieldTypeEnum;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.enums.TimeRangeEnum;
import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomQueryOrder;
import com.born.fcs.am.ws.service.pledgetext.PledgeTextCustomService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("pledgeTextCustomService")
public class PledgeTextCustomServiceImpl extends BaseFormAutowiredDomainService implements
																				PledgeTextCustomService {
	private PledgeTextCustomInfo convertDO2Info(PledgeTextCustomDO DO) {
		if (DO == null)
			return null;
		PledgeTextCustomInfo info = new PledgeTextCustomInfo();
		BeanCopier.staticCopy(DO, info);
		info.setFieldType(FieldTypeEnum.getByCode(DO.getFieldType()));
		info.setTimeSelectionRange(TimeRangeEnum.getByCode(DO.getTimeSelectionRange()));
		info.setIsRequired(BooleanEnum.getByCode(DO.getIsRequired()));
		info.setIsByRelation(BooleanEnum.getByCode(DO.getIsByRelation()));
		info.setLatestEntryForm(LatestEntryFormEnum.getByCode(DO.getLatestEntryForm()));
		return info;
	}
	
	@Override
	public PledgeTextCustomInfo findById(long textId) {
		PledgeTextCustomInfo info = null;
		if (textId > 0) {
			PledgeTextCustomDO DO = pledgeTextCustomDAO.findById(textId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public PledgeTextCustomInfo findByTypeIdAndFieldName(long typeId, String fieldName) {
		PledgeTextCustomInfo info = null;
		if (typeId > 0) {
			PledgeTextCustomDO DO = pledgeTextCustomDAO.findByTypeIdAndFieldName(typeId, fieldName);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public int deleteById(long typeId) {
		int num = 0;
		if (typeId != 0) {
			num = pledgeTextCustomDAO.deleteById(typeId);
		}
		return num;
		
	}
	
	@Override
	public List<PledgeTextCustomInfo> findByTypeId(long typeId) {
		List<PledgeTextCustomInfo> textInfoList = new ArrayList<PledgeTextCustomInfo>();
		if (typeId != 0) {
			List<PledgeTextCustomDO> listTextDO = pledgeTextCustomDAO.findByTypeId(typeId);
			for (PledgeTextCustomDO pledgeTextCustomDO : listTextDO) {
				PledgeTextCustomInfo textInfo = convertDO2Info(pledgeTextCustomDO);
				textInfoList.add(textInfo);
			}
		}
		return textInfoList;
	}
	
	@Override
	public List<PledgeTextCustomInfo> findMtextByTypeId(long typeId) {
		List<PledgeTextCustomInfo> textInfoList = new ArrayList<PledgeTextCustomInfo>();
		if (typeId != 0) {
			List<PledgeTextCustomDO> listTextDO = pledgeTextCustomDAO.findMtextByTypeId(typeId);
			for (PledgeTextCustomDO pledgeTextCustomDO : listTextDO) {
				PledgeTextCustomInfo textInfo = convertDO2Info(pledgeTextCustomDO);
				textInfoList.add(textInfo);
			}
		}
		return textInfoList;
	}
	
	@Override
	public List<PledgeTextCustomInfo> findNotMtextByTypeId(long typeId) {
		List<PledgeTextCustomInfo> textInfoList = new ArrayList<PledgeTextCustomInfo>();
		if (typeId != 0) {
			List<PledgeTextCustomDO> listTextDO = pledgeTextCustomDAO.findNotMtextByTypeId(typeId);
			for (PledgeTextCustomDO pledgeTextCustomDO : listTextDO) {
				PledgeTextCustomInfo textInfo = convertDO2Info(pledgeTextCustomDO);
				textInfoList.add(textInfo);
			}
		}
		return textInfoList;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTextCustomInfo> query(PledgeTextCustomQueryOrder order) {
		QueryBaseBatchResult<PledgeTextCustomInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTextCustomInfo>();
		
		PledgeTextCustomDO queryCondition = new PledgeTextCustomDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getTypeId() > 0)
			queryCondition.setTypeId(order.getTypeId());
		
		long totalSize = pledgeTextCustomDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<PledgeTextCustomDO> pageList = pledgeTextCustomDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize());
		
		List<PledgeTextCustomInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (PledgeTextCustomDO pledgeType : pageList) {
				list.add(convertDO2Info(pledgeType));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
}
