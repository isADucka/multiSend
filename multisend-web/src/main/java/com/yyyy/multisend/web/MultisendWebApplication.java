package com.yyyy.multisend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com", "com.yyyy.multisend.handler.pojo"})
//@ComponentScan(basePackageClasses = com.yyyy.multisend.handler.pojo.SsmClient.class)
public class MultisendWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultisendWebApplication.class, args);
    }



}
