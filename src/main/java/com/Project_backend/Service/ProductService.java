package com.Project_backend.Service;

import com.Project_backend.Entity.Category;
import com.Project_backend.Entity.Product;
import com.Project_backend.Repository.CategoryRepository;
import com.Project_backend.Repository.ProductRepository;
import com.Project_backend.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Product> create(Product product) {
        try {
            // Validasi kategori
            if (product.getCategory() == null || product.getCategory().getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Set kategori yang valid dari database
            product.setCategory(categoryOpt.get());

            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
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
        if (products.isEmpty()) {
            System.out.println("Tidak ada produk dalam database.");
        } else {
            System.out.println("Data produk yang dikembalikan: " + products.size());
        }
        return products;
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);

        List<ProductResponse> responseList = products.stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setImageBase64(product.getImageBase64());
            response.setCategoryName(product.getCategory().getName()); // ambil nama kategori
            return response;
        }).toList();

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<List<Product>> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }


}
