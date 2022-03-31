// async는 호출하는 곳보다 위에 만들자
async function emailDupleCheckFromServer(emailVal){
    // async 안에는 에러를 빨리 찾기 위해 try catch를 반드시 써주자
    try {
        const url = "/member/dupleCheck";   // 주소값 세팅
        const config = {    // async는 xml 데이터로 변환해서 보내는 http통신에 요청하기 때문에 js로 내것도 가져가줘 하는 거임 -> ajax 통신 방식
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'   // 타입 설명
            },
            body: JSON.stringify({email: emailVal}) // 실제 body에 데이터를 email이란 이름으로 emailVal 값을 보낼거야
        };
        const resp = await fetch(url, config);  // 얘가 속도가 아래보다 훨씬 느리기 때문에 얘 하기 전에 아래걸 실행시킬 것이다. -> await로 다 실행한뒤 다음 줄 실행
        const result = await resp.text();   // 있냐 없냐만 판단(a태그, form태그 x)하기 때문에 실행속도가 빠르다 -> text로만 받을 것이다
        // text가 아니라 json으로 받을 경우 속도가 느리기 때문에 return에도 await를 거는것이 맞다
        return result;  // 1(중복) || 0(중복 x) 을 리턴한다
    } catch (error) {
        console.log(error);
    }
}

document.getElementById("dupleCheck").addEventListener('click',(e)=>{
    e.preventDefault();
    let emailInput = document.getElementById("email");
    let emailVal = emailInput.value;
    if(emailVal == '') {
        alert('가입할 이메일을 반드시 입력하세요!');
        emailInput.focus();
        return;
    }else {
        emailDupleCheckFromServer(emailVal).then(result => {
            if(parseInt(result)) {
                alert("이미 사용중인 이메일 주소입니다!");
                emailInput.value = "";
                emailInput.focus();
            }else{
                alert("사용 가능한 이메일 주소입니다~");
                document.getElementById("nickName").focus();
            }
        });
    }
});