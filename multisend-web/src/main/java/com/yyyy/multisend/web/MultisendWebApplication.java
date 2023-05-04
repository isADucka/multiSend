package com.yyyy.multisend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com"})
//@ComponentScan(value = "com")

@EnableJpaRepositories("com.yyyy.multisend.dao.Mapper")
@EntityScan("com.yyyy.multisend.common.povo.po")
public class MultisendWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultisendWebApplication.class, args);
    }



}
