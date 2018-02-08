package com.bornsoft.pub.result.kingdee;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornSynResultBase;

/**
 * @Description: 查询保证金账户信息
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:01:02
 * @version:     v1.0
 */
public class KingdeeQueryGuaranteeResult extends BornSynResultBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 数据总条 **/
	private int totalCount;
	/** 总页数 **/
	private int totalPage;
	/** 分页页数 **/
	private int pageSize;
	/** 每页大小 **/
	private int pageNumber;
	/** 集合 **/
	private List<GuaranteeInfo> dataList;
	
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the dataList
	 */
	public List<GuaranteeInfo> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<GuaranteeInfo> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @Description: 
	 * @author:      xiaohui@yiji.com
	 * @date         2016-9-1 上午10:16:41
	 * @version:     v1.0
	 */
	public static class GuaranteeInfo extends BornInfoBase{
		/** 代码 **/
		private String code;
		/** 名称（账号） **/
		private String name;
		
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
