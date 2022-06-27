package ezenweb.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    // 1. 아이디를 이용한 엔티티 검색
    Optional<MemberEntity> findBymid(String mid); // select sql문법 없이 검색 메소드 생성
    // 2. 이메일을 이용한 엔티티 검색
    Optional<MemberEntity> findBymemail(String memail);


}

// 특정 필드 검색 메소드 만들기 : findBy필드명

// JpaRepository [CRUD]
    // 1. findAll() : 모든 엔티티 호출
    // 3. findbyid(pk값) : 해당 pk의 엔티티 호출
    // 2. save(엔티티) : 해당 엔티티를 DB 레코드 추가
    // 4. delete(엔티티) : 해당 엔티티를 삭제 처리
    // 5. 수정은 없다!!!!!!!!ㅡㅡ!!![매핑된 엔티티는 JPA 자동감지 지원]
        // 엔티티를 수정하면 자동으로 DB수정된다.