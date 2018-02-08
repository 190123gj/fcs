package com.born.fcs.crm.biz.service.customer.enterprise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.CompanyQualificationDO;
import com.born.fcs.crm.ws.service.CompanyQualificationService;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("companyQualificationService")
public class CompanyQualificationServiceImpl extends BaseAutowiredDAO implements
																		CompanyQualificationService {
	
	@Override
	public FcsBaseResult add(CompanyQualificationInfo order) {
		FcsBaseResult result = new FcsBaseResult();
		
		try {
			CompanyQualificationDO companyQualification = new CompanyQualificationDO();
			BeanCopier.staticCopy(order, companyQualification);
			companyQualification.setExperDate(DateUtil.parse(order.getExperDate()));
			companyQualification.setRawAddTime(sqlDateService.getSqlDate());
			companyQualificationDAO.insert(companyQualification);
			result.setSuccess(true);
			result.setMessage("新增资质证书成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("新增资质证书异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("新增资质证书异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("新增资质证书异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			companyQualificationDAO.deleteById(id);
			result.setSuccess(true);
			result.setMessage("删除资质证书成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("删除资质证书异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除资质证书异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult update(CompanyQualificationInfo order) {
		FcsBaseResult result = new FcsBaseResult();
		
		try {
			CompanyQualificationDO companyQualification = new CompanyQualificationDO();
			BeanCopier.staticCopy(order, companyQualification);
			companyQualification.setId(order.getId());
			companyQualification.setExperDate(DateUtil.parse(order.getExperDate()));
			companyQualificationDAO.updateById(companyQualification);
			result.setSuccess(true);
			result.setMessage("更新资质证书成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新资质证书异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新资质证书异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新资质证书异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult updateByList(List<CompanyQualificationInfo> list, String customerId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			List<Long> ids = companyQualificationDAO.findByAllIds(customerId);
			if (ListUtil.isNotEmpty(list)) {
				for (CompanyQualificationInfo order : list) {
					if (allValueIsNull(order)) {
						continue;
					}
					if (StringUtil.isBlank(order.getQualificationName())) {
						continue;
					}
					order.setCustomerId(customerId);
					if (order.getId() != null && order.getId() > 0) {
						update(order);
						ids.remove(order.getId());
					} else if (StringUtil.isNotBlank(order.getQualificationName())) {
						add(order);
					}
					
				}
				
				result.setSuccess(true);
				result.setMessage("修改成功");
			}
			if (ListUtil.isNotEmpty(ids)) {
				for (Long id : ids) {
					delete(id);
				}
			}
		} catch (Exception e) {
			logger.error("批量修改资质证书异常", e);
			result.setMessage("修改异常");
		}
		
		return result;
	}
	
	@Override
	public List<CompanyQualificationInfo> list(String customerId) {
		List<CompanyQualificationInfo> result = new ArrayList<CompanyQualificationInfo>();
		CompanyQualificationDO companyQualification = new CompanyQualificationDO();
		companyQualification.setCustomerId(customerId);
		List<CompanyQualificationDO> list = companyQualificationDAO.findWithCondition(
			companyQualification, 0, 100);
		CompanyQualificationInfo info = null;
		for (CompanyQualificationDO Do : list) {
			info = new CompanyQualificationInfo();
			BeanCopier.staticCopy(Do, info);
			if (Do.getExperDate() != null)
				info.setExperDate(com.yjf.common.lang.util.DateUtil.dtSimpleFormat(Do
					.getExperDate()));
			info.setId(Do.getId());
			result.add(info);
		}
		return result;
	}
}
