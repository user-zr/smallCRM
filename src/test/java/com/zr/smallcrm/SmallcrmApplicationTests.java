package com.zr.smallcrm;

import com.zr.smallcrm.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SmallcrmApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(MD5Util.getMD5("123"));

    }

}
