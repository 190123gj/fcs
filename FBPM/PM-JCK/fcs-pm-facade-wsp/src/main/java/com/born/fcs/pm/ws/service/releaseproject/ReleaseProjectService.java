package com.born.fcs.pm.ws.service.releaseproject;

import com.born.fcs.pm.ws.order.release.UpdateReleaseBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 解保项目
 * 
 * @author lirz
 *
 * 2016-6-1 下午1:47:31
 */
public interface ReleaseProjectService {
	
	/**
	 * 
	 * 添加待解保项目(不包括承销，发债类项目)
	 * 
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult add(String projectCode);
	/**
	 * 
	 * 添加待解保项目(发债类项目:到期才可以解保)
	 * 
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult addBond(String projectCode);
	/**
	 * 
	 * 添加待解保项目(委货类项目:收费通知中，添加了收费种类为：委贷本金收回)
	 * 
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult addEntrusted(String projectCode);
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	FcsBaseResult update(UpdateReleaseBaseOrder order);
}
