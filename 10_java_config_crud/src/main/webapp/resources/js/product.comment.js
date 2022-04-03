async function postCommentToServer(cmtData){
    try {
        const url = '/comment/post';
        const config = {
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
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
    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글 내용을 입력해 주세요!');
        cmtText.focus();
        return false;
    }else{
        let cmtData = {
            pno: pnoVal,
            writer: document.getElementById('cmtWriter').innerText,
            content: cmtText.value
        };
        postCommentToServer(cmtData).then(result=>{
            if(parseInt(result)){
                alert('댓글 등록 성공~');
            }
            printCommentList(cmtData.pno);
        });
    }
});