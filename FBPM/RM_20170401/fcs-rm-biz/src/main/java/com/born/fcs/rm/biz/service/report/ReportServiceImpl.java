package com.born.fcs.rm.biz.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.exception.ExceptionFactory;
import com.born.fcs.rm.dal.dataobject.ReportDO;
import com.born.fcs.rm.domain.context.FcsPmDomainHolder;
import com.born.fcs.rm.ws.enums.ReportCodeEnum;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.enums.VersionEnum;
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
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.report.ReportBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.report.ReportService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 报表生成记录
 * 
 * @author lirz
 * 
 * 2016-8-5 上午11:01:43
 */
@Service("reportService")
public class ReportServiceImpl extends BaseAutowiredDomainService implements ReportService,
																	ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Autowired
	protected SubmissionService submissionService;
	
	@Override
	public FcsBaseResult save(final ReportOrder order) {
		return commonProcess(order, "保存报表", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ReportCodeEnum reportCode = ReportCodeEnum.getByCode(order.getReportCode());
				if (null == reportCode) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未定义的报表 ");
				}
				ReportDO reportDO = new ReportDO();
				BeanCopier.staticCopy(order, reportDO);
				reportDO.setVersion(VersionEnum.NOW.code());
				reportDO.setReportType(reportCode.getType().code());
				reportDO.setRawAddTime(getSysdate());
				//更新历史版本
				if (StringUtil.equals(ReportCodeEnum.BASE_REPORT.code(), reportCode.code())) //基层表报时历史数据单独处理
					reportDAO.updateBaseDataToHis(reportDO);
				else
					reportDAO.updateToHis(reportDO);
				long reportId = reportDAO.insert(reportDO);
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(reportId);
				
				if (StringUtil.isNotBlank(reportCode.getService())) {
					ReportProcessService reportProcessService = (ReportProcessService) getBean(reportCode
						.getService());
					if (null != reportProcessService) {
						reportProcessService.save(order);
					}
				}
				//更新上报数据报表的状态
				if (StringUtil.isNotEmpty(reportCode.getQuoted())) {
					submissionService.updateStatus(reportCode.getQuoted(), order.getReportYear(),
						order.getReportMonth());
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ReportInfo findById(long reportId) {
		ReportDO report = reportDAO.findById(reportId);
		if (null == report) {
			return null;
		}
		
		ReportInfo reportInfo = new ReportInfo();
		BeanCopier.staticCopy(report, reportInfo);
		reportInfo.setReportCode(ReportCodeEnum.getByCode(report.getReportCode()));
		
		return reportInfo;
	}
	
	@Override
	public ReportInfo findByAccountPeriod(String reportCode, int reportYear, int reportMonth) {
		ReportCodeEnum reportCodeEnum = ReportCodeEnum.getByCode(reportCode);
		if (null == reportCodeEnum) {
			return null;
		}
		
		ReportQueryOrder queryOrder = new ReportQueryOrder();
		queryOrder.setReportYear(reportYear);
		queryOrder.setReportMonth(reportMonth);
		queryOrder.setReportCode(reportCode);
		queryOrder.setVersion(VersionEnum.NOW.code());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<ReportInfo> pageList = queryList(queryOrder);
		
		ReportInfo reportInfo = null;
		if (null != pageList && ListUtil.isNotEmpty(pageList.getPageList())) {
			reportInfo = pageList.getPageList().get(0);
		}
		if (StringUtil.isNotBlank(reportCodeEnum.getService())) {
			ReportProcessService reportProcessService = (ReportProcessService) getBean(reportCodeEnum
				.getService());
			if (null != reportProcessService) {
				ReportQueryOrder newQueryOrder = new ReportQueryOrder();
				newQueryOrder.setReportYear(reportYear);
				newQueryOrder.setReportMonth(reportMonth);
				newQueryOrder.setReportCode(reportCode);
				//obj.sortCol被用来使用 外部报表，引用上报数据为空的，不允许报送 的标志
				Object obj = reportProcessService.findByAccountPeriod(newQueryOrder);
				if (null != obj) {
					if (null == reportInfo) {
						reportInfo = new ReportInfo();
					}
					reportInfo.setObj(obj);
				}
			}
		}
		
		return reportInfo;
	}
	
	@Override
	public QueryBaseBatchResult<ReportInfo> queryList(ReportQueryOrder queryOrder) {
		QueryBaseBatchResult<ReportInfo> batchResult = new QueryBaseBatchResult<>();
		
		ReportDO report = new ReportDO();
		BeanCopier.staticCopy(queryOrder, report);
		
		List<String> reportTypeList = new ArrayList<>();
		if (StringUtil.isNotBlank(queryOrder.getReportTypes())) {
			String[] ss = queryOrder.getReportTypes().trim().split(",");
			for (String s : ss) {
				reportTypeList.add(s);
			}
		}
		
		long totalSize = reportDAO.findByConditionCount(report, queryOrder.getStartDate(),
			queryOrder.getEndDate(), reportTypeList, queryOrder.getDeptIdList());
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ReportDO> pageList = reportDAO.findByCondition(report, queryOrder.getStartDate(),
				queryOrder.getEndDate(), reportTypeList, component.getFirstRecord(),
				component.getPageSize(), queryOrder.getSortCol(), queryOrder.getSortCol(),
				queryOrder.getDeptIdList());
			List<ReportInfo> list = batchResult.getPageList();
			if (totalSize > 0) {
				for (ReportDO doObj : pageList) {
					ReportInfo info = new ReportInfo();
					BeanCopier.staticCopy(doObj, info);
					info.setReportCode(ReportCodeEnum.getByCode(doObj.getReportCode()));
					list.add(info);
				}
			}
			
			batchResult.initPageParam(component);
			batchResult.setPageList(list);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String beanName) throws BeansException {
		return applicationContext.getBean(beanName);
	}
	
	private ReportInfo findReport(ReportBaseQueryOrder baseQueryOrder) {
		ReportCodeEnum reportCodeEnum = ReportCodeEnum.getByCode(baseQueryOrder.getReportCode());
		if (null == reportCodeEnum) {
			return null;
		}
		
		ReportQueryOrder queryOrder = new ReportQueryOrder();
		BeanCopier.staticCopy(baseQueryOrder, queryOrder);
		queryOrder.setVersion(VersionEnum.NOW.code());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<ReportInfo> pageList = queryList(queryOrder);
		if (null != pageList && ListUtil.isNotEmpty(pageList.getPageList())) {
			return pageList.getPageList().get(0);
		}
		
		return null;
	}
	
	@Override
	public GuaranteeBaseInfo findGuaranteeBaseInfo(ReportBaseQueryOrder baseQueryOrder) {
		ReportInfo report = findReport(baseQueryOrder);
		if (null == report) {
			return null;
		}
		
		GuaranteeBaseInfo info = new GuaranteeBaseInfo();
		BeanCopier.staticCopy(report, info);
		SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
		submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGQKB.code());
		submissionQueryOrder.setReportYear(baseQueryOrder.getReportYear());
		submissionQueryOrder.setReportMonth(baseQueryOrder.getReportMonth());
		submissionQueryOrder.setStatus(ReportStatusEnum.SUBMITTED);
		submissionQueryOrder.setPageNumber(1L);
		submissionQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<SubmissionInfo> batchResult = submissionService
			.query(submissionQueryOrder);
		if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
			SubmissionInfo submission = batchResult.getPageList().get(0);
			info.setSubmission(submission);
			submission = submissionService.findById(submission.getSubmissionId());
			if (null != submission) {
				info.setSubmission(submission);
			}
			return info;
		}
		
		return null;
	}
	
	@Override
	public ReportInfo findGuaranteeDebtInfo(ReportBaseQueryOrder baseQueryOrder) {
		ReportInfo report = findReport(baseQueryOrder);
		if (null == report) {
			return null;
		}
		
		//		GuaranteeBaseInfo info = new GuaranteeBaseInfo();
		//		BeanCopier.staticCopy(report, info);
		SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
		submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGQKB.code());
		submissionQueryOrder.setReportYear(baseQueryOrder.getReportYear());
		submissionQueryOrder.setReportMonth(baseQueryOrder.getReportMonth());
		submissionQueryOrder.setStatus(ReportStatusEnum.SUBMITTED);
		submissionQueryOrder.setPageNumber(1L);
		submissionQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<SubmissionInfo> batchResult = submissionService
			.query(submissionQueryOrder);
		if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
			SubmissionInfo submission = batchResult.getPageList().get(0);
			report.setObj(submission);
			submission = submissionService.findById(submission.getSubmissionId());
			if (null != submission) {
				report.setObj(submission);
			}
			return report;
		}
		
		return null;
	}
	
	@Override
	public ExpectReleaseInfo finExpectReleaseInfo() {
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		return info;
	}
	
	@Override
	public GuaranteeStructureInfo findGuaranteeStructureInfo() {
		GuaranteeStructureInfo info = new GuaranteeStructureInfo();
		return info;
	}
	
	@Override
	public GuaranteeKpiDataInfo findGuaranteeKpiDataInfo() {
		return new GuaranteeKpiDataInfo();
	}
	
	@Override
	public MainIndexDataInfo findMainIndexDataInfo() {
		return new MainIndexDataInfo();
	}
	
	@Override
	public EntrustedeMainIndexInfo findEntrustedeMainIndexInfo() {
		return new EntrustedeMainIndexInfo();
	}
	
	@Override
	public GuaranteeClassifyInfo findGuaranteeClassifyInfo() {
		return new GuaranteeClassifyInfo();
	}
	
	@Override
	public GuaranteeCustomerData findGuaranteeCustomerData() {
		return new GuaranteeCustomerData();
	}
	
	@Override
	public GuaranteeBusiInfoW5Info findGuaranteeBusiInfoW5Info() {
		return new GuaranteeBusiInfoW5Info();
	}
	
	@Override
	public GuaranteeAmountLimitInfo findGuaranteeAmountLimitInfo() {
		return new GuaranteeAmountLimitInfo();
	}
	
	@Override
	public W10GuaranteeDataMonthlyInfo findW10GuaranteeDataMonthlyInfo() {
		return new W10GuaranteeDataMonthlyInfo();
	}
}
