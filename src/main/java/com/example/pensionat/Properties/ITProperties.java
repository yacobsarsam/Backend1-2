package com.example.pensionat.Properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integrations")
@Getter
@Setter
public class ITProperties {
    private BlackListProperties blacklist;
    private ContractCustomerProperties contractcustomer;
    private ShipperProperties shipper;
}
