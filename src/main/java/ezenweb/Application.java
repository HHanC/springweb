package ezenweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA 매핑된 엔티티(테이블)의 변화감지 [웹 시작시 jap감지 시작]
@SpringBootApplication // 스프링 부트 자동 설정 , 스프링 빈(클래스 메모리) 읽기와 생성을 모두 자동 설정
public class Application {

    public static void main(String[] args) { // 메인 스레드(코드를 읽어주는 역할)
        SpringApplication.run(Application.class); // 내장 서버 (톰캣) 스프링 시작
    }

}
