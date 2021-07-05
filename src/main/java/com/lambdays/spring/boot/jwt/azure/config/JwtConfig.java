package com.lambdays.spring.boot.jwt.azure.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig implements Serializable {

    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 15 ;
    public static final String TOKEN_PREFIX = "Bearer " ;
    public static final String HEADER_AUTHORIZATION = "Authorization" ;
    public static final String SINGUP_URL = "/api/v1/security/authenticate" ;
    public static final String STATUS_URL = "/actuator" ;

    private String secret;

}
