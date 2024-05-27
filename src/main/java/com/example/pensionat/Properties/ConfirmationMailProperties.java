package com.example.pensionat.Properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "confirmation.mail")
@Getter
@Setter
public class ConfirmationMailProperties {


        private Boolean showRoomNumber;
        private Boolean showDate;
        private Boolean showNumOfBeds;
        private Boolean showTotalPrice;
        private Boolean showName;
        private Boolean showPhoneNumber;
        private Boolean showEmail;

}
