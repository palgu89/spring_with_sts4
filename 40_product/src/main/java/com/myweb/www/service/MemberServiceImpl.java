package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myweb.www.domain.MemberVO;
import com.myweb.www.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Inject
	private MemberDAO mdao;
	
	@Override
	public int register(MemberVO mvo) {
		return mdao.insert(mvo);
	}

	@Override
	public MemberVO login(MemberVO mvo) {	// 로그인 성공해야 last login이 update 된다
		MemberVO loginMvo = mdao.selectOne(mvo);
		if (loginMvo != null) {
			mdao.update(mvo.getEmail());			
		}
		return loginMvo;
	}	// 단 로그인 했을 때 화면에서 last login은 update 되지 않는다. -> update를 원하면 다시 한번 불러와야 한다.

	@Override
	public List<MemberVO> getList() {
		return mdao.selectList();
	}

	@Override
	public MemberVO getDetail(String email) {
		return mdao.selectOne(email);
	}

	@Override
	public int modify(MemberVO mvo) {
		return mdao.update(mvo);
	}

	@Override
	public int remove(String email) {
		return mdao.delete(email);
	}	// 멤버 탈퇴시 게시물, 댓글 삭제는 보통 하지 않고 작성자를 '탈퇴한 유저' 등으로 바꾼다 -> db에서 처리 ( foreign key ) ( 자바로 짤 순 있으나 불필요 )

}
