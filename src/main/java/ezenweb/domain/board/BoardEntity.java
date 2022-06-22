package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import ezenweb.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = {"memberEntity","categoryEntity"} ) // 객체 주소값 대신 데이터로 출력해주는 메소드
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board") @Entity
public class BoardEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int blike;

    // 작성자 [연관관계]
    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;
    // 카테고리 [연관관계]
    @ManyToOne
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity;
    // 첨부파일 [연관관계]

    // 댓글 [연관관계]


}
