package com.born.fcs.face.integration.pm.service.file;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.file.*;
import com.born.fcs.pm.ws.order.file.*;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.file.FileService;

/**
 *
 * @author heh
 *
 */
@Service("fileServiceClient")
public class FileServiceClient extends ClientAutowiredBaseService implements FileService{
    @Override
    public FileInfo findById(final long id) {
        return callInterface(new CallExternalInterface<FileInfo>() {

            @Override
            public FileInfo call() {
                return fileWebService.findById(id);
            }
        });
    }
    @Override
    public List<FileListInfo> findByType(final String type) {
        return callInterface(new CallExternalInterface<List<FileListInfo>>() {

            @Override
            public List<FileListInfo> call() {
                return fileWebService.findByType(type);
            }
        });
    }
    @Override
    public List<FileListInfo> findByTypeAndNotArchive(final String type) {
        return callInterface(new CallExternalInterface<List<FileListInfo>>() {

            @Override
            public List<FileListInfo> call() {
                return fileWebService.findByTypeAndNotArchive(type);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<ProjectInfo> querySelectableProject(final FileQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {

            @Override
            public QueryBaseBatchResult<ProjectInfo> call() {
                return fileWebService.querySelectableProject(order);
            }
        });
    }

    @Override
    public List<FileInputListInfo> searchArchivedByProjectCode(final long formId,final String type,final String projectCode,final String notNeedDraft,final String no) {
        return callInterface(new CallExternalInterface<List<FileInputListInfo>>() {

            @Override
            public List<FileInputListInfo> call() {
                return fileWebService.searchArchivedByProjectCode(formId,type,projectCode,notNeedDraft,no);
            }
        });
    }
    @Override
    public List<FileListInfo> searchNotArchiveByProjectCode(final long formId,final String type,final String projectCode) {
        return callInterface(new CallExternalInterface<List<FileListInfo>>() {

            @Override
            public List<FileListInfo> call() {
                return fileWebService.searchNotArchiveByProjectCode(formId,type,projectCode);
            }
        });
    }
    @Override
    public FcsBaseResult save(final FileCatalogOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return fileWebService.save(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<FileFormInfo> query(final FileQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FileFormInfo>>() {

            @Override
            public QueryBaseBatchResult<FileFormInfo> call() {
                return fileWebService.query(order);
            }
        });
    }

    @Override
    public FormBaseResult saveInput(final FileOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return fileWebService.saveInput(order);
            }
        });
    }
    @Override
    public FormBaseResult saveOuput(final FileOutputOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return fileWebService.saveOuput(order);
            }
        });
    }

    @Override
    public FormBaseResult saveBorrow(final FileBorrowOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return fileWebService.saveBorrow(order);
            }
        });
    }

    @Override
    public FileInfo findByFormId(final long formId,final String type) {
        return callInterface(new CallExternalInterface<FileInfo>() {

            @Override
            public FileInfo call() {
                return fileWebService.findByFormId(formId,type);
            }
        });
    }

    @Override
    public FileOutputInfo findByOutputFormId(final long formId) {
        return callInterface(new CallExternalInterface<FileOutputInfo>() {

            @Override
            public FileOutputInfo call() {
                return fileWebService.findByOutputFormId(formId);
            }
        });
    }

    @Override
    public FileBorrowInfo findByBorrowFormId(final long formId) {
        return callInterface(new CallExternalInterface<FileBorrowInfo>() {

            @Override
            public FileBorrowInfo call() {
                return fileWebService.findByBorrowFormId(formId);
            }
        });
    }

    @Override
    public String findCheckStatus(final String type) {
        return callInterface(new CallExternalInterface<String>() {

            @Override
            public String call() {
                return fileWebService.findCheckStatus(type);
            }
        });
    }

    @Override
    public  QueryBaseBatchResult<FileFormInfo> fileDetailList(final FileQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FileFormInfo>>() {

            @Override
            public QueryBaseBatchResult<FileFormInfo> call() {
                return fileWebService.fileDetailList(order);
            }
        });
    }

    @Override
    public FileInputListInfo findByInputId(final long id) {
        return callInterface(new CallExternalInterface<FileInputListInfo>() {

            @Override
            public FileInputListInfo call() {
                return fileWebService.findByInputId(id);
            }
        });
    }



    @Override
    public FcsBaseResult saveInputList(final FileInputListOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return fileWebService.saveInputList(order);
            }
        });
    }

    @Override
    public List<FileFormInfo> needMessageFile() {
        return callInterface(new CallExternalInterface<List<FileFormInfo>>() {

            @Override
            public List<FileFormInfo> call() {
                return fileWebService.needMessageFile();
            }
        });
    }

    @Override
    public List<FileInOutInfo> getInOutHistory(final Long inputListId) {
        return callInterface(new CallExternalInterface<List<FileInOutInfo>>() {

            @Override
            public List<FileInOutInfo> call() {
                return fileWebService.getInOutHistory(inputListId);
            }
        });
    }

    @Override
    public FcsBaseResult updateFileProjectTime(final FileOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return fileWebService.updateFileProjectTime(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<FileViewInfo> fileViewList(final FileQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FileViewInfo>>() {
            @Override
            public QueryBaseBatchResult<FileViewInfo> call() {
                return fileWebService.fileViewList(order);
            }
        });
    }

    @Override
    public String getProjectInputStatusFileIds(final String type,final String projectCode,final Date startTime,final Date endTime) {
        return callInterface(new CallExternalInterface<String>() {
            @Override
            public String call() {
                return fileWebService.getProjectInputStatusFileIds(type,projectCode,startTime,endTime);
            }
        });
    }

    @Override
    public FileImportBatchResult fileImport(final ImportOrder order) {
        return callInterface(new CallExternalInterface<FileImportBatchResult>() {
            @Override
            public FileImportBatchResult call() {
                return fileWebService.fileImport(order);
            }
        });
    }
    
    @Override
    public List<FileInfo> findByInputIds(final String ids) {
        return callInterface(new CallExternalInterface<List<FileInfo>>() {

            @Override
            public List<FileInfo> call() {
                return fileWebService.findByInputIds(ids);
            }
        });
    }
	@Override
	public QueryBaseBatchResult<FileInfo> queryFiles(final FileBatchQueryOrder queryOrder) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FileInfo>>() {

            @Override
            public QueryBaseBatchResult<FileInfo> call() {
                return fileWebService.queryFiles(queryOrder);
            }
        });
	}
	@Override
	public FcsBaseResult checkApply(final FileBatchApplyOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return fileWebService.checkApply(order);
            }
        });
	}

    @Override
    public FormBaseResult saveOuputExtension(final FileOutputExtensionOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return fileWebService.saveOuputExtension(order);
            }
        });
    }

    @Override
    public FileOutputExtensionInfo findByOutputExtensionFormId(final long formId) {
        return callInterface(new CallExternalInterface<FileOutputExtensionInfo>() {

            @Override
            public FileOutputExtensionInfo call() {
                return fileWebService.findByOutputExtensionFormId(formId);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<FileFormInfo> extensionList(final FileQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<FileFormInfo>>() {

            @Override
            public QueryBaseBatchResult<FileFormInfo> call() {
                return fileWebService.extensionList(order);
            }
        });
    }

    @Override
	public List<FileInfo> findByFileIds(final String fileIds) {
        return callInterface(new CallExternalInterface<List<FileInfo>>() {

            @Override
            public List<FileInfo> call() {
                return fileWebService.findByFileIds(fileIds);
            }
        });
	}
}
