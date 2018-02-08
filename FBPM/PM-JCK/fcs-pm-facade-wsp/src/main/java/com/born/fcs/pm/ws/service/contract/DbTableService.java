package com.born.fcs.pm.ws.service.contract;

import com.born.fcs.pm.ws.info.contract.DbTableInfo;
import com.born.fcs.pm.ws.order.contract.DbTableOrder;
import com.born.fcs.pm.ws.order.contract.DbTableQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * 常用数据库表
 *
 * @author heh
 */
@WebService
public interface DbTableService {

    /**
     * 根据ID常用数据库表
     * @param id
     * @return
     */
    DbTableInfo findById(long id);

    /**
     * 保存根据ID常用数据库表
     * @param order
     * @return
     */
    FcsBaseResult save(DbTableOrder order);


    /**
     * 查询根据ID常用数据库表信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<DbTableInfo> query(DbTableQueryOrder order);



    /**
     * 根据ID删除根据ID常用数据库表
     * @param order
     * @return
     */
    FcsBaseResult deleteById(DbTableOrder order);

    /**
     * 根据表名称查数据
     * @param tableName
     * @return
     */
    long findByName(String tableName);
}
