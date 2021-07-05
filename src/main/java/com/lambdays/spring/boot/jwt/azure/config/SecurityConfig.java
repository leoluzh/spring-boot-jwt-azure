package com.lambdays.spring.boot.jwt.azure.config;

import com.lambdays.spring.boot.jwt.azure.filter.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public void configure(final HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                //.formLogin().loginPage(JwtConfig.SINGUP_URL)
                //.and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, JwtConfig.STATUS_URL).permitAll()
                .antMatchers(HttpMethod.POST, JwtConfig.SINGUP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter( new JWTAuthenticationFilter( this.authenticationManager() , this.jwtConfig ))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailService).passwordEncoder(this.passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
