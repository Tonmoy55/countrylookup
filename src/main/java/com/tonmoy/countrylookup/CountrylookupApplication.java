package com.tonmoy.countrylookup;

import com.tonmoy.countrylookup.service.RequestLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CountrylookupApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountrylookupApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
