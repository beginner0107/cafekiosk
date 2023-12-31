package sample.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static sample.cafekiosk.unit.CafeKiosk.NOT_ORDER_TIME;
import static sample.cafekiosk.unit.CafeKiosk.ORDER_MORE_THAN_ONE_DRINK;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

class CafeKioskTest {

  @Test
  void add_manual_test() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
    System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
  }

  @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
  @Test
  void add() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @DisplayName("음료를 여러개 추가하면 주문 목록에 담긴다.")
  @Test
  void addSeveralBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 2);

    assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
  }

  @DisplayName("음료의 개수를 입력하지 않으면 주문을 생성할 수 없다.")
  @Test
  void addZeroBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(ORDER_MORE_THAN_ONE_DRINK);
  }

  @DisplayName("음료를 추가한 후, 추가된 음료 한 건을 제거할 수 있다.")
  @Test
  void remove() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);
    assertThat(cafeKiosk.getBeverages()).hasSize(1);

    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @DisplayName("여러 개의 음료를 추가한 후, 음료 목록들을 제거할 수 있다.")
  @Test
  void clear() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    Latte latte = new Latte();

    cafeKiosk.add(americano);
    cafeKiosk.add(latte);
    assertThat(cafeKiosk.getBeverages()).hasSize(2);

    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
  @Test
  void calculateTotalPrice() {
    // given
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    Latte latte = new Latte();

    cafeKiosk.add(americano);
    cafeKiosk.add(latte);

    // when
    int totalPrice = cafeKiosk.calculateTotalPrice();

    // then
    assertThat(totalPrice).isEqualTo(8500);
  }

  //@DisplayName("음료를 1개 추가하면 주문목록에 담긴다.")
  //@Test
  void createOrder() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);

    Order order = cafeKiosk.createOrder();

    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @DisplayName("영업 시작 시간과 종료 시간 이전에는 주문을 생성할 수 있다.")
  @Test
  void createOrderWithCurrentTime() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);

    Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,14,0));

    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
  @Test
  void createOrderWithOutsideOpenTime() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);

    assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,9,59)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(NOT_ORDER_TIME);
  }
}
