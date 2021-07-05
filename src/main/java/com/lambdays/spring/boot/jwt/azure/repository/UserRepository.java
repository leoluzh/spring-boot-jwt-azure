package com.lambdays.spring.boot.jwt.azure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepository {

    private PasswordEncoder passwordEncoder;

    public User findByUsername(final String username) {
        return new User(username, passwordEncoder.encode("12345"), Collections.emptyList());
    }

    public List<User> findAll(){
        final var users = new ArrayList<User>();
        users.add(findByUsername("jonh.doe"));
        return users;
    }

}
