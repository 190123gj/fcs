package com.born.fcs.pm.biz.service.summary;

import java.util.Date;
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
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.summary.AfterwardsProjectSummaryInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryOrder;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
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
	public FcsBaseResult save(final AfterwardsProjectSummaryOrder order) {
		
		return commonProcess(order, "保存保后项目汇总信息", new BeforeProcessInvokeService() {
			
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
					BeanCopier.staticCopy(order, afterwardsProjectSummary,
						UnBoxingConverter.getInstance());
					//					decisionInstitution.setDeleteMark("0");
					afterwardsProjectSummary.setRawAddTime(now);
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
					if (ListUtil.isNotEmpty(sysUserFGFZList)) {
						MessageOrder messageOrder = new MessageOrder();
						messageOrder.setMessageSenderAccount(order.getUserAccount());
						messageOrder.setMessageSenderId(order.getUserId());
						messageOrder.setMessageSenderName(order.getUserName());
						messageOrder.setMessageTitle(order.getDeptName() + order.getReportPeriod()
														+ "授信后检查汇总报告");
						String url = CommonUtil
							.getRedirectUrl("/projectMg/afterwardsCheck/viewSummary.htm")
										+ "&id="
										+ id;
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
						messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
							.toArray(new SimpleUserInfo[sendUserList.size()]));
						siteMessageService.addMessageInfo(messageOrder);
					}
				} else { //修改
					BeanCopier.staticCopy(order, afterwardsProjectSummary,
						UnBoxingConverter.getInstance());
					//					decisionInstitution.setDeleteMark("0");
					afterwardsProjectSummaryDAO.update(afterwardsProjectSummary);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<AfterwardsProjectSummaryInfo> query(AfterwardsProjectSummaryQueryOrder order) {
		QueryBaseBatchResult<AfterwardsProjectSummaryInfo> baseBatchResult = new QueryBaseBatchResult<AfterwardsProjectSummaryInfo>();
		
		AfterwardsProjectSummaryDO queryCondition = new AfterwardsProjectSummaryDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getSummaryId() != null)
			queryCondition.setSummaryId(order.getSummaryId());
		
		long totalSize = afterwardsProjectSummaryDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<AfterwardsProjectSummaryDO> pageList = afterwardsProjectSummaryDAO.findByCondition(
			queryCondition, component.getFirstRecord(), component.getPageSize(),
			order.getSortOrder(), order.getSortCol());
		
		List<AfterwardsProjectSummaryInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AfterwardsProjectSummaryDO product : pageList) {
				list.add(convertDO2Info(product));
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
	public int findHouseholdsByCondition(String busiType, Date finishTime, String deptCode) {
		AfterwardsProjectSummaryDO DO = new AfterwardsProjectSummaryDO();
		DO.setBusiType(busiType);
		
		DO.setFinishTime(finishTime);
		DO.setDeptCode(deptCode);
		long totalCount = extraDAO.searchSummaryCount(DO);
		return (int) totalCount;
	}
	
	@Override
	public Money findReleasingAmountByCondition(String busiType, Date finishTime, String deptCode) {
		AfterwardsProjectSummaryDO DO = new AfterwardsProjectSummaryDO();
		DO.setBusiType(busiType);
		
		DO.setFinishTime(finishTime);
		DO.setDeptCode(deptCode);
		List<AfterwardsProjectSummaryDO> listSummaryDO = extraDAO.searchSummaryLoanedAmount(DO);
		Money sumLoanedAmount = new Money(0);
		for (AfterwardsProjectSummaryDO afterwardsProjectSummaryDO : listSummaryDO) {
			sumLoanedAmount = sumLoanedAmount.add(afterwardsProjectSummaryDO.getLoanedAmount());
		}
		List<AfterwardsProjectSummaryDO> listSummaryDO1 = extraDAO.searchSummaryApplyAmount(DO);
		Money sumApplyAmount = new Money(0);
		for (AfterwardsProjectSummaryDO afterwardsProjectSummaryDO : listSummaryDO1) {
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
	
}
