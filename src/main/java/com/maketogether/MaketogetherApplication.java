package com.maketogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MaketogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaketogetherApplication.class, args);
    }

}
