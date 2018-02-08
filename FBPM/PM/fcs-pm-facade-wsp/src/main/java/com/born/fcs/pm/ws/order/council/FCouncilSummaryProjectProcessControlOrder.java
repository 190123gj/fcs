package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 会议纪要
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectProcessControlOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5771978053649235312L;
	
	/** 主键 */
	private long id;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 所属过程控制 */
	private String type;
	/** 上调百分点 */
	private String upRate;
	/** 上调bp */
	private String upBp;
	/** 下降百分点 */
	private String downRate;
	/** 下降bp */
	private String downBp;
	/** 信用等级/资产负债率警戒/过程控制内容 */
	private String content;
	/** 排序号 */
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(upRate) && isNull(upBp) && isNull(downRate) && isNull(downBp)
				&& isNull(content);
	}
	
	@Override
	public void check() {
		validateNotNull(spId, "会议纪要对应项目ID");
		validateHasZore(spId, "会议纪要对应项目ID");
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUpRate() {
		return this.upRate;
	}
	
	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}
	
	public String getUpBp() {
		return this.upBp;
	}
	
	public void setUpBp(String upBp) {
		this.upBp = upBp;
	}
	
	public String getDownRate() {
		return this.downRate;
	}
	
	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}
	
	public String getDownBp() {
		return this.downBp;
	}
	
	public void setDownBp(String downBp) {
		this.downBp = downBp;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
