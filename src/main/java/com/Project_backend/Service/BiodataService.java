package com.Project_backend.Service;

import com.Project_backend.Entity.Biodata;
import com.Project_backend.Repository.BiodataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiodataService {

    @Autowired
    private BiodataRepository biodataRepository;

    // Menyimpan biodata baru
    public Biodata save(Biodata biodata) {
        return biodataRepository.save(biodata);
    }

    // Mengambil semua biodata
    public List<Biodata> findAll() {
        return biodataRepository.findAll();
    }

    // Cari berdasarkan nama user
    public Biodata findByName(String name) {
        return biodataRepository.findByUserName(name); // ✅ ini udah cocok sama repository
    }
}
