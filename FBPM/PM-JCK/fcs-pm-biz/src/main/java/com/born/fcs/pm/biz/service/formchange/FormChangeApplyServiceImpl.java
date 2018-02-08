package com.born.fcs.pm.biz.service.formchange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.dal.dataobject.FormChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.FormChangeRecordDO;
import com.born.fcs.pm.dal.dataobject.FormChangeRecordDetailDO;
import com.born.fcs.pm.dataobject.FormChangeApplySearchDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.HttpClientUtil;
import com.born.fcs.pm.util.MD5Util;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeRecordStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordDetailInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;
import com.born.fcs.pm.ws.service.formchange.FormChangeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("formChangeApplyService")
public class FormChangeApplyServiceImpl extends BaseFormAutowiredDomainService
																				implements
																				FormChangeApplyService,
																				ApplicationContextAware,
																				AsynchronousService {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Autowired
	DateSeqService dateSeqService;
	
	private static ApplicationContext _applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		_applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return _applicationContext;
	}
	
	public static Object getBean(String beanName) throws BeansException {
		return _applicationContext.getBean(beanName);
	}
	
	@Override
	public FormBaseResult saveApply(final FormChangeApplyOrder order) {
		
		if (order.getFormCode() == null) {
			order.setFormCode(FormCodeEnum.FORM_CHANGE_APPLY);
		}
		
		return commonFormSaveProcess(order, "保存签报申请单", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//项目相关的签报
				if (StringUtil.isNotEmpty(order.getProjectCode())) {
					//项目信息
					ProjectInfo project = projectService.queryByCode(order.getProjectCode(), false);
					if (project == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
					}
					
					if (project.getStatus() == ProjectStatusEnum.PAUSE) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目已暂缓");
					}
					
					//如果是表单签报
					if (order.getApplyType() == FormChangeApplyTypeEnum.FORM) {
						
						//验证是否有进行中的签报
						int count = 0;
						FormRelatedUserQueryOrder qOrder = new FormRelatedUserQueryOrder();
						qOrder.setProjectCode(order.getProjectCode());
						qOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
						qOrder.setExeStatus(ExeStatusEnum.IN_PROGRESS);
						qOrder.setFormCode(FormCodeEnum.FORM_CHANGE_APPLY);
						qOrder.setPageSize(999);
						QueryBaseBatchResult<FormRelatedUserInfo> rels = formRelatedUserService
							.queryPage(qOrder);
						if (rels != null && rels.getTotalCount() > 0) {
							for (FormRelatedUserInfo rel : rels.getPageList()) {
								if (order.getFormId() != null
									&& rel.getFormId() != order.getFormId()) {
									count++;
								}
							}
						}
						
						//表单签报一次只允许一个
						if (count > 0) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目正在签报中");
						}
						
						FormChangeApplyEnum changeForm = order.getChangeForm();
						if (StringUtil.isNotEmpty(changeForm.getProcessService())) {
							//校验表单是否可签报
							FormChangeProcessService processService = (FormChangeProcessService) getBean(changeForm
								.getProcessService());
							if (processService != null) {
								FormCheckCanChangeOrder checkCanChangeOrder = new FormCheckCanChangeOrder();
								checkCanChangeOrder.setProject(project);
								checkCanChangeOrder.setChangeForm(order.getChangeForm());
								checkCanChangeOrder.setChangeFormId(order.getChangeFormId());
								checkCanChangeOrder.setCustomerId(project.getCustomerId());
								checkCanChangeOrder.setProjectCode(project.getProjectCode());
								FcsBaseResult checkResult = processService
									.checkCanChange(checkCanChangeOrder);
								if (!checkResult.isSuccess()) {
									throw ExceptionFactory.newFcsException(
										checkResult.getFcsResultEnum(), checkResult.getMessage());
								}
							}
						}
						
						//签报事项名称 = 项目编号+签报单据类型
						order.setApplyTitle(project.getProjectCode() + "-"
											+ order.getChangeForm().message());
					}
					
					FcsPmDomainHolder.get().addAttribute("project", project);
				}
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FormChangeApplyDO apply = null;
				if (order.getApplyId() != null && order.getApplyId() > 0) {
					apply = formChangeApplyDAO.findById(order.getApplyId());
					if (apply == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"申请单不存在");
					}
				}
				
				boolean isUpdate = false;
				if (apply == null) {//新增
					apply = new FormChangeApplyDO();
					BeanCopier.staticCopy(order, apply);
					apply.setRawAddTime(now);
					//生成签报编号
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String applyCode = "QB"
										+ sdf.format(now)
										+ "-"
										+ StringUtil.leftPad(String.valueOf(dateSeqService
											.getNextSeqNumberWithoutCache(
												SysDateSeqNameEnum.FORM_CHANGE_APPLY_CODE.code(),
												false)), 3, "0");
					apply.setApplyCode(applyCode);
				} else { //修改
					isUpdate = true;
					String applyCode = apply.getApplyCode();
					BeanCopier.staticCopy(order, apply);
					apply.setApplyCode(applyCode);
				}
				apply.setFormId(form.getFormId());
				apply.setApplyId(order.getApplyId() == null ? 0 : order.getApplyId());
				if (order.getApplyType() != null)
					apply.setApplyType(order.getApplyType().code());
				FormChangeApplyEnum changeForm = order.getChangeForm();
				if (changeForm != null) {
					apply.setChangeForm(changeForm.code());
				}
				
				if (StringUtil.isNotEmpty(order.getProjectCode())) {
					ProjectInfo project = (ProjectInfo) FcsPmDomainHolder.get().getAttribute(
						"project");
					if (project != null) {
						apply.setChangeFormId(order.getChangeFormId() == null ? 0 : order
							.getChangeFormId());
						apply.setProjectCode(project.getProjectCode());
						apply.setProjectName(project.getProjectName());
						apply.setBusiType(project.getBusiType());
						apply.setBusiTypeName(project.getBusiTypeName());
						apply.setCustomerId(project.getCustomerId());
						apply.setCustomerName(project.getCustomerName());
						apply.setCustomerType(project.getCustomerType() == null ? "" : project
							.getCustomerType().code());
					}
					if (order.getApplyType() == FormChangeApplyTypeEnum.ITEM)
						apply.setApplyTitle(order.getProjectCode() + "-项目事项签报");
				}
				
				if (order.getIsNeedCouncil() == null
					|| StringUtil.isNotEmpty(order.getProjectCode())) {
					apply.setIsNeedCouncil(BooleanEnum.NO.code());
				} else {
					apply.setIsNeedCouncil(order.getIsNeedCouncil().code());
				}
				apply.setStatus(FormChangeApplyStatusEnum.DRAFT.code());
				
				if (isUpdate) {
					formChangeApplyDAO.update(apply);
				} else {
					apply.setApplyId(formChangeApplyDAO.insert(apply));
				}
				
				FcsPmDomainHolder.get().addAttribute("apply", apply);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveRecord(final FormChangeRecordOrder order) {
		
		if (order.getApplyType() == FormChangeApplyTypeEnum.FORM) {
			String formData = order.getFormData();
			String orignalFormData = order.getOriginalFormData();
			if (MD5Util.getMD5_32(orignalFormData).equals(MD5Util.getMD5_32(formData))) {
				FormBaseResult result = createResult();
				result.setSuccess(false);
				result.setMessage("表单数据未改变");
				return result;
			}
		}
		
		return commonFormSaveProcess(order, "保存签报修改记录", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//这里就不重复处理表单了
				order.setIsFormChangeApply(BooleanEnum.IS);
				//保存申请单
				FormBaseResult result = saveApply(order);
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(),
						result.getMessage());
				}
				FormInfo form = result.getFormInfo();
				//表单签报记录修改历史
				if (order.getApplyType() == FormChangeApplyTypeEnum.FORM) {
					
					FormChangeRecordDO queryDO = new FormChangeRecordDO();
					queryDO.setUserId(order.getUserId());
					queryDO.setApplyFormId(result.getFormInfo().getFormId());
					queryDO.setTabIndex(order.getTabIndex() == null ? 0 : order.getTabIndex());
					List<String> statusList = Lists.newArrayList();
					statusList.add(FormChangeRecordStatusEnum.DRAFT.code());
					statusList.add(FormChangeRecordStatusEnum.APPLY_AUDITING.code());
					//一个单子同一个人只记录一次
					List<FormChangeRecordDO> records = formChangeRecordDAO.findByCondition(queryDO,
						statusList, 0l, 999l, null, null);
					FormChangeRecordDO record = null;
					boolean isUpdate = false;
					if (ListUtil.isEmpty(records)) { //新增记录
						record = new FormChangeRecordDO();
						BeanCopier.staticCopy(order, record);
						SimpleUserInfo userInfo = new SimpleUserInfo();
						userInfo.setUserId(order.getUserId());
						userInfo.setUserName(order.getUserName());
						userInfo.setUserAccount(order.getUserAccount());
						userInfo.setEmail(order.getUserEmail());
						userInfo.setMobile(order.getUserMobile());
						//执行时候的访问密钥
						record.setAccessToken(getAccessToken(userInfo));
						record.setRawAddTime(now);
					} else {//修改记录
						//不修改原始值，之记录最新的
						record = records.get(0);
						record.setFormData(order.getFormData());
						record.setPageContent(order.getPageContent());
						isUpdate = true;
					}
					
					record.setApplyFormId(form.getFormId());
					record.setStatus(FormChangeRecordStatusEnum.DRAFT.code());
					record.setUserId(order.getUserId());
					record.setDeptId(order.getDeptId());
					record.setTabIndex(order.getTabIndex() == null ? 0 : order.getTabIndex());
					if (isUpdate) {
						//全部重新更新很慢
						formChangeRecordDAO.updateNewData(record.getFormData(),
							record.getPageContent(), record.getRecordId());
						formChangeRecordDetailDAO.deleteByRecordId(record.getRecordId());
					} else {
						record.setRecordId(formChangeRecordDAO.insert(record));
					}
					
					//新增修改明细
					if (StringUtil.isNotEmpty(order.getDetailData())) {
						List<Map<String, String>> detailData = (List<Map<String, String>>) JSONArray
							.parse(order.getDetailData());
						for (Map<String, String> detail : detailData) {
							FormChangeRecordDetailDO detailDO = new FormChangeRecordDetailDO();
							detailDO.setRecordId(record.getRecordId());
							detailDO.setLabel(detail.get("label"));
							detailDO.setName(detail.get("name"));
							detailDO.setNewText(detail.get("newText"));
							detailDO.setNewValue(detail.get("newValue"));
							if (detail.containsKey("oldValue")) {
								detailDO.setOldText(detail.get("oldText"));
								detailDO.setOldValue(detail.get("oldValue"));
							} else {
								detailDO.setNewText(detail.get("newValue"));
							}
							detailDO.setRawAddTime(now);
							formChangeRecordDetailDAO.insert(detailDO);
						}
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	private String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("签报访问");
		FcsBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	@Override
	public FcsBaseResult reExeChange(long recordId) {
		FcsBaseResult executeResult = createResult();
		try {
			FormChangeRecordInfo record = queryRecord(recordId, false);
			asynchronousTaskJob.addAsynchronousService(this, new FormChangeRecordInfo[] { record });
			executeResult.setSuccess(true);
		} catch (Exception e) {
			executeResult.setSuccess(false);
			executeResult.setMessage("重新执行签报出错");
			logger.error("重新执行签报出错 ： {}", e);
		}
		return executeResult;
	}
	
	/**
	 * 执行签报
	 * @param record
	 */
	private FcsBaseResult executeChange(final FormChangeRecordInfo record) {
		
		FcsBaseResult res = createResult();
		
		if (record == null) {
			res.setSuccess(false);
			res.setMessage("修改记录为空");
			return res;
		}
		
		try {
			
			//重新生成执行的token
			if (record.getStatus() == FormChangeRecordStatusEnum.EXECUTE_FAIL) {
				FormChangeRecordDO recordDO = new FormChangeRecordDO();
				BeanCopier.staticCopy(record, recordDO);
				
				recordDO.setStatus(record.getStatus().code());
				
				SimpleUserInfo userInfo = new SimpleUserInfo();
				userInfo.setUserId(record.getUserId());
				userInfo.setUserName(record.getUserName());
				userInfo.setUserAccount(record.getUserAccount());
				
				//重新生成访问密钥
				String accessToken = getAccessToken(userInfo);
				
				recordDO.setAccessToken(accessToken);
				record.setAccessToken(accessToken);
				formChangeRecordDAO.updateAccessToken(accessToken, recordDO.getRecordId());
			}
			
			FormChangeApplyInfo apply = queryByFormId(record.getApplyFormId());
			
			String exeUrl = record.getExeUrl();
			final String formData = record.getFormData()
									+ "&loadMenu=NO&isFormChangeApply=IS&accessToken="
									+ record.getAccessToken();
			
			String intranetUrl = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_INTRANET_URL.code());
			if (StringUtil.isBlank(intranetUrl)) {
				intranetUrl = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
			}
			//内网访问地址
			exeUrl = intranetUrl + exeUrl;
			try {
				String result = HttpClientUtil.post(exeUrl, formData, 120000);
				if (StringUtil.isNotEmpty(result)) {
					HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
					boolean success = (Boolean) resultMap.get("success");
					String message = (String) resultMap.get("message");
					if (success) {
						formChangeRecordDAO.updateExeStatus(
							FormChangeRecordStatusEnum.EXECUTE_SUCCESS.code(), message,
							record.getRecordId());
						
						//签报执行后的业务处理
						FormChangeProcessService processService = null;
						if (apply.getChangeForm() != null
							&& StringUtil.isNotEmpty(apply.getChangeForm().getProcessService())) {
							processService = (FormChangeProcessService) getBean(apply
								.getChangeForm().getProcessService());
						}
						if (processService != null)
							processService.processChange(apply, record);
						
					} else {
						formChangeRecordDAO.updateExeStatus(
							FormChangeRecordStatusEnum.EXECUTE_FAIL.code(), message,
							record.getRecordId());
					}
				} else {
					formChangeRecordDAO.updateExeStatus(
						FormChangeRecordStatusEnum.EXECUTE_FAIL.code(), "执行签报保存数据失败",
						record.getRecordId());
				}
				
			} catch (Exception e) {
				formChangeRecordDAO.updateExeStatus(FormChangeRecordStatusEnum.EXECUTE_FAIL.code(),
					e.getMessage(), record.getRecordId());
				res.setSuccess(false);
				res.setMessage("执行签报保存数据出错");
				logger.error("执行签报保存数据出错：{}", e);
			}
			
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return res;
	}
	
	@Override
	public FcsBaseResult executeChange(long formId) {
		FcsBaseResult executeResult = createResult();
		try {
			FormChangeRecordQueryOrder order = new FormChangeRecordQueryOrder();
			order.setApplyFormId(formId);
			order.setLimitStart(0);
			order.setPageSize(999);
			order.setQueryDetail(false);
			//执行审核通过的记录
			order.setStatus(FormChangeRecordStatusEnum.APPLY_APPROVAL.code());
			QueryBaseBatchResult<FormChangeRecordInfo> records = searchRecord(order);
			if (records != null && records.isSuccess() && records.getTotalCount() > 0) {
				for (final FormChangeRecordInfo record : records.getPageList()) {
					asynchronousTaskJob.addAsynchronousService(this,
						new FormChangeRecordInfo[] { record });
				}
			}
		} catch (Exception e) {
			executeResult.setSuccess(false);
			executeResult.setMessage("执行签报出错");
			logger.error("执行签报出错 ： {}", e);
		}
		return executeResult;
	}
	
	@Override
	public QueryBaseBatchResult<FormChangeApplySearchInfo> searchForm(	FormChangeApplyQueryOrder order) {
		
		QueryBaseBatchResult<FormChangeApplySearchInfo> baseBatchResult = new QueryBaseBatchResult<FormChangeApplySearchInfo>();
		
		FormChangeApplySearchDO searchDO = new FormChangeApplySearchDO();
		BeanCopier.staticCopy(order, searchDO);
		searchDO.setFormUserId(order.getFormSubmitManId());
		if (order.getIsSelForCharge() != null) {
			searchDO.setIsSelForCharge(order.getIsSelForCharge().code());
		}
		
		if (order.getIsNeedCouncil() != null) {
			searchDO.setIsNeedCouncil(order.getIsNeedCouncil().code());
		}
		long totalSize = extraDAO.searchFormChangeApplyCount(searchDO);
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FormChangeApplySearchDO> pageList = extraDAO.searchFormChangeApply(searchDO);
		
		List<FormChangeApplySearchInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FormChangeApplySearchDO sf : pageList) {
				FormChangeApplySearchInfo info = new FormChangeApplySearchInfo();
				BeanCopier.staticCopy(sf, info);
				info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
				info.setApplyType(FormChangeApplyTypeEnum.getByCode(sf.getApplyType()));
				info.setChangeForm(FormChangeApplyEnum.getByCode(sf.getChangeForm()));
				info.setCustomerType(CustomerTypeEnum.getByCode(sf.getCustomerType()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(sf.getProjectStatus()));
				info.setIsNeedCouncil(BooleanEnum.getByCode(sf.getIsNeedCouncil()));
				info.setStatus(FormChangeApplyStatusEnum.getByCode(sf.getStatus()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<FormChangeApplyInfo> searchApply(FormChangeApplyQueryOrder order) {
		
		QueryBaseBatchResult<FormChangeApplyInfo> baseBatchResult = new QueryBaseBatchResult<FormChangeApplyInfo>();
		
		FormChangeApplyDO searchDO = new FormChangeApplyDO();
		BeanCopier.staticCopy(order, searchDO);
		
		long totalSize = formChangeApplyDAO.findByConditionCount(searchDO, order.getLoginUserId(),
			order.getDeptIdList(), order.getFormSubmitManId(),
			order.getIsSelForCharge() == null ? null : order.getIsSelForCharge().code(), order
				.getIsSelXinHui() == null ? null : order.getIsSelXinHui().code());
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FormChangeApplyDO> pageList = formChangeApplyDAO.findByCondition(searchDO,
			order.getLoginUserId(), order.getDeptIdList(), order.getFormSubmitManId(),
			order.getIsSelForCharge() == null ? null : order.getIsSelForCharge().code(),
			order.getIsSelXinHui() == null ? null : order.getIsSelXinHui().code(),
			order.getSortCol(), order.getSortOrder(), component.getFirstPage(),
			component.getPageSize());
		
		List<FormChangeApplyInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FormChangeApplyDO sf : pageList) {
				FormChangeApplyInfo info = new FormChangeApplyInfo();
				BeanCopier.staticCopy(sf, info);
				info.setApplyType(FormChangeApplyTypeEnum.getByCode(sf.getApplyType()));
				info.setChangeForm(FormChangeApplyEnum.getByCode(sf.getChangeForm()));
				info.setCustomerType(CustomerTypeEnum.getByCode(sf.getCustomerType()));
				info.setIsNeedCouncil(BooleanEnum.getByCode(sf.getIsNeedCouncil()));
				info.setStatus(FormChangeApplyStatusEnum.getByCode(sf.getStatus()));
				if (order.isQueryRecord()) {
					
					FormChangeRecordDO recordDO = new FormChangeRecordDO();
					recordDO.setApplyFormId(info.getFormId());
					List<FormChangeRecordDO> recordDOList = formChangeRecordDAO.findByCondition(
						recordDO, null, 0, 999, null, null);
					List<FormChangeRecordInfo> recordList = Lists.newArrayList();
					if (ListUtil.isNotEmpty(recordDOList)) {
						for (FormChangeRecordDO rDO : recordDOList) {
							FormChangeRecordInfo recordInfo = new FormChangeRecordInfo();
							BeanCopier.staticCopy(rDO, recordInfo);
							recordInfo.setStatus(FormChangeRecordStatusEnum.getByCode(sf
								.getStatus()));
							recordInfo.setDetailList(queryRecordDetail(recordInfo.getRecordId()));
							recordList.add(recordInfo);
						}
					}
					info.setRecords(recordList);
				}
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<FormChangeRecordInfo> searchRecord(FormChangeRecordQueryOrder order) {
		QueryBaseBatchResult<FormChangeRecordInfo> baseBatchResult = new QueryBaseBatchResult<FormChangeRecordInfo>();
		
		FormChangeRecordDO searchDO = new FormChangeRecordDO();
		BeanCopier.staticCopy(order, searchDO);
		long totalSize = formChangeRecordDAO.findByConditionCount(searchDO, order.getStatusList());
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FormChangeRecordDO> pageList = formChangeRecordDAO.findByCondition(searchDO,
			order.getStatusList(), component.getFirstRecord(), component.getPageSize(),
			order.getSortCol(), order.getSortOrder());
		
		List<FormChangeRecordInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FormChangeRecordDO sf : pageList) {
				FormChangeRecordInfo info = new FormChangeRecordInfo();
				BeanCopier.staticCopy(sf, info);
				info.setStatus(FormChangeRecordStatusEnum.getByCode(sf.getStatus()));
				if (order.isQueryDetail()) {
					info.setDetailList(queryRecordDetail(info.getRecordId()));
				}
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FormChangeRecordInfo queryRecord(long recordId, boolean queryDetail) {
		FormChangeRecordDO record = formChangeRecordDAO.findById(recordId);
		if (record != null) {
			FormChangeRecordInfo info = new FormChangeRecordInfo();
			BeanCopier.staticCopy(record, info);
			info.setStatus(FormChangeRecordStatusEnum.getByCode(record.getStatus()));
			if (queryDetail) {
				info.setDetailList(queryRecordDetail(info.getRecordId()));
			}
			return info;
		}
		return null;
	}
	
	@Override
	public FormChangeApplyInfo queryByFormId(long formId) {
		FormChangeApplyDO applyDO = formChangeApplyDAO.findByFormId(formId);
		if (applyDO != null) {
			FormChangeApplyInfo info = new FormChangeApplyInfo();
			BeanCopier.staticCopy(applyDO, info);
			info.setApplyType(FormChangeApplyTypeEnum.getByCode(applyDO.getApplyType()));
			info.setChangeForm(FormChangeApplyEnum.getByCode(applyDO.getChangeForm()));
			info.setCustomerType(CustomerTypeEnum.getByCode(applyDO.getCustomerType()));
			info.setIsNeedCouncil(BooleanEnum.getByCode(applyDO.getIsNeedCouncil()));
			info.setStatus(FormChangeApplyStatusEnum.getByCode(applyDO.getStatus()));
			return info;
		}
		return null;
	}
	
	@Override
	public List<FormChangeRecordDetailInfo> queryRecordDetail(long recordId) {
		List<FormChangeRecordDetailInfo> list = Lists.newArrayList();
		List<FormChangeRecordDetailDO> dataList = formChangeRecordDetailDAO
			.findByRecordId(recordId);
		if (ListUtil.isNotEmpty(dataList)) {
			for (FormChangeRecordDetailDO detail : dataList) {
				FormChangeRecordDetailInfo info = new FormChangeRecordDetailInfo();
				BeanCopier.staticCopy(detail, info);
				list.add(info);
			}
		}
		return list;
	}
	
	@Override
	public FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder) {
		
		FcsBaseResult result = createResult();
		
		//查看检查可否签报的服务
		if (StringUtil.isNotBlank(checkOrder.getChangeForm().getProcessService())) {
			FormChangeProcessService processService = (FormChangeProcessService) getBean(checkOrder
				.getChangeForm().getProcessService());
			if (processService != null) {
				result = processService.checkCanChange(checkOrder);
			} else {
				result.setSuccess(true);
			}
		} else {
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@Override
	public void execute(Object[] objects) {
		FormChangeRecordInfo record = (FormChangeRecordInfo) objects[0];
		executeChange(record);
	}
}
