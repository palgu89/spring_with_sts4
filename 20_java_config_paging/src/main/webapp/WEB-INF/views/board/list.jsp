<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<div class="container my-3">
<h2>
	Product List
	<a href="/board/register" class="btn btn-outline-primary">REG</a>
</h2>
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Title</th>
      <th scope="col">Writer</th>
      <th scope="col">Read Count</th>
      <th scope="col">Mod At</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${list }" var="bvo">
    <tr>
      <th scope="row">${bvo.bno }</th>
      <td><a href="/board/detail?bno=${bvo.bno }&pageNo=${pgn.pgvo.pageNo}&qty=${pgn.pgvo.qty}">${bvo.title }</a></td>
      <td>${bvo.writer }</td>
      <td>${bvo.readCount }</td>
      <td>${bvo.modAt }</td>
    </tr>
   </c:forEach>
  </tbody>
</table>
<ul class="pagination justify-content-center">
	<c:if test="${pgn.prev }">
	    <li class="page-item">
	    <!-- /board/list?pageNo=${pgn.startPage - 1 }&qty=${pgn.pgvo.qty}는 페이지 시작 넘버와 몇개 보여줄지 다 보내줘야하기 때문에 이렇게 작성 -->
	    <!-- 네이버 뉴스 같은데 가보면 이런 주소를 많이 볼 수 있다 -->
	      <a href="/board/list?pageNo=${pgn.startPage - 1 }&qty=${pgn.pgvo.qty}" class="page-link">Prev</a>
	    </li>
    </c:if>
    <!-- i는 startPage와 endPage 사이의 갯수 -->
    <!-- ${pgn.pgvo.pageNo == i ? 'active' : '' }은 내가 클릭한 페이지를 active로 만들기 위한 방법 -->
    <c:forEach begin="${pgn.startPage }" end="${pgn.endPage }" var="i">
	    <li class="page-item ${pgn.pgvo.pageNo == i ? 'active' : '' }" 
	    aria-current="page">
	      <a class="page-link" href="/board/list?pageNo=${i }&qty=${pgn.pgvo.qty}">${i }</a>
	    </li>
    </c:forEach>
    <c:if test="${pgn.next }">
	    <li class="page-item">
	      <a class="page-link" href="/board/list?pageNo=${pgn.endPage + 1 }&qty=${pgn.pgvo.qty}">Next</a>
	    </li>
    </c:if>
  </ul>
</div>
<script>
	let isUp = '<c:out value="${isUp}"/>';
	let isDel = '<c:out value="${isDel}"/>';
	if(parseInt(isUp)) {
		alert('게시글 등록 성공~');
	}
	if(parseInt(isDel)) {
		alert('게시글 삭제 성공~');
	}
</script>
<jsp:include page="../common/footer.jsp" />