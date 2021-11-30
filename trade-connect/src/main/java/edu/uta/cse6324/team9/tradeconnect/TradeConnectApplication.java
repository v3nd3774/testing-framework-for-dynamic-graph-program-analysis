package edu.uta.cse6324.team9.tradeconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class TradeConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeConnectApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
