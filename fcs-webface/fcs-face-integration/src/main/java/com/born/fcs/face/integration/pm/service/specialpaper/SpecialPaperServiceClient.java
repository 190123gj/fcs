package com.born.fcs.face.integration.pm.service.specialpaper;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperNoInfo;
import com.born.fcs.pm.ws.info.specialpaper.SpecialPaperResultInfo;
import com.born.fcs.pm.ws.order.specialpaper.*;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.specialpaper.SpecialPaperService;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

import java.util.List;


/**
 *
 * @author heh
 *
 */
@Service("specialPaperServiceClient")
public class SpecialPaperServiceClient extends ClientAutowiredBaseService implements
        SpecialPaperService {


    @Override
    public FcsBaseResult checkIn(final SpecialPaperNoListOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return specialPaperWebService.checkIn(order);
            }
        });
    }

    @Override
    public FcsBaseResult provideDept(final SpecialPaperProvideDeptOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return specialPaperWebService.provideDept(order);
            }
        });
    }

    @Override
    public FcsBaseResult provideProject(final SpecialPaperProvideProjectOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return specialPaperWebService.provideProject(order);
            }
        });
    }

    @Override
    public FcsBaseResult invalid(final SpecialPaperInvalidOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return specialPaperWebService.invalid(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<SpecialPaperResultInfo> specialPaperList(final SpecialPaperQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<SpecialPaperResultInfo>>() {

            @Override
            public QueryBaseBatchResult<SpecialPaperResultInfo> call() {
                return specialPaperWebService.specialPaperList(order);
            }
        });
    }


    @Override
    public  SpecialPaperNoInfo findById(final long id) {
        return callInterface(new CallExternalInterface<SpecialPaperNoInfo>() {

            @Override
            public SpecialPaperNoInfo call() {
                return specialPaperWebService.findById(id);
            }
        });
    }
    @Override
    public QueryBaseBatchResult<SpecialPaperNoInfo> checkInList(final SpecialPaperQueryOrder order){
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<SpecialPaperNoInfo>>() {

            @Override
            public QueryBaseBatchResult<SpecialPaperNoInfo> call() {
                return specialPaperWebService.checkInList(order);
            }
        });
    }

    public FcsBaseResult deleteById(final SpecialPaperNoOrder order){
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return specialPaperWebService.deleteById(order);
            }
        });
    }
    public List<SpecialPaperResultInfo> checkInvalid(final SpecialPaperQueryOrder order){
        return callInterface(new CallExternalInterface<List<SpecialPaperResultInfo>>() {

            @Override
            public List<SpecialPaperResultInfo> call() {
                return specialPaperWebService.checkInvalid(order);
            }
        });
    }

    public List<SpecialPaperResultInfo> checkSaveInvalid(final SpecialPaperQueryOrder order){
        return callInterface(new CallExternalInterface<List<SpecialPaperResultInfo>>() {

            @Override
            public List<SpecialPaperResultInfo> call() {
                return specialPaperWebService.checkSaveInvalid(order);
            }
        });
    }

}
