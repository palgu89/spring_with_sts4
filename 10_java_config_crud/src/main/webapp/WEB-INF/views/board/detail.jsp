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
        <h4 class="mb-3">Board Information</h4>
          <div class="row g-3">
          
           <div class="col-12">
             <label for="writer" class="form-label">Writer</label>
             <div class="input-group has-validation">
               <span class="input-group-text">@</span>
               <input type="email" class="form-control" name="writer" 
               id="writer" value="${bvo.writer }" readOnly>
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
             id="title" value="${bvo.title }" readOnly>
           </div>

           <div class="col-12">
             <label for="cont" class="form-label">Content</label>
             <textarea class="form-control" name="content" 
             id="cont" placeholder="Content" readOnly>${bvo.content }</textarea>
           </div>

    		 <a href="/board/modify?bno=${bvo.bno }" id="modBtn" class="btn btn-outline-warning">MOD</a>
    		 <button type="button" id="delBtn" class="btn btn-outline-danger">DEL</button>
         </div>
      </div>
    </div>
  </main>
</div>
<form action="/board/remove" method="post" id="delForm">
	<input type="hidden" name="bno" value="${bvo.bno }">
</form>
<script src="/resources/js/board.detail.js"></script>
<script>
	let isMod = '<c:out value="${isMod}"/>';
	if(parseInt(isMod)) {
		alert('게시글 수정 성공~');
	}
</script>
<jsp:include page="../common/footer.jsp" />