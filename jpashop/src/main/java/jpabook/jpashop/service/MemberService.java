package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 컴포넌트 스캔 대상
@Transactional(readOnly = true) // public 메서드들은 트랜잭션에 걸려 들어감, 데이터 변경은 트랜잭션 안에서 일어나야 함, 조회만 할땐 readOnly 옵션
@RequiredArgsConstructor // final 붙은 필드만 생성자 자동 생성
public class MemberService {

    private final MemberRepository memberRepository; // @Autowired 어노테이션 붙일 시 필드 주입 but 변경 불가

    // 생성자 주입, 생성자가 하나만 있는 경우엔 스프링이 자동으로 인젝션 해줌

    /**
    * 회원 가입
    **/
    @Transactional
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member); 
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 문제가 있으면 예외
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
