///////////////////////////////// 페이지가 처음 열렸을 때 게시물 출력 메소드 호출
board_list(1, 0, "", "");  // cno, page, key, keyword
category_list();
/////////////////////////////////
// 전역변수 !!!!!
let current_cno = 1; // 카테고리 선택 변수 [없을경우 1 = 자유게시판]
let current_page = 0;
let current_key = ""; // 현재 검색된 키 변수 [없을경우 공백]
let current_keyword = ""; // 현재 검색된 키워드 변수 [없을경우 공백]



// 2. R 출력 처리 메소드 [cno = 카테고리 번호 , key = 검색 키 , keyword = 검색내용]
function board_list( cno, page, key , keyword ){
    // 현재 출력된 정보 변경

    this.current_cno = cno;
    this.current_page = page;
    if(key != undefined) {this.current_key=key;}
    if(keyword != undefined) {this.current_keyword=keyword;}




    $.ajax({
        url : "/board/getboardlist" ,
        data : {"cno" : this.current_cno , "key" : this.current_key , "keyword" : this.current_keyword , "page" : this.current_page} ,
        method : "get" ,
        success : function(boardlist){


        console.log(boardlist);

        let html =  '<tr> <th width="10%">번호</th><th width="50%">제목</th><th width="10%">작성일</th><th width="10%">조회수</th><th width="10%">좋아요수</th><th width="10%">작성자</th> </tr>';

            if(boardlist.data.length == 0){ // 검색 결과가 존재하지 않으면
                html +=
                '<tr>' +
                    '<td colspan="5">검색 결과가 존재하지 않습니다.</td>' +
                '<tr>' ;
            }else{
                for(let i=0; i < boardlist.data.length; i++){
                    html +=
                         '<tr>'+
                            '<td>'+boardlist.data[i].bno+'</td>' +
                            '<td><a href="/board/view/'+boardlist.data[i].bno+'">'+boardlist.data[i].btitle+'<a></td>' +
                            /*'<td><span onclick="view('+boardlist[i].bno+')">'+boardlist[i].btitle+'</span></td>' +*/
                            '<td>'+boardlist.data[i].bindate+'</td>' +
                            '<td>'+boardlist.data[i].bview+'</td>' +
                            '<td>'+boardlist.data[i].blike+'</td>' +
                            '<td>'+boardlist.data[i].mid+'</td>' +
                        '</tr>';
                }
            }
///////////////////////////////////////////////////////////////////////////////////페이징 버튼 생성 코드/////////////////////////////////////////////////////////////////////////////////////
            let pagehtml = "";
            /////////////////////////////////////////////////이전 버튼/////////////////////////////////////////////
            if(page == 0){ // 현재 페이지가 첫 페이지면
                pagehtml +=
                '<li class="page-item">'+
                    '<button class="page-link" onclick="board_list('+cno+','+(page)+')">이전</button> '+ // 검색 없음
                '</li>';
            }else{ // 현재 페이지가 첫 페이지가 아니면
                pagehtml +=
                '<li class="page-item">'+
                    '<button class="page-link" onclick="board_list('+cno+','+(page-1)+')">이전</button> '+ // 검색 없음
                '</li>';
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            for(let i = boardlist.startbtn; i <= boardlist.endbtn; i++){
                pagehtml +=
                '<li class="page-item">'+
                    '<button class="page-link" onclick="board_list('+cno+','+(i-1)+')">'+i+'</button> '+ // 검색 없음
                '</li>';
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////다음 버튼//////////////////////////////////////////////////////////////
            if(page == boardlist.totalpages-1){ // 현재 페이지가 마지막 페이지이면
                pagehtml +=
                '<li class="page-item">'+
                    '<button class="page-link" onclick="board_list('+cno+','+(page)+')">다음</button> '+ // 검색 없음
                '</li>';
            }else{
                pagehtml +=
                '<li class="page-item">'+
                    '<button class="page-link" onclick="board_list('+cno+','+(page+1)+')">다음</button> '+ // 검색 없음
                '</li>';
            }

            /////////////////////////////////////////////////이전 버튼//////////////////////////////////////////////////////////////



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            $("#boardtable").html(html); // 테이블에 html넣기
            $("#pagebtnbox").html(pagehtml); // 페이징버튼 html 넣기
        }
    });
}

// 카테고리 출력
function category_list(){
    $.ajax({
        url : "/board/getcategorylist",
        success : function(categorylist){
            let html = "";
            for(let i=0; i<categorylist.length; i++){
                html +=
                '<button onclick="categorybtn('+categorylist[i].cno+')">'+categorylist[i].cname+'</button>';
            }
            $("#categorybox").html(html);
        }
    });
}

// 카테고리 버튼을 눌렀을때
function categorybtn(cno){ // (list.js 에 category_list()안에있는 categorybtn버튼)

    this.current_cno = cno; // 현재 카테고리 번호 변경

    board_list(this.current_cno , 0, "",""); // 검색이 없을경우 공백 전달
}
// 검색 버튼을 눌렀을때 (list.html에 있는 seach())
function seach(){

    let key = $("#key").val();
    let keyword = $("#keyword").val();
    //  키와 키워드 입력받음
    board_list(this.current_cno, 0, key , keyword);
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
