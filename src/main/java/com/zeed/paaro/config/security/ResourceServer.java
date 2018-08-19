package com.zeed.paaro.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
public class ResourceServer extends ResourceServerConfigurerAdapter{


    @Value("${paaro.oauth.resource.id}")
    private String resourceId;

    @Value("${oauth2.verifierKey}")
    private String oauth2VerifierKey;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId)
                .authenticationManager(new OAuth2AuthenticationManager())
                .tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/view/users").permitAll()
                .antMatchers("/view/view-authorities").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/regDetails/**").permitAll()
                .antMatchers(
                        new String[]{"/app/**",
                                "/login",
                                "/regDetails/**",
                                "**/home",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/fonts/**",
                                "/font-awesome/**",
                                "/template-js/**",
                                "/template-pages/**",
                                "/template-css/**",
                                "/",
                                "/authority/**",
                                "/favicon.ico",
                                "/dashboard/**",
                                "/image/**",
                                "/assets/**"
                        }
                ).permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .csrf()
                .disable();
    }


    @Bean
    public JdbcTokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(oauth2VerifierKey);
        return converter;
    }
}
