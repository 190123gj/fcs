package com.born.fcs.rm.biz.service.report.outer;

import java.util.*;

import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 外部报表<br />
 * 中担协季报<br />
 * W6-（中担协）融资性担保机构风险指标
 *
 * @author heh
 *
 * 2016年8月23日  16:45:42
 */
@Service("guaranteeRiskIndexService")
public class GuaranteeRiskIndexServiceImpl extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    ToReportServiceClient toReportServiceClient;

    @Autowired
    private PmReportServiceClient pmReportServiceClient;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
        String hasAllData="IS";
        QueryBaseBatchResult<String> batchResult = new QueryBaseBatchResult<>();
        List<String> lists = new ArrayList<>();
        //科目余额表的数据
        AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
        abQueryOrder.setReportYear(queryOrder.getReportYear());
        abQueryOrder.setReportMonth(queryOrder.getReportMonth());
        List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
        String t1="";
        if (ListUtil.isNotEmpty(list)) {
            AccountBalanceHelper helper = new AccountBalanceHelper(list, "1", "2");
            //1.1 流动性资产  总资产（1开头的借方-贷方，加和）-1811借方余额 -1604借方余额-1701借方+1702贷方-（1601借方-1602贷方）-1511借方
            String m1= MoneyUtil.getMoneyByw2(helper.caculateCapital().subtract(helper.caculateEndingDebit("1811")).subtract(helper.caculateEndingDebit("1604"))
                    .subtract(helper.caculateEndingDebit("1701")).add(helper.caculateEndingCredit("1702")).subtract(helper.caculateEndingDebit("1601"))
                    .add(helper.caculateEndingCredit("1602")).subtract(helper.caculateEndingDebit("1511")));
            lists.add(m1);
//            AccountBalanceHelper helper2 = new AccountBalanceHelper(list,"2");
            //1.2 流动性负债 总负债（2开头的所有贷方-借方）-2401贷方余额
            String m2= MoneyUtil.getMoneyByw2(helper.caculateDebt().subtract(helper.caculateEndingCredit("2401")));
            lists.add(m2);
            //1.3 流动性比率
            lists.add((CommonUtil.numberFormat(Double.parseDouble(m1)/Double.parseDouble(m2),2)));
            //2.2 净资产 W2  序号19 (1开头的借方-贷方，加和) - (1001+1002+1012 借方余额)
            t1= MoneyUtil.getMoneyByw2(helper.caculateCapital().subtract(helper.caculateDebt()));
            if(StringUtil.isEmpty(t1)){
                t1="0.00";
            }
            lists.add(t1);
            //6.1 担保准备金 2602贷方+4102贷方
            lists.add(MoneyUtil.getMoneyByw2(helper.caculateEndingCredit("2602","4102")));
            Money capital = helper.caculateCapital();
        }else{
            lists.add("");
            lists.add("");
            lists.add("");
            lists.add("");
            lists.add("");
            hasAllData="NO";
        }
          //2.1 融资性担保责任余额计算在保余额
//        String t2=toReportServiceClient.getReleasingAmount(queryOrder.getReportYear(),queryOrder.getReportMonth());
        String t2=balanceAmount(queryOrder.getReportYear(),queryOrder.getReportMonth());
        if(StringUtil.isEmpty(t2)){
            t2="0.00";
        }
        if(StringUtil.isEmpty(t1)){
            t1="0.00";
        }
        t2=MoneyUtil.getMoneyByw2(new Money(t2).divide(100));
        lists.add(t2);
         //2.3 融资性担保放大倍数 (2.1 / 2.2)
        if(!t2.equals("0.00")){
            lists.add(CommonUtil.numberFormat(Double.parseDouble(t2)/Double.parseDouble(t1),2));
        }else{
            lists.add("0.00");
        }
        //来自数据报送：融资性担保机构基本情况
        SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGFXZB.code());
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
        
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submission = batches.getPageList().get(0);
            submission = submissionService.findById(submission.getSubmissionId());
            if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
                List<SubmissionDataInfo> data = submission.getData();
                if (data.size() >= 1) {
                    //3.1 本年度累计担保代偿额
                    lists.add(data.get(6).getData4());
                    //其中：本年度累计融资性担保代偿额
                    lists.add(data.get(7).getData4());
                    //3.2 本年度累计解除的担保额
                    lists.add(data.get(8).getData4());
                    //其中：本年度累计解除的融资性担保额
                    lists.add(data.get(9).getData4());
                    //3.3 担保代偿率
                    lists.add(data.get(10).getData4());
                    //3.4 融资性担保代偿率
                    lists.add(data.get(11).getData4());
                    //4.1 本年度累计代偿回收额
                    lists.add(data.get(12).getData4());
                    //其中：本年度累计融资性担保代偿回收额
                    lists.add(data.get(13).getData4());
                    //4.2 年初担保代偿余额
                    lists.add(data.get(14).getData3());
                    //其中：年初融资性担保代偿余额
                    lists.add(data.get(15).getData3());
                    //4.3 代偿回收率
                    lists.add(data.get(16).getData4());
                    //4.4 融资性担保代偿回收率
                    lists.add(data.get(17).getData4());
                    //5.1 本年度累计担保损失额
                    lists.add(data.get(18).getData4());
                    //其中：本年度累计融资性担保损失额
                    lists.add(data.get(19).getData4());
                    //5.2 担保损失率
                    lists.add(data.get(20).getData4());
                    //5.3 融资性担保损失率
                    lists.add(data.get(21).getData4());
                    //6.2 担保代偿余额
                    lists.add(data.get(23).getData5());
                    //6.3 拨备覆盖率
                    lists.add(data.get(24).getData5());
                }
            }
        }else{
            hasAllData="NO";
        }
        //补全
        if(lists.size()<25){
            for(int i=lists.size();i<25;i++){
                lists.add("");
            }
        }
        batchResult.setPageList(lists);
        batchResult.setSuccess(true);
        batchResult.setSortCol(hasAllData);
        return batchResult;
    }

    /**
     * 在保余额
     */
    private String balanceAmount(int year,int month) {
        String sql = "SELECT  SUM(ba.balance) amount FROM ${pmDbTitle}.project_data_info p "
                + "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
                + "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  p.busi_type like '1%' ";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", getReportMonthEndDay(year,month)));
        List<DataListItem>  dataResult = pmReportServiceClient.doQuery(queyOrder);
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                return String.valueOf(itm.getMap().get("amount"));
            }
        }

       return "0.00";
    }

    //转化Money
    private Money toMoney(Object fen) {
        try {
            String s = String.valueOf(fen);
            return Money.amout(s).divide(100);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Money.zero();
    }

    /** 查询的是否本年第一天 */
    public boolean isThisYearFirstDay(int reportYear) {
        return com.born.fcs.pm.util.StringUtil.equals(getReportYearStartDay(reportYear), DateUtil.dtSimpleFormat(new Date()));
    }

    /** 查询的是否本月第一天 */
    public boolean isThisMonthFirstDay(int reportYear,int reportMonth) {
        return com.born.fcs.pm.util.StringUtil.equals(getReportMonthStartDay(reportYear,reportMonth), DateUtil.dtSimpleFormat(new Date()));
    }
    /**
     * 查询当年第一天
     * @return
     */
    public String getReportYearStartDay(int reportYear) {
        return reportYear + "-01-01";
    }

    /**
     * 查询当月第一天
     * @return
     */
    public String getReportMonthStartDay(int reportYear,int reportMonth) {
        return getReportYearMonth(reportYear,reportMonth) + "-01";
    }

    /**
     * 查询账期
     * @return
     */
    public String getReportYearMonth(int reportYear,int reportMonth) {
        return reportYear + (reportMonth < 10 ? "-0" : "-") + reportMonth;
    }

    /**
     * 查询当月结束时间（不超过当月最大时间）
     * @return
     */
    public String getReportMonthEndDay(int reportYear,int reportMonth) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int moth = now.get(Calendar.MONTH) + 1;
        if (year == reportYear && moth == reportMonth) {
            //当月
        } else {
            now.set(Calendar.YEAR, reportYear);
            now.set(Calendar.MONTH, reportMonth - 1);
            now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return DateUtil.dtSimpleFormat(now.getTime());
    }

    /** 查询的是否本月 */
    public boolean isThisMonth(int reportYear,int reportMonth) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int moth = now.get(Calendar.MONTH) + 1;
        return year == reportYear && moth == reportMonth;
    }

}
