package com.born.fcs.am.biz.service.transfer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.FAssetsTransferApplicationDO;
import com.born.fcs.am.dataobject.AssetsTransferApplicationFormDO;
import com.born.fcs.am.intergration.bpm.BpmUserQueryService;
import com.born.fcs.am.intergration.bpm.service.client.user.SysUser;
import com.born.fcs.am.ws.enums.LiquidaterStatusEnum;
import com.born.fcs.am.ws.enums.MeetTypeEnum;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.SubsystemDockFormTypeEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SubsystemDockProjectOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("assetsTransferApplicationService")
public class AssetsTransferApplicationServiceImpl extends BaseFormAutowiredDomainService implements
																						AssetsTransferApplicationService {
	// 子系统对接项目信息
	@Autowired
	protected SubsystemDockProjectService subsystemDockProjectWebService;
	@Autowired
	protected RiskHandleTeamService riskHandleTeamService;
	@Autowired
	protected SiteMessageService siteMessageServiceClient;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	FormService formService;
	
	@Autowired
	ForecastService forecastServiceClient;
	
	private FAssetsTransferApplicationInfo convertDO2Info(FAssetsTransferApplicationDO DO) {
		if (DO == null)
			return null;
		FAssetsTransferApplicationInfo info = new FAssetsTransferApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsTrusteeLiquidate(BooleanEnum.getByCode(DO.getIsTrusteeLiquidate()));
		info.setIsCloseMessage(BooleanEnum.getByCode(DO.getIsCloseMessage()));
		info.setIsToMeet(BooleanEnum.getByCode(DO.getIsToMeet()));
		info.setMeetType(MeetTypeEnum.getByCode(DO.getMeetType()));
		info.setLiquidaterStatus(LiquidaterStatusEnum.getByCode(DO.getLiquidaterStatus()));
		info.setIsCharge(BooleanEnum.getByCode(DO.getIsCharge()));
		return info;
	}
	
	@Override
	public FAssetsTransferApplicationInfo findById(long id) {
		FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO.findById(id);
		FAssetsTransferApplicationInfo info = new FAssetsTransferApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FAssetsTransferApplicationInfo findByFormId(long formId) {
		FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO.findByFormId(formId);
		FAssetsTransferApplicationInfo info = convertDO2Info(DO);
		return info;
	}
	
	@Override
	public FormBaseResult save(final FAssetsTransferApplicationOrder order) {
		return commonFormSaveProcess(order, "资产转让申请单", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() == null || order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					// 保存
					FAssetsTransferApplicationDO DO = new FAssetsTransferApplicationDO();
					BeanCopier.staticCopy(order, DO);
					DO.setFormId(formInfo.getFormId());
					DO.setRawAddTime(now);
					DO.setTransferPrice(new Money(order.getTransferPrice() == null ? 0.00 : order
						.getTransferPrice()));
					DO.setLiquidaterPrice(new Money(order.getLiquidaterPrice() == null ? 0.00
						: order.getLiquidaterPrice()));
					DO.setIsCloseMessage(BooleanEnum.NO.code());
					DO.setLiquidaterStatus(LiquidaterStatusEnum.NO_LIQUIDATER.code());// 未清收
					long id = FAssetsTransferApplicationDAO.insert(DO);// 主表id
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
					sendMessageForm(order.getProjectCode(), formInfo);
					// 保存表单项目信息 到子系统关联项目信息表中
					SubsystemDockProjectOrder subsystemDockProjectOrder = new SubsystemDockProjectOrder();
					subsystemDockProjectOrder.setProjectCode(order.getProjectCode());
					subsystemDockProjectOrder
						.setDockFormType(SubsystemDockFormTypeEnum.ASSETS_TRANSFER.code());
					subsystemDockProjectWebService.save(subsystemDockProjectOrder);
					
				} else {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					// 更新
					Long id = order.getId();
					FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
						.findById(id);
					if (null == transferDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到资产转让记录");
					}
					FormInfo formInfo = formService.findByFormId(transferDO.getFormId());
					BeanCopier.staticCopy(order, transferDO);
					transferDO.setTransferPrice(new Money(order.getTransferPrice() == null ? 0.00
						: order.getTransferPrice()));
					transferDO.setLiquidaterPrice(new Money(
						order.getLiquidaterPrice() == null ? 0.00 : order.getLiquidaterPrice()));
					
					transferDO.setIsCloseMessage(transferDO.getIsCloseMessage());
					FAssetsTransferApplicationDAO.update(transferDO);// 更新主表信息
					sendMessageForm(order.getProjectCode(), formInfo);
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FAssetsTransferApplicationInfo> query(	FAssetsTransferApplicationQueryOrder order) {
		QueryBaseBatchResult<FAssetsTransferApplicationInfo> baseBatchResult = new QueryBaseBatchResult<FAssetsTransferApplicationInfo>();
		try {
			AssetsTransferApplicationFormDO DO = new AssetsTransferApplicationFormDO();
			BeanCopier.staticCopy(order, DO);
			if (order.getFormId() != null && order.getFormId() > 0) {
				DO.setFormId(order.getFormId());
			}
			long totalCount = extraDAO.searchAssetsTransferApplicationCount(DO,
				order.getSubmitTimeStart(), order.getSubmitTimeEnd(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<AssetsTransferApplicationFormDO> pageList = extraDAO
				.searchAssetsTransferApplicationList(DO, component.getFirstRecord(),
					component.getPageSize(), order.getSubmitTimeStart(), order.getSubmitTimeEnd(),
					order.getDeptIdList(), order.getSortCol(), order.getSortOrder());
			
			List<FAssetsTransferApplicationInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (AssetsTransferApplicationFormDO sf : pageList) {
					FAssetsTransferApplicationInfo info = new FAssetsTransferApplicationInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					
					info.setIsTrusteeLiquidate(BooleanEnum.getByCode(sf.getIsTrusteeLiquidate()));
					info.setIsCloseMessage(BooleanEnum.getByCode(sf.getIsCloseMessage()));
					info.setLiquidaterStatus(LiquidaterStatusEnum.getByCode(sf
						.getLiquidaterStatus()));
					info.setIsToMeet(BooleanEnum.getByCode(sf.getIsToMeet()));
					info.setMeetType(MeetTypeEnum.getByCode(sf.getMeetType()));
					info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(sf.getCouncilStatus()));
					info.setCouncilBack(BooleanEnum.getByCode(sf.getCouncilBack()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询资产转让列表失败" + e.getMessage(), e);
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
			FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO.findById(id);
			DO.setIsCloseMessage(BooleanEnum.IS.code());
			num = FAssetsTransferApplicationDAO.update(DO);
		} catch (Exception e) {
			logger.error("关闭系统消息失败" + e.getMessage(), e);
		}
		return num;
	}
	
	// 发送消息
	public void sendMessageForm(String projectCode, FormInfo formInfo) {
		RiskHandlerTeamQueryOrder order = new RiskHandlerTeamQueryOrder();
		order.setProjectCode(projectCode);
		order.setPageSize(999);
		QueryBaseBatchResult<RiskHandleTeamInfo> result = riskHandleTeamService
			.queryRiskHandleTeam(order);
		List<RiskHandleTeamInfo> teamInfos = result.getPageList();
		if (teamInfos != null) {
			for (RiskHandleTeamInfo info : teamInfos) {
				
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				StringBuffer sb = new StringBuffer();
				String url = CommonUtil.getRedirectUrl("/assetMg/transfer/view.htm") + "?formId="
								+ formInfo.getFormId();
				sb.append("项目编号" + info.getProjectCode());
				sb.append("，项目名称" + info.getProjectName() + "在" + DateUtil.simpleFormat(new Date())
							+ "由" + formInfo.getUserName() + "发起了资产转让申请单，");
				sb.append("点击");
				sb.append("<a href='" + url + "'>查看详情</a>");
				String content = sb.toString();
				messageOrder.setMessageContent(content);
				List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
				long chiefLeaderId = info.getChiefLeaderId();//组长
				SysUser leaderUser = bpmUserQueryService.findUserByUserId(chiefLeaderId);
				SimpleUserInfo user = new SimpleUserInfo();
				
				user.setUserAccount(leaderUser.getAccount());
				user.setUserId(leaderUser.getUserId());
				user.setUserName(leaderUser.getFullname());
				sendUserList.add(user);
				long viceLeaderId = info.getViceLeaderId();//副组长
				SysUser viceLeaderUser = bpmUserQueryService.findUserByUserId(viceLeaderId);
				if (viceLeaderUser != null) {
					SimpleUserInfo user1 = new SimpleUserInfo();
					
					user1.setUserAccount(viceLeaderUser.getAccount());
					user1.setUserId(viceLeaderUser.getUserId());
					user1.setUserName(viceLeaderUser.getFullname());
					sendUserList.add(user1);
				}
				String memberIds = info.getMemberIds();//成员
				String[] strMemberIds = memberIds.split(",");
				if (strMemberIds != null && strMemberIds.length > 0) {
					for (String strMemberId : strMemberIds) {
						SimpleUserInfo user3 = new SimpleUserInfo();
						SysUser member = bpmUserQueryService.findUserByUserId(NumberUtil
							.parseInt(strMemberId));
						if (member != null) {
							user3.setUserAccount(member.getAccount());
							user3.setUserId(member.getUserId());
							user3.setUserName(member.getFullname());
							sendUserList.add(user3);
						}
					}
				}
				messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
					.toArray(new SimpleUserInfo[sendUserList.size()]));
				siteMessageServiceClient.addMessageInfo(messageOrder);
			}
		}
	}
	
	@Override
	public List<FAssetsTransferApplicationInfo> findByProjectCode(String projectCode) {
		List<FAssetsTransferApplicationInfo> listInfo = new ArrayList<FAssetsTransferApplicationInfo>();
		List<FAssetsTransferApplicationDO> listDO = FAssetsTransferApplicationDAO
			.findByProjectCode(projectCode);
		for (FAssetsTransferApplicationDO fAssetsTransferApplicationDO : listDO) {
			FAssetsTransferApplicationInfo info = convertDO2Info(fAssetsTransferApplicationDO);
			listInfo.add(info);
		}
		return listInfo;
	}
	
	@Override
	public int updateIsCharge(long id) {
		int num = 0;
		try {
			FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO.findById(id);
			DO.setIsCharge(BooleanEnum.IS.code());
			num = FAssetsTransferApplicationDAO.update(DO);
		} catch (Exception e) {
			logger.error("更新转让信息失败" + e.getMessage(), e);
		}
		return num;
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId) {
		FcsBaseResult result = createResult();
		try {
			//转让流入预测
			FAssetsTransferApplicationDO apply = FAssetsTransferApplicationDAO.findByFormId(formId);
			Money applyAmount = Money.zero();
			if (apply != null)
				applyAmount = apply.getTransferPrice();
			
			FormInfo form = formService.findByFormId(formId);
			
			if (applyAmount.greaterThan(ZERO_MONEY)) {
				//查询预测规则  没查询到用默认规则
				SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
				SysForecastParamResult ruleResult = forecastServiceClient.findAll();
				if (ruleResult != null && ruleResult.isSuccess()) {
					rule = ruleResult.getParamAllInfo();
				}
				TimeUnitEnum forcastTimeType = rule.getInXczrTimeType();
				int forcastTime = NumberUtil.parseInt(rule.getInXczrTime());
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
				forecastAccountOrder.setAmount(applyAmount);
				forecastAccountOrder.setForecastMemo("资产转让（" + apply.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_ZCZR);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(apply.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.ASSET_TANSFER.code() + "_"
												+ formId);
				forecastAccountOrder.setSystemForm(SystemEnum.AM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(form.getDeptId()));
				forecastAccountOrder.setUsedDeptName(form.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.ASSET_TANSFER);
				forecastAccountOrder.setProjectCode(apply.getProjectCode());
				logger.info("资产转让流入预测 , projectCode：{}, forecastAccountOrder：{} ",
					apply.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
			//受让流出预测
			Money qxMoney = Money.zero();
			if (apply != null
				&& StringUtil.equals(apply.getIsTrusteeLiquidate(), BooleanEnum.IS.code())) {
				qxMoney = apply.getLiquidaterPrice();
			}
			if (qxMoney.greaterThan(ZERO_MONEY)) {
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(apply.getLiquidateTime());
				forecastAccountOrder.setAmount(qxMoney);
				forecastAccountOrder.setForecastMemo("资产转让清收（" + apply.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_ZCSQ);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(apply.getProjectCode() + "_"+ForecastFeeTypeEnum.ASSET_TRANSFERQS.code()+"_"
												+ formId);
				forecastAccountOrder.setSystemForm(SystemEnum.AM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(form.getDeptId()));
				forecastAccountOrder.setUsedDeptName(form.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.ASSET_TRANSFERQS);
				forecastAccountOrder.setProjectCode(apply.getProjectCode());
				logger.info("资产转让流入预测 , projectCode：{}, forecastAccountOrder：{} ",
					apply.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据出错");
			logger.error("同步资产转让预测数据出错：formId{} {}", formId, e);
		}
		return result;
	}
}
