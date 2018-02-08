package com.born.fcs.pm.biz.service.capitalappropriationapply;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyPayeeDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyReceiptDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dataobject.CapitalAppropriationApplyFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyPayeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyReceiptInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyFeeOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyPayeeOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyReceiptOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("fCapitalAppropriationApplyService")
public class FCapitalAppropriationApplyServiceImpl extends BaseFormAutowiredDomainService
																							implements
																							FCapitalAppropriationApplyService,
																							AsynchronousService {
	@Autowired
	FinancialProjectTransferService financialProjectTransferService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CouncilSummaryService councilSummaryService;
	@Autowired
	FormService formService;
	@Autowired
	AssetsTransfereeApplicationService assetsTransfereeApplicationWebService;
	@Autowired
	protected SiteMessageService siteMessageService;
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormServiceClient;
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	private FCapitalAppropriationApplyInfo convertDO2Info(FCapitalAppropriationApplyDO DO) {
		if (DO == null)
			return null;
		FCapitalAppropriationApplyInfo info = new FCapitalAppropriationApplyInfo();
		BeanCopier.staticCopy(DO, info);
		info.setProjectType(FCapitalAppropriationApplyTypeEnum.getByCode(DO.getProjectType()));
		info.setIsReceipt(BooleanEnum.getByCode(DO.getIsReceipt()));
		return info;
	}
	
	private FCapitalAppropriationApplyFeeInfo convertFCapitalAppropriationApplyFeeDO2Info(	FCapitalAppropriationApplyFeeDO DO) {
		if (DO == null)
			return null;
		FCapitalAppropriationApplyFeeInfo info = new FCapitalAppropriationApplyFeeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setAppropriateReason(PaymentMenthodEnum.getByCode(DO.getAppropriateReason()));
		return info;
	}
	
	private FCapitalAppropriationApplyPayeeInfo convertDO2PayeeInfo(FCapitalAppropriationApplyPayeeDO DO) {
		if (DO == null)
			return null;
		FCapitalAppropriationApplyPayeeInfo info = new FCapitalAppropriationApplyPayeeInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private FCapitalAppropriationApplyReceiptInfo convertDO2ReceiptInfo(FCapitalAppropriationApplyReceiptDO DO) {
		if (DO == null)
			return null;
		FCapitalAppropriationApplyReceiptInfo info = new FCapitalAppropriationApplyReceiptInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FCapitalAppropriationApplyInfo findById(long id) {
		FCapitalAppropriationApplyDO fCapitalAppropriationApplyDO = FCapitalAppropriationApplyDAO
			.findById(id);
		FCapitalAppropriationApplyInfo info = new FCapitalAppropriationApplyInfo();
		BeanCopier.staticCopy(fCapitalAppropriationApplyDO, info);
		return info;
	}
	
	@Override
	public FCapitalAppropriationApplyInfo findCapitalAppropriationApplyByFormId(long formId) {
		FCapitalAppropriationApplyDO DO = FCapitalAppropriationApplyDAO.findByFormId(formId);
		FCapitalAppropriationApplyInfo info = convertDO2Info(DO);
		info.setProjectType(FCapitalAppropriationApplyTypeEnum.getByCode(DO.getProjectType()));
		FCapitalAppropriationApplyPayeeDAO.findByApplyId(DO.getApplyId());
		info.setPayeeInfo(convertDO2PayeeInfo(FCapitalAppropriationApplyPayeeDAO.findByApplyId(DO
			.getApplyId())));
		info.setReceiptInfo(convertDO2ReceiptInfo(FCapitalAppropriationApplyReceiptDAO
			.findByFormId(formId)));
		return info;
	}
	
	@Override
	public FormBaseResult save(final FCapitalAppropriationApplyOrder order) {
		return commonFormSaveProcess(order, "资金收付申请单", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getApplyId() == null || order.getApplyId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FCapitalAppropriationApplyDO fCapitalAppropriationApplyDO = new FCapitalAppropriationApplyDO();
					BeanCopier.staticCopy(order, fCapitalAppropriationApplyDO);
					fCapitalAppropriationApplyDO.setFormId(formInfo.getFormId());
					fCapitalAppropriationApplyDO.setProjectType(order.getProjectType());
					fCapitalAppropriationApplyDO.setRawAddTime(now);
					fCapitalAppropriationApplyDO.setAttach(null);
					fCapitalAppropriationApplyDO.setOutBizNo(order.getOutBizNo());
					//					fCapitalAppropriationApplyDO.setAmount(new Money(order.getAmount()));
					fCapitalAppropriationApplyDO.setIsReceipt(BooleanEnum.NO.code());
					long id = FCapitalAppropriationApplyDAO.insert(fCapitalAppropriationApplyDO);//主表id
					FCapitalAppropriationApplyPayeeOrder payeeOrder = order.getPayeeOrder();
					FCapitalAppropriationApplyPayeeDO payeeDO = new FCapitalAppropriationApplyPayeeDO();
					BeanCopier.staticCopy(payeeOrder, payeeDO);
					payeeDO.setApplyId(id);
					payeeDO.setRawAddTime(now);
					FCapitalAppropriationApplyPayeeDAO.insert(payeeDO);
					List<FCapitalAppropriationApplyFeeOrder> feeOrder = order
						.getfCapitalAppropriationApplyFeeOrders();
					for (FCapitalAppropriationApplyFeeOrder fCapitalAppropriationApplyFeeOrder : feeOrder) {
						FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO = new FCapitalAppropriationApplyFeeDO();
						if (fCapitalAppropriationApplyFeeOrder != null) {
							BeanCopier.staticCopy(fCapitalAppropriationApplyFeeOrder,
								fCapitalAppropriationApplyFeeDO);
							fCapitalAppropriationApplyFeeDO.setApplyId(id);//设置副表依赖主表id
							fCapitalAppropriationApplyFeeDO.setRawAddTime(now);
							fCapitalAppropriationApplyFeeDO
								.setAppropriateAmount(new Money(fCapitalAppropriationApplyFeeOrder
									.getAppropriateAmount() == null ? 0.00
									: fCapitalAppropriationApplyFeeOrder.getAppropriateAmount()));
							FCapitalAppropriationApplyFeeDAO
								.insert(fCapitalAppropriationApplyFeeDO);
						}
						
					}
					if (fCapitalAppropriationApplyDO.getProjectType().equals("FINANCIAL_PRODUCT")) {
						//更新项目状态  理财类项目
						if (fCapitalAppropriationApplyDO.getOutBizNo() > 0) {
							financialProjectService.changeStatus(order.getProjectCode(),
								FinancialProjectStatusEnum.BUY_BACK_APPLYING);
						} else {
							financialProjectService.changeStatus(order.getProjectCode(),
								FinancialProjectStatusEnum.CAPITAL_APPLY_DRAFT);
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//更新
					Long id = order.getApplyId();
					FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO
						.findById(id);
					if (null == applyDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到资金划付录");
					}
					BeanCopier.staticCopy(order, applyDO);
					applyDO.setProjectType(order.getProjectType());
					FCapitalAppropriationApplyDAO.update(applyDO);//更新主表信息
					
					FCapitalAppropriationApplyPayeeOrder payeeOrder = order.getPayeeOrder();
					FCapitalAppropriationApplyPayeeDO payeeDO = FCapitalAppropriationApplyPayeeDAO
						.findByApplyId(applyDO.getApplyId());
					BeanCopier.staticCopy(payeeOrder, payeeDO);
					payeeDO.setApplyId(id);
					FCapitalAppropriationApplyPayeeDAO.update(payeeDO);
					if (applyDO.getProjectType().equals("FINANCIAL_PRODUCT")) {
						//更新项目状态  理财类项目
						if (applyDO.getOutBizNo() > 0) {//回购
							financialProjectService.changeStatus(order.getProjectCode(),
								FinancialProjectStatusEnum.BUY_BACK_APPLYING);
						} else {
							financialProjectService.changeStatus(order.getProjectCode(),
								FinancialProjectStatusEnum.CAPITAL_APPLY_DRAFT);
						}
					}
					if (order.getfCapitalAppropriationApplyFeeOrders() != null) {
						//删除先
						FCapitalAppropriationApplyFeeDAO.deleteByApplyId(applyDO.getApplyId());
					}
					//更新副表(直接插入新数据)  资金划付申请-资金划付信息
					for (FCapitalAppropriationApplyFeeOrder fCapitalAppropriationApplyFeeOrder : order
						.getfCapitalAppropriationApplyFeeOrders()) {
						
						FCapitalAppropriationApplyFeeDO applyFeeDO = new FCapitalAppropriationApplyFeeDO();
						if (fCapitalAppropriationApplyFeeOrder != null) {
							applyFeeDO.setApplyId(id);
							applyFeeDO.setRawAddTime(now);
							applyFeeDO
								.setAppropriateAmount(new Money(fCapitalAppropriationApplyFeeOrder
									.getAppropriateAmount() == null ? 0.00
									: fCapitalAppropriationApplyFeeOrder.getAppropriateAmount()));
							applyFeeDO.setAppropriateReason(fCapitalAppropriationApplyFeeOrder
								.getAppropriateReason());
							applyFeeDO.setRemark(fCapitalAppropriationApplyFeeOrder.getRemark());
							FCapitalAppropriationApplyFeeDAO.insert(applyFeeDO);
						}
						
					}
					
				}
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FCapitalAppropriationApplyInfo> query(	FCapitalAppropriationApplyQueryOrder order) {
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> baseBatchResult = new QueryBaseBatchResult<FCapitalAppropriationApplyInfo>();
		try {
			CapitalAppropriationApplyFormDO fCapitalAppropriationApplyDO = new CapitalAppropriationApplyFormDO();
			BeanCopier.staticCopy(order, fCapitalAppropriationApplyDO);
			
			long totalCount = extraDAO.searchCapitalAppropriationCount(
				fCapitalAppropriationApplyDO, order.getSubmitTimeStart(), order.getSubmitTimeEnd(),
				order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<CapitalAppropriationApplyFormDO> pageList = extraDAO
				.searchCapitalAppropriationList(fCapitalAppropriationApplyDO,
					component.getFirstRecord(), component.getPageSize(),
					order.getSubmitTimeStart(), order.getSubmitTimeEnd(), order.getDeptIdList(),
					order.getSortCol(), order.getSortOrder());
			
			List<FCapitalAppropriationApplyInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (CapitalAppropriationApplyFormDO sf : pageList) {
					FCapitalAppropriationApplyInfo info = new FCapitalAppropriationApplyInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					info.setIsReceipt(BooleanEnum.getByCode(sf.getIsReceipt()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询资金划付项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public List<FCapitalAppropriationApplyInfo> findCapitalAppropriationApplyByProjectCode(	String projectCode) {
		List<FCapitalAppropriationApplyInfo> listApplyInfo = new ArrayList<FCapitalAppropriationApplyInfo>();
		List<FCapitalAppropriationApplyDO> listApplyDO = FCapitalAppropriationApplyDAO
			.findByProjectCode(projectCode);
		for (FCapitalAppropriationApplyDO fCapitalAppropriationApplyDO : listApplyDO) {
			FCapitalAppropriationApplyInfo info = new FCapitalAppropriationApplyInfo();
			info = convertDO2Info(fCapitalAppropriationApplyDO);
			listApplyInfo.add(info);
		}
		return listApplyInfo;
	}
	
	@Override
	public FcsBaseResult saveCapitalAppropriationApply(FCapitalAppropriationApplyOrder order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<FCapitalAppropriationApplyFeeInfo> findByApplyId(long applyId) {
		List<FCapitalAppropriationApplyFeeInfo> listFCapitalAppropriationApplyFeeInfo = new ArrayList<FCapitalAppropriationApplyFeeInfo>();
		List<FCapitalAppropriationApplyFeeDO> listFCapitalAppropriationApplyFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyId);
		for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : listFCapitalAppropriationApplyFeeDO) {
			FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo = convertFCapitalAppropriationApplyFeeDO2Info(fCapitalAppropriationApplyFeeDO);
			listFCapitalAppropriationApplyFeeInfo.add(fCapitalAppropriationApplyFeeInfo);
		}
		return listFCapitalAppropriationApplyFeeInfo;
	}
	
	@Override
	public List<PaymentMenthodEnum> getPaymentMenthodEnum(String projectCode) {
		ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
		boolean isGuarantee = ProjectUtil.isGuarantee(projectInfo.getBusiType());//是否担保业务
		boolean isLitigation = ProjectUtil.isLitigation(projectInfo.getBusiType());//是否诉讼保函业务
		boolean isUnderwriting = ProjectUtil.isUnderwriting(projectInfo.getBusiType());//是否是承销业务
		boolean isEntrusted = ProjectUtil.isEntrusted(projectInfo.getBusiType());//是否是委贷业务
		boolean isBond = ProjectUtil.isBond(projectInfo.getBusiType());//发债是担保的一种
		List<PaymentMenthodEnum> paymentTypeEnum = Lists.newArrayList();
		Boolean isRepay = false;
		List<FCouncilSummaryRiskHandleInfo> riskHandleInfoList = councilSummaryService
			.queryRiskHandleCsByProjectCode(projectCode);
		if (riskHandleInfoList != null) {
			for (FCouncilSummaryRiskHandleInfo info : riskHandleInfoList) {
				if (info.getIsComp() == BooleanEnum.IS) {
					isRepay = true;
					break;
				}
			}
		}
		
		if (isGuarantee || isBond) {
			//担保项目可选择的费用种类：存出保证金、退换客户保证金、代偿本金、代偿利息、退费、其他；
			paymentTypeEnum.add(PaymentMenthodEnum.DEPOSIT_PAID);
			paymentTypeEnum.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND);
			if (isRepay) {
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			}
			
			paymentTypeEnum.add(PaymentMenthodEnum.REFUND);
			paymentTypeEnum.add(PaymentMenthodEnum.OTHER);
		}
		if (isEntrusted) {
			//当项目为委贷项目，可选的费用种类为：委贷放款、委贷手续费、存出保证金、退换客户保证金、代偿本金、代偿利息、退费、其他；
			paymentTypeEnum.add(PaymentMenthodEnum.COMMISSION_LOAN);
			paymentTypeEnum.add(PaymentMenthodEnum.COMMISSION_LOAN_FEE);
			paymentTypeEnum.add(PaymentMenthodEnum.DEPOSIT_PAID);
			paymentTypeEnum.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND);
			if (isRepay) {
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			}
			paymentTypeEnum.add(PaymentMenthodEnum.REFUND);
			paymentTypeEnum.add(PaymentMenthodEnum.OTHER);
		}
		if (isLitigation) {
			//代偿本金、代偿利息、退费、其他
			if (isRepay) {
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
				paymentTypeEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			}
			
			paymentTypeEnum.add(PaymentMenthodEnum.REFUND);
			paymentTypeEnum.add(PaymentMenthodEnum.OTHER);
		}
		if (isUnderwriting) {
			//退费、其他
			paymentTypeEnum.add(PaymentMenthodEnum.REFUND);
			paymentTypeEnum.add(PaymentMenthodEnum.OTHER);
		}
		//资产受让有改项目审核通过，可选该划付种类
		FAssetsTransfereeApplicationQueryOrder order = new FAssetsTransfereeApplicationQueryOrder();
		order.setProjectCode(projectCode);
		order.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> batchResult = assetsTransfereeApplicationWebService
			.query(order);
		if (batchResult != null && batchResult.getPageList().size() > 0) {
			paymentTypeEnum.add(PaymentMenthodEnum.OTHER_ASSET_SWAP);
		}
		paymentTypeEnum.add(PaymentMenthodEnum.PROXY_CHARGING_OUT);
		/** 所有业务都可发起，不控制阶段和发起的金额 */
		paymentTypeEnum.add(PaymentMenthodEnum.CASE_ACCEPTANCE_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.PRESERVATION_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.ANNOUNCEMENT_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.APPRAISAL_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.EXPERT_WITNESS_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.LAWYER_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.ASSESSMENT_FEE);
		paymentTypeEnum.add(PaymentMenthodEnum.AUCTION_FEE);
		if (!isGuarantee && !isBond && !isEntrusted && !isUnderwriting && !isLitigation) {
			paymentTypeEnum = PaymentMenthodEnum.getAllEnum();
		}
		return paymentTypeEnum;
	}
	
	@Override
	public List<Money> getLimitByProject(String projectCode,
											FCapitalAppropriationApplyTypeEnum type, long applyId,
											Boolean isEdit) {
		List<Money> list = Lists.newArrayList();
		//非理财
		if (type == FCapitalAppropriationApplyTypeEnum.NOT_FINANCIAL_PRODUCT) {
			ProjectSimpleDetailInfo projectInfo = projectService.querySimpleDetailInfo(projectCode);
			//退还客户保证金  页面填写金额  和 产品表单的金额之和  必须小于等于 project表中客户保证 
			Money customerDepositRefund = Money.zero();
			//委贷放款
			Money commissionLoan = Money.zero();
			//代偿本金
			Money compensatoryPrincipal = Money.zero();
			//代偿利息
			Money compensatoryInterest = Money.zero();
			//退费
			Money refundAmount = Money.zero();
			//存出保证金
			Money depositPaid = Money.zero();
			//资产受让
			Money assetsTransferee = Money.zero();
			FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
			queryOrder.setProjectCode(projectCode);
			QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = query(queryOrder);
			List<FCapitalAppropriationApplyInfo> applyInfoList = batchResult.getPageList();
			//编辑时 ,移除本次编辑的对象
			if (isEdit) {
				for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
					FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
						.next();
					if (fCapitalAppropriationApplyInfo.getApplyId() == applyId) {
						iterator.remove();
					}
				}
			}
			if (applyInfoList != null && applyInfoList.size() > 0) {
				
				for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
					FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
						.next();
					FormInfo form1 = formService.findByFormId(fCapitalAppropriationApplyInfo
						.getFormId());
					if (form1.getStatus().code().equals("APPROVAL")) {//移除已经审核通过的申请单，因为审核通过后已经减了相应的金额
						iterator.remove();
					}
					if (form1.getStatus().code().equals("DENY")) {//移除已经审核不通过的申请单
						iterator.remove();
					}
				}
			}
			if (applyInfoList != null && applyInfoList.size() > 0) {
				for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList) {
					List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
						.getApplyId());
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						//退还客户保证金
						if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							customerDepositRefund = customerDepositRefund
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
						//委贷放款
						if (PaymentMenthodEnum.COMMISSION_LOAN.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							commissionLoan = commissionLoan.add(fCapitalAppropriationApplyFeeInfo
								.getAppropriateAmount());
						}
						//代偿本金
						if (PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							compensatoryPrincipal = compensatoryPrincipal
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
						//代偿利息
						if (PaymentMenthodEnum.COMPENSATORY_INTEREST.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							compensatoryInterest = compensatoryInterest
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
						//退费
						if (PaymentMenthodEnum.REFUND.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							refundAmount = refundAmount.add(fCapitalAppropriationApplyFeeInfo
								.getAppropriateAmount());
						}
						//资产受让
						if (PaymentMenthodEnum.OTHER_ASSET_SWAP.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							assetsTransferee = assetsTransferee
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
					}
				}
			}
			//加回审核通过的   资产受让 -委贷放款
			FCapitalAppropriationApplyQueryOrder queryOrder2 = new FCapitalAppropriationApplyQueryOrder();
			queryOrder2.setProjectCode(projectCode);
			queryOrder2.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult2 = query(queryOrder2);
			List<FCapitalAppropriationApplyInfo> applyInfoList2 = batchResult2.getPageList();
			for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList2) {
				List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
					.getApplyId());
				if (fCapitalAppropriationApplyFeeInfoList != null) {
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						//资产受让
						if (PaymentMenthodEnum.OTHER_ASSET_SWAP.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							assetsTransferee = assetsTransferee
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
						//委贷放款
						if (PaymentMenthodEnum.COMMISSION_LOAN.code().equals(
							fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							commissionLoan = commissionLoan.add(fCapitalAppropriationApplyFeeInfo
								.getAppropriateAmount());
						}
					}
				}
			}
			//代偿本金和利息不能超过风险处置会会议纪要中代偿金额的总和
			List<FCouncilSummaryRiskHandleInfo> listCouncilSummaryRiskHandleInfo = councilSummaryService
				.queryRiskHandleCsByProjectCode(projectCode);
			Money riskHandelCPSum = new Money(0);//风险处置会会议纪要中代偿本金之和
			Money riskHandelCISum = new Money(0);//风险处置会会议纪要中代偿利息之和
			if (listCouncilSummaryRiskHandleInfo != null) {
				for (FCouncilSummaryRiskHandleInfo fCouncilSummaryRiskHandleInfo : listCouncilSummaryRiskHandleInfo) {
					if (fCouncilSummaryRiskHandleInfo.getIsComp() == BooleanEnum.IS) {//代偿
						riskHandelCPSum = riskHandelCPSum.add(fCouncilSummaryRiskHandleInfo
							.getCompPrincipal());
						riskHandelCISum = riskHandelCISum.add(fCouncilSummaryRiskHandleInfo
							.getCompInterest());
					}
					
				}
			}
			//委贷放款  金额不能超过目前可放款的总额
			list.add(projectInfo.getLoanedAmount().subtract(commissionLoan));
			list.add(riskHandelCPSum.subtract(compensatoryPrincipal).subtract(
				projectInfo.getCompPrincipalAmount()));
			list.add(riskHandelCISum.subtract(compensatoryInterest).subtract(
				projectInfo.getCompInterestAmount()));
			list.add(projectInfo.getCustomerDepositAmount().subtract(customerDepositRefund));
			list.add(projectInfo.getRefundAmount().subtract(refundAmount));
			//资产受让 最近一次资产受让通过的项目受让金额 作为限制
			FAssetsTransfereeApplicationQueryOrder order = new FAssetsTransfereeApplicationQueryOrder();
			order.setProjectCode(projectCode);
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
			order.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> batchResult1 = assetsTransfereeApplicationWebService
				.query(order);
			Money assetsTransferee1 = Money.zero();
			List<FAssetsTransfereeApplicationInfo> listTransfereeInfo = batchResult1.getPageList();
			if (listTransfereeInfo != null) {
				for (FAssetsTransfereeApplicationInfo fAssetsTransfereeApplicationInfo : listTransfereeInfo) {
					assetsTransferee1 = fAssetsTransfereeApplicationInfo.getTransfereePrice();
					break;
				}
			}
			list.add(assetsTransferee1.subtract(assetsTransferee));
		} else {//理财
			ProjectFinancialInfo projectFinancialInfo = financialProjectService
				.queryByCode(projectCode);
			Money financialProduct = new Money(0);
			List<FCapitalAppropriationApplyInfo> applyInfoList = findCapitalAppropriationApplyByProjectCode(projectCode);
			//编辑时 ,移除本次编辑的对象
			if (isEdit) {
				for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
					FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
						.next();
					if (fCapitalAppropriationApplyInfo.getApplyId() == applyId) {
						iterator.remove();
					}
				}
			}
			if (applyInfoList != null && applyInfoList.size() > 0) {
				
				for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
					FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
						.next();
					FormInfo form1 = formService.findByFormId(fCapitalAppropriationApplyInfo
						.getFormId());
					if (form1.getStatus().code().equals("DENY")) {//移除已经审核不通过的申请单
						iterator.remove();
					}
				}
			}
			if (applyInfoList != null && applyInfoList.size() > 0) {
				for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList) {
					List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
						.getApplyId());
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						if (PaymentMenthodEnum.FINANCIAL_PRODUCT.code() != null
							&& PaymentMenthodEnum.FINANCIAL_PRODUCT.code().equals(
								fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
							financialProduct = financialProduct
								.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
						}
					}
				}
			}
			list.add(projectFinancialInfo.getPrice().multiply(projectFinancialInfo.getBuyNum())
				.subtract(financialProduct));
		}
		return list;
	}
	
	//计算存出保证金 限额
	private Money getDepositPaid(String projectCode, Boolean isEdit, long applyId) {
		//存出保证金
		Money depositPaid = Money.zero();
		FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = query(queryOrder);
		List<FCapitalAppropriationApplyInfo> applyInfoList = batchResult.getPageList();
		//编辑时 ,移除本次编辑的对象
		if (isEdit) {
			for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
				FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
					.next();
				if (fCapitalAppropriationApplyInfo.getApplyId() == applyId) {
					iterator.remove();
				}
			}
		}
		if (applyInfoList != null && applyInfoList.size() > 0) {
			
			for (Iterator iterator = applyInfoList.iterator(); iterator.hasNext();) {
				FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo = (FCapitalAppropriationApplyInfo) iterator
					.next();
				FormInfo form1 = formService.findByFormId(fCapitalAppropriationApplyInfo
					.getFormId());
				if (form1.getStatus().code().equals("DENY")) {//移除已经审核不通过的申请单
					iterator.remove();
				}
			}
		}
		if (applyInfoList != null && applyInfoList.size() > 0) {
			for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList) {
				List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
					.getApplyId());
				for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
					//存出保证金
					if (PaymentMenthodEnum.DEPOSIT_PAID.code().equals(
						fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
						depositPaid = depositPaid.add(fCapitalAppropriationApplyFeeInfo
							.getAppropriateAmount());
					}
				}
			}
		}
		//计算 放用款中 本次申请划付保证金金额
		
		return depositPaid;
	}
	
	@Override
	public FcsBaseResult saveReceipt(final FCapitalAppropriationApplyReceiptOrder order) {
		return commonProcess(order, "资金收付申请单-回执信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() == null || order.getId() <= 0) {
					//					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FCapitalAppropriationApplyReceiptDO receiptDO = new FCapitalAppropriationApplyReceiptDO();
					BeanCopier.staticCopy(order, receiptDO);
					receiptDO.setRawAddTime(now);
					long id = FCapitalAppropriationApplyReceiptDAO.insert(receiptDO);//主表id
					FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO
						.findByFormId(order.getFormId());
					applyDO.setIsReceipt(BooleanEnum.YES.code());
					FCapitalAppropriationApplyDAO.update(applyDO);
					sendMessageForm(applyDO.getFormId());
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					//更新
					Long id = order.getId();
					FCapitalAppropriationApplyReceiptDO receiptDO = FCapitalAppropriationApplyReceiptDAO
						.findById(id);
					if (null == receiptDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到资金划付申请单-回执记录");
					}
					BeanCopier.staticCopy(order, receiptDO);
					
					FCapitalAppropriationApplyReceiptDAO.update(receiptDO);//更新主表信息
					
				}
				FCapitalAppropriationApplyDO applyDO1 = FCapitalAppropriationApplyDAO
					.findByFormId(order.getFormId());
				//划付明细
				List<FCapitalAppropriationApplyFeeDO> ListFeeDO = FCapitalAppropriationApplyFeeDAO
					.findByApplyId(applyDO1.getApplyId());
				
				if ("FINANCIAL_PRODUCT".equals(applyDO1.getProjectType())) {//理财类项目
					if (applyDO1.getOutBizNo() > 0) { //转让回购划付
						ProjectFinancialTradeTansferOrder tOrder = new ProjectFinancialTradeTansferOrder();
						tOrder.setApplyId(applyDO1.getOutBizNo());
						tOrder.setIsConfirm(BooleanEnum.YES);
						financialProjectTransferService.saveTrade(tOrder);
						
						financialProjectService.changeStatus(applyDO1.getProjectCode(),
							FinancialProjectStatusEnum.BUY_BACK_APPLY_APPROVAL);
					} else { //立项购买划付
						ProjectFinancialDO financialProject = projectFinancialDAO
							.findByCode(applyDO1.getProjectCode());
						if (financialProject != null) {
							//申请的金额
							List<FCapitalAppropriationApplyFeeDO> feeList = FCapitalAppropriationApplyFeeDAO
								.findByApplyId(applyDO1.getApplyId());
							Money applyAmount = Money.zero();
							if (ListUtil.isNotEmpty(feeList)) {
								for (FCapitalAppropriationApplyFeeDO fee : feeList) {
									applyAmount.addTo(fee.getAppropriateAmount());
								}
							}
							//计算实际能购买的份额
							long actualBuyNum = applyAmount.getCent()
												/ financialProject.getPrice().getCent();
							financialProject.setActualBuyNum(actualBuyNum);
							financialProject
								.setStatus(FinancialProjectStatusEnum.CAPITAL_APPLY_APPROVAL.code());
							projectFinancialDAO.update(financialProject);
						}
					}
				} else {//非理财类项目
					ProjectDO project = projectDAO.findByProjectCode(applyDO1.getProjectCode());
					Money selfDepositAmount = project.getSelfDepositAmount();//原有自家保证金
					Money customerDepositAmount = project.getCustomerDepositAmount();//原有客户保证金
					Money compPrincipalAmount = project.getCompPrincipalAmount();//原有已代偿本金
					Money compInterestAmount = project.getCompInterestAmount();//原有已代偿利息
					Money refundAmount = project.getRefundAmount();//原有已退费金额
					
					for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : ListFeeDO) {
						//存出保证金
						if (fCapitalAppropriationApplyFeeDO.getAppropriateReason().equals(
							PaymentMenthodEnum.DEPOSIT_PAID.code())) {
							selfDepositAmount = selfDepositAmount
								.add(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
						}
						//退还客户保证金
						if (fCapitalAppropriationApplyFeeDO.getAppropriateReason().equals(
							PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code())) {
							customerDepositAmount = customerDepositAmount
								.subtract(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
						}
						//代偿本金
						if (fCapitalAppropriationApplyFeeDO.getAppropriateReason().equals(
							PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code())) {
							compPrincipalAmount = compPrincipalAmount
								.add(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
							//在资金划付申请单中已经做了代偿本金/代偿利息，并且审核通过后，状态更改为追偿中
							project.setPhases(ProjectPhasesEnum.RECOVERY_PHASES.code());
							project.setStatus(ProjectStatusEnum.RECOVERY.code());
						}
						
						//代偿利息
						if (fCapitalAppropriationApplyFeeDO.getAppropriateReason().equals(
							PaymentMenthodEnum.COMPENSATORY_INTEREST.code())) {
							compInterestAmount = compInterestAmount
								.add(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
							project.setPhases(ProjectPhasesEnum.RECOVERY_PHASES.code());
							project.setStatus(ProjectStatusEnum.RECOVERY.code());
						}
						//退费
						if (fCapitalAppropriationApplyFeeDO.getAppropriateReason().equals(
							PaymentMenthodEnum.REFUND.code())) {
							refundAmount = refundAmount.subtract(fCapitalAppropriationApplyFeeDO
								.getAppropriateAmount());
						}
						
					}
					project.setCompPrincipalAmount(compPrincipalAmount);
					project.setCompInterestAmount(compInterestAmount);
					project.setSelfDepositAmount(selfDepositAmount);
					project.setCustomerDepositAmount(customerDepositAmount);
					project.setRefundAmount(refundAmount);
					projectDAO.update(project); //更新项目信息
					
				}
				//保存回执的时候，将信息同步到资金系统
				asynchronousDataToFundSys(order.getFormId(), applyDO1, ListFeeDO);
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public FCapitalAppropriationApplyReceiptInfo findByReceiptFormId(long formId) {
		FCapitalAppropriationApplyReceiptDO DO = FCapitalAppropriationApplyReceiptDAO
			.findByFormId(formId);
		return convertDO2ReceiptInfo(DO);
	}
	
	// 发送消息
	public void sendMessageForm(long formId) {
		FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setFormId(formId);
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = query(queryOrder);
		List<FCapitalAppropriationApplyInfo> listInfo = batchResult.getPageList();
		if (listInfo != null) {
			for (FCapitalAppropriationApplyInfo info : listInfo) {
				
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				StringBuffer sb = new StringBuffer();
				String url = CommonUtil
					.getRedirectUrl("/pprojectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm")
								+ "?formId=" + formId;
				sb.append("您发起项目编号" + info.getProjectCode());
				sb.append("，项目名称" + info.getProjectName() + "的资金划付申请单财务已上传回执,");
				sb.append("<a href='" + url + "'>查看详情</a>");
				String content = sb.toString();
				messageOrder.setMessageContent(content);
				List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
				SimpleUserInfo user = new SimpleUserInfo();
				
				user.setUserAccount(info.getFormUserAccount());
				user.setUserId(info.getFormUserId());
				user.setUserName(info.getFormUserName());
				sendUserList.add(user);
				messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
					.toArray(new SimpleUserInfo[sendUserList.size()]));
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}
	
	/**
	 * see line 238 //XXX 异步同步单据到资金系统
	 * @param objects
	 * @see com.born.fcs.pm.biz.service.common.AsynchronousService#execute(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object[] objects) {
		try {
			FormInfo formInfo = (FormInfo) objects[0];
			FCapitalAppropriationApplyDO applyDO = (FCapitalAppropriationApplyDO) objects[1];
			List<FCapitalAppropriationApplyFeeDO> feeDOs = (List<FCapitalAppropriationApplyFeeDO>) objects[2];
			ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
			BeanCopier.staticCopy(formInfo, rpOrder);
			BeanCopier.staticCopy(applyDO, rpOrder);
			
			rpOrder.setSourceForm(SourceFormEnum.CAPITAL_APPROPRIATION);
			rpOrder.setSourceFormId(formInfo.getFormId());
			rpOrder.setSourceFormSys(SystemEnum.PM);
			
			//理财项目相关划付
			if ("FINANCIAL_PRODUCT".equals(applyDO.getProjectType())) {
				ProjectFinancialInfo fp = financialProjectService.queryByCode(applyDO
					.getProjectCode());
				if (fp != null)
					rpOrder.setProjectName(fp.getProductName());
				//						if (applyDO.getOutBizNo() > 0) {
				//							rpOrder.setRemark("理财产品回购划付");
				//						} else {
				//							rpOrder.setRemark("理财产品购买划付");
				//						}
			} else { //授信业务相关划付
				ProjectInfo project = projectService.queryByCode(applyDO.getProjectCode(), false);
				if (project != null) {
					rpOrder.setContractNo(project.getContractNo());
					rpOrder.setCustomerId(project.getCustomerId());
					rpOrder.setCustomerName(project.getCustomerName());
				}
			}
			
			//费用明细
			List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
			for (FCapitalAppropriationApplyFeeDO fee : feeDOs) {
				ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
				feeOrder.setFeeType(SubjectCostTypeEnum.getByPayFeeType(PaymentMenthodEnum
					.getByCode(fee.getAppropriateReason())));
				feeOrder.setAmount(fee.getAppropriateAmount());
				feeOrder.setRemark(fee.getRemark());
				feeOrderList.add(feeOrder);
			}
			rpOrder.setFeeOrderList(feeOrderList);
			receiptPaymentFormServiceClient.add(rpOrder);
		} catch (Exception e) {
			logger.error("发送资金划付数据到资金系统出错：{}", e);
		}
	}
	
	private void asynchronousDataToFundSys(long formId, FCapitalAppropriationApplyDO applyDO,
											List<FCapitalAppropriationApplyFeeDO> ListFeeDO) {
		FormInfo formInfo = formService.findByFormId(formId);
		
		//XXX 异步同步单据到资金系统
		logger.info("添加资金划付数据到资金系统异步任务");
		asynchronousTaskJob.addAsynchronousService(this, new Object[] { formInfo, applyDO,
																		ListFeeDO });
	}
}
