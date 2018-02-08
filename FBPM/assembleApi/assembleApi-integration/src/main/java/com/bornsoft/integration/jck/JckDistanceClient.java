package com.bornsoft.integration.jck;

import java.util.Calendar;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.pub.interfaces.IKingdeeSystemService;
import com.bornsoft.pub.interfaces.IRiskSystemService;
import com.bornsoft.pub.order.electric.ElectricQueryOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder.QueryModeEnum;
import com.bornsoft.pub.order.kingdee.KingdeeVoucherNoRecevieOrder;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder;
import com.bornsoft.pub.result.electric.ElectricQueryResult;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.JsonParseUtil;

/**
  * @Description: 进出口项目远程客户端  
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午5:30:53
  * @version V1.0
 */
@Service("jckDistanceClient")
public class JckDistanceClient {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IRiskSystemService 	  riskSystemService;
	@Autowired
	private IKingdeeSystemService kingdeeSystemService;
	
	@IntegrationLog
	public BornSynResultBase recieveRiskInfo(RiskInfoRecOrder riskInfo){
		return riskSystemService.recieveRiskInfo(riskInfo);
	}
	@IntegrationLog
	public BornSynResultBase recieveVoucherNo(
			KingdeeVoucherNoRecevieOrder reqOrder) {
		logger.info("通知order= {}",reqOrder);
		return kingdeeSystemService.recieveRiskInfo(reqOrder);
	}
	
	@IntegrationLog
	public  ElectricQueryResult queryElectric(ElectricQueryOrder order){
		order.validateOrder();
		ElectricQueryResult result = new ElectricQueryResult();
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
		result.setResultMessage("查询成功");
		result.setData(makeTestData(order));
		return result;
		
	}
	
	private String makeTestData(ElectricQueryOrder order) {
		TreeMap<String,String> map = new TreeMap<String,String>();
		int total=0;
		switch (order.getQueryMode()) {
				case THAT_DAY:
					for(int i=1;i<=12;i++){
						total+=pading(QueryModeEnum.THAT_DAY ,map, i);
					}
					map.put("all", total+"");
					break;
				case DAYS:
					for(int i=1;i<=Integer.valueOf(order.getConditions());i++){
						total+=pading(QueryModeEnum.DAYS,map, i);
					}
					map.put("all", total+"");
					break;
				case WEEKS:
					for(int i=1;i<=Integer.valueOf(order.getConditions());i++){
						total+=pading(QueryModeEnum.WEEKS,map, i);
					}
					map.put("all", total+"");
					break;
				case MONTHS:
					for(int i=1;i<=Integer.valueOf(order.getConditions());i++){
						total+=pading(QueryModeEnum.MONTHS,map, i);
					}
					map.put("all", total+"");
					break;
				default:
					break;
			}
		return JsonParseUtil.toJSONString(map);
	}

	Random ran = new Random();
	private int pading(QueryModeEnum mode , TreeMap<String, String> map, int i) {
		Calendar cal =  Calendar.getInstance();
		String key = null;
		int v = ran.nextInt(1000);
		if(mode == QueryModeEnum.DAYS){
			cal.add(Calendar.DATE, 0-i);
			key = DateUtils.toString(cal.getTime(), DateUtils.PATTERN1);
		}else if(mode == QueryModeEnum.WEEKS){
			 cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周    
		     cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
			cal.add(Calendar.WEEK_OF_YEAR, 0-i);
			key = DateUtils.toString(cal.getTime(), DateUtils.PATTERN1);
		}else if(mode == QueryModeEnum.MONTHS){
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.MONTH, 0-i);
			key = DateUtils.toString(cal.getTime(), DateUtils.PATTERN1);
		}else{
			if(i<10){
				key = "0"+i;
			}else{
				key = i+"";
			}
		}
		map.put(key, v+"");
		
		return v;
	}

}
