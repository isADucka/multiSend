package com.yyyy.multisend.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author isADuckA
 * @Date 2023/4/9 18:17
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public String health(){
        return "health-test";
    }
}
