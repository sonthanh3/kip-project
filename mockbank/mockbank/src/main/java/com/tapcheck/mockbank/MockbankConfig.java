package com.tapcheck.mockbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MockbankConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
