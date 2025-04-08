package com.Project_backend.Repository;

import com.Project_backend.Entity.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Long> {
    Biodata findByUserName(String name); // method ini yang kamu butuhkan
}
