package sample.cafekiosk.spring.api.service.order.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderCreateServiceRequest {

  private List<String> productNumbers;

  public static OrderCreateServiceRequest of(List<String> productNumbers) {
    return OrderCreateServiceRequest.builder()
        .productNumbers(productNumbers)
        .build();
  }
}
