package hello.core.member;

public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository = new MemoryMemberRepository(); // 추상화 및 구체화에 의존중

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
