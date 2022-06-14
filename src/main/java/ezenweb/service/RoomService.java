package ezenweb.service;

import ezenweb.domain.RoomEntity;
import ezenweb.domain.RoomRepository;
import ezenweb.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto) {
        // dto -> entitiy
        RoomEntity roomEntity = RoomEntity.builder()
                .roomname(roomDto.getRname())
                .x(roomDto.getX())
                .y(roomDto.getY())
                .transactionmethod(roomDto.getTransactionmethod())
                .price(roomDto.getPrice())
                .area(roomDto.getArea())
                .administrativeexpenses(roomDto.getAdministrativeexpenses())
                .rescue(roomDto.getRescue())
                .completiondate(roomDto.getCompletiondate())
                .parking(roomDto.getParking())
                .elevator(roomDto.getElevator())
                .movein(roomDto.getMovein())
                .currentfloor(roomDto.getCurrentfloor())
                .building(roomDto.getBuilding())
                .buildingtype(roomDto.getBuildingtype())
                .address(roomDto.getAddress())
                .detaileddescription(roomDto.getDetaileddescription())
                .build();
        String uuidfile = null;

        // 첨부파일
        if(roomDto.getRimg().size() != 0){ // 첨부파일 1개 이상이면

            // 1. 반복문을 이용한 모든 첨부파일 호출
            for(MultipartFile file : roomDto.getRimg()){

                // 파일명이 동일하면 식별 문제 발생
                    //1. UUID 난수 생성
                UUID uuid = UUID.randomUUID();
                    // 2. UUID + 파일명 [// getOriginalFilename() : 실제 첨부파일 이름]
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_","-");
                    // UUID 와 파일명 구분 _ 사용 [만약에 파일명에 _ 존재하면 문재발생 -> 파일명 _ -> - 변경]

                // 2. 경로 설정
                // \ : 제어문자
                String dir = "C:\\Users\\504\\Desktop\\springweb\\src\\main\\resources\\static\\upload\\"; // 저장 경로
                String filepath = dir + uuidfile;
                // .getOriginalFilename() : 실제 첨부파일 이름

                try{
                // 3. 첨부파일 읽기모드 처리
                file.transferTo(new File(filepath));
                // 4. 엔티티에 파일명 저장
                roomEntity.setRimg(uuidfile);
                // 파일명.transferTo(새로운 경로)
                }catch(Exception e){System.out.println("파일저장 실패 : " + e);}
            }

        }

        // 저장
        roomRepository.save(roomEntity);
        return true;
    }

    // 2. 룸 호출
    // 반환타입   {   키 : [ { }, {} , {} ,{}  ]  }
    //          JSON vs 컬렉션프레임워크
    //          JSONObject == MAP
    //          JSONArray  == List
    //  { 키 : 값 } = entry      --> Map 컬렉션
    //  [  요소1 , 요소2 , 요소3 ] --> List 컬렉션
    //  List< Map<String,String> >
    // { "positions" : [  ]  }
    // Map<String , List< Map<String,String> > >
    // 1.  JSON
    /*
    public JSONObject room_list() {
        JSONArray jsonArray = new JSONArray();
        // 1. 모든 엔티티 호출
        List<RoomEntity> roomEntityList = roomRepository.findAll(); // 엔티티에 생성자 없으면 오류발생
        // 2. 모든 엔티티 -> json 변환
        for (RoomEntity roomEntity : roomEntityList) {
            JSONObject object = new JSONObject();
            object.put("rname", roomEntity.getRoomname());
            object.put("lng", roomEntity.getX());
            object.put("lat", roomEntity.getY());
            jsonArray.put(object);
        }
        JSONObject object = new JSONObject();
        object.put("positions" , jsonArray );
        // 3. 반환
        return object;
    }
     */

    // 2.
    public // 접근제한자
    Map< String , List<  Map<String , String >  > >     // 반환타입
    room_list   // 메소드명
    ( Map<String,String> Location ) // 인수
    {

        List<  Map<String , String >  > Maplist = new ArrayList<>();

        // 현재 보고 있는 지도 범위
        double qa = Double.parseDouble(   Location.get("qa")    );
        double pa = Double.parseDouble(   Location.get("pa")    );
        double ha = Double.parseDouble(   Location.get("ha")    );
        double oa = Double.parseDouble(   Location.get("oa")    );

        // 1.모든 엔티티 꺼내오기 ~~~~
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        // 2. 엔티티 -> map -> 리스트 add
        for( RoomEntity entity : roomEntityList  ){ // 리스트에서 엔티티 하나씩 꺼내오기

            // [ 조건 ]   Location 범위내 좌표만 저장 하기
            if(  Double.parseDouble(  entity.getY() ) > qa
                    && Double.parseDouble(  entity.getY() ) < pa
                    && Double.parseDouble(  entity.getX() )   > ha
                    && Double.parseDouble(  entity.getX() )   < oa
            ) {
                // 3. map 객체 생성
                Map<String, String> map = new HashMap<>();
                map.put("rname", entity.getRoomname());
                map.put("lng", entity.getX());
                map.put("lat", entity.getY());
                map.put("rno", entity.getRno()+"");
                map.put("transactionmethod", entity.getTransactionmethod());
                map.put("price", entity.getPrice());
                map.put("area", entity.getArea());
                map.put("administrativeexpenses", entity.getAdministrativeexpenses());
                map.put("rescue", entity.getRescue());
                map.put("completiondate", entity.getCompletiondate());
                map.put("parking", entity.getParking());
                map.put("elevator", entity.getElevator());
                map.put("movein", entity.getMovein());
                map.put("currentfloor", entity.getCurrentfloor());
                map.put("building", entity.getBuilding());
                map.put("buildingtype", entity.getBuildingtype());
                map.put("address", entity.getAddress());
                map.put("detaileddescription", entity.getDetaileddescription());
                // 4. 리스트 넣기
                Maplist.add(map);
            }
        }
        Map< String , List<  Map<String , String >  > > object = new HashMap<>();
        object.put( "positions" , Maplist );

        return  object;

    }
}