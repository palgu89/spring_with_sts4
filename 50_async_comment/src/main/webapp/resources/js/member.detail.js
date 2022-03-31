document.addEventListener("click",(e)=>{
    if(e.target.id == "modBtn") {
        e.preventDefault();
        let modBtn = document.querySelector("#modBtn");
        modBtn.innerText = "Submit";
        modBtn.setAttribute("type", "submit");

        document.getElementById("pwd").readOnly = false;
        document.getElementById("nickName").readOnly = false;

        modBtn.id = "sbmBtn";   // subBtn 지워도 되고 안 지워도 됨
    }
    
});