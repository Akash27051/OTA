package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private AppUserRepository userRepository;

    public UserController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody AppUser user){

        Optional<AppUser> opUsername = userRepository.findByUsername(user.getUsername());

        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail = userRepository.findByEmail(user.getEmail());

        if (opEmail.isPresent()){
            return new ResponseEntity<>("User already present with this email id: "+ user.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppUser save = userRepository.save(user);

        return new ResponseEntity<>(save, HttpStatus.CREATED);


    }

    @GetMapping("/hello")
    public ResponseEntity<?> greet(){
        return new ResponseEntity<>("Hello Akash", HttpStatus.CREATED);
    }




}
