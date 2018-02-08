package com.born.fcs.am.ws.service.transferee;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资产受让service
 *
 * @author Ji
 */
@WebService
public interface AssetsTransfereeApplicationService {

	/**
	 * 根据ID查询资产受让信息
	 * 
	 * @param id
	 * @return
	 */
	FAssetsTransfereeApplicationInfo findById(long id);

	/**
	 * 根据表单ID查询资产受让信息
	 * 
	 * @param id
	 * @return
	 */
	FAssetsTransfereeApplicationInfo findByFormId(long formId);

	/**
	 * 分页查询资产受让信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> query(
			FAssetsTransfereeApplicationQueryOrder order);

	/**
	 * 保存资产受让申请 表单数据
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult save(FAssetsTransfereeApplicationOrder order);

	/**
	 * 根据id关闭系统消息推送
	 * 
	 * @param id
	 * @return
	 */
	int updateIsColseMessage(long id);

	/**
	 * 分页查询资产转让项目
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FAssetsTransferApplicationInfo> queryTransferProject(
			FAssetsTransferApplicationQueryOrder order);

	/**
	 * 检查外部项目填写的时候 是否重名
	 * 
	 * @param projectName
	 * @return
	 */
	long findByOuterProjectNameCount(String projectName);
}
