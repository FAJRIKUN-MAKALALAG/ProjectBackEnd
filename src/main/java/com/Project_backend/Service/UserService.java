package com.Project_backend.Service;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> create(User user){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> getListData(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
    /**
     * todo
     * buat method update di service
     * buat endpoint di controller
     * */
}
