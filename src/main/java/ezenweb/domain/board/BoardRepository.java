package ezenweb.domain.board;

import ezenweb.domain.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 1. 검색 메소드
        // 1. findAll() :  모든 엔티티 호출
        // 2. findbyid(pk값) : 해당 pk의 엔티티 호출
        // 3. [직접 선언] : .findBy필드명(값) : 해당 필드명에서 값에 해당하는 엔티티 검색 [Optional]
        // 4. [직접 선언] : .findBy필드명(값) : 해당 필드명에서 값에 해당하는 여러개 엔티티 검색 [List<엔티티명>]
        // 5. [직접 쿼리작성] : @Query(value = "쿼리문작성" , nativeQuery = true)
                    // SQL에 변수 넣기
                        // * 필드(col)
                        // * @Param() 생략가능
                        // : 변수명 , ?인수순서번호
                        // 1. [인수] @Param("변수명") String 변수명 -> [sql] : 변수명
                        // 2. [인수] @Param("변수명") 엔티티/dto 변수명 -> [sql] : #{#엔티티명.필드명}
        // 1. 제목 검색
                // 1. sql 없이
                // List<BoardEntity> findBybtitle(String keyword);
                // 2. sql 적용
//        @Query(value = "select * from board where btitle = :keyword" , nativeQuery = true)
//        List<BoardEntity> findBybtitle(@Param("keyword") String keyword);
        @Query(value = "select * from board where cno = :cno and btitle like %:keyword%" , nativeQuery = true)
        Page<BoardEntity> findBybtitle(int cno , String keyword , Pageable pageable); // @Param 없을 경우 // ?1
        // List 대신 Page 사용하는 이유 : Page
        @Query(value = "select * from board where cno = :cno and bcontent like %:keyword%" , nativeQuery = true)
        Page<BoardEntity> findBybcontent(int cno , String keyword,Pageable pageable ); // @Param 없을 경우 // ?1
        // 3. 작성자 검색
//        List<BoardEntity> findBymno(MemberEntity memberEntity);
        @Query(value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true)
        Page<BoardEntity> findBymno( int cno ,@Param("memberEntity") MemberEntity memberEntity,Pageable pageable); // @Param 없을 경우 // ?1

}


















