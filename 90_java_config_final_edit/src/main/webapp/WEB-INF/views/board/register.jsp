<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />

<div class="container">
  <main>
    <div class="py-5 text-center">
      <img class="d-block mx-auto mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
      <h2>Product Register</h2>
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
      
<!-- 상품등록란 시작 -->
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">Input product information</h4>
        <!-- enctype="multipart/form-data" : 파일을 첨부하기 위해 필수로 써야 됨. 파일이 여기에 실림 -->
        <form action="/board/register" method="post" enctype="multipart/form-data">
          <div class="row g-3">
          
          	<div class="col-12">
              <label for="title" class="form-label">Title</label>
              <input type="text" class="form-control" name="title"
               id="title" placeholder="Title">              
            </div>

            <div class="col-12">
              <label for="email" class="form-label">Writer</label>
              <div class="input-group has-validation">
                <span class="input-group-text">@</span>
                <input type="email" class="form-control" name="writer"
                id="writer" placeholder="Writer">              
              </div>
            </div>

            <div class="col-12">
              <label for="cont" class="form-label">Content</label>
              <textarea class="form-control" name="content"
               id="desc" placeholder="Content"></textarea>              
            </div>

			<div class="col-12 d-grid">
			  <!-- 모양 변경을 위해 쓰는 팁: style="display: none; 으로 가려놓음 -->
			  <input class="form-control" type="file" style="display: none;"
			  id="files" name="files" multiple>
			  <!-- 버튼 클릭시 파일첨부 클릭 한 효과를 내게 할 것임 -->
			  <button type="button" id="trigger" class="btn btn-outline-primary btn-block d-block">Files Upload</button>
			</div>
			<div class="col-12" id="fileZone">
				
			</div>

          <button class="w-100 btn btn-primary btn-lg my-5" id="regBtn" type="submit">Continue to Register</button>
        </div>
        </form>
      </div>
    </div>
  </main>
</div>
<script src="/resources/js/board.register.js"></script>
<jsp:include page="../common/footer.jsp" />