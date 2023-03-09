package io.backspace.milkman.controller;

import io.backspace.milkman.model.Users;
import io.backspace.milkman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        Users registeredUser;
        ResponseEntity<String> response = null;
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            registeredUser = userService.registerUser(user);
            if(registeredUser.getUserId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("User has been registered successfully");
            }
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error while registering user - "+ex.getMessage());
        }
        return response;
    }


}
