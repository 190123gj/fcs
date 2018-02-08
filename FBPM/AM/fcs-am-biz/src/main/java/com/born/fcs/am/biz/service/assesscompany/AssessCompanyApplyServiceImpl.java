package com.born.fcs.am.biz.service.assesscompany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.convert.UnBoxingConverter;
import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.AssessCompanyBusinessScopeDO;
import com.born.fcs.am.dal.dataobject.AssessCompanyContactDO;
import com.born.fcs.am.dal.dataobject.AssessCompanyEvaluateDO;
import com.born.fcs.am.dal.dataobject.AssetsAssessCompanyDO;
import com.born.fcs.am.dal.dataobject.FAssessCompanyApplyDO;
import com.born.fcs.am.dal.dataobject.FAssessCompanyApplyItemDO;
import com.born.fcs.am.dataobject.AssessCompanyApplyFormDO;
import com.born.fcs.am.ws.enums.AppointWayEnum;
import com.born.fcs.am.ws.enums.AssessCompanyApplyStatusEnum;
import com.born.fcs.am.ws.enums.AssessCompanyStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyBusinessScopeInfo;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyContactInfo;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyEvaluateInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyItemInfo;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyEvaluateOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;
import com.born.fcs.am.ws.service.assesscompany.AssetsAssessCompanyService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.collection.ArrayUtils;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("assessCompanyApplyService")
public class AssessCompanyApplyServiceImpl extends BaseFormAutowiredDomainService implements
																					AssessCompanyApplyService {
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	protected AssetsAssessCompanyService assetsAssessCompanyService;
	/** 事务模板 */
	@Autowired
	protected TransactionTemplate transactionTemplate;
	
	private FAssessCompanyApplyInfo convertDO2Info(FAssessCompanyApplyDO DO) {
		if (DO == null)
			return null;
		FAssessCompanyApplyInfo info = new FAssessCompanyApplyInfo();
		BeanCopier.staticCopy(DO, info);
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setAppointWay(AppointWayEnum.getByCode(DO.getAppointWay()));
		info.setApplyStatus(AssessCompanyApplyStatusEnum.getByCode(DO.getApplyStatus()));
		return info;
	}
	
	private FAssessCompanyApplyItemInfo convertDO2ItemInfo(FAssessCompanyApplyItemDO DO) {
		if (DO == null)
			return null;
		FAssessCompanyApplyItemInfo info = new FAssessCompanyApplyItemInfo();
		info.setStatus(AssessCompanyStatusEnum.getByCode(DO.getStatus()));
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private AssessCompanyEvaluateInfo convertEvaluateDO2Info(AssessCompanyEvaluateDO DO) {
		if (DO == null)
			return null;
		AssessCompanyEvaluateInfo info = new AssessCompanyEvaluateInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsReview(BooleanEnum.getByCode(DO.getIsReview()));
		return info;
	}
	
	@Override
	public FAssessCompanyApplyInfo findByFormId(long formId) {
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		FAssessCompanyApplyInfo info = convertDO2Info(DO);
		return info;
	}
	
	@Override
	public FormBaseResult save(final FAssessCompanyApplyOrder order) {
		return commonFormSaveProcess(order, "评估公司申请单", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() == null || order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					// 保存
					FAssessCompanyApplyDO DO = new FAssessCompanyApplyDO();
					BeanCopier.staticCopy(order, DO);
					DO.setFormId(formInfo.getFormId());
					DO.setRawAddTime(now);
					if (order.getReplacedId() > 0) {// 更换
						FAssessCompanyApplyDO DO1 = FAssessCompanyApplyDAO.findById(order
							.getReplacedId());
						// DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_DRAFT
						// .code());
						
						DO1.setApplyStatus(AssessCompanyApplyStatusEnum.BE_CHANGE.code());// 被更换中
						FAssessCompanyApplyDAO.update(DO1);
					} else {// 指定
						DO.setApplyStatus(AssessCompanyApplyStatusEnum.NOT_SPECIFIED.code());
					}
					long id = FAssessCompanyApplyDAO.insert(DO);// 主表id
					//更换时，需要把更换的评估公司信息 存储到 f_assess_company_apply_item 表中
					//便于取当时的存储时评估公司的信息
					if (order.getReplacedId() > 0) {
						FAssessCompanyApplyItemDAO.deleteByApplyId(id);//删除评估公司临时存储
						String[] strCompanyIds = DO.getCompanyId().split(",");
						String[] strcompanyNames = DO.getCompanyName().split(",");
						for (int i = 0; i < strCompanyIds.length; i++) {
							FAssessCompanyApplyItemDO itemDO = FAssessCompanyApplyItemDAO
								.findByApplyIdAndCompanyId(DO.getId(),
									Long.parseLong(strCompanyIds[i]));
							if (itemDO == null) {
								itemDO = new FAssessCompanyApplyItemDO();
								AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO
									.findById(Long.parseLong(strCompanyIds[i]));
								itemDO.setApplyId(DO.getId());
								itemDO.setCompanyId(Long.parseLong(strCompanyIds[i]));
								itemDO.setCompanyName(strcompanyNames[i]);
								CompanyCopyToApplyItem(companyDO, itemDO);
								itemDO.setRawAddTime(now);
								FAssessCompanyApplyItemDAO.insert(itemDO);
							}
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					// 更新
					Long id = order.getId();
					FAssessCompanyApplyDO applyDO = FAssessCompanyApplyDAO.findById(id);
					if (null == applyDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到评估公司申请单记录");
					}
					if (order.getProjectCode() != null) {
						applyDO.setProjectCode(order.getProjectCode());
					}
					if (order.getProjectName() != null) {
						applyDO.setProjectName(order.getProjectName());
					}
					if (order.getCustomerId() > 0) {
						applyDO.setCustomerId(order.getCustomerId());
					}
					if (order.getCustomerName() != null) {
						applyDO.setCustomerName(order.getCustomerName());
					}
					if (order.getCustomerType() != null) {
						applyDO.setCustomerType(order.getCustomerType());
					}
					if (order.getCustomerAddr() != null) {
						applyDO.setCustomerAddr(order.getCustomerAddr());
					}
					if (order.getCountryName() != null) {
						applyDO.setCountryName(order.getCountryName());
					}
					if (order.getCountryCode() != null) {
						applyDO.setCountryCode(order.getCountryCode());
					}
					if (order.getProvinceCode() != null) {
						applyDO.setProvinceCode(order.getProvinceCode());
					}
					if (order.getProvinceName() != null) {
						applyDO.setProvinceName(order.getProvinceName());
					}
					if (order.getCityCode() != null) {
						applyDO.setCityCode(order.getCityCode());
					}
					if (order.getCityName() != null) {
						applyDO.setCityName(order.getCityName());
					}
					if (order.getCountyCode() != null) {
						applyDO.setCountyCode(order.getCountyCode());
					}
					if (order.getCountyName() != null) {
						applyDO.setCountyName(order.getCountyName());
					}
					
					applyDO.setQualityLand(order.getQualityLand());
					
					applyDO.setQualityHouse(order.getQualityHouse());
					
					applyDO.setQualityAssets(order.getQualityAssets());
					
					if (order.getAppointPerson() > 0) {
						applyDO.setAppointPerson(order.getAppointPerson());
					}
					if (order.getAppointPersonAccount() != null) {
						applyDO.setAppointPersonAccount(order.getAppointPersonAccount());
					}
					if (order.getAppointPersonName() != null) {
						applyDO.setAppointPersonName(order.getAppointPersonName());
					}
					if (order.getAppointWay() != null) {
						applyDO.setAppointWay(order.getAppointWay());
					}
					
					applyDO.setAppointRemark(order.getAppointRemark());
					
					if (order.getCompanyId() != null) {
						applyDO.setCompanyId(order.getCompanyId());
					}
					if (order.getCompanyName() != null) {
						applyDO.setCompanyName(order.getCompanyName());
					}
					if (order.getReplacedId() > 0) {// 更换
						// applyDO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_DRAFT
						// .code());
						FAssessCompanyApplyDO DO1 = FAssessCompanyApplyDAO.findById(order
							.getReplacedId());
						DO1.setApplyStatus(AssessCompanyApplyStatusEnum.BE_CHANGE.code());// 被更换中
						FAssessCompanyApplyDAO.update(DO1);
						
					} else {// 指定
						applyDO.setApplyStatus(AssessCompanyApplyStatusEnum.NOT_SPECIFIED.code());
					}
					FAssessCompanyApplyDAO.update(applyDO);// 更新主表信息
					
					//更换时，需要把更换的评估公司信息 存储到 f_assess_company_apply_item 表中
					//便于取当时的存储时评估公司的信息
					if (order.getReplacedId() > 0) {
						FAssessCompanyApplyItemDAO.deleteByApplyId(id);//删除评估公司临时存储
						String[] strCompanyIds = applyDO.getCompanyId().split(",");
						String[] strcompanyNames = applyDO.getCompanyName().split(",");
						for (int i = 0; i < strCompanyIds.length; i++) {
							FAssessCompanyApplyItemDO itemDO = FAssessCompanyApplyItemDAO
								.findByApplyIdAndCompanyId(applyDO.getId(),
									Long.parseLong(strCompanyIds[i]));
							if (itemDO == null) {
								itemDO = new FAssessCompanyApplyItemDO();
								AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO
									.findById(Long.parseLong(strCompanyIds[i]));
								itemDO.setApplyId(applyDO.getId());
								itemDO.setCompanyId(Long.parseLong(strCompanyIds[i]));
								itemDO.setCompanyName(strcompanyNames[i]);
								CompanyCopyToApplyItem(companyDO, itemDO);
								itemDO.setRawAddTime(now);
								FAssessCompanyApplyItemDAO.insert(itemDO);
							}
						}
					}
					
				}
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FAssessCompanyApplyInfo> query(FAssessCompanyApplyQueryOrder order) {
		QueryBaseBatchResult<FAssessCompanyApplyInfo> baseBatchResult = new QueryBaseBatchResult<FAssessCompanyApplyInfo>();
		try {
			AssessCompanyApplyFormDO DO = new AssessCompanyApplyFormDO();
			BeanCopier.staticCopy(order, DO);
			
			long totalCount = extraDAO.searchAssessCompanyApplyCount(DO,
				order.getSubmitTimeStart(), order.getSubmitTimeEnd(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<AssessCompanyApplyFormDO> pageList = extraDAO.searchAssessCompanyApplyList(DO,
				component.getFirstRecord(), component.getPageSize(), order.getSubmitTimeStart(),
				order.getSubmitTimeEnd(), order.getDeptIdList(), order.getSortCol(),
				order.getSortOrder());
			
			List<FAssessCompanyApplyInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (AssessCompanyApplyFormDO sf : pageList) {
					FAssessCompanyApplyInfo info = new FAssessCompanyApplyInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					info.setCustomerType(CustomerTypeEnum.getByCode(sf.getCustomerType()));
					info.setAppointWay(AppointWayEnum.getByCode(sf.getAppointWay()));
					info.setApplyStatus(AssessCompanyApplyStatusEnum.getByCode(sf.getApplyStatus()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询评估公司申请单列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FAssessCompanyApplyInfo findById(long id) {
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findById(id);
		
		return convertDO2Info(DO);
	}
	
	@Override
	public FcsBaseResult saveAssessCompanyEvaluate(final AssessCompanyEvaluateOrder order) {
		return commonProcess(order, "保存评价评估公司信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				AssessCompanyEvaluateDO evaluate = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getId() != null && order.getId() > 0) {
					evaluate = assessCompanyEvaluateDAO.findById(order.getId());
					if (evaluate == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"评价评估公司信息不存在");
					}
				}
				
				if (evaluate == null) { // 新增
					evaluate = new AssessCompanyEvaluateDO();
					BeanCopier.staticCopy(order, evaluate, UnBoxingConverter.getInstance());
					evaluate.setRawAddTime(now);
					long evaluateId = assessCompanyEvaluateDAO.insert(evaluate);
					
					// 更新评估公司所得分 本次评价
					if (StringUtil.isNotEmpty(order.getIsReview())
						&& BooleanEnum.NO.code().equals(order.getIsReview())) {
						long companyId = order.getCompanyId();
						AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO.findById(order
							.getCompanyId());// 评估公司信息
						List<AssessCompanyEvaluateDO> listEvaluateDO = assessCompanyEvaluateDAO
							.findByCompanyId(order.getCompanyId());
						
						int evaluateTime = listEvaluateDO.size();// 评价次数
						double workSituation = 0;// 现场工作情况
						double attachment = 0;// 附件齐全程度
						double technicalLevel = 0;// 评估报告技术水平
						double evaluationEfficiency = 0;// 评估效率
						double cooperationSituation = 0;// 评估效率
						double serviceAttitude = 0;// 服务态度
						// 计算总的评分
						
						for (AssessCompanyEvaluateDO assessCompanyEvaluateDO : listEvaluateDO) {
							workSituation += assessCompanyEvaluateDO.getWorkSituation();
							attachment += assessCompanyEvaluateDO.getAttachment();
							technicalLevel += assessCompanyEvaluateDO.getTechnicalLevel();
							evaluationEfficiency += assessCompanyEvaluateDO
								.getEvaluationEfficiency();
							cooperationSituation += assessCompanyEvaluateDO
								.getCooperationSituation();
							serviceAttitude += assessCompanyEvaluateDO.getServiceAttitude();
						}
						// 更新评价得分
						companyDO.setWorkSituation((Double) MathUtils.divide(workSituation,
							evaluateTime));
						companyDO.setAttachment((Double) MathUtils.divide(attachment, evaluateTime));
						companyDO.setTechnicalLevel((Double) MathUtils.divide(technicalLevel,
							evaluateTime));
						companyDO.setEvaluationEfficiency((Double) MathUtils.divide(
							evaluationEfficiency, evaluateTime));
						companyDO.setCooperationSituation((Double) MathUtils.divide(
							cooperationSituation, evaluateTime));
						companyDO.setServiceAttitude((Double) MathUtils.divide(serviceAttitude,
							evaluateTime));
						assetsAssessCompanyDAO.update(companyDO);
					}
					
				} else { // 修改
					long companyId = order.getId();
					BeanCopier.staticCopy(order, evaluate, UnBoxingConverter.getInstance());
					assessCompanyEvaluateDAO.update(evaluate);
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public List<AssessCompanyEvaluateInfo> findEvaluateByApplyId(long applyId) {
		List<AssessCompanyEvaluateInfo> listInfo = Lists.newArrayList();
		List<AssessCompanyEvaluateDO> listEvaluateDO = assessCompanyEvaluateDAO
			.findByApplyId(applyId);
		for (AssessCompanyEvaluateDO assessCompanyEvaluateDO : listEvaluateDO) {
			listInfo.add(convertEvaluateDO2Info(assessCompanyEvaluateDO));
		}
		return listInfo;
	}
	
	@Override
	public Boolean IsEvaluateByProjectCode(String projectCode) {
		Boolean isEvaluate = true;
		try {
			ProjectRelatedUserInfo relatedUserInfo = projectRelatedUserService
				.getRiskManager(projectCode);
			if (relatedUserInfo == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前项目风险经理不存在");
			}
			long riskManagerId = relatedUserInfo.getUserId();//风险经理ID
			List<FAssessCompanyApplyDO> listApplyDO = FAssessCompanyApplyDAO
				.findByProjectCode(projectCode);
			for (FAssessCompanyApplyDO DO : listApplyDO) {
				List<AssessCompanyEvaluateDO> listEvaluateDO = assessCompanyEvaluateDAO
					.findByApplyId(DO.getId());
				if (listEvaluateDO != null) {
					for (AssessCompanyEvaluateDO assessCompanyEvaluateDO : listEvaluateDO) {
						if (riskManagerId != assessCompanyEvaluateDO.getEvaluatePerson()) {
							isEvaluate = false;
						} else {
							isEvaluate = true;
							break;
						}
					}
				} else {
					isEvaluate = false;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("查询评估公司申请单列表失败" + e.getMessage(), e);
		}
		return isEvaluate;
	}
	
	@Override
	public AssetsAssessCompanyInfo findByApplyIdAndCompany(long applyId, long companyId) {
		AssetsAssessCompanyInfo info = new AssetsAssessCompanyInfo();
		FAssessCompanyApplyItemDO DO = FAssessCompanyApplyItemDAO.findByApplyIdAndCompanyId(
			applyId, companyId);
		String[] contactNames = DO.getContactName().split(",");
		String[] contactNumbers = DO.getContactNumber().split(",");
		String[] scopeRegions = DO.getBusinessScopeRegion().split(",");
		String[] codes = DO.getCode().split(",");
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setId(DO.getCompanyId());
		info.setStatus(AssessCompanyStatusEnum.getByCode(DO.getStatus()));
		List<AssessCompanyContactInfo> contactInfos = new ArrayList<AssessCompanyContactInfo>();
		
		List<AssessCompanyBusinessScopeInfo> scopeInfos = new ArrayList<AssessCompanyBusinessScopeInfo>();
		for (int i = 0; i < contactNames.length; i++) {
			AssessCompanyContactInfo contactInfo = new AssessCompanyContactInfo();
			contactInfo.setContactName(contactNames[i]);
			contactInfo.setContactNumber(contactNumbers[i]);
			contactInfos.add(contactInfo);
		}
		for (int i = 0; i < scopeRegions.length; i++) {
			AssessCompanyBusinessScopeInfo scopeInfo = new AssessCompanyBusinessScopeInfo();
			scopeInfo.setBusinessScopeRegion(scopeRegions[i]);
			scopeInfo.setCode(codes[i]);
			scopeInfos.add(scopeInfo);
		}
		info.setContactInfos(contactInfos);
		info.setScopeInfos(scopeInfos);
		
		return info;
	}
	
	private void CompanyCopyToApplyItem(AssetsAssessCompanyDO companyDO,
										FAssessCompanyApplyItemDO itemDO) {
		
		itemDO.setQualityAssets(companyDO.getQualityAssets());
		itemDO.setQualityHouse(companyDO.getQualityHouse());
		itemDO.setQualityLand(companyDO.getQualityLand());
		itemDO.setCityCode(companyDO.getCityCode());
		itemDO.setCity(companyDO.getCity());
		itemDO.setCountyCode(companyDO.getCountyCode());
		itemDO.setCountryName(companyDO.getCountyName());
		itemDO.setProvinceCode(companyDO.getProvinceCode());
		itemDO.setProvinceName(companyDO.getProvinceName());
		itemDO.setCountryCode(companyDO.getCountryCode());
		itemDO.setCountryName(companyDO.getCountryName());
		itemDO.setContactAddr(companyDO.getContactAddr());
		itemDO.setRegisteredCapital(companyDO.getRegisteredCapital());
		itemDO.setStatus(companyDO.getStatus());
		itemDO.setAttach(companyDO.getAttach());
		itemDO.setRemark(companyDO.getRemark());
		itemDO.setWorkSituation(companyDO.getWorkSituation());
		itemDO.setAttachment(companyDO.getAttachment());
		itemDO.setTechnicalLevel(companyDO.getTechnicalLevel());
		itemDO.setEvaluationEfficiency(companyDO.getEvaluationEfficiency());
		itemDO.setCooperationSituation(companyDO.getCooperationSituation());
		itemDO.setServiceAttitude(companyDO.getServiceAttitude());
		List<AssessCompanyContactDO> listContactDO = assessCompanyContactDAO
			.findByAssessCompanyId(companyDO.getId());
		String contactName = "";
		String contactNumber = "";
		if (listContactDO != null) {
			for (AssessCompanyContactDO assessCompanyContactDO : listContactDO) {
				contactName += assessCompanyContactDO.getContactName() + ",";
				contactNumber += assessCompanyContactDO.getContactNumber() + ",";
			}
			contactName = contactName.substring(0, contactName.length() - 1);
			contactNumber = contactNumber.substring(0, contactNumber.length() - 1);
		}
		itemDO.setContactName(contactName);
		itemDO.setContactNumber(contactNumber);
		List<AssessCompanyBusinessScopeDO> listScopeDO = assessCompanyBusinessScopeDAO
			.findByAssessCompanyId(companyDO.getId());
		String businessScopeRegion = "";
		String code = "";
		if (listScopeDO != null) {
			for (AssessCompanyBusinessScopeDO assessCompanyBusinessScopeDO : listScopeDO) {
				businessScopeRegion += assessCompanyBusinessScopeDO.getBusinessScopeRegion() + ",";
				code += assessCompanyBusinessScopeDO.getCode() + ",";
			}
			businessScopeRegion = businessScopeRegion
				.substring(0, businessScopeRegion.length() - 1);
			code = code.substring(0, code.length() - 1);
		}
		itemDO.setBusinessScopeRegion(businessScopeRegion);
		itemDO.setCode(code);
	}
	
	@Override
	public List<AssetsAssessCompanyInfo> assessCompanyeExtract(long id, String cityName,
																String provinceName) {
		//用来保存筛选符合要求的评估公司
		List<AssetsAssessCompanyInfo> listCompanyForRequired = new ArrayList<AssetsAssessCompanyInfo>();
		try {
			if (cityName != null || provinceName != null) {//目前不支持国外客户所在区域在国外
				FAssessCompanyApplyInfo assessCompanyApplyInfo = findById(id);
				if (assessCompanyApplyInfo == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "评估公司申请单不存在!");
				}
				resetCityName(assessCompanyApplyInfo);
				String qualityAssets = assessCompanyApplyInfo.getQualityAssets();//资产
				String qualityHouse = assessCompanyApplyInfo.getQualityHouse();//房屋
				String qualityLand = assessCompanyApplyInfo.getQualityLand();//土地  全国包括  全省
				String[] qualityHouses = { "一级", "二级", "三级" };
				
				List<String> qualityAssetsList = Lists.newArrayList();
				if (qualityAssets != null) {
					String[] qualityAssetsStr = qualityAssets.split(",");
					for (int i = 0; i < qualityAssetsStr.length; i++) {
						qualityAssetsList.add(qualityAssetsStr[i]);
					}
				}
				List<String> qualityHouseList = Lists.newArrayList();
				if (StringUtil.isNotEmpty(qualityHouse)) {
					if ("二级".equals(qualityHouse)) {
						qualityHouseList.add(qualityHouses[0]);
						qualityHouseList.add(qualityHouses[1]);
						
					}
					if ("三级".equals(qualityHouse)) {
						qualityHouseList.add(qualityHouses[0]);
						qualityHouseList.add(qualityHouses[1]);
						qualityHouseList.add(qualityHouses[2]);
					}
					if ("一级".equals(qualityHouse)) {
						qualityHouseList.add(qualityHouses[0]);
					}
					
				}
				List<String> qualityLandList = Lists.newArrayList();
				if (StringUtil.isNotEmpty(qualityLand)) {
					if ("全省".equals(qualityLand)) {
						qualityLandList.add("全省");
						qualityLandList.add("全国");
					}
					if ("全国".equals(qualityLand)) {
						qualityLandList.add("全国");
					}
				}
				listCompanyForRequired = conditionScreen(cityName, provinceName, qualityAssetsList,
					qualityHouseList, qualityLandList);
				//				if (StringUtil.isNotEmpty(qualityLand) && "全省".equals(qualityLand)
				//					&& listCompanyForRequired.size() == 0) {//全省筛选不出来
				//					qualityLandList = Lists.newArrayList();
				//					qualityLandList.add("全国");
				//					qualityLandList.add("全省");
				//					listCompanyForRequired = conditionScreen(cityName, provinceName,
				//						qualityAssetsList, qualityHouseList, qualityLandList);//再次筛选
				//				}
				//				if (StringUtil.isNotEmpty(qualityHouse) && listCompanyForRequired.size() == 0) {//房产 精确查询无结果，模糊查询
				//					if ("二级".equals(qualityHouse)) {
				//						qualityHouseList.add(qualityHouses[0]);
				//						qualityHouseList.add(qualityHouses[1]);
				//						listCompanyForRequired = conditionScreen(cityName, provinceName,
				//							qualityAssetsList, qualityHouseList, qualityLandList);//再次筛选
				//					}
				//					if ("三级".equals(qualityHouse)) {
				//						qualityHouseList.add(qualityHouses[0]);
				//						qualityHouseList.add(qualityHouses[1]);
				//						qualityHouseList.add(qualityHouses[2]);
				//						listCompanyForRequired = conditionScreen(cityName, provinceName,
				//							qualityAssetsList, qualityHouseList, qualityLandList);//再次筛选
				//					}
				//				}
				if (listCompanyForRequired.size() == 0) {
					listCompanyForRequired = manyCompany(cityName, provinceName, qualityAssets,
						qualityHouseList, qualityLandList);
					//					if (StringUtil.isNotEmpty(qualityLand) && "全省".equals(qualityLand)
					//						&& listCompanyForRequired.size() == 0) {//全省筛选不出来
					//						qualityLandList = Lists.newArrayList();
					//						qualityLandList.add("全国");
					//						qualityLandList.add("全省");
					//						listCompanyForRequired = manyCompany(cityName, provinceName, qualityAssets,
					//							qualityHouse, qualityLand);//再次筛选
					//					}
					//					if (StringUtil.isNotEmpty(qualityHouse) && listCompanyForRequired.size() == 0) {//房产 精确查询无结果，模糊查询
					//						if ("二级".equals(qualityHouse)) {
					//							String tempQualityHouse = qualityHouses[0];
					//							listCompanyForRequired = manyCompany(cityName, provinceName,
					//								qualityAssets, tempQualityHouse, qualityLand);//再次筛选
					//						}
					//						if ("三级".equals(qualityHouse)) {
					//							String tempQualityHouse = qualityHouses[1];
					//							listCompanyForRequired = manyCompany(cityName, provinceName,
					//								qualityAssets, tempQualityHouse, qualityLand);//再次筛选
					//							if (listCompanyForRequired.size() == 0) {
					//								tempQualityHouse = qualityHouses[0];
					//								listCompanyForRequired = manyCompany(cityName, provinceName,
					//									qualityAssets, tempQualityHouse, qualityLand);//再次筛选
					//							}
					//						}
					//					}
				}
			}
			try {
				if (listCompanyForRequired != null && listCompanyForRequired.size() > 0) {
					saveExtract(id, listCompanyForRequired);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存评估公司抽取出错", e);
				return listCompanyForRequired;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("评估公司抽取出错", e);
		}
		return listCompanyForRequired;
	}
	
	private void removeSame(List<AssetsAssessCompanyInfo> listInfo) {
		for (int i = 0; i < listInfo.size() - 1; i++) {
			for (int j = listInfo.size() - 1; j > i; j--) {
				long jid = listInfo.get(j).getId();
				long iid = listInfo.get(i).getId();
				if (jid == iid) {
					listInfo.remove(j);
				}
			}
			
		}
	}
	
	//对结果再一次筛选
	private List<AssetsAssessCompanyInfo> accuracyEstimation(	String qualityAssets,
																List<String> qualityHouse,
																List<String> qualityLand,
																List<AssetsAssessCompanyInfo> listInfo) {
		List<AssetsAssessCompanyInfo> listCompanyForRequired = Lists.newArrayList();
		if (ListUtil.isNotEmpty(qualityLand)) {//土地
			for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listInfo) {
				boolean temp = false;
				if (assetsAssessCompanyInfo.getQualityLand() != null) {//说明已经找到符合土地的	
					String land = assetsAssessCompanyInfo.getQualityLand();
					for (String str : qualityLand) {
						if (land.equals(str)) {
							listCompanyForRequired.add(assetsAssessCompanyInfo);
							temp = true;
						}
					}
				}
				if (temp) {
					break;
				}
			}
		}
		if (ListUtil.isNotEmpty(qualityHouse)) {//房产
			for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listInfo) {
				boolean temp = false;
				if (assetsAssessCompanyInfo.getQualityHouse() != null) {
					String house = assetsAssessCompanyInfo.getQualityHouse();
					for (String str : qualityHouse) {
						if (house.equals(str)) {
							listCompanyForRequired.add(assetsAssessCompanyInfo);
							temp = true;
						}
					}
				}
				if (temp) {
					break;
				}
			}
		}
		if (StringUtil.isNotEmpty(qualityAssets)) {//资产
			String[] forAssets = qualityAssets.split(",");//需要匹配的资质
			for (String str : forAssets) {
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listInfo) {
					if (assetsAssessCompanyInfo.getQualityAssets() != null) {
						String[] assets = assetsAssessCompanyInfo.getQualityAssets().split(",");//当前评估公司的资质
						boolean temp = false;
						
						if (ArrayUtils.contains(assets, str)) {
							listCompanyForRequired.add(assetsAssessCompanyInfo);
							temp = true;
						}
						
						if (temp) {
							break;
						}
					}
				}
			}
		}
		removeSame(listCompanyForRequired);
		
		return listCompanyForRequired;
		
	}
	
	//判断两个公司资质是不是覆盖
	private void removeQualitySame(List<AssetsAssessCompanyInfo> listInfo) {
		for (int i = 0; i < listInfo.size() - 1; i++) {
			for (int j = listInfo.size() - 1; j > i; j--) {
				long jid = listInfo.get(j).getId();
				long iid = listInfo.get(i).getId();
				AssetsAssessCompanyInfo jInfo = assetsAssessCompanyService.findById(jid);
				AssetsAssessCompanyInfo iInfo = assetsAssessCompanyService.findById(iid);
				String jLand = jInfo.getQualityLand();
				String iLand = iInfo.getQualityLand();
				//资质-土地
				boolean qualityLand = false;//true 表示覆盖，false表示未覆盖
				if ((iLand == null && jLand == null)
					|| (iLand != null && jLand == null)
					|| (iLand != null && jLand != null && ("全国".equals(iLand) || iLand
						.equals(jLand)))) {
					qualityLand = true;
				}
				String jHouse = jInfo.getQualityHouse();
				String iHouse = iInfo.getQualityHouse();
				//资质-房产
				boolean qualityHouse = false;//true 表示覆盖，false表示未覆盖
				if ((iHouse == null && jHouse == null)
					|| (iHouse != null && jHouse == null)
					|| (iHouse != null && jHouse != null && ("一级".equals(iHouse)
																|| iHouse.equals(jHouse) || (("一级"
						.equals(iHouse) && ("二级".equals(jHouse) || "三级".equals(jHouse))) || (("二级"
						.equals(iHouse) && "三级".equals(jHouse))))))) {
					qualityHouse = true;
				}
				
				String jAssets = jInfo.getQualityAssets();
				String iAssets = iInfo.getQualityAssets();
				//资质-资产
				boolean qualityAssets = false;//true 表示覆盖，false表示未覆盖
				if ((iAssets == null && jAssets == null) || (iAssets != null && jAssets == null)
					|| (iAssets != null && jAssets != null && iAssets.contains(jAssets))) {
					qualityAssets = true;
				}
				if (qualityLand && qualityHouse && qualityAssets) {
					listInfo.remove(j);
				}
			}
			
		}
	}
	
	//	public AssetsAssessCompanyInfo mapSort(Map<Long, Integer> map) {
	//		//这里将map.entrySet()转换成list
	//		AssetsAssessCompanyInfo assetsAssessCompanyInfo = null;
	//		List<Map.Entry<Long, Integer>> list = new ArrayList<Map.Entry<Long, Integer>>(
	//			map.entrySet());
	//		
	//		//然后通过比较器来实现排序
	//		
	//		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
	//			
	//			//升序排序
	//			@Override
	//			public int compare(Entry<Long, Integer> o1, Entry<Long, Integer> o2) {
	//				return o1.getValue().compareTo(o2.getValue());
	//			}
	//			
	//		});
	//		Collections.shuffle(list);
	//		for (Map.Entry<Long, Integer> mapping : list) {
	//			
	//			assetsAssessCompanyInfo = assetsAssessCompanyService.findById(mapping.getKey());
	//			break;
	//		}
	//		return assetsAssessCompanyInfo;
	//	}
	
	private AssetsAssessCompanyInfo extractManyCompany(String qualityAssets, String assets,
														String provinceName) {
		AssetsAssessCompanyInfo assetsAssessCompanyInfoForRequird = null;
		//用来存储 客户所属区域和该评估公司业务区域吻合 的所有评估公司
		List<AssetsAssessCompanyInfo> listManyCompanyForRequired1 = new ArrayList<AssetsAssessCompanyInfo>();
		if (qualityAssets != null) {
			if (qualityAssets != null && qualityAssets.contains(assets)) {
				AssetsAssessCompanyQueryOrder order1 = new AssetsAssessCompanyQueryOrder();
				order1.setQualityAssets(assets);
				order1.setStatus(AssessCompanyStatusEnum.NORMAL.code());
				order1.setPageSize(99999);
				QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult1 = assetsAssessCompanyService
					.query(order1);
				List<AssetsAssessCompanyInfo> listCompanyInfo1 = batchResult1.getPageList();
				Collections.shuffle(listCompanyInfo1);//区域符合要求的评估公司，随机筛选
				
				if (listCompanyInfo1 != null && listCompanyInfo1.size() > 0) {
					for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyInfo1) {
						//客户所属区域和该评估公司业务区域吻合
						List<AssessCompanyBusinessScopeInfo> listScopeInfo = assetsAssessCompanyInfo
							.getScopeInfos();
						Collections.shuffle(listScopeInfo);//区域符合要求的评估公司，随机筛选
						if (listScopeInfo != null) {
							for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo) {
								if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
									|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
									listManyCompanyForRequired1.add(assetsAssessCompanyInfo);
								}
							}
						}
						//用来存储 客户所属区域和该评估公司业务区域吻合 并且客户所在区域和评估公司所在区域相同的评估公司
						List<AssetsAssessCompanyInfo> listManyCompanyForRequired2 = new ArrayList<AssetsAssessCompanyInfo>();
						if (listManyCompanyForRequired1 != null
							&& listManyCompanyForRequired1.size() > 0) {
							Collections.shuffle(listManyCompanyForRequired1);//区域符合要求的评估公司，随机筛选
							for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
								if (provinceName.equals(companyInfo1.getCity())
									|| provinceName.equals(companyInfo1.getProvinceName())) {
									listManyCompanyForRequired2.add(companyInfo1);
								}
							}
						}
						//无法找到  客户所在区域和评估公司所在区域相同的评估公司  只有随机取一个客户所属区域和该评估公司业务区域吻合的评估公司
						if (listManyCompanyForRequired2.size() == 0) {
							Collections.shuffle(listManyCompanyForRequired1);//区域符合要求的评估公司，随机筛选
							for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
								assetsAssessCompanyInfoForRequird = companyInfo1;
								break;
							}
						} else {
							Collections.shuffle(listManyCompanyForRequired2);
							//							Map<Long, Integer> map = new TreeMap<Long, Integer>();
							for (AssetsAssessCompanyInfo assetsAssessCompanyInfo1 : listManyCompanyForRequired2) {
								//客户所属区域和该评估公司业务区域吻合
								List<AssessCompanyBusinessScopeInfo> listScopeInfo1 = assetsAssessCompanyInfo1
									.getScopeInfos();
								Collections.shuffle(listScopeInfo1);//区域符合要求的评估公司，随机筛选
								if (listScopeInfo1 != null) {
									for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo1) {
										if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
											|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
											//											map.put(assetsAssessCompanyInfo1.getId(),
											//												scopeInfo.getSort());
											assetsAssessCompanyInfoForRequird = assetsAssessCompanyInfo1;
											break;
										}
									}
								}
							}
							//							if (map.size() > 0) {
							//								assetsAssessCompanyInfoForRequird = mapSort(map);
							//							}
							
						}
					}
				}
			}
		}
		return assetsAssessCompanyInfoForRequird;
	}
	
	//系统自动抽取子方法
	private AssetsAssessCompanyInfo extract(String provinceName,
											List<AssetsAssessCompanyInfo> listCompanyInfo) {
		AssetsAssessCompanyInfo assetsAssessCompanyInfoForRequired = null;
		Collections.shuffle(listCompanyInfo);//区域符合要求的评估公司，随机筛选
		
		//用来存储 客户所属区域和该评估公司业务区域吻合 的所有评估公司
		List<AssetsAssessCompanyInfo> listCompanyForRequired1 = new ArrayList<AssetsAssessCompanyInfo>();
		
		if (listCompanyInfo != null && listCompanyInfo.size() > 0) {
			for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyInfo) {
				assetsAssessCompanyInfo = assetsAssessCompanyService
					.findById(assetsAssessCompanyInfo.getId());
				//客户所属区域和该评估公司业务区域吻合
				List<AssessCompanyBusinessScopeInfo> listScopeInfo = assetsAssessCompanyInfo
					.getScopeInfos();
				if (listScopeInfo != null) {
					for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo) {
						if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
							|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
							listCompanyForRequired1.add(assetsAssessCompanyInfo);
						}
					}
				}
			}
		}
		//用来存储 客户所属区域和该评估公司业务区域吻合 并且客户所在区域和评估公司所在区域相同的评估公司
		List<AssetsAssessCompanyInfo> listCompanyForRequired2 = new ArrayList<AssetsAssessCompanyInfo>();
		Collections.shuffle(listCompanyForRequired1);
		//如果存在客户区域和业务区域吻合的 评估公司   再筛选是否有  客户所在区域和评估公司所在区域相同的评估公司
		if (listCompanyForRequired1 != null) {
			for (AssetsAssessCompanyInfo companyInfo1 : listCompanyForRequired1) {
				if (provinceName.equals(companyInfo1.getCity())
					|| provinceName.equals(companyInfo1.getProvinceName())) {
					listCompanyForRequired2.add(companyInfo1);
				}
			}
			//无法找到  客户所在区域和评估公司所在区域相同的评估公司  只有随机取一个客户所属区域和该评估公司业务区域吻合的评估公司
			if (listCompanyForRequired2.size() == 0) {
				Collections.shuffle(listCompanyForRequired1);//区域符合要求的评估公司，随机筛选
				for (AssetsAssessCompanyInfo companyInfo1 : listCompanyForRequired1) {
					assetsAssessCompanyInfoForRequired = companyInfo1;
					break;
				}
			} else {
				//				Map<Long, Integer> map = new TreeMap<Long, Integer>();
				
				//				for (AssetsAssessCompanyInfo companyInfo2 : listCompanyForRequired2) {
				//					assetsAssessCompanyInfoForRequired = companyInfo2;
				//					break;
				//				}
				Collections.shuffle(listCompanyForRequired2);
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyForRequired2) {
					//客户所属区域和该评估公司业务区域吻合
					List<AssessCompanyBusinessScopeInfo> listScopeInfo = assetsAssessCompanyInfo
						.getScopeInfos();
					Collections.shuffle(listScopeInfo);//区域符合要求的评估公司，随机筛选
					if (listScopeInfo != null) {
						for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo) {
							if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
								|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
								//								map.put(assetsAssessCompanyInfo.getId(), scopeInfo.getSort());
								assetsAssessCompanyInfoForRequired = assetsAssessCompanyInfo;
								break;
							}
						}
					}
				}
				//				if (map.size() > 0) {
				//					assetsAssessCompanyInfoForRequired = mapSort(map);
				//				}
				
			}
		}
		return assetsAssessCompanyInfoForRequired;
	}
	
	//按条件筛选  梯次筛选
	private List<AssetsAssessCompanyInfo> conditionScreen(String cityName, String provinceName,
															List<String> qualityAssets,
															List<String> qualityHouse,
															List<String> qualityLand) {
		List<AssetsAssessCompanyInfo> listCompanyForRequired = Lists.newArrayList();
		//资质匹配
		AssetsAssessCompanyQueryOrder order = new AssetsAssessCompanyQueryOrder();
		if (ListUtil.isNotEmpty(qualityLand)) {
			//			if ("全省".equals(qualityLand)) {
			//				qualityLand = "全";
			//			}
			order.setQualityLandList(qualityLand);
		}
		if (ListUtil.isNotEmpty(qualityHouse)) {
			order.setQualityHouseList(qualityHouse);
		}
		
		if (ListUtil.isNotEmpty(qualityAssets)) {
			
			order.setQualityAssetsList(qualityAssets);
		}
		order.setStatus(AssessCompanyStatusEnum.NORMAL.code());
		order.setPageSize(99999);
		QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult = assetsAssessCompanyService
			.query(order);
		List<AssetsAssessCompanyInfo> listCompanyInfo = batchResult.getPageList();
		Collections.shuffle(listCompanyInfo);//区域符合要求的评估公司，随机筛选
		if (extract(provinceName, listCompanyInfo) != null) {
			listCompanyForRequired.add(extract(provinceName, listCompanyInfo));
		}
		//		if (listCompanyForRequired.size() == 0) {
		//			if (StringUtil.isNotEmpty(qualityLand) && "全省".equals(qualityLand)
		//				&& listCompanyForRequired.size() == 0) {//全省筛选不出来
		//				qualityLand = "全";
		//				order.setQualityLand(qualityLand);
		//			}
		//		}
		//		if (listCompanyForRequired.size() == 0) {//不能找到一家公司能满足所需条件,请求多家公司合力
		//		
		//		}
		return listCompanyForRequired;
	}
	
	private List<AssetsAssessCompanyInfo> manyCompany(String cityName, String provinceName,
														String qualityAssets,
														List<String> qualityHouse,
														List<String> qualityLand) {
		List<AssetsAssessCompanyInfo> listCompanyForRequired = Lists.newArrayList();
		//记录多家公司 没家公司都满足至少一个条件
		List<AssetsAssessCompanyInfo> listManyCompany = new ArrayList<AssetsAssessCompanyInfo>();
		Boolean isLand = true;
		Boolean isHouse = true;
		Boolean isAssets1 = true;
		Boolean isAssets2 = true;
		Boolean isAssets3 = true;
		Boolean isAssets4 = true;
		//用来存储 客户所属区域和该评估公司业务区域吻合 的所有评估公司
		List<AssetsAssessCompanyInfo> listManyCompanyForRequired1 = new ArrayList<AssetsAssessCompanyInfo>();
		
		if (ListUtil.isNotEmpty(qualityLand)) {//先找资质-土地
			AssetsAssessCompanyQueryOrder order1 = new AssetsAssessCompanyQueryOrder();
			order1.setQualityLandList(qualityLand);
			order1.setStatus(AssessCompanyStatusEnum.NORMAL.code());
			order1.setPageSize(99999);
			QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult1 = assetsAssessCompanyService
				.query(order1);
			List<AssetsAssessCompanyInfo> listCompanyInfo1 = batchResult1.getPageList();
			Collections.shuffle(listCompanyInfo1);//区域符合要求的评估公司，随机筛选
			
			if (listCompanyInfo1 != null && listCompanyInfo1.size() > 0) {
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyInfo1) {
					//客户所属区域和该评估公司业务区域吻合
					List<AssessCompanyBusinessScopeInfo> listScopeInfo = assetsAssessCompanyInfo
						.getScopeInfos();
					if (listScopeInfo != null) {
						for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo) {
							if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
								|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
								listManyCompanyForRequired1.add(assetsAssessCompanyInfo);
							}
						}
					}
				}
			} else {
				isLand = false;
			}
			if (listManyCompanyForRequired1.size() == 0) {
				isLand = false;
			}
			//用来存储 客户所属区域和该评估公司业务区域吻合 并且客户所在区域和评估公司所在区域相同的评估公司
			List<AssetsAssessCompanyInfo> listManyCompanyForRequired2 = new ArrayList<AssetsAssessCompanyInfo>();
			if (listManyCompanyForRequired1 != null && listManyCompanyForRequired1.size() > 0) {
				for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
					if (provinceName.equals(companyInfo1.getCity())
						|| provinceName.equals(companyInfo1.getProvinceName())) {
						listManyCompanyForRequired2.add(companyInfo1);
					}
				}
			}
			//无法找到  客户所在区域和评估公司所在区域相同的评估公司  只有随机取一个客户所属区域和该评估公司业务区域吻合的评估公司
			if (listManyCompanyForRequired2.size() == 0) {
				Collections.shuffle(listManyCompanyForRequired1);//区域符合要求的评估公司，随机筛选
				for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
					listManyCompany.add(companyInfo1);
					break;
				}
			} else {
				//						Map<Long, Integer> map = new TreeMap<Long, Integer>();
				Collections.shuffle(listManyCompanyForRequired2);
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo1 : listManyCompanyForRequired2) {
					//客户所属区域和该评估公司业务区域吻合
					List<AssessCompanyBusinessScopeInfo> listScopeInfo1 = assetsAssessCompanyInfo1
						.getScopeInfos();
					Collections.shuffle(listScopeInfo1);//区域符合要求的评估公司，随机筛选
					if (listScopeInfo1 != null) {
						for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo1) {
							if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
								|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
								//										map.put(assetsAssessCompanyInfo1.getId(),
								//											scopeInfo.getSort());
								listManyCompany.add(assetsAssessCompanyInfo1);
								break;
							}
						}
					}
				}
				//						if (map.size() > 0) {
				//							listManyCompany.add(mapSort(map));
				//						}
				
			}
			
		}
		if (ListUtil.isNotEmpty(qualityHouse)) {//先找资质-房产
			listManyCompanyForRequired1 = Lists.newArrayList();
			AssetsAssessCompanyQueryOrder order1 = new AssetsAssessCompanyQueryOrder();
			
			order1.setQualityHouseList(qualityHouse);
			order1.setStatus(AssessCompanyStatusEnum.NORMAL.code());
			order1.setPageSize(99999);
			QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult1 = assetsAssessCompanyService
				.query(order1);
			List<AssetsAssessCompanyInfo> listCompanyInfo1 = batchResult1.getPageList();
			Collections.shuffle(listCompanyInfo1);//区域符合要求的评估公司，随机筛选
			
			if (listCompanyInfo1 != null && listCompanyInfo1.size() > 0) {
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyInfo1) {
					//客户所属区域和该评估公司业务区域吻合
					List<AssessCompanyBusinessScopeInfo> listScopeInfo = assetsAssessCompanyInfo
						.getScopeInfos();
					if (listScopeInfo != null) {
						for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo) {
							if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
								|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
								listManyCompanyForRequired1.add(assetsAssessCompanyInfo);
							}
						}
					}
				}
			} else {
				isHouse = false;
			}
			if (listManyCompanyForRequired1.size() == 0) {
				isHouse = false;
			}
			//用来存储 客户所属区域和该评估公司业务区域吻合 并且客户所在区域和评估公司所在区域相同的评估公司
			List<AssetsAssessCompanyInfo> listManyCompanyForRequired2 = new ArrayList<AssetsAssessCompanyInfo>();
			if (listManyCompanyForRequired1 != null && listManyCompanyForRequired1.size() > 0) {
				for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
					if (provinceName.equals(companyInfo1.getCity())
						|| provinceName.equals(companyInfo1.getProvinceName())) {
						listManyCompanyForRequired2.add(companyInfo1);
					}
				}
			}
			//无法找到  客户所在区域和评估公司所在区域相同的评估公司  只有随机取一个客户所属区域和该评估公司业务区域吻合的评估公司
			if (listManyCompanyForRequired2.size() == 0) {
				Collections.shuffle(listManyCompanyForRequired1);//区域符合要求的评估公司，随机筛选
				for (AssetsAssessCompanyInfo companyInfo1 : listManyCompanyForRequired1) {
					listManyCompany.add(companyInfo1);
					break;
				}
			} else {
				//						Map<Long, Integer> map = new TreeMap<Long, Integer>();
				Collections.shuffle(listManyCompanyForRequired2);
				for (AssetsAssessCompanyInfo assetsAssessCompanyInfo1 : listManyCompanyForRequired2) {
					//客户所属区域和该评估公司业务区域吻合
					List<AssessCompanyBusinessScopeInfo> listScopeInfo1 = assetsAssessCompanyInfo1
						.getScopeInfos();
					Collections.shuffle(listScopeInfo1);//区域符合要求的评估公司，随机筛选
					if (listScopeInfo1 != null) {
						for (AssessCompanyBusinessScopeInfo scopeInfo : listScopeInfo1) {
							if (provinceName.equals(scopeInfo.getBusinessScopeRegion())
								|| "全国".equals(scopeInfo.getBusinessScopeRegion())) {
								//										map.put(assetsAssessCompanyInfo1.getId(),
								//											scopeInfo.getSort());
								listManyCompany.add(assetsAssessCompanyInfo1);
								break;
							}
						}
					}
				}
				//						if (map.size() > 0) {
				//							listManyCompany.add(mapSort(map));
				//						}
			}
		}
		AssetsAssessCompanyInfo info1 = null;
		AssetsAssessCompanyInfo info2 = null;
		AssetsAssessCompanyInfo info3 = null;
		AssetsAssessCompanyInfo info4 = null;
		if (qualityAssets != null && qualityAssets.contains("林权")) {
			//			List<String> tempList = Lists.newArrayList();
			//			tempList.add("林权");
			info1 = extractManyCompany(qualityAssets, "林权", provinceName);
			if (info1 == null) {
				isAssets1 = false;
			} else {
				listManyCompany.add(info1);
			}
		}
		if (qualityAssets != null && qualityAssets.contains("车辆")) {
			
			info2 = extractManyCompany(qualityAssets, "车辆", provinceName);
			if (info2 == null) {
				isAssets2 = false;
			} else {
				listManyCompany.add(info2);
			}
		}
		if (qualityAssets != null && qualityAssets.contains("船舶")) {
			
			info3 = extractManyCompany(qualityAssets, "船舶", provinceName);
			if (info3 == null) {
				isAssets3 = false;
			} else {
				listManyCompany.add(info3);
			}
		}
		if (qualityAssets != null && qualityAssets.contains("设备")) {
			//			List<String> tempList = Lists.newArrayList();
			//			tempList.add("设备");
			info4 = extractManyCompany(qualityAssets, "设备", provinceName);
			if (info4 == null) {
				isAssets4 = false;
			} else {
				listManyCompany.add(info4);
			}
		}
		
		if (isLand && isHouse && isAssets1 && isAssets2 && isAssets3 && isAssets4) {
			removeSame(listManyCompany);
			List<AssetsAssessCompanyInfo> forRequired = accuracyEstimation(qualityAssets,
				qualityHouse, qualityLand, listManyCompany);
			if (ListUtil.isNotEmpty(forRequired)) {
				Collections.shuffle(forRequired);
				List<AssetsAssessCompanyInfo> forRequired1 = accuracyEstimation(qualityAssets,
					qualityHouse, qualityLand, forRequired);
				if (ListUtil.isNotEmpty(forRequired1)) {
					Collections.shuffle(forRequired1);
					List<AssetsAssessCompanyInfo> forRequired2 = accuracyEstimation(qualityAssets,
						qualityHouse, qualityLand, forRequired1);
					if (ListUtil.isNotEmpty(forRequired2)) {
						for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : forRequired2) {
							listCompanyForRequired.add(assetsAssessCompanyInfo);
						}
					}
				}
			}
		}
		return listCompanyForRequired;
	}
	
	private void resetCityName(FAssessCompanyApplyInfo assessCompanyApplyInfo) {
		String cityCode = assessCompanyApplyInfo.getCityCode();
		String cityName = assessCompanyApplyInfo.getCityName();
		if ("市辖区".equals(assessCompanyApplyInfo.getProvinceName())
			|| "县".equals(assessCompanyApplyInfo.getProvinceName())
			|| "请选择".equals(assessCompanyApplyInfo.getProvinceName())) {
			assessCompanyApplyInfo.setCityCode(assessCompanyApplyInfo.getProvinceCode());
			assessCompanyApplyInfo.setCityName(assessCompanyApplyInfo.getProvinceName());
			assessCompanyApplyInfo.setProvinceCode(cityCode);
			assessCompanyApplyInfo.setProvinceName(cityName);
		}
	}
	
	private FcsBaseResult saveExtract(final long id,
										final List<AssetsAssessCompanyInfo> listCompanyForRequired) {
		
		logger.info("进入保存抽取评估公司临时存储信息");
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = new FcsBaseResult();
				try {
					String ids = "";
					String companyNames = "";
					for (AssetsAssessCompanyInfo assetsAssessCompanyInfo : listCompanyForRequired) {
						ids += assetsAssessCompanyInfo.getId() + ",";
						companyNames += assetsAssessCompanyInfo.getCompanyName() + ",";
					}
					FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findById(id);
					if (StringUtil.isEmpty(DO.getCompanyName())) {//如果为空，说明是指定审核
						DO.setCompanyName(companyNames.substring(0, companyNames.length() - 1));
						
						DO.setCompanyId(ids.substring(0, ids.length() - 1));
						DO.setAppointWay(AppointWayEnum.EXTRACT.code());
						FAssessCompanyApplyDAO.update(DO);
						FAssessCompanyApplyItemDAO.deleteByApplyId(DO.getId());//删除评估公司临时存储
						String[] strCompanyIds = ids.split(",");
						String[] strcompanyNames = companyNames.split(",");
						for (int i = 0; i < strCompanyIds.length; i++) {
							FAssessCompanyApplyItemDO itemDO = FAssessCompanyApplyItemDAO
								.findByApplyIdAndCompanyId(DO.getId(),
									NumberUtil.parseLong(strCompanyIds[i]));
							if (itemDO == null) {
								itemDO = new FAssessCompanyApplyItemDO();
								AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO
									.findById(Long.parseLong(strCompanyIds[i]));
								itemDO.setApplyId(DO.getId());
								itemDO.setCompanyId(Long.parseLong(strCompanyIds[i]));
								itemDO.setCompanyName(strcompanyNames[i]);
								itemDO.setQualityAssets(companyDO.getQualityAssets());
								itemDO.setQualityHouse(companyDO.getQualityHouse());
								itemDO.setQualityLand(companyDO.getQualityLand());
								itemDO.setCityCode(companyDO.getCityCode());
								itemDO.setCity(companyDO.getCity());
								itemDO.setCountyCode(companyDO.getCountyCode());
								itemDO.setCountryName(companyDO.getCountyName());
								itemDO.setProvinceCode(companyDO.getProvinceCode());
								itemDO.setProvinceName(companyDO.getProvinceName());
								itemDO.setCountryCode(companyDO.getCountryCode());
								itemDO.setCountryName(companyDO.getCountryName());
								itemDO.setContactAddr(companyDO.getContactAddr());
								itemDO.setRegisteredCapital(companyDO.getRegisteredCapital());
								itemDO.setStatus(companyDO.getStatus());
								itemDO.setAttach(companyDO.getAttach());
								itemDO.setRemark(companyDO.getRemark());
								itemDO.setWorkSituation(companyDO.getWorkSituation());
								itemDO.setAttachment(companyDO.getAttachment());
								itemDO.setTechnicalLevel(companyDO.getTechnicalLevel());
								itemDO.setEvaluationEfficiency(companyDO.getEvaluationEfficiency());
								itemDO.setCooperationSituation(companyDO.getCooperationSituation());
								itemDO.setServiceAttitude(companyDO.getServiceAttitude());
								List<AssessCompanyContactDO> listContactDO = assessCompanyContactDAO
									.findByAssessCompanyId(companyDO.getId());
								String contactName = "";
								String contactNumber = "";
								if (listContactDO != null) {
									for (AssessCompanyContactDO assessCompanyContactDO : listContactDO) {
										contactName += assessCompanyContactDO.getContactName()
														+ ",";
										contactNumber += assessCompanyContactDO.getContactNumber()
															+ ",";
									}
									contactName = contactName.substring(0, contactName.length() - 1);
									contactNumber = contactNumber.substring(0,
										contactNumber.length() - 1);
								}
								itemDO.setContactName(contactName);
								itemDO.setContactNumber(contactNumber);
								List<AssessCompanyBusinessScopeDO> listScopeDO = assessCompanyBusinessScopeDAO
									.findByAssessCompanyId(companyDO.getId());
								String businessScopeRegion = "";
								String code = "";
								if (listScopeDO != null) {
									for (AssessCompanyBusinessScopeDO assessCompanyBusinessScopeDO : listScopeDO) {
										businessScopeRegion += assessCompanyBusinessScopeDO
											.getBusinessScopeRegion() + ",";
										code += assessCompanyBusinessScopeDO.getCode() + ",";
									}
									businessScopeRegion = businessScopeRegion.substring(0,
										businessScopeRegion.length() - 1);
									code = code.substring(0, code.length() - 1);
								}
								itemDO.setBusinessScopeRegion(businessScopeRegion);
								itemDO.setCode(code);
								FAssessCompanyApplyItemDAO.insert(itemDO);
							}
						}
					}
					result.setSuccess(true);
					result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				} catch (DataAccessException e) {
					logger.error("评估公司保存抽取结果出错{}", e);
					result.setSuccess(false);
					result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
					result.setMessage(FcsResultEnum.DATABASE_EXCEPTION.getMessage());
				} catch (Exception e) {
					logger.error("评估公司保存抽取结果出错{}", e);
					result.setSuccess(false);
				}
				return result;
			}
		});
	}
}
