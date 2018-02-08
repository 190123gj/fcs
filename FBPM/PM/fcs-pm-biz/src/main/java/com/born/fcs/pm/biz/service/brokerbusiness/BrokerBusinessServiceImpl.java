package com.born.fcs.pm.biz.service.brokerbusiness;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FBrokerBusinessDO;
import com.born.fcs.pm.dataobject.BrokerBusinessFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.brokerbusiness.BrokerBusinessFormInfo;
import com.born.fcs.pm.ws.info.brokerbusiness.FBrokerBusinessInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.brokerbusiness.BrokerBusinessQueryOrder;
import com.born.fcs.pm.ws.order.brokerbusiness.FBrokerBusinessOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.brokerbusiness.BrokerBusinessService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("brokerBusinessService")
public class BrokerBusinessServiceImpl extends BaseFormAutowiredDomainService implements
																				BrokerBusinessService {
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public FormBaseResult save(final FBrokerBusinessOrder order) {
		
		order.setRelatedProjectCode(order.getProjectCode());
		order.setFormCode(FormCodeEnum.BROKER_BUSINESS);
		
		return commonFormSaveProcess(order, "保存经纪业务申请单 ", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				FBrokerBusinessDO busiDO = FBrokerBusinessDAO.findByFormId(form.getFormId());
				boolean isUpdate = false;
				if (busiDO == null) {
					busiDO = new FBrokerBusinessDO();
					BeanCopier.staticCopy(order, busiDO);
					//生成编号
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					String projectCode = "JJ"
											+ sdf.format(now)
											+ "-"
											+ StringUtil.leftPad(String.valueOf(dateSeqService
												.getNextSeqNumberWithoutCache(
													SysDateSeqNameEnum.BROKER_BUSINESS_CODE.code(),
													false)), 4, "0");
					busiDO.setProjectCode(projectCode);
					busiDO.setRawAddTime(now);
				} else {
					BeanCopier.staticCopy(order, busiDO);
					isUpdate = true;
				}
				
				busiDO.setFormId(form.getFormId());
				if (StringUtil.isBlank(busiDO.getIsNeedCouncil())) {
					busiDO.setIsNeedCouncil(BooleanEnum.NO.code());
				}
				busiDO.setStatus(FormChangeApplyStatusEnum.DRAFT.code());
				
				if (isUpdate) {
					FBrokerBusinessDAO.updateByFormId(busiDO);
				} else {
					FBrokerBusinessDAO.insert(busiDO);
				}
				
				FcsPmDomainHolder.get().addAttribute("projectCode", busiDO.getProjectCode());
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				//保存业务经理到相关人员表
				FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				String projectCode = (String) FcsPmDomainHolder.get().getAttribute("projectCode");
				ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
				BeanCopier.staticCopy(formInfo, relatedUser);
				relatedUser.setProjectCode(projectCode);
				relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
				relatedUser.setRemark("经纪业务经理");
				projectRelatedUserService.setRelatedUser(relatedUser);
				return null;
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<BrokerBusinessFormInfo> queryPage(BrokerBusinessQueryOrder order) {
		QueryBaseBatchResult<BrokerBusinessFormInfo> batchResult = new QueryBaseBatchResult<BrokerBusinessFormInfo>();
		try {
			BrokerBusinessFormDO queryDO = new BrokerBusinessFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, queryDO);
			long totalCount = busiDAO.searchBrokerBusinessFormCount(queryDO);
			PageComponent component = new PageComponent(order, totalCount);
			List<BrokerBusinessFormDO> dataList = busiDAO.searchBrokerBusinessForm(queryDO);
			
			List<BrokerBusinessFormInfo> list = Lists.newArrayList();
			for (BrokerBusinessFormDO DO : dataList) {
				BrokerBusinessFormInfo info = new BrokerBusinessFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setStatus(FormChangeApplyStatusEnum.getByCode(DO.getStatus()));
				info.setIsNeedCouncil(BooleanEnum.getByCode(DO.getIsNeedCouncil()));
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询经纪业务表单失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FBrokerBusinessInfo findByFormId(long formId) {
		FBrokerBusinessDO busiDO = FBrokerBusinessDAO.findByFormId(formId);
		if (busiDO != null) {
			FBrokerBusinessInfo info = new FBrokerBusinessInfo();
			BeanCopier.staticCopy(busiDO, info);
			info.setIsNeedCouncil(BooleanEnum.getByCode(busiDO.getIsNeedCouncil()));
			info.setStatus(FormChangeApplyStatusEnum.getByCode(busiDO.getStatus()));
			return info;
		}
		return null;
	}
}
