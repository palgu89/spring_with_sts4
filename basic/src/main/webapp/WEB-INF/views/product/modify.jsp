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
        <h4 class="mb-3">Product information Modify</h4>
        <form action="/product/modify" method="post" enctype="multipart/form-data">
        <input type="hidden" name="pno" value="${pvo.pno }">
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
                id="writer" value="${pvo.writer }" readonly>              
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
               id="pname" placeholder="Product Name" value="${pvo.pname }">              
            </div>

            <div class="col-12">
              <label for="desc" class="form-label">Description</label>
              <textarea class="form-control" name="description"
               id="desc" placeholder="Description">${pvo.description }</textarea>              
            </div>

            <div class="col-md-5">
              <label for="madeBy" class="form-label">Made By</label>
              <c:set var="madeby" value="${pvo.madeBy }"/>
              <select class="form-select" name="madeBy" id="madeBy"
               required>
                <option value="">Choose...</option>
                <option value="us" ${madeby eq 'us' ? 'selected' : ''}>United States</option>
                <option value="ko" ${madeby eq 'ko' ? 'selected' : ''}>Korea</option>
                <option value="cn" ${madeby eq 'cn' ? 'selected' : ''}>China</option>
                <option value="eu" ${madeby eq 'eu' ? 'selected' : ''}>European Union</option>
              </select>
            </div>

            <div class="col-md-4">
              <label for="cate" class="form-label">Category</label>
              <c:set var="cate" value="${pvo.category }"/>
              <select class="form-select" name="category" id="category"
               required>
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
              <input type="text" class="form-control" name="price"
               id="price" placeholder="" value="${pvo.price }" required>
            </div>

<c:set var="fList" value="${pdto.fList }" />
			<div class="col-12 mb-3">
				<ul class="list-group list-group-flush">
				<c:forEach items="${fList }" var="fvo">
				  <li class="list-group-item d-flex justify-content-between align-items-start">
				  <c:choose>
				  		<c:when test="${fvo.fileType > 0 }">
					  	<div>
					  		<img src="/upload/${fn:replace(fvo.saveDir, '\\', '/') }/${fvo.uuid }_th_${fvo.fileName }">
					  	</div>
				  		</c:when>
				  		<c:otherwise>
				  			<i class="far fa-file-word"></i>
				  		</c:otherwise>
				  </c:choose>
				    <div class="ms-2 me-auto">
				      <div class="fw-bold">${fvo.fileName }</div>
				      ${fvo.regAt }
				    </div>
				    <span class="badge bg-secondary rounded-pill">${fvo.fileSize } Byte</span>
				    <button type="button" data-uuid="${fvo.uuid }" class="btn btn-sm btn-danger py-0 file-x">X</button>
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

<script src="/resources/js/product.register.js"></script>
<script src="/resources/js/product.modify.js"></script>
<jsp:include page="../common/footer.jsp" />