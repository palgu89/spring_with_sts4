async function postCommentToServer(cmtData) {
    // db와의 통신이기 때문에 본인이 아닌것과의 연결은 string으로 연결되기 때문에 꼭 try/catch 해주자
    // jQuery는 다른 것이 있는데, 지금 배우는건 바닐라 js라 try/catch 사용
    try {
        const url = '/comment/post';
        const config = {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
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

document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    if(cmtText.value == null || cmtText.value == '') {
        alert('댓글 내용을 입력해 주세요!');
        cmtText.focus();
        return false;
    }else{
        let cmtData = {
            pno : pnoVal,   // product detail.jsp에 script로 전역상수로 선언해뒀음
            writer : document.getElementById('cmtWriter').innerText,
            content : cmtText.value
        };
        postCommentToServer(cmtData).then(result => {
            if(parseInt(result)){
                alert('댓글 등록 성공~');
            }
            printCommentList(cmtData.pno);
        });
    }
});

async function spreadCommentFromServer(pno) {
    try {
        // 내가 가져오기만 하면 되니까 post도 필요없고, config도 필요 없음 > get방식으로 요청
        // 내가 요청하는 req에 내가 요청하는 service를 재 요청한다는 의미(?)
        // 주의: 내가 뭘 부르는지 알면 안됨 (/comment/list/pno > 이런식으로 뭔지 알면 안됨) > 보안성
        // post나 register 등 어쩔 수 없는 경우는 써도 됨. list는 없어도 되니까 안쓰는 것임
        const resp = await fetch('/comment/' + pno);
        const cmtList = await resp.json();  // 일반적 텍스트가 아니라 객체 형식을 요구하는거니까 json
        return await cmtList;   // 위에 json으로 변환요청했기 때문에 text보다 느리기 때문에 꼭 return에 await를 걸어주자
    } catch (error) {
        console.log(error);
    }
}

function printCommentList(pno) {
    // pno 대신 pnoVal써도 됨. 위에 pnoVal이 있기 때문에
    spreadCommentFromServer(pno).then(result =>{
       // console.log(result);    // 잘 나온다면 다시 js에서 풀어헤쳐서 뷰로 보내주면 됨

       const ul = document.getElementById('cmtListArea');
       ul.innerHTML = "";
       if(result.length){
           
            for (let cvo of result) {
                let li = `<li data-cno="${cvo.cno}" class="list-group-item d-flex justify-content-between align-items-start">`;
                li += `<div class="ms-2 me-auto"><div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content}</div>`;
                li += `<span class="badge bg-dark rounded-pill">${cvo.modAt}</span>`;
                if(ses == cvo.writer){
                    li += `<button type="button" class="btn btn-sm btn-outline-warning py-0 mod" data-bs-toggle="modal" data-bs-target="#myModal">e</button>`;
                    li += `<button type="button" class="btn btn-sm btn-outline-danger py-0 del">x</button>`;
                }
                li += `</li>`;
                ul.innerHTML += li;
            }
       }else{
            let li = `<li class="list-group-item">Comment List Empty</li>`;
            ul.innerHTML += li;
       }
    });
}

async function eraseCommentAtServer(cnoVal){
    try {
        const url = '/comment/' + cnoVal;
        // list와 똑같다. 레스트풀 기술을 제대로 사용하기 위해선 전송방식이 get/post만으론 부족하다
        // 그래서 put(또는 patch)/delete/... 를 추가했다. 아래 method를 보자
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

async function editCommentToServer(cmtDataMod){
    try {
        const url = '/comment/' + cmtDataMod.cno;
        const config = {
            method: 'put', // patch도 있음
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
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

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('del')){
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;
        eraseCommentAtServer(cnoVal).then(result =>{
            alert('댓글 삭제 성공~');
            printCommentList(pnoVal);
        });
    }else if(e.target.classList.contains('mod')){
        
        let li = e.target.closest('li');
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;
        // 우리 눈엔 텍스트로 보이지만 실제론 객체이므로 뒤에 nodeValue를 붙여줘야 한다
        document.getElementById('cmtModBtn').setAttribute('data-cno', li.dataset.cno);
    }else if(e.target.id == 'cmtModBtn'){
        let cmtDataMod = {
            cno : e.target.dataset.cno,
            content : document.getElementById('cmtTextMod').value
        };
        editCommentToServer(cmtDataMod).then(result =>{
            if(parseInt(result)){
                document.querySelector('.btn-close').click();
            }
            printCommentList(pnoVal);
        });
        
    }
});

