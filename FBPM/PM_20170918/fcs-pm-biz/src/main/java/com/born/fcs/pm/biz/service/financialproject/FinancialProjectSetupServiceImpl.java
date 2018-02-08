package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.FinancialProductDO;
import com.born.fcs.pm.dataobject.FinancialProjectSetupFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectSetupService")
public class FinancialProjectSetupServiceImpl extends BaseFormAutowiredDomainService implements
																					FinancialProjectSetupService {
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	private ForecastService forecastServiceClient;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectSetupFormInfo> query(	FinancialProjectQueryOrder order) {
		
		QueryBaseBatchResult<FinancialProjectSetupFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectSetupFormInfo>();
		try {
			FinancialProjectSetupFormDO projectDO = new FinancialProjectSetupFormDO();
			BeanCopier.staticCopy(order, projectDO);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				projectDO.setSortCol("f.form_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				projectDO.setSortOrder("DESC");
			}
			
			projectDO.setFormStatus(order.getStatus());
			projectDO.setProductId(order.getProductId() == null ? 0 : order.getProductId());
			projectDO.setFormStatusList(order.getStatusList());
			projectDO
				.setCreateUserId(order.getCreateUserId() == null ? 0 : order.getCreateUserId());
			projectDO.setChooseForContract(order.getChooseForContract());
			projectDO.setCreateUserId(order.getBusiManagerId());
			long totalCount = extraDAO.searchFinancialProjectSetupFormCount(projectDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			projectDO.setLimitStart(component.getFirstRecord());
			projectDO.setPageSize(component.getPageSize());
			List<FinancialProjectSetupFormDO> list = extraDAO
				.searchFinancialProjectSetupForm(projectDO);
			
			List<FinancialProjectSetupFormInfo> pageList = new ArrayList<FinancialProjectSetupFormInfo>(
				list.size());
			for (FinancialProjectSetupFormDO DO : list) {
				FinancialProjectSetupFormInfo info = new FinancialProjectSetupFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
				info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
				info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(DO.getCouncilStatus()));
				info.setCouncilType(ProjectCouncilEnum.getByCode(DO.getCouncilType()));
				info.setCanRedeem(BooleanEnum.getByCode(DO.getCanRedeem()));
				info.setIsRoll(BooleanEnum.getByCode(DO.getIsRoll()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目申请失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
		
	}
	
	/**
	 * 生成理财立项编号
	 * @param busiType
	 * @return
	 */
	private synchronized String genProjectCode(String deptCode) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		deptCode = (deptCode == null ? "" : deptCode);
		String projectCode = "F" + year + "-" + StringUtil.leftPad(deptCode, 3, "0");
		String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + projectCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialOrder order) {
		return commonFormSaveProcess(order, "保存理财项目信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FinancialProductDO product = null;
				if (order.getProductId() != null && order.getProductId() > 0) {
					product = financialProductDAO.findById(order.getProductId());
					if (product == null
						|| FinancialProductSellStatusEnum.STOP.code().equals(
							product.getSellStatus())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"理财产品不存在或已停售");
					}
				} else {
					if (StringUtil.isNotBlank(order.getProductName())
						&& StringUtil.isNotBlank(order.getIssueInstitution())
						&& StringUtil.isNotBlank(order.getProductType())) {
						FinancialProductDO areadyExists = financialProductDAO.findByUnique(order
							.getProductName());
						if (areadyExists != null) {
							FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get()
								.getAttribute("result");
							//已经存在的产品ID
							result.setKeyId(areadyExists.getProductId());
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "产品已存在");
						}
					}
				}
				
				FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(form.getFormId());
				boolean isUpdate = false;
				/*新增*/
				if (project == null) {
					project = new FProjectFinancialDO();
					BeanCopier.staticCopy(order, project, UnBoxingConverter.getInstance());
					project.setCreateUserId(order.getUserId());
					project.setCreateUserName(order.getUserName());
					project.setCreateUserAccount(order.getUserAccount());
					project.setProjectCode(genProjectCode(order.getDeptCode()));
					project.setFormId(form.getFormId());
					project.setRawAddTime(nowDate);
				} else {/*修改*/
					isUpdate = true;
					long id = project.getId();
					String projectCode = project.getProjectCode();
					BeanCopier.staticCopy(order, project, UnBoxingConverter.getInstance());
					project.setId(id);
					project.setProjectCode(projectCode);
					project.setCreateUserId(order.getUserId());
					project.setCreateUserName(order.getUserName());
					project.setCreateUserAccount(order.getUserAccount());
				}
				//复制产品信息
				if (product != null) {
					project.setProductId(product.getProductId());
					project.setProductType(product.getProductType());
					project.setProductName(product.getProductName());
					project.setInterestType(product.getInterestType());
					project.setTermType(product.getTermType());
					project.setTimeLimit(product.getTimeLimit());
					project.setTimeUnit(product.getTimeUnit());
					project.setIssueInstitution(product.getIssueInstitution());
					project.setInterestRate(product.getInterestRate());
					project.setRateRangeStart(product.getRateRangeStart());
					project.setRateRangeEnd(product.getRateRangeEnd());
					project.setInterestSettlementWay(product.getInterestSettlementWay());
					project.setRiskLevel(product.getRiskLevel());
					project.setPrice(product.getPrice());
					project.setYearDayNum(product.getYearDayNum());
				}
				
				if (StringUtil.isBlank(order.getIsRoll())) {
					project.setIsRoll(BooleanEnum.NO.code());
				}
				
				if (isUpdate) {
					FProjectFinancialDAO.update(project);
				} else {
					FProjectFinancialDAO.insert(project);
				}
				
				//保存业务经理到相关人员表
				ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
				BeanCopier.staticCopy(form, relatedUser);
				relatedUser.setProjectCode(project.getProjectCode());
				relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
				relatedUser.setRemark("理财项目业务经理");
				projectRelatedUserService.setRelatedUser(relatedUser);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult syncForecast(String projectCode) {
		FcsBaseResult result = createResult();
		try {
			
			FProjectFinancialDO project = FProjectFinancialDAO.findByProjectCode(projectCode);
			if (project != null) {
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(project.getExpectBuyDate());
				forecastAccountOrder.setAmount(project.getPrice().multiply(
					project.getExpectBuyNum()));
				forecastAccountOrder.setForecastMemo("理财产品购买（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_LCYW);
				//设置中长期属性
				forecastAccountOrder.setForecastChildTypeOne(ForecastChildTypeOneEnum
					.getByCode(project.getTermType()));
				//设置产品类型
				forecastAccountOrder.setForecastChildTypeTwo(ForecastChildTypeTwoEnum
					.getByCode(project.getProductType()));
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.BUY.code());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getProductId());
				forecastAccountOrder.setCustomerName(project.getProductName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.BUY);
				logger.info("理财立项通过后资金流出预测,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据到资金系统出错");
			logger.error("同步预测数据到资金系统出错：{}", e);
		}
		return result;
	}
	
	@Override
	public FProjectFinancialInfo queryByFormId(long formId) {
		return convertFinancialProjectDO2Info(FProjectFinancialDAO.findByFormId(formId));
	}
	
	@Override
	public FProjectFinancialInfo queryByProjectCode(String projectCode) {
		return convertFinancialProjectDO2Info(FProjectFinancialDAO.findByProjectCode(projectCode));
	}
	
	private FProjectFinancialInfo convertFinancialProjectDO2Info(FProjectFinancialDO DO) {
		if (DO == null)
			return null;
		FProjectFinancialInfo info = new FProjectFinancialInfo();
		BeanCopier.staticCopy(DO, info);
		info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
		info.setInterestSettlementWay(InterestSettlementWayEnum.getByCode(DO
			.getInterestSettlementWay()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setTermType(FinancialProductTermTypeEnum.getByCode(DO.getTermType()));
		info.setInterestType(FinancialProductInterestTypeEnum.getByCode(DO.getInterestType()));
		info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(DO.getCouncilStatus()));
		info.setCouncilType(ProjectCouncilEnum.getByCode(DO.getCouncilType()));
		info.setCanRedeem(BooleanEnum.getByCode(DO.getCanRedeem()));
		info.setIsRoll(BooleanEnum.getByCode(DO.getIsRoll()));
		return info;
	}
	
}
