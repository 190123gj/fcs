package com.born.fcs.face.integration.pm.service.contract;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.pm.ws.info.contract.DbTableInfo;
import com.born.fcs.pm.ws.order.contract.DbTableOrder;
import com.born.fcs.pm.ws.order.contract.DbTableQueryOrder;
import com.born.fcs.pm.ws.service.contract.DbTableService;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;


/**
 *
 * @author heh
 *
 */
@Service("dbTableServiceClient")
public class DbTableServiceClient extends ClientAutowiredBaseService implements
        DbTableService {


    @Override
    public DbTableInfo findById(final long id) {
        return callInterface(new CallExternalInterface<DbTableInfo>() {

            @Override
            public DbTableInfo call() {
                return dbTableWebService.findById(id);
            }
        });
    }

    @Override
    public FcsBaseResult save(final DbTableOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return dbTableWebService.save(order);
            }
        });
    }


    @Override
    public QueryBaseBatchResult<DbTableInfo> query(final DbTableQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<DbTableInfo>>() {

            @Override
            public QueryBaseBatchResult<DbTableInfo> call() {
                return dbTableWebService.query(order);
            }
        });
    }

    @Override
    public FcsBaseResult deleteById(final DbTableOrder order) {

        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return dbTableWebService.deleteById(order);

            }
        });
    }

    @Override
    public long findByName(final String tableName) {
        return callInterface(new CallExternalInterface<Long>() {
            @Override
            public Long call() {
                return dbTableWebService.findByName(tableName);

            }
        });
    }


}
