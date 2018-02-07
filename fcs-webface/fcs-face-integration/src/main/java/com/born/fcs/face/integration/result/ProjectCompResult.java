package com.born.fcs.face.integration.result;

import java.util.List;

import com.born.fcs.am.ws.result.base.FcsBaseResult;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目代偿数据
 * 
 * @author wuzj
 */
public class ProjectCompResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 4679524731344400959L;
	
	/** 项目编号 */
	private String projectCode;
	
	/** 代偿本金 */
	private Money compPrincipal = new Money(0, 0);
	/** 代偿利息 */
	private Money compInterest = new Money(0, 0);
	/** 代偿违约金 */
	private Money compPenalty = new Money(0, 0);
	/** 代偿罚息 */
	private Money compPenaltyInterest = new Money(0, 0);
	/** 代偿其他 */
	private Money compOther = new Money(0, 0);
	/** 代偿总计 */
	private Money compTotal = new Money(0, 0);
	
	public Money getCompTotal() {
		return this.compTotal;
	}
	
	public void setCompTotal(Money compTotal) {
		this.compTotal = compTotal;
	}
	
	/** 代偿本金收回 */
	private Money compPrincipalBack = new Money(0, 0);
	/** 代偿利息收回 */
	private Money compInterestBack = new Money(0, 0);
	/** 代偿违约金 收回 */
	private Money compPenaltyBack = new Money(0, 0);
	/** 代偿罚息收回 */
	private Money compPenaltyInterestBack = new Money(0, 0);
	/** 代偿其他收回 */
	private Money compOtherBack = new Money(0, 0);
	
	/** 代偿收回总计 */
	private Money compTotalBack = new Money(0, 0);
	
	public Money getCompTotalBack() {
		return this.compTotalBack;
	}
	
	public void setCompTotalBack(Money compTotalBack) {
		this.compTotalBack = compTotalBack;
	}
	
	/** 代偿明细 */
	List<CompInfo> compList;
	
	/**
	 * 代偿信息
	 * @author wuzj
	 */
	public class CompInfo extends BaseToStringInfo {
		
		private static final long serialVersionUID = -3089691932577717085L;
		/** 代偿日期 */
		private String compDate;
		/** 资金方向 */
		private FundDirectionEnum direction;
		/** 代偿本金 */
		private Money compPrincipal = new Money(0, 0);
		/** 代偿利息 */
		private Money compInterest = new Money(0, 0);
		/** 代偿违约金 */
		private Money compPenalty = new Money(0, 0);
		/** 代偿罚息 */
		private Money compPenaltyInterest = new Money(0, 0);
		/** 代偿其他 */
		private Money compOther = new Money(0, 0);
		
		public String getCompDate() {
			return this.compDate;
		}
		
		public void setCompDate(String compDate) {
			this.compDate = compDate;
		}
		
		public Money getCompPrincipal() {
			return this.compPrincipal;
		}
		
		public void setCompPrincipal(Money compPrincipal) {
			this.compPrincipal = compPrincipal;
		}
		
		public Money getCompInterest() {
			return this.compInterest;
		}
		
		public void setCompInterest(Money compInterest) {
			this.compInterest = compInterest;
		}
		
		public Money getCompPenalty() {
			return this.compPenalty;
		}
		
		public void setCompPenalty(Money compPenalty) {
			this.compPenalty = compPenalty;
		}
		
		public Money getCompPenaltyInterest() {
			return this.compPenaltyInterest;
		}
		
		public void setCompPenaltyInterest(Money compPenaltyInterest) {
			this.compPenaltyInterest = compPenaltyInterest;
		}
		
		public Money getCompOther() {
			return this.compOther;
		}
		
		public void setCompOther(Money compOther) {
			this.compOther = compOther;
		}
		
		public FundDirectionEnum getDirection() {
			return this.direction;
		}
		
		public void setDirection(FundDirectionEnum direction) {
			this.direction = direction;
		}
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public List<CompInfo> getCompList() {
		return this.compList;
	}
	
	public void setCompList(List<CompInfo> compList) {
		this.compList = compList;
	}
	
	public Money getCompPrincipal() {
		return this.compPrincipal;
	}
	
	public void setCompPrincipal(Money compPrincipal) {
		this.compPrincipal = compPrincipal;
	}
	
	public Money getCompInterest() {
		return this.compInterest;
	}
	
	public void setCompInterest(Money compInterest) {
		this.compInterest = compInterest;
	}
	
	public Money getCompPenalty() {
		return this.compPenalty;
	}
	
	public void setCompPenalty(Money compPenalty) {
		this.compPenalty = compPenalty;
	}
	
	public Money getCompPenaltyInterest() {
		return this.compPenaltyInterest;
	}
	
	public void setCompPenaltyInterest(Money compPenaltyInterest) {
		this.compPenaltyInterest = compPenaltyInterest;
	}
	
	public Money getCompOther() {
		return this.compOther;
	}
	
	public void setCompOther(Money compOther) {
		this.compOther = compOther;
	}
	
	public Money getCompPrincipalBack() {
		return this.compPrincipalBack;
	}
	
	public void setCompPrincipalBack(Money compPrincipalBack) {
		this.compPrincipalBack = compPrincipalBack;
	}
	
	public Money getCompInterestBack() {
		return this.compInterestBack;
	}
	
	public void setCompInterestBack(Money compInterestBack) {
		this.compInterestBack = compInterestBack;
	}
	
	public Money getCompPenaltyBack() {
		return this.compPenaltyBack;
	}
	
	public void setCompPenaltyBack(Money compPenaltyBack) {
		this.compPenaltyBack = compPenaltyBack;
	}
	
	public Money getCompPenaltyInterestBack() {
		return this.compPenaltyInterestBack;
	}
	
	public void setCompPenaltyInterestBack(Money compPenaltyInterestBack) {
		this.compPenaltyInterestBack = compPenaltyInterestBack;
	}
	
	public Money getCompOtherBack() {
		return this.compOtherBack;
	}
	
	public void setCompOtherBack(Money compOtherBack) {
		this.compOtherBack = compOtherBack;
	}
	
}
