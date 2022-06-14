package ezenweb.domain.room;

import lombok.*;

import javax.persistence.*;

@Entity @Builder @Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roomimg")
public class RoomimgEntity {

    // pk번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rimgno;
    // 이미지 이름
    private String rimg;
    // 방 번호 [fk]
    @ManyToOne
    @JoinColumn(name = "rno")
    private RoomEntity roomEntity;
}
