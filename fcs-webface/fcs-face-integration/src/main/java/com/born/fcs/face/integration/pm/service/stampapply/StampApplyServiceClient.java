package com.born.fcs.face.integration.pm.service.stampapply;

import com.born.fcs.pm.ws.info.stampapply.*;
import com.born.fcs.pm.ws.order.stampapply.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 *
 * @author heh
 *
 */
@Service("stampApplyServiceClient")
public class StampApplyServiceClient extends ClientAutowiredBaseService implements
        StampApplyService {


    @Override
    public StampApplyInfo findById(final long id,final String isReplace) {
        return callInterface(new CallExternalInterface<StampApplyInfo>() {

            @Override
            public StampApplyInfo call() {
                return stampApplyWebService.findById(id,isReplace);
            }
        });
    }

    @Override
    public StampApplyInfo findByFormId(final long formId,final String isReplace) {
        return callInterface(new CallExternalInterface<StampApplyInfo>() {

            @Override
            public StampApplyInfo call() {
                return stampApplyWebService.findByFormId(formId,isReplace);
            }
        });
    }

    @Override
    public List<StampAFileInfo> findByContractCode(final String contractCode) {
        return callInterface(new CallExternalInterface<List<StampAFileInfo>>() {

            @Override
            public List<StampAFileInfo> call() {
                return stampApplyWebService.findByContractCode(contractCode);
            }
        });
    }

    @Override
    public FormBaseResult save(final StampApplyOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {

            @Override
            public FormBaseResult call() {
                return stampApplyWebService.save(order);
            }
        });
    }

    @Override
    public StampAFileInfo findByFieldId(final long fileId) {
        return callInterface(new CallExternalInterface<StampAFileInfo>() {

            @Override
            public StampAFileInfo call() {
                return stampApplyWebService.findByFieldId(fileId);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<StampApplyProjectResultInfo> query(final StampApplyQueryOrder order,final String fromList) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<StampApplyProjectResultInfo>>() {

            @Override
            public QueryBaseBatchResult<StampApplyProjectResultInfo> call() {
                return stampApplyWebService.query(order,fromList);
            }
        });
    }

    @Override
    public FormBaseResult revokeStatusById(final StampApplyOrder order) {
       return callInterface(new CallExternalInterface<FormBaseResult>() {
            @Override
            public FormBaseResult call() {
               return stampApplyWebService.revokeStatusById(order);
            }
        });
    }

    @Override
    public FormBaseResult deleteById(final StampApplyOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {
            @Override
            public FormBaseResult call() {
                return stampApplyWebService.deleteById(order);
            }
        });
    }

    @Override
    public FcsBaseResult saveStampConfigure(final StampConfigureOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return stampApplyWebService.saveStampConfigure(order);
            }
        });
    }

    @Override
    public List<StampConfigureListInfo> getStampConfig() {
        return callInterface(new CallExternalInterface<List<StampConfigureListInfo>>() {
            @Override
            public List<StampConfigureListInfo> call() {
                return stampApplyWebService.getStampConfig();
            }
        });
    }

    @Override
    public StampConfigureListInfo getStampConfigByOrgName(final String orgName) {
        return callInterface(new CallExternalInterface<StampConfigureListInfo>() {
            @Override
            public StampConfigureListInfo call() {
                return stampApplyWebService.getStampConfigByOrgName(orgName);
            }
        });
    }

    @Override
    public FcsBaseResult saveStampBasicData(final StampBasicDataOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return stampApplyWebService.saveStampBasicData(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<StampBasicDataListInfo> queryBasicData(final StampBasicDataQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<StampBasicDataListInfo>>() {
            @Override
            public QueryBaseBatchResult<StampBasicDataListInfo> call() {
                return stampApplyWebService.queryBasicData(order);
            }
        });
    }

    @Override
    public List<StampBasicDataListInfo> getStampBasicData() {
        return callInterface(new CallExternalInterface<List<StampBasicDataListInfo>>() {
            @Override
            public List<StampBasicDataListInfo> call() {
                return stampApplyWebService.getStampBasicData();
            }
        });
    }

    @Override
    public StampBasicDataApplyInfo findStampBasicDataApplyById(final long id) {
        return callInterface(new CallExternalInterface<StampBasicDataApplyInfo>() {
            @Override
            public StampBasicDataApplyInfo call() {
                return stampApplyWebService.findStampBasicDataApplyById(id);
            }
        });
    }

    @Override
    public StampBasicDataApplyInfo findStampBasicDataApplyByFormId(final long formId) {
        return callInterface(new CallExternalInterface<StampBasicDataApplyInfo>() {
            @Override
            public StampBasicDataApplyInfo call() {
                return stampApplyWebService.findStampBasicDataApplyByFormId(formId);
            }
        });
    }

    @Override
    public FormBaseResult saveStampBasicDataApply(final StampBasicDataApplyOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {
            @Override
            public FormBaseResult call() {
                return stampApplyWebService.saveStampBasicDataApply(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<StampApplyProjectResultInfo> queryStampBasicDataApply(final StampApplyQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<StampApplyProjectResultInfo>>() {
            @Override
            public QueryBaseBatchResult<StampApplyProjectResultInfo> call() {
                return stampApplyWebService.queryStampBasicDataApply(order);
            }
        });
    }
//    @Override
//    public FormBaseResult updateIsPass(final StampApplyOrder order) {
//        return callInterface(new CallExternalInterface<FormBaseResult>() {
//            @Override
//            public FormBaseResult call() {
//                return stampApplyWebService.updateIsPass(order);
//            }
//        });
//    }

}
