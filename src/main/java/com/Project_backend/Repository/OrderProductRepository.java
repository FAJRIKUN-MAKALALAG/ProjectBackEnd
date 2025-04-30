package com.Project_backend.Repository;


import com.Project_backend.Entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    // Kamu bisa menambahkan method custom jika diperlukan
}
