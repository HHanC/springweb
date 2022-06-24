package ezenweb.domain.member;

import ezenweb.domain.BaseTime;
import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.room.RoomEntity;
import ezenweb.domain.room.RoomimgEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

    // DB에 저장될 enum 타입 설정
    @Enumerated(EnumType.STRING) // 열거형 이름
//    @Enumerated(EnumType.ORDINAL) // 열거형 인덱스 번호
    private Role role;  // 권한

    //

    public String getrolekey(){ // 시큐리티에서 인증 허가된
        return role.getKey();
    }

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL)
    List<RoomEntity> roomEntityList ;

    @Builder.Default // 빌더 사용시 초기값 설정
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL) // 1: M
    List<BoardEntity> boardEntityList = new ArrayList<>();
}

// extends : 상속[슈퍼클래스로부터 메모리 할당]