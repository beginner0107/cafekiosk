package sample.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

  private static final Stock stock = Stock.create("001", 1);

  @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
  @Test
  void isQuantityLessThanEx() {
    // given
    int quantity = 2;

    // when
    boolean result = stock.isQuantityLessThen(quantity);

    // then
    assertThat(result).isTrue();
  }

  @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
  @Test
  void deductQuantityEx() {
    // given
    int quantity = 1;

    // when
    stock.deductQuantity(quantity);

    // then
    assertThat(stock.getQuantity()).isZero();
  }
}