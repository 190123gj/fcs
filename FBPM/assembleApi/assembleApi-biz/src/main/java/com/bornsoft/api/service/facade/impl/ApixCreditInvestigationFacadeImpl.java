package com.bornsoft.api.service.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.apix.ApixCreditInvestigationFacade;
import com.bornsoft.integration.apix.ApixDistanceClient;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.jck.dal.daointerface.CardInfoDAO;
import com.bornsoft.jck.dal.daointerface.MobileInfoDAO;
import com.bornsoft.jck.dal.daointerface.PersonRealInfoDAO;
import com.bornsoft.jck.dal.dataobject.CardInfoDO;
import com.bornsoft.jck.dal.dataobject.MobileInfoDO;
import com.bornsoft.jck.dal.dataobject.PersonRealInfoDO;
import com.bornsoft.pub.enums.ApixResultEnum;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixLocationBasedOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult.DishonestInfo;
import com.bornsoft.pub.result.apix.ApixLocationBasedResult;
import com.bornsoft.pub.result.apix.ApixValidateBankCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult.PersonInfo;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult.MobileValidateInfo;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.SwitchEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 黑格征信
 * @author taibai@yiji.com
 * @date 2016-8-29 上午10:14:04
 * @version V1.0
 */
@Service("apixCreditInvestigationFacadeApi")
public class ApixCreditInvestigationFacadeImpl implements
		ApixCreditInvestigationFacade {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private   SystemParamCacheHolder	systemParamCacheHolder;
	
	@Autowired
	private PersonRealInfoDAO personRealInfoDAO;
	
	@Autowired
	private MobileInfoDAO 	  mobileInfoDAO;
	
	@Autowired
	private CardInfoDAO		  cardInfoDAO;
	
	@Autowired
	private ApixDistanceClient apixDistanceClient;
	
	@Override
	public ApixDishonestQueryResult dishonestQuery(ApixDishonestQueryOrder order) {
		logger.error("apix接口请求入参={}",order);
		ApixDishonestQueryResult result = null;
		try {
			if(SwitchEnum.Off.code().equals(systemParamCacheHolder.getConfigNoCache(ApiSystemParamEnum.Apix_Api_Switch))){
				result = mock(order);
			}else{
				order.validateOrder();
				String vryKey = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Apix_Dishonest_Key);
				if(StringUtil.isBlank(vryKey)){
					throw new BornApiException("未配置apix-Key");
				}else{
					order.setApixKey(vryKey);
				}
				result = apixDistanceClient.execute(order, ApixDishonestQueryResult.class);
			}
		} catch (Exception e) {
			logger.error("apix接口请求失败",e);
			result = new ApixDishonestQueryResult();
			InstallCommonResultUtil.installDefaultFailureResult(order, result,e);
		}
		logger.error("apix接口请求出参={}",result);
		return result;
	}

	@Override
	public ApixLocationBasedResult locateByBaseStation(
			ApixLocationBasedOrder order) {
		return apixDistanceClient.execute(order, ApixLocationBasedResult.class);
	}

	@Override
	public ApixValidateBankCardResult validateBankCard(
			ApixValidateBankCardOrder order) {
		logger.info("apix接口请求入参={}",order);
		ApixValidateBankCardResult result = null;
		try {
			order.validateOrder();
			String vryKey = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Apix_Very_Key);
			if(StringUtil.isBlank(vryKey)){
				throw new BornApiException("未配置apix-Key");
			}else{
				order.setApixKey(vryKey);
			}
			//首先本地校验
			CardInfoDO queryInfo = new CardInfoDO();
			queryInfo.setCertNo(order.getCertNo());
			queryInfo.setRealName(order.getName());
			queryInfo.setMobile(order.getMobile());
			queryInfo.setCardNo(order.getBankCardNo());
			
			List<CardInfoDO> infoList = cardInfoDAO.queryCardInfo(queryInfo);
			if(infoList!=null && infoList.size()>0){
				result = new ApixValidateBankCardResult();
				result.setCode(ApixResultEnum.EXECUTE_SUCCESS.code());
				result.setMsg("校验通过");
			}else{
				result = apixDistanceClient.execute(order, ApixValidateBankCardResult.class);
				if(result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS 
						&&ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode())){
					CardInfoDO cardInfo = new CardInfoDO();
					cardInfo.setCardNo(queryInfo.getCardNo());
					cardInfo.setCertNo(queryInfo.getCertNo());
					cardInfo.setMobile(queryInfo.getMobile());
					cardInfo.setRealName(queryInfo.getRealName());
					if(result.getData()!=null){
						cardInfo.setBankName(result.getData().getBankName());
					}
					logger.error("保存用户银行卡校验信息{}",cardInfo);
					cardInfoDAO.insert(cardInfo);
				}
			}
			InstallCommonResultUtil.installDefaultSuccessResult(order, result);
		} catch (Exception e) {
			logger.error("apix接口请求失败",e);
			result = new ApixValidateBankCardResult();
			InstallCommonResultUtil.installDefaultFailureResult(order, result ,e);
		}
		logger.info("apix接口请求出参={}",result);
		return result;
	}

	@Override
	public ApixValidateIdCardResult validateIdCard(ApixValidateIdCardOrder order) {
		logger.info("个人身份证校验,入参={}",order);
		ApixValidateIdCardResult result = null;
		try {
			order.validateOrder();
			//设置KEY
			String vryKey = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Apix_Very_Key);
			if(StringUtil.isBlank(vryKey)){
				throw new BornApiException("未配置apix-Key");
			}else{
				order.setApixKey(vryKey);
			}

			//首先本地校验
			PersonRealInfoDO realInfo = new PersonRealInfoDO();
			realInfo.setCertNo(order.getCertNo());
			realInfo.setRealName(order.getName());
			List<PersonRealInfoDO> infoList = personRealInfoDAO.queryRealInfo(realInfo);
			if(infoList!=null && infoList.size()>0){
				PersonRealInfoDO tmpInfo = infoList.get(0);
				result = new ApixValidateIdCardResult();
				PersonInfo cardInfo = new PersonInfo();
				BeanUtils.copyProperties(tmpInfo, cardInfo);
				cardInfo.setName(tmpInfo.getRealName());
				cardInfo.setIdcardphoto(tmpInfo.getCardPhoto());
				result.setPersonInfo(cardInfo);
				result.setCode(ApixResultEnum.EXECUTE_SUCCESS.code());
				result.setMsg("校验通过");
			}else{
				if(SwitchEnum.Off.code().equals(systemParamCacheHolder.getConfigNoCache(ApiSystemParamEnum.Apix_Api_Switch))){
					result = new ApixValidateIdCardResult();
					ApixResultEnum resultEnum = Math.random()>0.5?ApixResultEnum.EXECUTE_SUCCESS:ApixResultEnum.EXECUTE_FAILED;
					result.setCode(resultEnum.code());
					result.setMsg(resultEnum==ApixResultEnum.EXECUTE_SUCCESS?"校验通过[模拟]":"校验不通过[模拟]");
				}else{
					//本地记录不存在则调用接口校验
					result =  apixDistanceClient.execute(order, ApixValidateIdCardResult.class);
					if(result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS && result.getPersonInfo()!=null
							&&ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode())){
						PersonRealInfoDO personRealInfo = new PersonRealInfoDO();
						BeanUtils.copyProperties(result.getPersonInfo(), personRealInfo);
						personRealInfo.setRealName(result.getPersonInfo().getName());
						personRealInfo.setCardPhoto(result.getPersonInfo().getIdcardphoto());
						logger.error("保存用户校验信息{}",personRealInfo);
						personRealInfoDAO.insert(personRealInfo);
					}
				}
			
			}
			InstallCommonResultUtil.installDefaultSuccessResult(order, result);
		} catch (Exception e) {
			logger.error("apix接口请求失败",e);
			result = new ApixValidateIdCardResult();
			InstallCommonResultUtil.installDefaultFailureResult(order, result ,e);
		} 
		logger.info("个人身份证校验,出参={}",result);
		return result;
	}

	@Override
	public ApixValidateMobileResult validateMobile(ApixValidateMobileOrder order) {
		logger.info("个人手机校验,入参={}",order);
		ApixValidateMobileResult result = null;

		try {
			order.validateOrder();
			String vryKey = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Apix_Very_Key);
			if(StringUtil.isBlank(vryKey)){
				throw new BornApiException("未配置apix-Key");
			}else{
				order.setApixKey(vryKey);
			}
			//先查询本地
			MobileInfoDO realInfo = new MobileInfoDO();
			realInfo.setMobile(order.getMobile());
			realInfo.setRealName(order.getName());
			realInfo.setCertNo(order.getCertNo());
			
			List<MobileInfoDO> infoList= mobileInfoDAO.queryMobileInfoList(realInfo);
			if(infoList!=null && infoList.size()>0){
				realInfo = infoList.get(0);
				//结果拼装
				result = new ApixValidateMobileResult();
				MobileValidateInfo data = new MobileValidateInfo();
				data.setAddress(realInfo.getAddress());
				data.setBirthday(realInfo.getBirthday());
				data.setMobileCity(realInfo.getCity());
				data.setMobileOperator(realInfo.getOperator());
				data.setMoibleProv(realInfo.getProvince());
				data.setSex(realInfo.getSex());
				result.setData(data);

				InstallCommonResultUtil.installDefaultSuccessResult(order, result);
				result.setCode(ApixResultEnum.EXECUTE_SUCCESS.code());
				result.setMsg("校验通过");
			
			}else{
				//本地记录不存在则调用接口校验
				result = apixDistanceClient.execute(order, ApixValidateMobileResult.class);
				if(result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS && result.getData()!=null
						&&ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode())){
					MobileValidateInfo mobileInfo = result.getData();
					MobileInfoDO mobileDo = new MobileInfoDO(); 
					mobileDo.setAddress(mobileInfo.getAddress());
					mobileDo.setBirthday(mobileInfo.getBirthday());
					mobileDo.setCertNo(order.getCertNo());
					mobileDo.setCity(mobileInfo.getMobileCity());
					mobileDo.setMobile(order.getMobile());
					mobileDo.setOperator(mobileInfo.getMobileOperator());
					mobileDo.setProvince(mobileInfo.getMoibleProv());
					mobileDo.setRealName(order.getName());
					mobileDo.setSex(mobileInfo.getSex());
					logger.error("保存用户手机校验信息{}",mobileDo);
					mobileInfoDAO.insert(mobileDo);
				}
			}
			
		} catch (DataAccessException e) {
			logger.error("apix接口请求失败",e);
			result = new ApixValidateMobileResult();
			InstallCommonResultUtil.installDefaultFailureResult(order, result ,e);
		}
		logger.info("个人手机校验,出参={}",result);
		return  result;
	}
	
	private ApixDishonestQueryResult mock(ApixDishonestQueryOrder order) {
		ApixDishonestQueryResult result = new ApixDishonestQueryResult();
		List<DishonestInfo> dishonestList = new ArrayList<>();
		if(Math.random()>0.5){
			result.setCode(ApixResultEnum.EXECUTE_SUCCESS.code());
			result.setMsg("");
			DishonestInfo info = new DishonestInfo();
			info.setAge("80");
			info.setDuty("由被告李尚钟支付原告李东华借款205300元，并支付违约金43770元，合计349070元");
			info.setDisruptType("违反财产报告制度,其他有履行能力而拒不履行生效法律文书确定义务");
			info.setCode("(2011)宁执字第00079号");
			info.setSex("男");
			info.setPubTime("2013年10月11日");
			info.setCourt("宁洱哈尼族彝族自治县人民法院");
			info.setName(order.getName());
			info.setArea("云南");
			info.setPerformance("全部未履行");
			info.setCertNo(order.getCertNo());
			dishonestList.add(info);
			
			info = new DishonestInfo();
			info.setAge("80");
			info.setDuty("由被告李尚钟支付原告李东华借款205300元，并支付违约金43770元，合计349070元");
			info.setDisruptType("违反财产报告制度,其他有履行能力而拒不履行生效法律文书确定义务");
			info.setCode("(2011)宁执字第00079号");
			info.setSex("男");
			info.setPubTime("2013年10月11日");
			info.setCourt("宁洱哈尼族彝族自治县人民法院");
			info.setName(order.getName());
			info.setArea("云南");
			info.setPerformance("全部未履行");
			info.setCertNo(order.getCertNo());
			dishonestList.add(info);
			
			info = new DishonestInfo();
			info.setAge("80");
			info.setDuty("由被告李尚钟支付原告李东华借款205300元，并支付违约金43770元，合计349070元");
			info.setDisruptType("违反财产报告制度,其他有履行能力而拒不履行生效法律文书确定义务");
			info.setCode("(2011)宁执字第00079号");
			info.setSex("男");
			info.setPubTime("2013年10月11日");
			info.setCourt("宁洱哈尼族彝族自治县人民法院");
			info.setName(order.getName());
			info.setArea("云南");
			info.setPerformance("全部未履行");
			info.setCertNo(order.getCertNo());
			dishonestList.add(info);
		}else{
			result.setCode(ApixResultEnum.NO_DISHONEST.code());
			result.setMsg("未找到失信数据");
		}
		result.setDishonestList(dishonestList);
		InstallCommonResultUtil.installDefaultSuccessResult(order, result);
		result.setResultMessage("模拟成功");
		return result;
	}

}
