package com.born.fcs.am.ws.info.pledgetype;

import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 抵押物类型
 * 
 * <br/>固定三级
 * 
 * @author lirz
 *
 * 2016-8-24 上午11:15:46
 */
public class PledgeTypeSimpleInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -568764221361138188L;
	
	private long typeId;
	
	private int level;
	
	private String levelDesc;
	
	private List<PledgeTypeSimpleInfo> subLevels;
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getLevelDesc() {
		return levelDesc;
	}
	
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
	
	public List<PledgeTypeSimpleInfo> getSubLevels() {
		return subLevels;
	}
	
	public void setSubLevels(List<PledgeTypeSimpleInfo> subLevels) {
		this.subLevels = subLevels;
	}
	
}
