package sample.cafekiosk.spring.api.service.product.request;

import lombok.Builder;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

public class ProductCreateServiceRequest {
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name,
      int price) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public Product toEntity(String newProductNumber) {
    return Product.builder()
        .productNumber(newProductNumber)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }
}
