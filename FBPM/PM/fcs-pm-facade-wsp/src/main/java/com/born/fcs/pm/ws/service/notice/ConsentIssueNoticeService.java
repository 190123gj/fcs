package com.born.fcs.pm.ws.service.notice;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.notice.ConsentIssueNoticeInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 发行通知书
 *
 * @author Ji
 */
@WebService
public interface ConsentIssueNoticeService {
	
	/**
	 * 根据ID查询发行通知书信息
	 * @param noticeId
	 * @return
	 */
	ConsentIssueNoticeInfo findById(long noticeId);
	
	/**
	 * 保存发行通知书
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ConsentIssueNoticeOrder order);
	
	/**
	 * 查询发行通知书列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ConsentIssueNoticeInfo> query(ConsentIssueNoticeQueryOrder order);
	
	/**
	 * 根据id删除发行通知书信息
	 * @param noticeId
	 * @return
	 */
	int deleteById(long noticeId);
	
	/**
	 * 上传回执
	 * @param order
	 * @return
	 */
	FcsBaseResult uploadReceipt(ConsentIssueNoticeOrder order);
	
	/**
	 * 新增时分页查询 可以发行通知书的发债项目
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryConsentIssueNotice(ProjectQueryOrder order);
}
