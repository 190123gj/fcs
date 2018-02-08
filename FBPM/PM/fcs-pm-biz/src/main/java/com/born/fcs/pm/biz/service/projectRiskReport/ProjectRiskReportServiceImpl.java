package com.born.fcs.pm.biz.service.projectRiskReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjf.common.lang.util.DateUtil;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectRiskReportCompDetailDO;
import com.born.fcs.pm.dal.dataobject.ProjectRiskReportDO;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.projectRiskReport.ProjectRiskReportCompDetailInfo;
import com.born.fcs.pm.ws.info.projectRiskReport.ProjectRiskReportInfo;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportCompDetailProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportDeleteOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectRiskReport.ProjectRiskReportService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by wqh on 2016/9/20.
 */
@Service("projectRiskReportService")
public class ProjectRiskReportServiceImpl extends BaseAutowiredDomainService implements
																			ProjectRiskReportService {
	@Override
	public ProjectRiskReportInfo findByReportId(long reportId) {
		ProjectRiskReportDO projectRiskReportDO = projectRiskReportDAO.findById(reportId);
		if (projectRiskReportDO != null) {
			ProjectRiskReportInfo info = new ProjectRiskReportInfo();
			BeanCopier.staticCopy(projectRiskReportDO, info);
			ProjectRiskReportCompDetailDO detailDO = new ProjectRiskReportCompDetailDO();
			detailDO.setReportId(projectRiskReportDO.getReportId());
			List<ProjectRiskReportCompDetailDO> detailDOList = projectRiskReportCompDetailDAO
				.findByCondition(detailDO, 0, 0, null, null);
			if (ListUtil.isNotEmpty(detailDOList)) {
				List<ProjectRiskReportCompDetailInfo> detailInfos = new ArrayList<ProjectRiskReportCompDetailInfo>();
				for (ProjectRiskReportCompDetailDO compDetailDO : detailDOList) {
					ProjectRiskReportCompDetailInfo compDetailInfo = new ProjectRiskReportCompDetailInfo();
					BeanCopier.staticCopy(compDetailDO, compDetailInfo);
					detailInfos.add(compDetailInfo);
				}
				info.setDetailInfos(detailInfos);
			}
			return info;
		}
		return null;
	}
	
	@Override
	public FcsBaseResult deleteByReportId(final ProjectRiskReportDeleteOrder deleteOrder) {
		return commonProcess(deleteOrder, "删除风险项目情况汇报", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				projectRiskReportDAO.deleteById(deleteOrder.getReportId());
				projectRiskReportCompDetailDAO.deleteByReportId(deleteOrder.getReportId());
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectRiskReportInfo> queryProjectRiskReportInfo(	ProjectRiskReportQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectRiskReportInfo> batchResult = new QueryBaseBatchResult<ProjectRiskReportInfo>();
		try {
			queryOrder.check();
			List<ProjectRiskReportInfo> pageList = Lists.newArrayList();
			ProjectRiskReportDO reportDO = new ProjectRiskReportDO();
			BeanCopier.staticCopy(queryOrder, reportDO);
			long totalCount = projectRiskReportDAO.findByConditionCount(reportDO,
				queryOrder.getStartTimeBegin(), queryOrder.getStartTimeEnd(),queryOrder.getLoginUserId(),queryOrder.getDeptIdList(),queryOrder.getRelatedRoleList());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ProjectRiskReportDO> recordList = projectRiskReportDAO.findByCondition(reportDO,
				component.getFirstRecord(), component.getPageSize(),
				queryOrder.getStartTimeBegin(), queryOrder.getStartTimeEnd(),queryOrder.getLoginUserId(),queryOrder.getDeptIdList(),queryOrder.getRelatedRoleList(),
					queryOrder.getSortCol(),queryOrder.getSortOrder());
			for (ProjectRiskReportDO item : recordList) {
				ProjectRiskReportInfo info = new ProjectRiskReportInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return batchResult;
	}
	
	@Override
	public FcsBaseResult save(final ProjectRiskReportProcessOrder processOrder) {
		return commonProcess(processOrder, "保存风险项目汇报", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				ProjectRiskReportDO reportDO = new ProjectRiskReportDO();
				BeanCopier.staticCopy(processOrder, reportDO);
				reportDO.setReportManId(processOrder.getUserId());
				reportDO.setReportManName(processOrder.getUserName());
				if (processOrder.getReportId() != 0) {
					projectRiskReportDAO.update(reportDO);
				} else {
					reportDO.setRawAddTime(getSysdate());
					projectRiskReportDAO.insert(reportDO);
				}
				
				Map<Long, ProjectRiskReportCompDetailDO> map = new HashMap<Long, ProjectRiskReportCompDetailDO>();
				ProjectRiskReportCompDetailDO compDetailDO = new ProjectRiskReportCompDetailDO();
				compDetailDO.setReportId(reportDO.getReportId());
				List<ProjectRiskReportCompDetailDO> compDetailDOs = projectRiskReportCompDetailDAO
					.findByCondition(compDetailDO, 0, 0, null, null);
				if (ListUtil.isNotEmpty(compDetailDOs)) {
					for (ProjectRiskReportCompDetailDO reportCompDetailDO : compDetailDOs) {
						map.put(reportCompDetailDO.getDetailId(), reportCompDetailDO);
					}
				}
				
				if (ListUtil.isNotEmpty(processOrder.getDetailProcessOrders())) {
					for (ProjectRiskReportCompDetailProcessOrder detailProcessOrder : processOrder
						.getDetailProcessOrders()) {
						ProjectRiskReportCompDetailDO detailDO = new ProjectRiskReportCompDetailDO();
						BeanCopier.staticCopy(detailProcessOrder, detailDO);
						detailDO.setProjectCode(processOrder.getProjectCode());
						detailDO.setCompDate(DateUtil.strToDtSimpleFormat(detailProcessOrder.getCompDate()));
						detailDO.setRawAddTime(getSysdate());
						detailDO.setReportId(reportDO.getReportId());
						if (detailDO.getDetailId() != 0) {
							projectRiskReportCompDetailDAO.update(detailDO);
							map.remove(detailDO.getDetailId());
						} else {
							projectRiskReportCompDetailDAO.insert(detailDO);
						}
					}
				}
				
				//删除的数据
				if (null != map && map.size() > 0) {
					for (long id : map.keySet()) {
						projectRiskReportCompDetailDAO.deleteById(id);
					}
				}
				
				return null;
			}
		}, null, null);
	}
}
