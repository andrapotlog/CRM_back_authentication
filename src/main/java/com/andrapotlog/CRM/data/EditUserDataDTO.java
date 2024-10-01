package com.andrapotlog.CRM.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class EditUserDataDTO {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Integer city;

    private String postalCode;

    private boolean sendEmail;

}