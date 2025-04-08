package com.Project_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
//@Setter
//@Getter
public class BiodataDTO {
    private String name;
    private Integer age;
    private Character gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }
}
