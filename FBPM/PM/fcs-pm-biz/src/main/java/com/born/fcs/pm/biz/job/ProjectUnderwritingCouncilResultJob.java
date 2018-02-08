package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FInvestigationDAO;
import com.born.fcs.pm.dal.daointerface.FInvestigationUnderwritingDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 承销业务总经理办公会结果
 * 
 * @author wuzj
 *
 */
@Service("projectUnderwritingCouncilResultJob")
public class ProjectUnderwritingCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FInvestigationDAO FInvestigationDAO;
	
	@Autowired
	FInvestigationUnderwritingDAO FInvestigationUnderwritingDAO;
	
	@Autowired
	private CouncilProjectService councilProjectService;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	ForecastService forecastServiceClient;
	
	private Money ZERO_MONEY = Money.zero();
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		try {
			//Date now = new Date();
			//查询转让上会中的承销
			List<FInvestigationDO> applys = FInvestigationDAO.findCouncilWaitingApply(100);
			logger.info("扫描到上会中承销项目数量 ：{} ", applys == null ? 0 : applys.size());
			if (applys != null && applys.size() > 0) {
				for (FInvestigationDO apply : applys) {
					logger.info("扫描到上会中承销项目  projectCode ：{} ", apply.getProjectCode());
					//查询上会结果
					CouncilProjectInfo councilProject = null;
					try {
						councilProject = councilProjectService.getLastInfoByApplyId(apply
							.getCouncilApplyId());
					} catch (Exception e) {
						logger.error("获取上会结果出错：{}", e);
					}
					
					if (councilProject == null)
						continue;
					
					if (councilProject.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
						//会议通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
						FInvestigationDAO.update(apply);
						
						//项目通过 进入合同阶段
						ProjectDO proejctDO = projectDAO.findByProjectCode(apply.getProjectCode());
						proejctDO.setIsApproval(BooleanEnum.IS.code()); //项目已批复
						proejctDO.setApprovalTime(new Date());
						proejctDO.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
						proejctDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
						projectDAO.update(proejctDO);
						
						logger.info("承销项目上会通过 projectCode : {}", proejctDO.getProjectCode());
						
						syncForecast(apply);
					} else if (ProjectVoteResultEnum.END_NOPASS == councilProject
						.getProjectVoteResult()) {
						
						//会议不通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
						FInvestigationDAO.update(apply);
						
						//项目未通过 项目失败
						ProjectDO proejctDO = projectDAO.findByProjectCode(apply.getProjectCode());
						proejctDO.setIsApproval(BooleanEnum.NO.code());
						proejctDO.setApprovalTime(new Date());
						proejctDO.setPhasesStatus(ProjectPhasesStatusEnum.NOPASS.code());
						proejctDO.setStatus(ProjectStatusEnum.FAILED.code());
						if (proejctDO.getLastRecouncilTime() == null) { //只复议一次
							proejctDO.setIsRecouncil(BooleanEnum.IS.code());
						}
						projectDAO.update(proejctDO);
						logger.info("承销项目上会未通过  projectCode : {}", proejctDO.getProjectCode());
					}
				}
			}
		} catch (Exception e) {
			logger.error("扫描承销项目上会结果异常---异常原因：{}", e);
		}
		
	}
	
	/**
	 * 
	 * @param apply
	 */
	private void syncForecast(FInvestigationDO apply) {
		try {
			// 流入事由为“收取承销费”
			FInvestigationUnderwritingDO detail = FInvestigationUnderwritingDAO.findByFormId(apply
				.getFormId());
			ProjectDO project = projectDAO.findByProjectCode(apply.getProjectCode());
			Money underwritingMoney = Money.zero();
			if (StringUtil.equals(detail.getUnderwritingUnit(), ChargeTypeEnum.AMOUNT.code())) {
				underwritingMoney = Money.amout(String.valueOf(detail.getUnderwritingRate()));
			} else {
				underwritingMoney = detail.getFinancingAmount()
					.multiply(detail.getUnderwritingRate()).divide(100);
			}
			if (underwritingMoney.greaterThan(ZERO_MONEY)) {
				//查询预测规则  没查询到用默认规则
				SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
				SysForecastParamResult ruleResult = forecastServiceClient.findAll();
				if (ruleResult != null && ruleResult.isSuccess()) {
					rule = ruleResult.getParamAllInfo();
				}
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				Calendar calendar = Calendar.getInstance();
				if (rule.getInCxywTimeType() == TimeUnitEnum.YEAR) {
					calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getInCxywTime()));
				} else if (rule.getInCxywTimeType() == TimeUnitEnum.MONTH) {
					calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getInCxywTime()));
				} else if (rule.getInCxywTimeType() == TimeUnitEnum.DAY) {
					calendar.add(Calendar.DAY_OF_MONTH, NumberUtil.parseInt(rule.getInCxywTime()));
				}
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(underwritingMoney);
				forecastAccountOrder.setForecastMemo("收取承销费（" + apply.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_ZJRZDB);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.CXFEE.code());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.CXFEE);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				logger.info("承销业务上会通过后资金流入预测 ,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
		} catch (Exception e) {
			logger.error("承销上会通过后同步预测数据出错：{} ", e);
		}
	}
}
