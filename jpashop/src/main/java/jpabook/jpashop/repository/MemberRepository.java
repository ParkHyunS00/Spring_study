package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 컴포넌트 스캔에 의해 자동으로 스프링 빈으로 관리됨
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 스프링이 EntityManager 만들어서 주입해줌

    public void save(Member member) {
        em.persist(member); // insert 쿼리
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // Entity 객체를 대상으로 쿼리, JPQL
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
