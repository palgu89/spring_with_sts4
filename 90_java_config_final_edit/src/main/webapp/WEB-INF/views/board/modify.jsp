<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
      
<!-- 게시글 편집란 시작 -->
<c:set var="bvo" value="${bdto.bvo }" />
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">Board information Modify</h4>
        <form action="/board/modify" method="post" enctype="multipart/form-data">
        <input type="hidden" name="bno" value="${bvo.bno }">
        <input type="hidden" name="pageNo" value="${pgvo.pageNo }">
        <input type="hidden" name="qty" value="${pgvo.qty }">
        <input type="hidden" name="type" value="${pgvo.type }">
    	<input type="hidden" name="keyword" value="${pgvo.keyword }">
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
               id="title" placeholder="Title" value="${bvo.title }" >              
            </div>

            <div class="col-12">
              <label for="cont" class="form-label">Content</label>
              <textarea class="form-control" name="content" 
               id="cont" placeholder="Content">${bvo.content }</textarea>              
            </div>
<!-- 새로 등록한 파일리스트 출력 부분 -->
			<div class="col-12 d-grid">
			  <!-- 모양 변경을 위해 쓰는 팁: style="display: none; 으로 가려놓음 -->
			  <input class="form-control" type="file" style="display: none;"
			  id="files" name="files" multiple>
			  <!-- 버튼 클릭시 파일첨부 클릭 한 효과를 내게 할 것임 -->
			  <button type="button" id="trigger" class="btn btn-outline-primary btn-block d-block">Files Upload</button>
			</div>
			<div class="col-12" id="fileZone">
				
			</div>

<!-- 기존에 등록된 파일 리스트 출력 부분 -->
<c:set var="bfList" value="${bdto.bfList }" />
			<div class="col-12 mb-3">
				<ul class="list-group list-group-flush">
				<c:forEach items="${bfList }" var="bfvo">
				  <li class="list-group-item d-flex justify-content-between align-items-start">
				  <c:choose>
				  		<c:when test="${bfvo.fileType > 0 }">
					  	<div>
					  		<!-- ServletConfiguration.java에 /upload/ 쓰기로 해놨음 -->
					  		<!-- 위에 taglib으로 fn을 선언해 놨음 -->
					  		<!-- 파일이름 중 날짜가 2022\01\11 처럼 되어있어서 2022/01/11로 만듦 -->
					  		<img src="/upload/${fn:replace(bfvo.saveDir, '\\', '/') }/${bfvo.uuid }_th_${bfvo.fileName }">
					  	</div>
				  		</c:when>
				  		<c:otherwise>
				  			<!-- 클립모양의 아이콘을 넣어서 파일을 표현할 수 있음 -->
				  			<i class="far fa-file-word"></i>
				  		</c:otherwise>
				  </c:choose>
				    <div class="ms-2 me-auto">
				      <div class="fw-bold">${bfvo.fileName }</div>
				      ${bfvo.regAt }
				    </div>
				    <span class="badge bg-secondary rounded-pill">${bfvo.fileSize } Byte</span>
				    <button type="button" data-uuid="${bfvo.uuid }" class="btn btn-sm btn-danger py-0 file-x">X</button>
				  </li>
				 </c:forEach>
				 </ul>
			</div>

    		<button type="submit" class="btn btn-outline-warning" id="regBtn">Submit</button>
        </div>
        </form>
      </div>
    </div>
  </main>
</div>
<script src="/resources/js/board.register.js"></script>
<script src="/resources/js/board.modify.js"></script>
<jsp:include page="../common/footer.jsp" />