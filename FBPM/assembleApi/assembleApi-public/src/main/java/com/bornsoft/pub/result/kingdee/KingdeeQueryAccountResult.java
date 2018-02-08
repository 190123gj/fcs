package com.bornsoft.pub.result.kingdee;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornSynResultBase;

/**
 * @Description: 查询会计科目
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:01:02
 * @version:     v1.0
 */
public class KingdeeQueryAccountResult extends BornSynResultBase {

	/**
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
	private List<AccountInfo> dataList;
	
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
	public List<AccountInfo> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<AccountInfo> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @Description: 
	 * @author:      xiaohui@yiji.com
	 * @date         2016-9-1 上午10:17:13
	 * @version:     v1.0
	 */
	public static class AccountInfo extends BornInfoBase{
		/** 科目代码 **/
		private String code;
		/** 科目名称 **/
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
