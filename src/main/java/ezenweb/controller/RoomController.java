package ezenweb.controller;

import ezenweb.dto.RoomDto;
import ezenweb.service.RoomService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller // 해당 클래스가 템플릿 영역으로사용
@RequestMapping("/room") // 해당 클래스 요청매핑(room)
public class RoomController {

    @Autowired
    RoomService  roomService;

    @GetMapping("/write") // 1. 등록페이지 이동
    public String write(){



        return "room/write";
    }
    @PostMapping("/write") // 2. 등록 처리
    @ResponseBody // 템플릿이 아닌 객체 반환시 사용되는 어노테이션
    public boolean write_save(RoomDto roomDto){
                            // 요청 변수중 dto필드와 변수명이 동일할 경우 자동 주입
        roomService.room_save(roomDto);
        System.out.println("입력값 : " + roomDto.toString());
        //ㅁㄴㅇㅁㄴㅇㅁㅇㄴ
    return  true;
    }

    // 3. 방 목록 페이지 이동
    @GetMapping("/list")
    public String list(){
        return "room/list";
    }

    @GetMapping("/roomlist")
    public void roomlist(HttpServletResponse response){

        JSONObject object = new JSONObject();
        JSONArray jsonArray = roomService.room_list();

        object.put("positions", jsonArray);

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(object);
        }catch (Exception e){}
        return ;
    }

}


/*
*   get : find, get
*   post : save
*   put : update
*   delete : delete
*
*
*
*   mapping 사용시 dto 로 자동 주입 된다
*       // 조건 1 : mapping
*       // 조건 2 :
*
* */













