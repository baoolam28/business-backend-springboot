package com.onestep.business_management.Service;

import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void create_account(User user){

        User findUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        System.out.println(findUser);
        if (findUser != null) {
            throw new RuntimeException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
