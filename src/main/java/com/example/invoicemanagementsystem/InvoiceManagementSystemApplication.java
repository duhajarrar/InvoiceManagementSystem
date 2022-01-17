package com.example.invoicemanagementsystem;

import com.example.invoicemanagementsystem.model.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class InvoiceManagementSystemApplication {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(InvoiceManagementSystemApplication.class, args);
    }

}
