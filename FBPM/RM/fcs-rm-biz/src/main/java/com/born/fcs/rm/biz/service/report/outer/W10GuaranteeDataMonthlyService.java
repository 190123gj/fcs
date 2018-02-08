package com.born.fcs.rm.biz.service.report.outer;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.report.inner.W10GuaranteeDataMonthlyInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * W10-（市金融办）担保公司主要数据月报表
 * @author heh
 */
@Service("w10GuaranteeDataMonthlyService")
public class W10GuaranteeDataMonthlyService extends BaseAutowiredDomainService implements
        ReportProcessService {
    @Autowired
    private AccountBalanceService accountBalanceService;
    @Autowired
    private PmReportServiceClient pmReportServiceClient;
    @Autowired
    private SubmissionService submissionService;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder order) {
        QueryBaseBatchResult<W10GuaranteeDataMonthlyInfo> result = new QueryBaseBatchResult<W10GuaranteeDataMonthlyInfo>();
        List<W10GuaranteeDataMonthlyInfo> data = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //9-11
        List<W10GuaranteeDataMonthlyInfo> accountList911 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //20-21
        List<W10GuaranteeDataMonthlyInfo> accountList2021 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //14-19
        List<W10GuaranteeDataMonthlyInfo> submssionList1419 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //22
        List<W10GuaranteeDataMonthlyInfo> submssionList22 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //5-8
        List<W10GuaranteeDataMonthlyInfo> submssionList58 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        //12-13
        List<W10GuaranteeDataMonthlyInfo> submssionList1213 = new ArrayList<W10GuaranteeDataMonthlyInfo>();
        try {
            String hasAllData="IS";
            //查科目余额表
            hasAllData =  accountBalance(order,data,accountList911,accountList2021,hasAllData);
            //查上报数据
            hasAllData = submssinData(order,submssionList1419,submssionList22,hasAllData);
            //担保金额,担保笔数,在保金额，在保户数
            occurData(order,submssionList58);
            blanceData(order,submssionList58);
            releaseData(order,submssionList1213);
            //拼接list
            data.addAll(submssionList58);
            data.addAll(accountList911);
            data.addAll(submssionList1213);
            data.addAll(submssionList1419);
            data.addAll(accountList2021);
            data.addAll(submssionList22);
            result.setSuccess(true);
            result.setPageList(data);
            result.setTotalCount(data.size());
            result.setSortCol(hasAllData);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("W10-（市金融办）担保公司主要数据月报表");
            logger.error("W10-（市金融办）担保公司主要数据月报表：{}", e);
        }
        return result;
    }
    private void occurData(ReportQueryOrder order,List<W10GuaranteeDataMonthlyInfo> submssionList58){
        //担保金额
        String sql = "SELECT ifNULL(SUM(r.occur_amount),0) amount,count(distinct r.project_code) bs  "
                + "FROM ${pmDbTitle}.view_project_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code "
                + "WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')   "
                + "AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' ";
         String thisMonth=sql;
         thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay());
        //当月数
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(thisMonth);
        List<DataListItem> dbReult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        String lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay());
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> dbReult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"当年累计发生担保金额","当年累计发生担保金额",getMoney(dbReult1),"-",getMoney(dbReult2)));
        //涉农
        sql = "SELECT ifNULL(SUM(r.occur_amount),0) amount,count(distinct r.project_code) bs "
                + "FROM ${pmDbTitle}.view_project_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code "
                +"JOIN industry i ON p.industry_code=i.code "
                + "WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') and i.type_big='01' "
                + "AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' ";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay());
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> snResult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay());
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> snResult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","涉农担保",getMoney(snResult1),"-",getMoney(snResult2)));
        //小型微型企业担保
        sql = "SELECT ifNULL(SUM(r.occur_amount),0) amount,count(distinct r.project_code) bs  "
                + "FROM ${pmDbTitle}.view_project_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code "
                + "WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') conditionStr   "
                + "AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' ";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("conditionStr", "AND p.scale = 'SMALL'");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> xxResult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("conditionStr", "AND p.scale = 'SMALL'");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> xxResult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","小型企业担保",getMoney(xxResult1),"-",getMoney(xxResult2)));
        //微型企业担保
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("conditionStr", "AND p.scale = 'TINY'");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> wxResult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("conditionStr", "AND p.scale = 'TINY'");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> wxResult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","微型企业担保",getMoney(wxResult1),"-",getMoney(wxResult2)));
        //互联网融资担保
        sql = "SELECT ifNULL(SUM(r.occur_amount),0) amount,count(distinct r.project_code) bs  "
                + "FROM ${pmDbTitle}.view_project_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code  "
                + "WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') conditionStr   "
                + "AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' ";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("conditionStr", "AND r.capital_channel_type = 'WLJR'");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> hlwResult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("conditionStr", "AND r.capital_channel_type = 'WLJR'");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> hlwResult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","互联网融资担保",getMoney(hlwResult1),"-",getMoney(hlwResult2)));
        //债券等直接融资担保
         sql = "SELECT ifNULL(SUM(r.occur_amount),0) amount,count(distinct r.project_code) bs  "
                + "FROM ${pmDbTitle}.view_project_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code "
                + "WHERE busiTypeStr  "
                + "AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' ";
         thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("busiTypeStr", " p.busi_type like '12%'").replaceAll("conditionStr", "");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> cqReult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("busiTypeStr", " p.busi_type like '12%'").replaceAll("conditionStr", "");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> cqReult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","债券等直接融资担保",getMoney(cqReult1),"-",getMoney(cqReult2)));
        //保本基金
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("busiTypeStr", " p.busi_type='241'").replaceAll("conditionStr", "");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> bbjjReult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("busiTypeStr", " p.busi_type='241'").replaceAll("conditionStr", "");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> bbjjReult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","保本基金担保",getMoney(bbjjReult1),"-",getMoney(bbjjReult2)));
        //非融资担保
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay())
                .replaceAll("busiTypeStr", " p.busi_type like '2%'").replaceAll("conditionStr", "");
        //当月数
        queyOrder.setSql(thisMonth);
        List<DataListItem> frzReult1 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{loanStartTime\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("busiTypeStr", " p.busi_type like '2%'").replaceAll("conditionStr", "");
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> frzReult2 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(5,"其中","非融资担保",getMoney(frzReult1),"-",getMoney(frzReult2)));
        //笔数
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"当年累计发生担保笔数","当年累计发生担保笔数",getbs(dbReult1),"-",getbs(dbReult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","涉农担保",getbs(snResult1),"-",getbs(snResult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","小型企业担保",getbs(xxResult1),"-",getbs(xxResult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","微型企业担保",getbs(wxResult1),"-",getbs(wxResult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","互联网融资担保",getbs(hlwResult1),"-",getbs(hlwResult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","债券等直接融资担保",getbs(cqReult1),"-",getbs(cqReult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","保本基金担保",getbs(bbjjReult1),"-",getbs(bbjjReult2)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(6,"其中","非融资担保",getbs(frzReult1),"-",getbs(frzReult2)));

    }
    private void blanceData(ReportQueryOrder order,List<W10GuaranteeDataMonthlyInfo> submssionList58){
        //担保金额
        String sql = "SELECT COUNT(DISTINCT p.customer_id) bs,  ifnull(SUM(ba.balance),0) amount FROM ${pmDbTitle}.project_data_info p LeftJoinStr"
                + "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}'  GROUP BY project_code) occer "
                + "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance>0 and whereStr ";
        String whereStr="( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')";
        String thisMonth=sql;
        String leftJoinStr="";
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        //当月数
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(thisMonth);
        List<DataListItem> dbReult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        String beginYear=sql;
         beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "")
                        .replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> dbReult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        String lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "")
                        .replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> dbReult3 = pmReportServiceClient.doQuery(queyOrder);

        //涉农
        //当月数
        leftJoinStr="JOIN industry i ON p.industry_code=i.code ";
        whereStr="( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') and i.type_big='01'";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", leftJoinStr)
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> snReult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", leftJoinStr).replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> snReult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", leftJoinStr).replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> snReult3 = pmReportServiceClient.doQuery(queyOrder);
        //小型微型企业担保
        leftJoinStr="";
        whereStr="( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') AND p.scale = 'SMALL'";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> xxResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> xxResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> xxResult3 = pmReportServiceClient.doQuery(queyOrder);

        //微型企业担保
        leftJoinStr="";
        whereStr="( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') AND p.scale = 'TINY'";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> wxResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> wxResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> wxResult3 = pmReportServiceClient.doQuery(queyOrder);

        //互联网融资担保
        String wljrSql= "SELECT COUNT(DISTINCT p.customer_id) bs,  ifnull(SUM(ba.balance),0) amount FROM ${pmDbTitle}.project_data_info p "
                + "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' AND capital_channel_type = 'WLJR' GROUP BY project_code) occer "
                + "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' AND capital_channel_type = 'WLJR' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance>0 and whereStr ";
        thisMonth=wljrSql;
        leftJoinStr="";
        whereStr="( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') ";
        thisMonth=wljrSql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", leftJoinStr)
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> hlwResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=wljrSql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", leftJoinStr).replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> hlwResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=wljrSql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", leftJoinStr).replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> hlwResult3 = pmReportServiceClient.doQuery(queyOrder);

        //债券等直接融资担保
        thisMonth=sql;
        leftJoinStr="";
        whereStr="(  p.busi_type like '12%' )";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> zqResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> zqResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> zqResult3 = pmReportServiceClient.doQuery(queyOrder);
        //保本基金
        thisMonth=sql;
        leftJoinStr="";
        whereStr="(  p.busi_type ='241' ) ";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> bbjjResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> bbjjResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> bbjjResult3 = pmReportServiceClient.doQuery(queyOrder);

        //非融资担保
        thisMonth=sql;
        leftJoinStr="";
        whereStr="(  p.busi_type like '2%' )";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportMonthEndDay())
                .replaceAll("LeftJoinStr", "")
                .replaceAll("whereStr", whereStr);
        queyOrder.setSql(thisMonth);
        List<DataListItem> frzResult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getLastYearEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> frzResult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearMonthEndDay()).replaceAll("LeftJoinStr", "").replaceAll("whereStr", whereStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> frzResult3 = pmReportServiceClient.doQuery(queyOrder);
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"在保余额","在保余额",getMoney(dbReult1),getMoney(dbReult2),getMoney(dbReult3)));
        //在保责任余额-其2
        Money data1=Money.zero();
        Money data2=Money.zero();
        Money data3=Money.zero();
        data1=getMoney2(dbReult1).subtract(getMoney2(snReult1).add(getMoney2(xxResult1)).add(getMoney2(wxResult1)).add(getMoney2(hlwResult1)).add(getMoney2(zqResult1)).add(getMoney2(bbjjResult1)).add(getMoney2(frzResult1)));
        data2=getMoney2(dbReult2).subtract(getMoney2(snReult2).add(getMoney2(xxResult2)).add(getMoney2(wxResult2)).add(getMoney2(hlwResult2)).add(getMoney2(zqResult2)).add(getMoney2(bbjjResult2)).add(getMoney2(frzResult2)));
        data3=getMoney2(dbReult3).subtract(getMoney2(snReult3).add(getMoney2(xxResult3)).add(getMoney2(wxResult3)).add(getMoney2(hlwResult3)).add(getMoney2(zqResult3)).add(getMoney2(bbjjResult3)).add(getMoney2(frzResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中1","在保责任余额",MoneyUtil.formatW(data1),MoneyUtil.formatW(data2),MoneyUtil.formatW(data3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","涉农担保",getMoney(snReult1),getMoney(snReult2),getMoney(snReult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","小型企业担保",getMoney(xxResult1),getMoney(xxResult2),getMoney(xxResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","微型企业担保",getMoney(wxResult1),getMoney(wxResult2),getMoney(wxResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","互联网融资担保",getMoney(hlwResult1),getMoney(hlwResult2),getMoney(hlwResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","债券等直接融资担保",getMoney(zqResult1),getMoney(zqResult2),getMoney(zqResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","保本基金担保",getMoney(bbjjResult1),getMoney(bbjjResult2),getMoney(bbjjResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(7,"其中2","非融资担保",getMoney(frzResult1),getMoney(frzResult2),getMoney(frzResult3)));
        //笔数
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"在保户数","在保户数",getbs(dbReult1),getbs(dbReult2),getbs(dbReult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","涉农担保",getbs(snReult1),getbs(snReult2),getbs(snReult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","小型企业担保",getbs(xxResult1),getbs(xxResult2),getbs(xxResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","微型企业担保",getbs(wxResult1),getbs(wxResult2),getbs(wxResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","互联网融资担保",getbs(hlwResult1),getbs(hlwResult2),getbs(hlwResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","债券等直接融资担保",getbs(zqResult1),getbs(zqResult2),getbs(zqResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","保本基金担保",getbs(bbjjResult1),getbs(bbjjResult2),getbs(bbjjResult3)));
        submssionList58.add(new W10GuaranteeDataMonthlyInfo(8,"其中","非融资担保",getbs(frzResult1),getbs(frzResult2),getbs(frzResult3)));

    }
    private void releaseData(ReportQueryOrder order,List<W10GuaranteeDataMonthlyInfo> submssionList1213){
        //解保金额
        String sql="SELECT IFNULL(SUM(r.repay_amount), 0) amount  " +
                "  FROM ${pmDbTitle}.view_project_repay_detail r JOIN ${pmDbTitle}.project p ON p.project_code = r.project_code  " +
                "  WHERE busiTypeStr  " +
                "  AND r.repay_type in ( '解保','诉保解保','代偿' ) AND r.repay_confirm_time >= '${repayTimeStart}' AND r.repay_confirm_time <= '${repayTimeEnd}'  ";
        String thisMonth=sql;
        String busiTypeStr="(p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')";
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{repayTimeEnd\\}",
                order.getReportMonthEndDay())
                .replaceAll("busiTypeStr", busiTypeStr);
        //当月数
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(thisMonth);
        List<DataListItem> dbReult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
        String beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{releaseEndTime\\}",order.getReportLastYearEndDay()).replaceAll("busiTypeStr", busiTypeStr);
        queyOrder.setSql(beginYear);
        List<DataListItem> dbReult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
        String lastYearMonth=sql;
        lastYearMonth = lastYearMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{repayTimeEnd\\}",order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr);
        queyOrder.setSql(lastYearMonth);
        List<DataListItem> dbReult3 = pmReportServiceClient.doQuery(queyOrder);
        submssionList1213.add(new W10GuaranteeDataMonthlyInfo(12,"当年解除担保金额","当年解除担保金额",getMoney(dbReult1),"--",getMoney(dbReult3)));
        busiTypeStr="(p.busi_type like '1%')";
        thisMonth=sql;
        thisMonth = thisMonth.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{repayTimeEnd\\}",
                order.getReportMonthEndDay())
                .replaceAll("busiTypeStr", busiTypeStr);
        //当月数
        queyOrder.setSql(thisMonth);
         dbReult1 = pmReportServiceClient.doQuery(queyOrder);
        //年初数
         beginYear=sql;
        beginYear = beginYear.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{repayTimeEnd\\}",order.getReportLastYearEndDay()).replaceAll("busiTypeStr", busiTypeStr);
        queyOrder.setSql(beginYear);
        dbReult2 = pmReportServiceClient.doQuery(queyOrder);
        //上年同期
         lastYearMonth=sql;
        lastYearMonth = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{repayTimeStart\\}",
                order.getReportLastYearStartDay()).replaceAll("\\$\\{repayTimeEnd\\}",order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr);
        queyOrder.setSql(lastYearMonth);
        dbReult3 = pmReportServiceClient.doQuery(queyOrder);
        submssionList1213.add(new W10GuaranteeDataMonthlyInfo(12,"其中","融资性担保当年解除金额",getMoney(dbReult1),"--",getMoney(dbReult3)));
        submssionList1213.add(new W10GuaranteeDataMonthlyInfo(13,"累计解除担保金额","累计解除担保金额","","",""));
    }
    private String getMoney(List<DataListItem> dataResult){
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                return MoneyUtil.formatW(toMoney(itm.getMap().get("amount")));
            }
        }
        return Money.zero().toStandardString();
    }
    private Money getMoney2(List<DataListItem> dataResult){
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                return toMoney(itm.getMap().get("amount"));
            }
        }
        return Money.zero();
    }
    private String getbs(List<DataListItem> dataResult){
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                return itm.getMap().get("bs")+"";
            }
        }
        return "0";
    }
    private String accountBalance(ReportQueryOrder order,List<W10GuaranteeDataMonthlyInfo> data,List<W10GuaranteeDataMonthlyInfo> accountList911,List<W10GuaranteeDataMonthlyInfo> accountList2021,String hasAllData){
        AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
        abQueryOrder.setReportYear(order.getReportYear());
        abQueryOrder.setReportMonth(order.getReportMonth());
        List<AccountBalanceDataInfo> currMonth = accountBalanceService.queryDatas(abQueryOrder);
        Money currAmount=Money.zero();
        Money yearInitAmount=Money.zero();
        Money samePeriodAmount=Money.zero();
        Money currCapital = Money.zero();
        Money yearInitCapital = Money.zero();
        Money samePeriodCapital = Money.zero();
        String currJzCapital="0.00";
        String yearInitJzCapital="0.00";
        String samePeriodJzCapital="0.00";
        String curr6103="0.00";
        String samePeriod6103="0.00";
        String currJLR="0.00";
        String samePeriodJLR="0.00";
        String curr2221="0.00";
        String samePeriod2221="0.00";
        String curr26024102="0.00";
        String yearInit26024102="0.00";
        String samePeriod26024102="0.00";
        String curr6502="0.00";
        String yearInit6502="0.00";
        String samePeriod6502="0.00";
        String curr6501="0.00";
        String yearInit6501="0.00";
        String samePeriod6501="0.00";
        String curr4102="0.00";
        String yearInit4102="0.00";
        String samePeriod4102="0.00";
        String curr610302="0.00";
        String yearInit610302="0.00";
        String samePeriod610302="0.00";
        String currDWTZ="0.00";
        String yearInitDWTZ="0.00";
        String samePeriodDWTZ="0.00";
        if (ListUtil.isNotEmpty(currMonth)) {
            AccountBalanceHelper helper = new AccountBalanceHelper(currMonth,"1","2");
            currCapital = helper.caculateCapital();
            currJzCapital=MoneyUtil.formatW(currCapital.subtract(helper.caculateDebt()));
             currAmount = helper.caculateEndingCredit("4001");
            //6031当年累计发生贷方
            curr6103= MoneyUtil.formatW(helper.caculateYearCredit("6031"));
            //净利润
            currJLR= MoneyUtil.formatW(getJlr(helper));
            //2221借方发生累计
            curr2221 = MoneyUtil.formatW(helper.caculateCurrentDebit("2221"));
            //20 2602贷方+4102贷方
            curr26024102 = MoneyUtil.formatW(helper.caculateYearCredit("2602","4102"));
            //20.1 6502借方发生累计
            curr6502=MoneyUtil.formatW(helper.caculateYearDebit("6502"));
            //20.2 6501借方发生累计
            curr6501=MoneyUtil.formatW(helper.caculateYearDebit("6501"));
            //20.3 4102
            curr4102=MoneyUtil.formatW(helper.caculateYearDebit("4102"));
            //6103.02借方发生累计
            curr610302 = MoneyUtil.formatW(helper.caculateYearDebit("6103.02"));
            //21 对外投资
            currDWTZ=MoneyUtil.formatW(helper.caculateYearDebit("6103.02").add(helper.caculateYearDebit("1501")));
        }else{hasAllData="NO";}
        //年初数
        abQueryOrder.setReportYear(order.getReportYear()-1);
        abQueryOrder.setReportMonth(12);
        List<AccountBalanceDataInfo> yearInit = accountBalanceService.queryDatas(abQueryOrder);
        if (ListUtil.isNotEmpty(yearInit)) {
            AccountBalanceHelper helper = new AccountBalanceHelper(yearInit,"1","2");
            yearInitCapital = helper.caculateCapital();
            yearInitJzCapital=MoneyUtil.formatW(yearInitCapital.subtract(helper.caculateDebt()));
             yearInitAmount = helper.caculateEndingCredit("4001");
            //20 2602贷方+4102贷方
            yearInit26024102 = MoneyUtil.formatW(helper.caculateYearCredit("2602","4102"));
            //20.1 6502借方发生累计
            yearInit6502=MoneyUtil.formatW(helper.caculateYearDebit("6502"));
            //20.2 6501借方发生累计
            yearInit6501=MoneyUtil.formatW(helper.caculateYearDebit("6501"));
            //20.3 4102
            yearInit4102=MoneyUtil.formatW(helper.caculateYearDebit("4102"));
            //6103.02借方发生累计
            yearInit610302 = MoneyUtil.formatW(helper.caculateYearDebit("6103.02"));
            //21 对外投资
            yearInitDWTZ=MoneyUtil.formatW(helper.caculateYearDebit("6103.02").add(helper.caculateYearDebit("1501")));
        }else{hasAllData="NO";}
        //去年同期
        abQueryOrder.setReportYear(order.getReportYear()-1);
        abQueryOrder.setReportMonth(order.getReportMonth());
        List<AccountBalanceDataInfo> samePeriod = accountBalanceService.queryDatas(abQueryOrder);
        if (ListUtil.isNotEmpty(samePeriod)) {
            AccountBalanceHelper helper = new AccountBalanceHelper(samePeriod,"1","2");
            samePeriodCapital = helper.caculateCapital();
            samePeriodJzCapital=MoneyUtil.formatW(samePeriodCapital.subtract(helper.caculateDebt()));
             samePeriodAmount = helper.caculateEndingCredit("4001");
            samePeriod6103= MoneyUtil.formatW(helper.caculateYearCredit("6103"));
            //净利润
            samePeriodJLR= MoneyUtil.formatW(getJlr(helper));
            //2221借方发生累计
            samePeriod2221 = MoneyUtil.formatW(helper.caculateCurrentCredit("2221"));
            //20 2602贷方+4102贷方
            samePeriod26024102 = MoneyUtil.formatW(helper.caculateYearCredit("2602","4102"));
            //20.1 6502借方发生累计
            samePeriod6502=MoneyUtil.formatW(helper.caculateYearDebit("6502"));
            //20.2 6501借方发生累计
            samePeriod6501=MoneyUtil.formatW(helper.caculateYearDebit("6501"));
            //20.3 4102
            samePeriod4102=MoneyUtil.formatW(helper.caculateYearDebit("4102"));
            //6103.02借方发生累计
            samePeriod610302 = MoneyUtil.formatW(helper.caculateCurrentDebit("6103.02"));
            //21 对外投资
            samePeriodDWTZ=MoneyUtil.formatW(helper.caculateCurrentDebit("6103.02").add(helper.caculateYearDebit("1501")));
        }else{hasAllData="NO";}
        String currAmountStr=MoneyUtil.formatW(currAmount);
        String yearInitAmountStr=MoneyUtil.formatW(yearInitAmount);
        String samePeriodAmountStr=MoneyUtil.formatW(samePeriodAmount);
        String currCapitalStr=MoneyUtil.formatW(currCapital);
        String yearInitCapitalStr=MoneyUtil.formatW(yearInitCapital);
        String samePeriodCapitalStr=MoneyUtil.formatW(samePeriodCapital);
        data.add(new W10GuaranteeDataMonthlyInfo(1,"注册资本金","注册资本金",currAmountStr,yearInitAmountStr,samePeriodAmountStr));
        data.add(new W10GuaranteeDataMonthlyInfo(2,"实收资本","实收资本",currAmountStr,yearInitAmountStr,samePeriodAmountStr));
        data.add(new W10GuaranteeDataMonthlyInfo(2,"其中","国有资本",currAmountStr,yearInitAmountStr,samePeriodAmountStr));
        data.add(new W10GuaranteeDataMonthlyInfo(2,"其中","民营资本","0.00","0.00","0.00"));
        data.add(new W10GuaranteeDataMonthlyInfo(2,"其中","外资","0.00","0.00","0.00"));
        //资产总额
        data.add(new W10GuaranteeDataMonthlyInfo(3,"资产总额","资产总额",currCapitalStr,yearInitCapitalStr,samePeriodCapitalStr));
        //净资产
        data.add(new W10GuaranteeDataMonthlyInfo(4,"净资产","净资产",currJzCapital,yearInitJzCapital,samePeriodJzCapital));
        //9-11
        accountList911.add(new W10GuaranteeDataMonthlyInfo(9,"担保业务收入","担保业务收入",curr6103,"--",samePeriod6103));
        accountList911.add(new W10GuaranteeDataMonthlyInfo(10,"净利润","净利润",currJLR,"--",samePeriodJLR));
        accountList911.add(new W10GuaranteeDataMonthlyInfo(11,"税金","税金",curr2221,"--",samePeriod2221));
        //20-21
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(20,"担保准备金","担保准备金",curr26024102,yearInit26024102,samePeriod26024102));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(20,"其中","担保赔偿准备金",curr6502,yearInit6502,samePeriod6502));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(20,"其中","未到期责任准备金",curr6501,yearInit6501,samePeriod6501));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(20,"其中","一般风险准备金",curr4102,yearInit4102,samePeriod4102));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(21,"对外投资","对外投资",currDWTZ,yearInitDWTZ,samePeriodDWTZ));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(21,"其中","股权投资","0.00","0.00","0.00"));
        accountList2021.add(new W10GuaranteeDataMonthlyInfo(21,"委托贷款","委托贷款",curr610302,yearInit610302,samePeriod610302));
        return hasAllData;
    }
    private String submssinData(ReportQueryOrder order,List<W10GuaranteeDataMonthlyInfo> submssionList1419,List<W10GuaranteeDataMonthlyInfo> submssionList22,String hasAllData){
        //来自数据报送：融资性担保机构基本情况
        SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.DBGSZYSJYBB.code());
        submissionQueryOrder.setReportYear(order.getReportYear());
        submissionQueryOrder.setReportMonth(order.getReportMonth());
        List<String> statusList= Lists.newArrayList();
        statusList.add(ReportStatusEnum.SUBMITTED.code());
        statusList.add(ReportStatusEnum.IN_USE.code());
        submissionQueryOrder.setStatusList(statusList);
        submissionQueryOrder.setPageNumber(1L);
        submissionQueryOrder.setPageSize(1L);
        QueryBaseBatchResult<SubmissionInfo> batches = submissionService
                .query(submissionQueryOrder);
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submissionInfo = batches.getPageList().get(0);
            submissionInfo = submissionService.findById(submissionInfo.getSubmissionId());
            if (null != submissionInfo && ListUtil.isNotEmpty(submissionInfo.getData())) {
                List<SubmissionDataInfo> info=submissionInfo.getData();
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(14,"当年累计代偿金额","当年累计代偿金额",MoneyUtil.format(toMoneySubmissionData(info.get(0).getData2())),"--",MoneyUtil.format(toMoneySubmissionData(info.get(0).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(14,"其中","融资性担保当年代偿金额",MoneyUtil.format(toMoneySubmissionData(info.get(1).getData2())),"--",MoneyUtil.format(toMoneySubmissionData(info.get(1).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(15,"累计担保代偿额","累计担保代偿额",MoneyUtil.format(toMoneySubmissionData(info.get(2).getData2())),MoneyUtil.format(toMoneySubmissionData(info.get(2).getData3())),MoneyUtil.format(toMoneySubmissionData(info.get(2).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(16,"当年累计担保损失金额","当年累计担保损失金额",MoneyUtil.format(toMoneySubmissionData(info.get(3).getData2())),"--",MoneyUtil.format(toMoneySubmissionData(info.get(3).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(16,"其中","融资性担保当年损失金额",MoneyUtil.format(toMoneySubmissionData(info.get(4).getData2())),"--",MoneyUtil.format(toMoneySubmissionData(info.get(4).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(17,"累计担保损失金额","累计担保损失金额",MoneyUtil.format(toMoneySubmissionData(info.get(5).getData2())),MoneyUtil.format(toMoneySubmissionData(info.get(5).getData3())),MoneyUtil.format(toMoneySubmissionData(info.get(5).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(18,"不良资产余额","不良资产余额",MoneyUtil.format(toMoneySubmissionData(info.get(6).getData2())),MoneyUtil.format(toMoneySubmissionData(info.get(6).getData3())),MoneyUtil.format(toMoneySubmissionData(info.get(6).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(19,"代偿余额","代偿余额",MoneyUtil.format(toMoneySubmissionData(info.get(7).getData2())),MoneyUtil.format(toMoneySubmissionData(info.get(7).getData3())),MoneyUtil.format(toMoneySubmissionData(info.get(7).getData4()))));
                submssionList1419.add(new W10GuaranteeDataMonthlyInfo(19,"其中","融资担保代偿余额",MoneyUtil.format(toMoneySubmissionData(info.get(8).getData2())),MoneyUtil.format(toMoneySubmissionData(info.get(8).getData3())),MoneyUtil.format(toMoneySubmissionData(info.get(8).getData4()))));
            }
        }else{hasAllData="NO";}
        //当月数
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGQKB.code());
        batches = submissionService
                .query(submissionQueryOrder);
        String str[][]=new String[4][3];
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submissionInfo = batches.getPageList().get(0);
            submissionInfo = submissionService.findById(submissionInfo.getSubmissionId());
            if (null != submissionInfo && ListUtil.isNotEmpty(submissionInfo.getData())) {
                List<SubmissionDataInfo> infos=submissionInfo.getData();
                int i=0;
                for(SubmissionDataInfo info:infos){
                    if(i==0){
                        str[i][0] = info.getData2();
                    }else {
                        str[i][0] = info.getData3();
                    }
                    i++;
                }
            }
        }else{hasAllData="NO";}
        //年初
        submissionQueryOrder.setReportYear(order.getReportYear()-1);
        submissionQueryOrder.setReportMonth(12);
        batches = submissionService
                .query(submissionQueryOrder);
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submissionInfo = batches.getPageList().get(0);
            submissionInfo = submissionService.findById(submissionInfo.getSubmissionId());
            if (null != submissionInfo && ListUtil.isNotEmpty(submissionInfo.getData())) {
                List<SubmissionDataInfo> infos=submissionInfo.getData();
                int i=0;
                for(SubmissionDataInfo info:infos){
                    if(i==0){
                        str[i][1] = info.getData2();
                    }else {
                        str[i][1] = info.getData3();
                    }
                    i++;
                }
            }
        }else{hasAllData="NO";}
        //去年同期
        submissionQueryOrder.setReportYear(order.getReportYear()-1);
        submissionQueryOrder.setReportMonth(order.getReportMonth());
        batches = submissionService
                .query(submissionQueryOrder);
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submissionInfo = batches.getPageList().get(0);
            submissionInfo = submissionService.findById(submissionInfo.getSubmissionId());
            if (null != submissionInfo && ListUtil.isNotEmpty(submissionInfo.getData())) {
                List<SubmissionDataInfo> infos=submissionInfo.getData();
                int i=0;
                for(SubmissionDataInfo info:infos){
                    if(i==0){
                        str[i][2] = info.getData2();
                    }else {
                        str[i][2] = info.getData3();
                    }
                    i++;
                }
            }
        }else{hasAllData="NO";}
        submssionList22.add(new W10GuaranteeDataMonthlyInfo(22,"在职人数","在职人数",str[0][0],str[0][1],str[0][2]));
        submssionList22.add(new W10GuaranteeDataMonthlyInfo(22,"其中","研究生及以上",str[1][0],str[1][1],str[1][2]));
        submssionList22.add(new W10GuaranteeDataMonthlyInfo(22,"其中","本科",str[2][0],str[2][1],str[2][2]));
        submssionList22.add(new W10GuaranteeDataMonthlyInfo(22,"其中","大专及以下",str[3][0],str[3][1],str[3][2]));
        return hasAllData;
    }
    private Money getJlr(AccountBalanceHelper helper) {
        //1. 1. 担保业务收入		6031当年累计发生贷方
        Money m1 = helper.caculateYearCredit("6031");

        //3.2. 担保业务成本	6403借方发生累计
        Money m2 = helper.caculateYearDebit("6403");
        //4.其中：融资性担保赔偿支出	6511借方发生累计
        //5.融资性分担保费支出	6542借方发生累计
        //6.营业税金及附加		6403借方发生累计
        //7.3.提取未到期责任准备	6501借方发生累计
        Money m3 = helper.caculateYearDebit("6501");
        //8.4.担保赔偿准备金		6502借方发生累计
        Money m4 = helper.caculateYearDebit("6502");
        //9.5. 担保业务利润		本表1减2减3减4
        Money m5 = m1.subtract(m2).subtract(m3).subtract(m4);
        //10.6. 利息净收入（净支出则前加“－”号填列）	6103贷方累计减6421借方累计
        Money m6 = helper.caculateYearCredit("6103").subtract(helper.caculateYearDebit("6421"));
        //11.7. 其他业务利润	6051贷方累计
        Money m7 = helper.caculateYearCredit("6051");
        //12.8. 业务及管理费	6601借方累计
        Money m8 = helper.caculateYearDebit("6601");
        //13.9. 投资收益（投资损失则前加“－”号填列）	6111贷方累计
        Money m9 = helper.caculateYearCredit("6111");
        //14.10. 资产减值损失（转回的金额则前加“－”号填列）6701借方累计
        Money m10 = helper.caculateYearDebit("6701");
        //15.11. 营业利润	5加6加7减8加9减10
        Money m11 = m5.add(m6).add(m7).subtract(m8).add(m9).subtract(m10);
        //16.12. 营业外净收入（净亏损则前加“－”号填列）	6301贷方累计-6711借方累计
        Money m12 = helper.caculateYearCredit("6301").subtract(helper.caculateYearDebit("6711"));
        //17.13. 所得税	6801借方累计
        Money m13 = helper.caculateYearDebit("6801");
        //18.14. 净利润（净亏损则前加“－”号填列）	11加12减13
        Money m14 = m11.add(m12).subtract(m13);
        return m14;
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

    //转化Money
    private Money toMoneySubmissionData(Object fen) {
        try {
            String s = String.valueOf(fen);
            return Money.amout(s);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Money.zero();
    }

}
