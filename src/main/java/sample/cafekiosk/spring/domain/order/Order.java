package sample.cafekiosk.spring.domain.order;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderProduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "Orders")
public class Order extends BaseEntity {

  @Id @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Enumerated(STRING)
  private OrderStatus orderStatus;

  private int totalPrice;

  private LocalDateTime registeredDateTime;

  @OneToMany(mappedBy = "order", cascade = ALL)
  private List<OrderProduct> orderProducts;

  public Order(List<Product> products, LocalDateTime registeredDateTime) {
    this.orderStatus = OrderStatus.INIT;
    this.totalPrice = calculateTotalPrice(products);
    this.registeredDateTime = registeredDateTime;
    this.orderProducts = products.stream()
        .map(product -> new OrderProduct(product, this))
        .collect(Collectors.toList());
  }

  public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
    return new Order(products, registeredDateTime);
  }

  private int calculateTotalPrice(List<Product> products) {
    return products.stream()
        .mapToInt(Product::getPrice)
        .sum();
  }
}
