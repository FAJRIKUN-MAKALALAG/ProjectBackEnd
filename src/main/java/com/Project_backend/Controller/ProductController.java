package com.Project_backend.Controller;

import com.Project_backend.Entity.Product;
import com.Project_backend.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return productService.create(product);
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.listProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> detail(@PathVariable("id") Long id) {
        return productService.detail(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProduct() {
        return ResponseEntity.ok(productService.listProduct());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
