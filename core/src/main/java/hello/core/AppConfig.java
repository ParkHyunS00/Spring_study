package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 팩토리 메서드를 통해 등록하는 방법
 */
@Configuration
public class AppConfig {
    // 객체의 생성과 연결 담당

    // MemberServiceImpl은 MemberRepository인 추상에만 의존하면 됨
    @Bean // 스프링 컨테이너에 등록
    public MemberService memberService() {
        // MemoryMemberRepository 객체 생성하고 참조값을 생성자로 전달
        // 클라이언트인 MemberServiceImpl 입장에서 보면 의존관계를 외부에서 주입해주는 것 같아 DI라고 함
        return new MemberServiceImpl(memberRepository()); // 생성자 주입
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy(); // 할인 정책을 변경해도 "사용 영역"의 어떤 코드도 변경할 필요 없음
    }

    /* 역할이 전부 드러나는 코드 */
}
