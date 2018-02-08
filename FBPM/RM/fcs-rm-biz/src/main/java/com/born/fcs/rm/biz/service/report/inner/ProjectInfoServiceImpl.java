package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.dal.dataobject.ProjectBaseDO;
import com.born.fcs.rm.dal.dataobject.ProjectCustomerDO;
import com.born.fcs.rm.dal.dataobject.ProjectItemDO;
import com.born.fcs.rm.ws.enums.ProjectItemTypeEnum;
import com.born.fcs.rm.ws.info.report.project.Project;
import com.born.fcs.rm.ws.info.report.project.ProjectBaseInfo;
import com.born.fcs.rm.ws.info.report.project.ProjectCustomerInfo;
import com.born.fcs.rm.ws.info.report.project.ProjectItemInfo;
import com.born.fcs.rm.ws.order.report.project.BatchSaveOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectCustomerQueryOrder;
import com.born.fcs.rm.ws.service.report.inner.ProjectInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 基层定期报表
 * 
 * @author lirz
 * 
 * 2016-8-9 下午5:56:13
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends BaseAutowiredDomainService implements
																		ProjectInfoService {
	
	@Override
	public ProjectCustomerInfo queryCustomerInfo(ProjectCustomerQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectCustomerInfo> batchResult = queryCustomers(queryOrder);
		if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
			batchResult.getPageList().get(0);
		}
		return null;
	}
	
	/**
	 * 查询客户列表
	 * 
	 * @param queryOrder
	 * @return
	 */
	private QueryBaseBatchResult<ProjectCustomerInfo> queryCustomers(ProjectCustomerQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectCustomerInfo> batchResult = new QueryBaseBatchResult<>();
		
		ProjectCustomerDO projectCustomer = new ProjectCustomerDO();
		BeanCopier.staticCopy(queryOrder, projectCustomer);
		
		long totalSize = projectCustomerDAO.findByConditionCount(projectCustomer);
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ProjectCustomerDO> pageList = projectCustomerDAO.findByCondition(projectCustomer,
				component.getFirstRecord(), component.getPageSize(), queryOrder.getSortCol(),
				queryOrder.getSortCol());
			List<ProjectCustomerInfo> list = batchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectCustomerDO doObj : pageList) {
					ProjectCustomerInfo info = new ProjectCustomerInfo();
					BeanCopier.staticCopy(doObj, info);
					list.add(info);
				}
			}
			
			batchResult.initPageParam(component);
			batchResult.setPageList(list);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	@Override
	public Project queryProjectInfo(ProjectBaseQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectBaseInfo> batchResult = queryProjects(queryOrder);
		if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
			return null;
		}
		
		Project project = new Project();
		ProjectBaseInfo projectBase = batchResult.getPageList().get(0);
		long reportId = projectBase.getReportId();
		
		queryProjectItems(project, reportId);
		
		return project;
	}
	
	/**
	 * 查询项目信息
	 * 
	 * @param queryOrder
	 * @return
	 */
	private QueryBaseBatchResult<ProjectBaseInfo> queryProjects(ProjectBaseQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectBaseInfo> batchResult = new QueryBaseBatchResult<>();
		
		ProjectBaseDO projectBase = new ProjectBaseDO();
		BeanCopier.staticCopy(queryOrder, projectBase);
		
		long totalSize = projectBaesDAO.findByConditionCount(projectBase);
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ProjectBaseDO> pageList = projectBaesDAO.findByCondition(projectBase,
				component.getFirstRecord(), component.getPageSize(), queryOrder.getSortCol(),
				queryOrder.getSortCol());
			List<ProjectBaseInfo> list = batchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectBaseDO doObj : pageList) {
					ProjectBaseInfo info = new ProjectBaseInfo();
					BeanCopier.staticCopy(doObj, info);
					list.add(info);
				}
			}
			
			batchResult.initPageParam(component);
			batchResult.setPageList(list);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	/**
	 * 查询项目其它信息
	 * @param project
	 * @param reportId
	 */
	private void queryProjectItems(Project project, long reportId) {
		List<ProjectItemDO> items = projectItemDAO.findByReportId(reportId);
		if (ListUtil.isEmpty(items)) {
			return;
		}
		
		List<ProjectItemInfo> repays = new ArrayList<>();
		List<ProjectItemInfo> charges = new ArrayList<>();
		List<ProjectItemInfo> operations = new ArrayList<>();
		List<ProjectItemInfo> incomes = new ArrayList<>();
		List<ProjectItemInfo> risks = new ArrayList<>();

		for (ProjectItemDO item : items) {
			ProjectItemInfo info = new ProjectItemInfo();
			BeanCopier.staticCopy(item, info);
			info.setItemType(ProjectItemTypeEnum.getByCode(item.getItemType()));
			if (info.getItemType() == ProjectItemTypeEnum.REPAY) {
				repays.add(info);
			} else if (info.getItemType() == ProjectItemTypeEnum.CHARGE) {
				charges.add(info);
			}  else if (info.getItemType() == ProjectItemTypeEnum.OPERATION) {
				operations.add(info);
			}  else if (info.getItemType() == ProjectItemTypeEnum.INCOME) {
				incomes.add(info);
			}  else if (info.getItemType() == ProjectItemTypeEnum.RISK) {
				risks.add(info);
				
			} 
		}
		
		project.setRepays(repays);
		project.setCharges(charges);
		project.setOperations(operations);
		project.setIncomes(incomes);
		project.setRisks(risks);
	}

	@Override
	public FcsBaseResult saveBatch(final BatchSaveOrder order) {
		return commonProcess(order, "批量保存", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				// TODO 查询所有客户(CRM) 企业客户，立项申请审核已通过的业务项目所属客户
				
				// TODO 查询所有的项目  立项申请审核已通过的项目
				
				return null;
			}
		}, null, null);
	}

	public static void main(String[] args) {
		String s = "20160808112423696600";
//		long l = Long.parseLong(s);
//		System.out.println(l);
		System.out.println(s);
		System.out.println(Long.MAX_VALUE);
	}
}
