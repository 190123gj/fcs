package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Sets;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.CostCategoryDO;
import com.born.fcs.fm.dal.queryCondition.CostCategoryQueryCondition;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("costCategoryService")
public class CostCategoryServiceImpl extends BaseFormAutowiredDomainService implements CostCategoryService {

	@Override
	public QueryBaseBatchResult<CostCategoryInfo> queryPage(
			CostCategoryQueryOrder order) {

		QueryBaseBatchResult<CostCategoryInfo> batchResult = new QueryBaseBatchResult<CostCategoryInfo>();
		
		try {
			CostCategoryQueryCondition condition = new CostCategoryQueryCondition();
			BeanCopier.staticCopy(order,condition,UnBoxingConverter.getInstance());
			if(order.getStatusList() != null){
				condition.setStatusList(
					new ArrayList<String>(order.getStatusList().size()));
				for (CostCategoryStatusEnum status : order.getStatusList()) {
					condition.getStatusList().add(status.code());
				}
			}
			long totalCount = costCategoryDAO.findByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);

			List<CostCategoryDO> list = 
					costCategoryDAO.findByCondition(condition);

			List<CostCategoryInfo> pageList = new ArrayList<CostCategoryInfo>(list.size());
			for (CostCategoryDO DO : list) {
				CostCategoryInfo info = new CostCategoryInfo();
				//未知的神秘逻辑，于2017-06-27注释掉
//				updateUsed(DO);
				BeanCopier.staticCopy(DO,info,UnBoxingConverter.getInstance());
				if(DO.getUsed() != null){
					info.setUsed(BooleanEnum.getByCode(DO.getUsed()));
				}
				if(DO.getStatus() != null){
					info.setStatus(CostCategoryStatusEnum.getByCode(DO.getStatus()));
				}
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询费用种类列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	
	}


	@Override
	public CostCategoryInfo queryById(long categoryId) {
		CostCategoryDO costCategory = costCategoryDAO.findById(categoryId);
		if(costCategory!=null){
			CostCategoryInfo info = new CostCategoryInfo();
			BeanCopier.staticCopy(costCategory,info,UnBoxingConverter.getInstance());
			return info;
		}
		return null;
	}

	@Override
	public FcsBaseResult save(final CostCategoryOrder order) {
		
		return this.commonProcess(order, "费用种类处理", new BeforeProcessInvokeService() {

			@Override
			public Domain before() {
				List<CostCategoryOrder> orders = order.getBatchList();
				if (orders == null) {
					processCostCategoryOrder(order);
				}else{
					int cNum = 0;
					Set nameset = Sets.newHashSet();
					for(CostCategoryOrder costCategory : orders){
						if(!costCategory.isDel()){
							cNum = cNum + 1;
							nameset.add(costCategory.getName());
						}
					}
					if(cNum>0 && nameset.size() != cNum){
						throw ExceptionFactory.newFcsException(FcsResultEnum.FORM_CHECK_ERROR, "费用种类名称重复");
					}
					
					for(CostCategoryOrder costCategory : orders){
						processCostCategoryOrder(costCategory);
					}
				}
				
				return null;
				
			}}, null, null);
	}

	private void processCostCategoryOrder(CostCategoryOrder costCategory){
		
		long categoryId = costCategory.getCategoryId();
		
		if(categoryId == 0 && !costCategory.isDel()){
			Date now = FcsFmDomainHolder.get().getSysDate();
			//新增
			CostCategoryDO costCategoryDO = new CostCategoryDO();
			BeanCopier.staticCopy(costCategory,costCategoryDO,UnBoxingConverter.getInstance());
			if(costCategory.getStatus() != null){
				costCategoryDO.setStatus(costCategory.getStatus().code());
			}else{
				costCategoryDO.setStatus(CostCategoryStatusEnum.NORMAL.code());
			}
			costCategoryDO.setUsed(BooleanEnum.NO.code());
			costCategoryDO.setRawAddTime(now);
			costCategoryDAO.insert(costCategoryDO);
		}
		
		if(categoryId > 0 && !costCategory.isDel()){
			//更新
			CostCategoryDO costCategoryDO = costCategoryDAO.findById(categoryId);
			if(costCategoryDO == null){
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据已更新");
			}
			if(StringUtil.isNotBlank(costCategory.getAccountCode())){
				costCategoryDO.setAccountCode(costCategory.getAccountCode());
			}
			if(StringUtil.isNotBlank(costCategory.getAccountName())){
				costCategoryDO.setAccountName(costCategory.getAccountName());
			}
			if(StringUtil.isNotBlank(costCategory.getName())){
				costCategoryDO.setName(costCategory.getName());
			}
			if(costCategory.getUsed() != null){
				costCategoryDO.setUsed(costCategory.getUsed().code());
			}
			if(costCategory.getStatus() != null){
				costCategoryDO.setStatus(costCategory.getStatus().code());
			}
			costCategoryDAO.update(costCategoryDO);
		}
		
		if(categoryId > 0 && costCategory.isDel()){
			//删除
			FcsBaseResult result = deleteById(categoryId);
			if(!result.isSuccess()){
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, result.getMessage());
			}
		}
	}


	@Override
	public FcsBaseResult deleteById(long categoryId) {
		FcsBaseResult result = new FcsBaseResult();
		long count = formExpenseApplicationDetailDAO.
				findCountByExpenseType(String.valueOf(categoryId));
		if(count>0){
			result.setMessage("改类型已被使用");
		}else{
			costCategoryDAO.deleteById(categoryId);
			result.setSuccess(true);
		}
		
		return result;
	}
	
	/**
	 * 更新使用状态
	 * @param costCategoryDO
	 */
	private void updateUsed(CostCategoryDO costCategoryDO){
		long categoryId = costCategoryDO.getCategoryId();
		long count = formExpenseApplicationDetailDAO.
				findCountByExpenseType(String.valueOf(categoryId));//是否被差旅费报销单使用
		if(count == 0){
			count = budgetDetailDAO.findCountByCategoryId(categoryId);//是否被预算管理使用
		}
		
		if(count>0){
			if(!BooleanEnum.IS.code().equals(costCategoryDO.getUsed())){
				costCategoryDO.setUsed(BooleanEnum.IS.code());
				costCategoryDAO.update(costCategoryDO);
			}
		}else{
			if(!BooleanEnum.NO.code().equals(costCategoryDO.getUsed())){
				costCategoryDO.setUsed(BooleanEnum.NO.code());
				costCategoryDAO.update(costCategoryDO);
			}
		}
	}
	
}
