/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:08:52 创建
 */
package com.born.fcs.fm.biz.service.payment;

import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
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
import com.born.fcs.fm.biz.service.common.WorkflowExtProcessService;
import com.born.fcs.fm.dal.dataobject.BankMessageDO;
import com.born.fcs.fm.dal.dataobject.FormDO;
import com.born.fcs.fm.dal.dataobject.FormPaymentDO;
import com.born.fcs.fm.dal.dataobject.FormPaymentFeeDO;
import com.born.fcs.fm.dataobject.FormPaymentFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
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
import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentFeeOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.bornsoft.pub.order.kingdee.KingdeePaymentOrder;
import com.bornsoft.pub.order.kingdee.KingdeePaymentOrder.PaymentInfo;
import com.bornsoft.pub.result.kingdee.KingdeePaymentResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("paymentApplyService")
public class PaymentApplyServiceImpl extends BaseFormAutowiredDomainService implements
																			PaymentApplyService {
	
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
	private FinancialProjectSetupService financialProjectSetupService;
	
	@Autowired
	private WorkflowExtProcessService paymentApplyProcessService;
	
	/**
	 * 生成单据号
	 */
	private String genBillNo() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//序列号 每年重置
		String seqName = SysDateSeqNameEnum.FORM_PAYMENT_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		return "FK" + sdf.format(calendar.getTime())
				+ StringUtil.leftPad(String.valueOf(seq), 4, "0");
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#saveApply(com.born.fcs.fm.ws.order.payment.FormPaymentOrder)
	 */
	@Override
	public FormBaseResult saveApply(final FormPaymentOrder order) {
		return commonFormSaveProcess(order, "保存付款单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				// 20170329 添加判断，若为onlyChangeDetailList 只修改明细
				if (BooleanEnum.YES == order.getOnlyChangeDetailList()) {
					for (FormPaymentFeeOrder feeOrder : order.getFeeOrder()) {
						// 能修改付款账户和付款时间
						FormPaymentFeeDO feeDO = formPaymentFeeDAO.findById(feeOrder.getId());
						feeDO.setPaymentAccount(feeOrder.getPaymentAccount());
						feeDO.setPaymentDate(feeOrder.getPaymentDate());
						formPaymentFeeDAO.update(feeDO);
					}
					logger.info("明细修订完成！");
					return null;
				}
				
				boolean isUpdate = false;
				FormPaymentDO payment = formPaymentDAO.findByFormId(form.getFormId());
				if (payment == null) {
					payment = new FormPaymentDO();
					BeanCopier.staticCopy(order, payment, UnBoxingConverter.getInstance());
					payment.setFormId(form.getFormId());
					payment.setBillNo(genBillNo());
					payment.setRawAddTime(now);
					payment.setFormSource(order.getFormSource().code());
				} else {
					BeanCopier.staticCopy(order, payment, UnBoxingConverter.getInstance());
					isUpdate = true;
					payment.setFormSource(order.getFormSource().code());
				}
				
				//收款金额
				Money totalAmount = Money.zero();
				for (FormPaymentFeeOrder feeOrder : order.getFeeOrder()) {
					totalAmount.addTo(feeOrder.getAmount());
				}
				
				//付款来源单据
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
					payment.setAmount(rpInfo.getAmount());
					payment.setApplyDeptCode(rpInfo.getDeptCode());
					payment.setApplyDeptId(rpInfo.getDeptId());
					payment.setApplyDeptName(rpInfo.getDeptName());
					payment.setApplyUserId(rpInfo.getUserId());
					payment.setApplyUserAccount(rpInfo.getUserAccount());
					payment.setApplyUserName(rpInfo.getUserName());
					payment.setCustomerId(rpInfo.getCustomerId());
					payment.setCustomerName(rpInfo.getCustomerName());
					payment.setSourceForm(rpInfo.getSourceForm().code());
					payment.setSourceFormId(rpInfo.getSourceFormId());
					payment.setSourceFormSys(rpInfo.getSourceFormSys().code());
					payment.setProjectCode(rpInfo.getProjectCode());
					payment.setProjectName(rpInfo.getProjectName());
					payment.setIsSimple(rpInfo.getIsSimple());
					
					//验证收款金额
					if (!totalAmount.equals(payment.getAmount())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "付款明细总额必须等于付款总额");
					}
					
					//更新单据状态未处理中
					UpdateReceiptPaymentFormStatusOrder uOrder = new UpdateReceiptPaymentFormStatusOrder();
					uOrder.setSourceForm(order.getSourceForm());
					uOrder.setSourceFormId(order.getSourceFormId());
					uOrder.setStatus(ReceiptPaymentFormStatusEnum.IN_PROCESS);
					receiptPaymentFormService.updateStatus(uOrder);
					
				} else {
					payment.setAmount(totalAmount);
				}
				
				//凭证号状态未 未发送
				payment.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
				
				if (isUpdate) {
					formPaymentDAO.updateByFormId(payment);
				} else {
					formPaymentDAO.insert(payment);
				}
				
				FcsFmDomainHolder.get().addAttribute("payment", payment);
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				FormPaymentInfo info = new FormPaymentInfo();
				info.setBillNo(payment.getBillNo());
				result.setReturnObject(info);
				
				if (isUpdate) {
					
					//查询已经存在的费用明细
					List<FormPaymentFeeDO> existFeeList = formPaymentFeeDAO.findByFormId(form
						.getFormId());
					Map<Long, FormPaymentFeeDO> feeMap = Maps.newHashMap();
					if (ListUtil.isNotEmpty(existFeeList)) {
						for (FormPaymentFeeDO feeDO : existFeeList) {
							feeMap.put(feeDO.getId(), feeDO);
						}
					}
					
					for (FormPaymentFeeOrder feeOrder : order.getFeeOrder()) {
						FormPaymentFeeDO feeDO = feeMap.get(feeOrder.getId());
						//新增不存在的
						if (feeDO == null) {
							feeDO = new FormPaymentFeeDO();
							BeanCopier.staticCopy(feeOrder, feeDO);
							feeDO.setFormId(form.getFormId());
							feeDO.setFeeType(feeOrder.getFeeType().code());
							formPaymentFeeDAO.insert(feeDO);
						} else {
							//更新现有的
							if (!isEqual(feeOrder, feeDO)) {
								BeanCopier.staticCopy(feeOrder, feeDO);
								feeDO.setId(feeOrder.getId());
								feeDO.setFeeType(feeOrder.getFeeType().code());
								feeDO.setFormId(form.getFormId());
								formPaymentFeeDAO.update(feeDO);
							}
							feeMap.remove(feeOrder.getId());
						}
					}
					//删掉多余的
					for (long id : feeMap.keySet()) {
						formPaymentFeeDAO.deleteById(id);
					}
				} else {//直接新增
					for (FormPaymentFeeOrder feeOrder : order.getFeeOrder()) {
						FormPaymentFeeDO feeDO = new FormPaymentFeeDO();
						BeanCopier.staticCopy(feeOrder, feeDO);
						feeDO.setFormId(form.getFormId());
						feeDO.setFeeType(feeOrder.getFeeType().code());
						formPaymentFeeDAO.insert(feeDO);
					}
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				Date now = FcsFmDomainHolder.get().getSysDate();
				FormPaymentDO payment = (FormPaymentDO) FcsFmDomainHolder.get().getAttribute(
					"payment");
				if (order.getCheckStatus() == 1 && StringUtil.equals(payment.getIsSimple(), "IS")) {
					FormDO formDO = formDAO.findByFormId(payment.getFormId());
					formDO.setStatus(FormStatusEnum.APPROVAL.code());
					formDO.setSubmitTime(now);
					formDO.setFinishTime(now);
					formDO.setTaskUserData(null);
					formDAO.update(formDO);
					FormInfo formInfo = new FormInfo();
					BeanCopier.staticCopy(formDO, formInfo);
					formInfo.setFormCode(FormCodeEnum.getByCode(formDO.getFormCode()));
					formInfo.setStatus(FormStatusEnum.APPROVAL);
					WorkflowResult workflowResult = new WorkflowResult();
					workflowResult.setSuccess(true);
					paymentApplyProcessService.endFlowProcess(formInfo, workflowResult);
				}
				return null;
			}
		});
	}
	
	/**
	 * 是否是相同的费用明细
	 * @param feeOrder
	 * @param feeDO
	 * @return
	 */
	private boolean isEqual(FormPaymentFeeOrder feeOrder, FormPaymentFeeDO feeDO) {
		return feeOrder.getAmount().equals(feeDO.getAmount())
				&& StringUtil.equals(feeOrder.getAtCode(), feeDO.getAtCode())
				&& StringUtil.equals(feeOrder.getAtName(), feeDO.getAtName())
				&& StringUtil.equals(feeOrder.getPaymentAccount(), feeDO.getPaymentAccount())
				&& StringUtil.equals(feeOrder.getReceiptAccount(), feeDO.getReceiptAccount())
				&& StringUtil.equals(feeOrder.getRemark(), feeDO.getRemark())
				&& feeOrder.getPaymentDate() == feeDO.getPaymentDate()
				&& StringUtil.equals(feeOrder.getFeeType().code(), feeDO.getFeeType());
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#searchForm(com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<FormPaymentFormInfo> searchForm(FormPaymentQueryOrder order) {
		QueryBaseBatchResult<FormPaymentFormInfo> batchResult = new QueryBaseBatchResult<FormPaymentFormInfo>();
		try {
			FormPaymentFormDO paymentForm = new FormPaymentFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, paymentForm);
			long totalCount = busiDAO.searchPaymentFormCount(paymentForm);
			PageComponent component = new PageComponent(order, totalCount);
			List<FormPaymentFormDO> dataList = busiDAO.searchPaymentForm(paymentForm);
			
			List<FormPaymentFormInfo> list = Lists.newArrayList();
			for (FormPaymentFormDO DO : dataList) {
				FormPaymentFormInfo info = new FormPaymentFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
				info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
				info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
				info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
				if (order.isQueryFeeDetail()) {
					info.setFeeList(findPaymentFeeByFormId(info.getFormId()));
				}
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询付款单失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	/**
	 * @param formId
	 * @param queryFeeDetail
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#findPaymentByFormId(long,
	 * boolean)
	 */
	@Override
	public FormPaymentInfo findPaymentByFormId(long formId, boolean queryFeeDetail) {
		FormPaymentDO DO = formPaymentDAO.findByFormId(formId);
		if (DO != null) {
			FormPaymentInfo info = new FormPaymentInfo();
			BeanCopier.staticCopy(DO, info);
			info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
			info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
			info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
			if (queryFeeDetail) {
				info.setFeeList(findPaymentFeeByFormId(info.getFormId()));
			}
			return info;
		}
		return null;
	}
	
	/**
	 * @param billNo
	 * @param queryFeeDetail
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#findPaymentByBillNo(java.lang.String,
	 * boolean)
	 */
	@Override
	public FormPaymentInfo findPaymentByBillNo(String billNo, boolean queryFeeDetail) {
		FormPaymentDO DO = formPaymentDAO.findByBillNo(billNo);
		if (DO != null) {
			FormPaymentInfo info = new FormPaymentInfo();
			BeanCopier.staticCopy(DO, info);
			info.setFormSource(FormSourceEnum.getByCode(DO.getFormSource()));
			info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
			info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
			if (queryFeeDetail) {
				info.setFeeList(findPaymentFeeByFormId(info.getFormId()));
			}
			return info;
		}
		return null;
	}
	
	/**
	 * @param formId
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#findPaymentFeeByFormId(long)
	 */
	@Override
	public List<FormPaymentFeeInfo> findPaymentFeeByFormId(long formId) {
		List<FormPaymentFeeDO> feeList = formPaymentFeeDAO.findByFormId(formId);
		List<BankMessageDO> banks = bankMessageDAO.findByCondition(new BankMessageDO(), null, 0,
			999, null, null);
		
		List<FormPaymentFeeInfo> list = Lists.newArrayList();
		if (ListUtil.isNotEmpty(feeList)) {
			for (FormPaymentFeeDO DO : feeList) {
				FormPaymentFeeInfo info = new FormPaymentFeeInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFeeType(SubjectCostTypeEnum.getByCode(DO.getFeeType()));
				// 获取余额
				for (BankMessageDO bank : banks) {
					if (StringUtil.equals(info.getPaymentAccount(), bank.getAccountNo())) {
						info.setCanUseAmount(bank.getAmount());
						break;
					}
				}
				list.add(info);
			}
		}
		return list;
	}
	
	/**
	 * @param PaymentInfo
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#sync2FinancialSys(com.born.fcs.fm.ws.info.payment.FormPaymentInfo)
	 */
	@Override
	public FcsBaseResult sync2FinancialSys(FormPaymentInfo paymentInfo) {
		FcsBaseResult result = createResult();
		try {
			
			logger.info("付款单同步至财务系统开始  ,receiptInfo : {}", paymentInfo);
			if (paymentInfo == null) {
				result.setSuccess(false);
				result.setMessage("付款单不存在");
				return result;
			}
			
			List<FormPaymentFeeInfo> feeList = paymentInfo.getFeeList();
			if (ListUtil.isEmpty(feeList)) {
				feeList = findPaymentFeeByFormId(paymentInfo.getFormId());
			}
			KingdeePaymentOrder order = new KingdeePaymentOrder();
			
			SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
			if (FormSourceEnum.FORM == paymentInfo.getFormSource()) {
				if (SourceFormEnum.INNER_LOAN == paymentInfo.getSourceForm()) {
					
				} else if (ProjectUtil.isFinancial(paymentInfo.getProjectCode())) {
					FProjectFinancialInfo project = financialProjectSetupService
						.queryByProjectCode(paymentInfo.getProjectCode());
					if (project != null) {
						order.setBusiType("理财产品购买");
						if (project.getCreateUserId() > 0
							&& StringUtil.isNotBlank(project.getCreateUserName())) {
							order.setBusiManager(String.valueOf(project.getCreateUserId()));
							order.setBusiManagerName(String.valueOf(project.getCreateUserName()));
						}
						//TODO						
						//						syncBasicOrder.setUserId(project.getCreateUserId());
						//						syncBasicOrder.setUserName(project.getCreateUserName());
					}
				} else {
					// 项目				
					ProjectInfo project = projectServiceClient.queryByCode(
						paymentInfo.getProjectCode(), false);
					order.setBusiType(project.getBusiTypeName());
					order.setCustomerCode(String.valueOf(project.getCustomerId()));
					order.setCustomerName(project.getCustomerName());
					if (project.getBusiManagerId() > 0
						&& StringUtil.isNotBlank(project.getBusiManagerName())) {
						
						order.setBusiManager(String.valueOf(project.getBusiManagerId()));
						order.setBusiManagerName(String.valueOf(project.getBusiManagerName()));
					}
					//TODO					
					//					syncBasicOrder.setUserId(project.getBusiManagerId());
					//					syncBasicOrder.setUserName(project.getBusiManagerName());
					//					syncBasicOrder.setCustomerId(String.valueOf(project.getCustomerId()));
					//					syncBasicOrder.setCustomerName(project.getCustomerName());
				}
				
				ReceiptPaymentFormInfo paymentForm = receiptPaymentFormService.queryBySourceFormId(
					paymentInfo.getSourceForm(), paymentInfo.getSourceFormId(), true);
				
				if (paymentForm != null) {
					
					order.setContractNo(paymentForm.getContractNo());
					order.setContractName(paymentForm.getContractName());
					// 兼容模式，若合同名称为空，传入合同code
					if (StringUtil.isBlank(paymentForm.getContractName())) {
						order.setContractName(paymentForm.getContractNo());
					}
					
					order.setTransferName(paymentForm.getTransferName());
					// 保证金帐号
					String depositName = null;
					for (ReceiptPaymentFormFeeInfo info : paymentForm.getFeeList()) {
						if (SubjectCostTypeEnum.DEPOSIT_PAID == info.getFeeType()
							|| SubjectCostTypeEnum.GUARANTEE_DEPOSIT == info.getFeeType()) {
							depositName = info.getDepositAccount();
						}
					}
					order.setDepositName(depositName);
					order.setProductName(paymentForm.getProductName());
				}
				
			} else {
				
			}
			
			order.setBizNo(paymentInfo.getBillNo());
			order.setProjectCode(paymentInfo.getProjectCode());
			order.setDeptCode(String.valueOf(paymentInfo.getApplyDeptCode()));
			
			//			//理财产品相关信息
			//			if (ProjectUtil.isFinancial(paymentInfo.getProjectCode())) {
			//			if (paymentForm != null) {
			//					//金碟接口待定
			
			//			}
			//			}
			
			syncBasicOrder.setDeptId(paymentInfo.getApplyDeptId());
			
			List<PaymentInfo> feeDetail = new ArrayList<PaymentInfo>();
			if (ListUtil.isNotEmpty(feeList)) {
				List<BankMessageDO> banks = bankMessageDAO.findByCondition(new BankMessageDO(),
					null, 0, 999, null, null);
				for (FormPaymentFeeInfo info : feeList) {
					PaymentInfo gInfo = new PaymentInfo();
					gInfo.setFeeType(info.getFeeType().message());
					gInfo.setAmount(info.getAmount().toString());
					//防止历史数据保存临时加上的
					if (info.getPaymentDate() != null) {
						gInfo.setFkDate(DateUtil.dtSimpleFormat(info.getPaymentDate()));
					} else {
						gInfo.setFkDate(DateUtil.dtSimpleFormat(paymentInfo.getPaymentDate()));
					}
					gInfo.setDebit(info.getAtCode());//account duiying收    atcode对应收
					if (StringUtil.isNotEmpty(info.getPaymentAccount())) {
						for (BankMessageDO bank : banks) {
							if (info.getPaymentAccount().equals(bank.getAccountNo())) {
								gInfo.setCredit(bank.getAtCode());//atcode对应付  付款账户对应贷方
								break;
							}
						}
					}
					
					feeDetail.add(gInfo);
				}
			}
			
			order.setFeeDetail(feeDetail);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			
			kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
			
			logger.info("同步付款单数据到金蝶入参order:" + order);
			KingdeePaymentResult paymentResult = kingdeeTogetheFacadeClient.payment(order);
			logger.info("同步付款单数据到金蝶结果result:" + paymentResult);
			
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(paymentInfo.getBillNo());
			updateOrder.setVoucherSyncSendTime(getSysdate());
			if (paymentResult != null
				&& CommonResultEnum.EXECUTE_SUCCESS == paymentResult.getResultCode()) {
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
				updateOrder.setBillNo(paymentInfo.getBillNo());
				updateOrder.setVoucherSyncSendTime(getSysdate());
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
				updateVoucher(updateOrder);
			} catch (Exception e1) {
				logger.error("付款单同步到财务系统出错后,更定状态为发送失败，失败！+order=" + updateOrder, e1);
			}
			logger.error("付款单同步到财务系统出错：{}", e);
		}
		
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.payment.PaymentApplyService#updateVoucher(com.born.fcs.fm.ws.order.common.UpdateVoucherOrder)
	 */
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return commonProcess(order, "更新凭证号同步状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormPaymentDO payment = formPaymentDAO.findByBillNo(order.getBillNo());
				
				if (payment == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "付款单不存在");
				}
				
				//同步状态
				if (order.getVoucherStatus() != null) {
					if (order.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE
					//						&& StringUtil.equals(order.getVoucherNo(), payment.getVoucherNo())
					) {
						payment.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE.code());
					} else if (order.getVoucherStatus() != VoucherStatusEnum.SYNC_DELETE) {
						payment.setVoucherStatus(order.getVoucherStatus().code());
					}
				}
				
				//凭证号
				payment.setVoucherNo(order.getVoucherNo());
				
				//同步发送时间
				if (order.getVoucherSyncSendTime() != null)
					payment.setVoucherSyncSendTime(order.getVoucherSyncSendTime());
				//同步完成时间
				if (order.getVoucherSyncFinishTime() != null)
					payment.setVoucherSyncFinishTime(order.getVoucherSyncFinishTime());
				
				formPaymentDAO.update(payment);
				
				return null;
			}
		}, null, null);
	}
	
}
