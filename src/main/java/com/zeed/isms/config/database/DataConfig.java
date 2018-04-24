package com.zeed.isms.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = "com.zeed.isms.lib.repository", entityManagerFactoryRef = "localContainerEntityManagerFactoryBean" ,transactionManagerRef = "platformTransactionManager" )
public class DataConfig {

    @Value("${usersource.jdbc.user}")
    private String username;
    @Value("${usersource.jdbc.password}")
    private String password;
    @Value("${usersource.jdbc.url}")
    private String url;
    @Value("${usersource.jdbc.driverClassName}")
    private String driverClassName;

@Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                localContainerEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(
                new String[] {"com.zeed.isms.lib.models" });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect","org.hibernate.dialect.SQLServerDialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

}
