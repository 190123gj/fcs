package com.bornsoft.api.service.facade.impl;

import java.util.Random;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.electric.ElectricQueryFacade;
import com.bornsoft.pub.order.electric.ElectricInfoExportOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder;
import com.bornsoft.pub.result.common.CoreExportDataResult;
import com.bornsoft.pub.result.electric.ElectricQueryResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.utils.tool.PropertiesUtil;

@Service("electricQueryFacade")
@SuppressWarnings("unused")
public class ElectricQueryFacadeImpl implements ElectricQueryFacade {

	private static String tempPath = PropertiesUtil.getProperty("capitalpool.export.temp.dir");
	private static String savePath = PropertiesUtil.getProperty("capitalpool.export.save.dir");
	private static String viewPath = PropertiesUtil.getProperty("capitalpool.export.view.dir");
	
	@Override
	public ElectricQueryResult queryElectric(ElectricQueryOrder order) {
		order.validateOrder();
		ElectricQueryResult result = new ElectricQueryResult();
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
		result.setResultMessage("查询成功");
		result.setData(makeTestData(order));
		return result;
	}
	
	@Override
	public CoreExportDataResult exportElectricInfo(ElectricInfoExportOrder order) {
		CoreExportDataResult result = new CoreExportDataResult();
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
		result.setResultMessage("查询成功");
		result.setDownloadUrl(viewPath+"power_montht.xls");
		return result;
	}

	private String makeTestData(ElectricQueryOrder order) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		int total =0;
		switch (order.getQueryMode()) {
		case THAT_DAY:
			for (int i = 1; i <= 12; i++) {
				total+=pading(map, i);
			}
			map.put("all", total+"");
			break;
		case DAYS:
			for (int i = 1; i <= Integer.valueOf(order.getConditions()); i++) {
				total+=pading(map, i);
			}
			map.put("all", total + "");
			break;
		case WEEKS:
			for (int i = 1; i <= Integer.valueOf(order.getConditions()); i++) {
				total+=pading(map, i);
			}
			map.put("all", total+"");
			break;
		case MONTHS:
			for (int i = 1; i <= Integer.valueOf(order.getConditions()); i++) {
				total+=pading(map, i);
			}
			map.put("all", total+"");
			break;
		default:
			break;
		}
		return JsonParseUtil.toJSONString(map);
	}

	Random ran = new Random();
	private int pading(TreeMap<String, String> map, int i) {
		int v  =ran.nextInt(1000);
		if (i < 10) {
			map.put("0" + i, v+"");
		} else {
			map.put("" + i, v+"");
		}
		return v;
	}

}
