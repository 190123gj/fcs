package com.born.fcs.pm.biz.service.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectTransferDetailDO;
import com.born.fcs.pm.dataobject.ProjectTransferDetailFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailFormInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferAcceptOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailQueryOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferSelectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.ProjectTransferService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectTransferService")
public class ProjectTransferServiceImpl extends BaseFormAutowiredDomainService implements
																				ProjectTransferService {
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	ProjectService projectService;
	
	@Override
	public FcsBaseResult transferProject(final TransferProjectOrder order) {
		return commonProcess(order, "项目移交", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				String projectCode = order.getProjectCode();
				ProjectDO project = projectDAO.findByProjectCode(projectCode);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"项目不存在");
				}
				
				ProjectRelatedUserInfo orignal = null;
				if (order.getUserType() == ProjectRelatedUserTypeEnum.BUSI_MANAGER) {
					orignal = projectRelatedUserService.getBusiManager(projectCode);
				} else if (order.getUserType() == ProjectRelatedUserTypeEnum.LEGAL_MANAGER) {
					orignal = projectRelatedUserService.getLegalManager(projectCode);
				} else {
					orignal = projectRelatedUserService.getRiskManager(projectCode);
				}
				
				SimpleUserInfo user = null;
				if (orignal != null) {
					user = orignal.toSimpleUserInfo();
				}
				
				if (user == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						order.getUserType().message() + "未找到");
				}
				
				if (user.getUserId() == order.getAcceptUserId()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前" + order.getUserType().message() + "未改变");
				}
				
				FormRelatedUserQueryOrder qOrder = new FormRelatedUserQueryOrder();
				qOrder.setProjectCode(order.getProjectCode());
				qOrder.setExeStatus(ExeStatusEnum.WAITING);
				qOrder.setUserId(user.getUserId());
				//查询正在执行的任务
				QueryBaseBatchResult<FormRelatedUserInfo> exeTaskUsers = formRelatedUserService
					.queryPage(qOrder);
				
				//等待人审核的表单
				String waitAuditForm = "";
				if (ListUtil.isNotEmpty(exeTaskUsers.getPageList())) {//当前还有执行中的任务
					Map<Long, Integer> exeForm = Maps.newHashMap();
					for (FormRelatedUserInfo exeUser : exeTaskUsers.getPageList()) {
						Integer count = exeForm.get(exeUser.getFormId());
						if (count == null || count == 0) {
							exeForm.put(exeUser.getFormId(), 1);
						} else {
							exeForm.put(exeUser.getFormId(), count++);
						}
					}
					
					//所有在执行中的表单
					for (Long formId : exeForm.keySet()) {
						FormDO form = formDAO.findByFormId(formId);
						if (form != null) {
							waitAuditForm += "[ " + form.getFormName() + " ] * "
												+ exeForm.get(formId) + "，";
						}
					}
					if (StringUtil.isNotEmpty(waitAuditForm)) {
						waitAuditForm = "等待当前" + order.getUserType().message() + "处理的表单："
										+ waitAuditForm.substring(0, waitAuditForm.length() - 1);
					}
					
				}
				
				//查询提交的任务(正在审核中)
				qOrder.setExeStatus(ExeStatusEnum.IN_PROGRESS);
				qOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
				exeTaskUsers = formRelatedUserService.queryPage(qOrder);
				String submitedForm = "";
				if (ListUtil.isNotEmpty(exeTaskUsers.getPageList())) {//当前还有执行中的任务
					Map<Long, Integer> exeForm = Maps.newHashMap();
					for (FormRelatedUserInfo exeUser : exeTaskUsers.getPageList()) {
						Integer count = exeForm.get(exeUser.getFormId());
						if (count == null || count == 0) {
							exeForm.put(exeUser.getFormId(), 1);
						} else {
							exeForm.put(exeUser.getFormId(), count++);
						}
					}
					
					//所有在执行中的表单
					for (Long formId : exeForm.keySet()) {
						FormDO form = formDAO.findByFormId(formId);
						if (form != null) {
							submitedForm += "[ " + form.getFormName() + " ] * "
											+ exeForm.get(formId) + "，";
						}
					}
					if (StringUtil.isNotEmpty(submitedForm)) {
						submitedForm = "当前" + order.getUserType().message() + "提交审核中的表单："
										+ submitedForm.substring(0, submitedForm.length() - 1);
					}
				}
				
				//有提交审核中的表单、有待处理的表单
				if (StringUtil.isNotBlank(waitAuditForm) || StringUtil.isNotBlank(submitedForm)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						waitAuditForm + "  " + submitedForm);
				}
				
				ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
				relatedUser.setUserId(order.getAcceptUserId());
				relatedUser.setUserName(order.getAcceptUserName());
				relatedUser.setUserAccount(order.getAcceptUserAccount());
				relatedUser.setDelOrinigal(true);
				relatedUser.setProjectCode(projectCode);
				relatedUser.setTransferTime(now);
				relatedUser.setUserType(order.getUserType());
				
				//移交项目
				Org org = null;
				if (order.getAcceptDeptId() > 0) {
					org = bpmUserQueryService.findDeptById(order.getAcceptDeptId());
				} else {
					UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(order
						.getAcceptUserId());
					if (userDetail != null) {
						org = userDetail.getPrimaryOrg();
					}
				}
				if (org == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"移交出错");
				}
				
				relatedUser.setDeptId(org.getId());
				relatedUser.setDeptCode(org.getCode());
				relatedUser.setDeptName(org.getName());
				relatedUser.setDeptPath(org.getPath());
				relatedUser.setDeptPathName(org.getPathName());
				relatedUser.setDelOrinigal(true);
				relatedUser.setRemark("项目移交");
				
				FcsBaseResult result = projectRelatedUserService.setRelatedUser(relatedUser);
				
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"设置新的" + order.getUserType().message() + "出错");
				}
				
				//修改项目业务经理
				if (order.getUserType() == ProjectRelatedUserTypeEnum.BUSI_MANAGER) {
					
					project.setBusiManagerId(order.getAcceptUserId());
					project.setBusiManagerName(order.getAcceptUserName());
					project.setBusiManagerAccount(order.getAcceptUserAccount());
					
					//设置到新的部门
					project.setDeptId(org.getId());
					project.setDeptCode(org.getCode());
					project.setDeptName(org.getName());
					project.setDeptPath(org.getPath());
					project.setDeptPathName(org.getPathName());
					//setProjectNewDept(project);
					//更新项目信息
					projectDAO.update(project);
				}
				
				FcsPmDomainHolder.get().addAttribute("orignal", orignal);
				FcsPmDomainHolder.get().addAttribute("project", project);
				FcsPmDomainHolder.get().addAttribute("relatedUser", relatedUser);
				FcsPmDomainHolder.get().addAttribute("transferOrder", order);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				//记录移交明细
				TransferProjectOrder transferOrder = (TransferProjectOrder) FcsPmDomainHolder.get()
					.getAttribute("transferOrder");
				ProjectRelatedUserOrder relatedUser = (ProjectRelatedUserOrder) FcsPmDomainHolder
					.get().getAttribute("relatedUser");
				ProjectRelatedUserInfo orignal = (ProjectRelatedUserInfo) FcsPmDomainHolder.get()
					.getAttribute("orignal");
				ProjectInfo project = DoConvert.convertProjectDO2Info((ProjectDO) FcsPmDomainHolder
					.get().getAttribute("project"));
				
				ProjectTransferDetailOrder detailOrder = new ProjectTransferDetailOrder();
				BeanCopier.staticCopy(project, detailOrder);
				detailOrder.setProjectPhase(project.getPhases());
				detailOrder.setProjectStatus(project.getStatus());
				detailOrder.setProjectAmount(project.getAmount());
				detailOrder.setProjectBalance(project.getBalance());
				detailOrder.setApplyUserId(transferOrder.getUserId());
				detailOrder.setApplyUserName(transferOrder.getUserName());
				detailOrder.setApplyUserAccount(transferOrder.getUserAccount());
				//移交成功
				detailOrder.setTransferStatus(ProjectTransferStatusEnum.TRANSFER_SUCCESS);
				if (relatedUser.getUserType() == ProjectRelatedUserTypeEnum.BUSI_MANAGER) {
					detailOrder.setTransferType(ProjectTransferTypeEnum.BUSI_MANAGER);
				} else if (relatedUser.getUserType() == ProjectRelatedUserTypeEnum.RISK_MANAGER) {
					detailOrder.setTransferType(ProjectTransferTypeEnum.RISK_MANAGER);
				} else {
					detailOrder.setTransferType(ProjectTransferTypeEnum.LEGAL_MANAGER);
				}
				detailOrder.setTransferTime(FcsPmDomainHolder.get().getSysDate());
				//原始人员
				detailOrder.setOriginalUserId(orignal.getUserId());
				detailOrder.setOriginalUserName(orignal.getUserName());
				detailOrder.setOriginalUserAccount(orignal.getUserAccount());
				detailOrder.setOriginalUserDeptId(orignal.getDeptId());
				detailOrder.setOriginalUserDeptName(orignal.getDeptName());
				//接收人员
				detailOrder.setAcceptUserId(relatedUser.getUserId());
				detailOrder.setAcceptUserName(relatedUser.getUserName());
				detailOrder.setAcceptUserAccount(relatedUser.getUserAccount());
				detailOrder.setAcceptUserDeptId(relatedUser.getDeptId());
				detailOrder.setAcceptUserDeptName(relatedUser.getDeptName());
				detailOrder.setRemark("项目列表移交");
				logTransferDetail(detailOrder);
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatusByFormId(ProjectTransferStatusEnum status, long formId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			logger.info("修改移交状态：{},{}", status, formId);
			projectTransferDetailDAO.updateStatusByFormId(status.code(), formId);
			result.setSuccess(true);
			result.setMessage("修改成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改移交状态出错");
			logger.error("修改移交状态出错 {}", e);
		}
		return result;
	}
	
	@Override
	public FormBaseResult saveTransferApply(final ProjectTransferOrder order) {
		return commonFormSaveProcess(order, "项目移交申请", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				List<ProjectTransferDetailOrder> detailOrders = order.getDetailOrder();
				if (ListUtil.isEmpty(detailOrders)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "请选择移交的项目");
				}
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				//已经存在的数据
				Map<Long, ProjectTransferDetailDO> detailMap = Maps.newHashMap();
				List<ProjectTransferDetailDO> existsDetails = projectTransferDetailDAO
					.findByFormId(form.getFormId());
				if (ListUtil.isNotEmpty(existsDetails)) {
					for (ProjectTransferDetailDO detail : existsDetails) {
						detailMap.put(detail.getId(), detail);
					}
				}
				
				//移交详情
				for (ProjectTransferDetailOrder detailOrder : detailOrders) {
					
					ProjectTransferDetailDO detail = null;
					if (detailOrder.getId() > 0)
						detail = detailMap.get(detailOrder.getId());
					
					if (detail == null)
						detail = new ProjectTransferDetailDO();
					BeanCopier.staticCopy(detailOrder, detail);
					
					String projectCode = detail.getProjectCode();
					
					if (StringUtil.isBlank(projectCode))
						continue;
					
					ProjectInfo project = projectService.queryByCode(projectCode, false);
					if (project == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目不存在[" + projectCode + "]");
					}
					
					if (!project.getLoanedAmount().greaterThan(ZERO_MONEY)) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目尚未放款[" + projectCode + "]");
					}
					
					//查看是否有进行中的流程
					FormRelatedUserQueryOrder qOrder = new FormRelatedUserQueryOrder();
					qOrder.setProjectCode(projectCode);
					qOrder.setExeStatus(ExeStatusEnum.IN_PROGRESS);
					qOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
					QueryBaseBatchResult<FormRelatedUserInfo> exeTaskUsers = formRelatedUserService
						.queryPage(qOrder);
					String inProgressForm = "";
					if (ListUtil.isNotEmpty(exeTaskUsers.getPageList())) {//当前还有执行中的任务
						Set<String> exeForm = new HashSet<String>();
						for (FormRelatedUserInfo exeUser : exeTaskUsers.getPageList()) {
							exeForm.add(exeUser.getFormCode().getMessage());
						}
						
						//所有在执行中的表单
						for (String formName : exeForm) {
							if (StringUtil.isBlank(inProgressForm)) {
								inProgressForm = formName;
							} else {
								inProgressForm += "，" + formName;
							}
						}
					}
					
					//有提交审核中的表单、有待处理的表单
					if (StringUtil.isNotBlank(inProgressForm)) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目[" + projectCode + "]以下流程审批中["
																	+ inProgressForm + "]");
					}
					
					BeanCopier.staticCopy(project, detail);
					detail.setProjectPhase(project.getPhases().code());
					detail.setProjectStatus(project.getStatus().code());
					detail.setProjectAmount(project.getAmount());
					detail.setProjectBalance(project.getBalance());
					detail.setApplyUserId(order.getUserId());
					detail.setApplyUserName(order.getUserName());
					detail.setApplyUserAccount(order.getUserAccount());
					detail.setTransferStatus(ProjectTransferStatusEnum.TRANSFER_DRAFT.code());
					detail.setTransferType(ProjectTransferTypeEnum.LEGAL_DEPT.code());
					
					//原始人员
					ProjectRelatedUserInfo busiManager = projectRelatedUserService
						.getBusiManager(projectCode);
					if (busiManager != null) {
						detail.setOriginalUserId(busiManager.getUserId());
						detail.setOriginalUserName(busiManager.getUserName());
						detail.setOriginalUserAccount(busiManager.getUserAccount());
						detail.setOriginalUserDeptId(busiManager.getDeptId());
						detail.setOriginalUserDeptName(busiManager.getDeptName());
					}
					//原始人员b
					ProjectRelatedUserInfo busiManagerb = projectRelatedUserService
						.getBusiManagerb(projectCode);
					if (busiManagerb != null) {
						detail.setOriginalUserbId(busiManagerb.getUserId());
						detail.setOriginalUserbName(busiManagerb.getUserName());
						detail.setOriginalUserbAccount(busiManagerb.getUserAccount());
						detail.setOriginalUserbDeptId(busiManagerb.getDeptId());
						detail.setOriginalUserbDeptName(busiManagerb.getDeptName());
					}
					
					detail.setFormId(form.getFormId());
					
					if (detail.getId() > 0) {
						projectTransferDetailDAO.update(detail);
						detailMap.remove(detail.getId());
					} else {
						detail.setRawAddTime(now);
						projectTransferDetailDAO.insert(detail);
					}
				}
				
				if (!detailMap.isEmpty()) {
					for (Long id : detailMap.keySet()) {
						projectTransferDetailDAO.deleteById(id);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult setAcceptUser(final ProjectTransferAcceptOrder order) {
		
		return commonProcess(order, "设置项目移交接收人员", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				ProjectTransferDetailDO detail = projectTransferDetailDAO.findById(order.getId());
				if (detail == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "移交明细未找到");
				}
				
				if (order.getAcceptUserId() == order.getAcceptUserbId()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"A角和B角不能是同一人");
				}
				
				//验证人员是否属于法务部
				String legalDeptCode = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());
				if (StringUtil.isBlank(legalDeptCode)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "法务部编码未配置");
				}
				
				UserDetailInfo usera = bpmUserQueryService.findUserDetailByUserId(order
					.getAcceptUserId());
				if (usera == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "A角未找到");
				}
				
				Org legalDept = null;
				boolean isBelongLegalDept = false;
				for (Org org : usera.getOrgList()) {
					if (StringUtil.equals(org.getCode(), legalDeptCode)) {
						isBelongLegalDept = true;
						legalDept = org;
						break;
					}
				}
				
				if (!isBelongLegalDept) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						usera.getName() + " 不属于法务部");
				}
				
				UserDetailInfo userb = bpmUserQueryService.findUserDetailByUserId(order
					.getAcceptUserbId());
				if (userb == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "B角未找到");
				}
				
				isBelongLegalDept = false;
				for (Org org : userb.getOrgList()) {
					if (StringUtil.equals(org.getCode(), legalDeptCode)) {
						isBelongLegalDept = true;
						legalDept = org;
						break;
					}
				}
				
				if (!isBelongLegalDept) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						userb.getName() + " 不属于法务部");
				}
				//BeanCopier.staticCopy(order, detail);
				
				detail.setAcceptUserId(usera.getId());
				detail.setAcceptUserName(usera.getName());
				detail.setAcceptUserAccount(usera.getAccount());
				detail.setAcceptUserDeptId(legalDept.getId());
				detail.setAcceptUserDeptName(legalDept.getName());
				
				detail.setAcceptUserbId(userb.getId());
				detail.setAcceptUserbName(userb.getName());
				detail.setAcceptUserbAccount(userb.getAccount());
				detail.setAcceptUserbDeptId(legalDept.getId());
				detail.setAcceptUserbDeptName(legalDept.getName());
				
				projectTransferDetailDAO.update(detail);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult doTransfer(long formId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			logger.info("开始处理项目移交 formId :{}", formId);
			
			List<ProjectTransferDetailDO> details = projectTransferDetailDAO.findByFormId(formId);
			if (ListUtil.isNotEmpty(details)) {
				
				//移交到法务部
				final Date now = getSysdate();
				final String fwDeptCode = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());
				final Org fwDept = bpmUserQueryService.findDeptByCode(fwDeptCode);
				
				for (final ProjectTransferDetailDO detail : details) {
					
					logger.info("开始处理项目移交 [{}]", detail);
					
					if (StringUtil.equals(ProjectTransferStatusEnum.TRANSFER_SUCCESS.code(),
						detail.getTransferStatus()))
						continue;
					
					//每个项目单独事务
					FcsBaseResult singleResult = transactionTemplate
						.execute(new TransactionCallback<FcsBaseResult>() {
							
							@Override
							public FcsBaseResult doInTransaction(TransactionStatus status) {
								FcsBaseResult sResult = new FcsBaseResult();
								try {
									
									detail.setTransferTime(now);
									
									if (fwDept == null) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.HAVE_NOT_DATA, "法务部[" + fwDeptCode
																			+ "]未找到");
									}
									
									//开始移交
									ProjectDO projectDO = projectDAO.findByProjectCode(detail
										.getProjectCode());
									if (projectDO == null) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.HAVE_NOT_DATA,
											"项目[" + detail.getProjectCode() + "]未找到");
									}
									
									ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
									relatedUser.setProjectCode(projectDO.getProjectCode());
									relatedUser.setDeptId(fwDept.getId());
									relatedUser.setDeptCode(fwDept.getCode());
									relatedUser.setDeptName(fwDept.getName());
									relatedUser.setDeptPath(fwDept.getPath());
									relatedUser.setDeptPathName(fwDept.getPathName());
									relatedUser.setDelOrinigal(true);//移除原有客户经理权限
									
									FcsBaseResult setUserResult = null;
									
									//设置A角
									relatedUser
										.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
									relatedUser.setRemark("项目移交-业务经理");
									relatedUser.setUserId(detail.getAcceptUserId());
									relatedUser.setUserAccount(detail.getAcceptUserAccount());
									relatedUser.setUserName(detail.getAcceptUserName());
									
									setUserResult = projectRelatedUserService
										.setRelatedUser(relatedUser);
									
									if (setUserResult == null || !setUserResult.isSuccess()) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.EXECUTE_FAILURE,
											"设置A角出错[" + setUserResult == null ? "未知"
												: setUserResult.getMessage() + "]");
									}
									
									//设置B角
									relatedUser
										.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
									relatedUser.setRemark("项目移交-业务经理B");
									relatedUser.setUserId(detail.getAcceptUserbId());
									relatedUser.setUserAccount(detail.getAcceptUserbAccount());
									relatedUser.setUserName(detail.getAcceptUserbName());
									
									setUserResult = projectRelatedUserService
										.setRelatedUser(relatedUser);
									
									if (setUserResult == null || !setUserResult.isSuccess()) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.EXECUTE_FAILURE,
											"设置B角出错[" + setUserResult == null ? "未知"
												: setUserResult.getMessage() + "]");
									}
									
									//收回流程处理的权限
									projectRelatedUserService.revokeFlowRelated(
										projectDO.getProjectCode(), detail.getOriginalUserId());
									projectRelatedUserService.revokeFlowRelated(
										projectDO.getProjectCode(), detail.getOriginalUserbId());
									
									//设置项目部门
									projectDO.setBusiManagerId(detail.getAcceptUserId());
									projectDO.setBusiManagerName(detail.getAcceptUserName());
									projectDO.setBusiManagerAccount(detail.getAcceptUserAccount());
									projectDO.setBusiManagerbId(detail.getAcceptUserbId());
									projectDO.setBusiManagerbName(detail.getAcceptUserbName());
									projectDO.setBusiManagerbAccount(detail.getAcceptUserbAccount());
									projectDO.setDeptId(fwDept.getId());
									projectDO.setDeptCode(fwDept.getCode());
									projectDO.setDeptName(fwDept.getName());
									projectDO.setDeptPath(fwDept.getPath());
									projectDO.setDeptPathName(fwDept.getPathName());
									try {
										projectDAO.update(projectDO);
									} catch (Exception e) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.EXECUTE_FAILURE, "更新项目信息出错");
									}
									
									detail
										.setTransferStatus(ProjectTransferStatusEnum.TRANSFER_SUCCESS
											.code());
									detail.setRemark("项目移交成功");
									projectTransferDetailDAO.update(detail);
									
									sResult.setSuccess(true);
									sResult.setMessage("项目移交成功");
									
								} catch (FcsPmBizException bizException) {
									if (status != null)
										status.setRollbackOnly();
									sResult.setSuccess(false);
									sResult.setMessage(bizException.getMessage());
									logger.error("项目移交出错  {},{} ", detail.getProjectCode(),
										bizException);
									
								} catch (Exception e) {
									if (status != null)
										status.setRollbackOnly();
									sResult.setSuccess(false);
									sResult.setMessage("项目移交出错");
									logger.error("项目移交出错  {},{} ", detail.getProjectCode(), e);
								}
								
								return sResult;
							}
						});
					
					if (singleResult == null || !singleResult.isSuccess()) {
						detail.setTransferStatus(ProjectTransferStatusEnum.TRANSFER_FAIL.code());
						detail.setRemark(singleResult == null ? "项目移交出错" : singleResult
							.getMessage());
						projectTransferDetailDAO.update(detail);
					}
				}
			}
			
			result.setSuccess(true);
			result.setMessage("项目移交处理成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("项目移交处理出错");
			logger.error("项目移交处理出错:{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult logTransferDetail(final ProjectTransferDetailOrder order) {
		return commonProcess(order, "记录项目移交明细", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ProjectTransferDetailDO detail = new ProjectTransferDetailDO();
				BeanCopier.staticCopy(order, detail);
				if (order.getTransferType() != null)
					detail.setTransferType(order.getTransferType().code());
				if (order.getTransferStatus() != null)
					detail.setTransferStatus(order.getTransferStatus().code());
				if (order.getProjectPhase() != null)
					detail.setProjectPhase(order.getProjectPhase().code());
				if (order.getProjectStatus() != null)
					detail.setProjectStatus(order.getProjectStatus().code());
				detail.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
				projectTransferDetailDAO.insert(detail);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectTransferDetailFormInfo> queryDetail(	ProjectTransferDetailQueryOrder order) {
		QueryBaseBatchResult<ProjectTransferDetailFormInfo> batchResult = new QueryBaseBatchResult<ProjectTransferDetailFormInfo>();
		try {
			
			HashMap<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("formId", order.getFormId());
			paramMap.put("formStatus", order.getFormStatus());
			if (order.getTransferStatus() != null)
				paramMap.put("transferStatus", order.getTransferStatus().code());
			if (order.getTransferType() != null)
				paramMap.put("transferType", order.getTransferType().code());
			paramMap.put("projectCode", order.getProjectCode());
			paramMap.put("projectName", order.getProjectName());
			paramMap.put("customerId", order.getCustomerId());
			paramMap.put("customerName", order.getCustomerName());
			paramMap.put("busiType", order.getBusiType());
			paramMap.put("transferTimeStart", order.getTransferTimeStart());
			paramMap.put("transferTimeEnd", order.getTransferTimeEnd());
			
			paramMap.put("sortCol", order.getSortCol());
			paramMap.put("sortOrder", order.getSortOrder());
			paramMap.put("loginUserId", order.getLoginUserId());
			paramMap.put("draftUserId", order.getDraftUserId());
			paramMap.put("deptIdList", order.getDeptIdList());
			
			long totalCount = busiDAO.projectTransferDetailFormCount(paramMap);
			PageComponent component = new PageComponent(order, totalCount);
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			List<ProjectTransferDetailFormDO> list = busiDAO.projectTransferDetailForm(paramMap);
			List<ProjectTransferDetailFormInfo> pageList = new ArrayList<ProjectTransferDetailFormInfo>(
				list.size());
			for (ProjectTransferDetailFormDO DO : list) {
				ProjectTransferDetailFormInfo info = new ProjectTransferDetailFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setProjectPhase(ProjectPhasesEnum.getByCode(DO.getProjectPhase()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(DO.getProjectStatus()));
				info.setTransferStatus(ProjectTransferStatusEnum.getByCode(DO.getTransferStatus()));
				info.setTransferType(ProjectTransferTypeEnum.getByCode(DO.getTransferType()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目移交明细失败   {} {}", e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> selectProject(ProjectTransferSelectOrder order) {
		QueryBaseBatchResult<ProjectInfo> batchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			
			HashMap<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("projectCode", order.getProjectCode());
			paramMap.put("projectName", order.getProjectName());
			paramMap.put("customerId", order.getCustomerId());
			paramMap.put("customerName", order.getCustomerName());
			paramMap.put("busiType", order.getBusiType());
			paramMap.put("excludeProjects", order.getExcludeProjects());
			
			paramMap.put("sortCol", order.getSortCol());
			paramMap.put("sortOrder", order.getSortOrder());
			paramMap.put("loginUserId", order.getLoginUserId());
			paramMap.put("draftUserId", order.getDraftUserId());
			paramMap.put("deptIdList", order.getDeptIdList());
			
			long totalCount = busiDAO.canTransferProjectCount(paramMap);
			PageComponent component = new PageComponent(order, totalCount);
			
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			List<ProjectDO> list = busiDAO.canTransferProject(paramMap);
			List<ProjectInfo> pageList = new ArrayList<ProjectInfo>(list.size());
			for (ProjectDO item : list) {
				pageList.add(DoConvert.convertProjectDO2Info(item));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询可移交项目信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public List<ProjectTransferDetailInfo> queryByFormId(long formId) {
		List<ProjectTransferDetailDO> detailList = projectTransferDetailDAO.findByFormId(formId);
		List<ProjectTransferDetailInfo> infoList = Lists.newArrayList();
		if (ListUtil.isNotEmpty(detailList)) {
			for (ProjectTransferDetailDO detail : detailList) {
				infoList.add(convertDO2Info(detail));
			}
		}
		return infoList;
	}
	
	@Override
	public List<ProjectTransferDetailInfo> queryByProjectCode(String projectCode) {
		List<ProjectTransferDetailDO> detailList = projectTransferDetailDAO
			.findByProjectCode(projectCode);
		List<ProjectTransferDetailInfo> infoList = Lists.newArrayList();
		if (ListUtil.isNotEmpty(detailList)) {
			for (ProjectTransferDetailDO detail : detailList) {
				infoList.add(convertDO2Info(detail));
			}
		}
		return infoList;
	}
	
	private ProjectTransferDetailInfo convertDO2Info(ProjectTransferDetailDO DO) {
		if (DO == null)
			return null;
		ProjectTransferDetailInfo info = new ProjectTransferDetailInfo();
		BeanCopier.staticCopy(DO, info);
		info.setProjectPhase(ProjectPhasesEnum.getByCode(DO.getProjectPhase()));
		info.setProjectStatus(ProjectStatusEnum.getByCode(DO.getProjectStatus()));
		info.setTransferStatus(ProjectTransferStatusEnum.getByCode(DO.getTransferStatus()));
		info.setTransferType(ProjectTransferTypeEnum.getByCode(DO.getTransferType()));
		return info;
	}
}
