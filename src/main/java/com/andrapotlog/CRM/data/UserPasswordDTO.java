package com.andrapotlog.CRM.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordDTO {
    private String password;

    private String email;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO( String password, String email) {
        this.password = password;
        this.email = email;
    }
}
