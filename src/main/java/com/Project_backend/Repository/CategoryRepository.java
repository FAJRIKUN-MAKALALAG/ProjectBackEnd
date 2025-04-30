package com.Project_backend.Repository;

import com.Project_backend.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);  // Mencari kategori berdasarkan nama
}
