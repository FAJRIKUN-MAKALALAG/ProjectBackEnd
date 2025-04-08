package com.Project_backend.Controller;

import com.Project_backend.Entity.User;
import com.Project_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user){
        return userService.create(user);
    }

    @GetMapping
    public ResponseEntity<Object> getListUser(){
        return userService.getListData();
    }

    @GetMapping("/{id}")
    public Object getDetailUser(@PathVariable("id") Long id){
        return userService.getDataDetail(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleted(id);
    }

    @PutMapping
    public Object updateUser(@RequestBody User user){
        return  userService.update(user);
    }
}
