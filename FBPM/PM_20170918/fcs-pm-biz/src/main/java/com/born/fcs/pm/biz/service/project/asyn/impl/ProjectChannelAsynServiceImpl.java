package com.born.fcs.pm.biz.service.project.asyn.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelAsynService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelDataSetService;
import com.born.fcs.pm.dal.daointerface.ProjectChannelRelationDAO;
import com.born.fcs.pm.dal.daointerface.ProjectChannelRelationHiDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDataInfoDAO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationHiDO;
import com.born.fcs.pm.dal.dataobject.ProjectDataInfoDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("projectChannelAsynService")
public class ProjectChannelAsynServiceImpl extends BaseAutowiredDAOService implements
																			ProjectChannelAsynService {
	
	@Autowired
	ProjectChannelRelationDAO projectChannelRelationDAO;
	@Autowired
	ProjectChannelRelationHiDAO projectChannelRelationHiDAO;
	@Autowired
	ProjectDataInfoDAO projectDataInfoDAO;
	@Autowired
	PmReportService pmReportService;
	@Autowired
	ProjectChannelDataSetService projectChannelDataSetService;
	
	@Override
	public FcsBaseResult makeProjectChannelHisByDay(Date now) {
		FcsBaseResult result = new FcsBaseResult();
		List<ProjectChannelRelationDO> datas = projectChannelRelationDAO.findAll();
		if (ListUtil.isNotEmpty(datas)) {
			//同步前，清除已有当前日期的数据
			int del = projectChannelRelationHiDAO.deleteByProjectDate(now);
			logger.info("同步数据前清除当前日期的历史数据：result={}", del);
			Map<String, List<String>> dMap = new HashMap<String, List<String>>();
			for (ProjectChannelRelationDO data : datas) {
				ProjectChannelRelationHiDO his = new ProjectChannelRelationHiDO();
				BeanCopier.staticCopy(data, his);
				his.setProjectDate(now);
				if (projectChannelDataSetService.hisSet(his, dMap)) {
					ProjectChannelRelationDO projectChannelRelation = new ProjectChannelRelationDO();
					BeanCopier.staticCopy(his, projectChannelRelation);
					//更新当前数据
					projectChannelRelationDAO.update(projectChannelRelation);
					//存历史数据
					his.setId(0);
					projectChannelRelationHiDAO.insert(his);
				}
				
			}
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult asyChannelData(String projectCode) {
		try {
			logger.info("同步渠道数据开始：projectCode={}", projectCode);
			FcsBaseResult result = new FcsBaseResult();
			ProjectDataInfoDO projectData = projectDataInfoDAO.findByProjectCode(projectCode);
			
			//'新增户数',
			double newCustomer = 0;
			// '新增项目',
			double newProject = 0;
			//'在保用户',
			double inCustomer = 0;
			// '在保项目'
			double inProject = 0;
			
			if (projectData.getCustomerAddTime() != null
				&& DateUtil.dtSimpleYmFormat(projectData.getCustomerAddTime()).equals(
					DateUtil.dtSimpleYmFormat(new Date())))
				newCustomer = 1;//本期添加的客户
				
			if (projectData.getApplyDate() != null
				&& DateUtil.dtSimpleYmFormat(projectData.getApplyDate()).equals(
					DateUtil.dtSimpleYmFormat(new Date())))
				newProject = 1;//本期添加的项目
				
			if ("在保".equals(projectData.getCustomName1())) {
				inCustomer = 1;
				inProject = 1;
			}
			
			//尽调通过
			if (StringUtil.notEquals(projectData.getPhases(), "SET_UP_PHASES")
				&& StringUtil.notEquals(projectData.getPhases(), "INVESTIGATING_PHASES")
				|| ("INVESTIGATING_PHASES".equals(projectData.getPhases()) && "APPROVAL"
					.equals(projectData.getPhasesStatus()))) {
				
				//项目渠道
				List<ProjectChannelRelationDO> ProChannelData = projectChannelRelationDAO
					.findByProjectlatest(projectCode, "PROJECT_CHANNEL");
				if (ListUtil.isNotEmpty(ProChannelData))
					for (ProjectChannelRelationDO data : ProChannelData) {
						data.setInCutomer(inCustomer);
						data.setInProject(inProject);
						data.setNewCustomer(newCustomer);
						data.setNewProject(newProject);
						projectChannelRelationDAO.update(data);
					}
				
				//资金渠道
				List<ProjectChannelRelationDO> capChannelData = projectChannelRelationDAO
					.findByProjectlatest(projectCode, "CAPITAL_CHANNEL");
				
				//银行融资担保
				if (ProjectUtil.isBankFinancing(projectData.getBusiType())) {
					
					if (ListUtil.isEmpty(capChannelData)) {
						return result;
					}
					//当前项目回执金额最大的渠道
					List<ProjectChannelRelationDO> channelList = new ArrayList<>();
					
					//合同金额最大的渠道
					Money maxAmount = new Money(0, 0);
					for (ProjectChannelRelationDO itm : capChannelData) {
						Money thisAmount = itm.getContractAmount();
						if (thisAmount != null) {
							if (maxAmount.getCent() < thisAmount.getCent()) {
								maxAmount = thisAmount;
								channelList = new ArrayList<>();
								channelList.add(itm);
							} else if (maxAmount.getCent() == thisAmount.getCent()
										&& thisAmount.getCent() > 0) {
								channelList.add(itm);
							}
							
						}
						
					}
					
					if (ListUtil.isNotEmpty(channelList)) {//有最金额时
						//最大的几个渠道平分数量
						int size = channelList.size();
						
						inCustomer = Math.round((inCustomer / size) * 10) / 10.0;
						inProject = Math.round((inProject / size) * 10) / 10.0;
						newCustomer = Math.round((newCustomer / size) * 10) / 10.0;
						newProject = Math.round((newProject / size) * 10) / 10.0;
					} else {//无回执单时置0
						inCustomer = 0;
						inProject = 0;
						newCustomer = 0;
						newProject = 0;
					}
					
					for (ProjectChannelRelationDO data : capChannelData) {
						
						boolean isMax = false;
						if (ListUtil.isNotEmpty(channelList))
							for (ProjectChannelRelationDO s : channelList) {
								if (StringUtil.equals(s.getChannelCode(), data.getChannelCode()))
									isMax = true;
							}
						if (isMax) {
							if (data.getInAmount().getCent() <= 0) {
								data.setInCutomer(0);
								data.setInProject(0);
							} else {
								data.setInCutomer(inCustomer);
								data.setInProject(inProject);
							}
							
							data.setNewCustomer(newCustomer);
							data.setNewProject(newProject);
						} else {
							data.setInCutomer(0);
							data.setInProject(0);
							data.setNewCustomer(0);
							data.setNewProject(0);
						}
						projectChannelRelationDAO.update(data);
					}
					
				} else {
					if (ListUtil.isNotEmpty(capChannelData))
						for (ProjectChannelRelationDO data : capChannelData) {
							data.setInCutomer(inCustomer);
							data.setInProject(inProject);
							data.setNewCustomer(newCustomer);
							data.setNewProject(newProject);
							projectChannelRelationDAO.update(data);
						}
				}
				
			} else if (StringUtil.equals(projectData.getPhases(), "SET_UP_PHASES")
						&& "APPROVAL".equals(projectData.getPhasesStatus())) {
				//立项通过
				List<ProjectChannelRelationDO> ProChannelData = projectChannelRelationDAO
					.findByProjectlatest(projectCode, "PROJECT_CHANNEL");
				if (ListUtil.isNotEmpty(ProChannelData))
					for (ProjectChannelRelationDO data : ProChannelData) {
						data.setInCutomer(inCustomer);
						data.setInProject(inProject);
						data.setNewCustomer(newCustomer);
						data.setNewProject(newProject);
						projectChannelRelationDAO.update(data);
					}
			}
			logger.info("同步渠道数据结束：projectCode={},result={}", projectCode, result);
			
			return result;
			
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
