<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />

<div class="container">
  <main>
    <div class="py-5 text-center">
      <img class="d-block mx-auto mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
      <h2>Product Detial</h2>
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
<!-- 상품상세정보란 시작 -->
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">Product Information</h4>
        <form action="/product/modify" method="post">
          <input type="hidden" name="pno" value="${pvo.pno }">
          <div class="row g-3">
          
          <!-- writer -->
           <div class="col-12">
             <label for="writer" class="form-label">Writer</label>
             <div class="input-group has-validation">
               <span class="input-group-text">@</span>
               <input type="email" class="form-control" name="writer" 
               id="writer" value="${pvo.writer }" readOnly>
             </div>
           </div>
           
           <div class="col-12">
              <label for="regAt" class="form-label">Reg At</label>
              <input type="text" class="form-control" value="${pvo.regAt }" readOnly>
            </div>
            
            <div class="col-12">
              <label for="modAt" class="form-label">Mod At</label>
              <input type="text" class="form-control" value="${pvo.modAt }" readOnly>
            </div>
            
            <div class="col-12">
              <label for="readCount" class="form-label">Read Count</label>
              <input type="text" class="form-control" value="${pvo.readCount }" readOnly>
            </div>
            
            <div class="col-12">
              <label for="cmtQty" class="form-label">Comment Qty.</label>
              <input type="text" class="form-control" value="${pvo.cmtQty }" readOnly>
            </div>
            
         	<div class="col-12">
             <label for="pname" class="form-label">Product Name</label>
             <input type="text" class="form-control" name="pname" 
             id="pname" value="${pvo.pname }" readOnly>
           </div>

           <div class="col-12">
             <label for="desc" class="form-label">Description</label>
             <textarea class="form-control" name="description" 
             id="desc" placeholder="Description" readOnly>${pvo.description }</textarea>
           </div>

           <div class="col-md-5">
             <label for="madeBy" class="form-label">Made By</label>
             <!-- jsp에 변수 선언하는 방식 -->
             <c:set var="madeBy" value="${pvo.madeBy }"/>
             <select class="form-select" name="madeBy" id="madeBy" required disabled>
               <option value="">Choose...</option>
               <!-- ${madeBy }는 위에서 c:set으로 선언한 변수 madeBy를 불러온 것임 -->
               <!-- modify 할 때 작성자가 골랐던 것을 보여주기 위함 -->
               <option value="us" ${madeBy eq 'us' ? 'selected' : ''}>United States</option>
               <option value="ko" ${madeBy eq 'ko' ? 'selected' : ''}>Korea</option>
               <option value="cn" ${madeBy eq 'cn' ? 'selected' : ''}>China</option>
               <option value="eu" ${madeBy eq 'eu' ? 'selected' : ''}>European Union</option>
             </select>
           </div>

           <div class="col-md-4">
             <label for="cate" class="form-label">Category</label>
             <c:set var="cate" value="${pvo.category }"/>
             <select class="form-select" name="category" id="category" required disabled>
               <option value="">Choose...</option>
               <option value="Clothes" ${cate eq 'Clothes' ? 'selected' : ''}>Clothes</option>
               <option value="Electronic" ${cate eq 'Electronic' ? 'selected' : ''}>Electronic</option>
               <option value="Food" ${cate eq 'Food' ? 'selected' : ''}>Food</option>
               <option value="Health" ${cate eq 'Health' ? 'selected' : ''}>Health</option>
               <option value="Media" ${cate eq 'Media' ? 'selected' : ''}>Media</option>
             </select>
           </div>

           <div class="col-md-3">
             <label for="price" class="form-label">Price</label>
             <input type="number" class="form-control" id="price" name="price" value="${pvo.price }" required readOnly>
           </div>

           <c:if test="${ses.email eq pvo.writer }">
    		 <button type="button" id="modBtn" class="btn btn-outline-warning">MOD</button>
    		 <button type="button" id="delBtn" class="btn btn-outline-danger">DEL</button>
    	   </c:if>
         </div>
        </form>
      </div>
    </div>
  </main>
</div>
<form action="/product/remove" method="post" id="delForm">
	<input type="hidden" name="pno" value="${pvo.pno }">
</form>
<!-- 댓글영역 시작 -->
<div class="container">
	<c:if test="${ses.email ne null && ses.email ne '' }">
		<div class="input-group my-3">
			<span class="input-group-text" id="cmtWriter">${ses.email }</span>
	  		<input type="text" class="form-control" id="cmtText" placeholder="Add Comment">
	  		<button class="btn btn-success" id="cmtPostBtn" type="button">Post</button>
		</div>
	</c:if>
	<ul class="list-group list-group-flush" id="cmtListArea">
  		<li class="list-group-item d-flex justify-content-between align-items-start">
    		<div class="ms-2 me-auto">
      		<div class="fw-bold">Writer</div>
      		Content for Comment
    		</div>
    		<span class="badge bg-dark rounded-pill">modAt</span>
  		</li>
  	</ul>
<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">${ses.email }</h4>
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
	// comment를 위해 전역상수를 선언했다.
	const pnoVal = document.querySelector("input[name=pno]").value;
	// 작성자와 ses를 비교하기 위해 전역상수를 선언했다. 또 이건 js에서 읽지 못하기때문에 js에서 작성하지 않았고, 또 밑의 src보다 위에다 적어줘야 한다.
	const ses = '<c:out value="${ses.email}"/>';
</script>
<script src="/resources/js/product.detail.js"></script>
<script src="/resources/js/product.comment.js"></script>
<script>
	let isMod = '<c:out value="${isMod}"/>';
	if(parseInt(isMod)) {
		alert('상품 수정 성공~');
	}
	// detail 페이지 오는 순간 list를 부르기 위해 선언
	printCommentList(pnoVal);
</script>
<jsp:include page="../common/footer.jsp" />