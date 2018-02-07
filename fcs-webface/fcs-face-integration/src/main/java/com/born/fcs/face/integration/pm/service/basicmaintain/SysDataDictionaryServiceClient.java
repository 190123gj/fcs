package com.born.fcs.face.integration.pm.service.basicmaintain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysDataDictionaryInfo;
import com.born.fcs.pm.ws.order.basicmaintain.SysDataDictionaryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.basicmaintain.SysDataDictionaryService;

/**
 * @author wuzj
 */
@Service("sysDataDictionaryServiceClient")
public class SysDataDictionaryServiceClient extends ClientAutowiredBaseService implements
																				SysDataDictionaryService {
	@Autowired
	private SysDataDictionaryService sysDataDictionaryWebService;
	
	@Override
	public FcsBaseResult save(final SysDataDictionaryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return sysDataDictionaryWebService.save(order);
			}
		});
	}
	
	@Override
	public SysDataDictionaryInfo findOne(final long id) {
		return callInterface(new CallExternalInterface<SysDataDictionaryInfo>() {
			
			@Override
			public SysDataDictionaryInfo call() {
				return sysDataDictionaryWebService.findOne(id);
			}
		});
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByDataCode(final DataCodeEnum dataCode) {
		return callInterface(new CallExternalInterface<List<SysDataDictionaryInfo>>() {
			
			@Override
			public List<SysDataDictionaryInfo> call() {
				return sysDataDictionaryWebService.findByDataCode(dataCode);
			}
		});
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByParentId(final long parentId) {
		return callInterface(new CallExternalInterface<List<SysDataDictionaryInfo>>() {
			
			@Override
			public List<SysDataDictionaryInfo> call() {
				return sysDataDictionaryWebService.findByParentId(parentId);
			}
		});
	}
	
	@Override
	public SysDataDictionaryInfo findOneCascade(final long id) {
		return callInterface(new CallExternalInterface<SysDataDictionaryInfo>() {
			
			@Override
			public SysDataDictionaryInfo call() {
				return sysDataDictionaryWebService.findOneCascade(id);
			}
		});
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByDataCodeCascade(final DataCodeEnum dataCode) {
		return callInterface(new CallExternalInterface<List<SysDataDictionaryInfo>>() {
			
			@Override
			public List<SysDataDictionaryInfo> call() {
				return sysDataDictionaryWebService.findByDataCodeCascade(dataCode);
			}
		});
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByParentIdCascade(final long parentId) {
		return callInterface(new CallExternalInterface<List<SysDataDictionaryInfo>>() {
			
			@Override
			public List<SysDataDictionaryInfo> call() {
				return sysDataDictionaryWebService.findByParentIdCascade(parentId);
			}
		});
	}
	
}
