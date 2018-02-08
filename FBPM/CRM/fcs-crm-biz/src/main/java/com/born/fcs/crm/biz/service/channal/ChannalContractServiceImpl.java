package com.born.fcs.crm.biz.service.channal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.exception.ExceptionFactory;
import com.born.fcs.crm.biz.service.Date.SqlDateService;
import com.born.fcs.crm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.crm.dal.daointerface.ChannalContractDAO;
import com.born.fcs.crm.dal.daointerface.ChannalInfoDAO;
import com.born.fcs.crm.dal.dataobject.ChannalContractDO;
import com.born.fcs.crm.dal.dataobject.ChannalInfoDO;
import com.born.fcs.crm.domain.context.FcsCrmDomainHolder;
import com.born.fcs.crm.ws.service.ChannalContractService;
import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.order.ChannalContractOrder;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("channalContractService")
public class ChannalContractServiceImpl extends BaseFormAutowiredDomainService implements
																				ChannalContractService {
	@Autowired
	private ChannalContractDAO channalContractDAO;
	@Autowired
	private SqlDateService sqlDateService;
	@Autowired
	private ChannalService channalService;
	@Autowired
	private ChannalInfoDAO channalInfoDAO;
	
	@Override
	public FcsBaseResult save(final ChannalContractOrder order) {
		//数据完整性校验
		try {
			order.checkStatus();
			order.setCheckStatus(1);
		} catch (Exception e) {
			order.setCheckStatus(0);
			logger.info("渠道合同信息保存不完整，存为暂存数据order={}", order);
		}
		
		order.setFormCode(FormCodeEnum.CHANNAL_CONTRACT);
		return commonFormSaveProcess(order, "保存渠道合同信息 ", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FormInfo form = (FormInfo) FcsCrmDomainHolder.get().getAttribute("form");
				ChannalContractDO channalContract = new ChannalContractDO();
				BeanCopier.staticCopy(order, channalContract);
				if (order.isNewChannal() && (order.getFormId() == null || order.getFormId() == 0)) {
					//重新生成渠道号
					order.setChannalCode(channalService.createChannalCode(order.getChannalType()));
				}
				if (StringUtil.isNotBlank(order.getChannalCode())) {
					if (StringUtil.isBlank(order.getContractNo())
						|| order.getContractNo().indexOf(order.getChannalCode()) == -1)
						//合同号为空或渠道号改变重新生成
						channalContract.setContractNo(getContracNo(order.getChannalCode()));
					
				}
				if (order.getFormId() != null && order.getFormId() > 0) {
					channalContract.setFormId(order.getFormId());
					channalContractDAO.updateById(channalContract);
				} else {
					if (form == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
					}
					channalContract.setFormId(form.getFormId());
					channalContract.setRawAddTime(sqlDateService.getSqlDate());
					channalContractDAO.insert(channalContract);
				}
				FormBaseResult result = (FormBaseResult) FcsCrmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(form.getFormId());
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ChannalContractInfo> list(ChannalContractQueryOrder queryOrder) {
		QueryBaseBatchResult<ChannalContractInfo> result = new QueryBaseBatchResult<ChannalContractInfo>();
		ChannalContractDO channalContract = new ChannalContractDO();
		BeanCopier.staticCopy(queryOrder, channalContract);
		long count = channalContractDAO.countWithCondition(channalContract,
			queryOrder.getStartDate(), queryOrder.getEndDate(), queryOrder.getLikeChannalName(),
			queryOrder.getLikeChannalCode());
		PageComponent pageComponent = new PageComponent(queryOrder, count);
		List<ChannalContractDO> list = channalContractDAO.findWithCondition(channalContract,
			pageComponent.getFirstRecord(), pageComponent.getPageSize(), queryOrder.getStartDate(),
			queryOrder.getEndDate(), queryOrder.getLikeChannalName(),
			queryOrder.getLikeChannalCode());
		List<ChannalContractInfo> pageList = new ArrayList<>();
		if (ListUtil.isNotEmpty(list)) {
			ChannalContractInfo info = null;
			for (ChannalContractDO infos : list) {
				info = new ChannalContractInfo();
				BeanCopier.staticCopy(infos, info);
				info.setChannalType(ChanalTypeEnum.getMsgByCode(infos.getChannalType()));
				pageList.add(info);
			}
			
		}
		result.setSuccess(true);
		result.setPageList(pageList);
		result.setMessage("查询合同列表成功");
		result.initPageParam(pageComponent);
		return result;
	}
	
	@Override
	public ChannalContractInfo info(long formId) {
		ChannalContractInfo channalContractInfo = null;
		try {
			ChannalContractDO formDO = channalContractDAO.findById(formId);
			if (formDO != null) {
				channalContractInfo = new ChannalContractInfo();
				BeanCopier.staticCopy(formDO, channalContractInfo);
				channalContractInfo.setChannalType(ChanalTypeEnum.getMsgByCode(channalContractInfo
					.getChannalType()));
			}
		} catch (Exception e) {
			logger.error("查询渠道合同异常formId={},e:", formId, e);
		}
		return channalContractInfo;
	}
	
	/** 生成合同号 */
	private String getContracNo(String channalCode) {
		String maxNo = channalContractDAO.getMaxContractNo(channalCode);
		String nextNo = channalCode;
		if (maxNo != null) {
			long next = Long.parseLong(maxNo.replace(channalCode, "")) + 1;
			String counts = String.valueOf(next);
			for (int i = counts.length(); i < 3; i++) {
				nextNo += "0";
			}
			nextNo += counts;
		} else {
			nextNo += "001";
		}
		return nextNo;
	}
	
	@Override
	public FcsBaseResult updateStatus(String contractNo, ContractStatusEnum status) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ChannalContractDO info = channalContractDAO.findByNO(contractNo);
			int upRsult = channalContractDAO.updateStatus(info.getFormId(), status.getCode());
			if (upRsult > 0) {
				result.setSuccess(true);
				result.setMessage("渠道合同状态更新成功");
				if (status == ContractStatusEnum.RETURN) {
					//合同审核通过
					ChannalInfoDO channalInfo = new ChannalInfoDO();
					channalInfo.setChannelCode(info.getChannalCode());
					long count = channalInfoDAO.countWithCondition(channalInfo, null, null, null);
					if (count == 0) {
						
						ChannalOrder order = new ChannalOrder();
						order.setChannelName(info.getChannalName());
						order.setChannelCode(info.getChannalCode());
						order.setChannelType(info.getChannalType());
						order.setEnclosureUrl(info.getContract());
						order.setInputPerson(info.getUserName());
						order.setStatus("off");
						order.setContractNo(contractNo);
						FcsBaseResult addResult = channalService.add(order);
						logger.info("合同回传，生成新的渠道结果result={}", addResult);
					}
				}
			} else {
				result.setSuccess(false);
				result.setMessage("渠道合同状态更新失败");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("更新渠道合同状态异常：", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult returnContract(long formId, String contractReturn) {
		FcsBaseResult result = new FcsBaseResult();
		
		ChannalContractDO channalContract = channalContractDAO.findById(formId);
		if (channalContract != null) {
			if (StringUtil.equals(channalContract.getStatus(), ContractStatusEnum.SEAL.code())) {
				String oldUrl = channalContract.getContractReturn();
				channalContract.setContractReturn(contractReturn);
				channalContractDAO.updateById(channalContract);
				result = updateStatus(channalContract.getContractNo(), ContractStatusEnum.RETURN);
				if (result.isSuccess()) {
					result.setMessage("回传合同成功");
					result.setSuccess(true);
				} else {
					channalContract.setContractReturn(oldUrl);
					channalContractDAO.updateById(channalContract);
					result.setMessage("更新失败");
					result.setSuccess(false);
				}
			} else {
				result.setMessage("合同状态异常");
				result.setSuccess(false);
			}
			
		} else {
			result.setMessage("未找到该合同");
			result.setSuccess(false);
		}
		return result;
	}
}
