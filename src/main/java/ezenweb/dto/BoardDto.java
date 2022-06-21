package ezenweb.dto;

import ezenweb.domain.board.BoardEntity;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardDto {

    private int bno; // 번호
    private String btitle; // 제목
    private String bcontent; // 내용
    private int bview; // 조회수
    private int blike; // 좋아요 수

    private String category; // 카테고리

    // DTO -> ENTITY
    public BoardEntity toentity(){
        return BoardEntity.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .blike(this.blike)
                .build();
    }

}
