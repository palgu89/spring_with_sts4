<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="common/header.jsp"/>
<jsp:include page="common/nav.jsp"/>

	<div class="p-5 mb-4 bg-light rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">Why Spring?</h1>
        <p class="col-md-8 fs-4">
        	Spring makes programming Java quicker, easier, and safer for everybody. Spring’s focus on speed, simplicity, and productivity has made it the world's most popular Java framework.
        </p>
      </div>
    </div>

<script>
	// register.jsp에서 isUp이 addFlashAttribute로부터 날라 옴
	let isUp = '<c:out value="${isUp}"/>';
	let isLogin = '<c:out value="${isLogin}"/>';
	let isOut = '<c:out value="${isOut}"/>';
	let isDel = '<c:out value="${isDel}"/>';
	
	if(parseInt(isUp)) {
		alert("회원가입 성공~");
	}
	if(parseInt(isLogin)) {
		alert("로그인 성공~");
	}
	if(parseInt(isOut)) {
		alert("로그아웃 성공~");
	}
	if(parseInt(isDel)) {
		alert("회원탈퇴 성공~");
	}
</script>
<jsp:include page="common/footer.jsp"/>