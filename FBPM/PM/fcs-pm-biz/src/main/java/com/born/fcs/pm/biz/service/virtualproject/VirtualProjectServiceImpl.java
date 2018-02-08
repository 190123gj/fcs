package com.born.fcs.pm.biz.service.virtualproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectVirtualDO;
import com.born.fcs.pm.dal.dataobject.ProjectVirtualDetailDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.VirtualCustomerEnum;
import com.born.fcs.pm.ws.enums.VirtualProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectDetailInfo;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectDeleteOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectDetailOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.virtualproject.VirtualProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("virtualProjectService")
public class VirtualProjectServiceImpl extends BaseAutowiredDomainService implements
																			VirtualProjectService {
	
	@Autowired
	ProjectService projectService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public FcsBaseResult save(final VirtualProjectOrder order) {
		return commonProcess(order, "保存虚拟项目", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ProjectVirtualDO virtual = null;
				
				if (order.getVirtualId() > 0) {
					virtual = projectVirtualDAO.findById(order.getVirtualId());
					if (virtual == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据不存在");
					}
				} else {
					virtual = new ProjectVirtualDO();
					virtual.setStatus(VirtualProjectStatusEnum.DRAFT.code());
					virtual.setRawAddTime(now);
				}
				
				BeanCopier.staticCopy(order, virtual);
				if (StringUtil.isBlank(order.getBusiType())) {//默认其他
					virtual.setBusiType("711");
					virtual.setBusiTypeName("其他");
				}
				
				virtual.setApplyUserId(order.getUserId());
				virtual.setApplyUserAccount(order.getUserAccount());
				virtual.setApplyUserName(order.getUserName());
				
				VirtualCustomerEnum virtualCustomer = VirtualCustomerEnum.getByCustomerId(virtual
					.getCustomerId());
				if (virtualCustomer != null)
					virtual.setCustomerName(virtualCustomer.getCustoemrName());
				
				if (virtual.getVirtualId() > 0) {
					projectVirtualDAO.update(virtual);
				} else {
					virtual.setVirtualId(projectVirtualDAO.insert(virtual));
				}
				
				List<ProjectVirtualDetailDO> oldList = projectVirtualDetailDAO
					.findByVirtualId(virtual.getVirtualId());
				Map<Long, ProjectVirtualDetailDO> oldMap = Maps.newHashMap();
				if (ListUtil.isNotEmpty(oldList)) {
					for (ProjectVirtualDetailDO detail : oldList) {
						oldMap.put(detail.getDetailId(), detail);
					}
				}
				
				Money amount = Money.zero();
				if (ListUtil.isNotEmpty(order.getDetailOrder())) {
					for (VirtualProjectDetailOrder detailOrder : order.getDetailOrder()) {
						ProjectVirtualDetailDO detailDO = projectVirtualDetailDAO
							.findById(detailOrder.getDetailId());
						if (detailDO == null) {
							detailDO = new ProjectVirtualDetailDO();
							detailDO.setRawAddTime(now);
						} else {
							oldMap.remove(detailDO.getDetailId());
						}
						detailDO.setProjectCode(detailOrder.getProjectCode());
						//查询对应的项目
						ProjectInfo project = projectService.queryByCode(detailDO.getProjectCode(),
							false);
						if (project == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目不存在[" + detailDO.getProjectCode() + "]");
						}
						BeanCopier.staticCopy(project, detailDO);
						detailDO.setBalance(project.getBalance());
						detailDO.setVirtualId(virtual.getVirtualId());
						detailDO.setVirtualProjectCode(virtual.getProjectCode());
						if (detailDO.getDetailId() > 0) {
							projectVirtualDetailDAO.update(detailDO);
						} else {
							projectVirtualDetailDAO.insert(detailDO);
						}
						amount.addTo(detailDO.getAmount());
					}
					for (long detailId : oldMap.keySet()) {
						projectVirtualDetailDAO.deleteById(detailId);
					}
				} else {
					projectVirtualDetailDAO.deleteByVirtualId(virtual.getVirtualId());
				}
				
				virtual.setAmount(amount);
				projectVirtualDAO.update(virtual);
				
				FcsPmDomainHolder.get().addAttribute("virtual", virtual);
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				if (order.getIsSubmit() == BooleanEnum.IS) { //提交生成项目
					ProjectVirtualDO virtual = (ProjectVirtualDO) FcsPmDomainHolder.get()
						.getAttribute("virtual");
					FcsBaseResult submitResult = subtmitVirtualProject(virtual.getVirtualId());
					if (!submitResult.isSuccess()) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, submitResult.getMessage());
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * 生成项目数据
	 * @param virtualId
	 * @return
	 */
	private FcsBaseResult subtmitVirtualProject(final long virtualId) {
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					
					Date now = getSysdate();
					
					VirtualProjectInfo virtual = findById(virtualId);
					if (virtual == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据未找到");
					}
					
					if (StringUtil.isNotBlank(virtual.getProjectCode())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "已生成项目");
					}
					
					ProjectDO project = new ProjectDO();
					BeanCopier.staticCopy(virtual, project);
					//生成项目编号（年  CQEX  转- 0001，如，2007-CQEX转-0001）
					int year = Calendar.getInstance().get(Calendar.YEAR);
					String projectCode = year + "-CQEX转-";
					String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + "_CQEX";
					long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
					projectCode += StringUtil.leftPad(String.valueOf(seq), 3, "0");
					
					project.setProjectCode(projectCode);
					
					if (virtual.getAmount().greaterThan(ZERO_MONEY)) {
						project.setAmount(virtual.getAmount());
					} else {
						project.setAmount(virtual.sumDetailAmount());
					}
					
					//不晓得期限，顺便设置个
					project.setTimeLimit(100);
					project.setTimeUnit(TimeUnitEnum.YEAR.code());
					
					//标记一下、后续流程不做限制
					project.setIsRedoProject("IS");
					project.setRedoFrom(virtual.detailProjectCodes());
					
					//客户经理A角、B角全是申请人
					project.setBusiManagerId(virtual.getApplyUserId());
					project.setBusiManagerAccount(virtual.getApplyUserAccount());
					project.setBusiManagerName(virtual.getApplyUserName());
					project.setBusiManagerbId(virtual.getApplyUserId());
					project.setBusiManagerbAccount(virtual.getApplyUserAccount());
					project.setBusiManagerbName(virtual.getApplyUserName());
					
					//查询部门信息
					Org dept = bpmUserQueryService.findDeptById(virtual.getApplyDeptId());
					if (dept != null) {
						project.setDeptId(dept.getId());
						project.setDeptCode(dept.getCode());
						project.setDeptName(dept.getName());
						project.setDeptPath(dept.getPath());
						project.setDeptPathName(dept.getPathName());
					}
					project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
					project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
					project.setStatus(ProjectStatusEnum.NORMAL.code());
					project.setApprovalTime(now);
					project.setIsApproval(BooleanEnum.IS.code());
					project.setIsApprovalDel(BooleanEnum.NO.code());
					project.setIsMaximumAmount(BooleanEnum.NO.code());
					project.setBelongToNc("NO");
					project.setProjectId(0);
					//新增项目
					projectDAO.insert(project);
					projectVirtualDAO.updateProjectCodeAndStatus(projectCode,
						VirtualProjectStatusEnum.SUBMIT.code(), virtualId);
					projectVirtualDetailDAO.updateVirtualProjectCode(projectCode, virtualId);
					
					//新增项目 客户经理、客户经理B角、风险经理
					UserDetailInfo user = bpmUserQueryService.findUserDetailByUserId(virtual
						.getApplyUserId());
					//客户经理
					setRelatedUser(projectCode, user, dept, ProjectRelatedUserTypeEnum.BUSI_MANAGER);
					//客户经理B
					setRelatedUser(projectCode, user, dept,
						ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
					//风险经理
					setRelatedUser(projectCode, user, dept, ProjectRelatedUserTypeEnum.RISK_MANAGER);
					
				} catch (FcsPmBizException e) {
					result.setSuccess(false);
					result.setMessage("生成虚拟项目出错[" + e.getErrorMsg() + "]");
					logger.error("生成虚拟项目出错[{}],{}", e.getErrorMsg(), e);
					if (status != null)
						status.setRollbackOnly();
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("生成虚拟项目出错");
					logger.error("生成虚拟项目出错 {}", e);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	/**
	 * 设置项目相关人员
	 * @param user
	 * @param dept
	 * @param userType
	 */
	private void setRelatedUser(String projectCode, UserDetailInfo user, Org dept,
								ProjectRelatedUserTypeEnum userType) {
		if (user == null)
			return;
		ProjectRelatedUserOrder relatedUserOrder = new ProjectRelatedUserOrder();
		relatedUserOrder.setProjectCode(projectCode);
		relatedUserOrder.setUserType(userType);
		relatedUserOrder.setUserId(user.getId());
		relatedUserOrder.setUserAccount(user.getAccount());
		relatedUserOrder.setUserName(user.getName());
		relatedUserOrder.setUserEmail(user.getEmail());
		relatedUserOrder.setUserMobile(user.getMobile());
		if (dept == null)
			dept = user.getPrimaryOrg();
		if (dept != null) {
			relatedUserOrder.setDeptId(dept.getId());
			relatedUserOrder.setDeptCode(dept.getCode());
			relatedUserOrder.setDeptName(dept.getName());
			relatedUserOrder.setDeptPath(dept.getPath());
			relatedUserOrder.setDeptPathName(dept.getPathName());
		}
		relatedUserOrder.setIsCurrent(BooleanEnum.IS);
		relatedUserOrder.setRemark("虚拟项目立项");
		FcsBaseResult setResult = projectRelatedUserService.setRelatedUser(relatedUserOrder);
		if (!setResult.isSuccess()) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
				"设置" + userType.getMessage() + "出错");
		}
	}
	
	@Override
	public FcsBaseResult delete(final VirtualProjectDeleteOrder order) {
		return commonProcess(order, "删除虚拟项目", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Domain before() {
				ProjectVirtualDO virtual = null;
				if (order.getVirtualId() > 0) {
					virtual = projectVirtualDAO.findById(order.getVirtualId());
				} else {
					virtual = projectVirtualDAO.findByProjectCode(order.getProjectCode());
				}
				
				if (virtual == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据不存在");
				}
				
				//草稿直接删除
				if (StringUtil.equals(VirtualProjectStatusEnum.DRAFT.code(), virtual.getStatus())
					|| StringUtil.isBlank(virtual.getProjectCode())) {
					projectVirtualDAO.deleteById(virtual.getVirtualId());
					projectVirtualDetailDAO.deleteByVirtualId(virtual.getVirtualId());
				} else { //已经生成项目后、查看项目是否已经被使用（新增了表单）
					HashMap forms = busiDAO.queryProjectForms(virtual.getProjectCode());
					Object inNum = forms.get("in_num");
					if (inNum != null && NumberUtil.parseInt(inNum.toString()) > 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目已发起单据无法删除");
					}
					//删除立项
					projectVirtualDAO.deleteById(virtual.getVirtualId());
					projectVirtualDetailDAO.deleteByVirtualId(virtual.getVirtualId());
					//删除项目
					projectDAO.deleteByProjectCode(virtual.getProjectCode());
					projectDataInfoDAO.deleteByProjectCode(virtual.getProjectCode());
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<VirtualProjectInfo> query(VirtualProjectQueryOrder order) {
		QueryBaseBatchResult<VirtualProjectInfo> batchResult = new QueryBaseBatchResult<VirtualProjectInfo>();
		try {
			
			ProjectVirtualDO queryDO = new ProjectVirtualDO();
			BeanCopier.staticCopy(order, queryDO);
			queryDO.setApplyUserId(order.getBusiManagerId());
			
			long totalCount = projectVirtualDAO.findByConditionCount(queryDO,
				order.getDraftUserId(), order.getLoginUserId(), order.getDeptIdList(),
				order.getRelatedRoleList());
			PageComponent component = new PageComponent(order, totalCount);
			List<ProjectVirtualDO> list = projectVirtualDAO.findByCondition(queryDO,
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder(), order.getDraftUserId(), order.getLoginUserId(),
				order.getDeptIdList(), order.getRelatedRoleList());
			List<VirtualProjectInfo> pageList = new ArrayList<VirtualProjectInfo>(list.size());
			for (ProjectVirtualDO DO : list) {
				VirtualProjectInfo info = convertDO2Info(DO);
				//info.setDetailList(findDetailByVirtualId(info.getVirtualId()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询虚拟项目列表出错   {} {}", e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public VirtualProjectInfo findById(long virtualId) {
		ProjectVirtualDO DO = projectVirtualDAO.findById(virtualId);
		VirtualProjectInfo info = convertDO2Info(DO);
		if (info != null)
			info.setDetailList(findDetailByVirtualId(virtualId));
		return info;
	}
	
	@Override
	public VirtualProjectInfo findByProjectCode(String projectCode) {
		ProjectVirtualDO DO = projectVirtualDAO.findByProjectCode(projectCode);
		VirtualProjectInfo info = convertDO2Info(DO);
		if (info != null)
			info.setDetailList(findDetailByVirtualProjectCode(projectCode));
		return info;
	}
	
	@Override
	public List<VirtualProjectDetailInfo> findDetailByVirtualId(long virtualId) {
		List<ProjectVirtualDetailDO> detailList = projectVirtualDetailDAO
			.findByVirtualId(virtualId);
		List<VirtualProjectDetailInfo> infoList = null;
		if (detailList != null) {
			infoList = Lists.newArrayList();
			for (ProjectVirtualDetailDO DO : detailList) {
				infoList.add(convertDetailDI2Info(DO));
			}
			return infoList;
		}
		return infoList;
	}
	
	@Override
	public List<VirtualProjectDetailInfo> findDetailByVirtualProjectCode(String virtualProjectCode) {
		List<ProjectVirtualDetailDO> detailList = projectVirtualDetailDAO
			.findByVirtualProjectCode(virtualProjectCode);
		List<VirtualProjectDetailInfo> infoList = null;
		if (detailList != null) {
			infoList = Lists.newArrayList();
			for (ProjectVirtualDetailDO DO : detailList) {
				infoList.add(convertDetailDI2Info(DO));
			}
			return infoList;
		}
		return infoList;
	}
	
	private VirtualProjectInfo convertDO2Info(ProjectVirtualDO DO) {
		if (DO == null)
			return null;
		VirtualProjectInfo info = new VirtualProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setStatus(VirtualProjectStatusEnum.getByCode(DO.getStatus()));
		return info;
	}
	
	private VirtualProjectDetailInfo convertDetailDI2Info(ProjectVirtualDetailDO DO) {
		if (DO == null)
			return null;
		VirtualProjectDetailInfo info = new VirtualProjectDetailInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
}
