package com.born.fcs.pm.ws.info.check;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.ListUtil;

/**
 * 
 * 企业资产/负债情况调查工作底稿
 * 
 * @author lirz
 * 
 * 2016-6-6 上午11:42:36
 */
public class AfterwardsCheckReportFinancialInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -217325765305793813L;
	
	private String financialItem;
	private List<FAfterwardsCheckReportFinancialInfo> infos;
	
	public void add(FAfterwardsCheckReportFinancialInfo info) {
		if (null == infos) {
			infos = new ArrayList<>();
		}
		infos.add(info);
	}
	
	//子列数据
	public int getSize() {
		return (null != infos) ? infos.size() : 0;
	}
	
	//判断是否可编辑
	public BooleanEnum getDelAble() {
		BooleanEnum delAble = BooleanEnum.YES;
		if (ListUtil.isNotEmpty(infos)) {
			delAble = infos.get(0).getDelAble();
		}
		return delAble;
	}
	
	public String getFinancialItem() {
		return financialItem;
	}
	
	public void setFinancialItem(String financialItem) {
		this.financialItem = financialItem;
	}
	
	public List<FAfterwardsCheckReportFinancialInfo> getInfos() {
		return infos;
	}
	
	public void setInfos(List<FAfterwardsCheckReportFinancialInfo> infos) {
		this.infos = infos;
	}
	
}
