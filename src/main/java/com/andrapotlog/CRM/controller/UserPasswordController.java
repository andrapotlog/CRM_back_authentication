package com.andrapotlog.CRM.controller;

import com.andrapotlog.CRM.data.UserPasswordDTO;
import com.andrapotlog.CRM.entity.Role;
import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.entity.UserPassword;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.exceptions.WrongPasswordException;
import com.andrapotlog.CRM.pojo.AuthenticationResponse;
import com.andrapotlog.CRM.security.JwtConfig;
import com.andrapotlog.CRM.service.UserPasswordService;
import com.andrapotlog.CRM.service.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class UserPasswordController {
    private UserPasswordService userPasswordService;

    private final JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserPasswordController(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Autowired
    public void setUserPasswordService(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserPasswordDTO userDTO){
        try {
            UserData userData = userPasswordService.authenticate(new UserPassword(userDTO.getPassword(),userDTO.getEmail()));

            userData.getRoles().forEach(role -> System.out.println(role.getRoleName()));
            String token = Jwts.builder()
                                .setSubject(userData.getEmail())
                                .claim("userId", userData.getId_user())
                                .claim("location", userData.getCity())
                                .claim("roles", userData.getRoles().stream()
                                        .map(Role::getRoleName)
                                        .collect(Collectors.toList()))
                    .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                                .compact();

            System.out.println(token);
            System.out.println(userData.getCity());

            return ResponseEntity.ok(new AuthenticationResponse(token,200));
        } catch(UserDoesNotExistException | WrongPasswordException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUserPassword(@RequestBody String email){
        try {
            userPasswordService.existsByEmail( email);

            return ResponseEntity.ok(new AuthenticationResponse("Email OK", 200));
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }
}
