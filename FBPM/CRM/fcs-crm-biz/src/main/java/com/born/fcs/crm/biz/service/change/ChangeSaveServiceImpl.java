package com.born.fcs.crm.biz.service.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.ChangeDetailDO;
import com.born.fcs.crm.dal.dataobject.ChangeListDO;
import com.born.fcs.crm.ws.service.ChangeSaveService;
import com.born.fcs.crm.ws.service.enums.CertTypeEnum;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.ChangeDetailInfo;
import com.born.fcs.crm.ws.service.info.ChangeListInfo;
import com.born.fcs.crm.ws.service.order.ChangeDetailOrder;
import com.born.fcs.crm.ws.service.order.ChangeListOrder;
import com.born.fcs.crm.ws.service.order.query.ChangeListQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("changeSaveService")
public class ChangeSaveServiceImpl extends BaseAutowiredDAO implements ChangeSaveService {
	
	@Override
	public FcsBaseResult save(ChangeListOrder changeListOrder) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ChangeListDO changeList = new ChangeListDO();
			BeanCopier.staticCopy(changeListOrder, changeList);
			changeList.setChangeType(changeListOrder.getChangeType().getCode());
			long changeId = changeListDAO.insert(changeList);
			if (ListUtil.isNotEmpty(changeListOrder.getChageDetailList())) {
				for (ChangeDetailOrder info : changeListOrder.getChageDetailList()) {
					if (changeListOrder.getChangeType() == ChangeTypeEnum.LX) {
						//立项没这两个字段
						if ("companyQualification".equals(info.getLableKey()))
							continue;
					}
					ChangeDetailDO changeDetail = new ChangeDetailDO();
					BeanCopier.staticCopy(info, changeDetail);
					changeDetail.setChangeId(changeId);
					changeDetailDAO.insert(changeDetail);
				}
			}
			result.setSuccess(true);
			result.setMessage("保存成功");
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("保存修改记录异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("保存修改记录异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("保存修改记录异常", e);
		}
		
		return result;
	}
	
	@Override
	public ChangeListInfo queryChange(Long changeId) {
		
		try {
			ChangeListInfo result = new ChangeListInfo();
			ChangeListDO listInfo = changeListDAO.findById(changeId);
			if (listInfo != null) {
				ChangeDetailDO changeDetail = new ChangeDetailDO();
				changeDetail.setChangeId(changeId);
				List<ChangeDetailDO> detail = changeDetailDAO.findWithCondition(changeDetail);
				List<ChangeDetailInfo> chageDetailList = new ArrayList<>();
				ChangeDetailInfo rsInfo = null;
				for (ChangeDetailDO info : detail) {
					rsInfo = new ChangeDetailInfo();
					BeanCopier.staticCopy(info, rsInfo);
					chageDetailList.add(rsInfo);
				}
				BeanCopier.staticCopy(listInfo, result);
				result.setChageDetailList(chageDetailList);
			}
			return result;
		} catch (Exception e) {
			
			logger.error("查询修改记录异常", e);
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ChangeListInfo> list(ChangeListQueryOrder queryOrder) {
		QueryBaseBatchResult<ChangeListInfo> result = new QueryBaseBatchResult<ChangeListInfo>();
		ChangeListDO changeList = new ChangeListDO();
		BeanCopier.staticCopy(queryOrder, changeList);
		long count = changeListDAO.countWithCondition(changeList);
		PageComponent component = new PageComponent(queryOrder, count);
		List<ChangeListDO> list = changeListDAO.findWithCondition(changeList,
			component.getFirstRecord(), component.getPageSize());
		ChangeListInfo change = null;
		List<ChangeListInfo> pageList = new ArrayList<>();
		for (ChangeListDO info : list) {
			change = new ChangeListInfo();
			BeanCopier.staticCopy(info, change);
			change.setChangeType(ChangeTypeEnum.getMsgByCode(info.getChangeType()));
			pageList.add(change);
		}
		result.setPageList(pageList);
		result.initPageParam(component);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public ChangeResult createChangeOrder(Object newInfo, Object oldInfo,
											HashMap<String, String> lableNames, Boolean onlyContaint) {
		
		ChangeResult result = new ChangeResult();
		if (newInfo != null && oldInfo != null) {
			if (newInfo.getClass() == oldInfo.getClass()) {
				Map<String, String> newInfoMap = CrmCommonUtil.beanToMap(newInfo);
				Map<String, String> oldInfoMap = CrmCommonUtil.beanToMap(oldInfo);
				
				if (!newInfoMap.isEmpty() && !oldInfoMap.isEmpty()) {
					String customerType = newInfoMap.get("customerType");
					String certType = newInfoMap.get("certType");
					boolean isCompany = false;//企业客户
					boolean isCert = false;//个人客户证件类型是身份证
					if (StringUtil.isNotEmpty(customerType)
						&& customerType.equals(CustomerTypeEnum.ENTERPRISE.code())) {
						isCompany = true;
					} else if (StringUtil.isNotEmpty(certType)
								&& certType.equals(CertTypeEnum.IDENTITY_CARD.code())) {
						isCert = true;
					}
					boolean haveName = false;
					if (lableNames != null && !lableNames.isEmpty()) {
						//有字段名注释的在修改order附上名
						haveName = true;
					}
					
					Object[] newInfoArr = newInfoMap.keySet().toArray();
					Arrays.sort(newInfoArr);
					List<ChangeDetailOrder> list = new ArrayList<>();
					ChangeDetailOrder order = null;
					for (int i = 0; i < newInfoArr.length; i++) {
						
						String nowKey = (String) newInfoArr[i];
						if (!(onlyContaint != null && onlyContaint && lableNames
							.containsKey(nowKey))) {
							continue;
						}
						String oldValue = "";
						if (oldInfoMap.get(nowKey) != null) {
							oldValue = String.valueOf(oldInfoMap.get(nowKey));
						}
						String newValue = "";
						if (newInfoMap.get(nowKey) != null) {
							newValue = String.valueOf(newInfoMap.get(nowKey));
						}
						if ((StringUtil.isNotBlank(oldValue) || StringUtil.isNotBlank(newValue))
							&& StringUtil.notEquals(newValue, oldValue)) {
							order = new ChangeDetailOrder();
							order.setLableKey(nowKey);
							order.setOldValue(StringUtil.defaultIfBlank(oldValue, "--"));
							order.setNewValue(StringUtil.defaultIfBlank(newValue, "--"));
							if ((StringUtil.isBlank(order.getOldValue()) && StringUtil.equals(
								order.getNewValue(), "0.00"))
								|| (StringUtil.isBlank(order.getNewValue()) && StringUtil.equals(
									order.getOldValue(), "0.00"))) {
								continue;
							}
							if (StringUtil.equals(nowKey, "companyQualification")
								|| StringUtil.equals(nowKey, "companyOwnershipStructure")
								|| StringUtil.equals(nowKey, "reqList")) {
								order.setOldValue("--");
								order.setNewValue("有修改");
							}
							if (haveName && lableNames.containsKey(nowKey)) {
								String name = lableNames.get(nowKey);
								//是否多用字段
								if (StringUtil.isNotBlank(name) && name.indexOf("&") > -1) {
									if (isCompany)
										name = name.split("&")[0];
									else
										name = name.split("&")[1];
								}
								if (!isCert) {
									if (nowKey.equals("certImgFont")) {
										name = "证照上传图片";
									}
								}
								
								order.setLableName(name);
							}
							list.add(order);
						}
					}
					if (ListUtil.isNotEmpty(list)) {
						result.setChangeOrder(list);
						result.setSuccess(true);
						result.setMessage("修改数据对比成功");
					} else {
						result.setSuccess(false);
						result.setMessage("无修改数据");
					}
				} else {
					result.setSuccess(false);
					result.setMessage("对象为空不能比较");
				}
			} else {
				result.setSuccess(false);
				result.setMessage("不同类型的对象都不能比较");
			}
		} else {
			result.setSuccess(false);
			result.setMessage("相互比较的对象都不能为空");
		}
		
		return result;
		
	}
}
