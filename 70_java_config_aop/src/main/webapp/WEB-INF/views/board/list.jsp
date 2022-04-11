<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />
<div class="container my-3">
<div class="row">
	<div class="col-sm-12 col-md-6">
		<h2>Product List
			<a href="/board/register" class="btn btn-outline-primary">REG</a>
		</h2>	
	</div>
	<div class="col-sm-12 col-md-6">
	<form action="/board/list" method="get">
		<div class="input-group mb-3">
		<c:set value="${pgn.pgvo.type }" var="typed"/>
  			<select class="form-select" name="type">
    			<option ${typed == null ? 'selected':'' }>Choose...</option>
    			<option value="t" ${typed eq 't' ? 'selected':'' }>Title</option>
    			<option value="c" ${typed eq 'c' ? 'selected':'' }>Content</option>
    			<option value="w" ${typed eq 'w' ? 'selected':'' }>Writer</option>
    			<option value="tc" ${typed eq 'tc' ? 'selected':'' }>Title or Content</option>
    			<option value="tw" ${typed eq 'tw' ? 'selected':'' }>Title or Writer</option>
    			<option value="cw" ${typed eq 'cw' ? 'selected':'' }>Content or Writer</option>
  			</select>
  			<input class="form-control" type="text" name="keyword" placeholder="Search" value="${pgn.pgvo.keyword }">
  			<input type="hidden" name="pageNo" value="1">
  			<input type="hidden" name="qty" value="${pgn.pgvo.qty }">
  			<button type="submit" class="btn btn-success position-relative">
  			Search
  			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
    		${pgn.totalCount }
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
      <th scope="col">Title</th>
      <th scope="col">Writer</th>
      <th scope="col">Read Count</th>
      <th scope="col">Mod At</th>
      <th scope="col">Atteched</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${list }" var="bvo">
    <tr>
      <th scope="row">${bvo.bno }</th>
      <td><a href="/board/detail?bno=${bvo.bno }&pageNo=${pgn.pgvo.pageNo}&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">${bvo.title }</a></td>
      <td>${bvo.writer } 
	      <c:if test="${bvo.cmtQty > 0 }">
	      	<span class="badge rounded-pill bg-info">${bvo.cmtQty }</span>
	      </c:if>
      </td>
      <td>${bvo.readCount }</td>
      <td>${bvo.modAt }</td>
      <td><c:if test="${bvo.hasFile > 0 }">
      	Y<!-- 파일이 첨부되어 있다는 이모티콘을 설정 -->
      </c:if></td>
    </tr>
    </c:forEach>    
  </tbody>
</table>
<ul class="pagination justify-content-center">
	<c:if test="${pgn.prev }">
    <li class="page-item">
      <a href="/board/list?pageNo=${pgn.startPage - 1 }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}" class="page-link">Prev</a>
    </li>
    </c:if>
    <c:forEach begin="${pgn.startPage }" end="${pgn.endPage }" var="i">
    <li class="page-item ${pgn.pgvo.pageNo == i ? 'active':''}"
     aria-current="page">
      <a class="page-link" href="/board/list?pageNo=${i }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">${i }</a>
    </li>
    </c:forEach>
    <c:if test="${pgn.next }">
    <li class="page-item">
      <a class="page-link" href="/board/list?pageNo=${pgn.endPage + 1 }&qty=${pgn.pgvo.qty}&type=${pgn.pgvo.type}&keyword=${pgn.pgvo.keyword}">Next</a>
    </li>
    </c:if>
  </ul>
</div>
<script>
let isUp = '<c:out value="${isUp}"/>';
let isDel = '<c:out value="${isDel}"/>';
if (parseInt(isUp)) {
	alert('게시글 등록 성공~');
}
if (parseInt(isDel)) {
	alert('게시글 삭제 성공~');
}
</script>
<jsp:include page="../common/footer.jsp" />