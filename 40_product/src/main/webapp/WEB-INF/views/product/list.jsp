<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<div class="container my-3">
<h2>
	Product List
	<c:if test="${ses.email ne null && ses.email ne '' }">
		<a href="/product/register" class="btn btn-outline-primary">REG</a>
	</c:if>
</h2>
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Category</th>
      <th scope="col">Product Name</th>
      <th scope="col">Price</th>
      <th scope="col">Read Count</th>
      <th scope="col">Comment Qty.</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${list }" var="pvo">
    <tr>
      <th scope="row">${pvo.pno }</th>
      <td>${pvo.category }</td>
      <td><a href="/product/detail?pno=${pvo.pno }">${pvo.pname }</a></td>
      <td>${pvo.price }</td>
      <td>${pvo.readCount }</td>
      <td>${pvo.cmtQty }</td>
    </tr>
   </c:forEach>
  </tbody>
</table>
</div>
<script>
	let isUp = '<c:out value="${isUp}"/>';
	let isDel = '<c:out value="${isDel}"/>';
	if(parseInt(isUp)) {
		alert('상품 등록 성공~');
	}
	if(parseInt(isDel)) {
		alert('상품 삭제 성공~');
	}
</script>
<jsp:include page="../common/footer.jsp" />