package com.born.fcs.face.integration.pm.service.basicmaintain;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.pm.ws.info.basicmaintain.AssessCompanyInfo;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyOrder;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyQueryOrder;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 *
 * @author heh
 *
 */
@Service("assessCompanyServiceClient")
public class AssessCompanyServiceClient extends ClientAutowiredBaseService implements
        AssessCompanyService {


    @Override
    public AssessCompanyInfo findById(final long companyId) {
        return callInterface(new CallExternalInterface<AssessCompanyInfo>() {

            @Override
            public AssessCompanyInfo call() {
                return assessCompanyWebService.findById(companyId);
            }
        });
    }

    @Override
    public FcsBaseResult save(final AssessCompanyOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return assessCompanyWebService.save(order);
            }
        });
    }


    @Override
    public QueryBaseBatchResult<AssessCompanyInfo> query(final AssessCompanyQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<AssessCompanyInfo>>() {

            @Override
            public QueryBaseBatchResult<AssessCompanyInfo> call() {
                return assessCompanyWebService.query(order);
            }
        });
    }

    @Override
    public FcsBaseResult deleteById(final AssessCompanyOrder order) {

       return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
               return assessCompanyWebService.deleteById(order);

            }
        });
    }

    @Override
    public List<String> findCities() {

        return callInterface(new CallExternalInterface<List<String>>() {
            @Override
            public  List<String> call() {
                return assessCompanyWebService.findCities();

            }
        });
    }

    @Override
    public long findByName(final String companyName) {
        return callInterface(new CallExternalInterface<Long>() {

            @Override
            public Long call() {
                return assessCompanyWebService.findByName(companyName);
            }
        });
    }
}
