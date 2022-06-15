package ezenweb.dto;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.room.RoomEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    // 필드
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

    // dto -> entity
    public MemberEntity toentity(){
        return MemberEntity.builder() // 빌더패턴 : 포함하지 않는 필드는 0 또는 null
                .mid(this.mid)
                .mpassword(this.mpassword)
                .mname(this.mname)
                .roomEntityList(new ArrayList<>())
                .build();
    }

}
