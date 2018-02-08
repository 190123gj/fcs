package com.born.fcs.pm.ws.service.creditrefrerenceapply;


import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * 征信管理数据维护
 *
 * @author heh
 */
@WebService
public interface CreditRefrerenceApplyService {
        /**
         * 根据ID查询征信申请数据
         * @param id
         * @return
         */
        CreditRefrerenceApplyInfo findById(long id);

        /**
         * 根据formId查询征信申请数据
         * @param formId
         * @return
         */
        CreditRefrerenceApplyInfo findByFormId(long formId);

        /**
         * 征信查询申请
         * @param order
         * @return
         */
        FormBaseResult save(CreditRefrerenceApplyOrder order);


        /**
         * 查询征信数据
         * @param order
         * @return
         */
        QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> query(CreditRefrerenceApplyQueryOrder order);


        /**
         * 根据ID撤销申请
         * @param Id
         * @return
         */
        void revokeById(long Id);

        /**
         * 根据ID更审批状态
         * @param id
         * @param status
         * @return
         */
        int updateStatusById(long id,String status);

        /**
         * 根据ID更申请状态
         * @param id
         * @param applyStatus
         * @return
         */
        int updateApplyStatusById(long id,String applyStatus);

        /**
         * 根据id上传征信报告
         * @param order
         * @return
         */
        FcsBaseResult updateCreditReportById(CreditRefrerenceApplyOrder order);
    }

