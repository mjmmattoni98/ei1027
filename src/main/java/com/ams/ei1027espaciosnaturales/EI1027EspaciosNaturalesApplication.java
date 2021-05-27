package com.ams.ei1027espaciosnaturales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.SpringVersion;

import java.util.logging.Logger;

@SpringBootApplication
public class EI1027EspaciosNaturalesApplication {
    private static final Logger log = Logger.getLogger(EI1027EspaciosNaturalesApplication.class.getName());

    public static void main(String[] args) {
//        SpringApplication.run(EI1027EspaciosNaturalesApplication.class, args);
        new SpringApplicationBuilder(EI1027EspaciosNaturalesApplication.class).run(args);
    }

}
