board_get();
// 1. 특정 게시물 호출
function board_get(){

    $.ajax({
        url : "/board/getboard",
        success : function(board){
        console.log(board);
            let html =
                '<div>게시물 번호 : '+board.bno+' </div>' +
                '<div>게시물 제목 : '+board.btitle+' </div>' +
                '<div>게시물 내용 : '+board.bcontent+' </div>' +
                '<div>게시물 작성일 : '+board.bindate+' </div>' +
                '<div>게시물 수정일 : '+board.bmodate+'  </div>' +
                '<div>게시물 조회수 : '+board.bview+' </div>' +
                '<div>게시물 좋아요수 : '+board.blike+' </div>' +
                '<button onclick="board_delete('+board.bno+')">삭제</button>';
            $("#boarddiv").html(html);
        }
    })
}

// 4. D 삭제 처리 메소드
function board_delete(bno){
        $.ajax({
            url : "/board/delete" ,
            data : {"bno" : bno},
            method : "delete" ,
            success : function(re){
                alert(re);
            }
        })
}

/////////////////////////////////////////////////
/*
    url 변수 이동
        1. <a href = "URL/데이터"> </a>
            @GetMapping("/view/{변수명}")
                @PathVariable("변수명")

        2. ajax : url : "/board/view/"+bno



*/





















