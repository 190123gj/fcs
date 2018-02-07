package com.born.fcs.face.integration.filesys;


/**
 * 对接档案系统（致得E6接口说明文档）
 *
 * @author wuzj
 */
public interface E6ApiService {

    /**
     * 登录档案系统、得到令牌
     * @return
     */
    String loginFileSys();

    /**
     * 根据档案号、档案所在数据表名(例:业务档案案卷)、件号、文件所在数据表名(例:业务档案卷内文件)查询对应的档案信息
     * @return
     */
    String getDocIds(String fileNum,String fileTable,String packingNum,String packingTable);

    /**
     * 根据docId查询对应的文档
     * @return
     */
    byte[] getDocByDocId(String docId);


}
