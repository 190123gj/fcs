package com.born.fcs.am.biz.service.pledgeimage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.PledgeImageCustomDO;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.am.ws.service.pledgeimage.PledgeImageCustomService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("pledgeImageCustomService")
public class PledgeImageCustomServiceImpl extends BaseFormAutowiredDomainService implements
																				PledgeImageCustomService {
	private PledgeImageCustomInfo convertDO2Info(PledgeImageCustomDO DO) {
		if (DO == null)
			return null;
		PledgeImageCustomInfo info = new PledgeImageCustomInfo();
		BeanCopier.staticCopy(DO, info);
		info.setLatestEntryForm(LatestEntryFormEnum.getByCode(DO.getLatestEntryForm()));
		info.setIsRequired(BooleanEnum.getByCode(DO.getIsRequired()));
		return info;
	}
	
	@Override
	public PledgeImageCustomInfo findById(long imageId) {
		PledgeImageCustomInfo info = null;
		if (imageId > 0) {
			PledgeImageCustomDO DO = pledgeImageCustomDAO.findById(imageId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public int deleteById(long typeId) {
		int num = 0;
		if (typeId != 0) {
			num = pledgeImageCustomDAO.deleteById(typeId);
		}
		return num;
		
	}
	
	@Override
	public List<PledgeImageCustomInfo> findByTypeId(long typeId) {
		List<PledgeImageCustomInfo> imageInfoList = new ArrayList<PledgeImageCustomInfo>();
		if (typeId != 0) {
			List<PledgeImageCustomDO> listImageDO = pledgeImageCustomDAO.findByTypeId(typeId);
			for (PledgeImageCustomDO pledgeImageCustomDO : listImageDO) {
				PledgeImageCustomInfo imageInfo = convertDO2Info(pledgeImageCustomDO);
				imageInfoList.add(imageInfo);
			}
		}
		return imageInfoList;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeImageCustomInfo> query(PledgeImageCustomQueryOrder order) {
		QueryBaseBatchResult<PledgeImageCustomInfo> baseBatchResult = new QueryBaseBatchResult<PledgeImageCustomInfo>();
		
		PledgeImageCustomDO queryCondition = new PledgeImageCustomDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getImageId() != null)
			queryCondition.setImageId(order.getImageId());
		
		long totalSize = pledgeImageCustomDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<PledgeImageCustomDO> pageList = pledgeImageCustomDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize());
		
		List<PledgeImageCustomInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (PledgeImageCustomDO pledgeType : pageList) {
				list.add(convertDO2Info(pledgeType));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
}
