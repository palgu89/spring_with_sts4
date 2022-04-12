document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('files').click();
});

const regExp = new RegExp("\.(exe|sh|bat|js|msi|dll)$");
const regExpimg = new RegExp("\.(jpeg|jpg|png|gif)$");
const maxSize = 1024 * 1024;
function fileSizeValidation(fileName, fileSize){
    if(!regExpimg.test(fileName)){
        return 0;
    }else if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else {
        return 1;
    }
}

document.addEventListener('change', (e)=>{
    if(e.target.id == 'files') {
        document.getElementById('regBtn').disabled = false;
        const fileObjects = document.getElementById('files').files;
        console.log(fileObjects);

        let div = document.getElementById('fileZone');
        div.innerHTML = "";
        let ul = '<ul class="list-group list-group-flush">';
        let isOk = 1;
        for (let file of fileObjects) {
            let validResult = fileSizeValidation(file.name, file.size);
            isOk *= validResult;
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
            document.getElementById('regBtn').disabled = true;
        }
    }
});