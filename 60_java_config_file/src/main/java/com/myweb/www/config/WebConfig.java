package com.myweb.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] {encodingFilter};
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		// 웹에서 파일 보여줄 수 있도록 하는 부분
		// 경로 지정 (_java에 새폴더 > fileUpload)
		// 맥은 c드라이브가 아니라 원하는 경로 받아오면 됨
		String uploadLocation = "C:\\_javaweb\\_java\\fileUpload";
		int maxFileSize = 1024 * 1024;	// 1 MB ( 1 byte * 1024 = KB -> *1024 = MB )
		int maxReqSize = maxFileSize * 5;	// 파일 요청 최대 크기
		int fileSizeThreshold = maxReqSize;	// 메모리에서 파일을 전송할 때 만들어지는 임시 공간의 크기
		
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
		// register에 던져주기 위해 작성
		registration.setMultipartConfig(multipartConfigElement);
	}
	

}
