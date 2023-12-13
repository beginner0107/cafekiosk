package sample.cafekiosk.spring.domain.product;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Product {

  @Id @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String productNumber;
  @Enumerated(STRING)
  private ProductType type;
  @Enumerated(STRING)
  private ProductSellingType sellingType;
  private String name;
  private int price;

}
