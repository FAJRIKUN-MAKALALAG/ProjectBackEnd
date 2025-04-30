package com.Project_backend.Repository;

import com.Project_backend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    @Query(value = "select * from product where id = ?1 and name = ?2", nativeQuery = true)
    List<Product> findByIdAndName(Long id, String name);

    // Query untuk mengambil produk berdasarkan nama kategori menggunakan JPQL
    @Query("SELECT p FROM Product p WHERE p.category.name = ?1")
    List<Product> findByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    List<Product> findByCategoryId(Long categoryId);

}
