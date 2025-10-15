package com.shea.ghostnets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

/**
 * Start
 */
@SpringBootApplication
public class GhostNetsApplication {
    private static final Logger log = LoggerFactory.getLogger(GhostNetsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GhostNetsApplication.class, args);
        log.info("Webanwendung erfolgreich gestartet");
    }
    //debugging konsolen info reasons datenbank creation on start
    @Bean
    CommandLineRunner checkDb(JdbcTemplate jdbc) {
        return args -> {
            List<String> tables = jdbc.queryForList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'", String.class);
            System.out.println("Folgende Datenbank Tabellen exisitieren " + tables);
        };
    }
}
