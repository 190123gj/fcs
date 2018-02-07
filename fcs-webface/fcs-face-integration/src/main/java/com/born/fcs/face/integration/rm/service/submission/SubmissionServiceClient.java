package com.born.fcs.face.integration.rm.service.submission;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.submission.SubmissionOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author heh
 *
 */
@Service("submissionServiceClient")
public class SubmissionServiceClient extends ClientAutowiredBaseService implements SubmissionService {
    @Override
    public SubmissionInfo findById(final long id) {
        return callInterface(new CallExternalInterface<SubmissionInfo>() {

            @Override
            public SubmissionInfo call() {
                return submissionWebService.findById(id);
            }
        });
    }

    @Override
    public FcsBaseResult save(final SubmissionOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return submissionWebService.save(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<SubmissionInfo> query(final SubmissionQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<SubmissionInfo>>() {

            @Override
            public QueryBaseBatchResult<SubmissionInfo> call() {
                return submissionWebService.query(order);
            }
        });
    }

    @Override
    public FcsBaseResult updateStatus(final String submissionCodes,final int year,final int month) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return submissionWebService.updateStatus(submissionCodes,year,month);
            }
        });
    }

    @Override
    public FcsBaseResult deleteById(final SubmissionOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return submissionWebService.deleteById(order);
            }
        });
    }

    @Override
    public String findDeptInfoByDeptCode(final String deptCode) {
        return callInterface(new CallExternalInterface<String>() {

            @Override
            public String call() {
                return submissionWebService.findDeptInfoByDeptCode(deptCode);
            }
        });
    }

    @Override
    public List<String> getDepts() {
        return callInterface(new CallExternalInterface<List<String>>() {

            @Override
            public List<String> call() {
                return submissionWebService.getDepts();
            }
        });
    }
}
