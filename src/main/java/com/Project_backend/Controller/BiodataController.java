package com.Project_backend.Controller;

import com.Project_backend.Entity.Biodata;
import com.Project_backend.Service.BiodataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biodata")
@CrossOrigin("http://localhost:5173")
public class BiodataController {

    @Autowired
    private BiodataService biodataService;

    @PostMapping("/add")
    public Biodata addBiodata(@RequestBody Biodata biodata) {
        return biodataService.save(biodata);
    }

    @GetMapping("/list")
    public List<Biodata> getAllBiodata() {
        return biodataService.findAll();
    }

    @GetMapping("/detail/{name}")
    public Biodata getByName(@PathVariable String name) {
        return biodataService.findByName(name);
    }
}

