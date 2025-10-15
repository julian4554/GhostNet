package com.shea.ghostnets.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//idk trying to stop :8080/api/nets/available:1 Failed to load resource: the server responded with a status of 500 ()Understand this error
// problem leere objekte/serializing gefunden, belasse diese klasse weil prototyp und unterdrücke
//https://www.baeldung.com/jackson-jsonmappingexception#:~:text=We%20can%20disable%20fail_on_empty_beans%20using,FAIL_ON_EMPTY_BEANS%2C%20false)%3B
// kleiner workaround
@Configuration
public class JacksonConfig {

    private static final Logger log = LoggerFactory.getLogger(JacksonConfig.class);

    @PostConstruct
    public void init() {
        log.info("FAIL_ON_EMPTY_BEANS deaktiviert");
    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return mapper;
    }
}
