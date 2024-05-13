package com.andrapotlog.CRM.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_passwords")
public class UserPassword {
    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    public UserPassword() {    }

    public UserPassword(String password, String email) {
        this.password = password;
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

