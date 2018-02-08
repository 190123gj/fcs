package com.born.fcs.pm.biz.service.specialpaper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.dal.dataobject.SpecialPaperInvalidDO;
import com.born.fcs.pm.dal.dataobject.SpecialPaperNoDO;
import com.born.fcs.pm.dal.dataobject.SpecialPaperProvideDeptDO;
import com.born.fcs.pm.dal.dataobject.SpecialPaperProvideProjectDO;
import com.born.fcs.pm.dataobject.SpecialPaperResultDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SpecialPaperSourceEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperNoInfo;
import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperResultInfo;
import com.born.fcs.pm.ws.order.specialpaper.*;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.specialpaper.SpecialPaperService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("specialPaperService")
public class SpecialPaperServiceImpl extends BaseAutowiredDomainService implements
        SpecialPaperService {


    @Override
    public FcsBaseResult checkIn(final SpecialPaperNoListOrder order) {
        return commonProcess(order, "特殊纸登记", new BeforeProcessInvokeService() {

            @SuppressWarnings("deprecation")
            @Override
            public Domain before() {
                final Date now = FcsPmDomainHolder.get().getSysDate();
               List<SpecialPaperNoOrder> noOrders=order.getListOrder();
                for(SpecialPaperNoOrder noOrder:noOrders){
                    if(noOrder.getId()==null||noOrder.getId()<=0){//新增
                        SpecialPaperNoDO noDO = new SpecialPaperNoDO();
                        BeanCopier.staticCopy(noOrder, noDO, UnBoxingConverter.getInstance());
                        noDO.setKeepingManId(order.getKeepingManId());
                        noDO.setKeepingManName(order.getKeepingManName());
                        noDO.setSource(SpecialPaperSourceEnum.CHECKIN.code());
                        noDO.setLeftPaper(noOrder.getEndNo()-noDO.getStartNo()+1);
                        noDO.setRawAddTime(now);
                        specialPaperNoDAO.insert(noDO);
                    }else{//更新
                        SpecialPaperNoDO noDO=specialPaperNoDAO.findById(noOrder.getId());
                        noDO.setStartNo(noOrder.getStartNo());
                        noDO.setEndNo(noOrder.getEndNo());
                        noDO.setLeftPaper(noOrder.getEndNo()-noDO.getStartNo()+1);
                        specialPaperNoDAO.update(noDO);
                    }

                }
                return null;
            }
        }, null, null);
    }

    @Override
    public FcsBaseResult provideDept(final SpecialPaperProvideDeptOrder order) {
        return commonProcess(order, "特殊纸部门发放", new BeforeProcessInvokeService() {

            @SuppressWarnings("deprecation")
            @Override
            public Domain before() {
                if(order.getId()<=0){//新增
                    final Date now = FcsPmDomainHolder.get().getSysDate();
                    SpecialPaperProvideDeptDO deptDO=new SpecialPaperProvideDeptDO();
                    BeanCopier.staticCopy(order, deptDO, UnBoxingConverter.getInstance());
                    deptDO.setRawAddTime(now);
                    long id=specialPaperProvideDeptDAO.insert(deptDO);
                    List<SpecialPaperNoOrder> noOrders=order.getListOrder();
                    for(SpecialPaperNoOrder noOrder:noOrders){
                        //先更新
                        SpecialPaperNoDO noDo=specialPaperNoDAO.findById(noOrder.getId());
                        noDo.setLeftPaper(noDo.getLeftPaper()-(noOrder.getEndNo()-noOrder.getStartNo()+1));
                        specialPaperNoDAO.update(noDo);
                        SpecialPaperNoDO newNoDO = new SpecialPaperNoDO();
                        BeanCopier.staticCopy(noOrder, newNoDO, UnBoxingConverter.getInstance());
                        newNoDO.setId(0);
                        newNoDO.setSource(SpecialPaperSourceEnum.PROVIDE_DEPT.code());
                        newNoDO.setRawAddTime(now);
                        newNoDO.setSourceId(id);
                        newNoDO.setLeftPaper(noOrder.getEndNo()-noOrder.getStartNo()+1);
                        newNoDO.setParentId(noOrder.getId());
                        specialPaperNoDAO.insert(newNoDO);
                    }
//                }else{
//                    SpecialPaperProvideDeptDO deptDO = specialPaperProvideDeptDAO.findById(order.getId());
//                    if (null == deptDO) {
//                        throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
//                                "未找到特殊纸部门发放记录");
//                    }
//                    BeanCopier.staticCopy(order, deptDO,UnBoxingConverter.getInstance());
//                    specialPaperProvideDeptDAO.update(deptDO);
//                    SpecialPaperNoDO queryCondition=new SpecialPaperNoDO();
//                    queryCondition.setSourceId(order.getId());
//                    queryCondition.setSource(SpecialPaperSourceEnum.PROVIDE_DEPT.code());
//                    List<SpecialPaperNoDO> inDataBaseList=specialPaperNoDAO.findByCondition(queryCondition,0,0,null,null,null,null);
//                    Map<Long,Long> inDataBaseMap= new HashMap<Long,Long>();
//                    for(SpecialPaperNoDO DO:inDataBaseList){
//                        inDataBaseMap.put(DO.getId(), DO.getSourceId());
//                    }
//                    //更新用印文件
//                    for (SpecialPaperNoOrder noOrder : order.getListOrder()) {
//                        if (noOrder.getId() <= 0) {//不存在就新增
//                            final Date now = FcsPmDomainHolder.get().getSysDate();
//                            SpecialPaperNoDO noDO = new SpecialPaperNoDO();
//                            BeanCopier.staticCopy(noOrder, noDO);
//                            noDO.setId(order.getId());
//                            noDO.setSource(SpecialPaperSourceEnum.PROVIDE_DEPT.code());
//                            noDO.setRawAddTime(now);
//                            specialPaperNoDAO.insert(noDO);
//                        } else {
//                            SpecialPaperNoDO noDO = specialPaperNoDAO
//                                    .findById(noOrder.getId());
//                            if (null == noDO) {
//                                throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
//                                        "未找到特殊纸部门发放编号");
//                            }
//
//                            BeanCopier.staticCopy(noOrder, noDO,
//                                    UnBoxingConverter.getInstance());
//                            noDO.setSourceId(order.getId());
//                            noDO.setSource(SpecialPaperSourceEnum.PROVIDE_DEPT.code());
//                            specialPaperNoDAO.update(noDO);
//                            inDataBaseMap.remove(noDO.getId());
//                        }
//
//                    }
//                    if(inDataBaseMap.size()>0){//删除
//                        for(long id:inDataBaseMap.keySet()){
//                            specialPaperNoDAO.deleteById(id);
//                        }
//
//                    }
                }
                return null;
            }
        }, null, null);
    }

    @Override
    public FcsBaseResult provideProject(final SpecialPaperProvideProjectOrder order) {
        return commonProcess(order, "特殊纸项目发放", new BeforeProcessInvokeService() {

            @SuppressWarnings("deprecation")
            @Override
            public Domain before() {
                if(order.getId()<=0){//新增
                    final Date now = FcsPmDomainHolder.get().getSysDate();
                    SpecialPaperProvideProjectDO projectDO=new SpecialPaperProvideProjectDO();
                    BeanCopier.staticCopy(order, projectDO, UnBoxingConverter.getInstance());
                    projectDO.setRawAddTime(now);
                    long id=specialPaperProvideProjectDAO.insert(projectDO);
                    List<SpecialPaperNoOrder> noOrders=order.getListOrder();
                    for(SpecialPaperNoOrder noOrder:noOrders){
                        //先更新
                        SpecialPaperNoDO noDo=specialPaperNoDAO.findById(noOrder.getId());
                        noDo.setLeftPaper(noDo.getLeftPaper()-(noOrder.getEndNo()-noOrder.getStartNo()+1));
                        specialPaperNoDAO.update(noDo);
                        SpecialPaperNoDO newNoDO = new SpecialPaperNoDO();
                        BeanCopier.staticCopy(noOrder, newNoDO, UnBoxingConverter.getInstance());
                        newNoDO.setId(0);
                        newNoDO.setSource(SpecialPaperSourceEnum.PROVIDE_PROJECT.code());
                        newNoDO.setRawAddTime(now);
                        newNoDO.setSourceId(id);
                        newNoDO.setLeftPaper(noOrder.getEndNo()-noOrder.getStartNo()+1);
                        newNoDO.setParentId(noOrder.getId());
                        newNoDO.setKeepingManId(order.getReceiveManId());
                        newNoDO.setKeepingManName(order.getReceiveManName());
                        specialPaperNoDAO.insert(newNoDO);
                    }
//                }else{
//                    SpecialPaperProvideProjectDO projectDO = specialPaperProvideProjectDAO.findById(order.getId());
//                    if (null == projectDO) {
//                        throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
//                                "未找到特殊纸项目发放记录");
//                    }
//                    BeanCopier.staticCopy(order, projectDO,UnBoxingConverter.getInstance());
//                    specialPaperProvideProjectDAO.update(projectDO);
//                    SpecialPaperNoDO queryCondition=new SpecialPaperNoDO();
//                    queryCondition.setSourceId(order.getId());
//                    queryCondition.setSource(SpecialPaperSourceEnum.PROVIDE_PROJECT.code());
//                    List<SpecialPaperNoDO> inDataBaseList=specialPaperNoDAO.findByCondition(queryCondition,0,0,null,null,null,null);
//                    Map<Long,Long> inDataBaseMap= new HashMap<Long,Long>();
//                    for(SpecialPaperNoDO DO:inDataBaseList){
//                        inDataBaseMap.put(DO.getId(), DO.getSourceId());
//                    }
//                    //更新用印文件
//                    for (SpecialPaperNoOrder noOrder : order.getListOrder()) {
//                        if (noOrder.getId() <= 0) {//不存在就新增
//                            final Date now = FcsPmDomainHolder.get().getSysDate();
//                            SpecialPaperNoDO noDO = new SpecialPaperNoDO();
//                            BeanCopier.staticCopy(noOrder, noDO);
//                            noDO.setId(order.getId());
//                            noDO.setSource(SpecialPaperSourceEnum.PROVIDE_PROJECT.code());
//                            noDO.setRawAddTime(now);
//                            specialPaperNoDAO.insert(noDO);
//                        } else {
//                            SpecialPaperNoDO noDO = specialPaperNoDAO
//                                    .findById(noOrder.getId());
//                            if (null == noDO) {
//                                throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
//                                        "未找到特殊纸项目发放编号");
//                            }
//
//                            BeanCopier.staticCopy(noOrder, noDO,
//                                    UnBoxingConverter.getInstance());
//                            noDO.setSourceId(order.getId());
//                            noDO.setSource(SpecialPaperSourceEnum.PROVIDE_PROJECT.code());
//                            specialPaperNoDAO.update(noDO);
//                            inDataBaseMap.remove(noDO.getId());
//                        }
//
//                    }
//                    if(inDataBaseMap.size()>0){//删除
//                        for(long id:inDataBaseMap.keySet()){
//                            specialPaperNoDAO.deleteById(id);
//                        }
//
//                    }
                }
                return null;
            }
        }, null, null);
    }

    @Override
    public FcsBaseResult invalid(final SpecialPaperInvalidOrder order) {
        return commonProcess(order, "特殊纸作废", new BeforeProcessInvokeService() {

            @SuppressWarnings("deprecation")
            @Override
            public Domain before() {
                if(order.getId()<=0){//新增
                    final Date now = FcsPmDomainHolder.get().getSysDate();
                    SpecialPaperInvalidDO invalidDO=new SpecialPaperInvalidDO();
                    BeanCopier.staticCopy(order, invalidDO, UnBoxingConverter.getInstance());
                    invalidDO.setRawAddTime(now);
                    long id=specialPaperInvalidDAO.insert(invalidDO);
                    List<SpecialPaperNoOrder> noOrders=order.getListOrder();
                    for(SpecialPaperNoOrder noOrder:noOrders){
                        SpecialPaperNoDO noDO = new SpecialPaperNoDO();
                        BeanCopier.staticCopy(noOrder, noDO, UnBoxingConverter.getInstance());
                        noDO.setSource(SpecialPaperSourceEnum.INVALID.code());
                        noDO.setRawAddTime(now);
                        noDO.setSourceId(id);
                        specialPaperNoDAO.insert(noDO);
                    }
                }else{
                    SpecialPaperInvalidDO invalidDO = specialPaperInvalidDAO.findById(order.getId());
                    if (null == invalidDO) {
                        throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
                                "未找到特殊纸作废记录");
                    }
                    BeanCopier.staticCopy(order, invalidDO,UnBoxingConverter.getInstance());
                    specialPaperInvalidDAO.update(invalidDO);
                    SpecialPaperNoDO queryCondition=new SpecialPaperNoDO();
                    queryCondition.setSourceId(order.getId());
                    queryCondition.setSource(SpecialPaperSourceEnum.INVALID.code());
                    List<SpecialPaperNoDO> inDataBaseList=specialPaperNoDAO.findByCondition(queryCondition,0,0,null,null,null,null);
                    Map<Long,Long> inDataBaseMap= new HashMap<Long,Long>();
                    for(SpecialPaperNoDO DO:inDataBaseList){
                        inDataBaseMap.put(DO.getId(), DO.getSourceId());
                    }
                    //更新用印文件
                    for (SpecialPaperNoOrder noOrder : order.getListOrder()) {
                        if (noOrder.getId() <= 0) {//不存在就新增
                            final Date now = FcsPmDomainHolder.get().getSysDate();
                            SpecialPaperNoDO noDO = new SpecialPaperNoDO();
                            BeanCopier.staticCopy(noOrder, noDO);
                            noDO.setId(order.getId());
                            noDO.setSource(SpecialPaperSourceEnum.INVALID.code());
                            noDO.setRawAddTime(now);
                            specialPaperNoDAO.insert(noDO);
                        } else {
                            SpecialPaperNoDO noDO = specialPaperNoDAO
                                    .findById(noOrder.getId());
                            if (null == noDO) {
                                throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
                                        "未找到特殊纸作废编号");
                            }

                            BeanCopier.staticCopy(noOrder, noDO,
                                    UnBoxingConverter.getInstance());
                            noDO.setSourceId(order.getId());
                            noDO.setSource(SpecialPaperSourceEnum.INVALID.code());
                            specialPaperNoDAO.update(noDO);
                            inDataBaseMap.remove(noDO.getId());
                        }

                    }
                    if(inDataBaseMap.size()>0){//删除
                        for(long id:inDataBaseMap.keySet()){
                            specialPaperNoDAO.deleteById(id);
                        }

                    }
                }
                return null;
            }
        }, null, null);
    }

    @Override
    public QueryBaseBatchResult<SpecialPaperResultInfo> specialPaperList(SpecialPaperQueryOrder order) {
            QueryBaseBatchResult<SpecialPaperResultInfo> baseBatchResult = new QueryBaseBatchResult<SpecialPaperResultInfo>();

            SpecialPaperResultDO queryCondition = new SpecialPaperResultDO();
            Date applyTimeStart = null;
            Date applyTimeEnd = null;
            Date invalidReceiveDateStart = null;
            Date invalidReceiveDateEnd = null;
            if (order != null)
                BeanCopier.staticCopy(order, queryCondition);

            if (order.getProjectName() != null)
                queryCondition.setProjectName(order.getProjectName());
            if (order.getStartNo()!= null)
                queryCondition.setStartNo(Long.parseLong(order.getStartNo()));
            if (order.getEndNo()!= null)
                queryCondition.setEndNo(Long.parseLong(order.getEndNo()));
            if (order.getKeepingManName() != null)
                queryCondition.setKeepingManName(order.getKeepingManName());
            if (order.getReceiptMan() != null) {
                queryCondition.setReceiveManName(order.getReceiveManName());
            }
            if (order.getReceiveDate() != null) {
                applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getReceiveDate());
                applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getReceiveDate());
            }
            if (order.getReceiptPlace() != null) {
                queryCondition.setReceiptPlace(order.getReceiptPlace());
            }
            if (order.getReceiptMan() != null) {
            queryCondition.setReceiptMan(order.getReceiptMan());
            }

            if(order.getProvideManName()!=null){
                queryCondition.setProvideManName(order.getProvideManName());
            }

            if(order.getDeptName()!=null){
                queryCondition.setDeptName(order.getDeptName());
            }
            if(order.getInvalidDate()!=null){
                applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getInvalidDate());
                applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getInvalidDate());
            }
            if(order.getInvalidReceiveDate()!=null){
                invalidReceiveDateStart = DateUtil.getStartTimeOfTheDate(order.getInvalidReceiveDate());
                invalidReceiveDateEnd = DateUtil.getEndTimeOfTheDate(order.getInvalidReceiveDate());
            }

            long totalSize = 0;
            if (order.getSource().code().equals(SpecialPaperSourceEnum.PROVIDE_DEPT.code())) {
                totalSize = extraDAO.searchSpecialPaperDeptListCount(queryCondition,applyTimeStart,applyTimeEnd);
            } else if(order.getSource().code().equals(SpecialPaperSourceEnum.PROVIDE_PROJECT.code())){
                totalSize = extraDAO.searchSpecialPaperProjectListCount(queryCondition,applyTimeStart,applyTimeEnd);
            }
            else if(order.getSource().code().equals(SpecialPaperSourceEnum.INVALID.code())){
                totalSize = extraDAO.searchSpecialPaperInvalidListCount(queryCondition,applyTimeStart,applyTimeEnd,invalidReceiveDateStart,invalidReceiveDateEnd);
           }

            PageComponent component = new PageComponent(order, totalSize);
            List<SpecialPaperResultDO> pageList = Lists.newArrayList();
            if (order.getSource().code().equals(SpecialPaperSourceEnum.PROVIDE_DEPT.code())) {
                pageList = extraDAO.searchSpecialPaperDeptList(queryCondition, component.getFirstRecord(),
                        component.getPageSize(),applyTimeStart,applyTimeEnd, order.getSortCol(), order.getSortOrder());
            } else if(order.getSource().code().equals(SpecialPaperSourceEnum.PROVIDE_PROJECT.code())){
                pageList = extraDAO.searchSpecialPaperProjectList(queryCondition,
                        component.getFirstRecord(), component.getPageSize(),applyTimeStart,applyTimeEnd,
                        order.getSortCol(), order.getSortOrder());
            }
            else if(order.getSource().code().equals(SpecialPaperSourceEnum.INVALID.code())){
                pageList = extraDAO.searchSpecialPaperInvalidList(queryCondition,
                        component.getFirstRecord(), component.getPageSize(),applyTimeStart,applyTimeEnd,
                        order.getSortCol(), order.getSortOrder(),invalidReceiveDateStart,invalidReceiveDateEnd);
            }
            List<SpecialPaperResultInfo> list = baseBatchResult.getPageList();
            if (totalSize > 0) {
                for (SpecialPaperResultDO apply : pageList) {
                    SpecialPaperResultInfo info=new SpecialPaperResultInfo();
                    BeanCopier.staticCopy(apply, info,UnBoxingConverter.getInstance());
                    list.add(info);
                }
            }

            baseBatchResult.initPageParam(component);
            baseBatchResult.setPageList(list);
            baseBatchResult.setSuccess(true);
            return baseBatchResult;
    }

    @Override
    public SpecialPaperNoInfo findById(long id) {
        SpecialPaperNoInfo noInfo=new SpecialPaperNoInfo();
        SpecialPaperNoDO noDO=specialPaperNoDAO.findById(id);
        BeanCopier.staticCopy(noDO, noInfo,UnBoxingConverter.getInstance());
        return noInfo;
    }

    @Override
    public QueryBaseBatchResult<SpecialPaperNoInfo> checkInList(SpecialPaperQueryOrder order) {
        QueryBaseBatchResult<SpecialPaperNoInfo> baseBatchResult = new QueryBaseBatchResult<SpecialPaperNoInfo>();
        SpecialPaperNoDO queryDO=new SpecialPaperNoDO();
        Date applyTimeStart = null;
        Date applyTimeEnd = null;
        if(order.getStartNo()!=null){
            queryDO.setStartNo(Long.parseLong(order.getStartNo()));
        }
        if(order.getEndNo()!=null){
            queryDO.setEndNo(Long.parseLong(order.getEndNo()));
        }
        if (order.getStartDate() != null) {
            applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getStartDate());
            if(order.getEndDate()==null){
                applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getStartDate());
            }
        }
        if (order.getEndDate() != null) {
            applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getEndDate());
        }
        if (order.getKeepingManName() != null) {
            queryDO.setKeepingManName(order.getKeepingManName());
        }
        if("IS".equals(order.getHasLeft())){
            queryDO.setLeftPaper(1);
        }
        if(order.getSource()!=null){
            queryDO.setSource(order.getSource().code());
        }
        if(order.getParentId()>0){
            queryDO.setParentId(order.getParentId());
        }
        if("IS".equals(order.getCheckNo())){
            List<SpecialPaperNoDO> pageList= specialPaperNoDAO.checkNoBySource(queryDO);
            List<SpecialPaperNoInfo> list = baseBatchResult.getPageList();
            if (null!=pageList&&pageList.size() > 0) {
                for (SpecialPaperNoDO DO : pageList) {
                    SpecialPaperNoInfo info = new SpecialPaperNoInfo();
                    BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
                    list.add(info);
                }
                baseBatchResult.setPageList(list);
                baseBatchResult.setSuccess(true);
            }
        }
    else{
        long totalSize = specialPaperNoDAO.findByConditionCount(queryDO, applyTimeStart, applyTimeEnd);
        PageComponent component = new PageComponent(order, totalSize);
        List<SpecialPaperNoDO> pageList = specialPaperNoDAO.findByCondition(queryDO, component.getFirstRecord(), component.getPageSize(), order.getSortCol(), order.getSortOrder(), applyTimeStart, applyTimeEnd
        );

        List<SpecialPaperNoInfo> list = baseBatchResult.getPageList();
        if (totalSize > 0) {
            for (SpecialPaperNoDO DO : pageList) {
                SpecialPaperNoInfo info = new SpecialPaperNoInfo();
                BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
                list.add(info);
            }
        }
        baseBatchResult.initPageParam(component);
        baseBatchResult.setPageList(list);
        baseBatchResult.setSuccess(true);
    }

        return baseBatchResult;
    }

    @Override
    public List<SpecialPaperResultInfo> checkInvalid(SpecialPaperQueryOrder order) {
        List<SpecialPaperResultDO> pageList = Lists.newArrayList();
        List<SpecialPaperResultInfo> list = Lists.newArrayList();
        SpecialPaperResultDO queryCondition=new SpecialPaperResultDO();
        if (order != null)
            BeanCopier.staticCopy(order, queryCondition);
        if (order.getEndNo() != null)
            queryCondition.setEndNo(Long.parseLong(order.getEndNo()));
        if (order.getStartNo()!= null)
            queryCondition.setStartNo(Long.parseLong(order.getStartNo()));
            pageList = extraDAO.checkInvalidNo(queryCondition);
            for (SpecialPaperResultDO apply : pageList) {
                SpecialPaperResultInfo info=new SpecialPaperResultInfo();
                BeanCopier.staticCopy(apply, info,UnBoxingConverter.getInstance());
                list.add(info);
            }
        return list;
    }

    @Override
    public List<SpecialPaperResultInfo> checkSaveInvalid(SpecialPaperQueryOrder order) {
        List<SpecialPaperResultDO> pageList = Lists.newArrayList();
        List<SpecialPaperResultInfo> list = Lists.newArrayList();
        SpecialPaperResultDO queryCondition=new SpecialPaperResultDO();
        if (order != null)
            BeanCopier.staticCopy(order, queryCondition);
        if (order.getEndNo() != null)
            queryCondition.setEndNo(Long.parseLong(order.getEndNo()));
        if (order.getStartNo()!= null)
            queryCondition.setStartNo(Long.parseLong(order.getStartNo()));
        if (order.getIsSaveInvlid()!= null)
            queryCondition.setIsSaveInvail(order.getIsSaveInvlid());
        pageList = extraDAO.searchSpecialPaperInvalidList(queryCondition,0,999,null,null,null,null,null,null);
        for (SpecialPaperResultDO apply : pageList) {
            SpecialPaperResultInfo info=new SpecialPaperResultInfo();
            BeanCopier.staticCopy(apply, info,UnBoxingConverter.getInstance());
            list.add(info);
        }
        return list;
    }

    @Override
    public FcsBaseResult deleteById(final SpecialPaperNoOrder order) {
        return commonProcess(order, "删除特殊纸登记记录", new BeforeProcessInvokeService() {
            @Override
            public Domain before() {
                if (order.getId() <= 0) {
                    throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
                            "未找到特殊纸登记记录");
                } else {
                   specialPaperNoDAO.deleteById(order.getId());
                }
                return null;
            }
        }, null, null);
    }
}
