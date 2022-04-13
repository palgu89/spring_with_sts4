<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- security 태그 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>
          <li><a href="/product/list" class="nav-link px-2 text-white">Products</a></li>
          <li><a href="/board/list" class="nav-link px-2 text-white">Board</a></li>

<!-- security 구역. 인증이 되면 안에 access하는 메서드. 메서드이름은 약속된 이름이다 -->
<sec:authorize access="isAuthenticated()">
<!-- 변수 선언. vo객체에서 가져옴 -->
<sec:authentication property="principal.mvo.email" var="authEmail"/>
<sec:authentication property="principal.mvo.nickName" var="authNick"/>
<sec:authentication property="principal.mvo.authList" var="auths"/>
<c:choose>
	<c:when test="${auths.stream().anyMatch(authVO -> authVO.auth.equals('ROLE_ADMIN')).get() }">
	<!-- auths에는 list가 들어있음 > stream으로 풀어서 > anyMatch로 비교 > authVO에서 빼서 그게 admin이면 get하겠다 -->
		<li><a href="/member/list" class="nav-link px-2 text-white">${authNick }(${authEmail })</a></li>
	</c:when>
	<c:otherwise>
		<li><a href="/member/detail?email=${authEmail }" class="nav-link px-2 text-white">${authNick }(${authEmail })</a></li>
	</c:otherwise>
</c:choose>
          <li><a href="" id="logoutLink" class="nav-link px-2 text-white">Log Out</a></li>
          <form action="/member/logout" method="post" id="logoutForm">
          	<input type="hidden" name="email" value="${authEmail }">
          </form>
</sec:authorize>
<!-- 인증되지 않은 아무나일 경우 -->
<sec:authorize access="isAnonymous()">
          <li><a href="/member/register" class="nav-link px-2 text-white">Sign Up</a></li>
          <li><a href="/member/login" class="nav-link px-2 text-white">Log In</a></li>
</sec:authorize>
        </ul>
      </div>
    </div>
  </header>
  
<script>
	document.getElementId('logoutLink').addEventListener('click',(e)=>{
		e.preventDefault();
		document.getElementId('logoutForm').submit();
	});
</script>
  