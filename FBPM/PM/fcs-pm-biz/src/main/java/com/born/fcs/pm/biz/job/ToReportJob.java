package com.born.fcs.pm.biz.job;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ReportExpectEventDAO;
import com.born.fcs.pm.dal.dataobject.ReportExpectEventDO;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ReportSysDataTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * 报表系统每个月统计数据
 *
 * @author heh
 *
 */
@Service("toReportJob")
public class ToReportJob extends JobBase implements ProcessJobService {

    @Autowired
    private BusiDAO busiDAO;
    @Autowired
    private ReportExpectEventDAO reportExpectEventDAO;

    @Scheduled(cron = "0 0 0 1 * ? ")
    @Override
    public void doJob() throws Exception {
        if (!isRun)
            return;
        try {
            final Date now = DateUtil.getWeekdayBeforeDate(new Date());
            logger.error("报表系统统计融资担保业务在保余额合计:",now);
            String amount=busiDAO.getReleasingAmountToReport();
                    ReportExpectEventDO DO=new ReportExpectEventDO();
                    DO.setAmount(StringUtil.isNotEmpty(amount)?Long.parseLong(amount):0);
                    DO.setDeptCode(null);
                    DO.setDeptName(null);
                    DO.setType(ReportSysDataTypeEnum.RXDB_BALANCE_AMOUNT.code());
                    DO.setDeptId(0);
                    DO.setBusiType("1");
                    DO.setRawAddTime(now);
                    DO.setYear(Integer.parseInt(DateUtil.getYear(now)));
                    DO.setMonth(Integer.parseInt(DateUtil.getMonth(now)));
                    reportExpectEventDAO.insert(DO);
        } catch (Exception e) {
            logger.error("报表系统统计融资担保业务在保余额合计异常---异常原因：{}", e);
        }
        try {
            final Date now = DateUtil.getWeekdayBeforeDate(new Date());
            logger.error("报表系统根据批复中的授信金额(统计范围为：已批未放的担保、委贷类项目)开始时间:",now);
            List<ToReportExpectEventDO> eventDOs=busiDAO.getExpectEvent(now);
            if(eventDOs!=null&&eventDOs.size()>0){
                for(ToReportExpectEventDO eventDO:eventDOs){
                    ReportExpectEventDO DO=new ReportExpectEventDO();
                    DO.setAmount(eventDO.getAmount());
                    DO.setDeptCode(eventDO.getDeptCode());
                    DO.setDeptName(eventDO.getDeptName());
                    DO.setType(eventDO.getType());
                    DO.setDeptId(eventDO.getDeptId());
                    DO.setBusiType(eventDO.getBusiType());
                    DO.setRawAddTime(now);
                    DO.setYear(Integer.parseInt(DateUtil.getYear(now)));
                    DO.setMonth(Integer.parseInt(DateUtil.getMonth(now)));
                    reportExpectEventDAO.insert(DO);
                }
            }
        } catch (Exception e) {
            logger.error("报表系统根据批复中的授信金额(统计范围为：已批未放的担保、委贷类项目)异常---异常原因：{}", e);
        }
        try {
            final Date now = DateUtil.getWeekdayBeforeDate(new Date());
            logger.error("报表系统统计担保类别每个月在保余额(担保业务结构汇总表):", now);
            List<ToReportExpectEventDO> eventDOs=busiDAO.getReportGuaranteeStructreBalanceAmount();
            if(eventDOs!=null&&eventDOs.size()>0){
                for(ToReportExpectEventDO eventDO:eventDOs){
                    ReportExpectEventDO DO=new ReportExpectEventDO();
                    DO.setAmount(eventDO.getAmount());
                    DO.setDeptCode(eventDO.getDeptCode());
                    DO.setDeptName(eventDO.getDeptName());
                    DO.setType(ReportSysDataTypeEnum.DB_BALANCEAMOUNT.code());
                    DO.setDeptId(eventDO.getDeptId());
                    DO.setBusiType(eventDO.getBusiType());
                    DO.setRawAddTime(now);
                    DO.setYear(Integer.parseInt(DateUtil.getYear(now)));
                    DO.setMonth(Integer.parseInt(DateUtil.getMonth(now)));
                    reportExpectEventDAO.insert(DO);
                }
            }
        } catch (Exception e) {
            logger.error("报表系统统计担保类别每个月在保余额(担保业务结构汇总表)---异常原因：{}", e);
        }
    }
}
