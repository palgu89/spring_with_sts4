package com.myweb.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myweb.www.domain.BFileVO;
import com.myweb.www.repository.BFileDAO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FileSweeper {
	// 그날 삭제 된 파일들을 view에서 그때 그떄 삭제하는게 아니라 모아뒀다가
	// 타이머를 맞춰서(스케쥴러) 그 시간에 삭제 됐던 db와 데이터가 남아있는 view를 비교해서 한번에 지우는 기능
	
	private final String BASE_PATH = "C:\\_javaweb\\_java\\fileUpload\\";
	
//	@Inject
//	private BFileDAO fdao;
	private final BFileDAO fdao;	// edit 5.
	
	// 초 분 시 일 월 요일 연도(생략 가능)
	@Scheduled(cron = "1 27 21 * * *")	// 1초 16분 21시 매일 매월 매요일
	public void fileSweeper() throws Exception {
		log.info(">>> FileSweeper Running Start : {}", LocalDateTime.now());
		
		// db에 등록된 파일 목록 가져오기
		List<BFileVO> dbFiles = fdao.selectListAllFiles();
		
		// 저장소를 검색할 떄 필요한 파일 경로 리스트 (실제 존재해야 될 리스트)
		List<String> currFiles = new ArrayList<String>();
		
		for(BFileVO fvo : dbFiles) {
			String filePath = fvo.getSaveDir() + "\\" + fvo.getUuid();	// \\를 넣는 이유 : 실제로 검사를 할 경로를 만들기 위해
			String fileName = fvo.getFileName();
			currFiles.add(BASE_PATH + filePath + "_" + fileName);
			// 이미지 파일일 경우 썸네일 파일 경로도 추가해줘야 함
			if(fvo.getFileType() > 0) {	// 0보다 크면 이미지
				currFiles.add(BASE_PATH + filePath + "_th_" + fileName);
			}
		}
		
		// 날짜를 반영한 폴더 구조 경로 만들기
		LocalDate now = LocalDate.now();	// 어제날짜로 하고 싶으면 LocalDate.now().minusDays(1L); > 이럼 새벽시간도 설정이 가능해짐
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// 경로를 기반으로 저장되어 있는 파일 검색
		File dir = Paths.get(BASE_PATH + today).toFile();	// Paths : 실제 로컬디스크의 디렉토리로 가서 검색. .toFile을 붙여주면 파일 전용 객체가 됨 > 패키지가 nio 패키지임(new io)
		File[] allFileObjects = dir.listFiles();	// 파일들을 list화 해서 객체화 함
		
		
		// 실제 저장되어 있는 파일과 DB에 기반하여 존재해야 되는 파일을 비교하여 없으면 삭제 진행
		for (File file : allFileObjects) {	// 전체 파일들을 돌면서
			String storedFileName = file.toPath().toString();	// 하나씩 string으로 뺴와서
			if(!currFiles.contains(storedFileName)) {	// db에 일치하는 이름이 없으면
				file.delete();	// 지운다
				log.info(">>> delete > {}", storedFileName);
			}
		}
		log.info(">>> FileSweeper Running Finish : {}", LocalDateTime.now());
	}
}
