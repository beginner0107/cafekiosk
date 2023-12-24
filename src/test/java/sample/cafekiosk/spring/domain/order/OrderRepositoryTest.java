package sample.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;

  @DisplayName("주문 상태가 완료인, 하루에 주문한 주문 목록을 가져온다.")
  @Test
  void findOrdersBy() {
    // given
    Order order = createOrderAndOrderStatusComplete(OrderStatus.COMPLETE);

    // when
    List<Order> orders = orderRepository.findOrdersBy(
        LocalDate.now().atStartOfDay(),
        LocalDate.now().plusDays(1).atStartOfDay(),
        OrderStatus.COMPLETE
    );

    // then
    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    assertThat(orders.get(0).getRegisteredDateTime()).isEqualTo(order.getRegisteredDateTime());
  }

  private Order createOrderAndOrderStatusComplete(OrderStatus orderStatus) {
    List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000)
    );
    productRepository.saveAll(products);

    LocalDateTime registeredDateTime = LocalDateTime.now();
    Order order = Order.create(products, registeredDateTime);
    order.changeOrderStatus(orderStatus);
    orderRepository.save(order);

    return order;
  }

  private Product createProduct(String productNumber, int price) {
    return Product.builder()
        .type(HANDMADE)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("메뉴이름")
        .build();
  }
}