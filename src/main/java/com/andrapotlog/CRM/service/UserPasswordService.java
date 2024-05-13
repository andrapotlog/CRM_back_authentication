package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserPassword;

public interface UserPasswordService {
/*
    void addUser(UserPassword user);
*/
    String authenticate(UserPassword user);

    UserPassword addUser(UserPassword user);
}
