package com.andrapotlog.CRM.data;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataDTO {
    private long id_user;

    private String firstName;

    private String lastName;

    private String email;

    private String clientCode;

    private String cnp;

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    private String postalCode;

    public UserDataDTO() {
    }

    public UserDataDTO(long id_user, String firstName, String lastName, String email, String clientCode, String cnp, String phoneNumber, String address, String city, String country, String postalCode) {
        this.id_user = id_user;
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
