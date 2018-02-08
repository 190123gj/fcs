package com.born.fcs.pm.biz.service.creditrefrerenceapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.dal.dataobject.FormDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.FCreditRefrerenceApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.CreditRefrerenceApplyFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CreditRefrerenceApplylStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.creditrefrerenceapply.CreditRefrerenceApplyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("creditRefrerenceApplyService")
public class CreditRefrerenceApplyServiceImpl extends BaseFormAutowiredDomainService implements
																					CreditRefrerenceApplyService {
	@Autowired
	SiteMessageService siteMessageService;//消息推送
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	CommonAttachmentService commonAttachmentService;
	
	private CreditRefrerenceApplyInfo convertDO2Info(FCreditRefrerenceApplyDO DO) {
		if (DO == null)
			return null;
		CreditRefrerenceApplyInfo info = new CreditRefrerenceApplyInfo();
		BeanCopier.staticCopy(DO, info);
		info.setStatus(CreditRefrerenceApplylStatusEnum.getByCode(DO.getStatus()));
		if (DO.getCreditReport() == null) {
			info.setCreditReport("");
		}
		return info;
	}
	
	private CreditRefrerenceApplyReusltInfo convertDO2Info(CreditRefrerenceApplyFormDO DO) {
		if (DO == null)
			return null;
		CreditRefrerenceApplyReusltInfo info = new CreditRefrerenceApplyReusltInfo();
		BeanCopier.staticCopy(DO, info);
		if (DO.getCreditReport() == null) {
			info.setCreditReport("");
		}
		info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
		return info;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public CreditRefrerenceApplyInfo findById(long id) {
		CreditRefrerenceApplyInfo info = null;
		if (id > 0) {
			FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public CreditRefrerenceApplyInfo findByFormId(long formId) {
		CreditRefrerenceApplyInfo info = null;
		if (formId > 0) {
			FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findByFormId(formId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FormBaseResult save(final CreditRefrerenceApplyOrder order) {
		return commonFormSaveProcess(order, "保存征信查询申请", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				order.setApplyStatus("1");
				FCreditRefrerenceApplyDO apply = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getId() != null && order.getId() > 0) {
					apply = fCreditRefrerenceApplyDAO.findById(order.getId());
					if (apply == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"征信查询不存在");
					}
				}
				
				if (apply == null) { //新增
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					FormDO formDO=formDAO.findByFormId(formInfo.getFormId());
					if(StringUtil.isBlank(order.getProjectCode())) {//没有项目编号 虚拟一个
						String projectCode="ZXCX_" + formInfo.getFormId();
						formDO.setRelatedProjectCode("ZXCX_" + formInfo.getFormId());
						formDAO.update(formDO);
						order.setProjectCode(projectCode);
					}
					order.setFormId(formInfo.getFormId());
					apply = new FCreditRefrerenceApplyDO();
					order.setStatus(CreditRefrerenceApplylStatusEnum.NOAPPROVED.code());
					order.setRawAddTime(now);
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setRegisterCapital(Money.amout(String.valueOf(order.getRegisterCapital())));
					fCreditRefrerenceApplyDAO.insert(apply);
				} else { //修改
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setRegisterCapital(Money.amout(String.valueOf(order.getRegisterCapital())));
					fCreditRefrerenceApplyDAO.update(apply);
					
				}
				//                if(order.getIsBeforeFinishCouncil().equals("IS")){//如果是上会完成前法定代表人、公司成立时间、注册资本、经营范围、地址可以修改，修改的信息要同步到客户信息表里
				//                    FProjectCustomerBaseInfoDO baseInfoDO=FProjectCustomerBaseInfoDAO.findByProjectCode(order.getProjectCode());
				//                    baseInfoDO.setLegalPersion(order.getLegalPersion());
				//                    baseInfoDO.setEstablishedTime(order.getEstablishedTime());
				//                    baseInfoDO.setRegisterCapital(Money.amout(String.valueOf(order.getRegisterCapital().multiply(10000))));
				//                    baseInfoDO.setBusiScope(order.getBusiScope());
				//                    baseInfoDO.setAddress(order.getAddress());
				//                    FProjectCustomerBaseInfoDAO.update(baseInfoDO);
				//                }
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> query(	CreditRefrerenceApplyQueryOrder order) {
		QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> baseBatchResult = new QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo>();
		
		CreditRefrerenceApplyFormDO queryCondition = new CreditRefrerenceApplyFormDO();
		Date submitTimeStart = null;
		Date submitTimeEnd = null;
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getCompanyName() != null)
			queryCondition.setCompanyName(order.getCompanyName());
		if (order.getBusiLicenseNo() != null)
			queryCondition.setBusiLicenseNo(order.getBusiLicenseNo());
		if (order.getApplyManName() != null)
			queryCondition.setApplyManName(order.getApplyManName());
		if (order.getStatus() != null)
			queryCondition.setFormStatus(order.getStatus());
		//        if (order.getProjectName() != null)
		//            queryCondition.setProjectName(order.getProjectName());
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getOperateDate() != null) {
			submitTimeStart = DateUtil.getStartTimeOfTheDate(order.getOperateDate());
			submitTimeEnd = DateUtil.getEndTimeOfTheDate(order.getOperateDate());
		}
		if (order.getFormId() != null) {
			queryCondition.setFormId(Long.parseLong(order.getFormId()));
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchCreditApplyCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<CreditRefrerenceApplyFormDO> pageList = extraDAO.searchCreditApplyList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<CreditRefrerenceApplyReusltInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (CreditRefrerenceApplyFormDO apply : pageList) {
				list.add(convertDO2Info(apply));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public void revokeById(long Id) {
		fCreditRefrerenceApplyDAO.deleteById(Id);
	}
	
	@Override
	public int updateStatusById(long id, String status) {
		FCreditRefrerenceApplyDO DO = new FCreditRefrerenceApplyDO();
		DO.setId(id);
		DO.setStatus(status);
		int result = fCreditRefrerenceApplyDAO.updateStatusById(DO);
		return result;
	}
	
	@Override
	public int updateApplyStatusById(long id, String applyStatus) {
		FCreditRefrerenceApplyDO DO = new FCreditRefrerenceApplyDO();
		DO.setId(id);
		DO.setApplyStatus(applyStatus);
		int result = fCreditRefrerenceApplyDAO.updateApplystatusById(DO);
		return result;
	}
	
	@Override
	public FcsBaseResult updateCreditReportById(final CreditRefrerenceApplyOrder order) {
		
		return commonProcess(order, "上传征信报告", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findById(order.getId());
				DO.setCreditReport(order.getCreditReport());
				DO.setAttachment(order.getAttachment());
				fCreditRefrerenceApplyDAO.update(DO);
				if (DO != null) {
					MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
					String url = CommonUtil
						.getRedirectUrl("/projectMg/creditRefrerenceApply/list.htm")
									+ "&formId="
									+ DO.getFormId();
					messageOrder.setMessageContent("尊敬的用户" + DO.getApplyManName() + ",您于"
													+ DateUtil.dtSimpleFormat(DO.getRawAddTime())
													+ "提交的征信查询申请单（客户名称："
													+ DO.getCompanyName()
													+ "） 已有报告上传，快去<a href='" + url + "'>查看详情</a>吧!");
					List<SimpleUserInfo> sendUserList = Lists.newArrayList();
					
					SysUser sysUser = bpmUserQueryService.findUserByUserId(DO.getApplyManId());
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserAccount(sysUser.getAccount());
					user.setUserId(sysUser.getUserId());
					user.setUserName(sysUser.getFullname());
					sendUserList.add(user);
					
					messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
						.toArray(new SimpleUserInfo[sendUserList.size()]));
					siteMessageService.addMessageInfo(messageOrder);
				}
				CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
				attachOrder.setProjectCode(DO.getProjectCode());
				attachOrder.setBizNo("PM_" + DO.getFormId() + "_ZXBG");
				attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
				attachOrder.setModuleType(CommonAttachmentTypeEnum.CREDIT_REPORT);
				attachOrder.setUploaderId(order.getUserId());
				attachOrder.setUploaderName(order.getUserName());
				attachOrder.setUploaderAccount(order.getUserAccount());
				attachOrder.setPath(DO.getCreditReport());
				attachOrder.setRemark("征信报告");
				commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				if (StringUtil.isNotEmpty(order.getAttachment())) {
					attachOrder.setBizNo("PM_" + DO.getFormId() + "_ZXBGFJ");
					attachOrder.setPath(order.getAttachment());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.CREDIT_REPORT_ATTACHMENT);
					attachOrder.setRemark("征信报告附件");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				return null;
			}
		}, null, null);
		
	}

	@Override
	public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> findCreditRefrerenceApplyInfos(CreditRefrerenceApplyQueryOrder queryOrder) {
		
		QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> baseBatchResult = new QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo>();
		
		CreditRefrerenceApplyFormDO queryCondition = new CreditRefrerenceApplyFormDO();
		if (queryOrder != null)
			BeanCopier.staticCopy(queryOrder, queryCondition);
		queryCondition.setCreditReport("creditReport");
		long totalSize = extraDAO.findCreditRefrerenceApplyInfosCount(queryCondition);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		
		List<CreditRefrerenceApplyFormDO> pageList = extraDAO.findCreditRefrerenceApplyInfos(queryCondition,
			component.getFirstRecord(), component.getPageSize(), queryOrder.getSortCol(),
			queryOrder.getSortOrder());
		
		List<CreditRefrerenceApplyReusltInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (CreditRefrerenceApplyFormDO apply : pageList) {
				list.add(convertDO2Info(apply));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> queryCreditRefrerenceApply(
			CreditRefrerenceApplyQueryOrder queryOrder) {
QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> baseBatchResult = new QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo>();
		
		CreditRefrerenceApplyFormDO queryCondition = new CreditRefrerenceApplyFormDO();
		if (queryOrder != null)
			BeanCopier.staticCopy(queryOrder, queryCondition);
		
		List<CreditRefrerenceApplyFormDO> pageList = extraDAO.searchNewCreditApplyList(queryCondition);
		
		List<CreditRefrerenceApplyReusltInfo> list = baseBatchResult.getPageList();
			for (CreditRefrerenceApplyFormDO apply : pageList) {
				list.add(convertDO2Info(apply));
			}
		
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
}
