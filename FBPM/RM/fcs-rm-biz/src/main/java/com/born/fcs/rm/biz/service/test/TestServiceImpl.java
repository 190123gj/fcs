package com.born.fcs.rm.biz.service.test;


import com.born.fcs.rm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.rm.biz.service.convert.UnBoxingConverter;
import com.born.fcs.rm.biz.service.exception.ExceptionFactory;
import com.born.fcs.rm.dal.dataobject.TestDO;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.rm.ws.info.test.TestInfo;
import com.born.fcs.rm.ws.order.test.TestOrder;
import com.born.fcs.rm.ws.order.test.TestQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.service.TestService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("testService")
public class TestServiceImpl extends BaseAutowiredDAOService implements
        TestService {
    @Override
    public TestInfo findById(long testId) {
        TestDO DO= testDAO.findById(testId);
        TestInfo info=new TestInfo();
        BeanCopier.staticCopy(DO, info);
        return info;
    }

    @Override
    public FcsBaseResult save(final TestOrder order) {
        FcsBaseResult result = new FcsBaseResult();
                TestDO test = null;
                //final Date now = FcsPmDomainHolder.get().getSysDate();
                if ( order.getTestId() > 0) {
                    test = testDAO.findById(order.getTestId());
                    if (test == null) {
                        throw ExceptionFactory
                                .newFcsException(FcsResultEnum.HAVE_NOT_DATA, "不存在");
                    }
                }

                if (test == null) { //新增
                    test = new TestDO();
                    BeanCopier.staticCopy(order, test, UnBoxingConverter.getInstance());
                    testDAO.insert(test);
                } else { //修改
                    BeanCopier.staticCopy(order, test, UnBoxingConverter.getInstance());
                    testDAO.update(test);

                }
                   result.setSuccess(true);
                   result.setMessage("测试新增成功！");
                   result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
               return result;
    }

    @Override
    public QueryBaseBatchResult<TestInfo> query(TestQueryOrder order) {
        QueryBaseBatchResult<TestInfo> baseBatchResult = new QueryBaseBatchResult<TestInfo>();

        TestDO queryCondition = new TestDO();

        if (order != null)
            BeanCopier.staticCopy(order, queryCondition);

        if (order.getTestKey() != null)
            queryCondition.setTestKey(order.getTestKey());
        if (order.getTestValue() != null)
            queryCondition.setTestValue(order.getTestValue());
        long totalSize = testDAO.findByConditionCount(queryCondition);

        PageComponent component = new PageComponent(order, totalSize);

        List<TestDO> pageList = testDAO.findByCondition(queryCondition,
                component.getFirstRecord(), component.getPageSize(),order.getSortCol(),order.getSortOrder());

        List<TestInfo> list = baseBatchResult.getPageList();
        if (totalSize > 0) {
            for (TestDO testDO : pageList) {
                TestInfo info =new TestInfo();
                BeanCopier.staticCopy(testDO, info);
                list.add(info);
            }
        }

        baseBatchResult.initPageParam(component);
        baseBatchResult.setPageList(list);
        baseBatchResult.setSuccess(true);
        return baseBatchResult;
    }

    @Override
    public FcsBaseResult deleteById(TestOrder order) {
        FcsBaseResult result = new FcsBaseResult();
             int deleteResult=0;
                if (order.getTestId() <= 0) {
                    throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到测试数据");
                } else {
                    TestDO DO=testDAO.findById(order.getTestId());
                    deleteResult=   testDAO.deleteById(order.getTestId());
                }
        if(deleteResult>0){
            result.setSuccess(true);
            result.setMessage("测试删除成功！");
            result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
        }else{
            result.setSuccess(false);
            result.setMessage("测试删除失败！");
            result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
        }

            return result;
    }
}
