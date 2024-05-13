package com.andrapotlog.CRM.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_data")
@Getter
@Setter
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private long id_user;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="client_code")
    private String clientCode;

    @Column(name="cnp")
    private String cnp;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="postal_code")
    private String postalCode;

    public UserData() {
    }

    public UserData(String firstName, String lastName, String email, String clientCode, String cnp, String phoneNumber, String address, String city, String country, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.clientCode = clientCode;
        this.cnp = cnp;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }


}
