package com.dac.topic3.controller;


import com.dac.topic3.entity.*;
import com.dac.topic3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SignupController {

    @Autowired
    UserRepository userrepo;

    @PostMapping("/createmember")
    public User createUser(@RequestBody User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(user.getName(), encodedPassword,"inactive","ROLE_CUSTOMER");
        return userrepo.save(newUser);
    }

}
