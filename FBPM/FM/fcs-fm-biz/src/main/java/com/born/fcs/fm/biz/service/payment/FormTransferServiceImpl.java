package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.FormDO;
import com.born.fcs.fm.dal.dataobject.FormTransferDO;
import com.born.fcs.fm.dal.dataobject.FormTransferDetailDO;
import com.born.fcs.fm.dal.queryCondition.FormTransferQueryCondition;
import com.born.fcs.fm.dataobject.FormTransferFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.payment.FormTransferDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferDetailOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.bornsoft.pub.order.kingdee.KingdeeTernalTransOrder;
import com.bornsoft.pub.order.kingdee.KingdeeTernalTransOrder.TernalTransInfo;
import com.bornsoft.pub.result.kingdee.KingdeeTernalTransResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("formTransferService")
public class FormTransferServiceImpl extends BaseFormAutowiredDomainService implements
																			FormTransferService {
	
	@Autowired
	private KingdeeTogetheFacadeClient kingdeeTogetheFacadeClient;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Override
	public QueryBaseBatchResult<FormTransferInfo> queryPage(FormTransferQueryOrder order) {
		QueryBaseBatchResult<FormTransferInfo> batchResult = new QueryBaseBatchResult<FormTransferInfo>();
		
		try {
			FormTransferQueryCondition condition = getQueryCondition(order);
			//			long totalCount = formTransferDAO.findByConditionCount(condition);
			//			PageComponent component = new PageComponent(order, totalCount);
			//
			//			List<FormTransferDO> list = 
			//					formTransferDAO.findByCondition(condition);
			//
			//			List<FormTransferInfo> pageList = new ArrayList<FormTransferInfo>(list.size());
			//			for (FormTransferDO DO : list) {
			//				FormTransferInfo info = new FormTransferInfo();
			//				setFormTransferInfo(DO, info, order.isDetail());
			//				pageList.add(info);
			//			}
			
			long totalCount = busiDAO.findTransferByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormTransferFormDO> list = busiDAO.findTransferByCondition(condition);
			
			List<FormTransferInfo> pageList = new ArrayList<FormTransferInfo>(list.size());
			for (FormTransferFormDO DO : list) {
				FormTransferInfo info = new FormTransferInfo();
				setFormTransferInfo(DO, info, order.isDetail());
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询转账申请单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FormTransferInfo queryByFormId(long formId) {
		FormTransferDO formTransferDO = formTransferDAO.findByFormId(formId);
		if (formTransferDO != null) {
			FormTransferInfo formTransferInfo = new FormTransferInfo();
			setFormTransferInfo(formTransferDO, formTransferInfo, true);
			return formTransferInfo;
		}
		return null;
	}
	
	@Override
	public FormTransferInfo queryById(long transferId) {
		FormTransferDO formTransferDO = formTransferDAO.findById(transferId);
		if (formTransferDO != null) {
			FormTransferInfo formTransferInfo = new FormTransferInfo();
			setFormTransferInfo(formTransferDO, formTransferInfo, true);
			return formTransferInfo;
		}
		return null;
	}
	
	@Override
	public FormBaseResult save(final FormTransferOrder order) {
		return commonFormSaveProcess(order, "转账申请单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				if (order.getDetailList() == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"转账申请单明细不存在");
				}
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				
				FormTransferDO formTransfer = formTransferDAO.findByFormId(form.getFormId());
				if (formTransfer == null) {//新增
					if (order.getFormId() != null && order.getFormId() > 0) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
					}
					formTransfer = new FormTransferDO();
					BeanCopier.staticCopy(order, formTransfer, UnBoxingConverter.getInstance());
					formTransfer.setDeptName(order.getTransferDeptName());
					formTransfer.setFormId(form.getFormId());
					formTransfer.setBillNo(genBillNo());
					formTransfer.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
					formTransfer.setRawAddTime(now);
					long transferId = formTransferDAO.insert(formTransfer);
					FormTransferInfo info = new FormTransferInfo();
					info.setTransferId(transferId);
					info.setBillNo(formTransfer.getBillNo());
					result.setReturnObject(info);
					
					List<FormTransferDetailOrder> detailOrders = order.getDetailList();
					for (FormTransferDetailOrder detailOrder : detailOrders) {
						FormTransferDetailDO detailDO = new FormTransferDetailDO();
						BeanCopier.staticCopy(detailOrder, detailDO,
							UnBoxingConverter.getInstance());
						detailDO.setTransferId(transferId);
						detailDO.setRawAddTime(now);
						formTransferDetailDAO.insert(detailDO);
					}
					result.setMessage(formTransfer.getBillNo());
				} else {//更新
					long transferId = formTransfer.getTransferId();
					formTransfer.setTransferDeptId(order.getTransferDeptId());
					formTransfer.setDeptName(order.getTransferDeptName());
					formTransfer.setDeptHead(order.getDeptHead());
					formTransfer.setApplicationTime(order.getApplicationTime());
					formTransfer.setAgentId(order.getAgentId());
					formTransfer.setAgent(order.getAgent());
					formTransfer.setReasons(order.getReasons());
					formTransfer.setBankId(order.getBankId());
					formTransfer.setBankName(order.getBankName());
					formTransfer.setBankAccount(order.getBankAccount());
					formTransfer.setAmount(order.getAmount());
					formTransfer.setAttachmentsNum(order.getAttachmentsNum());
					formTransferDAO.update(formTransfer);
					FormTransferInfo info = new FormTransferInfo();
					info.setTransferId(transferId);
					info.setBillNo(formTransfer.getBillNo());
					result.setReturnObject(info);
					
					List<FormTransferDetailDO> oleDetailDOs = formTransferDetailDAO
						.findByTransferId(transferId);
					List<FormTransferDetailOrder> detailOrders = order.getDetailList();
					for (FormTransferDetailOrder detailOrder : detailOrders) {
						if (detailOrder.getDetailId() == 0) {
							//新增明细
							FormTransferDetailDO detailDO = new FormTransferDetailDO();
							BeanCopier.staticCopy(detailOrder, detailDO,
								UnBoxingConverter.getInstance());
							detailDO.setTransferId(transferId);
							detailDO.setRawAddTime(now);
							formTransferDetailDAO.insert(detailDO);
						} else {
							//更新明细
							for (int i = 0; i < oleDetailDOs.size(); i++) {
								FormTransferDetailDO updateDetailDO = oleDetailDOs.get(i);
								if (detailOrder.getDetailId() == updateDetailDO.getDetailId()) {
									updateDetailDO.setBankId(detailOrder.getBankId());
									updateDetailDO.setBankName(detailOrder.getBankName());
									updateDetailDO.setBankAccount(detailOrder.getBankAccount());
									updateDetailDO.setAmount(detailOrder.getAmount());
									formTransferDetailDAO.update(updateDetailDO);
									
									//移除已更新的数据，剩下需要删除的数据
									oleDetailDOs.remove(i);
									break;
								}
							}
						}
						
					}
					
					//删除明细
					for (FormTransferDetailDO deletDo : oleDetailDOs) {
						formTransferDetailDAO.deleteById(deletDo.getDetailId());
					}
					
					result.setMessage(formTransfer.getBillNo());
				}
				
				//保存一下表单的相关人员
				FormRelatedUserOrder relatedUser = new FormRelatedUserOrder();
				relatedUser.setFormId(form.getFormId());
				relatedUser.setFormCode(form.getFormCode());
				relatedUser.setTaskId(0);
				relatedUser.setUserType(FormRelatedUserTypeEnum.OTHER);
				relatedUser.setRemark("【" + form.getFormName() + "】" + "表单暂存人");
				relatedUser.setUserId(form.getUserId());
				relatedUser.setUserAccount(form.getUserAccount());
				relatedUser.setUserName(form.getUserName());
				relatedUser.setUserMobile(form.getUserMobile());
				relatedUser.setUserEmail(form.getUserEmail());
				relatedUser.setExeStatus(ExeStatusEnum.NOT_TASK);
				formRelatedUserService.setRelatedUser(relatedUser);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			@Override
			public Domain after(Domain domain) {
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormTransferInfo transferInfo) {
		FcsBaseResult result = createResult();
		try {
			
			logger.info("转账申请单同步至财务系统开始  ,receiptInfo : {}", transferInfo);
			if (transferInfo == null) {
				result.setSuccess(false);
				result.setMessage("内部转账单不存在");
				return result;
			}
			
			FormDO formDO = formDAO.findByFormId(transferInfo.getFormId());
			if (formDO == null) {
				result.setSuccess(false);
				result.setMessage("转账申请单不存在");
				return result;
			}
			
			//经办人
			UserDetailInfo dealMan = bpmUserQueryService.findUserDetailByUserId(transferInfo
				.getAgentId());
			KingdeeTernalTransOrder order = new KingdeeTernalTransOrder();
			order.setBizNo(transferInfo.getBillNo());
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			order.setDealMan(String.valueOf(dealMan.getId()));
			order.setDealManName(dealMan.getName());
			order.setDealManDeptCode(dealMan.getPrimaryOrg().getCode());
			
			BankMessageResult bankResult = bankMessageService.findByAccount(transferInfo
				.getBankAccount());
			BankMessageInfo inBank = bankResult.getBankMessageInfo();
			List<FormTransferDetailInfo> ds = transferInfo.getDetailList();
			if (ds != null) {
				List<TernalTransInfo> transDetails = new ArrayList<TernalTransInfo>(ds.size());
				for (FormTransferDetailInfo dInfo : ds) {
					TernalTransInfo ternalTransInfo = new TernalTransInfo();
					ternalTransInfo.setSkAccount(transferInfo.getBankAccount());
					ternalTransInfo.setFkAccount(dInfo.getBankAccount());
					ternalTransInfo.setAmount(dInfo.getAmount().toString());
					if (inBank != null)
						ternalTransInfo.setDebit(inBank.getAtCode());//借方科目
					bankResult = bankMessageService.findByAccount(dInfo.getBankAccount());
					BankMessageInfo outBank = bankResult.getBankMessageInfo();
					if (outBank != null)
						ternalTransInfo.setCredit(outBank.getAtCode());//贷方科目
					transDetails.add(ternalTransInfo);
				}
				order.setTransDetail(transDetails);
			}
			
			SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
			syncBasicOrder.setDept(dealMan.getPrimaryOrg());
			kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
			
			KingdeeTernalTransResult kingdeeResult = kingdeeTogetheFacadeClient.ternalTrans(order);
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(transferInfo.getBillNo());
			updateOrder.setVoucherSyncSendTime(getSysdate());
			if (kingdeeResult != null
				&& CommonResultEnum.EXECUTE_SUCCESS == kingdeeResult.getResultCode()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_SUCCESS);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
			}
			logger.info("转账申请单同步金蝶结果:", kingdeeResult);
			updateVoucher(updateOrder);
			
			result.setSuccess(true);
			result.setMessage("同步成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步出错");
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			try {
				updateOrder.setBillNo(transferInfo.getBillNo());
				updateOrder.setVoucherSyncSendTime(getSysdate());
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
				updateVoucher(updateOrder);
			} catch (Exception e1) {
				logger.error("转账申请单同步到财务系统出错后,更定状态为发送失败，失败！+order=" + updateOrder, e1);
			}
			logger.error("转账申请单同步到财务系统出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return commonProcess(order, "更新凭证号同步状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormTransferDO transferDO = formTransferDAO.findByBillNo(order.getBillNo());
				
				if (transferDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "转账申请单不存在");
				}
				
				//同步状态
				if (order.getVoucherStatus() != null) {
					if (order.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE
						&& StringUtil.equals(order.getVoucherNo(), transferDO.getVoucherNo())) {
						transferDO.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE.code());
					} else if (order.getVoucherStatus() != VoucherStatusEnum.SYNC_DELETE) {
						transferDO.setVoucherStatus(order.getVoucherStatus().code());
					}
				}
				
				//凭证号
				transferDO.setVoucherNo(order.getVoucherNo());
				
				//同步发送时间
				if (order.getVoucherSyncSendTime() != null)
					transferDO.setVoucherSyncSendTime(order.getVoucherSyncSendTime());
				//同步完成时间
				if (order.getVoucherSyncFinishTime() != null)
					transferDO.setVoucherSyncFinishTime(order.getVoucherSyncFinishTime());
				
				formTransferDAO.update(transferDO);
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 生成单据号
	 * @param busiType
	 * @return
	 */
	private String genBillNo() {
		//20161226、转账申请单 单据号更改：字母（缩写）+6位流水号；
		FormTransferQueryCondition condition = new FormTransferQueryCondition();
		condition.setBillNo("ZZ" + "%");
		long count = formTransferDAO.findByConditionCount(condition);
		String billNo = "ZZ" + StringUtil.alignRight((count + 1) + "", 6, '0');
		return billNo;
	}
	
	@SuppressWarnings("deprecation")
	private FormTransferQueryCondition getQueryCondition(FormTransferQueryOrder order) {
		FormTransferQueryCondition condition = new FormTransferQueryCondition();
		if (order != null) {
			BeanCopier.staticCopy(order, condition, UnBoxingConverter.getInstance());
			if (StringUtil.isNotBlank(order.getBillNo()))
				condition.setBillNo("%" + order.getBillNo() + "%");
			if (order.getProcess() != null)
				condition.setProcess(order.getProcess().code());
			if (order.getAmountStart() != null)
				condition.setAmountStart(order.getAmountStart().getCent());
			if (order.getAmountEnd() != null)
				condition.setAmountEnd(order.getAmountEnd().getCent());
			if (order.getFormStatusList() != null) {
				condition
					.setFormStatusList(new ArrayList<String>(order.getFormStatusList().size()));
				for (FormStatusEnum status : order.getFormStatusList()) {
					condition.getFormStatusList().add(status.code());
				}
			}
			if (order.getExcFormStatusList() != null) {
				condition.setExcFormStatusList(new ArrayList<String>(order.getExcFormStatusList()
					.size()));
				for (FormStatusEnum status : order.getExcFormStatusList()) {
					condition.getExcFormStatusList().add(status.code());
				}
			}
			condition.setSearchUserIdList(order.getSearchUserIdList());
		}
		return condition;
	}
	
	@SuppressWarnings("deprecation")
	private void setFormTransferInfo(FormTransferDO DO, FormTransferInfo info, boolean isDetail) {
		
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
		FormDO formDO = formDAO.findByFormId(DO.getFormId());
		if (StringUtil.isNotBlank(formDO.getStatus()))
			info.setFormStatus(FormStatusEnum.getByCode(formDO.getStatus()));
		info.setFormDeptName(formDO.getDeptName());
		info.setFormUserName(formDO.getUserName());
		info.setFormUserId(formDO.getUserId());
		info.setActDefId(formDO.getActDefId());
		info.setActInstId(formDO.getActInstId());
		info.setFormUrl(formDO.getFormUrl());
		info.setRunId(formDO.getRunId());
		info.setTaskId(formDO.getTaskId());
		info.setTaskUserData(formDO.getTaskUserData());
		info.setRawUpdateTime(formDO.getRawUpdateTime());
		
		if (isDetail) {
			List<FormTransferDetailDO> detailDOs = formTransferDetailDAO.findByTransferId(DO
				.getTransferId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormTransferDetailInfo> detailList = new ArrayList<FormTransferDetailInfo>(
					detailDOs.size());
				for (FormTransferDetailDO detailDO : detailDOs) {
					FormTransferDetailInfo detailInfo = new FormTransferDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setFormTransferInfo(FormTransferFormDO DO, FormTransferInfo info, boolean isDetail) {
		
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
		FormDO formDO = formDAO.findByFormId(DO.getFormId());
		if (StringUtil.isNotBlank(formDO.getStatus()))
			info.setFormStatus(FormStatusEnum.getByCode(formDO.getStatus()));
		info.setFormDeptName(formDO.getDeptName());
		info.setFormUserName(formDO.getUserName());
		info.setFormUserId(formDO.getUserId());
		info.setActDefId(formDO.getActDefId());
		info.setActInstId(formDO.getActInstId());
		info.setFormUrl(formDO.getFormUrl());
		info.setRunId(formDO.getRunId());
		info.setTaskId(formDO.getTaskId());
		info.setTaskUserData(formDO.getTaskUserData());
		info.setRawUpdateTime(formDO.getRawUpdateTime());
		
		if (isDetail) {
			List<FormTransferDetailDO> detailDOs = formTransferDetailDAO.findByTransferId(DO
				.getTransferId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormTransferDetailInfo> detailList = new ArrayList<FormTransferDetailInfo>(
					detailDOs.size());
				for (FormTransferDetailDO detailDO : detailDOs) {
					FormTransferDetailInfo detailInfo = new FormTransferDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
		}
	}
	
}
