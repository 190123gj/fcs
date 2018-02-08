package com.born.fcs.pm.ws.service.stampapply;

import com.born.fcs.pm.ws.info.stampapply.StampAFileInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyProjectResultInfo;
import com.born.fcs.pm.ws.order.stampapply.StampApplyOrder;
import com.born.fcs.pm.ws.order.stampapply.StampApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * 用印申请维护
 *
 * @author heh
 */
@WebService
public interface StampApplyService {

    /**
     * 根据ID查询用印申请信息
     * @param id
     * @return
     */
    StampApplyInfo findById(long id,String isReplace);

    /**
     * 根据formId查询用印申请信息
     * @param formId
     * @param isReplace
     * @return
     */
    StampApplyInfo findByFormId(long formId,String isReplace);

    /**
     * 保存用印申请
     * @param order
     * @return
     */
    FormBaseResult save(StampApplyOrder order);

    /**
     * 根据fileId查用印明细
     * @param fileId
     * @return
     */
    StampAFileInfo findByFieldId(long fileId);

    /**
     * 查询用印申请信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<StampApplyProjectResultInfo> query(StampApplyQueryOrder order,String fromList);


    /**
     * 根据ID撤销提交
     * @param order
     * @return
     */
    FormBaseResult revokeStatusById(StampApplyOrder order);

    /**
     * 根据ID删除用印申请
     * @param order
     * @return
     */
    FormBaseResult deleteById(StampApplyOrder order);

//    /**
//     * 更新通过状态
//     * @param order
//     * @return
//     */
//    FormBaseResult updateIsPass(StampApplyOrder order);

}
