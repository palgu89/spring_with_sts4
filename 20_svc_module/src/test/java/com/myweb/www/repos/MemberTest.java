package com.myweb.www.repos;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.MemberVO;
import com.myweb.www.repository.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MemberTest {
	private static Logger logger = LoggerFactory.getLogger(MemberTest.class);
	
	@Inject
	private MemberDAO mdao;
	
	@Test
	public void insertMemberDummy() throws Exception {
		MemberVO mvo = new MemberVO();
		mvo.setEmail("tester@tester.com");
		mvo.setNickName("TESTER");
		mvo.setPwd("1111");
		mdao.insert(mvo);
	}
	@Test
	public void insertMemberDummies() throws Exception {
		for (int i = 0; i < 100; i++) {
			MemberVO mvo = new MemberVO();
			mvo.setEmail("tester" + i + "@tester.com");
			mvo.setNickName("TESTER" + i);
			mvo.setPwd("1111");
			mdao.insert(mvo);
		}
	}
	@Test
	public void getListMembers() throws Exception {
		List<MemberVO> list = mdao.selectList();
		for (MemberVO mvo : list) {
			logger.info(">>> {},{},{},{},{}", 
					mvo.getEmail(), mvo.getNickName(), mvo.getRegAt(), mvo.getLastLogin(), mvo.getGrade());
		}	// log 안찍어도 테이블이 콘솔에 나옴 -> META-INF - logback.xml에 <logger name="jdbc.sqlonly" level="info" appender-ref="STDOUT"></logger> 때문
	}
	
	// @Transactional 원래는 이게 있어야 함
	@Test
	public void loginMember() throws Exception {
		MemberVO mvo = new MemberVO();
		mvo.setEmail("tester@tester.com");
		mvo.setPwd("1111");
		
		mdao.update("tester@tester.com");	// last login update
		
		MemberVO loginMvo = mdao.selectOne(mvo);
		logger.info(">>> {},{},{},{}", 
				loginMvo.getEmail(), loginMvo.getNickName(), loginMvo.getLastLogin(), loginMvo.getGrade());
	}	// jsp에서는 안됐지만 spring에서 되는 이유는 -> <bean id="transactionmanager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		//											<property name="dataSource" ref="dataSource"></property>
		//									   </bean>
	@Test
	public void reMember() throws Exception {
		MemberVO mvo = new MemberVO();
		mvo.setEmail("tester@tester.com");
		mvo.setPwd("1111");
		mdao.selectOne("tester@tester.com");
	}
	@Test
	public void modifyMember() throws Exception {
		MemberVO mvo = new MemberVO();
		mvo.setEmail("tester1@tester.com");
		mvo.setPwd("1234");
		mvo.setNickName("TESTER01");
		mvo.setGrade(50);
		int isUp = mdao.update(mvo);
		logger.info(">>> Modify {}", isUp > 0 ? "Success" : "Fail");
	}
	@Test
	public void removeMember() throws Exception {
		int isUp = mdao.delete("tester@tester.com");
		logger.info(">>> Remove {}", isUp > 0 ? "Success" : "Fail");
	}
}
