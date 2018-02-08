package com.born.fcs.pm.biz.service.projectissueinformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectBondReinsuranceInformationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectBondReinsuranceInformationInfo;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectBondReinsuranceInformationService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectBondReinsuranceInformationService")
public class ProjectBondReinsuranceInformationServiceImpl extends BaseAutowiredDomainService
																							implements
																							ProjectBondReinsuranceInformationService {
	@Autowired
	ProjectService projectService;
	@Autowired
	protected ExpireProjectService expireProjectService;
	@Autowired
	protected ReleaseProjectService releaseProjectService;
	@Autowired
	protected ProjectContractService projectContractService;
	
	private ProjectBondReinsuranceInformationInfo convertDO2Info(	ProjectBondReinsuranceInformationDO DO) {
		if (DO == null)
			return null;
		ProjectBondReinsuranceInformationInfo info = new ProjectBondReinsuranceInformationInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public ProjectBondReinsuranceInformationInfo findById(long id) {
		ProjectBondReinsuranceInformationInfo info = null;
		if (id > 0) {
			ProjectBondReinsuranceInformationDO DO = projectBondReinsuranceInformationDAO
				.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final ProjectBondReinsuranceInformationOrder order) {
		return commonProcess(order, "保存发债类项目-分保信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				if (order.getId() == null || order.getId() <= 0) {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					ProjectBondReinsuranceInformationDO reinsuranceDO = new ProjectBondReinsuranceInformationDO();
					BeanCopier.staticCopy(order, reinsuranceDO);
					reinsuranceDO.setRawAddTime(now);
					reinsuranceDO.setReinsuranceRatio(order.getReinsuranceRatio());
					Long id = projectBondReinsuranceInformationDAO.insert(reinsuranceDO);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else { //修改
					ProjectBondReinsuranceInformationDO reinsuranceDO = projectBondReinsuranceInformationDAO
						.findById(order.getId());
					BeanCopier.staticCopy(order, reinsuranceDO, UnBoxingConverter.getInstance());
					reinsuranceDO.setReinsuranceRatio(order.getReinsuranceRatio());
					projectBondReinsuranceInformationDAO.update(reinsuranceDO);
					
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo> query(	ProjectBondReinsuranceInformationQueryOrder order) {
		QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo> baseBatchResult = new QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo>();
		
		ProjectBondReinsuranceInformationDO queryCondition = new ProjectBondReinsuranceInformationDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getId() != null)
			queryCondition.setId(order.getId());
		
		long totalSize = projectBondReinsuranceInformationDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<ProjectBondReinsuranceInformationDO> pageList = projectBondReinsuranceInformationDAO
			.findByCondition(queryCondition, component.getFirstRecord(), component.getPageSize());
		
		List<ProjectBondReinsuranceInformationInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectBondReinsuranceInformationDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public List<ProjectBondReinsuranceInformationInfo> findByProjectCode(String projectCode) {
		
		List<ProjectBondReinsuranceInformationInfo> listReinsuranceInfo = new ArrayList<ProjectBondReinsuranceInformationInfo>();
		List<ProjectBondReinsuranceInformationDO> listReinsuranceDO = projectBondReinsuranceInformationDAO
			.findByProjectCode(projectCode);
		for (ProjectBondReinsuranceInformationDO reinsuranceDO : listReinsuranceDO) {
			ProjectBondReinsuranceInformationInfo reinsuranceInfo = convertDO2Info(reinsuranceDO);
			listReinsuranceInfo.add(reinsuranceInfo);
		}
		return listReinsuranceInfo;
	}
	
}
