package com.lambdays.spring.boot.jwt.azure.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lambdays.spring.boot.jwt.azure.config.JwtConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtConfig jwtConfig;

    public JWTAuthorizationFilter( final AuthenticationManager authenticationManager , final JwtConfig jwtConfig ){
        super(authenticationManager);
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request ,
                                    final HttpServletResponse response ,
                                    final FilterChain chain ) throws IOException , ServletException {

        final var header = request.getHeader(JwtConfig.HEADER_AUTHORIZATION);

        if( !StringUtils.startsWith(header,JwtConfig.SINGUP_URL) ){
            chain.doFilter(request,response);
            return;
        }

        final var upat = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(upat);
        chain.doFilter(request,response);

    }

    protected UsernamePasswordAuthenticationToken getAuthentication(final String token) {

        final var bearer = StringUtils.substring(token,JwtConfig.TOKEN_PREFIX.length());
        final var username = JWT
                .require(Algorithm.HMAC512(jwtConfig.getSecret().getBytes()))
                .build()
                .verify(bearer)
                .getSubject();

        if( StringUtils.isNotBlank( username ) ){
            return new UsernamePasswordAuthenticationToken(username,null, new ArrayList<>());
        }

        return null;

    }

}
