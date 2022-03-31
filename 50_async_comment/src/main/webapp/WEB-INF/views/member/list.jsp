<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<div class="container my-3">
<h2>Member List</h2>
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Email</th>
      <th scope="col">Nick Name</th>
      <th scope="col">Reg At</th>
      <th scope="col">Last Login</th>
      <th scope="col">Grade</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${list }" var="mvo">
    <tr>
      <th scope="row">${mvo.email }</th>
      <td>${mvo.nickName }</td>
      <td>${mvo.regAt }</td>
      <td>${mvo.lastLogin }</td>
      <td>
      	<input type="number" class="form-control w-25 d-inline" value="${mvo.grade }">
      	<button type="button" class="btn btn-outline-danger py-1 grade">E</button>
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table>
</div>
<script src="/resources/js/member.list.js"></script>
<jsp:include page="../common/footer.jsp" />