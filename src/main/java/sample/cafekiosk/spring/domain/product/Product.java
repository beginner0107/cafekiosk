package sample.cafekiosk.spring.domain.product;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Product extends BaseEntity {

  @Id @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String productNumber;
  @Enumerated(STRING)
  private ProductType type;
  @Enumerated(STRING)
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  public Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus,
      String name, int price) {
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }
}
