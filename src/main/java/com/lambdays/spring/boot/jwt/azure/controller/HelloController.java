package com.lambdays.spring.boot.jwt.azure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController implements Serializable {

    @GetMapping("/group1")
    @PreAuthorize("hasRole('ROLE_group1')")
    public String group1(){
        return "Hello Group 1 users!!!";
    }

    @GetMapping("/group2")
    @PreAuthorize("hasRole('ROLE_group2')")
    public String group2(){
        return "Hello Group 2 users!!!";
    }

}
