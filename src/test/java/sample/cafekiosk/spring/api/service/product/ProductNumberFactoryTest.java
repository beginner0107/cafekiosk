package sample.cafekiosk.spring.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

class ProductNumberFactoryTest extends IntegrationTestSupport {

  @Autowired
  private ProductNumberFactory productNumberFactory;
  @Autowired
  private ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
  }

  @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
  @Test
  void createNewProductNumber() {
    // when
    String productNumber = productNumberFactory.createNewProductNumber();

    // then
    assertThat(productNumber).isEqualTo("001");
  }

  @DisplayName("신규 제품을 등록할 때 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
  @Test
  void createNewProductNumber2() {
    // given
    Product product = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
    productRepository.save(product);

    // when
    String productNumber = productNumberFactory.createNewProductNumber();

    // then
    assertThat(productNumber).isEqualTo("002");
  }

  private Product createProduct(String productNumber, ProductType type,
      ProductSellingStatus sellingStatus, String name, int price) {
    return Product.builder()
        .productNumber(productNumber)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }
}