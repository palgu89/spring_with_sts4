package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BFileVO;

public interface BFileDAO {
	int insertBFile(BFileVO bfvo);
	List<BFileVO> selectListBFile(long bno);
	int deleteBFile(String uuid);
	int deleteAllBFile(long bno);
	long selectOneBno(String uuid);
	int selectOneFileCount(long bno);
	List<BFileVO> selectListAllFiles();
}
