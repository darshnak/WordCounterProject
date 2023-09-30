package com.word.javawordcounter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//purpose is to configure the application's security.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF protection
            .authorizeRequests()
                .antMatchers("/word-counter/add-words").permitAll() // Allow public access to "/word-counter/add-words"
                .anyRequest().authenticated() // Require authentication for other requests
                .and()
            .formLogin()
                .permitAll() // Allow login page to be accessed by anyone
                .and()
            .logout()
                .permitAll(); // Allow logout to be accessed by anyone
    }
}
