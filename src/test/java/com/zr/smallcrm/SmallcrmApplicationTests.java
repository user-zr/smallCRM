package com.zr.smallcrm;

import com.zr.smallcrm.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SmallcrmApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(testss());
    }

    private int testss() {
        int j=1;
        try {
            int i = 1 / 0;
        } catch (Exception e) {
           return j=2;
        } finally {
           return j=3;
        }
    }
}
