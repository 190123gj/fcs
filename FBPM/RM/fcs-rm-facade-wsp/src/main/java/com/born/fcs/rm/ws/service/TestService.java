package com.born.fcs.rm.ws.service;

import javax.jws.WebService;

import com.born.fcs.rm.ws.info.test.TestInfo;
import com.born.fcs.rm.ws.order.test.TestOrder;
import com.born.fcs.rm.ws.order.test.TestQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 * 测试一个
 *
 * @author heh
 */
@WebService
public interface TestService {


    TestInfo findById(long testId);


    FcsBaseResult save(TestOrder order);



    QueryBaseBatchResult<TestInfo> query(TestQueryOrder order);



    FcsBaseResult deleteById( TestOrder order);


}
