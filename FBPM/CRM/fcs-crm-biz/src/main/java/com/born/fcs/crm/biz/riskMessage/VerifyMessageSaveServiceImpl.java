package com.born.fcs.crm.biz.riskMessage;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.CutomerInfoVerifyMessageDO;
import com.born.fcs.crm.ws.service.VerifyMessageSaveService;
import com.born.fcs.crm.ws.service.info.VerifyMessageInfo;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("verifyMessageSaveService")
public class VerifyMessageSaveServiceImpl extends BaseAutowiredDAO implements
																	VerifyMessageSaveService {
	
	@Override
	public FcsBaseResult save(VerifyMessageOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		
		try {
			order.check();
			CutomerInfoVerifyMessageDO exsitDo = cutomerInfoVerifyMessageDAO.findByErrorKey(order
				.getErrorKey());
			if (exsitDo != null) {
				BeanCopier.staticCopy(order, exsitDo);
				cutomerInfoVerifyMessageDAO.updateByErrorKey(exsitDo);
			} else {
				CutomerInfoVerifyMessageDO cutomerInfoVerifyMessage = new CutomerInfoVerifyMessageDO();
				BeanCopier.staticCopy(order, cutomerInfoVerifyMessage);
				cutomerInfoVerifyMessageDAO.insert(cutomerInfoVerifyMessage);
			}
			result.setSuccess(true);
			result.setMessage("操作成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("操作异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("操作异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("操作异常", e);
		}
		return result;
	}
	
	@Override
	public VerifyMessageInfo queryById(String errorKey) {
		
		CutomerInfoVerifyMessageDO exsitDo = cutomerInfoVerifyMessageDAO.findByErrorKey(errorKey);
		if (exsitDo != null) {
			VerifyMessageInfo info = new VerifyMessageInfo();
			BeanCopier.staticCopy(exsitDo, info);
			return info;
		}
		return null;
	}
}
