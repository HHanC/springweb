package ezenweb.dto;

import ezenweb.domain.room.RoomEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomDto {

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

    private List<MultipartFile> rimg;

    // Dto -> Entity 메소드
        // 1. 생성자
        // 2. 빌더패턴 [빌더에 포함되지 않는 필드는 0 또는 null]
        // 3. ModelMapper 라이브러리
    public RoomEntity toentity(){
        return RoomEntity.builder()
                .rno(this.rno)
                .rtitle(this.rtitle)
                .rlat(this.rlat)
                .rlon(this.rlon)
                .rtrans(this.rtrans)
                .rprice(this.rprice)
                .rarea(this.rarea)
                .rmanagementfee(this.rmanagementfee)
                .rstructure(this.rstructure)
                .rcompletiondate(this.rcompletiondate)
                .rindate(this.rindate)
                .rkind(this.rkind)
                .raddress(this.raddress)
                .ractive(this.ractive)
                .rfloor(this.rfloor)
                .rmaxfloor(this.rmaxfloor)
                .relevator(this.relevator)
                .rparking(this.rparking)
                .rcontents(this.rcontents)
                .build();
    }

}















