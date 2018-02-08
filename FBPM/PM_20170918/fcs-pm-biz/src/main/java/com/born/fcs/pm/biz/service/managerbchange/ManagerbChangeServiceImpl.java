package com.born.fcs.pm.biz.service.managerbchange;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.FManagerbChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.ManagerbChangeApplyFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.managerbchange.FManagerbChangeApplyInfo;
import com.born.fcs.pm.ws.info.managerbchange.ManagerbChangeApplyFormInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.managerbchange.FManagerbChangeApplyOrder;
import com.born.fcs.pm.ws.order.managerbchange.ManagerbChangeApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.managerbchange.ManagerbChangeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("managerbChangeService")
public class ManagerbChangeServiceImpl extends BaseFormAutowiredDomainService implements
																				ManagerbChangeService {
	@Autowired
	FormService formService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	SysParameterService sysParameterService;
	
	@Override
	public FormBaseResult saveApply(final FManagerbChangeApplyOrder order) {
		
		order.setRelatedProjectCode(order.getProjectCode());
		order.setFormCode(FormCodeEnum.MANAGERB_CHANGE_APPLY);
		
		return commonFormSaveProcess(order, "保存B角更换申请单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FManagerbChangeApplyDO applyDO = FManagerbChangeApplyDAO.findByFormId(form
					.getFormId());
				
				boolean isUpdate = false;
				if (applyDO == null) {
					applyDO = new FManagerbChangeApplyDO();
					BeanCopier.staticCopy(order, applyDO, UnBoxingConverter.getInstance());
					applyDO.setRawAddTime(now);
				} else {
					isUpdate = true;
					BeanCopier.staticCopy(order, applyDO, UnBoxingConverter.getInstance());
				}
				ProjectDO project = projectDAO.findByProjectCode(applyDO.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
				}
				BeanCopier.staticCopy(project, applyDO);
				applyDO.setFormId(form.getFormId());
				applyDO.setManagerId(project.getBusiManagerId());
				applyDO.setManagerName(project.getBusiManagerName());
				applyDO.setManagerAccount(project.getBusiManagerAccount());
				applyDO.setStatus(ChangeManagerbStatusEnum.APPLYING.code());
				
				if (order.getCheckStatus() == 1) { //提交
					//负责人角色名
					//					String fzrRole = sysParameterService
					//						.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
					//					List<SimpleUserInfo> fzrList = projectRelatedUserService.getDeptRoleUser(
					//						project.getDeptCode(), fzrRole);
					List<SysUser> fzrList = bpmUserQueryService.findChargeByOrgId(project
						.getDeptId());
					boolean isFzr = false;
					if (ListUtil.isNotEmpty(fzrList)) {
						for (SysUser fzr : fzrList) {
							if (fzr.getUserId() == form.getUserId()) {
								isFzr = true;
								break;
							}
						}
					}
					
					//业务部负责人直接变更不用提交到表单
					if (isFzr) {
						applyDO.setStatus(ChangeManagerbStatusEnum.APPROVAL.code());
						form.setStatus(FormStatusEnum.APPROVAL);
						formService.updateForm(form);
						
						//直接变更 或者到达时间段变更
						if (StringUtil.equals(applyDO.getChangeWay(),
							ChangeManagerbWayEnum.DIRECT.code())
							|| (StringUtil.equals(applyDO.getChangeWay(),
								ChangeManagerbWayEnum.PERIOD.code()) && now.after(applyDO
								.getChangeStartTime()))) {
							
							ProjectRelatedUserOrder changeOrder = new ProjectRelatedUserOrder();
							changeOrder.setProjectCode(applyDO.getProjectCode());
							changeOrder.setUserId(applyDO.getNewBid());
							changeOrder.setUserName(applyDO.getNewBname());
							changeOrder.setUserAccount(applyDO.getNewBaccount());
							changeOrder.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
							changeOrder.setDelOrinigal(true);
							changeOrder.setTransferTime(now);
							changeOrder.setRemark(form.getUserName() + "申请变更B角");
							FcsBaseResult changeResult = projectRelatedUserService
								.setRelatedUser(changeOrder);
							
							if (!changeResult.isSuccess()) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"变更B角失败");
							}
							
							if (project != null) {
								project.setBusiManagerbId(applyDO.getNewBid());
								project.setBusiManagerbAccount(applyDO.getNewBaccount());
								project.setBusiManagerbName(applyDO.getNewBname());
								projectDAO.update(project);
							}
							
							if ((StringUtil.equals(applyDO.getChangeWay(),
								ChangeManagerbWayEnum.PERIOD.code()))) {
								applyDO.setStatus(ChangeManagerbStatusEnum.WAIT_RESTORE.code());
							} else {
								applyDO.setStatus(ChangeManagerbStatusEnum.APPLIED.code());
							}
						}
					}
				}
				if (isUpdate) {
					FManagerbChangeApplyDAO.updateByFormId(applyDO);
				} else {
					FManagerbChangeApplyDAO.insert(applyDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ManagerbChangeApplyFormInfo> searchForm(ManagerbChangeApplyQueryOrder order) {
		QueryBaseBatchResult<ManagerbChangeApplyFormInfo> batchResult = new QueryBaseBatchResult<ManagerbChangeApplyFormInfo>();
		try {
			ManagerbChangeApplyFormDO searchDO = new ManagerbChangeApplyFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, searchDO);
			long totalCount = busiDAO.searchManagerbChangeApplyFormCount(searchDO);
			PageComponent component = new PageComponent(order, totalCount);
			List<ManagerbChangeApplyFormDO> dataList = busiDAO
				.searchManagerbChangeApplyForm(searchDO);
			
			List<ManagerbChangeApplyFormInfo> list = Lists.newArrayList();
			for (ManagerbChangeApplyFormDO DO : dataList) {
				ManagerbChangeApplyFormInfo info = new ManagerbChangeApplyFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setChangeWay(ChangeManagerbWayEnum.getByCode(DO.getChangeWay()));
				info.setStatus(ChangeManagerbStatusEnum.getByCode(DO.getStatus()));
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询B角变更申请单失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FManagerbChangeApplyInfo queryApplyByFormId(long formId) {
		FManagerbChangeApplyDO applyDO = FManagerbChangeApplyDAO.findByFormId(formId);
		if (applyDO != null) {
			FManagerbChangeApplyInfo info = new FManagerbChangeApplyInfo();
			BeanCopier.staticCopy(applyDO, info);
			info.setChangeWay(ChangeManagerbWayEnum.getByCode(applyDO.getChangeWay()));
			info.setStatus(ChangeManagerbStatusEnum.getByCode(applyDO.getStatus()));
			return info;
		}
		return null;
	}
	
}
