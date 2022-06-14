package ezenweb.controller;

import ezenweb.dto.RoomDto;
import ezenweb.service.RoomService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
       System.out.println(roomDto.getRimg().get(0));
        roomService.room_save(roomDto);
    return true;
    }

    // 3. 방 목록 페이지 이동
    @GetMapping("/list")
    public String list(){
        return "room/list";
    }
    // json 사용시
 /*   @GetMapping("/roomlist")
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
    }*/

    @PostMapping("/roomlist")
    @ResponseBody // 객체 반환
    public Map<String, List<Map<String,String>>> roomlist(@RequestBody Map<String,String> Location) {

        return roomService.room_list(Location);

    }

}


/*
    -----------  @RequestMapping( "경로" ) --------------------------
    @GetMapping          : FIND , GET   [ @RequestMapping( "경로" , method=RequestMethod.GET )  ]
    @PostMapping         :  SAVE            [ @RequestMapping( "경로" , method=RequestMethod.POST ) ]
    @PutMapping          : UPDATE         [ @RequestMapping( "경로" , method=RequestMethod.PUT ) ]
    @ DeleteMapping       : DELETE      [ @RequestMapping( "경로" , method=RequestMethod.DELETE ) ]
 */

/*
    view -----> controller  변수 요청 방식
        // 1.  HttpServletRequest request 이용한 방식
            String roomname = request.getParameter("roomname");
            String x = request.getParameter("x");
            String y = request.getParameter("y");
        // 2-1.  @RequestParam("요청변수) 자료형 변수명
            @RequestParam("roomname") String roomname ,
            @RequestParam("x") String x ,
            @RequestParam("y") String y

        // 2-2. JS ---JSON---> Controller
            @ResponseBody

        // 3. Mapping 사용시  DTO 로 자동 주입 된다
            // 조건1. : Mapping
            // 조건2 : 요청변수명 과 DTO 필드명 동일하다
 */
/*
*
*   controller -> view(js)
*       // 1.해당 클래스가 @ RestController 이면 메소드 return 객체
*               vs @Controller 이면 메소드 return 값이 템플릿(html)
*       // 2. HttpServletResponse response
*           response.getWriter().print()
*
*       // 3. @ResponseBody 메소드 return 객체
*
* */












