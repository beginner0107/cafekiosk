package sample.cafekiosk.spring.domain.orderProduct;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderProduct extends BaseEntity {

  @Id @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  private Product product;

  @ManyToOne(fetch = LAZY)
  private Order order;

  public OrderProduct(Product product, Order order) {
    this.product = product;
    this.order = order;
  }
}
