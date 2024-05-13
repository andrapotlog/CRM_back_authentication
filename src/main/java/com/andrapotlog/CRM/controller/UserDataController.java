package com.andrapotlog.CRM.controller;

import com.andrapotlog.CRM.data.UserDataDTO;
import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.pojo.AuthenticationResponse;
import com.andrapotlog.CRM.security.JwtConfig;
import com.andrapotlog.CRM.service.UserDataService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDataController {
    private UserDataService userService;

    private final JwtConfig jwtConfig;

    //check client Code!!!

    @Autowired
    public UserDataController(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Autowired
    public void setUserService(UserDataService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getAllUsers() {
        List<UserData> users = userService.listUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserDataDTO userDTO) {
        System.out.println("access");
        try {
            userService.addUser(new UserData(
                                        userDTO.getFirstName(),
                                        userDTO.getLastName(),
                                        userDTO.getEmail(),
                                        userDTO.getClientCode(),
                                        userDTO.getCnp(),
                                        userDTO.getPhoneNumber(),
                                        userDTO.getAddress(),
                                        userDTO.getCity(),
                                        userDTO.getCountry(),
                                        userDTO.getPostalCode()));
            return ResponseEntity.ok(new AuthenticationResponse("User created successfully",200));
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            String email = claims.getSubject();

            try{
                return new ResponseEntity<>(userService.findUser(email), HttpStatus.OK);
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(new AuthenticationResponse(exception.getMessage(), 400));
            }
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse("UNAUTHORIZED", 401));
        }
    }
}
