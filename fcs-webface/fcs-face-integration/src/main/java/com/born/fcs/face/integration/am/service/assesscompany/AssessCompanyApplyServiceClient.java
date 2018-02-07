package com.born.fcs.face.integration.am.service.assesscompany;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.assesscompany.AssessCompanyEvaluateInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyEvaluateOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("assessCompanyApplyServiceClient")
public class AssessCompanyApplyServiceClient extends ClientAutowiredBaseService implements
																				AssessCompanyApplyService {
	
	@Override
	public FAssessCompanyApplyInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FAssessCompanyApplyInfo>() {
			
			@Override
			public FAssessCompanyApplyInfo call() {
				return assessCompanyApplyWebService.findById(id);
			}
		});
	}
	
	@Override
	public FAssessCompanyApplyInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAssessCompanyApplyInfo>() {
			
			@Override
			public FAssessCompanyApplyInfo call() {
				return assessCompanyApplyWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FAssessCompanyApplyInfo> query(	final FAssessCompanyApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FAssessCompanyApplyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FAssessCompanyApplyInfo> call() {
				return assessCompanyApplyWebService.query(order);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FAssessCompanyApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return assessCompanyApplyWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveAssessCompanyEvaluate(final AssessCompanyEvaluateOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return assessCompanyApplyWebService.saveAssessCompanyEvaluate(order);
			}
		});
	}
	
	@Override
	public List<AssessCompanyEvaluateInfo> findEvaluateByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<List<AssessCompanyEvaluateInfo>>() {
			
			@Override
			public List<AssessCompanyEvaluateInfo> call() {
				return assessCompanyApplyWebService.findEvaluateByApplyId(applyId);
			}
		});
	}
	
	@Override
	public Boolean IsEvaluateByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Boolean>() {
			
			@Override
			public Boolean call() {
				return assessCompanyApplyWebService.IsEvaluateByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public AssetsAssessCompanyInfo findByApplyIdAndCompany(final long applyId, final long companyId) {
		return callInterface(new CallExternalInterface<AssetsAssessCompanyInfo>() {
			
			@Override
			public AssetsAssessCompanyInfo call() {
				return assessCompanyApplyWebService.findByApplyIdAndCompany(applyId, companyId);
			}
		});
	}
	
	@Override
	public List<AssetsAssessCompanyInfo> assessCompanyeExtract(final long id,
																final String cityName,
																final String provinceName) {
		return callInterface(new CallExternalInterface<List<AssetsAssessCompanyInfo>>() {
			
			@Override
			public List<AssetsAssessCompanyInfo> call() {
				return assessCompanyApplyWebService.assessCompanyeExtract(id, cityName,
					provinceName);
			}
		});
	}
	
}
