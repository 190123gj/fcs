package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.FinancialProductDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dataobject.FinancialProjectSetupFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectSetupResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectSetupService")
public class FinancialProjectSetupServiceImpl extends BaseFormAutowiredDomainService implements
																					FinancialProjectSetupService {
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectSetupFormInfo> query(	FinancialProjectQueryOrder order) {
		QueryBaseBatchResult<FinancialProjectSetupFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectSetupFormInfo>();
		
		try {
			FinancialProjectSetupFormDO projectDO = new FinancialProjectSetupFormDO();
			BeanCopier.staticCopy(order, projectDO);
			projectDO.setFormStatus(order.getStatus());
			
			if (StringUtil.isBlank(order.getSortCol())) {
				projectDO.setSortCol("f.form_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				projectDO.setSortOrder("DESC");
			}
			
			projectDO
				.setCreateUserId(order.getCreateUserId() == null ? 0 : order.getCreateUserId());
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
	 * 生成理财项目编号
	 * @param busiType
	 * @return
	 */
	private synchronized String genProjectCode(String deptCode) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		deptCode = (deptCode == null ? "" : deptCode);
		String projectCode = "F-" + year + "-" + StringUtil.leftPad(deptCode, 3, "0");
		String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + projectCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	@Override
	public FinancialProjectSetupResult save(final FProjectFinancialOrder order) {
		return (FinancialProjectSetupResult) commonFormSaveProcess(order, "保存理财项目信息",
			new BeforeProcessInvokeService() {
				
				@SuppressWarnings("deprecation")
				@Override
				public Domain before() {
					
					final Date nowDate = FcsPmDomainHolder.get().getSysDate();
					
					//取得表单信息
					FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					
					if (form == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
					}
					
					FinancialProductDO product = financialProductDAO.findById(order.getProductId());
					if (product == null
						|| FinancialProductSellStatusEnum.STOP.code().equals(
							product.getSellStatus())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"理财产品不存在或已停售");
					}
					
					FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(form
						.getFormId());
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
						BeanCopier.staticCopy(order, project, UnBoxingConverter.getInstance());
						project.setId(id);
						project.setCreateUserId(order.getUserId());
						project.setCreateUserName(order.getUserName());
						project.setCreateUserAccount(order.getUserAccount());
					}
					
					//复制产品信息
					project.setProductId(product.getProductId());
					project.setProductType(product.getProductType());
					project.setProductName(product.getProductName());
					project.setInterestType(product.getInterestType());
					project.setTermType(product.getTermType());
					project.setTimeLimit(product.getTimeLimit());
					project.setTimeUnit(product.getTimeUnit());
					project.setIssueInstitution(product.getIssueInstitution());
					project.setInterestRate(product.getInterestRate());
					project.setInterestSettlementWay(product.getInterestSettlementWay());
					project.setRiskLevel(product.getRiskLevel());
					project.setPrice(product.getPrice());
					
					if (isUpdate) {
						FProjectFinancialDAO.update(project);
					} else {
						FProjectFinancialDAO.insert(project);
					}
					
					return null;
				}
			}, null, new AfterProcessInvokeService() {
				
				@Override
				public Domain after(Domain domain) {
					
					Date now = FcsPmDomainHolder.get().getSysDate();
					
					//保存项目信息
					FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					//重新查询表单项目信息
					FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(form
						.getFormId());
					//查询理财项目信息
					ProjectFinancialDO projectFinancial = projectFinancialDAO.findByCode(project
						.getProjectCode());
					
					//保存业务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					BeanCopier.staticCopy(form, relatedUser);
					relatedUser.setProjectCode(project.getProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
					relatedUser.setRemark("理财项目业务经理");
					projectRelatedUserService.setRelatedUser(relatedUser);
					
					//查询同一个产品是否是第一次购买
					ProjectFinancialDO queryDO = new ProjectFinancialDO();
					queryDO.setProductId(project.getProductId());
					List<String> status = Lists.newArrayList();
					List<FinancialProjectStatusEnum> aStatus = FinancialProjectStatusEnum
						.getAllEnum();
					for (FinancialProjectStatusEnum s : aStatus) { //以下状态不算购买过的
						if (s != FinancialProjectStatusEnum.SET_UP_DRAFT
							&& s != FinancialProjectStatusEnum.SET_UP_AUDITING
							&& s != FinancialProjectStatusEnum.SET_UP_APPROVAL
							&& s != FinancialProjectStatusEnum.SET_UP_APPROVAL
							&& s != FinancialProjectStatusEnum.COUNCIL_WAITING
							&& s != FinancialProjectStatusEnum.FAILED) {
							status.add(s.code());
						}
					}
					
					long buyTimes = projectFinancialDAO.findByConditionCount(queryDO, 0, null,
						status, null, null, null, null, null, null);
					
					boolean isUpdate = false;
					if (projectFinancial == null) {
						projectFinancial = new ProjectFinancialDO();
						BeanCopier.staticCopy(project, projectFinancial);
						project.setRawAddTime(now);
					} else {
						isUpdate = true;
						BeanCopier.staticCopy(project, projectFinancial);
					}
					
					FinancialProjectSetupResult result = (FinancialProjectSetupResult) FcsPmDomainHolder
						.get().getAttribute("result");
					
					// order.getCheckStatus() 前台传过来 0表示暂存 1表示提交 
					if (buyTimes > 0 && order.getCheckStatus() != null
						&& order.getCheckStatus() == 1) {//如果以前购买过改产品则直接线下待购买
					
						projectFinancial.setStatus(FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING
							.code());
						result.setFirstBuy(false);
						
						//表单直接通过,不走审核流程
						FormDO formDO = formDAO.findByFormId(form.getFormId());
						formDO.setStatus(FormStatusEnum.APPROVAL.code());
						formDAO.update(formDO);
					} else {
						projectFinancial.setStatus(FinancialProjectStatusEnum.SET_UP_DRAFT.code());
						if (buyTimes == 0)
							result.setFirstBuy(true);
					}
					
					if (project.getRawAddTime() == null)
						project.setRawAddTime(now);
					projectFinancial.setProjectName(project.getProductName()
													+ "-"
													+ DateUtil.dtSimpleFormat(project
														.getRawAddTime()));
					if (isUpdate) {
						projectFinancialDAO.update(projectFinancial);
					} else {
						projectFinancialDAO.insert(projectFinancial);
					}
					
					return null;
				}
			});
	}
	
	@Override
	public FProjectFinancialInfo queryByFormId(long formId) {
		return convertFinancialProjectDO2Info(FProjectFinancialDAO.findByFormId(formId));
	}
	
	@Override
	public FProjectFinancialInfo queryByProductId(long productId) {
		return convertFinancialProjectDO2Info(FProjectFinancialDAO.findById(productId));
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
		return info;
	}
	
	@Override
	protected FinancialProjectSetupResult createResult() {
		return new FinancialProjectSetupResult();
	}
}
