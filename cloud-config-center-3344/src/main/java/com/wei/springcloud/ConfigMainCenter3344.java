package com.wei.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigMainCenter3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigMainCenter3344.class,args);
    }
}
