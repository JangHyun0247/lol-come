package com.sparta.lolcome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LolComeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolComeApplication.class, args);
    }

}
