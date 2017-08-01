package com.rentacar.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Andrei.Plesca
 */
@Configuration
@EnableAutoConfiguration
@Import({TestJPAConfig.class, ServiceConfig.class})
public class TestWebConfig {


}