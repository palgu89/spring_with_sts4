<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
        <!-- / 가 home을 가르킨다 -->
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>
          <li><a href="#" class="nav-link px-2 text-white">Features</a></li>
          <li><a href="#" class="nav-link px-2 text-white">Pricing</a></li>
          <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
          <li><a href="#" class="nav-link px-2 text-white">About</a></li>
        </ul>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
          <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search">
        </form>

		<c:choose>
			<c:when test="${ses.email ne null && ses.email ne ''}">
				<div class="text-end">
				<c:choose>
					<c:when test="${ses.grade > 100 }">
						<a href="/member/list" class="btn btn-outline-light me-2">
          					${ses.nickName }[${ses.email }]
          				</a>
					</c:when>
					<c:otherwise>
						<a href="/member/detail?email=${ses.email }" class="btn btn-outline-light me-2">
          					${ses.nickName }[${ses.email }]
          				</a>
					</c:otherwise>
				</c:choose>
          			
          			<a href="/member/logout" class="btn btn-warning">LOGOUT</a>
		        </div>
			</c:when>
			<c:otherwise>
				<div class="text-end">
		            <a href="/member/login" class="btn btn-outline-light me-2">Login</a>
		            <a href="/member/register" class="btn btn-warning">Sign-up</a>
		            <!-- register.jsp의 /member/register 경로는 post방식 여기는 get방식이기 때문에 주소값이 같아도 다르게 인식한다. -->
		            <!-- <a href="/WEB-INF/views/member/register.jsp" class="btn btn-warning">Sign-up</a> jsp에서는 이렇게 썼었음 -->
		        </div>
			</c:otherwise>
		</c:choose>

      </div>
    </div>
  </header>