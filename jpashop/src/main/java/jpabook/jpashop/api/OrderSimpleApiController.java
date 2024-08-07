package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * _ToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member (ManyToOne)
 * Order -> Delivery (OneToOne)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> simpleOrders() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch()); // Order -> Member -> Order 순환 구조가 되기에 장애 발생
        // 이런 양방향 연결관계가 있을 때 둘 중 하나는 @JsonIgnore 지정
        // 그럼에도 두번째 문제 발생 -> 지연 로딩이기 때문에 초기엔 ByteBuddyInterceptor 로 초기화됨

        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch()); // Order 2개
        List<SimpleOrderDto> result = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        /**
         * ORDER 조회 -> SQL 1번 -> 결과 ROW 2개
         * 위 반복문 2번 돈다는 것
         * Member 쿼리 후 Delivery 쿼리 가 반복 됨
         * 2 개의 결과에 쿼리가 5 개 발생
         *
         * N + 1 문제임 -> 첫 번째 쿼리의 결과만큼 N 번의 쿼리가 추가 실행되는 것
         * 1 + 회원 N + 배송 N
         * 일단 모든 연관관계는 LAZY 로
         */

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return result;

        /**
         * 일단 연관 관계는 LAZY
         * 필요한 것들만 묶어서 fetch join
         */
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
