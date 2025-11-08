package com.coachme.coachme.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String sayHello(){
        return "Welcome to Coach Me! Your Learning journey starts here";
    }

    @GetMapping("/api/info")
    public String getInfo(){
        return "Coachme backend v1.0 - powered by Spring-boot";
    }

}
