package com.born.fcs.rm.biz.service.report.outer;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 外部报表<br />
 * 市金融办月/季报<br />
 * （市金融办季度报告）融资性担保公司年末经营预测表
 *
 * @author heh
 *
 * 2016年8月23日  16:45:42
 */
@Service("guaranteeYearEndExpectService")
public class GuaranteeYearEndExpectServiceImpl extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    private SubmissionService submissionService;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
        String hasAllData="IS";
        QueryBaseBatchResult<SubmissionDataInfo> batchResult = new QueryBaseBatchResult<>();
        List<SubmissionDataInfo> lists = new ArrayList<SubmissionDataInfo>();
        //来自数据报送：融资性担保公司年末经营预测表-规划投资部上报
        SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBGSNMJYYCB_GHTZB.code());
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
                 for(SubmissionDataInfo dataInfo:data){
                     lists.add(dataInfo);
                 }
                }
            }
        }else{
            SubmissionDataInfo dataInfo=new SubmissionDataInfo();
            lists.add(dataInfo);
            lists.add(dataInfo);
            lists.add(dataInfo);
            hasAllData="NO";
        }
        //来自数据报送：融资性担保公司年末经营预测表-财务部上报
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBGSNMJYYCB_CWB.code());
        batches = submissionService
                .query(submissionQueryOrder);
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submission = batches.getPageList().get(0);
            submission = submissionService.findById(submission.getSubmissionId());
            if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
                List<SubmissionDataInfo> data = submission.getData();
                if (data.size() >= 1) {
                    for(SubmissionDataInfo dataInfo:data){
                        lists.add(dataInfo);
                    }
                }
            }
        }else{
            SubmissionDataInfo dataInfo=new SubmissionDataInfo();
            lists.add(dataInfo);
            lists.add(dataInfo);
            hasAllData="NO";
        }
        //来自数据报送：融资性担保公司年末经营预测表-风险管理部上报
        submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBGSNMJYYCB_FXB.code());
        batches = submissionService
                .query(submissionQueryOrder);
        if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
            SubmissionInfo submission = batches.getPageList().get(0);
            submission = submissionService.findById(submission.getSubmissionId());
            if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
                List<SubmissionDataInfo> data = submission.getData();
                if (data.size() >= 1) {
                    for(SubmissionDataInfo dataInfo:data){
                        lists.add(dataInfo);
                    }
                }
            }
        }else{
            SubmissionDataInfo dataInfo=new SubmissionDataInfo();
            lists.add(dataInfo);
            lists.add(dataInfo);
            lists.add(dataInfo);
            hasAllData="NO";
        }

        batchResult.setPageList(lists);
        batchResult.setSuccess(true);
        batchResult.setSortCol(hasAllData);
        return batchResult;
    }
}
