package com.bus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@SpringBootApplication
@MapperScan("com.bus.mapper")
public class PublicTransApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicTransApplication.class, args);
    }

}