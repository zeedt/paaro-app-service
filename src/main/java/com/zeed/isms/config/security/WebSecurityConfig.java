package com.zeed.isms.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
//                    .antMatchers(new String[]{"/**"}).permitAll()
                    .antMatchers(new String[]{"/login"}).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .failureUrl("/login?error")
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();
        }

    @Bean
    public CORSFilter corsFilter() {
        return new CORSFilter();
    }

}