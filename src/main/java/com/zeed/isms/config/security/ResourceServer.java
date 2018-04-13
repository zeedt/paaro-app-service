package com.zeed.isms.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import redis.clients.jedis.JedisShardInfo;

@Configuration
public class ResourceServer extends ResourceServerConfigurerAdapter{


    @Value("${isms.oauth.resource.id}")
    private String resourceId;

    @Value("${oauth2.verifierKey}")
    private String oauth2VerifierKey;

    @Value("${cosmos.redis.host}")
    private String redisHost;

    @Value("${cosmos.redis.port}")
    private String redisPort;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId)
                .authenticationManager(new OAuth2AuthenticationManager())
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
//                .antMatchers("/getHash").permitAll()
//                .antMatchers("/wwwww").permitAll()
                .antMatchers(
                        new String[]{"/app/**",
                                "/login",
//                                "/getHash",
                                "**/home",
                                "/css/**",
                                "/js/**",
                                "/dashboard/**",
                                "/image/**",
                                "/assets/**",
                                "/localusers/**",
                                "/activations/**"
                        }
                ).permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(jedisConnectionFactory());
    }
    private JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(Integer.valueOf(redisPort));
        jedisConnectionFactory.setShardInfo(new JedisShardInfo(redisHost, redisPort));
        return jedisConnectionFactory;
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(oauth2VerifierKey);
        return converter;
    }
}
