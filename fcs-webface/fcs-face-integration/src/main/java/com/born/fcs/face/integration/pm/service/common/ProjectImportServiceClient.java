package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.excel.OldProjectExcelInfo;
import com.born.fcs.pm.ws.result.common.ProjectImportBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportResult;
import com.born.fcs.pm.ws.service.common.ProjectImportService;

@Service("projectImportServiceClient")
public class ProjectImportServiceClient extends ClientAutowiredBaseService implements
																			ProjectImportService {
	
	@Override
	public ProjectImportBatchResult importBatch(final List<OldProjectExcelInfo> oldInfos) {
		return callInterface(new CallExternalInterface<ProjectImportBatchResult>() {
			
			@Override
			public ProjectImportBatchResult call() {
				return projectImportWebService.importBatch(oldInfos);
			}
		});
	}
	
	@Override
	public ProjectImportResult importOne(final OldProjectExcelInfo oldInfos) {
		return callInterface(new CallExternalInterface<ProjectImportResult>() {
			
			@Override
			public ProjectImportResult call() {
				return projectImportWebService.importOne(oldInfos);
			}
		});
	}
	
	@Override
	public ProjectImportResult reImportOne(final OldProjectExcelInfo oldInfo) {
		return callInterface(new CallExternalInterface<ProjectImportResult>() {
			
			@Override
			public ProjectImportResult call() {
				return projectImportWebService.reImportOne(oldInfo);
			}
		});
	}
	
	@Override
	public ProjectImportBatchResult reImportBatch(final List<OldProjectExcelInfo> oldInfos) {
		return callInterface(new CallExternalInterface<ProjectImportBatchResult>() {
			
			@Override
			public ProjectImportBatchResult call() {
				return projectImportWebService.reImportBatch(oldInfos);
			}
		});
	}
}
