package com.born.fcs.am.biz.service.pledgeasset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.convert.UnBoxingConverter;
import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.AssetRelationProjectDO;
import com.born.fcs.am.dal.dataobject.PledgeAssetDO;
import com.born.fcs.am.dal.dataobject.PledgeTypeAttributeDO;
import com.born.fcs.am.dal.dataobject.PledgeTypeCommonDO;
import com.born.fcs.am.dataobject.AssetSimpleDO;
import com.born.fcs.am.intergration.bpm.pm.PmWebServiceClient;
import com.born.fcs.am.ws.enums.AssetRemarkInfoEnum;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeTypeCommonInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.pledgetypeattribute.PledgeTypeAttributeService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("pledgeAssetService")
public class PledgeAssetServiceImpl extends BaseFormAutowiredDomainService implements
																			PledgeAssetService {
	
	@Autowired
	protected PmWebServiceClient pmWebServiceClient;
	@Autowired
	protected PledgeTypeAttributeService pledgeTypeAttributeService;
	
	private PledgeAssetInfo convertDO2Info(PledgeAssetDO DO) {
		if (DO == null)
			return null;
		PledgeAssetInfo info = new PledgeAssetInfo();
		BeanCopier.staticCopy(DO, info);
		info.setStatus(AssetStatusEnum.getByCode(DO.getStatus()));
		return info;
	}
	
	private PledgeTypeCommonInfo convertCommonDO2Info(PledgeTypeCommonDO DO) {
		if (DO == null)
			return null;
		PledgeTypeCommonInfo info = new PledgeTypeCommonInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private AssetRelationProjectInfo convertAssetRelationProjectDO2Info(AssetRelationProjectDO DO) {
		if (DO == null)
			return null;
		AssetRelationProjectInfo info = new AssetRelationProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setAssetsStatus(AssetStatusEnum.getByCode(DO.getAssetsStatus()));
		return info;
	}
	
	@Override
	public PledgeAssetInfo findById(long typeId) {
		PledgeAssetInfo info = null;
		if (typeId > 0) {
			PledgeAssetDO DO = pledgeAssetDAO.findById(typeId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final PledgeAssetOrder order) {
		return commonProcess(order, "保存资产信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getIsView() != null && "true".equals(order.getIsView())) {//是否重详情页面跳转过来的编辑，只修改搜索关键字
					PledgeAssetDO pledgeAsset = pledgeAssetDAO.findById(order.getAssetsId());
					if (pledgeAsset == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产不存在");
					}
					pledgeAsset.setSearchKey(order.getSearchKey());
					pledgeAssetDAO.update(pledgeAsset);
				} else {
					PledgeAssetDO pledgeAsset = null;
					if (order.getAssetsId() != null && order.getAssetsId() > 0) {
						pledgeAsset = pledgeAssetDAO.findById(order.getAssetsId());
						if (pledgeAsset == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"资产不存在");
						}
					}
					
					long assetId = 0L;
					if (pledgeAsset == null) { // 新增
						long typeId = order.getTypeId();
						pledgeAsset = new PledgeAssetDO();
						BeanCopier.staticCopy(order, pledgeAsset, UnBoxingConverter.getInstance());
						pledgeAsset.setRawAddTime(now);
						pledgeAsset.setStatus(AssetStatusEnum.NOT_USED.code());// 新增时都是未使用状态
						assetId = pledgeAssetDAO.insert(pledgeAsset);// 主键
						List<PledgeTypeAttributeOrder> attributeOrders = order
							.getPledgeTypeAttributeOrders();
						if (ListUtil.isNotEmpty(attributeOrders)) {
							for (PledgeTypeAttributeOrder attributeOrder : attributeOrders) {// 将所有的属性插入属性表
								PledgeTypeAttributeDO DO = new PledgeTypeAttributeDO();
								BeanCopier.staticCopy(attributeOrder, DO,
									UnBoxingConverter.getInstance());
								if (attributeOrder.getCustomType() != null
									&& attributeOrder.getCustomType().equals("IMAGE")) {
									DO.setAttributeValue((attributeOrder.getAttributeImageText() == null ? ""
										: attributeOrder.getAttributeImageText())
															+ "&&"
															+ (attributeOrder.getAttributeValue() == null ? ""
																: attributeOrder
																	.getAttributeValue()));//用&&符号分割开
								}
								DO.setAssetsId(assetId);
								DO.setTypeId(typeId);
								pledgeTypeAttributeDAO.insert(DO);
							}
						}
						PledgeTypeCommonDO commonDO = new PledgeTypeCommonDO();
						commonDO.setAssetsId(assetId);
						commonDO.setTypeId(typeId);
						PledgeAssetDO assetDO = pledgeAssetDAO.findById(assetId);
						// 还需将通用属性写进pledge_type_common表中 和写进资产表中
						String assetRemarkInfoStr = "";
						if (ListUtil.isNotEmpty(attributeOrders)) {
							for (PledgeTypeAttributeOrder attributeOrder : attributeOrders) {
								//								if ("权证号".equals(attributeOrder.getAttributeKey())) {
								//									assetDO.setWarrantNo(attributeOrder.getAttributeValue());
								//								}
								
								if ("抵质押率".equals(attributeOrder.getAttributeKey())) {
									commonDO
										.setPledgeRate(attributeOrder.getAttributeValue() == null ? 0.00
											: Double.parseDouble(attributeOrder.getAttributeValue()));
								}
								if ("评估公司".equals(attributeOrder.getAttributeKey())) {
									commonDO.setEvaluationCompany(attributeOrder
										.getAttributeValue());
								}
								if ("评估价格".equals(attributeOrder.getAttributeKey())
									|| "拟质押金额".equals(attributeOrder.getAttributeKey())) {
									commonDO.setEvaluationPrice(Money.amout((attributeOrder
										.getAttributeValue() == null ? "0" : attributeOrder
										.getAttributeValue())));
									assetDO.setEvaluationPrice(Money.amout((attributeOrder
										.getAttributeValue() == null ? "0" : attributeOrder
										.getAttributeValue())));
								}
								if ("抵押价格".equals(attributeOrder.getAttributeKey())) {
									commonDO.setMortgagePrice(Money.amout((attributeOrder
										.getAttributeValue() == null ? "0" : attributeOrder
										.getAttributeValue())));
								}
								
								List<AssetRemarkInfoEnum> listInfoEnum = AssetRemarkInfoEnum
									.getAllEnum();//所有资产标示信息
								for (AssetRemarkInfoEnum assetRemarkInfoEnum : listInfoEnum) {
									if (assetRemarkInfoEnum.getMessage().equals(
										attributeOrder.getAttributeKey())) {
										assetRemarkInfoStr += attributeOrder.getAttributeKey()
																+ "："
																+ (attributeOrder
																	.getAttributeValue() == null ? ""
																	: attributeOrder
																		.getAttributeValue()) + ",";
									}
								}
								
							}
						}
						commonDO.setLatitude(order.getLatitude()); // 经度 纬度
						// 直接保存在通用表中
						commonDO.setLongitude(order.getLongitude());
						commonDO.setRawUpdateTime(now);
						
						if (assetRemarkInfoStr != null && assetRemarkInfoStr.length() > 0) {
							assetRemarkInfoStr = assetRemarkInfoStr.substring(0,
								assetRemarkInfoStr.length() - 1);
						}
						commonDO.setAssetRemarkInfo(assetRemarkInfoStr);
						pledgeTypeCommonDAO.insert(commonDO);
						
						assetDO.setAssetRemarkInfo(assetRemarkInfoStr);
						pledgeAssetDAO.update(assetDO);
						
					} else { // 修改
						PledgeAssetDO assetDO = pledgeAssetDAO.findById(order.getAssetsId());
						long assetsId = assetDO.getAssetsId();
						assetDO.setIsCustomer(order.getIsCustomer());
						assetDO.setOwnershipId(order.getOwnershipId());
						assetDO.setOwnershipName(order.getOwnershipName());
						assetDO.setLatitude(order.getLatitude()); // 经度 纬度
						assetDO.setRemark(order.getRemark());
						assetDO.setAttach(order.getAttach());
						assetDO.setSearchKey(order.getSearchKey());
						
						// 直接保存在通用表中
						assetDO.setLongitude(order.getLongitude());
						List<PledgeTypeAttributeOrder> attributeOrders = order
							.getPledgeTypeAttributeOrders();
						if (ListUtil.isNotEmpty(attributeOrders)) {
							for (PledgeTypeAttributeOrder attributeOrder : attributeOrders) {// 将所有的属性插入属性表
								PledgeTypeAttributeDO DO = pledgeTypeAttributeDAO
									.findById(attributeOrder.getAttributeId());
								BeanCopier.staticCopy(attributeOrder, DO,
									UnBoxingConverter.getInstance());
								if (attributeOrder.getCustomType() != null
									&& attributeOrder.getCustomType().equals("IMAGE")) {
									DO.setAttributeValue((attributeOrder.getAttributeImageText() == null ? ""
										: attributeOrder.getAttributeImageText())
															+ "&&"
															+ (attributeOrder.getAttributeValue() == null ? ""
																: attributeOrder
																	.getAttributeValue()));//用&&符号分割开
								}
								DO.setAssetsId(order.getAssetsId());
								DO.setTypeId(order.getTypeId());
								pledgeTypeAttributeDAO.update(DO);
							}
						}
						PledgeTypeCommonDO commonDO = pledgeTypeCommonDAO.findByAssetsId(assetsId);
						// 还需将通用属性写进pledge_type_common表中 和写进资产表中
						boolean isChanged = false;
						String assetRemarkInfoStr = "";
						if (ListUtil.isNotEmpty(attributeOrders)) {
							for (PledgeTypeAttributeOrder attributeOrder : attributeOrders) {
								//								if ("权证号".equals(attributeOrder.getAttributeKey())) {
								//									assetDO.setWarrantNo(attributeOrder.getAttributeValue());
								//								}
								
								if ("抵质押率".equals(attributeOrder.getAttributeKey())) {
									double rate = attributeOrder.getAttributeValue() == null ? 0.00
										: Double.parseDouble(attributeOrder.getAttributeValue());
									if (!new BigDecimal(rate).equals(new BigDecimal(commonDO
										.getPledgeRate()))) {
										isChanged = true;
									}
									commonDO.setPledgeRate(rate);
								}
								if ("评估公司".equals(attributeOrder.getAttributeKey())) {
									commonDO.setEvaluationCompany(attributeOrder
										.getAttributeValue());
								}
								if ("评估价格".equals(attributeOrder.getAttributeKey())
									|| "拟质押金额".equals(attributeOrder.getAttributeKey())) {
									Money price = Money
										.amout((attributeOrder.getAttributeValue() == null ? "0"
											: attributeOrder.getAttributeValue()));
									if (!price.equals(commonDO.getEvaluationPrice())) {
										isChanged = true;
									}
									commonDO.setEvaluationPrice(price);
									assetDO.setEvaluationPrice(price);
								}
								if ("抵押价格".equals(attributeOrder.getAttributeKey())) {
									commonDO.setMortgagePrice(Money.amout((attributeOrder
										.getAttributeValue() == null ? "0" : attributeOrder
										.getAttributeValue())));
								}
								
								List<AssetRemarkInfoEnum> listInfoEnum = AssetRemarkInfoEnum
									.getAllEnum();//所有资产标示信息
								for (AssetRemarkInfoEnum assetRemarkInfoEnum : listInfoEnum) {
									if (assetRemarkInfoEnum.getMessage().equals(
										attributeOrder.getAttributeKey())) {
										assetRemarkInfoStr += attributeOrder.getAttributeKey()
																+ "："
																+ (attributeOrder
																	.getAttributeValue() == null ? ""
																	: attributeOrder
																		.getAttributeValue()) + ",";
									}
								}
								
							}
						}
						
				
						
						commonDO.setLatitude(order.getLatitude()); // 经度 纬度
						// 直接保存在通用表中
						commonDO.setLongitude(order.getLongitude());
						
						if (assetRemarkInfoStr != null && assetRemarkInfoStr.length() > 0) {
							assetRemarkInfoStr = assetRemarkInfoStr.substring(0,
								assetRemarkInfoStr.length() - 1);
						}
						commonDO.setAssetRemarkInfo(assetRemarkInfoStr);
						pledgeTypeCommonDAO.update(commonDO);
						
						assetDO.setAssetRemarkInfo(assetRemarkInfoStr);
						pledgeAssetDAO.update(assetDO);
						
						if (isChanged) {
							UpdateInvestigationCreditSchemePledgeAssetOrder updateOrder = new UpdateInvestigationCreditSchemePledgeAssetOrder();
							updateOrder.setAssetsId(order.getAssetsId());
							updateOrder.setEvaluationPrice(commonDO.getEvaluationPrice());
							updateOrder.setPledgeRate(commonDO.getPledgeRate());
							updateOrder.setOwnershipName(assetDO.getOwnershipName());
							updateOrder.setAssetRemark(assetDO.getAssetRemarkInfo());
							FcsPmDomainHolder.get().addAttribute("updateOrder", updateOrder);
						}
						
						assetId = order.getAssetsId();
					}
					
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(assetId);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				Object updateOrderObj = FcsPmDomainHolder.get().getAttribute("updateOrder");
				if (null != updateOrderObj
					&& updateOrderObj instanceof UpdateInvestigationCreditSchemePledgeAssetOrder) {
					UpdateInvestigationCreditSchemePledgeAssetOrder updateOrder = (UpdateInvestigationCreditSchemePledgeAssetOrder) updateOrderObj;
					pmWebServiceClient.updatePledgeAsset(updateOrder);
				}
				return null;
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeAssetInfo> query(PledgeAssetQueryOrder order) {
		QueryBaseBatchResult<PledgeAssetInfo> baseBatchResult = new QueryBaseBatchResult<PledgeAssetInfo>();
		
		PledgeAssetDO queryCondition = new PledgeAssetDO();
		List<Long> assetsIdList = Lists.newArrayList();
		if (order.getAssetsIdList() != null) {
			assetsIdList = order.getAssetsIdList();
		}
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getAssetsId() != null)
			queryCondition.setTypeId(order.getTypeId());
		
		long totalSize = pledgeAssetDAO.findByConditionCount(queryCondition, assetsIdList);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<PledgeAssetDO> pageList = pledgeAssetDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortOrder(),
			order.getSortCol(), assetsIdList);
		//		dateChange(pageList);
		List<PledgeAssetInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (PledgeAssetDO pledgeAsset : pageList) {
				list.add(convertDO2Info(pledgeAsset));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	//数据订正--用来订正关键信息
	private void dateChange(List<PledgeAssetDO> listDO) {
		if (ListUtil.isNotEmpty(listDO)) {
			for (PledgeAssetDO pledgeAssetDO : listDO) {
				
				PledgeAssetDO assetDO = pledgeAssetDAO.findById(pledgeAssetDO.getAssetsId());
				long assetsId = assetDO.getAssetsId();
				
				PledgeTypeAttributeTextQueryOrder queryOrder = new PledgeTypeAttributeTextQueryOrder();
				queryOrder.setAssetsId(pledgeAssetDO.getAssetsId());
				
				queryOrder.setCustomType("TEXT");
				QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result = pledgeTypeAttributeService
					.queryAttributeText(queryOrder);
				List<PledgeTypeAttributeTextInfo> textInfoList = result.getPageList();
				PledgeTypeCommonDO commonDO = pledgeTypeCommonDAO.findByAssetsId(assetsId);
				String assetRemarkInfoStr = "";
				if (ListUtil.isNotEmpty(textInfoList)) {
					for (PledgeTypeAttributeTextInfo info : textInfoList) {// 将所有的属性插入属性表
					
						// 还需将通用属性写进pledge_type_common表中 和写进资产表中
						
						List<AssetRemarkInfoEnum> listInfoEnum = AssetRemarkInfoEnum.getAllEnum();//所有资产标示信息
						for (AssetRemarkInfoEnum assetRemarkInfoEnum : listInfoEnum) {
							if (assetRemarkInfoEnum.getMessage().equals(info.getAttributeKey())) {
								assetRemarkInfoStr += info.getAttributeKey()
														+ "："
														+ (info.getAttributeValue() == null ? ""
															: info.getAttributeValue()) + ",";
							}
						}
						
					}
				}
				
				if (assetRemarkInfoStr != null && assetRemarkInfoStr.length() > 0) {
					assetRemarkInfoStr = assetRemarkInfoStr.substring(0,
						assetRemarkInfoStr.length() - 1);
				}
				commonDO.setAssetRemarkInfo(assetRemarkInfoStr);
				pledgeTypeCommonDAO.update(commonDO);
				
				assetDO.setAssetRemarkInfo(assetRemarkInfoStr);
				pledgeAssetDAO.update(assetDO);
				
			}
		}
	}
	
	@Override
	public int deleteById(long typeId) {
		int num = 0;
		if (typeId != 0) {
			num = pledgeAssetDAO.deleteById(typeId);
			pledgeTypeCommonDAO.deleteByAssetsId(typeId);
		}
		return num;
		
	}
	
	@Override
	public PledgeTypeCommonInfo findAssetCommonByAssetsId(long assetsId) {
		PledgeTypeCommonDO DO = pledgeTypeCommonDAO.findByAssetsId(assetsId);
		return convertCommonDO2Info(DO);
	}
	
	@Override
	public QueryBaseBatchResult<AssetSimpleInfo> queryAssetSimple(AssetQueryOrder queryOrder) {
		QueryBaseBatchResult<AssetSimpleInfo> baseBatchResult = new QueryBaseBatchResult<>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("typeId", queryOrder.getTypeId());
		paramMap.put("ownershipName", queryOrder.getOwnershipName());
		paramMap.put("exclusives", queryOrder.getExclusives());
		paramMap.put("assetsIdList", queryOrder.getAssetsIdList());
		paramMap.put("userId", queryOrder.getUserId());
		if (queryOrder.getAssetType() != null) {
			paramMap.put("assetType", queryOrder.getAssetType());
		}
		if (queryOrder.getRalateVideo() != null) {
			paramMap.put("ralateVideo", queryOrder.getRalateVideo());
		}
		if (queryOrder.getAssetRemarkInfo() != null) {
			paramMap.put("assetRemarkInfo", queryOrder.getAssetRemarkInfo());
		}
		long totalSize = extraDAO.searchAssessSimple(paramMap);
		
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			
			List<AssetSimpleDO> pageList = extraDAO.searchAssessSimpleList(paramMap);
			
			List<AssetSimpleInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (AssetSimpleDO doObj : pageList) {
					AssetSimpleInfo info = new AssetSimpleInfo();
					BeanCopier.staticCopy(doObj, info);
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
	public QueryBaseBatchResult<AssetRelationProjectInfo> queryAssetRelationProject(AssetRelationProjectQueryOrder order) {
		QueryBaseBatchResult<AssetRelationProjectInfo> baseBatchResult = new QueryBaseBatchResult<AssetRelationProjectInfo>();
		
		AssetRelationProjectDO queryCondition = new AssetRelationProjectDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		long totalSize = assetRelationProjectDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<AssetRelationProjectDO> pageList = assetRelationProjectDAO.findByCondition(
			queryCondition, order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
			component.getPageSize());
		
		List<AssetRelationProjectInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AssetRelationProjectDO pledgeAsset : pageList) {
				list.add(convertAssetRelationProjectDO2Info(pledgeAsset));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult assetRelationProject(final AssetRelationProjectBindOrder bindOrder) {
		
		return commonProcess(bindOrder, "资产关联项目", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				List<AssetStatusOrder> assetStatusList = bindOrder.getAssetList();
				if (bindOrder.isDelOld()) {
					
					//删除原来的关联资产
					assetRelationProjectDAO.deleteByProjectCode(bindOrder.getProjectCode());
					cancelRelationAssetByProjectCode(bindOrder.getProjectCode());
					//新增资产项目关联关系
					if (ListUtil.isNotEmpty(assetStatusList)) {
						for (AssetStatusOrder as : assetStatusList) {
							AssetRelationProjectDO arp = new AssetRelationProjectDO();
							BeanCopier.staticCopy(bindOrder, arp);
							arp.setAssetsId(as.getAssetId());
							arp.setAssetsStatus(as.getStatus().code());
							arp.setRawAddTime(now);
							if (as.getStatus() == AssetStatusEnum.QUASI_PLEDGE
								|| as.getStatus() == AssetStatusEnum.SECURED_PLEDGE) {//拟抵押
								arp.setAssetFirstType(CreditConditionTypeEnum.PLEDGE.code());
							}
							if (as.getStatus() == AssetStatusEnum.QUASI_MORTGAGE
								|| as.getStatus() == AssetStatusEnum.SECURED_MORTGAGE) {//拟质押
								arp.setAssetFirstType(CreditConditionTypeEnum.MORTGAGE.code());
							}
							AssetRelationProjectDO exits = assetRelationProjectDAO
								.findByAssetsIdAndProjectCodeAndStatusAndFirst(as.getAssetId(),
									bindOrder.getProjectCode(), as.getStatus().code(),
									arp.getAssetFirstType());
							if (exits == null) {
								assetRelationProjectDAO.insert(arp);
								assetRelationProject(as.getAssetId());
							}
							
						}
					}
				} else {
					//修改资产状态
					for (AssetStatusOrder as : assetStatusList) {
						//已经存在的
						List<AssetRelationProjectDO> exits = assetRelationProjectDAO
							.findByAssetsIdAndProjectCodeAndStatus(as.getAssetId(),
								bindOrder.getProjectCode(), as.getStatus().code());
						
						if (exits == null || exits.size() == 0) { //没有就新增
							AssetRelationProjectDO arp = new AssetRelationProjectDO();
							BeanCopier.staticCopy(bindOrder, arp);
							arp.setAssetsId(as.getAssetId());
							arp.setAssetsStatus(as.getStatus().code());
							arp.setRawAddTime(now);
							
							if (as.getStatus() == AssetStatusEnum.QUASI_PLEDGE
								|| as.getStatus() == AssetStatusEnum.SECURED_PLEDGE) {//拟抵押
								arp.setAssetFirstType(CreditConditionTypeEnum.PLEDGE.code());
							}
							if (as.getStatus() == AssetStatusEnum.QUASI_MORTGAGE
								|| as.getStatus() == AssetStatusEnum.SECURED_MORTGAGE) {//拟质押
								arp.setAssetFirstType(CreditConditionTypeEnum.MORTGAGE.code());
							}
							assetRelationProjectDAO.insert(arp);
							assetRelationProject(as.getAssetId());
						} else { //状态变更修改状态
							for (AssetRelationProjectDO assetRelationProjectDO : exits) {
								
								if (as.getStatus() == AssetStatusEnum.QUASI_PLEDGE
									|| as.getStatus() == AssetStatusEnum.SECURED_PLEDGE) {//拟抵押
									assetRelationProjectDO
										.setAssetFirstType(CreditConditionTypeEnum.PLEDGE.code());
								}
								if (as.getStatus() == AssetStatusEnum.QUASI_MORTGAGE
									|| as.getStatus() == AssetStatusEnum.SECURED_MORTGAGE) {//拟质押
									assetRelationProjectDO
										.setAssetFirstType(CreditConditionTypeEnum.MORTGAGE.code());
								}
								
								assetRelationProjectDO.setAssetsStatus(as.getStatus().code());
								assetRelationProjectDAO.update(assetRelationProjectDO);
							}
							
						}
					}
				}
				return null;
			}
		}, null, null);
	}
	
	private void cancelRelationAssetByProjectCode(String projectCode) {
		if (!"".equals(projectCode) && null != projectCode) {
			PledgeAssetQueryOrder order = new PledgeAssetQueryOrder();
			order.setProjectCode(projectCode);
			QueryBaseBatchResult<PledgeAssetInfo> batchResult = query(order);
			List<PledgeAssetInfo> listInfo = batchResult.getPageList();
			for (PledgeAssetInfo pledgeAssetInfo : listInfo) {
				PledgeAssetDO DO = pledgeAssetDAO.findById(pledgeAssetInfo.getAssetsId());
				if (DO.getProjectCode() != null && DO.getProjectCode().contains(projectCode)) {
					DO.setProjectCode(DO.getProjectCode().replace("," + projectCode, ""));
					if ("".equals(DO.getProjectCode())) {
						DO.setProjectCode(null);
					}
				}
				pledgeAssetDAO.update(DO);
			}
		}
	}
	
	private void assetRelationProject(long assetId) {
		//将资产关联项目信息写入资产表中
		PledgeAssetDO assetDO = pledgeAssetDAO.findById(assetId);
		List<AssetRelationProjectDO> listAssetRelationProjectDO = assetRelationProjectDAO
			.findByAssetsId(assetId);
		String projectCode = "";
		if (listAssetRelationProjectDO != null) {
			for (AssetRelationProjectDO assetRelationProjectDO : listAssetRelationProjectDO) {
				projectCode += assetRelationProjectDO.getProjectCode() + ",";
			}
		}
		projectCode = projectCode.substring(0, projectCode.length() - 1);
		if (assetDO != null) {
			assetDO.setProjectCode(projectCode);// 把关联项目编号信息写入资产表中
			pledgeAssetDAO.update(assetDO);
		}
	}
	
	@Override
	public List<AssetRelationProjectInfo> findRelationByCustomerId(long customerId) {
		List<AssetRelationProjectInfo> listInfo = new ArrayList<AssetRelationProjectInfo>();
		List<AssetRelationProjectDO> listDO = assetRelationProjectDAO.findByCustomerId(customerId);
		for (AssetRelationProjectDO assetRelationProjectDO : listDO) {
			listInfo.add(convertAssetRelationProjectDO2Info(assetRelationProjectDO));
		}
		return listInfo;
	}
	
	@Override
	public FcsBaseResult update(long assetsId, String ralateVideo) {
		FcsBaseResult result = createResult();
		try {
			PledgeAssetDO assetDO = pledgeAssetDAO.findById(assetsId);
			if (assetDO != null) {
				assetDO.setRalateVideo(ralateVideo);
				pledgeAssetDAO.update(assetDO);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新资产信息出错");
			logger.error("资产关联视频监控出错：", e);
		}
		return result;
	}
}
