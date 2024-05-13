package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.entity.UserData;

import java.util.List;

public interface UserDataService {
List<UserData> listUsers();

UserData findUser(String email);

void addUser(UserData user);

void removeUser();

}
