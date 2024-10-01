package com.andrapotlog.CRM.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDataDTO {
    private long id_user;

    private String firstName;

    private String lastName;

    private String email;

    private String clientCode;

    private String cnp;

    private LocalDate birthdate;

    private String phoneNumber;

    private String address;

    private Integer city;

    private String postalCode;

    private boolean sendEmail;

    private boolean termsAndConditions;

    public UserDataDTO() {
    }

    public UserDataDTO(String firstName, String lastName, String email, String clientCode, String cnp, String phoneNumber, String address, Integer city, String postalCode) {
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

    public UserDataDTO(long id_user, String firstName, String lastName, String email, String clientCode, String cnp, LocalDate birthdate, String phoneNumber, String address, Integer city, String postalCode, boolean sendEmail, boolean termsAndConditions) {
        this.id_user = id_user;
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
}
