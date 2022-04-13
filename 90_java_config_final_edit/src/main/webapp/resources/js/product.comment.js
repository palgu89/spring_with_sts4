async function postCommentToServer(cmtData) {
    try {
        const url = '/comment/post';
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
            pno : pnoVal,
            writer : document.getElementById('cmtWriter').innerText,
            content : cmtText.value
        };
        postCommentToServer(cmtData).then(result => {
            if (parseInt(result)) {
                alert("댓글 등록 성공~");
            }
            getCommentList(cmtData.pno);
        });
    }
});

async function spreadCommentFromServer(pno, page){
    try {
        const resp = await fetch('/comment/'+pno +'/'+page);
        const pagingHandler = await resp.json();
        return await pagingHandler;
    } catch (error) {
        console.log(error);
    }
}

function getCommentList(pno, page=1) {
    spreadCommentFromServer(pno, page).then(result => {
        // console.log(result);        
        if(result.cmtList.length){            
            const ul = document.getElementById('cmtListArea');
            if (page == 1) {
                ul.innerHTML = "";
            }
            for (let cvo of result.cmtList) {
                let li = `<li data-cno="${cvo.cno}" class="list-group-item d-flex justify-content-between align-items-start">`;
                li += `<div class="ms-2 me-auto"><div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content}</div>`;
                li += `<span class="badge bg-dark rounded-pill">${cvo.modAt}</span>`;
                li += `<button type="button" class="btn btn-sm btn-outline-warning py-0 mod" data-bs-toggle="modal" data-bs-target="#myModal">e</button>`;
                li += `<button type="button" class="btn btn-sm btn-outline-danger py-0 del">x</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
            let moreBtn = document.getElementById('moreBtn');
            if(result.pgvo.pageNo < result.endPage || result.next){
                moreBtn.style.visibility = 'visible';
                moreBtn.dataset.page = page + 1;
            }else {
                moreBtn.style.visibility = 'hidden';
            }
        }else{
            let li = `<li class="list-group-item">Comment List Empty</li>`;
            ul.innerHTML += li;
        }
    });
}

async function eraseCommentAtServer(cno) {
    try {
        const url = '/comment/'+cno;
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
        const url = '/comment/'+cmtDataMod.cno;
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
            getCommentList(pnoVal);
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
            getCommentList(pnoVal);
        });
        
    }else if(e.target.id == 'moreBtn'){
        getCommentList(pnoVal, parseInt(e.target.dataset.page));
    }
});