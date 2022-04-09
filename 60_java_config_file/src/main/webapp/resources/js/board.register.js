// 파일첨부 모양 숨겨서 버튼 클릭시 파일첨부 클릭하게끔 함
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

const regExp = new RegExp("\.(exe|sh|bat|js|msi|dll)$");    // 정규식. .으로 끝나고 뒤의 확장자가 괄호 안의 확장자다.
const regExpimg = new RegExp("\.(jpeg|jpg|png|gif)$");  // 이미지 파일만
const maxSize = 1024 * 1024;
function fileSizeValidation(fileName, fileSize){
    if(!regExpimg.test(fileName)){
        return 0;
    }else if(regExp.test(fileName)){  // 정규식의 파일이름을 테스트해서 위에 포함 되면 0
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else {
        return 1;
    }
}

document.addEventListener('change', (e)=>{
    if(e.target.id == 'files') {
        document.getElementById('regBtn').disabled = false; // 일단 게시 가능하게 게시버튼을 열어 놓는다(다시 파일업로드시 제대로 되면 버튼이 열린다)
        // input file element에 저장된 file 정보를 가져오는 property 리턴은 객체형
        const fileObjects = document.getElementById('files').files; // .files 하면 실제 파일객체를 가져 옴
        console.log(fileObjects);
        // fileObjects 안에 name, size 등등 다 들어가 있음. 아래 ul 부분 확인

        let div = document.getElementById('fileZone');
        div.innerHTML = "";
        let ul = '<ul class="list-group list-group-flush">';
        let isOk = 1;
        for (let file of fileObjects) {    // length가 있으면 for of 가능, 이름이 있으면 in
            let validResult = fileSizeValidation(file.name, file.size);
            isOk *= validResult;  // 하나라도 실패하면 isOk = 0
            ul += `<li class="list-group-item d-flex justify-content-between align-items-start">`;
            ul += `<div class="ms-2 me-auto">`;
            ul += `${validResult ? '<div class="fw-bold">업로드가능' : '<div class="fw-bold text-danger">업로드불가'}</div>`;
            ul += `${file.name}</div>`;
            ul += `<span class="badge bg-${validResult ? 'success' : 'danger'} rounded-pill">${file.size} Bytes</span></li>`;
        }
        ul += '</ul>';
        div.innerHTML = ul;
        console.log("isOk :", isOk);
        if(isOk == 0){
            document.getElementById('regBtn').disabled = true;  // 못올리는걸 올리면 게시버튼을 닫는다
        }
    }
});