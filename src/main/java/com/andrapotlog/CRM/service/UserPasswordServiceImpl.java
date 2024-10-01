package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.entity.UserPassword;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.exceptions.WrongPasswordException;
import com.andrapotlog.CRM.repository.UserDataRepo;
import com.andrapotlog.CRM.repository.UserPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPasswordServiceImpl implements UserPasswordService{
    @Autowired
    private UserPasswordRepo userPasswordRepo;

    @Autowired
    private UserDataRepo userDataRepo;

    public UserPasswordServiceImpl() {
    }

    @Override
    public UserData authenticate(UserPassword user) {
        Optional<UserPassword> userOptional = userPasswordRepo.findById(user.getEmail());

        if (userOptional.isEmpty())
            throw new UserDoesNotExistException("User does not exist");

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        UserPassword retrievedUser = userOptional.get();

        if(!bcrypt.matches(user.getPassword(), retrievedUser.getPassword()))
            throw new WrongPasswordException("Email and password do not match");

        return userDataRepo.findByEmail(user.getEmail());
    }

    @Override
    public void existsByEmail(String email) {
        System.out.println(email);
        System.out.println(userPasswordRepo.existsByEmail(email));
        if(userPasswordRepo.existsByEmail(email)) {
            throw new UserAlreadyExistsException("This email is already registered");
        }
    }

    @Override
    public UserPassword addUser(UserPassword user) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String encodedPassword = bcrypt.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userPasswordRepo.save(user);

        return user;
    }
}
