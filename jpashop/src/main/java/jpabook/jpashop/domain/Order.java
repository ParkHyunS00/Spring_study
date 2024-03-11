package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // X to One 관계는 모두 지연 로딩으로 설정
    @JoinColumn(name = "member_id")
    private Member member; // 양방향 연결관계 -> 연관관계 주인을 정해둘것 (FK 갖고 있는 테이블이 주인)

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화하는 것이 안전

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 1 to 1 인 경우 더 많이 조회하는 쪽에 FK

    private LocalDateTime orderDate; // Hibernate 가 알아서 날짜 매핑해줌, 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관관계 메서드 -> 양방향일 때 쓰면 좋음, 양쪽 세팅하는 것을 한 코드로 해결
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
