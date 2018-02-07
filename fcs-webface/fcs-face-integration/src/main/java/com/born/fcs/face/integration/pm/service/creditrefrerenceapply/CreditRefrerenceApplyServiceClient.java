package com.born.fcs.face.integration.pm.service.creditrefrerenceapply;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.creditrefrerenceapply.CreditRefrerenceApplyService;
import org.springframework.stereotype.Service;

/**
 *
 * @author heh
 *
 */
@Service("creditRefrerenceApplyServiceClient")
public class CreditRefrerenceApplyServiceClient extends ClientAutowiredBaseService implements
        CreditRefrerenceApplyService {


    @Override
    public CreditRefrerenceApplyInfo findById(final long id) {
        return callInterface(new CallExternalInterface<CreditRefrerenceApplyInfo>() {

            @Override
            public CreditRefrerenceApplyInfo call() {
                return creditRefrerenceApplyWebService.findById(id);
            }
        });
    }

    @Override
    public CreditRefrerenceApplyInfo findByFormId(final long formId) {
        return callInterface(new CallExternalInterface<CreditRefrerenceApplyInfo>() {

            @Override
            public CreditRefrerenceApplyInfo call() {
                return creditRefrerenceApplyWebService.findByFormId(formId);
            }
        });
    }

    @Override
    public FormBaseResult save(final CreditRefrerenceApplyOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return creditRefrerenceApplyWebService.save(order);
            }
        });
    }


    @Override
    public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> query(final CreditRefrerenceApplyQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo>>() {

            @Override
            public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> call() {
                return creditRefrerenceApplyWebService.query(order);
            }
        });
    }

    @Override
    public void revokeById(final long id) {

        callInterface(new CallExternalInterface<Object>() {
            @Override
            public Object call() {
                creditRefrerenceApplyWebService.revokeById(id);
                return null;
            }
        });
    }

    @Override
    public int updateStatusById(final long id, final String status) {
        return  callInterface(new CallExternalInterface<Integer>() {
            @Override
            public Integer call() {
                return creditRefrerenceApplyWebService.updateStatusById(id,status);
            }
        });
    }

    @Override
    public int updateApplyStatusById(final long id,final String applyStatus) {
        return  callInterface(new CallExternalInterface<Integer>() {
            @Override
            public Integer call() {
                return creditRefrerenceApplyWebService.updateApplyStatusById(id,applyStatus);
            }
        });
    }

    @Override
    public FcsBaseResult updateCreditReportById(final CreditRefrerenceApplyOrder order) {
        return  callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return creditRefrerenceApplyWebService.updateCreditReportById(order);
            }
        });
    }

	@Override
	public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> findCreditRefrerenceApplyInfos(
			CreditRefrerenceApplyQueryOrder queryOrder) {
		return creditRefrerenceApplyWebService.findCreditRefrerenceApplyInfos(queryOrder);
	}

	@Override
	public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> queryCreditRefrerenceApply(
			CreditRefrerenceApplyQueryOrder order) {
		
		return creditRefrerenceApplyWebService.queryCreditRefrerenceApply(order);
		
	}
}
