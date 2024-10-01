package com.andrapotlog.CRM.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
        private UserPasswordDTO userCredentials;
        private UserDataDTO userData;

        public UserRegistrationDTO(UserPasswordDTO userCredentials, UserDataDTO userData) {
                this.userCredentials = userCredentials;
                this.userData = userData;
        }
}
