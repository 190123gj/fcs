package com.born.fcs.am.biz.service.pledgetype;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.convert.UnBoxingConverter;
import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.PledgeImageCustomDO;
import com.born.fcs.am.dal.dataobject.PledgeNetworkCustomDO;
import com.born.fcs.am.dal.dataobject.PledgeTextCustomDO;
import com.born.fcs.am.dal.dataobject.PledgeTypeAttributeDO;
import com.born.fcs.am.dal.dataobject.PledgeTypeDO;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeSimpleInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomOrder;
import com.born.fcs.am.ws.order.pledgenetwork.PledgeNetworkCustomOrder;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomOrder;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeOrder;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeQueryOrder;
import com.born.fcs.am.ws.service.pledgetype.PledgeTypeService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("pledgeTypeService")
public class PledgeTypeServiceImpl extends BaseFormAutowiredDomainService implements
																			PledgeTypeService {
	
	private PledgeTypeInfo convertDO2Info(PledgeTypeDO DO) {
		if (DO == null)
			return null;
		PledgeTypeInfo info = new PledgeTypeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsMapPosition(BooleanEnum.getByCode(DO.getIsMapPosition()));
		return info;
	}
	
	@Override
	public PledgeTypeInfo findById(long typeId) {
		PledgeTypeInfo info = null;
		if (typeId > 0) {
			PledgeTypeDO DO = pledgeTypeDAO.findById(typeId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final PledgeTypeOrder order) {
		return commonProcess(order, "保存抵质押品分类", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				PledgeTypeDO pledgeType = null;
				if (order.getTypeId() != null && order.getTypeId() > 0) {
					pledgeType = pledgeTypeDAO.findById(order.getTypeId());
					if (pledgeType == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"抵质押品类型不存在");
					}
				}
				
				if (pledgeType == null) { // 新增
					pledgeType = new PledgeTypeDO();
					BeanCopier.staticCopy(order, pledgeType, UnBoxingConverter.getInstance());
					
					pledgeType.setRawAddTime(now);
					// 判断当前插入的类型是否有三级或者二级被删除的数据，如果有，就删除1级和2级存在无三级的或者1级存在2级和3级没有的数据
					List<PledgeTypeDO> listTypeDO = pledgeTypeDAO
						.findLevelThreeByLevelOneAndLevelTwo(pledgeType.getLevelOne(),
							pledgeType.getLevelTwo());
					for (PledgeTypeDO pledgeTypeDO : listTypeDO) {
						if (null == pledgeTypeDO.getLevelThree()
							|| "" == pledgeTypeDO.getLevelThree()) {
							pledgeTypeDAO.deleteById(pledgeTypeDO.getTypeId());
						}
					}
					List<PledgeTypeDO> listTypeDO1 = pledgeTypeDAO
						.findLevelOneByLevelOne(pledgeType.getLevelOne());
					for (PledgeTypeDO pledgeTypeDO : listTypeDO1) {
						if ((null == pledgeTypeDO.getLevelThree() || "" == pledgeTypeDO
							.getLevelThree())
							&& (null == pledgeTypeDO.getLevelTwo() || "" == pledgeTypeDO
								.getLevelTwo())) {
							pledgeTypeDAO.deleteById(pledgeTypeDO.getTypeId());
						}
					}
					// 抵质押品分类 -类型 主表
					long typeId = pledgeTypeDAO.insert(pledgeType);
					// 文字信息
					if (order.getPledgeTextCustomOrders() != null) {
						for (PledgeTextCustomOrder textOrder : order.getPledgeTextCustomOrders()) {
							PledgeTextCustomDO textDO = new PledgeTextCustomDO();
							BeanCopier.staticCopy(textOrder, textDO,
								UnBoxingConverter.getInstance());
							textDO.setFieldType(textOrder.getFieldType());
							textDO.setTypeId(typeId);// 将主表的id设置进去
							textDO.setRawAddTime(now);
							if (!"IS".equals(textDO.getIsByRelation())) {
								textDO.setIsByRelation("NO");
							}
							pledgeTextCustomDAO.insert(textDO);
						}
					}
					// 图像信息
					if (order.getPledgeImageCustomOrders() != null) {
						for (PledgeImageCustomOrder imageOrder : order.getPledgeImageCustomOrders()) {
							PledgeImageCustomDO imageDO = new PledgeImageCustomDO();
							BeanCopier.staticCopy(imageOrder, imageDO,
								UnBoxingConverter.getInstance());
							imageDO.setTypeId(typeId);// 将主表的id设置进去
							imageDO.setRawAddTime(now);
							pledgeImageCustomDAO.insert(imageDO);
						}
					}
					
					// 网络信息
					if (order.getPledgeNetworkCustomOrders() != null) {
						for (PledgeNetworkCustomOrder networkOrder : order
							.getPledgeNetworkCustomOrders()) {
							PledgeNetworkCustomDO networkDO = new PledgeNetworkCustomDO();
							BeanCopier.staticCopy(networkOrder, networkDO,
								UnBoxingConverter.getInstance());
							networkDO.setTypeId(typeId);// 将主表的id设置进去
							networkDO.setRawAddTime(now);
							pledgeNetworkCustomDAO.insert(networkDO);
						}
					}
				} else { // 修改
					Long typeId = order.getTypeId();
					BeanCopier.staticCopy(order, pledgeType, UnBoxingConverter.getInstance());
					pledgeTypeDAO.update(pledgeType);
					
					List<PledgeTextCustomDO> listTextDO = pledgeTextCustomDAO.findByTypeId(typeId);
					List<PledgeTextCustomOrder> listTextOrder = order.getPledgeTextCustomOrders();
					for (PledgeTextCustomDO textDO : listTextDO) {
						for (PledgeTextCustomOrder textOrder : listTextOrder) {
							if (textDO.getTextId() > 0
								&& textDO.getTextId() == textOrder.getTextId()
								&& !textDO.getFieldName().equals(textOrder.getFieldName())) {
								List<PledgeTypeAttributeDO> listAttributeDO = pledgeTypeAttributeDAO
									.findByTypeIdAndCustomTypeAndAttributeKey(typeId, "TEXT",
										textDO.getFieldName());
								for (PledgeTypeAttributeDO pledgeTypeAttributeDO : listAttributeDO) {
									pledgeTypeAttributeDO.setAttributeKey(textOrder.getFieldName());
									pledgeTypeAttributeDAO.update(pledgeTypeAttributeDO);
								}
							}
						}
					}
					//					if (order.getPledgeTextCustomOrders() != null) {// 删除先，再插入
					//						
					//					}
					pledgeTextCustomDAO.deleteByTypeId(order.getTypeId());
					if (order.getPledgeTextCustomOrders() != null) {
						for (PledgeTextCustomOrder textOrder : order.getPledgeTextCustomOrders()) {
							PledgeTextCustomDO textDO = new PledgeTextCustomDO();
							BeanCopier.staticCopy(textOrder, textDO,
								UnBoxingConverter.getInstance());
							textDO.setTypeId(typeId);// 将主表的id设置进去
							textDO.setRawAddTime(now);
							if (!"IS".equals(textDO.getIsByRelation())) {
								textDO.setIsByRelation("NO");
							}
							pledgeTextCustomDAO.insert(textDO);
						}
					}
					List<PledgeImageCustomDO> listImageDO = pledgeImageCustomDAO
						.findByTypeId(typeId);
					List<PledgeImageCustomOrder> listImageOrder = order
						.getPledgeImageCustomOrders();
					if (ListUtil.isNotEmpty(listImageDO)) {
						for (PledgeImageCustomDO imageDO : listImageDO) {
							if (ListUtil.isNotEmpty(listImageOrder)) {
								for (PledgeImageCustomOrder imageOrder : listImageOrder) {
									if (imageDO.getImageId() > 0
										&& imageDO.getImageId() == imageOrder.getImageId()
										&& !imageDO.getFieldName()
											.equals(imageOrder.getFieldName())) {
										List<PledgeTypeAttributeDO> listAttributeDO = pledgeTypeAttributeDAO
											.findByTypeIdAndCustomTypeAndAttributeKey(typeId,
												"IMAGE", imageDO.getFieldName());
										for (PledgeTypeAttributeDO pledgeTypeAttributeDO : listAttributeDO) {
											pledgeTypeAttributeDO.setAttributeKey(imageOrder
												.getFieldName());
											pledgeTypeAttributeDAO.update(pledgeTypeAttributeDO);
										}
									}
								}
							}
						}
					}
					//					if (order.getPledgeImageCustomOrders() != null) {// 删除先，再插入
					//						
					//					}
					pledgeImageCustomDAO.deleteByTypeId(order.getTypeId());
					if (order.getPledgeImageCustomOrders() != null) {
						for (PledgeImageCustomOrder imageOrder : order.getPledgeImageCustomOrders()) {
							PledgeImageCustomDO imageDO = new PledgeImageCustomDO();
							BeanCopier.staticCopy(imageOrder, imageDO,
								UnBoxingConverter.getInstance());
							imageDO.setTypeId(typeId);// 将主表的id设置进去
							imageDO.setRawAddTime(now);
							pledgeImageCustomDAO.insert(imageDO);
						}
					}
					List<PledgeNetworkCustomDO> listNetworkDO = pledgeNetworkCustomDAO
						.findByTypeId(typeId);
					List<PledgeNetworkCustomOrder> listNetworkOrder = order
						.getPledgeNetworkCustomOrders();
					for (PledgeNetworkCustomDO networkDO : listNetworkDO) {
						if (listNetworkOrder != null) {
							for (PledgeNetworkCustomOrder networkOrder : listNetworkOrder) {
								if (networkDO.getNetworkId() > 0
									&& networkDO.getNetworkId() == networkOrder.getNetworkId()
									&& !networkDO.getWebsiteName().equals(
										networkOrder.getWebsiteName())) {
									List<PledgeTypeAttributeDO> listAttributeDO = pledgeTypeAttributeDAO
										.findByTypeIdAndCustomTypeAndAttributeKey(typeId, "IMAGE",
											networkDO.getWebsiteName());
									for (PledgeTypeAttributeDO pledgeTypeAttributeDO : listAttributeDO) {
										pledgeTypeAttributeDO.setAttributeKey(networkOrder
											.getWebsiteName());
										pledgeTypeAttributeDAO.update(pledgeTypeAttributeDO);
									}
								}
							}
							
						}
					}
					//					if (order.getPledgeNetworkCustomOrders() != null) {// 删除先，再插入
					//						
					//					}
					pledgeNetworkCustomDAO.deleteByTypeId(order.getTypeId());
					if (order.getPledgeNetworkCustomOrders() != null) {
						for (PledgeNetworkCustomOrder networkOrder : order
							.getPledgeNetworkCustomOrders()) {
							PledgeNetworkCustomDO networkDO = new PledgeNetworkCustomDO();
							BeanCopier.staticCopy(networkOrder, networkDO,
								UnBoxingConverter.getInstance());
							networkDO.setTypeId(typeId);// 将主表的id设置进去
							networkDO.setRawAddTime(now);
							pledgeNetworkCustomDAO.insert(networkDO);
						}
					}
					
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeInfo> query(PledgeTypeQueryOrder order) {
		QueryBaseBatchResult<PledgeTypeInfo> baseBatchResult = new QueryBaseBatchResult<PledgeTypeInfo>();
		
		PledgeTypeDO queryCondition = new PledgeTypeDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getTypeId() != null)
			queryCondition.setTypeId(order.getTypeId());
		
		long totalSize = pledgeTypeDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<PledgeTypeDO> pageList = pledgeTypeDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize());
		
		List<PledgeTypeInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (PledgeTypeDO pledgeType : pageList) {
				list.add(convertDO2Info(pledgeType));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public int deleteById(long typeId) {
		int num = 0;
		if (typeId != 0) {
			num = pledgeTypeDAO.deleteById(typeId);
			pledgeImageCustomDAO.deleteByTypeId(typeId);// 删除时 把关联的文字 图像 网络信息也删除
			pledgeTextCustomDAO.deleteByTypeId(typeId);
			pledgeNetworkCustomDAO.deleteByTypeId(typeId);
		}
		return num;
		
	}
	
	@Override
	public Boolean deleteCheck(long typeId, String level) {
		Boolean check = false;
		try {
			if (typeId != 0 && level != null) {
				PledgeTypeDO DO = pledgeTypeDAO.findById(typeId);
				List<PledgeTypeAttributeDO> listTypeAttributeDO = pledgeTypeAttributeDAO
					.findByTypeId(typeId);
				if (DO != null && listTypeAttributeDO.size() == 0) {
					if ("three".equals(level)) {
						// 检测当前一级分类下的三级分类是否最后一条
						List<PledgeTypeDO> levelThreeList = pledgeTypeDAO
							.findLevelThreeByLevelOne(DO.getLevelOne());
						if (levelThreeList.size() == 1) {
							DO.setLevelThree(null);
							DO.setPledgeRate(0);
							pledgeTypeDAO.update(DO);
							check = true;
						} else {
							deleteById(typeId);
							check = true;
						}
					} else if ("two".equals(level)) {// 删除二级分类
						List<PledgeTypeDO> levelTwoList = pledgeTypeDAO.findLevelTwoByLevelOne(DO
							.getLevelOne());
						if (levelTwoList.size() == 1) {
							DO.setLevelTwo(null);
							pledgeTypeDAO.update(DO);
							check = true;
						}
					} else if ("one".equals(level)) {// 删除一级分类
						// 检测当前二级分类下的三级分类有没有
						
						List<PledgeTypeDO> levelOneList = pledgeTypeDAO.findLevelOneByLevelOne(DO
							.getLevelOne());
						if (levelOneList.size() == 1) {
							deleteById(typeId);
							check = true;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("抵质押品分类删除检测失败" + e.getMessage(), e);
		}
		return check;
	}
	
	@Override
	public Boolean isSameNameCheck(String levelOne, boolean isAddlevelOne, String levelTwo,
									boolean isAddlevelTwo, String levelThree,
									boolean isAddlevelThree) {
		Boolean isCheck = false;
		try {
			if (isAddlevelOne) { // 一级目录新增
				PledgeTypeQueryOrder queryOrder = new PledgeTypeQueryOrder();
				queryOrder.setLevelOne(levelOne);
				QueryBaseBatchResult<PledgeTypeInfo> result = query(queryOrder);
				if (result.getPageList() == null || result.getPageList().size() == 0) {
					isCheck = true;
				}
			}
			if (!isAddlevelOne && isAddlevelTwo) {// 二级目录新增
				PledgeTypeQueryOrder queryOrder = new PledgeTypeQueryOrder();
				queryOrder.setLevelOne(levelOne);
				queryOrder.setLevelTwo(levelTwo);
				QueryBaseBatchResult<PledgeTypeInfo> result = query(queryOrder);
				if (result.getPageList() == null || result.getPageList().size() == 0) {
					isCheck = true;
				}
			}
			if (!isAddlevelOne && !isAddlevelTwo && isAddlevelThree) {// 三级目录新增
				PledgeTypeQueryOrder queryOrder = new PledgeTypeQueryOrder();
				queryOrder.setLevelOne(levelOne);
				queryOrder.setLevelTwo(levelTwo);
				queryOrder.setLevelThree(levelThree);
				QueryBaseBatchResult<PledgeTypeInfo> result = query(queryOrder);
				if (result.getPageList() == null || result.getPageList().size() == 0) {
					isCheck = true;
				}
			}
			if (!isAddlevelOne && !isAddlevelTwo && !isAddlevelThree) {// 都不是新增
				isCheck = true;
			}
		} catch (Exception e) {
			logger.error("重名检测失败" + e.getMessage(), e);
		}
		return isCheck;
	}
	
	@Override
	public List<PledgeTypeInfo> findByLevelOneAndLevelTwo(String levelOne, String levelTwo) {
		List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
		if (levelOne == null) {// 查询所有的一级分类
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findByLevelOne();
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		if (levelOne != null && levelTwo == null) {// 选择一级分类后查询二级分类
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findLevelTwoByLevelOne(levelOne);
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					if (pledgeTypeDO.getLevelTwo() == null || "".equals(pledgeTypeDO.getLevelTwo())) {
						listDO.remove(pledgeTypeDO);
					}
				}
			}
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		if (levelOne != null && levelTwo != null) {// 选择二级后查询三级分类
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findLevelThreeByLevelOneAndLevelTwo(levelOne,
				levelTwo);
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		return listInfo;
	}
	
	@Override
	public PledgeTypeInfo findByLevelOneTwoThree(String levelOne, String levelTwo, String levelThree) {
		PledgeTypeDO DO = pledgeTypeDAO.findByLevelOneTwoThree(levelOne, levelTwo, levelThree);
		return convertDO2Info(DO);
	}
	
	@Override
	public List<PledgeTypeInfo> findByLevelOneAndLevelTwoForAssets(String levelOne, String levelTwo) {
		List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
		if (levelOne == null) {// 查询所有的一级分类 排除三级分类为空的
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findByOneAndThreeNotNull();
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		if (levelOne != null && levelTwo == null) {// 选择一级分类后查询二级分类
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findLevelTwoByLevelOne(levelOne);
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					if (pledgeTypeDO.getLevelTwo() == null || "".equals(pledgeTypeDO.getLevelTwo())) {
						listDO.remove(pledgeTypeDO);
					}
				}
			}
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		if (levelOne != null && levelTwo != null) {// 选择二级后查询三级分类
			List<PledgeTypeDO> listDO = pledgeTypeDAO.findLevelThreeByLevelOneAndLevelTwo(levelOne,
				levelTwo);
			if (listDO != null) {
				for (PledgeTypeDO pledgeTypeDO : listDO) {
					PledgeTypeInfo info = new PledgeTypeInfo();
					info = convertDO2Info(pledgeTypeDO);
					listInfo.add(info);
				}
			}
		}
		return listInfo;
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeSimpleInfo> queryAll() {
		QueryBaseBatchResult<PledgeTypeSimpleInfo> batchResult = new QueryBaseBatchResult<>();
		try {
			PledgeTypeDO pledgeType = new PledgeTypeDO();
			List<PledgeTypeDO> items = pledgeTypeDAO.findByCondition(pledgeType, 0L, 999L);
			if (ListUtil.isNotEmpty(items)) {
				Map<String, Map<String, List<PledgeTypeSimpleInfo>>> map = new LinkedHashMap<>();
				for (PledgeTypeDO doObj : items) {
					//1,2,3，任何一层级为空，就排除掉
					if (StringUtil.isEmpty(doObj.getLevelOne())
						|| StringUtil.isEmpty(doObj.getLevelTwo())
						|| StringUtil.isEmpty(doObj.getLevelThree())) {
						continue;
					}
					Map<String, List<PledgeTypeSimpleInfo>> subMap = map.get(doObj.getLevelOne());
					if (null == subMap) {
						subMap = new LinkedHashMap<>();
						map.put(doObj.getLevelOne(), subMap);
					}
					List<PledgeTypeSimpleInfo> list = subMap.get(doObj.getLevelTwo());
					if (null == list) {
						list = new ArrayList<>();
						subMap.put(doObj.getLevelTwo(), list);
					}
					PledgeTypeSimpleInfo info = new PledgeTypeSimpleInfo();
					info.setTypeId(doObj.getTypeId());
					info.setLevel(3);
					info.setLevelDesc(doObj.getLevelThree());
					list.add(info);
				}
				
				List<PledgeTypeSimpleInfo> infos = new ArrayList<>();
				for (Map.Entry<String, Map<String, List<PledgeTypeSimpleInfo>>> e1 : map.entrySet()) {
					PledgeTypeSimpleInfo info1 = new PledgeTypeSimpleInfo();
					info1.setLevel(1);
					info1.setLevelDesc(e1.getKey());
					List<PledgeTypeSimpleInfo> list1 = new ArrayList<>();
					for (Map.Entry<String, List<PledgeTypeSimpleInfo>> e2 : e1.getValue()
						.entrySet()) {
						PledgeTypeSimpleInfo info2 = new PledgeTypeSimpleInfo();
						info2.setLevel(2);
						info2.setLevelDesc(e2.getKey());
						info2.setSubLevels(e2.getValue());
						list1.add(info2);
					}
					info1.setSubLevels(list1);
					infos.add(info1);
				}
				
				batchResult.setPageList(infos);
				batchResult.setSuccess(true);
			}
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.CALL_REMOTE_SERVICE_ERROR);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
}
