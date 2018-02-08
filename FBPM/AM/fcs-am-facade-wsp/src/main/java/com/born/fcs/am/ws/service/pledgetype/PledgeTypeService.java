package com.born.fcs.am.ws.service.pledgetype;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeSimpleInfo;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeOrder;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 抵质押品分类service
 *
 * @author Ji
 */
@WebService
public interface PledgeTypeService {

	/**
	 * 根据ID查询分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	PledgeTypeInfo findById(long typeId);

	/**
	 * 根据一级分类和二级分类查询分类分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeTypeInfo> findByLevelOneAndLevelTwo(String levelOne,
			String levelTwo);

	/**
	 * 保存分类信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(PledgeTypeOrder order);

	/**
	 * 查询分类信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeInfo> query(PledgeTypeQueryOrder order);

	/**
	 * 根据id删除分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	int deleteById(long typeId);

	/**
	 * 删除目录时检测
	 * 
	 * @param typeId
	 * @return
	 */
	Boolean deleteCheck(long typeId, String level);

	/**
	 * 是否重名检测
	 * 
	 * @param typeId
	 * @return
	 */
	Boolean isSameNameCheck(String levelOne, boolean isAddlevelOne,
			String levelTwo, boolean isAddlevelTwo, String levelThree,
			boolean isAddlevelThree);

	/**
	 * 根据levelOne levelTwo levelThree查询分类信息
	 * 
	 * @param levelOne
	 * @param levelTwo
	 * @param levelThree
	 * @return
	 */
	PledgeTypeInfo findByLevelOneTwoThree(String levelOne, String levelTwo,
			String levelThree);

	/**
	 * 根据一级分类和二级分类查询分类分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeTypeInfo> findByLevelOneAndLevelTwoForAssets(String levelOne,
			String levelTwo);
	
	/**
	 * 查询所有分类
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeSimpleInfo> queryAll();
	
}
