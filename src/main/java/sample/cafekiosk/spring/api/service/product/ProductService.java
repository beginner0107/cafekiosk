package sample.cafekiosk.spring.api.service.product;

import static java.util.stream.Collectors.toList;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.forDisplay;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  // 동시성 이슈
  // UUID
  @Transactional
  public ProductResponse createProduct(ProductCreateServiceRequest request) {
    String newProductNumber = createNewProductNumber();
    Product product = request.toEntity(newProductNumber);
    Product savedProduct = productRepository.save(product);
    return ProductResponse.of(savedProduct);
  }

  public List<ProductResponse> getSellingProducts() {
    List<Product> products = productRepository.findAllBySellingStatusIn(
        forDisplay());
    return products.stream()
        .map(ProductResponse::of)
        .collect(toList());
  }

  private String createNewProductNumber() {
    String latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) return "001";
    int latestProductNumberInt = Integer.parseInt(latestProductNumber);
    int nextProductNumberInt = latestProductNumberInt + 1;
    return String.format("%03d", nextProductNumberInt);
  }
}
