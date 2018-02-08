package com.born.fcs.pm.biz.service.notice;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ConsentIssueNoticeDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.notice.ConsentIssueNoticeInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("consentIssueNoticeService")
public class ConsentIssueNoticeServiceImpl extends BaseFormAutowiredDomainService implements
																					ConsentIssueNoticeService {
	
	private ConsentIssueNoticeInfo convertDO2Info(ConsentIssueNoticeDO DO) {
		if (DO == null)
			return null;
		ConsentIssueNoticeInfo info = new ConsentIssueNoticeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setIsUploadReceipt(BooleanEnum.getByCode(DO.getIsUploadReceipt()));
		return info;
	}
	
	@Override
	public ConsentIssueNoticeInfo findById(long noticeId) {
		ConsentIssueNoticeInfo info = null;
		if (noticeId > 0) {
			ConsentIssueNoticeDO DO = consentIssueNoticeDAO.findById(noticeId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final ConsentIssueNoticeOrder order) {
		
		return commonProcess(order, "保存同意发行通知书", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ConsentIssueNoticeDO consentIssueNoticeDO = null;
				if (order.getNoticeId() != null && order.getNoticeId() > 0) {
					consentIssueNoticeDO = consentIssueNoticeDAO.findById(order.getNoticeId());
					if (consentIssueNoticeDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"同意发行通知书信息不存在");
					}
				}
				
				if (consentIssueNoticeDO == null) { //新增
					consentIssueNoticeDO = new ConsentIssueNoticeDO();
					BeanCopier.staticCopy(order, consentIssueNoticeDO,
						UnBoxingConverter.getInstance());
					consentIssueNoticeDO.setCustomerType(order.getCustomerType());
					consentIssueNoticeDO.setIsUploadReceipt(BooleanEnum.NO.code());//新增时  未上传回执
					if (consentIssueNoticeDO.getRawAddTime() == null) {
						consentIssueNoticeDO.setRawAddTime(now);
					}
					Long id = consentIssueNoticeDAO.insert(consentIssueNoticeDO);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else { //修改
					BeanCopier.staticCopy(order, consentIssueNoticeDO,
						UnBoxingConverter.getInstance());
					//					decisionInstitution.setDeleteMark("0");
					consentIssueNoticeDAO.update(consentIssueNoticeDO);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ConsentIssueNoticeInfo> query(ConsentIssueNoticeQueryOrder order) {
		QueryBaseBatchResult<ConsentIssueNoticeInfo> baseBatchResult = new QueryBaseBatchResult<ConsentIssueNoticeInfo>();
		try {
			ConsentIssueNoticeDO queryCondition = new ConsentIssueNoticeDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, queryCondition);
			
			if (order.getNoticeId() != null)
				queryCondition.setNoticeId(order.getNoticeId());
			long totalCount = extraDAO.searchConsentIssueNoticelistCount(queryCondition,
				order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ConsentIssueNoticeDO> pageList = extraDAO.searchConsentIssueNoticeListList(
				queryCondition, component.getFirstRecord(), component.getPageSize(),
				order.getDeptIdList(), order.getSortCol(), order.getSortOrder());
			
			List<ConsentIssueNoticeInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (ConsentIssueNoticeDO sf : pageList) {
					ConsentIssueNoticeInfo notice = convertDO2Info(sf);
					list.add(notice);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询同意发行通知书项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
		
	}
	
	@Override
	public int deleteById(long noticeId) {
		
		return consentIssueNoticeDAO.deleteById(noticeId);
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FcsBaseResult uploadReceipt(final ConsentIssueNoticeOrder order) {
		return commonProcess(order, "更新发行通知书上传回执", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				ConsentIssueNoticeDO consentIssueNoticeDO = consentIssueNoticeDAO.findById(order
					.getNoticeId());
				if (null == consentIssueNoticeDO) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"找不到发行通知书数据");
				}
				consentIssueNoticeDO.setIsUploadReceipt(BooleanEnum.IS.code());
				consentIssueNoticeDO.setReceiptAttachment(order.getReceiptAttachment());
				
				consentIssueNoticeDAO.update(consentIssueNoticeDO);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryConsentIssueNotice(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ConsentIssueNoticeDO projectDO = new ConsentIssueNoticeDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			long totalCount = extraDAO.searchConsentIssueNoticeCount(projectDO,
				order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchConsentIssueNoticeList(projectDO,
				component.getFirstRecord(), component.getPageSize(), order.getDeptIdList(),
				order.getSortCol(), order.getSortOrder());
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (ProjectDO sf : pageList) {
					ProjectInfo project = DoConvert.convertProjectDO2Info(sf);
					list.add(project);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询可发行通知书的项目失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
}
