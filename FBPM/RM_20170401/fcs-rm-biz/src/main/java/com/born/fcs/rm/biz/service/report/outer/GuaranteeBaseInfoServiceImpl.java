package com.born.fcs.rm.biz.service.report.outer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.report.outer.GuaranteeBaseInfoService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 外部报表<br />
 * 中担协季报<br />
 * W1-（中担协）融资性担保机构基本情况
 * 
 * @author lirz
 *
 * 2016-8-20 下午3:35:48
 */
@Service("guaranteeBaseInfoService")
public class GuaranteeBaseInfoServiceImpl extends BaseAutowiredDomainService
																			implements
																			GuaranteeBaseInfoService,
																			ReportProcessService {
	@Autowired
	private SubmissionService submissionService;
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
//		logger.info("保存：" + order.getReportCode());
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		String hasAllData="IS";
		//来自对应会计期间的科目余额表
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(queryOrder.getReportYear());
		abQueryOrder.setReportMonth(queryOrder.getReportMonth());
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		Money m = new Money(0L);
		if (ListUtil.isNotEmpty(list)) {
			AccountBalanceHelper helper = new AccountBalanceHelper(list); 
			m = helper.caculateEndingCredit("4001");
		}else{
			hasAllData="NO";
		}
		
		QueryBaseBatchResult<String> batchResult = new QueryBaseBatchResult<>();
		List<String> lists = new ArrayList<>();
		//注册资本
		lists.add(MoneyUtil.getMoneyByw2(m));
		//实收货币资本
		lists.add(MoneyUtil.getMoneyByw2(m));
		//---------------------固定值---------------------
		//公司性质
		lists.add("国有");
		//分支机构数量
		lists.add("1");
		
		//来自数据报送：融资性担保机构基本情况
		SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
		submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGQKB.code());
		submissionQueryOrder.setReportYear(queryOrder.getReportYear());
		submissionQueryOrder.setReportMonth(queryOrder.getReportMonth());
		List<String> statusList= Lists.newArrayList();
		statusList.add(ReportStatusEnum.SUBMITTED.code());
		statusList.add(ReportStatusEnum.IN_USE.code());
		submissionQueryOrder.setStatusList(statusList);
		submissionQueryOrder.setPageNumber(1L);
		submissionQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<SubmissionInfo> batches = submissionService
			.query(submissionQueryOrder);
		boolean hasData = false;
		if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
			SubmissionInfo submission = batches.getPageList().get(0);
			submission = submissionService.findById(submission.getSubmissionId());
			if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
				List<SubmissionDataInfo> data = submission.getData();
				if (data.size() >= 1) {
					//职工总数
					lists.add(data.get(0).getData2());
					//专业技术人员
					lists.add(data.get(0).getData4());
					//研究生
					lists.add(data.get(1).getData3());
					//本科生
					lists.add(data.get(2).getData3());
					//大专及以下
					lists.add(data.get(3).getData3());
					hasData = true;
				}
			}
		}
		
		if (!hasData) {
			lists.add("");
			lists.add("");
			lists.add("");
			lists.add("");
			lists.add("");
			hasAllData="NO";
		}
		
		batchResult.setPageList(lists);
		batchResult.setSuccess(true);
		batchResult.setSortCol(hasAllData);
		return batchResult;
	}
	
}
