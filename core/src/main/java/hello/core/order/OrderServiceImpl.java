package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // DiscountPolicy 인터페이스만 의존함, 생성자를 통해 어떤 구현 객체가 들어올지 알 수 없음
    // 어떤 구현 객체를 주입할 지는 오직 AppConfig 에서 결정, 실행에만 집중

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // 구현체에 의존하지 않도록 함(DIP는 지킴) But NullPointException

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
