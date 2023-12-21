package sample.cafekiosk.spring.api.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

@RequiredArgsConstructor
@RestController
public class ProductController {

  private final ProductService productService;

  @PostMapping("/api/v1/products/new")
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.ok(productService.createProduct(request));
  }

  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts() {
    return productService.getSellingProducts();
  }
}
