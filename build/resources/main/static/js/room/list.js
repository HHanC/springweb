// 마커 클러스터 지도 사용

/*    // 0. 현재 내 위치의 위도 경도 구하기
    navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude, // 위도
                 lng = position.coords.longitude; // 경도*/

    // 1. Map 변수
    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng( lat , lng ), // 지도의 중심좌표 // 현재 접속된 디바이스 좌표
        level : 5 // 지도의 확대 레벨
    });

    // 2. 클러스터[ 마커 집합 ]  변수
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 6, // 클러스터 할 최소 지도 레벨
        disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
    });

    // 데이터를 가져오기 위해 jQuery를 사용합니다
    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
    //   $.get("통신할 URL ", function( 반환 인수 ) {

//    // 3. 여러개 MAP -> 클러스터 저장 [  모든 지도 좌표 가져오기 ]
//    $.get("/room/roomlist", function(data) {
//        console.log(data); // 통신 확인
//        // 데이터에서 좌표 값을 가지고 마커를 표시합니다
//        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
//        var markers = $(data.positions).map(function(i, position) {
//            return new kakao.maps.Marker({
//                position : new kakao.maps.LatLng( position.lat, position.lng)
//            });
//        });
//
//        // 클러스터러에 마커들을 추가합니다
//        clusterer.addMarkers(markers);
//    });

    // 6. 마커 이미지 변경
                // 마커 이미지의 주소
                var markerImageUrl = 'http://192.168.17.91:8081/img/집.png',
                    markerImageSize = new kakao.maps.Size(40, 40), // 마커 이미지의 크기
                    markerImageOptions = {
                        offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
                    };

                // 마커 이미지를 생성한다
                var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);

    // 지도 시점 변화 완료 이벤트를
    // 4. 등록한다 [  idle(드래그 완료시 이벤트발생 ) vs bounds_changed(드래그 중에 이벤트발생 )  ]
    kakao.maps.event.addListener(map, 'idle', function () {
            // 클러스터 초기화
            clusterer.clear();
            // 사이드바에 넣을 html 변수 선언
            let html = "";
            $.ajax({
                url: '/room/roomlist' ,
                data : JSON.stringify(  map.getBounds() ) , // 현재 보고 있는 지도 범위 [ 동서남북 좌표 ]
                method : 'POST' ,
                contentType : 'application/json' ,
                success : function( data ){
                        console.log(data); // 통신 확인
                        console.log(data.positions); //
                        // 만약에 데이터가 없으면 메시지를 사이드바에 띄우기
                        if(data.positions.length == 0){
                            html +=
                                 '<div> <img src="/img/엑스.png"; width="100%";> 방 없음 </div>';
                        }


                        // 마커목록 생성
                          var markers = $(data.positions).map(function(i, position) {

                            // 마커 하나 생성  start
                            var marker =  new kakao.maps.Marker({
                                position : new kakao.maps.LatLng( position.rlat, position.rlon) ,
                                image : markerImage // 마커의 이미지
                            });

                                 // 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
                                    kakao.maps.event.addListener(marker, 'click', function() {

                                    getroom(position.rno);

                                        // 모달 띄우기
                                        $("#modalbtn").click();
                                    });

                                    // 사이드바에 추가할 html 구성
                                    html +=
                                    '<div class="row" onclick="getroom('+position.rno+')">'+
                                       '<div class="col-md-6">'+
                                           ' <img onclick="getroom('+position.rno+')" data-bs-toggle="modal" data-bs-target="#exampleModal" src="/upload/'+position.rimg+'" width="100%">'+
                                        '</div>'+
                                        '<div class="col-md-6">'+
                                            '<div> 방번호 : <span>'+position.rno+'</span> </div>'+
                                            '<div> 방이름 : <span>'+position.rtitle+'</span> </div>'+
/*                                            '<div> 거래방식 : <span>'+position.transactionmethod+'</span> </div>'+
                                            '<div> 가격 : <span>'+position.price+'</span> </div>'+
                                            '<div> 면적 : <span>'+position.area+'</span> </div>'+
                                            '<div> 관리비 : <span>'+position.administrativeexpenses+'</span> </div>'+
                                            '<div> 구조 : <span>'+position.rescue+'</span> </div>'+
                                            '<div> 준공날짜 : <span>'+position.completiondate+'</span> </div>'+
                                            '<div> 주차여부 : <span>'+position.parking+'</span> </div>'+
                                            '<div> 엘리베이터여부 : <span>'+position.elevator+'</span> </div>'+
                                            '<div> 입주가능일 : <span>'+position.movein+'</span> </div>'+
                                            '<div> 현재층 : <span>'+position.currentfloor+'</span> </div>'+
                                            '<div> 건물전체층 : <span>'+position.building+'</span> </div>'+
                                            '<div> 건물종류 : <span>'+position.buildingtype+'</span> </div>'+
                                            '<div> 주소 : <span>'+position.address+'</span> </div>'+
                                            '<div> 상세설명 : <span>'+position.detaileddescription+'</span> </div>'+*/
                                        '</div>'+
                                    '</div>';

                               return marker;

                             //  마커 하나 생성 end

                        }); // markers end

                         // 클러스터에 마커 추가
                        clusterer.addMarkers(markers);
                        // 해당 html을 해당 id값에 추가
                        $("#sidebar").html(html);
                } // sueess end
            }); // ajax end
    }); // 이벤트 end


    // 5.
    // 마커 클러스터러에 클릭이벤트를 등록합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
    // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
    kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
        // 현재 지도 레벨에서 1레벨 확대한 레벨
        var level = map.getLevel()-1;
        // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
        map.setLevel(level, {anchor: cluster.getCenter()});
    });

}); // 현재 내 위치의 위도경도 구하기 end

function roomlist(){



}


//모달의 방 정보 출력
function getroom(rno){
      // 해당 모달에 데이터 넣기
       $.ajax({
            url : "/room/getroom",
            method : "GET",
            data : {"rno" : rno} ,
            success : function(room){
            let imgtag = "";
                console.log("Room");
                console.log(room.rimglist);
                for(let i=0; i<room.rimglist.length; i++){
                    if(i==0){ // 첫번째 이미지만 active 속성 추가
                        imgtag +=
                            '<div class="carousel-item active">'+
                                '<img onclick="" src="/upload/'+room.rimglist[i]+'" class="d-block w-100" alt="...">'+
                            '</div>';
                    }else{
                        imgtag +=
                            '<div class="carousel-item">'+
                                '<img onclick="" src="/upload/'+room.rimglist[i]+'" class="d-block w-100" alt="...">'+
                            '</div>';
                    }
                }
                $("#modalimglist").html(imgtag)
            }
        })

}





