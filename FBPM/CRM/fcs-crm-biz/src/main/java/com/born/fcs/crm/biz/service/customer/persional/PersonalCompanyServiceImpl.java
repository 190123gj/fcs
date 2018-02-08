package com.born.fcs.crm.biz.service.customer.persional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.PersonalCompanyDO;
import com.born.fcs.crm.ws.service.PersonalCompanyService;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.query.PersonalCompanyQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("personalCompanyService")
public class PersonalCompanyServiceImpl extends BaseAutowiredDAO implements PersonalCompanyService {
	
	@Override
	public FcsBaseResult add(PersonalCompanyOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			PersonalCompanyDO personalCompanyDO = new PersonalCompanyDO();
			BeanCopier.staticCopy(order, personalCompanyDO);
			personalCompanyDO.setRawAddTime(sqlDateService.getSqlDate());
			if (StringUtil.isNotBlank(order.getPerRegistAmountString())) {
				personalCompanyDO.setPerRegistAmount(Money.amout(order.getPerRegistAmountString()));
			}
			if (StringUtil.isNotBlank(order.getPerRegistDateString())) {
				personalCompanyDO.setPerRegistDate(DateUtil.parse(order.getPerRegistDateString()));
			}
			long keyId = personalCompanyDAO.insert(personalCompanyDO);
			result.setKeyId(keyId);
			result.setSuccess(true);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("增加个人公司异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("增加个人公司异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("增加个人公司异常", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateById(PersonalCompanyOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			PersonalCompanyDO personalCompanyDO = personalCompanyDAO.findById(order.getId());
			if (personalCompanyDO != null) {
				BeanCopier.staticCopy(order, personalCompanyDO);
				if (StringUtil.isNotBlank(order.getPerRegistAmountString())) {
					personalCompanyDO.setPerRegistAmount(Money.amout(order
						.getPerRegistAmountString()));
				}
				if (StringUtil.isNotBlank(order.getPerRegistDateString())) {
					personalCompanyDO.setPerRegistDate(DateUtil.parse(order
						.getPerRegistDateString()));
				}
				personalCompanyDAO.update(personalCompanyDO);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setMessage("无效Id");
			}
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新个人公司异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新个人公司异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新个人公司异常", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateByList(List<PersonalCompanyOrder> list, String customerId) {
		FcsBaseResult result = new FcsBaseResult();
		List<Long> ids = personalCompanyDAO.findByAllIds(customerId);
		if (ListUtil.isEmpty(list)) {
			result.setMessage("更新数据不能为空");
		} else {
			for (PersonalCompanyOrder order : list) {
				if (allValueIsNull(order)) {
					continue;
				}
				order.setCustomerId(customerId);
				if (order.getId() != null && order.getId() > 0) {
					updateById(order);
					ids.remove(order.getId());
				} else {
					add(order);
				}
				
			}
			
		}
		if (ListUtil.isNotEmpty(ids)) {
			for (Long id : ids) {
				delete(id);
			}
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public CompanyCustomerInfo queryById(long id) {
		CompanyCustomerInfo result = new CompanyCustomerInfo();
		PersonalCompanyDO PersonalCompanyDO = personalCompanyDAO.findById(id);
		BeanCopier.staticCopy(PersonalCompanyDO, result);
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			personalCompanyDAO.deleteById(id);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("删除个人公司异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除个人公司异常", e);
		}
		
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<PersonalCompanyInfo> list(PersonalCompanyQueryOrder queryOrder) {
		QueryBaseBatchResult<PersonalCompanyInfo> result = new QueryBaseBatchResult<PersonalCompanyInfo>();
		PersonalCompanyDO personalCompanyDO = new PersonalCompanyDO();
		BeanCopier.staticCopy(queryOrder, personalCompanyDO);
		long count = personalCompanyDAO.countWithCondition(personalCompanyDO);
		PageComponent component = new PageComponent(queryOrder, count);
		List<PersonalCompanyDO> list = personalCompanyDAO.findWithCondition(personalCompanyDO,
			component.getFirstRecord(), component.getPageSize());
		List<PersonalCompanyInfo> pageList = new ArrayList<PersonalCompanyInfo>();
		PersonalCompanyInfo info = null;
		for (PersonalCompanyDO Do : list) {
			info = new PersonalCompanyInfo();
			BeanCopier.staticCopy(Do, info);
			info.setId(Do.getId());
			pageList.add(info);
		}
		result.setSuccess(true);
		result.setPageList(pageList);
		result.initPageParam(component);
		return result;
	}
}
