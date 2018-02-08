package com.born.fcs.crm.biz.service.evaluating.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.exception.ExceptionFactory;
import com.born.fcs.crm.biz.service.Date.SqlDateService;
import com.born.fcs.crm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.crm.dal.daointerface.CustomerBaseInfoDAO;
import com.born.fcs.crm.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.fcs.crm.dal.daointerface.EvaluetingListDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerCompanyDetailDO;
import com.born.fcs.crm.dal.dataobject.EvaluetingListDO;
import com.born.fcs.crm.domain.context.FcsCrmDomainHolder;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("evaluetingAuditService")
public class EvaluetingAuditServiceImpl extends BaseFormAutowiredDomainService implements
																				EvaluetingAuditService {
	
	@Autowired
	private EvaluetingListDAO evaluetingListDAO;
	@Autowired
	private SqlDateService sqlDateService;
	@Autowired
	private CustomerBaseInfoDAO customerBaseInfoDAO;
	@Autowired
	private CustomerCompanyDetailDAO customerCompanyDetailDAO;
	
	@Override
	public FormBaseResult save(final EvaluetingOrder order) {
		//保存数据时生成formId
		order.setRelatedProjectCode(order.getProjectCode());
		order.setFormCode(FormCodeEnum.EVALUETING_CUSTOMER);
		return commonFormSaveProcess(order, "保存评级信息 ", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FormInfo form = (FormInfo) FcsCrmDomainHolder.get().getAttribute("form");
				EvaluetingListDO evaluetingList = new EvaluetingListDO();
				String customerId = order.getCustomerId();
				CompanyCustomerInfo customerInfo = querUser(customerId);
				BeanCopier.staticCopy(customerInfo, evaluetingList);
				BeanCopier.staticCopy(order, evaluetingList);
				evaluetingList.setYear(sqlDateService.getYear());
				evaluetingList.setIsProsecute(StringUtil.defaultIfBlank(order.getIsProsecute(),
					BooleanEnum.NO.code()));
				if (order.getFormId() != null && order.getFormId() > 0) {
					order.setFormId(order.getFormId());
					evaluetingList.setFormId(order.getFormId());
					if (StringUtil.isBlank(order.getLevel())) {
						EvaluetingListDO doInfo = evaluetingListDAO.findById(order.getFormId());
						evaluetingList.setLevel(doInfo.getLevel());
					}
					evaluetingListDAO.updateById(evaluetingList);
				} else {
					if (form == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
					}
					//					order.setFormId(order.getFormId());
					evaluetingList.setFormId(form.getFormId());
					evaluetingList.setRawAddTime(sqlDateService.getSqlDate());
					evaluetingListDAO.insert(evaluetingList);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult auditResult(EvaluetingOrder order) {
		
		return null;
	}
	
	/** 查询客户 */
	private CompanyCustomerInfo querUser(String customerId) {
		CompanyCustomerInfo companyCustomerInfo = new CompanyCustomerInfo();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(customerId);
		CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
			.findByCustomerId(customerId);
		BeanCopier.staticCopy(customerBaseInfoDO, companyCustomerInfo);
		BeanCopier.staticCopy(customerCompanyDetailDO, companyCustomerInfo);
		return companyCustomerInfo;
	}
	
}
