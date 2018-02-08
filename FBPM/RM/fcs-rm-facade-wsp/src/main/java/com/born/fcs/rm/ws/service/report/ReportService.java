package com.born.fcs.rm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.rm.ws.info.report.inner.*;
import com.born.fcs.rm.ws.info.report.outer.GuaranteeBusiInfoW5Info;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.report.GuaranteeBaseInfo;
import com.born.fcs.rm.ws.info.report.ReportInfo;
import com.born.fcs.rm.ws.order.report.ReportBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;

/**
 * 
 * 报表生成记录
 * 
 * @author lirz
 * 
 * 2016-8-5 上午11:00:43
 */
@WebService
public interface ReportService {
	
	/**
	 * 
	 * 报表：数据保存
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ReportOrder order);
	
	/**
	 * 按报表id查询
	 * 
	 * @param reportId
	 * @return
	 */
	ReportInfo findById(long reportId);
	
	/**
	 * 按会计期间查询
	 * 
	 * @param reportCode
	 * @param reportYear
	 * @param reportMonth
	 * @return
	 */
	ReportInfo findByAccountPeriod(String reportCode, int reportYear, int reportMonth);
	
	/**
	 * 报表生成：查询列表
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<ReportInfo> queryList(ReportQueryOrder queryOrder);
	
	/**
	 * W1-（中担协）融资性担保机构基本情况
	 * @param queryOrder
	 * @return
	 */
	GuaranteeBaseInfo findGuaranteeBaseInfo(ReportBaseQueryOrder queryOrder);
	
	/**
	 * W2-（中担协）融资性担保机构资产负债情况
	 * @param baseQueryOrder
	 * @return
	 */
	ReportInfo findGuaranteeDebtInfo(ReportBaseQueryOrder baseQueryOrder);
	
	/**
	 * 没什么用，值是不加这个个接口face层找不到这个对象
	 * @param
	 * @return
	 */
	ExpectReleaseInfo finExpectReleaseInfo();
	
	GuaranteeStructureInfo findGuaranteeStructureInfo();
	
	GuaranteeKpiDataInfo findGuaranteeKpiDataInfo();
	
	MainIndexDataInfo findMainIndexDataInfo();
	
	EntrustedeMainIndexInfo findEntrustedeMainIndexInfo();
	
	GuaranteeClassifyInfo findGuaranteeClassifyInfo();
	
	GuaranteeCustomerData findGuaranteeCustomerData();
	
	GuaranteeBusiInfoW5Info findGuaranteeBusiInfoW5Info();
	
	GuaranteeAmountLimitInfo findGuaranteeAmountLimitInfo();
	
	W10GuaranteeDataMonthlyInfo findW10GuaranteeDataMonthlyInfo();
	
	/**
	 * 基层定期报表数据
	 * @param reportDate 指定天跑数 yyyy-MM-dd
	 * @return
	 */
	FcsBaseResult runRegularReport(String reportDate);
}
