package com.born.fcs.pm.ws.service.file;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.file.*;
import com.born.fcs.pm.ws.order.file.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * 档案管理
 *
 * @author heh
 */
@WebService
public interface FileService {

    /**
     * 根据ID查询档案信息
     * @param id
     * @return
     */
    FileInfo findById(long id);

    /**
     * 根据档案类型查询档案信息
     * @param type
     * @return
     */
    List<FileListInfo> findByType(String type);

    /**
     * 根据档案类型查询没有归档的文件
     * @param type
     * @return
     */
    List<FileListInfo> findByTypeAndNotArchive(String type);

    /**
     * 入库选择项目
     * @param order
     * @return
     */
    QueryBaseBatchResult<ProjectInfo> querySelectableProject(FileQueryOrder order);

    /**
     * 查询该项目没有审批的档案明细
     * @param type
     * @return
     */
    List<FileListInfo> searchNotArchiveByProjectCode(long formId,String type,String projectCode);

    /**
     * 查询该项目已归档的明细
     * @param type
     * @return
     */
    List<FileInputListInfo> searchArchivedByProjectCode(long formId,String type,String projectCode,String notNeedDraft,String no);


    /**
     * 保存文件目录
     * @param order
     * @return
     */
    FcsBaseResult save(FileCatalogOrder order);


    /**
     * 查询档案信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<FileFormInfo> query(FileQueryOrder order);

    /**
     * 保存入库申请
     * @param order
     * @return
     */
    FormBaseResult saveInput(FileOrder order);

    /**
     * 保存出库申请
     * @param order
     * @return
     */
    FormBaseResult saveOuput(FileOutputOrder order);

    /**
     * 保存借阅申请
     * @param order
     * @return
     */
    FormBaseResult saveBorrow(FileBorrowOrder order);

    /**
     * 根据formId查入库单信息
     * @param formId
     * @return
     */
    FileInfo findByFormId(long formId,String type);

    /**
     * 根据formId查出库单信息
     * @param formId
     * @return
     */
    FileOutputInfo findByOutputFormId(long formId);
    /**
     * 根据formId查入库单信息
     * @param formId
     * @return
     */
    FileBorrowInfo findByBorrowFormId(long formId);


    /**
     * 得到档案目录的checkStatus
     * @return
     */
     String findCheckStatus(String type);

    /**
     * 档案明细列表
     * @param order
     * @return
     */
    QueryBaseBatchResult<FileFormInfo> fileDetailList(FileQueryOrder order);


    /**
     * 根据明细ID查询档案信息
     * @param id
     * @return
     */
    FileInputListInfo findByInputId(long id);

    /**
     * 保存文档明细
     * @param order
     * @return
     */
    FcsBaseResult saveInputList(FileInputListOrder order);

    /**
     * 需要发消息的档案
     * @return
     */
    List<FileFormInfo> needMessageFile();

    /**
     * 需要发消息的档案
     * @return
     */
    List<FileInOutInfo> getInOutHistory(Long inputListId);

    /**
     * 更新档案的操作时间
     * @return
     */
    FcsBaseResult updateFileProjectTime(FileOrder order);

    /**
     * 档案一览表
     * @return
     */
    QueryBaseBatchResult<FileViewInfo> fileViewList(FileQueryOrder order);

    /**
     * 档案一览表-批量借出，得到可以借出的所有id集
     * @return
     */
    String getProjectInputStatusFileIds(String type, String projectCode, Date startTime, Date endTime);

    /**
     * 已解保项目导入
     * @return
     */
    FileImportBatchResult fileImport(ImportOrder orders);

    /**
     * 根据入库申请ids查询档案列表
     * @param ids
     * @return
     */
    List<FileInfo> findByInputIds(String ids);
    
    /**
     * 根据档案fileid查询
     * @param fileIds
     * @return
     */
    List<FileInfo> findByFileIds(String fileIds);
    
    QueryBaseBatchResult<FileInfo> queryFiles(FileBatchQueryOrder queryOrder);
    
    /**
     * 验证能否提交申请
     * @param order
     * @return
     */
    FcsBaseResult checkApply(FileBatchApplyOrder order);

    /**
     * 申请权利凭证展期
     * @param order
     * @return
     */
    FormBaseResult saveOuputExtension(FileOutputExtensionOrder order);

    /**
     * 根据formId查展期申请单信息
     * @param formId
     * @return
     */
    FileOutputExtensionInfo findByOutputExtensionFormId(long formId);

    /**
     * 展期申请列表
     * @param order
     * @return
     */
    QueryBaseBatchResult<FileFormInfo> extensionList(FileQueryOrder order);
}
