package com.Project_backend.Repository;

import com.Project_backend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where id = ?1 and name = ?2", nativeQuery = true)
    List<Product> findByIdAndName(Long id, String name);
}
