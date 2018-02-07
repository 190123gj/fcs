package com.born.fcs.face.integration.filesys;

import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.integration.pm.service.user.UserExtraMessageServiceClient;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.HttpClientUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 对接档案系统（致得E6接口说明文档）
 *
 * @author heh
 */
@Service("e6ApiService")
public class E6ApiServiceImpl  implements E6ApiService{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserExtraMessageServiceClient userExtraMessageServiceClient;
    @Autowired
    SysParameterServiceClient sysParameterServiceClient;
    @Override
    public String loginFileSys() {
        SessionLocal sessionLocal= ShiroSessionUtils.getSessionLocal();
        UserExtraMessageResult userExtraMessageResult=userExtraMessageServiceClient.findByUserId(sessionLocal.getUserId());
        UserExtraMessageInfo userExtraMessageInfo=userExtraMessageResult.getUserExtraMessageInfo();
        if(userExtraMessageInfo==null|| StringUtil.isBlank(userExtraMessageInfo.getFileSysUname())|| StringUtil.isBlank(userExtraMessageInfo.getFileSysPsw())){
            throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
                    "登录档案系统失败,请联系管理员添加您的档案系统用户名和密码!");
        }
        String loginUrl=getFileSysUrl()+"/ssoGetTicket.html";
        Map<String, String> params=new HashMap<>();
        params.put("username",userExtraMessageInfo.getFileSysUname());
        params.put("password",userExtraMessageInfo.getFileSysPsw());
        logger.info("登录档案系统地址：{}", loginUrl);
        logger.info("参数：{}", params);
        String ticket = HttpClientUtil.post(loginUrl, params,"UTF-8",120000);
        return ticket;
    }

    @Override
    public String getDocIds(String fileNum, String fileTable, String packingNum, String packingTable) {
        String ticket=loginFileSys();
        String getDocIdsUrl=getFileSysUrl()+"/";
        Map<String, String> params=new HashMap<>();
        params.put("ticket",ticket);
        params.put("fileNum",fileNum);
        params.put("fileTable",fileTable);
        params.put("packingNum",packingNum);
        params.put("packingTable",packingTable);
        logger.info("根据根据档案编号、件号获取docIds地址：{}", getDocIdsUrl);
        logger.info("参数：{}", params);
        String result = HttpClientUtil.post(getDocIdsUrl, params,"UTF-8",120000);
        return result;
    }

    @Override
    public byte[] getDocByDocId(String docId) {
        String ticket=loginFileSys();
        String readUrl=getFileSysUrl()+"/api/v2/document/read.html";
        Map<String, String> params=new HashMap<>();
        params.put("ticket",ticket);
        params.put("docId",docId);
        logger.info("根据docId查询文档地址：{}", readUrl);
        logger.info("参数：{}", params);
        byte[] bytes= HttpClientUtil.post(readUrl, params,"UTF-8",120000).getBytes();
        return bytes;
    }

    private String getFileSysUrl(){
        return sysParameterServiceClient.getSysParameterValue(SysParamEnum.FILE_SYSTEM_URL.code());
    }
}
