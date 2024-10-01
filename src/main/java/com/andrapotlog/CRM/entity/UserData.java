package com.andrapotlog.CRM.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_data")
@Getter
@Setter
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
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

    @Column(name="birthdate")
    private LocalDate birthdate;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private Integer city;
/*
    @Column(name="country")
    private String country;*/

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="send_email")
    private boolean sendEmail;

    @Column(name="terms_and_conditions")
    private boolean termsAndConditions;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserData() {
    }

    public UserData(String firstName, String lastName, String email, String clientCode, String cnp, String phoneNumber, String address, Integer city, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.clientCode = clientCode;
        this.cnp = cnp;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public UserData(String firstName, String lastName, String email, String clientCode, String cnp, LocalDate birthdate, String phoneNumber, String address, Integer city, String postalCode, boolean sendEmail, boolean termsAndConditions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.clientCode = clientCode;
        this.cnp = cnp;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.sendEmail = sendEmail;
        this.termsAndConditions = termsAndConditions;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id_user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", cnp='" + cnp + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

}
