package com.born.fcs.pm.biz.convert;

import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.util.FormUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 将DO转化为其它对象
 * 
 * @author lirz
 *
 * 2016-4-20 下午4:07:58
 */
public class DoConvert {
	
	public static ProjectInfo convertProjectDO2Info(ProjectDO DO) {
		if (DO == null)
			return null;
		ProjectInfo info = new ProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setPhases(ProjectPhasesEnum.getByCode(DO.getPhases()));
		info.setStatus(ProjectStatusEnum.getByCode(DO.getStatus()));
		info.setPhasesStatus(ProjectPhasesStatusEnum.getByCode(DO.getPhasesStatus()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(DO.getIsMaximumAmount()));
		info.setIsContinue(BooleanEnum.getByCode(DO.getIsContinue()));
		info.setIsApproval(BooleanEnum.getByCode(DO.getIsApproval()));
		info.setIsApprovalDel(BooleanEnum.getByCode(DO.getIsApprovalDel()));
		info.setIsRecouncil(BooleanEnum.getByCode(DO.getIsRecouncil()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setIsCourtRuling(BooleanEnum.getByCode(DO.getIsCourtRuling()));
		info.setBelongToNc(BooleanEnum.getByCode(DO.getBelongToNc()));
		info.setIsRedo(BooleanEnum.getByCode(DO.getIsRedo()));
		info.setIsRedoProject(BooleanEnum.getByCode(DO.getIsRedoProject()));
		return info;
	}
	
	public static FormInfo convertFormInfo(FormDO form) {
		FormInfo formInfo = new FormInfo();
		BeanCopier.staticCopy(form, formInfo);
		formInfo.setFormCode(FormCodeEnum.getByCode(form.getFormCode()));
		formInfo.setStatus(FormStatusEnum.getByCode(form.getStatus()));
		formInfo.setFormExecuteInfo(FormUtil.parseTaskUserData(form.getTaskUserData()));
		return formInfo;
	}
	
	public static ProjectFinancialInfo convertFinancialProjectInfo(ProjectFinancialDO DO) {
		if (DO == null) {
			return null;
		}
		ProjectFinancialInfo info = new ProjectFinancialInfo();
		BeanCopier.staticCopy(DO, info);
		info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
		info.setTermType(FinancialProductTermTypeEnum.getByCode(DO.getTermType()));
		info.setInterestType(FinancialProductInterestTypeEnum.getByCode(DO.getInterestType()));
		info.setInterestSettlementWay(InterestSettlementWayEnum.getByCode(DO
			.getInterestSettlementWay()));
		info.setStatus(FinancialProjectStatusEnum.getByCode(DO.getStatus()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setCanRedeem(BooleanEnum.getByCode(DO.getCanRedeem()));
		info.setIsRoll(BooleanEnum.getByCode(DO.getIsRoll()));
		info.setIsOpen(BooleanEnum.getByCode(DO.getIsOpen()));
		return info;
	}
}
