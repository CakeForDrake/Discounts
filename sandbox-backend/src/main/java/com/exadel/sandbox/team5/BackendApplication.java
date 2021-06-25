package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.barcodes.QRCodeGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan
@EnableAutoConfiguration
public class BackendApplication {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public QRCodeGenerator qrCodeGenerator() {
        return new QRCodeGenerator();
    }
}
