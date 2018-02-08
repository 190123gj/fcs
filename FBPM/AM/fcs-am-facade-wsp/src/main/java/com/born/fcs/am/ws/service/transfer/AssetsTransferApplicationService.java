package com.born.fcs.am.ws.service.transfer;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资产转让service
 *
 * @author Ji
 */
@WebService
public interface AssetsTransferApplicationService {
	
	/**
	 * 根据ID查询资产转让信息
	 * 
	 * @param id
	 * @return
	 */
	FAssetsTransferApplicationInfo findById(long id);
	
	/**
	 * 根据表单ID查询资产转让信息
	 * 
	 * @param id
	 * @return
	 */
	FAssetsTransferApplicationInfo findByFormId(long formId);
	
	/**
	 * 分页查询资产转让信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FAssetsTransferApplicationInfo> query(	FAssetsTransferApplicationQueryOrder order);
	
	/**
	 * 根据项目编号查询所有的资产转让信息
	 *
	 * @param projectCode
	 * @return
	 */
	List<FAssetsTransferApplicationInfo> findByProjectCode(String projectCode);
	
	/**
	 * 保存资产转让申请 表单数据
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult save(FAssetsTransferApplicationOrder order);
	
	/**
	 * 根据id关闭系统消息推送
	 * 
	 * @param id
	 * @return
	 */
	int updateIsColseMessage(long id);
	
	/**
	 * 根据id更新是否收费
	 * 
	 * @param id
	 * @return
	 */
	int updateIsCharge(long id);
	
	/**
	 * 同步预测数据
	 * @param formId
	 * @return
	 */
	public FcsBaseResult syncForecast(long formId);
}
