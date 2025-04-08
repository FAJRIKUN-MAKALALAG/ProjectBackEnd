package com.Project_backend.Service;

import com.Project_backend.Entity.Product;
import com.Project_backend.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Product> create(Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<Product> detail(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public List<Product> listProduct() {
        List<Product> products = productRepository.findAll();
        System.out.println("Data produk yang dikembalikan: " + products); // Debug log
        return products;
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
