package com.born.fcs.pm.biz.service.basicmaintain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.FinancialProductDO;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductOrder;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProductService")
public class FinancialProductServiceImpl extends BaseAutowiredDomainService implements
																			FinancialProductService {
	@Autowired
	SysParameterService sysParameterService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	private FinancialProductInfo convertDO2Info(FinancialProductDO DO) {
		if (DO == null)
			return null;
		FinancialProductInfo info = new FinancialProductInfo();
		BeanCopier.staticCopy(DO, info);
		info.setInterestSettlementWay(InterestSettlementWayEnum.getByCode(DO
			.getInterestSettlementWay()));
		info.setSellStatus(FinancialProductSellStatusEnum.getByCode(DO.getSellStatus()));
		info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
		info.setTermType(FinancialProductTermTypeEnum.getByCode(DO.getTermType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setInterestType(FinancialProductInterestTypeEnum.getByCode(DO.getInterestType()));
		return info;
	}
	
	@Override
	public FinancialProductInfo findById(long productId) {
		FinancialProductInfo info = null;
		if (productId > 0) {
			FinancialProductDO DO = financialProductDAO.findById(productId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final FinancialProductOrder order) {
		
		return commonProcess(order, "保存理财产品", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				FinancialProductDO product = null;
				if (order.getProductId() != null && order.getProductId() > 0) {
					product = financialProductDAO.findById(order.getProductId());
					if (product == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"理财产品不存在");
					}
				}
				
				boolean isUpdate = false;
				if (product == null) { //新增
					product = new FinancialProductDO();
					BeanCopier.staticCopy(order, product, UnBoxingConverter.getInstance());
				} else { //修改
					isUpdate = true;
					BeanCopier.staticCopy(order, product, UnBoxingConverter.getInstance());
				}
				
				//计算中长期还是短期
				if (StringUtil.isBlank(product.getTermType())) {
					FinancialProductTermTypeEnum termType = calculateProductTermType(
						product.getTimeLimit(), product.getTimeUnit());
					product.setTermType(termType.code());
				}
				if (isUpdate) {
					financialProductDAO.update(product);
				} else {
					financialProductDAO.insert(product);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult changeSellStatus(final FinancialProductOrder order) {
		
		return commonProcess(order, "修改理财产品状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FinancialProductDO product = financialProductDAO.findById(order.getProductId());
				if (product == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财产品不存在");
				}
				
				//立项完成  立项审核中  上会中 资金划付中不能停售
				if (FinancialProductSellStatusEnum.STOP.code().equals(order.getSellStatus())) {
					FinancialProjectQueryOrder qOrder = new FinancialProjectQueryOrder();
					qOrder.setProductId(product.getProductId());
					List<String> statusList = Lists.newArrayList();
					statusList.add(FinancialProjectStatusEnum.SET_UP_DRAFT.code());
					statusList.add(FinancialProjectStatusEnum.SET_UP_AUDITING.code());
					statusList.add(FinancialProjectStatusEnum.SET_UP_APPROVAL.code());
					statusList.add(FinancialProjectStatusEnum.COUNCIL_WAITING.code());
					statusList.add(FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING.code());
					statusList.add(FinancialProjectStatusEnum.CAPITAL_APPLY_DRAFT.code());
					statusList.add(FinancialProjectStatusEnum.CAPITAL_APPLY_AUDITING.code());
					statusList.add(FinancialProjectStatusEnum.CAPITAL_APPLY_APPROVAL.code());
					statusList.add(FinancialProjectStatusEnum.SET_UP_DRAFT.code());
					statusList.add(FinancialProjectStatusEnum.SET_UP_DRAFT.code());
					qOrder.setStatusList(statusList);
					QueryBaseBatchResult<ProjectFinancialInfo> projectList = financialProjectService
						.query(qOrder);
					if (projectList.getTotalCount() > 0) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "当前产品正在申请购买，不能停售");
					}
				}
				
				product.setSellStatus(order.getSellStatus());
				financialProductDAO.update(product);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FinancialProductInfo> query(FinancialProductQueryOrder order) {
		QueryBaseBatchResult<FinancialProductInfo> baseBatchResult = new QueryBaseBatchResult<FinancialProductInfo>();
		
		FinancialProductDO queryCondition = new FinancialProductDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getProductId() != null)
			queryCondition.setProductId(order.getProductId());
		
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("product_id");
			order.setSortOrder("desc");
		}
		
		long totalSize = financialProductDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<FinancialProductDO> pageList = financialProductDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<FinancialProductInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FinancialProductDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FinancialProductTermTypeEnum calculateProductTermType(int timeLimit, String timeUnit) {
		
		String rule = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FINANCIAL_PRODUCT_SHORT_TERM_RULE.code());
		
		//默认短期
		FinancialProductTermTypeEnum termType = FinancialProductTermTypeEnum.SHORT_TERM;
		
		if (StringUtil.isNotBlank(rule)) {
			int idxY = rule.indexOf("Y");
			int idxM = rule.indexOf("M");
			int idxD = rule.indexOf("D");
			if (idxY != -1) { //年
				int ruleTimeLimit = NumberUtil.parseInt(rule.substring(0, idxY));
				if ("M".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit * 12;
				} else if ("D".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit * 365;
				}
				if (ruleTimeLimit <= timeLimit) {
					termType = FinancialProductTermTypeEnum.LONG_TERM;
				}
			} else if (idxM != -1) { //月
				int ruleTimeLimit = NumberUtil.parseInt(rule.substring(0, idxM));
				if ("Y".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit / 12;
				} else if ("D".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit * 30;
				}
				if (ruleTimeLimit <= timeLimit) {
					termType = FinancialProductTermTypeEnum.LONG_TERM;
				}
			} else if (idxD != -1) { //天
				int ruleTimeLimit = NumberUtil.parseInt(rule.substring(0, idxD));
				if ("Y".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit / 12;
				} else if ("M".equals(timeUnit)) {
					ruleTimeLimit = ruleTimeLimit / 30;
				}
				if (ruleTimeLimit <= timeLimit) {
					termType = FinancialProductTermTypeEnum.LONG_TERM;
				}
			}
		}
		
		return termType;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
}
