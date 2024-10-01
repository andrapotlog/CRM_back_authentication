package com.andrapotlog.CRM.controller;

import com.andrapotlog.CRM.data.EditUserDataDTO;
import com.andrapotlog.CRM.data.UserDataDTO;
import com.andrapotlog.CRM.data.UserPasswordDTO;
import com.andrapotlog.CRM.data.UserRegistrationDTO;
import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.entity.UserPassword;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.pojo.AuthenticationResponse;
import com.andrapotlog.CRM.security.JwtConfig;
import com.andrapotlog.CRM.service.UserDataService;
import com.andrapotlog.CRM.service.UserPasswordService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDataController {
    private UserDataService userService;
    private UserPasswordService authService;

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

    @Autowired
    public void setAuthService(UserPasswordService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getAllUsers() {
        List<UserData> users = userService.listUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllUsersEmails(@RequestParam(required = false, defaultValue = "0") Integer location) {
        List<String> emails = userService.listEmails(location);
        System.out.println(Arrays.toString(emails.toArray()));

        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserRegistrationDTO registrationDTO) {
        UserPasswordDTO userCredentialsDTO = registrationDTO.getUserCredentials();
        UserDataDTO userDataDTO = registrationDTO.getUserData();

        try {
            authService.addUser(
                   new UserPassword(userCredentialsDTO.getPassword(),userCredentialsDTO.getEmail())
            );

            userService.addUser(new UserData(
                    userDataDTO.getFirstName(),
                    userDataDTO.getLastName(),
                    userDataDTO.getEmail(),
                    userDataDTO.getClientCode(),
                    userDataDTO.getCnp(),
                    userDataDTO.getBirthdate(),
                    userDataDTO.getPhoneNumber(),
                    userDataDTO.getAddress(),
                    userDataDTO.getCity(),
                    userDataDTO.getPostalCode(),
                    userDataDTO.isSendEmail(),
                    userDataDTO.isTermsAndConditions()));

            return ResponseEntity.ok(new AuthenticationResponse("User created successfully",200));
        } catch (UserAlreadyExistsException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse(exception.getMessage(),400));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @RequestBody @Valid EditUserDataDTO editUserDataDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            long user_id = claims.get("userId", Long.class);

            try{
                UserData userData = userService.updateUser(user_id, editUserDataDTO);

                return new ResponseEntity<>(userData, HttpStatus.OK);
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthenticationResponse("UNAUTHORIZED", 401));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            String email = claims.getSubject();

            try{
                UserData user = userService.findUser(email);
                System.out.println(user.toString());
                return new ResponseEntity<>(user, HttpStatus.OK);
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
