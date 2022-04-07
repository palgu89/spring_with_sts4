<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<div class="container my-3">
<div class="row">
	<div class="col-sm-12 col-md-6">
		<h2>Product List
			<a href="/product/register" class="btn btn-outline-primary">REG</a>
		</h2>	
	</div>
<div class="col-sm-12 col-md-6">
	<form action="/product/list" method="get">
		<div class="input-group mb-3">
		<c:set value="${pgn.pgvo.type }" var="typed"/>
  			<select class="form-select" name="type">
    			<option ${typed == null ? 'selected':'' }>Choose...</option>
    			<option value="n" ${typed eq 'n' ? 'selected':'' }>Pname</option>
    			<option value="d" ${typed eq 'd' ? 'selected':'' }>Description</option>
    			<option value="m" ${typed eq 'm' ? 'selected':'' }>Made By</option>
    			<option value="nd" ${typed eq 'nd' ? 'selected':'' }>Pname or Description</option>
    			<option value="dm" ${typed eq 'dm' ? 'selected':'' }>Description or Made By</option>
    			<option value="nm" ${typed eq 'nm' ? 'selected':'' }>Pname or Made By</option>
  			</select>
  			<input class="form-control" type="text" name="keyword" placeholder="Search" value="${pgn.pgvo.keyword }">
  			<input type="hidden" name="pageNo" value="1">
  			<input type="hidden" name="qty" value="${pgn.pgvo.qty }">
  			<button type="submit" class="btn btn-success position-relative">
  			Search
  			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
    		${pgn.pgvo.totalCount }
    		<span class="visually-hidden">unread messages</span></span>
  			</button>
		</div>
	</form>
	</div>
</div>
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
      <td><a href="/product/detail?pno=${pvo.pno }&pageNo=${pgn.pgvo.pageNo}&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">${pvo.pname }</a></td>
      <td>${pvo.price }</td>
      <td>${pvo.readCount }</td>
      <td>${pvo.cmtQty }</td>
    </tr>
    </c:forEach>    
  </tbody>
</table>
<ul class="pagination justify-content-center">
	<c:if test="${pgn.prev }">
    <li class="page-item">
      <a href="/product/list?pageNo=${pgn.startPage - 1 }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}" class="page-link">Prev</a>
    </li>
    </c:if>
    <c:forEach begin="${pgn.startPage }" end="${pgn.endPage }" var="i">
    <li class="page-item ${pgn.pgvo.pageNo == i ? 'active':''}"
     aria-current="page">
      <a class="page-link" href="/product/list?pageNo=${i }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">${i }</a>
    </li>
    </c:forEach>
    <c:if test="${pgn.next }">
    <li class="page-item">
      <a class="page-link" href="/product/list?pageNo=${pgn.endPage + 1 }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">Next</a>
    </li>
    </c:if>
  </ul>
</div>
<script>
let isUp = '<c:out value="${isUp}"/>';
let isDel = '<c:out value="${isDel}"/>';
if (parseInt(isUp)) {
	alert('상품 등록 성공~');
}
if (parseInt(isDel)) {
	alert('상품 삭제 성공~');
}
</script>
<jsp:include page="../common/footer.jsp" />