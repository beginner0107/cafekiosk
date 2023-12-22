package sample.cafekiosk.spring.api.service.order;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final StockRepository stockRepository;

  /**
   * 재고 감소 -> 동시성 문제
   * optimistic lock / pessimistic lock / ...
   */
  public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();
    List<Product> products = findProductsBy(productNumbers);

    deductStockQuantities(products);

    Order order = Order.create(products, registeredDateTime);
    Order savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }

  private void deductStockQuantities(List<Product> products) {
    List<String> stockProductNumbers = extractStockProductNumbers(products);

    List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
    Map<String, Stock> stockMap = createStockMapBy(stocks);

    Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

    for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
      Stock stock = stockMap.get(stockProductNumber);
      int quantity = productCountingMap.get(stockProductNumber).intValue();

      if (stock.isQuantityLessThen(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }
      stock.deductQuantity(quantity);
    }
  }

  private List<Product> findProductsBy(List<String> productNumbers) {
    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    Map<String, Product> productMap = products.stream()
        .collect(toMap(Product::getProductNumber, p -> p));
    return productNumbers.stream()
        .map(productMap::get)
        .collect(toList());
  }

  private static List<String> extractStockProductNumbers(List<Product> products) {
    return products.stream()
        .filter(product -> ProductType.containsStockType(product.getType()))
        .map(Product::getProductNumber)
        .collect(toList());
  }

  private static Map<String, Stock> createStockMapBy(List<Stock> stocks) {
    return stocks.stream()
        .collect(toMap(Stock::getProductNumber, s -> s));
  }

  private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
    return stockProductNumbers.stream()
        .collect(groupingBy(p -> p, counting()));
  }
}
