package com.born.fcs.face.integration.pm.service.fund;


import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.List;
@Service("chargeNotificationFeeServiceClient")
public class ChargeNotificationFeeServiceClient extends ClientAutowiredBaseService implements ChargeNotificationFeeService{
    @Override
    public FcsBaseResult saveChargeNotificationFee(final FChargeNotificationFeeOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return chargeNotificationFeeWebService.saveChargeNotificationFee(order);
            }
        });
    }

    @Override
    public FcsBaseResult destroyById(final FChargeNotificationFeeOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return chargeNotificationFeeWebService.destroyById(order);
            }
        });
    }

    @Override
    public FChargeNotificationFeeInfo queryById(final long id) {
        return callInterface(new CallExternalInterface<FChargeNotificationFeeInfo>() {

            @Override
            public FChargeNotificationFeeInfo call() throws SocketTimeoutException {
                return chargeNotificationFeeWebService.queryById(id);
            }
        });
    }

    @Override
    public List<FChargeNotificationFeeInfo> queryByNotificationId(final long notificationId) {
        return callInterface(new CallExternalInterface<List<FChargeNotificationFeeInfo>>() {

            @Override
            public List<FChargeNotificationFeeInfo> call() throws SocketTimeoutException {
                return chargeNotificationFeeWebService.queryByNotificationId(notificationId);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<FChargeNotificationFeeInfo> queryChargeNotification(final FChargeNotificationFeeQueryOrder ChargeNotificationFeeQueryOrder) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FChargeNotificationFeeInfo>>() {

            @Override
            public QueryBaseBatchResult<FChargeNotificationFeeInfo> call() throws SocketTimeoutException {
                return chargeNotificationFeeWebService.queryChargeNotification(ChargeNotificationFeeQueryOrder);
            }
        });
    }
}
