package ezenweb.controller;

import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 컨트롤과 뷰의 통신 역할 // 템플릿 영역
@RequestMapping("/member")
public class MemberController {

    // 1. 로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/email/{authkey}/{mid}")
    public String signupemail(@PathVariable("authkey") String authkey , @PathVariable("mid") String mid){
        // @PathVariable : 경로상(url) 변수 요청
        System.out.println("검증번호 : " + authkey);
        // 이메일 검증 처리
        boolean result = memberService.authsuccess(authkey, mid);
        if(result){
            return "memmber/authsuccess";
        }else{
            return  "";
        }
    }

/*    // 2.  로그인 처리 매핑 [시큐리티 사용하기 전]
    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestParam("mid") String mid ,
                         @RequestParam("mpassword") String mpassword){
        System.out.println("id : " + mpassword);
        return memberService.login(mid, mpassword);
    }*/

    // 4. 로그아웃 처리 매핑
/*    @GetMapping("/logout")
    public String logout(){
        memberService.logout();
        // return "main"; // 타임리프 반환
        return "redirect:/"; // url 이동
    }*/
    // 5. 회원수정 페이지 이동 매핑
    @GetMapping("/update")
    public String update(){
        return "member/update";
    }

    // 6. 회원수정 처리 매핑
    @PutMapping("/update")
    @ResponseBody
    public boolean update(@RequestParam("mname") String mname){

        return memberService.update(mname);
    }

    // 7.
    @GetMapping("/info")
    public String info(){

        return "member/info";

    }

    // 8.
    @GetMapping("/myroom")
    public String myroom(){

        return "member/myroom";

    }


    // 3. 회원가입 페이지 열기 이동 맾칭
    @GetMapping("/signup")
    public String signup(){
        return "member/write";
    }
    // member서비스
    @Autowired
    MemberService memberService;

    @GetMapping("/delete")
    public String delete(){return "member/delete";}

    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("mpassword") String mpassword ){
        return memberService.delete(mpassword);
    }

    // 4. 회원가입 페이지 이동 처리 매핑
    @PostMapping("/signup")
    @ResponseBody
    public boolean save(MemberDto memberDto){
        boolean result = memberService.signup(memberDto);
        System.out.println("이메일!!!! : " + memberDto.getMemail());
        return result;
    }
}
