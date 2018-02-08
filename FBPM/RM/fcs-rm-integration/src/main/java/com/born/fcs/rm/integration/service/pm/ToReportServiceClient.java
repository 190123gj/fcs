package com.born.fcs.rm.integration.service.pm;


import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.service.report.ToReportService;
import com.born.fcs.rm.integration.common.CallExternalInterface;
import com.born.fcs.rm.integration.common.impl.ClientAutowiredBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("toReportServiceClient")
public class ToReportServiceClient extends ClientAutowiredBaseService implements
        ToReportService {
    @Override
    public String getReleasingAmount(final int year,final int month) {
        return callInterface(new CallExternalInterface<String>() {

            @Override
            public String call() {
                return toReportWebService.getReleasingAmount(year,month);
            }
    });
}

    @Override
    public List<String> getRepayOrChargeAmount(final String deptCode,final String type,final int year,final int month) {
        return callInterface(new CallExternalInterface<List<String>>() {

            @Override
            public List<String> call() {
                return toReportWebService.getRepayOrChargeAmount(deptCode,type,year,month);
            }
        });
    }

    @Override
    public List<ToReportInfo> getExpectEvent(final String deptCodes,final int year,final int month) {
        return callInterface(new CallExternalInterface<List<ToReportInfo>>() {

            @Override
            public List<ToReportInfo> call() {
                return toReportWebService.getExpectEvent(deptCodes,year,month);
            }
        });
    }

    @Override
    public List<ToReportInfo> getProjectProcess(final int year,final int month) {
        return callInterface(new CallExternalInterface<List<ToReportInfo>>() {

            @Override
            public List<ToReportInfo> call() {
                return toReportWebService.getProjectProcess(year,month);
            }
        });
    }

    @Override
    public List<ToReportInfo> getReportGuaranteeStructre(final int year,final int month) {
        return callInterface(new CallExternalInterface<List<ToReportInfo>>() {

            @Override
            public List<ToReportInfo> call() {
                return toReportWebService.getReportGuaranteeStructre(year,month);
            }
        });
    }

    @Override
    public List<ToReportInfo> getReportGuaranteeStructreBalanceAmount(final int year,final int month) {
        return callInterface(new CallExternalInterface<List<ToReportInfo>>() {

            @Override
            public List<ToReportInfo> call() {
                return toReportWebService.getReportGuaranteeStructreBalanceAmount(year,month);
            }
        });
    }

    @Override
    public List<ToReportInfo> getReportGuaranteeStructreContractAmount(final int year,final int month) {
        return callInterface(new CallExternalInterface<List<ToReportInfo>>() {

            @Override
            public List<ToReportInfo> call() {
                return toReportWebService.getReportGuaranteeStructreContractAmount(year,month);
            }
        });
    }

}
