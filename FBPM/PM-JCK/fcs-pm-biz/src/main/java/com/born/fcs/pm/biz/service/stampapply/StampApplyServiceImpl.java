package com.born.fcs.pm.biz.service.stampapply;

import java.util.*;

import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.*;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FStampApplyDO;
import com.born.fcs.pm.dal.dataobject.FStampApplyFileDO;
import com.born.fcs.pm.dataobject.StampApplyFormProjeactDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.stampapply.StampAFileInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyProjectResultInfo;
import com.born.fcs.pm.ws.order.stampapply.StampAFileOrder;
import com.born.fcs.pm.ws.order.stampapply.StampApplyOrder;
import com.born.fcs.pm.ws.order.stampapply.StampApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("stampApplyService")
public class StampApplyServiceImpl extends BaseFormAutowiredDomainService implements
																			StampApplyService {
	@Override
	public StampApplyInfo findByFormId(long formId,String isReplace) {
		StampApplyInfo info = null;
		List<FStampApplyFileDO> fStampApplyFileDO = null;
		List<StampAFileInfo> stampAFileInfo = Lists.newArrayList();
		if (formId > 0) {
			FStampApplyDO DO = fStampApplyDAO.findByFormId(formId);
			if("IS".equals(isReplace)){
				fStampApplyFileDO = fStampApplyFileDAO.findByReplaceApplyId(DO.getApplyId());
			}
			else {
				fStampApplyFileDO = fStampApplyFileDAO.findByApplyId(DO.getApplyId());
			}
			info = convertDO2Info(DO);
		}
		if (fStampApplyFileDO.size() > 0) {
			for (FStampApplyFileDO DO2 : fStampApplyFileDO) {
				stampAFileInfo.add(convertDO2Info(DO2));
			}
		}
		info.setStampAFiles(stampAFileInfo);
		return info;
	}

	@Override
	public StampApplyInfo findById(long id,String isReplace) {
		StampApplyInfo info = null;
		List<FStampApplyFileDO> fStampApplyFileDO = null;
		List<StampAFileInfo> stampAFileInfo = Lists.newArrayList();
		if (id > 0) {
			FStampApplyDO DO = fStampApplyDAO.findById(id);
			if("IS".equals(isReplace)){
				fStampApplyFileDO = fStampApplyFileDAO.findByReplaceApplyId(DO.getApplyId());
			}
			else {
				fStampApplyFileDO = fStampApplyFileDAO.findByApplyId(id);
			}
			info = convertDO2Info(DO);
		}
		if (fStampApplyFileDO.size() > 0) {
			for (FStampApplyFileDO DO2 : fStampApplyFileDO) {
				stampAFileInfo.add(convertDO2Info(DO2));
			}
		}
		info.setStampAFiles(stampAFileInfo);
		return info;
	}
	
	private StampAFileInfo convertDO2Info(FStampApplyFileDO DO) {
		if (DO == null)
			return null;
		StampAFileInfo info = new StampAFileInfo();
		BeanCopier.staticCopy(DO, info);
		info.setSource(StampSourceEnum.getByCode(DO.getSource()));
		if(DO.getContractCode()!=null){
			FProjectContractItemDO item=fProjectContractItemDAO.findByContractCode(DO.getContractCode());
			info.setCnt(item.getCnt());
		}
		return info;
	}
	
	private StampApplyInfo convertDO2Info(FStampApplyDO DO) {
		if (DO == null)
			return null;
		StampApplyInfo info = new StampApplyInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FormBaseResult save(final StampApplyOrder order) {
		return commonFormSaveProcess(order, "用印申请", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				
				if (order.getApplyId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FStampApplyDO fStampApplyDO = new FStampApplyDO();
					FStampApplyFileDO fStampApplyFileDO = new FStampApplyFileDO();
					BeanCopier.staticCopy(order, fStampApplyDO);
					if("IS".equals(order.getIsReplace())) {
						FStampApplyFileDO fileDO = fStampApplyFileDAO
								.findById(order.getFiles().get(0).getId());
						fStampApplyDO=fStampApplyDAO.findById(fileDO.getApplyId());
						fStampApplyDO.setApplyId(0);
						fStampApplyDO.setStatus("SUBMIT");
					}
					fStampApplyDO.setFormId(formInfo.getFormId());
					fStampApplyDO.setRawAddTime(now);
					fStampApplyDO.setApplyCode(genApplyCode());
					long applyId = fStampApplyDAO.insert(fStampApplyDO);
					if(!"IS".equals(order.getIsReplace())) {
						for (StampAFileOrder stampAFileOrder : order.getFiles()) {
							BeanCopier.staticCopy(stampAFileOrder, fStampApplyFileDO);
							fStampApplyFileDO.setApplyId(applyId);
							fStampApplyFileDO.setOldFileName(stampAFileOrder.getFileName());
							fStampApplyFileDO.setOldLegalChapterNum(stampAFileOrder.getLegalChapterNum());
							fStampApplyFileDO.setOldCachetNum(stampAFileOrder.getCachetNum());
							fStampApplyFileDO.setOldFileContent(stampAFileOrder.getFileConent());
							fStampApplyFileDO.setRawAddTime(now);
							fStampApplyFileDO.setSource(stampAFileOrder.getSource().code());
							long id = fStampApplyFileDAO.insert(fStampApplyFileDO);
						}
					}else{//申请替换更新
						for (StampAFileOrder stampAFileOrder : order.getFiles()) {
							fStampApplyFileDO = fStampApplyFileDAO
									.findById(stampAFileOrder.getId());
							fStampApplyFileDO.setLegalChapterNum(stampAFileOrder.getLegalChapterNum());
							fStampApplyFileDO.setCachetNum(stampAFileOrder.getCachetNum());
							fStampApplyFileDO.setFileName(stampAFileOrder.getFileName());
							fStampApplyFileDO.setFileConent(stampAFileOrder.getFileConent());
							fStampApplyFileDO.setIsReplaceOld(stampAFileOrder.getIsReplaceOld());
							fStampApplyFileDO.setOldFileNum(stampAFileOrder.getOldFileNum());
						    fStampApplyFileDO.setReplaceApplyId(applyId);
						    fStampApplyFileDAO.update(fStampApplyFileDO);
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(applyId);
				} else {
					if("IS".equals(order.getIsReplace())){//申请替换更新
						FStampApplyDO fStampApplyDO = fStampApplyDAO.findById(order.getApplyId());
						if (null == fStampApplyDO) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"未找到用印申请记录");
						}
						//更新用印文件
						for (StampAFileOrder stampAFileOrder : order.getFiles()) {
								FStampApplyFileDO fStampApplyFileDO = fStampApplyFileDAO
										.findById(stampAFileOrder.getId());
								if (null == fStampApplyFileDO) {
									throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
											"未找到用印申请文件记录");
								}
							fStampApplyFileDO.setOldFileNum(stampAFileOrder.getOldFileNum());
							fStampApplyFileDO.setIsReplaceOld(stampAFileOrder.getIsReplaceOld());
							fStampApplyFileDO.setFileName(stampAFileOrder.getFileName());
							fStampApplyFileDO.setFileConent(stampAFileOrder.getFileConent());
							fStampApplyFileDO.setCachetNum(stampAFileOrder.getCachetNum());
							fStampApplyFileDO.setLegalChapterNum(stampAFileOrder.getLegalChapterNum());
								fStampApplyFileDAO.update(fStampApplyFileDO);
						}

					}else{
					//更新
					FStampApplyDO fStampApplyDO = fStampApplyDAO.findById(order.getApplyId());
					if (null == fStampApplyDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到用印申请记录");
					}
					BeanCopier.staticCopy(order, fStampApplyDO);
					fStampApplyDAO.update(fStampApplyDO);
					List<FStampApplyFileDO> inDataBaseList=fStampApplyFileDAO.findByApplyId(order.getApplyId());
					Map<Long,FStampApplyFileDO> inDataBaseMap= new HashMap<Long,FStampApplyFileDO>();
					for(FStampApplyFileDO DO:inDataBaseList){
						inDataBaseMap.put(DO.getId(), DO);
					}
					//更新用印文件
					for (StampAFileOrder stampAFileOrder : order.getFiles()) {
						if (stampAFileOrder.getId() <= 0) {//不存在就新增
							final Date now = FcsPmDomainHolder.get().getSysDate();
							FStampApplyFileDO fStampApplyFileDO2 = new FStampApplyFileDO();
							BeanCopier.staticCopy(stampAFileOrder, fStampApplyFileDO2);
							fStampApplyFileDO2.setApplyId(order.getApplyId());
							fStampApplyFileDO2.setOldLegalChapterNum(stampAFileOrder.getLegalChapterNum());
							fStampApplyFileDO2.setOldCachetNum(stampAFileOrder.getCachetNum());
							fStampApplyFileDO2.setOldFileName(stampAFileOrder.getFileName());
							fStampApplyFileDO2.setOldFileContent(stampAFileOrder.getFileConent());
							fStampApplyFileDO2.setRawAddTime(now);
							fStampApplyFileDO2.setSource(stampAFileOrder.getSource().code());
							fStampApplyFileDAO.insert(fStampApplyFileDO2);
						} else {
							FStampApplyFileDO fStampApplyFileDO = fStampApplyFileDAO
								.findById(stampAFileOrder.getId());
							if (null == fStampApplyFileDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"未找到用印申请文件记录");
							}

							BeanCopier.staticCopy(stampAFileOrder, fStampApplyFileDO,
								UnBoxingConverter.getInstance());
							fStampApplyFileDO.setApplyId(order.getApplyId());
							fStampApplyFileDO.setOldFileName(stampAFileOrder.getFileName());
							fStampApplyFileDO.setOldLegalChapterNum(stampAFileOrder.getLegalChapterNum());
							fStampApplyFileDO.setOldCachetNum(stampAFileOrder.getCachetNum());
							fStampApplyFileDO.setOldFileContent(stampAFileOrder.getFileConent());
							fStampApplyFileDO.setSource(stampAFileOrder.getSource().code());
							fStampApplyFileDAO.update(fStampApplyFileDO);
							inDataBaseMap.remove(fStampApplyFileDO.getId());
						}
						
					}
					if(inDataBaseMap.size()>0){//删除
						for(long id:inDataBaseMap.keySet()){
							fStampApplyFileDAO.deleteById(id);
							FStampApplyFileDO fileDO=inDataBaseMap.get(id);
							//更新合同、文书、函状态
							if (fileDO.getContractCode() != null||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
									||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
									||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
									||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
									||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
									||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
									||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
								FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
										.getContractCode());
								if(fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
										||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
										||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())){
									itemDO.setContractStatus(ContractStatusEnum.APPROVAL.code());
								}else {
									itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
								}
								fProjectContractItemDAO.update(itemDO);
							}
						}

					}
				}

				}
				return null;
			}
		}, null, null);
	}

	@Override
	public StampAFileInfo findByFieldId(long fileId) {
		FStampApplyFileDO fStampApplyFileDO=fStampApplyFileDAO.findById(fileId);
		return convertDO2Info(fStampApplyFileDO);
	}

	/**
	 * 生成申请单编号
	 * @return
	 */
	private synchronized String genApplyCode() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String seqName = SysDateSeqNameEnum.STAMP_APPLY_CODE_SEQ.code() + year ;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String projectCode = DateUtil.shortDate(new Date()).substring(2,DateUtil.shortDate(new Date()).length())
				+ StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return projectCode;
	}
	@Override
	public QueryBaseBatchResult<StampApplyProjectResultInfo> query(StampApplyQueryOrder order,
																	String isApplyList) {
		QueryBaseBatchResult<StampApplyProjectResultInfo> baseBatchResult = new QueryBaseBatchResult<StampApplyProjectResultInfo>();
		
		StampApplyFormProjeactDO queryCondition = new StampApplyFormProjeactDO();
		Date applyTimeStart = null;
		Date applyTimeEnd = null;
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getCustomerName() != null)
			queryCondition.setCustomerName(order.getCustomerName());
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getApplyCode() != null)
			queryCondition.setApplyCode(order.getApplyCode());
		if (order.getFileName() != null)
			queryCondition.setFileName(order.getFileName());
		if (order.getApprovalStatus() != null) {
				queryCondition.setCheckStatus(order.getApprovalStatus());
		}
		if (order.getApplyDate() != null) {
			applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getApplyDate());
			applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getApplyDate());
		}
		if (order.getF_status() != null) {
			queryCondition.setSubmitStatus(order.getF_status());
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		if (order.getApprovalStatus() != null) {
			if(order.getApprovalStatus().startsWith("REPLACE")){
				queryCondition.setFormStatus(order.getApprovalStatus().replace("REPLACE",""));
				queryCondition.setFormCode("REPLACE_STAMP_APPLY");
			}else {
			queryCondition.setFormStatus(order.getApprovalStatus());
				queryCondition.setFormCode("STAMP_APPLY");
			}
		}
		long totalSize = 0;
		if (isApplyList.equals("")) {
			totalSize = extraDAO.searchStampApplyListCount(queryCondition, applyTimeStart,
				applyTimeEnd);
		} else if("ledger".equals(isApplyList)){
			totalSize = extraDAO.searchStampLedgerCount(queryCondition, applyTimeStart,
				applyTimeEnd);
		}
		else if("seal".equals(isApplyList)){
			totalSize = extraDAO.searchStampApplyFormProjeactCount(queryCondition, applyTimeStart,
					applyTimeEnd);
		}
		
		PageComponent component = new PageComponent(order, totalSize);
		List<StampApplyFormProjeactDO> pageList = Lists.newArrayList();
		if (isApplyList.equals("")) {
			pageList = extraDAO.searchStampApplyList(queryCondition, component.getFirstRecord(),
				component.getPageSize(), applyTimeStart, applyTimeEnd, order.getSortCol(), order.getSortOrder());
		} else if("ledger".equals(isApplyList)){
			pageList = extraDAO.searchStampLedger(queryCondition,
				component.getFirstRecord(), component.getPageSize(), applyTimeStart, applyTimeEnd,
					order.getSortCol(), order.getSortOrder());
		}
		else if("seal".equals(isApplyList)){
			pageList = extraDAO.searchStampApplyFormProjeact(queryCondition,
					component.getFirstRecord(), component.getPageSize(), applyTimeStart, applyTimeEnd,
					order.getSortCol(), order.getSortOrder());
		}
		List<StampApplyProjectResultInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (StampApplyFormProjeactDO apply : pageList) {
				list.add(convertDO2Info(apply));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	private StampApplyProjectResultInfo convertDO2Info(StampApplyFormProjeactDO DO) {
		if (DO == null)
			return null;
		StampApplyProjectResultInfo info = new StampApplyProjectResultInfo();
		BeanCopier.staticCopy(DO, info);
		info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
		if((!"".equals(DO.getReplaceFileName())&&"".equals(DO.getFileName()))||(null!=DO.getReplaceFileName())&&null==DO.getFileName()){
			info.setFileName(DO.getReplaceFileName());
		}
		info.setSource(StampSourceEnum.getByCode(DO.getSource()));
		return info;
	}
	
	@Override
	public FormBaseResult revokeStatusById(final StampApplyOrder order) {
		return commonFormSaveProcess(order, "确认用印", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				FStampApplyDO fStampApplyDO = fStampApplyDAO.findById(order.getApplyId());
				fStampApplyDO.setStatus(order.getStatus());
				fStampApplyDAO.update(fStampApplyDO);
				return null;
			}
		}, null, null);
	}

	@Override
	public FormBaseResult deleteById(final StampApplyOrder order) {
		return commonFormSaveProcess(order, "删除用印", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				fStampApplyDAO.deleteById(order.getApplyId());
				fStampApplyFileDAO.deleteByApplyId(order.getApplyId());
				return null;
			}
		}, null, null);

	}

//	@Override
//	public FormBaseResult updateIsPass(final StampApplyOrder order) {
//		return commonFormSaveProcess(order, "更新通过状态", new BeforeProcessInvokeService() {
//			@Override
//			public Domain before() {
//				FStampApplyDO fStampApplyDO = fStampApplyDAO.findById(order.getApplyId());
//				fStampApplyDO.setIsPass(StampApplyStatusEnum.getByCode(order.getIsPass()));
//				fStampApplyDAO.update(fStampApplyDO);
//				return null;
//			}
//		}, null, null);
//	}
}
