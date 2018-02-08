package com.born.fcs.pm.biz.service.basicmaintain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.AssessCompanyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.RegionTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.AssessCompanyInfo;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyOrder;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("assessCompanyService")
public class AssessCompanyServiceImpl extends BaseAutowiredDomainService implements
																		AssessCompanyService {
	
	private AssessCompanyInfo convertDO2Info(AssessCompanyDO DO) {
		if (DO == null)
			return null;
		AssessCompanyInfo info = new AssessCompanyInfo();
		BeanCopier.staticCopy(DO, info);
		info.setRegion(RegionTypeEnum.getByCode(DO.getRegion()));
		return info;
	}

	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	@Override
	public AssessCompanyInfo findById(long companyId) {
		AssessCompanyInfo info = null;
		if (companyId > 0) {
			AssessCompanyDO DO = assessCompanyDAO.findById(companyId);
			info = convertDO2Info(DO);
		}
		return info;
	}

	@Override
	public long findByName(String companyName) {
		AssessCompanyDO DO=new AssessCompanyDO();
		DO.setCompanyName(companyName);
		return assessCompanyDAO.findByNameCount(DO);
	}
	
	@Override
	public FcsBaseResult save(final AssessCompanyOrder order) {
		return commonProcess(order, "保存评估公司数据", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				AssessCompanyDO company = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getCompanyId() != null && order.getCompanyId() > 0) {
					company = assessCompanyDAO.findById(order.getCompanyId());
					if (company == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "公司不存在");
					}
				}
				
				if (company == null) { //新增
					company = new AssessCompanyDO();
					BeanCopier.staticCopy(order, company, UnBoxingConverter.getInstance());
					company.setRawAddTime(now);
					company.setDeleteMark("0");
					assessCompanyDAO.insert(company);
				} else { //修改
					BeanCopier.staticCopy(order, company, UnBoxingConverter.getInstance());
					assessCompanyDAO.update(company);
					
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public QueryBaseBatchResult<AssessCompanyInfo> query(AssessCompanyQueryOrder order) {
		QueryBaseBatchResult<AssessCompanyInfo> baseBatchResult = new QueryBaseBatchResult<AssessCompanyInfo>();
		
		AssessCompanyDO queryCondition = new AssessCompanyDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getCompanyName() != null)
			queryCondition.setCompanyName(order.getCompanyName());
		if (order.getRegion() != null)
			queryCondition.setRegion(order.getRegion());
		if (order.getCity() != null)
			queryCondition.setCity(order.getCity());
		long totalSize = assessCompanyDAO.findByConditionCount(queryCondition);

		PageComponent component = new PageComponent(order, totalSize);
		
		List<AssessCompanyDO> pageList = assessCompanyDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(),order.getSortCol(),order.getSortOrder());
		
		List<AssessCompanyInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AssessCompanyDO assessCompany : pageList) {
				list.add(convertDO2Info(assessCompany));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult deleteById(final AssessCompanyOrder order) {
		return commonProcess(order, "删除评估公司", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getCompanyId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到评估公司");
				} else {
					AssessCompanyDO DO=assessCompanyDAO.findById(order.getCompanyId());
					DO.setDeleteMark("1");
					assessCompanyDAO.update(DO);
				}
				return null;
			}
		}, null, null);
	}

	@Override
	public List<String> findCities() {
		return assessCompanyDAO.findCities();
	}
}
