package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.repository.UserDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService{

    @Autowired
    private UserDataRepo userRepository;

    @Override
    public List<UserData> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserData findUser(String email) {
        UserData user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UserDoesNotExistException("User does not exist");
        }
        return user;
    }

    @Override
    public void addUser(UserData user) {
        if(userRepository.existsByCnp(user.getCnp())) {
            throw new UserAlreadyExistsException("Cnp already registered");
        }
        System.out.println("post");

        System.out.println(user.toString());

        userRepository.save(user);

    }

    @Override
    public void removeUser() {

    }
}
