package com.zr.smallcrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@MapperScan("com.zr.smallcrm.dao")
@ServletComponentScan(basePackages = "com.zr.smallcrm.*")
public class SmallcrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallcrmApplication.class, args);
    }

}
