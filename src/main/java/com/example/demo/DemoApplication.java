package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan // Scan for ConfigurationProperties
// Alternatively: Specify ConfigurationProperties class explicitly
// @EnableConfigurationProperties({ExtSysConfig.class, ExtSysConfigRecord.class, Settings.class})
public class DemoApplication {

    Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(
            @Value("${message-from-application-properties:OOPS!}") String valueDoesExist,
            @Value("${mesage-from-application-properties:OOPS!}") String valueDoesNotExist
    ) {
        return args -> {
            log.debug("this is debug logging");
            log.info("message from application.properties {}", valueDoesExist);
            log.info("missing message from application.properties {}", valueDoesNotExist);
        };
    }
}
