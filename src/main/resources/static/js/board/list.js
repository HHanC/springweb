board_list();

// 2. R 출력 처리 메소드
function board_list(){
        $.ajax({
            url : "/board/getboardlist" ,
            method : "get" ,
            success : function(boardlist){
            console.log(boardlist);
                let html = $("#boardtable").html();
                for(let i=0; i < boardlist.length; i++){
                    html +=
                         '<tr>'+
                            '<td>'+boardlist[i].bno+'</td>' +
                            '<td><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].btitle+'<a></td>' +
                            /*'<td><span onclick="view('+boardlist[i].bno+')">'+boardlist[i].btitle+'</span></td>' +*/
                            '<td>'+boardlist[i].bdate+'</td>' +
                            '<td>'+boardlist[i].bview+'</td>' +
                            '<td>'+boardlist[i].blike+'</td>' +
                        '</tr>';
                }
                $("#boardtable").html(html);
            }
        })
}



/*
function view(bno){
    alert(bno);
    $.ajax({
        url : "/board/view/"+bno,
        success : function(re){
        */
/*let html = "";
            html +=
                '<p>게시물 번호 : '+re.bno+'</p><br>' +
                '<p>제목 : '+re.btitle+'</p><br>' +
                '<p>게시글 : '+re.bcontent+'</p><br>' +
                '<p>뷰 : '+re.bview+'</p><br>' +
                '<p>좋아요 : '+re.blike+'</p><br>' +
                '<p>시간 : '+re.bindate+'</p><br>' +
                '<p>시간 : '+re.bmodate+'</p>' ;
            $("#boardlist").html(html);*//*

        }

    })
}*/
