package com.oracle.studies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.mockito.Mockito.mock;

@Configuration
public class TestApplicationConfig {

    @Bean
    public EntityManagerFactory entityManagerFactoryMock() { return mock(EntityManagerFactory.class);}

    @Bean
    public IUserRepository userRepoMock() { return mock(IUserRepository.class); }
}
