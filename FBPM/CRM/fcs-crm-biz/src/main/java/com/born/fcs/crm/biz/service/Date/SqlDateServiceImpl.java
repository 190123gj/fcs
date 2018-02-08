package com.born.fcs.crm.biz.service.Date;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.dal.daointerface.ExtraDAO;
import com.yjf.common.lang.util.DateUtil;

@Service("sqlDateService")
public class SqlDateServiceImpl implements SqlDateService {
	@Autowired
	private ExtraDAO extraDAO;
	
	@Override
	public Date getSqlDate() {
		return extraDAO.getSysdate();
	}
	
	@Override
	public String getYear() {
		Date date = getSqlDate();
		return DateUtil.simpleFormat(date).substring(0, 4);
	}
	
}
