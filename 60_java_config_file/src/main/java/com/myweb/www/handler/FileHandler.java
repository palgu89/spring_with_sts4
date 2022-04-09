package com.myweb.www.handler;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myweb.www.domain.BFileVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@AllArgsConstructor
@Component	// ServletConfiguration에 등록해놔서 new 안해도 inject 할 수 있음
public class FileHandler {
	private final String UP_DIR = "C:\\_javaweb\\_java\\fileUpload";
	
	public List<BFileVO> uploadFiles(MultipartFile[] files) {
		// 많은 인원이 사용하면 무조건 중복이 생길 수 밖에 없기 때문에
		// 날짜별로 묶는 방법을 사용한다
		LocalDate date = LocalDate.now();
		String today = date.toString();	// 2022-01-10 의 형태 -> 2022년 폴더 > 1월 폴더 > 10일 폴더
		today = today.replace("-", File.separator);
		// File.separator : \
		// 2022\01\10 으로 바꿈 , linux는 2022/01/10
		
		File folders = new File(UP_DIR, today);
		
		if(!folders.exists()) {
			folders.mkdirs();	// 그 날짜의 폴더가 없으면 만듦
		}
		
		List<BFileVO> bfList = new ArrayList<BFileVO>();
		
		// jsp때처럼 아파치까진 할 필요가 없어보임
		for(MultipartFile file : files) {
			BFileVO fvo = new BFileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			// 확장자까지 전부 포함된 파일 이름
			String originalFileName = file.getOriginalFilename();
			log.debug(">>> originalFileName : {}", originalFileName);
			
			// only file name
			String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf("\\")+1);
			fvo.setFileName(onlyFileName);
			
			// 이걸로 일련번호를 만듦
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());
			
			// 이 윗부분은 fvo에 저장할 파일 정보 생성
			// 아래부터는 디스크에 저장할 파일 객체 생성
			String fullFileName = uuid.toString() + "_" + onlyFileName;
			File storeFile = new File(folders, fullFileName);
			
			// 파일 저장하려면 무조건 try/catch 걸자
			try {
				file.transferTo(storeFile);	// 실제 save 됨. 원본객체를 저장을 위한 형태로 만든 객체로 복사
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					File thumbNail = new File(folders, uuid.toString() + "_th_" + onlyFileName);
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);	// thumbnilrator 로도 만들수 있음
					
				}
			} catch (Exception e) {
				log.debug(">>> File 생성 오류 !!!");
				e.printStackTrace();
			}
			bfList.add(fvo);
			// bno가 없는 상태 > 나중에 넣을 거임 > 설명은 controller에 써놓음
		}
		return bfList;
	}

	private boolean isImageFile(File storeFile) throws IOException {
		// org.apache.tika > 실제 파일이 무슨 파일인지를 꺼내서 확인하는 라이브러리
		String mimeType = new Tika().detect(storeFile);	// mime : multimedia를 뜻함
		return mimeType.startsWith("image") ? true : false;
		// mdn mime type 구글링하면 설명 나옴. image가 앞에 붙는지 > image/gif, image/jpeg ...
	}

}
