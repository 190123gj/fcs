package com.born.fcs.pm.biz.service.capitalappropriationapply;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.ChargeNoticeCapitalApproproationDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeCompensatoryChannelDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyPayeeDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyReceiptDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyTransferDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.CapitalAppropriationApplyFormDO;
import com.born.fcs.pm.dataobject.CapitalExportDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.CapitalExportInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeCompensatoryChannelInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyPayeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyReceiptInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyTransferInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyFeeCompensatoryChannelOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyFeeOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyPayeeOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyReceiptOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyTransferOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeNoticeCapitalApproproationOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
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
	FinancialProjectSetupService financialProjectSetupService;
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
	ForecastService forecastServiceClient;
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	@Autowired
	FinanceAffirmService financeAffirmService;
	@Autowired
	CommonAttachmentService commonAttachmentService;//附件
	
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
	
	private FFinanceAffirmInfo convertDO2FinanceAffirmInfo(FFinanceAffirmDO DO) {
		if (DO == null)
			return null;
		FFinanceAffirmInfo info = new FFinanceAffirmInfo();
		BeanCopier.staticCopy(DO, info);
		info.setAffirmFormType(FinanceAffirmTypeEnum.getByCode(DO.getAffirmFormType()));
		return info;
	}
	
	private FCapitalAppropriationApplyFeeCompensatoryChannelInfo convertDO2CompensatoryChannelInfo(	FCapitalAppropriationApplyFeeCompensatoryChannelDO DO) {
		if (DO == null)
			return null;
		FCapitalAppropriationApplyFeeCompensatoryChannelInfo info = new FCapitalAppropriationApplyFeeCompensatoryChannelInfo();
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
		FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(formId);
		if (affirmDO != null) {
			List<FFinanceAffirmDetailDO> listDetailDO = fFinanceAffirmDetailDAO
				.findByAffirmId(affirmDO.getAffirmId());
			List<FFinanceAffirmDetailInfo> listDetailInfo = Lists.newArrayList();
			if (listDetailDO != null && listDetailDO.size() > 0) {
				for (FFinanceAffirmDetailDO fFinanceAffirmDetailDO : listDetailDO) {
					FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
					BeanCopier.staticCopy(fFinanceAffirmDetailDO, detailInfo);
					detailInfo.setMenthodType(PaymentMenthodEnum.getByCode(fFinanceAffirmDetailDO
						.getFeeType()));
					listDetailInfo.add(detailInfo);
				}
			}
			FFinanceAffirmInfo affirmInfo = convertDO2FinanceAffirmInfo(affirmDO);
			affirmInfo.setFeeList(listDetailInfo);
			info.setFinanceAffirmInfo(affirmInfo);
		}
		List<FCapitalAppropriationApplyFeeDO> feeList = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(DO.getApplyId());
		if (feeList != null && feeList.size() > 0) {
			for (FCapitalAppropriationApplyFeeDO feeDO : feeList) {
				if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
					feeDO.getAppropriateReason())) {
					List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
					List<ChargeNoticeCapitalApproproationDO> approproationDOs = chargeNoticeCapitalApproproationDAO
						.findByTypeAndDetailId(
							FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY.code(), feeDO.getId());
					for (ChargeNoticeCapitalApproproationDO approproationDO : approproationDOs) {
						FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
						FFinanceAffirmDetailDO detailDO = fFinanceAffirmDetailDAO.findById(Long
							.parseLong(approproationDO.getPayId()));
						BeanCopier.staticCopy(detailDO, detailInfo);
						detailInfo.setMenthodType(PaymentMenthodEnum.getByCode(detailDO
							.getFeeType()));
						detailInfo.setUseAmount(approproationDO.getUseAmount());
						detailInfos.add(detailInfo);
					}
					info.setDetailInfos(detailInfos);
				}
			}
			
		}
		
		//资金调动信息
		List<FCapitalAppropriationApplyTransferDO> transferDOs = FCapitalAppropriationApplyTransferDAO
			.findByFormId(formId);
		if (ListUtil.isNotEmpty(transferDOs)) {
			List<FCapitalAppropriationApplyTransferInfo> transferInfos = Lists.newArrayList();
			for (FCapitalAppropriationApplyTransferDO tansferDO : transferDOs) {
				FCapitalAppropriationApplyTransferInfo transferInfo = new FCapitalAppropriationApplyTransferInfo();
				BeanCopier.staticCopy(tansferDO, transferInfo);
				transferInfos.add(transferInfo);
			}
			info.setTransferInfos(transferInfos);
		}
		
		return info;
	}
	
	@Override
	public FormBaseResult save(final FCapitalAppropriationApplyOrder order) {
		return commonFormSaveProcess(order, "资金收付申请单", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				
				if (!StringUtil.equals(order.getIsSimple(), "IS")) {
					order.setIsSimple("NO");
				}
				
				String formIdStr = "";
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (order.getApplyId() == null || order.getApplyId() <= 0) {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FCapitalAppropriationApplyDO fCapitalAppropriationApplyDO = new FCapitalAppropriationApplyDO();
					formIdStr = formInfo.getFormId() + "";
					BeanCopier.staticCopy(order, fCapitalAppropriationApplyDO);
					fCapitalAppropriationApplyDO.setFormId(formInfo.getFormId());
					fCapitalAppropriationApplyDO.setProjectType(order.getProjectType());
					fCapitalAppropriationApplyDO.setRawAddTime(now);
					fCapitalAppropriationApplyDO.setAttach(null);
					fCapitalAppropriationApplyDO.setOutBizNo(order.getOutBizNo());
					//					fCapitalAppropriationApplyDO.setAmount(new Money(order.getAmount()));
					fCapitalAppropriationApplyDO.setIsReceipt(BooleanEnum.NO.code());
					if (order.getProjectType() != null
						&& order.getProjectType().equals(
							FCapitalAppropriationApplyTypeEnum.NOT_FINANCIAL_PRODUCT.code())) {
						ProjectDO project = projectDAO.findByProjectCode(order.getProjectCode());
						fCapitalAppropriationApplyDO.setBusiType(project.getBusiType());
						fCapitalAppropriationApplyDO.setBusiTypeName(project.getBusiTypeName());
						fCapitalAppropriationApplyDO.setCustomerId(project.getCustomerId());
						fCapitalAppropriationApplyDO.setCustomerName(project.getCustomerName());
					} else {//理财无客户信息和业务类型
						fCapitalAppropriationApplyDO.setBusiType("-1");
						fCapitalAppropriationApplyDO.setBusiTypeName("理财产品");
					}
					
					long id = FCapitalAppropriationApplyDAO.insert(fCapitalAppropriationApplyDO);//主表id
					FCapitalAppropriationApplyPayeeOrder payeeOrder = order.getPayeeOrder();
					if (payeeOrder != null) {
						FCapitalAppropriationApplyPayeeDO payeeDO = new FCapitalAppropriationApplyPayeeDO();
						BeanCopier.staticCopy(payeeOrder, payeeDO);
						payeeDO.setApplyId(id);
						payeeDO.setRawAddTime(now);
						FCapitalAppropriationApplyPayeeDAO.insert(payeeDO);
					}
					List<FCapitalAppropriationApplyFeeOrder> feeOrder = order
						.getfCapitalAppropriationApplyFeeOrders();
					if (ListUtil.isNotEmpty(feeOrder)) {
						for (FCapitalAppropriationApplyFeeOrder fCapitalAppropriationApplyFeeOrder : feeOrder) {
							FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO = new FCapitalAppropriationApplyFeeDO();
							if (fCapitalAppropriationApplyFeeOrder != null) {
								BeanCopier.staticCopy(fCapitalAppropriationApplyFeeOrder,
									fCapitalAppropriationApplyFeeDO);
								fCapitalAppropriationApplyFeeDO.setApplyId(id);//设置副表依赖主表id
								fCapitalAppropriationApplyFeeDO.setRawAddTime(now);
								fCapitalAppropriationApplyFeeDO
									.setAppropriateAmount(new Money(
										fCapitalAppropriationApplyFeeOrder.getAppropriateAmount() == null ? 0.00
											: fCapitalAppropriationApplyFeeOrder
												.getAppropriateAmount()));
								fCapitalAppropriationApplyFeeDO
									.setFinanceAffirmDetailId(fCapitalAppropriationApplyFeeOrder
										.getFinanceAffirmDetailId());//财务确认明细ID
								
								Long detailId = FCapitalAppropriationApplyFeeDAO
									.insert(fCapitalAppropriationApplyFeeDO);
								List<FCapitalAppropriationApplyFeeCompensatoryChannelOrder> listCompensatoryChannelOrder = fCapitalAppropriationApplyFeeOrder
									.getCompensatoryChannelOrders();
								//								FCapitalAppropriationApplyFeeCompensatoryChannelDAO
								//									.deleteByFeeId(detailId);//先删除代偿渠道明细
								if (ListUtil.isNotEmpty(listCompensatoryChannelOrder)) {
									for (FCapitalAppropriationApplyFeeCompensatoryChannelOrder channelOrder : listCompensatoryChannelOrder) {
										FCapitalAppropriationApplyFeeCompensatoryChannelDO compensatoryChannelDO = new FCapitalAppropriationApplyFeeCompensatoryChannelDO();
										BeanCopier.staticCopy(channelOrder, compensatoryChannelDO);
										compensatoryChannelDO.setFormId(formInfo.getFormId());
										compensatoryChannelDO.setFeeId(detailId);
										FCapitalAppropriationApplyFeeCompensatoryChannelDAO
											.insert(compensatoryChannelDO);
									}
								}
								if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
									fCapitalAppropriationApplyFeeOrder.getAppropriateReason())) {
									
									//updateAffirmAmount(feeOrder.getAffirmDetailIds(),null,"add");
									setChargeCapitalApproproation(order.getProjectCode(),
										fCapitalAppropriationApplyFeeDO, detailId,
										fCapitalAppropriationApplyFeeOrder
											.getFinanceAffirmDetailId());
								}
							}
							
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
					formIdStr = applyDO.getFormId() + "";
					BeanCopier.staticCopy(order, applyDO);
					applyDO.setProjectType(order.getProjectType());
					FCapitalAppropriationApplyDAO.update(applyDO);//更新主表信息
					
					FCapitalAppropriationApplyPayeeOrder payeeOrder = order.getPayeeOrder();
					if (payeeOrder != null) {
						FCapitalAppropriationApplyPayeeDO payeeDO = FCapitalAppropriationApplyPayeeDAO
							.findByApplyId(applyDO.getApplyId());
						BeanCopier.staticCopy(payeeOrder, payeeDO);
						payeeDO.setApplyId(id);
						FCapitalAppropriationApplyPayeeDAO.update(payeeDO);
					}
					List<FCapitalAppropriationApplyFeeOrder> listFeeOrder = order
						.getfCapitalAppropriationApplyFeeOrders();
					if (ListUtil.isNotEmpty(listFeeOrder)) {
						for (FCapitalAppropriationApplyFeeOrder fCapitalAppropriationApplyFeeOrder2 : listFeeOrder) {
							if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
								fCapitalAppropriationApplyFeeOrder2.getAppropriateReason())) {
								chargeNoticeCapitalApproproationDAO.deleteByTypeAndDetailId(
									"CAPITAL_APPROPROATION_APPLY",
									fCapitalAppropriationApplyFeeOrder2.getId() == null ? 0
										: fCapitalAppropriationApplyFeeOrder2.getId());
							}
						}
					}
					if (ListUtil.isNotEmpty(listFeeOrder)) {
						//删除先
						FCapitalAppropriationApplyFeeDAO.deleteByApplyId(applyDO.getApplyId());
						FCapitalAppropriationApplyFeeCompensatoryChannelDAO.deleteByFormId(order
							.getFormId());//先删除代偿渠道明细
						
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
							applyFeeDO.setFormChange(fCapitalAppropriationApplyFeeOrder
								.getFormChange());
							applyFeeDO.setProjectApproval(fCapitalAppropriationApplyFeeOrder
								.getProjectApproval());
							applyFeeDO.setRiskCouncilSummary(fCapitalAppropriationApplyFeeOrder
								.getRiskCouncilSummary());
							applyFeeDO.setFinanceAffirmDetailId(fCapitalAppropriationApplyFeeOrder
								.getFinanceAffirmDetailId());
							applyFeeDO.setComType(fCapitalAppropriationApplyFeeOrder.getComType());
							
							Long detailId = FCapitalAppropriationApplyFeeDAO.insert(applyFeeDO);
							List<FCapitalAppropriationApplyFeeCompensatoryChannelOrder> listCompensatoryChannelOrder = fCapitalAppropriationApplyFeeOrder
								.getCompensatoryChannelOrders();
							
							if (ListUtil.isNotEmpty(listCompensatoryChannelOrder)) {
								for (FCapitalAppropriationApplyFeeCompensatoryChannelOrder channelOrder : listCompensatoryChannelOrder) {
									FCapitalAppropriationApplyFeeCompensatoryChannelDO compensatoryChannelDO = new FCapitalAppropriationApplyFeeCompensatoryChannelDO();
									BeanCopier.staticCopy(channelOrder, compensatoryChannelDO);
									compensatoryChannelDO.setFormId(order.getFormId());
									compensatoryChannelDO.setFeeId(detailId);
									FCapitalAppropriationApplyFeeCompensatoryChannelDAO
										.insert(compensatoryChannelDO);
								}
							}
							if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
								fCapitalAppropriationApplyFeeOrder.getAppropriateReason())) {
								//updateAffirmAmount(feeOrder.getAffirmDetailIds(),null,"add");
								setChargeCapitalApproproation(order.getProjectCode(), applyFeeDO,
									detailId,
									fCapitalAppropriationApplyFeeOrder.getFinanceAffirmDetailId());
							}
						}
						
					}
					
				}
				
				//资金调动信息
				List<FCapitalAppropriationApplyTransferOrder> transferOrders = order
					.getTransferOrder();
				//删掉之前的，保存最新的
				FCapitalAppropriationApplyTransferDAO.deleteByFormId(formInfo.getFormId());
				if (ListUtil.isNotEmpty(transferOrders)) {
					for (FCapitalAppropriationApplyTransferOrder transferOrder : transferOrders) {
						if (transferOrder.isNull())
							continue;
						FCapitalAppropriationApplyTransferDO transferDO = new FCapitalAppropriationApplyTransferDO();
						BeanCopier.staticCopy(transferOrder, transferDO);
						transferDO.setFormId(formInfo.getFormId());
						FCapitalAppropriationApplyTransferDAO.insert(transferDO);
					}
				}
				
				//保存附件
				if (StringUtil.isNotEmpty(order.getAttach())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder.setBizNo("PM_" + formIdStr);
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getAttach());
					attachOrder.setRemark("资金划付申请单-表单附件");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
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
			if (order.getFormId() != null && order.getFormId() > 0) {
				fCapitalAppropriationApplyDO.setFormId(order.getFormId());
			}
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
					if (info.getFormId() > 0) {
						FFinanceAffirmDO affirmDO = fFinanceAffirmDAO
							.findByFormId(info.getFormId());
						if (affirmDO != null) {
							info.setActualPayAmount(affirmDO.getAmount());
						} else {
							info.setActualPayAmount(Money.zero());
						}
					} else {
						info.setActualPayAmount(Money.zero());
					}
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
		return null;
	}
	
	@Override
	public List<FCapitalAppropriationApplyFeeInfo> findByApplyId(long applyId) {
		List<FCapitalAppropriationApplyFeeInfo> listFCapitalAppropriationApplyFeeInfo = new ArrayList<FCapitalAppropriationApplyFeeInfo>();
		List<FCapitalAppropriationApplyFeeDO> listFCapitalAppropriationApplyFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyId);
		for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : listFCapitalAppropriationApplyFeeDO) {
			FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo = convertFCapitalAppropriationApplyFeeDO2Info(fCapitalAppropriationApplyFeeDO);
			List<FCapitalAppropriationApplyFeeCompensatoryChannelDO> listCompensatoryChannelDO = FCapitalAppropriationApplyFeeCompensatoryChannelDAO
				.findByFeeId(fCapitalAppropriationApplyFeeDO.getId());
			List<FCapitalAppropriationApplyFeeCompensatoryChannelInfo> listChannelInfo = Lists
				.newArrayList();
			if (ListUtil.isNotEmpty(listCompensatoryChannelDO)) {
				for (FCapitalAppropriationApplyFeeCompensatoryChannelDO channelDO : listCompensatoryChannelDO) {
					FCapitalAppropriationApplyFeeCompensatoryChannelInfo channelInfo = convertDO2CompensatoryChannelInfo(channelDO);
					listChannelInfo.add(channelInfo);
				}
			}
			fCapitalAppropriationApplyFeeInfo.setCompensatoryChannelInfos(listChannelInfo);
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
	
	@SuppressWarnings("rawtypes")
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
					if (form1.getStatus().code().equals("END")) {//移除作废单据
						iterator.remove();
					}
				}
			}
			if (applyInfoList != null && applyInfoList.size() > 0) {
				for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : applyInfoList) {
					FFinanceAffirmDO affirmDO = fFinanceAffirmDAO
						.findByFormId(fCapitalAppropriationApplyInfo.getFormId());//财务确认信息
					List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
						.getApplyId());
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						if (affirmDO == null) {
							//退还客户保证金
							if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code().equals(
								fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
								
								customerDepositRefund = customerDepositRefund
									.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
								
							}
							//委贷放款
							if (PaymentMenthodEnum.COMMISSION_LOAN.code().equals(
								fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
								commissionLoan = commissionLoan
									.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
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
						} else {
							FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(affirmDO.getAffirmId(),
									fCapitalAppropriationApplyFeeInfo.getId());
							if (detialDO != null) {
								//退还客户保证金
								if (PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									
									customerDepositRefund = customerDepositRefund.add(detialDO
										.getPayAmount());
									
								}
								//委贷放款
								if (PaymentMenthodEnum.COMMISSION_LOAN.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									commissionLoan = commissionLoan.add(detialDO.getPayAmount());
								}
								//代偿本金
								if (PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									compensatoryPrincipal = compensatoryPrincipal.add(detialDO
										.getPayAmount());
								}
								//代偿利息
								if (PaymentMenthodEnum.COMPENSATORY_INTEREST.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									compensatoryInterest = compensatoryInterest.add(detialDO
										.getPayAmount());
								}
								//退费
								if (PaymentMenthodEnum.REFUND.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									refundAmount = refundAmount.add(detialDO.getPayAmount());
								}
								//资产受让
								if (PaymentMenthodEnum.OTHER_ASSET_SWAP.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									assetsTransferee = assetsTransferee
										.add(detialDO.getPayAmount());
								}
							}
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
				FFinanceAffirmDO affirmDO = fFinanceAffirmDAO
					.findByFormId(fCapitalAppropriationApplyInfo.getFormId());//财务确认信息
				List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
					.getApplyId());
				if (fCapitalAppropriationApplyFeeInfoList != null) {
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						if (affirmDO == null) {
							//资产受让
							if (PaymentMenthodEnum.OTHER_ASSET_SWAP.code().equals(
								fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
								assetsTransferee = assetsTransferee
									.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
							}
							//委贷放款
							if (PaymentMenthodEnum.COMMISSION_LOAN.code().equals(
								fCapitalAppropriationApplyFeeInfo.getAppropriateReason().code())) {
								commissionLoan = commissionLoan
									.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
							}
						} else {
							FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(affirmDO.getAffirmId(),
									fCapitalAppropriationApplyFeeInfo.getId());
							if (detialDO != null) {
								//资产受让
								if (PaymentMenthodEnum.OTHER_ASSET_SWAP.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									assetsTransferee = assetsTransferee
										.add(detialDO.getPayAmount());
								}
								//委贷放款
								if (PaymentMenthodEnum.COMMISSION_LOAN.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									commissionLoan = commissionLoan.add(detialDO.getPayAmount());
								}
							}
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
						//						riskHandelCISum = riskHandelCISum.add(fCouncilSummaryRiskHandleInfo
						//							.getCompInterest());
					}
					
				}
			}
			//委贷放款  金额不能超过目前可放款的总额
			//			if (projectInfo.getIsRedoProject() == BooleanEnum.IS) {//重新授信项目不限制
			list.add(projectInfo.getLoanedAmount().subtract(commissionLoan));
			//			} else {
			//				list.add(projectInfo.getContractAmount().subtract(commissionLoan));
			//			}
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
			FProjectFinancialInfo projectFinancialInfo = financialProjectSetupService
				.queryByProjectCode(projectCode);
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
					FFinanceAffirmDO affirmDO = fFinanceAffirmDAO
						.findByFormId(fCapitalAppropriationApplyInfo.getFormId());//财务确认信息
					List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = findByApplyId(fCapitalAppropriationApplyInfo
						.getApplyId());
					for (FCapitalAppropriationApplyFeeInfo fCapitalAppropriationApplyFeeInfo : fCapitalAppropriationApplyFeeInfoList) {
						if (affirmDO == null) {
							if (PaymentMenthodEnum.FINANCIAL_PRODUCT.code() != null
								&& PaymentMenthodEnum.FINANCIAL_PRODUCT.code()
									.equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
								financialProduct = financialProduct
									.add(fCapitalAppropriationApplyFeeInfo.getAppropriateAmount());
							}
						} else {
							FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(affirmDO.getAffirmId(),
									fCapitalAppropriationApplyFeeInfo.getId());
							if (detialDO != null) {
								if (PaymentMenthodEnum.FINANCIAL_PRODUCT.code() != null
									&& PaymentMenthodEnum.FINANCIAL_PRODUCT.code().equals(
										fCapitalAppropriationApplyFeeInfo.getAppropriateReason()
											.code())) {
									financialProduct = financialProduct
										.add(detialDO.getPayAmount());
								}
							}
						}
					}
				}
			}
			list.add(projectFinancialInfo.getPrice()
				.multiply(projectFinancialInfo.getExpectBuyNum()).subtract(financialProduct));
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
				
				final Date now = FcsPmDomainHolder.get().getSysDate();
				
				if (order.getId() == null || order.getId() <= 0) {
					//					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
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
					} else {
						
						//立项购买划付
						List<FCapitalAppropriationApplyFeeDO> feeList = FCapitalAppropriationApplyFeeDAO
							.findByApplyId(applyDO1.getApplyId());
						Money applyAmount = Money.zero();
						if (ListUtil.isNotEmpty(feeList)) {
							for (FCapitalAppropriationApplyFeeDO fee : feeList) {
								applyAmount.addTo(fee.getAppropriateAmount());
							}
						}
						FcsBaseResult initResult = financialProjectService.initPorjectRecord(
							applyDO1.getProjectCode(), applyAmount);
						if (!initResult.isSuccess()) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"生成理财产品购买记录出错");
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
					
					//客户保证金变化
					if (customerDepositAmount.greaterThan(ZERO_MONEY)) {
						projectService.syncForecastDeposit(project.getProjectCode());
					}
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
					.getRedirectUrl("/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm")
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
	 * see line 238 异步同步单据到资金系统
	 * @param objects
	 * @see com.born.fcs.pm.biz.service.common.AsynchronousService#execute(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object[] objects) {
		try {
			ProjectInfo project = null;
			FormInfo formInfo = (FormInfo) objects[0];
			FCapitalAppropriationApplyDO applyDO = (FCapitalAppropriationApplyDO) objects[1];
			List<FCapitalAppropriationApplyFeeDO> feeDOs = (List<FCapitalAppropriationApplyFeeDO>) objects[2];
			ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
			BeanCopier.staticCopy(formInfo, rpOrder);
			BeanCopier.staticCopy(applyDO, rpOrder);
			
			rpOrder.setSourceForm(SourceFormEnum.CAPITAL_APPROPRIATION);
			rpOrder.setSourceFormId(String.valueOf(formInfo.getFormId()));
			rpOrder.setSourceFormSys(SystemEnum.PM);
			rpOrder.setIsSimple(applyDO.getIsSimple());
			
			//理财项目相关划付
			if ("FINANCIAL_PRODUCT".equals(applyDO.getProjectType())) {
				ProjectFinancialInfo fp = financialProjectService.queryByCode(applyDO
					.getProjectCode());
				if (fp != null)
					rpOrder.setProjectName(fp.getProductName());
				if (applyDO.getOutBizNo() > 0) {
					rpOrder.setRemark("理财产品回购划付");
				} else {
					rpOrder.setRemark("理财产品购买划付");
				}
			} else { //授信业务相关划付
				project = projectService.queryByCode(applyDO.getProjectCode(), false);
				if (project != null) {
					rpOrder.setContractNo(project.getContractNo());
					rpOrder.setCustomerId(project.getCustomerId());
					rpOrder.setCustomerName(project.getCustomerName());
				}
			}
			
			//代偿金额
			Money compAmount = Money.zero();
			//费用明细
			List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
			for (FCapitalAppropriationApplyFeeDO fee : feeDOs) {
				ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
				feeOrder.setFeeType(SubjectCostTypeEnum.getByPayFeeType(PaymentMenthodEnum
					.getByCode(fee.getAppropriateReason())));
				feeOrder.setAmount(fee.getAppropriateAmount());
				feeOrder.setRemark(fee.getRemark());
				feeOrderList.add(feeOrder);
				
				PaymentMenthodEnum feeType = PaymentMenthodEnum.getByCode(fee
					.getAppropriateReason());
				if (feeType == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL
					|| feeType == PaymentMenthodEnum.COMPENSATORY_INTEREST
					|| feeType == PaymentMenthodEnum.COMPENSATORY_PENALTY
					|| feeType == PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES
					|| feeType == PaymentMenthodEnum.COMPENSATORY_OTHER) {
					compAmount.addTo(fee.getAppropriateAmount());
				}
			}
			rpOrder.setFeeOrderList(feeOrderList);
			receiptPaymentFormServiceClient.add(rpOrder);
			
			//同步预测数据
			if (project != null && compAmount.greaterThan(ZERO_MONEY)) {
				
				//查询预测规则  没查询到用默认规则
				SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
				SysForecastParamResult result = forecastServiceClient.findAll();
				if (result != null && result.isSuccess()) {
					rule = result.getParamAllInfo();
				}
				TimeUnitEnum forcastTimeType = rule.getInDckshTimeType();
				int forcastTime = NumberUtil.parseInt(rule.getInDckshTime());
				Calendar calendar = Calendar.getInstance();
				if (forcastTimeType == TimeUnitEnum.YEAR) {
					calendar.add(Calendar.YEAR, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.MONTH) {
					calendar.add(Calendar.MONTH, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.DAY) {
					calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
				}
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(compAmount);
				forecastAccountOrder.setForecastMemo("代偿款收回（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_DCKSH);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.COMPBACK.code() + "_"
												+ applyDO.getApplyId());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.COMPBACK);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				logger.info("代偿款收回资金流入预测,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
		} catch (Exception e) {
			logger.error("发送资金划付数据到资金系统出错：{}", e);
		}
	}
	
	private void asynchronousDataToFundSys(long formId, FCapitalAppropriationApplyDO applyDO,
											List<FCapitalAppropriationApplyFeeDO> ListFeeDO) {
		FormInfo formInfo = formService.findByFormId(formId);
		logger.info("添加资金划付数据到资金系统异步任务");
		asynchronousTaskJob.addAsynchronousService(this, new Object[] { formInfo, applyDO,
																		ListFeeDO });
	}
	
	/**
	 * 保存到收费资金划付关联表
	 * @param
	 */
	
	private void setChargeCapitalApproproation(String projectCode,
												FCapitalAppropriationApplyFeeDO feeDO,
												Long detailId, String affirmDetailIds) {
		//		String affirmIds = "";
		//		
		//		if (StringUtil.isNotBlank(affirmDetailIds)) {
		//			String firms[] = affirmDetailIds.split(";");
		//			List<Long> ids = Lists.newArrayList();
		//			for (String firm : firms) {
		//				if (StringUtil.isNotBlank(firm)) {
		//					Long id = Long.parseLong(firm.split(",")[1]);
		//					ids.add(id);
		//				}
		//			}
		//			
		//			ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
		//			queryOrder.setPayIdList(ids);
		//			queryOrder.setProjectCode(projectCode);
		//			queryOrder.setAffirmFormType("CHARGE_NOTIFICATION");
		//			queryOrder.setFeeType("GUARANTEE_DEPOSIT");
		//			queryOrder.setSortCol("p.return_customer_amount");
		//			queryOrder.setSortOrder("ASC");
		//			QueryBaseBatchResult<ProjectChargePayInfo> baseBatchResult = financeAffirmService
		//				.queryProjectChargePayDetailChoose(queryOrder);
		//			Money total = Money.zero();
		//			if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
		//				for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
		//					total = total.add(payInfo.getReturnCustomerAmount());
		//				}
		//			}
		//			if (feeDO.getAppropriateAmount().greaterThan(total)) {
		//				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
		//					"所选存入保证金金额不足，不能提交!");
		//			} else {
		//				String detailIds = "";
		//				if (feeDO.getAppropriateAmount().equals(total)) {//如果相等说明所有的都用完了
		//					if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
		//						for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
		//							detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
		//										+ payInfo.getPayId() + ";";
		//						}
		//					}
		//					affirmDetailIds = detailIds.substring(0, detailIds.length() - 1);
		//				} else if (total.greaterThan(feeDO.getAppropriateAmount())) {//小于就重新分摊
		//					Money tempAmount = feeDO.getAppropriateAmount();
		//					for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
		//						Money tempAmount2 = tempAmount;
		//						tempAmount = tempAmount.subtract(payInfo.getReturnCustomerAmount());
		//						if (tempAmount.greaterThan(Money.zero()) || tempAmount.equals(Money.zero())) {//减完了的
		//							if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
		//								continue;
		//							}
		//							detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
		//										+ payInfo.getPayId() + ";";
		//						} else {//没用完直接存剩下的
		//							detailIds = detailIds + tempAmount2 + "," + payInfo.getPayId() + ";";
		//							break;
		//						}
		//					}
		//					affirmDetailIds = detailIds.substring(0, detailIds.length() - 1);
		//					affirmIds = affirmDetailIds;
		//				}
		//			}
		//		}
		ChargeCapitalOrder order = new ChargeCapitalOrder();
		List<ChargeNoticeCapitalApproproationOrder> itemOrders = Lists.newArrayList();
		order.setType(FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY);
		order.setDetailId(detailId);
		if (affirmDetailIds != null) {//affirmIds  收费通知单，财务确认的id
			String firms[] = affirmDetailIds.split(";");
			for (String firm : firms) {
				if (StringUtil.isNotBlank(firm)) {
					Long id = Long.parseLong(firm.split(",")[1]);
					Money firmAmount = new Money(firm.split(",")[0]);
					ChargeNoticeCapitalApproproationOrder itemOrder = new ChargeNoticeCapitalApproproationOrder();
					itemOrder.setPayId(id + "");
					itemOrder.setUseAmount(firmAmount);
					itemOrders.add(itemOrder);
				}
			}
			
			order.setItemOrder(itemOrders);
			financeAffirmService.saveChargeCapital(order);
		}
	}
	
	private Money sumAfirmMoney(String ids) {
		Money sum = Money.zero();
		if (ids != null && !"".equals(ids)) {
			String[] id1 = ids.split(",");
			for (int i = 0; i < id1.length; i++) {
				FFinanceAffirmDetailDO DO = fFinanceAffirmDetailDAO.findById(NumberUtil
					.parseLong(id1[i]));
				if (DO != null) {
					sum = sum.add(DO.getPayAmount());
				}
			}
		}
		return sum;
	}
	
	@Override
	public QueryBaseBatchResult<CapitalExportInfo> capitalExport(	FCapitalAppropriationApplyQueryOrder order) {
		QueryBaseBatchResult<CapitalExportInfo> baseBatchResult = new QueryBaseBatchResult<CapitalExportInfo>();
		try {
			//			CapitalExportDO capitalExportDO = new CapitalExportDO();
			Map<String, Object> paramMap = new HashMap<>();
			if (order.getProjectCode() != null) {
				paramMap.put("projectCode", order.getProjectCode());
			}
			if (order.getProjectType() != null) {
				paramMap.put("projectType", order.getProjectType());
			}
			if (order.getBusiTypeName() != null) {
				paramMap.put("busiTypeName", order.getBusiTypeName().trim());
			}
			if (order.getFormUserName() != null) {
				paramMap.put("formUserName", order.getFormUserName());
			}
			if (order.getFormStatus() != null) {
				paramMap.put("formStatus", order.getFormStatus());
			}
			if (order.getProjectName() != null) {
				paramMap.put("projectName", order.getProjectName());
			}
			paramMap.put("draftUserId", order.getDraftUserId());
			paramMap.put("loginUserId", order.getLoginUserId());
			paramMap.put("deptIdList", order.getDeptIdList());
			paramMap.put("isSimple", order.getIsSimple());
			long totalCount = busiDAO.capitalExportCount(paramMap);
			PageComponent component = new PageComponent(order, totalCount);
			if (totalCount > 0) {
				paramMap.put("limitStart", component.getFirstRecord());
				paramMap.put("pageSize", 99999L);
				//				paramMap.put("sortOrder", order.getSortCol());
				//				paramMap.put("sortCol", order.getSortOrder());
				
				List<CapitalExportDO> pageList = busiDAO.capitalExportList(paramMap);
				
				List<CapitalExportInfo> list = baseBatchResult.getPageList();
				
				for (CapitalExportDO sf : pageList) {
					CapitalExportInfo info = new CapitalExportInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					info.setProjectType(FCapitalAppropriationApplyTypeEnum.getByCode(sf
						.getProjectType()));
					info.setAppropriateReason(PaymentMenthodEnum.getByCode(sf
						.getAppropriateReason()));
					info.setTimeUnit(TimeUnitEnum.getByCode(sf.getTimeUnit()));
					list.add(info);
					
				}
				baseBatchResult.setSuccess(true);
				baseBatchResult.setPageList(list);
				baseBatchResult.initPageParam(component);
			}
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			logger.error("资金划付数据导出失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
}
