package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.dal.dataobject.*;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dataobject.ChargeNotificationExportDO;
import com.born.fcs.pm.dataobject.ChargeNotificationFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeNoticeCapitalApproproationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeNotificationService")
public class ChargeNotificationServiceImpl extends BaseFormAutowiredDomainService implements
																					ChargeNotificationService {

	@Autowired
	ChargeNotificationFeeService chargeNotificationFeeService;
	@Autowired
	ProjectService projectService;
	@Autowired
	FinanceAffirmService financeAffirmService;
	@Autowired
	CommonAttachmentService commonAttachmentService;

	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}

	@Override
	public FormBaseResult saveChargeNotification(final FChargeNotificationOrder order) {
		return commonFormSaveProcess(order, "保存收费通知", new BeforeProcessInvokeService() {

			@Override
			public Domain before() {
				//校验feeType是存出保证划回 时不能大于project表self_deposit_amount
				//委贷本金收回，委贷本金收回金额综合不能大于在保余额
				if (order.getProjectCode() != null
						&& order.getChargeBasis() == ChargeBasisEnum.PROJECT) {
					checkBackAmount(order);
				}
				String formIdStr = "";
				if (order.getNotificationId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FChargeNotificationDO fChargeNotificationDO = new FChargeNotificationDO();
					BeanCopier.staticCopy(order, fChargeNotificationDO);
					fChargeNotificationDO.setFormId(formInfo.getFormId());
					fChargeNotificationDO.setRawAddTime(now);
					fChargeNotificationDO.setChargeNo(genChargeNo());
					fChargeNotificationDO.setStatus(FormStatusEnum.DRAFT.code());
					fChargeNotificationDO.setChargeBasis(order.getChargeBasis().code());
					long notificationId = fChargeNotificationDAO.insert(fChargeNotificationDO);
					formIdStr = formInfo.getFormId() + "";
					List<FChargeNotificationFeeOrder> feeList = order.getFeeList();
					if (ListUtil.isNotEmpty(feeList)) {
						for (FChargeNotificationFeeOrder feeOrder : feeList) {
							FChargeNotificationFeeDO chargeNotificationFee = new FChargeNotificationFeeDO();
							BeanCopier.staticCopy(feeOrder, chargeNotificationFee);
							if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
								chargeNotificationFee.setStartTime(DateUtil.parse(feeOrder
										.getStartTimeStr()));
							}
							if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
								chargeNotificationFee.setEndTime(DateUtil.parse(feeOrder
										.getEndTimeStr()));
							}
							if (feeOrder.getFeeType() != null) {
								chargeNotificationFee.setFeeType(feeOrder.getFeeType().code());
								chargeNotificationFee.setFeeTypeDesc(feeOrder.getFeeType()
										.message());
							}
							Money chargeBase = feeOrder.getChargeBase();
							chargeNotificationFee.setChargeBase(chargeBase);
							chargeNotificationFee.setNotificationId(notificationId);
							chargeNotificationFee.setRawAddTime(now);
							Long detailId = fChargeNotificationFeeDAO.insert(chargeNotificationFee);
							if (feeOrder.getFeeType() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
								//updateAffirmAmount(feeOrder.getAffirmDetailIds(),null,"add");
								setChargeCapitalApproproation(detailId,
										feeOrder.getAffirmDetailIds());
							}
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
							"result");
					result.setKeyId(notificationId);
				} else {
					//更新
					FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO
							.findById(order.getNotificationId());
					if (null == fChargeNotificationDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未找到收费通知");
					}

					BeanCopier.staticCopy(order, fChargeNotificationDO);
					fChargeNotificationDAO.update(fChargeNotificationDO);
					updateFees(order, fChargeNotificationDO);
					formIdStr = fChargeNotificationDO.getFormId() + "";
				}
				//保存附件
				if (StringUtil.isNotEmpty(order.getAttachment())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder.setBizNo("PM_" + formIdStr);
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getAttachment());
					attachOrder.setRemark("收费通知单-委托付款说明");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				return null;
			}
		}, null, null);
	}

	/**
	 * 保存到收费资金划付关联表
	 *
	 * @param
	 */

	private void setChargeCapitalApproproation(Long detailId, String affirmDetailIds) {
		ChargeCapitalOrder order = new ChargeCapitalOrder();
		List<ChargeNoticeCapitalApproproationOrder> itemOrders = Lists.newArrayList();
		order.setType(FinanceAffirmTypeEnum.CHARGE_NOTIFICATION);
		order.setDetailId(detailId);
		if (affirmDetailIds == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "请选择相应的存出保证金");
		}
		String firms[] = affirmDetailIds.split(";");
		for (String firm : firms) {
			if (StringUtil.isNotBlank(firm)) {
				Long id = StringUtil.isNotBlank(firm.split(",")[1]) ? Long.parseLong(firm.split(",")[1]) : 0;
				Money firmAmount = StringUtil.isNotBlank(firm.split(",")[0]) ? new Money(firm.split(",")[0]) : Money.zero();
				ChargeNoticeCapitalApproproationOrder itemOrder = new ChargeNoticeCapitalApproproationOrder();
				itemOrder.setPayId(id + "");
				itemOrder.setUseAmount(firmAmount);
				itemOrders.add(itemOrder);
			}
		}
		order.setItemOrder(itemOrders);
		financeAffirmService.saveChargeCapital(order);

	}

	/**
	 * 存出保证金划回更新财务确认金额里的值
	 *
	 * @param type (add,update,delete)
	 */

	private void updateAffirmAmount(String affirmDetailIds, String oldAffirmDetailIds, String type) {
		if (StringUtil.isNotBlank(affirmDetailIds)) {
			String firms[] = affirmDetailIds.split(";");
			for (String firm : firms) {
				if (StringUtil.isNotBlank(firm)) {
					Long id = Long.parseLong(firm.split(",")[1]);
					Money firmAmount = new Money(firm.split(",")[0]);
					FFinanceAffirmDetailDO DO = fFinanceAffirmDetailDAO.findById(id);
					Money amount = Money.zero();
					if (DO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未找到该条资金划付存出保证金记录");
					}
					if ("add".equals(type)) {//新增减钱
						amount = DO.getReturnCustomerAmount().subtract(firmAmount);
					} else if ("delete".equals(type)) {//删除加钱
						amount = DO.getReturnCustomerAmount().add(firmAmount);
					}
					DO.setReturnCustomerAmount(amount);
					fFinanceAffirmDetailDAO.update(DO);
				}
			}

			//编辑单独处理
			if ("update".equals(type)) {//编辑先加以前的数据后减
				//先把对象放到map里
				Map<Long, Money> map = new HashMap<Long, Money>();
				Map<Long, Money> oldMap = new HashMap<Long, Money>();
				Money amount = Money.zero();
				for (String firm : firms) {
					Long id = Long.parseLong(firm.split(",")[1]);
					Money firmAmount = new Money(firm.split(",")[0]);
					map.put(id, firmAmount);
				}
				//原记录的值放到map里去
				if (StringUtil.isNotBlank(oldAffirmDetailIds)) {
					String oldFirms[] = oldAffirmDetailIds.split(";");
					for (String oldFirm : oldFirms) {
						Long oldId = Long.parseLong(oldFirm.split(",")[1]);
						Money oldFirmAmount = new Money(oldFirm.split(",")[0]);
						oldMap.put(oldId, oldFirmAmount);
					}
				}
				for (Long firmId : map.keySet()) {
					FFinanceAffirmDetailDO DO = fFinanceAffirmDetailDAO.findById(firmId);
					if (oldMap.get(firmId) == null) {//没有就新增减钱
						amount = DO.getReturnCustomerAmount().subtract(map.get(firmId));
					} else if (oldMap.get(firmId) != null) {//编辑先加以前的数据后减
						amount = DO.getReturnCustomerAmount().add(oldMap.get(firmId))
								.subtract(map.get(firmId));
						oldMap.remove(firmId);
					}
					DO.setReturnCustomerAmount(amount);
					fFinanceAffirmDetailDAO.update(DO);
				}
				//原记录map还有数据就是删除把钱加回去
				if (oldMap.size() > 0) {
					for (Long firmId : oldMap.keySet()) {
						FFinanceAffirmDetailDO DO = fFinanceAffirmDetailDAO.findById(firmId);
						amount = DO.getReturnCustomerAmount().add(oldMap.get(firmId));
						DO.setReturnCustomerAmount(amount);
						fFinanceAffirmDetailDAO.update(DO);
					}
				}
			}
		}
	}

	@Override
	public List<FChargeNotificationInfo> queryAuditingByProjectCode(String projectCode) {

		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> notificationDOs = fChargeNotificationDAO
				.findAuditingByProjectCode(projectCode);
		for (FChargeNotificationDO item : notificationDOs) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
					.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			result.add(info);
		}
		return result;
	}

	public List<FChargeNotificationInfo> queryApprovalByProjectCode(String projectCode) {
		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> notificationDOs = fChargeNotificationDAO
				.findByProjectCode(projectCode);
		for (FChargeNotificationDO item : notificationDOs) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
					.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			//财务确认信息
			FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(info.getFormId());
			if (affirmDO != null) {
				FFinanceAffirmInfo fFinanceAffirmInfo = new FFinanceAffirmInfo();
				BeanCopier.staticCopy(affirmDO, fFinanceAffirmInfo);
				List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO
						.findByAffirmId(affirmDO.getAffirmId());
				if (ListUtil.isNotEmpty(detailDOs)) {
					List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
					for (FFinanceAffirmDetailDO detailDO : detailDOs) {
						FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
						BeanCopier.staticCopy(detailDO, detailInfo);
						detailInfo.setChargeFeeType(FeeTypeEnum.getByCode(detailDO.getFeeType()));
						detailInfos.add(detailInfo);
					}
					fFinanceAffirmInfo.setFeeList(detailInfos);
				}
				info.setFirmInfo(fFinanceAffirmInfo);
			}
			result.add(info);
		}
		return result;
	}

	private synchronized String genChargeNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String seqName = SysDateSeqNameEnum.CHARGE_NOTIFICATION_CODE_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String code = StringUtil.leftPad(String.valueOf(seq), 6, "0");
		return code;
	}

	private void updateFees(final FChargeNotificationOrder order,
							FChargeNotificationDO fChargeNotificationDO) {
		List<FChargeNotificationFeeOrder> fees = order.getFeeList();
		List<FChargeNotificationFeeDO> delList = fChargeNotificationFeeDAO
				.findByNotificationId(fChargeNotificationDO.getNotificationId());

		for (FChargeNotificationFeeOrder feeOrder : fees) {
			FChargeNotificationFeeDO feeDO = new FChargeNotificationFeeDO();
			BeanCopier.staticCopy(feeOrder, feeDO);
			if (feeOrder.getFeeType() != null) {
				feeDO.setFeeType(feeOrder.getFeeType().code());
				feeDO.setFeeTypeDesc(feeOrder.getFeeType().message());
			}
			feeDO.setNotificationId(fChargeNotificationDO.getNotificationId());
			if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
				feeDO.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
			}
			if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
				feeDO.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
			}
			//收费基数 万元
			Money chargeBase = feeOrder.getChargeBase();
			feeDO.setChargeBase(chargeBase);
			if (feeDO.getId() != 0) {
				FChargeNotificationFeeDO oldDO = fChargeNotificationFeeDAO.findById(feeDO.getId());
				if (feeOrder.getFeeType() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
					//updateAffirmAmount(feeOrder.getAffirmDetailIds(),oldDO.getAffirmDetailIds(),"update");
					setChargeCapitalApproproation(oldDO.getId(), feeOrder.getAffirmDetailIds());
				}
				fChargeNotificationFeeDAO.update(feeDO);
				Iterator<FChargeNotificationFeeDO> iter = delList.iterator();
				while (iter.hasNext()) {
					FChargeNotificationFeeDO curDO = iter.next();
					if (curDO.getId() == feeOrder.getId()) {
						iter.remove();
					}
				}
			} else {
				feeDO.setNotificationId(fChargeNotificationDO.getNotificationId());
				if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
					feeDO.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
				}
				if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
					feeDO.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
				}
				Long detailId = fChargeNotificationFeeDAO.insert(feeDO);
				if (feeOrder.getFeeType() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
					//updateAffirmAmount(feeOrder.getAffirmDetailIds(),null,"add");
					setChargeCapitalApproproation(detailId, feeOrder.getAffirmDetailIds());
				}
			}
		}

		for (FChargeNotificationFeeDO feeDO : delList) {
			if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {
				//updateAffirmAmount(feeDO.getAffirmDetailIds(),null,"delete");
				setChargeCapitalApproproation(feeDO.getId(), feeDO.getAffirmDetailIds());
			}
			fChargeNotificationFeeDAO.deleteById(feeDO.getId());
		}
	}

	@Override
	public FChargeNotificationInfo queryChargeNotificationById(long notificationId) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO
				.findById(notificationId);

		FChargeNotificationInfo info = new FChargeNotificationInfo();
		BeanCopier.staticCopy(fChargeNotificationDO, info);
		info.setChargeBasis(ChargeBasisEnum.getByCode(fChargeNotificationDO.getChargeBasis()));
		List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
				.queryByNotificationId(info.getNotificationId());
		info.setFeeList(feeList);

		return info;
	}

	@Override
	public FcsBaseResult destroyChargeNotificationById(final FChargeNotificationOrder order) {
		return commonProcess(order, "删除收费通知", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getNotificationId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到收费通知");
				} else {
					fChargeNotificationFeeDAO.deleteByNotificationId(order.getNotificationId());
					fChargeNotificationDAO.deleteById(order.getNotificationId());
				}
				return null;
			}
		}, null, null);
	}

	@Override
	public FChargeNotificationInfo queryChargeNotificationByFormId(long formId) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formId);
		FChargeNotificationInfo info = new FChargeNotificationInfo();
		BeanCopier.staticCopy(fChargeNotificationDO, info);
		info.setChargeBasis(ChargeBasisEnum.getByCode(fChargeNotificationDO.getChargeBasis()));
		List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
				.queryByNotificationId(info.getNotificationId());
		info.setFeeList(feeList);
		//财务确认信息
		FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(formId);
		if (affirmDO != null) {
			FFinanceAffirmInfo fFinanceAffirmInfo = new FFinanceAffirmInfo();
			BeanCopier.staticCopy(affirmDO, fFinanceAffirmInfo);
			List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO
					.findByAffirmId(affirmDO.getAffirmId());
			if (ListUtil.isNotEmpty(detailDOs)) {
				List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
				for (FFinanceAffirmDetailDO detailDO : detailDOs) {
					FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo);
					detailInfo.setChargeFeeType(FeeTypeEnum.getByCode(detailDO.getFeeType()));
					detailInfos.add(detailInfo);
				}
				fFinanceAffirmInfo.setFeeList(detailInfos);
			}
			info.setFirmInfo(fFinanceAffirmInfo);
		}
		for (FChargeNotificationFeeInfo feeInfo : feeList) {
			if (feeInfo.getFeeType() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
				List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
				List<ChargeNoticeCapitalApproproationDO> approproationDOs = chargeNoticeCapitalApproproationDAO
						.findByTypeAndDetailId(FinanceAffirmTypeEnum.CHARGE_NOTIFICATION.code(),
								feeInfo.getId());
				for (ChargeNoticeCapitalApproproationDO approproationDO : approproationDOs) {
					FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
					FFinanceAffirmDetailDO detailDO = fFinanceAffirmDetailDAO.findById(Long
							.parseLong(approproationDO.getPayId()));
					BeanCopier.staticCopy(detailDO, detailInfo);
					detailInfo.setMenthodType(PaymentMenthodEnum.getByCode(detailDO.getFeeType()));
					detailInfo.setUseAmount(approproationDO.getUseAmount());
					detailInfos.add(detailInfo);
				}
				info.setDetailInfos(detailInfos);
			}

		}

		return info;
	}

	@Override
	public List<FChargeNotificationInfo> queryChargeNotificationByProjectCode(String projectCode) {

		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> list = fChargeNotificationDAO.findByProjectCode(projectCode);
		for (FChargeNotificationDO item : list) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
					.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			//财务确认信息
			FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(info.getFormId());
			if (affirmDO != null) {
				FFinanceAffirmInfo fFinanceAffirmInfo = new FFinanceAffirmInfo();
				BeanCopier.staticCopy(affirmDO, fFinanceAffirmInfo);
				List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO
						.findByAffirmId(affirmDO.getAffirmId());
				if (ListUtil.isNotEmpty(detailDOs)) {
					List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
					for (FFinanceAffirmDetailDO detailDO : detailDOs) {
						FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
						BeanCopier.staticCopy(detailDO, detailInfo);
						detailInfo.setChargeFeeType(FeeTypeEnum.getByCode(detailDO.getFeeType()));
						detailInfos.add(detailInfo);
					}
					fFinanceAffirmInfo.setFeeList(detailInfos);
				}
				info.setFirmInfo(fFinanceAffirmInfo);
			}
			result.add(info);
		}
		return result;
	}

	@Override
	public QueryBaseBatchResult<FChargeNotificationInfo> queryChargeNotification(FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		QueryBaseBatchResult<FChargeNotificationInfo> batchResult = new QueryBaseBatchResult<FChargeNotificationInfo>();
		try {
			FChargeNotificationDO fChargeNotificationDO = new FChargeNotificationDO();
			BeanCopier.staticCopy(chargeNotificationQueryOrder, fChargeNotificationDO);

			long totalCount = fChargeNotificationDAO.findByConditionCount(fChargeNotificationDO);
			PageComponent component = new PageComponent(chargeNotificationQueryOrder, totalCount);

			List<FChargeNotificationDO> list = fChargeNotificationDAO.findByCondition(
					fChargeNotificationDO, component.getFirstRecord(), component.getPageSize());

			List<FChargeNotificationInfo> pageList = new ArrayList<FChargeNotificationInfo>(
					list.size());
			for (FChargeNotificationDO item : list) {
				FChargeNotificationInfo info = new FChargeNotificationInfo();
				BeanCopier.staticCopy(item, info);
				info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
				if (chargeNotificationQueryOrder.isQueryFeeList()) {
					info.setFeeList(chargeNotificationFeeService.queryByNotificationId(item
							.getNotificationId()));
				}
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费通知失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}

	@Override
	public QueryBaseBatchResult<FChargeNotificationResultInfo> queryChargeNotificationList(FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		QueryBaseBatchResult<FChargeNotificationResultInfo> batchResult = new QueryBaseBatchResult<FChargeNotificationResultInfo>();
		try {
			ChargeNotificationFormDO chargeNotificationFormDO = new ChargeNotificationFormDO();
			BeanCopier.staticCopy(chargeNotificationQueryOrder, chargeNotificationFormDO);
			if (chargeNotificationQueryOrder.getChargeBasis() != null) {
				chargeNotificationFormDO.setChargeBasis(chargeNotificationQueryOrder
						.getChargeBasis().code());
			}
			chargeNotificationFormDO.setDeptIdList(chargeNotificationQueryOrder.getDeptIdList());
			long totalCount = extraDAO.searchChargeNotificationCount(chargeNotificationFormDO);
			PageComponent component = new PageComponent(chargeNotificationQueryOrder, totalCount);

			List<ChargeNotificationFormDO> list = extraDAO.searchChargeNotificationList(
					chargeNotificationFormDO, component.getFirstRecord(), component.getPageSize(),
					chargeNotificationQueryOrder.getSortCol(),
					chargeNotificationQueryOrder.getSortOrder());

			List<FChargeNotificationResultInfo> pageList = new ArrayList<FChargeNotificationResultInfo>(
					list.size());
			for (ChargeNotificationFormDO item : list) {
				FChargeNotificationResultInfo info = new FChargeNotificationResultInfo();
				BeanCopier.staticCopy(item, info);
				info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(item.getProjectStatus()));
				if (chargeNotificationQueryOrder.isQueryFeeList()) {
					info.setFeeList(chargeNotificationFeeService.queryByNotificationId(item
							.getNotificationId()));
					//财务确认信息
					FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(info.getFormId());
					if (affirmDO != null) {
						FFinanceAffirmInfo fFinanceAffirmInfo = new FFinanceAffirmInfo();
						BeanCopier.staticCopy(affirmDO, fFinanceAffirmInfo);
						List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO
								.findByAffirmId(affirmDO.getAffirmId());
						if (ListUtil.isNotEmpty(detailDOs)) {
							List<FFinanceAffirmDetailInfo> detailInfos = Lists.newArrayList();
							for (FFinanceAffirmDetailDO detailDO : detailDOs) {
								FFinanceAffirmDetailInfo detailInfo = new FFinanceAffirmDetailInfo();
								BeanCopier.staticCopy(detailDO, detailInfo);
								detailInfo.setChargeFeeType(FeeTypeEnum.getByCode(detailDO.getFeeType()));
								detailInfos.add(detailInfo);
							}
							fFinanceAffirmInfo.setFeeList(detailInfos);
						}
						info.setFirmInfo(fFinanceAffirmInfo);
					}
				}
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费通知失败 : {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}

	@Override
	public ReportDataResult chargeNorificationExport(FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		ReportDataResult result = new ReportDataResult();
		try {
			result.getFcsFields().add(new FcsField("charge_basis", "收费依据", null));
			result.getFcsFields().add(new FcsField("project_code", "项目编码", null));
			result.getFcsFields().add(new FcsField("customer_name", "客户名称", null));
			result.getFcsFields().add(new FcsField("project_name", "项目名称", null));
			result.getFcsFields().add(new FcsField("contract_code", "合同编号", null));
			result.getFcsFields().add(new FcsField("contract_name", "合同名称", null));
			result.getFcsFields().add(new FcsField("busi_type_name", "授信类型", null));
			result.getFcsFields().add(new FcsField("time_limit", "担保期间", null));
			result.getFcsFields().add(new FcsField("institutionName", "融资机构", null));
			result.getFcsFields().add(new FcsField("amount", "担保金额", null));
			result.getFcsFields().add(new FcsField("contract_amount", "合同金额", null));
			result.getFcsFields().add(new FcsField("balance_amount", "在保余额", null));
			result.getFcsFields().add(new FcsField("charge_fee", "担保费用", null));
			result.getFcsFields().add(new FcsField("fee_type_desc", "收费种类", null));
			result.getFcsFields().add(new FcsField("charge_base", "收费基数", null));
			result.getFcsFields().add(new FcsField("charge_rate", "收费费率", null));
			result.getFcsFields().add(new FcsField("start_time", "计费期间(开始)", null));
			result.getFcsFields().add(new FcsField("end_time", "计费期间(结束)", null));
			result.getFcsFields().add(new FcsField("charge_amount", "预计收费金额", null));
			result.getFcsFields().add(new FcsField("actual_amount", "实际收费金额", null));
			result.getFcsFields().add(new FcsField("pay_time", "收费时间", null));

			ChargeNotificationFormDO chargeNotificationFormDO = new ChargeNotificationFormDO();
			BeanCopier.staticCopy(chargeNotificationQueryOrder, chargeNotificationFormDO);
			if (chargeNotificationQueryOrder.getChargeBasis() != null) {
				chargeNotificationFormDO.setChargeBasis(chargeNotificationQueryOrder
						.getChargeBasis().code());
			}
			chargeNotificationFormDO.setDeptIdList(chargeNotificationQueryOrder.getDeptIdList());
			List<ChargeNotificationExportDO> list = extraDAO.searchChargeNotificationExport(
					chargeNotificationFormDO, 0, 0, chargeNotificationQueryOrder.getSortCol(),
					chargeNotificationQueryOrder.getSortOrder());
			ArrayList<DataListItem> al = new ArrayList<DataListItem>();
			for (ChargeNotificationExportDO item : list) {
				al.add(setDataListItem(item));
			}
			result.setDataList(al);
			return result;
		} catch (Exception e) {
			logger.error("查询失败 : {}", e);
		}
		return null;
	}

	private DataListItem setDataListItem(ChargeNotificationExportDO DO) {
		List<ReportItem> valueList = Lists.newArrayList();
		DataListItem dataList = new DataListItem();
		valueList.add(setReportItem("charge_basis", ChargeBasisEnum.getByCode(DO.getChargeBasis())
				.message(), DataTypeEnum.STRING));
		valueList.add(setReportItem("project_code", DO.getProjectCode(), DataTypeEnum.STRING));
		valueList.add(setReportItem("customer_name", DO.getCustomerName(), DataTypeEnum.STRING));
		valueList.add(setReportItem("project_name", DO.getProjectName(), DataTypeEnum.STRING));
		valueList.add(setReportItem("contract_code", DO.getContractCode(), DataTypeEnum.STRING));
		valueList.add(setReportItem("contract_name", DO.getContractName(), DataTypeEnum.STRING));
		valueList.add(setReportItem("busi_type_name", DO.getBusiTypeName(), DataTypeEnum.STRING));
		valueList.add(setReportItem(
				"time_limit",
				DO.getTimeLimit() == 0 ? "-" : DO.getTimeLimit()
						+ TimeUnitEnum.getByCode(DO.getTimeUnit()).message(),
				DataTypeEnum.STRING));
		valueList
				.add(setReportItem("institutionName", DO.getInstitutionName(), DataTypeEnum.STRING));
		valueList.add(setReportItem("amount", DO.getAmount().getCent() + "", DataTypeEnum.MONEY));
		valueList.add(setReportItem("contract_amount", DO.getContractAmount().getCent() + "",
				DataTypeEnum.MONEY));
		valueList.add(setReportItem(
				"charge_fee",
				DO.getChargeFee()
						+ (DO.getChargeType() == null ? "" : ChargeTypeEnum.getByCode(
						DO.getChargeType()).unit()), DataTypeEnum.STRING));
		valueList.add(setReportItem("fee_type_desc", DO.getFeeTypeDesc(), DataTypeEnum.STRING));
		valueList.add(setReportItem("charge_base", DO.getChargeBase() == null ? "" : DO
				.getChargeBase().getCent() + "", DataTypeEnum.MONEY));
		valueList
				.add(setReportItem("charge_rate", DO.getChargeRate() + "", DataTypeEnum.BIGDECIMAL));
		valueList.add(setReportItem("start_time",
				DO.getStartTime() == null ? "" : DateUtil.dtSimpleFormat(DO.getStartTime()),
				DataTypeEnum.STRING));
		valueList.add(setReportItem("end_time",
				DO.getEndTime() == null ? "" : DateUtil.dtSimpleFormat(DO.getEndTime()),
				DataTypeEnum.STRING));
		valueList.add(setReportItem("charge_amount", DO.getChargeAmount().getCent() + "",
				DataTypeEnum.MONEY));
		valueList.add(setReportItem("actual_amount", DO.getActualAmount().getCent() + "",
				DataTypeEnum.MONEY));
		valueList.add(setReportItem("pay_time",
				DO.getPayTime() == null ? "" : DateUtil.dtSimpleFormat(DO.getPayTime()),
				DataTypeEnum.STRING));
		Money balanceAmount = Money.zero();
		if (ProjectUtil.isEntrusted(DO.getBusiType())) {
			balanceAmount = DO.getLoanedAmount().subtract(DO.getReleasedAmount())
					.subtract(DO.getCompPrincipalAmount());
		} else {
			balanceAmount = DO.getReleasableAmount().subtract(DO.getReleasedAmount())
					.subtract(DO.getCompPrincipalAmount());
		}
		valueList.add(setReportItem("balance_amount", balanceAmount.getCent() + "",
				DataTypeEnum.MONEY));
		dataList.setValueList(valueList);
		return dataList;
	}

	private ReportItem setReportItem(String key, String value, DataTypeEnum type) {
		ReportItem reportItem = new ReportItem();
		reportItem.setKey(key);
		reportItem.setValue(value);
		reportItem.setDataTypeEnum(type);
		return reportItem;
	}

	@Override
	public RefundApplicationResult queryChargeTotalAmount(String projectCode, FeeTypeEnum feeType) {
		RefundApplicationResult result = new RefundApplicationResult();
		try {
			Money total = new Money(0L);
			FChargeNotificationQueryOrder queryOrder = new FChargeNotificationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<FChargeNotificationResultInfo> batchResult = this
					.queryChargeNotificationList(queryOrder);
			if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (FChargeNotificationResultInfo info : batchResult.getPageList()) {
//					FChargeNotificationFeeQueryOrder feeQueryOrder = new FChargeNotificationFeeQueryOrder();
//					feeQueryOrder.setFeeType(feeType.code());
//					feeQueryOrder.setNotificationId(info.getNotificationId());
//					QueryBaseBatchResult<FChargeNotificationFeeInfo> feeBatchResult = chargeNotificationFeeService
//						.queryChargeNotification(feeQueryOrder);
//					if (null != feeBatchResult && ListUtil.isNotEmpty(feeBatchResult.getPageList())) {
//						for (FChargeNotificationFeeInfo fee : feeBatchResult.getPageList()) {
//							total.addTo(fee.getA);
//						}
//					}
					FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(info.getFormId());
					FFinanceAffirmDetailDO queryDetail = new FFinanceAffirmDetailDO();
					queryDetail.setFeeType(feeType.code());
					queryDetail.setAffirmId(affirmDO.getAffirmId());
					List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO.findByCondition(queryDetail, 0, 99999);
					if (null != detailDOs && ListUtil.isNotEmpty(detailDOs)) {
						for (FFinanceAffirmDetailDO fee : detailDOs) {
							total.addTo(fee.getPayAmount());
						}
					}
				}
			}
			result.setOther(total);
		} catch (Exception e) {
			logger.error("查询收费失败 : {}", e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("查询收费失败：" + e.getMessage());
		}

		return result;
	}

	public void checkBackAmount(FChargeNotificationOrder order) {
//		List<FChargeNotificationInfo> noticeAuditingInfos = queryAuditingByProjectCode(order
//			.getProjectCode());//审核中
//		List<FChargeNotificationInfo> noticeApprovalInfos = queryApprovalByProjectCode(order
//			.getProjectCode());//审核通过
//		Money totalBackMoney = new Money(0, 0);//存出保证金划回
//		Money entrustedLoan = ZERO_MONEY; //委贷本金
//		if (noticeAuditingInfos != null) {
//			for (FChargeNotificationInfo noticeInfo : noticeAuditingInfos) {//审核中的单子 存出保证金总计
//				List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
//				if (feeInfos != null) {
//					for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
//						if (feeInfo.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
//							totalBackMoney = totalBackMoney.add(feeInfo.getChargeAmount());
//						}
//						if (feeInfo.getFeeType().equals(
//							FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL)) {
//							entrustedLoan = entrustedLoan.add(feeInfo.getChargeAmount());
//						}
//					}
//				}
//			}
//		}
//		//审核通过的查财务确认信息
//		if (noticeApprovalInfos != null) {
//			for (FChargeNotificationInfo noticeInfo : noticeApprovalInfos) {
//				List<FFinanceAffirmDetailInfo> feeInfos = noticeInfo.getFirmInfo().getFeeList();
//				if (feeInfos != null) {
//					for (FFinanceAffirmDetailInfo feeInfo : feeInfos) {
//						if (feeInfo.getFeeType().equals(
//							FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.code())) {
//							entrustedLoan = entrustedLoan.add(feeInfo.getPayAmount());
//						}
//					}
//				}
//			}
//		}
//		List<FChargeNotificationFeeOrder> feeOrders = order.getFeeList();
//		boolean ishasRDDB = false;
//		boolean ishasELPW = false;
//		if (feeOrders != null) {
//			for (FChargeNotificationFeeOrder feeOrder : feeOrders) {//加上本张单子的金额
//				if (feeOrder.getFeeType() != null) {
//					if (feeOrder.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
//						totalBackMoney = totalBackMoney.add(feeOrder.getChargeAmount());
//						ishasRDDB = true;
//					}
//					if (feeOrder.getFeeType().equals(
//						FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL)) {
//						entrustedLoan = entrustedLoan.add(feeOrder.getChargeAmount());
//						ishasELPW = true;
//					}
//				}
//			}
//		}
//		ProjectInfo projectInfo = projectService.queryByCode(order.getProjectCode(), false);
//		//校验
//		//		if (projectInfo.getSelfDepositAmount().compareTo(totalBackMoney) == -1&&ishasRDDB) {
//		//			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "存出保证金划回金额总和不能超过该项目已划付的存出保证金金额");
//		//		}
//		if (projectInfo.getLoanedAmount().compareTo(entrustedLoan) == -1 && ishasELPW) {
//			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
//				"委贷本金收回金额总和不能大于已放款金额");
//		}
	}

	public QueryBaseBatchResult<ProjectSimpleDetailInfo> selectChargeNotificationProject(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectSimpleDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectSimpleDetailInfo>();
		ProjectDO queryCondition = new ProjectDO();
		BeanCopier.staticCopy(order, queryCondition);
		List<String> phases = Lists.newArrayList();
		List<String> phasesStatus = Lists.newArrayList();
		List<String> status = Lists.newArrayList();
		String hasContract = null; //是否签订合同
		for (ProjectPhasesEnum s : order.getPhasesList()) {
			phases.add(s.getCode());
		}
		for (ProjectPhasesStatusEnum s : order.getPhasesStatusList()) {
			phasesStatus.add(s.getCode());
		}
		for (ProjectStatusEnum s : order.getStatusList()) {
			status.add(s.getCode());
		}
		//是否签订合同
		if (order.getHasContract() != null) {
			hasContract = order.getHasContract().code();
		}
		long totalSize = extraDAO.selectChargeNotificationProjectCount(queryCondition,
				order.getProjectCodeOrName(), order.getLoginUserId(), order.getDraftUserId(),
				order.getDeptIdList(), status, phases, phasesStatus, hasContract);
		PageComponent component = new PageComponent(order, totalSize);
		List<ProjectDO> pageList = extraDAO.selectChargeNotificationProject(queryCondition,
				order.getProjectCodeOrName(), order.getLoginUserId(), order.getDraftUserId(),
				order.getDeptIdList(), status, phases, phasesStatus, hasContract, component.getFirstRecord(), component.getPageSize(),
				order.getSortCol(), order.getSortOrder());

		List<ProjectSimpleDetailInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectDO project : pageList) {
				list.add(projectService.getSimpleDetailInfo(DoConvert
						.convertProjectDO2Info(project)));
			}
		}

		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	/**
	 * 修复导入项目没有委贷本金收回
	 */
	@Override
	public FcsBaseResult recoveryEenteustedLoanPrincipalWithdrawal(String projectCode, String amount) {
		FcsBaseResult result = createResult();
		try {
			Date now = new Date();
			//查询项目信息
			ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
			//构造form
			FormDO form = new FormDO();
			form.setFormCode(FormCodeEnum.CHARGE_NOTIFICATION.code());
			form.setFormName(FormCodeEnum.CHARGE_NOTIFICATION.message());
			form.setFormUrl(FormCodeEnum.CHARGE_NOTIFICATION.viewUrl());
			form.setUserId(projectInfo.getBusiManagerId());
			form.setUserAccount(projectInfo.getBusiManagerAccount());
			form.setUserName(projectInfo.getBusiManagerName());
			form.setDeptId(projectInfo.getDeptId());
			form.setDeptCode(projectInfo.getDeptCode());
			form.setDeptName(projectInfo.getDeptName());
			form.setDeptPath(projectInfo.getDeptPath());
			form.setDeptPathName(projectInfo.getDeptPathName());
			form.setStatus(FormStatusEnum.APPROVAL.code());
			form.setCheckStatus("1");
			form.setSubmitTime(now);
			form.setFinishTime(now);
			form.setRawAddTime(now);
			long formId = formDAO.insert(form);
			//构造收费通知单
			FChargeNotificationDO chargeDO = new FChargeNotificationDO();
			chargeDO.setFormId(formId);
			chargeDO.setProjectCode(projectInfo.getProjectCode());
			chargeDO.setProjectName(projectInfo.getProjectName());
			chargeDO.setCustomerId(projectInfo.getCustomerId());
			chargeDO.setCustomerName(projectInfo.getCustomerName());
			chargeDO.setChargeBasis("PROJECT");
			chargeDO.setChargeNo(genChargeNo());
			chargeDO.setSubmitManId(projectInfo.getBusiManagerId());
			chargeDO.setSubmitManName(projectInfo.getBusiManagerName());
			chargeDO.setRemark("已解保项目导入");
			chargeDO.setRawAddTime(now);
			long chargeId = fChargeNotificationDAO.insert(chargeDO);
			//构造收费明细
			FChargeNotificationFeeDO feeDO = new FChargeNotificationFeeDO();
			feeDO.setNotificationId(chargeId);
			feeDO.setFeeType(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.code());
			feeDO.setFeeTypeDesc(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.message());
			feeDO.setChargeAmount(new Money(amount));
			feeDO.setChargeBase(new Money(amount));
			feeDO.setChargeRate(1);
			feeDO.setRawAddTime(now);
			long feeId = fChargeNotificationFeeDAO.insert(feeDO);
			//构造财务确认数据
			FFinanceAffirmDO affirmDO = new FFinanceAffirmDO();
			affirmDO.setFormId(formId);
			affirmDO.setProjectCode(projectInfo.getProjectCode());
			affirmDO.setAffirmFormType(FinanceAffirmTypeEnum.CHARGE_NOTIFICATION.code());
			affirmDO.setAmount(new Money(amount));
			affirmDO.setRawAddTime(now);
			long affirmId = fFinanceAffirmDAO.insert(affirmDO);
			FFinanceAffirmDetailDO detailDO = new FFinanceAffirmDetailDO();
			detailDO.setAffirmId(affirmId);
			detailDO.setDetailId(feeId);
			detailDO.setFeeType(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.code());
			detailDO.setPayAmount(new Money(amount));
			detailDO.setPayTime(DateUtil.parse("2016-05-01"));
			detailDO.setRawAddTime(now);
			fFinanceAffirmDetailDAO.insert(detailDO);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("添加项目编号["+projectCode+"]委贷本金收回记录出错", e);
			result.setSuccess(false);
			result.setMessage("添加项目编号["+projectCode+"]委贷本金收回记录出错");
		}
		return result;
	}
}
