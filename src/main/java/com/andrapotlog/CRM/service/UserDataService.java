package com.andrapotlog.CRM.service;

import com.andrapotlog.CRM.data.EditUserDataDTO;
import com.andrapotlog.CRM.entity.UserData;

import java.util.List;

public interface UserDataService {
List<UserData> listUsers();
List<String> listEmails(Integer location);

UserData findUser(String email);

void addUser(UserData user);

UserData updateUser(Long id, EditUserDataDTO newUser);

void removeUser();

}
