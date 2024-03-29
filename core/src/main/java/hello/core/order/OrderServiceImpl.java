package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // 필수 값인 final 필드 초기화해주는 생성자를 만들어 줌
public class OrderServiceImpl implements OrderService {

    // DiscountPolicy 인터페이스만 의존함, 생성자를 통해 어떤 구현 객체가 들어올지 알 수 없음
    // 어떤 구현 객체를 주입할 지는 오직 AppConfig 에서 결정, 실행에만 집중

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // 구현체에 의존하지 않도록 함(DIP는 지킴) But NullPointException

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 생성자가 1개만 있다면 @Autowired 생략해도 됨

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}


