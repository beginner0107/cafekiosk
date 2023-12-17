package sample.cafekiosk.spring.domain.stock;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Stock extends BaseEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String productNumber;

  private int quantity;

  @Builder
  public Stock(String productNumber, int quantity) {
    this.productNumber = productNumber;
    this.quantity = quantity;
  }

  public static Stock create(String productNumber, int quantity) {
    return Stock.builder()
        .productNumber(productNumber)
        .quantity(quantity)
        .build();
  }

  public boolean isQuantityLessThen(int quantity) {
    return this.quantity < quantity;
  }

  public void deductQuantity(int quantity) {
    if (quantity > this.quantity) throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
    this.quantity -= quantity;
  }
}
