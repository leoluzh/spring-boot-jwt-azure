package com.lambdays.spring.boot.jwt.azure.controller;

import com.lambdays.spring.boot.jwt.azure.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;

@RestController
@RequestMapping("/api/v1/security")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController implements Serializable {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public void authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        authenticationRequest.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
    }

}
