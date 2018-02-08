package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 公司获得的资质证书
 * */
@WebService
public interface CompanyQualificationService {
	/** 增加 */
	FcsBaseResult add(CompanyQualificationInfo order);
	
	/** 删除 */
	FcsBaseResult delete(long id);
	
	/** 更新 */
	FcsBaseResult update(CompanyQualificationInfo order);
	
	/** 批量修改 */
	FcsBaseResult updateByList(List<CompanyQualificationInfo> list, String customerId);
	
	List<CompanyQualificationInfo> list(String customerId);
}
