package com.born.fcs.rm.ws.service.submission;

import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.submission.SubmissionOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;
import java.util.List;

/**
 * 数据报送
 *
 * @author heh
 */
@WebService
public interface SubmissionService {


    SubmissionInfo findById(long id);


    FcsBaseResult save(SubmissionOrder order);


    QueryBaseBatchResult<SubmissionInfo> query(SubmissionQueryOrder order);

    FcsBaseResult updateStatus(String submissionCodes,int year,int month);

    FcsBaseResult deleteById( SubmissionOrder order);

    String findDeptInfoByDeptCode(String deptCode);

    List<String> getDepts();

}
