package com.born.fcs.face.web.controller.project.file;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.fcs.pm.ws.info.file.*;
import com.born.fcs.pm.ws.order.file.*;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.filesys.E6ApiService;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FileAttachTypeEnum;
import com.born.fcs.pm.ws.enums.FileFormEnum;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("projectMg/file")
public class FileController extends WorkflowBaseController {

	@Autowired
	E6ApiService e6ApiService;
	
	final static String vm_path = "/projectMg/assistSys/recordManage/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "filingTime", "receiveTime", "handOverTime", "firstLoanTime",
								"applyTime", "expectReturnTime", "startTime", "endTime","extensionDate" };
	}
	
	/**
	 * 档案目录
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("fileCatalogMg.htm")
	public String archiveManagement(String type, String doing, HttpServletRequest request,
									HttpServletResponse response, Model model) {
		List<FileListInfo> info = Lists.newArrayList();
		if (type == null) {
			type = FileTypeEnum.CREDIT_BUSSINESS.code();
			info = fileServiceClient.findByType(FileTypeEnum.CREDIT_BUSSINESS.code());
		} else {
			info = fileServiceClient.findByType(type);
		}
		Map<String, Integer> sizeMap = new HashMap<String, Integer>();
		if (info != null && info.size() > 0) {
			String fileType = "";
			for (int i = 0; i < info.size(); i++) {
				if (info.get(i).getFileType() != null) {
					if (i == 0 || !fileType.equals(info.get(i).getFileType())) {
						fileType = info.get(i).getFileType();
						sizeMap.put(info.get(i).getFileType(), 0);
					}
					sizeMap.put(info.get(i).getFileType(),
						sizeMap.get(info.get(i).getFileType()) + 1);
					
				}
			}
		}
		String checkStatus = fileServiceClient.findCheckStatus("FILE_CATALOG_MG");
		if (checkStatus == null || checkStatus.equals("")) {
			checkStatus = "22222";
		}
		model.addAttribute("type", type);
		model.addAttribute("currType", type);
		model.addAttribute("sizeMap", sizeMap);
		model.addAttribute("checkStatus", checkStatus);
		model.addAttribute("info", info);
		model.addAttribute("doing", doing);
		model.addAttribute("Catalog", true);
		model.addAttribute("catalogHasBase", true);
		model.addAttribute("catalogHasManagment", true);
		model.addAttribute("catalogHasTitle", true);
		return vm_path + "archiveManagement.vm";
	}
	
	public boolean isCanInput(String projectCode, FileTypeEnum type, Date startTime, Date endTime,
								Long formId) {
		FileQueryOrder queryOrder = new FileQueryOrder();
		queryOrder.setPageSize(999);
		queryOrder.setApplyType("入库申请");
		queryOrder.setFileType(type.code());
		queryOrder.setProjectCode(projectCode);
		queryOrder.setStartTime(startTime);
		queryOrder.setEndTime(endTime);
		setSessionLocalInfo2Order(queryOrder);
		if (formId != null && formId > 0) {
			boolean result = false;
			QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.query(queryOrder);
			if (batchResult.getPageList() != null && batchResult.getPageList().size() > 0) {
				for (FileFormInfo formInfo : batchResult.getPageList()) {
					if (formInfo.getFormId() == formId) {
						result = true;
						break;
					}
				}
			} else {
				return true;
			}
			return result;
		} else {
			QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.query(queryOrder);
			if (batchResult.getPageList() != null && batchResult.getPageList().size() > 0) {
				return false;
			}
		}
		return true;
	}
	
	public FileFormInfo getFfileCode(String projectCode, FileTypeEnum type) {
		FileQueryOrder queryOrder = new FileQueryOrder();
		queryOrder.setPageSize(999);
		queryOrder.setApplyType("入库申请");
		queryOrder.setFileType(type.code());
		queryOrder.setProjectCode(projectCode);
		setSessionLocalInfo2Order(queryOrder);
		QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.query(queryOrder);
		if (batchResult.getPageList() != null && batchResult.getPageList().size() > 0) {
			return batchResult.getPageList().get(0);
		}
		return null;
	}
	/**
	 * 项目移交后项目客户经理也法务经理是同一个人
	 * @return
	 */
	private boolean isLegalManagerAndBusiManager(String projectCode){
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		if(DataPermissionUtil.isLegalManager()&&sessionLocal.getUserId()==projectInfo.getBusiManagerId()) {
			return true;
		}
		return false;
	}
	/**
	 * 申请入库
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("inputApply.htm")
	public String inputApply(String type, String doing, Long formId, String projectCode,String copyFormId,
								HttpServletRequest request, HttpServletResponse response,
								Model model) {
		FileInfo info = new FileInfo();
		List<FileInputListInfo> inputListInfos = Lists.newArrayList();
		boolean isCanArhiveBase = true;//基础卷
		boolean isCanArhiveManagment = true;//授信后管理卷
		boolean isCanArhiveTitle = true;//权利凭证卷
		boolean hasRiskCommon = true;//风险常规卷
		boolean hasRiskLawsuit = true;//风险诉讼卷
		//是否第一次申请
		boolean isFirstBase = true;
		boolean isFirstManagment = true;
		boolean isFirstTitle = true;
		boolean isFirstCommon = true;
		boolean isFirstLawsuit = true;
		boolean isXinhui = DataPermissionUtil.isXinHuiBusiManager();//是否信惠

		if (projectCode != null && formId == null) { //新增
			boolean isLegalManagerAndBusiManager=isLegalManagerAndBusiManager(projectCode);//项目移交后法务经理能操作客户经理的卷
			String oldType = type;
			if (type == null) {
				if(DataPermissionUtil.isLoanFile()){//放款档案岗没有风险诉讼卷，风险常规卷
					type = FileTypeEnum.CREDIT_BUSSINESS.code();
					hasRiskLawsuit=false;
					hasRiskCommon=false;
					if(DataPermissionUtil.isLegalManager(projectCode)){
						hasRiskLawsuit=true;
					}
					if(DataPermissionUtil.isBusiManager(projectCode)){
						hasRiskCommon=true;
					}
				}else {
					if (DataPermissionUtil.isBusiManager()) {//业务经理
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
						hasRiskLawsuit = false;
						isCanArhiveManagment = false;
						isCanArhiveTitle = false;
					} else if (isLegalManagerAndBusiManager) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
						isCanArhiveManagment = false;
						isCanArhiveTitle = false;
					} else if (DataPermissionUtil.isRiskManager()) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
						isCanArhiveManagment = false;
						isCanArhiveTitle = false;
						hasRiskCommon = false;
						hasRiskLawsuit = false;
					} else {//法务经理
						type = FileTypeEnum.RISK_LAWSUIT.code();
						isCanArhiveBase = false;
						isCanArhiveManagment = false;
						isCanArhiveTitle = false;
						hasRiskCommon = false;
					}
				}
			}
			//----------------------------------非信惠开始-------------------------------
			//非风险卷只能归档一次，有就不能归档
			//2016年10月10日14:22:55 基础卷和权证卷一次性全部归档以后不能再次归档的控制放开
			if (!isXinhui) {
				if (DataPermissionUtil.isBusiManager()||isLegalManagerAndBusiManager) {//业务经理
					isFirstBase = isCanInput(projectCode, FileTypeEnum.CREDIT_BUSSINESS, null,
						null, formId);
					//基础卷、权利凭证卷 一个项目只有一个编号
					if (!isFirstBase && type.equals(FileTypeEnum.CREDIT_BUSSINESS.code())) {
						FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.CREDIT_BUSSINESS);
						info.setFileCode(fileFormInfo.getFileCode());
						info.setOldFileCode(fileFormInfo.getOldFileCode());
					}
					//2017年3月16日18:01:54 一季度归一次限制去掉
//					isCanArhiveManagment = isCanInput(projectCode,
//						FileTypeEnum.CREDIT_BEFORE_MANAGEMENT,
//						DateUtil.getCurrentQuarterStartTime(), DateUtil.getCurrentQuarterEndTime(),
//						formId);
					isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE, null,
						null, formId);
					if (!isFirstTitle && type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code())) {
						FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE);
						info.setFileCode(fileFormInfo.getFileCode());
						info.setOldFileCode(fileFormInfo.getOldFileCode());
					}
					hasRiskCommon = isCanInput(projectCode, FileTypeEnum.RISK_COMMON,
						DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(),
						formId);
				}
				if(isLegalManagerAndBusiManager||DataPermissionUtil.isLegalManager()){//法务经理只有风险诉讼卷
					hasRiskLawsuit = isCanInput(projectCode, FileTypeEnum.RISK_LAWSUIT,
						DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(),
						formId);
				}
				if (oldType == null) {
					if (isCanArhiveBase) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
					} else if (isCanArhiveManagment) {
						type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
					} else if (isCanArhiveTitle) {
						type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
					} else if (hasRiskCommon) {
						type = FileTypeEnum.RISK_COMMON.code();
					} else if (hasRiskLawsuit) {
						type = FileTypeEnum.RISK_LAWSUIT.code();
					} else {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"当前时间没有可以归档的卷");
					}
				}
				//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
				//风险处置卷多次归档，第N次归档时将第N-1次归档的文件带出来
//				if (type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())
//					|| type.equals(FileTypeEnum.RISK_COMMON.code())
//					|| type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
//					List<FileInputListInfo> archivedList = fileServiceClient
//						.searchArchivedByProjectCode(0, type, projectCode, null, null);
//					model.addAttribute("archivedList", archivedList);
//				}
				//基础卷和权证卷下次归档时不需要读取归档目录的内容，纯自由添加行，可以添加目录，但是目录不回填到归档文件目录管理。
				if ((type.equals(FileTypeEnum.CREDIT_BUSSINESS.code()) && isFirstBase)
					|| (type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code()) && isFirstTitle)
					|| type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())
					|| type.equals(FileTypeEnum.RISK_COMMON.code())
					|| type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
					List<FileListInfo> listInfos = Lists.newArrayList();
					listInfos = fileServiceClient.searchNotArchiveByProjectCode(0, type,
						projectCode);
					if (listInfos != null) {
						for (FileListInfo info2 : listInfos) {
							FileInputListInfo inputListInfo = new FileInputListInfo();
							BeanCopier.staticCopy(info2, inputListInfo);
							inputListInfos.add(inputListInfo);
						}
					}
				}
				//----------------------------------非信惠结束-------------------------------
			} else {
				//----------------------------------信惠开始---------------------------------
				//信惠不限制归档时间
				if (DataPermissionUtil.isBusiManager()||isLegalManagerAndBusiManager) {//业务经理
					isFirstBase = isCanInput(projectCode, FileTypeEnum.CREDIT_BUSSINESS, null,
						null, formId);
					//基础卷、权利凭证卷 一个项目只有一个编号
					if (!isFirstBase && type.equals(FileTypeEnum.CREDIT_BUSSINESS.code())) {
						FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.CREDIT_BUSSINESS);
						info.setFileCode(fileFormInfo.getFileCode());
						info.setOldFileCode(fileFormInfo.getOldFileCode());
					}
					isFirstManagment = isCanInput(projectCode,
						FileTypeEnum.CREDIT_BEFORE_MANAGEMENT, null, null, formId);
					isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE, null,
						null, formId);
					if (!isFirstTitle && type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code())) {
						FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE);
						info.setFileCode(fileFormInfo.getFileCode());
						info.setOldFileCode(fileFormInfo.getOldFileCode());
					}
					//  hasRiskCommon = isCanInput(projectCode, FileTypeEnum.RISK_COMMON, null, null, formId);
				} else {//法务经理只有风险诉讼卷
					//  hasRiskLawsuit = isCanInput(projectCode, FileTypeEnum.RISK_LAWSUIT, null, null, formId);
				}
				if (oldType == null) {
					if (isCanArhiveBase) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
					} else if (isCanArhiveManagment) {
						type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
					} else if (isCanArhiveTitle) {
						type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
					} else if (hasRiskCommon) {
						type = FileTypeEnum.RISK_COMMON.code();
					} else if (hasRiskLawsuit) {
						type = FileTypeEnum.RISK_LAWSUIT.code();
					} else {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"当前时间没有可以归档的卷");
					}
				}
				//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
				//信惠的所有卷，第N次归档时将第N-1次归档的文件带出来
//				List<FileInputListInfo> archivedList = fileServiceClient
//					.searchArchivedByProjectCode(0, type, projectCode, null, null);
//				model.addAttribute("archivedList", archivedList);
				//基础卷和权证卷下次归档时不需要读取归档目录的内容，纯自由添加行，可以添加目录，但是目录不回填到归档文件目录管理。
				               if ((type.equals(FileTypeEnum.CREDIT_BUSSINESS.code()) && isFirstBase) || (type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code()) && isFirstTitle) || (type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())&&isFirstManagment) || type.equals(FileTypeEnum.RISK_COMMON.code()) || type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
				List<FileListInfo> listInfos = Lists.newArrayList();
				listInfos = fileServiceClient.searchNotArchiveByProjectCode(0, type, projectCode);
				if (listInfos != null) {
					for (FileListInfo info2 : listInfos) {
						FileInputListInfo inputListInfo = new FileInputListInfo();
						BeanCopier.staticCopy(info2, inputListInfo);
						inputListInfos.add(inputListInfo);
					}
				}
				                }
			}
			//----------------------------------信惠结束---------------------------------
		}
		//formId不为空先通过formId和type去查FileInfo
		else if (projectCode != null || null != formId) {//已存在入库单 编辑
			if (formId != 0) {
				FormInfo formInfo = formServiceClient.findByFormId(formId);
				boolean isLegalManagerAndBusiManager=isLegalManagerAndBusiManager(formInfo.getRelatedProjectCode());//项目移交后法务经理能操作客户经理的卷
				model.addAttribute("form", formInfo);
				if (DataPermissionUtil.isBusiManager()) {
					hasRiskLawsuit = false;
				} else if(DataPermissionUtil.isLegalManager()&&!isLegalManagerAndBusiManager){
					type = FileTypeEnum.RISK_LAWSUIT.code();
					//                    isCanArhiveBase=false;
					isCanArhiveManagment = false;
					//                    isCanArhiveTitle=false;
					hasRiskCommon = false;
				}
				
				if (!isXinhui) { //--------------------------------非信惠开始---------------------------
					//判断是否能归档
					if (DataPermissionUtil.isBusiManager()||isLegalManagerAndBusiManager) {
						isFirstBase = isCanInput(projectCode, FileTypeEnum.CREDIT_BUSSINESS, null,
							null, formId);
						//2017年3月16日18:01:54 一季度归一次限制去掉
//						isCanArhiveManagment = isCanInput(projectCode,
//							FileTypeEnum.CREDIT_BEFORE_MANAGEMENT,
//							DateUtil.getCurrentQuarterStartTime(),
//							DateUtil.getCurrentQuarterEndTime(), formId);
						isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE,
							null, null, formId);
						hasRiskCommon = isCanInput(projectCode, FileTypeEnum.RISK_COMMON,
							DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(),
							formId);
					}
					if(isLegalManagerAndBusiManager||DataPermissionUtil.isLegalManager()){
						hasRiskLawsuit = isCanInput(projectCode, FileTypeEnum.RISK_LAWSUIT,
							DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(),
							formId);
					}
					if (type == null) {
						if (isCanArhiveBase) {
							type = FileTypeEnum.CREDIT_BUSSINESS.code();
						} else if (isCanArhiveManagment) {
							type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
						} else if (isCanArhiveTitle) {
							type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
						} else if (hasRiskCommon) {
							type = FileTypeEnum.RISK_COMMON.code();
						} else if (hasRiskLawsuit) {
							type = FileTypeEnum.RISK_LAWSUIT.code();
						} else {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"当前时间没有可以归档的卷");
						}
					}
					info = fileServiceClient.findByFormId(formId, type);
					if (info.getFileId()==0) {//当前类型的数据不存在 带出该项目没有在审核的数据
						info = new FileInfo();
						//基础卷、权利凭证卷 一个项目只有一个编号
						if (!isFirstBase && type.equals(FileTypeEnum.CREDIT_BUSSINESS.code())) {
							FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.CREDIT_BUSSINESS);
							info.setFileCode(fileFormInfo.getFileCode());
							info.setOldFileCode(fileFormInfo.getOldFileCode());
						}
						isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE, null,
								null, formId);
						if (!isFirstTitle && type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code())) {
							FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE);
							info.setFileCode(fileFormInfo.getFileCode());
							info.setOldFileCode(fileFormInfo.getOldFileCode());
						}
						//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
						//风险处置卷多次归档，第N次归档时将第N-1次归档的文件带出来
//						if (type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())
//							|| type.equals(FileTypeEnum.RISK_COMMON.code())
//							|| type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
//							List<FileInputListInfo> archivedList = fileServiceClient
//								.searchArchivedByProjectCode(formId, type, projectCode, null, null);
//							model.addAttribute("archivedList", archivedList);
//						}
						//基础卷和权证卷下次归档时不需要读取归档目录的内容，纯自由添加行，可以添加目录，但是目录不回填到归档文件目录管理。
						if ((type.equals(FileTypeEnum.CREDIT_BUSSINESS.code()) && isFirstBase)
							|| (type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code()) && isFirstTitle)
							|| type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())
							|| type.equals(FileTypeEnum.RISK_COMMON.code())
							|| type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
							List<FileListInfo> listInfos = Lists.newArrayList();
							listInfos = fileServiceClient.searchNotArchiveByProjectCode(0, type,
								projectCode);
							if(listInfos!=null) {
								for (FileListInfo info2 : listInfos) {
									FileInputListInfo inputListInfo = new FileInputListInfo();
									BeanCopier.staticCopy(info2, inputListInfo);
									inputListInfos.add(inputListInfo);
								}
							}
						}
					} else {
						inputListInfos = info.getFileListInfos();//本在单子的数据
						if (inputListInfos == null) {
							inputListInfos = Lists.newArrayList();
						}
						//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
//						if (type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())
//							|| type.equals(FileTypeEnum.RISK_COMMON.code())
//							|| type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
//							List<FileInputListInfo> archivedList = fileServiceClient
//								.searchArchivedByProjectCode(formId, type, projectCode, null, null);
//							model.addAttribute("archivedList", archivedList);
//						}
						//不在本张申请单中且该项目没有在审核的数据
						//2017-2-7 14:45:21 暂存保存后不带目录数据
//						List<FileListInfo> listInfos = fileServiceClient
//							.searchNotArchiveByProjectCode(formId, type, projectCode);
//						if (listInfos != null) {
//							if (listInfos.size() > 0) {
//								for (FileListInfo listInfo : listInfos) {
//									FileInputListInfo newListInfo = new FileInputListInfo();
//									newListInfo.setId(listInfo.getId());
//									newListInfo.setFileType(listInfo.getFileType());
//									newListInfo.setFileName(listInfo.getFileName());
//									inputListInfos.add(newListInfo);
//								}
//							}
//						}
					} //--------------------------------非信惠结束---------------------------
				} else {//--------------------------------信惠开始---------------------------
						//判断是否能归档
					if (DataPermissionUtil.isBusiManager()) {
						isFirstBase = isCanInput(projectCode, FileTypeEnum.CREDIT_BUSSINESS, null,
							null, formId);
						isFirstManagment = isCanInput(projectCode,
							FileTypeEnum.CREDIT_BEFORE_MANAGEMENT, null, null, formId);
						isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE,
							null, null, formId);
						// hasRiskCommon = isCanInput(projectCode, FileTypeEnum.RISK_COMMON, null, null, formId);
					} else {
						// hasRiskLawsuit = isCanInput(projectCode, FileTypeEnum.RISK_LAWSUIT,null, null, formId);
					}
					
					if (type == null) {
						if (isCanArhiveBase) {
							type = FileTypeEnum.CREDIT_BUSSINESS.code();
						} else if (isCanArhiveManagment) {
							type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
						} else if (isCanArhiveTitle) {
							type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
						} else if (hasRiskCommon) {
							type = FileTypeEnum.RISK_COMMON.code();
						} else if (hasRiskLawsuit) {
							type = FileTypeEnum.RISK_LAWSUIT.code();
						} else {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"当前时间没有可以归档的卷");
						}
					}
					
					info = fileServiceClient.findByFormId(formId, type);
					if (info.getFileId()==0) {//当前类型的数据不存在 带出该项目没有在审核的数据
						info = new FileInfo();
						//基础卷、权利凭证卷 一个项目只有一个编号
						if (!isFirstBase && type.equals(FileTypeEnum.CREDIT_BUSSINESS.code())) {
							FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.CREDIT_BUSSINESS);
							info.setFileCode(fileFormInfo.getFileCode());
							info.setOldFileCode(fileFormInfo.getOldFileCode());
						}
						isFirstTitle = isCanInput(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE, null,
								null, formId);
						if (!isFirstTitle && type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code())) {
							FileFormInfo fileFormInfo=getFfileCode(projectCode, FileTypeEnum.DOCUMENT_OF_TITLE);
							info.setFileCode(fileFormInfo.getFileCode());
							info.setOldFileCode(fileFormInfo.getOldFileCode());
						}
						//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
						//信惠的所有卷，第N次归档时将第N-1次归档的文件带出来
//						List<FileInputListInfo> archivedList = fileServiceClient
//							.searchArchivedByProjectCode(formId, type, projectCode, null, null);
//						model.addAttribute("archivedList", archivedList);
						//基础卷和权证卷下次归档时不需要读取归档目录的内容，纯自由添加行，可以添加目录，但是目录不回填到归档文件目录管理。
						if (
						//(type.equals(FileTypeEnum.CREDIT_BUSSINESS.code()) && isFirstBase) || (type.equals(FileTypeEnum.DOCUMENT_OF_TITLE.code()) && isFirstTitle) || (type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code())&&isFirstManagment) || type.equals(FileTypeEnum.RISK_COMMON.code()) || type.equals(FileTypeEnum.RISK_LAWSUIT.code())
						true) {
							List<FileListInfo> listInfos = Lists.newArrayList();
							listInfos = fileServiceClient.searchNotArchiveByProjectCode(0, type,
								projectCode);
							if(listInfos!=null){
							for (FileListInfo info2 : listInfos) {
								FileInputListInfo inputListInfo = new FileInputListInfo();
								BeanCopier.staticCopy(info2, inputListInfo);
								inputListInfos.add(inputListInfo);
							}
							}
						}
					} else {
						inputListInfos = info.getFileListInfos();//本在单子的数据
						if (inputListInfos == null) {
							inputListInfos = Lists.newArrayList();
						}
						//2017年4月11日10:48:50改为归档时不自动带出前一次保后归档的内容
//						if (
//						//type.equals(FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code()) || type.equals(FileTypeEnum.RISK_COMMON.code()) || type.equals(FileTypeEnum.RISK_LAWSUIT.code())) {
//						true) {
//							List<FileInputListInfo> archivedList = fileServiceClient
//								.searchArchivedByProjectCode(formId, type, projectCode, null, null);
//							model.addAttribute("archivedList", archivedList);
//						}
						//不在本张申请单中且该项目没有在审核的数据
						//2017-2-7 14:45:21 暂存保存后不带目录数据
//						List<FileListInfo> listInfos = fileServiceClient
//							.searchNotArchiveByProjectCode(formId, type, projectCode);
//						if (listInfos != null) {
//							if (listInfos.size() > 0) {
//								for (FileListInfo listInfo : listInfos) {
//									FileInputListInfo newListInfo = new FileInputListInfo();
//									newListInfo.setId(listInfo.getId());
//									newListInfo.setFileType(listInfo.getFileType());
//									newListInfo.setFileName(listInfo.getFileName());
//									inputListInfos.add(newListInfo);
//								}
//							}
//						}
					}
					//--------------------------------信惠结束---------------------------
				}
				
			}
			
		} else {
			model.addAttribute("noProjectCode", true);
		}
		if (projectCode != null) {//放用款时间
			//2016-11-17 放用款回执变更
			List<FLoanUseApplyReceiptInfo> receipts = loanUseApplyServiceClient
				.queryReceipt(projectCode);
			if (ListUtil.isNotEmpty(receipts)) {
				Date firstLoanTime = null;
				for (FLoanUseApplyReceiptInfo receipt : receipts) {
					if (firstLoanTime == null || firstLoanTime.after(receipt.getActualLoanTime()))
						firstLoanTime = receipt.getActualLoanTime();
				}
				info.setFirstLoanTime(firstLoanTime);
			}
		}
		if (projectCode != null) {
			ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
			info.setProjectCode(projectCode);
			info.setProjectName(projectInfo.getProjectName());
			info.setCustomerId(projectInfo.getCustomerId());
			info.setCustomerName(projectInfo.getCustomerName());
		}
		String checkStatus = "22222";
		if (formId != null) {
			checkStatus = fileServiceClient.findCheckStatus(Long.toString(formId));
		}
		//权限处理
		if(DataPermissionUtil.isLoanFile()){//放款档案岗没有风险诉讼卷，风险常规卷
			hasRiskLawsuit=false;
			hasRiskCommon=false;
			//并且是业务经理
			if(DataPermissionUtil.isLegalManager(projectCode)){
				hasRiskLawsuit=true;
			}
			if(DataPermissionUtil.isBusiManager(projectCode)){
				hasRiskCommon=true;
			}
		}else {//不是放款档案岗
			if (DataPermissionUtil.isBusiManager()) {//业务经理
				hasRiskLawsuit = false;
				isCanArhiveManagment = false;
				isCanArhiveTitle = false;
			} else if (DataPermissionUtil.isRiskManager()) {
				isCanArhiveManagment = false;
				isCanArhiveTitle = false;
				hasRiskCommon = false;
				hasRiskLawsuit = false;
			} else if (DataPermissionUtil.isBusiManager(projectCode) && DataPermissionUtil.isLegalManager(projectCode)) {
				isCanArhiveManagment = false;
				isCanArhiveTitle = false;
			} else {//法务经理
				isCanArhiveBase = false;
				isCanArhiveManagment = false;
				isCanArhiveTitle = false;
				hasRiskCommon = false;
			}
		}
		info.setFileListInfos(inputListInfos);
		if(StringUtil.isNotBlank(copyFormId)&&Long.parseLong(copyFormId)>0){//复制历史单据
			FileInfo hisInfo=fileServiceClient.findByFormId(Long.parseLong(copyFormId), type);
			List<FileInputListInfo> fileListInfos=Lists.newArrayList();
			if(ListUtil.isNotEmpty(hisInfo.getFileListInfos())){
			for(FileInputListInfo listInfos:hisInfo.getFileListInfos()){
				FileInputListInfo listInfo=new FileInputListInfo();
				listInfo.setFileType(listInfos.getFileType());
				listInfo.setAttachType(listInfos.getAttachType());
				listInfo.setArchiveFileName(listInfos.getArchiveFileName());
				listInfo.setInputRemark(listInfos.getInputRemark());
				listInfo.setFilePage(listInfos.getFilePage());
				fileListInfos.add(listInfo);
				}
			}
			info.setFileListInfos(fileListInfos);
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		model.addAttribute("userName", sessionLocal.getRealName());
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", checkStatus);
		model.addAttribute("info", info);
		model.addAttribute("doing", doing);
		model.addAttribute("type", type);
		model.addAttribute("currType", type);
		model.addAttribute("formId", formId);
		model.addAttribute("edit", true);
		model.addAttribute("attachType", FileAttachTypeEnum.getAllEnum());
		model.addAttribute("isCanArhiveBase", isCanArhiveBase);
		model.addAttribute("isCanArhiveManagment", isCanArhiveManagment);
		model.addAttribute("isCanArhiveTitle", isCanArhiveTitle);
		model.addAttribute("hasRiskCommon", hasRiskCommon);
		model.addAttribute("hasRiskLawsuit", hasRiskLawsuit);
		return vm_path + "bePutInStorage.vm";
	}
	
	/**
	 * 保存归档文件目录
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("saveFileCatalog.htm")
	@ResponseBody
	public JSONObject saveFileCatalog(FileCatalogOrder order, Model model) {
		String tipPrefix = " 归档文件目录";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// order.setBusiType(projectInfo.getBusiType());
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = fileServiceClient.save(order);
			String checkStatus = fileServiceClient.findCheckStatus("FILE_CATALOG_MG");
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("submitStatus", order.getSubmitStatus());
			jsonObject.put("type", order.getType());
			jsonObject.put("checkStatus", checkStatus);
			jsonObject.put("doing", order.getDoing());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存归档文件目录出错", e);
		}
		return jsonObject;
	}
	
	/**
	 * 保存入库申请
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveInputApply.htm")
	@ResponseBody
	public JSONObject saveInputApply(FileOrder order, Model model) {
		String tipPrefix = " 保存入库申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setFileCheckStatus(order.getCheckStatus().toString());
			if (DataPermissionUtil.isBelong2Xinhui() && "Y".equals(order.getSubmitStatus())) {
				order.setCheckStatus(1);
			} else {
				boolean checkStatusFlag = true;
				String checkStatusStr = order.getCheckStatus().toString();
				for (int i = 0; i < checkStatusStr.length(); i++) {
					if ('0' == checkStatusStr.charAt(i)) {
						checkStatusFlag = false;
						break;
					}
				}
				if (checkStatusFlag) {
					order.setCheckStatus(1);
				} else {
					order.setCheckStatus(0);
				}
			}
			//order.setCheckStatus(1);
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setXinhui(DataPermissionUtil.isBelong2Xinhui());
			order.setFormCode(FormCodeEnum.FILE_INPUT_APPLY);
			setSessionLocalInfo2Order(order);
			ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(),
				false);
			order.setBusiType(projectInfo.getBusiType());
			FormBaseResult result = fileServiceClient.saveInput(order);
			jsonObject.put("success", true);
			jsonObject.put("message", "保存成功");
			String checkStatus = fileServiceClient.findCheckStatus(Long.toString(result
				.getFormInfo().getFormId()));
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("status", order.getStatus());
			jsonObject.put("formId", result.getFormInfo().getFormId());
			jsonObject.put("type", order.getType());
			jsonObject.put("checkStatus", checkStatus);
			jsonObject.put("doing", order.getDoing());
			jsonObject.put("submitStatus", order.getSubmitStatus());
			jsonObject.put("fileId", result.getKeyId());
			jsonObject.put("projectCode", order.getProjectCode());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存入库申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 档案入库列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String FileInputList(FileQueryOrder order, Model model) {
//		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setIsFileAdmin(BooleanEnum.NO);
		if (DataPermissionUtil.isFileAdministrator() || DataPermissionUtil.isBelongNK()
			|| DataPermissionUtil.isSystemAdministrator()) {//档案管理员或者内控的有所有数据的权限
			order.setIsFileAdmin(BooleanEnum.IS);
		}
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.query(order);
		if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (FileFormInfo fileInfo : batchResult.getPageList()) {
				FileBorrowInfo info = fileServiceClient.findByBorrowFormId(fileInfo.getFormId());
				if (null != info) {
					fileInfo.setIsBatch(info.getIsBatch());
					fileInfo.setFileIds(info.getFileIds());
					if ("IS".equals(info.getIsBatch()) || "YES".equals(info.getIsBatch())) {
						fileInfo.setProjectCode(info.getProjectCode());
						fileInfo.setCustomerName(info.getCustomerName());
						fileInfo.setFileCode(info.getFileCode());
						fileInfo.setOldFileCode(info.getOldFileCode());
					}
				}
			}
		}
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("fileTypeEnum",FileTypeEnum.getAllEnum());
		return vm_path + "frequentationList.vm";
	}
	
	@RequestMapping("checkList.htm")
	public String checkList(HttpServletRequest request, FileBatchQueryOrder order, Model model) {
		String xinhuiDeptCode = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code());
		order.setXinhuiDeptCode(xinhuiDeptCode);
		order.setIsFileAdmin(BooleanEnum.NO);
		order.setIsXinHui(BooleanEnum.NO);
		if (DataPermissionUtil.isFileAdministrator() || DataPermissionUtil.isSystemAdministrator()) {//档案管理员或者内控的有所有数据的权限
			order.setIsFileAdmin(BooleanEnum.IS);
		}
		setSessionLocalInfo2Order(order);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setApplyManId(sessionLocal.getUserId());
		String fileStartTime = request.getParameter("fileStartTimeStr");
		if (StringUtil.isNotBlank(fileStartTime)) {
			order.setStartTime(DateUtil.parse(fileStartTime));
			model.addAttribute("fileStartTimeStr", fileStartTime);
		}
		String fileEndTime = request.getParameter("fileEndTimeStr");
		if (StringUtil.isNotBlank(fileEndTime)) {
			order.setEndTime(DateUtil.parse(fileEndTime));
			model.addAttribute("fileEndTimeStr", fileEndTime);
		}
		
		QueryBaseBatchResult<FileInfo> batchResult = fileServiceClient.queryFiles(order);
		model.addAttribute("conditions", order);
		model.addAttribute("detailCanView", canView());
		model.addAttribute("canApplyView", canApplyView());
		model.addAttribute("status", FileStatusEnum.getAllEnum());
		model.addAttribute("types", FileTypeEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
//		model.addAttribute("map", listPermission(batchResult.getPageList()));
		return vm_path + "checkList.vm";
	}
	
	@RequestMapping("checkApply.json")
	@ResponseBody
	public JSONObject checkApply(FileBatchApplyOrder order, Model model) {
		String tipPrefix = " 申请验证";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = fileServiceClient.checkApply(order);
			
			jsonObject = toJSONResult(jsonObject, result, "验证通过", null);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);			
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 档案一览表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("viewList.htm")
	public String viewList(FileQueryOrder order, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FileViewInfo> batchResult = fileServiceClient.fileViewList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "pandectList.vm";
	}
	
	/**
	 * 档案明细列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("detailFileList.htm")
	public String detailFileList(FileQueryOrder order, Model model) {
		String xinhuiDeptCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code());
		order.setXinhuiDeptCode(xinhuiDeptCode);
		order.setIsFileAdmin(BooleanEnum.NO);
		order.setIsXinHui(BooleanEnum.NO);
		if (DataPermissionUtil.isFileAdministrator() || DataPermissionUtil.isSystemAdministrator()) {//档案管理员或者内控的有所有数据的权限
			order.setIsFileAdmin(BooleanEnum.IS);
		}
		setSessionLocalInfo2Order(order);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setApplyManId(sessionLocal.getUserId());
		QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.fileDetailList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("detailCanView", canView());
		model.addAttribute("canApplyView", canApplyView());
		model.addAttribute("status", FileStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("map", listPermission(batchResult.getPageList()));
		model.addAttribute("now",DateUtil.dtSimpleFormat(new Date()));
		return vm_path + "detailFileList.vm";
	}
	
	/**
	 * 信惠档案明细列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("XHDetailFileList.htm")
	public String XHDetailFileList(FileQueryOrder order, Model model) {
		String xinhuiDeptCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code());
		order.setXinhuiDeptCode(xinhuiDeptCode);
		order.setIsXinHui(BooleanEnum.IS);
		order.setIsFileAdmin(BooleanEnum.NO);
		if (DataPermissionUtil.isFileAdministrator() || DataPermissionUtil.isBelongNK()
			|| DataPermissionUtil.isSystemAdministrator()) {//档案管理员或者内控的有所有数据的权限
			order.setIsFileAdmin(BooleanEnum.IS);
		}
		setSessionLocalInfo2Order(order);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setApplyManId(sessionLocal.getUserId());
		QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.fileDetailList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("detailCanView", canView());
		model.addAttribute("canApplyView", canApplyView());
		model.addAttribute("status", FileStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("map", listPermission(batchResult.getPageList()));
		model.addAttribute("isXinhuiList", true);
		return vm_path + "detailFileList.vm";
	}
	
	/**
	 * 文档入库编辑
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("inputEdit.htm")
	public String editInput(Long formId, String type, Model model) {
		String tipPrefix = "文档入库编辑";
		try {
			if (type == null) {
				type = FileTypeEnum.CREDIT_BUSSINESS.code();
			}
			FormInfo form = formServiceClient.findByFormId(formId);
			FileInfo info = fileServiceClient.findByFormId(formId, type);
			model.addAttribute("info", info);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "addQuery.vm";
	}
	
	/**
	 * 入库审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String type, HttpServletRequest request, Model model,
						HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		String status = fileServiceClient.findCheckStatus(Long.toString(formId));
		if (type == null) {
			for (int i = 0; i < status.length(); i++) {
				if (!('2' == status.charAt(i))) {
					if (i == 0) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
						break;
					}
					if (i == 1) {
						type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
						break;
					}
					if (i == 2) {
						type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
						break;
					}
					if (i == 3) {
						type = FileTypeEnum.RISK_COMMON.code();
						break;
					}
					if (i == 4) {
						type = FileTypeEnum.RISK_LAWSUIT.code();
						break;
					}
				}
			}
		}
		FileInfo info = fileServiceClient.findByFormId(formId, type);
		boolean hasBase = false;//基础卷
		boolean hasManagment = false;//授信后管理卷
		boolean hasTitle = false;//权利凭证卷
		boolean hasRiskCommon = false;//权利凭证卷
		boolean hasRiskLawsuit = false;//权利凭证卷
		if (!('2' == status.charAt(0)))
			hasBase = true;
		if (!('2' == status.charAt(1)))
			hasManagment = true;
		if (!('2' == status.charAt(2)))
			hasTitle = true;
		if (!('2' == status.charAt(3)))
			hasRiskCommon = true;
		if (!('2' == status.charAt(4)))
			hasRiskLawsuit = true;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		model.addAttribute("hasBase", hasBase);
		model.addAttribute("hasManagment", hasManagment);
		model.addAttribute("hasTitle", hasTitle);
		model.addAttribute("hasRiskLawsuit", hasRiskLawsuit);
		model.addAttribute("hasRiskCommon", hasRiskCommon);
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", form.getCheckStatus());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("view", true);
		model.addAttribute("userName", sessionLocal.getRealName());
		model.addAttribute("dept", sessionLocal.getUserInfo().getDeptList().get(0).getOrgName());
		model.addAttribute("type", type);
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "investigateBePutInStorage.vm";
	}
	
	/**
	 * 入库查看详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("inputView.htm")
	public String inputView(long formId, String type, HttpServletRequest request, Model model,
							HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		String status = fileServiceClient.findCheckStatus(Long.toString(formId));
		if (type == null) {
			for (int i = 0; i < status.length(); i++) {
				if (!('2' == status.charAt(i))) {
					if (i == 0) {
						type = FileTypeEnum.CREDIT_BUSSINESS.code();
						break;
					}
					if (i == 1) {
						type = FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
						break;
					}
					if (i == 2) {
						type = FileTypeEnum.DOCUMENT_OF_TITLE.code();
						break;
					}
					if (i == 3) {
						type = FileTypeEnum.RISK_COMMON.code();
						break;
					}
					if (i == 4) {
						type = FileTypeEnum.RISK_LAWSUIT.code();
						break;
					}
				}
			}
		}
		FileInfo info = fileServiceClient.findByFormId(formId, type);
		boolean hasBase = false;//基础卷
		boolean hasManagment = false;//授信后管理卷
		boolean hasTitle = false;//权利凭证卷
		boolean hasRiskCommon = false;//风险常规卷
		boolean hasRiskLawsuit = false;//风险诉讼卷
		if (!('2' == status.charAt(0)))
			hasBase = true;
		if (!('2' == status.charAt(1)))
			hasManagment = true;
		if (!('2' == status.charAt(2)))
			hasTitle = true;
		if (!('2' == status.charAt(3)))
			hasRiskCommon = true;
		if (!('2' == status.charAt(4)))
			hasRiskLawsuit = true;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		setAuditHistory2Page(form, model);
		model.addAttribute("hasRiskCommon", hasRiskCommon);
		model.addAttribute("hasRiskLawsuit", hasRiskLawsuit);
		model.addAttribute("hasBase", hasBase);
		model.addAttribute("hasManagment", hasManagment);
		model.addAttribute("hasTitle", hasTitle);
		model.addAttribute("info", info);
		model.addAttribute("checkStatus", form.getCheckStatus());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("view", true);
		model.addAttribute("type", type);
//		model.addAttribute("userName", sessionLocal.getRealName());
//		model.addAttribute("dept", sessionLocal.getUserInfo().getPrimaryOrg().getOrgName());
		return vm_path + "investigateBePutInStorage.vm";
	}
	
	/**
	 * 档案一览表---档案详情
	 *
	 * @param projectCode
	 * @param model
	 * @return
	 */
	@RequestMapping("fileViewDetail.htm")
	public String fileViewDetail(String projectCode, String type, HttpServletRequest request,
									Model model, HttpSession session) {
		if (type == null) {
			type = FileTypeEnum.CREDIT_BUSSINESS.code();
		}
		if (projectCode == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		FileInfo info = new FileInfo();
		info.setProjectCode(projectCode);
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		//        //查询所有入库申请单的信息
		//        FileQueryOrder order=new FileQueryOrder();
		//        order.setProjectCode(projectCode);
		//        order.setApplyType("入库申请");
		//        order.setPageSize(99999l);
		//        order.setFileType(type);
		//        order.setSortCol("A.raw_add_time");
		//        order.setSortOrder("ASC");
		//        QueryBaseBatchResult<FileFormInfo> fileList=fileServiceClient.query(order);
		//        if(ListUtil.isNotEmpty(fileList.getPageList())){
		//            FileFormInfo tempInfo=null;
		//            for(FileFormInfo fileFormInfo:fileList.getPageList()){
		//                 if(fileFormInfo.getFormStatus().code().equals(FormStatusEnum.APPROVAL.code())){
		//                     tempInfo=fileFormInfo;
		//                     break;
		//                 }
		//            }
		//            if(tempInfo==null){
		//                tempInfo=fileList.getPageList().get(0);
		//            }
		//            FileInfo fileInfo = fileServiceClient.findByFormId(tempInfo.getFormId(),type);
		//            BeanCopier.staticCopy(fileInfo,info);
		//        }else {
		//            LoanUseApplyQueryOrder loanUseApplyQueryOrder = new LoanUseApplyQueryOrder();//放用款时间
		//            loanUseApplyQueryOrder.setProjectCode(projectCode);
		//            loanUseApplyQueryOrder.setHasReceipt(BooleanEnum.IS);
		//            loanUseApplyQueryOrder.setSortOrder("ASC");
		//            loanUseApplyQueryOrder.setSortCol("a.apply_id");
		//            QueryBaseBatchResult<LoanUseApplyFormInfo> batchResult = loanUseApplyServiceClient.searchApplyForm(loanUseApplyQueryOrder);
		//            if (batchResult.getPageList().size() > 0) {
		//                LoanUseApplyFormInfo loanUseApplyFormInfo = batchResult.getPageList().get(0);
		//                FLoanUseApplyInfo loanUseApplyInfo = loanUseApplyServiceClient.queryByApplyId(loanUseApplyFormInfo.getApplyId());
		//                info.setFirstLoanTime(loanUseApplyInfo.getReceipt().getActualLoanTime());
		//            }
		//        }
		info.setProjectName(projectInfo.getProjectName());
		info.setCustomerName(projectInfo.getCustomerName());
		info.setType(type);
		List<FileInputListInfo> listInfos = fileServiceClient.searchArchivedByProjectCode(0, type,
			projectCode, "IS", null);
		info.setFileListInfos(listInfos);
		boolean hasBase = true;//基础卷
		boolean hasManagment = true;//授信后管理卷
		boolean hasTitle = true;//权利凭证卷
		boolean hasRiskCommon = true;//风险常规卷
		boolean hasRiskLawsuit = true;//风险诉讼卷
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		model.addAttribute("hasRiskCommon", hasRiskCommon);
		model.addAttribute("hasRiskLawsuit", hasRiskLawsuit);
		model.addAttribute("hasBase", hasBase);
		model.addAttribute("hasManagment", hasManagment);
		model.addAttribute("hasTitle", hasTitle);
		model.addAttribute("info", info);
		model.addAttribute("view", true);
		model.addAttribute("type", type);
		model.addAttribute("userName", sessionLocal.getRealName());
		model.addAttribute("isFileView", true);
		model.addAttribute("fileTypeEnum",FileTypeEnum.getAllEnum());
		return vm_path + "investigateBePutInStorage.vm";
	}
	
	private void setModel(String ids, String formId, String type, Model model) {
		if (formId != null && Long.parseLong(formId) > 0) {
			FormInfo form = formServiceClient.findByFormId(Long.parseLong(formId));
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FileOutputInfo info = fileServiceClient.findByOutputFormId(Long.parseLong(formId));
			if (info.getFileForm() == FileFormEnum.OUTPUT) {
				model.addAttribute("isOutput", true);
			} else {
				model.addAttribute("view", true);
			}
			model.addAttribute("info", info);
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
		} else {
			String idss[] = ids.split(",");
			FileOutputInfo outputInfo = new FileOutputInfo();
			FileInputListInfo listInfo = fileServiceClient.findByInputId(Long.parseLong(idss[0]));
			FileInfo fileInfo = fileServiceClient.findById(listInfo.getFileId());
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			outputInfo.setProjectCode(fileInfo.getProjectCode());
			outputInfo.setProjectName(fileInfo.getProjectName());
			outputInfo.setCustomerId(fileInfo.getCustomerId());
			outputInfo.setCustomerName(fileInfo.getCustomerName());
			outputInfo.setFileCode(fileInfo.getFileCode());
			outputInfo.setApplyDept(sessionLocal.getUserInfo().getPrimaryOrg().getOrgName());
			outputInfo.setApplyMan(sessionLocal.getRealName());
			outputInfo.setFileId(fileInfo.getFileId());
			outputInfo.setOutputDetailIds(ids);
			outputInfo.setApplyTime(new Date());
			String outputDetail = "";
			for (String id : idss) {
				FileInputListInfo listInfo2 = fileServiceClient.findByInputId(Long.parseLong(id));
				outputDetail = outputDetail + listInfo2.getArchiveFileName() + ",";
			}
			outputInfo.setOutputDetail(outputDetail.substring(0, outputDetail.length() - 1));
			model.addAttribute("info", outputInfo);
			if (type != null && type.equals(FileFormEnum.OUTPUT.code())) {
				outputInfo.setFileForm(FileFormEnum.OUTPUT);
				model.addAttribute("isOutput", true);
			} else {
				outputInfo.setFileForm(FileFormEnum.VIEW);
				model.addAttribute("view", true);
			}
		}
	}
	
	/**
	 * 申请出库
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping("applyOutput.htm")
	public String applyOutput(String ids, String formId, String type, HttpServletRequest request,
								Model model, HttpSession session) {
		setModel(ids, formId, type, model);
		return vm_path + "borrowAndTakeOut.vm";
	}
	
	/**
	 * 申请查阅
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping("applyView.htm")
	public String applyView(String ids, String formId, String type, HttpServletRequest request,
							Model model, HttpSession session) {
		setModel(ids, formId, type, model);
		return vm_path + "borrowAndTakeOut.vm";
	}
	
	/**
	 * 申请借阅
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping("applyBorrow.htm")
	public String applyBorrow(String ids, String formId, String type, HttpServletRequest request,
								Model model, HttpSession session) {
		if (formId != null && Long.parseLong(formId) > 0) {
			FormInfo form = formServiceClient.findByFormId(Long.parseLong(formId));
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FileBorrowInfo info = fileServiceClient.findByBorrowFormId(Long.parseLong(formId));
			model.addAttribute("isOutput", false);
			model.addAttribute("info", info);
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
		} else {
			String idss[] = ids.split(",");
			FileBorrowInfo borrowInfo = new FileBorrowInfo();
			FileInputListInfo listInfo = fileServiceClient.findByInputId(Long.parseLong(idss[0]));
			FileInfo fileInfo = fileServiceClient.findById(listInfo.getFileId());
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			borrowInfo.setProjectCode(fileInfo.getProjectCode());
			borrowInfo.setProjectName(fileInfo.getProjectName());
			borrowInfo.setCustomerId(fileInfo.getCustomerId());
			borrowInfo.setCustomerName(fileInfo.getCustomerName());
			//borrowInfo.setFileCode(fileInfo.getFileCode());
			borrowInfo.setApplyDept(sessionLocal.getUserInfo().getPrimaryOrg().getOrgName());
			borrowInfo.setApplyMan(sessionLocal.getRealName());
			borrowInfo.setFileId(fileInfo.getFileId());
			borrowInfo.setBorrowDetailId(ids);
			String outputDetail = "";
			String fileCode="";
			Map<String,String> fileCodeMap=new HashMap<String, String>();
			String oldFileCode="";
			Map<String,String> oldFileCodeMap=new HashMap<String, String>();
			for (String id : idss) {
				FileInputListInfo listInfo2 = fileServiceClient.findByInputId(Long.parseLong(id));
				//批量借出修改
				FileInfo fileInfo2 = fileServiceClient.findById(listInfo2.getFileId());
				fileCodeMap.put(fileInfo2.getFileCode(),fileInfo2.getFileCode());
				if(StringUtil.isNotBlank(fileInfo2.getOldFileCode())){
					oldFileCodeMap.put(fileInfo2.getOldFileCode(),fileInfo2.getOldFileCode());
				}

				outputDetail = outputDetail + listInfo2.getArchiveFileName() + ",";
			}
			for(String key:fileCodeMap.keySet()){
				fileCode = fileCode + key + ",";
			}
			for(String key:oldFileCodeMap.keySet()){
				oldFileCode = oldFileCode + key + ",";
			}
			borrowInfo.setOldFileCode(StringUtil.isBlank(oldFileCode)?null:oldFileCode.substring(0,oldFileCode.length()-1));
			borrowInfo.setFileCode(fileCode.substring(0,fileCode.length()-1));
			borrowInfo.setBorrowDetail(outputDetail.substring(0, outputDetail.length() - 1));
			borrowInfo.setApplyTime(new Date());
			model.addAttribute("info", borrowInfo);
			model.addAttribute("isOutput", false);
			model.addAttribute("minReturnTime", DateUtil.getDateByMonth(new Date(), 1));
			
//			if (null != idss && idss.length > 0) {
//				List<FileInfo> files = fileServiceClient.findByInputIds(ids);
//				if (null != files && files.size() > 1) {
//					model.addAttribute("multiple", true);
//					model.addAttribute("files", files);
//				}
//			}
		}
		model.addAttribute("type", type);
		return vm_path + "borrowAndTakeOut.vm";
	}
	
	/**
	 * 批量借阅
	 * @param request
	 * @param model
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("applyBatchBorrow.htm")
	public String applyBatchBorrow(HttpServletRequest request,
								Model model, HttpSession session, FileBatchApplyOrder order, String type) {
		long formId = order.getFormId();
//		if ("Borrow".equals(type)) {
			order.setStatus(FileStatusEnum.INPUT.code());
//		}
		if (formId > 0) {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FileBorrowInfo info = fileServiceClient.findByBorrowFormId(formId);
			model.addAttribute("isOutput", false);
			model.addAttribute("info", info);
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
		} else {
			List<FileInfo> files = fileServiceClient.findByFileIds(order.getFileIds());
			if (ListUtil.isNotEmpty(files)) {
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				FileBorrowInfo borrowInfo = new FileBorrowInfo();
				borrowInfo.setApplyDept(sessionLocal.getUserInfo().getPrimaryOrg().getOrgName());
				borrowInfo.setApplyMan(sessionLocal.getRealName());
				borrowInfo.setApplyTime(new Date());
				
				addBorrowInfo(borrowInfo, files);
				if ("RETURN".equals(type)) {
					borrowInfo.setIsBatch("IS");
				}
				
				model.addAttribute("info", borrowInfo);
			}
			String s1 = request.getParameter("borrowFormId");
			model.addAttribute("borrowFormId", s1);
			model.addAttribute("isOutput", false);
			model.addAttribute("minReturnTime", DateUtil.getDateByMonth(new Date(), 1));
		}
		
		model.addAttribute("fileIds", order.getFileIds());
		model.addAttribute("type", type);
		return vm_path + "batchBorrowAndTakeOut.vm";
	}
	
	private void addBorrowInfo(FileBorrowInfo borrowInfo, List<FileInfo> files) {
		borrowInfo.setFileId(files.get(0).getFileId());
		borrowInfo.setCustomerId(files.get(0).getCustomerId());
		
		StringBuilder projectCodes  = new StringBuilder();
		Set<String> projectCodeSet = new HashSet<>();
		StringBuilder projectNames  = new StringBuilder();
//		Set<String> projectNameSet = new HashSet<>();
		StringBuilder customeNames  = new StringBuilder();
		Set<Long> customeIdSet = new HashSet<>();
		StringBuilder fileCodes  = new StringBuilder();
		Set<String> fileCodeSet = new HashSet<>();
		StringBuilder oldFileCodes  = new StringBuilder();
		Set<String> oldFileCodeSet = new HashSet<>();
		StringBuilder fileNames  = new StringBuilder();
		StringBuilder inputIds  = new StringBuilder();
		
		for (FileInfo file : files) {
			if (StringUtil.isNotBlank(file.getProjectCode())
				&& !projectCodeSet.contains(file.getProjectCode())) {
				projectCodeSet.add(file.getProjectCode());
				projectCodes.append(file.getProjectCode()).append(",");
				projectNames.append(file.getProjectName()).append(",");
			}
			if (file.getCustomerId() > 0 && !customeIdSet.contains(file.getCustomerId())) {
				customeIdSet.add(file.getCustomerId());
				customeNames.append(file.getCustomerName()).append(",");
			}
			if (StringUtil.isNotBlank(file.getFileCode())
				&& !fileCodeSet.contains(file.getFileCode())) {
				fileCodeSet.add(file.getFileCode());
				fileCodes.append(file.getFileCode()).append(",");
			}
			if (StringUtil.isNotBlank(file.getOldFileCode())
				&& !oldFileCodeSet.contains(file.getOldFileCode())) {
				oldFileCodeSet.add(file.getOldFileCode());
				oldFileCodes.append(file.getOldFileCode()).append(",");
			}
			for (FileInputListInfo input : file.getFileListInfos()) {
				fileNames.append(input.getArchiveFileName()).append(",");
				inputIds.append(input.getInputListId()).append(",");
			}
		}
		
		if (projectCodes.length() > 0) {
			borrowInfo.setProjectCode(projectCodes.deleteCharAt(projectCodes.length()-1).toString());
		}
		if (projectNames.length() > 0) {
			borrowInfo.setProjectName(projectNames.deleteCharAt(projectNames.length()-1).toString());
		}
		if (customeNames.length() > 0) {
			borrowInfo.setCustomerName(customeNames.deleteCharAt(customeNames.length()-1).toString());
		}
		if (fileCodes.length() > 0) {
			borrowInfo.setFileCode(fileCodes.deleteCharAt(fileCodes.length()-1).toString());
		}
		if (oldFileCodes.length() > 0) {
			borrowInfo.setOldFileCode(oldFileCodes.deleteCharAt(oldFileCodes.length()-1).toString());
		}
		if (fileNames.length() > 0) {
			borrowInfo.setBorrowDetail(fileNames.deleteCharAt(fileNames.length()-1).toString());
		}
		if (inputIds.length() > 0) {
			borrowInfo.setBorrowDetailId(inputIds.deleteCharAt(inputIds.length()-1).toString());
		}
	}

	/*
	 * 保存出库申请
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveOutput.htm")
	@ResponseBody
	public JSONObject saveOutput(FileOutputOrder order,HttpServletRequest request, Model model) {
		String tipPrefix = " 保存出库申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			String detailIds[] = order.getOutputDetailIds().split(",");
			FileInputListInfo listInfo = fileServiceClient.findByInputId(Long.parseLong(order
				.getOutputDetailIds().split(",")[0]));
			FileInfo fileInfo = fileServiceClient.findById(listInfo.getFileId());
			order.setProjectCode(fileInfo.getProjectCode());
			order.setProjectName(fileInfo.getProjectName());
			order.setCustomerId(fileInfo.getCustomerId());
			order.setCustomerName(fileInfo.getCustomerName());
			order.setFileCode(fileInfo.getFileCode());
			order.setOldFileCode(fileInfo.getOldFileCode());
			order.setApplyDeptCode(sessionLocal.getUserInfo().getDept().getCode());
			order.setApplyDept(sessionLocal.getUserInfo().getDept().getOrgName());
			order.setApplyManId(sessionLocal.getUserId());
			order.setApplyMan(sessionLocal.getRealName());
			order.setFileId(fileInfo.getFileId());
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			if (order.getFileForm() == FileFormEnum.OUTPUT) {
				order.setFormCode(FormCodeEnum.FILE_OUTPUT_APPLY);
			} else {
				order.setFormCode(FormCodeEnum.FILE_VIEW_APPLY);
			}
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = fileServiceClient.saveOuput(order);
			jsonObject.put("success", true);
			jsonObject.put("message", "保存成功");
			jsonObject.put("submitStatus", order.getSubmitStatus());
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("formId", result.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存出库申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存借阅申请
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveBorrow.htm")
	@ResponseBody
	public JSONObject saveBorrow(FileBorrowOrder order,HttpServletRequest request, Model model) {
		String tipPrefix = " 保存借阅申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			if (null != order.getBorrowFormId() && order.getBorrowFormId() > 0) {
				FileBorrowInfo info = fileServiceClient.findByBorrowFormId(order.getBorrowFormId());
				if (null != info && !"IS".equals(info.getIsBatch())) {
					jsonObject.put("success", false);
					jsonObject.put("message", "数据状态不正确，无法提交申请");
					return jsonObject;
				}
			}
			
			FileInputListInfo listInfo = fileServiceClient.findByInputId(Long.parseLong(order
				.getBorrowDetailId().split(",")[0]));
			FileInfo fileInfo = fileServiceClient.findById(listInfo.getFileId());
			if (StringUtil.isBlank(order.getProjectCode())) {
				order.setProjectCode(fileInfo.getProjectCode());
			}
			if (StringUtil.isBlank(order.getProjectName())) {
				order.setProjectName(fileInfo.getProjectName());
			}
			if (order.getCustomerId() <= 0) {
				order.setCustomerId(fileInfo.getCustomerId());
			}
			if (StringUtil.isBlank(order.getCustomerName())) {
				order.setCustomerName(fileInfo.getCustomerName());
			}
			order.setFileCode(order.getFileCode());
			order.setApplyDept(sessionLocal.getUserInfo().getDept().getOrgName());
			order.setApplyMan(sessionLocal.getRealName());
			if (order.getFileId() <= 0) {
				order.setFileId(fileInfo.getFileId());
			}
			//           order.setBorrowDetail(listInfo.getArchiveFileName());
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			if ("BORROW".equals(order.getType())) {
				order.setFormCode(FormCodeEnum.FILE_BORROW_APPLY);
			} else {
				order.setFormCode(FormCodeEnum.FILE_RETURN_APPLY);
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = fileServiceClient.saveBorrow(order);
			jsonObject.put("success", true);
			jsonObject.put("message", "保存成功");
			jsonObject.put("submitStatus", order.getSubmitStatus());
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("formId", result.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存借阅申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 出库审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("outputAudit.htm")
	public String outputAudit(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileOutputInfo info = fileServiceClient.findByOutputFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_OUTPUT_APPLY) {
			model.addAttribute("isOutput", true);
		} else {
			model.addAttribute("view", true);
		}
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "investigateBorrowAndTakeOut.vm";
	}
	
	/**
	 * 借阅审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("borrowAudit.htm")
	public String borrowAudit(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileBorrowInfo info = fileServiceClient.findByBorrowFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_BORROW_APPLY) {
			model.addAttribute("isBorrow", true);
		} else {
			model.addAttribute("isReturn", true);
		}
		model.addAttribute("isOutput", false);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "investigateBorrowAndTakeOut.vm";
	}
	
	/**
	 * 档案归还
	 *
	 * @param ids
	 * @param model
	 * @return
	 */
	
	@RequestMapping("fileReturn.htm")
	@ResponseBody
	public JSONObject fileReturn(long ids, HttpServletRequest request, Model model,
									HttpSession session) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			FileInputListOrder order = new FileInputListOrder();
			order.setId(ids);
			order.setStatus(FileStatusEnum.INPUT);
			order.setCurrBorrowManId(0);
			order.setCurrBorrowId(0);
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = fileServiceClient.saveInputList(order);
			jsonObject.put("success", true);
			jsonObject.put("message", "档案归还成功");
			jsonObject = toJSONResult(jsonObject, result, "档案归还成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("档案归还出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 出库单编辑
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("outputEdit.htm")
	public String outputEdit(Long formId, Model model) {
		String tipPrefix = "出库单编辑";
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileOutputInfo info = fileServiceClient.findByOutputFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_OUTPUT_APPLY) {
			model.addAttribute("isOutput", true);
		} else {
			model.addAttribute("view", true);
		}
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		return vm_path + "borrowAndTakeOut.vm";
	}
	
	/**
	 * 借阅单编辑
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowEdit.htm")
	public String inputEdit(Long formId, Model model) {
		String tipPrefix = "借阅单编辑";
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileBorrowInfo info = fileServiceClient.findByBorrowFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_BORROW_APPLY) {
			model.addAttribute("type", "BORROW");
			model.addAttribute("minReturnTime", DateUtil.getDateByMonth(info.getApplyTime(), 1));
		} else {
			model.addAttribute("type", "RETURN");
		}
		model.addAttribute("isOutput", false);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		return vm_path + "borrowAndTakeOut.vm";
	}
	
	/**
	 * 出库详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("outputView.htm")
	public String outputView(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileOutputInfo info = fileServiceClient.findByOutputFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_OUTPUT_APPLY) {
			model.addAttribute("isOutput", true);
		} else {
			model.addAttribute("view", true);
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		return vm_path + "investigateBorrowAndTakeOut.vm";
	}
	
	/**
	 * 借阅详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("borrowView.htm")
	public String borrowView(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileBorrowInfo info = fileServiceClient.findByBorrowFormId(formId);
		if (form.getFormCode() == FormCodeEnum.FILE_BORROW_APPLY) {
			model.addAttribute("isBorrow", true);
		} else {
			model.addAttribute("isReturn", true);
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("isOutput", false);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		return vm_path + "investigateBorrowAndTakeOut.vm";
	}
	
	private boolean canView() {
		boolean canView = false;
		if (DataPermissionUtil.isNkFZR() || DataPermissionUtil.isFWBFZR()
			|| DataPermissionUtil.isRiskFZR() || DataPermissionUtil.isNkFgfz()
			|| DataPermissionUtil.isLegalFgfz()) {
			canView = true;
		}
		return canView;
	}
	
	private boolean canApplyView() {
		//        boolean canView=false;
		//        SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//        String currDeptCode=sessionLocal.getUserInfo().getDept().getCode();
		//        String riskDeptCode= sysParameterServiceClient
		//                .getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code());//风险部
		//        String nKDeptCode= sysParameterServiceClient
		//                .getSysParameterValue(SysParamEnum.SYS_PARAM_NKHGB_DEPT_CODE.code());//内控部
		//        String fWDeptCode= sysParameterServiceClient
		//                .getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());//法务部
		//        String busiDeptCodes = sysParameterServiceClient
		//                .getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
		//        if(currDeptCode.equals(riskDeptCode)||currDeptCode.equals(nKDeptCode)||currDeptCode.equals(fWDeptCode)){
		//            canView=true;
		//            return canView;
		//        }
		//        String busiDept[]=busiDeptCodes.split(",");
		//        for (String dept:busiDept){
		//            if(currDeptCode.equals(dept)){
		//                return true;
		//            }
		//        }
		return true;
	}
	
	/**
	 * 档案明细列表权限判断
	 */
	private Map<String, Boolean> listPermission(List<FileFormInfo> infos) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		//去除重复项目
		Map<String, FileFormInfo> projectMap = new HashMap<String, FileFormInfo>();
		if (ListUtil.isNotEmpty(infos)) {
			for (FileFormInfo info : infos) {
				projectMap.put(info.getProjectCode(), info);
			}
		}
		//判断项目相关权限
		if (projectMap.size() > 0) {
			for (String projectCode : projectMap.keySet()) {
				if (DataPermissionUtil.isBusiManager(projectCode)) {//是否项目客户经理
					map.put(projectCode + "_busiManager", true);
				} else if (DataPermissionUtil.isBusiManagerb(projectCode)) {//是否项目客户经理b
					map.put(projectCode + "_busiManagerb", true);
				} else if (DataPermissionUtil.isBusiFZR(projectCode)) {//是否业务负责人
					map.put(projectCode + "_busiFZR", true);
				} else if (DataPermissionUtil.isBusinessFgfz(projectCode)) {//是否业务分管副总
					map.put(projectCode + "_busiFgfz", true);
				} else if (DataPermissionUtil.isRiskManager(projectCode)) {//是否风险经理
					map.put(projectCode + "_riskManager", true);
				} else if (DataPermissionUtil.isRiskFgfz(projectCode)) {//是否分管副总
					map.put(projectCode + "_riskFgfz", true);
				} else if (DataPermissionUtil.isLegalManager(projectCode)) {//是否法务经理
					map.put(projectCode + "_legalManager", true);
				}
				if (DataPermissionUtil.isXinHuiDept(projectMap.get(projectCode).getFormDeptCode())) {
					map.put(projectCode + "_isXinhui", true);
				} else {
					map.put(projectCode + "_isXinhui", false);
				}
			}
		}
		
		return map;
	}

	/**
	 * 档案一览表---批量借出检查
	 *
	 * @param projectCode
	 * @param model
	 * @return
	 */
	@RequestMapping("batchBorrow.htm")
	@ResponseBody
	public JSONObject batchBorrow(String projectCode, String type,String startTime,String endTime, HttpServletRequest request,
								 Model model, HttpSession session) {
			String tipPrefix = " 批量借出检查";
			JSONObject jsonObject = new JSONObject();
			try {
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				if (sessionLocal == null) {
					jsonObject.put("success", false);
					jsonObject.put("message", "您未登陆或登陆已失效");
					return jsonObject;
				}
				//信惠只能借信惠的档案 非信惠的只能借非信惠的档案
				ProjectInfo projectInfo=projectServiceClient.queryByCode(projectCode,false);
				if((DataPermissionUtil.isXinHuiDept(projectInfo.getDeptCode())&&DataPermissionUtil.isBelong2Xinhui())||(!DataPermissionUtil.isXinHuiDept(projectInfo.getDeptCode())&&!DataPermissionUtil.isBelong2Xinhui())){
					Date startTimeDate=null;
					Date endTimeDate=null;
					if(StringUtil.isNotBlank(startTime)){
						startTimeDate=DateUtil.parse(startTime);
						endTimeDate=DateUtil.parse(endTime);
					}
					String ids = fileServiceClient.getProjectInputStatusFileIds(type,projectCode,startTimeDate,endTimeDate);
  					if(StringUtil.isBlank(ids)){
						jsonObject.put("success", false);
						jsonObject.put("message", "当前项目["+FileTypeEnum.getByCode(type).message()+"]所选入库时间没有可以借出的档案");
					}else{
						jsonObject.put("success", true);
						jsonObject.put("ids", ids);
					}
				}else{
					jsonObject.put("success", false);
					if(DataPermissionUtil.isXinHuiDept(projectInfo.getDeptCode())){
						jsonObject.put("message", "信惠部门的档案只有信惠部门的人员能够借阅");
					}else{
						jsonObject.put("message", "非信惠部门的档案只有非信惠部门的人员能够借阅");
					}
				}
			} catch (Exception e) {
				jsonObject.put("success", false);
				jsonObject.put("message", e);
				logger.error("批量借出检查出错", e);
			}
			return jsonObject;
		}

	@RequestMapping("getFormFileCode.json")
	@ResponseBody
	public JSONObject getFormFileCode(long formId) {
		String tipPrefix = "查询表单所有档案编号";
		JSONObject result = new JSONObject();
		if (formId <= 0) {
			result.put("success", false);
			result.put("message", "请选择要删除的档案");
			return result;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
			result.put("success", false);
			result.put("message", "登陆已失效");
			return result;
		}
		List<JSONObject> dataList = Lists.newArrayList();
		JSONObject data = new JSONObject();
		FileInfo info = fileServiceClient.findByFormId(formId,FileTypeEnum.CREDIT_BUSSINESS.code());
		setJsonDataList(info,dataList);
		info = fileServiceClient.findByFormId(formId,FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code());
		setJsonDataList(info,dataList);
		info = fileServiceClient.findByFormId(formId,FileTypeEnum.DOCUMENT_OF_TITLE.code());
		setJsonDataList(info,dataList);
		info = fileServiceClient.findByFormId(formId,FileTypeEnum.RISK_LAWSUIT.code());
		setJsonDataList(info,dataList);
		info = fileServiceClient.findByFormId(formId,FileTypeEnum.RISK_COMMON.code());
		setJsonDataList(info,dataList);
		data.put("pageList", dataList);
		result = toStandardResult(data, tipPrefix);
		return result;
	}
	private void setJsonDataList(FileInfo info,List<JSONObject> dataList){
		if(info.getFileCode()!=null){
			JSONObject json = new JSONObject();
			json.put("fileCode", info.getFileCode());
			dataList.add(json);
		}
	}

	/**
	 * 选择历史档案
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("copyHisFileList.json")
	@ResponseBody
	public JSONObject copyHisFileList(FileQueryOrder order, Model model) {
		String tipPrefix = "选择历史档案";
		JSONObject result = new JSONObject();
		try {
			order.setIsFileAdmin(BooleanEnum.NO);
			if (DataPermissionUtil.isFileAdministrator() || DataPermissionUtil.isBelongNK()
					|| DataPermissionUtil.isSystemAdministrator()) {//档案管理员或者内控的有所有数据的权限
				order.setIsFileAdmin(BooleanEnum.IS);
			}
			setSessionLocalInfo2Order(order);
			QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.query(order);
			if (batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("pageCount", batchResult.getPageCount());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (FileFormInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("formId", info.getFormId());
						json.put("fileCode", info.getFileCode());
						json.put("oldFileCode", info.getOldFileCode());
						json.put("projectCode", info.getProjectCode());
						json.put("customerName", info.getCustomerName());
						json.put("rawAddTime", DateUtil.simpleFormat(info.getRawAddTime()));
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}

		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}

	/**
	 * 导入模板
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("importTemplate.json")
	public String templateExport(HttpServletRequest request,
								 HttpServletResponse response, Model model) {
		try {
				String busiManagerFileTypes[] = {FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.message(),FileTypeEnum.CREDIT_BUSSINESS.message(),FileTypeEnum.DOCUMENT_OF_TITLE.message(),FileTypeEnum.RISK_COMMON.message()};
				String legealManagerFileTypes[] = {FileTypeEnum.RISK_LAWSUIT.message()};
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				String text="业务经理";
				String fileTypes[]=busiManagerFileTypes;
				if (sessionLocal != null && DataPermissionUtil.isLegalManager()) {
					text="法务经理";
					fileTypes=legealManagerFileTypes;
				}
				OutputStream os = response.getOutputStream();//获得输出流
				response.reset();// 清空输出流
				response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ new String(("已解保项目档案导入模板("+text+").xls").getBytes("utf-8"),
								"ISO8859-1"));

				WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
				WritableSheet writeSheet =  wbook.createSheet("sheet1", 0);// sheet名称
				WritableFont font = new WritableFont(WritableFont.createFont("宋体"),16,WritableFont.NO_BOLD);
				WritableCellFormat format=new WritableCellFormat(font);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setAlignment(Alignment.CENTRE);
				format.setWrap(false);
				format.setBackground(Colour.LIGHT_TURQUOISE);
				format.setBorder(Border.ALL, BorderLineStyle.THIN);
				WritableFont font2 = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD);
				WritableCellFormat format2=new WritableCellFormat(font2);
				format2.setVerticalAlignment(VerticalAlignment.CENTRE);
				format2.setAlignment(Alignment.CENTRE);
				format2.setWrap(false);
				writeSheet.setColumnView(0, 20);
				writeSheet.setColumnView(1, 20);
				writeSheet.setColumnView(2, 20);
				writeSheet.setColumnView(3, 20);
				writeSheet.setColumnView(4, 25);
				writeSheet.setColumnView(5, 15);
				writeSheet.setColumnView(6, 25);
				writeSheet.setColumnView(7, 35);
				writeSheet.setColumnView(8, 20);
				writeSheet.setColumnView(9, 20);
				writeSheet.setColumnView(10, 20);
				writeSheet.setColumnView(11, 20);
				writeSheet.setColumnView(12, 20);
				writeSheet.setColumnView(13, 20);
				writeSheet.setColumnView(14, 20);
				writeSheet.setColumnView(15, 20);


				if(DataPermissionUtil.isLegalManager()){
					writeSheet.addCell(new Label(2,1,FileTypeEnum.RISK_LAWSUIT.message(),format2));
				}else{
					WritableSheet sheet2 = wbook.createSheet("档案类别", 1);
					for (int i = 0; i < fileTypes.length; i++) {
						sheet2.addCell(new Label(0, i, fileTypes[i],format2));
					}

					wbook.addNameArea("fileTypes", sheet2, 0, 0, 0, fileTypes.length+1);
					WritableCellFeatures wcf = new WritableCellFeatures();
					wcf.setDataValidationRange("fileTypes");
					Label label = new Label(2, 1, "");
					label.setCellFeatures(wcf);

					writeSheet.addCell(label);

				}
				writeSheet.addCell(new Label(0,0,"*项目编号",format));
				writeSheet.addCell(new Label(1,0,"*客户名称",format));
				writeSheet.addCell(new Label(2,0,"*档案类别",format));
				writeSheet.addCell(new Label(3,0,"*档案编码",format));
				writeSheet.addCell(new Label(4,0,"*归档文件类别",format));
				writeSheet.addCell(new Label(5,0,"*件号",format));
				writeSheet.addCell(new Label(6,0,"*归档文件名称",format));
				writeSheet.addCell(new Label(7,0,"*原件/复印件/打印件",format));
				writeSheet.addCell(new Label(8,0,"*页数",format));
				writeSheet.addCell(new Label(9,0,"备注",format));
				writeSheet.addCell(new Label(10,0,"移交部门",format));
				writeSheet.addCell(new Label(11,0,"档案移交人员",format));
				writeSheet.addCell(new Label(12,0,"移交时间",format));
				writeSheet.addCell(new Label(13,0,"接收部门",format));
				writeSheet.addCell(new Label(14,0,"档案接收人员",format));
				writeSheet.addCell(new Label(15,0,"接收时间",format));

				wbook.write();
				wbook.close();

		} catch (Exception e) {
			logger.error("档案导入模板导出失败：{}", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * excel导入
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping("dataImport.json")
	@ResponseBody
	public JSONObject dataImport(HttpServletRequest request, HttpServletResponse response,
								 Model model) {
		String tipPrefix = "excel导入";
		JSONObject json = new JSONObject();
		JSONArray message = new JSONArray();
		try {
			ExcelData excelData = ExcelUtil.uploadExcel(request);
			// 抓取数据生成order并存库
			int columns = excelData.getColumns(); //列数
			int rows = excelData.getRows(); //行数
			String[][] datas = excelData.getDatas(); //数据
			//循环得到每一行的数据
			List<FileImportOrder> orders=Lists.newArrayList();
			for (int i = 0; i < rows; i++) {
				if(i==0){continue;}
				FileImportOrder order=new FileImportOrder();
				order.setIndex(i+"");
				String projectCode=datas[i][0];
				String fileCode=datas[i][3];
				JSONObject r = new JSONObject();
				StringBuffer messageStr=new StringBuffer("");
				for (int j = 0; j < columns; j++) {
					String str = datas[i][j];
					switch (j){
						case 0:
							if(StringUtil.isNotBlank(str)){
								order.setProjectCode(str);
							}else{
								messageStr.append("第"+(i+1)+"行项目编号不能为空\n");
							}
							break;
						case 1:
							if(StringUtil.isNotBlank(str)){
								order.setCustomerName(str);
							}else{
								messageStr.append("第"+(i+1)+"行客户名称不能为空\n");
							}
							break;
						case 2:
							if(StringUtil.isNotBlank(str)){
								FileTypeEnum typeEnum=null;
								if(str.contains("基础卷")){
									typeEnum=FileTypeEnum.CREDIT_BUSSINESS;
									order.setType(typeEnum);
								}else {
									typeEnum = FileTypeEnum.getByMessage(str);
									if (typeEnum == null) {
										messageStr.append("第" + (i + 1) + "行档案类别不匹配\n");
									} else {
										if(DataPermissionUtil.isLegalManager()&&typeEnum!=FileTypeEnum.RISK_LAWSUIT){
										messageStr.append("法务经理只能操作"+FileTypeEnum.RISK_LAWSUIT.message()+"\n");
										}else if(DataPermissionUtil.isBusiManager()&&typeEnum==FileTypeEnum.RISK_LAWSUIT){
										messageStr.append("业务经理不能操作"+FileTypeEnum.RISK_LAWSUIT.message()+"\n");
										}else{
										order.setType(typeEnum);
										}
									}
								}
							}else{
								messageStr.append("第"+(i+1)+"行档案类别不能为空\n");
							}
							break;
						case 3:
							if(StringUtil.isNotBlank(str)){
								order.setFileCode(str);
							}else{
								messageStr.append("第"+(i+1)+"行档案编号不能为空\n");
							}
							break;
						case 4:
							if(StringUtil.isNotBlank(str)){
								order.setFileType(str);
							}else{
								messageStr.append("第"+(i+1)+"行归档文件类别不能为空\n");
							}
							break;
						case 5:
							if(StringUtil.isNotBlank(str)){
								order.setNo(str);
							}else{
								messageStr.append("第"+(i+1)+"行件号不能为空\n");
							}
							break;
						case 6:
							if(StringUtil.isNotBlank(str)){
								order.setArchiveFileName(str);
							}else{
								messageStr.append("第"+(i+1)+"行归档文件名称不能为空\n");
							}
							break;
						case 7:
							if(StringUtil.isNotBlank(str)){
									FileAttachTypeEnum typeEnum=FileAttachTypeEnum.getByMessage(str);
								if (typeEnum==null){
									messageStr.append("第"+(i+1)+"行原件/复印件/打印件不匹配\n");
								}else{
									order.setAttachType(typeEnum);
								}
							}else{
								messageStr.append("第"+(i+1)+"行档案类别不能为空\n");
							}
							break;
						case 8:
							if(StringUtil.isNotBlank(str)){
								order.setFilePage(str);
							}else{
								messageStr.append("第"+(i+1)+"行归档文件页数不能为空\n");
							}
							break;
						case 9:order.setRemark(str);break;
						case 10:order.setHandOverDept(str);break;
						case 11:order.setHandOverMan(str);break;
						case 12:order.setHandOverTime(str);break;
						case 13:order.setReceiveDept(str);break;
						case 14:order.setReceiveMan(str);break;
						case 15:order.setReceiveTime(str);break;
					}
				}
				if(StringUtil.isNotBlank(messageStr.toString())){
					r.put("projectCode",projectCode);
					r.put("fileCode",fileCode);
					r.put("importResult",messageStr);
					message.add(r);
				}
				orders.add(order);
			}
			if(message.size()>0){
				json.put("success", true);
				json.put("message", message);
				return json;
			}
			if (ListUtil.isEmpty(orders)) {
				json.put("success", false);
				json.put("message", "数据为空");
				return json;
			}

			JSONObject checkResult=checkOrders(orders);
			JSONArray checkResultArray=checkResult.getJSONArray("message");
			if(!(checkResultArray.size()>0)){
				ImportOrder order=new ImportOrder();
				setSessionLocalInfo2Order(order);
				order.setOrders(orders);
				FileImportBatchResult importResult = fileServiceClient.fileImport(order);
			if (importResult == null || !importResult.isSuccess()) {
				return toJSONResult(importResult, tipPrefix);
			} else {
				json.put("success", true);
				List<FileImportResult> detailResults = importResult.getBatchResult();
				message = new JSONArray();
				for (FileImportResult iResult : detailResults) {
					JSONObject r = new JSONObject();
					r.put("projectCode",
							iResult.getProjectCode() == null ? "" : iResult.getProjectCode());
					r.put("fileCode", iResult.getFileCode());
					r.put("importResult", iResult.isSuccess() ? "导入成功" : iResult.getMessage());
					message.add(r);
					}
				}
			}else{
				return checkResult;
			}
			json.put("success", true);
			json.put("message", message);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}

		return json;
	}
	public JSONObject checkOrders(List<FileImportOrder> orders){
			JSONObject json = new JSONObject();
			JSONArray message = new JSONArray();
			//项目客户名称map 校验项目编号与客户名称是否一一对应
			Map<String,String[]> projectCustomerMap=new HashMap<>();
			for(FileImportOrder order:orders){
				String customerName[]=projectCustomerMap.get(order.getProjectCode());
				if(customerName!=null&&customerName.length>0){
					if(StringUtil.equals(customerName[0],order.getCustomerName())){
						JSONObject r = new JSONObject();
						r.put("projectCode",order.getProjectCode());
						r.put("fileCode",order.getFileCode());
						r.put("importResult", "项目编号["+order.getProjectCode()+"]第"+order.getIndex()+"行客户名称与第"+customerName[1]+"行客户名称不一致!");
						message.add(r);
					}else{
						String temp[]={order.getCustomerName(),order.getIndex()};
						projectCustomerMap.put(order.getProjectCode(),temp);
					}
				}
			}
			//校验项目与档案编号是否一一对应
			Map<String,String[]> fileCodeProjectMap=new HashMap<>();
			for(FileImportOrder order:orders){
				String fileCode[]=fileCodeProjectMap.get(order.getFileCode());
				if(fileCode!=null&&fileCode.length>0){
					if(StringUtil.equals(fileCode[0],order.getProjectCode())){
						JSONObject r = new JSONObject();
						r.put("projectCode",order.getProjectCode());
						r.put("fileCode",order.getFileCode());
						r.put("importResult", "档案编号["+order.getFileCode()+"]第"+order.getIndex()+"行项目名称与第"+fileCode[1]+"行项目名称不一致!");
						message.add(r);
					}else{
						String temp[]={order.getFileCode(),order.getIndex()};
						fileCodeProjectMap.put(order.getFileCode(),temp);
					}
				}
			}
			//校验档案编号内件号是否重复
			Map<String,List<String[]>> fileCodeNoMap=new HashMap<>();
			for(FileImportOrder order:orders){
				List<String[]> nos=fileCodeNoMap.get(order.getFileCode());
				if(ListUtil.isEmpty(nos)){
					nos= Lists.newArrayList();
				}
				String tem[]={order.getNo(),order.getIndex()};
				nos.add(tem);
				fileCodeNoMap.put(order.getFileCode(),nos);
			}
			if(fileCodeNoMap.size()>0){
				for(String fileCode:fileCodeNoMap.keySet()){
					List<String[]> nos=fileCodeNoMap.get(fileCode);
					Map<String,String[]> nosMap=new HashMap();
					for(String[] no:nos){
						String tempNo[]=nosMap.get(no[0]);
						if(tempNo!=null&&tempNo.length>0){
							JSONObject r = new JSONObject();
							r.put("projectCode","");
							r.put("fileCode",fileCode);
							r.put("importResult", "第"+no[1]+"行档案编号["+fileCode+"]件号["+no[0]+"]与第"+tempNo[1]+"行件号重复!");
							message.add(r);
						}
					}
				}
			}
			json.put("success", true);
			json.put("message",message);
			return json;
	}


	/**
	 * 档案详情-文档列表
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping("fileDocList.json")
	@ResponseBody
	public JSONObject fileDocList(HttpServletRequest request, HttpServletResponse response,
								 Model model) {
		String tipPrefix = "档案详情-文档列表";
		JSONObject json = new JSONObject();
		try{
			String fileCode=request.getParameter("fileCode");
			String no=request.getParameter("no");
			String result=e6ApiService.getDocIds(fileCode,"业务档案案卷",no,"业务档案卷内文件");
			json.put("success", true);
			json.put("data", request);
		}catch (Exception e){
			json.put("success", false);
			json.put("message", e.getMessage());
			return json;
		}
		return json;
	}



	/**
	 * 展期申请单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("extensionList.htm")
	public String extensionList(FileQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FileFormInfo> batchResult = fileServiceClient.extensionList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "extensionList.vm";
	}

	/**
	 * 展期申请单编辑
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("extensionEdit.htm")
	public String extensionEdit(String projectCode,Long formId, Model model) {
		if(projectCode!=null){
			FileOutputExtensionInfo info = new FileOutputExtensionInfo();
			ProjectInfo projectInfo=projectServiceClient.queryByCode(projectCode,false);
			info.setProjectCode(projectCode);
			info.setProjectName(projectInfo.getProjectName());
			model.addAttribute("info", info);
			return vm_path + "extensionAdd.vm";
		}else {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FileOutputExtensionInfo info = fileServiceClient.findByOutputExtensionFormId(formId);
			model.addAttribute("info", info);
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
			return vm_path + "extensionAdd.vm";
		}
	}

	/**
	 * 申请查阅
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("extensionView.htm")
	public String extensionView(Long formId, HttpServletRequest request,
							Model model, HttpSession session) {
		FormInfo form = formServiceClient.findByFormId(formId);
		FileOutputExtensionInfo info = fileServiceClient.findByOutputExtensionFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		return vm_path + "extensionView.vm";
	}

	/*
	 * 保存展期申请单
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveExtension.json")
	@ResponseBody
	public JSONObject saveExtension(FileOutputExtensionOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = "保存展期申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();

			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			ProjectInfo projectInfo=projectServiceClient.queryByCode(order.getProjectCode(),false);
			order.setProjectCode(projectInfo.getProjectCode());
			order.setProjectName(projectInfo.getProjectName());
			order.setCustomerId(projectInfo.getCustomerId());
			order.setCustomerName(projectInfo.getCustomerName());
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setFormCode(FormCodeEnum.FILE_OUTPUT_EXTENSION);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = fileServiceClient.saveOuputExtension(order);
			jsonObject.put("success", true);
			jsonObject.put("message", "保存成功");
			jsonObject.put("submitStatus", order.getSubmitStatus());
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("formId", result.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存保存展期申请出错", e);
		}

		return jsonObject;
	}

	/**
	 * 展期审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */

	@RequestMapping("extensionAudit.htm")
	public String extensionAudit(long formId, HttpServletRequest request, Model model,
							  HttpSession session) {

		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FileOutputExtensionInfo info = fileServiceClient.findByOutputExtensionFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "extensionView.vm";
	}

}
