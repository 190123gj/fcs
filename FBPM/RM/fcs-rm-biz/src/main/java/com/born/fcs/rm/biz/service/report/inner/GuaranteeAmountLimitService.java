package com.born.fcs.rm.biz.service.report.inner;

import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeAmountLimitInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 担保业务金额/期限分类汇总表
 * @author heh
 */
@Service("guaranteeAmountLimitService")
public class GuaranteeAmountLimitService extends BaseAutowiredDomainService implements
        ReportProcessService {
    @Autowired
    private PmReportServiceClient pmReportServiceClient;

    private String sql="SELECT  class.classify,class.index_num,IFNULL(A.hs,0) hs,IFNULL(A1.bs,0) bs,IFNULL(B.zbhs,0) zbhs,IFNULL(B.zbbs,0) zbbs,IFNULL(B2.contract_amount,0) contract_amount,IFNULL(C.blance_init,0) blance_amount,IFNULL(D.occur_amount,0) occur_amount,IFNULL(E.repay_amount,0) repay_amount,IFNULL(B.blance,0) blance  FROM \n" +
            "( classTable )class LEFT JOIN\n" +
            "(SELECT classifyStr,COUNT(DISTINCT customer_id) hs FROM ${pmDbTitle}.project_data_info p WHERE customer_add_time>='reportMonthStartTime' and customer_add_time<='reportMonthEndTime'  AND conditionStr GROUP BY classify )A ON class.classify=A.classify LEFT JOIN\n" +
            "(SELECT classifyStr, COUNT(DISTINCT project_code) bs FROM ${pmDbTitle}.project_data_info p WHERE apply_date>='reportMonthStartTime' AND  apply_date<='reportMonthEndTime' AND conditionStr GROUP BY classify )A1 ON class.classify=A1.classify LEFT JOIN\n"+
            "(SELECT classifyStr,COUNT(DISTINCT p.customer_id) zbhs, COUNT(*) zbbs,SUM(ba.balance) blance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT  project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail  WHERE occur_date <= '${reportMonthEndDay}'   GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount  FROM  ${pmDbTitle}.view_project_repay_detail  WHERE repay_type IN ('解保','诉保解保','代偿')  AND repay_confirm_time <= '${reportMonthEndDay}'  GROUP BY project_code) repay  ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND conditionStr GROUP BY classify) B ON class.classify=B.classify LEFT JOIN \n" +
            "(SELECT classifyStr,SUM(p.contract_amount) contract_amount FROM  ${pmDbTitle}.projectTableName p  WHERE p.custom_name1 = '在保' projectDateCondition AND conditionStr GROUP BY classify ) B2 ON class.classify=B2.classify LEFT JOIN \n" +
            "(SELECT classifyStr, SUM(ba.balance) blance_init  FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT  project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail  WHERE occur_date <= '${reportLastYearEndDay}'   GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount  FROM  ${pmDbTitle}.view_project_repay_detail  WHERE repay_type IN ('解保','诉保解保','代偿')  AND repay_confirm_time <= '${reportLastYearEndDay}'  GROUP BY project_code) repay  ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  conditionStr GROUP BY classify)C ON A.classify=C.classify LEFT JOIN \n" +
            "(SELECT   classifyStr,  IFNULL(SUM(r.occur_amount),0) occur_amount FROM ${pmDbTitle}.project_data_info p join ${pmDbTitle}.view_project_occer_detail r on p.project_code=r.project_code WHERE  r.occur_date >= 'startTimeStr'  AND r.occur_date <= 'endTimeStr' AND conditionStr  GROUP BY classify )D ON class.classify=D.classify LEFT JOIN \n" +
            "(SELECT classifyStr, r.repay_amount repay_amount,p.project_code  FROM ${pmDbTitle}.view_project_repay_detail r  JOIN ${pmDbTitle}.project_data_info p  ON p.project_code = r.project_code \n" +
            "  WHERE conditionStr AND r.repay_type IN ('解保','诉保解保', '代偿') AND r.repay_confirm_time >= 'startTimeStr' AND r.repay_confirm_time <= 'endTimeStr' GROUP BY classify)E ON class.classify=E.classify ORDER BY class.index_num";
    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder order) {
        QueryBaseBatchResult<GuaranteeAmountLimitInfo> result = new QueryBaseBatchResult<GuaranteeAmountLimitInfo>();
        List<GuaranteeAmountLimitInfo> data = new ArrayList<GuaranteeAmountLimitInfo>();
        try {

            //按合同金额
            contractAmount(order, data);
            //按期末余额
            blanceAmount(order, data);
            //按授信期限
            creditLimit(order, data);
            //按到期期限分类
            expireLimit(order, data);
            result.setSuccess(true);
            result.setPageList(data);
            result.setTotalCount(data.size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("担保业务金额/期限分类汇总表");
            logger.error("担保业务金额/期限分类汇总表：{}", e);
        }
        return result;
    }
    private void contractAmount(ReportQueryOrder order, List<GuaranteeAmountLimitInfo> data) {
        String classifyStr="    CASE\n" +
                "    WHEN p.contract_amount >= 50000000000 \n" +
                "    THEN '5亿以上' \n" +
                "    WHEN p.contract_amount <50000000000 \n" +
                "    AND p.contract_amount >= 30000000000 \n" +
                "    THEN '3-5亿元（不含）' \n" +
                "     WHEN p.contract_amount <30000000000 \n" +
                "    AND p.contract_amount >= 10000000000 \n" +
                "    THEN '1-3亿元（不含）' \n" +
                "     WHEN p.contract_amount <10000000000 \n" +
                "    AND p.contract_amount >= 5000000000 \n" +
                "    THEN '5000万元-1亿元（不含）' \n" +
                "     WHEN p.contract_amount <5000000000 \n" +
                "    AND p.contract_amount >= 1000000000 \n" +
                "    THEN '1000万-5000万元（不含）' \n" +
                "    ELSE '1000万元以下' \n" +
                "   END classify";
        String classTable="SELECT '5亿以上' classify,0 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '3-5亿元（不含）' classify,1 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1-3亿元（不含）' classify,2 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '5000万元-1亿元（不含）' classify,3 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1000万-5000万元（不含）' classify,4 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1000万元以下' classify,5 index_num FROM DUAL";
        String execsql=replaceSql(order,classTable,classifyStr);
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(execsql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data,"按金额分类","按合同金额分类");
    }
    private void blanceAmount(ReportQueryOrder order, List<GuaranteeAmountLimitInfo> data) {
        String baTableStr="(SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT  project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail  WHERE occur_date <= '${reportMonthEndDay}'   GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount  FROM  ${pmDbTitle}.view_project_repay_detail  WHERE repay_type IN ('解保','诉保解保','代偿')  AND repay_confirm_time <= '${reportMonthEndDay}'  GROUP BY project_code) repay  ON repay.project_code = occer.project_code) ba";
        String balanceSql="SELECT  class.classify,class.index_num,IFNULL(A.hs,0) hs,IFNULL(A1.bs,0) bs,IFNULL(B.zbhs,0) zbhs,IFNULL(B.zbbs,0) zbbs,IFNULL(B2.contract_amount,0) contract_amount,IFNULL(C.blance_init,0) blance_amount,IFNULL(D.occur_amount,0) occur_amount,IFNULL(E.repay_amount,0) repay_amount,IFNULL(B.blance,0) blance  FROM \n" +
                "( classTable )class LEFT JOIN\n" +
                "(SELECT classifyStr,COUNT(DISTINCT customer_id) hs FROM ${pmDbTitle}.project_data_info p left join baTableStr on p.project_code=ba.project_code WHERE customer_add_time>='reportMonthStartTime' and customer_add_time<='reportMonthEndTime'  AND conditionStr GROUP BY classify )A ON class.classify=A.classify LEFT JOIN\n" +
                "(SELECT classifyStr, COUNT(DISTINCT p.project_code) bs FROM ${pmDbTitle}.project_data_info p left join baTableStr on p.project_code=ba.project_code WHERE apply_date>='reportMonthStartTime' AND  apply_date<='reportMonthEndTime' AND conditionStr GROUP BY classify )A1 ON class.classify=A1.classify LEFT JOIN\n"+
                "(SELECT classifyStr,COUNT(DISTINCT p.customer_id) zbhs, COUNT(*) zbbs,SUM(ba.balance) blance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT  project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail  WHERE occur_date <= '${reportMonthEndDay}'   GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount  FROM  ${pmDbTitle}.view_project_repay_detail  WHERE repay_type IN ('解保','诉保解保','代偿')  AND repay_confirm_time <= '${reportMonthEndDay}'  GROUP BY project_code) repay  ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND conditionStr GROUP BY classify) B ON class.classify=B.classify LEFT JOIN \n" +
                "(SELECT classifyStr,SUM(p.contract_amount) contract_amount FROM  ${pmDbTitle}.projectTableName p left join baTableStr on p.project_code=ba.project_code WHERE p.custom_name1 = '在保' projectDateCondition AND conditionStr GROUP BY classify ) B2 ON class.classify=B2.classify LEFT JOIN \n" +
                "(SELECT classifyStr, SUM(ba.balance) blance_init  FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT  project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail  WHERE occur_date <= '${reportLastYearEndDay}'   GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount  FROM  ${pmDbTitle}.view_project_repay_detail  WHERE repay_type IN ('解保','诉保解保','代偿')  AND repay_confirm_time <= '${reportLastYearEndDay}'  GROUP BY project_code) repay  ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  conditionStr GROUP BY classify)C ON A.classify=C.classify LEFT JOIN \n" +
                "(SELECT   classifyStr,  IFNULL(SUM(r.occur_amount),0) occur_amount FROM ${pmDbTitle}.project_data_info p left join baTableStr on p.project_code=ba.project_code join ${pmDbTitle}.view_project_occer_detail r on p.project_code=r.project_code WHERE  r.occur_date >= 'startTimeStr'  AND r.occur_date <= 'endTimeStr' AND conditionStr  GROUP BY classify )D ON class.classify=D.classify LEFT JOIN \n" +
                "(SELECT classifyStr, r.repay_amount repay_amount,p.project_code  FROM ${pmDbTitle}.view_project_repay_detail r  JOIN ${pmDbTitle}.project_data_info p  ON p.project_code = r.project_code left join baTableStr on p.project_code=ba.project_code\n" +
                "  WHERE conditionStr AND r.repay_type IN ('解保','诉保解保', '代偿') AND r.repay_confirm_time >= 'startTimeStr' AND r.repay_confirm_time <= 'endTimeStr' GROUP BY classify)E ON class.classify=E.classify ORDER BY class.index_num";
        String classifyStr="    CASE\n" +
                "    WHEN ba.balance >= 50000000000 \n" +
                "    THEN '5亿以上' \n" +
                "    WHEN ba.balance <50000000000 \n" +
                "    AND ba.balance >= 30000000000 \n" +
                "    THEN '3-5亿元（不含）' \n" +
                "     WHEN ba.balance <30000000000 \n" +
                "    AND ba.balance >= 10000000000 \n" +
                "    THEN '1-3亿元（不含）' \n" +
                "     WHEN ba.balance <10000000000 \n" +
                "    AND ba.balance >= 5000000000 \n" +
                "    THEN '5000万元-1亿元（不含）' \n" +
                "    ELSE '5000万元以下' \n" +
                "   END classify";
        String classTable="SELECT '5亿以上' classify,0 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '3-5亿元（不含）' classify,1 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1-3亿元（不含）' classify,2 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '5000万元-1亿元（不含）' classify,3 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '5000万元以下' classify,4 index_num FROM DUAL";
        String conditionStr="(p.contract_time IS NOT NULL AND p.contract_time!='')  AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') ";
        baTableStr=baTableStr.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle) .replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{reportLastYearEndDay\\}",order.getReportLastYearEndDay());
        if(order.isThisYear()&&order.isThisMonth()){//本年月查实时表
            balanceSql=balanceSql.replaceAll("projectTableName","project_data_info").replaceAll("projectDateCondition","");
        }else{
            balanceSql=balanceSql.replaceAll("projectTableName","project_data_info_his_data").replaceAll("projectDateCondition","and p.project_date='projectDateStr'");
        }
        balanceSql=  balanceSql.replaceAll("baTableStr",baTableStr).replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
                .replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay())
                .replaceAll("\\$\\{reportLastYearEndDay\\}",order.getReportLastYearEndDay())
                .replaceAll("classifyStr",classifyStr)
                .replaceAll("conditionStr",conditionStr)
                .replaceAll("classTable",classTable)
                .replaceAll("projectDateStr",order.getReportMonthEndDay())
                .replaceAll("startTimeStr",order.getReportYearStartDay())
                .replaceAll("endTimeStr", order.getReportMonthEndDay())
                .replaceAll("reportMonthStartTime",order.getReportMonthStartDay())
                .replaceAll("reportMonthEndTime",order.getReportMonthEndDay());
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(balanceSql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data,"按金额分类","按期末余额分类");
    }
    private void creditLimit(ReportQueryOrder order, List<GuaranteeAmountLimitInfo> data) {
        String classifyStr="    CASE\n" +
                "    WHEN (p.time_unit = 'Y' or p.time_unit = 'YEAR') and p.time_limit>=5 \n" +
                "    THEN '5年以上' \n" +
                "    WHEN (p.time_unit = 'Y' or p.time_unit = 'YEAR') and (p.time_limit>=3 and p.time_limit<5) "+
                "    THEN '3年（含）-5年（不含）' \n" +
                "    WHEN (p.time_unit = 'Y' or p.time_unit = 'YEAR') and (p.time_limit>=2 and p.time_limit<3) "+
                 "    THEN '2年（含）-3年（不含）' \n" +
                 "    WHEN (p.time_unit = 'Y' or p.time_unit = 'YEAR') and (p.time_limit>=1 and p.time_limit<2)"+
                "    THEN '1年（含）-2年（不含）' \n" +
                "    ELSE '1年以下' \n" +
                "   END classify";
        String classTable="SELECT '5年以上' classify,0 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '3年（含）-5年（不含）' classify,1 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '2年（含）-3年（不含）' classify,2 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1年（含）-2年（不含）' classify,3 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1年以下' classify,4 index_num FROM DUAL";
        String execsql=replaceSql(order,classTable,classifyStr);
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(execsql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data,"按授信期限分类","-");
    }
    private void expireLimit(ReportQueryOrder order, List<GuaranteeAmountLimitInfo> data) {
        String classifyStr="    CASE\n" +
                "    WHEN datediff(p.end_time,p.start_time)/365>5 \n" +
                "    THEN '5年以上' \n" +
                "    WHEN datediff(p.end_time,p.start_time)/365>=3 and datediff(p.end_time,p.start_time)/365<5 "+
                "    THEN '3年（含）-5年（不含）' \n" +
                "    WHEN datediff(p.end_time,p.start_time)/365>=2 and datediff(p.end_time,p.start_time)/365<3 "+
                "    THEN '2年（含）-3年（不含）' \n" +
                "    WHEN datediff(p.end_time,p.start_time)/365>=1 and datediff(p.end_time,p.start_time)/365<2"+
                "    THEN '1年（含）-2年（不含）' \n" +
                "    ELSE '1年以下' \n" +
                "   END classify";
        String classTable="SELECT '5年以上' classify,0 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '3年（含）-5年（不含）' classify,1 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '2年（含）-3年（不含）' classify,2 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1年（含）-2年（不含）' classify,3 index_num FROM DUAL\n" +
                "UNION ALL \n" +
                "SELECT '1年以下' classify,4 index_num FROM DUAL";
        String execsql=replaceSql(order,classTable,classifyStr);
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(execsql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        setDataInfo(dataResult,data,"按到期期限分类","-");
    }
    private void setDataInfo( List<DataListItem> dataResult,List<GuaranteeAmountLimitInfo> data,String type,String subType){
        GuaranteeAmountLimitInfo totalInfo=new GuaranteeAmountLimitInfo();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String classify = String.valueOf(itm.getMap().get("classify"));
                long hs = Long.parseLong(String.valueOf(itm.getMap().get("hs")));
                long bs = Long.parseLong(String.valueOf(itm.getMap().get("bs")));
                long zbhs = Long.parseLong(String.valueOf(itm.getMap().get("zbhs")));
                long zbbs = Long.parseLong(String.valueOf(itm.getMap().get("zbbs")));
                Money contractAmount = toMoney(itm.getMap().get("contract_amount"));
                Money blanceAmount = toMoney(itm.getMap().get("blance_amount"));
                Money occurAmount = toMoney(itm.getMap().get("occur_amount"));
                Money repayAmount = toMoney(itm.getMap().get("repay_amount"));
                Money blance = toMoney(itm.getMap().get("blance"));
                GuaranteeAmountLimitInfo dataInfo =  new GuaranteeAmountLimitInfo();
                dataInfo.setType(type);
                dataInfo.setSubType(subType);
                dataInfo.setClassify(classify);
                dataInfo.setHs(hs);
                dataInfo.setBs(bs);
                dataInfo.setZbhs(zbhs);
                dataInfo.setZbbs(zbbs);
                dataInfo.setContractAmount(contractAmount);
                dataInfo.setBalanceInit(blanceAmount);
                dataInfo.setOccurAmount(occurAmount);
                dataInfo.setReleaseAmount(repayAmount);
                dataInfo.setBalanceEnding(blance);
                data.add(dataInfo);
                totalInfo.setType(type);
                totalInfo.setSubType(subType);
                totalInfo.setClassify("合计");
                totalInfo.setHs(totalInfo.getHs()+hs);
                totalInfo.setBs(totalInfo.getBs()+bs);
                totalInfo.setZbhs(totalInfo.getZbhs()+zbhs);
                totalInfo.setZbbs(totalInfo.getZbbs()+zbbs);
                totalInfo.getContractAmount().addTo(contractAmount);
                totalInfo.getBalanceInit().addTo(blanceAmount);
                totalInfo.getOccurAmount().addTo(occurAmount);
                totalInfo.getReleaseAmount().addTo(repayAmount);
                totalInfo.getBalanceEnding().addTo(blance);
            }
            data.add(totalInfo);
        }
    }
    private String replaceSql(ReportQueryOrder order,String classTable,String classifyStr){
        String conditionStr="(p.contract_time IS NOT NULL AND p.contract_time!='')  AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') ";
        String execSql=sql;
        if(order.isThisYear()&&order.isThisMonth()){//本年月查实时表
            execSql=execSql.replaceAll("projectTableName","project_data_info").replaceAll("projectDateCondition","");
        }else{
            execSql=execSql.replaceAll("projectTableName","project_data_info_his_data").replaceAll("projectDateCondition","and p.project_date='projectDateStr'");
        }
        return execSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()).replaceAll("\\$\\{reportLastYearEndDay\\}",order.getReportLastYearEndDay())
                .replaceAll("classifyStr",classifyStr).replaceAll("conditionStr",conditionStr).replaceAll("classTable",classTable)
                .replaceAll("projectDateStr",order.getReportMonthEndDay())
                .replaceAll("startTimeStr",
                        order.getReportYearStartDay()).replaceAll("endTimeStr",
                order.getReportMonthEndDay()).replaceAll("reportMonthStartTime",order.getReportMonthStartDay()).replaceAll("reportMonthEndTime",order.getReportMonthEndDay());
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

}
