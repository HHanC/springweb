package ezenweb.dto;

import ezenweb.domain.board.BoardEntity;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardDto {

    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int blike;

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