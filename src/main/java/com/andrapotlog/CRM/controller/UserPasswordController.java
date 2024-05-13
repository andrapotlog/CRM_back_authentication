package com.andrapotlog.CRM.controller;

import com.andrapotlog.CRM.data.UserPasswordDTO;
import com.andrapotlog.CRM.entity.UserPassword;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.exceptions.WrongPasswordException;
import com.andrapotlog.CRM.pojo.AuthenticationResponse;
import com.andrapotlog.CRM.security.JwtConfig;
import com.andrapotlog.CRM.service.UserPasswordService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class UserPasswordController {
    private UserPasswordService userPasswordService;

    private final JwtConfig jwtConfig;

    @Autowired
    public UserPasswordController(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Autowired
    public void setUserPasswordService(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserPasswordDTO userDTO){
        try {
            userPasswordService.authenticate(new UserPassword(userDTO.getPassword(),userDTO.getEmail()));

            String token = Jwts.builder()
                                .setSubject(userDTO.getEmail())
                                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                                .compact();

            return ResponseEntity.ok(new AuthenticationResponse(token,200));
        } catch(UserDoesNotExistException | WrongPasswordException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUserPassword(@RequestBody UserPasswordDTO userDTO){
        try {
            userPasswordService.addUser(new UserPassword(
                    userDTO.getPassword(), userDTO.getEmail()));

            return ResponseEntity.ok(new AuthenticationResponse("User created successfully", 200));
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }
}
