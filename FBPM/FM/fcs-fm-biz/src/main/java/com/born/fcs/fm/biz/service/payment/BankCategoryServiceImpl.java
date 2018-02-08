package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.BankCategoryDO;
import com.born.fcs.fm.dal.queryCondition.BankCategoryQueryCondition;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.info.payment.BankCategoryInfo;
import com.born.fcs.fm.ws.order.payment.BankCategoryOrder;
import com.born.fcs.fm.ws.order.payment.BankCategoryQueryOrder;
import com.born.fcs.fm.ws.service.payment.BankCategoryService;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("bankCategoryService")
public class BankCategoryServiceImpl extends BaseFormAutowiredDomainService implements BankCategoryService {

	@Override
	public QueryBaseBatchResult<BankCategoryInfo> queryPage(
			BankCategoryQueryOrder order) {

		QueryBaseBatchResult<BankCategoryInfo> batchResult = new QueryBaseBatchResult<BankCategoryInfo>();
		
		try {
			BankCategoryQueryCondition condition = new BankCategoryQueryCondition();
			BeanCopier.staticCopy(order,condition,UnBoxingConverter.getInstance());
			long totalCount = bankCategoryDAO.findByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);

			List<BankCategoryDO> list = 
					bankCategoryDAO.findByCondition(condition);

			List<BankCategoryInfo> pageList = new ArrayList<BankCategoryInfo>(list.size());
			for (BankCategoryDO DO : list) {
				BankCategoryInfo info = new BankCategoryInfo();
//				updateUsed(DO);
				BeanCopier.staticCopy(DO,info,UnBoxingConverter.getInstance());
//				if(DO.getUsed() != null){
//					info.setUsed(BooleanEnum.getByCode(DO.getUsed()));
//				}
//				if(DO.getStatus() != null){
//					info.setStatus(bankCategoryStatusEnum.getByCode(DO.getStatus()));
//				}
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
	public BankCategoryInfo queryById(long categoryId) {
		BankCategoryDO bankCategory = bankCategoryDAO.findById(categoryId);
		if(bankCategory!=null){
			BankCategoryInfo info = new BankCategoryInfo();
			BeanCopier.staticCopy(bankCategory,info,UnBoxingConverter.getInstance());
			return info;
		}
		return null;
	}

	@Override
	public FcsBaseResult save(final BankCategoryOrder order) {
		
		return this.commonProcess(order, "费用种类处理", new BeforeProcessInvokeService() {

			@Override
			public Domain before() {
				List<BankCategoryOrder> orders = order.getBatchList();
				if (orders == null) {
					processBankCategoryOrder(order);
				}else{
					for(BankCategoryOrder bankCategory : orders){
						processBankCategoryOrder(bankCategory);
					}
				}
				
				return null;
				
			}}, null, null);
	}

	@Override
	public FcsBaseResult deleteById(long categoryId) {
		FcsBaseResult result = new FcsBaseResult();
		bankCategoryDAO.deleteById(categoryId);
		result.setSuccess(true);
		return result;
	}

	private void processBankCategoryOrder(BankCategoryOrder bankCategory){
		
		long categoryId = bankCategory.getCategoryId();
		
		if(categoryId == 0 && !bankCategory.isDel()){
			Date now = FcsFmDomainHolder.get().getSysDate();
			//新增
			BankCategoryDO bankCategoryDO = new BankCategoryDO();
			BeanCopier.staticCopy(bankCategory,bankCategoryDO,UnBoxingConverter.getInstance());
//			if(bankCategory.getStatus() != null){
//				bankCategoryDO.setStatus(bankCategory.getStatus().code());
//			}else{
//				bankCategoryDO.setStatus(bankCategoryStatusEnum.NORMAL.code());
//			}
			bankCategoryDO.setRawAddTime(now);
			bankCategoryDAO.insert(bankCategoryDO);
		}
		
		if(categoryId > 0 && !bankCategory.isDel()){
			//更新
			BankCategoryDO bankCategoryDO = bankCategoryDAO.findById(categoryId);
			if(bankCategoryDO == null){
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据已更新");
			}
			if(StringUtil.isNotBlank(bankCategory.getArea())){
				bankCategoryDO.setArea(bankCategory.getArea());
			}
			if(StringUtil.isNotBlank(bankCategory.getBankCategory())){
				bankCategoryDO.setBankCategory(bankCategory.getBankCategory());
			}
			if(StringUtil.isNotBlank(bankCategory.getBankName())){
				bankCategoryDO.setBankName(bankCategory.getBankName());
			}
			if(bankCategory.getStatus() != null){
				bankCategoryDO.setStatus(bankCategory.getStatus());
			}
			bankCategoryDAO.update(bankCategoryDO);
		}
		
		if(categoryId > 0 && bankCategory.isDel()){
			//删除
			FcsBaseResult result = deleteById(categoryId);
			if(!result.isSuccess()){
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, result.getMessage());
			}
		}
	}
}
