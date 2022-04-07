<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />

<div class="container">
  <main>
    <div class="py-5 text-center">
      <img class="d-block mx-auto mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
      <h2>Product Detail</h2>
      <p class="lead">Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it.</p>
    </div>

    <div class="row g-5">
      <div class="col-md-5 col-lg-4 order-md-last">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
          <span class="text-primary">Your cart</span>
          <span class="badge bg-primary rounded-pill">3</span>
        </h4>
        <ul class="list-group mb-3">
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0">Product name</h6>
              <small class="text-muted">Brief description</small>
            </div>
            <span class="text-muted">$12</span>
          </li>
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0">Second product</h6>
              <small class="text-muted">Brief description</small>
            </div>
            <span class="text-muted">$8</span>
          </li>
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0">Third item</h6>
              <small class="text-muted">Brief description</small>
            </div>
            <span class="text-muted">$5</span>
          </li>
          <li class="list-group-item d-flex justify-content-between bg-light">
            <div class="text-success">
              <h6 class="my-0">Promo code</h6>
              <small>EXAMPLECODE</small>
            </div>
            <span class="text-success">−$5</span>
          </li>
          <li class="list-group-item d-flex justify-content-between">
            <span>Total (USD)</span>
            <strong>$20</strong>
          </li>
        </ul>

        <form class="card p-2">
          <div class="input-group">
            <input type="text" class="form-control" placeholder="Promo code">
            <button type="submit" class="btn btn-secondary">Redeem</button>
          </div>
        </form>
      </div>
      
<!-- 게시글상세정보란 시작 -->
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">Board information</h4>
          <div class="row g-3">

            <div class="col-12">
              <label for="email" class="form-label">Writer</label>
              <div class="input-group has-validation">
                <span class="input-group-text">@</span>
                <input type="email" class="form-control" name="writer"
                id="writer" value="${bvo.writer }" readonly>              
              </div>
            </div>
            
            <div class="col-12">
              <label for="regAt" class="form-label">Reg At</label>
              <input type="text" class="form-control" value="${bvo.regAt }" readOnly>              
            </div>
            <div class="col-12">
              <label for="modAt" class="form-label">Mod At</label>
              <input type="text" class="form-control" value="${bvo.modAt }" readOnly>              
            </div>
            <div class="col-12">
              <label for="readCount" class="form-label">Read Count</label>
              <input type="text" class="form-control" value="${bvo.readCount }" readOnly>              
            </div>
            <div class="col-12">
              <label for="title" class="form-label">Title</label>
              <input type="text" class="form-control" name="title"
               id="title" placeholder="Title" value="${bvo.title }" readOnly>              
            </div>

            <div class="col-12">
              <label for="cont" class="form-label">Content</label>
              <textarea class="form-control" name="content" readOnly
               id="cont" placeholder="Content">${bvo.content }</textarea>              
            </div>
            
			<div class="col-4">
			<a href="/board/list?pageNo=${pgvo.pageNo }&qty=${pgvo.qty}&type=${pgvo.type}&keyword=${pgvo.keyword}" class="btn btn-primary">LIST</a>
			</div>
			<div class="col-4">
			<a href="/board/modify?bno=${bvo.bno }&pageNo=${pgvo.pageNo }&qty=${pgvo.qty}&type=${pgvo.type}&keyword=${pgvo.keyword}" id="modBtn" class="btn btn-outline-warning">MOD</a>
			</div>
			<div class="col-4">
    		<button type="button" id="delBtn" class="btn btn-outline-danger">DEL</button>
			</div>
    	   	
        </div>
      </div>
    </div>
  </main>
</div>
<form action="/board/remove" method="post" id="delForm">
	<input type="hidden" name="bno" value="${bvo.bno }">
	<input type="hidden" name="pageNo" value="${pgvo.pageNo }">
    <input type="hidden" name="qty" value="${pgvo.qty }">
    <input type="hidden" name="type" value="${pgvo.type }">
    <input type="hidden" name="keyword" value="${pgvo.keyword }">
</form>
<!-- 댓글영역 시작 -->
<div class="container">
<div class="input-group my-3">
	<span class="input-group-text" id="cmtWriter">Anonymous</span>
	<input type="text" class="form-control" id="cmtText" value="Test Add Comment ">
	<button class="btn btn-success" id="cmtPostBtn" type="button">Post</button>
</div>
<ul class="list-group list-group-flush" id="cmtListArea">
  <li class="list-group-item d-flex justify-content-between align-items-start">
    <div class="ms-2 me-auto">
      <div class="fw-bold">Writer</div>
      Content for Comment
    </div>
    <span class="badge bg-dark rounded-pill">modAt</span>
  </li>
</ul>
<div class="row">
	<div class="col"></div>
	<div class="col d-grid">
		<!-- 제일 처음이라 data-page를 그냥 1로 설정했다 -->
		<button type="button" id="moreBtn" data-page="1"
		 class="btn btn-outline-secondary btn-block" style="visibility: hidden">
		MORE +</button>
	</div>
	<div class="col"></div>
</div>
<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Anonymous</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        <div class="input-group my-3">
			<input type="text" class="form-control" id="cmtTextMod">
			<button class="btn btn-success" id="cmtModBtn" type="button">Post</button>
		</div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>
</div>
<script>
const bnoVal = document.querySelector("input[name=bno]").value;
</script>
<script src="/resources/js/board.detail.js"></script>
<script src="/resources/js/board.comment.js"></script>
<script>
let isMod = '<c:out value="${isMod}"/>';
if (parseInt(isMod)) {
	alert('게시글 수정 성공~');
}
getCommentList(bnoVal);
</script>
<jsp:include page="../common/footer.jsp" />