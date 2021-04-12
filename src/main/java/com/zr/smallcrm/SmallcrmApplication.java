package com.zr.smallcrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zr.smallcrm.dao")
public class SmallcrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallcrmApplication.class, args);
    }

}
