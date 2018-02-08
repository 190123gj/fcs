package com.born.fcs.rm.biz.service.report.inner;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * 内部报表<br />
 * 中担协季报<br />
 * W4-（中担协）融资性担保机构业务情况（表一）
 *
 * @author heh
 *
 * 2016-12-27 15:14:50
 */
@Service("w4GuaranteeBusiInfoService")
public class W4GuaranteeBusiInfoService extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    private PmReportServiceClient pmReportServiceClient;
    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    private Money totalAmount=Money.zero();

    private Money totalActulAmount=Money.zero();

    private Money totalRepayAmount=Money.zero();

    private Money totalcompAmount=Money.zero();

    private Money totalCurrCompAmount=Money.zero();

    private Money totalRecoveryAmount=Money.zero();



    @Override
    public Object findByAccountPeriod(ReportQueryOrder order) {
        QueryBaseBatchResult<ExpectReleaseInfo> result = new QueryBaseBatchResult<ExpectReleaseInfo>();
        List<ExpectReleaseInfo> data = new ArrayList<ExpectReleaseInfo>();
        try {
            totalAmount=Money.zero();
            totalActulAmount=Money.zero();
            totalRepayAmount=Money.zero();
            totalcompAmount=Money.zero();
            totalCurrCompAmount=Money.zero();
            totalRecoveryAmount=Money.zero();
            //在保余额
            balanceAmount(order,data);
            //担保金额
            guaranteeAmount(order, data);
            //担保户数
            guaranteeHouseHolds(order, data);
            //代偿金额
            compPrincipalAmount(order, data);
            //格式化info
            result.setSuccess(true);
            result.setPageList(formatInfo(data));
            result.setTotalCount(data.size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("W4-（中担协）融资性担保机构业务情况（表一）出错");
            logger.error("查询W4-（中担协）融资性担保机构业务情况（表一）出错：{}", e);
        }

        return result;
    }
    /**
     * 格式化
     * @param data
     */
    private List<ExpectReleaseInfo> formatInfo(List<ExpectReleaseInfo> data) {
        List<ExpectReleaseInfo> result = new ArrayList<>();
        String[] types=new String[]{"融资性担保业务","非融资性担保业务","再担保","担保业务合计"};
        for(String type:types){
        for(ExpectReleaseInfo info:data){
             if(type.equals(info.getData1())) {
                 if ("担保户数".equals(info.getData3())) {
                     info.setData7(Long.parseLong(info.getData4())+Long.parseLong(info.getData5())-Long.parseLong(info.getData6())+"");
                 } else {
                     info.setData7(MoneyUtil.getMoney(info.getData4()).add(MoneyUtil.getMoney(info.getData5())).subtract(MoneyUtil.getMoney(info.getData6()))+"");
                 }
                 result.add(info);
             }
            }
            ExpectReleaseInfo info = new ExpectReleaseInfo();
            info.setData1(type);
            if("担保业务合计".equals(type)){
                info.setData3("损失金额合计");
            }else {
                info.setData3("损失金额");
            }
            result.add(info);
        }
           return result;
    }
    /**
     * 担保金额
     * @param order
     * @param data
     */
    private void guaranteeAmount(ReportQueryOrder order,List<ExpectReleaseInfo> data) {
        String accurSql=" SELECT IFNULL(SUM(r.actual_amount),0) actual_amount  " +
                " FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code  " +
                " WHERE r.apply_type IN ('BOTH', 'LOAN')  " +
                " AND r.raw_add_time >= '${loanStartTime}' AND raw_add_time <= '${loanEndTime}'  " +
                " AND busiTypeStr  groupByStr ORDER BY p.busi_type ";
        String repaySql="SELECT IFNULL(SUM(r.repay_amount), 0) repay_amount  " +
                "  FROM ${pmDbTitle}.view_project_repay_detail r JOIN ${pmDbTitle}.project p ON p.project_code = r.project_code  " +
                "  WHERE busiTypeStr  " +
                "  AND r.repay_type in ( '解保','代偿' ) AND r.repay_confirm_time >= '${repayTimeStart}' AND r.repay_confirm_time <= '${repayTimeEnd}'  " +
                "  groupByStr ORDER BY p.busi_type ";
        String groupByStr="GROUP BY p.busi_type";
        //历史数据
        String guaranteeSql = "SELECT  IFNULL(sum(pds.amount),0) amount FROM ${pmDbTitle}.project_data_info_his_data pds WHERE  project_date='${projectDate}' AND busiTypeEndStr  Order by busi_type";
        if (data == null)
            data = new ArrayList<ExpectReleaseInfo>();
        guaranteeSql= guaranteeSql.replaceAll("\\$\\{projectDate\\}",
                order.getReportLastYearEndDay());
        String sql=  "select 'typeStr' type,'' busi_type,'担保金额小计' busi_type_name,pds.amount,r.actual_amount,re.repay_amount  from ("+guaranteeSql+") pds,("+accurSql+") r, ("+repaySql+") re  ";
        String typeStr="融资性担保业务";
        String busiTypeStr="p.busi_type like '1%'";
        String busiTypeEndStr="pds.busi_type like '1%'";
        String startWith1=sql;
        startWith1 = startWith1.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        //
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(startWith1);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        typeStr="非融资性担保业务";
        busiTypeStr="p.busi_type like '2%'";
        busiTypeEndStr="pds.busi_type like '2%'";
        String startWith2=sql;
        startWith2 = startWith2.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        queyOrder.setSql(startWith2);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        typeStr="再担保";
        busiTypeStr="p.busi_type like '3%'";
        busiTypeEndStr="pds.busi_type like '3%'";
        String startWith3=sql;
        startWith3 = startWith3.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        queyOrder.setSql(startWith3);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        //担保金额合计
        ExpectReleaseInfo totalInfo=new ExpectReleaseInfo();
        totalInfo.setData1("担保业务合计");
        totalInfo.setData2("");
        totalInfo.setData3("担保金额合计");
        totalInfo.setData4(totalAmount.toString());
        totalInfo.setData5(totalActulAmount.toString());
        totalInfo.setData6(totalRepayAmount.toString());
        data.add(totalInfo);
    }

    private void setDataInfo( List<DataListItem> dataResult,List<ExpectReleaseInfo> data){
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String type = String.valueOf(itm.getMap().get("type"));
                String busiType = String.valueOf(itm.getMap().get("busi_type"));
                String busiTypeName = String.valueOf(itm.getMap().get("busi_type_name"));
                Money amount = toMoney(itm.getMap().get("amount"));
                Money actualAmount = toMoney(itm.getMap().get("actual_amount"));
                Money repay_amount = toMoney(itm.getMap().get("repay_amount"));
                if("代偿金额".equals(busiTypeName)){
                    totalcompAmount.addTo(amount);
                    totalCurrCompAmount.addTo(actualAmount);
                    totalRecoveryAmount.addTo(repay_amount);
                }else {
                totalAmount.addTo(amount);
                totalActulAmount.addTo(actualAmount);
                totalRepayAmount.addTo(repay_amount);
                }
                ExpectReleaseInfo dataInfo = null;
                for (ExpectReleaseInfo info : data) {
                    if (StringUtil.equals(info.getData1(), type)&&StringUtil.equals(info.getData3(), busiTypeName)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new ExpectReleaseInfo();
                    dataInfo.setData1(type);
                    dataInfo.setData2(busiType);
                    dataInfo.setData3(busiTypeName);
                    dataInfo.setData4(amount.toString());
                    dataInfo.setData5(actualAmount.toString());
                    dataInfo.setData6(repay_amount.toString());
                    data.add(dataInfo);
                }
            }
        }
    }


    /**
     * 在保余额
     * @param order
     * @param data
     */
    private void balanceAmount(ReportQueryOrder order,List<ExpectReleaseInfo> data) {
        //银行融资担保细分类 细分类 已放款-已还款
        String bankTypeSql="SELECT \n" +
                "A.liquidity_loan_amount-B.liquidity_loan_amount AS liquidity_loan_balance_amount, \n" +
                "A.acceptance_bill_amount-B.acceptance_bill_amount AS acceptance_bill_balance_amount, \n" +
                "A.credit_letter_amount-B.credit_letter_amount AS credit_letter_balance_amount, \n" +
                "A.fixed_assets_financing_amount-B.fixed_assets_financing_amount AS fixed_assets_financing_amount, \n" +
                "C.liquidity_loan_loan_amount,C.acceptance_bill_loan_amount,C.credit_letter_loan_amount,C.fixed_assets_financing_loan_amount,\n" +
                "D.liquidity_loan_repay_amount,D.acceptance_bill_repay_amount,D.credit_letter_repay_amount,D.fixed_assets_financing_repay_amount\n" +
                "FROM (\n" +
                "SELECT \n" +
                "IFNULL(SUM(liquidity_loan_amount),0) liquidity_loan_amount,\n" +
                "IFNULL(SUM(acceptance_bill_amount),0) acceptance_bill_amount,\n" +
                "IFNULL(SUM(credit_letter_amount),0) credit_letter_amount,\n" +
                "IFNULL(SUM(fixed_assets_financing_amount),0) fixed_assets_financing_amount\n" +
                "FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p\n" +
                "ON r.project_code=p.project_code\n" +
                " WHERE p.busi_type='11' AND r.raw_add_time<='${projectDate} 23:59:59') A,\n" +
                " (\n" +
                "SELECT \n" +
                "IFNULL(SUM(liquidity_loan_amount),0) liquidity_loan_amount,\n" +
                "IFNULL(SUM(acceptance_bill_amount),0) acceptance_bill_amount,\n" +
                "IFNULL(SUM(credit_letter_amount),0) credit_letter_amount,\n" +
                "IFNULL(SUM(fixed_assets_financing_amount),0) fixed_assets_financing_amount\n" +
                " FROM ${pmDbTitle}.f_counter_guarantee_repay  r JOIN ${pmDbTitle}.f_counter_guarantee_release fc ON r.form_id=fc.form_id\n" +
                " JOIN ${pmDbTitle}.project p ON fc.project_code=p.project_code\n" +
                " JOIN ${pmDbTitle}.form f ON fc.form_id=f.form_id WHERE f.status='APPROVAL' AND p.busi_type='11' AND f.finish_time<='${projectDate} 23:59:59'\n" +
                " )B,( \n" +
                " SELECT \n" +
                "IFNULL(SUM(liquidity_loan_amount),0) liquidity_loan_loan_amount,\n" +
                "IFNULL(SUM(acceptance_bill_amount),0) acceptance_bill_loan_amount,\n" +
                "IFNULL(SUM(credit_letter_amount),0) credit_letter_loan_amount,\n" +
                "IFNULL(SUM(fixed_assets_financing_amount),0) fixed_assets_financing_loan_amount\n" +
                "FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p\n" +
                "ON r.project_code=p.project_code\n" +
                " WHERE p.busi_type='11' AND r.raw_add_time>='${loanStartTime}' AND r.raw_add_time <='${loanEndTime}' \n" +
                " )C,(\n" +
                " SELECT \n" +
                "IFNULL(SUM(liquidity_loan_amount),0) liquidity_loan_repay_amount,\n" +
                "IFNULL(SUM(acceptance_bill_amount),0) acceptance_bill_repay_amount,\n" +
                "IFNULL(SUM(credit_letter_amount),0) credit_letter_repay_amount,\n" +
                "IFNULL(SUM(fixed_assets_financing_amount),0) fixed_assets_financing_repay_amount\n" +
                " FROM ${pmDbTitle}.f_counter_guarantee_repay  r JOIN ${pmDbTitle}.f_counter_guarantee_release fc ON r.form_id=fc.form_id\n" +
                " JOIN ${pmDbTitle}.project p ON fc.project_code=p.project_code\n" +
                " JOIN ${pmDbTitle}.form f ON fc.form_id=f.form_id WHERE f.status='APPROVAL' AND p.busi_type='11' AND f.finish_time>='${repayTimeStart}' AND f.finish_time <='${repayTimeEnd}'\n" +
                " )D";
         bankTypeSql=bankTypeSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                 .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                 .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                 .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                 .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                 .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay());
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(bankTypeSql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String type = "融资性担保业务";
                String busiType = "";
                Money liquidityLoanBalanceAmount = toMoney(itm.getMap().get("liquidity_loan_balance_amount"));
                Money acceptanceBillBalanceAmount = toMoney(itm.getMap().get("acceptance_bill_balance_amount"));
                Money creditLetterBalanceAmount = toMoney(itm.getMap().get("credit_letter_balance_amount"));
                Money fixedAssetsFinancingAmount = toMoney(itm.getMap().get("fixed_assets_financing_amount"));
                Money liquidityLoanLoanAmount = toMoney(itm.getMap().get("liquidity_loan_loan_amount"));
                Money acceptanceBillLoanAmount = toMoney(itm.getMap().get("acceptance_bill_loan_amount"));
                Money creditLetterLoanAmount = toMoney(itm.getMap().get("credit_letter_loan_amount"));
                Money fixedAssetsFinancingLoanAmount = toMoney(itm.getMap().get("fixed_assets_financing_loan_amount"));
                Money liquidityLoanRepayAmount = toMoney(itm.getMap().get("liquidity_loan_repay_amount"));
                Money acceptanceBillRepayAmount = toMoney(itm.getMap().get("acceptance_bill_repay_amount"));
                Money creditLetterRepayAmount = toMoney(itm.getMap().get("credit_letter_repay_amount"));
                Money fixedAssetsFinancingRepayAmount = toMoney(itm.getMap().get("fixed_assets_financing_repay_amount"));
                ExpectReleaseInfo dataInfo =new ExpectReleaseInfo();
                    dataInfo.setData1(type);
                    dataInfo.setData2(busiType);
                    dataInfo.setData3("流动资金贷款");
                    dataInfo.setData4(liquidityLoanBalanceAmount.toString());
                    dataInfo.setData5(liquidityLoanLoanAmount.toString());
                    dataInfo.setData6(liquidityLoanRepayAmount.toString());
                    data.add(dataInfo);
                dataInfo =new ExpectReleaseInfo();
                dataInfo.setData1(type);
                dataInfo.setData2(busiType);
                dataInfo.setData3("承兑汇票担保");
                dataInfo.setData4(acceptanceBillBalanceAmount.toString());
                dataInfo.setData5(acceptanceBillLoanAmount.toString());
                dataInfo.setData6(acceptanceBillRepayAmount.toString());
                data.add(dataInfo);
                dataInfo =new ExpectReleaseInfo();
                dataInfo.setData1(type);
                dataInfo.setData2(busiType);
                dataInfo.setData3("信用证担保");
                dataInfo.setData4(creditLetterBalanceAmount.toString());
                dataInfo.setData5(creditLetterLoanAmount.toString());
                dataInfo.setData6(creditLetterRepayAmount.toString());
                data.add(dataInfo);
                dataInfo =new ExpectReleaseInfo();
                dataInfo.setData1(type);
                dataInfo.setData2(busiType);
                dataInfo.setData3("固定资产融资");
                dataInfo.setData4(fixedAssetsFinancingAmount.toString());
                dataInfo.setData5(fixedAssetsFinancingLoanAmount.toString());
                dataInfo.setData6(fixedAssetsFinancingRepayAmount.toString());
                data.add(dataInfo);
            }
        }

        //---------------------------------------------------------------
        //其他融资担保
        String accurSql=" SELECT p.busi_type,p.busi_type_name,SUM(r.actual_amount) actual_amount  " +
                " FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code  " +
                " WHERE r.apply_type IN ('BOTH', 'LOAN')  " +
                " AND r.raw_add_time >= '${loanStartTime}' AND raw_add_time <= '${loanEndTime}'  " +
                " AND busiTypeStr  groupByStr ORDER BY p.busi_type ";
        String repaySql="SELECT p.busi_type,p.busi_type_name,IFNULL(SUM(r.repay_amount), 0) repay_amount  " +
                "  FROM ${pmDbTitle}.view_project_repay_detail r JOIN ${pmDbTitle}.project p ON p.project_code = r.project_code  " +
                "  WHERE busiTypeStr  " +
                "  AND r.repay_type in('解保','代偿') AND r.repay_confirm_time >= '${repayTimeStart}' AND r.repay_confirm_time <= '${repayTimeEnd}'  " +
                "  groupByStr ORDER BY p.busi_type ";
        String groupByStr="";
        String balanceSql = "SELECT  p.busi_type,p.busi_type_name,SUM(p.blance) amount \n" +
                "FROM ${pmDbTitle}.project_data_info_his_data p WHERE  project_date='${projectDate}' AND busiTypeStr GROUP BY busi_type Order by busi_type";
        if (data == null)
            data = new ArrayList<ExpectReleaseInfo>();
        balanceSql= balanceSql.replaceAll("\\$\\{projectDate\\}",
                order.getReportLastYearEndDay());
        String sql="SELECT type,busi_type_name,SUM(amount) amount,SUM(actual_amount) actual_amount,SUM(repay_amount) repay_amount\n" +
                "FROM(" +
                "SELECT " +
                "    '融资性担保业务' type,\n" +
                "    CODE busi_type,\n"+
                "    '其他融资性担保项' busi_type_name,\n" +
                "    IFNULL(b.amount,0) amount,\n" +
                "    IFNULL(r.actual_amount,0) actual_amount,\n" +
                "    IFNULL(re.repay_amount,0) repay_amount" +
                "    FROM ${pmDbTitle}.busi_type bt LEFT JOIN  ("+balanceSql+") b ON bt.code=b.busi_type LEFT JOIN ("+accurSql+") r on b.busi_type=r.busi_type  LEFT JOIN ("+repaySql+") re on b.busi_type=re.busi_type \n"+
                "    WHERE (CODE like '1%' and CODE not in('11','111','113','114','12','121','122','123','124','125','126'))   order by CODE )A";

        String busiTypeStr=" (p.busi_type like '1%' and p.busi_type not in('11','111','113','114','12','121','122','123','124','125','126')) ";

                sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr",groupByStr);
                queyOrder = new PmReportDOQueryOrder();
                fieldMap = new HashMap<>();
                queyOrder.setFieldMap(fieldMap);
                queyOrder.setSql(sql);
                dataResult = pmReportServiceClient.doQuery(queyOrder);
        setInfo(dataResult,data);
        //---------------------------------------------------------------
        //债券发行担保
        sql="SELECT type,busi_type_name,SUM(amount) amount,SUM(actual_amount) actual_amount,SUM(repay_amount) repay_amount\n" +
                "FROM(" +
                "SELECT " +
                "    '融资性担保业务' type,\n" +
                "    CODE busi_type,\n"+
                "    '债券发行担保' busi_type_name,\n" +
                "    IFNULL(b.amount,0) amount,\n" +
                "    IFNULL(r.actual_amount,0) actual_amount,\n" +
                "    IFNULL(re.repay_amount,0) repay_amount" +
                "    FROM ${pmDbTitle}.busi_type bt LEFT JOIN  ("+balanceSql+") b ON bt.code=b.busi_type LEFT JOIN ("+accurSql+") r on b.busi_type=r.busi_type  LEFT JOIN ("+repaySql+") re on b.busi_type=re.busi_type \n"+
                "    WHERE (CODE  in('12','121','122','123','124','125','126'))   order by CODE )A";

        busiTypeStr=" ( p.busi_type  in('12','121','122','123','124','125','126')) ";

        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr",groupByStr);
        queyOrder = new PmReportDOQueryOrder();
        fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setInfo(dataResult,data);
        //非融资性担保业务
        sql="SELECT "+
                "    '非融资性担保业务' type,\n" +
                "    CODE busi_type,\n"+
                "    CASE  WHEN CODE ='211' THEN '诉讼保全担保'"+
                "    WHEN CODE ='221' THEN '履约担保' END busi_type_name,\n" +
                "    IFNULL(b.amount,0) amount,\n" +
                "    IFNULL(r.actual_amount,0) actual_amount,\n" +
                "    IFNULL(re.repay_amount,0) repay_amount" +
                "    FROM ${pmDbTitle}.busi_type bt LEFT JOIN  ("+balanceSql+") b ON bt.code=b.busi_type LEFT JOIN ("+accurSql+") r on b.busi_type=r.busi_type  LEFT JOIN ("+repaySql+") re on b.busi_type=re.busi_type \n"+
                "    WHERE (CODE  in('211','221'))   order by CODE ";

        busiTypeStr=" ( p.busi_type  in('211','221')) ";

        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr",groupByStr);
        queyOrder = new PmReportDOQueryOrder();
        fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setInfo(dataResult,data);

        //---------------------------------------------------------------
        //其他非融资担保
        sql="SELECT type,busi_type_name,SUM(amount) amount,SUM(actual_amount) actual_amount,SUM(repay_amount) repay_amount\n" +
                "FROM(" +
                "SELECT " +
                "    '非融资性担保业务' type,\n" +
                "    CODE busi_type,\n"+
                "    '其他非融资性担保' busi_type_name,\n" +
                "    IFNULL(b.amount,0) amount,\n" +
                "    IFNULL(r.actual_amount,0) actual_amount,\n" +
                "    IFNULL(re.repay_amount,0) repay_amount" +
                "    FROM ${pmDbTitle}.busi_type bt LEFT JOIN  ("+balanceSql+") b ON bt.code=b.busi_type LEFT JOIN ("+accurSql+") r on b.busi_type=r.busi_type  LEFT JOIN ("+repaySql+") re on b.busi_type=re.busi_type \n"+
                "    WHERE (CODE like '2%' and CODE not in('211','221'))   order by CODE )A";

        busiTypeStr=" ( p.busi_type like '2%' and p.busi_type not in('211','221')) ";

        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr",groupByStr);
        queyOrder = new PmReportDOQueryOrder();
        fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setInfo(dataResult,data);
    }

    private void setInfo( List<DataListItem> dataResult,List<ExpectReleaseInfo> data){
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String type = String.valueOf(itm.getMap().get("type"));
                String busiType = String.valueOf(itm.getMap().get("busi_type"));
                String busiTypeName = String.valueOf(itm.getMap().get("busi_type_name"));
                Money amount = toMoney(itm.getMap().get("amount"));
                Money actualAmount = toMoney(itm.getMap().get("actual_amount"));
                Money repay_amount = toMoney(itm.getMap().get("repay_amount"));
                ExpectReleaseInfo dataInfo = null;
                for (ExpectReleaseInfo info : data) {
                    if (StringUtil.equals(info.getData1(), type)&&StringUtil.equals(info.getData3(), busiTypeName)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new ExpectReleaseInfo();
                    dataInfo.setData1(type);
                    dataInfo.setData2(busiType);
                    dataInfo.setData3(busiTypeName);
                    dataInfo.setData4(amount.toString());
                    dataInfo.setData5(actualAmount.toString());
                    dataInfo.setData6(repay_amount.toString());
                    data.add(dataInfo);
                }
            }
        }
    }
    /**
     * 担保户数
     * @param order
     * @param data
     */
    private void guaranteeHouseHolds(ReportQueryOrder order,List<ExpectReleaseInfo> data) {
        //年初数
        String houseHoldsSql=" SELECT   '融资性担保业务' type,'担保户数' busi_type_name,  COUNT(DISTINCT customer_id) hs  FROM ${pmDbTitle}.project_data_info_his_data WHERE custom_name1 = '在保' AND customer_id > 0 AND project_date = '${projectDate}' AND (busi_type LIKE '1%')   " ;
        //本年增加数
        String accurSql=" SELECT COUNT(DISTINCT p.customer_id) accurHs  " +
                " FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code  " +
                " WHERE r.apply_type IN ('BOTH', 'LOAN')  " +
                " AND r.raw_add_time >= '${loanStartTime}' AND raw_add_time <= '${loanEndTime}'  AND (busi_type LIKE '1%') " ;
        //本年减少
        String releaseSql = "SELECT COUNT(DISTINCT p.customer_id) releaseHs "
                + "FROM ${pmDbTitle}.f_counter_guarantee_release r JOIN ${pmDbTitle}.form f ON r.form_id = f.form_id AND f.status = 'APPROVAL'   "
                + "JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code "
                + "WHERE (p.busi_type like '1%') AND f.finish_time >= '${releaseStartTime}' AND f.finish_time <= '${releaseEndTime}' ";
        if (data == null)
            data = new ArrayList<ExpectReleaseInfo>();
        String sql=  "select h.type,h.busi_type_name,h.hs,a.accurHs,r.releaseHs  from ("+houseHoldsSql+") h,("+accurSql+") a, ("+releaseSql+") r  ";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{releaseStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{releaseEndTime\\}", order.getReportMonthEndDay());
        //
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String type = String.valueOf(itm.getMap().get("type"));
                String busiTypeName = String.valueOf(itm.getMap().get("busi_type_name"));
                String hs = String.valueOf(itm.getMap().get("hs"));
                String accurHs = String.valueOf(itm.getMap().get("accurHs"));
                String releaseHs = String.valueOf(itm.getMap().get("releaseHs"));
                ExpectReleaseInfo dataInfo = null;
                for (ExpectReleaseInfo info : data) {
                    if (StringUtil.equals(info.getData1(), type)&&StringUtil.equals(info.getData3(), busiTypeName)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new ExpectReleaseInfo();
                    dataInfo.setData1(type);
                    dataInfo.setData3(busiTypeName);
                    dataInfo.setData4(hs);
                    dataInfo.setData5(accurHs);
                    dataInfo.setData6(releaseHs);
                    data.add(dataInfo);
                }
            }
        }

    }

    /**
     * 代偿金额
     * @param order
     * @param data
     */
    private void compPrincipalAmount(ReportQueryOrder order,List<ExpectReleaseInfo> data) {
        String paySql=" SELECT IFNULL(SUM(pay.pay_amount),0) actual_amount  " +
                " FROM ${pmDbTitle}.view_project_pay_detail pay JOIN ${pmDbTitle}.project p on pay.project_code=p.project_code where " +
                "fee_type in('COMPENSATORY_PRINCIPAL','COMPENSATORY_INTEREST','COMPENSATORY_PENALTY','COMPENSATORY_LIQUIDATED_DAMAGES','COMPENSATORY_OTHER') and  pay.pay_time >= '${loanStartTime}' AND pay.pay_time <= '${loanEndTime}'  " +
                " AND busiTypeStr  groupByStr ORDER BY p.busi_type ";
        String chargeSql=" SELECT IFNULL(SUM(charge.charge_amount),0) repay_amount  " +
                " FROM ${pmDbTitle}.view_project_charge_detail charge JOIN ${pmDbTitle}.project p on charge.project_code=p.project_code where " +
                "fee_type in('RECOVERY_INCOME') and  charge.charge_time >= '${loanStartTime}' AND charge.charge_time <= '${loanEndTime}'  " +
                " AND busiTypeStr  groupByStr ORDER BY p.busi_type ";
        String groupByStr="GROUP BY p.busi_type";
        //历史数据
        String guaranteeSql = " SELECT IFNULL(SUM(pay.pay_amount),0) amount  " +
                " FROM ${pmDbTitle}.view_project_pay_detail pay JOIN ${pmDbTitle}.project p on pay.project_code=p.project_code where " +
                "fee_type in('COMPENSATORY_PRINCIPAL','COMPENSATORY_INTEREST','COMPENSATORY_PENALTY','COMPENSATORY_LIQUIDATED_DAMAGES','COMPENSATORY_OTHER') and  pay.pay_time <= '${projectDate} 23:59:59'  " +
                " AND busiTypeStr  groupByStr ORDER BY p.busi_type ";
        if (data == null)
            data = new ArrayList<ExpectReleaseInfo>();
        guaranteeSql= guaranteeSql.replaceAll("\\$\\{projectDate\\}",
                order.getReportLastYearEndDay());
        String sql=  "select 'typeStr' type,'' busi_type,'代偿金额' busi_type_name,pds.amount,r.actual_amount,re.repay_amount  from ("+guaranteeSql+") pds,("+paySql+") r, ("+chargeSql+") re  ";
        String typeStr="融资性担保业务";
        String busiTypeStr="p.busi_type like '1%'";
        String busiTypeEndStr="pds.busi_type like '1%'";
        String startWith1=sql;
        startWith1 = startWith1.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        //
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(startWith1);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        typeStr="非融资性担保业务";
        busiTypeStr="p.busi_type like '2%'";
        busiTypeEndStr="pds.busi_type like '2%'";
        String startWith2=sql;
        startWith2 = startWith2.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        queyOrder.setSql(startWith2);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        typeStr="再担保";
        busiTypeStr="p.busi_type like '3%'";
        busiTypeEndStr="pds.busi_type like '3%'";
        String startWith3=sql;
        startWith3 = startWith3.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("typeStr",typeStr)
                .replaceAll("\\$\\{projectDate\\}", order.getReportLastYearEndDay())
                .replaceAll("busiTypeStr", busiTypeStr)
                .replaceAll("busiTypeEndStr", busiTypeEndStr)
                .replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{loanEndTime\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay())
                .replaceAll("\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay())
                .replaceAll("groupByStr","");
        queyOrder.setSql(startWith3);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data);
        //代偿金额合计
        ExpectReleaseInfo totalInfo=new ExpectReleaseInfo();
        totalInfo.setData1("担保业务合计");
        totalInfo.setData2("");
        totalInfo.setData3("代偿金额合计");
        totalInfo.setData4(totalcompAmount.toString());
        totalInfo.setData5(totalCurrCompAmount.toString());
        totalInfo.setData6(totalRecoveryAmount.toString());
        data.add(totalInfo);

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
    private Money toMoneyW(Object wan) {
        try {
            String s = String.valueOf(wan);
            return Money.amout(s).multiply(10000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Money.zero();
    }
}
