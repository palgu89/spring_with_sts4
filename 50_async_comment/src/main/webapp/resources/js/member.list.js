async function updateGradeToServer(emailVal, gradeVal) {
    try {
        const url = "/member/grade"; 
        const config = {
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8' 
            },
            body: JSON.stringify({email: emailVal, grade: gradeVal}) 
        };
        const resp = await fetch(url, config); 
        const result = await resp.text();
        return result;
    } catch (error) {    
        console.log(error);
    } 
}

document.addEventListener('click',(e)=>{    // queryselector는 한개만 가능해서 맨위 admin만 수정되므로 지금처럼 바꿈
    if(e.target.classList.contains("grade")){
        let tr = e.target.closest('tr'); // list.jsp에서 tr을 가져 옴
        let email = tr.querySelector('th:first-child').innerText;
        let grade = tr.querySelector('input[type=number]').value;
        console.log(email, " : ", grade);

        updateGradeToServer(email, grade).then(result => {
                alert(`회원 등급 수정 ${parseInt(result) ? "성공~" : "실패!"}`);   
        });
    }   
});