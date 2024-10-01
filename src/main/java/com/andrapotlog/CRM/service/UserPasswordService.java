package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserData;
import com.andrapotlog.CRM.entity.UserPassword;

public interface UserPasswordService {
/*
    void addUser(UserPassword user);
*/
    UserData authenticate(UserPassword user);

    void existsByEmail(String email);

    UserPassword addUser(UserPassword user);
}
