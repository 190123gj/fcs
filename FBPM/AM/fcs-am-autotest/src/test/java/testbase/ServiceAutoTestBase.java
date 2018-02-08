/**
 * 
 */
package testbase;

import org.springframework.beans.factory.annotation.Autowired;

import com.autotest.util.SpringUtil;
import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;

/**
 * @author Peng
 * 
 */
public class ServiceAutoTestBase extends DalAutoTestBase {
	/**
	 * 该层为注入被测服务层
	 */
	
	@Autowired
	protected AssessCompanyApplyService assessCompanyApplyService = (AssessCompanyApplyService) SpringUtil
		.getInstance().getBean("assessCompanyApplyService");
	
}
