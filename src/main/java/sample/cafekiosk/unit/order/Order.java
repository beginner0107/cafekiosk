package sample.cafekiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;

@Getter
public class Order {
  private final LocalDateTime orderDateTime;
  private final List<Beverage> beverages;

  public Order(LocalDateTime orderDateTime, List<Beverage> beverages) {
    this.orderDateTime = orderDateTime;
    this.beverages = beverages;
  }
}
