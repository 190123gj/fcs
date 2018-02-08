package com.born.fcs.pm.biz.job;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ReportExpectEventDAO;
import com.born.fcs.pm.dal.dataobject.ReportExpectEventDO;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.ReportSysDataTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * 报表系统每年统计数据
 *
 * @author heh
 *
 */
@Service("toReportWithYearJob")
public class ToReportWithYearJob extends JobBase implements ProcessJobService {

    @Autowired
    private BusiDAO busiDAO;

    @Scheduled(cron = "0 0 0 1 1 ? ")
    @Override
    public void doJob() throws Exception {
		if (!isRun)
			return;

    }
}
