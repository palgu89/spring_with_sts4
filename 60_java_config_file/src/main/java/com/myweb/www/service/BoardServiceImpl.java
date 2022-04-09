package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

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

	@Override
	public List<BoardVO> getList() {		
		return bdao.selectListBoard();
	}

	@Override
	public BoardDTO getDetail(long bno) {
		return new BoardDTO(bdao.selectOneBoard(bno), bfdao.selectListBFile(bno));
	}

	@Override
	public int modify(BoardDTO bdto) {
		int isUp = bdao.updateBoard(bdto.getBvo());
		if(isUp > 0 && bdto.getBfList().size() > 0) {
			long bno = bdto.getBvo().getBno();	// insert와 달리 굳이 bno를 서버에서 가져올 필요 없음. 이미 가지고 있음
			for(BFileVO bfvo : bdto.getBfList()) { 
				bfvo.setBno(bno);
				isUp *= bfdao.insertBFile(bfvo);
			}
		} 
		
		return isUp;
	}

	@Override
	public int remove(long bno) {
		cdao.deleteAllBComment(bno);
		return bdao.deleteBoard(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pagingVO) {
		return bdao.selectListBoardPaging(pagingVO);
	}

	@Override
	public int getTotalCount(PagingVO pagingVO) {
		return bdao.selectOneTotalCount(pagingVO);
	}

	@Override
	public int removeFile(String uuid) {
		return bfdao.deleteBFile(uuid);
	}

}
