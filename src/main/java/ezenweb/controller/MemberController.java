package ezenweb.controller;

import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 컨트롤과 뷰의 통신 역할 // 템플릿 영역
public class MemberController {

    // 1. 로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    // member서비스
    @Autowired
    MemberService memberService;
    // 2. 회원가입 페이지 이동 매핑
    @GetMapping("/signup")
    public String signup(){
        MemberDto memberDto = MemberDto.builder()
                .mid("qweqwe")
                .mpassword("qweqwe")
                .mname("qweqwe")
                .build();
        memberService.signup(memberDto);
        return "signup";
    }
}
