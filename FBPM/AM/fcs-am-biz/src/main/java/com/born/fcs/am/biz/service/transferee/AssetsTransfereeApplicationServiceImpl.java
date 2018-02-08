package com.born.fcs.am.biz.service.transferee;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.FAssetsTransfereeApplicationDO;
import com.born.fcs.am.dataobject.AssetsTransferApplicationFormDO;
import com.born.fcs.am.dataobject.AssetsTransfereeApplicationFormDO;
import com.born.fcs.am.ws.enums.LiquidaterStatusEnum;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("assetsTransfereeApplicationService")
public class AssetsTransfereeApplicationServiceImpl extends
		BaseFormAutowiredDomainService implements
		AssetsTransfereeApplicationService {

	private FAssetsTransfereeApplicationInfo convertDO2Info(
			FAssetsTransfereeApplicationDO DO) {
		if (DO == null)
			return null;
		FAssetsTransfereeApplicationInfo info = new FAssetsTransfereeApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsTrusteeLiquidate(BooleanEnum.getByCode(DO
				.getIsTrusteeLiquidate()));
		info.setIsCloseMessage(BooleanEnum.getByCode(DO.getIsCloseMessage()));
		return info;
	}

	@Override
	public FAssetsTransfereeApplicationInfo findById(long id) {
		FAssetsTransfereeApplicationDO DO = FAssetsTransfereeApplicationDAO
				.findById(id);
		FAssetsTransfereeApplicationInfo info = new FAssetsTransfereeApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}

	@Override
	public FAssetsTransfereeApplicationInfo findByFormId(long formId) {
		FAssetsTransfereeApplicationDO DO = FAssetsTransfereeApplicationDAO
				.findByFormId(formId);
		FAssetsTransfereeApplicationInfo info = convertDO2Info(DO);
		return info;
	}

	@Override
	public FormBaseResult save(final FAssetsTransfereeApplicationOrder order) {
		return commonFormSaveProcess(order, "资产受让申请单",
				new BeforeProcessInvokeService() {
					@Override
					public Domain before() {
						if (order.getId() == null || order.getId() <= 0) {
							FormInfo formInfo = (FormInfo) FcsPmDomainHolder
									.get().getAttribute("form");
							final Date now = FcsPmDomainHolder.get()
									.getSysDate();
							// 保存
							FAssetsTransfereeApplicationDO DO = new FAssetsTransfereeApplicationDO();
							BeanCopier.staticCopy(order, DO);
							DO.setTransfereePrice(new Money(order
									.getTransfereePrice() == null ? 0.00
									: order.getTransfereePrice()));
							DO.setLiquidaterPrice(new Money(order
									.getLiquidaterPrice() == null ? 0.00
									: order.getLiquidaterPrice()));
							DO.setFormId(formInfo.getFormId());
							DO.setRawAddTime(now);
							DO.setIsCloseMessage(BooleanEnum.NO.code());
							long id = FAssetsTransfereeApplicationDAO
									.insert(DO);// 主表id
							FormBaseResult result = (FormBaseResult) FcsPmDomainHolder
									.get().getAttribute("result");
							result.setKeyId(id);
						} else {
							final Date now = FcsPmDomainHolder.get()
									.getSysDate();
							// 更新
							Long id = order.getId();
							FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
									.findById(id);
							if (null == transferDO) {
								throw ExceptionFactory.newFcsException(
										FcsResultEnum.HAVE_NOT_DATA,
										"未找到资产受让记录");
							}
							BeanCopier.staticCopy(order, transferDO);
							transferDO.setTransfereePrice(new Money(order
									.getTransfereePrice() == null ? 0.00
									: order.getTransfereePrice()));
							transferDO.setLiquidaterPrice(new Money(order
									.getLiquidaterPrice() == null ? 0.00
									: order.getLiquidaterPrice()));
							transferDO.setIsCloseMessage(transferDO
									.getIsCloseMessage());
							FAssetsTransfereeApplicationDAO.update(transferDO);// 更新主表信息

						}
						FormBaseResult result = (FormBaseResult) FcsPmDomainHolder
								.get().getAttribute("result");

						return null;
					}
				}, null, null);
	}

	@Override
	public QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> query(
			FAssetsTransfereeApplicationQueryOrder order) {
		QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> baseBatchResult = new QueryBaseBatchResult<FAssetsTransfereeApplicationInfo>();
		try {
			AssetsTransfereeApplicationFormDO DO = new AssetsTransfereeApplicationFormDO();
			BeanCopier.staticCopy(order, DO);

			long totalCount = extraDAO.searchAssetsTransfereeApplicationCount(
					DO, order.getSubmitTimeStart(), order.getSubmitTimeEnd(),
					order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);

			List<AssetsTransfereeApplicationFormDO> pageList = extraDAO
					.searchAssetsTransfereeApplicationList(DO,
							component.getFirstRecord(),
							component.getPageSize(),
							order.getSubmitTimeStart(),
							order.getSubmitTimeEnd(), order.getDeptIdList(),
							order.getSortCol(), order.getSortOrder());

			List<FAssetsTransfereeApplicationInfo> list = baseBatchResult
					.getPageList();
			if (totalCount > 0) {
				for (AssetsTransfereeApplicationFormDO sf : pageList) {
					FAssetsTransfereeApplicationInfo info = new FAssetsTransfereeApplicationInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf
							.getFormStatus()));
					info.setIsTrusteeLiquidate(BooleanEnum.getByCode(sf
							.getIsTrusteeLiquidate()));
					info.setIsCloseMessage(BooleanEnum.getByCode(sf
							.getIsCloseMessage()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询资产受让列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}

	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}

	@Override
	public int updateIsColseMessage(long id) {
		int num = 0;
		try {
			FAssetsTransfereeApplicationDO DO = FAssetsTransfereeApplicationDAO
					.findById(id);
			DO.setIsCloseMessage(BooleanEnum.IS.code());
			num = FAssetsTransfereeApplicationDAO.update(DO);
		} catch (Exception e) {
			logger.error("关闭系统消息失败" + e.getMessage(), e);
		}
		return num;
	}

	@Override
	public QueryBaseBatchResult<FAssetsTransferApplicationInfo> queryTransferProject(
			FAssetsTransferApplicationQueryOrder order) {
		QueryBaseBatchResult<FAssetsTransferApplicationInfo> baseBatchResult = new QueryBaseBatchResult<FAssetsTransferApplicationInfo>();
		try {
			AssetsTransferApplicationFormDO DO = new AssetsTransferApplicationFormDO();
			BeanCopier.staticCopy(order, DO);

			long totalCount = extraDAO
					.searchAssetsTransfereeProjectSelectCount(DO,
							order.getSubmitTimeStart(),
							order.getSubmitTimeEnd(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);

			List<AssetsTransferApplicationFormDO> pageList = extraDAO
					.searchAssetsTransfereeProjectSelectList(DO,
							component.getFirstRecord(),
							component.getPageSize(),
							order.getSubmitTimeStart(),
							order.getSubmitTimeEnd(), order.getDeptIdList(),
							order.getSortCol(), order.getSortOrder());

			List<FAssetsTransferApplicationInfo> list = baseBatchResult
					.getPageList();
			if (totalCount > 0) {
				for (AssetsTransferApplicationFormDO sf : pageList) {
					FAssetsTransferApplicationInfo info = new FAssetsTransferApplicationInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf
							.getFormStatus()));
					info.setIsTrusteeLiquidate(BooleanEnum.getByCode(sf
							.getIsTrusteeLiquidate()));
					info.setIsCloseMessage(BooleanEnum.getByCode(sf
							.getIsCloseMessage()));
					info.setLiquidaterStatus(LiquidaterStatusEnum.getByCode(sf
							.getLiquidaterStatus()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询可资产受让项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}

	@Override
	public long findByOuterProjectNameCount(String projectName) {
		FAssetsTransfereeApplicationDO DO = new FAssetsTransfereeApplicationDO();
		DO.setProjectName(projectName);
		return FAssetsTransfereeApplicationDAO.findByOuterProjectNameCount(DO);
	}
}
