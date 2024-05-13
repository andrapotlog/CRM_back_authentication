package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserPassword;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.exceptions.WrongPasswordException;
import com.andrapotlog.CRM.repository.UserPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPasswordServiceImpl implements UserPasswordService{
    @Autowired
    private UserPasswordRepo userPasswordRepo;

    public UserPasswordServiceImpl() {
    }

    @Override
    public String authenticate(UserPassword user) {
        Optional<UserPassword> userOptional = userPasswordRepo.findById(user.getEmail());

        if (userOptional.isEmpty())
            throw new UserDoesNotExistException("User does not exist");

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        UserPassword retrievedUser = userOptional.get();

        if(!bcrypt.matches(user.getPassword(), retrievedUser.getPassword()))
            throw new WrongPasswordException("Email and password do not match");

        return user.getEmail();
    }

    @Override
    public UserPassword addUser(UserPassword user) {
        if(userPasswordRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("This email is already registered");
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String encodedPassword = bcrypt.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userPasswordRepo.save(user);

        return user;
    }
}
