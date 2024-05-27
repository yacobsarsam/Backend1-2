package com.example.pensionat.Properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.queue")
@Getter
@Setter
public class QueueProperties {
    private String host;
    private String queuename;
    private String username;
    private String password;
}
