package com.born.fcs.rm.biz.service.Date;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.rm.dal.daointerface.ExtraDAO;

@Service("sqlDateService")
public class SqlDateServiceImpl implements SqlDateService {
	@Autowired
	private ExtraDAO extraDAO;
	
	@Override
	public Date getSqlDate() {
		return extraDAO.getSysdate();
	}
	
}
