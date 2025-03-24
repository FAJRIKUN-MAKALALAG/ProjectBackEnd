package com.Project_backend.Service;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Object create(User user){
        return userRepository.save(user);
    }

    public Object getListData(){
        return userRepository.findAll();
    }

    public Object getDataDetail(Long id){
        return userRepository.findById(id).get();
    }

    public void deleted(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }
    public User update(User user){
        // check di database if user exist
        // if exist then update
        // else throw error
        return null;
    }
}
