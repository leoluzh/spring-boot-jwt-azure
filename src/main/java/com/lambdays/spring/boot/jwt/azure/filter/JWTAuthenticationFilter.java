package com.lambdays.spring.boot.jwt.azure.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdays.spring.boot.jwt.azure.config.JwtConfig;
import com.lambdays.spring.boot.jwt.azure.model.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(JwtConfig.SINGUP_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) throws AuthenticationException {

        try {

            final var credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            final var upat = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    new ArrayList<>());

            return authenticationManager.authenticate(upat);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication) throws IOException, ServletException {

        var token = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() * JwtConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(this.jwtConfig.getSecret()));

        response.addHeader(
                JwtConfig.HEADER_AUTHORIZATION,
                JwtConfig.TOKEN_PREFIX + token);
    }

}
