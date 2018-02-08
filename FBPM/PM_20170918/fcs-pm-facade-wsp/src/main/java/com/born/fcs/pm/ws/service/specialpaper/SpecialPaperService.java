package com.born.fcs.pm.ws.service.specialpaper;


import javax.jws.WebService;

import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperNoInfo;
import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperResultInfo;
import com.born.fcs.pm.ws.order.specialpaper.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 * 特殊纸管理数据维护
 *
 * @author heh
 */
@WebService
public interface SpecialPaperService {

    /**
     * 特殊纸登记
     * @param order
     * @return
     */
    FcsBaseResult checkIn(SpecialPaperNoListOrder order);

    /**
     * 特殊纸部门发放
     * @param order
     * @return
     */
    FcsBaseResult provideDept(SpecialPaperProvideDeptOrder order);


    /**
     * 特殊纸项目发放
     * @param order
     * @return
     */
    FcsBaseResult provideProject(SpecialPaperProvideProjectOrder order);


    /**
     * 特殊纸作废
     * @param order
     * @return
     */
    FcsBaseResult invalid(SpecialPaperInvalidOrder order);

    /**
     * 特殊纸列表
     * @param order
     * @return
     */
    QueryBaseBatchResult<SpecialPaperResultInfo> specialPaperList(SpecialPaperQueryOrder order);


    /**
     * 根据id查询登记信息
     * @param id
     * @return
     */
     SpecialPaperNoInfo findById(long id);

    /**
     * 根据id查询编号信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<SpecialPaperNoInfo> checkInList(SpecialPaperQueryOrder order);

    /**
     * 根据id删除登记记录
     * @param order
     * @return
     */
    FcsBaseResult deleteById(SpecialPaperNoOrder order);

    /**
     * 作废编号校验
     * @param order
     * @return
     */
    List<SpecialPaperResultInfo> checkInvalid(SpecialPaperQueryOrder order);

    /**
     * 特殊纸保存校验
     * @param order
     * @return
     */
    List<SpecialPaperResultInfo> checkSaveInvalid(SpecialPaperQueryOrder order);
}
