package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name = "category")
public class CategoryEntity extends BaseTime {

    @Id
    @GeneratedValue
    private int cno;
    private String cname;
    // board 연관관계
    @Builder.Default
    @OneToMany(mappedBy = "categoryEntity" , cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList = new ArrayList<>();



}
