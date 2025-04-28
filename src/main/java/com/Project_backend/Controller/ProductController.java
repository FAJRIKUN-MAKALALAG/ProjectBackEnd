package com.Project_backend.Controller;

import com.Project_backend.Entity.Product;
import com.Project_backend.Service.ProductService;
import com.Project_backend.dto.CreateProductRequest;
import com.Project_backend.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint untuk membuat produk baru
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        return productService.create(request); // Menggunakan ProductService untuk membuat produk
    }

    // Endpoint untuk mendapatkan semua produk
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.listProduct(); // Mengambil semua produk
        return ResponseEntity.ok(products);
    }

    // Endpoint untuk mendapatkan detail produk berdasarkan ID
    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> detail(@PathVariable("id") Long id) {
        return productService.detail(id); // Mendapatkan produk berdasarkan ID
    }
    // Endpoint untuk mengupdate produk berdasarkan ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody CreateProductRequest request) {
        return productService.update(id, request);
    }


    // Endpoint untuk menghapus produk berdasarkan ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id); // Menghapus produk berdasarkan ID
        return ResponseEntity.noContent().build(); // Mengembalikan status 204 No Content
    }

    // Endpoint untuk mendapatkan produk berdasarkan nama kategori
    @GetMapping("/category-name/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(@PathVariable String categoryName) {
        return productService.getProductsByCategoryName(categoryName); // Menggunakan ProductService untuk mengambil produk berdasarkan kategori
    }
}
