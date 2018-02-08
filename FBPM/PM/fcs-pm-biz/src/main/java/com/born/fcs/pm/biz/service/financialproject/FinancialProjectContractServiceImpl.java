package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialContractDO;
import com.born.fcs.pm.dataobject.FinancialProjectContractFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialContractInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectContractFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectContractService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectContractService")
public class FinancialProjectContractServiceImpl extends BaseFormAutowiredDomainService implements
																						FinancialProjectContractService {
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectContractFormInfo> queryPage(ProjectFinancialContractFormQueryOrder order) {
		QueryBaseBatchResult<FinancialProjectContractFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectContractFormInfo>();
		
		try {
			FinancialProjectContractFormDO projectForm = new FinancialProjectContractFormDO();
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			projectForm.setContractId(order.getContractId());
			projectForm.setProductName(order.getProductName());
			projectForm.setFormStatus(order.getFormStatus());
			projectForm.setProjectCode(order.getProjectCode());
			projectForm.setLoginUserId(order.getLoginUserId());
			projectForm.setDraftUserId(order.getDraftUserId());
			projectForm.setDeptIdList(order.getDeptIdList());
			projectForm.setIssueInstitution(order.getIssueInstitution());
			
			projectForm.setFormStatusList(order.getFormStatusList());
			
			long totalCount = busiDAO.searchFinancialProjectContractFormCount(projectForm);
			PageComponent component = new PageComponent(order, totalCount);
			
			//查询的分页参数
			projectForm.setSortCol(order.getSortCol());
			projectForm.setSortOrder(order.getSortOrder());
			projectForm.setLimitStart(component.getFirstRecord());
			projectForm.setPageSize(component.getPageSize());
			
			List<FinancialProjectContractFormDO> list = busiDAO
				.searchFinancialProjectContractForm(projectForm);
			
			List<FinancialProjectContractFormInfo> pageList = new ArrayList<FinancialProjectContractFormInfo>(
				list.size());
			for (FinancialProjectContractFormDO DO : list) {
				FinancialProjectContractFormInfo info = new FinancialProjectContractFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setProject(financialProjectSetupService.queryByProjectCode(info
					.getProjectCode()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目合同申请列表失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FProjectFinancialContractInfo queryByFormId(long formId) {
		FProjectFinancialContractDO contract = FProjectFinancialContractDAO.findByFormId(formId);
		if (contract != null) {
			FProjectFinancialContractInfo info = new FProjectFinancialContractInfo();
			BeanCopier.staticCopy(contract, info);
			info.setContractStatus(ContractStatusEnum.getByCode(contract.getContractStatus()));
			return info;
		}
		return null;
	}
	
	@Override
	public FormBaseResult save(final ProjectFinancialContractOrder order) {
		
		order.setFormCode(FormCodeEnum.FINANCING_CONTRACT);
		
		return commonFormSaveProcess(order, "保存理财项目合同申请单", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				boolean isUpdate = false;
				FProjectFinancialContractDO contract = FProjectFinancialContractDAO
					.findByFormId(order.getFormId());
				if (contract == null) {
					contract = new FProjectFinancialContractDO();
					contract.setRawAddTime(now);
				} else {
					isUpdate = true;
				}
				contract.setFormId(form.getFormId());
				contract.setProjectCode(order.getProjectCode());
				contract.setContract(order.getContract());
				contract.setAttach(order.getAttach());
				contract.setContractStatus(ContractStatusEnum.DRAFT.code());
				if (isUpdate) {
					FProjectFinancialContractDAO.update(contract);
				} else {
					FProjectFinancialContractDAO.insert(contract);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult changeStatus(final ProjectFinancialContractStatusOrder order) {
		return commonProcess(order, "修改理财项目合同状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FProjectFinancialContractDO contract = FProjectFinancialContractDAO
					.findByFormId(order.getFormId());
				if (contract == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "合同不存在");
				}
				
				boolean hasChange = false;
				if (order.getContractStatus() != null) {
					contract.setContractStatus(order.getContractStatus().code());
					hasChange = true;
				}
				if (StringUtil.isNotBlank(order.getContractReturn())) {
					contract.setContractReturn(order.getContractReturn());
					hasChange = true;
				}
				
				if (hasChange) {
					FProjectFinancialContractDAO.update(contract);
				}
				
				return null;
			}
		}, null, null);
	}
}
