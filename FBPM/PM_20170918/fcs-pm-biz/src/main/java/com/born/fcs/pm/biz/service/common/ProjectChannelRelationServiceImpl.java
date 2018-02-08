package com.born.fcs.pm.biz.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelDataSetService;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectChannelRelationService")
public class ProjectChannelRelationServiceImpl extends BaseAutowiredDomainService implements
																					ProjectChannelRelationService {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	InvestigationService investigationService;
	
	@Autowired
	ProjectChannelDataSetService projectChannelDataSetService;
	
	@Override
	public FcsBaseResult saveByBizNoAndType(final ProjectChannelRelationBatchOrder order) {
		return commonProcess(order, "保存项目和渠道关系", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//历史数据版本变更
				projectChannelRelationDAO.updateVersion(order.getProjectCode(), order
					.getChannelRelation().code());
				
				//相同表单历史数据
				List<ProjectChannelRelationDO> oldList = projectChannelRelationDAO
					.findByBizNoAndType(order.getBizNo(), order.getChannelRelation().code());
				
				//保存新的
				if (order.getRelations() != null) {
					ProjectInfo project = projectService.queryByCode(order.getProjectCode(), false);
					Map<String, List<String>> dMap = new HashMap<>();
					for (ProjectChannelRelationOrder relation : order.getRelations()) {
						if (relation.isNull())
							continue;
						
						ProjectChannelRelationDO relationDO = new ProjectChannelRelationDO();
						BeanCopier.staticCopy(relation, relationDO, UnBoxingConverter.getInstance());
						relationDO.setPhases(order.getPhases().code());
						relationDO.setBizNo(order.getBizNo());
						relationDO.setProjectCode(order.getProjectCode());
						relationDO.setChannelRelation(order.getChannelRelation().code());
						relationDO.setRawAddTime(now);
						relationDO.setLatest(BooleanEnum.YES.code());
						if (oldList != null) {
							//继承相同渠道的相关金额
							for (ProjectChannelRelationDO old : oldList) {
								if (StringUtil.equals(old.getChannelCode(),
									relationDO.getChannelCode())) {
									relationDO.setContractAmount(old.getContractAmount());
									relationDO.setLoanedAmount(old.getLoanedAmount());
									relationDO.setUsedAmount(old.getLoanedAmount());
									relationDO.setCompAmount(old.getCompAmount());
									relationDO.setReleasableAmount(old.getReleasableAmount());
									relationDO.setReleasedAmount(old.getReleasedAmount());
									relationDO.setRepayedAmount(old.getRepayedAmount());
								}
							}
						}
						if (project != null) {
							relationDO.setBusiType(project.getBusiType());
							relationDO.setBusiTypeName(project.getBusiTypeName());
						}
						projectChannelDataSetService.set(relationDO, dMap);
						projectChannelRelationDAO.insert(relationDO);
					}
				}
				if (oldList == null)
					return null;
				
				//删掉
				for (ProjectChannelRelationDO old : oldList) {
					projectChannelRelationDAO.deleteById(old.getId());
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult updateRelatedAmount(final ProjectChannelRelationAmountOrder order) {
		return commonProcess(order, "更新渠道相关金额", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				//先查询出来
				ProjectChannelRelationDO queryDO = new ProjectChannelRelationDO();
				queryDO.setProjectCode(order.getProjectCode());
				queryDO.setChannelId(order.getChannelId());
				queryDO.setChannelCode(order.getChannelCode());
				queryDO.setLatest(BooleanEnum.YES.code());
				if (order.getChannelRelation() == null) { //默认设置资金渠道
					queryDO.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL.code());
				} else {
					queryDO.setChannelRelation(order.getChannelRelation().getCode());
				}
				List<ProjectChannelRelationDO> channels = projectChannelRelationDAO
					.findByCondition(queryDO, null, null, 0, 999);
				if (channels != null) {
					Map<String, List<String>> dMap = new HashMap<>();
					for (ProjectChannelRelationDO channel : channels) {
						//更新合同金额
						if (order.getContractAmount().greaterThan(ZERO_MONEY))
							channel.setContractAmount(order.getContractAmount());
						
						channel.getLoanedAmount().addTo(order.getLoanedAmount());
						
						//放款业务细分类
						channel.getLoanAcceptanceBillAmount().addTo(
							order.getLoanAcceptanceBillAmount());
						channel.getLoanCreditAmount().addTo(order.getLoanCreditLetterAmount());
						channel.getLoanFinancialAmount().addTo(
							order.getLoanFixedAssetsFinancingAmount());
						channel.getLoanLiquidityLoansAmount().addTo(
							order.getLoanLiquidityLoanAmount());
						
						channel.getUsedAmount().addTo(order.getUsedAmount());
						if (order.isUpdateReleasable()) {
							channel.setReleasableAmount(order.getReleasableAmount());
						} else {
							channel.getReleasableAmount().addTo(order.getReleasableAmount());
						}
						channel.getReleasedAmount().addTo(order.getReleasedAmount());
						//解保业务细分类
						channel.getReleaseAcceptanceBillAmount().addTo(
							order.getReleaseAcceptanceBillAmount());
						channel.getReleaseCreditAmount()
							.addTo(order.getReleaseCreditLetterAmount());
						channel.getReleaseFinancialAmount().addTo(
							order.getReleaseFixedAssetsFinancingAmount());
						channel.getReleaseLiquidityLoansAmount().addTo(
							order.getReleaseLiquidityLoanAmount());
						
						channel.getCompAmount().addTo(order.getCompAmount());
						//代偿业务细分类
						channel.getCompAcceptanceBillAmount().addTo(
							order.getCompAcceptanceBillAmount());
						channel.getCompCreditAmount().addTo(order.getCompCreditLetterAmount());
						channel.getCompFinancialAmount().addTo(
							order.getCompFixedAssetsFinancingAmount());
						channel.getCompLiquidityLoansAmount().addTo(
							order.getCompLiquidityLoanAmount());
						
						channel.getRepayedAmount().addTo(order.getRepayedAmount());
						
						projectChannelDataSetService.set(channel, dMap);
						projectChannelRelationDAO.update(channel);
					}
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<ProjectChannelRelationInfo> queryByBizNoAndType(String bizNo,
																ChannelRelationEnum type) {
		List<ProjectChannelRelationDO> dataList = projectChannelRelationDAO.findByBizNoAndType(
			bizNo, type.code());
		if (ListUtil.isNotEmpty(dataList)) {
			List<ProjectChannelRelationInfo> infoList = Lists.newArrayList();
			for (ProjectChannelRelationDO DO : dataList) {
				infoList.add(convertDO2Info(DO));
			}
			return infoList;
		}
		return null;
	}
	
	@Override
	public List<ProjectChannelRelationInfo> queryCapitalChannel(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		ProjectChannelRelationDO queryDO = new ProjectChannelRelationDO();
		queryDO.setProjectCode(projectCode);
		queryDO.setLatest(BooleanEnum.YES.code());
		queryDO.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL.code());
		List<ProjectChannelRelationDO> capitalChannels = projectChannelRelationDAO.findByCondition(
			queryDO, null, null, 0, 999);
		if (ListUtil.isEmpty(capitalChannels)) {
			return null;
		} else {
			List<ProjectChannelRelationInfo> channels = Lists.newArrayList();
			for (ProjectChannelRelationDO channel : capitalChannels) {
				channels.add(convertDO2Info(channel));
			}
			return channels;
		}
	}
	
	@Override
	public ProjectChannelRelationInfo queryProjectChannel(String projectCode) {
		if (StringUtil.isBlank(projectCode))
			return null;
		ProjectChannelRelationDO queryDO = new ProjectChannelRelationDO();
		queryDO.setProjectCode(projectCode);
		queryDO.setChannelRelation(ChannelRelationEnum.PROJECT_CHANNEL.code());
		queryDO.setLatest(BooleanEnum.YES.code());
		List<ProjectChannelRelationDO> projectChannels = projectChannelRelationDAO.findByCondition(
			queryDO, null, null, 0, 999);
		if (ListUtil.isEmpty(projectChannels)) {
			return null;
		} else {
			return convertDO2Info(projectChannels.get(0));
		}
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChannelRelationInfo> query(	ProjectChannelRelationQueryOrder order) {
		QueryBaseBatchResult<ProjectChannelRelationInfo> batchResult = new QueryBaseBatchResult<ProjectChannelRelationInfo>();
		try {
			ProjectChannelRelationDO relation = new ProjectChannelRelationDO();
			BeanCopier.staticCopy(order, relation);
			if (order.getChannelRelation() != null)
				relation.setChannelRelation(order.getChannelRelation().code());
			if (order.getPhases() != null)
				relation.setPhases(order.getPhases().code());
			if (order.getLatest() != null)
				relation.setLatest(order.getLatest().code());
			long totalCount = projectChannelRelationDAO.findByConditionCount(relation);
			PageComponent component = new PageComponent(order, totalCount);
			List<ProjectChannelRelationDO> dataList = projectChannelRelationDAO.findByCondition(
				relation, order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			List<ProjectChannelRelationInfo> list = Lists.newArrayList();
			for (ProjectChannelRelationDO DO : dataList) {
				list.add(convertDO2Info(DO));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目渠道出错 ： {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	private ProjectChannelRelationInfo convertDO2Info(ProjectChannelRelationDO DO) {
		if (DO == null)
			return null;
		ProjectChannelRelationInfo info = new ProjectChannelRelationInfo();
		BeanCopier.staticCopy(DO, info);
		info.setPhases(ProjectPhasesEnum.getByCode(DO.getPhases()));
		info.setChannelRelation(ChannelRelationEnum.getByCode(DO.getChannelRelation()));
		return info;
	}
}
