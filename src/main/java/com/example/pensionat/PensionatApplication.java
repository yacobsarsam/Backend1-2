package com.example.pensionat;

import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.RumRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PensionatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PensionatApplication.class, args);
    }
    @Bean
    public CommandLineRunner enter(RumRepo rr){
        return args -> {
            Rum r1 = new Rum(false, 1, 10);
            Rum r2 = new Rum(false, 1, 11);
            Rum r3 = new Rum(false, 1, 12);
            Rum r4 = new Rum(false, 1, 13);
            Rum r5 = new Rum(true, 2, 20);
            Rum r6 = new Rum(true, 2, 21);
            Rum r7 = new Rum(true, 2, 22);
            Rum r8 = new Rum(true, 2, 23);
            Rum r9 = new Rum(true, 3, 30);
            Rum r10 = new Rum(true, 3, 31);
            Rum r11 = new Rum(true, 3, 32);
            Rum r12 = new Rum(true, 3, 33);
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

        };
    }



}
