package com.born.fcs.pm.biz.service.riskwarning;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FRiskWarningCreditDO;
import com.born.fcs.pm.dal.dataobject.FRiskWarningDO;
import com.born.fcs.pm.dataobject.RiskWarningDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningCreditInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningCreditOrder;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("riskWarningService")
public class RiskWarningServiceImpl extends BaseFormAutowiredDomainService implements
																			RiskWarningService {
	
	@Override
	public FormBaseResult save(final FRiskWarningOrder order) {
		return commonFormSaveProcess(order, "增加或修改风险预警项目信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				long warningId = order.getWarningId();
				if (warningId <= 0) {
					FRiskWarningDO doObj = new FRiskWarningDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setSignalLevel(order.getSignalLevelStr());
					doObj.setRawAddTime(now);
					warningId = FRiskWarningDAO.insert(doObj);
				} else {
					FRiskWarningDO doObj = FRiskWarningDAO.findById(order.getWarningId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setSignalLevel(order.getSignalLevelStr());
					FRiskWarningDAO.update(doObj);
				}
				
				//保存客户授信业务基本情况
				saveWarningCredits(warningId, order.getWarningCredits(), now);
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 保存客户授信业务基本情况
	 * 
	 * @param warningId 风险id
	 * @param orders 参数order列表
	 * @param now 当前时间
	 */
	private void saveWarningCredits(long warningId, List<FRiskWarningCreditOrder> orders, Date now) {
		if (warningId <= 0 || ListUtil.isEmpty(orders)) {
			return;
		}
		List<FRiskWarningCreditDO> doList = FRiskWarningCreditDAO.findByWarningId(warningId);
		Map<Long, FRiskWarningCreditDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(doList)) {
			for (FRiskWarningCreditDO doObj : doList) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		for (FRiskWarningCreditOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			order.setSortOrder(sortOrder++);
			order.setWarningId(warningId);
			FRiskWarningCreditDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FRiskWarningCreditDO();
				covertOrderToDO(order, doObj);
				doObj.setRawAddTime(now);
				FRiskWarningCreditDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					covertOrderToDO(order, doObj);
					FRiskWarningCreditDAO.update(doObj);
				}
			}
			
			map.remove(order.getId()); //移除处理过的数据
			
		}
		
		//删除的数据
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FRiskWarningCreditDAO.deleteById(id);
			}
		}
	}
	
	private void covertOrderToDO(FRiskWarningCreditOrder order, FRiskWarningCreditDO doObj) {
		BeanCopier.staticCopy(order, doObj);
		doObj.setLoanAmount(order.getLoanAmount());
		if (order.getHasRepayPlan() != null)
			doObj.setHasRepayPlan(order.getHasRepayPlan().code());
		if (order.getJsonObject() != null) {
			doObj.setJosnData(order.getJsonObject().toJSONString());
		}
	}
	
	private boolean isEqual(FRiskWarningCreditOrder order, FRiskWarningCreditDO item) {
		return StringUtil.equals(DateUtil.dtSimpleFormat(order.getIssueDate()),
			DateUtil.dtSimpleFormat(item.getIssueDate()))
				&& StringUtil.equals(DateUtil.dtSimpleFormat(order.getExpireDate()),
					DateUtil.dtSimpleFormat(item.getExpireDate()))
				&& order.getLoanAmount().equals(item.getLoanAmount())
				&& order.getDebitInterest().equals(item.getDebitInterest())
				&& order.getSortOrder() == item.getSortOrder()
				&& StringUtil.equals(order.getJsonObject().toJSONString(), item.getJosnData());
	}
	
	@Override
	public FRiskWarningInfo findById(long id) {
		return findByRiskWarning(FRiskWarningDAO.findById(id));
	}
	
	@Override
	public FRiskWarningInfo findByFormId(long formId) {
		return findByRiskWarning(FRiskWarningDAO.findByFormId(formId));
	}
	
	private FRiskWarningInfo findByRiskWarning(FRiskWarningDO doObj) {
		if (null == doObj) {
			return null;
		}
		FRiskWarningInfo info = new FRiskWarningInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setSignalLevel(SignalLevelEnum.getByCode(doObj.getSignalLevel()));
		List<FRiskWarningCreditDO> doList = FRiskWarningCreditDAO.findByWarningId(doObj
			.getWarningId());
		if (ListUtil.isNotEmpty(doList)) {
			List<FRiskWarningCreditInfo> credits = new ArrayList<>(doList.size());
			for (FRiskWarningCreditDO creditDO : doList) {
				FRiskWarningCreditInfo creditInfo = new FRiskWarningCreditInfo();
				BeanCopier.staticCopy(creditDO, creditInfo);
				creditInfo.setHasRepayPlan(BooleanEnum.getByCode(creditDO.getHasRepayPlan()));
				creditInfo.setJsonObject(MiscUtil.getJsonObjByParseJSON(creditDO.getJosnData()));
				credits.add(creditInfo);
			}
			info.setCredits(credits);
		}
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<RiskWarningInfo> queryList(RiskWarningQueryOrder queryOrder) {
		QueryBaseBatchResult<RiskWarningInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("warningId", queryOrder.getWarningId());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("customerNameFull", queryOrder.getCustomerNameFull());
		paramMap.put("warningBillType", queryOrder.getWarningBillType());
		paramMap.put("formStatus", queryOrder.getFormStatus());
		paramMap.put("submitUserName", queryOrder.getSubmitUserName());
		paramMap.put("signalLevel", queryOrder.getSignalLevel());
		paramMap.put("sortCol", queryOrder.getSortCol());
		paramMap.put("sortOrder", queryOrder.getSortOrder());
		paramMap.put("draftUserId", queryOrder.getDraftUserId());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
		long totalSize = extraDAO.searchRiskWarningCount(paramMap);
		PageComponent component = new PageComponent(queryOrder, totalSize);
		paramMap.put("limitStart", component.getFirstRecord());
		paramMap.put("pageSize", component.getPageSize());
		
		if (totalSize > 0) {
			List<RiskWarningDO> pageList = extraDAO.searchRiskWarning(paramMap);
			
			List<RiskWarningInfo> list = baseBatchResult.getPageList();
			for (RiskWarningDO sf : pageList) {
				RiskWarningInfo info = new RiskWarningInfo();
				BeanCopier.staticCopy(sf, info);
				info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
				info.setSignalLevel(SignalLevelEnum.getByCode(sf.getSignalLevel()));
				list.add(info);
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
}
