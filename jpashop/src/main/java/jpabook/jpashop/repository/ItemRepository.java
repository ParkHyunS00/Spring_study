package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) { // 상품 저장
        if (item.getId() == null) { // 완전히 새로 생성한 객체
            em.persist(item);
        } else {
            em.merge(item); // update 비슷? , 변경된 거 갈아치워버림, 가급적 merge를 쓰지 말고 변경 감지를 사용할 것
            // merge 는 모든 필드를 교체하기 때문 ==> 엔티티를 변경할 때는 항상 변경 감지를 사용할 것
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
