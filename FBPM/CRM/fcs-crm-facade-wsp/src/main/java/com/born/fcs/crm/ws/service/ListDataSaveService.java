package com.born.fcs.crm.ws.service;

import java.util.List;

import com.born.fcs.crm.ws.service.info.ListDataInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 集合类信息存储
 * **/
public interface ListDataSaveService {
	/** 增加 */
	FcsBaseResult add(ListDataInfo info);
	
	/** 更新 */
	FcsBaseResult update(ListDataInfo info);
	
	/** 删除 */
	FcsBaseResult delete(long id);
	
	/** 按类型删除 */
	FcsBaseResult deleteByType(String type);
	
	/**
	 * 批量更新
	 * @param id 父辈id
	 * @param list 挂在当前id下面的集合数据
	 * */
	FcsBaseResult updateByList(List<ListDataInfo> list, long id, String types);
	
	/** 按类型统计 */
	Long countByType(String type);
	
	/** 按类型查询集合 */
	List<ListDataInfo> list(String type);
}
