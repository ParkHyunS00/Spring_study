package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // Order - OrderItem 관계에서의 주인
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    protected OrderItem() {} // createOrderItem 생성 메서드로만 생성할 수 있도록 제약조건을 걸어둠

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count); // 재고 수량을 원복해줌
    }

    //== 조회 로직 ==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
