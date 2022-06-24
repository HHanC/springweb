package ezenweb.controller;

import ezenweb.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mvc; // MockMvc클래스 : 스프링에서 mvc테스트를 위한 메소드 제공
            // 1. perform(http 요청메소드("url"))

    @Test
    public void list() throws Exception {
        mvc.perform(get("/board/list")).andDo(print());
    }

    @Test
    void view() throws Exception {
        mvc.perform(get("/board/view/12")).andDo(print());
    }

    @Test
    void update() throws Exception {
        mvc.perform(get("/board/update")).andDo(print());
    }

    @Test
    void save() throws Exception {
        mvc.perform(get("/board/save")).andDo(print());
    }

/*    @Test
    void testSave() throws Exception{

        // 변수 전달 테스트
            // http 요청 메소드(url).param("필드명" , 데이터)
        // 세션 전달 테스트
            // MockHttpSession클래스
            // http 요청메소드("url").session(세션객체명);
        LoginDto loginDto = LoginDto.builder()
                .mno(1)
                .mid("qweqwe")
                .mname("qweqwe")
                .build();
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("login" , loginDto);
        mvc.perform(post("/board/save")
                        .param("btitle", "테스트제목")
                        .param("bcontent", "테스트내용")
                        .param("category", "테스트자유게시판")
                        .session(mockHttpSession))
                .andDo(print());

    }*/

    // 모든 게시물 호출 테스트
    @Test
    void getboardlist() throws Exception{

        mvc.perform(get("/board/getboardlist")
                .param("cno","1")
                .param("key","")
                .param("keyword","")
                .param("page","0")
        ).andDo(print());

    }

    // 게시물 검색 테스트
    @Test
    void getboard() throws Exception {
        mvc.perform(get("/board/getboardlist")
                .param("cno" , "1")
                .param("key" , "btitle")
                .param("keyword" , "qwe")
                .param("page" , "0")
        ).andDo(print());
    }

    // 게시물 개별 조회 테스트
    @Test
    void testgetboard() throws Exception {

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("bno" , 1);
        mvc.perform(get("/board/getboard")
                .session(mockHttpSession))
                .andDo(print());
    }

    // 특정 게시물 수정 테스트
    @Test
    void testUpdate() throws Exception{

        // 1번 게시물 수정 테스트
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("bno" , 1);

        mvc.perform(put("/board/update")
                .param("btitle" , "수정테스트제목")
                .param("bcontent" , "수정테스트내용")
                .session(mockHttpSession))
                .andDo(print());

    }

    @Test
    void testdelete() throws Exception{
        // 1번 게시물 삭제
        mvc.perform(delete("/board/delete")
                        .param("bno" , "1"))
                        .andDo(print());

    }

    @Test
    void getcategotylist() throws Exception {
        mvc.perform(get("/board/getcategorylist"))
                .andDo(print());
    }
}