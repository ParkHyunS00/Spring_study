package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/*
 * 설정 정보가 없어도 자동으로 스프링 빈 등록하는 컴포넌트 스캔 기능
 * 의존관계 자동 주입하는 @Autowired 기능 제공
 */

@Configuration
@ComponentScan( // 컴포넌트 스캔 사용 및 컴포넌트 스캔 대상 제외
        basePackages = "hello.core.member", // 탐색할 패키지의 시작 위치 지정, default 는 @ComponentScan 붙은 설정 정보 클래스의 패키지가 시작 위치
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) // 기존에 만들었던 Configuration 어노테이션 붙은 설정 제외 -> @Configuration 또한 자동으로 등록됨
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository") // 수동 등록 빈 vs 자동 등록 빈 충돌 시 수동 빈 등록이 우선권 가짐
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
