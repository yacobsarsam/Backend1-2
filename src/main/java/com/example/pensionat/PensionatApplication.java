package com.example.pensionat;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Repositories.RumRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootApplication
public class PensionatApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(PensionatApplication.class, args);
        } else if (Objects.equals(args[0], "fetchCustomers")) {
            SpringApplication application = new SpringApplication(CustomersConsoleApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "fetchShippers")) {
            SpringApplication application = new SpringApplication(ShippersConsoleApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "fetchRoomEvents")) {
            SpringApplication application = new SpringApplication(RoomEventApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }

    @Bean
    public CommandLineRunner enter(RumRepo rr, KundRepo kundRepo, BokningRepo bokningRepo) {
        return args -> {

            if ((rr.findAll().stream().findFirst().orElse(null)) == null) {

                Rum r1 = new Rum(false, 1, 10, 1000);
                Rum r2 = new Rum(false, 1, 11, 1000);
                Rum r3 = new Rum(false, 1, 12, 1000);
                Rum r4 = new Rum(false, 1, 13, 1000);
                Rum r5 = new Rum(true, 2, 20, 1500);
                Rum r6 = new Rum(true, 2, 21, 1500);
                Rum r7 = new Rum(true, 2, 22, 1500);
                Rum r8 = new Rum(true, 2, 23, 1500);
                Rum r9 = new Rum(true, 3, 30, 1500);
                Rum r10 = new Rum(true, 3, 31, 1500);
                Rum r11 = new Rum(true, 3, 32, 1500);
                Rum r12 = new Rum(true, 3, 33, 1500);
                rr.save(r1);
                rr.save(r2);
                rr.save(r3);
                rr.save(r4);
                rr.save(r5);
                rr.save(r6);
                rr.save(r7);
                rr.save(r8);
                rr.save(r9);
                rr.save(r10);
                rr.save(r11);
                rr.save(r12);
            }
        };
    }
}
