package com.born.fcs.pm.biz.service.project.asyn.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectDataAsynchronousService;
import com.born.fcs.pm.dal.daointerface.ProjectCreditConditionDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDataInfoDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDataInfoHisDataDAO;
import com.born.fcs.pm.dal.dataobject.FProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectDataInfoDO;
import com.born.fcs.pm.dal.dataobject.ProjectDataInfoHisDataDO;
import com.born.fcs.pm.integration.crm.service.customer.ChannelClient;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("projectDataAsynchronousService")
public class ProjectDataAsynchronousServiceImpl extends BaseAutowiredDAOService implements
																				ProjectDataAsynchronousService {
	private Date lastDate = null;
	@Autowired
	ProjectService projectService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	ProjectDataInfoDAO projectDataInfoDAO;
	
	@Autowired
	ProjectDataInfoHisDataDAO projectDataInfoHisDataDAO;
	@Autowired
	CustomerService customerServiceClient;
	@Autowired
	CouncilSummaryService councilSummaryService;
	
	@Autowired
	ProjectCreditConditionDAO projectCreditConditionDAO;
	@Autowired
	ChannelClient channelClient;
	@Autowired
	private ProjectSetupService projectSetupService;
	
	@Override
	public FcsBaseResult makeProjectDataByDay() {
		FcsBaseResult baseResult = new FcsBaseResult();
		Date sysDate = getSysdate();
		if (lastDate == null) {
			lastDate = sysDate;
		} else {
			long diff = sysDate.getTime() - lastDate.getTime();
			long hours = diff / (1000 * 60 * 60);
			if (hours < 6) {
				baseResult.setSuccess(true);
				return baseResult;
			}
		}
		long pageSize = 200;
		ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
		projectQueryOrder.setPageNumber(200);
		Date date = sysDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		Date updateTime = calendar.getTime();
		ProjectDO projectDO = new ProjectDO();
		long count = projectDAO.findByAllCount(projectDO, updateTime);
		long beginIndex = 0;
		long maxTime = 500;
		long curTime = 0;
		while (beginIndex < count && curTime < maxTime) {
			List<ProjectDO> projectDOs = projectDAO.findByAll(projectDO, updateTime, beginIndex,
				pageSize);
			for (ProjectDO itemProjectDO : projectDOs) {
				makeProjectData(itemProjectDO.getProjectCode());
			}
			beginIndex = beginIndex + pageSize;
			curTime++;
		}
		baseResult.setSuccess(true);
		return baseResult;
	}
	
	@Override
	public FcsBaseResult makeProjectData(String projectCode) {
		FcsBaseResult baseResult = new FcsBaseResult();
		logger.info("projectCode={} 开始同步", projectCode);
		try {
			boolean dataIsNew = false;
			ProjectDO projectDO = projectDAO.findByProjectCode(projectCode);
			if (projectDO != null) {
				ProjectDataInfoDO projectDataInfoDO = projectDataInfoDAO
					.findByProjectCode(projectDO.getProjectCode());
				if (projectDataInfoDO == null) {
					projectDataInfoDO = new ProjectDataInfoDO();
					dataIsNew = true;
				}
				BeanCopier.staticCopy(projectDO, projectDataInfoDO);
				if (projectDataInfoDO.getApplyDate() == null
					|| projectDataInfoDO.getSetupDate() == null) {
					FProjectDO fProjectDO = FProjectDAO.findByProjectCode(projectDO
						.getProjectCode());
					if (fProjectDO == null)
						return baseResult;
					FormInfo formInfo = formService.findByFormId(fProjectDO.getFormId());
					if (projectDataInfoDO.getApplyDate() == null) {
						if (formInfo.getSubmitTime() != null) {
							projectDataInfoDO.setApplyDate(formInfo.getSubmitTime());
							Calendar applyDateCal = Calendar.getInstance();
							applyDateCal.setTime(projectDataInfoDO.getApplyDate());
							projectDataInfoDO.setApplyDateYear(applyDateCal.get(Calendar.YEAR));
							projectDataInfoDO
								.setApplyDateMonth(applyDateCal.get(Calendar.MONTH) + 1);
							projectDataInfoDO.setApplyDateDay(applyDateCal
								.get(Calendar.DAY_OF_MONTH));
						} else {
							baseResult.setSuccess(true);
							return baseResult;
						}
					}
					if (projectDataInfoDO.getSetupDate() == null) {
						if (formInfo.getFinishTime() != null) {
							projectDataInfoDO.setSetupDate(formInfo.getFinishTime());
							Calendar dateCal = Calendar.getInstance();
							dateCal.setTime(projectDataInfoDO.getSetupDate());
							projectDataInfoDO.setSetUpYear(dateCal.get(Calendar.YEAR));
							projectDataInfoDO.setApplyDateMonth(dateCal.get(Calendar.MONTH) + 1);
							projectDataInfoDO.setApplyDateDay(dateCal.get(Calendar.DAY_OF_MONTH));
						}
					}
				}
				ProjectInfo projectInfo = DoConvert.convertProjectDO2Info(projectDO);
				projectDataInfoDO.setBlance(projectInfo.getBalance());
				if (projectDataInfoDO.getBlance().greaterThan(Money.zero())) {
					projectDataInfoDO.setCustomName1("在保");
				} else {
					projectDataInfoDO.setCustomName1("非在保");
				}
				if (projectInfo.getCustomerId() > 0) {
					CustomerDetailInfo customerDetailInfo = customerServiceClient
						.queryByUserId(projectInfo.getCustomerId());
					if (customerDetailInfo != null) {
						projectDataInfoDO.setCustomerAddTime(customerDetailInfo.getRawAddTime());
						BeanCopier.staticCopy(customerDetailInfo, projectDataInfoDO);
						//						projectDataInfoDO.setCapitalChannelId(customerDetailInfo.getChannalId());
						//						projectDataInfoDO
						//							.setCapitalChannelName(customerDetailInfo.getChannalName());
						//						ChanalTypeEnum chanalType = ChanalTypeEnum.getByCode(customerDetailInfo
						//							.getChannalType());
						//						if (chanalType != null) {
						//							projectDataInfoDO.setChannalType(chanalType.getMessage());
						//						}
					}
				}
				
				ProjectBean projectBean = getProjectBean(projectInfo);
				projectDataInfoDO.setProjectSubChannelName(projectBean.getProjectSubChannelName());
				projectDataInfoDO.setCapitalSubChannelName(projectBean.getCapitalSubChannelName());
				projectDataInfoDO.setGuaranteeAmount(projectBean.getGuaranteeFee());
				projectDataInfoDO.setGuaranteeFeeRate(projectBean.getDblGuaranteeRate());
				projectDataInfoDO.setCapitalChannelId(projectBean.getCapitalChannelId());
				projectDataInfoDO.setCapitalChannelName(projectBean.getCapitalChannelName());
				ChannalInfo channalInfo = null;
				if (projectBean.getCapitalChannelId() != 0) {
					channalInfo = channelClient.queryById(projectBean.getCapitalChannelId());
					if (channalInfo != null) {
						projectDataInfoDO.setChannalType(ChanalTypeEnum.getMsgByCode(channalInfo
							.getChannelType()));
						projectDataInfoDO.setChannalCode(channalInfo.getChannelCode());
						//银行类型渠道
						if (ChanalTypeEnum.YH.code().equals(channalInfo.getChannelType())) {
							String creditAmount = "";
							if (StringUtil.equals(BooleanEnum.IS.code(),
								channalInfo.getIsCreditAmount())) {
								creditAmount = channalInfo.getCreditAmount().toStandardString()
												+ "元";
							}
							if (StringUtil.equals(BooleanEnum.IS.code(), channalInfo.getIsTimes())) {
								if (StringUtil.isNotBlank(creditAmount))
									creditAmount += " 且不超过净资产的" + channalInfo.getTimes() + "倍";
								else
									creditAmount += "不超过净资产的" + channalInfo.getTimes() + "倍";
								
							}
							String singleLimit = "";
							if (StringUtil.equals(BooleanEnum.IS.code(),
								channalInfo.getIsSingleLimit())) {
								singleLimit = channalInfo.getSingleLimit().toStandardString() + "元";
							}
							if (StringUtil
								.equals(BooleanEnum.IS.code(), channalInfo.getIsPercent())) {
								if (StringUtil.isNotBlank(creditAmount))
									singleLimit += "且不超过净资产的" + channalInfo.getPercent() + "%";
								else
									singleLimit += "不超过净资产的" + channalInfo.getPercent() + "%";
							}
							String compensatoryLimit = "无期限";
							if (channalInfo.getCompensatoryLimit() != -1)
								compensatoryLimit = "到达授信截止日后 "
													+ channalInfo.getCompensatoryLimit()
													+ "个"
													+ (StringUtil.equals("ZR",
														channalInfo.getDayType()) ? "自然日" : "工作日")
													+ (StringUtil.equals(BooleanEnum.IS.code(),
														channalInfo.getStraddleYear()) ? " 不跨年"
														: "");
							projectDataInfoDO.setChannalCompensatoryLimit(compensatoryLimit);
							projectDataInfoDO.setChannalCreditAmount(creditAmount);
							projectDataInfoDO.setChannalCreditEndDate(channalInfo
								.getCreditEndDate());
							projectDataInfoDO.setChannalCreditStartDate(channalInfo
								.getCreditStartDate());
							projectDataInfoDO.setChannalLossAllocationRate(channalInfo
								.getLossAllocationRate());
							projectDataInfoDO.setChannalSingleLimit(singleLimit);
							projectDataInfoDO.setChannalBondRate(channalInfo.getBondRate());
						}
						
					}
					
				}
				if (projectBean.getProjectChannelId() > 0) {
					if (projectBean.getProjectChannelId() == projectBean.getCapitalChannelId()) {
						//与资金渠道一样
						if (channalInfo != null) {
							projectDataInfoDO.setProjectChannelCode(channalInfo.getChannelCode());
							projectDataInfoDO.setProjectChannelName(channalInfo.getChannelName());
							projectDataInfoDO.setProjectChannelType(ChanalTypeEnum
								.getMsgByCode(channalInfo.getChannelType()));
						}
					} else {
						channalInfo = channelClient.queryById(projectBean.getProjectChannelId());
						if (channalInfo != null) {
							projectDataInfoDO.setProjectChannelCode(channalInfo.getChannelCode());
							projectDataInfoDO.setProjectChannelName(channalInfo.getChannelName());
							projectDataInfoDO.setProjectChannelType(ChanalTypeEnum
								.getMsgByCode(channalInfo.getChannelType()));
						}
					}
				}
				if (projectInfo.getTimeUnit() == TimeUnitEnum.YEAR) {
					projectDataInfoDO.setTimeUnit(TimeUnitEnum.MONTH.code());
					projectDataInfoDO.setTimeLimit(projectInfo.getTimeLimit() * 12);
				} else if (projectInfo.getTimeUnit() == TimeUnitEnum.DAY) {
					projectDataInfoDO.setTimeUnit(TimeUnitEnum.MONTH.code());
					projectDataInfoDO.setTimeLimit(projectInfo.getTimeLimit() / 30);
				}
				List<ProjectCreditConditionDO> conditionDOs = projectCreditConditionDAO
					.findByProjectCode(projectCode);
				if (ListUtil.isNotEmpty(conditionDOs)) {
					String creditData = "";
					for (ProjectCreditConditionDO conditionDO : conditionDOs) {
						//资产类型：动产 - 车辆 - 普通车辆； 权利人：小从v不想从v吧； 评估价格：6,789.00元； 抵押率：45.0%
						String itemDesc = conditionDO.getItemDesc();
						String itemType = "";
						if (StringUtil.isNotBlank(itemDesc)) {
							String[] array = itemDesc.split("；");
							if (array.length > 0) {
								String[] itemTypeArray = array[0].split("：");
								if (itemTypeArray.length > 1) {
									String[] itemTypeChild = itemTypeArray[1].split("-");
									if (itemTypeChild.length > 0) {
										itemType = itemTypeChild[itemTypeChild.length - 1];
									}
								}
							}
						}
						CreditConditionTypeEnum creditConditionTypeEnum = CreditConditionTypeEnum
							.getByCode(conditionDO.getType());
						if (creditConditionTypeEnum != null && StringUtil.isNotBlank(itemType)) {
							if (creditData.length() == 0) {
								creditData = itemType + "(" + creditConditionTypeEnum.getMessage()
												+ ")";
							} else {
								creditData += "," + itemType + "("
												+ creditConditionTypeEnum.getMessage() + ")";
							}
						}
						
					}
					projectDataInfoDO.setFanGuaranteeMethord(creditData);
				}
				Date now = getSysdate();
				Calendar nowCalendar = Calendar.getInstance();
				nowCalendar.setTime(now);
				nowCalendar.add(Calendar.HOUR_OF_DAY, -6);
				now = DateUtil.getStartTimeOfTheDate(nowCalendar.getTime());
				boolean isAdd = false;
				ProjectDataInfoHisDataDO dataInfoHisDataDO = projectDataInfoHisDataDAO
					.findByProjectCode(projectCode, now);
				if (dataInfoHisDataDO == null) {
					dataInfoHisDataDO = new ProjectDataInfoHisDataDO();
					dataInfoHisDataDO.setProjectDate(now);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					dataInfoHisDataDO.setYear(calendar.get(Calendar.YEAR));
					dataInfoHisDataDO.setMonth(calendar.get(Calendar.MONTH) + 1);
					dataInfoHisDataDO.setDay(calendar.get(Calendar.DAY_OF_MONTH) + 1);
					isAdd = true;
					BeanCopier.staticCopy(projectDataInfoDO, dataInfoHisDataDO);
					dataInfoHisDataDO.setId(0);
				} else {
					long oldId = dataInfoHisDataDO.getId();
					BeanCopier.staticCopy(projectDataInfoDO, dataInfoHisDataDO);
					dataInfoHisDataDO.setId(oldId);
				}
				
				if (dataIsNew) {
					projectDataInfoDAO.insert(projectDataInfoDO);
				} else {
					projectDataInfoDAO.update(projectDataInfoDO);
				}
				if (isAdd) {
					projectDataInfoHisDataDAO.insert(dataInfoHisDataDO);
				} else
					projectDataInfoHisDataDAO.update(dataInfoHisDataDO);
			}
			baseResult.setSuccess(true);
			return baseResult;
		} catch (Exception e) {
			baseResult.setMessage(e.getMessage());
			baseResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			logger.error("异步处理projectCode={}", projectCode, e);
		}
		return baseResult;
	}
	
	protected ProjectBean getProjectBean(ProjectInfo projectTemp) {
		
		ProjectBean projectBean = new ProjectBean();
		String capitalSubChannelName = null;
		String capitalChannelName = null;
		long capitalChannelId = 0;
		double guaranteeRate = 0.0;
		Money guaranteeFee = new Money();
		//一级项目渠道
		long projectChannelId = 0;
		String projectChannelName = null;
		//二级项目渠道名
		long projectSubChannelId = 0;
		String projectSubChannelName = null;
		
		if (ProjectUtil.isBond(projectTemp.getBusiType())) {
			//发债业务 12
			FCouncilSummaryProjectBondInfo councilSummaryProjectBondInfo = councilSummaryService
				.queryBondProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (councilSummaryProjectBondInfo != null) {
				capitalSubChannelName = councilSummaryProjectBondInfo.getCapitalSubChannelName();
				if (councilSummaryProjectBondInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = councilSummaryProjectBondInfo.getGuaranteeFee();
				} else if (councilSummaryProjectBondInfo.getGuaranteeFeeType() == ChargeTypeEnum.AMOUNT) {
					guaranteeFee = new Money(NumberUtil.format(
						councilSummaryProjectBondInfo.getGuaranteeFee(), "#####0.00"));
				}
				capitalChannelName = councilSummaryProjectBondInfo.getCapitalChannelName();
				capitalChannelId = councilSummaryProjectBondInfo.getCapitalChannelId();
			}
		} else if (ProjectUtil.isGuarantee(projectTemp.getBusiType())) {
			//担保 ！（5 211 12 4）
			FCouncilSummaryProjectGuaranteeInfo guaranteeInfo = councilSummaryService
				.queryGuaranteeProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (guaranteeInfo != null) {
				capitalSubChannelName = guaranteeInfo.getCapitalSubChannelName();
				if (guaranteeInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = guaranteeInfo.getGuaranteeFee();
				} else if (guaranteeInfo.getGuaranteeFeeType() == ChargeTypeEnum.AMOUNT) {
					guaranteeFee = new Money(NumberUtil.format(guaranteeInfo.getGuaranteeFee(),
						"#####0.00"));
				}
				capitalChannelName = guaranteeInfo.getCapitalChannelName();
				capitalChannelId = guaranteeInfo.getCapitalChannelId();
			}
			
		} else if (ProjectUtil.isEntrusted(projectTemp.getBusiType())) {
			// 委贷业务 4
			FCouncilSummaryProjectEntrustedInfo entrustedInfo = councilSummaryService
				.queryEntrustedProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (entrustedInfo != null) {
				capitalSubChannelName = entrustedInfo.getCapitalSubChannelName();
				capitalChannelName = entrustedInfo.getCapitalChannelName();
				capitalChannelId = entrustedInfo.getCapitalChannelId();
			}
			
		} else if (ProjectUtil.isLitigation(projectTemp.getBusiType())) {
			//诉讼保函业务 211
			FCouncilSummaryProjectLgLitigationInfo lgLitigationInfo = councilSummaryService
				.queryLgLitigationProjectCsByProjectCode(projectTemp.getProjectCode(), true);
			if (lgLitigationInfo != null) {
				if (lgLitigationInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
					guaranteeRate = lgLitigationInfo.getGuaranteeFee();
				} else if (lgLitigationInfo.getGuaranteeFeeType() == ChargeTypeEnum.AMOUNT) {
					guaranteeFee = new Money(NumberUtil.format(lgLitigationInfo.getGuaranteeFee(),
						"#####0.00"));
				}
				
			}
			
		}
		
		projectBean.setCapitalSubChannelName(capitalSubChannelName);
		projectBean.setDblGuaranteeRate(guaranteeRate);
		if (guaranteeRate > 0.0) {
			projectBean.setGuaranteeRate(NumberUtil.format(guaranteeRate, "0.###") + "%");
		} else {
			projectBean.setGuaranteeRate("");
		}
		
		FProjectGuaranteeEntrustedInfo infos = projectSetupService
			.queryGuaranteeEntrustedProjectByCode(projectTemp.getProjectCode());
		if (infos != null) {
			if (capitalChannelId == 0) {
				capitalChannelId = infos.getCapitalChannelId();
				capitalChannelName = infos.getCapitalChannelName();
				capitalSubChannelName = infos.getCapitalSubChannelName();
			}
			projectChannelId = infos.getProjectChannelId();
			projectChannelName = infos.getProjectChannelName();
			projectSubChannelId = infos.getProjectSubChannelId();
			projectSubChannelName = infos.getProjectSubChannelName();
		}
		
		projectBean.setGuaranteeFee(guaranteeFee);
		projectBean.setCapitalChannelId(capitalChannelId);
		projectBean.setCapitalChannelName(capitalChannelName);
		
		projectBean.setProjectChannelId(projectChannelId);
		projectBean.setProjectChannelName(projectChannelName);
		projectBean.setProjectSubChannelId(projectSubChannelId);
		projectBean.setProjectSubChannelName(projectSubChannelName);
		return projectBean;
	}
	
	private class ProjectBean implements Serializable {
		
		private static final long serialVersionUID = -4331782852566937069L;
		String capitalSubChannelName;
		String guaranteeRate;
		double dblGuaranteeRate;
		Money guaranteeFee = new Money();
		String capitalChannelName = null;
		long capitalChannelId;
		
		//一级项目渠道
		long projectChannelId;
		String projectChannelName;
		//二级项目渠道名
		long projectSubChannelId;
		String projectSubChannelName;
		
		public long getProjectChannelId() {
			return this.projectChannelId;
		}
		
		public void setProjectChannelId(long projectChannelId) {
			this.projectChannelId = projectChannelId;
		}
		
		public String getProjectChannelName() {
			return this.projectChannelName;
		}
		
		public void setProjectChannelName(String projectChannelName) {
			this.projectChannelName = projectChannelName;
		}
		
		public long getProjectSubChannelId() {
			return this.projectSubChannelId;
		}
		
		public void setProjectSubChannelId(long projectSubChannelId) {
			this.projectSubChannelId = projectSubChannelId;
		}
		
		public String getProjectSubChannelName() {
			return this.projectSubChannelName;
		}
		
		public void setProjectSubChannelName(String projectSubChannelName) {
			this.projectSubChannelName = projectSubChannelName;
		}
		
		public String getCapitalSubChannelName() {
			return this.capitalSubChannelName;
		}
		
		public void setCapitalSubChannelName(String capitalSubChannelName) {
			this.capitalSubChannelName = capitalSubChannelName;
		}
		
		public String getGuaranteeRate() {
			return this.guaranteeRate;
		}
		
		public void setGuaranteeRate(String guaranteeRate) {
			this.guaranteeRate = guaranteeRate;
		}
		
		public double getDblGuaranteeRate() {
			return this.dblGuaranteeRate;
		}
		
		public void setDblGuaranteeRate(double dblGuaranteeRate) {
			this.dblGuaranteeRate = dblGuaranteeRate;
		}
		
		public Money getGuaranteeFee() {
			return this.guaranteeFee;
		}
		
		public void setGuaranteeFee(Money guaranteeFee) {
			this.guaranteeFee = guaranteeFee;
		}
		
		public String getCapitalChannelName() {
			return this.capitalChannelName;
		}
		
		public void setCapitalChannelName(String capitalChannelName) {
			this.capitalChannelName = capitalChannelName;
		}
		
		public long getCapitalChannelId() {
			return this.capitalChannelId;
		}
		
		public void setCapitalChannelId(long capitalChannelId) {
			this.capitalChannelId = capitalChannelId;
		}
		
		/**
		 * @return
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
}
