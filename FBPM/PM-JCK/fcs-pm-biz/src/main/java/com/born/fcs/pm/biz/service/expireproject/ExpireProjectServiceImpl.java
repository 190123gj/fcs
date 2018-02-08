package com.born.fcs.pm.biz.service.expireproject;

import java.util.*;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.dal.dataobject.ExpireProjectNoticeTemplateDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectNoticeTemplateInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectNoticeTemplateOrder;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ExpireProjectDO;
import com.born.fcs.pm.dataobject.ExpireFormProjectDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireFormProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;

import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午2:00:36
 */
@Service("expireProjectService")
public class ExpireProjectServiceImpl extends BaseAutowiredDomainService implements
																			ExpireProjectService {
	
	@Override
	public FcsBaseResult add(final ExpireProjectOrder order) {
		return commonProcess(order, "新增到期逾期项目", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				boolean isLitigation=false;
				if(null!=order.getProjectCode()){
					ProjectDO projectDO=projectDAO.findByProjectCode(order.getProjectCode());
					isLitigation=ProjectUtil.isLitigation(projectDO.getBusiType());
				}
				if(!isLitigation){//诉讼类的项目不进入到期列表
				ExpireProjectDO doObj = new ExpireProjectDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setStatus(order.getStatus().code());
					doObj.setRawAddTime(getSysdate());
					expireProjectDAO.insert(doObj);

				}
				return null;
			}
		}, null, null);
	}
	@Override
	public FcsBaseResult updateToExpire(final ExpireProjectOrder order) {
		return commonProcess(order, "更新项目状态为到期", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ExpireProjectDO doObj = expireProjectDAO.findById(order.getId());
				if (null == doObj) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"找不到项目数据");
				}
				
				doObj.setStatus(ExpireProjectStatusEnum.EXPIRE.code());
				expireProjectDAO.update(doObj);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult updateToOverdue(final ExpireProjectOrder order) {
		return commonProcess(order, "更新项目状态为逾期", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ExpireProjectDO doObj = expireProjectDAO.findById(order.getId());
				if (null == doObj) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
							"找不到项目数据");
				}
				
				doObj.setStatus(ExpireProjectStatusEnum.OVERDUE.code());
				expireProjectDAO.update(doObj);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult updateToDone(final ExpireProjectOrder order) {
		return commonProcess(order, "更新项目状态为完成", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
//				if (StringUtil.isBlank(order.getRepayCertificate())) {
//					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
//						"请上传还款凭证");
//				}
				if(order.getReceipt()!=null&&!"".equals(order.getReceipt())){
					ExpireProjectNoticeTemplateDO templateDO=expireProjectNoticeTemplateDAO.findById(order.getId());
					if (null == templateDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
								"找不到通知书数据");
					}
					templateDO.setReceipt(order.getReceipt());
					expireProjectNoticeTemplateDAO.update(templateDO);
				}else {
					ExpireProjectDO doObj = expireProjectDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
								"找不到项目数据");
					}
					if ("IS".equals(order.getIsDONE())) {
						doObj.setStatus(ExpireProjectStatusEnum.DONE.code());
					}
					if (order.getRepayCertificate() != null && !"".equals(order.getRepayCertificate())) {
						doObj.setRepayCertificate(order.getRepayCertificate());
					}
					expireProjectDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ExpireProjectInfo queryExpireProjectByProjectCode(String projectCode) {
		ExpireProjectDO doObj = expireProjectDAO.findByProjectCode(projectCode);
		if (null == doObj) {
			return null;
		}
		
		ExpireProjectInfo info = new ExpireProjectInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setStatus(ExpireProjectStatusEnum.getByCode(doObj.getStatus()));
		return info;
	}

	@Override
	public ExpireProjectInfo queryExpireProjectByExpireId(String expireId) {
		ExpireProjectDO doObj = expireProjectDAO.findById(Long.parseLong(expireId));
		if (null == doObj) {
			return null;
		}

		ExpireProjectInfo info = new ExpireProjectInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setStatus(ExpireProjectStatusEnum.getByCode(doObj.getStatus()));
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<ExpireProjectInfo> queryExpireProjectInfo(	ExpireProjectQueryOrder queryOrder) {
		QueryBaseBatchResult<ExpireProjectInfo> baseBatchResult = new QueryBaseBatchResult<ExpireProjectInfo>();
		
		ExpireProjectDO expireProject = new ExpireProjectDO();
		BeanCopier.staticCopy(queryOrder, expireProject);
		if (null != queryOrder.getStatus()) {
			expireProject.setStatus(queryOrder.getStatus().code());
		}
		
		String expireDateBegin = null;
		if (null != queryOrder.getExpireDateBegin()) {
			expireDateBegin = DateUtil.dtSimpleFormat(queryOrder.getExpireDateBegin());
		}
		String expireDateEnd = null;
		if (null != queryOrder.getExpireDateEnd()) {
			expireDateEnd = DateUtil.dtSimpleFormat(queryOrder.getExpireDateEnd());
		}
		
		long totalSize = expireProjectDAO.findByConditionCount(expireProject, expireDateBegin,
			expireDateEnd);
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ExpireProjectDO> pageList = expireProjectDAO
				.findByCondition(expireProject, expireDateBegin, expireDateEnd,
					component.getFirstRecord(), component.getPageSize());
			
			List<ExpireProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ExpireProjectDO doObj : pageList) {
					ExpireProjectInfo info = new ExpireProjectInfo();
					BeanCopier.staticCopy(doObj, info);
					info.setStatus(ExpireProjectStatusEnum.getByCode(doObj.getStatus()));
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public QueryBaseBatchResult<ExpireFormProjectInfo> queryExpireFormProjectInfo(	ExpireProjectQueryOrder queryOrder) {
		QueryBaseBatchResult<ExpireFormProjectInfo> baseBatchResult = new QueryBaseBatchResult<ExpireFormProjectInfo>();
		if (null == queryOrder) {
			return baseBatchResult;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("customerName", queryOrder.getCustomerName());
		if (null != queryOrder.getStatus()) {
			paramMap.put("expireStatus", queryOrder.getStatus().code());
		}
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		long totalSize = extraDAO.searchExpireProjectCount(paramMap);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ExpireFormProjectDO> pageList = extraDAO.searchExpireProject(paramMap);
			
			List<ExpireFormProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ExpireFormProjectDO doObj : pageList) {
					ExpireFormProjectInfo info = new ExpireFormProjectInfo();
					ProjectInfo projectInfo = DoConvert.convertProjectDO2Info(doObj);
					BeanCopier.staticCopy(projectInfo, info);
					info.setTemplateId(doObj.getTemplateId());
					info.setExpireProjectId(doObj.getExpireProjectId());
					info.setExpireDate(doObj.getExpireDate());
					info.setExpireStatus(ExpireProjectStatusEnum.getByCode(doObj.getExpireStatus()));
					info.setRepayCertificate(doObj.getRepayCertificate());
					info.setTemplateProjectCode(doObj.getTemplateProjectCode());
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public FcsBaseResult saveNoticeTemplate(final ExpireProjectNoticeTemplateOrder order) {
		return commonProcess(order, "保存到期项目通知模板", new BeforeProcessInvokeService() {

			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				ExpireProjectNoticeTemplateDO templateDO = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getId() != null && order.getId() > 0) {
					templateDO = expireProjectNoticeTemplateDAO.findById(order.getId());
					if (templateDO == null) {
						throw ExceptionFactory
								.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "到期项目通知模板不存在");
					}
				}

				if (templateDO == null) { //新增
					templateDO = new ExpireProjectNoticeTemplateDO();
					BeanCopier.staticCopy(order, templateDO, UnBoxingConverter.getInstance());
					int year = Calendar.getInstance().get(Calendar.YEAR);
					templateDO.setSequence(getseq(order.getStatus().code(),order.getIsUnderwriting()));
					templateDO.setYear(year+"");
					templateDO.setRawAddTime(now);
					templateDO.setStatus(order.getStatus().code());
					expireProjectNoticeTemplateDAO.insert(templateDO);
				} else { //修改
					BeanCopier.staticCopy(order, templateDO, UnBoxingConverter.getInstance());
					expireProjectNoticeTemplateDAO.update(templateDO);
					templateDO.setStatus(order.getStatus().code());
				}

				return null;
			}
		}, null, null);
	}

	@Override
	public ExpireProjectNoticeTemplateInfo findTemplateByExpireIdAndStatus(String expireId,String status) {
		ExpireProjectNoticeTemplateInfo info=new ExpireProjectNoticeTemplateInfo();
		ExpireProjectNoticeTemplateDO templateDO= expireProjectNoticeTemplateDAO.findByExpireIdAndStatus(Long.parseLong(expireId),status);
		if(templateDO==null){
			return null;
		}
		BeanCopier.staticCopy(templateDO, info, UnBoxingConverter.getInstance());
		return info;
	}

	@Override
	public ExpireProjectNoticeTemplateInfo findTemplateById(long templateId) {
		ExpireProjectNoticeTemplateInfo info=new ExpireProjectNoticeTemplateInfo();
		ExpireProjectNoticeTemplateDO DO=expireProjectNoticeTemplateDAO.findById(templateId);
		if(DO==null){
			return null;
		}
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		return info;
	}


	/**
	 * 生成序列号
	 * @return
	 */
	private synchronized String getseq(String status,String isUnderwriting) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String seqName ="";
		if("IS".equals(isUnderwriting)){//承销项目
			seqName=SysDateSeqNameEnum.UNDERWRITING_PROJECT_NOTICE_TEMPLATE_SEQ.code()+year;
		}else{
		seqName= SysDateSeqNameEnum.EXPIRE_PROJECT_NOTICE_TEMPLATE_SEQ.code()+"_"+status+year;
		}
		long sequence=dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String seq=StringUtil.leftPad(String.valueOf(sequence), 4, "0");
		return seq;
	}

}
