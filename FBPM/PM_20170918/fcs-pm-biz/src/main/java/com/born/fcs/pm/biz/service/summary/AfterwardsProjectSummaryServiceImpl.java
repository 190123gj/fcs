package com.born.fcs.pm.biz.service.summary;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.AfterwardsProjectSummaryDO;
import com.born.fcs.pm.dataobject.AfterwardsProjectSummaryFormDO;
import com.born.fcs.pm.dataobject.AfterwardsSummaryReleasingAmountDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.summary.AfterwardsProjectSummaryInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryOrder;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.summary.AfterwardsProjectSummaryResult;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.summary.AfterwardsProjectSummaryService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("afterwardsProjectSummaryService")
public class AfterwardsProjectSummaryServiceImpl extends BaseFormAutowiredDomainService implements
																						AfterwardsProjectSummaryService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SiteMessageService siteMessageService;//消息推送
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	SysParameterService sysParameterService;
	@Autowired
	private PmReportService pmReportService;
	
	private AfterwardsProjectSummaryInfo convertDO2Info(AfterwardsProjectSummaryDO DO) {
		if (DO == null)
			return null;
		AfterwardsProjectSummaryInfo info = new AfterwardsProjectSummaryInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public AfterwardsProjectSummaryInfo findById(long summaryId) {
		AfterwardsProjectSummaryInfo info = null;
		if (summaryId > 0) {
			AfterwardsProjectSummaryDO DO = afterwardsProjectSummaryDAO.findById(summaryId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FormBaseResult save(final AfterwardsProjectSummaryOrder order) {
		
		return commonFormSaveProcess(order, "保存保后项目汇总信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				AfterwardsProjectSummaryDO afterwardsProjectSummary = null;
				if (order.getSummaryId() != null && order.getSummaryId() > 0) {
					
					afterwardsProjectSummary = afterwardsProjectSummaryDAO.findById(order
						.getSummaryId());
					if (afterwardsProjectSummary == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"保后项目汇总信息不存在");
					}
				}
				
				if (afterwardsProjectSummary == null) { //新增
					afterwardsProjectSummary = new AfterwardsProjectSummaryDO();
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					BeanCopier.staticCopy(order, afterwardsProjectSummary,
						UnBoxingConverter.getInstance());
					//					decisionInstitution.setDeleteMark("0");
					afterwardsProjectSummary.setRawAddTime(now);
					afterwardsProjectSummary.setFormId(formInfo.getFormId());
					long id = afterwardsProjectSummaryDAO.insert(afterwardsProjectSummary);
					//保存后，系统自动向分管业务副总和总经理发送通知，提醒已完成该报告
					String sysParamValueCompany = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_COMPANY.code()); //公司
					String sysParamValueZJL = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_POSITION.code()); //总经理 参数
					
					String sysParamValueFGFZ = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_POSITION.code());
					String jobCodeFGFZ = afterwardsProjectSummary.getDeptCode().toLowerCase() + "_"
											+ sysParamValueFGFZ;
					List<SysUser> sysUserFGFZList = bpmUserQueryService
						.findUserByJobCode(jobCodeFGFZ);
					String jobCodeZJL = sysParamValueCompany.toLowerCase() + "_" + sysParamValueZJL;//总经理
					
					List<SysUser> sysUserZJLList = bpmUserQueryService
						.findUserByJobCode(jobCodeZJL);
					AfterwardsProjectSummaryDO afterwardsProjectSummary1 = afterwardsProjectSummaryDAO
						.findById(id);
					if (ListUtil.isNotEmpty(sysUserFGFZList)) {
						MessageOrder messageOrder = new MessageOrder();
						messageOrder.setMessageSenderAccount(order.getUserAccount());
						messageOrder.setMessageSenderId(order.getUserId());
						messageOrder.setMessageSenderName(order.getUserName());
						messageOrder.setMessageTitle(order.getDeptName() + order.getReportPeriod()
														+ "授信后检查汇总报告");
						String url = CommonUtil
							.getRedirectUrl("/projectMg/afterwardsSummary/viewSummary.htm")
										+ "&formId=" + afterwardsProjectSummary1.getFormId();
						messageOrder.setMessageContent(order.getDeptName()
														+ order.getReportPeriod() + "在"
														+ DateUtil.simpleFormat(now) + "由"
														+ order.getSubmitManName()
														+ "完成授信后检查汇总报告，并向分管业务副总和总经理发送通知！"
														+ "<html>" + "<head></head>" + "<body>"
														+ "<div><p>" + "<a href='" + url
														+ "'>查看详情</a>" + "</p></div>" + "</body>"
														+ "</html>");
						List<SimpleUserInfo> sendUserList = Lists.newArrayList();
						for (SysUser sysUser : sysUserFGFZList) {
							SimpleUserInfo user = new SimpleUserInfo();
							user.setUserAccount(sysUser.getAccount());
							user.setUserId(sysUser.getUserId());
							user.setUserName(sysUser.getFullname());
							sendUserList.add(user);
						}
						if (ListUtil.isNotEmpty(sysUserZJLList)) {
							for (SysUser sysUser : sysUserZJLList) {
								SimpleUserInfo user = new SimpleUserInfo();
								user.setUserAccount(sysUser.getAccount());
								user.setUserId(sysUser.getUserId());
								user.setUserName(sysUser.getFullname());
								sendUserList.add(user);
							}
						}
						messageOrder.setSendUsers(sendUserList
							.toArray(new SimpleUserInfo[sendUserList.size()]));
						siteMessageService.addMessageInfo(messageOrder);
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else { //修改
					BeanCopier.staticCopy(order, afterwardsProjectSummary,
						UnBoxingConverter.getInstance());
					afterwardsProjectSummary.setFormId(order.getFormId());
					
					afterwardsProjectSummaryDAO.update(afterwardsProjectSummary);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<AfterwardsProjectSummaryInfo> query(AfterwardsProjectSummaryQueryOrder order) {
		QueryBaseBatchResult<AfterwardsProjectSummaryInfo> baseBatchResult = new QueryBaseBatchResult<AfterwardsProjectSummaryInfo>();
		
		AfterwardsProjectSummaryFormDO queryCondition = new AfterwardsProjectSummaryFormDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getSummaryId() != null)
			queryCondition.setSummaryId(order.getSummaryId());
		
		long totalSize = extraDAO.searchSummaryCount(queryCondition);
		;
		
		PageComponent component = new PageComponent(order, totalSize);
		queryCondition.setPageSize(component.getPageSize());
		queryCondition.setLimitStart(component.getFirstRecord());
		List<AfterwardsProjectSummaryFormDO> pageList = extraDAO.searchSummary(queryCondition);
		List<AfterwardsProjectSummaryInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AfterwardsProjectSummaryFormDO DO : pageList) {
				AfterwardsProjectSummaryInfo info = new AfterwardsProjectSummaryInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public int deleteById(long summaryId) {
		AfterwardsProjectSummaryDO afterwardsProjectSummaryDO = afterwardsProjectSummaryDAO
			.findById(summaryId);
		//		decisionInstitutionDO.setDeleteMark("1");
		return afterwardsProjectSummaryDAO.update(afterwardsProjectSummaryDO);
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public int findHouseholdsByCondition(List<String> busiTypeList, Date finishTime, String deptCode) {
		AfterwardsSummaryReleasingAmountDO DO = new AfterwardsSummaryReleasingAmountDO();
		DO.setBusiTypeList(busiTypeList);
		DO.setFinishTime(finishTime);
		DO.setDeptCode(deptCode);
		long totalCount = extraDAO.searchSummaryCount(DO);
		return (int) totalCount;
	}
	
	@Override
	public Money findReleasingAmountByCondition(List<String> busiTypeList, Date finishTime,
												String deptCode) {
		AfterwardsSummaryReleasingAmountDO DO = new AfterwardsSummaryReleasingAmountDO();
		//		DO.setBusiType(busiType);
		DO.setBusiTypeList(busiTypeList);
		DO.setFinishTime(finishTime);
		DO.setDeptCode(deptCode);
		List<AfterwardsSummaryReleasingAmountDO> listSummaryDO = extraDAO
			.searchSummaryLoanedAmount(DO);
		Money sumLoanedAmount = new Money(0);
		for (AfterwardsSummaryReleasingAmountDO afterwardsProjectSummaryDO : listSummaryDO) {
			sumLoanedAmount = sumLoanedAmount.add(afterwardsProjectSummaryDO.getLoanedAmount());
		}
		List<AfterwardsSummaryReleasingAmountDO> listSummaryDO1 = extraDAO
			.searchSummaryApplyAmount(DO);
		Money sumApplyAmount = new Money(0);
		for (AfterwardsSummaryReleasingAmountDO afterwardsProjectSummaryDO : listSummaryDO1) {
			sumApplyAmount = sumApplyAmount.add(afterwardsProjectSummaryDO.getApplyAmount());
		}
		return sumLoanedAmount.subtract(sumApplyAmount);
	}
	
	@Override
	public AfterwardsProjectSummaryInfo findByDeptCodeAndReportPeriod(String deptCode,
																		String reportPeriod) {
		AfterwardsProjectSummaryDO summaryDO = new AfterwardsProjectSummaryDO();
		summaryDO = afterwardsProjectSummaryDAO.findByDeptCodeAndReportPeriod(deptCode,
			reportPeriod);
		
		return convertDO2Info(summaryDO);
	}
	
	@Override
	public AfterwardsProjectSummaryInfo findByFormId(long formId) {
		AfterwardsProjectSummaryInfo info = null;
		if (formId > 0) {
			AfterwardsProjectSummaryDO DO = afterwardsProjectSummaryDAO.findByFormId(formId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public AfterwardsProjectSummaryResult findProjectSummaryInfoByCondition(boolean isGuarantee,
																			Date finishTime,
																			String deptCode) {
		String sql = "";
		if (isGuarantee) {
			sql = "SELECT COUNT(DISTINCT p.customer_id) hs,SUM(ba.balance) balance FROM project_data_info p "
					+ "JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount "
					+ "FROM view_project_occer_detail WHERE occur_date < '"
					+ DateUtil.dtSimpleFormat(finishTime)
					+ "'"
					+ "GROUP BY project_code) occer "
					+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM view_project_repay_detail "
					+ " WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time < '"
					+ DateUtil.dtSimpleFormat(finishTime)
					+ "'"
					+ " GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code "
					+ "WHERE ba.balance > 0  AND  p.phases!='FINISH_PHASES' AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND p.dept_code = '"
					+ deptCode + "'";
		} else {
			sql = "SELECT COUNT(DISTINCT p.customer_id) hs,SUM(ba.balance) balance FROM project_data_info p "
					+ "JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount "
					+ "FROM view_project_occer_detail WHERE occur_date < '"
					+ DateUtil.dtSimpleFormat(finishTime)
					+ "'"
					+ "GROUP BY project_code) occer "
					+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM view_project_repay_detail "
					+ " WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time < '"
					+ DateUtil.dtSimpleFormat(finishTime)
					+ "'"
					+ " GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code "
					+ "WHERE ba.balance > 0 AND p.busi_type LIKE '4%' AND  p.phases!='FINISH_PHASES' AND p.dept_code = '"
					+ deptCode + "'";
		}
		AfterwardsProjectSummaryResult result = new AfterwardsProjectSummaryResult();
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportService.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				
				String hs = String.valueOf(itm.getMap().get("hs"));
				result.setHs(NumberUtil.parseInt(hs));
				String balance = String.valueOf(itm.getMap().get("balance"));
				result.setAmount(toMoney(balance));
			}
		}
		return result;
	}
	
	@Override
	public AfterwardsProjectSummaryResult queryProjectSummaryInfoByCondition(Date finishTime,
																				String projectCode) {
		String sql = "";
		sql = "SELECT COUNT(DISTINCT p.customer_id) hs,SUM(ba.balance) balance FROM project_data_info p "
				+ "JOIN (SELECT occer.project_code,(oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount "
				+ "FROM view_project_occer_detail WHERE occur_date < '"
				+ DateUtil.dtSimpleFormat(finishTime)
				+ "'"
				+ "GROUP BY project_code) occer "
				+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM view_project_repay_detail "
				+ " WHERE  repay_confirm_time < '"
				+ DateUtil.dtSimpleFormat(finishTime)
				+ "'"
				+ " GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code "
				+ "WHERE ba.balance > 0  AND  p.phases!='FINISH_PHASES' AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%' OR p.busi_type LIKE '4%' ) AND p.project_code = '"
				+ projectCode + "'";
		AfterwardsProjectSummaryResult result = new AfterwardsProjectSummaryResult();
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportService.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				
				String hs = String.valueOf(itm.getMap().get("hs"));
				result.setHs(NumberUtil.parseInt(hs));
				String balance = String.valueOf(itm.getMap().get("balance"));
				result.setAmount(toMoney(balance));
			}
		}
		return result;
	}
	
	//转化Money
	private Money toMoney(Object fen) {
		if (fen != null) {
			try {
				return Money.amout(fen.toString()).divide(100);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return Money.zero();
	}
}
