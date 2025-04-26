package com.Project_backend.Repository;

import com.Project_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Method untuk mencari user berdasarkan email
    Optional<User> findByEmail(String email);
}
