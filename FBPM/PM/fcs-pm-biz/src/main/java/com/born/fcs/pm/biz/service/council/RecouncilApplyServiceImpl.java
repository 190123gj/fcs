package com.born.fcs.pm.biz.service.council;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FReCouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.ReCouncilApplyFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.ReCouncilApplyFormInfo;
import com.born.fcs.pm.ws.order.council.FReCouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.ReCouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.RecouncilApplyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 项目复议
 *
 *
 * @author wuzj
 *
 */
@Service("recouncilApplyService")
public class RecouncilApplyServiceImpl extends BaseFormAutowiredDomainService implements
																				RecouncilApplyService {
	@Autowired
	ProjectService projectService;
	
	@Override
	public QueryBaseBatchResult<ReCouncilApplyFormInfo> queryForm(ReCouncilApplyQueryOrder order) {
		QueryBaseBatchResult<ReCouncilApplyFormInfo> baseBatchResult = new QueryBaseBatchResult<ReCouncilApplyFormInfo>();
		ReCouncilApplyFormDO queryCondition = new ReCouncilApplyFormDO();
		BeanCopier.staticCopy(order, queryCondition);
		queryCondition.setDeptIdList(order.getDeptIdList());
		queryCondition.setStatusList(order.getStatusList());
		queryCondition.setFormStatus(order.getStatus());
		if (StringUtil.isEmpty(order.getSortCol())) {
			queryCondition.setSortCol("f.form_id");
			queryCondition.setSortOrder("desc");
		}
		long totalSize = extraDAO.searchRecouncilApplyFormCount(queryCondition);
		PageComponent component = new PageComponent(order, totalSize);
		List<ReCouncilApplyFormDO> pageList = extraDAO.searchRecouncilApplyForm(queryCondition);
		
		List<ReCouncilApplyFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ReCouncilApplyFormDO apply : pageList) {
				ReCouncilApplyFormInfo info = new ReCouncilApplyFormInfo();
				BeanCopier.staticCopy(apply, info);
				info.setFormCode(FormCodeEnum.getByCode(apply.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(apply.getFormStatus()));
				info.setCustomerType(CustomerTypeEnum.getByCode(apply.getCustomerType()));
				info.setTimeUnit(TimeUnitEnum.getByCode(apply.getTimeUnit()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(apply.getProjectStatus()));
				info.setCouncilBack(BooleanEnum.getByCode(apply.getCouncilBack()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FormBaseResult saveApply(final FReCouncilApplyOrder order) {
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		order.setRelatedProjectCode(order.getProjectCode());
		return commonFormSaveProcess(order, "保存复议申请单", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				ProjectDO project = projectDAO.findByProjectCode(order.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目信息不存在");
				}
				
				if (ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"项目已暂缓");
				}
				
				FcsPmDomainHolder.get().addAttribute("projectDO", project);
				
				if (BooleanEnum.NO.code().equals(project.getIsApproval())) {//未通过的项目复议走尽调审批流程
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"未通过的项目复议请修改尽调后送审");
					//	6个月内未通过的项目才能复议
					//					Calendar c = Calendar.getInstance();
					//					c.setTime(now);
					//					c.add(Calendar.MONTH, -6);
					//					if (project.getApprovalTime() != null
					//						&& project.getApprovalTime().before(c.getTime())) {
					//						throw ExceptionFactory.newFcsException(
					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "6个月内未通过的项目才能复议");
					//					}
				}
				
				FReCouncilApplyDO apply = FReCouncilApplyDAO.findByFormId(form.getFormId());
				
				boolean isUpdate = false;
				
				if (apply == null) { //新增
					//					if (BooleanEnum.NO.code().equals(project.getIsRecouncil())) {
					//						throw ExceptionFactory.newFcsException(
					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目不可申请复议");
					//					}
					//					if (project.getLastRecouncilTime() != null) {
					//						throw ExceptionFactory.newFcsException(
					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目只可复议一次");
					//					}
					
					apply = new FReCouncilApplyDO();
					BeanCopier.staticCopy(order, apply);
					apply.setRawAddTime(now);
				} else { //修改
					isUpdate = true;
					long applyId = apply.getId(); //防止页面不传参丢失数据
					BeanCopier.staticCopy(order, apply);
					apply.setId(applyId);
				}
				
				apply.setOldSpId(project.getSpId());
				apply.setOldSpCode(project.getSpCode());
				apply.setProjectCode(project.getProjectCode());
				apply.setFormId(form.getFormId());
				
				if (isUpdate) {
					FReCouncilApplyDAO.update(apply);
				} else {
					FReCouncilApplyDAO.insert(apply);
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//进入复议申请阶段
				ProjectDO project = (ProjectDO) FcsPmDomainHolder.get().getAttribute("projectDO");
				//project.setPhases(ProjectPhasesEnum.COUNCIL_PHASES.code());
				//project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
				//project.setStatus(ProjectStatusEnum.NORMAL.code());
				project.setIsRecouncil(BooleanEnum.NO.code());
				project.setLastRecouncilTime(now);
				projectDAO.update(project);
				return null;
			}
		});
	}
	
	@Override
	public FReCouncilApplyInfo queryApplyByFormId(long formId) {
		FReCouncilApplyDO apply = FReCouncilApplyDAO.findByFormId(formId);
		if (apply != null) {
			FReCouncilApplyInfo info = new FReCouncilApplyInfo();
			BeanCopier.staticCopy(apply, info);
			info.setCouncilBack(BooleanEnum.getByCode(apply.getCouncilBack()));
			return info;
		}
		return null;
	}
	
}
