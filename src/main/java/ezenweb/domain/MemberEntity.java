package ezenweb.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

}
