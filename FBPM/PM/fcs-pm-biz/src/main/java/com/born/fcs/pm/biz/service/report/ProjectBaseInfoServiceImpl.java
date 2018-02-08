package com.born.fcs.pm.biz.service.report;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDetailDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectDataInfoDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.report.ProjectInfoBaseInfo;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.report.ProjectBaseInfoService;
import com.born.fcs.pm.ws.service.report.order.ProjectBaseInfoQueryOrder;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("projectBaseInfoService")
public class ProjectBaseInfoServiceImpl extends BaseAutowiredDomainService implements
																			ProjectBaseInfoService {
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	private BasicDataService basicDataService;
	@Autowired
	private ProjectChannelRelationService projectChannelRelationService;
	
	@Override
	public ProjectInfoBaseInfo queryProjectBaseInfo(ProjectBaseInfoQueryOrder queryOrder) {
		if (StringUtil.isBlank(queryOrder.getProjectCode())
			&& StringUtil.isBlank(queryOrder.getProjectName())) {
			return null;
		}
		
		Date now = getSysdate();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH) + 1;
		ProjectInfoBaseInfo baseInfo = new ProjectInfoBaseInfo();
		
//		if (queryOrder.getReportYear() == year && queryOrder.getReportMonth() == month) {
			ProjectDataInfoDO projectDataInfo = new ProjectDataInfoDO();
			projectDataInfo.setProjectCode(queryOrder.getProjectCode());
			projectDataInfo.setProjectName(queryOrder.getProjectName());
			List<ProjectDataInfoDO> infoDOs = projectDataInfoDAO.findByCondition(projectDataInfo,
				0, 1, null, null);
			if (ListUtil.isEmpty(infoDOs)) {
				return null;
			}
			
			BeanCopier.staticCopy(infoDOs.get(0), baseInfo);
//		} else {
//			ProjectDataInfoHisDataDO hisDO = new ProjectDataInfoHisDataDO();
//			hisDO.setProjectCode(queryOrder.getProjectCode());
//			hisDO.setProjectName(queryOrder.getProjectName());
//			hisDO.setYear(queryOrder.getReportYear());
//			hisDO.setMonth(queryOrder.getReportMonth());
//			List<ProjectDataInfoHisDataDO> hisDOs = projectDataInfoHisDataDAO.findByCondition(
//				hisDO, 0, 1, null, null);
//			if (ListUtil.isEmpty(hisDOs)) {
//				return null;
//			}
//			
//			BeanCopier.staticCopy(hisDOs.get(0), baseInfo);
//		}
		
		ProjectDO project = projectDAO.findByProjectCode(baseInfo.getProjectCode());
		if (null != project) {
			if ((year + "").equals(DateUtil.formatYear(project.getRawAddTime()))) {
				baseInfo.setIsNew("Y");
			}
			if (BooleanEnum.IS.code().equals(project.getIsApproval())) {
				baseInfo.setOnWillDate(project.getApprovalTime());
			}
		}
		
		List<ChargeRepayPlanDetailDO> details = chargeRepayPlanDetailDAO.findByProjectCode(baseInfo
			.getProjectCode());
		if (ListUtil.isNotEmpty(details)) {
			List<ChargeRepayPlanDetailInfo> repayList = Lists.newArrayList();
			List<ChargeRepayPlanDetailInfo> chargeList = Lists.newArrayList();
			for (ChargeRepayPlanDetailDO detailDO : details) {
				ChargeRepayPlanDetailInfo detail = convertPlanDetailDO2Info(detailDO);
				if (detail.getPlanType() == PlanTypeEnum.REPAY_PLAN) {
					repayList.add(detail);
				} else {
					chargeList.add(detail);
				}
			}
			baseInfo.setRepays(repayList);
			baseInfo.setCharges(chargeList);
		}
		
		//风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(baseInfo
			.getProjectCode());
		if (null != riskManager) {
			riskManager.getUserName();
			baseInfo.setRiskManagerName(riskManager.getUserName());
		}
		//法务经理
		ProjectRelatedUserInfo legalManager = projectRelatedUserService.getLegalManager(baseInfo
			.getProjectCode());
		if (null != legalManager) {
			baseInfo.setLegalManagerName(legalManager.getUserName());
		}
		
		if (StringUtil.isNotBlank(baseInfo.getBusiType())) {
			String btype = baseInfo.getBusiType().substring(0, 1);
			if ("1".equals(btype)) {
				btype = baseInfo.getBusiType().substring(0, 2);
			}
			BusiTypeInfo busiType = basicDataService.querySingleBusiType(btype, "");
			if (null != busiType) {
				baseInfo.setProductType(busiType.getName());
			}
		}
		
		List<ProjectChannelRelationInfo> channels = projectChannelRelationService
			.queryCapitalChannel(baseInfo.getProjectCode());
		baseInfo.setCapitalChannel(channels);
		
		return baseInfo;
	}
	
}
