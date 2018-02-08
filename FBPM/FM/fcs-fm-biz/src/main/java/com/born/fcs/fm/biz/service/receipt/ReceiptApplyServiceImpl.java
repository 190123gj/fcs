package com.born.fcs.fm.biz.service.receipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.BankMessageDO;
import com.born.fcs.fm.dal.dataobject.FormReceiptDO;
import com.born.fcs.fm.dal.dataobject.FormReceiptFeeDO;
import com.born.fcs.fm.dataobject.FormReceiptFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.pm.service.FinancialProjectServiceClient;
import com.born.fcs.fm.integration.pm.service.ProjectServiceClient;
import com.born.fcs.fm.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormFeeInfo;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptFeeOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.brokerbusiness.BrokerBusinessFormInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.brokerbusiness.BrokerBusinessQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.brokerbusiness.BrokerBusinessService;
import com.bornsoft.pub.order.kingdee.KingdeeGatheringOrder;
import com.bornsoft.pub.order.kingdee.KingdeeGatheringOrder.GatheringInfo;
import com.bornsoft.pub.result.kingdee.KingdeeGatheringResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("receiptApplyService")
public class ReceiptApplyServiceImpl extends BaseFormAutowiredDomainService implements
																			ReceiptApplyService {
	
	@Autowired
	DateSeqService dateSeqService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormService;
	
	@Autowired
	private KingdeeTogetheFacadeClient kingdeeTogetheFacadeClient;
	
	@Autowired
	private ProjectServiceClient projectServiceClient;
	
	@Autowired
	private FinancialProjectServiceClient financialProjectServiceClient;
	
	@Autowired
	BrokerBusinessService brokerBusinessServiceClient;
	
	/**
	 * 生成单据号
	 */
	private String genBillNo() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//序列号 每年重置
		String seqName = SysDateSeqNameEnum.FORM_RECEIPT_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		return "SK" + sdf.format(calendar.getTime())
				+ StringUtil.leftPad(String.valueOf(seq), 4, "0");
	}
	
	@Override
	public FormBaseResult saveApply(final FormReceiptOrder order) {
		return commonFormSaveProcess(order, "保存收款单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				boolean isUpdate = false;
				FormReceiptDO receipt = formReceiptDAO.findByFormId(form.getFormId());
				if (receipt == null) {
					receipt = new FormReceiptDO();
					BeanCopier.staticCopy(order, receipt, UnBoxingConverter.getInstance());
					receipt.setFormId(form.getFormId());
					receipt.setBillNo(genBillNo());
					receipt.setFormSource(order.getFormSource().code());
					receipt.setRawAddTime(now);
				} else {
					BeanCopier.staticCopy(order, receipt, UnBoxingConverter.getInstance());
					receipt.setFormSource(order.getFormSource().code());
					isUpdate = true;
				}
				
				//收款金额
				Money totalAmount = Money.zero();
				for (FormReceiptFeeOrder feeOrder : order.getFeeOrder()) {
					totalAmount.addTo(feeOrder.getAmount());
				}
				
				//收款来源单据
				if (order.getFormSource() == FormSourceEnum.FORM) {
					
					//查询待处理的单据
					ReceiptPaymentFormInfo rpInfo = receiptPaymentFormService.queryBySourceFormId(
						order.getSourceForm(), order.getSourceFormId(), false);
					if (rpInfo == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"来源单据不存在");
					}
					
					if (!isUpdate && rpInfo.getStatus() != ReceiptPaymentFormStatusEnum.NOT_PROCESS) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "单据已处理");
					}
					
					//复制单据内容
					receipt.setAmount(rpInfo.getAmount());
					receipt.setApplyDeptCode(rpInfo.getDeptCode());
					receipt.setApplyDeptId(rpInfo.getDeptId());
					receipt.setApplyDeptName(rpInfo.getDeptName());
					receipt.setApplyUserId(rpInfo.getUserId());
					receipt.setApplyUserAccount(rpInfo.getUserAccount());
					receipt.setApplyUserName(rpInfo.getUserName());
					receipt.setCustomerId(rpInfo.getCustomerId());
					receipt.setCustomerName(rpInfo.getCustomerName());
					receipt.setSourceForm(rpInfo.getSourceForm().code());
					receipt.setSourceFormId(rpInfo.getSourceFormId());
					receipt.setSourceFormSys(rpInfo.getSourceFormSys().code());
					receipt.setProjectCode(rpInfo.getProjectCode());
					receipt.setProjectName(rpInfo.getProjectName());
					receipt.setContractNo(rpInfo.getContractNo());
					receipt.setContractName(rpInfo.getContractName());
					
					//验证收款金额
					if (!totalAmount.equals(rpInfo.getAmount())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "收款明细总额必须等于收款总额");
					}
					
					//更新单据状态未处理中
					UpdateReceiptPaymentFormStatusOrder uOrder = new UpdateReceiptPaymentFormStatusOrder();
					uOrder.setSourceForm(order.getSourceForm());
					uOrder.setSourceFormId(order.getSourceFormId());
					uOrder.setStatus(ReceiptPaymentFormStatusEnum.IN_PROCESS);
					receiptPaymentFormService.updateStatus(uOrder);
					
				} else {
					receipt.setAmount(totalAmount);
				}
				
				//凭证号状态未 未发送
				receipt.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
				
				if (isUpdate) {
					formReceiptDAO.updateByFormId(receipt);
				} else {
					formReceiptDAO.insert(receipt);
				}
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				FormReceiptInfo info = new FormReceiptInfo();
				info.setBillNo(receipt.getBillNo());
				result.setReturnObject(info);
				
				if (isUpdate) {
					
					//查询已经存在的费用明细
					List<FormReceiptFeeDO> existFeeList = formReceiptFeeDAO.findByFormId(form
						.getFormId());
					Map<Long, FormReceiptFeeDO> feeMap = Maps.newHashMap();
					if (ListUtil.isNotEmpty(existFeeList)) {
						for (FormReceiptFeeDO feeDO : existFeeList) {
							feeMap.put(feeDO.getId(), feeDO);
						}
					}
					
					for (FormReceiptFeeOrder feeOrder : order.getFeeOrder()) {
						FormReceiptFeeDO feeDO = feeMap.get(feeOrder.getId());
						//新增不存在的
						if (feeDO == null) {
							feeDO = new FormReceiptFeeDO();
							BeanCopier.staticCopy(feeOrder, feeDO);
							feeDO.setAmount(feeOrder.getAmount());
							feeDO.setFormId(form.getFormId());
							feeDO.setFeeType(feeOrder.getFeeType().code());
							feeDO.setRawAddTime(now);
							formReceiptFeeDAO.insert(feeDO);
						} else {
							//更新现有的
							if (!isEqual(feeOrder, feeDO)) {
								BeanCopier.staticCopy(feeOrder, feeDO);
								feeDO.setAmount(feeOrder.getAmount());
								feeDO.setId(feeOrder.getId());
								feeDO.setFeeType(feeOrder.getFeeType().code());
								feeDO.setFormId(form.getFormId());
								formReceiptFeeDAO.update(feeDO);
							}
							feeMap.remove(feeOrder.getId());
						}
					}
					//删掉多余的
					for (long id : feeMap.keySet()) {
						formReceiptFeeDAO.deleteById(id);
					}
				} else {//直接新增
					for (FormReceiptFeeOrder feeOrder : order.getFeeOrder()) {
						FormReceiptFeeDO feeDO = new FormReceiptFeeDO();
						BeanCopier.staticCopy(feeOrder, feeDO);
						feeDO.setAmount(feeOrder.getAmount());
						feeDO.setFormId(form.getFormId());
						feeDO.setFeeType(feeOrder.getFeeType().code());
						feeDO.setRawAddTime(now);
						formReceiptFeeDAO.insert(feeDO);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 是否是相同的费用明细
	 * @param feeOrder
	 * @param feeDO
	 * @return
	 */
	private boolean isEqual(FormReceiptFeeOrder feeOrder, FormReceiptFeeDO feeDO) {
		return feeOrder.getAmount().equals(feeDO.getAmount())
				&& StringUtil.equals(feeOrder.getAtCode(), feeDO.getAtCode())
				&& StringUtil.equals(feeOrder.getAtName(), feeDO.getAtName())
				&& StringUtil.equals(feeOrder.getAccount(), feeDO.getAccount())
				&& StringUtil.equals(feeOrder.getRemark(), feeDO.getRemark())
				&& feeOrder.getReceiptDate() == feeDO.getReceiptDate()
				&& StringUtil.equals(feeOrder.getFeeType().code(), feeDO.getFeeType());
	}
	
	@Override
	public QueryBaseBatchResult<FormReceiptFormInfo> searchForm(FormReceiptQueryOrder order) {
		QueryBaseBatchResult<FormReceiptFormInfo> batchResult = new QueryBaseBatchResult<FormReceiptFormInfo>();
		try {
			FormReceiptFormDO receiptForm = new FormReceiptFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, receiptForm);
			long totalCount = busiDAO.searchReceiptFormCount(receiptForm);
			PageComponent component = new PageComponent(order, totalCount);
			List<FormReceiptFormDO> dataList = busiDAO.searchReceiptForm(receiptForm);
			
			List<FormReceiptFormInfo> list = Lists.newArrayList();
			for (FormReceiptFormDO DO : dataList) {
				FormReceiptFormInfo info = new FormReceiptFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
				info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
				info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
				info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
				if (order.isQueryFeeDetail()) {
					info.setFeeList(findReceiptFeeByFormId(info.getFormId()));
				}
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收款单失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FormReceiptInfo findReceiptByFormId(long formId, boolean queryFeeDetail) {
		FormReceiptDO DO = formReceiptDAO.findByFormId(formId);
		if (DO != null) {
			FormReceiptInfo info = new FormReceiptInfo();
			BeanCopier.staticCopy(DO, info);
			info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
			info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
			info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
			if (queryFeeDetail) {
				info.setFeeList(findReceiptFeeByFormId(info.getFormId()));
			}
			return info;
		}
		return null;
	}
	
	@Override
	public FormReceiptInfo findReceiptByBillNo(String billNo, boolean queryFeeDetail) {
		FormReceiptDO DO = formReceiptDAO.findByBillNo(billNo);
		if (DO != null) {
			FormReceiptInfo info = new FormReceiptInfo();
			BeanCopier.staticCopy(DO, info);
			info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
			info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
			info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
			if (queryFeeDetail) {
				info.setFeeList(findReceiptFeeByFormId(info.getFormId()));
			}
			return info;
		}
		return null;
	}
	
	@Override
	public List<FormReceiptFeeInfo> findReceiptFeeByFormId(long formId) {
		List<FormReceiptFeeDO> feeList = formReceiptFeeDAO.findByFormId(formId);
		List<FormReceiptFeeInfo> list = Lists.newArrayList();
		if (ListUtil.isNotEmpty(feeList)) {
			for (FormReceiptFeeDO DO : feeList) {
				FormReceiptFeeInfo info = new FormReceiptFeeInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFeeType(SubjectCostTypeEnum.getByCode(DO.getFeeType()));
				list.add(info);
			}
		}
		return list;
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormReceiptInfo receiptInfo) {
		FcsBaseResult result = createResult();
		try {
			
			logger.info("收款单同步至财务系统开始  ,receiptInfo : {}", receiptInfo);
			if (receiptInfo == null) {
				result.setSuccess(false);
				result.setMessage("收款单不存在");
				return result;
			}
			
			//XXX 同步传输数据到财务系统 根据结果改变同步状态
			List<FormReceiptFeeInfo> feeList = receiptInfo.getFeeList();
			if (ListUtil.isEmpty(feeList)) {
				feeList = findReceiptFeeByFormId(receiptInfo.getFormId());
			}
			
			KingdeeGatheringOrder order = new KingdeeGatheringOrder();
			//同步基础数据
			SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
			if (FormSourceEnum.FORM == receiptInfo.getFormSource()) {
				if (SourceFormEnum.INNER_LOAN_IN == receiptInfo.getSourceForm()) {
					
				} else if (SourceFormEnum.FINANCIAL_TRANSFER == receiptInfo.getSourceForm()
							|| SourceFormEnum.FINANCIAL_SETTLEMENT == receiptInfo.getSourceForm()
							|| SourceFormEnum.FINANCIAL_REDEEM_EXPIRE == receiptInfo
								.getSourceForm()
							|| SourceFormEnum.FINANCIAL_REDEEM == receiptInfo.getSourceForm()) {
					// 理财
					ProjectFinancialInfo project = financialProjectServiceClient
						.queryByCode(receiptInfo.getProjectCode());
					if (project != null) {
						order.setBusiType(receiptInfo.getSourceForm().message());
						//TODO						
						//						syncBasicOrder.setUserId(project.getCreateUserId());
						//						syncBasicOrder.setUserName(project.getCreateUserName());
						if (project.getCreateUserId() > 0
							&& StringUtil.isNotBlank(project.getCreateUserName())) {
							order.setBusiManager(String.valueOf(project.getCreateUserId()));
							order.setBusiManagerName(String.valueOf(project.getCreateUserName()));
						}
					}
				} else if (receiptInfo.getProjectCode().startsWith("JJ")) {//经济业务
					BrokerBusinessQueryOrder brokeroOrder = new BrokerBusinessQueryOrder();
					order.setProjectCode(receiptInfo.getProjectCode());
					QueryBaseBatchResult<BrokerBusinessFormInfo> brokerRresult = brokerBusinessServiceClient
						.queryPage(brokeroOrder);
					if (brokerRresult != null && brokerRresult.isSuccess()) {
						BrokerBusinessFormInfo brokerInfo = brokerRresult.getPageList().get(0);
						if (brokerInfo != null) {
							order.setBusiType("经济业务");
							//  经济业务暂时不传busimanager信息
							//							order.setBusiManager(brokerInfo.getFormUserAccount());
							//order.setBusiManager(String.valueOf(brokerInfo.getFormUserId()));
							//TODO							
							//							syncBasicOrder.setUserId(brokerInfo.getFormUserId());
							//							syncBasicOrder.setUserName(brokerInfo.getFormUserName());
							//							syncBasicOrder.setCustomerId(brokerInfo.getProjectCode());
							//							syncBasicOrder.setCustomerName(brokerInfo.getCustomerName());
							//							syncBasicOrder.setSystemContract(false);
						}
					}
				} else {
					// 项目				
					ProjectInfo project = projectServiceClient.queryByCode(
						receiptInfo.getProjectCode(), false);
					if (project != null) {
						order.setBusiType(project.getBusiTypeName());
						order.setCustomerCode(String.valueOf(project.getCustomerId()));
						order.setCustomerName(project.getCustomerName());
						if (project.getBusiManagerId() > 0
							&& StringUtil.isNotBlank(project.getBusiManagerName())) {
							order.setBusiManager(String.valueOf(project.getBusiManagerId()));
							order.setBusiManagerName(String.valueOf(project.getBusiManagerName()));
						}
						//TODO
						//						syncBasicOrder.setUserId(project.getBusiManagerId());
						//						syncBasicOrder.setUserName(project.getBusiManagerName());
						//						syncBasicOrder.setCustomerId(String.valueOf(project.getCustomerId()));
						//						syncBasicOrder.setCustomerName(project.getCustomerName());
					}
				}
				
				ReceiptPaymentFormInfo receiptForm = receiptPaymentFormService.queryBySourceFormId(
					receiptInfo.getSourceForm(), receiptInfo.getSourceFormId(), true);
				
				if (receiptForm != null) {
					order.setContractNo(receiptForm.getContractNo());
					order.setContractName(receiptForm.getContractName());
					// 兼容模式，若合同名称为空，传入合同code
					if (StringUtil.isBlank(receiptForm.getContractName())) {
						order.setContractName(receiptForm.getContractNo());
					}
					
					order.setTransferName(receiptForm.getTransferName());
					// 保证金帐号
					String depositName = null;
					for (ReceiptPaymentFormFeeInfo info : receiptForm.getFeeList()) {
						if (SubjectCostTypeEnum.DEPOSIT_PAID == info.getFeeType()
							|| SubjectCostTypeEnum.GUARANTEE_DEPOSIT == info.getFeeType()) {
							depositName = info.getDepositAccount();
						}
					}
					order.setDepositName(depositName);
					order.setProductName(receiptForm.getProductName());
				}
			}
			
			//			logger.info(msg);
			order.setBizNo(receiptInfo.getBillNo());
			
			order.setProjectCode(receiptInfo.getProjectCode());
			
			order.setDeptCode(String.valueOf(receiptInfo.getApplyDeptCode()));
			
			//金碟接口待定
			//order.setcontractName(receiptInfo.getContractName());
			//理财产品相关信息
			//			if (ProjectUtil.isFinancial(receiptInfo.getProjectCode())) {
			//				//				ReceiptPaymentFormInfo receiptForm = receiptPaymentFormService.queryBySourceFormId(
			//				//					receiptInfo.getSourceForm(), receiptInfo.getSourceFormId(), false);
			//				if (receiptForm != null) {
			//					//					//金碟接口待定
			//					order.setTransferName(receiptForm.getTransferName());
			//					// 保证金帐号
			//					//								order.setDepositName(paymentForm.getde);
			//					order.setProductName(receiptForm.getProductName());
			//				}
			//			}
			
			syncBasicOrder.setDeptId(receiptInfo.getApplyDeptId());
			//TODO			
			//			syncBasicOrder.setContractNo(receiptInfo.getContractNo());
			
			List<GatheringInfo> feeDetail = new ArrayList<GatheringInfo>();
			if (ListUtil.isNotEmpty(feeList)) {
				List<BankMessageDO> banks = bankMessageDAO.findByCondition(new BankMessageDO(),
					null, 0, 999, null, null);
				for (FormReceiptFeeInfo info : feeList) {
					GatheringInfo gInfo = new GatheringInfo();
					gInfo.setFeeType(info.getFeeType().message());
					gInfo.setAmount(info.getAmount().toString());
					//防止历史数据保存临时加上的
					if (info.getReceiptDate() != null) {
						gInfo.setSkDate(DateUtil.dtSimpleFormat(info.getReceiptDate()));
					} else {
						gInfo.setSkDate(DateUtil.dtSimpleFormat(receiptInfo.getReceiptDate()));
					}
					if (StringUtil.isNotEmpty(info.getAccount())) {
						for (BankMessageDO bank : banks) {
							if (info.getAccount().equals(bank.getAccountNo())) {
								gInfo.setDebit(bank.getAtCode());//account duiying收    atcode对应收
								break;
							}
						}
					}
					
					gInfo.setCredit(info.getAtCode());//atcode对应付  付款账户对应贷方
					feeDetail.add(gInfo);
				}
			}
			
			order.setFeeDetail(feeDetail);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			
			//同步基础数据
			
			kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
			logger.info("同步收款单数据到金蝶入参order:" + order);
			KingdeeGatheringResult gatherResult = kingdeeTogetheFacadeClient.gathering(order);
			logger.info("同步收款单数据到金蝶结果result:" + gatherResult);
			
			//			实现IKingdeeSystemService
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(receiptInfo.getBillNo());
			updateOrder.setVoucherSyncSendTime(getSysdate());
			if (gatherResult != null
				&& CommonResultEnum.EXECUTE_SUCCESS == gatherResult.getResultCode()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_SUCCESS);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
			}
			updateVoucher(updateOrder);
			
			result.setSuccess(true);
			result.setMessage("同步成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步出错");
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			try {
				updateOrder.setBillNo(receiptInfo.getBillNo());
				updateOrder.setVoucherSyncSendTime(getSysdate());
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
				updateVoucher(updateOrder);
			} catch (Exception e1) {
				logger.error("收款单同步到财务系统出错后,更定状态为发送失败，失败！+order=" + updateOrder, e1);
			}
			logger.error("收款单同步到财务系统出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return commonProcess(order, "更新凭证号同步状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormReceiptDO receipt = formReceiptDAO.findByBillNo(order.getBillNo());
				
				if (receipt == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "收款单不存在");
				}
				
				//同步状态
				if (order.getVoucherStatus() != null) {
					if (order.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE
						&& StringUtil.equals(order.getVoucherNo(), receipt.getVoucherNo())) {
						receipt.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE.code());
					} else if (order.getVoucherStatus() != VoucherStatusEnum.SYNC_DELETE) {
						receipt.setVoucherStatus(order.getVoucherStatus().code());
					}
				}
				
				//凭证号
				receipt.setVoucherNo(order.getVoucherNo());
				
				//同步发送时间
				if (order.getVoucherSyncSendTime() != null)
					receipt.setVoucherSyncSendTime(order.getVoucherSyncSendTime());
				//同步完成时间
				if (order.getVoucherSyncFinishTime() != null)
					receipt.setVoucherSyncFinishTime(order.getVoucherSyncFinishTime());
				
				formReceiptDAO.update(receipt);
				
				return null;
			}
		}, null, null);
	}
	
}
