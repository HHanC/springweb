package ezenweb.domain.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // jpa
@Table(name = "hello") // 테이블 이름 설정
@Getter // 룸복
@NoArgsConstructor // 룸복
public class HelloEntity {
    @Id // jap : pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa : autokey
    private  Long id;
        // length : 필드길이 , nullable : null 포함 여부
    @Column(length = 500 , nullable = false) // jpa : Column(속성명 = 값)
    private  String title;
        // columnDefinition : "TEXT" = 긴 글 자료형
    @Column(columnDefinition = "TEXT")
    private  String content;

    private  String author;

}
