package com.shan.tech.javlib;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.shan.tech.javlib.mapper")
public class JavlibApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavlibApplication.class, args);
    }

}
