async function postCommentToServer(cmtData) {
    try {
        const url = '/bcomment/post';
        const config = {
            method : 'post',
            headers : {
                'Content-Type':'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
document.getElementById("cmtPostBtn").addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    if (cmtText.value == null || cmtText.value =='') {
        alert('댓글 내용을 입력해주세요!');
        cmtText.focus();
        return false;
    } else {
        let cmtData ={
            bno : bnoVal,
            writer : document.getElementById('cmtWriter').innerText,
            content : cmtText.value
        };
        postCommentToServer(cmtData).then(result => {
            if (parseInt(result)) {
                alert("댓글 등록 성공~");
            }
            getCommentList(cmtData.bno);
        });
    }
});

async function spreadCommentFromServer(bno, page){
    console.log(bno);
    try {
        const resp = await fetch('/bcomment/'+bno+'/'+page);
        const pageHandler = await resp.json();
        return await pageHandler;
    } catch (error) {
        console.log(error);
    }
}

// 만약 result를 통째로 받는다면 이렇게 일일이 분해해줘야 한다.
/* function printPagination(result) {
    let prev = result.prev;
    let next = result.next;
    let startPage = result.startPage;
} */
function printPagination(prev, startPage, pgvo, endPage, next) {
    let pgn = document.getElementById('cmtPaging');
    pgn.innerHTML = '';
    let ul = '<ul class="pagination justify-content-center">';
    if(prev){
        ul += `<li class="page-item"><a class="page-link" href="${startPage-1}">Prev</a></li>`;
    }
    for(let i = startPage; i <= endPage; i++){
        ul += `<li class="page-item ${pgvo.pageNo == i ? 'active' : ''}" aria-current="page">`;
        ul += `<a class="page-link" href="${i}">${i}</a></li>`;
    }

    if(next){
        ul += `<li class="page-item"><a class="page-link" href="${endPage+1}">Next</a></li>`;
    }
    ul += '</ul>';
    pgn.innerHTML = ul;
    // 현재 a태그를 클릭하면 페이지가 이동되기 때문에(우리가 여태 했던 controller의 비동기 방식이 아님) 우리는 js로 a태그의 href의 기능을 막아줄 계획이다(또는 href를 없애거나)
    // 이미 list에서 만들어놔서 메서드만 잘 조정하면 된다
    // 이렇게 갖다 쓰는 형식을 모듈링이라고 부른다 > 여기서 더 커지면 API 서비스
}   // 이 다음 과정은 아래에 작성

//  page값이 안들어오면 무조건 1로 설정하겠다는 의미
function getCommentList(bno, page=1) {
    spreadCommentFromServer(bno, page).then(result => {
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        if(result.cmtListB.length){            
            // page가 1이면 초기화 한 뒤 처음부터 띄워줘야하기 때문에 if 설정
            /* if(page == 1){
                ul.innerHTML = "";
            } */
            // 위 if 때문에 comment의 2페이지를 누를 때 페이지가 바뀌는게 아니라 밑에 추가 됨 (more 때 썼던 거라서)
            ul.innerHTML = "";

            for (let cvo of result.cmtListB) {
                let li = `<li data-cno="${cvo.cno}" class="list-group-item d-flex justify-content-between align-items-start">`;
                li += `<div class="ms-2 me-auto"><div class="fw-bold">Anonymous</div>`;
                li += `${cvo.content}</div>`;
                li += `<span class="badge bg-dark rounded-pill">${cvo.modAt}</span>`;
                li += `<button type="button" class="btn btn-sm btn-outline-warning py-0 mod" data-bs-toggle="modal" data-bs-target="#myModal">e</button>`;
                li += `<button type="button" class="btn btn-sm btn-outline-danger py-0 del">x</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
            // 현재 page보다 더 있다고 판단되면 더 불러와야 함
            // result.next가 true여도 가능한데 지금은 안써도 됨 (1페이지부터 10페이지까지는)
            // 처음 로딩할 땐 필요없지만 만약 127개면 10페이지가 넘어서도 더 나와야 하니까 그땐 result.next가 무조건 필요

            // more 버튼 삭제로 인해 주석처리 함
            /* let moreBtn = document.getElementById('moreBtn');
            if(result.pgvo.pageNo < result.endPage || result.next){
                moreBtn.style.visibility = 'visible';
                moreBtn.dataset.page = page + 1;    // moreBtn.dataset.page += 1; 이렇게 하면 안 됨
            }else{
                moreBtn.style.visibility = 'hidden';
            } */
            
            // pagination을 만들기 위한 메서드
            // 우리가 원하는 정보는 모두 result 객체 즉, paginghandler에 들어있음
            // result를 통째로 보내도 되지만 여기서 그 값을 분해해서 넘기면 받는 쪽에서 좀 더 편하게 사용 할 수 있다
            printPagination(result.prev, result.startPage, result.pgvo, result.endPage, result.next);
            
        }else{
            let li = `<li class="list-group-item">Comment List Empty</li>`;
            ul.innerHTML += li;
        }
    });
}

async function eraseCommentAtServer(cno) {
    try {
        const url = '/bcomment/'+cno;
        const config = {
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
async function editCommentToServer(cmtDataMod) {
    try {
        const url = '/bcomment/'+cmtDataMod.cno;
        const config = {
            method: 'put', // patch도 있음
            headers: {
                'Content-Type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click', (e) => {
    if (e.target.classList.contains('del')) {
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;
        eraseCommentAtServer(cnoVal).then(result => {
            alert('댓글 삭제 성공~');
            getCommentList(bnoVal);
        });
    }else if(e.target.classList.contains('mod')){
        let li = e.target.closest('li');        
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;
        document.getElementById('cmtModBtn').setAttribute('data-cno', li.dataset.cno);
    }else if(e.target.id == 'cmtModBtn'){
        let cmtDataMod = {
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        };
        editCommentToServer(cmtDataMod).then(result => {
            if (parseInt(result)) {
                document.querySelector('.btn-close').click();
            }
            getCommentList(bnoVal);
        });
        
    }else if(e.target.classList.contains('page-link')){ // pagination의 a태그 기능 관련
        e.preventDefault();
        // list를 뿌려주는 쪽으로 주면 된다 > getCommentList\
        getCommentList(bnoVal, e.target.getAttribute('href'));  // e.target.getAttribute('href') > startPage+1 값을 가져옴
    }
    // more 버튼 삭제로 인해 주석처리 함
    /* else if(e.target.id == 'moreBtn'){
        // paging하기 위해 불러오는 메서드(처음 10개 부르고 버튼 누르면 페이지 값을 받아와 다음 10개 부르고)
        // e.target.dataset.page가 1이어도 문자이기 때문에 parseInt해줌
        getCommentList(bnoVal, parseInt(e.target.dataset.page));
    } */
    
    
});