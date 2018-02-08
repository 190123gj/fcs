package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.CommonAttachmentDAO;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.ExpireProjectDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationDO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmItemDO;
import com.born.fcs.pm.dal.dataobject.FCreditRefrerenceApplyDO;
import com.born.fcs.pm.dal.dataobject.FFileOutputDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyReceiptDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.FProjectCustomerBaseInfoDO;
import com.born.fcs.pm.dal.dataobject.FRefundApplicationDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.AttachmentModuleType;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentDeleteOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.CommonAttachmentResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
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
				result.setTotalCount(attachmentDOs == null ? 0 : attachmentDOs.size());
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
	public QueryBaseBatchResult<CommonAttachmentInfo> queryPage(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = new QueryBaseBatchResult<CommonAttachmentInfo>();
		try {
			CommonAttachmentDO queryDO = new CommonAttachmentDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getCheckStatus() != null) {
				queryDO.setCheckStatus(order.getCheckStatus().code());
			}
			if (order.getModuleType() != null) {
				queryDO.setModuleType(order.getModuleType().code());
			}
			if (order.getCheckStatus() != null) {
				queryDO.setCheckStatus(order.getCheckStatus().code());
			}
			List<String> moduleTypeList = null;
			if (order.getModuleTypeList() != null) {
				moduleTypeList = new ArrayList<String>();
				for (CommonAttachmentTypeEnum typeEnum : order.getModuleTypeList()) {
					moduleTypeList.add(typeEnum.code());
				}
			}
			long totalSize = commonAttachmentDAO.findConditionCount(queryDO, moduleTypeList);
			PageComponent component = new PageComponent(order, totalSize);
			
			List<CommonAttachmentDO> dataList = commonAttachmentDAO.findCondition(queryDO,
				moduleTypeList, order.getSortCol(), order.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			
			List<CommonAttachmentInfo> list = result.getPageList();
			if (totalSize > 0) {
				for (CommonAttachmentDO DO : dataList) {
					CommonAttachmentInfo info = new CommonAttachmentInfo();
					BeanCopier.staticCopy(DO, info);
					info.setCheckstatus(CheckStatusEnum.getByCode(DO.getCheckStatus()));
					info.setModuleType(CommonAttachmentTypeEnum.getByCode(DO.getModuleType()));
					list.add(info);
				}
			}
			result.initPageParam(component);
			result.setPageList(list);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询附件出错：{}", e);
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
	
	@Override
	public FcsBaseResult addNewDelOldByMoudleAndBizNo(final CommonAttachmentBatchOrder order) {
		
		logger.info("开始添加附件：{}", order);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = new FcsBaseResult();
				try {
					try {
						order.check();
					} catch (IllegalArgumentException e) {
						result.setSuccess(false);
						result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
						result.setMessage(e.getMessage());
					}
					
					//先删除，再保存
					if (StringUtil.isNotBlank(order.getChildId())) {
						commonAttachmentDAO.deleteByBizNoAndChildIdModuleType(order.getBizNo(),
							order.getModuleType().code(), order.getChildId());
					} else {
						commonAttachmentDAO.deleteByBizNoModuleType(order.getBizNo(), order
							.getModuleType().code());
					}
					
					String[] attachPaths = order.getPath().split(";");
					int j = 1;
					for (String path : attachPaths) {
						String paths[] = path.split(",");
						if (null != paths && paths.length >= 3) {
							CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
							MiscUtil.copyPoObject(attachmentDO, order);
							attachmentDO.setModuleType(order.getModuleType().code());
							if (StringUtil.isBlank(order.getRemark())) {
								attachmentDO.setRemark(order.getModuleType().message());
							}
							attachmentDO.setRawAddTime(new Date());
							attachmentDO.setIsort(j++);
							attachmentDO.setFileName(paths[0]);
							attachmentDO.setFilePhysicalPath(paths[1]);
							attachmentDO.setRequestPath(paths[2]);
							commonAttachmentDAO.insert(attachmentDO);
						}
					}
					result.setSuccess(true);
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("添加附件失败");
					logger.error("添加附件失败,{}", e.getMessage(), e);
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatusCondition(CheckStatusEnum checkStatus,
												CommonAttachmentOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		
		if (order == null || order.isConditionNull()) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("更新条件不能全为空");
			logger.error("更新条件不能全为空");
			return result;
		}
		
		logger.info("开始更新附件状态：{}", order);
		try {
			CommonAttachmentDO condition = new CommonAttachmentDO();
			BeanCopier.staticCopy(order, condition);
			if (order.getModuleType() != null)
				condition.setModuleType(order.getModuleType().code());
			commonAttachmentDAO.updateStatusCondition(condition);
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
	
	/**
	 * @param id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deleteById(long)
	 */
	@Override
	public CommonAttachmentResult deleteAttachment(long id, String bizNO, String moduleType) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				String oldAttach = attachmentDO.getFileName() + ","
									+ attachmentDO.getFilePhysicalPath() + ","
									+ attachmentDO.getRequestPath();//原始附件 被替换的附件
				commonAttachmentDAO.deleteById(id);
				String newAttach = "";
				updateModuleAttachment(bizNO, moduleType, newAttach, oldAttach);
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
	
	@Override
	public FcsBaseResult updateAttachment(CommonAttachmentOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		
		if (order == null || order.isConditionNull()) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("更新条件不能全为空");
			logger.error("更新条件不能全为空");
			return result;
		}
		
		logger.info("开始更新附件状态：{}", order);
		try {
			CommonAttachmentDO condition = commonAttachmentDAO.findById(order.getAttachmentId());
			if (condition != null) {
				String oldAttach = condition.getFileName() + "," + condition.getFilePhysicalPath()
									+ "," + condition.getRequestPath();//原始附件 被替换的附件
				if (StringUtil.isNotEmpty(order.getFileName())) {
					condition.setFileName(order.getFileName());
				}
				if (StringUtil.isNotEmpty(order.getFilePhysicalPath())) {
					condition.setFilePhysicalPath(order.getFilePhysicalPath());
				}
				if (StringUtil.isNotEmpty(order.getRequestPath())) {
					condition.setRequestPath(order.getRequestPath());
				}
				if (order.getUploaderId() > 0) {
					condition.setUploaderId(order.getUploaderId());
				}
				if (StringUtil.isNotEmpty(order.getUploaderName())) {
					condition.setUploaderName(order.getUploaderName());
				}
				if (StringUtil.isNotEmpty(order.getUploaderAccount())) {
					condition.setUploaderAccount(order.getUploaderAccount());
				}
				if (order.getModuleType() != null
					&& CommonAttachmentTypeEnum.OTHER != order.getModuleType())
					condition.setModuleType(order.getModuleType().code());
				commonAttachmentDAO.update(condition);
				//更新附件所在模块
				if (StringUtil.isNotEmpty(condition.getBizNo())
					&& StringUtil.isNotEmpty(condition.getModuleType())
					&& StringUtil.isNotEmpty(condition.getFileName())
					&& StringUtil.isNotEmpty(condition.getRequestPath())
					&& StringUtil.isNotEmpty(condition.getFilePhysicalPath())) {
					String newAttach = condition.getFileName() + ","
										+ condition.getFilePhysicalPath() + ","
										+ condition.getRequestPath();//附件完整路径
					updateModuleAttachment(condition.getBizNo(), condition.getModuleType(),
						newAttach, oldAttach);
				}
				result.setSuccess(true);
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				result.setMessage(FcsResultEnum.EXECUTE_SUCCESS.getMessage());
			}
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
	
	private void updateModuleAttachment(String bizNO, String moduleType, String newAttach,
										String oldAttach) {
		try {
			if (StringUtil.isNotEmpty(moduleType) && StringUtil.isNotEmpty(bizNO)) {
				if (moduleType.equals(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION.code())) {//资金划付表单
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FCapitalAppropriationApplyDO DO = FCapitalAppropriationApplyDAO
						.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttach();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttach(doAttach);
						
						FCapitalAppropriationApplyDAO.update(DO);
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION_FINANCE_AFFIRM.code())) {//资金划付表单-财务确认
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", "")
						.replace("_CWQR", ""));//bizNo由该模块定义规则（唯一性）
					FFinanceAffirmDO DO = fFinanceAffirmDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttach();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttach(doAttach);
						fFinanceAffirmDAO.update(DO);
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION_FINANCE_AFFIRM_DETAIL
						.code())) {//资金划付表单-财务确认明细
					String[] idStr = bizNO.replace("PM_", "").replace("_CWQR", "").split("_");//bizNo由该模块定义规则（唯一性）
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long detailId = NumberUtil.parseLong(idStr[1]);
						FFinanceAffirmDO DO = fFinanceAffirmDAO.findByFormId(formId);
						if (DO != null && detailId > 0) {
							FFinanceAffirmDetailDO detailDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(DO.getAffirmId(), detailId);
							if (detailDO != null) {
								String doAttach = detailDO.getAttach();
								if (StringUtil.isNotEmpty(oldAttach)
									&& StringUtil.isNotEmpty(doAttach)) {
									if (doAttach.contains(oldAttach)) {
										doAttach = doAttach.replace(oldAttach, newAttach);
										if (doAttach.startsWith(";")) {
											doAttach = doAttach.substring(1, doAttach.length());
										}
										if ("".equals(doAttach)) {
											doAttach = null;
										}
									}
								}
								
								detailDO.setAttach(doAttach);
								fFinanceAffirmDetailDAO.update(detailDO);
							}
						}
					}
				}
				//退费申请单附件
				else if (moduleType.equals(CommonAttachmentTypeEnum.REFUND_APPLICATION.code())) {
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FRefundApplicationDO DO = FRefundApplicationDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttach();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttach(doAttach);
						FRefundApplicationDAO.update(DO);
					}
				}
				//承销与发债
				else if (moduleType.equals(CommonAttachmentTypeEnum.ISSUE_VOUCHER.code())) {//承销/发债信息维护-募集资金到账凭证
					long id = NumberUtil.parseLong(bizNO.replace("ISSUE_VOUCHER_", ""));//bizNo由该模块定义规则（唯一性）
					ProjectIssueInformationDO DO = projectIssueInformationDAO.findById(id);
					if (DO != null) {
						String doAttach = DO.getVoucherUrl();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setVoucherUrl(doAttach);
						projectIssueInformationDAO.update(DO);
					}
				} else if (moduleType.equals(CommonAttachmentTypeEnum.ISSUE_LETTER.code())) {//承销/发债信息维护-担保函
					long id = NumberUtil.parseLong(bizNO.replace("ISSUE_LETTER_", ""));//bizNo由该模块定义规则（唯一性）
					ProjectIssueInformationDO DO = projectIssueInformationDAO.findById(id);
					if (DO != null) {
						String doAttach = DO.getLetterUrl();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						DO.setLetterUrl(doAttach);
						projectIssueInformationDAO.update(DO);
					}
				}
				//合同
				else if (moduleType.equals(CommonAttachmentTypeEnum.CONTRACT_SCANURL.code())) {//合同-回传文件
					String[] idStr = bizNO.replace("PM_", "").replace("_HCWJ", "").split("_");//bizNo由该模块定义规则（唯一性）
					
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long id = NumberUtil.parseLong(idStr[1]);
						FProjectContractItemDO DO = fProjectContractItemDAO.findById(id);
						if (DO != null) {
							
							String doAttach = DO.getContractScanUrl();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							DO.setContractScanUrl(doAttach);
							fProjectContractItemDAO.update(DO);
						}
					}
				} else if (moduleType.equals(CommonAttachmentTypeEnum.CONTRACT_COURT_RULINGURL
					.code())) {//合同-法院裁定书
					String[] idStr = bizNO.replace("PM_", "").replace("_CDS", "").split("_");//bizNo由该模块定义规则（唯一性）
					
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long id = NumberUtil.parseLong(idStr[1]);
						FProjectContractItemDO DO = fProjectContractItemDAO.findById(id);
						if (DO != null) {
							String doAttach = DO.getCourtRulingUrl();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							DO.setCourtRulingUrl(doAttach);
							fProjectContractItemDAO.update(DO);
						}
					}
				} else if (moduleType.equals(CommonAttachmentTypeEnum.CONTRACT_REFER.code())) {//合同/文书/(函/通知书)-参考附件
					String[] idStr = bizNO.replace("PM_", "").replace("_CKFJ", "").split("_");//bizNo由该模块定义规则（唯一性）
					
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long id = NumberUtil.parseLong(idStr[1]);
						FProjectContractItemDO DO = fProjectContractItemDAO.findById(id);
						if (DO != null) {
							String doAttach = DO.getReferAttachment();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							DO.setReferAttachment(doAttach);
							fProjectContractItemDAO.update(DO);
						}
					}
				} else if (moduleType.equals(CommonAttachmentTypeEnum.CONTRACT_REFER_ATTACHMENT
					.code())) {//合同/文书/(函/通知书)-附件
					String[] idStr = bizNO.replace("PM_", "").replace("_HT", "").split("_");//bizNo由该模块定义规则（唯一性）
					
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long id = NumberUtil.parseLong(idStr[1]);
						FProjectContractItemDO DO = fProjectContractItemDAO.findById(id);
						if (DO != null) {
							String doAttach = DO.getFileUrl();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							DO.setFileUrl(doAttach);
							fProjectContractItemDAO.update(DO);
						}
					}
				}
				//征信
				else if (moduleType.equals(CommonAttachmentTypeEnum.CREDIT_REPORT.code())) {//征信报告
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", "")
						.replace("_ZXBG", ""));//bizNo由该模块定义规则（唯一性）
					FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getCreditReport();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setCreditReport(doAttach);
						fCreditRefrerenceApplyDAO.update(DO);
					}
				} else if (moduleType.equals(CommonAttachmentTypeEnum.CREDIT_REPORT_ATTACHMENT
					.code())) {//征信报告附件
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", "").replace("_ZXBGFJ",
						""));//bizNo由该模块定义规则（唯一性）
					FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttachment();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttachment(doAttach);
						fCreditRefrerenceApplyDAO.update(DO);
					}
				}
				//承销项目-到期通知列表
				else if (moduleType.equals(CommonAttachmentTypeEnum.EXPIRE_PROJECT_RECEIPT.code())) {//承销项目-到期通知列表还款凭证
					long id = NumberUtil.parseLong(bizNO.replace("PM_", "").replace("DQTZ", ""));//bizNo由该模块定义规则（唯一性）
					ExpireProjectDO DO = expireProjectDAO.findById(id);
					if (DO != null) {
						String doAttach = DO.getReceipt();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setReceipt(doAttach);
						expireProjectDAO.update(DO);
					}
				}
				//档案
				else if (moduleType.equals(CommonAttachmentTypeEnum.FILE_OUTPUT.code())) {//档案-出库附件
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", "")
						.replace("_DACK", ""));//bizNo由该模块定义规则（唯一性）
					FFileOutputDO DO = fileOutputDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttachment();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttachment(doAttach);
						fileOutputDAO.update(DO);
					}
				}
				//收费通知单
				else if (moduleType.equals(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION.code())) {//收费通知单-委托付款说明
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FChargeNotificationDO DO = fChargeNotificationDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttachment();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttachment(doAttach);
						fChargeNotificationDAO.update(DO);
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_FINANCE_AFFIRM.code())) {//收费通知单-财务确认附件
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", "")
						.replace("_CWQR", ""));//bizNo由该模块定义规则（唯一性）
					FFinanceAffirmDO DO = fFinanceAffirmDAO.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getAttach();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setAttach(doAttach);
						fFinanceAffirmDAO.update(DO);
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_FINANCE_AFFIRM_DETAIL
						.code())) {//收费通知单-财务确认明细附件
					String[] idStr = bizNO.replace("PM_", "").replace("_CWQR", "").split("_");//bizNo由该模块定义规则（唯一性）
					if (idStr != null && idStr.length == 2) {
						long formId = NumberUtil.parseLong(idStr[0]);
						long detailId = NumberUtil.parseLong(idStr[1]);
						FFinanceAffirmDO DO = fFinanceAffirmDAO.findByFormId(formId);
						if (DO != null && detailId > 0) {
							FFinanceAffirmDetailDO detailDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(DO.getAffirmId(), detailId);
							if (detailDO != null) {
								String doAttach = detailDO.getAttach();
								if (StringUtil.isNotEmpty(oldAttach)
									&& StringUtil.isNotEmpty(doAttach)) {
									if (doAttach.contains(oldAttach)) {
										doAttach = doAttach.replace(oldAttach, newAttach);
										if (doAttach.startsWith(";")) {
											doAttach = doAttach.substring(1, doAttach.length());
										}
										if ("".equals(doAttach)) {
											doAttach = null;
										}
									}
								}
								
								detailDO.setAttach(doAttach);
								fFinanceAffirmDetailDAO.update(detailDO);
							}
						}
					}
				}
				//项目立项
				else if (moduleType.equals(CommonAttachmentTypeEnum.PROJECT_SETUP.code())) {//立项-客户申请书
					long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FProjectCustomerBaseInfoDO DO = FProjectCustomerBaseInfoDAO
						.findByFormId(formId);
					if (DO != null) {
						String doAttach = DO.getApplyScanningUrl();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							}
						}
						
						DO.setApplyScanningUrl(doAttach);
						FProjectCustomerBaseInfoDAO.update(DO);
					}
				}
				//放用款
				else if (moduleType
					.equals(CommonAttachmentTypeEnum.LOAN_USE_RECEIPT_VOUCHER.code())) {//放用款回执-放款凭证
				
					String[] strArr = bizNO.split("_");
					if (strArr != null && strArr.length > 2) {
						
						long detailId = NumberUtil.parseLong(strArr[2]);
						FLoanUseApplyReceiptDO fLoanUseApplyReceiptDO = FLoanUseApplyReceiptDAO
							.findById(detailId);
						if (fLoanUseApplyReceiptDO != null) {
							String doAttach = fLoanUseApplyReceiptDO.getVoucherUrl();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							fLoanUseApplyReceiptDO.setVoucherUrl(doAttach);
							FLoanUseApplyReceiptDAO.update(fLoanUseApplyReceiptDO);
						}
					} else {
						long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
						FLoanUseApplyDO DO = FLoanUseApplyDAO.findByFormId(formId);
						if (DO != null) {
							List<FLoanUseApplyReceiptDO> listDO = FLoanUseApplyReceiptDAO
								.findByApplyId(DO.getApplyId());
							if (ListUtil.isNotEmpty(listDO)) {
								for (FLoanUseApplyReceiptDO fLoanUseApplyReceiptDO : listDO) {
									
									String doAttach = fLoanUseApplyReceiptDO.getVoucherUrl();
									if (StringUtil.isNotEmpty(oldAttach)
										&& StringUtil.isNotEmpty(doAttach)) {
										if (doAttach.contains(oldAttach)) {
											doAttach = doAttach.replace(oldAttach, newAttach);
											if (doAttach.startsWith(";")) {
												doAttach = doAttach.substring(1, doAttach.length());
											}
											if ("".equals(doAttach)) {
												doAttach = null;
											}
										}
									}
									
									fLoanUseApplyReceiptDO.setVoucherUrl(doAttach);
									FLoanUseApplyReceiptDAO.update(fLoanUseApplyReceiptDO);
								}
							}
						}
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.LOAN_USE_RECEIPT_OTHER.code())) {//放用款回执-其他
				
					String[] strArr = bizNO.split("_");
					if (strArr != null && strArr.length > 2) {
						
						long detailId = NumberUtil.parseLong(strArr[2]);
						FLoanUseApplyReceiptDO fLoanUseApplyReceiptDO = FLoanUseApplyReceiptDAO
							.findById(detailId);
						if (fLoanUseApplyReceiptDO != null) {
							String doAttach = fLoanUseApplyReceiptDO.getOtherUrl();
							if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
								if (doAttach.contains(oldAttach)) {
									doAttach = doAttach.replace(oldAttach, newAttach);
									if (doAttach.startsWith(";")) {
										doAttach = doAttach.substring(1, doAttach.length());
									}
									if ("".equals(doAttach)) {
										doAttach = null;
									}
								}
							}
							
							fLoanUseApplyReceiptDO.setOtherUrl(doAttach);
							FLoanUseApplyReceiptDAO.update(fLoanUseApplyReceiptDO);
						}
					} else {
						long formId = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
						FLoanUseApplyDO DO = FLoanUseApplyDAO.findByFormId(formId);
						if (DO != null) {
							List<FLoanUseApplyReceiptDO> listDO = FLoanUseApplyReceiptDAO
								.findByApplyId(DO.getApplyId());
							if (ListUtil.isNotEmpty(listDO)) {
								for (FLoanUseApplyReceiptDO fLoanUseApplyReceiptDO : listDO) {
									
									String doAttach = fLoanUseApplyReceiptDO.getOtherUrl();
									if (StringUtil.isNotEmpty(oldAttach)
										&& StringUtil.isNotEmpty(doAttach)) {
										if (doAttach.contains(oldAttach)) {
											doAttach = doAttach.replace(oldAttach, newAttach);
											if (doAttach.startsWith(";")) {
												doAttach = doAttach.substring(1, doAttach.length());
											}
											if ("".equals(doAttach)) {
												doAttach = null;
											}
										}
									}
									
									fLoanUseApplyReceiptDO.setOtherUrl(doAttach);
									FLoanUseApplyReceiptDAO.update(fLoanUseApplyReceiptDO);
								}
							}
						}
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_CONTRACT_AGREEMENT
						.code())) {//授信落实附件-合同/协议
					long id = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FCreditConditionConfirmItemDO DO = FCreditConditionConfirmItemDAO.findById(id);
					if (DO != null) {
						String doAttach = DO.getContractAgreementUrl();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							} else {
								DO.setContractAgreementUrl(newAttach);
							}
						}
						DO.setContractAgreementUrl(doAttach);
						FCreditConditionConfirmItemDAO.update(DO);
					}
				} else if (moduleType
					.equals(CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_ATTACHMENT.code())) {//授信落实附件-普通附件
					long id = NumberUtil.parseLong(bizNO.replace("PM_", ""));//bizNo由该模块定义规则（唯一性）
					FCreditConditionConfirmItemDO DO = FCreditConditionConfirmItemDAO.findById(id);
					if (DO != null) {
						String doAttach = DO.getRightVouche();
						if (StringUtil.isNotEmpty(oldAttach) && StringUtil.isNotEmpty(doAttach)) {
							if (doAttach.contains(oldAttach)) {
								doAttach = doAttach.replace(oldAttach, newAttach);
								if (doAttach.startsWith(";")) {
									doAttach = doAttach.substring(1, doAttach.length());
								}
								if ("".equals(doAttach)) {
									doAttach = null;
								}
							} else {
								DO.setRightVouche(newAttach);
							}
						}
						DO.setRightVouche(doAttach);
						FCreditConditionConfirmItemDAO.update(DO);
					}
				}
				//还有其它模块的
			}
		} catch (Exception e) {
			logger.error("删除/更新" + CommonAttachmentTypeEnum.getByCode(moduleType).message()
							+ "模块附件失败,{}", e.getMessage(), e);
		}
	}
	
	@Override
	public List<AttachmentModuleType> queryAttachment(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = queryCommonAttachment(order);
		if (null == result || !result.isSuccess() || ListUtil.isEmpty(result.getPageList())) {
			return null;
		}
		
		Map<CommonAttachmentTypeEnum, List<CommonAttachmentInfo>> map = new HashMap<>();
		for (CommonAttachmentInfo attach : result.getPageList()) {
			List<CommonAttachmentInfo> list = map.get(attach.getModuleType());
			if (null == list) {
				list = new ArrayList<>();
				map.put(attach.getModuleType(), list);
			}
			list.add(attach);
		}
		
		List<AttachmentModuleType> types = new ArrayList<>(map.size());
		for (Map.Entry<CommonAttachmentTypeEnum, List<CommonAttachmentInfo>> m : map.entrySet()) {
			AttachmentModuleType type = new AttachmentModuleType();
			type.setModuleType(m.getKey());
			type.setAttachmentInfos(m.getValue());
			types.add(type);
		}
		
		return types;
	}
	
}