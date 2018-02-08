package com.born.fcs.pm.ws.service.council;

import java.util.Date;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.order.council.CouncilDelOrder;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@WebService
public interface CouncilService {
	
	/**
	 * 保存会议
	 * 
	 * @param councilOrder
	 * @return
	 */
	FcsBaseResult saveCouncil(CouncilOrder councilOrder);
	
	/**
	 * 分页查询会议列表
	 * 
	 * @param councilQueryOrder
	 * @return 会议列表
	 */
	QueryBaseBatchResult<CouncilInfo> queryCouncil(CouncilQueryOrder councilQueryOrder);
	
	/**
	 * 查询某会议
	 * 
	 * @param councilId
	 * @return
	 */
	CouncilInfo queryCouncilById(long councilId);
	
	/**
	 * 设置会议结束
	 * 
	 * @param councilId
	 * @return
	 */
	FcsBaseResult endCouncil(long councilId);
	
	/**
	 * 设置会议开始
	 * 
	 * @param councilId
	 * @return
	 */
	FcsBaseResult beginCouncil(long councilId);
	
	/**
	 * 获取系统时间用于判定是否应该开始会议，【因为多次会议时每次都啦系统时间太麻烦，所以把啦系统时间单独拉成一个方法，
	 * 以后有多个地方用到的时候再考虑单独提出来，这里就只为开始会议服务】 from: hjiajie,有问题请联系 佳洁。
	 * 
	 * @param councilId
	 * @return
	 */
	Date getSysDate();
	
	/**
	 * 删除会议
	 * 
	 * @param councilOrder
	 * @return
	 */
	FcsBaseResult delCouncilByCouncilId(Long councilId);
	
	/**
	 * 删除会议
	 * 
	 * @param councilOrder
	 * @return
	 */
	FcsBaseResult delCouncil(CouncilDelOrder order);
	
}
