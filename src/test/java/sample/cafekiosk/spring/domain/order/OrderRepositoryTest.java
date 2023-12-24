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
    LocalDateTime now = LocalDateTime.of(2023, 12, 2, 10, 0);

    Product product1 = createProduct("001", 1000);
    Product product2 = createProduct("002", 2000);
    Product product3 = createProduct("003", 3000);
    productRepository.saveAll(List.of(product1, product2, product3));

    List<Product> products = List.of(product1, product2, product3);
    Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2023, 12, 1, 23, 59, 59), products);
    Order order2 = createPaymentCompletedOrder(now, products);
    Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023, 12, 2, 23, 59, 59), products);
    Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023, 12, 3, 0, 0), products);
    LocalDate orderDate = LocalDate.of(2023, 12, 2);

    // when
    List<Order> orders = orderRepository.findOrdersBy(
        orderDate.atStartOfDay(),
        orderDate.plusDays(1).atStartOfDay(),
        OrderStatus.COMPLETE
    );

    // then
    assertThat(orders).hasSize(2);
    assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    assertThat(orders.get(0).getRegisteredDateTime()).isEqualTo(now);
    assertThat(orders.get(1).getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    assertThat(orders.get(1).getRegisteredDateTime()).isEqualTo(
        LocalDateTime.of(2023, 12, 2, 23, 59, 59));
  }

  private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
    Order order = Order.builder()
        .products(products)
        .orderStatus(OrderStatus.COMPLETE)
        .registeredDateTime(now)
        .build();
    orderRepository.save(order);
    return order;
  }


  private Product createProduct(String productNumber, int price) {
    return Product.builder()
        .type(HANDMADE)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("메뉴 이름")
        .build();
  }
}