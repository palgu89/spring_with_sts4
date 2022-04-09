async function removeFileAtServer(uuid){
    try {
        const url = '/board/file/'+uuid;
        const config = {
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();   // 지우는거니까 그냥 text로 받자
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('file-x')){
        removeFileAtServer(e.target.dataset.uuid).then(result => {
            alert('파일 삭제' + (parseInt(result) > 0 ? '완료' : '실패'));
            if(parseInt(result)){
                e.target.closest('li').remove();
            }
        });
    }
});