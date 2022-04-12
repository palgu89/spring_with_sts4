package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.domain.BFileVO;
import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.repository.BCommentDAO;
import com.myweb.www.repository.BFileDAO;
import com.myweb.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private BCommentDAO cdao;
	
	@Inject
	private BFileDAO bfdao;
	
	// @Transactional	// 이거 넣으면 다 대기시켜서 중간에 다른게 들어올 틈을 안주기 때문에 확실한 방법이 됨
	// vo객체 db에 등록 -> bno를 다시 받아옴 -> file에 bno를 넣어줌 -> file db에 등록
	// 이 과정에서 중간에 아무것도 못하게 함
	// 원하는 작업 할 때 분류할 수 있는 최소 단위
	// update 한 뒤 select 할 때 문제가 발생되는 것임
	// executeUpdate와 executeQuery 사이에 무조건 트랜잭션을 걸어야 함
	
	@Transactional
	@Override	// file을 위해 기존과 다르게 변경
	public int register(BoardDTO bdto) {
		// list를 받기 위해 bdto에서 인덱스 0인 list를 가져오는 과정
		/*
		 * for (int i = 0; i < bdto.getBfList().size(); i++) {
		 * bfdao.insertBFile(bdto.getBfList().get(0)); }
		 */
		int isUp = bdao.insertBoard(bdto.getBvo());
		
			if(isUp > 0 && bdto.getBfList().size() > 0) {	// controller에서도 확인을 한번 하긴 하지만 안전하게 여기도 bdto.getBfList().size() > 0 조건을 넣어준다
				// vo 등록 한 뒤 bno를 들고 나와 file의 bno에 넣음
				long bno = bdao.selectOneBno();
				
				for(BFileVO bfvo : bdto.getBfList()) { 
					bfvo.setBno(bno);
					isUp *= bfdao.insertBFile(bfvo);
					// 하나라도 실패하면 다 실패해야하기 때문에 마지막에 isUp을 곱해서 (1 || 0) 실패했을 때 0이 되게 만듦
				}
			} 			
		
		return isUp;
	}

	/*
	 * @Override public List<BoardVO> getList() { return bdao.selectListBoard(); }
	 */

	@Transactional
	@Override
	public BoardDTO getDetail(long bno) {
		bdao.updateBoardReadCount(bno, 1);	// detail 뿐만 아니라 modify도 여기로 오고, 수정 후 다시 detail로 가기 때문에 modify일 땐 -2을 해줘서 readCount가 오르는 걸 막아야 한다
		return new BoardDTO(bdao.selectOneBoard(bno), bfdao.selectListBFile(bno));
	}

	@Override
	public int modify(BoardDTO bdto) {
		int isUp = bdao.updateBoard(bdto.getBvo());
		if(bdto.getBfList() != null) {
			if(isUp > 0 && bdto.getBfList().size() > 0) {
				long bno = bdto.getBvo().getBno();	// insert와 달리 굳이 bno를 서버에서 가져올 필요 없음. 이미 가지고 있음
				for(BFileVO bfvo : bdto.getBfList()) { 
					bfvo.setBno(bno);
					isUp *= bfdao.insertBFile(bfvo);
				}
			} 
		}
		bdao.updateBoardReadCount(bdto.getBvo().getBno(), -2);	// modify에 readCount를 -2 해줬
		return isUp;
	}

	@Override
	public int remove(long bno) {
		cdao.deleteAllBComment(bno);
		return bdao.deleteBoard(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.selectListBoardPaging(pgvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.selectOneTotalCount(pgvo);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)	// commit 된 상황을 읽어라(내가 삭제할 때 다른사람이 동시에 삭제하는 상황일 때)
	@Override
	public int removeFile(String uuid) {	// 최대한 많이 db를 들리기 위해 이렇게 작성 > 실무에선 최소한으로 들리는게 좋음
		long bno = bfdao.selectOneBno(uuid);	// front에서 bno 받아와도 됨
		int isDel = bfdao.deleteBFile(uuid);
		int cnt = bfdao.selectOneFileCount(bno);	// 얘 필요 없고, cnt를 그냥 -1 해줘도 됨
		bdao.updateBoardFileCount(bno, cnt);
		return isDel;
	}

}
