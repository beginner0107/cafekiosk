package sample.cafekiosk.spring.api.controller.order.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderCreateRequest {

  @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
  private List<String> productNumbers;

  public OrderCreateServiceRequest toServiceRequest() {
    return OrderCreateServiceRequest.of(productNumbers);
  }
}
