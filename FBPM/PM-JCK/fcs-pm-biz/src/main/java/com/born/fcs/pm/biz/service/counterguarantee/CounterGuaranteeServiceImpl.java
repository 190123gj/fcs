package com.born.fcs.pm.biz.service.counterguarantee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.GuaranteeApplyCounterDAO;
import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeReleaseDO;
import com.born.fcs.pm.dal.dataobject.GuaranteeApplyCounterDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.ReleaseApplyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeReleaseInfo;
import com.born.fcs.pm.ws.info.counterguarantee.GuaranteeApplyCounterInfo;
import com.born.fcs.pm.ws.info.counterguarantee.ReleaseApplyInfo;
import com.born.fcs.pm.ws.order.counterguarantee.CounterGuaranteeQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CreditConditionReleaseOrder;
import com.born.fcs.pm.ws.order.counterguarantee.FCounterGuaranteeReleaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 到期解保
 * 
 * @author lirz
 * 
 * 2016-5-12 下午6:39:04
 */
@Service("counterGuaranteeService")
public class CounterGuaranteeServiceImpl extends BaseFormAutowiredDomainService implements
																				CounterGuaranteeService {
	@Autowired
	private ProjectCreditConditionService projectCreditConditionService;
	
	@Autowired
	private GuaranteeApplyCounterDAO guaranteeApplyCounterDAO;
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FormBaseResult save(final FCounterGuaranteeReleaseOrder order) {
		return commonFormSaveProcess(order, "保存解保申请", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FcsBaseResult result = canRelease(order);
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(),
						result.getMessage());
				}
				
				FCounterGuaranteeReleaseDO doObj = null;
				if (null != order.getFormId() && order.getFormId() > 0) {
					doObj = FCounterGuaranteeReleaseDAO.findByFormId(order.getFormId());
				}
				long releaseId = order.getId();
				if (null == doObj) {
					doObj = new FCounterGuaranteeReleaseDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setApplyAmount(order.getApplyAmount());
					doObj.setFormId(form.getFormId());
					doObj.setRawAddTime(now);
					releaseId = FCounterGuaranteeReleaseDAO.insert(doObj);
				} else {
					if (releaseId != doObj.getId()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"表单已经过期，请刷新页面重新操作");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setApplyAmount(order.getApplyAmount());
					FCounterGuaranteeReleaseDAO.update(doObj);
				}
				
				saveReleaseConditions(releaseId, order.getReleases());
				
				FormBaseResult result2 = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result2.setKeyId(releaseId);
				return null;
			}
		}, null, null);
	}
	
	private void saveReleaseConditions(long releaseId, List<CreditConditionReleaseOrder> orders) {
		if (ListUtil.isNotEmpty(orders)) {
			for (CreditConditionReleaseOrder order : orders) {
				if (order.getId() > 0) {
					order.setReleaseId(releaseId);
					projectCreditConditionService.saveCreditConditionRelease(order);
				}
			}
		}
	}
	
	@Override
	public FCounterGuaranteeReleaseInfo findByFormId(long formId) {
		FCounterGuaranteeReleaseDO doObj = FCounterGuaranteeReleaseDAO.findByFormId(formId);
		if (null == doObj) {
			return null;
		} else {
			FCounterGuaranteeReleaseInfo info = new FCounterGuaranteeReleaseInfo();
			BeanCopier.staticCopy(doObj, info);
			info.setTimeUnit(TimeUnitEnum.getByCode(doObj.getTimeUnit()));
			return info;
		}
	}
	
	@Override
	public QueryBaseBatchResult<ReleaseApplyInfo> queryList(CounterGuaranteeQueryOrder queryOrder) {
		QueryBaseBatchResult<ReleaseApplyInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerName", queryOrder.getBusiManagerName());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
		if (null != queryOrder.getProjectStatus()) {
			paramMap.put("status", queryOrder.getProjectStatus().code());
		}
		if (null != queryOrder.getPhases()) {
			paramMap.put("phases", queryOrder.getPhases().code());
		}
		if (null != queryOrder.getFormStatus()) {
			paramMap.put("formStatus", queryOrder.getFormStatus().code());
		}
		
		long totalSize = extraDAO.searchReleaseApplyCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
		}
		List<ReleaseApplyDO> pageList = extraDAO.searchReleaseApply(paramMap);
		
		List<ReleaseApplyInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ReleaseApplyDO doObj : pageList) {
				ReleaseApplyInfo info = new ReleaseApplyInfo();
				BeanCopier.staticCopy(doObj, info);
				info.setFormCode(FormCodeEnum.getByCode(doObj.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(doObj.getFormStatus()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(doObj.getProjectStatus()));
				info.setPhases(ProjectPhasesEnum.getByCode(doObj.getPhases()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public Money queryReleasedAmount(String projectCode) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", projectCode);
		paramMap.put("formStatus", FormStatusEnum.APPROVAL.code());
		Long amount = extraDAO.searchReleaseAmount(paramMap);
		if (null == amount) {
			amount = 0L;
		}
		return new Money(amount / 100, (int) (amount % 100));
	}
	
	@Override
	public Money queryReleasingAmount(String projectCode) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", projectCode);
		List<String> formStatusList = new ArrayList<>();
		formStatusList.add(FormStatusEnum.SUBMIT.code());
		formStatusList.add(FormStatusEnum.CANCEL.code());
		formStatusList.add(FormStatusEnum.AUDITING.code());
		formStatusList.add(FormStatusEnum.BACK.code());
		paramMap.put("formStatusList", formStatusList);
		Long amount = extraDAO.searchReleaseAmount(paramMap);
		if (null == amount) {
			amount = 0L;
		}
		return new Money(amount / 100, (int) (amount % 100));
	}
	
	@Override
	public FcsBaseResult canRelease(FCounterGuaranteeReleaseOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		result.setSuccess(false);
		
		if (null == order || StringUtil.isBlank(order.getProjectCode())) {
			result.setSuccess(false);
			result.setMessage("缺少必要的参数");
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			return result;
		}
		
		if (ProjectUtil.isLitigation(order.getBusiType())) {
			result.setSuccess(true);
			return result;
		}
		
		if (null == order.getApplyAmount() || !order.getApplyAmount().greaterThan(new Money(0L))) {
			result.setSuccess(false);
			result.setMessage("申请解保金额必须大于0");
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			return result;
		}
		
		ProjectDO project = projectDAO.findByProjectCode(order.getProjectCode());
		if (null == project) {
			result.setSuccess(false);
			result.setMessage("没有找到项目信息");
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			return result;
		}
		
		Money appliedAmount = new Money(0L);
		if (order.getFormId() > 0) {
			FCounterGuaranteeReleaseDO doObj = FCounterGuaranteeReleaseDAO.findByFormId(order
				.getFormId());
			if (null != doObj) {
				appliedAmount = doObj.getApplyAmount();
			}
		}
		
		//冻结金额=已解保金额+解保中的金额
		Money frozonAmount = queryReleasedAmount(order.getProjectCode()).add(
			queryReleasingAmount(order.getProjectCode())).subtract(appliedAmount);
		Money availableAmount = project.getReleasableAmount().subtract(frozonAmount);
		if (order.getApplyAmount().greaterThan(availableAmount)) {
			result.setSuccess(false);
			result.setMessage("超过了最大可解保金额");
			result.setFcsResultEnum(FcsResultEnum.WRONG_REQ_PARAM);
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(QueryProjectBase queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		
		long totalSize = extraDAO.searchAvailableReleaseSelectProjectCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			List<ProjectDO> pageList = extraDAO.searchAvailableReleaseSelectProject(paramMap);
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectDO doObj : pageList) {
					list.add(DoConvert.convertProjectDO2Info(doObj));
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<GuaranteeApplyCounterInfo> queryCounts(long formId) {
		QueryBaseBatchResult<GuaranteeApplyCounterInfo> baseBatchResult = new QueryBaseBatchResult<>();
		List<GuaranteeApplyCounterDO> pageList = guaranteeApplyCounterDAO.findByFormId(formId);
		if (ListUtil.isNotEmpty(pageList)) {
			long totalSize = pageList.size();
			
			PageComponent component = new PageComponent(1, (int) totalSize);
			
			List<GuaranteeApplyCounterInfo> list = baseBatchResult.getPageList();
			for (GuaranteeApplyCounterDO doObj : pageList) {
				GuaranteeApplyCounterInfo info = new GuaranteeApplyCounterInfo();
				BeanCopier.staticCopy(doObj, info);
				list.add(info);
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			
		}
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
}
