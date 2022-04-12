package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BFileVO;

public interface FileDAO {
	int insertFile(BFileVO bfvo);
	List<BFileVO> selectListFile(long pno);
	int deleteFile(String uuid);
	int deleteAllFile(long pno);
	long selectOnePno(String uuid);
	int selectOneFileCount(long pno);
}
