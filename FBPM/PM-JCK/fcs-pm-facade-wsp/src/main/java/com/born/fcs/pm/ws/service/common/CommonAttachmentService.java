/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentDeleteOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.CommonAttachmentResult;

/**
 * 
 * @Filename CommonAttachmentService.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author jiajie
 * 
 * @Email hjiajie@yiji.com
 * 
 * @History <li>Author: jiajie</li> <li>Date: 2013-6-19</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@WebService
public interface CommonAttachmentService {
	
	/**
	 * 群查图片信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(	CommonAttachmentQueryOrder order);
	
	/**
	 * 单插图片信息
	 * @param order
	 * @return
	 */
	FcsBaseResult insert(CommonAttachmentOrder order);
	
	/**
	 * 群插图片信息
	 * @param CommonAttachments
	 * @return
	 */
	FcsBaseResult insertAll(List<CommonAttachmentOrder> CommonAttachments);
	
	/**
	 * 通过id删除图片
	 * @param id
	 * @return
	 */
	FcsBaseResult deleteById(long id);
	
	/**
	 * 通过id删除图片(同时删除未审核的所有同张图片)
	 * @param id
	 * @return
	 */
	CommonAttachmentResult deleteByIdAllSame(long id);
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	CommonAttachmentResult deletePicture(CommonAttachmentDeleteOrder order);
	
	/**
	 * 删除图片
	 * 
	 * @param bizNo
	 * @param types
	 * @return
	 */
	CommonAttachmentResult deleteByBizNoModuleType(String bizNo, CommonAttachmentTypeEnum... types);
	
	/**
	 * 通过id查找图片信息
	 * @param id
	 * @return
	 */
	CommonAttachmentResult findById(long id);
	
	/**
	 * 通过id更新图片信息
	 * @param Id
	 * @return
	 */
	FcsBaseResult updateStatusById(long id, CheckStatusEnum status);
	
	/**
	 * 通过bizNo和主状态更新所有符合条件的图片信息(更新状态)
	 * @param Id
	 * @return
	 */
	FcsBaseResult updateStatus(String bizNo, CommonAttachmentTypeEnum moduleType,
								CheckStatusEnum status);
	
	/**
	 * 删除图片 [所有参数不能为空]
	 * @param bizNo
	 * @param childId
	 * @param moduleType
	 * @return
	 */
	FcsBaseResult deleteByBizNoAndChildIdModuleType(String bizNo, String childId,
													CommonAttachmentTypeEnum... moduleType);
}
