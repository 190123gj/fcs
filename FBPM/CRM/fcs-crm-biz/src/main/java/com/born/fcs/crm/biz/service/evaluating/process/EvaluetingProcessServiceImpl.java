package com.born.fcs.crm.biz.service.evaluating.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.biz.service.Date.SqlDateService;
import com.born.fcs.crm.biz.service.base.BaseProcessService;
import com.born.fcs.crm.dal.daointerface.CustomerBaseInfoDAO;
import com.born.fcs.crm.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.fcs.crm.dal.daointerface.CustomerInfoForEvalueDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingBaseDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingLevelDAO;
import com.born.fcs.crm.dal.daointerface.EvaluetingListDAO;
import com.born.fcs.crm.dal.daointerface.EvaluetingListForAuditDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.EvaluatingLevelDO;
import com.born.fcs.crm.dal.dataobject.EvaluetingListDO;
import com.born.fcs.crm.dal.dataobject.EvaluetingListForAuditDO;
import com.born.fcs.crm.integration.bpm.result.WorkflowResult;
import com.born.fcs.crm.integration.pm.service.ProjectRelatedUserServicePmClient;
import com.born.fcs.crm.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.crm.ws.service.CompanyOwnershipStructureService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.bornsoft.pub.enums.CreditLevelEnum;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder.CustomLevelInfo;
import com.bornsoft.utils.base.BornSynResultBase;

@Service("evaluetingProcessService")
public class EvaluetingProcessServiceImpl extends BaseProcessService {
	@Autowired
	EvaluetingAuditService evaluetingAuditService;
	@Autowired
	EvaluetingListDAO evaluetingListDAO;
	@Autowired
	EvaluetingListForAuditDAO evaluetingListForAuditDAO;
	@Autowired
	CustomerInfoForEvalueDAO customerInfoForEvalueDAO;
	@Autowired
	CustomerBaseInfoDAO customerBaseInfoDAO;
	@Autowired
	CustomerCompanyDetailDAO customerCompanyDetailDAO;
	@Autowired
	CompanyOwnershipStructureService companyOwnershipStructureService;
	@Autowired
	EvaluatingLevelDAO evaluatingLevelDAO;
	@Autowired
	SqlDateService sqlDateService;
	@Autowired
	EvaluatingBaseDAO evaluatingBaseDAO;
	
	@Autowired
	protected ProjectRelatedUserServicePmClient projectRelatedUserServiceClient;
	
	@Autowired
	protected RiskSystemFacadeClient riskSystemFacadeClient;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> vars = Lists.newArrayList();
		try {
			//设置风险经理
			EvaluetingListDO info = evaluetingListDAO.findById(formInfo.getFormId());
			ProjectRelatedUserInfo riskManager = projectRelatedUserServiceClient
				.getRiskManager(info.getProjectCode());
			ProjectRelatedUserInfo legalManager = null;
			if (riskManager == null) {
				legalManager = projectRelatedUserServiceClient.getLegalManager(info
					.getProjectCode());
			}
			
			List<FlowVarField> list = Lists.newArrayList();
			FlowVarField field = new FlowVarField();
			field.setVarName("RiskManagerID");
			field.setVarType(FlowVarTypeEnum.STRING);
			if (riskManager != null) {
				field.setVarVal(String.valueOf(riskManager.getUserId()));
			} else if (legalManager != null) {
				field.setVarVal(String.valueOf(legalManager.getUserId()));
			} else {
				field.setVarVal("0");
			}
			list.add(field);
			return list;
		} catch (Exception e) {
			logger.error("评级审核设置流程变量出错：", e);
		}
		return vars;
	}
	
	/** 评级结束处理等级信息 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			//审核通过更新客户评级数据
			if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
				//评价信息
				EvaluetingListForAuditDO info = evaluetingListForAuditDAO.findByFormId(formInfo
					.getFormId());
				//评级修改信息
				//				CustomerInfoForEvalueDO evalue_customerInfo = customerInfoForEvalueDAO
				//					.findById(formInfo.getFormId());
				//原信息
				CustomerBaseInfoDO customerBaseInfo = customerBaseInfoDAO.findByCustomerId(info
					.getCustomerId());
				//				CustomerCompanyDetailDO customerDetailInfo = customerCompanyDetailDAO
				//					.findByCustomerId(info.getCustomerId());
				// 暂不修改原信息
				//				BeanCopier.staticCopy(evalue_customerInfo, customerBaseInfo);
				//				BeanCopier.staticCopy(evalue_customerInfo, customerDetailInfo);
				customerBaseInfo.setCustomerLevel(info.getLevel());
				customerBaseInfo.setEvalueStatus(BooleanEnum.NO.code());
				customerBaseInfoDAO.updateByUserId(customerBaseInfo);
				
				SynCustomLevelOrder arg0 = new SynCustomLevelOrder();
				CustomLevelInfo levelInfo = new CustomLevelInfo();
				levelInfo.setLicenseNo(customerBaseInfo.getBusiLicenseNo());
				levelInfo.setCustomName(customerBaseInfo.getCustomerName());
				levelInfo.setCreditLevel(CreditLevelEnum.getByCode(info.getLevel()));
				List<CustomLevelInfo> list = new ArrayList<>();
				list.add(levelInfo);
				arg0.setList(list);
				arg0.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
				BornSynResultBase synResult = riskSystemFacadeClient.synCustomLevel(arg0);
				logger.info("评级审核通过后同步等级到风险系统结果:{}", synResult);
				
				//				customerDetailInfo.setCustomerId(customerBaseInfo.getCustomerId());
				//				customerCompanyDetailDAO.updateByCustomerId(customerDetailInfo);
				//				companyOwnershipStructureService.updateByList(order.getCompanyOwnershipStructure(),
				//					order.getCustomerId());
				String key = String.valueOf(info.getUserId())
								+ String.valueOf(formInfo.getFormId());
				EvaluatingLevelDO evaluatingLevel = new EvaluatingLevelDO();
				evaluatingLevel.setFormId(formInfo.getFormId());
				evaluatingLevel.setLevel(info.getLevel());
				evaluatingLevel.setUserId(formInfo.getUserId());
				evaluatingLevel.setEvalueKey(key);
				evaluatingLevel.setYear(sqlDateService.getYear());
				evaluatingLevel.setRawAddTime(sqlDateService.getSqlDate());
				evaluatingLevelDAO.insert(evaluatingLevel);
				
			}
			//			else if (formInfo.getStatus() == FormStatusEnum.BACK) {
			//				evaluatingBaseDAO.deleteB
			//			}
			
		} catch (Exception e) {
			logger.error("评级审核结束处理数据异常 ：", e);
		}
	}
}
