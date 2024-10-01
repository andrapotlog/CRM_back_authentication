package com.andrapotlog.CRM.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="user_passwords")
@Getter
@Setter
public class UserPassword {
    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    public UserPassword() {}

    public UserPassword(String password, String email) {
        this.password = password;
        this.email = email;
    }
}

