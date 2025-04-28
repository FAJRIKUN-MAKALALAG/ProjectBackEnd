package com.Project_backend.Service;

import com.Project_backend.Entity.Category;
import com.Project_backend.Entity.Product;
import com.Project_backend.Repository.CategoryRepository;
import com.Project_backend.Repository.ProductRepository;
import com.Project_backend.dto.CreateProductRequest;
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

    // Method untuk membuat produk baru dengan validasi kategori
    public ResponseEntity<Product> create(CreateProductRequest request) {
        try {
            // Cari kategori berdasarkan nama kategori
            Optional<Category> categoryOpt = categoryRepository.findByName(request.getCategoryName());
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // Jika kategori tidak ditemukan
            }

            // Buat objek Product baru
            Product product = new Product();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStock(request.getStock());
            product.setImageBase64(request.getImageBase64());
            product.setCategory(categoryOpt.get()); // Mengaitkan kategori dengan produk

            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null); // Error jika terjadi masalah saat menyimpan produk
        }
    }

    // Method untuk mendapatkan detail produk berdasarkan ID
    public ResponseEntity<Product> detail(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Jika produk tidak ditemukan
    }

    // Method untuk mendapatkan semua produk
    public List<Product> listProduct() {
        return productRepository.findAll(); // Mengembalikan semua produk
    }
    public ResponseEntity<Product> update(Long id, CreateProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setImageBase64(request.getImageBase64());

        // Kalau kamu punya relasi ke category, bisa diupdate juga
        if (request.getCategoryName() != null) {
            Category category = categoryRepository.findByName(request.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    // Method untuk menghapus produk berdasarkan ID
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // Method untuk mendapatkan produk berdasarkan nama kategori
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(String categoryName) {
        // Cari produk berdasarkan nama kategori
        List<Product> products = productRepository.findByCategoryName(categoryName);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // Jika tidak ada produk ditemukan
        }

        // Convert produk menjadi response list
        List<ProductResponse> responseList = products.stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setImageBase64(product.getImageBase64());
            response.setCategoryName(product.getCategory().getName()); // Menambahkan nama kategori ke response
            return response;
        }).toList();

        return ResponseEntity.ok(responseList); // Mengembalikan daftar produk berdasarkan kategori
    }
}
