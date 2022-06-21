package ezenweb.controller;

import ezenweb.dto.BoardDto;
import ezenweb.service.BoardService;
import lombok.Getter;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
// @RestController 객체 반환
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private HttpServletRequest request; // 1. 세션 호출을 위한 request 객체 생성

    @Autowired
    private BoardService boardService; // 2. 서비스 호출을 위한 boardService객체 생성

    ////////////////////////////////////// 1. view 열기[템플릿 연결] 매핑 ////////////////////////////////////////////
    // 1. 게시판 페이지 열기
    @GetMapping("/list")
    public String list(){return "board/list";}

    // 2. 게시판 개별 조회 페이지
    int selectbno = 0; // view 메소드와 getboard 메소드 에서 사용될 변수

    @GetMapping("/view/{bno}")
    public String view(@PathVariable("bno") int bno){ // {}안세서 선언된 변수는 밖에 사용불가
        // 내가 보고있는 게시물의 변호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        selectbno = bno;
        return "board/view";
    }


/*    @GetMapping("/view/{bno}") // url 경로에 변수 = {변수명}
    @ResponseBody
    public void view(@PathVariable("bno") int bno , HttpServletResponse response*//*, Model model*//*){ // (@PathVariable("변수명")
            // Model 인터페이스 : Controller -> html : 데이터 전송
        *//*model.addAttribute("data" , boardService.getboard(bno));*//*

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(bno));
            System.out.println("선택한 게시물 번호 : " + bno);
        }catch(Exception e){System.out.println(e);}
        *//*return "board/view"; // 템플릿을 ajax에게 통신*//*
    }*/

    // 3. 게시물 수정 페이지
    @GetMapping("/update")
    public String update(){return "board/update";}
    @GetMapping("/save")
    public String save(){return "board/save";}
    ////////////////////////////////////// 2. service 처리 매핑 ////////////////////////////////////////////
    // 1. C : 게시물 저장 메소드
    @PostMapping("/save")
    @ResponseBody // 템플릿이 아닌 객체 반환
    public boolean save(BoardDto boardDto){
        System.out.println(boardDto.toString());
        return boardService.save(boardDto);
    }
    // 2. R : 모든 게시물 출력 메소드
    @GetMapping("/getboardlist")
    public void getboardlist(HttpServletResponse response , @RequestParam("cno") int cno){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboardlist(cno));
        }catch(Exception e){System.out.println(e);}
    }
    // 2. R2 개별 조회 메소드
    @GetMapping("/getboard")
    public void getboard(HttpServletResponse response){

        int bno = (Integer) request.getSession().getAttribute("bno");
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(bno));
        }catch(Exception e){}
    }

    // 3. U : 수정 메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update(BoardDto boardDto){
        int bno = (Integer) request.getSession().getAttribute("bno");
        boardDto.setBno(bno);
        return boardService.update(boardDto);
    }
    // 4. D : 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("bno") int bno){
        return boardService.delete(bno);
    }

    // 5. 카테고리 출력 메소드
    @GetMapping("/getcategorylist")
    public void getcategotylist(HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategotylist());
            System.out.println(boardService.getcategotylist());
        }catch (Exception e){System.out.println(e);}
    }


}



















