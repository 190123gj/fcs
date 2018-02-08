package com.born.fcs.pm.biz.service.file;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dataobject.*;
import com.born.fcs.pm.ws.enums.*;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

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
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.file.FileBorrowInfo;
import com.born.fcs.pm.ws.info.file.FileFormInfo;
import com.born.fcs.pm.ws.info.file.FileInfo;
import com.born.fcs.pm.ws.info.file.FileInputInfo;
import com.born.fcs.pm.ws.info.file.FileInputListInfo;
import com.born.fcs.pm.ws.info.file.FileListInfo;
import com.born.fcs.pm.ws.info.file.FileOutputInfo;
import com.born.fcs.pm.ws.order.file.FileBorrowOrder;
import com.born.fcs.pm.ws.order.file.FileCatalogOrder;
import com.born.fcs.pm.ws.order.file.FileInputListOrder;
import com.born.fcs.pm.ws.order.file.FileListOrder;
import com.born.fcs.pm.ws.order.file.FileOrder;
import com.born.fcs.pm.ws.order.file.FileOutputOrder;
import com.born.fcs.pm.ws.order.file.FileQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.file.FileService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("fileService")
public class FileServiceImpl extends BaseFormAutowiredDomainService implements FileService {
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
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
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
	public List<FileListInfo>  searchNotArchiveByProjectCode(long formId, String type,
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
	public List<FileInputListInfo>  searchArchivedByProjectCode(long formId,String type,
																String projectCode) {
		List<FileInputListInfo> listInfos = Lists.newArrayList();
		List<com.born.fcs.pm.dataobject.FFileInputListDO> archivedDOs = extraDAO.searchArchivedByProjectCode(formId,type,
				projectCode);
		if (archivedDOs.size() > 0) {
			for (com.born.fcs.pm.dataobject.FFileInputListDO archiveDO : archivedDOs) {
				FileInputListInfo fileListInfo = new FileInputListInfo();
				BeanCopier.staticCopy(archiveDO, fileListInfo, UnBoxingConverter.getInstance());
				fileListInfo.setAttachType(FileAttachTypeEnum.getByCode(archiveDO.getAttachType()));
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
					for (FileListOrder fileListOrder : order.getFileListOrder()) {
						if (fileListOrder.getId() <= 0) {//不存在就新增
							FFileListDO fileListDO = new FFileListDO();
							BeanCopier.staticCopy(fileListOrder, fileListDO,
									UnBoxingConverter.getInstance());
							//fileListDO.setFileId(order.getFileId());
							fileListDO.setRawAddTime(now);
							fileListDO.setType(order.getCurrType().code());
							fileListDAO.insert(fileListDO);
						} else {
							FFileListDO fileListDO = fileListDAO.findById(fileListOrder.getId());
							if (null == fileListDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
										"未找到文件目录记录");
							}

							BeanCopier.staticCopy(fileListOrder, fileListDO,
									UnBoxingConverter.getInstance());
							fileListDAO.update(fileListDO);
							inDataBaseMap.remove(fileListDO.getId());
						}

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
		SetupFormProjectDO setupForm = new SetupFormProjectDO();
		setupForm.setFormStatus(FormStatusEnum.APPROVAL.code());
		setupForm.setProjectCode(projectCode);
		Date projectFinishTime=null;
		List<FormProjectDO> pageList = extraDAO.searchSetupForm(setupForm,
				0, 999, null,
				null, null, null,
				null);//查询立项通过时间
		if(pageList!=null&&pageList.size()>0){
			projectFinishTime=pageList.get(0).getFinishTime();
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		String monthStr="";
		if(month<10){
			monthStr="0"+month;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(projectFinishTime);
		String seqName = SysDateSeqNameEnum.FILE_CODE_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String fileCode = "";
		if (FileTypeEnum.CREDIT_BUSSINESS.code().equals(type.code())) {
			fileCode = fileCode + "基";
		}
		else if (FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code().equals(type.code())) {
			fileCode = fileCode + "授后"+ now.get(Calendar.YEAR) + "-" + busiType + "-" + deptCode + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 3, "0")+"-"+year;
			if(month==1||month==2||month==3){
				fileCode=fileCode+"A";
			}
			if(month==4||month==5||month==6){
				fileCode=fileCode+"B";
			}
			if(month==7||month==8||month==9){
				fileCode=fileCode+"C";
			}
			if(month==10||month==11||month==12){
				fileCode=fileCode+"D";
			}
			return fileCode;
		}
		else if (FileTypeEnum.DOCUMENT_OF_TITLE.code().equals(type.code())) {
			fileCode = fileCode + "权证";
		}else if (FileTypeEnum.RISK_COMMON.code().equals(type.code())) {
			fileCode = fileCode + "险"+now.get(Calendar.YEAR)+"-"+ busiType + "-"
					+ deptCode + "-"+ StringUtil.leftPad(String.valueOf(seq), 3, "0")+"-01"+"-"+year+monthStr;
			return  fileCode;
		}else if (FileTypeEnum.RISK_LAWSUIT.code().equals(type.code())) {
			fileCode = fileCode + "险"+now.get(Calendar.YEAR)+"-"+ busiType + "-"
					+ deptCode + "-"+ StringUtil.leftPad(String.valueOf(seq), 3, "0")+"-02"+"-"+year+monthStr;
			return  fileCode;
		}
		fileCode = fileCode + now.get(Calendar.YEAR) + "-" + busiType + "-" + deptCode + "-"
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
		if(order.getFileType()!=null){
			queryCondition.setFileType(order.getFileType());
		}if(order.getStartTime()!=null){
			queryCondition.setStartTime(order.getStartTime());
		}if(order.getEndTime()!=null){
			queryCondition.setEndTime(order.getEndTime());
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
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
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchFileDetailListCount(queryCondition);

		PageComponent component = new PageComponent(order, totalSize);
		List<FileFormDO> pageList = extraDAO.searchFileDetailList(queryCondition,
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());
		List<FileFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FileFormDO DO : pageList) {
				FileFormInfo info=convertDO2Info(DO);
				FileFormDO condition = new FileFormDO();
				condition.setApplyManId(order.getApplyManId());
				condition.setDetailId(DO.getInputListId());
				//condition.setFormStatus(FormStatusEnum.APPROVAL.code());
				condition.setApplyType("查阅申请");
				List<FileFormDO> fileList = extraDAO.searchFileList(condition,
						0, 999, null,null);
				if(fileList!=null&&fileList.size()>0){
					if(fileList.get(0).getFormStatus().equals(FormStatusEnum.APPROVAL.code())){
						info.setCanView(BooleanEnum.IS);
					}else {
						info.setCanView(BooleanEnum.NO);
					}
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
	public FileInputListInfo findByInputId(long id) {
		FFileInputListDO DO = fileInputListDAO.findById(id);
		FileInputListInfo info = new FileInputListInfo();
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
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
					FFileDO fFileDO=fileDAO.findById(listDO.getFileId());
					fileBorrowDAO.updateByFileCode(fFileDO.getFileCode());
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
	public FormBaseResult saveInput(final FileOrder order) {
		return commonFormSaveProcess(order, " 申请入库", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getFileId() == null || order.getFileId() <= 0) {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileDO fileDO = new FFileDO();

					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setType(order.getCurrType().code());
					if (order.getFormId() == null || order.getFormId() <= 0) {
						FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
						fileDO.setFormId(formInfo.getFormId());
					} else {
						fileDO.setFormId(order.getFormId());
					}
					fileDO.setRawAddTime(now);
					fileDO.setFileCode(genFileCode(order.getCurrType(), order.getBusiType(),
							order.getDeptCode(), order.getProjectCode()));
					long fileId = fileDAO.insert(fileDO);

					for (FileInputListOrder fileListOrder : order.getFileListOrder()) {
						if (fileListOrder.getInputListId() <= 0) {//不存在就新增
							FFileInputListDO fileListDO = new FFileInputListDO();
							BeanCopier.staticCopy(fileListOrder, fileListDO,
									UnBoxingConverter.getInstance());
							fileListDO.setFileId(fileId);
//							fileListDO.setAttachType(fileListOrder.getAttachType().code());
							fileListDO.setRawAddTime(now);
							fileInputListDAO.insert(fileListDO);
						}

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
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
							"result");
					result.setKeyId(fileId);
				} else {
					//更新
					FFileDO fileDO = fileDAO.findById(order.getFileId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未找到入库记录");
					}
					order.setFileCode(fileDO.getFileCode());
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setType(order.getCurrType().code());
					fileDAO.update(fileDO);
					List<FFileInputListDO> inDataBaseList = fileInputListDAO.findByFileId(fileDO
							.getFileId());
					Map<Long, String> inDataBaseMap = new HashMap<Long, String>();
					for (FFileInputListDO DO : inDataBaseList) {
						inDataBaseMap.put(DO.getInputListId(), DO.getFileName());
					}
					//更新文件
					for (FileInputListOrder fileListOrder : order.getFileListOrder()) {
						if (fileListOrder.getInputListId() <= 0) {//不存在就新增
							final Date now = FcsPmDomainHolder.get().getSysDate();
							FFileInputListDO fileListDO = new FFileInputListDO();
							BeanCopier.staticCopy(fileListOrder, fileListDO,
									UnBoxingConverter.getInstance());
							fileListDO.setFileId(order.getFileId());
							fileListDO.setRawAddTime(now);
							//	fileListDO.setAttachType(fileListOrder.getAttachType().code());
							fileInputListDAO.insert(fileListDO);
						} else {
							FFileInputListDO fileInputListDO = fileInputListDAO
									.findById(fileListOrder.getInputListId());
							if (null == fileInputListDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
										"未找到文件目录记录");
							}

							fileInputListDO.setArchiveFileName(fileListOrder.getArchiveFileName());
							fileInputListDO.setFilePage(fileListOrder.getFilePage());
							fileInputListDO.setFilePath(fileListOrder.getFilePath());
							fileInputListDO.setInputRemark(fileListOrder.getInputRemark());
							fileInputListDO.setFileId(order.getFileId());
							//fileInputListDO.setAttachType(fileListOrder.getAttachType().code());
							fileInputListDAO.update(fileInputListDO);
							inDataBaseMap.remove(fileInputListDO.getInputListId());
						}

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

	@Override
	public FormBaseResult saveOuput(final FileOutputOrder order) {
		return commonFormSaveProcess(order, " 申请出库", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (null == order.getId() || order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileOutputDO fileDO = new FFileOutputDO();
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setFormId(formInfo.getFormId());
					fileDO.setRawAddTime(now);
					fileOutputDAO.insert(fileDO);
				} else {
					//更新
					FFileOutputDO fileDO = fileOutputDAO.findById(order.getId());
					if (null == fileDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未找到出库记录");
					}
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileOutputDAO.update(fileDO);
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
				if (order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FFileBorrowDO fileDO = new FFileBorrowDO();
					BeanCopier.staticCopy(order, fileDO, UnBoxingConverter.getInstance());
					fileDO.setFormId(formInfo.getFormId());
					fileDO.setRawAddTime(now);
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
				return null;
			}
		}, null, null);
	}

}
