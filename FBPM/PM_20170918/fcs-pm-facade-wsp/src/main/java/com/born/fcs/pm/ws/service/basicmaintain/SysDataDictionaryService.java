package com.born.fcs.pm.ws.service.basicmaintain;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysDataDictionaryInfo;
import com.born.fcs.pm.ws.order.basicmaintain.SysDataDictionaryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 数据字典
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface SysDataDictionaryService {
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(SysDataDictionaryOrder order);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	SysDataDictionaryInfo findOne(long id);
	
	/**
	 * 根据数据分类查询
	 * @param dataCode
	 * @return
	 */
	List<SysDataDictionaryInfo> findByDataCode(DataCodeEnum dataCode);
	
	/**
	 * 根据上级ID查询
	 * @param parentId
	 * @return
	 */
	List<SysDataDictionaryInfo> findByParentId(long parentId);
	
	/**
	 * 根据ID级联查询
	 * @param id
	 * @return
	 */
	SysDataDictionaryInfo findOneCascade(long id);
	
	/**
	 * 根据数据分类级联查询
	 * @param dataCode
	 * @return
	 */
	List<SysDataDictionaryInfo> findByDataCodeCascade(DataCodeEnum dataCode);
	
	/**
	 * 根据上级级联查询
	 * @param parentId
	 * @return
	 */
	List<SysDataDictionaryInfo> findByParentIdCascade(long parentId);
}
