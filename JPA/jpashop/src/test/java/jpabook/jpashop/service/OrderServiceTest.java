package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상품 주문")
    public void order() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("어린왕자", 10000, 100);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다");
        Assertions.assertEquals(20000, getOrder.getTotalPrice(), "주문 가격은 가격*수량이다.");
        Assertions.assertEquals(98, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test
    @DisplayName("상품 주문 재고 수량 초과")
    public void itemException() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("어린왕자", 10000, 100);

        // when
        int orderCount = 101; // 주문 수량보다 많을 때

        // then
        // ()-> orderService.order(member.getId(), book.getId(), ordercount)가 실행됐을 때,
        // 주문 수량보다 많을 경우 NotEnoughStockException.class를 반환해라
        Assertions.assertThrows(NotEnoughStockException.class, ()->orderService.order(member.getId(), book.getId(), orderCount));

    }

    @Test
    @DisplayName("주문 취소")
    public void itemCounting() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("어린왕자", 10000, 100);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancleOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.CANCLE, getOrder.getStatus(), "주문 상태가 CANCLE 된게 맞는지 확인");
        Assertions.assertEquals(100, book.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    private Item createBook(String title, int price, int quantity) {
        Item book = new Book();
        book.setName(title);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}
