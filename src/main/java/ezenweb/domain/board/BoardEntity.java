package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString @Builder
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
    // 첨부파일 [연관관계]
    // 카테고리 [연관관계]
    // 댓글 [연관관계]


}
