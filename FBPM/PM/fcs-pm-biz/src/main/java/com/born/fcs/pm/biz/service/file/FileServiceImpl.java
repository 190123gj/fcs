package com.born.fcs.pm.biz.service.file;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FFileBorrowDO;
import com.born.fcs.pm.dal.dataobject.FFileDO;
import com.born.fcs.pm.dal.dataobject.FFileInputDO;
import com.born.fcs.pm.dal.dataobject.FFileInputListDO;
import com.born.fcs.pm.dal.dataobject.FFileListDO;
import com.born.fcs.pm.dal.dataobject.FFileListStatusDO;
import com.born.fcs.pm.dal.dataobject.FFileOutputDO;
import com.born.fcs.pm.dal.dataobject.FFileOutputExtensionDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.FileExtensionFormDO;
import com.born.fcs.pm.dataobject.FileFormDO;
import com.born.fcs.pm.dataobject.FileInOutFormDO;
import com.born.fcs.pm.dataobject.FileNotArchiveDO;
import com.born.fcs.pm.dataobject.FileViewDO;
import com.born.fcs.pm.dataobject.SetupFormProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FileAttachTypeEnum;
import com.born.fcs.pm.ws.enums.FileFormEnum;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.file.FileBorrowInfo;
import com.born.fcs.pm.ws.info.file.FileFormInfo;
import com.born.fcs.pm.ws.info.file.FileInOutInfo;
import com.born.fcs.pm.ws.info.file.FileInfo;
import com.born.fcs.pm.ws.info.file.FileInputInfo;
import com.born.fcs.pm.ws.info.file.FileInputListInfo;
import com.born.fcs.pm.ws.info.file.FileListInfo;
import com.born.fcs.pm.ws.info.file.FileOutputExtensionInfo;
import com.born.fcs.pm.ws.info.file.FileOutputInfo;
import com.born.fcs.pm.ws.info.file.FileViewInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.file.FileBatchApplyOrder;
import com.born.fcs.pm.ws.order.file.FileBatchQueryOrder;
import com.born.fcs.pm.ws.order.file.FileBorrowOrder;
import com.born.fcs.pm.ws.order.file.FileCatalogOrder;
import com.born.fcs.pm.ws.order.file.FileImportBatchResult;
import com.born.fcs.pm.ws.order.file.FileImportOrder;
import com.born.fcs.pm.ws.order.file.FileImportResult;
import com.born.fcs.pm.ws.order.file.FileInputListOrder;
import com.born.fcs.pm.ws.order.file.FileListOrder;
import com.born.fcs.pm.ws.order.file.FileOrder;
import com.born.fcs.pm.ws.order.file.FileOutputExtensionOrder;
import com.born.fcs.pm.ws.order.file.FileOutputOrder;
import com.born.fcs.pm.ws.order.file.FileQueryOrder;
import com.born.fcs.pm.ws.order.file.ImportOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.file.FileService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("fileService")
public class FileServiceImpl extends BaseFormAutowiredDomainService implements FileService {
	@Autowired
	CommonAttachmentService commonAttachmentService;
	
	@Autowired
	LoanUseApplyService loanUseApplyService;
	
	@Autowired
	ProjectService projectService;
	
	@Override
	public FileInfo findByFormId(long formId, String type) {
		FileInfo info = new FileInfo();
		Lists.newArrayList();
		if (formId > 0) {
			FFileDO DO = fileDAO.findByFormId(formId, type);
			if (null != DO) {
				BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
				List<FileInputListInfo> fileListInfos = Lists.newArrayList();
				List<FFileInputListDO> fileListDO = fileInputListDAO.findByFileId(DO.getFileId());
				if (fileListDO.size() > 0) {
					for (FFileInputListDO DO2 : fileListDO) {
						fileListInfos.add(convertDO2Info(DO2));
					}
					info.setFileListInfos(fileListInfos);
				}
			}
			return info;
		}
		return null;
	}
	
	@Override
	public FileOutputInfo findByOutputFormId(long formId) {
		FileOutputInfo info = new FileOutputInfo();
		if (formId > 0) {
			FFileOutputDO DO = fileOutputDAO.findByFormId(formId);
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
			info.setFileForm(FileFormEnum.getByCode(DO.getFileFormType()));
		}
		
		return info;
	}
	
	@Override
	public FileBorrowInfo findByBorrowFormId(long formId) {
		FileBorrowInfo info = new FileBorrowInfo();
		if (formId > 0) {
			FFileBorrowDO DO = fileBorrowDAO.findByFormId(formId);
			if (null != DO) {
				BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
			}
		}
		return info;
	}
	
	@Override
	public FileInfo findById(long id) {
		FileInfo info = new FileInfo();
		List<FileInputListInfo> fileListInfos = Lists.newArrayList();
		List<FFileInputListDO> fileListDO = Lists.newArrayList();
		if (id > 0) {
			FFileDO DO = fileDAO.findById(id);
			fileListDO = fileInputListDAO.findByFileId(DO.getFileId());
			info = convertDO2Info(DO);
		}
		if (fileListDO.size() > 0) {
			for (FFileInputListDO DO2 : fileListDO) {
				fileListInfos.add(convertDO2Info(DO2));
			}
		}
		info.setFileListInfos(fileListInfos);
		return info;
	}
	
	@Override
	public List<FileListInfo> findByType(String type) {
		List<FileListInfo> fileListInfos = Lists.newArrayList();
		List<FFileListDO> fileListDO = Lists.newArrayList();
		if (type != null) {
			fileListDO = fileListDAO.findByType(type);
		}
		if (fileListDO.size() > 0) {
			for (FFileListDO DO2 : fileListDO) {
				fileListInfos.add(convertDO2Info(DO2));
			}
		}
		return fileListInfos;
	}
	
	@Override
	public List<FileListInfo> findByTypeAndNotArchive(String type) {
		List<FileListInfo> fileListInfos = Lists.newArrayList();
		List<FFileListDO> fileListDO = Lists.newArrayList();
		if (type != null) {
			fileListDO = fileListDAO.findByTypeAndNotArchive(type);
		}
		if (fileListDO.size() > 0) {
			for (FFileListDO DO2 : fileListDO) {
				fileListInfos.add(convertDO2Info(DO2));
			}
		}
		return fileListInfos;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> querySelectableProject(FileQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		if (null == queryOrder) {
			return baseBatchResult;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		paramMap.put("busiManagerbId", queryOrder.getBusiManagerbId());
		paramMap.put("legalManagerId", queryOrder.getLegalManagerId());
		paramMap.put("riskManagerId", queryOrder.getRiskManagerId());
		paramMap.put("isHasLoanAmount", queryOrder.getIsHasLoanAmount().code());
		paramMap.put("phases", queryOrder.getPhasesList());
		long totalSize = extraDAO.searchFileSelectProjectCount(paramMap);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ProjectDO> pageList = extraDAO.searchFileSelectProject(paramMap);
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectDO project : pageList) {
					ProjectInfo info = DoConvert.convertProjectDO2Info(project);
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
	public List<FileListInfo> searchNotArchiveByProjectCode(long formId, String type,
															String projectCode) {
		List<FileListInfo> listInfos = Lists.newArrayList();
		List<FileNotArchiveDO> notArchiveDOs = extraDAO.searchNotArchiveByProjectCode(formId, type,
			projectCode);
		if (notArchiveDOs.size() > 0) {
			for (FileNotArchiveDO archiveDO : notArchiveDOs) {
				FileListInfo fileListInfo = new FileListInfo();
				BeanCopier.staticCopy(archiveDO, fileListInfo, UnBoxingConverter.getInstance());
				listInfos.add(fileListInfo);
			}
		}
		return listInfos;
	}
	
	@Override
	public List<FileInputListInfo> searchArchivedByProjectCode(long formId, String type,
																String projectCode,
																String notNeedDraft, String no) {
		List<FileInputListInfo> listInfos = Lists.newArrayList();
		List<com.born.fcs.pm.dataobject.FFileInputListDO> archivedDOs = extraDAO
			.searchArchivedByProjectCode(formId, type, projectCode, notNeedDraft, no);
		if (archivedDOs.size() > 0) {
			for (com.born.fcs.pm.dataobject.FFileInputListDO archiveDO : archivedDOs) {
				FileInputListInfo fileListInfo = new FileInputListInfo();
				BeanCopier.staticCopy(archiveDO, fileListInfo, UnBoxingConverter.getInstance());
				fileListInfo.setAttachType(FileAttachTypeEnum.getByCode(archiveDO.getAttachType()));
				fileListInfo.setFormStatus(FormStatusEnum.getByCode(archiveDO.getFormStatus()));
				fileListInfo.setStatus(FileStatusEnum.getByCode(archiveDO.getStatus()));
				listInfos.add(fileListInfo);
			}
		}
		return listInfos;
	}
	
	@Override
	public String findCheckStatus(String type) {
		FFileListStatusDO checkStatusDO = fileListStatusDAO.findByType(type);
		if (checkStatusDO != null) {
			return checkStatusDO.getCheckStatus();
		} else {
			return "";
		}
	}
	
	private FileInputInfo convertDO2Info(FFileInputDO DO) {
		if (DO == null)
			return null;
		FileInputInfo info = new FileInputInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		return info;
	}
	
	private FileInputListInfo convertDO2Info(FFileInputListDO DO) {
		if (DO == null)
			return null;
		FileInputListInfo info = new FileInputListInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setAttachType(FileAttachTypeEnum.getByCode(DO.getAttachType()));
		return info;
	}
	
	private FileInfo convertDO2Info(FFileDO DO) {
		if (DO == null)
			return null;
		FileInfo info = new FileInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setFileType(FileTypeEnum.getByCode(DO.getType()));
		return info;
	}
	
	private FileListInfo convertDO2Info(FFileListDO DO) {
		if (DO == null)
			return null;
		FileListInfo info = new FileListInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		return info;
	}
	
	private FileFormInfo convertDO2Info(FileFormDO DO) {
		if (DO == null)
			return null;
		FileFormInfo info = new FileFormInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
		info.setStatus(FileStatusEnum.getByCode(DO.getStatus()));
		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
		info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
		info.setType(FileTypeEnum.getByCode(DO.getType()));
		return info;
	}
	
	@Override
	public FcsBaseResult save(final FileCatalogOrder order) {
		return commonProcess(order, "文件目录", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//更新
				List<FFileListDO> inDataBaseList = fileListDAO.findByType(order.getCurrType()
					.code());
				Map<Long, String> inDataBaseMap = new HashMap<Long, String>();
				if (inDataBaseList != null) {
					for (FFileListDO DO : inDataBaseList) {
						inDataBaseMap.put(DO.getId(), DO.getFileName());
					}
				}
				//更新文件
				if (order.getFileListOrder() != null) {
					int sortOrder = 0;
					for (FileListOrder fileListOrder : order.getFileListOrder()) {
						if (fileListOrder.getId() <= 0) {//不存在就新增
							FFileListDO fileListDO = new FFileListDO();
							BeanCopier.staticCopy(fileListOrder, fileListDO,
								UnBoxingConverter.getInstance());
							//fileListDO.setFileId(order.getFileId());
							fileListDO.setRawAddTime(now);
							fileListDO.setType(order.getCurrType().code());
							fileListDO.setSortOrder(sortOrder);
							fileListDAO.insert(fileListDO);
						} else {
							FFileListDO fileListDO = fileListDAO.findById(fileListOrder.getId());
							if (null == fileListDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"未找到文件目录记录");
							}
							
							BeanCopier.staticCopy(fileListOrder, fileListDO,
								UnBoxingConverter.getInstance());
							fileListDO.setSortOrder(sortOrder);
							fileListDAO.update(fileListDO);
							inDataBaseMap.remove(fileListDO.getId());
						}
						sortOrder++;
					}
				}
				if (inDataBaseMap.size() > 0) {//删除
					for (long id : inDataBaseMap.keySet()) {
						fileListDAO.deleteById(id);
					}
					
				}
				//更新页面完整性标志
				FFileListStatusDO statusDO = new FFileListStatusDO();
				statusDO.setRawAddTime(now);
				statusDO.setCheckStatus(order.getCheckStatus());
				statusDO.setType("FILE_CATALOG_MG");
				FFileListStatusDO listStatusDO = fileListStatusDAO.findByType("FILE_CATALOG_MG");
				if (listStatusDO == null) {
					fileListStatusDAO.insert(statusDO);
				} else {
					listStatusDO.setCheckStatus(order.getCheckStatus());
					fileListStatusDAO.update(listStatusDO);
				}
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 生成档案编号
	 * @return
	 */
	private synchronized String genFileCode(FileTypeEnum type, String busiType, String deptCode,
											String projectCode) {
		if (busiType.length() == 1) {
			busiType = "00" + busiType;
		} else if (busiType.length() == 2) {
			busiType = "0" + busiType;
		}
		SetupFormProjectDO setupForm = new SetupFormProjectDO();
		setupForm.setFormStatus(FormStatusEnum.APPROVAL.code());
		setupForm.setProjectCode(projectCode);
		Date projectFinishTime = null;
		List<SetupFormProjectDO> pageList = extraDAO.searchSetupForm(setupForm, 0, 999, null, null,
			null, null, null, null);//查询立项通过时间
		if (pageList != null && pageList.size() > 0) {
			projectFinishTime = pageList.get(0).getFinishTime();
		} else {
			projectFinishTime = new Date();
		}
		ProjectDO projectDO = projectDAO.findByProjectCode(projectCode);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String monthStr = "";
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = month + "";
		}
		Calendar now = Calendar.getInstance();
		now.setTime(projectFinishTime);
		String seqName = SysDateSeqNameEnum.FILE_CODE_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String fileCode = "";
		if (FileTypeEnum.CREDIT_BUSSINESS.code().equals(type.code())) {
			fileCode = fileCode + "基";
		} else if (FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code().equals(type.code())) {
			fileCode = fileCode + "授后" + now.get(Calendar.YEAR) + "-" + busiType + "-"
						+ projectDO.getDeptCode() + "-"
						+ StringUtil.leftPad(String.valueOf(seq), 3, "0") + "-" + year;
			if (month == 1 || month == 2 || month == 3) {
				fileCode = fileCode + "A";
			}
			if (month == 4 || month == 5 || month == 6) {
				fileCode = fileCode + "B";
			}
			if (month == 7 || month == 8 || month == 9) {
				fileCode = fileCode + "C";
			}
			if (month == 10 || month == 11 || month == 12) {
				fileCode = fileCode + "D";
			}
			return fileCode;
		} else if (FileTypeEnum.DOCUMENT_OF_TITLE.code().equals(type.code())) {
			fileCode = fileCode + "权证";
		} else if (FileTypeEnum.RISK_COMMON.code().equals(type.code())) {
			fileCode = fileCode + "险" + now.get(Calendar.YEAR) + "-" + busiType + "-"
						+ projectDO.getDeptCode() + "-"
						+ StringUtil.leftPad(String.valueOf(seq), 3, "0") + "-01" + "-" + year
						+ monthStr;
			return fileCode;
		} else if (FileTypeEnum.RISK_LAWSUIT.code().equals(type.code())) {
			fileCode = fileCode + "险" + now.get(Calendar.YEAR) + "-" + busiType + "-"
						+ projectDO.getDeptCode() + "-"
						+ StringUtil.leftPad(String.valueOf(seq), 3, "0") + "-02" + "-" + year
						+ monthStr;
			return fileCode;
		}
		fileCode = fileCode + now.get(Calendar.YEAR) + "-" + busiType + "-"
					+ projectDO.getDeptCode() + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return fileCode;
	}
	
	@Override
	public QueryBaseBatchResult<FileFormInfo> query(FileQueryOrder order) {
		QueryBaseBatchResult<FileFormInfo> baseBatchResult = new QueryBaseBatchResult<FileFormInfo>();
		
		FileFormDO queryCondition = new FileFormDO();
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition, UnBoxingConverter.getInstance());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getFileCode() != null)
			queryCondition.setFileCode(order.getFileCode());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getFileStatus() != null) {
			queryCondition.setStatus(order.getFileStatus());
		}
		if (order.getFormStatus() != null) {
			queryCondition.setFormStatus(order.getFormStatus());
		}
		if (order.getCustomerName() != null) {
			queryCondition.setCustomerName(order.getCustomerName());
		}
		if (order.getApplyType() != null) {
			queryCondition.setApplyType(order.getApplyType());
		}
		if (order.getFileType() != null) {
			queryCondition.setFileType(order.getFileType());
		}
		if (order.getStartTime() != null) {
			queryCondition.setStartTime(DateUtil.getStartTimeOfTheDate(order.getStartTime()));
		}
		if (order.getEndTime() != null) {
			queryCondition.setEndTime(DateUtil.getEndTimeOfTheDate(order.getEndTime()));
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		if (order.getIsFileAdmin() != null) {
			queryCondition.setIsFileAdmin(order.getIsFileAdmin().code());
		}
		
		long totalSize = extraDAO.searchFileListCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FileFormDO> pageList = extraDAO.searchFileList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		List<FileFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FileFormDO DO : pageList) {
				list.add(convertDO2Info(DO));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<FileFormInfo> fileDetailList(FileQueryOrder order) {
		QueryBaseBatchResult<FileFormInfo> baseBatchResult = new QueryBaseBatchResult<FileFormInfo>();
		
		FileFormDO queryCondition = new FileFormDO();
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition, UnBoxingConverter.getInstance());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getFileCode() != null)
			queryCondition.setFileCode(order.getFileCode());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getFileStatus() != null) {
			queryCondition.setStatus(order.getFileStatus());
		}
		if (order.getFormStatus() != null) {
			queryCondition.setFormStatus(order.getFormStatus());
		}
		if (order.getCustomerName() != null) {
			queryCondition.setCustomerName(order.getCustomerName());
		}
		if (order.getApplyType() != null) {
			queryCondition.setApplyType(order.getApplyType());
		}
		if (order.getArchiveFileName() != null) {
			queryCondition.setArchiveFileName(order.getArchiveFileName());
		}
		if (order.getIsXinHui() != null) {
			queryCondition.setIsXinhui(order.getIsXinHui().code());
		}
		if (StringUtil.isNotBlank(order.getFileType())) {
			queryCondition.setFileType(order.getFileType());
		}
		
		queryCondition.setIsFileAdmin(order.getIsFileAdmin().code());
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchFileDetailListCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FileFormDO> pageList = extraDAO.searchFileDetailList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		List<FileFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FileFormDO DO : pageList) {
				FileFormInfo info = convertDO2Info(DO);
				FileFormDO condition = new FileFormDO();
				condition.setApplyManId(order.getApplyManId());
				condition.setDetailId(DO.getInputListId());
				condition.setFormStatus(FormStatusEnum.APPROVAL.code());
				condition.setApplyType("查阅申请");
				List<FileFormDO> fileList = extraDAO.searchFileList(condition, 0, 1, null, null);
				if (fileList != null && fileList.size() > 0) {
					if (fileList.get(0).getFormStatus().equals(FormStatusEnum.APPROVAL.code())) {
						info.setCanView(BooleanEnum.IS);
					} else {
						info.setCanView(BooleanEnum.NO);
					}
				}
				ProjectInfo projectInfo = projectService.queryByCode(DO.getProjectCode(), false);
				info.setBalance(projectInfo.getBalance());
				info.setLoanned(projectInfo.getLoanedAmount());
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FileInputListInfo findByInputId(long id) {
		FFileInputListDO DO = fileInputListDAO.findById(id);
		FileInputListInfo info = new FileInputListInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		info.setStatus(FileStatusEnum.getByCode(DO.getStatus()));
		return info;
	}
	
	@Override
	public FcsBaseResult saveInputList(final FileInputListOrder order) {
		return commonProcess(order, " 保存文档明细", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileInputListDO listDO = new FFileInputListDO();
					BeanCopier.staticCopy(order, listDO, UnBoxingConverter.getInstance());
					listDO.setRawAddTime(now);
					fileInputListDAO.insert(listDO);
					
				} else {
					//更新
					FFileInputListDO listDO = fileInputListDAO.findById(order.getId());
					if (null == listDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到明细记录");
					}
					listDO.setStatus(order.getStatus().code());
					fileInputListDAO.update(listDO);
					//更新借阅单
					FFileDO fFileDO = fileDAO.findById(listDO.getFileId());
					fileBorrowDAO.updateByFileCode(fFileDO.getFileCode());
					//更新档案项目操作时间
					FileOrder fileOrder = new FileOrder();
					fileOrder.setFileId(listDO.getFileId());
					updateFileProjectTime(fileOrder);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FileFormInfo> needMessageFile() {
		List<FileFormInfo> list = Lists.newArrayList();
		List<FileFormDO> DOs = extraDAO.needMessageFile();
		for (FileFormDO DO : DOs) {
			FileFormInfo info = new FileFormInfo();
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
			list.add(info);
		}
		return list;
	}
	
	@Override
	public List<FileInOutInfo> getInOutHistory(Long inputListId) {
		List<FileInOutInfo> list = Lists.newArrayList();
		List<FileInOutFormDO> DOs = extraDAO.getInOutHistory(inputListId);
		for (FileInOutFormDO DO : DOs) {
			FileInOutInfo info = new FileInOutInfo();
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
			info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
			list.add(info);
		}
		return list;
	}
	
	@Override
	public FormBaseResult saveInput(final FileOrder order) {
		return commonFormSaveProcess(order, " 申请入库", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				//				if(ListUtil.isEmpty(order.getFileListOrder())){
				//					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
				//							"档案明细不能为空!");
				//				}
				if (order.getFileId() == null || order.getFileId() <= 0) {
					if (ListUtil.isNotEmpty(order.getFileListOrder())) {//档案明细为空什么都不做
						final Date now = FcsPmDomainHolder.get().getSysDate();
						//保存
						FFileDO fileDO = new FFileDO();
						
						BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
						fileDO.setType(order.getCurrType().code());
						if (order.getFormId() == null || order.getFormId() <= 0) {
							FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute(
								"form");
							fileDO.setFormId(formInfo.getFormId());
						} else {
							fileDO.setFormId(order.getFormId());
						}
						fileDO.setUpdateTime(now);
						fileDO.setRawAddTime(now);
						if (StringUtil.isEmpty(order.getFileCode())) {
							fileDO.setFileCode(genFileCode(order.getCurrType(),
								order.getBusiType(), order.getDeptCode(), order.getProjectCode()));
						}
						long fileId = fileDAO.insert(fileDO);
						int sortOrder = 0;
						if (ListUtil.isNotEmpty(order.getFileListOrder())) {
							for (FileInputListOrder fileListOrder : order.getFileListOrder()) {
								if (fileListOrder.getInputListId() <= 0) {//不存在就新增
									FFileInputListDO fileListDO = new FFileInputListDO();
									BeanCopier.staticCopy(fileListOrder, fileListDO,
										UnBoxingConverter.getInstance());
									fileListDO.setFileId(fileId);
									//							fileListDO.setAttachType(fileListOrder.getAttachType().code());
									fileListDO.setRawAddTime(now);
									//信惠或者总部风险卷没有填写归档名称就不保存
									if (order.isXinhui()
										|| order.getCurrType().equals(FileTypeEnum.RISK_COMMON)
										|| order.getCurrType().equals(FileTypeEnum.RISK_LAWSUIT)) {
										if ("Y".equals(order.getSubmitStatus())) {
											if (StringUtil.isNotBlank(fileListOrder
												.getArchiveFileName())) {
												fileListDO.setSortOrder(sortOrder);
												fileInputListDAO.insert(fileListDO);
											}
										} else {
											fileListDO.setSortOrder(sortOrder);
											fileInputListDAO.insert(fileListDO);
										}
									} else {
										fileListDO.setSortOrder(sortOrder);
										fileInputListDAO.insert(fileListDO);
									}
									sortOrder++;
								}
								
							}
						} else {//新增的时候校验
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"档案明细不能为空!");
						}
						//更新页面完整性标志
						FFileListStatusDO statusDO = new FFileListStatusDO();
						statusDO.setRawAddTime(now);
						statusDO.setCheckStatus(order.getFileCheckStatus());
						statusDO.setType(Long.toString(fileDO.getFormId()));
						FFileListStatusDO listStatusDO = fileListStatusDAO.findByType(Long
							.toString(fileDO.getFormId()));
						if (listStatusDO == null) {
							fileListStatusDAO.insert(statusDO);
						} else {
							listStatusDO.setCheckStatus(order.getFileCheckStatus());
							fileListStatusDAO.update(listStatusDO);
						}
						//信惠的提交的时候删除其他卷没有归档名称的档案
						if (order.isXinhui() && "Y".equals(order.getSubmitStatus())) {
							deleteNotArchiveName(fileDO.getFormId(), order.getCurrType(),
								order.getFileCheckStatus());
						}
						FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get()
							.getAttribute("result");
						result.setKeyId(fileId);
					}
				} else {
					//更新
					final Date now = FcsPmDomainHolder.get().getSysDate();
					FFileDO fileDO = fileDAO.findById(order.getFileId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到入库记录");
					}
					order.setFileCode(fileDO.getFileCode());
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setType(order.getCurrType().code());
					fileDO.setUpdateTime(now);
					fileDAO.update(fileDO);
					List<FFileInputListDO> inDataBaseList = fileInputListDAO.findByFileId(fileDO
						.getFileId());
					Map<Long, String> inDataBaseMap = new HashMap<Long, String>();
					for (FFileInputListDO DO : inDataBaseList) {
						inDataBaseMap.put(DO.getInputListId(), DO.getFileName());
					}
					//更新文件
					int sortOrder = 0;
					if (ListUtil.isNotEmpty(order.getFileListOrder())) {
						for (FileInputListOrder fileListOrder : order.getFileListOrder()) {
							
							if (fileListOrder.getInputListId() <= 0) {//不存在就新增
								FFileInputListDO fileListDO = new FFileInputListDO();
								BeanCopier.staticCopy(fileListOrder, fileListDO,
									UnBoxingConverter.getInstance());
								fileListDO.setFileId(order.getFileId());
								fileListDO.setRawAddTime(now);
								//	fileListDO.setAttachType(fileListOrder.getAttachType().code());
								//风险卷没有填写归档名称就不保存
								if (order.isXinhui()
									|| order.getCurrType().equals(FileTypeEnum.RISK_COMMON)
									|| order.getCurrType().equals(FileTypeEnum.RISK_LAWSUIT)) {
									if ("Y".equals(order.getSubmitStatus())) {
										if (StringUtil.isNotBlank(fileListOrder
											.getArchiveFileName())) {
											fileListDO.setSortOrder(sortOrder);
											fileInputListDAO.insert(fileListDO);
										}
									} else {
										fileListDO.setSortOrder(sortOrder);
										fileInputListDAO.insert(fileListDO);
									}
								} else {
									fileListDO.setSortOrder(sortOrder);
									fileInputListDAO.insert(fileListDO);
								}
								sortOrder++;
							} else {
								FFileInputListDO fileInputListDO = fileInputListDAO
									.findById(fileListOrder.getInputListId());
								if (null == fileInputListDO) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.HAVE_NOT_DATA, "未找到文件目录记录");
								}
								fileInputListDO.setFileType(fileListOrder.getFileType());
								fileInputListDO.setFileName(fileListOrder.getFileName());
								fileInputListDO.setArchiveFileName(fileListOrder
									.getArchiveFileName());
								fileInputListDO.setFilePage(fileListOrder.getFilePage());
								fileInputListDO.setFilePath(fileListOrder.getFilePath());
								fileInputListDO.setInputRemark(fileListOrder.getInputRemark());
								fileInputListDO.setFileId(order.getFileId());
								fileInputListDO.setNo(fileListOrder.getNo());
								fileInputListDO.setAttachType(fileListOrder.getAttachType());
								if (order.isXinhui()
									|| order.getCurrType().equals(FileTypeEnum.RISK_COMMON)
									|| order.getCurrType().equals(FileTypeEnum.RISK_LAWSUIT)) {
									if ("Y".equals(order.getSubmitStatus())) {
										if (StringUtil.isNotBlank(fileListOrder
											.getArchiveFileName())) {
											fileInputListDO.setSortOrder(sortOrder);
											fileInputListDAO.update(fileInputListDO);
											inDataBaseMap.remove(fileInputListDO.getInputListId());
										}
									} else {
										fileInputListDO.setSortOrder(sortOrder);
										fileInputListDAO.update(fileInputListDO);
										inDataBaseMap.remove(fileInputListDO.getInputListId());
									}
								} else {
									fileInputListDO.setSortOrder(sortOrder);
									fileInputListDAO.update(fileInputListDO);
									inDataBaseMap.remove(fileInputListDO.getInputListId());
								}
								sortOrder++;
								
							}
							//信惠的提交的时候删除其他卷没有归档名称的档案
							if (order.isXinhui() && "Y".equals(order.getSubmitStatus())) {
								deleteNotArchiveName(fileDO.getFormId(), order.getCurrType(),
									order.getFileCheckStatus());
							}
							
						}
					} else {//删除这张
						fileDAO.deleteById(order.getFileId());
						//改变checkstatus
						String checkStatus = order.getFileCheckStatus();
						char[] index = checkStatus.toCharArray();
						if (order.getCurrType().equals(FileTypeEnum.CREDIT_BUSSINESS)) {
							index[0] = '2';
						} else if (order.getCurrType()
							.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT)) {
							index[1] = '2';
						} else if (order.getCurrType().equals(FileTypeEnum.DOCUMENT_OF_TITLE)) {
							index[2] = '2';
						} else if (order.getCurrType().equals(FileTypeEnum.RISK_COMMON)) {
							index[3] = '2';
						} else if (order.getCurrType().equals(FileTypeEnum.RISK_LAWSUIT)) {
							index[4] = '2';
						}
						checkStatus = "";
						for (int i = 0; i < index.length; i++) {
							checkStatus = checkStatus + index[i];
						}
						order.setFileCheckStatus(checkStatus);
					}
					if (inDataBaseMap.size() > 0) {//删除
						for (long id : inDataBaseMap.keySet()) {
							fileInputListDAO.deleteById(id);
						}
						
					}
					//更新页面完整性标志
					FFileListStatusDO listStatusDO = fileListStatusDAO.findByType(Long
						.toString(fileDO.getFormId()));
					listStatusDO.setCheckStatus(order.getFileCheckStatus());
					fileListStatusDAO.update(listStatusDO);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(fileDO.getFileId());
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 提交的时候删除其他卷没有填写归档名称的档案明细
	 */
	
	private void deleteNotArchiveName(long formId, FileTypeEnum cuurType, String checkStatus) {
		List<FileTypeEnum> types = FileTypeEnum.getAllEnum();
		for (FileTypeEnum type : types) {
			if (type != cuurType) {
				FFileDO DO = fileDAO.findByFormId(formId, type.code());
				if (null != DO) {
					List<FFileInputListDO> fileListDO = fileInputListDAO.findByFileId(DO
						.getFileId());
					if (fileListDO.size() > 0) {
						for (FFileInputListDO listDO : fileListDO) {
							if (StringUtil.isBlank(listDO.getArchiveFileName())) {
								fileInputListDAO.deleteById(listDO.getInputListId());
							}
						}
					}
				}
				
			}
		}
	}
	
	@Override
	public FormBaseResult saveOuput(final FileOutputOrder order) {
		return commonFormSaveProcess(order, " 申请出库", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String formIdStr = "";
				if (null == order.getId() || order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileOutputDO fileDO = new FFileOutputDO();
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setFormId(formInfo.getFormId());
					fileDO.setRawAddTime(now);
					fileOutputDAO.insert(fileDO);
					formIdStr = formInfo.getFormId() + "";
				} else {
					//更新
					FFileOutputDO fileDO = fileOutputDAO.findById(order.getId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到出库记录");
					}
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileOutputDAO.update(fileDO);
					formIdStr = fileDO.getFormId() + "";
				}
				//更新档案项目操作时间
				FileOrder fileOrder = new FileOrder();
				fileOrder.setFileId(order.getFileId());
				updateFileProjectTime(fileOrder);
				//保存附件
				if (order.getFileForm() == FileFormEnum.OUTPUT
					&& StringUtil.isNotEmpty(order.getAttachment())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder.setBizNo("PM_" + formIdStr + "_DACK");
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.FILE_OUTPUT);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getAttachment());
					attachOrder.setRemark("档案出库附件");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
					
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveBorrow(final FileBorrowOrder order) {
		return commonFormSaveProcess(order, "借阅申请", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				FFileBorrowDO borrowDO = null;
				if (null != order.getBorrowFormId() && order.getBorrowFormId() > 0) {
					borrowDO = fileBorrowDAO.findByFormId(order.getBorrowFormId());
				}
				if (order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileBorrowDO fileDO = new FFileBorrowDO();
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setFormId(formInfo.getFormId());
					fileDO.setRawAddTime(now);
					if (null != borrowDO) {
						fileDO.setFileCode(borrowDO.getFileCode());
						fileDO.setOldFileCode(borrowDO.getOldFileCode());
						fileDO.setProjectCode(borrowDO.getProjectCode());
						fileDO.setCustomerName(borrowDO.getCustomerName());
					}
					fileBorrowDAO.insert(fileDO);
				} else {
					//更新
					FFileBorrowDO fileDO = fileBorrowDAO.findById(order.getId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到出库记录");
					}
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileBorrowDAO.update(fileDO);
				}
				//更新
				if (null != borrowDO && !"YES".equals(borrowDO.getIsBatch())) {
					borrowDO.setIsBatch("YES");
					fileBorrowDAO.update(borrowDO);
				}
				//更新档案项目操作时间
				FileOrder fileOrder = new FileOrder();
				fileOrder.setFileId(order.getFileId());
				updateFileProjectTime(fileOrder);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult updateFileProjectTime(final FileOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		if (order.getFileId() == null || order.getFileId() < 0) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
				"更新项目档案操作时间出错,未找到该记录");
		}
		final Date now = FcsPmDomainHolder.get().getSysDate();
		FFileDO DO = fileDAO.findById(order.getFileId());
		if (null == DO) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
				"更新项目档案操作时间出错,未找到该记录");
		}
		try {
			DO.setUpdateTime(now);
			fileDAO.update(DO);
			result.setSuccess(true);
			result.setMessage("更新项目档案操作时间成功");
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新项目档案操作时间出错", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新项目档案操作时间出错", e);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<FileViewInfo> fileViewList(FileQueryOrder order) {
		QueryBaseBatchResult<FileViewInfo> baseBatchResult = new QueryBaseBatchResult<FileViewInfo>();
		FileViewDO queryCondition = new FileViewDO();
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition, UnBoxingConverter.getInstance());
		long totalSize = extraDAO.searchFileViewListCount(queryCondition);
		PageComponent component = new PageComponent(order, totalSize);
		List<FileViewDO> pageList = extraDAO.searchFileViewList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		List<FileViewInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FileViewDO DO : pageList) {
				FileViewInfo info = new FileViewInfo();
				BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
				list.add(info);
			}
			
		}
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public String getProjectInputStatusFileIds(String type, String projectCode, Date startTime,
												Date endTime) {
		if (startTime != null) {
			startTime = DateUtil.getStartTimeOfTheDate(startTime);
		}
		if (endTime != null) {
			endTime = DateUtil.getEndTimeOfTheDate(endTime);
		}
		return extraDAO.searchInputStatusFileIds(type, projectCode, startTime, endTime);
	}
	
	@Override
	public FileImportBatchResult fileImport(ImportOrder mainOrder) {
		List<FileImportOrder> orders = mainOrder.getOrders();
		FileImportBatchResult result = new FileImportBatchResult();
		List<FileImportResult> batchResult = Lists.newArrayList();
		//格式化数据 根据档案编号分组
		Map<String, List<FileImportOrder>> fileCodeMap = new HashMap<>();
		//统计项目有几个档案类别，一个档案类别对应几个档案编号
		Map<String, Map<String, String>> projectTypeMap = new HashMap<>();
		for (FileImportOrder order : orders) {
			List<FileImportOrder> tempList = fileCodeMap.get(order.getFileCode());
			if (ListUtil.isEmpty(tempList)) {
				tempList = Lists.newArrayList();
			}
			tempList.add(order);
			fileCodeMap.put(order.getFileCode(), tempList);
			Map<String, String> typeFileCodeMap = projectTypeMap.get(order.getProjectCode() + "_"
																		+ order.getType());
			if (typeFileCodeMap == null || !(typeFileCodeMap.size() > 0)) {
				typeFileCodeMap = new HashMap<>();
			}
			typeFileCodeMap.put(order.getFileCode(), order.getFileCode());
			projectTypeMap.put(order.getProjectCode() + "_" + order.getType(), typeFileCodeMap);
		}
		//保存
		for (String fileCode : fileCodeMap.keySet()) {
			ImportOrder order = new ImportOrder();
			BeanCopier.staticCopy(mainOrder, order, UnBoxingConverter.getInstance());
			order.setFormId(1l);
			order.setOrders(fileCodeMap.get(fileCode));
			batchResult.add(ImportData(order));
		}
		result.setBatchResult(batchResult);
		result.setSuccess(true);
		return result;
	}
	
	public FileImportResult ImportData(final ImportOrder order) {
		return (FileImportResult) commonProcess(order, "已解保项目档案导入",
			new BeforeProcessInvokeService() {
				@Override
				public Domain before() {
					List<FileImportOrder> orders = order.getOrders();
					Date now = FcsPmDomainHolder.get().getSysDate();
					FileImportResult result = (FileImportResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					//校验项目信息
					String projectCode = orders.get(0).getProjectCode();
					String customerName = orders.get(0).getCustomerName();
					String fileCode = orders.get(0).getFileCode();
					result.setProjectCode(projectCode);
					result.setFileCode(fileCode);
					ProjectDO project = new ProjectDO();
					project.setProjectCode(projectCode);
					List<ProjectDO> exists = projectDAO.findByCondition(project, null, 0, null,
						null, null, null, null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null, null, null, null, 0,
						1);
					
					if (ListUtil.isEmpty(exists)) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目编号不存在");
					}
					ProjectDO projectDO = exists.get(0);
					if (!StringUtil.equals(customerName, projectDO.getCustomerName())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目编号与客户名称不匹配");
					}
					//校验档案编号
					long count = extraDAO.checkFileCode(fileCode);
					if (count > 0) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "档案编号已存在");
					}
					//构造form
					FormDO form = new FormDO();
					form.setFormCode(FormCodeEnum.FILE_INPUT_APPLY.code());
					form.setFormName(FormCodeEnum.FILE_INPUT_APPLY.message());
					form.setFormUrl(FormCodeEnum.FILE_INPUT_APPLY.viewUrl());
					form.setUserId(order.getUserId());
					form.setUserAccount(order.getUserAccount());
					form.setUserName(order.getUserName());
					form.setDeptId(order.getDeptId());
					form.setDeptCode(order.getDeptCode());
					form.setDeptName(order.getDeptName());
					form.setDeptPath(order.getDeptPath());
					form.setDeptPathName(order.getDeptPathName());
					form.setStatus(FormStatusEnum.APPROVAL.code());
					form.setCheckStatus("1");
					form.setSubmitTime(now);
					form.setFinishTime(now);
					form.setRawAddTime(now);
					long formId = formDAO.insert(form);
					//构造F_file
					FileTypeEnum type = orders.get(0).getType();
					String handOverDept = orders.get(0).getHandOverDept();
					String handOverMan = orders.get(0).getHandOverMan();
					String handOverTime = orders.get(0).getHandOverTime();
					String receiveDept = orders.get(0).getReceiveDept();
					String receiveMan = orders.get(0).getReceiveMan();
					String receiveTime = orders.get(0).getReceiveTime();
					FFileDO fileDO = new FFileDO();
					fileDO.setFormId(formId);
					fileDO.setFileCode(fileCode);
					fileDO.setType(type.code());
					fileDO.setProjectCode(projectCode);
					fileDO.setProjectName(projectDO.getProjectName());
					fileDO.setCustomerId(projectDO.getCustomerId());
					fileDO.setCustomerName(projectDO.getCustomerName());
					//首次放款时间
					List<FLoanUseApplyReceiptInfo> receipts = loanUseApplyService
						.queryReceipt(projectCode);
					if (ListUtil.isNotEmpty(receipts)) {
						Date firstLoanTime = null;
						for (FLoanUseApplyReceiptInfo receipt : receipts) {
							if (firstLoanTime == null
								|| firstLoanTime.after(receipt.getActualLoanTime()))
								firstLoanTime = receipt.getActualLoanTime();
						}
						fileDO.setFirstLoanTime(firstLoanTime);
					}
					fileDO.setFilingTime(null);
					fileDO.setHandOverDept(handOverDept);
					fileDO.setHandOverMan(handOverMan);
					DateFormat dateFormat = com.born.fcs.pm.util.DateUtil.getFormat("yyyy-MM-dd");
					try {
						fileDO.setHandOverTime(StringUtil.isNotBlank(handOverTime) ? dateFormat
							.parse(getParseAbleDate(handOverTime)) : null);
					} catch (ParseException e) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"档案移交时间解析错误[" + handOverTime + "]");
					}
					fileDO.setReceiveDept(receiveDept);
					fileDO.setReceiveMan(receiveMan);
					try {
						fileDO.setReceiveTime(StringUtil.isNotBlank(receiveTime) ? dateFormat
							.parse(getParseAbleDate(receiveTime)) : null);
					} catch (ParseException e) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"档案接收时间解析错误[" + receiveTime + "]");
					}
					String checkStatus = "";
					if (type == FileTypeEnum.CREDIT_BUSSINESS) {
						checkStatus = "12222";
					} else if (type == FileTypeEnum.CREDIT_BEFORE_MANAGEMENT) {
						checkStatus = "21222";
					} else if (type == FileTypeEnum.DOCUMENT_OF_TITLE) {
						checkStatus = "22122";
					} else if (type == FileTypeEnum.RISK_COMMON) {
						checkStatus = "22212";
					} else if (type == FileTypeEnum.RISK_LAWSUIT) {
						checkStatus = "22221";
					}
					fileDO.setFileCheckStatus(checkStatus);
					fileDO.setDeptCode(projectDO.getDeptCode());
					fileDO.setDeptName(projectDO.getDeptName());
					fileDO.setUpdateTime(now);
					fileDO.setRawAddTime(now);
					long fileId = fileDAO.insert(fileDO);
					//构造_file_input
					int sortOrder = 0;
					for (FileImportOrder order1 : orders) {
						FFileInputListDO listDO = new FFileInputListDO();
						listDO.setFileId(fileId);
						listDO.setFileType(order1.getFileType());
						listDO.setArchiveFileName(order1.getArchiveFileName());
						listDO.setFilePage(Integer.parseInt(order1.getFilePage()));
						listDO.setInputRemark(order1.getRemark());
						listDO.setNo(order1.getNo());
						listDO.setSortOrder(sortOrder);
						listDO.setAttachType(order1.getAttachType().code());
						listDO.setSortOrder(sortOrder);
						listDO.setStatus(FileStatusEnum.INPUT.code());
						listDO.setRawAddTime(now);
						fileInputListDAO.insert(listDO);
						sortOrder++;
					}
					//构造f_file_list_status
					FFileListStatusDO statusDO = new FFileListStatusDO();
					statusDO.setCheckStatus(checkStatus);
					statusDO.setType(formId + "");
					statusDO.setRawAddTime(now);
					fileListStatusDAO.insert(statusDO);
					
					return null;
				}
			}, null, null);
	}
	
	/**
	 * 处理各种怪异日期格式 9/9.22 9/9/9
	 * @param date
	 * @return
	 */
	private String getParseAbleDate(String date) {
		date = date.replaceAll("/", "-").replaceAll("\\.", "-");
		if (date.split("-")[0].length() == 1)
			date = "0" + date;
		return date;
	}
	
	@Override
	protected FileImportResult createResult() {
		return new FileImportResult();
	}
	
	@Override
	public List<FileInfo> findByInputIds(String ids) {
		if (StringUtil.isBlank(ids)) {
			return null;
		}
		
		try {
			List<Long> list = new ArrayList<>();
			String[] idses = ids.split(",");
			for (String s : idses) {
				list.add(Long.valueOf(s));
			}
			
			List<FFileInputListDO> inputs = fileInputListDAO.findByCondition(
				new FFileInputListDO(), 0L, 99L, list, null);
			if (ListUtil.isEmpty(inputs)) {
				return null;
			}
			
			Map<Long, FFileInputListDO> inputMap = new HashMap<>();
			List<Long> fileIds = new ArrayList<>();
			for (FFileInputListDO doObj : inputs) {
				inputMap.put(doObj.getInputListId(), doObj);
				fileIds.add(doObj.getFileId());
			}
			
			List<FFileDO> files = fileDAO
				.findByCondition(new FFileDO(), 0, fileIds.size(), fileIds);
			if (ListUtil.isEmpty(files)) {
				return null;
			}
			
			Map<Long, FFileDO> fileMap = new HashMap<>();
			for (FFileDO file : files) {
				fileMap.put(file.getFileId(), file);
			}
			
			Set<String> sets = new HashSet<>();
			List<FileInfo> fileInfos = new ArrayList<>();
			for (Long inputId : list) {
				FFileInputListDO inputDO = inputMap.get(inputId);
				if (null == inputDO) {
					continue;
				}
				
				FFileDO fileDO = fileMap.get(inputDO.getFileId());
				if (null == fileDO) {
					continue;
				}
				
				if (sets.contains(fileDO.getProjectCode())) {
					continue;
				}
				
				sets.add(fileDO.getProjectCode());
				fileInfos.add(convertDO2Info(fileDO));
			}
			
			return fileInfos;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取档案列表失败:[" + ids + "]", e);
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<FileInfo> queryFiles(FileBatchQueryOrder queryOrder) {
		QueryBaseBatchResult<FileInfo> baseBatchResult = new QueryBaseBatchResult<>();
		if (null == queryOrder) {
			return baseBatchResult;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectCodes", split(queryOrder.getProjectCodes()));
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("customerNames", split(queryOrder.getCustomerNames()));
		paramMap.put("fileCode", queryOrder.getFileCode());
		paramMap.put("fileCodes", split(queryOrder.getFileCodes()));
		if (StringUtil.isBlank(queryOrder.getFormStatus())) {
			paramMap.put("status", FormStatusEnum.APPROVAL.code());
		} else {
			paramMap.put("status", queryOrder.getFormStatus());
		}
		paramMap.put("type", queryOrder.getFileType());
		paramMap.put("startTime", queryOrder.getStartTime());
		paramMap.put("endTime", queryOrder.getEndTime());
		
		long totalSize = extraDAO.searchFileCodeCount(paramMap);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<FFileDO> pageList = extraDAO.searchFileCode(paramMap);
			
			List<FileInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (FFileDO doObj : pageList) {
					FileInfo info = convertDO2Info(doObj);
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	private List<String> split(String s) {
		List<String> list = new ArrayList<>();
		if (StringUtil.isNotBlank(s)) {
			String[] ss = s.split(",");
			for (String str : ss) {
				list.add(str);
			}
		}
		return list;
	}
	
	@Override
	public FcsBaseResult checkApply(final FileBatchApplyOrder order) {
		return commonProcess(order, "申请验证", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				List<Long> fileIds = new ArrayList<>();
				String[] ss = order.getFileIds().split(",");
				for (String s : ss) {
					fileIds.add(Long.valueOf(s));
				}
				
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("status", order.getStatus());
				if (fileIds.size() == 1) {
					paramMap.put("fileId", fileIds.get(0));
				} else {
					paramMap.put("fileIds", fileIds);
				}
				
				long totalSize = extraDAO.searchFileStatusCount(paramMap);
				if (totalSize > 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"档案状态不一致，不能提交申请");
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FileInfo> findByFileIds(String fileIds) {
		if (StringUtil.isBlank(fileIds)) {
			return null;
		}
		
		try {
			List<Long> ids = new ArrayList<>();
			for (String s : fileIds.split(",")) {
				ids.add(Long.valueOf(s));
			}
			
			List<FFileDO> files = fileDAO.findByCondition(new FFileDO(), 0, ids.size(), ids);
			if (ListUtil.isEmpty(files)) {
				return null;
			}
			
			List<FFileInputListDO> inputs = fileInputListDAO.findByCondition(
				new FFileInputListDO(), 0L, 99999L, null, ids);
			Map<Long, List<FileInputListInfo>> map = new HashMap<>();
			if (ListUtil.isNotEmpty(inputs)) {
				for (FFileInputListDO input : inputs) {
					FileInputListInfo inputInfo = convertDO2Info(input);
					List<FileInputListInfo> inputList = map.get(input.getFileId());
					if (null == inputList) {
						inputList = new ArrayList<>();
						map.put(input.getFileId(), inputList);
					}
					inputList.add(inputInfo);
				}
			}
			
			List<FileInfo> fileInfos = new ArrayList<>(files.size());
			for (FFileDO file : files) {
				FileInfo fileInfo = convertDO2Info(file);
				fileInfo.setFileListInfos(map.get(file.getFileId()));
				fileInfos.add(fileInfo);
			}
			
			return fileInfos;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据fileIds查询失败: " + fileIds);
		}
		return null;
	}
	
	@Override
	public FormBaseResult saveOuputExtension(final FileOutputExtensionOrder order) {
		return commonFormSaveProcess(order, " 申请出库展期", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String formIdStr = "";
				if (null == order.getId() || order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileOutputExtensionDO fileDO = new FFileOutputExtensionDO();
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setFormId(formInfo.getFormId());
					fileDO.setRawAddTime(now);
					fileOutputExtensionDAO.insert(fileDO);
				} else {
					//更新
					FFileOutputExtensionDO fileDO = fileOutputExtensionDAO.findById(order.getId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到出库记录");
					}
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileOutputExtensionDAO.update(fileDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FileOutputExtensionInfo findByOutputExtensionFormId(long formId) {
		FileOutputExtensionInfo info = new FileOutputExtensionInfo();
		if (formId > 0) {
			FFileOutputExtensionDO DO = fileOutputExtensionDAO.findByFormId(formId);
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		}
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<FileFormInfo> extensionList(FileQueryOrder order) {
		QueryBaseBatchResult<FileFormInfo> baseBatchResult = new QueryBaseBatchResult<FileFormInfo>();
		
		FileFormDO queryCondition = new FileFormDO();
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition, UnBoxingConverter.getInstance());
		
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getFileCode() != null)
			queryCondition.setFileCode(order.getFileCode());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getFormStatus() != null) {
			queryCondition.setFormStatus(order.getFormStatus());
		}
		if (order.getCustomerName() != null) {
			queryCondition.setCustomerName(order.getCustomerName());
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchExtensionListCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FileExtensionFormDO> pageList = extraDAO.searchExtensionList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		List<FileFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FileExtensionFormDO DO : pageList) {
				FileFormInfo info = new FileFormInfo();
				BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
}
