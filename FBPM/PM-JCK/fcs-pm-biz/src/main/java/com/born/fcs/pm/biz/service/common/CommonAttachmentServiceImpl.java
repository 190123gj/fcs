package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.CommonAttachmentDAO;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentDeleteOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.CommonAttachmentResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.yjf.common.lang.util.StringUtil;

@Service("commonAttachmentService")
public class CommonAttachmentServiceImpl extends BaseAutowiredDomainService implements
																			CommonAttachmentService {
	@Autowired
	CommonAttachmentDAO commonAttachmentDAO;
	
	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = new QueryBaseBatchResult<CommonAttachmentInfo>();
		logger.info("进入列表查询图片信息");
		try {
			
			List<String> moduleTypeCodeList = new ArrayList<String>();
			if (order.getModuleTypeList() != null) {
				for (CommonAttachmentTypeEnum typeEnum : order.getModuleTypeList()) {
					moduleTypeCodeList.add(typeEnum.code());
				}
			}
			if (order.getIsort() != 0) {
				List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO.findByIsort(
					order.getIsort(), order.getBizNo(), order.getChildId());
				List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
				for (CommonAttachmentDO tradeAttachmentDO : attachmentDOs) {
					CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
					MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
					attachmentInfos.add(attachmentInfo);
				}
				result.setPageList(attachmentInfos);
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
			} else {
				List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO.findByManyModuleType(
					moduleTypeCodeList, order.getBizNo(), order.getChildId());
				List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
				for (CommonAttachmentDO tradeAttachmentDO : attachmentDOs) {
					CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
					MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
					if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
						attachmentInfo.setModuleType(CommonAttachmentTypeEnum
							.getByCode(tradeAttachmentDO.getModuleType()));
					}
					
					if (StringUtil.isNotBlank(tradeAttachmentDO.getCheckStatus())) {
						attachmentInfo.setCheckstatus(CheckStatusEnum.getByCode(tradeAttachmentDO
							.getCheckStatus()));
					}
					attachmentInfos.add(attachmentInfo);
				}
				result.setPageList(attachmentInfos);
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
			}
			
		} catch (DataAccessException e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult insert(CommonAttachmentOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		logger.info("进入插入单条图片信息");
		try {
			int count = 1;
			CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
			MiscUtil.copyPoObject(attachmentDO, order);
			//attachmentDO.setModuleType(order.getModuleType().getCode());
			attachmentDO.setRawAddTime(new Date());
			commonAttachmentDAO.insert(attachmentDO);
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult insertAll(List<CommonAttachmentOrder> CommonAttachments) {
		FcsBaseResult result = new FcsBaseResult();
		logger.info("进入插入多条图片信息");
		try {
			int count = 0;
			for (CommonAttachmentOrder order : CommonAttachments) {
				CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
				MiscUtil.copyPoObject(attachmentDO, order);
				attachmentDO.setModuleType(order.getModuleType().getCode());
				attachmentDO.setRawAddTime(new Date());
				commonAttachmentDAO.insert(attachmentDO);
				count++;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("插入多条图片信息失败");
		}
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deleteById(long)
	 */
	@Override
	public CommonAttachmentResult deleteById(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				commonAttachmentDAO.deleteById(id);
				
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deleteByIdAllSame(long)
	 */
	@Override
	public CommonAttachmentResult deleteByIdAllSame(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息(同时删除未审核的其他同地址图片信息)");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				long findCount = 0;
				long delCount = 0;
				commonAttachmentDAO.deleteById(id);
				delCount++;
				List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
					.getFilePhysicalPath());
				for (CommonAttachmentDO pic : pics) {
					if (StringUtil.isNotBlank(pic.getCheckStatus())
						&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
						findCount++;
					} else {
						commonAttachmentDAO.deleteById(pic.getAttachmentId());
						delCount++;
					}
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append("删除完成,总计删除[");
				sb.append(delCount);
				sb.append("]行,找到[");
				sb.append(findCount);
				sb.append("]行,已审核过的数据!");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param Id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#findJoinApplyById(long)
	 */
	@Override
	public CommonAttachmentResult findById(long id) {
		
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入查询单条图片信息{}", id);
		try {
			CommonAttachmentDO tradeAttachmentDO = commonAttachmentDAO.findById(id);
			if (tradeAttachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
				MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
				if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
					attachmentInfo.setModuleType(CommonAttachmentTypeEnum
						.getByCode(tradeAttachmentDO.getModuleType()));
				}
				
				result.setAttachmentInfo(attachmentInfo);
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deletePicture(com.yjf.estate.service.order.CommonAttachmentDeleteOrder)
	 */
	@Override
	public CommonAttachmentResult deletePicture(CommonAttachmentDeleteOrder order) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			order.check();
			if (order.getAttachmentId() > 0) {
				CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(order
					.getAttachmentId());
				if (attachmentDO == null || !attachmentDO.getBizNo().equals(order.getBizNo())) {
					result.setSuccess(false);
					result.setMessage("无效的图片id");
				} else {
					long findCount = 0;
					long delCount = 0;
					commonAttachmentDAO.deleteById(order.getAttachmentId());
					delCount++;
					List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
						.getFilePhysicalPath());
					for (CommonAttachmentDO pic : pics) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			} else {
				if (order.getFilePhysicalPath() != null) {
					long findCount = 0;
					long delCount = 0;
					List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO
						.findByPicpath(order.getFilePhysicalPath());
					for (CommonAttachmentDO pic : attachmentDOs) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult deleteByBizNoModuleType(String bizNo,
															CommonAttachmentTypeEnum... types) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除图片信息[{}], [{}]", bizNo, types);
		if (null != types && types.length > 0) {
			for (CommonAttachmentTypeEnum type : types) {
				if (null != type) {
					commonAttachmentDAO.deleteByBizNoModuleType(bizNo, type.code());
				}
			}
		}
		result.setSuccess(true);
		result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		return result;
	}
	
	@Override
	public FcsBaseResult updateStatusById(long id, CheckStatusEnum status) {
		FcsBaseResult result = new FcsBaseResult();
		logger.info("进入更新图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				attachmentDO.setCheckStatus(status.code());
				commonAttachmentDAO.update(attachmentDO);
				StringBuilder sb = new StringBuilder();
				sb.append("更新完成,将id为[");
				sb.append(id);
				sb.append("]更新为[");
				sb.append(status);
				sb.append("]状态");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult updateStatus(String bizNo, CommonAttachmentTypeEnum moduleType,
										CheckStatusEnum status) {
		
		FcsBaseResult result = new FcsBaseResult();
		StringBuilder sb1 = new StringBuilder();
		sb1.append("入参:bizNo=[");
		sb1.append(bizNo);
		sb1.append("],moduleType=[");
		sb1.append(moduleType);
		sb1.append("],status=[");
		sb1.append(status);
		sb1.append("].");
		if (StringUtil.isBlank(bizNo) || moduleType == null || status == null) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("请求参数不完整,参数全部必传");
			sb1.append("进入更新图片状态信息,请求参数不完整");
			logger.error(sb1.toString());
			return result;
		}
		logger.info(sb1.toString());
		try {
			commonAttachmentDAO.updateStatusAllSame(status.code(), bizNo, moduleType.code());
			result.setSuccess(true);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
			result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult deleteByBizNoAndChildIdModuleType(String bizNo, String childId,
															CommonAttachmentTypeEnum... moduleType) {
		logger
			.error(
				"进入CommonAttachmentServiceImpl的deleteByBizNoAndChildIdModuleType【3参数必传】，入参：bizNo={},childId={},moduleType={}",
				bizNo, childId, moduleType);
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			if (StringUtil.isBlank(bizNo) || StringUtil.isBlank(childId) || moduleType == null) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("入参不能为空！");
			}
			for (CommonAttachmentTypeEnum type : moduleType) {
				commonAttachmentDAO.deleteByBizNoAndChildIdModuleType(bizNo, type.code(), childId);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除附件异常");
		}
		
		return result;
	}
	
}