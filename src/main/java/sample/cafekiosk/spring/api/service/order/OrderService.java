package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.orderProduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();

    List<Product> duplicateProducts = findProduct(productNumbers);

    Order order = Order.create(duplicateProducts, registeredDateTime);
    Order savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }

  private List<Product> findProduct(List<String> productNumbers) {
    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    Map<String, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getProductNumber, p -> p));
    return productNumbers.stream()
        .map(productMap::get)
        .collect(Collectors.toList());
  }
}
