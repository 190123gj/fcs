package com.born.fcs.face.integration.pm.service.setup;

import java.net.SocketTimeoutException;
import java.util.List;

import com.born.fcs.pm.ws.order.setup.*;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.setup.FProjectBankLoanInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteeGuarantorInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteePledgeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectEquityStructureInfo;
import com.born.fcs.pm.ws.info.setup.FProjectExternalGuaranteeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.common.CopyHisFormOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;

/**
 * 项目立项 client
 * @author wuzj
 */
@Service("projectSetupServiceClient")
public class ProjectSetupServiceClient extends ClientAutowiredBaseService implements
																			ProjectSetupService {
	
	@Override
	public FormBaseResult saveCustomerBaseInfo(final FProjectCustomerBaseInfoOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveCustomerBaseInfo(order);
			}
		});
	}
	
	@Override
	public FProjectCustomerBaseInfo queryCustomerBaseInfoByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectCustomerBaseInfo>() {
			
			@Override
			public FProjectCustomerBaseInfo call() {
				return projectSetupWebService.queryCustomerBaseInfoByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectCustomerBaseInfo queryCustomerBaseInfoByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectCustomerBaseInfo>() {
			
			@Override
			public FProjectCustomerBaseInfo call() {
				return projectSetupWebService.queryCustomerBaseInfoByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult saveEquityStructure(final FProjectEquityStructureBatchOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveEquityStructure(order);
			}
		});
	}
	
	@Override
	public List<FProjectEquityStructureInfo> queryEquityStructureByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FProjectEquityStructureInfo>>() {
			
			@Override
			public List<FProjectEquityStructureInfo> call() {
				return projectSetupWebService.queryEquityStructureByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FProjectEquityStructureInfo> queryEquityStructureByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FProjectEquityStructureInfo>>() {
			
			@Override
			public List<FProjectEquityStructureInfo> call() {
				return projectSetupWebService.queryEquityStructureByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult saveBankLoan(final FProjectBankLoanBatchOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveBankLoan(order);
			}
		});
	}
	
	@Override
	public List<FProjectBankLoanInfo> queryBankLoanByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FProjectBankLoanInfo>>() {
			
			@Override
			public List<FProjectBankLoanInfo> call() {
				return projectSetupWebService.queryBankLoanByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FProjectBankLoanInfo> queryBankLoanByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FProjectBankLoanInfo>>() {
			
			@Override
			public List<FProjectBankLoanInfo> call() {
				return projectSetupWebService.queryBankLoanByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult saveExternalGuarantee(final FProjectExternalGuaranteeBatchOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveExternalGuarantee(order);
			}
		});
	}
	
	@Override
	public List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FProjectExternalGuaranteeInfo>>() {
			
			@Override
			public List<FProjectExternalGuaranteeInfo> call() {
				return projectSetupWebService.queryExternalGuaranteeByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FProjectExternalGuaranteeInfo>>() {
			
			@Override
			public List<FProjectExternalGuaranteeInfo> call() {
				return projectSetupWebService.queryExternalGuaranteeByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult saveCounterGuarantee(final FProjectCounterGuaranteeOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveCounterGuarantee(order);
			}
		});
	}
	
	@Override
	public List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FProjectCounterGuaranteePledgeInfo>>() {
			
			@Override
			public List<FProjectCounterGuaranteePledgeInfo> call() {
				return projectSetupWebService.queryCounterGuaranteePledgeByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FProjectCounterGuaranteePledgeInfo>>() {
			
			@Override
			public List<FProjectCounterGuaranteePledgeInfo> call() {
				return projectSetupWebService.queryCounterGuaranteePledgeByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByFormId(	final long formId) {
		return callInterface(new CallExternalInterface<List<FProjectCounterGuaranteeGuarantorInfo>>() {
			
			@Override
			public List<FProjectCounterGuaranteeGuarantorInfo> call() {
				return projectSetupWebService.queryCounterGuaranteeGuarantorByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FProjectCounterGuaranteeGuarantorInfo>>() {
			
			@Override
			public List<FProjectCounterGuaranteeGuarantorInfo> call() {
				return projectSetupWebService
					.queryCounterGuaranteeGuarantorByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<SetupFormProjectInfo> querySetupForm(final SetupFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SetupFormProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<SetupFormProjectInfo> call() {
				return projectSetupWebService.querySetupForm(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveGuaranteeEntrustedProject(final FProjectGuaranteeEntrustedOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveGuaranteeEntrustedProject(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveUnderwritingProject(final FProjectUnderwritingOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveUnderwritingProject(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveLgLitigationProject(final FProjectLgLitigationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.saveLgLitigationProject(order);
			}
		});
	}
	
	@Override
	public FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectGuaranteeEntrustedInfo>() {
			
			@Override
			public FProjectGuaranteeEntrustedInfo call() {
				return projectSetupWebService.queryGuaranteeEntrustedProjectByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectGuaranteeEntrustedInfo>() {
			
			@Override
			public FProjectGuaranteeEntrustedInfo call() {
				return projectSetupWebService.queryGuaranteeEntrustedProjectByCode(projectCode);
			}
		});
	}
	
	@Override
	public FProjectUnderwritingInfo queryUnderwritingProjectByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectUnderwritingInfo>() {
			
			@Override
			public FProjectUnderwritingInfo call() {
				return projectSetupWebService.queryUnderwritingProjectByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectUnderwritingInfo queryUnderwritingProjectByCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectUnderwritingInfo>() {
			
			@Override
			public FProjectUnderwritingInfo call() {
				return projectSetupWebService.queryUnderwritingProjectByCode(projectCode);
			}
		});
	}
	
	@Override
	public FProjectLgLitigationInfo queryLgLitigationProjectByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectLgLitigationInfo>() {
			
			@Override
			public FProjectLgLitigationInfo call() {
				return projectSetupWebService.queryLgLitigationProjectByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectLgLitigationInfo queryLgLitigationProjectByCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectLgLitigationInfo>() {
			
			@Override
			public FProjectLgLitigationInfo call() {
				return projectSetupWebService.queryLgLitigationProjectByCode(projectCode);
			}
		});
	}
	
	@Override
	public FProjectInfo queryProjectByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectInfo>() {
			
			@Override
			public FProjectInfo call() {
				return projectSetupWebService.queryProjectByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectInfo queryProjectByCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectInfo>() {
			
			@Override
			public FProjectInfo call() {
				return projectSetupWebService.queryProjectByCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult copyHistory(final CopyHisFormOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectSetupWebService.copyHistory(order);
			}
		});
	}

	@Override
	public FormBaseResult saveInnovativeProduct(final ProjectInnovativeProductOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return projectSetupWebService.saveInnovativeProduct(order);
			}
		});
	}
}
