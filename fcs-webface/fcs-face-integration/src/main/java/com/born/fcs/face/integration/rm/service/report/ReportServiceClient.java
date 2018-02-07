package com.born.fcs.face.integration.rm.service.report;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.report.GuaranteeBaseInfo;
import com.born.fcs.rm.ws.info.report.ReportInfo;
import com.born.fcs.rm.ws.info.report.inner.EntrustedeMainIndexInfo;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeAmountLimitInfo;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeClassifyInfo;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeCustomerData;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeKpiDataInfo;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeStructureInfo;
import com.born.fcs.rm.ws.info.report.inner.MainIndexDataInfo;
import com.born.fcs.rm.ws.info.report.inner.W10GuaranteeDataMonthlyInfo;
import com.born.fcs.rm.ws.info.report.outer.GuaranteeBusiInfoW5Info;
import com.born.fcs.rm.ws.order.report.ReportBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportService;

/**
 * 
 * 报表生成
 * 
 * @author lirz
 * 
 * 2016-8-11 下午2:41:37
 */
@Service("reportServiceClient")
public class ReportServiceClient extends ClientAutowiredBaseService implements ReportService {
	
	@Override
	public FcsBaseResult save(final ReportOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return reportWebService.save(order);
			}
		});
	}
	
	@Override
	public ReportInfo findById(final long reportId) {
		return callInterface(new CallExternalInterface<ReportInfo>() {
			
			@Override
			public ReportInfo call() {
				return reportWebService.findById(reportId);
			}
		});
	}
	
	@Override
	public ReportInfo findByAccountPeriod(final String reportCode, final int reportYear,
											final int reportMonth) {
		return callInterface(new CallExternalInterface<ReportInfo>() {
			
			@Override
			public ReportInfo call() {
				return reportWebService.findByAccountPeriod(reportCode, reportYear, reportMonth);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ReportInfo> queryList(final ReportQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ReportInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ReportInfo> call() {
				return reportWebService.queryList(queryOrder);
			}
		});
	}
	
	@Override
	public GuaranteeBaseInfo findGuaranteeBaseInfo(final ReportBaseQueryOrder baseQueryOrder) {
		return callInterface(new CallExternalInterface<GuaranteeBaseInfo>() {
			
			@Override
			public GuaranteeBaseInfo call() {
				return reportWebService.findGuaranteeBaseInfo(baseQueryOrder);
			}
		});
	}
	
	@Override
	public ReportInfo findGuaranteeDebtInfo(final ReportBaseQueryOrder baseQueryOrder) {
		return callInterface(new CallExternalInterface<ReportInfo>() {
			
			@Override
			public ReportInfo call() {
				return reportWebService.findGuaranteeDebtInfo(baseQueryOrder);
			}
		});
	}
	
	@Override
	public ExpectReleaseInfo finExpectReleaseInfo() {
		return callInterface(new CallExternalInterface<ExpectReleaseInfo>() {
			
			@Override
			public ExpectReleaseInfo call() {
				return reportWebService.finExpectReleaseInfo();
			}
		});
	}
	
	@Override
	public GuaranteeStructureInfo findGuaranteeStructureInfo() {
		return callInterface(new CallExternalInterface<GuaranteeStructureInfo>() {
			
			@Override
			public GuaranteeStructureInfo call() {
				return reportWebService.findGuaranteeStructureInfo();
			}
		});
	}
	
	@Override
	public GuaranteeKpiDataInfo findGuaranteeKpiDataInfo() {
		return callInterface(new CallExternalInterface<GuaranteeKpiDataInfo>() {
			
			@Override
			public GuaranteeKpiDataInfo call() {
				return reportWebService.findGuaranteeKpiDataInfo();
			}
		});
	}
	
	@Override
	public MainIndexDataInfo findMainIndexDataInfo() {
		return callInterface(new CallExternalInterface<MainIndexDataInfo>() {
			
			@Override
			public MainIndexDataInfo call() {
				return reportWebService.findMainIndexDataInfo();
			}
		});
	}
	
	@Override
	public EntrustedeMainIndexInfo findEntrustedeMainIndexInfo() {
		return callInterface(new CallExternalInterface<EntrustedeMainIndexInfo>() {
			
			@Override
			public EntrustedeMainIndexInfo call() {
				return reportWebService.findEntrustedeMainIndexInfo();
			}
		});
	}
	
	@Override
	public GuaranteeClassifyInfo findGuaranteeClassifyInfo() {
		return callInterface(new CallExternalInterface<GuaranteeClassifyInfo>() {
			
			@Override
			public GuaranteeClassifyInfo call() {
				return reportWebService.findGuaranteeClassifyInfo();
			}
		});
	}
	
	@Override
	public GuaranteeCustomerData findGuaranteeCustomerData() {
		return callInterface(new CallExternalInterface<GuaranteeCustomerData>() {
			
			@Override
			public GuaranteeCustomerData call() {
				return reportWebService.findGuaranteeCustomerData();
			}
		});
	}
	
	@Override
	public GuaranteeBusiInfoW5Info findGuaranteeBusiInfoW5Info() {
		return callInterface(new CallExternalInterface<GuaranteeBusiInfoW5Info>() {
			
			@Override
			public GuaranteeBusiInfoW5Info call() {
				return reportWebService.findGuaranteeBusiInfoW5Info();
			}
		});
	}
	
	@Override
	public GuaranteeAmountLimitInfo findGuaranteeAmountLimitInfo() {
		return callInterface(new CallExternalInterface<GuaranteeAmountLimitInfo>() {
			
			@Override
			public GuaranteeAmountLimitInfo call() {
				return reportWebService.findGuaranteeAmountLimitInfo();
			}
		});
	}
	
	@Override
	public W10GuaranteeDataMonthlyInfo findW10GuaranteeDataMonthlyInfo() {
		return callInterface(new CallExternalInterface<W10GuaranteeDataMonthlyInfo>() {
			
			@Override
			public W10GuaranteeDataMonthlyInfo call() {
				return reportWebService.findW10GuaranteeDataMonthlyInfo();
			}
		});
	}
	
	@Override
	public FcsBaseResult runRegularReport(final String reportDate) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return reportWebService.runRegularReport(reportDate);
			}
		});
	}
}
