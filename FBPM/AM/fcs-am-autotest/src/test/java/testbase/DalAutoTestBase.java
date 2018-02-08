/**
 * 
 */
package testbase;


/**
 * @author Peng
 * 
 */
public class DalAutoTestBase extends ParametersAutoTestBase {
	/*
	 * 该层主要负责构造对数据表进行增、删、改、查和对象SET方法
	 */
	
	//	/**
	//	 * 插入 p2pDivistionTemplTrade表数据
	//	 * 
	//	 * @param
	//	 */
	//	protected long insertP2pDivistionTemplTrade(long templateId, String outTemplateId,
	//												long investTemplateId, long repayTemplateId,
	//												String partnerId, String partnerName) {
	//		
	//		P2pDivistionTemplTradeDO p2pDivistionTemplTradeDO = new P2pDivistionTemplTradeDO();
	//		p2pDivistionTemplTradeDO.setTemplateId(templateId);
	//		p2pDivistionTemplTradeDO.setOutTemplateId(outTemplateId);
	//		p2pDivistionTemplTradeDO.setInvestTemplateId(investTemplateId);
	//		p2pDivistionTemplTradeDO.setRepayTemplateId(repayTemplateId);
	//		p2pDivistionTemplTradeDO.setPartnerId(partnerId);
	//		p2pDivistionTemplTradeDO.setPartnerName(partnerName);
	//		
	//		templateId = p2pDivistionTemplTradeDAO.insertAutotest(p2pDivistionTemplTradeDO);
	//		return templateId;
	//	}
	//	
	//	/**
	//	 * 查询p2pDivistionTemplTrade表数据
	//	 * 
	//	 * @param userName
	//	 * @return
	//	 */
	//	protected P2pDivistionTemplTradeDO p2pDivistionTemplTradeFindByPartnerIdAndOutId(	String outTemplateId,
	//																						String partnerId) {
	//		P2pDivistionTemplTradeDO p2pDivistionTemplTradeDO = p2pDivistionTemplTradeDAO
	//			.queryTemplate(outTemplateId, partnerId);
	//		return p2pDivistionTemplTradeDO;
	//	}
	//	
	//	/**
	//	 * 清除 p2pDivistionTemplTrade表数据
	//	 * 
	//	 * @param id
	//	 */
	//	protected void clearP2pDivistionTemplTradeByTemplateId(String templateId) {
	//		p2pDivistionTemplTradeDAO.deleteById(templateId);
	//	}
	//	
	//	/**
	//	 * 清除 p2pDivistionTemplTrade表数据
	//	 * 
	//	 * @param id
	//	 */
	//	protected void clearP2pDivistionTemplTradeByOutTemplateId(String outTemplateId) {
	//		p2pDivistionTemplTradeDAO.deleteByOutTemplateIdAutotest(outTemplateId);
	//	}
	//	
	//	/**
	//	 * 插入 p2pDivistionTemplDetail表数据
	//	 * 
	//	 * @param
	//	 */
	//	protected long insertP2pDivistionTemplDetail(long detailId, String templateName,
	//													String divistionPhase, String templateStatus,
	//													String createDate, String lastModifyDate,
	//													String templateNote) {
	//		
	//		P2pDivistionTemplDetailDO p2pDivistionTemplDetailDO = new P2pDivistionTemplDetailDO();
	//		p2pDivistionTemplDetailDO.setDetailId(detailId);
	//		p2pDivistionTemplDetailDO.setTemplateName(templateName);
	//		p2pDivistionTemplDetailDO.setDivistionPhase(divistionPhase);
	//		p2pDivistionTemplDetailDO.setTemplateStatus(templateStatus);
	//		p2pDivistionTemplDetailDO.setCreateDate(createDate);
	//		p2pDivistionTemplDetailDO.setLastModifyDate(lastModifyDate);
	//		p2pDivistionTemplDetailDO.setTemplateNote(templateNote);
	//		
	//		detailId = p2pDivistionTemplDetailDAO.insertAutotest(p2pDivistionTemplDetailDO);
	//		return detailId;
	//	}
	//	
	//	/**
	//	 * 查询p2pDivistionTemplDetail表数据
	//	 * 
	//	 * @param userName
	//	 * @return
	//	 */
	//	protected P2pDivistionTemplDetailDO p2pDivistionTemplDetailFindByDetailId(long detailId) {
	//		P2pDivistionTemplDetailDO p2pDivistionTemplDetailDO = p2pDivistionTemplDetailDAO
	//			.queryTemplateDetail(detailId);
	//		return p2pDivistionTemplDetailDO;
	//	}
	//	
	//	/**
	//	 * 清除 p2pDivistionTemplDetail表数据
	//	 * 
	//	 * @param id
	//	 */
	//	protected void clearP2pDivistionTemplDetailByDetailId(long detailId) {
	//		p2pDivistionTemplDetailDAO.deleteById(detailId);
	//	}
	
	//	@Autowired
	//	protected P2pDivistionTemplTradeDAO p2pDivistionTemplTradeDAO = (P2pDivistionTemplTradeDAO) SpringUtil
	//		.getInstance().getBean("p2pDivistionTemplTradeDAO");
}
