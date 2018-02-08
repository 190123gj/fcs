package com.born.fcs.rm.biz.service.report;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.exception.ExceptionFactory;
import com.born.fcs.rm.dal.dataobject.RegularProjectChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectImpelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectStoreInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectSubBusiTypeInfoDO;
import com.born.fcs.rm.dal.dataobject.ReportDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectMultiCapitalChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectSubBusiTypeDO;
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
import com.yjf.common.lang.util.money.Money;
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
	
	@Autowired
	protected PmReportService pmReportServiceClient;
	
	@Autowired
	protected ProjectCreditConditionService projectCreditConditionServiceClient;
	
	@Autowired
	protected ProjectContractService projectContractServiceClient;
	
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
	
	@Override
	public FcsBaseResult runRegularReport(final String reportDate) {
		
		FcsBaseResult result = createResult();
		try {
			if (reportDate == null || reportDate.length() == 10) { // 2017-01-01
				logger.info("启动基础定期数据取数线程：{}", reportDate);
				taskExecutor.execute(createRunThread(reportDate));
			} else { // "2017-01-01,2017-01-22";
			
				String reportRange[] = reportDate.split(",");
				//起
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTime(DateUtil.parse(reportRange[0]));
				//止
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(DateUtil.parse(reportRange[1]));
				
				if (endCalendar.getTime().after(startCalendar.getTime())) {
					String rDate = DateUtil.dtSimpleFormat(startCalendar.getTime());
					logger.info("启动基础定期数据取数线程：{}", rDate);
					taskExecutor.execute(createRunThread(rDate));
					while (endCalendar.getTime().after(startCalendar.getTime())) {
						Thread.sleep(10000);
						startCalendar.add(Calendar.DAY_OF_MONTH, 1);
						rDate = DateUtil.dtSimpleFormat(startCalendar.getTime());
						logger.info("启动基础定期数据取数线程：{}", rDate);
						taskExecutor.execute(createRunThread(rDate));
					}
				}
			}
			//doRegularReport(reportDate);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("基层定期报表取数错误:{}", e);
			result.setSuccess(false);
			result.setMessage("基层定期报表取数错误");
		}
		return result;
	}
	
	/**
	 * 创建跑数据的线程
	 * @param reportDate
	 * @return
	 */
	private Runnable createRunThread(final String reportDate) {
		
		return new Runnable() {
			
			public void run() {
				doRegularReport(reportDate);
			}
		};
	}
	
	private void doRegularReport(String reportDate) {
		Map<String, Object> param = getReportParam(reportDate);
		Date rDate = DateUtil.parse((String) param.get("reportDate"));
		param.put("rDate", rDate);
		
		//先跑项目基础情况表，后续会用到
		regularProjectBaseInfo(param);
		//客户基本情况表，后续会用到
		regularCustomerBaseInfo(param);
		//项目推进情况表
		regularProjectImpelInfo(param);
		//项目储备情况表
		regularProjectStoreInfo(param);
		//项目运行情况表
		regularProjectRunInfo(param);
		//项目收入情况
		regularProjectIncomeInfo(param);
		//项目风险情况
		regularProjectRiskInfo(param);
		//项目月明细
		regularProjectExtraList(param);
		//项目渠道情况（项目渠道、资金渠道）
		regularProjectChannelInfo(param);
		//项目业务细分类情况
		regularProjectSubBusiTypeInfo(param);
		
		//报告期是当月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			copy2Month(param);
		}
		
		//滞后数据处理 上月
		Map<String, Object> lastParam = getReportParam(String
			.valueOf(param.get("lastMonthEndDate")));
		rDate = DateUtil.parse((String) lastParam.get("reportDate"));
		lastParam.put("rDate", rDate);
		
		//收入情况表本月跑上月的数据
		regularProjectIncomeInfo(lastParam);
		regularProjectIncomeInfoMonthDAO.deleteByReportDate((String) lastParam
			.get("reportYearMonth"));
		logger.info("项目收入情况上月数据：{}", regularReportDAO.insertRegularProjectIncomeMonth(lastParam));
		
//		//滞后数据处理 上上月
//		Map<String, Object> lastLastParam = getReportParam(String.valueOf(lastParam
//			.get("lastMonthEndDate")));
//		rDate = DateUtil.parse((String) lastLastParam.get("reportDate"));
//		lastParam.put("rDate", rDate);
//		
//		//收入情况表本月跑上月的数据
//		regularProjectIncomeInfo(lastLastParam);
//		regularProjectIncomeInfoMonthDAO.deleteByReportDate((String) lastLastParam
//			.get("reportYearMonth"));
//		logger.info("项目收入情况上上月数据：{}",
//			regularReportDAO.insertRegularProjectIncomeMonth(lastLastParam));
	}
	
	/**
	 * 获取公共参数
	 * @return
	 */
	private Map<String, Object> getReportParam(String reportDate) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Calendar calendar = Calendar.getInstance();
		
		//自定义报告期
		if (StringUtil.isNotBlank(reportDate)) {
			calendar.setTime(DateUtil.parse(reportDate));
			param.put("reportDate", reportDate);
		} else {
			//系统时间 取报告期取前一天
			Date now = extraDAO.getSysdate();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			param.put("reportDate", DateUtil.dtSimpleFormat(calendar.getTime()));
		}
		
		param.put("pmDbTitle", pmDbTitle);
		param.put("fmDbTitle", fmDbTitle);
		param.put("crmDbTitle", crmDbTitle);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		param.put("reportYear", year);
		param.put("reportMonth", month);
		param.put("reportDay", day);
		param.put("thisMonthStartDate", year + "-" + (month < 10 ? "0" + month : month + "")
										+ "-01");
		param.put("thisYearStartDate", year + "-01-01");
		param.put("lastYearEndDate", (year - 1) + "-12-31");
		param.put("reportYearMonth", year + "-" + (month < 10 ? "0" + month : month + ""));
		
		//lastMonthEndDate
		Calendar lastCalendar = Calendar.getInstance();
		lastCalendar.setTime(DateUtil.parse(String.valueOf(param.get("thisMonthStartDate"))));
		lastCalendar.add(Calendar.DAY_OF_MONTH, -1);
		param.put("lastMonthEndDate", DateUtil.dtSimpleFormat(lastCalendar.getTime()));
		
		return param;
	}
	
	/**
	 * 项目基本情况表
	 * @param param
	 */
	private void regularProjectBaseInfo(Map<String, Object> param) {
		try {
			logger.info("开始生成项目基本情况表数据：{}", param);
			regularProjectBaseInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成项目基本情况表数据完成：{}", regularReportDAO.insertRegularProjectBase(param));
		} catch (Exception e) {
			logger.error("生成项目基本情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 客户基本情况表
	 * @param param
	 */
	private void regularCustomerBaseInfo(Map<String, Object> param) {
		try {
			logger.info("开始生成客户基本情况表数据：{}", param);
			regularCustomerBaseInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成客户基本情况表数据完成：{}", regularReportDAO.insertRegularCustomerBase(param));
		} catch (Exception e) {
			logger.error("生成客户基本情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目推进情况
	 * @param param
	 */
	private void regularProjectImpelInfo(Map<String, Object> param) {
		
		int pageSize = 100;
		try {
			
			logger.info("开始生成项目推进情况表数据：{}", param);
			
			regularProjectImpelInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			
			long implCount = regularReportDAO.countRegularProjectImpel(param);
			
			if (implCount > 0) {
				
				PageComponent page = new PageComponent(pageSize, 1, (int) implCount);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectImpelInfoDO> impelList = regularReportDAO
					.selectRegularProjectImpel(param);
				
				for (RegularProjectImpelInfoDO impelDO : impelList) {
					fillImpelProgress(impelDO);
					regularProjectImpelInfoDAO.insert(impelDO);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) implCount);
					
					param.put("limitStart", page.getFirstRecord());
					
					impelList = regularReportDAO.selectRegularProjectImpel(param);
					for (RegularProjectImpelInfoDO impelDO : impelList) {
						fillImpelProgress(impelDO);
						regularProjectImpelInfoDAO.insert(impelDO);
					}
				}
			}
			logger.info("生成目推进情况表数据完成：{}", implCount);
		} catch (Exception e) {
			logger.error("生成目推进情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 填充项目推进情况进度描述
	 * @param impelDO
	 */
	private void fillImpelProgress(RegularProjectImpelInfoDO impelDO) {
		
		PmReportDOQueryOrder order = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField field = new FcsField();
		field.setDataTypeEnum(DataTypeEnum.DATE);
		field.setColName("date");
		fieldMap.put("date", field);
		order.setFieldMap(fieldMap);
		
		String sql = null;
		List<DataListItem> list = null;
		
		//复议
		String huyMessage = "";
		
		//有会议开始时间却没的尽调时间，提起会议了,重新查尽调时间
		if (impelDO.getInvestFinishDate() == null && impelDO.getApprovalTime() != null) {
			
			sql = "SELECT f.finish_time 'date' FROM f_investigation p JOIN form f ON p.form_id=f.form_id  WHERE f.status='DELETED' AND p.project_code='"
					+ impelDO.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportServiceClient.doQuery(order);
			if (ListUtil.isNotEmpty(list))
				for (DataListItem itm : list) {
					for (ReportItem it : itm.getValueList()) {
						impelDO.setInvestFinishDate((Date) it.getObject());
					}
				}
			huyMessage = "7、已发起复议";
		}
		
		String lxMessage = "";//项目立项阶段记录
		sql = "SELECT f.status status,f.submit_time 'date' FROM f_project  p JOIN form f ON f.form_id=p.form_id WHERE p.project_code='"
				+ impelDO.getProjectCode() + "'";
		order.setSql(sql);
		list = pmReportServiceClient.doQuery(order);
		if (ListUtil.isNotEmpty(list)) {
			String tj = "";//提交信息
			String sh = "";//审核信息
			for (DataListItem itm : list) {
				ProjectPhasesStatusEnum status = ProjectPhasesStatusEnum.getByCode(String
					.valueOf(itm.getMap().get("status")));
				if (status == null) {
					if (StringUtil.equals(String.valueOf(itm.getMap().get("status")),
						FormStatusEnum.DENY.getCode())
						|| StringUtil.equals(String.valueOf(itm.getMap().get("status")),
							FormStatusEnum.BACK.getCode())) {
						status = ProjectPhasesStatusEnum.NOPASS;
					}
					
				}
				if (status == ProjectPhasesStatusEnum.NOPASS
					|| status == ProjectPhasesStatusEnum.APPROVAL
					|| status == ProjectPhasesStatusEnum.AUDITING) {
					
					if (status == ProjectPhasesStatusEnum.AUDITING) {
						tj += "1、" + "立项申请已提交，等待审核通过；";
						
					} else {
						tj += "1、" + "立项申请已提交，等待审核通过；";
						
						sh += "2、" + "立项申请" + status.getMessage() + "；";
						
					}
				}
				
			}
			lxMessage = tj + sh;
		}
		
		//尽调阶段记录
		String jdMessage = "";//项目尽调阶段记录
		sql = "SELECT status ,f.submit_time 'date' FROM f_investigation i JOIN form f ON i.form_id=f.form_id WHERE project_code='"
				+ impelDO.getProjectCode() + "'";
		order.setSql(sql);
		list = pmReportServiceClient.doQuery(order);
		if (ListUtil.isNotEmpty(list)) {
			String tj = "";//提交信息
			String sh = "";//审核信息
			for (DataListItem itm : list) {
				ProjectPhasesStatusEnum status = ProjectPhasesStatusEnum.getByCode(String
					.valueOf(itm.getMap().get("status")));
				if (status == null) {
					if (StringUtil.equals(String.valueOf(itm.getMap().get("status")),
						FormStatusEnum.DENY.getCode())
						|| StringUtil.equals(String.valueOf(itm.getMap().get("status")),
							FormStatusEnum.BACK.getCode())) {
						status = ProjectPhasesStatusEnum.NOPASS;
					}
					
				}
				if (StringUtil.equals("DELETED", String.valueOf(itm.getMap().get("status")))
					&& impelDO.getApprovalTime() != null) {
					status = ProjectPhasesStatusEnum.APPROVAL;
				}
				if (status == ProjectPhasesStatusEnum.NOPASS
					|| status == ProjectPhasesStatusEnum.APPROVAL
					|| status == ProjectPhasesStatusEnum.AUDITING) {
					
					if (status == ProjectPhasesStatusEnum.AUDITING) {
						tj += "3、" + "尽职调查报告已提交，等待审核通过；";
					} else {
						tj += "3、" + "尽职调查报告已提交，等待审核通过；";
						
						sh += "4、" + "尽职调查报告审核" + status.getMessage() + "；";
					}
					
				}
				
			}
			jdMessage = tj + sh;
		}
		
		//会议阶段记录
		String hyMessage = "";//项目会议阶段记录
		sql = "SELECT c.council_id,c.council_code,IFNULL(c.project_vote_result,'') 'status',co.start_time 'date' FROM council_apply a LEFT JOIN council_projects c ON a.apply_id=c.apply_id  JOIN council co ON c.council_id=co.council_id WHERE c.project_code='"
				+ impelDO.getProjectCode()
				+ "'  ORDER BY a.raw_add_time ASC,c.raw_add_time ASC,co.raw_add_time ASC";
		order.setSql(sql);
		list = pmReportServiceClient.doQuery(order);
		if (ListUtil.isNotEmpty(list)) {
			String tj = "";//提交信息
			String sh = "";//审核信息
			for (DataListItem itm : list) {
				String status = String.valueOf(itm.getMap().get("status"));
				String code = String.valueOf(itm.getMap().get("council_code"));
				
				if (StringUtil.isBlank(status)
					|| StringUtil.equals(status, ProjectVoteResultEnum.NOT_BEGIN.code())
					|| StringUtil.equals(status, ProjectVoteResultEnum.IN_VOTE.code())) {
					
					if (StringUtil.isBlank(tj)) {
						tj += "5、" + "已安排" + code + "上会，等待上会完成；";
					} else {
						tj += "已安排" + code + "上会，等待上会完成；";
					}
				} else {
					String rs = ProjectVoteResultEnum.getMsgByCode(status);
					if (StringUtil.isBlank(tj)) {
						tj += "5、" + "已安排" + code + "上会，等待上会完成；";
					} else {
						tj += "已安排" + code + "上会，等待上会完成；";
					}
					
					if (StringUtil.isBlank(sh)) {
						sh += "6、" + "上会" + code + "" + rs + "；";
					} else {
						sh += "上会" + code + "" + rs + "；";
					}
					
				}
			}
			hyMessage = tj + sh;
		}
		impelDO.setProgress(lxMessage + jdMessage + hyMessage + huyMessage);
	}
	
	/**
	 * 项目储备情况表
	 * @param param
	 */
	private void regularProjectStoreInfo(Map<String, Object> param) {
		
		int pageSize = 100;
		
		try {
			
			logger.info("开始生成项目储备情况表数据：{}", param);
			
			regularProjectStoreInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			
			long storeCount = regularReportDAO.countRegularProjectStore(param);
			
			if (storeCount > 0) {
				PageComponent page = new PageComponent(pageSize, 1, (int) storeCount);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectStoreInfoDO> storeList = regularReportDAO
					.selectRegularProjectStore(param);
				
				//合同签订情况sql
				String contractSql = "SELECT "
										+ "COUNT(*) contract_num,"
										+ "SUM(CASE WHEN ( "
										+ "CASE WHEN p.is_his = 1 OR "
										+ "i.return_add_time IS NULL THEN i.signed_time ELSE i.return_add_time "
										+ "END <= CONCAT('#reportDate#', ' 23:59:59')) THEN 1 ELSE 0 "
										+ "END) sign_num FROM #pmDbTitle#.f_project_contract c "
										+ "JOIN #pmDbTitle#.f_project_contract_item i "
										+ "ON c.contract_id = i.contract_id "
										+ "JOIN #pmDbTitle#.project p "
										+ "ON c.project_code = p.project_code "
										+ "WHERE i.contract_status NOT IN ('DELETED', 'END', 'INVALID') "
										+ "AND c.raw_add_time <= CONCAT('#reportDate#', ' 23:59:59') "
										+ "AND c.project_code = '#projectCode#'";
				
				//授信落实情况sql
				String creditSql = "SELECT COUNT(*) credit_num, "
									+ "SUM(CASE WHEN is_confirm = 'IS' THEN 1 ELSE 0 END) confirm_num, "
									+ "SUM(CASE WHEN c.status = 'NOT_APPLY' THEN 1 ELSE 0 END) wait_num "
									+ "FROM #pmDbTitle#.project_credit_condition c "
									+ "WHERE project_code='#projectCode#' "
									+ "AND c.raw_add_time <= CONCAT('#reportDate#', ' 23:59:59')";
				
				contractSql = contractSql.replaceAll("#pmDbTitle#", pmDbTitle).replaceAll(
					"#reportDate#", (String) param.get("reportDate"));
				
				creditSql = creditSql.replaceAll("#pmDbTitle#", pmDbTitle).replaceAll(
					"#reportDate#", (String) param.get("reportDate"));
				
				//合同签订情况查询
				PmReportDOQueryOrder order = new PmReportDOQueryOrder();
				HashMap<String, FcsField> fieldMap = new HashMap<>();
				FcsField contractNum = new FcsField();
				//contractNum.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				contractNum.setColName("contract_num");
				fieldMap.put("contract_num", contractNum);
				
				FcsField signNum = new FcsField();
				//signNum.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				signNum.setColName("sign_num");
				fieldMap.put("sign_num", signNum);
				order.setFieldMap(fieldMap);
				
				//授信落实情况查询
				PmReportDOQueryOrder creditOrder = new PmReportDOQueryOrder();
				HashMap<String, FcsField> cFieldMap = new HashMap<>();
				FcsField creditNum = new FcsField();
				//creditNum.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				contractNum.setColName("credit_num");
				cFieldMap.put("credit_num", creditNum);
				
				FcsField confirmNum = new FcsField();
				//confirmNum.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				signNum.setColName("confirm_num");
				cFieldMap.put("confirm_num", confirmNum);
				FcsField waitNum = new FcsField();
				//confirmNum.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				signNum.setColName("wait_num");
				cFieldMap.put("wait_num", waitNum);
				creditOrder.setFieldMap(cFieldMap);
				
				for (RegularProjectStoreInfoDO storeDO : storeList) {
					
					order.setSql(contractSql.replaceAll("#projectCode#", storeDO.getProjectCode()));
					creditOrder.setSql(creditSql.replaceAll("#projectCode#",
						storeDO.getProjectCode()));
					
					fillStoreProgress(storeDO, pmReportServiceClient.doQuery(order),
						pmReportServiceClient.doQuery(creditOrder));
					
					regularProjectStoreInfoDAO.insert(storeDO);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) storeCount);
					
					param.put("limitStart", page.getFirstRecord());
					
					storeList = regularReportDAO.selectRegularProjectStore(param);
					for (RegularProjectStoreInfoDO storeDO : storeList) {
						
						order.setSql(contractSql.replaceAll("#projectCode#",
							storeDO.getProjectCode()));
						creditOrder.setSql(creditSql.replaceAll("#projectCode#",
							storeDO.getProjectCode()));
						
						fillStoreProgress(storeDO, pmReportServiceClient.doQuery(order),
							pmReportServiceClient.doQuery(creditOrder));
						
						regularProjectStoreInfoDAO.insert(storeDO);
					}
				}
			}
			logger.info("生成目储备情况表数据完成：{}", storeCount);
		} catch (Exception e) {
			logger.error("生成目储备情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目储备情况进度填充
	 * @param storeDO
	 */
	private void fillStoreProgress(RegularProjectStoreInfoDO storeDO,
									List<DataListItem> contractList, List<DataListItem> creditList) {
		
		//组装费用描述
		if (storeDO.getFeeRate() > 0) {
			DecimalFormat nf = new DecimalFormat("###,###,##0.00");
			storeDO.setFeeDescribe(nf.format(storeDO.getFeeRate()) + storeDO.getFeeType());
		}
		
		//合同情况
		String htString = "";
		String contractStatus = "未发起";
		boolean sign = false;
		if (ListUtil.isNotEmpty(contractList)) {
			DataListItem contract = contractList.get(0);
			long contractNum = (contract.getMap().get("contract_num") == null ? 0 : NumberUtil
				.parseLong(String.valueOf(contract.getMap().get("contract_num"))));
			long signNum = (contract.getMap().get("sign_num") == null ? 0 : NumberUtil
				.parseLong(String.valueOf(contract.getMap().get("sign_num"))));
			if (contractNum == 0) {
				htString = "尚未签订合同";
			} else if (contractNum == signNum) {
				htString = "合同已全部签订";
				contractStatus = "已全签";
				sign = true;
			} else {
				htString = "合同未全部签订";
				contractStatus = "未全签";
				sign = true;
			}
		} else {
			contractStatus = "未发起";
		}
		storeDO.setContractStatus(contractStatus);
		
		String sxString = "";
		String creditStatus = "未发起";
		if (ListUtil.isNotEmpty(creditList)) {
			DataListItem credit = creditList.get(0);
			long creditNum = (credit.getMap().get("credit_num") == null ? 0 : NumberUtil
				.parseLong(String.valueOf(credit.getMap().get("credit_num"))));
			long confirmNum = (credit.getMap().get("confirm_num") == null ? 0 : NumberUtil
				.parseLong(String.valueOf(credit.getMap().get("confirm_num"))));
			long waitNum = (credit.getMap().get("wait_num") == null ? 0 : NumberUtil
				.parseLong(String.valueOf(credit.getMap().get("wait_num"))));
			if (creditNum == 0) {
				//sxString = "无授信条件";
				//creditStatus = "无反担保措施";
			} else if (creditNum == confirmNum) {
				sxString = "; 授信条件已全部落实";
				creditStatus = "全部落实";
			} else if (confirmNum == 0) {
				sxString = "; 尚未落实授信条件";
			} else {
				sxString = "; 授信条件未全部落实";
				creditStatus = "未全部落实";
			}
			if (creditNum > 0 && waitNum == creditNum) {
				creditStatus = "未发起";
			}
		}
		storeDO.setCreditStatus(creditStatus);
		if (sign) {
			storeDO.setProgress(htString + sxString);
		} else {
			storeDO.setProgress(htString);
			
		}
		
		// //查询项目全部合同
		//		ProjectContractQueryOrder cOrder = new ProjectContractQueryOrder();
		//		cOrder.setProjectCode(storeDO.getProjectCode());
		//		cOrder.setPageSize(500);
		//		QueryBaseBatchResult<ProjectContractResultInfo> contract = projectContractServiceClient
		//			.query(cOrder);
		//		boolean htQb = true;//合同全部通过
		//		String htString = "合同已全部签订";
		//		if (contract.isSuccess() && ListUtil.isNotEmpty(contract.getPageList())) {
		//			
		//			for (ProjectContractResultInfo infos : contract.getPageList()) {
		//				if (StringUtil.notEquals(String.valueOf(infos.getContractStatus()), "null")
		//					&& (infos.getContractStatus() == ContractStatusEnum.SUBMIT
		//						|| infos.getContractStatus() == ContractStatusEnum.AUDITING
		//						|| infos.getContractStatus() == ContractStatusEnum.CHECKED
		//						|| infos.getContractStatus() == ContractStatusEnum.APPROVAL
		//						|| infos.getContractStatus() == ContractStatusEnum.DENY
		//						|| infos.getContractStatus() == ContractStatusEnum.CONFIRMED
		//						|| infos.getContractStatus() == ContractStatusEnum.SEALING || infos
		//						.getContractStatus() == ContractStatusEnum.SEAL)) {
		//					htQb = false;
		//					htString = "合同申请通过，未全部签订，";
		//					break;
		//				}
		//			}
		//		} else {
		//			htString = "";
		//		}
		//
		// //查询授信落实
		//		FCreditConditionConfirmQueryOrder queryOrder = new FCreditConditionConfirmQueryOrder();
		//		queryOrder.setProjectCode(storeDO.getProjectCode());
		//		QueryBaseBatchResult<FCreditConditionConfirmInfo> credit = projectCreditConditionServiceClient
		//			.query(queryOrder);
		//		String sxString = "授信条件未全部落实";
		//		if (credit.isSuccess() && ListUtil.isNotEmpty(credit.getPageList())) {
		//			boolean qb = true;//已全部落实
		//			for (FCreditConditionConfirmInfo infos : credit.getPageList()) {
		//				if (infos.getFormStatus() == FormStatusEnum.SUBMIT
		//					|| infos.getFormStatus() == FormStatusEnum.AUDITING
		//					|| infos.getFormStatus() == FormStatusEnum.BACK
		//					|| infos.getFormStatus() == FormStatusEnum.DENY) {
		//					qb = false;
		//					break;
		//				}
		//			}
		//			if (qb)
		//				sxString = "授信条件已全部落实";
		//		}
		//		if (contract.isSuccess() && ListUtil.isNotEmpty(contract.getPageList())) {
		//			if (htQb) {
		//				storeDO.setProgress(htString);
		//			} else {
		//				storeDO.setProgress(htString + sxString);
		//			}
		//		}
	}
	
	/**
	 * 项目运行情况表
	 * @param param
	 */
	private void regularProjectRunInfo(Map<String, Object> param) {
		try {
			logger.info("开始生成项目运行情况表数据：{}", param);
			regularProjectRunInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成项目运行情况表数据完成：{}", regularReportDAO.insertRegularProjectRun(param));
		} catch (Exception e) {
			logger.error("生成项目运行情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目收入情况表
	 * @param param
	 */
	private void regularProjectIncomeInfo(Map<String, Object> param) {
		try {
			logger.info("开始生成项目收入情况表数据：{}", param);
			regularProjectIncomeInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成项目收入情况表数据完成：{}", regularReportDAO.insertRegularProjectIncome(param));
		} catch (Exception e) {
			logger.error("生成项目收入情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目风险情况表
	 * @param param
	 */
	private void regularProjectRiskInfo(Map<String, Object> param) {
		try {
			logger.info("开始生成项目风险情况表数据：{}", param);
			regularProjectRiskInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成项目风险情况表数据完成：{}", regularReportDAO.insertRegularProjectRisk(param));
		} catch (Exception e) {
			logger.error("生成项目风险情况表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目本月明细（收费计划、还款计划、本月发生、本月解保、本月代偿、本月回收）
	 * @param param
	 */
	private void regularProjectExtraList(Map<String, Object> param) {
		try {
			logger.info("开始生成项目本月明细表数据：{}", param);
			regularProjectExtraListInfoDAO.deleteByReportDate((Date) param.get("rDate"));
			logger.info("生成项目本月明细表数据完成：{}", regularReportDAO.insertRegularProjectExtraList(param));
		} catch (Exception e) {
			logger.error("生成项目本月明细表数据出错：{}", e);
		}
	}
	
	/**
	 * 项目渠道情况表(项目渠道、资金渠道)
	 * @param param
	 */
	private void regularProjectChannelInfo(Map<String, Object> param) {
		
		//删掉之前跑的数据
		regularProjectChannelInfoDAO.deleteByReportDate((Date) param.get("rDate"));
		
		logger.info("开始生成项目渠道相关数据：{}", param);
		try {
			
			//项目渠道部分和单资金渠道
			logger.info("生成项目渠道相关数据（项目渠道/单资金渠道）完成：{}",
				regularReportDAO.insertRegularProjectChannel(param));
		} catch (Exception e) {
			logger.error("生成项目渠道相关数据（项目渠道/单资金渠道）出错：{}", e);
		}
		
		//多资金渠道部分
		int pageSize = 100;
		try {
			long count = regularReportDAO.countRegularProjectMultiCapitalChannel(param);
			logger.info("开始生成项目渠道相关数据（多资金渠道）：{}", count);
			long insertCount = 0;
			if (count > 0) {
				
				Date now = getSysdate();
				
				PageComponent page = new PageComponent(pageSize, 1, (int) count);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectMultiCapitalChannelInfoDO> list = regularReportDAO
					.selectRegularProjectMultiCapitalChannel(param);
				for (RegularProjectMultiCapitalChannelInfoDO channel : list) {
					insertCount += fullChannelInfoAndInsert(channel, now);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) count);
					
					param.put("limitStart", page.getFirstRecord());
					
					list = regularReportDAO.selectRegularProjectMultiCapitalChannel(param);
					for (RegularProjectMultiCapitalChannelInfoDO channel : list) {
						insertCount += fullChannelInfoAndInsert(channel, now);
					}
				}
			}
			logger.info("生成项目渠道相关数据（多资金渠道）完成：{}", insertCount);
		} catch (Exception e) {
			logger.info("生成项目渠道相关数据（多金渠道）出错 {}", e);
		}
		
		//计算客户、资金渠道维度 统计占比
		try {
			long count = regularReportDAO.countRegularCustomerMultiCapitalChannelOccur(param);
			logger.info("开始计算并更新多资金渠道客户统计占比：{}", count);
			long updateCount = 0;
			if (count > 0) {
				PageComponent page = new PageComponent(100, 1, (int) count);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectMultiCapitalChannelInfoDO> list = regularReportDAO
					.selectRegularCustomerChannelMultiCapitalChannelOccur(param);
				for (RegularProjectMultiCapitalChannelInfoDO channel : list) {
					channel.setReportDate((Date) param.get("rDate"));
					updateCount += caculateCustomerCountRateAndUpdate(channel);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) count);
					param.put("limitStart", page.getFirstRecord());
					list = regularReportDAO.selectRegularProjectMultiCapitalChannel(param);
					for (RegularProjectMultiCapitalChannelInfoDO channel : list) {
						channel.setReportDate((Date) param.get("rDate"));
						updateCount += caculateCustomerCountRateAndUpdate(channel);
					}
				}
			}
			logger.info("计算并更新多资金渠道客户统计占比完成：{}", updateCount);
		} catch (Exception e) {
			logger.info("计算并更新多资金渠道客户统计占比出错 {}", e);
		}
	}
	
	/**
	 * 项目业务细分类
	 * @param param
	 */
	private void regularProjectSubBusiTypeInfo(Map<String, Object> param) {
		
		//删掉之前跑的数据
		regularProjectSubBusiTypeInfoDAO.deleteByReportDate((Date) param.get("rDate"));
		
		logger.info("开始生成项目业务细分类数据：{}", param);
		
		int pageSize = 100;
		try {
			long count = regularReportDAO.countRegularProjectSubBusiTypeInfo(param);
			
			logger.info("查询项目业务细分类数据：{}", count);
			
			long insertCount = 0;
			if (count > 0) {
				
				Date now = getSysdate();
				
				PageComponent page = new PageComponent(pageSize, 1, (int) count);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectSubBusiTypeDO> list = regularReportDAO
					.selectRegularProjectSubBusiTypeInfo(param);
				for (RegularProjectSubBusiTypeDO busiType : list) {
					insertCount += fullSubBusiTypeInfoAndInsert(busiType, now);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) count);
					
					param.put("limitStart", page.getFirstRecord());
					
					list = regularReportDAO.selectRegularProjectSubBusiTypeInfo(param);
					for (RegularProjectSubBusiTypeDO busiType : list) {
						insertCount += fullSubBusiTypeInfoAndInsert(busiType, now);
					}
				}
			}
			logger.info("生成项目业务细分类数据完成：{}", insertCount);
		} catch (Exception e) {
			logger.info("生成项目业务细分类数据出错 {}", e);
		}
		
		//计算客户、业务细分类维度 统计占比
		try {
			long count = regularReportDAO.countRegularCustomerSubBusiTypeOccur(param);
			logger.info("开始计算并更新业务细分类客户统计占比：{}", count);
			long updateCount = 0;
			if (count > 0) {
				PageComponent page = new PageComponent(100, 1, (int) count);
				
				param.put("pageSize", page.getPageSize());
				param.put("limitStart", page.getFirstRecord());
				
				List<RegularProjectSubBusiTypeDO> list = regularReportDAO
					.selectRegularCustomerSubBusiTypeOccur(param);
				for (RegularProjectSubBusiTypeDO busiType : list) {
					busiType.setReportDate((Date) param.get("rDate"));
					updateCount += caculateSubBusiTypeCustomerCountRateAndUpdate(busiType);
				}
				
				//循环翻页处理
				long totalPage = page.getPageCount();
				long pageNumber = page.getCurPage();
				
				while (pageNumber < totalPage) {
					pageNumber++;
					page = new PageComponent(100, (int) pageNumber, (int) count);
					param.put("limitStart", page.getFirstRecord());
					list = regularReportDAO.selectRegularCustomerSubBusiTypeOccur(param);
					for (RegularProjectSubBusiTypeDO busiType : list) {
						busiType.setReportDate((Date) param.get("rDate"));
						updateCount += caculateSubBusiTypeCustomerCountRateAndUpdate(busiType);
					}
				}
			}
			logger.info("计算并更新业务细分类客户统计占比完成：{}", updateCount);
		} catch (Exception e) {
			logger.info("计算并更新业务细分类客户统计占比出错 {}", e);
		}
	}
	
	/**
	 * 填充渠道信息并插入
	 * @param channel
	 * @param now
	 */
	private int fullChannelInfoAndInsert(RegularProjectMultiCapitalChannelInfoDO channel, Date now) {
		
		int insertCount = 0;
		try {
			
			Calendar calendar = Calendar.getInstance();
			
			Map<String, List<String>> channels = parse(channel.getContractInfo());
			if (channels != null && !channels.isEmpty()) {
				
				//首次发生情况
				Map<String, List<String>> firstOccurMap = parse(channel.getFirstOccurInfo());
				//累计发生情况
				Map<String, List<String>> occurMap = parse(channel.getOccurInfo());
				//期初发生情况
				Map<String, List<String>> occurBeginningMap = parse(channel.getOccurInfoBeginning());
				//本月发生情况
				Map<String, List<String>> occurThisMonthMap = parse(channel.getOccurInfoThisMonth());
				//本年发生情况
				Map<String, List<String>> occurThisYearMap = parse(channel.getOccurInfoThisYear());
				//解保情况
				Map<String, List<String>> releaseMap = parse(channel.getReleaseInfo());
				//期初解保情况
				Map<String, List<String>> releaseBeginningMap = parse(channel
					.getReleaseInfoBeginning());
				//本月解保情况
				Map<String, List<String>> releaseThisMonthMap = parse(channel
					.getReleaseInfoThisMonth());
				//本年解保情况
				Map<String, List<String>> releaseThisYearMap = parse(channel
					.getReleaseInfoThisYear());
				
				//循环多个资金渠道
				//渠道编码&&IFNULL(渠道类型,'-')&&IFNULL(渠道名称,'-')&&IFNULL(合同金额,0)||渠道编码1&&渠道类型1&&渠道名称1&&合同金额1
				//渠道编码&&IFNULL(首次发生时间,'-')||渠道编码1&&首次发生时间1
				//渠道编码&&IFNULL(金额,0)||渠道编码1&&金额1
				
				//项目最大发生额
				Money maxOccurAmount = Money.zero();
				List<RegularProjectChannelInfoDO> waitInsertList = Lists.newArrayList();
				//logger.info("项目资金渠道信息  {}", channel);
				for (String channelCode : channels.keySet()) {
					//初始化渠道相关数据
					RegularProjectChannelInfoDO channelInfoDO = new RegularProjectChannelInfoDO();
					BeanCopier.staticCopy(channel, channelInfoDO);
					channelInfoDO.setChannelRelation("资金渠道");
					channelInfoDO.setId(0);
					channelInfoDO.setCountRate(0);
					channelInfoDO.setIsNew("否");
					channelInfoDO.setIsInsurance("否");
					channelInfoDO.setRawAddTime(now);
					
					//渠道信息、合同金额
					List<String> channelInfo = channels.get(channelCode);
					channelInfoDO.setChannelCode(channelCode);
					channelInfoDO.setChannelType(StringUtil.equals("-", channelInfo.get(1)) ? null
						: channelInfo.get(1));
					channelInfoDO.setChannelName(StringUtil.equals("-", channelInfo.get(2)) ? null
						: channelInfo.get(2));
					channelInfoDO.setContractAmount(Money.cent(NumberUtil.parseLong(channelInfo
						.get(3))));
					
					//首次发生时间
					Date firstOccurDate = null;
					List<String> firstOccurInfo = firstOccurMap == null ? null : firstOccurMap
						.get(channelCode);
					if (ListUtil.isNotEmpty(firstOccurInfo)) {
						String firstOccurDateStr = firstOccurInfo.get(1);
						if (firstOccurDateStr != null && !StringUtil.equals(firstOccurDateStr, "-")) {
							firstOccurDate = DateUtil.parse(firstOccurDateStr);
						}
					}
					channelInfoDO.setFirstOccurDate(firstOccurDate);
					
					//首次是在当年的就是新增
					if (firstOccurDate != null) {
						calendar.setTime(firstOccurDate);
						int firstOccurYear = calendar.get(Calendar.YEAR);
						calendar.setTime(channel.getReportDate());
						if (firstOccurYear == calendar.get(Calendar.YEAR)) {
							channelInfoDO.setIsNew("是");
						}
					}
					
					//累计发生情况
					Money occurAmount = Money.zero();
					List<String> occurInfo = occurMap == null ? null : occurMap.get(channelCode);
					if (ListUtil.isNotEmpty(occurInfo)) {
						occurAmount = Money.cent(NumberUtil.parseLong(occurInfo.get(1)));
					}
					channelInfoDO.setOccurAmount(occurAmount);
					//最大发生额
					if (occurAmount.greaterThan(maxOccurAmount)) {
						maxOccurAmount = occurAmount;
					}
					
					//累计解保情况
					Money releaseAmount = Money.zero();
					List<String> releaseInfo = releaseMap == null ? null : releaseMap
						.get(channelCode);
					if (ListUtil.isNotEmpty(releaseInfo)) {
						releaseAmount = Money.cent(NumberUtil.parseLong(releaseInfo.get(1)));
					}
					channelInfoDO.setReleaseAmount(releaseAmount);
					
					//在保余额（期末在保余额）
					channelInfoDO.setBalance(occurAmount.subtract(releaseAmount));
					if (channelInfoDO.getBalance().greaterThan(ZERO_MONEY)) {
						channelInfoDO.setIsInsurance("是");
					}
					
					//期初发生情况
					Money occurBeginningAmount = Money.zero();
					List<String> occurBeginningInfo = occurBeginningMap == null ? null
						: occurBeginningMap.get(channelCode);
					if (ListUtil.isNotEmpty(occurBeginningInfo)) {
						occurBeginningAmount = Money.cent(NumberUtil.parseLong(occurBeginningInfo
							.get(1)));
					}
					
					//期初解保情况
					Money releaseBeginningAmount = Money.zero();
					List<String> releaseBeginningInfo = releaseBeginningMap == null ? null
						: releaseBeginningMap.get(channelCode);
					if (ListUtil.isNotEmpty(releaseBeginningInfo)) {
						releaseBeginningAmount = Money.cent(NumberUtil
							.parseLong(releaseBeginningInfo.get(1)));
					}
					
					//在保余额（期初在保余额）
					channelInfoDO.setBalanceBeginning(occurBeginningAmount
						.subtract(releaseBeginningAmount));
					
					//本月发生情况
					Money occurThisMonthAmount = Money.zero();
					List<String> occurThisMonthInfo = occurThisMonthMap == null ? null
						: occurThisMonthMap.get(channelCode);
					if (ListUtil.isNotEmpty(occurThisMonthInfo)) {
						occurThisMonthAmount = Money.cent(NumberUtil.parseLong(occurThisMonthInfo
							.get(1)));
					}
					channelInfoDO.setOccurAmountThisMonth(occurThisMonthAmount);
					
					//本月解保情况
					Money releaseThisMonthAmount = Money.zero();
					List<String> releaseThisMonthInfo = releaseThisMonthMap == null ? null
						: releaseThisMonthMap.get(channelCode);
					if (ListUtil.isNotEmpty(releaseThisMonthInfo)) {
						releaseThisMonthAmount = Money.cent(NumberUtil
							.parseLong(releaseThisMonthInfo.get(1)));
					}
					channelInfoDO.setReleaseAmountThisMonth(releaseThisMonthAmount);
					
					//本年发生情况
					Money occurThisYearAmount = Money.zero();
					List<String> occurThisYearInfo = occurThisYearMap == null ? null
						: occurThisYearMap.get(channelCode);
					if (ListUtil.isNotEmpty(occurThisYearInfo)) {
						occurThisYearAmount = Money.cent(NumberUtil.parseLong(occurThisYearInfo
							.get(1)));
					}
					channelInfoDO.setOccurAmountThisYear(occurThisYearAmount);
					
					//本年解保情况
					Money releaseThisYearAmount = Money.zero();
					List<String> releaseThisYearInfo = releaseThisYearMap == null ? null
						: releaseThisYearMap.get(channelCode);
					if (ListUtil.isNotEmpty(releaseThisYearInfo)) {
						releaseThisYearAmount = Money.cent(NumberUtil.parseLong(releaseThisYearInfo
							.get(1)));
					}
					channelInfoDO.setReleaseAmountThisYear(releaseThisYearAmount);
					
					waitInsertList.add(channelInfoDO);
				}
				
				//最大发生额的几个按比例平分
				List<RegularProjectChannelInfoDO> maxOccurChannel = Lists.newArrayList();
				for (RegularProjectChannelInfoDO channelInfoDO : waitInsertList) {
					if (channelInfoDO.getOccurAmount().greaterThan(ZERO_MONEY)
						&& channelInfoDO.getOccurAmount().equals(maxOccurAmount)) {
						maxOccurChannel.add(channelInfoDO);
					} else {
						regularProjectChannelInfoDAO.insert(channelInfoDO);
						insertCount++;
					}
				}
				int size = maxOccurChannel.size();
				if (size > 0) {
					//保证除不尽时合为1
					BigDecimal whole = new BigDecimal(1);
					BigDecimal negative = new BigDecimal(-1 * (size - 1));
					BigDecimal countRate = new BigDecimal(NumberUtil.formatDouble2(1d / size));
					
					int count = 1;
					for (RegularProjectChannelInfoDO channelInfoDO : maxOccurChannel) {
						if (count == size) {
							BigDecimal lastCountRate = whole.add(countRate.multiply(negative));
							channelInfoDO.setCountRate(lastCountRate.doubleValue());
						} else {
							channelInfoDO.setCountRate(countRate.doubleValue());
						}
						regularProjectChannelInfoDAO.insert(channelInfoDO);
						insertCount++;
						count++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("插入资金渠道数据出错 {},{}", channel, e);
		}
		return insertCount;
	}
	
	/**
	 * 填充业务细分类信息并插入
	 * @param subBusiType
	 * @param now
	 */
	private int fullSubBusiTypeInfoAndInsert(RegularProjectSubBusiTypeDO subBusiType, Date now) {
		
		int insertCount = 0;
		try {
			
			Calendar calendar = Calendar.getInstance();
			
			Map<String, List<String>> occurMap = parse(subBusiType.getOccurInfo());
			if (occurMap != null && !occurMap.isEmpty()) {
				//合同金额情况
				Map<String, List<String>> contractMap = parse(subBusiType.getContractInfo());
				
				//首次发生情况
				Map<String, List<String>> firstOccurMap = parse(subBusiType.getFirstOccurInfo());
				
				//期初发生情况
				Map<String, List<String>> occurBeginningMap = parse(subBusiType
					.getOccurInfoBeginning());
				//本月发生情况
				Map<String, List<String>> occurThisMonthMap = parse(subBusiType
					.getOccurInfoThisMonth());
				//本年发生情况
				Map<String, List<String>> occurThisYearMap = parse(subBusiType
					.getOccurInfoThisYear());
				//解保情况
				Map<String, List<String>> releaseMap = parse(subBusiType.getReleaseInfo());
				//期初解保情况
				Map<String, List<String>> releaseBeginningMap = parse(subBusiType
					.getReleaseInfoBeginning());
				//本月解保情况
				Map<String, List<String>> releaseThisMonthMap = parse(subBusiType
					.getReleaseInfoThisMonth());
				//本年解保情况
				Map<String, List<String>> releaseThisYearMap = parse(subBusiType
					.getReleaseInfoThisYear());
				
				//循环多个业务细分类
				//业务分类&&业务分类名称&&IFNULL(金额,0)||业务分类1&&业务分类名称1&&IFNULL(金额1,0)
				//业务分类&&业务分类名称&&IFNULL(首次发生时间,'-')||业务分类&&业务分类名称&&首次发生时间1
				
				//项目最大发生额
				Money maxOccurAmount = Money.zero();
				List<RegularProjectSubBusiTypeInfoDO> waitInsertList = Lists.newArrayList();
				//logger.info("项目资金渠道信息  {}", channel);
				for (String subBusiTypeCode : occurMap.keySet()) {
					//初始化业务细分类相关数据
					RegularProjectSubBusiTypeInfoDO subBusiTypeInfoDO = new RegularProjectSubBusiTypeInfoDO();
					BeanCopier.staticCopy(subBusiType, subBusiTypeInfoDO);
					subBusiTypeInfoDO.setId(0);
					subBusiTypeInfoDO.setCountRate(0);
					subBusiTypeInfoDO.setCustomerCountRate(0);
					subBusiTypeInfoDO.setIsNew("否");
					subBusiTypeInfoDO.setIsInsurance("否");
					subBusiTypeInfoDO.setRawAddTime(now);
					
					//细分类编码、细分类名称、累计发生额
					List<String> occurInfo = occurMap.get(subBusiTypeCode);
					subBusiTypeInfoDO.setSubBusiType(subBusiTypeCode);
					subBusiTypeInfoDO.setSubBusiTypeName(occurInfo.get(1));
					Money occurAmount = Money.cent(NumberUtil.parseLong(occurInfo.get(2)));
					subBusiTypeInfoDO.setOccurAmount(occurAmount);
					
					//最大发生额
					if (occurAmount.greaterThan(maxOccurAmount)) {
						maxOccurAmount = occurAmount;
					}
					
					//合同情况
					List<String> contractInfo = contractMap == null ? null : contractMap
						.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(contractInfo)) {
						subBusiTypeInfoDO.setContractAmount(Money.cent(NumberUtil
							.parseLong(contractInfo.get(2))));
					}
					
					//首次发生时间
					Date firstOccurDate = null;
					List<String> firstOccurInfo = firstOccurMap == null ? null : firstOccurMap
						.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(firstOccurInfo)) {
						String firstOccurDateStr = firstOccurInfo.get(2);
						if (firstOccurDateStr != null && !StringUtil.equals(firstOccurDateStr, "-")) {
							firstOccurDate = DateUtil.parse(firstOccurDateStr);
						}
					}
					subBusiTypeInfoDO.setFirstOccurDate(firstOccurDate);
					
					//首次是在当年的就是新增
					if (firstOccurDate != null) {
						calendar.setTime(firstOccurDate);
						int firstOccurYear = calendar.get(Calendar.YEAR);
						calendar.setTime(subBusiType.getReportDate());
						if (firstOccurYear == calendar.get(Calendar.YEAR)) {
							subBusiTypeInfoDO.setIsNew("是");
						}
					}
					
					//累计解保情况
					Money releaseAmount = Money.zero();
					List<String> releaseInfo = releaseMap == null ? null : releaseMap
						.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(releaseInfo)) {
						releaseAmount = Money.cent(NumberUtil.parseLong(releaseInfo.get(2)));
					}
					subBusiTypeInfoDO.setReleaseAmount(releaseAmount);
					
					//在保余额（期末在保余额）
					subBusiTypeInfoDO.setBalance(occurAmount.subtract(releaseAmount));
					if (subBusiTypeInfoDO.getBalance().greaterThan(ZERO_MONEY)) {
						subBusiTypeInfoDO.setIsInsurance("是");
					}
					
					//期初发生情况
					Money occurBeginningAmount = Money.zero();
					List<String> occurBeginningInfo = occurBeginningMap == null ? null
						: occurBeginningMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(occurBeginningInfo)) {
						occurBeginningAmount = Money.cent(NumberUtil.parseLong(occurBeginningInfo
							.get(2)));
					}
					
					//期初解保情况
					Money releaseBeginningAmount = Money.zero();
					List<String> releaseBeginningInfo = releaseBeginningMap == null ? null
						: releaseBeginningMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(releaseBeginningInfo)) {
						releaseBeginningAmount = Money.cent(NumberUtil
							.parseLong(releaseBeginningInfo.get(2)));
					}
					
					//在保余额（期初在保余额）
					subBusiTypeInfoDO.setBalanceBeginning(occurBeginningAmount
						.subtract(releaseBeginningAmount));
					
					//本月发生情况
					Money occurThisMonthAmount = Money.zero();
					List<String> occurThisMonthInfo = occurThisMonthMap == null ? null
						: occurThisMonthMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(occurThisMonthInfo)) {
						occurThisMonthAmount = Money.cent(NumberUtil.parseLong(occurThisMonthInfo
							.get(2)));
					}
					subBusiTypeInfoDO.setOccurAmountThisMonth(occurThisMonthAmount);
					
					//本月解保情况
					Money releaseThisMonthAmount = Money.zero();
					List<String> releaseThisMonthInfo = releaseThisMonthMap == null ? null
						: releaseThisMonthMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(releaseThisMonthInfo)) {
						releaseThisMonthAmount = Money.cent(NumberUtil
							.parseLong(releaseThisMonthInfo.get(2)));
					}
					subBusiTypeInfoDO.setReleaseAmountThisMonth(releaseThisMonthAmount);
					
					//本年发生情况
					Money occurThisYearAmount = Money.zero();
					List<String> occurThisYearInfo = occurThisYearMap == null ? null
						: occurThisYearMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(occurThisYearInfo)) {
						occurThisYearAmount = Money.cent(NumberUtil.parseLong(occurThisYearInfo
							.get(2)));
					}
					subBusiTypeInfoDO.setOccurAmountThisYear(occurThisYearAmount);
					
					//本年解保情况
					Money releaseThisYearAmount = Money.zero();
					List<String> releaseThisYearInfo = releaseThisYearMap == null ? null
						: releaseThisYearMap.get(subBusiTypeCode);
					if (ListUtil.isNotEmpty(releaseThisYearInfo)) {
						releaseThisYearAmount = Money.cent(NumberUtil.parseLong(releaseThisYearInfo
							.get(2)));
					}
					subBusiTypeInfoDO.setReleaseAmountThisYear(releaseThisYearAmount);
					
					waitInsertList.add(subBusiTypeInfoDO);
				}
				
				//最大发生额的几个按比例平分
				List<RegularProjectSubBusiTypeInfoDO> maxOccurBusiType = Lists.newArrayList();
				for (RegularProjectSubBusiTypeInfoDO subBusiTypeInfoDO : waitInsertList) {
					if (subBusiTypeInfoDO.getOccurAmount().greaterThan(ZERO_MONEY)
						&& subBusiTypeInfoDO.getOccurAmount().equals(maxOccurAmount)) {
						maxOccurBusiType.add(subBusiTypeInfoDO);
					} else {
						regularProjectSubBusiTypeInfoDAO.insert(subBusiTypeInfoDO);
						insertCount++;
					}
				}
				int size = maxOccurBusiType.size();
				if (size > 0) {
					//保证除不尽时合为1
					BigDecimal whole = new BigDecimal(1);
					BigDecimal negative = new BigDecimal(-1 * (size - 1));
					BigDecimal countRate = new BigDecimal(NumberUtil.formatDouble2(1d / size));
					
					int count = 1;
					for (RegularProjectSubBusiTypeInfoDO subBusiTypeInfoDO : maxOccurBusiType) {
						if (count == size) {
							BigDecimal lastCountRate = whole.add(countRate.multiply(negative));
							subBusiTypeInfoDO.setCountRate(lastCountRate.doubleValue());
						} else {
							subBusiTypeInfoDO.setCountRate(countRate.doubleValue());
						}
						regularProjectSubBusiTypeInfoDAO.insert(subBusiTypeInfoDO);
						insertCount++;
						count++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("插入业务细分类数据出错 {},{}", subBusiType, e);
		}
		return insertCount;
	}
	
	/**
	 * 计算并更新客户资金渠道统计占比
	 * @param channel
	 * @param now
	 */
	private int caculateCustomerCountRateAndUpdate(RegularProjectMultiCapitalChannelInfoDO channel) {
		int updateCount = 0;
		try {
			
			//累计发生情况
			Map<String, List<String>> occurMap = parse(channel.getOccurInfo());
			if (occurMap != null && !occurMap.isEmpty()) {
				//循环多个资金渠道
				//渠道编码&&IFNULL(金额,0)||渠道编码1&&金额1
				//客户最大发生额
				Money maxOccurAmount = Money.zero();
				List<RegularProjectChannelInfoDO> allList = Lists.newArrayList();
				for (String channelCode : occurMap.keySet()) {
					
					RegularProjectChannelInfoDO channelInfo = new RegularProjectChannelInfoDO();
					channelInfo.setChannelCode(channelCode);
					channelInfo.setCustomerId(channel.getCustomerId());
					channelInfo.setReportDate(channel.getReportDate());
					
					//累计发生情况
					Money occurAmount = Money.zero();
					List<String> occurInfo = occurMap.get(channelCode);
					if (ListUtil.isNotEmpty(occurInfo)) {
						occurAmount = Money.cent(NumberUtil.parseLong(occurInfo.get(1)));
					}
					channelInfo.setOccurAmount(occurAmount);
					
					//最大发生额
					if (occurAmount.greaterThan(maxOccurAmount)) {
						maxOccurAmount = occurAmount;
					}
					allList.add(channelInfo);
				}
				
				//最大发生额的几个按比例平分
				List<RegularProjectChannelInfoDO> maxOccurChannel = Lists.newArrayList();
				for (RegularProjectChannelInfoDO channelInfoDO : allList) {
					if (channelInfoDO.getOccurAmount().greaterThan(ZERO_MONEY)
						&& channelInfoDO.getOccurAmount().equals(maxOccurAmount)) {
						maxOccurChannel.add(channelInfoDO);
					} else {
						channelInfoDO.setCustomerCountRate(0);
						updateCount += regularReportDAO
							.updateRegularCustomerMultiCapitalChannelCountRate(channelInfoDO);
					}
				}
				int size = maxOccurChannel.size();
				if (size > 0) {
					//保证除不尽时合为1
					BigDecimal whole = new BigDecimal(1);
					BigDecimal negative = new BigDecimal(-1 * (size - 1));
					BigDecimal countRate = new BigDecimal(NumberUtil.formatDouble2(1d / size));
					
					int count = 1;
					for (RegularProjectChannelInfoDO channelInfoDO : maxOccurChannel) {
						if (count == size) {
							BigDecimal lastCountRate = whole.add(countRate.multiply(negative));
							channelInfoDO.setCustomerCountRate(lastCountRate.doubleValue());
						} else {
							channelInfoDO.setCustomerCountRate(countRate.doubleValue());
						}
						updateCount += regularReportDAO
							.updateRegularCustomerMultiCapitalChannelCountRate(channelInfoDO);
						count++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新客户资金渠道统计占比出错 {},{}", channel, e);
		}
		return updateCount;
	}
	
	/**
	 * 计算并更新客户业务细分类统计占比
	 * @param busiType
	 */
	private int caculateSubBusiTypeCustomerCountRateAndUpdate(RegularProjectSubBusiTypeDO busiType) {
		
		int updateCount = 0;
		try {
			
			//累计发生情况
			Map<String, List<String>> occurMap = parse(busiType.getOccurInfo());
			if (occurMap != null && !occurMap.isEmpty()) {
				//循环多个业务种类
				//业务编码&&金额||业务编码1&&金额1
				//客户最大发生额
				Money maxOccurAmount = Money.zero();
				List<RegularProjectSubBusiTypeInfoDO> allList = Lists.newArrayList();
				for (String busiSubTypeCode : occurMap.keySet()) {
					RegularProjectSubBusiTypeInfoDO busiTypeInfo = new RegularProjectSubBusiTypeInfoDO();
					busiTypeInfo.setSubBusiType(busiSubTypeCode);
					busiTypeInfo.setCustomerId(busiType.getCustomerId());
					busiTypeInfo.setReportDate(busiType.getReportDate());
					
					//累计发生情况
					Money occurAmount = Money.zero();
					List<String> occurInfo = occurMap.get(busiSubTypeCode);
					if (ListUtil.isNotEmpty(occurInfo)) {
						occurAmount = Money.cent(NumberUtil.parseLong(occurInfo.get(1)));
					}
					busiTypeInfo.setOccurAmount(occurAmount);
					
					//最大发生额
					if (occurAmount.greaterThan(maxOccurAmount)) {
						maxOccurAmount = occurAmount;
					}
					allList.add(busiTypeInfo);
				}
				
				//最大发生额的几个按比例平分
				List<RegularProjectSubBusiTypeInfoDO> maxOccurBusiType = Lists.newArrayList();
				for (RegularProjectSubBusiTypeInfoDO busiTypeInfo : allList) {
					if (busiTypeInfo.getOccurAmount().greaterThan(ZERO_MONEY)
						&& busiTypeInfo.getOccurAmount().equals(maxOccurAmount)) {
						maxOccurBusiType.add(busiTypeInfo);
					} else {
						busiTypeInfo.setCustomerCountRate(0);
						updateCount += regularReportDAO
							.updateRegularCustomerSubBusiTypeOccur(busiTypeInfo);
					}
				}
				int size = maxOccurBusiType.size();
				if (size > 0) {
					//保证除不尽时合为1
					BigDecimal whole = new BigDecimal(1);
					BigDecimal negative = new BigDecimal(-1 * (size - 1));
					BigDecimal countRate = new BigDecimal(NumberUtil.formatDouble2(1d / size));
					
					int count = 1;
					for (RegularProjectSubBusiTypeInfoDO busiTypeInfo : maxOccurBusiType) {
						if (count == size) {
							BigDecimal lastCountRate = whole.add(countRate.multiply(negative));
							busiTypeInfo.setCustomerCountRate(lastCountRate.doubleValue());
						} else {
							busiTypeInfo.setCustomerCountRate(countRate.doubleValue());
						}
						updateCount += regularReportDAO
							.updateRegularCustomerSubBusiTypeOccur(busiTypeInfo);
						count++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新客户业务细分类统计占比出错 {},{}", busiType, e);
		}
		return updateCount;
	}
	
	/**
	 * key：渠道编码/业务细分类编码, value：对应信息
	 * @param info
	 * @param type
	 * @return
	 */
	private Map<String, List<String>> parse(String info) {
		Map<String, List<String>> dataMap = null;
		try {
			if (StringUtil.isNotBlank(info)) {
				dataMap = new HashMap<String, List<String>>();
				//渠道编码&&IFNULL(渠道类型,'-')&&IFNULL(渠道名称,'-')&&IFNULL(合同金额,0)||渠道编码1&&渠道类型1&&渠道名称1&&合同金额1
				//渠道编码&&IFNULL(首次发生时间,'-')||渠道编码1&&首次发生时间1
				//渠道编码&&IFNULL(金额,0)||渠道编码1&&金额1
				//业务编码&&业务名称&&金额||业务编码1&&业务名称1&&金额1
				String[] channelArr = info.split("\\|\\|");
				for (String channel : channelArr) {
					String[] dataArr = channel.split("&&");
					List<String> dataList = new ArrayList<String>(4);
					for (String data : dataArr) {
						dataList.add(data);
					}
					dataMap.put(dataList.get(0), dataList);
				}
			}
		} catch (Exception e) {
			logger.error("解析多资金渠道/业务细分类信息出错，{}， {}", info, e);
		}
		return dataMap;
	}
	
	/**
	 * 当月最后一天从日表中拷贝一份到月表
	 * @param param
	 */
	private void copy2Month(Map<String, Object> param) {
		try {
			
			logger.info("开始生成基层定期月数据：{}", param);
			
			regularProjectBaseInfoMonthDAO
				.deleteByReportDate((String) param.get("reportYearMonth"));
			logger.info("项目基本情况月数据：{}", regularReportDAO.insertRegularProjectBaseMonth(param));
			
			regularCustomerBaseInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("客户基本情况月数据：{}", regularReportDAO.insertRegularCustomerBaseMonth(param));
			
			regularProjectImpelInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目推进情况月数据：{}", regularReportDAO.insertRegularProjectImpelMonth(param));
			
			regularProjectStoreInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目储备情况月数据：{}", regularReportDAO.insertRegularProjectStoreMonth(param));
			
			regularProjectRunInfoMonthDAO.deleteByReportDate((String) param.get("reportYearMonth"));
			logger.info("项目运行情况月数据：{}", regularReportDAO.insertRegularProjectRunMonth(param));
			
			regularProjectIncomeInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目收入情况月数据：{}", regularReportDAO.insertRegularProjectIncomeMonth(param));
			
			regularProjectRiskInfoMonthDAO
				.deleteByReportDate((String) param.get("reportYearMonth"));
			logger.info("项目风险情况月数据：{}", regularReportDAO.insertRegularProjectRiskMonth(param));
			
			regularProjectExtraListInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目本月明细月数据：{}", regularReportDAO.insertRegularProjectExtraListMonth(param));
			
			regularProjectChannelInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目渠道情况月数据：{}", regularReportDAO.insertRegularProjectChannelMonth(param));
			
			regularProjectSubBusiTypeInfoMonthDAO.deleteByReportDate((String) param
				.get("reportYearMonth"));
			logger.info("项目业务细分类情况月数据：{}",
				regularReportDAO.insertRegularProjectSubBusiTypeMonth(param));
			
			logger.info("生成基层定期月数据完成");
			
		} catch (Exception e) {
			logger.error("生成基层定期月数据出错：{}", e);
		}
	}
	
}