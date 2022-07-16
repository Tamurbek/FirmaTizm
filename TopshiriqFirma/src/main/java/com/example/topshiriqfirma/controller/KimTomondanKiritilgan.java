package com.example.topshiriqfirma.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class KimTomondanKiritilgan {
    @Bean
    AuditorAware<Integer> auditorAware(){
        return new AddUser();
    }
}
