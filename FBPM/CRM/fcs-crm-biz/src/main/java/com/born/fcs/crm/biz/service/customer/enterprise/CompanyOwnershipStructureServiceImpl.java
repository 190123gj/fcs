package com.born.fcs.crm.biz.service.customer.enterprise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.CompanyOwnershipStructureDO;
import com.born.fcs.crm.ws.service.CompanyOwnershipStructureService;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("companyOwnershipStructureService")
public class CompanyOwnershipStructureServiceImpl extends BaseAutowiredDAO implements
																			CompanyOwnershipStructureService {
	
	@Override
	public FcsBaseResult add(CompanyOwnershipStructureInfo order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			CompanyOwnershipStructureDO companyOwnershipStructure = new CompanyOwnershipStructureDO();
			BeanCopier.staticCopy(order, companyOwnershipStructure);
			if (StringUtil.isNotBlank(order.getEquity())) {
				companyOwnershipStructure.setEquity(Double.parseDouble(order.getEquity()));
			}
			companyOwnershipStructure.setRawAddTime(sqlDateService.getSqlDate());
			long keyId = companyOwnershipStructureDAO.insert(companyOwnershipStructure);
			result.setKeyId(keyId);
			result.setSuccess(true);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("增加公司股东异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("增加公司股东异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("增加公司股东异常", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateById(CompanyOwnershipStructureInfo order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			CompanyOwnershipStructureDO companyOwnershipStructureDO = companyOwnershipStructureDAO
				.findById(order.getId());
			if (companyOwnershipStructureDO != null) {
				BeanCopier.staticCopy(order, companyOwnershipStructureDO);
				if (StringUtil.isNotBlank(order.getEquity())) {
					companyOwnershipStructureDO.setEquity(Double.parseDouble(order.getEquity()));
				}
				companyOwnershipStructureDAO.update(companyOwnershipStructureDO);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setMessage("无效Id");
			}
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新公司股东异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新公司股东异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新公司股东异常", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateByList(List<CompanyOwnershipStructureInfo> list, String customerId) {
		FcsBaseResult result = new FcsBaseResult();
		List<Long> ids = companyOwnershipStructureDAO.findByAllIds(customerId);
		if (ListUtil.isEmpty(list)) {
			result.setMessage("更新数据不能为空");
		} else {
			for (CompanyOwnershipStructureInfo order : list) {
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
				
				result.setSuccess(true);
			}
			
		}
		if (ListUtil.isNotEmpty(ids)) {
			for (Long id : ids) {
				delete(id);
			}
		}
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			companyOwnershipStructureDAO.deleteById(id);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("删除公司股东异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除公司股东异常", e);
		}
		
		return result;
	}
	
	@Override
	public List<CompanyOwnershipStructureInfo> list(String customerId) {
		List<CompanyOwnershipStructureInfo> result = new ArrayList<CompanyOwnershipStructureInfo>();
		CompanyOwnershipStructureDO companyOwnershipStructure = new CompanyOwnershipStructureDO();
		companyOwnershipStructure.setCustomerId(customerId);
		List<CompanyOwnershipStructureDO> list = companyOwnershipStructureDAO.findWithCondition(
			companyOwnershipStructure, 0, 200);
		CompanyOwnershipStructureInfo info = null;
		for (CompanyOwnershipStructureDO Do : list) {
			info = new CompanyOwnershipStructureInfo();
			BeanCopier.staticCopy(Do, info);
			
			info.setEquity(String.valueOf(Do.getEquity()));
			
			info.setId(Do.getId());
			result.add(info);
		}
		
		return result;
	}
}
