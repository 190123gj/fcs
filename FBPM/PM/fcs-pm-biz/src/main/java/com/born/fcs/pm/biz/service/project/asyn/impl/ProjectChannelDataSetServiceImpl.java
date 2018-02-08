package com.born.fcs.pm.biz.service.project.asyn.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelDataSetService;
import com.born.fcs.pm.dal.daointerface.ProjectChannelRelationDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDataInfoDAO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationHiDO;
import com.born.fcs.pm.dal.dataobject.ProjectDataInfoDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("projectChannelDataSetService")
public class ProjectChannelDataSetServiceImpl implements ProjectChannelDataSetService {
	
	@Autowired
	ProjectDataInfoDAO projectDataInfoDAO;
	@Autowired
	PmReportService pmReportService;
	@Autowired
	ProjectChannelRelationDAO projectChannelRelationDAO;
	
	@Override
	public boolean hisSet(ProjectChannelRelationHiDO his, Map<String, List<String>> dMap) {
		
		ProjectDataInfoDO projectData = projectDataInfoDAO.findByProjectCode(his.getProjectCode());
		if (projectData == null) {
			return false;
		}
		//在保余额
		Money inAmount = Money.zero();
		//'新增户数',
		double newCustomer = 0;
		// '新增项目',
		double newProject = 0;
		//'在保用户',
		double inCustomer = 0;
		// '在保项目'
		double inProject = 0;
		
		if (ProjectUtil.isEntrusted(projectData.getBusiType())) {
			inAmount = his.getLoanedAmount().subtract(his.getReleasedAmount())
				.subtract(his.getCompAmount());
		} else {
			inAmount = his.getReleasableAmount().subtract(his.getReleasedAmount())
				.subtract(his.getCompAmount());
		}
		
		if (projectData.getCustomerAddTime() != null
			&& DateUtil.dtSimpleYmFormat(projectData.getCustomerAddTime()).equals(
				DateUtil.dtSimpleYmFormat(his.getProjectDate())))
			newCustomer = 1;//本期添加的客户
			
		if (projectData.getApplyDate() != null
			&& DateUtil.dtSimpleYmFormat(projectData.getApplyDate()).equals(
				DateUtil.dtSimpleYmFormat(his.getProjectDate())))
			newProject = 1;//本期添加的项目
		if (inAmount.greaterThan(Money.zero())) {
			inCustomer = 1;
			inProject = 1;
		}
		if ("CAPITAL_CHANNEL".equals(his.getChannelRelation())) {
			//资金渠道
			if (newCustomer > 0 || newProject > 0 || inCustomer > 0 || inProject > 0) {
				
				if ("SET_UP_PHASES".equals(projectData.getPhases())
					|| ("INVESTIGATING_PHASES".equals(projectData.getPhases()) && !"APPROVAL"
						.equals(projectData.getPhasesStatus()))) {
					//立项阶段 或者尽调没通过 都不计数
					inCustomer = 0;
					inProject = 0;
					newCustomer = 0;
					newProject = 0;
				} else {
					//银行融资担保
					if (ProjectUtil.isBankFinancing(projectData.getBusiType())) {
						//必须是签订合同后
						if (projectData.getContractTime() != null) {
							
							List<ProjectChannelRelationDO> channelList = new ArrayList<>();
							
							//资金渠道  合同金额查询
							List<ProjectChannelRelationDO> list = projectChannelRelationDAO
								.findByProjectlatest(his.getProjectCode(), "CAPITAL_CHANNEL");
							if (list != null && ListUtil.isNotEmpty(list)) {
								//合同金额最大的渠道 最大金额中有在保余额为0的：false
								Money maxAmount = new Money(0, 0);
								for (ProjectChannelRelationDO itm : list) {
									Money thisAmount = itm.getContractAmount();
									if (thisAmount != null) {
										if (maxAmount.getCent() < thisAmount.getCent()) {
											maxAmount = thisAmount;
											channelList = new ArrayList<>();
											channelList.add(itm);
										} else if (maxAmount.getCent() == thisAmount.getCent()
													&& maxAmount.getCent() > 0) {
											channelList.add(itm);
											
										}
										
									}
									
								}
								
							}
							
							if (ListUtil.isNotEmpty(channelList)) {
								
								//当前渠道是否最大
								boolean isMax = false;
								
								for (ProjectChannelRelationDO s : channelList) {
									if (StringUtil.equals(s.getChannelCode(), his.getChannelCode())) {
										isMax = true;
										break;
									}
								}
								if (!isMax) {
									//不是最大不计数
									inCustomer = 0;
									inProject = 0;
									newCustomer = 0;
									newProject = 0;
								} else {
									//最大的几个渠道平分数量
									int size = channelList.size();
									if (his.getInAmount().getCent() > 0) {
										inCustomer = Math.round((inCustomer / size) * 10) / 10.0;
										inProject = Math.round((inProject / size) * 10) / 10.0;
									} else {
										inCustomer = 0;
										inProject = 0;
									}
									
									newCustomer = Math.round((newCustomer / size) * 10) / 10.0;
									newProject = Math.round((newProject / size) * 10) / 10.0;
								}
							} else {
								inCustomer = 0;
								inProject = 0;
								newCustomer = 0;
								newProject = 0;
							}
							
						} else {
							inCustomer = 0;
							inProject = 0;
							newCustomer = 0;
							newProject = 0;
						}
					}
					
				}
				
			}
			
		} else {
			//项目渠道
			if ("SET_UP_PHASES".equals(projectData.getPhases())
				&& !"APPROVAL".equals(projectData.getPhasesStatus())) {
				//统计立项没通过的不计数
				inCustomer = 0;
				inProject = 0;
				newCustomer = 0;
				newProject = 0;
			}
		}
		
		his.setInAmount(inAmount);
		his.setNewCustomer(newCustomer);
		his.setNewProject(newProject);
		his.setInCutomer(inCustomer);
		his.setInProject(inProject);
		return true;
	}
	
	@Override
	public boolean set(ProjectChannelRelationDO relationDO, Map<String, List<String>> dMap) {
		try {
			
			ProjectChannelRelationHiDO his = new ProjectChannelRelationHiDO();
			BeanCopier.staticCopy(relationDO, his);
			boolean rs = hisSet(his, dMap);
			BeanCopier.staticCopy(his, relationDO);
			return rs;
			
		} catch (Exception e) {
			
		}
		return false;
	}
}
