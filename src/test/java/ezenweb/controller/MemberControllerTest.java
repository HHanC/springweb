package ezenweb.controller;

import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
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
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void login() throws Exception {
        mvc.perform(get("/member/login")).andDo(print());
    }

/*    @Test
    void testLogin() throws Exception{

        mvc.perform(post("/member/login")
                        .param("mid" , "qweqwe")
                        .param("mpassword" , "qweqwe"))
                .andDo(print());
    }*/

    @Test
    void logout() throws Exception {

        mvc.perform(get("/member/logout")).andDo(print());

    }

    @Test
    void update() throws Exception {

        mvc.perform(get("/member/update")).andDo(print());

    }

/*    @Test
    void testUpdate() throws Exception {
        LoginDto loginDto = LoginDto.builder()
                .mno(1)
                .mname("qweqwe")
                .build();
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("login", loginDto);
        mvc.perform(put("/member/update")
                        .param("mname" , "qweqwe")
                        .session(mockHttpSession))
                .andDo(print());
    }*/

    @Test
    void info() throws Exception {
        mvc.perform(get("/member/info")).andDo(print());
    }

    @Test
    void myroom() throws Exception {

        mvc.perform(get("/member/myroom")).andDo(print());

    }

    @Test
    void signup() throws Exception {

        mvc.perform(get("/member/singnup")).andDo(print());

    }

/*    @Test
    void testdelete() throws Exception{

        LoginDto loginDto = LoginDto.builder()
                .mno(1)
                .build();
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("login" , loginDto);

        mvc.perform(delete("/member/delete")
                        .param("mpassword" , "qweqwe")
                        .session(mockHttpSession))
                .andDo(print());

    }*/

    @Test
    void save() {

    }

}