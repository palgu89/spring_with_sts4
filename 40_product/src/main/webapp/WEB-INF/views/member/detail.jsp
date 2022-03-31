<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />

<main>
<div class="container px-4 py-5" id="icon-grid">
    <h2 class="pb-2 border-bottom">Member Detail</h2>
	<form action="/member/modify" method="post">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 py-5">
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#bootstrap"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Email</h4>
          <p><input type="text" class="form-control" name="email" value="${mvo.email }" readonly></p>
        </div>
      </div>
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#cpu-fill"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Password</h4>
          <p><input type="password" id="pwd" class="form-control" name="pwd" value="${mvo.pwd }" readonly></p>
        </div>
      </div>
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#calendar3"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Nick Name</h4>
          <p><input type="text" id="nickName" class="form-control" name="nickName" value="${mvo.nickName }" readonly></p>
        </div>
      </div>
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#home"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Reg At</h4>
          <p>${mvo.regAt }"</p>
        </div>
      </div>
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#speedometer2"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Last Login</h4>
          <p>${mvo.lastLogin }</p>
        </div>
      </div>
      <div class="col d-flex align-items-start">
        <svg class="bi text-muted flex-shrink-0 me-3" width="1.75em" height="1.75em"><use xlink:href="#toggles2"/></svg>
        <div>
          <h4 class="fw-bold mb-0">Grade</h4>
          <p>${mvo.grade }</p>
        </div>
      </div>
    </div>
    <c:if test="${ses.email eq mvo.email }">
    	<button type="button" id="modBtn" class="btn btn-outline-warning">MOD</button>
    	<a href="/member/remove?email=${ses.email }" class="btn btn-outline-danger">DEL</a>
    </c:if>
    </form>
  </div>
</main>
<script src="/resources/js/member.detail.js"></script>
<script>
	let isUp = '<c:out value="${isUp}"/>';
	if(parseInt(isUp)) {
		alert("회원정보 수정 성공~");
	}
</script>
<jsp:include page="../common/footer.jsp" />