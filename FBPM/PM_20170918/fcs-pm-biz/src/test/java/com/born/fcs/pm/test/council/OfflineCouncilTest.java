package com.born.fcs.pm.test.council;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.council.CouncilService;

/**
 * 
 * 
 * @author jiajie
 * 
 */
public class OfflineCouncilTest extends SeviceTestBase {
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Autowired
	CouncilService councilService;
	
	@Autowired
	CouncilProjectService councilProjectService;
	
	@Autowired
	CouncilProjectVoteService councilProjectVoteService;
	
	@Autowired
	DateSeqService dateSeqService;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testSaveCouncilApply() {
		
		FcsBaseResult result = null;
		CouncilApplyOrder order = new CouncilApplyOrder();
		
		order.setProjectCode("1458111625315");
		order.setProjectName("f博恩担保业务系统项目");
		order.setCustomerId(11);
		order.setCustomerName("测试测试");
		order.setApplyManId(1);
		order.setApplyManName("某某");
		order.setApplyDeptId(2);
		order.setApplyDeptName("某某部");
		order.setCouncilType(1);
		order.setCouncilTypeDesc("项目评审会");
		order.setStatus(CouncilApplyStatusEnum.WAIT.code());
		
		result = councilApplyService.saveCouncilApply(order);
		
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryCouncilApply() {
		
		CouncilApplyQueryOrder order = new CouncilApplyQueryOrder();
		
		order.setPageSize(5);
		
		QueryBaseBatchResult<CouncilApplyInfo> result = councilApplyService
			.queryCouncilApply(order);
		
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryCouncil() {
		
		CouncilQueryOrder order = new CouncilQueryOrder();
		
		order.setPageSize(5);
		
		QueryBaseBatchResult<CouncilInfo> result = councilService.queryCouncil(order);
		
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryCouncilById() {
		
		CouncilInfo info = councilService.queryCouncilById(1);
		
		Assert.assertEquals(true, info.getCouncilId() > 0);
	}
	
	@Test
	public void addQueryCouncil() {
		
		CouncilOrder order = new CouncilOrder();
		
		order.setCouncilCode("（2016）第01期");
		order.setCouncilPlace("第3会议室");
		order.setCouncilSubject("风险审查");
		order.setCouncilType(1);
		order.setCouncilTypeName("项目评审会");
		order.setStartTime(new Date());
		order.setEndTime(new Date());
		order.setStatus(CouncilStatusEnum.NOT_BEGIN.code());
		order.setDecisionInstitutionId(1);
		order.setCreateManId(1);
		order.setCreateManAccount("user001");
		order.setCreateManName("张三");
		
		List<CouncilProjectInfo> projects = new ArrayList<CouncilProjectInfo>();
		CouncilProjectInfo project = new CouncilProjectInfo();
		project.setProjectCode("20160405511001");
		project.setProjectName("我的承销项目");
		projects.add(project);
		
		/*CouncilProjectInfo project2 = new CouncilProjectInfo();
		project2.setProjectCode("20160405122001");
		project2.setProjectName("我的企业债担保项目");
		projects.add(project2);*/
		
		order.setProjects(projects);
		
		List<CouncilParticipantInfo> participants = new ArrayList<CouncilParticipantInfo>();
		CouncilParticipantInfo participant = new CouncilParticipantInfo();
		participant.setParticipantId(1);
		participant.setParticipantName("李秘书");
		participants.add(participant);
		
		CouncilParticipantInfo participant2 = new CouncilParticipantInfo();
		participant2.setParticipantId(2);
		participant2.setParticipantName("王秘书");
		participants.add(participant2);
		
		order.setParticipants(participants);
		
		List<CouncilJudgeInfo> judges = new ArrayList<CouncilJudgeInfo>();
		CouncilJudgeInfo judge = new CouncilJudgeInfo();
		judge.setJudgeId(2);
		judge.setJudgeName("王总");
		judges.add(judge);
		
		CouncilJudgeInfo judge2 = new CouncilJudgeInfo();
		judge2.setJudgeId(3);
		judge2.setJudgeName("李总");
		judges.add(judge2);
		
		order.setJudges(judges);
		
		FcsBaseResult result = councilService.saveCouncil(order);
		
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryProjectVoteResul() {
		List<CouncilProjectVoteResultInfo> ls = councilProjectService
			.queryProjectVoteResultByCouncilId(7);
		Assert.assertEquals(true, ls.size() > 0);
		
	}
	
	@Test
	public void testQueryProjectVoteResult() {
		CouncilVoteProjectQueryOrder order = new CouncilVoteProjectQueryOrder();
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> result = councilProjectService
			.queryProjectVoteResult(order);
		Assert.assertEquals(true, result.getTotalCount() > 0);
		
	}
	
	@Test
	public void testProjectVote() {
		
		CouncilProjectVoteOrder voteOrder = new CouncilProjectVoteOrder();
		voteOrder.setCouncilId(9);
		voteOrder.setProjectCode("20160405122001");
		voteOrder.setJudgeId(3);
		voteOrder.setVoteResult(VoteResultEnum.NOTPASS.code());
		voteOrder.setVoteRemark("可以");
		FcsBaseResult result = councilProjectVoteService.updateCouncilProjectVote(voteOrder);
		Assert.assertEquals(true, result.isSuccess());
		
	}
	
	@Test
	public void councilCodeSeq() {
		
		//		String code = dateSeqService.getNextCouncilCodeSeq();
		//		
		//		System.out.println(">>>>>>>>" + code);
		//		Assert.assertEquals(true, StringUtil.isNotEmpty(code));
		
	}
	
	@Test
	public void councilAvailableCodeSeq() {
		
		//		String code = dateSeqService.getAvailableCouncilCodeSeq();
		//		
		//		System.out.println(">>>>>>>>" + code);
		//		Assert.assertEquals(true, StringUtil.isNotEmpty(code));
		
	}
	
}
