package com.born.fcs.crm.biz.service.channal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.ChannalInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.ws.service.ChannalContractService;
import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.ListDataSaveService;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.ListDataInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("channalService")
public class ChannalServiceImpl extends BaseAutowiredDAO implements ChannalService {
	
	@Autowired
	private ListDataSaveService listDataSaveService;
	@Autowired
	private ChannalContractService channalContractService;
	
	@Override
	public FcsBaseResult add(ChannalOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			ChannalInfoDO channalInfo = new ChannalInfoDO();
			BeanCopier.staticCopy(order, channalInfo);
			channalInfo.setRawAddTime(sqlDateService.getSqlDate());
			
			if (StringUtil.isNotBlank(channalInfo.getContractNo())) {
				//使用合同 合同状态改成 已使用 
				channalContractService.updateStatus(channalInfo.getContractNo(),
					ContractStatusEnum.INVALID);
				
			} else if (StringUtil.notEquals(order.getIsXuQian(), BooleanEnum.IS.code())) {
				//不是续签的和没使用合同的 生成渠道号
				channalInfo.setChannelCode(createChannalCode(order.getChannelType()));
			}
			long id = channalInfoDAO.insert(channalInfo);
			listDataSaveService.updateByList(
				saveType(order.getListData(), id, order.getChannelCode()), 0,
				type(id, channalInfo.getChannelCode()));
			result.setSuccess(true);
			result.setMessage("新增渠道成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
			
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("新增渠道异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("新增渠道异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("新增渠道异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult update(ChannalOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			ChannalInfoDO DOInfo = channalInfoDAO.findById(order.getId());
			String oldContractNo = DOInfo.getContractNo();
			BeanCopier.staticCopy(order, DOInfo);
			boolean temporary = true;//暂存
			if (StringUtil.notEquals(order.getIsTemporary(), BooleanEnum.IS.code())) {
				// 如果是保存
				temporary = false;
				ChannalInfoDO channalInfo = new ChannalInfoDO();
				channalInfo.setChannelCode(order.getChannelCode());
				//其他渠道号相同的数据变为历史数据
				channalInfo.setIsHistory(BooleanEnum.IS.code());
				channalInfoDAO.updateHistoryStatusByChannalCode(channalInfo);
				//当前数据变为正式数据
				order.setIsHistory(BooleanEnum.NO.code());
			}
			
			if (StringUtil.equals(order.getIsXuQian(), BooleanEnum.IS.code())) {
				//1.续签 ：新增一条数据
				if (temporary) {
					//暂存为历史数据
					order.setIsHistory(BooleanEnum.IS.code());
				}
				order.setId(0);
				result = add(order);
				if (result.isSuccess()) {
					result.setMessage("续签数据保存成功");
				}
			} else {
				//2.普通更新
				channalInfoDAO.updateById(DOInfo);
				listDataSaveService.updateByList(
					saveType(order.getListData(), order.getId(), order.getChannelCode()), 0,
					type(DOInfo.getId(), DOInfo.getChannelCode()));
				result.setSuccess(true);
				result.setMessage("更新渠道成功！");
				result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
				if (StringUtil.notEquals(order.getContractNo(), oldContractNo)) {
					//更改合同
					channalContractService.updateStatus(order.getContractNo(),
						ContractStatusEnum.INVALID);
					//释放先前合同
					channalContractService.updateStatus(oldContractNo, ContractStatusEnum.RETURN);
					
				}
			}
			
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新渠道异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新渠道异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新渠道异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult updateStatus(long id, String status) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ChannalInfoDO info = channalInfoDAO.findById(id);
			if (info != null) {
				ChannalInfoDO channalInfo = new ChannalInfoDO();
				channalInfo.setStatus(status);
				//渠道号不为空，更新渠道号相同的所有数据状态
				if (StringUtil.isNotBlank(info.getChannelCode())) {
					channalInfo.setChannelCode(info.getChannelCode());
					channalInfoDAO.updateStatusByChannalCode(channalInfo);
				} else {
					channalInfo.setId(id);
					channalInfoDAO.updateById(channalInfo);
				}
				
				result.setSuccess(true);
				result.setMessage("更新渠道状态成功！");
			} else {
				result.setSuccess(false);
				result.setMessage("渠道不存在或已删除！");
			}
			
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新渠道状态异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新渠道状态异常", e);
		}
		return result;
	}
	
	@Override
	public ChannalInfo queryById(long id) {
		ChannalInfoDO DOInfo = channalInfoDAO.findById(id);
		if (DOInfo != null) {
			ChannalInfo info = new ChannalInfo();
			BeanCopier.staticCopy(DOInfo, info);
			queryContactedPerson(info);
			return info;
		}
		return null;
	}
	
	@Override
	public ChannalInfo queryByChannalCode(String channalCode) {
		ChannalInfoDO channalInfo = new ChannalInfoDO();
		channalInfo.setChannelCode(channalCode);
		channalInfo.setIsHistory(BooleanEnum.NO.code());
		List<ChannalInfoDO> list = channalInfoDAO.findWithCondition(channalInfo, 0, 10, null, null,
			null);
		if (ListUtil.isNotEmpty(list)) {
			ChannalInfo info = new ChannalInfo();
			BeanCopier.staticCopy(list.get(0), info);
			queryContactedPerson(info);
			return info;
		}
		return null;
	}
	
	@Override
	public ChannalInfo queryByChannalName(String channelName) {
		ChannalInfoDO channalInfo = new ChannalInfoDO();
		channalInfo.setChannelName(channelName);
		channalInfo.setIsHistory(BooleanEnum.NO.code());
		List<ChannalInfoDO> list = channalInfoDAO.findWithCondition(channalInfo, 0, 10, null, null,
			null);
		if (ListUtil.isNotEmpty(list)) {
			ChannalInfo info = new ChannalInfo();
			BeanCopier.staticCopy(list.get(0), info);
			queryContactedPerson(info);
			return info;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ChannalInfo> list(ChannalQueryOrder queryOrder) {
		QueryBaseBatchResult<ChannalInfo> result = new QueryBaseBatchResult<ChannalInfo>();
		ChannalInfoDO channalInfoDo = new ChannalInfoDO();
		BeanCopier.staticCopy(queryOrder, channalInfoDo);
		long count = channalInfoDAO.countWithCondition(channalInfoDo,
			queryOrder.getLikeChannelName(), queryOrder.getLikeChannelCode(),
			queryOrder.getLikeCodeOrName());
		PageComponent component = new PageComponent(queryOrder, count);
		List<ChannalInfoDO> list = channalInfoDAO.findWithCondition(channalInfoDo,
			component.getFirstRecord(), component.getPageSize(), queryOrder.getLikeChannelName(),
			queryOrder.getLikeChannelCode(), queryOrder.getLikeCodeOrName());
		ChannalInfo channalInfo = null;
		List<ChannalInfo> pageList = new ArrayList<>();
		for (ChannalInfoDO info : list) {
			channalInfo = new ChannalInfo();
			BeanCopier.staticCopy(info, channalInfo);
			queryContactedPerson(channalInfo);
			pageList.add(channalInfo);
		}
		result.setPageList(pageList);
		result.initPageParam(component);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			boolean canDel = true;
			String message = "删除渠道成功！";
			List<ChannalInfo> channalList = queryAll(id, null);
			if (ListUtil.isNotEmpty(channalList) && channalList.size() > 1) {
				canDel = false;
				message = "该渠道已有续签数据，不允许删除！";
			}
			if (canDel) {
				ChannalInfoDO infos = channalInfoDAO.findById(id);
				if (StringUtil.isNotBlank(infos.getContractNo())) {
					canDel = false;
					message = "该渠道已关联合同，不允许删除！";
				}
			}
			if (canDel) {
				CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
				customerBaseInfo.setChannalId(id);
				List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(
					customerBaseInfo, 0, 10, null, null, null, null, null, null, 0, null, null);
				if (ListUtil.isNotEmpty(list)) {
					message = "该渠道已有关联客户，不允许删除！";
					canDel = false;
				}
			}
			if (canDel) {
				FcsBaseResult used = crmUseServiceClient.channalUsed(id);
				if (used.isSuccess()) {
					message = "该渠道已关联项目，不允许删除！";
					canDel = false;
				}
				
			}
			
			if (canDel) {
				channalInfoDAO.deleteById(id);
				result.setSuccess(true);
				result.setMessage(message);
			} else {
				result.setSuccess(false);
				result.setMessage(message);
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除渠道异常！");
			logger.error("删除渠道异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateCreditAmountUsedById(long id, Money creditAmountUsed) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ChannalInfoDO channalInfo = new ChannalInfoDO();
			channalInfo.setId(id);
			channalInfo.setCreditAmountUsed(creditAmountUsed);
			channalInfoDAO.updateCreditAmountUsedById(channalInfo);
			result.setSuccess(true);
			result.setMessage("更新已用授信额度成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除渠道异常！");
			logger.error("更新已用授信额度异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateRemindStatus(long id, String isRemind) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ChannalInfoDO channalInfo = new ChannalInfoDO();
			channalInfo.setId(id);
			channalInfo.setIsRemind(isRemind);
			channalInfoDAO.updateRemindStatus(channalInfo);
			result.setSuccess(true);
			result.setMessage("更新到期提醒成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新到期提醒异常！");
			logger.error("更新到期提醒异常：", e);
		}
		
		return result;
	}
	
	@Override
	public List<ChannalInfo> queryAll(Long id, String channelCode) {
		List<ChannalInfo> result = new ArrayList<>();
		try {
			ChannalInfoDO channalInfo = new ChannalInfoDO();
			channalInfo.setId(id);
			channalInfo.setChannelCode(channelCode);
			List<ChannalInfoDO> list = channalInfoDAO.findAllByChannelCodeOrId(channalInfo);
			if (ListUtil.isNotEmpty(list)) {
				ChannalInfo info = null;
				for (ChannalInfoDO dos : list) {
					info = new ChannalInfo();
					BeanCopier.staticCopy(dos, info);
					queryContactedPerson(info);
					result.add(info);
				}
			}
		} catch (Exception e) {
			logger.error("查询渠道历史数据失败：", e);
		}
		
		return result;
	}
	
	/** 000001-999999 */
	@Override
	public String createChannalCode(String channalType) {
		String channalCode = ChanalTypeEnum.getPreCodeByCode(channalType);
		try {
			
			//取合同表中渠道号最大值
			String maxCode1 = channalContractDAO.getMaxChannalCode(channalType);
			
			//取渠道表中合同
			String maxCode2 = channalInfoDAO.findMaxCode(channalType);
			long code1 = 0;
			if (maxCode1 != null)
				code1 = Long.parseLong(maxCode1.substring(1));
			long code2 = 0;
			if (maxCode2 != null)
				code2 = Long.parseLong(maxCode2.substring(1));
			
			String endStr = String.valueOf(Math.max(code1, code2) + 1);
			for (int i = endStr.length(); i < 5; i++) {
				channalCode += "0";
			}
			channalCode += endStr;
			
		} catch (Exception e) {
			logger.error("生成渠道编号异常:", e);
		}
		
		return channalCode;
	}
	
	/**
	 * 查询联系人信息 渠道联系人type= "channal"+channalCode
	 * */
	private void queryContactedPerson(ChannalInfo channalInfo) {
		List<ListDataInfo> list = listDataSaveService.list(type(channalInfo.getId(),
			channalInfo.getChannelCode()));
		channalInfo.setListData(list);
		
	}
	
	/** 设置联系人保存类型 **/
	private List<ListDataInfo> saveType(List<ListDataInfo> list, long id, String channalCode) {
		if (ListUtil.isNotEmpty(list)) {
			for (ListDataInfo info : list) {
				info.setDataType(type(id, channalCode));
			}
		}
		return list;
		
	}
	
	/** 类型 **/
	private String type(long id, String channalCode) {
		
		return "channal" + channalCode + id;
		
	}
	
}
