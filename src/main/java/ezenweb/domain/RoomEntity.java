package ezenweb.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;
    private String roomname;
    private String x;
    private String y;
    private String rimg;

    private String transactionmethod; // 거래방식

    private String price; // 가격

    private String area; // 면적

    private String administrativeexpenses; // 관리비

    private String rescue; // 구조

    private String completiondate; // 준공날짜

    private String parking; // 추차 여부

    private String elevator; // 엘리베이터

    private String movein; // 입주가능일

    private String currentfloor; // 현재층

    private String building; // 건물

    private String buildingtype; // 건물 종류

    private String address; // 주소

    private String detaileddescription; // 상세설명

}
