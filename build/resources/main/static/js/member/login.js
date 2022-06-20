function login(){
    $.ajax({
        url :"/member/login",
        method : "POST" ,
        data : {"mid" : $("#mid").val() , "mpassword" : $("#mpassword").val()} ,
        success : function(re){
            alert(re);
            if(re == true){
                location.href ="/"; // 메인 페이지로 매핑
            }else{
                alert("로그인 실패");
            }

        }

    })

}