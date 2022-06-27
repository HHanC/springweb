package ezenweb.dto;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.Role;
import lombok.*;

import java.util.Map;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthDto { // Oauth회원 정보를 담고있는 Dto

    private String mid;// 아이디 [이메일에서 아이디만 추출 vs 문자열 난수]
    private String mname;// 이름
    private String memail;// 이메일
    private String registrationId;// 회원정보
    private Map<String, Object> attributes;// 인증결과 // 결과 반환타입 json
    private String nameAttributeKey;//클라이언트 id

    // 1. 클라이언트 구분 메소드[네이버 or 카카오] p.186 -> 1
    public static OauthDto of(String registrationId, String nameAttributeKey , Map<String, Object> attributes){

        if(registrationId.equals("naver")){
            return ofnaver(registrationId, nameAttributeKey, attributes);
        }else if(registrationId.equals("kakao")){
            return ofkakao(registrationId, nameAttributeKey, attributes);
        }
        return null;
    }

    // 2. 만약에 registrationId가 네이버 이면
    public static OauthDto ofnaver( String registrationId , String nameAttributeKey, Map<String, Object> attributes){

        // p. 208
        Map<String , Object> response = (Map<String, Object>) attributes.get(nameAttributeKey);
        String mid = ((String) response.get("email")).split("@")[0]; // 이메일에서 아이디만 추출

        return OauthDto.builder()
                .mid(mid)
                .mname((String)response.get("name")) // 네이버 로그인 문서참고 : 요청변수 확인
                .memail((String)response.get("email"))
                .registrationId(registrationId)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    // 3. 만약에 registrationId가 카카오 이면
    public static OauthDto ofkakao(String registrationId, String nameAttributeKey, Map<String, Object> attributes){

        Map<String , Object> kakao_account = (Map<String, Object>) attributes.get(nameAttributeKey);
        Map<String , Object> profile = (Map<String, Object>) kakao_account.get("profile");

        String mid = ((String) kakao_account.get("email")).split("@")[0];

        return OauthDto.builder()
                .mid(mid)
                .mname((String)profile.get("nickname"))
                .memail((String)kakao_account.get("email"))
                .registrationId(registrationId)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    // oauth정보 -> entity 변경
    public MemberEntity toentity(){
        return MemberEntity.builder()
                .mid(this.mid)
                .mname(this.mname)
                .memail(this.memail)
                .oauth(this.registrationId)
                .role(Role.MEMBER)
                .build();
    }

    /*
    *  kakao_account = {
    *       profile = {nicname = 이름}
    *       email
    * }
    * */


}
