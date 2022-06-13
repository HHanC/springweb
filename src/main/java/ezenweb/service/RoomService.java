package ezenweb.service;

import ezenweb.domain.RoomEntity;
import ezenweb.domain.RoomRepository;
import ezenweb.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    // 1. 룸 저장

    public boolean room_save(RoomDto roomDto){
        // dto -> entity
        RoomEntity roomEntity = RoomEntity.builder()
                .roomname(roomDto.getRoomname())
                .x(roomDto.getX())
                .y(roomDto.getY())
                .build();
        roomRepository.save(roomEntity);
        return true;
    }

    // 2. 룸 호출
    public JSONArray room_list(){
        JSONArray jsonArray = new JSONArray();
        // 1. 모든 엔티티 호출
        List<RoomEntity> roomEntityList = roomRepository.findAll();

        // 2. 모든 엔티티 -> json 변환
        for(RoomEntity roomEntity : roomEntityList){
            JSONObject object = new JSONObject();
            object.put("name",roomEntity.getRoomname());
            object.put("lng",roomEntity.getX());
            object.put("lat",roomEntity.getY());

            jsonArray.put(object);
        }
        return jsonArray;
    }

}
