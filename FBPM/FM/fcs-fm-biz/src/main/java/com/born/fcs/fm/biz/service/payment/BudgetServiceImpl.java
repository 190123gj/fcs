package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.BudgetDO;
import com.born.fcs.fm.dal.dataobject.BudgetDetailDO;
import com.born.fcs.fm.dal.dataobject.CostCategoryDO;
import com.born.fcs.fm.dal.queryCondition.BudgetQueryCondition;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.enums.PeriodEnum;
import com.born.fcs.fm.ws.enums.SpeCostCategory;
import com.born.fcs.fm.ws.info.payment.BudgetDetailInfo;
import com.born.fcs.fm.ws.info.payment.BudgetInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseDetailInfo;
import com.born.fcs.fm.ws.order.payment.BudgetDetailOrder;
import com.born.fcs.fm.ws.order.payment.BudgetOrder;
import com.born.fcs.fm.ws.order.payment.BudgetQueryOrder;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Sets;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("budgetService")
public class BudgetServiceImpl extends BaseFormAutowiredDomainService implements BudgetService {

	@Autowired
	protected TravelExpenseService travelExpenseService;
	@Autowired
	protected ExpenseApplicationService expenseApplicationService;
	
	@Override
	public QueryBaseBatchResult<BudgetInfo> queryPage(BudgetQueryOrder order) {
		QueryBaseBatchResult<BudgetInfo> batchResult = new QueryBaseBatchResult<BudgetInfo>();
		
		try {
			BudgetQueryCondition condition = getQueryCondition(order);
			long totalCount = budgetDAO.findByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);

			List<BudgetDO> list = 
					budgetDAO.findByCondition(condition);

			List<BudgetInfo> pageList = new ArrayList<BudgetInfo>(
				list.size());
			for (BudgetDO DO : list) {
				BudgetInfo info = new BudgetInfo();
				setBudgetInfo(DO, info, order.isDetail());
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询预算列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}

	@Override
	public BudgetInfo queryById(long budgetId) {
		BudgetDO budgetDO = budgetDAO.findById(budgetId);
		if(budgetDO!=null){
			BudgetInfo budgetInfo = new BudgetInfo();
			setBudgetInfo(budgetDO, budgetInfo, true);
			return budgetInfo;
		}
		return null;
	}

	@Override
	public FcsBaseResult save(final BudgetOrder order) {
		
		return this.commonProcess(order, "预算管理", new BeforeProcessInvokeService() {

			@Override
			public Domain before() {
				long budgetId = order.getBudgetId();
				List<BudgetDetailOrder> detailOrders = order.getDetailList();
				if (detailOrders == null || detailOrders.size() == 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "预算明细不存在");
				}
				
				BudgetQueryCondition condition = new BudgetQueryCondition();
				condition.setBudgetDeptId(String.valueOf(order.getBudgetDeptId()));
				List<BudgetDO> budgetDOs = budgetDAO.findByCondition(condition);
				for(BudgetDO DO : budgetDOs){
					boolean isErr = true;
					if(budgetId == DO.getBudgetId()){//本数据不作处理
						continue;
					}
					
//					if(DO.getEndTime().compareTo(DateUtil.parse(order.getStartTime()))<0){
//						isErr = false;
//					}else if(DO.getStartTime().compareTo(DateUtil.parse(order.getEndTime()))>0){
//						isErr = false;
//					}
//					if(isErr && order.getPeriod().code().equals(DO.getPeriod())){
//						String errmsg = "该部门在" + DateUtil.dtSimpleFormat(DO.getStartTime()) + "到" + 
//								DateUtil.dtSimpleFormat(DO.getEndTime()) + "已编制预算，无法重复提交预算！";
//						throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM, errmsg);
//					}
					
					if(order.getPeriod().code().equals(DO.getPeriod()) 
							&& order.getPeriodValue().equals(DO.getPeriodValue())){
						String errmsg = "部门在该" + order.getPeriod().message() + "已编制预算，无法重复提交预算！";
						throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM, errmsg);
					}
				}
				
				HashSet set = Sets.newHashSet();
				for(BudgetDetailOrder detailOrder : detailOrders){
					set.add(detailOrder.getCategoryId());
				}
				if(detailOrders.size() != set.size()){
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM, "一个费用种类仅能对应一个预算金额");
				}
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute("result");
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				if (budgetId == 0) {
					//新增
					BudgetDO budget = new BudgetDO();
					BeanCopier.staticCopy(order, budget, UnBoxingConverter.getInstance());
					if(StringUtil.isNotBlank(order.getStartTime()))
						budget.setStartTime(DateUtil.parse(order.getStartTime()));
					if(StringUtil.isNotBlank(order.getEndTime()))
						budget.setEndTime(DateUtil.parse(order.getEndTime()));
					budget.setPeriod(order.getPeriod().code());
					budget.setRawAddTime(now);
					processTime(budget);
					budgetId = budgetDAO.insert(budget);
					
					for(BudgetDetailOrder detail : detailOrders){
						BudgetDetailDO budgetDetail = new BudgetDetailDO();
						BeanCopier.staticCopy(detail, budgetDetail, UnBoxingConverter.getInstance());
						budgetDetail.setBudgetId(budgetId);
						if(detail.getIsContrl()==null){
							budgetDetail.setIsContrl(BooleanEnum.NO.code());
						}else{
							budgetDetail.setIsContrl(detail.getIsContrl().code());
						}
						budgetDetail.setRawAddTime(now);
						budgetDetailDAO.insert(budgetDetail);
					}
					
				}else{
					//更新
					BudgetDO budget = budgetDAO.findById(budgetId);
					if (budget == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "预算不存在");
					}
					if(StringUtil.isNotBlank(order.getStartTime()))
						budget.setStartTime(DateUtil.parse(order.getStartTime()));
					if(StringUtil.isNotBlank(order.getEndTime()))
						budget.setEndTime(DateUtil.parse(order.getEndTime()));
					budget.setPeriodValue(order.getPeriodValue());
					budget.setPeriod(order.getPeriod().code());
					budget.setBudgetDeptId(order.getBudgetDeptId());
					budget.setBudgetDeptName(order.getBudgetDeptName());
					processTime(budget);
					budgetDAO.update(budget);
					
					List<BudgetDetailDO> orgDetailDOs = budgetDetailDAO.findByBudgetId(budgetId);
					for(BudgetDetailOrder detail : detailOrders){
						
						if (detail.getDetailId() == 0) {
							//明细新增
							BudgetDetailDO budgetDetail = new BudgetDetailDO();
							BeanCopier.staticCopy(detail, budgetDetail, UnBoxingConverter.getInstance());
							budgetDetail.setBudgetId(budgetId);
							if(detail.getIsContrl()==null){
								budgetDetail.setIsContrl(BooleanEnum.NO.code());
							}else{
								budgetDetail.setIsContrl(detail.getIsContrl().code());
							}
							budgetDetail.setRawAddTime(now);
							budgetDetailDAO.insert(budgetDetail);
						}else{
							//明细更新
							for(int i=0; i<orgDetailDOs.size(); i++){
								BudgetDetailDO updateDetailDO = orgDetailDOs.get(i);
								if(detail.getDetailId() == updateDetailDO.getDetailId()){
									updateDetailDO.setCategoryId(detail.getCategoryId());
									updateDetailDO.setAmount(detail.getAmount());
									if(detail.getIsContrl()==null){
										updateDetailDO.setIsContrl(BooleanEnum.NO.code());
									}else{
										updateDetailDO.setIsContrl(detail.getIsContrl().code());
									}
									budgetDetailDAO.update(updateDetailDO);
									
									//移除已更新的数据，剩下需要删除的数据
									orgDetailDOs.remove(i);
									break;
								}
							}
						}
						
					}
					
					//明细删除
					for(BudgetDetailDO deletDo : orgDetailDOs){
						budgetDetailDAO.deleteById(deletDo.getDetailId());
					}
				}
				
				result.setMessage(String.valueOf(budgetId));
				
				return null;
				
			}}, null, null);
	}

	private BudgetQueryCondition getQueryCondition(BudgetQueryOrder order){
		BudgetQueryCondition condition = new BudgetQueryCondition();
		if(order!=null){
			BeanCopier.staticCopy(order,condition,UnBoxingConverter.getInstance());
		}
		if(StringUtil.isNotBlank(order.getEndTime())){
			condition.setEndTime(order.getEndTime()+" 23:59:59");
		}
		condition.setBudgetDeptList(order.getBudgetDeptList());
		return condition;
	}
	
	private void processTime(BudgetDO budget){
		int year = 1900;
		int startMonth = 1;
		int endMonth = 1;
		if(PeriodEnum.YEAR.code().equals(budget.getPeriod())){
			year = Integer.valueOf(budget.getPeriodValue());
			startMonth = 1;
			endMonth = 12;
		}else if(PeriodEnum.SEASON.code().equals(budget.getPeriod())){
			year = Integer.valueOf(budget.getPeriodValue().substring(0, 4));
			int season = Integer.valueOf(budget.getPeriodValue().substring(4, 5));
			if(season == 1){
				startMonth = 1;
				endMonth = 3;
			}
			if(season == 2){
				startMonth = 4;
				endMonth = 6;
			}
			if(season == 3){
				startMonth = 7;
				endMonth = 9;
			}
			if(season == 4){
				startMonth = 10;
				endMonth = 12;
			}
		}else if(PeriodEnum.MONTH.code().equals(budget.getPeriod())){
			year = Integer.valueOf(budget.getPeriodValue().substring(0, 4));
			int month = Integer.valueOf(budget.getPeriodValue().substring(4, 6));
			startMonth = month;
			endMonth = month;
		}

		budget.setStartTime(DateUtil.getStartTimeByYearAndMonth(year,startMonth));
		budget.setEndTime(DateUtil.getEndTimeByYearAndMonth(year,endMonth));
	}
	
	private void setBudgetInfo(BudgetDO DO, BudgetInfo info, boolean isDetail){
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setPeriod(PeriodEnum.getByCode(DO.getPeriod()));
		if(isDetail){
			List<BudgetDetailDO> detailDOs = 
					budgetDetailDAO.findByBudgetId(DO.getBudgetId());
			if(detailDOs!=null && detailDOs.size()>0){
				List<BudgetDetailInfo> detailList = 
						new ArrayList<BudgetDetailInfo>(detailDOs.size());
				for(BudgetDetailDO detailDO : detailDOs){
					BudgetDetailInfo detailInfo = new BudgetDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					CostCategoryDO costCategoryDO = costCategoryDAO.findById(detailInfo.getCategoryId());
					if(costCategoryDO!=null){
						detailInfo.setCategoryNm(costCategoryDO.getName());
					}
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
		}
	}

	/**
	 * 未设置预算或预算余额处该部门为不控制且isNull=ture,返回null
	 */
	@Override
	public Money queryBalanceByDeptCategoryId(long deptId, long categoryId, Date applyTime, boolean isNull) {
		
		String budgetDeptId = String.valueOf(deptId);
		Money budgetDept = null;
		BudgetInfo budgetInfo = null;
		PeriodEnum curPeriodEnum = null;
		BudgetInfo maxBudgetInfo = null;
		PeriodEnum maxPeriodEnum = null;
		boolean isContrl = false;
		if(deptId<=0 || categoryId<=0 || applyTime== null){
			return budgetDept;
		}
		
		BudgetQueryOrder order = new BudgetQueryOrder();
		order.setBudgetDeptId(budgetDeptId);
		order.setDetail(true);
		QueryBaseBatchResult<BudgetInfo> budgets = this.queryPage(order);
		if(budgets.getPageList()!=null){
			for(BudgetInfo budge : budgets.getPageList()){
				if(budge.getStartTime().compareTo(applyTime)<=0 
						&& budge.getEndTime().compareTo(applyTime)>=0){//对应部门预算日期
					
					if(maxPeriodEnum == null){
						maxPeriodEnum = budge.getPeriod();
						maxBudgetInfo = budge;
					}else{
						if(maxPeriodEnum.priority()<budge.getPeriod().priority()){
							maxPeriodEnum = budge.getPeriod();
							maxBudgetInfo = budge;
						}
					}
					
					if(curPeriodEnum!=null && curPeriodEnum.priority()<budge.getPeriod().priority()){
						//先取月度，再取季度，最后年度
						continue;
					}
					List<BudgetDetailInfo> budgetDetails = budge.getDetailList();
					for(BudgetDetailInfo budgetDetail : budgetDetails){
						if(budgetDetail.getCategoryId() == categoryId){//费用种类
							budgetDept = budgetDetail.getAmount()==null?Money.zero():budgetDetail.getAmount();
							if(BooleanEnum.IS.code().equals(budgetDetail.getIsContrl())){
								isContrl = true;
							}else{
								isContrl = false;
							}
							budgetInfo = budge;
							break;
						}
					}
					curPeriodEnum = budge.getPeriod();
				}
			}
		}
		
		if(budgetDept == null){//未设置预算
			if(isNull){
				return null;
			}
			budgetDept = Money.zero();
		}
		
		if(!isContrl && isNull){//预算余额处该部门为不控制
			return null;
		}
		
		if(budgetDept.compareTo(Money.zero())>0 && categoryId == SpeCostCategory.TRAVEL.categoryId()){ //差旅费
//			List<FormTravelExpenseDetailInfo> ainfos = travelExpenseService
//					.findApprlInfoByDeptId(budgetDeptId, budgetInfo.getStartTime(), budgetInfo.getEndTime());
			List<FormTravelExpenseDetailInfo> ainfos = travelExpenseService
					.findApprlInfoByDeptId(budgetDeptId, maxBudgetInfo.getStartTime(), maxBudgetInfo.getEndTime());
			if(ainfos!=null){
				for(FormTravelExpenseDetailInfo abudge : ainfos){
					if(budgetDept.compareTo(Money.zero())<=0){//预算已用完
						break;
					}
					budgetDept = budgetDept.subtract(abudge.getTotalAmount());
				}
			}
		}
		
		if(budgetDept.compareTo(Money.zero())>0){
			List<FormExpenseApplicationDetailInfo> sublInfos = expenseApplicationService.findSublInfoByDeptCategory(
						deptId, categoryId, budgetInfo.getStartTime(), budgetInfo.getEndTime());
			if(sublInfos!=null){
				for(FormExpenseApplicationDetailInfo abudge : sublInfos){
					if(budgetDept.compareTo(Money.zero())<=0){//预算已用完
						break;
					}
					budgetDept = budgetDept.subtract(abudge.getAmount());
				}
			}
		}
		
		return (budgetDept.compareTo(Money.zero())<0)?Money.zero():budgetDept;
	
	}
}
