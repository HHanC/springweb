package ezenweb.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 풀 생성자
@Builder // 안정성 보장 // 필요한 데이터만 넣을 수 있음 // 가독성이 좋음 // 생성자 사용 규칙 x
// --> 생성자 만드는데 안정성 보장 [인수 순서, 개수, null/0 등등]
public class HelloDto {
    // 필드
    private String name;
    private int amount;

    // 생성자

    // get set

    // to string

}
