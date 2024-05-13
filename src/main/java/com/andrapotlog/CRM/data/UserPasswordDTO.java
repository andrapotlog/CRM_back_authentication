package com.andrapotlog.CRM.data;

import lombok.Getter;

@Getter
public class UserPasswordDTO {
    private String password;

    private String email;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO( String password, String email) {
        this.password = password;
        this.email = email;   }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
