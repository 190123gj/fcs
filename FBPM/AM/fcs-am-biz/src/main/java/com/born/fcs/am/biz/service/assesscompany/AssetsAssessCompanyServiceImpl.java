package com.born.fcs.am.biz.service.assesscompany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.convert.UnBoxingConverter;
import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.AssessCompanyBusinessScopeDO;
import com.born.fcs.am.dal.dataobject.AssessCompanyContactDO;
import com.born.fcs.am.dal.dataobject.AssetsAssessCompanyDO;
import com.born.fcs.am.ws.enums.AssessCompanyStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyBusinessScopeInfo;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyContactInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyBusinessScopeOrder;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyContactOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssetsAssessCompanyService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("assetsAssessCompanyService")
public class AssetsAssessCompanyServiceImpl extends BaseAutowiredDomainService implements
																				AssetsAssessCompanyService {
	
	private AssetsAssessCompanyInfo convertDO2Info(AssetsAssessCompanyDO DO) {
		if (DO == null)
			return null;
		AssetsAssessCompanyInfo info = new AssetsAssessCompanyInfo();
		info.setStatus(AssessCompanyStatusEnum.getByCode(DO.getStatus()));
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private AssessCompanyContactInfo convertDO2ContactInfo(AssessCompanyContactDO DO) {
		if (DO == null)
			return null;
		AssessCompanyContactInfo info = new AssessCompanyContactInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private AssessCompanyBusinessScopeInfo convertDO2ScopeInfo(AssessCompanyBusinessScopeDO DO) {
		if (DO == null)
			return null;
		AssessCompanyBusinessScopeInfo info = new AssessCompanyBusinessScopeInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	@Override
	public AssetsAssessCompanyInfo findById(long id) {
		AssetsAssessCompanyInfo info = null;
		if (id > 0) {
			AssetsAssessCompanyDO DO = assetsAssessCompanyDAO.findById(id);
			info = convertDO2Info(DO);
			List<AssessCompanyContactDO> listContactDO = assessCompanyContactDAO
				.findByAssessCompanyId(id);
			List<AssessCompanyContactInfo> listContactInfo = new ArrayList<AssessCompanyContactInfo>();
			if (listContactDO != null) {
				for (AssessCompanyContactDO assessCompanyContactDO : listContactDO) {
					listContactInfo.add(convertDO2ContactInfo(assessCompanyContactDO));
				}
			}
			info.setContactInfos(listContactInfo);
			List<AssessCompanyBusinessScopeDO> listScopeDO = assessCompanyBusinessScopeDAO
				.findByAssessCompanyId(id);
			List<AssessCompanyBusinessScopeInfo> listScopeInfo = new ArrayList<AssessCompanyBusinessScopeInfo>();
			for (AssessCompanyBusinessScopeDO scopeDO : listScopeDO) {
				listScopeInfo.add(convertDO2ScopeInfo(scopeDO));
			}
			info.setScopeInfos(listScopeInfo);
			
		}
		return info;
	}
	
	@Override
	public long findByCompanyNameCount(String companyName) {
		AssetsAssessCompanyDO DO = new AssetsAssessCompanyDO();
		DO.setCompanyName(companyName);
		return assetsAssessCompanyDAO.findByCompanyNameCount(DO);
	}
	
	@Override
	public FcsBaseResult save(final AssetsAssessCompanyOrder order) {
		return commonProcess(order, "保存评估公司信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				AssetsAssessCompanyDO company = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getId() != null && order.getId() > 0) {
					company = assetsAssessCompanyDAO.findById(order.getId());
					if (company == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "公司不存在");
					}
				}
				
				if (company == null) { // 新增
					company = new AssetsAssessCompanyDO();
					BeanCopier.staticCopy(order, company, UnBoxingConverter.getInstance());
					company.setRawAddTime(now);
					company.setRegisteredCapital(order.getRegisteredCapital());
					long companyId = assetsAssessCompanyDAO.insert(company);
					
					List<AssessCompanyContactOrder> listContactOrder = order.getContactOrders();
					if (listContactOrder != null) {
						for (AssessCompanyContactOrder order : listContactOrder) {
							AssessCompanyContactDO contactDO = new AssessCompanyContactDO();
							BeanCopier.staticCopy(order, contactDO, UnBoxingConverter.getInstance());
							contactDO.setAssessCompanyId(companyId);
							contactDO.setRawAddTime(now);
							assessCompanyContactDAO.insert(contactDO);
						}
					}
					List<AssessCompanyBusinessScopeOrder> listScopOrder = order.getScopeOrders();
					if (listScopOrder != null) {
						for (AssessCompanyBusinessScopeOrder order : listScopOrder) {
							AssessCompanyBusinessScopeDO scopeDO = new AssessCompanyBusinessScopeDO();
							BeanCopier.staticCopy(order, scopeDO, UnBoxingConverter.getInstance());
							scopeDO.setAssessCompanyId(companyId);
							scopeDO.setRawAddTime(now);
							assessCompanyBusinessScopeDAO.insert(scopeDO);
						}
					}
					
				} else { // 修改
					long companyId = order.getId();
					AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO.findById(companyId);
					if (null == companyDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到评估公司");
					}
					BeanCopier.staticCopy(order, company, UnBoxingConverter.getInstance());
					company.setRegisteredCapital(order.getRegisteredCapital());
					if (order.getWorkSituation() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setWorkSituation(companyDO.getWorkSituation());
					}
					if (order.getAttachment() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setAttachment(companyDO.getAttachment());
					}
					if (order.getTechnicalLevel() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setTechnicalLevel(companyDO.getTechnicalLevel());
					}
					if (order.getEvaluationEfficiency() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setEvaluationEfficiency(companyDO.getEvaluationEfficiency());
					}
					if (order.getCooperationSituation() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setCooperationSituation(companyDO.getCooperationSituation());
					}
					if (order.getServiceAttitude() == 5.0 || order.getWorkSituation() == 0.0) {
						company.setServiceAttitude(companyDO.getServiceAttitude());
					}
					assetsAssessCompanyDAO.update(company);
					if (order.getContactOrders() != null) {
						assessCompanyContactDAO.deleteByAssessCompanyId(companyId);
					}
					if (order.getContactOrders() != null) {
						for (AssessCompanyContactOrder contactOrder : order.getContactOrders()) {
							AssessCompanyContactDO contactDO = new AssessCompanyContactDO();
							BeanCopier.staticCopy(contactOrder, contactDO,
								UnBoxingConverter.getInstance());
							contactDO.setAssessCompanyId(companyId);
							contactDO.setRawAddTime(now);
							assessCompanyContactDAO.insert(contactDO);
						}
					}
					if (order.getScopeOrders() != null) {// 删除 再重新排序
						assessCompanyBusinessScopeDAO.deleteByAssessCompanyId(companyId);
					}
					List<AssessCompanyBusinessScopeOrder> listScopOrder = order.getScopeOrders();
					if (listScopOrder != null) {
						for (AssessCompanyBusinessScopeOrder order : listScopOrder) {
							AssessCompanyBusinessScopeDO scopeDO = new AssessCompanyBusinessScopeDO();
							BeanCopier.staticCopy(order, scopeDO, UnBoxingConverter.getInstance());
							scopeDO.setAssessCompanyId(companyId);
							scopeDO.setRawAddTime(now);
							assessCompanyBusinessScopeDAO.insert(scopeDO);
						}
					}
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public QueryBaseBatchResult<AssetsAssessCompanyInfo> query(AssetsAssessCompanyQueryOrder order) {
		QueryBaseBatchResult<AssetsAssessCompanyInfo> baseBatchResult = new QueryBaseBatchResult<AssetsAssessCompanyInfo>();
		
		AssetsAssessCompanyDO queryCondition = new AssetsAssessCompanyDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getCompanyName() != null)
			queryCondition.setCompanyName(order.getCompanyName());
		if (order.getCity() != null)
			queryCondition.setCity(order.getCity());
		List<String> qualityLandList = order.getQualityLandList();
		List<String> qualityHouseList = order.getQualityHouseList();
		List<String> qualityAssetsList = order.getQualityAssetsList();
		long totalSize = assetsAssessCompanyDAO.findByConditionCount(queryCondition,
			qualityLandList, qualityHouseList, qualityAssetsList);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<AssetsAssessCompanyDO> pageList = assetsAssessCompanyDAO.findByCondition(
			queryCondition, component.getFirstRecord(), component.getPageSize(),
			order.getSortCol(), order.getSortOrder(), qualityLandList, qualityHouseList,
			qualityAssetsList);
		
		List<AssetsAssessCompanyInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AssetsAssessCompanyDO assessCompany : pageList) {
				AssetsAssessCompanyInfo info = convertDO2Info(assessCompany);
				info = findById(info.getId());
				info.setStatus(AssessCompanyStatusEnum.getByCode(assessCompany.getStatus()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult deleteById(final AssetsAssessCompanyOrder order) {
		return commonProcess(order, "删除评估公司", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到评估公司");
				} else {
					AssetsAssessCompanyDO DO = assetsAssessCompanyDAO.findById(order.getId());
					
					assetsAssessCompanyDAO.update(DO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<AssessCompanyContactInfo> findContactByCompanyId(long assessCompanyId) {
		List<AssessCompanyContactInfo> listContactInfo = Lists.newArrayList();
		List<AssessCompanyContactDO> listContactDO = assessCompanyContactDAO
			.findByAssessCompanyId(assessCompanyId);
		for (AssessCompanyContactDO DO : listContactDO) {
			listContactInfo.add(convertDO2ContactInfo(DO));
		}
		return listContactInfo;
	}
	
	@Override
	public List<String> findCities() {
		return assetsAssessCompanyDAO.findCities();
	}
}
