package com.example.pensionat.Properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
public class ConfirmationMailProperties {

        private Boolean showRoomNumber = true;
        private Boolean showDate = true;
        private Boolean showNumOfBeds = true;
        private Boolean showTotalPrice = true;
        private Boolean showName = false;
        private Boolean showPhoneNumber = true;
        private Boolean showEmail = true;
}

//Might delete file
