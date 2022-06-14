package ezenweb.domain.room;

import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="room")
public class RoomEntity { // Entity = 개체

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;
    private String rtitle;              // 방 타이틀
    private String rlat;                 // 위도
    private String rlon;                 // 경도
    private String rtrans;              // 거래방식(월세 전세 매매)
    private String rprice;              // 가격
    private String rarea;               // 면적
    private String rmanagementfee;      // 관리비
    private String rstructure;          // 구조
    private String rcompletiondate;     // 준공날짜
    private String rindate;            // 입주가능일
    private String rkind;               // 건물 종류
    private String raddress;            // 주소
    private String ractive;             // 거래상태
    private int rfloor;                 // 현재층
    private int rmaxfloor;               // 건물 전체층
    private boolean relevator;          // 엘리베이터
    private boolean rparking;            // 주차 여부
    @Column(columnDefinition = "TEXT")
    private String rcontents;           // 상세설명

    @Builder.Default
    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    private List<RoomimgEntity> roomimgEntityList = new ArrayList<>();


}




















