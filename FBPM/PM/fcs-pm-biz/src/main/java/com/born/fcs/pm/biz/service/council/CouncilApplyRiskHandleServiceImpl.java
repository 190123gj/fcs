package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FCouncilApplyCreditCompensationDO;
import com.born.fcs.pm.dal.dataobject.FCouncilApplyCreditDO;
import com.born.fcs.pm.dal.dataobject.FCouncilApplyRiskHandleDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.CouncilApplyRiskHandleDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyCreditCompensationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyCreditInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyCreditCompensationOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyCreditOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyRiskHandleOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyRiskHandleService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 上会申报
 * 
 * @author lirz
 * 
 * 2016-4-19 下午5:10:15
 */
@Service("councilApplyRiskHandleService")
public class CouncilApplyRiskHandleServiceImpl extends BaseFormAutowiredDomainService implements
																						CouncilApplyRiskHandleService {
	
	@Override
	public FormBaseResult saveCouncilApplyRiskHandle(final FCouncilApplyRiskHandleOrder order) {
		return commonFormSaveProcess(order, "保存上会申报", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectProcessing(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				long id = order.getId();
				FCouncilApplyRiskHandleDO doObj = FCouncilApplyRiskHandleDAO.findByFormId(form
					.getFormId());
				if (null == doObj) {
					doObj = new FCouncilApplyRiskHandleDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setRawAddTime(now);
					id = FCouncilApplyRiskHandleDAO.insert(doObj);
				} else {
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					FCouncilApplyRiskHandleDAO.update(doObj);
					id = doObj.getId();
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(id);
				
				saveHandleCredits(id, order.getCredits(), now);
				saveHandleCreditCompensations(id, order.getCreditCompensationOrders(), now);
				return null;
			}
		}, null, null);
	}
	
	private void saveHandleCredits(long handleId, List<FCouncilApplyCreditOrder> orders, Date now) {
		if (handleId <= 0 || ListUtil.isEmpty(orders)) {
			return;
		}
		List<FCouncilApplyCreditDO> doList = FCouncilApplyCreditDAO.findByHandleId(handleId);
		Map<Long, FCouncilApplyCreditDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(doList)) {
			for (FCouncilApplyCreditDO doObj : doList) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		for (FCouncilApplyCreditOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			if (order.getId() > 0) {
				FCouncilApplyCreditDO doObj = map.get(order.getId());
				if (null == doObj) {
					continue;
				}
				
				orderCovertDO(order, doObj);
				doObj.setHandleId(handleId);
				doObj.setSortOrder(sortOrder++);
				FCouncilApplyCreditDAO.update(doObj);
				map.remove(order.getId()); //移除处理过的数据
			} else {
				FCouncilApplyCreditDO doObj = new FCouncilApplyCreditDO();
				orderCovertDO(order, doObj);
				doObj.setHandleId(handleId);
				doObj.setSortOrder(sortOrder++);
				doObj.setRawAddTime(now);
				FCouncilApplyCreditDAO.insert(doObj);
			}
		}
		
		//删除的数据
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FCouncilApplyCreditDAO.deleteById(id);
			}
		}
	}
	
	private void saveHandleCreditCompensations(long handleId,
												List<FCouncilApplyCreditCompensationOrder> orders,
												Date now) {
		if (handleId <= 0 || ListUtil.isEmpty(orders)) {
			return;
		}
		List<FCouncilApplyCreditCompensationDO> doList = fCouncilApplyCreditCompensationDAO
			.findByHandleId(handleId);
		Map<Long, FCouncilApplyCreditCompensationDO> map = new HashMap<Long, FCouncilApplyCreditCompensationDO>();
		if (ListUtil.isNotEmpty(doList)) {
			for (FCouncilApplyCreditCompensationDO doObj : doList) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		for (FCouncilApplyCreditCompensationOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			if (order.getId() > 0) {
				FCouncilApplyCreditCompensationDO doObj = map.get(order.getId());
				if (null == doObj) {
					continue;
				}
				
				compensationOrderCovertDO(order, doObj);
				doObj.setHandleId(handleId);
				doObj.setSortOrder(sortOrder++);
				fCouncilApplyCreditCompensationDAO.update(doObj);
				map.remove(order.getId()); //移除处理过的数据
			} else {
				FCouncilApplyCreditCompensationDO doObj = new FCouncilApplyCreditCompensationDO();
				compensationOrderCovertDO(order, doObj);
				doObj.setHandleId(handleId);
				doObj.setSortOrder(sortOrder++);
				doObj.setRawAddTime(now);
				fCouncilApplyCreditCompensationDAO.insert(doObj);
			}
		}
		
		//删除的数据
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				fCouncilApplyCreditCompensationDAO.deleteById(id);
			}
		}
	}
	
	private void orderCovertDO(FCouncilApplyCreditOrder order, FCouncilApplyCreditDO doObj) {
		BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
		doObj.setJsonData(order.getJsonData().toJSONString());
	}
	
	private void compensationOrderCovertDO(FCouncilApplyCreditCompensationOrder order,
											FCouncilApplyCreditCompensationDO doObj) {
		BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
		doObj.setJsonData(order.getJsonData().toJSONString());
	}
	
	@Override
	public FCouncilApplyRiskHandleInfo findById(long id) {
		return findByRiskHandle(FCouncilApplyRiskHandleDAO.findById(id));
	}
	
	@Override
	public FCouncilApplyRiskHandleInfo findByFormId(long formId) {
		return findByRiskHandle(FCouncilApplyRiskHandleDAO.findByFormId(formId));
	}
	
	private FCouncilApplyRiskHandleInfo findByRiskHandle(FCouncilApplyRiskHandleDO doObj) {
		if (null == doObj) {
			return null;
		}
		
		FCouncilApplyRiskHandleInfo info = new FCouncilApplyRiskHandleInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setGuaranteeRateType(ChargeTypeEnum.getByCode(doObj.getGuaranteeRateType()));
		
		List<FCouncilApplyCreditDO> doList = FCouncilApplyCreditDAO.findByHandleId(doObj.getId());
		if (ListUtil.isNotEmpty(doList)) {
			List<FCouncilApplyCreditInfo> credits = new ArrayList<>();
			for (FCouncilApplyCreditDO credit : doList) {
				FCouncilApplyCreditInfo creditInfo = new FCouncilApplyCreditInfo();
				BeanCopier.staticCopy(credit, creditInfo);
				creditInfo.setJsonObject(MiscUtil.getJsonObjByParseJSON(credit.getJsonData()));
				credits.add(creditInfo);
			}
			info.setCredits(credits);
		}
		List<FCouncilApplyCreditCompensationDO> compensationDOList = fCouncilApplyCreditCompensationDAO
			.findByHandleId(doObj.getId());
		if (ListUtil.isNotEmpty(compensationDOList)) {
			List<FCouncilApplyCreditCompensationInfo> credits = new ArrayList<>();
			for (FCouncilApplyCreditCompensationDO credit : compensationDOList) {
				FCouncilApplyCreditCompensationInfo creditInfo = new FCouncilApplyCreditCompensationInfo();
				BeanCopier.staticCopy(credit, creditInfo);
				creditInfo.setJsonData(MiscUtil.getJsonObjByParseJSON(credit.getJsonData()));
				credits.add(creditInfo);
			}
			info.setCreditCompensationInfos(credits);
		}
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<CouncilApplyRiskHandleInfo> queryList(	CouncilApplyRiskHandleQueryOrder queryOrder) {
		QueryBaseBatchResult<CouncilApplyRiskHandleInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerName", queryOrder.getBusiManagerName());
		if (null != queryOrder.getProjectStatus()) {
			paramMap.put("status", queryOrder.getProjectStatus().code());
		}
		if (null != queryOrder.getPhases()) {
			paramMap.put("phases", queryOrder.getPhases().code());
		}
		paramMap.put("isRepay", queryOrder.getIsRepay());
		paramMap.put("formStatus", queryOrder.getFormStatus());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("draftUserId", queryOrder.getDraftUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		long totalSize = extraDAO.searchCouncilApplyRiskHandleCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
		}
		List<CouncilApplyRiskHandleDO> pageList = extraDAO.searchCouncilApplyRiskHandle(paramMap);
		
		List<CouncilApplyRiskHandleInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (CouncilApplyRiskHandleDO doObj : pageList) {
				CouncilApplyRiskHandleInfo info = new CouncilApplyRiskHandleInfo();
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
	public QueryBaseBatchResult<ProjectInfo> queryProject(QueryProjectBase queryOrder) {
		//当选择代偿上会，上会申报表中的客户名称自动带出，项目筛选范围为已经有放款记录且未全部解保的项目
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		
		long totalSize = extraDAO.searchRepayProjectCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			List<ProjectDO> pageList = extraDAO.searchRepayProjectList(paramMap);
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectDO doObj : pageList) {
					ProjectInfo info = DoConvert.convertProjectDO2Info(doObj);
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryNoRepayProject(ProjectQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		List<String> phases = Lists.newArrayList();
		for (ProjectPhasesEnum s : queryOrder.getPhasesList()) {
			phases.add(s.getCode());
		}
		paramMap.put("phases", phases);
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		paramMap.put("relatedRoleList", queryOrder.getRelatedRoleList());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		
		long totalSize = extraDAO.searchNoRepayProjectCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
		}
		List<ProjectDO> pageList = extraDAO.searchNoRepayProjectList(paramMap);
		
		List<ProjectInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectDO doObj : pageList) {
				ProjectInfo info = DoConvert.convertProjectDO2Info(doObj);
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
}
