package com.packet.mktcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true)
@EnableCaching
public class MktcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MktcenterApplication.class, args);
    }

}
