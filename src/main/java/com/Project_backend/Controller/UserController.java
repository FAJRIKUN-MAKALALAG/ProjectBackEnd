package com.Project_backend.Controller;

import com.Project_backend.Entity.User;
import com.Project_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return userService.create(user);
    }


    @GetMapping("/list")
    public ResponseEntity<Object> getList() {
        return userService.getListData();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable Long id) {
        return userService.getDataDetail(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User userUpdate) {
        return userService.update(id, userUpdate);
    }
}
