package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

  @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
  @Test
  void containsStockType() {
    // given
    ProductType givenType = HANDMADE;

    // when
    boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isFalse();
  }

  @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
  @Test
  void containsStockType2() {
    // given
    ProductType givenType = BAKERY;

    // when
    boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isTrue();
  }
}