package com.born.fcs.pm.ws.service.contract;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.contract.DbFieldInfo;
import com.born.fcs.pm.ws.info.contract.DbFieldListInfo;
import com.born.fcs.pm.ws.order.contract.DbFieldOrder;
import com.born.fcs.pm.ws.order.contract.DbFieldQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 常用数据库字段
 * 
 * @author heh
 */
@WebService
public interface DbFieldService {
	
	/**
	 * 根据ID常用数据库字段
	 * @param id
	 * @return
	 */
	DbFieldInfo findById(long id);
	
	/**
	 * 保存根据ID常用数据库字段
	 * @param order
	 * @return
	 */
	FcsBaseResult save(DbFieldOrder order);
	
	/**
	 * 查询根据ID常用数据库字段
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<DbFieldListInfo> query(DbFieldQueryOrder order);
	
	/**
	 * 根据ID删除根据ID常用数据库字段
	 * @param order
	 * @return
	 */
	FcsBaseResult deleteById(DbFieldOrder order);
	
	/**
	 * 根据字段名称查数据
	 * @param fieldName
	 * @return
	 */
	long findByFieldName(long tableId, String fieldName);
	
	/**
	 * 根据字段简称名称查数据
	 * @param shortForName
	 * @return
	 */
	long findByFieldShortName(long tableId, String shortForName);
	
	/**
	 * 查询tableID查询字段
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<DbFieldInfo> queryByTableId(DbFieldQueryOrder order);
}
