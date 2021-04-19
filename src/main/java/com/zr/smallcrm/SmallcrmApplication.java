package com.zr.smallcrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.zr.smallcrm.dao")
@ServletComponentScan
public class SmallcrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallcrmApplication.class, args);
    }

}
