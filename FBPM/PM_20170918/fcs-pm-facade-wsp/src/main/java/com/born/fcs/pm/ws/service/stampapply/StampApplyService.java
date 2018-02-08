package com.born.fcs.pm.ws.service.stampapply;

import com.born.fcs.pm.ws.info.stampapply.*;
import com.born.fcs.pm.ws.order.stampapply.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;
import java.util.List;

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
     * 根据contractCode查所有用印记录
     * @param contractCode
     * @return
     */
    List<StampAFileInfo> findByContractCode(String contractCode);

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

    /**
     * 保存印章配置
     * @param order
     * @return
     */
    FcsBaseResult saveStampConfigure(StampConfigureOrder order);
    /**
     * 获取印章配置信息
     * @param
     * @return
     */
    List<StampConfigureListInfo> getStampConfig();
    /**
     * 根据公司名称获取印章配置信息
     * @param
     * @return
     */
    StampConfigureListInfo getStampConfigByOrgName(String orgName);

    /**
     * 基础资料用印保存
     * @param order
     * @return
     */
    FcsBaseResult saveStampBasicData(StampBasicDataOrder order);


    /**
     * 基础资料查询
     * @param order
     * @return
     */
    QueryBaseBatchResult<StampBasicDataListInfo> queryBasicData(StampBasicDataQueryOrder order);

    /**
     * 获取基础资料
     * @param
     * @return
     */
    List<StampBasicDataListInfo> getStampBasicData();


    /**
     * 根据ID查询基础资料用印
     * @param id
     * @return
     */
    StampBasicDataApplyInfo findStampBasicDataApplyById(long id);

    /**
     * 根据formId查询基础资料用印
     * @param formId
     * @return
     */
    StampBasicDataApplyInfo findStampBasicDataApplyByFormId(long formId);

    /**
     * 保存用印申请
     * @param order
     * @return
     */
    FormBaseResult saveStampBasicDataApply(StampBasicDataApplyOrder order);

    /**
     * 查询用印申请信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<StampApplyProjectResultInfo> queryStampBasicDataApply(StampApplyQueryOrder order);


}
