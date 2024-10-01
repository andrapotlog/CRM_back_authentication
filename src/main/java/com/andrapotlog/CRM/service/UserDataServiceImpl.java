package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.data.EditUserDataDTO;
import com.andrapotlog.CRM.entity.Role;
import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.exceptions.UserAlreadyExistsException;
import com.andrapotlog.CRM.exceptions.UserDoesNotExistException;
import com.andrapotlog.CRM.exceptions.UserNotFoundException;
import com.andrapotlog.CRM.repository.RoleRepo;
import com.andrapotlog.CRM.repository.UserDataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService{

    @Autowired
    private UserDataRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);


    @Override
    public List<UserData> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<String> listEmails(Integer location) {
        return userRepository.findAllEmails(location);
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

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("ROLE_USER")); // Default role
        user.setRoles(roles);

        logger.info("Creating new user: {}", user.getEmail());
        userRepository.save(user);

    }

    public UserData updateUser(Long id, EditUserDataDTO newUser){
        Optional<UserData> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }


        UserData user = existingUser.get();
        BeanUtils.copyProperties(newUser, user, "id_user", "email", "cnp", "client_code", "terms_and_conditions", "roles", "creation_date", "birthdate");
        System.out.println(user.toString());
        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public void removeUser() {

    }
}
